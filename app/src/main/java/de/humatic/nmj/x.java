package de.humatic.nmj;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
final class x extends w {
    private static j a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static d f333a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private static UsbManager f335b;
    private static int f;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private int f336a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private BroadcastReceiver f337a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private UsbDevice f338a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private UsbDeviceConnection f339a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    UsbEndpoint f340a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private final UsbManager f341a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[] f342a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private int f343b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    UsbEndpoint f344b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private byte[] f345b;
    private int e;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static Vector<a> f334a = new Vector<>();
    private static UsbDevice b = null;
    private static Vector<UsbDevice> c = new Vector<>();

    interface b {
        void a();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    x(int i, n nVar) throws Exception {
        super(nVar, i);
        byte b2 = 0;
        this.f340a = null;
        this.f344b = null;
        this.f342a = new byte[3];
        this.f345b = new byte[4];
        if (Build.VERSION.SDK_INT < 12) {
            throw new IllegalArgumentException("USB hostmode requires Android 3.1 or greater");
        }
        this.f336a = NMJConfig.getPort(this.c);
        this.f343b = (NMJConfig.getLocalPort(this.c) >> 16) & 65535;
        this.e = NMJConfig.getLocalPort(this.c) & 65535;
        NMJConfig.getIP(this.c);
        this.f341a = (UsbManager) NMJConfig.m11a().getSystemService("usb");
        this.f337a = new c(new b() { // from class: de.humatic.nmj.x.1
            @Override // de.humatic.nmj.x.b
            public final void a() {
                NMJConfig.a(x.this.c, NMJConfig.E_USB, "USB permission denied");
                x.this.mo96a();
            }
        });
        for (UsbDevice usbDevice : this.f341a.getDeviceList().values()) {
            p.logln(5, "USB device " + usbDevice + " vendorID: " + p.a(usbDevice.getVendorId()) + ", Product ID: " + p.a(usbDevice.getProductId()));
            if (usbDevice.getVendorId() == (this.f343b & 65535) && usbDevice.getProductId() == (this.e & 65535)) {
                p.logln(3, "USBMidi " + this.c + " - using device " + usbDevice.toString() + "\nPermission: " + this.f341a.hasPermission(usbDevice));
                this.f338a = usbDevice;
                if (!this.f341a.hasPermission(usbDevice)) {
                    PendingIntent broadcast = PendingIntent.getBroadcast(NMJConfig.m11a(), 0, new Intent("de.humatic.nmj.USB"), 0);
                    NMJConfig.m11a().registerReceiver(this.f337a, new IntentFilter("de.humatic.nmj.USB"));
                    this.f341a.requestPermission(usbDevice, broadcast);
                } else {
                    UsbDevice usbDevice2 = this.f338a;
                    if (m184a() == null) {
                        a aVar = new a(this, this.f338a, b2);
                        f334a.add(aVar);
                        new Thread(aVar).start();
                    }
                    f();
                    return;
                }
            }
        }
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected final void mo191a(byte[] bArr) throws IOException {
        if (this.f338a == null) {
            p.a("USB " + this.c + " - no device");
            return;
        }
        if (bArr.length > 3) {
            this.b.removeAllElements();
            p.a(bArr, this.b);
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < this.b.size()) {
                    UsbDevice usbDevice = this.f338a;
                    m184a().a(this.f336a, this.b.get(i2));
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        } else {
            UsbDevice usbDevice2 = this.f338a;
            m184a().a(this.f336a, bArr);
        }
    }

    static /* synthetic */ void a(x xVar, byte[] bArr, int i) {
        xVar.a.a(bArr, i);
    }

    static /* synthetic */ a a(x xVar, UsbDevice usbDevice) {
        return xVar.m184a();
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private a m184a() {
        for (a aVar : f334a) {
            if (aVar.f346a.equals(this.f338a)) {
                return aVar;
            }
        }
        return null;
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final void mo96a() {
        if (f334a.size() != 0 && this.f338a != null) {
            p.logln(4, "USBMidi unregistering client " + this.f336a);
            UsbDevice usbDevice = this.f338a;
            a.a(m184a(), this);
        }
    }

    protected static void c() {
        try {
            NMJConfig.m11a().unregisterReceiver(f333a);
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        if (this.f338a != null) {
            UsbDevice usbDevice = this.f338a;
            a.b(m184a(), this);
        }
    }

    class c extends BroadcastReceiver {
        private final b a;

        public c(b bVar) {
            this.a = bVar;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            byte b = 0;
            NMJConfig.m11a().unregisterReceiver(this);
            if (intent.getAction().equals("de.humatic.nmj.USB")) {
                if (!intent.getBooleanExtra("permission", false)) {
                    b bVar = this.a;
                    intent.getParcelableExtra("device");
                    bVar.a();
                    return;
                }
                p.logln(4, "Permission granted");
                UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                if (usbDevice != null) {
                    if (usbDevice.getVendorId() == (x.this.f343b & 65535) && usbDevice.getProductId() == (x.this.e & 65535)) {
                        x.this.f338a = usbDevice;
                        if (x.a(x.this, x.this.f338a) == null) {
                            a aVar = new a(x.this, x.this.f338a, b);
                            x.f334a.add(aVar);
                            new Thread(aVar).start();
                        }
                        x.this.f();
                        return;
                    }
                    return;
                }
                NMJConfig.a(x.this.c, NMJConfig.E_USB, "USB - Device not available");
            }
        }
    }

    protected static void a(j jVar) {
        try {
            jVar.a(5, 0);
            b = null;
            c.removeAllElements();
            a = jVar;
            UsbManager usbManager = (UsbManager) NMJConfig.m11a().getSystemService("usb");
            f335b = usbManager;
            HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            if (deviceList.isEmpty()) {
                p.logln(3, "USBMidi - No USB devices attached");
                jVar.a(5, 1);
                return;
            }
            deviceList.size();
            f = 0;
            f333a = new d();
            NMJConfig.m11a().registerReceiver(f333a, new IntentFilter("de.humatic.nmj.USB"));
            if (deviceList.values().size() > 1) {
                p.logln(3, "USBHost - num devs " + deviceList.values().size());
            }
            for (UsbDevice usbDevice : deviceList.values()) {
                if ((usbDevice.getDeviceClass() == 1 && usbDevice.getDeviceSubclass() == 3) || (usbDevice.getDeviceClass() == 0 && usbDevice.getDeviceSubclass() == 0)) {
                    if (!c.contains(usbDevice)) {
                        if (usbDevice.getVendorId() == 1478) {
                            p.logln(6, "USB_HOST - skipping 0x5c6 / 0x909e");
                        } else {
                            c.add(usbDevice);
                        }
                    }
                }
            }
            if (c.size() == 0) {
                a.a(5, 1);
            } else {
                d(c.get(0));
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(UsbDevice usbDevice) {
        try {
            if (!f335b.hasPermission(usbDevice)) {
                PendingIntent broadcast = PendingIntent.getBroadcast(NMJConfig.m11a(), 0, new Intent("de.humatic.nmj.USB"), 0);
                p.logln(3, "USBMidi - requesting permission for " + usbDevice.getDeviceName());
                f335b.requestPermission(usbDevice, broadcast);
            } else {
                p.logln(3, "USBMidi - already got permission for " + usbDevice.getDeviceName());
                e(usbDevice);
                int i = f + 1;
                f = i;
                if (i < c.size()) {
                    d(c.get(f));
                } else {
                    c.removeAllElements();
                    c();
                    a.a(5, 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected static UsbDevice m179a() {
        return b;
    }

    protected static void e() {
        b = null;
    }

    static class d extends BroadcastReceiver {
        private boolean a;

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            x.f++;
            if (intent.getAction().equals("de.humatic.nmj.USB")) {
                if (!intent.getBooleanExtra("permission", false)) {
                    x.b = null;
                    this.a = true;
                } else {
                    x.e((UsbDevice) intent.getParcelableExtra("device"));
                }
            }
            if (x.f < x.c.size()) {
                x.d((UsbDevice) x.c.get(x.f));
                return;
            }
            NMJConfig.m11a().unregisterReceiver(this);
            x.c.removeAllElements();
            x.a.a(5, this.a ? 2 : 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(UsbDevice usbDevice) {
        UsbInterface usbInterface;
        int endpointCount;
        UsbInterface usbInterface2;
        int i;
        int i2;
        String strA;
        int[] iArr;
        int i3;
        int i4;
        try {
            p.logln(3, "USBMidi - examining device " + p.a(usbDevice.getVendorId()) + " " + p.a(usbDevice.getProductId()) + " " + usbDevice);
            if (usbDevice.getDeviceClass() != 1 || usbDevice.getDeviceSubclass() != 3) {
                if (usbDevice.getDeviceClass() == 0 && usbDevice.getDeviceSubclass() == 0) {
                    p.logln(3, "Nr. of interfaces " + usbDevice.getInterfaceCount());
                    if (usbDevice.getInterfaceCount() < 2) {
                        if (usbDevice.getInterfaceCount() == 0) {
                            p.logln(3, "No interfaces on " + usbDevice);
                            return;
                        } else {
                            p.logln(3, "Not a MIDI interface, only 1 interface on " + usbDevice);
                            return;
                        }
                    }
                    UsbDeviceConnection usbDeviceConnectionOpenDevice = ((UsbManager) NMJConfig.m11a().getSystemService("usb")).openDevice(usbDevice);
                    p.logln(3, "DeviceConnection established " + usbDeviceConnectionOpenDevice);
                    if (!usbDeviceConnectionOpenDevice.claimInterface(usbDevice.getInterface(1), true)) {
                        if (usbDevice.getInterfaceCount() <= 2) {
                            p.logln(1, "USBMidi - failed to claim interface 1 for " + usbDevice);
                            return;
                        } else {
                            usbInterface = null;
                            endpointCount = 0;
                        }
                    } else {
                        usbInterface = usbDevice.getInterface(1);
                        endpointCount = usbInterface.getEndpointCount();
                    }
                    if (endpointCount == 0) {
                        usbDeviceConnectionOpenDevice.releaseInterface(usbInterface);
                        if (usbDevice.getInterfaceCount() <= 2) {
                            i4 = 1;
                        } else if (!usbDeviceConnectionOpenDevice.claimInterface(usbDevice.getInterface(2), true)) {
                            p.logln(1, "USBMidi - failed to claim interface 2 for " + usbDevice);
                            return;
                        } else {
                            usbInterface = usbDevice.getInterface(2);
                            endpointCount = usbInterface.getEndpointCount();
                            i4 = 2;
                        }
                        if (endpointCount == 0) {
                            p.logln(1, "USB - No endpoints");
                            return;
                        } else {
                            usbInterface2 = usbInterface;
                            i = endpointCount;
                            i2 = i4;
                        }
                    } else {
                        usbInterface2 = usbInterface;
                        i = endpointCount;
                        i2 = 1;
                    }
                    p.logln(3, "Interface " + i2 + " claimed, nr endpoints: " + i);
                    if (usbInterface2.getInterfaceClass() != 1 || usbInterface2.getInterfaceSubclass() != 3) {
                        p.logln(1, "USBMidi - class / subclass mismatch " + usbInterface2.getInterfaceClass() + " " + usbInterface2.getInterfaceSubclass());
                        usbDeviceConnectionOpenDevice.close();
                        return;
                    }
                    p.logln(3, "Interface class: " + usbInterface2.getInterfaceClass() + " subclass: " + usbInterface2.getInterfaceSubclass());
                    try {
                        byte[] bArr = new byte[64];
                        int iControlTransfer = usbDeviceConnectionOpenDevice.controlTransfer(128, 6, 770, 0, bArr, 64, 500);
                        if (iControlTransfer > 0) {
                            p.a(5, "ctrl transfer ret.:", bArr);
                            byte[] bArr2 = new byte[iControlTransfer / 2];
                            for (int i5 = 0; i5 < bArr2.length; i5++) {
                                bArr2[i5] = bArr[i5 << 1];
                            }
                            strA = new String(bArr2).substring(1);
                        } else {
                            strA = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        strA = null;
                    }
                    if (Build.VERSION.SDK_INT >= 13) {
                        byte[] rawDescriptors = usbDeviceConnectionOpenDevice.getRawDescriptors();
                        p.a(5, "Raw descriptors:", rawDescriptors);
                        int iA = a(rawDescriptors);
                        int[] iArrM189a = m189a(rawDescriptors);
                        int iMax = Math.max(iArrM189a[0], iArrM189a[1]);
                        int[] iArr2 = new int[iMax];
                        if (iMax == 1) {
                            iArr2[0] = 0;
                            if (iArrM189a[0] > 0) {
                                iArr2[0] = iArr2[0] | 1;
                            }
                            if (iArrM189a[1] > 0) {
                                iArr2[0] = iArr2[0] | 2;
                            }
                        } else {
                            for (int i6 = 0; i6 < iMax; i6++) {
                                iArr2[i6] = 3;
                            }
                        }
                        p.logln(2, "nr. ports: " + iMax + ", max pkt size " + iA);
                        iArr = iArr2;
                        i3 = iMax;
                    } else {
                        int[] iArr3 = null;
                        int i7 = 1;
                        for (int i8 = 0; i8 < usbInterface2.getEndpointCount(); i8++) {
                            if (usbInterface2.getEndpoint(i8).getType() == 2) {
                                int maxPacketSize = usbInterface2.getEndpoint(i8).getMaxPacketSize();
                                if (maxPacketSize > 32) {
                                    i7 = maxPacketSize / 32;
                                }
                                iArr3 = new int[i7];
                                if (usbInterface2.getEndpoint(i8).getDirection() == 128) {
                                    p.logln(2, "input " + usbInterface2.getEndpoint(i8));
                                    for (int i9 = 0; i9 < i7; i9++) {
                                        iArr3[i9] = iArr3[i9] | 1;
                                    }
                                } else {
                                    p.logln(2, "output " + usbInterface2.getEndpoint(i8));
                                    for (int i10 = 0; i10 < i7; i10++) {
                                        iArr3[i10] = iArr3[i10] | 2;
                                    }
                                }
                            }
                        }
                        iArr = iArr3;
                        i3 = i7;
                    }
                    b = usbDevice;
                    int vendorId = (usbDevice.getVendorId() << 16) | usbDevice.getProductId();
                    if (i3 == 1) {
                        g gVar = new g(null, -1);
                        gVar.c = usbDevice.getDeviceName();
                        gVar.d = String.valueOf(vendorId);
                        if (strA == null) {
                            strA = a(usbDevice.getVendorId(), usbDevice.getProductId());
                        }
                        gVar.a(strA);
                        gVar.b = iArr[0] << 8;
                        a.a(5, gVar, -1);
                    } else {
                        String strA2 = strA == null ? a(usbDevice.getVendorId(), usbDevice.getProductId()) : strA;
                        for (int i11 = 0; i11 < i3; i11++) {
                            g gVar2 = new g(null, -1);
                            gVar2.c = usbDevice.getDeviceName();
                            gVar2.d = String.valueOf(vendorId);
                            gVar2.a(String.valueOf(strA2) + " Port " + (i11 + 1));
                            gVar2.b = (iArr[i11] << 8) | i11;
                            a.a(5, gVar2, -1);
                        }
                    }
                    usbDeviceConnectionOpenDevice.close();
                    return;
                }
                return;
            }
            b = usbDevice;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private static int[] m189a(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i3 < bArr.length) {
            int i4 = i3 + 1;
            int i5 = bArr[i3] & 255;
            int i6 = i4 + 1;
            int i7 = bArr[i4] & 255;
            if (i5 <= 2) {
                break;
            }
            if (i7 == 36) {
                int i8 = i6 + 1;
                int i9 = bArr[i6] & 255;
                if (i9 == 2 || i9 == 3) {
                    int i10 = bArr[i8] & 255;
                    if (i9 == 2) {
                        if (i10 == 1) {
                            i2++;
                        }
                    } else if (i9 == 3 && i10 == 1) {
                        i++;
                    }
                }
            }
            i3 = (i5 - 2) + i6;
        }
        return new int[]{i2, i};
    }

    private static int a(byte[] bArr) {
        int i = -1;
        int i2 = 0;
        int i3 = -1;
        while (i2 < bArr.length) {
            int i4 = i2 + 1;
            int i5 = bArr[i2] & 255;
            int i6 = i4 + 1;
            int i7 = bArr[i4] & 255;
            if (i5 <= 2) {
                break;
            }
            if (i7 == 1) {
                i = bArr[i6 + 5] & 255;
                p.logln(3, "   Ctrl mps " + i);
            } else if (i7 == 5) {
                i3 = ((bArr[i6 + 3] & 255) << 8) | (bArr[i6 + 2] & 255);
                p.logln(3, "   Endpoint mps " + i3);
            }
            i2 = (i5 - 2) + i6;
        }
        if (i3 >= 0 || i >= 0) {
            return i3 >= 0 ? i3 : i;
        }
        return 4;
    }

    private static String a(int i, int i2) {
        p.logln(2, "getKnownDeviceName " + p.a(i) + " " + p.a(i2));
        String str = " (" + p.a(i) + ":" + p.a(i2) + ")";
        switch (i) {
            case 1054:
                return i2 == 16128 ? "E-Mu Xboard 25 MIDI Controller" : i2 == 16130 ? "E-Mu 0202" : i2 == 16132 ? "E-Mu 0404" : i2 == 16135 ? "E-Mu Xmidi 1x1" : "Creative" + str;
            case 1177:
                return "Yamaha" + str;
            case 1240:
                return i2 == 64156 ? "Missing Link USB" : "Microchip Technology" + str;
            case 1410:
                return i2 == 0 ? "UA-100" : i2 == 2 ? "UM-4/MPU-64" : i2 == 5 ? "Edirol UM-2" : (i2 == 9 || i2 == 82) ? "Edirol UM-1SX" : i2 == 154 ? "Edirol UM-3EX" : i2 == 157 ? "Edirol UM-1" : "Roland" + str;
            case 1891:
                return i2 == 4176 ? "MIDISport 2x2 Anniv." : i2 == 279 ? "Trigger Finger" : i2 == 336 ? "MIDISport Uno" : i2 == 405 ? "Oxygen 8 v2" : i2 == 4129 ? "MidiSport 4x4 Anniv." : "M-Audio" + str;
            case 1999:
                return i2 == 26626 ? "Casio MIDI Keyboard" : "Casio" + str;
            case 2045:
                return i2 == 0 ? "FastLane MIDI Interface" : i2 == 1 ? "FastLane Quad MIDI Interface" : "MOTU" + str;
            case 2372:
                return i2 == 32 ? "KAOSS Pad KP3" : i2 == 35 ? "KAOSSILATOR PRO" : i2 == 269 ? "nanoKEY" : i2 == 270 ? "nanoPAD" : i2 == 271 ? "nanoKONTROL" : i2 == 279 ? "nanoKONTROL2" : i2 == 3843 ? "K-Series K61P" : "Korg" + str;
            case 2536:
                return i2 == 98 ? "Akai MPD16" : i2 == 13 ? "Akai EWI" : i2 == 113 ? "Akai MPK25" : i2 == 118 ? "Akai LPK25" : "Akai" + str;
            case 2623:
            case 5899:
                return i2 == 17 ? "Swissonic MIDI-USB 1x1" : "Swissonic" + str;
            case 2637:
                return i2 == 140 ? "TerraTec MIDI MASTER" : i2 == 142 ? "TerraTec MK-249C" : i2 == 163 ? "TerraTec MK-461C" : i2 == 245 ? "TerraTec UC-33e" : "Terratec" + str;
            case 2638:
                return "Steinberg" + str;
            case 2663:
                return i2 == 20497 ? "Fame HD 1000" : "Medeli Electronics" + str;
            case 2706:
                return i2 == 4096 ? "MIDI Mate" : i2 == 4240 ? "KeyControl49" : i2 == 4256 ? "KeyControl25" : "AudioTrak" + str;
            case 3649:
                return "Line6" + str;
            case 5015:
                return i2 == 188 ? "Behringer BCF2000" : i2 == 436 ? "Behringer UMA25S" : "Behringer" + str;
            case 5040:
                if (i2 == 10) {
                    return "Alesis Photon X25";
                }
                break;
            case 5042:
                break;
            case 5578:
                return (i2 == 101 || i2 == 1806) ? "Textech MIDI cable" : "Textech" + str;
            case 5636:
                return "Tascam" + str;
            case 6790:
                return ((double) i2) == 752.0d ? "CH345 MIDI adapter" : "QinHeng" + str;
            case 7447:
                return i2 == 1 ? "AXiS-49" : "C-Thru Music Ltd." + str;
            case 18258:
                return i2 == 17 ? "Midistart-2" : "Miditech" + str;
            case 28932:
                return "CME" + str;
            default:
                return "Class compl. MIDI" + str;
        }
        return "Alesis" + str;
    }

    class a extends Thread {
        private int a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private UsbDevice f346a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private Vector<x> f348a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private boolean f349a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private byte[] f350a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private int[] f351a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private boolean[] f352a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private byte[][] f353a;
        private int b;
        private int c;

        private a(UsbDevice usbDevice) {
            this.f348a = new Vector<>();
            this.f349a = true;
            this.a = 32;
            this.b = 1;
            this.c = 20;
            this.f346a = usbDevice;
        }

        /* synthetic */ a(x xVar, UsbDevice usbDevice, byte b) {
            this(usbDevice);
        }

        static /* synthetic */ void b(a aVar, x xVar) {
            if (aVar.f348a.contains(xVar)) {
                return;
            }
            aVar.f348a.add(xVar);
            p.logln(3, "USBIO - client added " + xVar + " " + xVar.f336a);
        }

        static /* synthetic */ void a(a aVar, x xVar) {
            if (aVar.f348a.contains(xVar)) {
                aVar.f348a.remove(xVar);
            }
            if (aVar.f348a.size() == 0) {
                try {
                    x.this.f332a = true;
                    x.this.f339a.close();
                    x.f334a.remove(x.a(x.this, x.this.f338a));
                    x.this.f338a = null;
                    p.logln(2, "USB IOThread closed");
                } catch (Exception e) {
                }
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            int endpointCount;
            try {
                x.this.f339a = x.this.f341a.openDevice(this.f346a);
                UsbInterface usbInterface = null;
                if (!x.this.f339a.claimInterface(this.f346a.getInterface(1), true)) {
                    if (this.f346a.getInterfaceCount() <= 2) {
                        x.this.mo96a();
                        NMJConfig.a(x.this.c, NMJConfig.E_USB, new StringBuilder("USB - Failed to claim interface 1").toString());
                        return;
                    }
                    endpointCount = 0;
                } else {
                    usbInterface = this.f346a.getInterface(1);
                    endpointCount = usbInterface.getEndpointCount();
                }
                if (endpointCount == 0) {
                    p.logln(1, "USB - No endpoints on interface 1");
                    try {
                        x.this.f339a.releaseInterface(usbInterface);
                    } catch (Exception e) {
                    }
                    if (this.f346a.getInterfaceCount() > 2) {
                        if (!x.this.f339a.claimInterface(this.f346a.getInterface(2), true)) {
                            x.this.mo96a();
                            NMJConfig.a(x.this.c, NMJConfig.E_USB, new StringBuilder("USB - Failed to claim interface 2").toString());
                            return;
                        } else {
                            usbInterface = this.f346a.getInterface(2);
                            endpointCount = usbInterface.getEndpointCount();
                        }
                    }
                    if (endpointCount == 0) {
                        x.this.mo96a();
                        NMJConfig.a(x.this.c, NMJConfig.E_USB, "USB - No endpoints");
                        return;
                    }
                }
                UsbInterface usbInterface2 = usbInterface;
                int i = endpointCount;
                p.logln(2, "USBIO - Interface aquired");
                p.logln(2, "class " + usbInterface2.getInterfaceClass() + " subclass " + usbInterface2.getInterfaceSubclass() + " protocol " + usbInterface2.getInterfaceProtocol());
                if (usbInterface2.getInterfaceClass() != 1 || usbInterface2.getInterfaceSubclass() != 3) {
                    x.this.mo96a();
                    NMJConfig.a(x.this.c, NMJConfig.E_USB, "USB - Not a class-compliant MIDI device");
                    return;
                }
                p.logln(2, "num endpoints " + i);
                for (int i2 = 0; i2 < usbInterface2.getEndpointCount(); i2++) {
                    if (usbInterface2.getEndpoint(i2).getType() == 2) {
                        if (usbInterface2.getEndpoint(i2).getDirection() == 128) {
                            x.this.f340a = usbInterface2.getEndpoint(i2);
                            this.a = usbInterface2.getEndpoint(i2).getMaxPacketSize();
                            if (this.a > 32) {
                                this.b = this.a / 32;
                            }
                        } else {
                            x.this.f344b = usbInterface2.getEndpoint(i2);
                        }
                    }
                }
                p.logln(2, "Input: " + x.this.f340a + "\nOutput: " + x.this.f344b);
                NMJConfig.a(x.this.c, 4, 32);
                try {
                    this.f350a = new byte[this.a];
                    this.f353a = new byte[this.b][];
                    this.f352a = new boolean[this.b];
                    this.f351a = new int[this.b];
                    while (!x.this.f332a) {
                        try {
                            int iBulkTransfer = x.this.f339a.bulkTransfer(x.this.f340a, this.f350a, this.a, 500);
                            if (iBulkTransfer >= 0) {
                                if (iBulkTransfer > 0 || (this.f350a[0] & 15) > 2) {
                                    if ((this.f350a[0] & 15) != 15 || iBulkTransfer > 4 || !this.f349a || (this.f350a[1] & 255) != 254) {
                                        int i3 = 0;
                                        while (true) {
                                            if (i3 < (iBulkTransfer == 0 ? 4 : iBulkTransfer)) {
                                                int i4 = this.f350a[i3] >> 4;
                                                if ((this.f350a[i3] & 15) > 7 && (this.f350a[i3] & 15) < 15) {
                                                    int iB = p.b(this.f350a, i3 + 1) + 1;
                                                    System.arraycopy(this.f350a, i3 + 1, x.this.f342a, 0, iB);
                                                    for (x xVar : this.f348a) {
                                                        if (xVar.f336a == i4) {
                                                            x.a(xVar, x.this.f342a, iB);
                                                        }
                                                    }
                                                } else {
                                                    int i5 = this.f350a[i3] >> 4;
                                                    switch (this.f350a[i3] & 15) {
                                                        case 3:
                                                            System.arraycopy(this.f350a, i3 + 1, x.this.f342a, 0, 3);
                                                            for (x xVar2 : this.f348a) {
                                                                if (xVar2.f336a == i5) {
                                                                    x.a(xVar2, x.this.f342a, 3);
                                                                }
                                                            }
                                                            break;
                                                        case 4:
                                                            if (!this.f352a[i5]) {
                                                                if (this.f353a[i5] == null) {
                                                                    this.f353a[i5] = new byte[4096];
                                                                }
                                                                this.f352a[i5] = true;
                                                                System.arraycopy(this.f350a, i3 + 1, this.f353a[i5], 0, 3);
                                                                this.f351a[i5] = 3;
                                                            } else {
                                                                if (this.f351a[i5] > this.f353a[i5].length - 4) {
                                                                    a(i5, -1);
                                                                }
                                                                if ((this.f350a[i3 + 1] & 255) == 240) {
                                                                    System.arraycopy(this.f350a, i3 + 2, this.f353a[i5], this.f351a[i5], 2);
                                                                    int[] iArr = this.f351a;
                                                                    iArr[i5] = iArr[i5] + 2;
                                                                } else {
                                                                    System.arraycopy(this.f350a, i3 + 1, this.f353a[i5], this.f351a[i5], 3);
                                                                    int[] iArr2 = this.f351a;
                                                                    iArr2[i5] = iArr2[i5] + 3;
                                                                }
                                                            }
                                                            break;
                                                        case 5:
                                                            if (this.f352a[i5] && (this.f350a[i3 + 1] & 255) == 247) {
                                                                byte[] bArr = this.f353a[i5];
                                                                int[] iArr3 = this.f351a;
                                                                int i6 = iArr3[i5];
                                                                iArr3[i5] = i6 + 1;
                                                                bArr[i6] = -9;
                                                                for (x xVar3 : this.f348a) {
                                                                    if (xVar3.f336a == i5) {
                                                                        x.a(xVar3, this.f353a[i5], this.f351a[i5]);
                                                                    }
                                                                }
                                                                this.f352a[i5] = false;
                                                            }
                                                            break;
                                                        case 6:
                                                            if (this.f351a[i5] > this.f353a[i5].length - 4) {
                                                                a(i5, 1);
                                                            }
                                                            if ((this.f350a[i3 + 1] & 255) == 240) {
                                                                byte[] bArr2 = this.f353a[i5];
                                                                int[] iArr4 = this.f351a;
                                                                int i7 = iArr4[i5];
                                                                iArr4[i5] = i7 + 1;
                                                                bArr2[i7] = -9;
                                                            } else {
                                                                byte[] bArr3 = this.f353a[i5];
                                                                int[] iArr5 = this.f351a;
                                                                int i8 = iArr5[i5];
                                                                iArr5[i5] = i8 + 1;
                                                                bArr3[i8] = this.f350a[i3 + 1];
                                                                byte[] bArr4 = this.f353a[i5];
                                                                int[] iArr6 = this.f351a;
                                                                int i9 = iArr6[i5];
                                                                iArr6[i5] = i9 + 1;
                                                                bArr4[i9] = -9;
                                                            }
                                                            for (x xVar4 : this.f348a) {
                                                                if (xVar4.f336a == i5) {
                                                                    x.a(xVar4, this.f353a[i5], this.f351a[i5]);
                                                                }
                                                            }
                                                            this.f352a[i5] = false;
                                                            break;
                                                        case 7:
                                                            if (this.f351a[i5] > this.f353a[i5].length - 4) {
                                                                a(i5, 2);
                                                            }
                                                            if ((this.f350a[i3 + 1] & 255) == 240) {
                                                                byte[] bArr5 = this.f353a[i5];
                                                                int[] iArr7 = this.f351a;
                                                                int i10 = iArr7[i5];
                                                                iArr7[i5] = i10 + 1;
                                                                bArr5[i10] = this.f350a[i3 + 2];
                                                                byte[] bArr6 = this.f353a[i5];
                                                                int[] iArr8 = this.f351a;
                                                                int i11 = iArr8[i5];
                                                                iArr8[i5] = i11 + 1;
                                                                bArr6[i11] = -9;
                                                            } else {
                                                                byte[] bArr7 = this.f353a[i5];
                                                                int[] iArr9 = this.f351a;
                                                                int i12 = iArr9[i5];
                                                                iArr9[i5] = i12 + 1;
                                                                bArr7[i12] = this.f350a[i3 + 1];
                                                                byte[] bArr8 = this.f353a[i5];
                                                                int[] iArr10 = this.f351a;
                                                                int i13 = iArr10[i5];
                                                                iArr10[i5] = i13 + 1;
                                                                bArr8[i13] = this.f350a[i3 + 2];
                                                                byte[] bArr9 = this.f353a[i5];
                                                                int[] iArr11 = this.f351a;
                                                                int i14 = iArr11[i5];
                                                                iArr11[i5] = i14 + 1;
                                                                bArr9[i14] = -9;
                                                            }
                                                            for (x xVar5 : this.f348a) {
                                                                if (xVar5.f336a == i5) {
                                                                    x.a(xVar5, this.f353a[i5], this.f351a[i5]);
                                                                }
                                                            }
                                                            this.f352a[i5] = false;
                                                            break;
                                                        case 15:
                                                            if (i5 >= 0) {
                                                                if (this.f352a[i5] && (this.f350a[i3 + 1] & 240) < 128) {
                                                                    byte[] bArr10 = this.f353a[i5];
                                                                    int[] iArr12 = this.f351a;
                                                                    int i15 = iArr12[i5];
                                                                    iArr12[i5] = i15 + 1;
                                                                    bArr10[i15] = this.f350a[i3 + 1];
                                                                } else if (!this.f349a || (this.f350a[i3 + 1] & 255) != 254) {
                                                                    x.this.f342a[0] = this.f350a[i3 + 1];
                                                                    for (x xVar6 : this.f348a) {
                                                                        if (xVar6.f336a == i5) {
                                                                            x.a(xVar6, x.this.f342a, 1);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            break;
                                                    }
                                                }
                                                i3 += 4;
                                            }
                                        }
                                    }
                                }
                                this.f350a[0] = 0;
                            }
                        } catch (Exception e2) {
                            if (e2.toString().indexOf("closed") < 0) {
                                e2.printStackTrace();
                            }
                            if (!(e2 instanceof IndexOutOfBoundsException)) {
                                return;
                            }
                        }
                    }
                } catch (Exception e3) {
                }
            } catch (Exception e4) {
                if (!x.this.f332a) {
                    NMJConfig.a(x.this.c, NMJConfig.E_USB, e4.toString());
                }
            }
        }

        private void a(int i, int i2) {
            byte[] bArr = new byte[this.f353a[i].length];
            System.arraycopy(this.f353a[i], 0, bArr, 0, bArr.length);
            this.f353a[i] = new byte[i2 < 0 ? bArr.length << 1 : bArr.length + i2];
            System.arraycopy(bArr, 0, this.f353a[i], 0, bArr.length);
        }

        protected final void a(int i, byte[] bArr) throws IOException {
            if (i > this.b - 1) {
                p.logln(3, "USB_HOST " + x.this.c + " invalid cable ID " + i);
                i = 0;
            }
            if ((bArr[0] & 240) < 240) {
                x.this.f345b[0] = (byte) (((bArr[0] & 240) >> 4) | (i << 4));
                int iB = p.b(bArr, 0) + 1;
                System.arraycopy(bArr, 0, x.this.f345b, 1, iB);
                if (iB < 3) {
                    for (int i2 = iB + 1; i2 < 4; i2++) {
                        x.this.f345b[i2] = 0;
                    }
                }
                int iBulkTransfer = x.this.f339a.bulkTransfer(x.this.f344b, x.this.f345b, 4, this.c);
                if (iBulkTransfer < 0) {
                    p.logln(3, "USB_HOST " + x.this.c + " bulkTransfer returned " + iBulkTransfer);
                }
                return;
            }
            switch (bArr[0] & 240) {
                case 240:
                    int i3 = 0;
                    while (i3 + 3 < bArr.length) {
                        x.this.f345b[0] = (byte) ((i << 4) | 4);
                        System.arraycopy(bArr, i3, x.this.f345b, 1, 3);
                        x.this.f339a.bulkTransfer(x.this.f344b, x.this.f345b, 4, this.c);
                        i3 += 3;
                    }
                    switch (bArr.length - i3) {
                        case 1:
                            x.this.f345b[0] = (byte) ((i << 4) | 5);
                            x.this.f345b[1] = -9;
                            x.this.f345b[2] = 0;
                            x.this.f345b[3] = 0;
                            x.this.f339a.bulkTransfer(x.this.f344b, x.this.f345b, 4, this.c);
                            break;
                        case 2:
                            x.this.f345b[0] = (byte) ((i << 4) | 6);
                            x.this.f345b[1] = bArr[i3];
                            x.this.f345b[2] = -9;
                            x.this.f345b[3] = 0;
                            x.this.f339a.bulkTransfer(x.this.f344b, x.this.f345b, 4, this.c);
                            break;
                        case 3:
                            x.this.f345b[0] = (byte) ((i << 4) | 7);
                            x.this.f345b[1] = bArr[i3];
                            x.this.f345b[2] = bArr[i3 + 1];
                            x.this.f345b[3] = -9;
                            x.this.f339a.bulkTransfer(x.this.f344b, x.this.f345b, 4, this.c);
                            break;
                    }
                    break;
                case 241:
                case 243:
                    x.this.f345b[0] = (byte) ((i << 4) | 2);
                    System.arraycopy(bArr, 0, x.this.f345b, 1, 2);
                    x.this.f339a.bulkTransfer(x.this.f344b, x.this.f345b, 4, this.c);
                    break;
                case 242:
                    x.this.f345b[0] = (byte) ((i << 4) | 3);
                    System.arraycopy(bArr, 0, x.this.f345b, 1, 3);
                    x.this.f339a.bulkTransfer(x.this.f344b, x.this.f345b, 4, this.c);
                    break;
                case 244:
                case 245:
                case 246:
                    x.this.f345b[0] = (byte) ((i << 4) | 5);
                    x.this.f345b[1] = bArr[0];
                    x.this.f339a.bulkTransfer(x.this.f344b, x.this.f345b, 4, this.c);
                    break;
                case 248:
                case 250:
                case 251:
                case 252:
                    x.this.f345b[0] = (byte) ((i << 4) | 15);
                    x.this.f345b[1] = bArr[0];
                    x.this.f339a.bulkTransfer(x.this.f344b, x.this.f345b, 4, this.c);
                    break;
            }
        }
    }
}
