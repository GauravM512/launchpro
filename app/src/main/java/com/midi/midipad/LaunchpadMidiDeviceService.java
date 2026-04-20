package com.midi.midipad;

import android.media.midi.MidiDeviceService;
import android.media.midi.MidiReceiver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import java.util.Arrays;

public class LaunchpadMidiDeviceService extends MidiDeviceService {
    private static final String TAG_IN = "MIDI_IN";
    private static final String TAG_OUT = "MIDI_OUT";

    private static volatile LaunchpadMidiDeviceService instance;
    private static volatile MidiEventListener midiEventListener;

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private HandlerThread ioThread;
    private Handler ioHandler;

    private final MidiReceiver[] inputReceivers = new MidiReceiver[]{new MidiReceiver() {
        @Override
        public void onSend(byte[] data, int offset, int count, long timestamp) {
            if (data == null || count <= 0 || offset < 0 || offset + count > data.length) {
                return;
            }
            byte[] copy = Arrays.copyOfRange(data, offset, offset + count);
            Log.d(TAG_IN, "rx bytes=" + bytesToHex(copy));
            dispatchIncoming(copy, timestamp);
        }
    }};

    public interface MidiEventListener {
        void onMidiPacket(byte[] data, long timestamp);
    }

    public static LaunchpadMidiDeviceService getInstance() {
        return instance;
    }

    public static void setMidiEventListener(MidiEventListener listener) {
        midiEventListener = listener;
        Log.d(TAG_IN, listener == null ? "listener cleared" : "listener attached");
    }

    public static boolean sendMidi(byte[] data) {
        LaunchpadMidiDeviceService s = instance;
        return s != null && s.sendInternal(data);
    }

    public static boolean isOutputReady() {
        LaunchpadMidiDeviceService s = instance;
        return s != null && s.hasConnectedOutputReceiver();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ioThread = new HandlerThread("launchpad-midi-io");
        ioThread.start();
        ioHandler = new Handler(ioThread.getLooper());
    }

    @Override
    public void onDestroy() {
        if (instance == this) {
            instance = null;
        }
        if (ioThread != null) {
            ioThread.quitSafely();
        }
        super.onDestroy();
    }

    @Override
    public MidiReceiver[] onGetInputPortReceivers() {
        // Persistent receiver array: Android sends host->device MIDI here.
        return inputReceivers;
    }

    private boolean sendInternal(final byte[] data) {
        if (data == null || data.length == 0) {
            Log.w(TAG_OUT, "tx skipped: empty packet");
            return false;
        }
        final MidiReceiver[] receivers = getOutputPortReceivers();
        if (receivers == null || receivers.length == 0) {
            Log.w(TAG_OUT, "tx skipped: output receivers unavailable (host may not have opened output yet)");
            return false;
        }
        boolean hasAny = false;
        for (MidiReceiver receiver : receivers) {
            if (receiver != null) {
                hasAny = true;
                break;
            }
        }
        if (!hasAny) {
            Log.w(TAG_OUT, "tx skipped: all output receivers are null (host output not ready)");
            return false;
        }

        final byte[] packet = Arrays.copyOf(data, data.length);
        ioHandler.post(new Runnable() {
            @Override
            public void run() {
                for (MidiReceiver receiver : receivers) {
                    if (receiver == null) {
                        continue;
                    }
                    try {
                        receiver.send(packet, 0, packet.length, 0);
                        Log.d(TAG_OUT, "tx bytes=" + bytesToHex(packet));
                    } catch (Exception e) {
                        Log.e(TAG_OUT, "send failed", e);
                    }
                }
            }
        });
        return true;
    }

    private boolean hasConnectedOutputReceiver() {
        MidiReceiver[] receivers = getOutputPortReceivers();
        if (receivers == null || receivers.length == 0) {
            return false;
        }
        for (MidiReceiver receiver : receivers) {
            if (receiver != null) {
                return true;
            }
        }
        return false;
    }

    private void dispatchIncoming(final byte[] data, final long timestamp) {
        // Normalize NOTE ON velocity 0 to NOTE OFF semantics in logs/callback stream.
        byte[] normalized = normalizeNoteOff(data);
        final byte[] msg = normalized;
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                MidiEventListener listener = midiEventListener;
                if (listener != null) {
                    listener.onMidiPacket(msg, timestamp);
                } else {
                    Log.w(TAG_IN, "rx packet dropped: no MidiEventListener attached");
                }
            }
        });
    }

    private byte[] normalizeNoteOff(byte[] data) {
        if (data.length < 3) {
            return data;
        }
        byte[] out = Arrays.copyOf(data, data.length);
        int status = out[0] & 255;
        int hi = status & 240;
        if (hi == 144 && (out[2] & 255) == 0) {
            out[0] = (byte) ((status & 15) | 128);
        }
        return out;
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
}
