package com.midi.midipad;

import android.content.Context;
import android.media.midi.MidiDevice;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiInputPort;
import android.media.midi.MidiManager;
import android.media.midi.MidiOutputPort;
import android.media.midi.MidiReceiver;
import android.os.Build;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

final class UsbMidiBridge implements MidiByteSender {
    private static final String TAG_OUT = "MIDI_OUT";
    private static final String TAG_IN = "MIDI_IN";
    private final Context context;
    private MidiManager midiManager;
    private MidiDevice currentTxDevice;
    private MidiDevice currentRxDevice;
    private MidiInputPort currentInputPort;
    private MidiOutputPort currentOutputPort;
    private final List<MidiInputPort> extraInputPorts = new ArrayList<>();
    private final List<MidiDevice> extraTxDevices = new ArrayList<>();
    private final List<MidiOutputPort> extraOutputPorts = new ArrayList<>();
    private final List<MidiDevice> extraRxDevices = new ArrayList<>();
    private MidiPacketListener packetListener;

    UsbMidiBridge(Context context) {
        this.context = context.getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.midiManager = (MidiManager) this.context.getSystemService(Context.MIDI_SERVICE);
        }
    }

    boolean isSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.midiManager != null;
    }

    synchronized void setPacketListener(MidiPacketListener packetListener) {
        this.packetListener = packetListener;
    }

    String[] getOutputDisplayNames() {
        if (!isSupported()) {
            return new String[0];
        }
        List<DeviceTarget> targets = listDeviceTargets();
        String[] names = new String[targets.size()];
        for (int i = 0; i < targets.size(); i++) {
            DeviceTarget target = targets.get(i);
            String product = target.info.getProperties().getString(MidiDeviceInfo.PROPERTY_PRODUCT);
            if (product == null || product.length() == 0) {
                product = target.info.getProperties().getString(MidiDeviceInfo.PROPERTY_NAME);
            }
            if (product == null || product.length() == 0) {
                product = "USB MIDI Device";
            }
            StringBuilder suffix = new StringBuilder();
            if (target.inputPortNumber >= 0) {
                suffix.append("Tx");
            }
            if (target.outputPortNumber >= 0) {
                if (suffix.length() > 0) {
                    suffix.append("/");
                }
                suffix.append("Rx");
            }
            names[i] = suffix.length() == 0 ? product : product + " (" + suffix + ")";
        }
        return names;
    }

    boolean openOutputByIndex(int index) {
        if (!isSupported()) {
            return false;
        }
        List<DeviceTarget> targets = listDeviceTargets();
        if (index < 0 || index >= targets.size()) {
            Log.w(TAG_OUT, "openOutputByIndex invalid index=" + index + " size=" + targets.size());
            return false;
        }
        DeviceTarget target = targets.get(index);
        boolean opened = openTarget(targets, target);
        Log.i(TAG_OUT, "openOutputByIndex index=" + index + " opened=" + opened + " target=" + describeDevice(target.info));
        return opened;
    }

    boolean openFirstOutput() {
        return openOutputByIndex(0);
    }

    int getRecommendedTargetIndex() {
        List<DeviceTarget> targets = listDeviceTargets();
        for (int i = 0; i < targets.size(); i++) {
            DeviceTarget t = targets.get(i);
            if (t.inputPortNumber >= 0 && t.outputPortNumber >= 0) {
                return i;
            }
        }
        for (int i = 0; i < targets.size(); i++) {
            if (targets.get(i).inputPortNumber >= 0) {
                return i;
            }
        }
        return targets.isEmpty() ? -1 : 0;
    }

    boolean reopenBestTxTarget(int preferredIndex) {
        List<DeviceTarget> targets = listDeviceTargets();
        if (targets.isEmpty()) {
            return false;
        }

        int pick = -1;
        if (preferredIndex >= 0 && preferredIndex < targets.size() && targets.get(preferredIndex).inputPortNumber >= 0) {
            pick = preferredIndex;
        }
        if (pick < 0) {
            for (int i = 0; i < targets.size(); i++) {
                DeviceTarget t = targets.get(i);
                if (t.inputPortNumber >= 0 && t.outputPortNumber >= 0) {
                    pick = i;
                    break;
                }
            }
        }
        if (pick < 0) {
            for (int i2 = 0; i2 < targets.size(); i2++) {
                if (targets.get(i2).inputPortNumber >= 0) {
                    pick = i2;
                    break;
                }
            }
        }
        if (pick < 0) {
            Log.w(TAG_OUT, "reopenBestTxTarget found no TX-capable endpoints");
            return false;
        }

        DeviceTarget selected = targets.get(pick);
        boolean opened = openTarget(targets, selected);
        Log.i(TAG_OUT, "reopenBestTxTarget index=" + pick + " opened=" + opened + " target=" + describeDevice(selected.info));
        return opened;
    }

    synchronized boolean canSend() {
        return this.currentInputPort != null || !this.extraInputPorts.isEmpty();
    }

    synchronized boolean canReceive() {
        return this.currentOutputPort != null || !this.extraOutputPorts.isEmpty();
    }

    @Override
    public synchronized void sendMidi(byte[] data) throws Exception {
        if (!isValidOutgoingMidi(data)) {
            throw new IllegalArgumentException("Malformed MIDI packet");
        }
        if (this.currentInputPort == null) {
            tryRecoverTxPort();
        }
        if (this.currentInputPort == null && this.extraInputPorts.isEmpty()) {
            throw new IllegalStateException("No MIDI Tx port available");
        }
        try {
            if (this.currentInputPort != null) {
                this.currentInputPort.send(data, 0, data.length);
                Log.d(TAG_OUT, "bridge tx bytes=" + bytesToHex(data));
            }
            for (MidiInputPort p : this.extraInputPorts) {
                try {
                    p.send(data, 0, data.length);
                    Log.d(TAG_OUT, "bridge tx extra bytes=" + bytesToHex(data));
                } catch (Exception ignored) {
                }
            }
        } catch (Exception sendError) {
            // Some hosts drop/recreate endpoints after reconnect; retry once.
            tryRecoverTxPort();
            if (this.currentInputPort == null && this.extraInputPorts.isEmpty()) {
                throw sendError;
            }
            if (this.currentInputPort != null) {
                this.currentInputPort.send(data, 0, data.length);
                Log.d(TAG_OUT, "bridge tx retry bytes=" + bytesToHex(data));
            }
            for (MidiInputPort p2 : this.extraInputPorts) {
                try {
                    p2.send(data, 0, data.length);
                    Log.d(TAG_OUT, "bridge tx retry extra bytes=" + bytesToHex(data));
                } catch (Exception ignored2) {
                }
            }
        }
    }

    private boolean isValidOutgoingMidi(byte[] data) {
        if (data == null || data.length == 0) {
            return false;
        }
        int status = data[0] & 255;
        if (status < 128) {
            return false;
        }
        if (status == 240) {
            return data.length >= 2 && (data[data.length - 1] & 255) == 247;
        }
        if (status >= 248) {
            return data.length == 1;
        }
        int high = status & 240;
        if (high == 192 || high == 208) {
            return data.length == 2;
        }
        if (high >= 128 && high <= 224) {
            return data.length == 3;
        }
        if (status == 241 || status == 243) {
            return data.length == 2;
        }
        if (status == 242) {
            return data.length == 3;
        }
        return true;
    }

    synchronized void close() {
        MidiDevice txDevice = this.currentTxDevice;
        MidiDevice rxDevice = this.currentRxDevice;
        List<MidiInputPort> txPorts = new ArrayList<>(this.extraInputPorts);
        List<MidiDevice> txExtras = new ArrayList<>(this.extraTxDevices);
        List<MidiOutputPort> outputs = new ArrayList<>(this.extraOutputPorts);
        List<MidiDevice> rxExtras = new ArrayList<>(this.extraRxDevices);
        this.extraInputPorts.clear();
        this.extraTxDevices.clear();
        this.extraOutputPorts.clear();
        this.extraRxDevices.clear();
        try {
            if (this.currentInputPort != null) {
                this.currentInputPort.close();
            }
        } catch (Exception ignored) {
        }
        this.currentInputPort = null;
        for (MidiInputPort p : txPorts) {
            try {
                p.close();
            } catch (Exception ignoredTxPorts) {
            }
        }
        try {
            if (this.currentOutputPort != null) {
                this.currentOutputPort.close();
            }
        } catch (Exception ignored) {
        }
        this.currentOutputPort = null;
        for (MidiOutputPort p : outputs) {
            try {
                p.close();
            } catch (Exception ignoredPorts) {
            }
        }
        try {
            if (txDevice != null) {
                txDevice.close();
            }
        } catch (Exception ignored) {
        }
        this.currentTxDevice = null;
        for (MidiDevice dtx : txExtras) {
            try {
                if (dtx != txDevice && dtx != rxDevice) {
                    dtx.close();
                }
            } catch (Exception ignoredTxDev) {
            }
        }
        try {
            if (rxDevice != null && rxDevice != txDevice) {
                rxDevice.close();
            }
        } catch (Exception ignored) {
        }
        this.currentRxDevice = null;
        for (MidiDevice d : rxExtras) {
            try {
                if (d != txDevice && d != rxDevice) {
                    d.close();
                }
            } catch (Exception ignoredExtra) {
            }
        }
    }

    private boolean openTarget(List<DeviceTarget> targets, DeviceTarget selectedTarget) {
        if (targets == null || targets.isEmpty() || selectedTarget == null) {
            return false;
        }

        close();

        List<DeviceTarget> ordered = orderedTargets(targets, selectedTarget);

        MidiDevice txDevice = null;
        MidiDeviceInfo txInfo = null;
        MidiInputPort inputPort = null;
        List<MidiInputPort> extraTxPorts = new ArrayList<>();
        List<MidiDevice> extraTxDevs = new ArrayList<>();
        for (DeviceTarget candidate : ordered) {
            if (!hasInputPort(candidate.info)) {
                continue;
            }
            MidiDevice candidateDevice = openDeviceBlocking(candidate.info);
            if (candidateDevice == null) {
                continue;
            }
            List<MidiInputPort> candidatePorts = openAllInputPorts(candidateDevice, candidate.info);
            if (!candidatePorts.isEmpty()) {
                if (txDevice == null) {
                    txDevice = candidateDevice;
                    txInfo = candidate.info;
                    inputPort = candidatePorts.get(0);
                    for (int i = 1; i < candidatePorts.size(); i++) {
                        extraTxPorts.add(candidatePorts.get(i));
                    }
                } else {
                    extraTxPorts.addAll(candidatePorts);
                    extraTxDevs.add(candidateDevice);
                }
                continue;
            }
            try {
                candidateDevice.close();
            } catch (Exception ignored) {
            }
        }

        MidiDevice rxDevice = null;
        MidiOutputPort outputPort = null;
        List<MidiOutputPort> extraPorts = new ArrayList<>();
        List<MidiDevice> extraDevices = new ArrayList<>();
        for (DeviceTarget candidate2 : ordered) {
            if (!hasOutputPort(candidate2.info)) {
                continue;
            }
            boolean reusedTxHandle = txInfo != null && txInfo.getId() == candidate2.info.getId();
            MidiDevice candidateDevice2 = reusedTxHandle ? txDevice : openDeviceBlocking(candidate2.info);
            if (candidateDevice2 == null) {
                continue;
            }
            List<MidiOutputPort> candidatePorts = openAllOutputPorts(candidateDevice2, candidate2.info);
            if (candidatePorts.isEmpty() && reusedTxHandle) {
                MidiDevice fresh = openDeviceBlocking(candidate2.info);
                if (fresh != null) {
                    List<MidiOutputPort> freshPorts = openAllOutputPorts(fresh, candidate2.info);
                    if (!freshPorts.isEmpty()) {
                        candidateDevice2 = fresh;
                        candidatePorts = freshPorts;
                    } else {
                        try {
                            fresh.close();
                        } catch (Exception ignored3) {
                        }
                    }
                }
            }
            if (!candidatePorts.isEmpty()) {
                rxDevice = candidateDevice2;
                outputPort = candidatePorts.get(0);
                for (int i = 0; i < candidatePorts.size(); i++) {
                    MidiOutputPort p = candidatePorts.get(i);
                    bindOutputPort(p);
                    if (i > 0) {
                        extraPorts.add(p);
                    }
                }
                break;
            }
            if (candidateDevice2 != txDevice) {
                try {
                    candidateDevice2.close();
                } catch (Exception ignored2) {
                }
            }
        }

        synchronized (this) {
            this.currentTxDevice = txDevice;
            this.currentRxDevice = rxDevice;
            this.currentInputPort = inputPort;
            this.currentOutputPort = outputPort;
            this.extraInputPorts.clear();
            this.extraInputPorts.addAll(extraTxPorts);
            this.extraTxDevices.clear();
            for (MidiDevice dtx : extraTxDevs) {
                if (dtx != null && dtx != txDevice && dtx != rxDevice) {
                    this.extraTxDevices.add(dtx);
                }
            }
            this.extraOutputPorts.clear();
            this.extraOutputPorts.addAll(extraPorts);
            this.extraRxDevices.clear();
            if (rxDevice != null && rxDevice != txDevice) {
                this.extraRxDevices.add(rxDevice);
            }
            for (MidiDevice d : extraDevices) {
                if (d != null && d != txDevice && d != rxDevice) {
                    this.extraRxDevices.add(d);
                }
            }
        }
        Log.i(TAG_OUT, "openTarget result tx=" + (inputPort != null || !extraTxPorts.isEmpty())
                + " rx=" + (outputPort != null || !extraPorts.isEmpty())
                + " selected=" + describeDevice(selectedTarget.info));
        return inputPort != null || !extraTxPorts.isEmpty() || outputPort != null;
    }

    private MidiDevice openDeviceBlocking(final MidiDeviceInfo info) {
        final CountDownLatch latch = new CountDownLatch(1);
        final MidiDevice[] opened = new MidiDevice[1];
        MidiManager mm = this.midiManager;
        if (mm == null || info == null) {
            return null;
        }
        mm.openDevice(info, new MidiManager.OnDeviceOpenedListener() {
            @Override
            public void onDeviceOpened(MidiDevice device) {
                opened[0] = device;
                latch.countDown();
            }
        }, null);
        try {
            latch.await(2500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return opened[0];
    }

    private List<DeviceTarget> orderedTargets(List<DeviceTarget> targets, DeviceTarget selectedTarget) {
        List<DeviceTarget> ordered = new ArrayList<>();
        ordered.add(selectedTarget);
        for (DeviceTarget t : targets) {
            if (t.info.getId() != selectedTarget.info.getId()) {
                ordered.add(t);
            }
        }
        return ordered;
    }

    private boolean hasInputPort(MidiDeviceInfo info) {
        MidiDeviceInfo.PortInfo[] ports = info.getPorts();
        for (MidiDeviceInfo.PortInfo port : ports) {
            if (port.getType() == MidiDeviceInfo.PortInfo.TYPE_INPUT) {
                return true;
            }
        }
        return false;
    }

    private boolean hasOutputPort(MidiDeviceInfo info) {
        MidiDeviceInfo.PortInfo[] ports = info.getPorts();
        for (MidiDeviceInfo.PortInfo port : ports) {
            if (port.getType() == MidiDeviceInfo.PortInfo.TYPE_OUTPUT) {
                return true;
            }
        }
        return false;
    }

    private MidiInputPort openFirstInputPort(MidiDevice device, MidiDeviceInfo info) {
        MidiDeviceInfo.PortInfo[] ports = info.getPorts();
        for (MidiDeviceInfo.PortInfo port : ports) {
            if (port.getType() == MidiDeviceInfo.PortInfo.TYPE_INPUT) {
                MidiInputPort p = device.openInputPort(port.getPortNumber());
                if (p != null) {
                    return p;
                }
            }
        }
        return null;
    }

    private MidiOutputPort openFirstOutputPort(MidiDevice device, MidiDeviceInfo info) {
        MidiDeviceInfo.PortInfo[] ports = info.getPorts();
        for (MidiDeviceInfo.PortInfo port : ports) {
            if (port.getType() == MidiDeviceInfo.PortInfo.TYPE_OUTPUT) {
                MidiOutputPort p = device.openOutputPort(port.getPortNumber());
                if (p != null) {
                    return p;
                }
            }
        }
        return null;
    }

    private List<MidiInputPort> openAllInputPorts(MidiDevice device, MidiDeviceInfo info) {
        List<MidiInputPort> out = new ArrayList<>();
        MidiDeviceInfo.PortInfo[] ports = info.getPorts();
        for (MidiDeviceInfo.PortInfo port : ports) {
            if (port.getType() == MidiDeviceInfo.PortInfo.TYPE_INPUT) {
                MidiInputPort p = device.openInputPort(port.getPortNumber());
                if (p != null) {
                    out.add(p);
                }
            }
        }
        return out;
    }

    private List<MidiOutputPort> openAllOutputPorts(MidiDevice device, MidiDeviceInfo info) {
        List<MidiOutputPort> out = new ArrayList<>();
        MidiDeviceInfo.PortInfo[] ports = info.getPorts();
        for (MidiDeviceInfo.PortInfo port : ports) {
            if (port.getType() == MidiDeviceInfo.PortInfo.TYPE_OUTPUT) {
                MidiOutputPort p = device.openOutputPort(port.getPortNumber());
                if (p != null) {
                    out.add(p);
                }
            }
        }
        return out;
    }

    private void bindOutputPort(MidiOutputPort outputPort) {
        if (outputPort == null) {
            return;
        }
        outputPort.connect(new MidiReceiver() {
            @Override
            public void onSend(byte[] msg, int offset, int count, long timestamp) {
                MidiPacketListener listener;
                synchronized (UsbMidiBridge.this) {
                    listener = packetListener;
                }
                if (listener != null && msg != null && count > 0 && offset >= 0 && offset + count <= msg.length) {
                    byte[] copy = Arrays.copyOfRange(msg, offset, offset + count);
                    Log.d(TAG_IN, "bridge rx bytes=" + bytesToHex(copy));
                    listener.onMidiPacket(copy, timestamp);
                } else if (msg != null && count > 0) {
                    Log.w(TAG_IN, "bridge rx dropped due to invalid bounds or missing listener");
                }
            }
        });
    }

    private List<DeviceTarget> listDeviceTargets() {
        List<DeviceTarget> out = new ArrayList<>();
        MidiManager mm = this.midiManager;
        if (mm == null) {
            return out;
        }
        for (MidiDeviceInfo info : mm.getDevices()) {
            if (isSelfServiceDevice(info)) {
                Log.d(TAG_OUT, "scan skip self service " + describeDevice(info));
                continue;
            }
            int inputPortNumber = -1;
            int outputPortNumber = -1;
            MidiDeviceInfo.PortInfo[] ports = info.getPorts();
            for (MidiDeviceInfo.PortInfo portInfo : ports) {
                if (inputPortNumber < 0 && portInfo.getType() == MidiDeviceInfo.PortInfo.TYPE_INPUT) {
                    inputPortNumber = portInfo.getPortNumber();
                } else if (outputPortNumber < 0 && portInfo.getType() == MidiDeviceInfo.PortInfo.TYPE_OUTPUT) {
                    outputPortNumber = portInfo.getPortNumber();
                }
            }
            if (inputPortNumber >= 0 || outputPortNumber >= 0) {
                out.add(new DeviceTarget(info, inputPortNumber, outputPortNumber));
                Log.d(TAG_OUT, "scan target " + describeDevice(info) + " txPort=" + inputPortNumber + " rxPort=" + outputPortNumber);
            } else {
                Log.d(TAG_OUT, "scan ignore no-midi-ports " + describeDevice(info));
            }
        }
        return out;
    }

    private boolean isSelfServiceDevice(MidiDeviceInfo info) {
        if (info == null || info.getProperties() == null) {
            return false;
        }
        String manufacturer = info.getProperties().getString(MidiDeviceInfo.PROPERTY_MANUFACTURER);
        String product = info.getProperties().getString(MidiDeviceInfo.PROPERTY_PRODUCT);
        String name = info.getProperties().getString(MidiDeviceInfo.PROPERTY_NAME);

        boolean oursByMeta = ("BassApps".equalsIgnoreCase(safe(manufacturer))
            || "Gaurav".equalsIgnoreCase(safe(manufacturer)))
                && ("LaunchButtons".equalsIgnoreCase(safe(product))
                || "LaunchButtons".equalsIgnoreCase(safe(name)));
        if (oursByMeta) {
            return true;
        }

        return false;
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String describeDevice(MidiDeviceInfo info) {
        if (info == null || info.getProperties() == null) {
            return "unknown";
        }
        String name = safe(info.getProperties().getString(MidiDeviceInfo.PROPERTY_NAME));
        String product = safe(info.getProperties().getString(MidiDeviceInfo.PROPERTY_PRODUCT));
        String manufacturer = safe(info.getProperties().getString(MidiDeviceInfo.PROPERTY_MANUFACTURER));
        return "id=" + info.getId() + " name=" + name + " product=" + product + " manufacturer=" + manufacturer;
    }

    private static String bytesToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(String.format("%02X", b & 255));
        }
        return sb.toString();
    }

    private void tryRecoverTxPort() {
        MidiManager mm = this.midiManager;
        if (mm == null) {
            return;
        }
        MidiInputPort recoveredPort = null;
        MidiDevice recoveredDevice = null;
        MidiDeviceInfo[] infos = mm.getDevices();

        // First try to recover TX on the same device that currently delivers RX lights.
        MidiDeviceInfo preferred = null;
        if (this.currentRxDevice != null) {
            int rxId = this.currentRxDevice.getInfo().getId();
            for (MidiDeviceInfo info : infos) {
                if (isSelfServiceDevice(info)) {
                    continue;
                }
                if (info.getId() == rxId) {
                    preferred = info;
                    break;
                }
            }
        }

        if (preferred != null && hasInputPort(preferred)) {
            MidiDevice device = openDeviceBlocking(preferred);
            if (device != null) {
                MidiInputPort port = openFirstInputPort(device, preferred);
                if (port != null) {
                    recoveredPort = port;
                    recoveredDevice = device;
                } else {
                    try {
                        device.close();
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        if (recoveredPort == null) {
            for (MidiDeviceInfo info : infos) {
                if (preferred != null && info.getId() == preferred.getId()) {
                    continue;
                }
                if (isSelfServiceDevice(info)) {
                    continue;
                }
                if (!hasInputPort(info)) {
                    continue;
                }
                MidiDevice device = openDeviceBlocking(info);
                if (device == null) {
                    continue;
                }
                MidiInputPort port = openFirstInputPort(device, info);
                if (port != null) {
                    recoveredPort = port;
                    recoveredDevice = device;
                    break;
                }
                try {
                    device.close();
                } catch (Exception ignored) {
                }
            }
        }
        if (recoveredPort == null) {
            return;
        }

        MidiInputPort oldPort = this.currentInputPort;
        MidiDevice oldDevice = this.currentTxDevice;
        List<MidiInputPort> oldExtraPorts = new ArrayList<>(this.extraInputPorts);
        List<MidiDevice> oldExtraDevices = new ArrayList<>(this.extraTxDevices);
        this.currentInputPort = recoveredPort;
        this.currentTxDevice = recoveredDevice;
        this.extraInputPorts.clear();
        this.extraTxDevices.clear();

        try {
            if (oldPort != null) {
                oldPort.close();
            }
        } catch (Exception ignored) {
        }
        try {
            if (oldDevice != null && oldDevice != this.currentRxDevice && oldDevice != recoveredDevice) {
                oldDevice.close();
            }
        } catch (Exception ignored) {
        }
        for (MidiInputPort p : oldExtraPorts) {
            try {
                p.close();
            } catch (Exception ignoredOldPorts) {
            }
        }
        for (MidiDevice dtx : oldExtraDevices) {
            try {
                if (dtx != oldDevice && dtx != this.currentRxDevice && dtx != recoveredDevice) {
                    dtx.close();
                }
            } catch (Exception ignoredOldDev) {
            }
        }

        // If Rx was unavailable but the recovered device has an output port, bind it for lights.
        if (this.currentOutputPort == null && recoveredDevice != null && hasOutputPort(recoveredDevice.getInfo())) {
            MidiOutputPort recoveredOutput = openFirstOutputPort(recoveredDevice, recoveredDevice.getInfo());
            if (recoveredOutput != null) {
                try {
                    bindOutputPort(recoveredOutput);
                    this.currentOutputPort = recoveredOutput;
                    this.currentRxDevice = recoveredDevice;
                } catch (Exception ignored2) {
                    try {
                        recoveredOutput.close();
                    } catch (Exception ignored3) {
                    }
                }
            }
        }
    }

    private static final class DeviceTarget {
        final MidiDeviceInfo info;
        final int inputPortNumber;
        final int outputPortNumber;

        DeviceTarget(MidiDeviceInfo info, int inputPortNumber, int outputPortNumber) {
            this.info = info;
            this.inputPortNumber = inputPortNumber;
            this.outputPortNumber = outputPortNumber;
        }
    }
}
