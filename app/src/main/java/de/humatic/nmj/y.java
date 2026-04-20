package de.humatic.nmj;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build;
import de.humatic.nmj.x;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
final class y extends w {
    private static j a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static b f355a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static Vector<UsbDevice> f356a;
    private static UsbDevice b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private static UsbManager f357b;
    private static int f;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private int f358a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private BroadcastReceiver f359a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private UsbDevice f360a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private final UsbManager f361a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private u f362a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private int f363b;
    private int e;

    static {
        new Vector();
        f356a = new Vector<>();
    }

    y(int i, n nVar) throws Exception {
        super(nVar, i);
        if (Build.VERSION.SDK_INT < 12) {
            throw new IllegalArgumentException("USB hostmode requires Android 3.1 or greater");
        }
        this.f358a = NMJConfig.getPort(this.c);
        this.f363b = (NMJConfig.getLocalPort(this.c) >> 16) & 65535;
        this.e = NMJConfig.getLocalPort(this.c) & 65535;
        NMJConfig.getIP(this.c);
        this.f361a = (UsbManager) NMJConfig.m11a().getSystemService("usb");
        this.f359a = new a(new x.b() { // from class: de.humatic.nmj.y.1
            @Override // de.humatic.nmj.x.b
            public final void a() {
                NMJConfig.a(y.this.c, NMJConfig.E_USB, "USB permission denied");
                y.this.mo96a();
            }
        });
        for (UsbDevice usbDevice : this.f361a.getDeviceList().values()) {
            if (usbDevice.getVendorId() != 1478) {
                p.logln(3, "USB device " + usbDevice + " vendorID: " + usbDevice.getVendorId() + ", Product ID: " + usbDevice.getProductId());
                if (usbDevice.getVendorId() == (this.f363b & 65535) && usbDevice.getProductId() == (this.e & 65535)) {
                    p.logln(3, "USBSerial - using device " + usbDevice.toString() + "\nPermission: " + this.f361a.hasPermission(usbDevice));
                    this.f360a = usbDevice;
                    if (!this.f361a.hasPermission(usbDevice)) {
                        PendingIntent broadcast = PendingIntent.getBroadcast(NMJConfig.m11a(), 0, new Intent("de.humatic.nmj.USB"), 0);
                        NMJConfig.m11a().registerReceiver(this.f359a, new IntentFilter("de.humatic.nmj.USB"));
                        this.f361a.requestPermission(usbDevice, broadcast);
                    } else {
                        if (this.f362a == null) {
                            try {
                                this.f362a = u.a(i, this.f360a);
                                new Thread(this.f362a).start();
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        f();
                        return;
                    }
                }
            }
        }
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final void mo96a() {
        if (this.f362a != null) {
            p.logln(4, "USBSerial closing device " + this.f358a);
            this.f362a.a();
            this.f362a = null;
        }
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final void mo191a(byte[] bArr) throws IOException {
        if (this.f360a == null) {
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
                    u uVar = this.f362a;
                    int i3 = this.f358a;
                    uVar.a(this.b.get(i2));
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        } else {
            u uVar2 = this.f362a;
            int i4 = this.f358a;
            uVar2.a(bArr);
        }
    }

    final void a(byte[] bArr, int i, int i2) {
        this.a.a(bArr, i, i2);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final int m197a() {
        return this.f358a;
    }

    protected static void c() {
        try {
            NMJConfig.m11a().unregisterReceiver(f355a);
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        if (this.f362a != null) {
            this.f362a.a(this);
        }
    }

    class a extends BroadcastReceiver {
        private final x.b a;

        public a(x.b bVar) {
            this.a = bVar;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            NMJConfig.m11a().unregisterReceiver(this);
            if (intent.getAction().equals("de.humatic.nmj.USB")) {
                if (!intent.getBooleanExtra("permission", false)) {
                    x.b bVar = this.a;
                    intent.getParcelableExtra("device");
                    bVar.a();
                    return;
                }
                p.logln(4, "Permission granted");
                UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                if (usbDevice != null) {
                    if (usbDevice.getVendorId() == y.this.f363b && usbDevice.getProductId() == y.this.e) {
                        y.this.f360a = usbDevice;
                        if (y.this.f362a == null) {
                            try {
                                y.this.f362a = u.a(y.this.c, y.this.f360a);
                                new Thread(y.this.f362a).start();
                            } catch (IOException e) {
                                NMJConfig.a(y.this.c, NMJConfig.E_USB, "USB - failed to initialize device: " + e.toString());
                                e.printStackTrace();
                                return;
                            }
                        }
                        y.this.f();
                        return;
                    }
                    return;
                }
                NMJConfig.a(y.this.c, NMJConfig.E_USB, "USB - Device not available");
            }
        }
    }

    protected static void a(j jVar) {
        try {
            jVar.a(7, 0);
            b = null;
            f356a.removeAllElements();
            a = jVar;
            UsbManager usbManager = (UsbManager) NMJConfig.m11a().getSystemService("usb");
            f357b = usbManager;
            HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            if (deviceList.isEmpty()) {
                p.logln(3, "USBSerial - No USB devices attached");
                jVar.a(7, 1);
                return;
            }
            deviceList.size();
            f = 0;
            f355a = new b();
            NMJConfig.m11a().registerReceiver(f355a, new IntentFilter("de.humatic.nmj.USB"));
            if (deviceList.values().size() > 1) {
                p.logln(3, "USBSerial - num devs " + deviceList.values().size());
            }
            for (UsbDevice usbDevice : deviceList.values()) {
                if ((usbDevice.getDeviceClass() == 255 && usbDevice.getDeviceSubclass() == 255) || ((usbDevice.getDeviceClass() == 2 && usbDevice.getDeviceSubclass() == 0) || (usbDevice.getDeviceClass() == 0 && usbDevice.getDeviceSubclass() == 0))) {
                    if (!f356a.contains(usbDevice) && usbDevice.getVendorId() != 1478) {
                        f356a.add(usbDevice);
                    }
                }
            }
            d(f356a.get(0));
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(UsbDevice usbDevice) {
        try {
            if (!f357b.hasPermission(usbDevice)) {
                PendingIntent broadcast = PendingIntent.getBroadcast(NMJConfig.m11a(), 0, new Intent("de.humatic.nmj.USB"), 0);
                p.logln(3, "USBSerial - requesting permission for " + usbDevice.getDeviceName());
                f357b.requestPermission(usbDevice, broadcast);
            } else {
                p.logln(3, "USBSerial - already got permission for " + usbDevice.getDeviceName());
                e(usbDevice);
                int i = f + 1;
                f = i;
                if (i < f356a.size()) {
                    d(f356a.get(f));
                } else {
                    f356a.removeAllElements();
                    a.a(7, 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static UsbDevice a() {
        return b;
    }

    protected static void e() {
        b = null;
    }

    static class b extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            y.f++;
            if (intent.getAction().equals("de.humatic.nmj.USB")) {
                if (!intent.getBooleanExtra("permission", false)) {
                    y.b = null;
                } else {
                    y.e((UsbDevice) intent.getParcelableExtra("device"));
                }
            }
            if (y.f < y.f356a.size()) {
                y.d((UsbDevice) y.f356a.get(y.f));
                return;
            }
            NMJConfig.m11a().unregisterReceiver(this);
            y.f356a.removeAllElements();
            y.a.a(7, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(UsbDevice usbDevice) {
        String str;
        try {
            p.logln(3, "USBSerial - examining device " + usbDevice);
            if ((usbDevice.getDeviceClass() == 0 && usbDevice.getDeviceSubclass() == 0) || ((usbDevice.getDeviceClass() == 2 && usbDevice.getDeviceSubclass() == 0) || (usbDevice.getDeviceClass() == 255 && usbDevice.getDeviceSubclass() == 255))) {
                int i = usbDevice.getDeviceClass() == 2 ? 1 : 0;
                if (usbDevice.getInterfaceCount() == 0) {
                    p.logln(3, "No interfaces on " + usbDevice);
                }
                UsbDeviceConnection usbDeviceConnectionOpenDevice = ((UsbManager) NMJConfig.m11a().getSystemService("usb")).openDevice(usbDevice);
                if (!usbDeviceConnectionOpenDevice.claimInterface(usbDevice.getInterface(i), true)) {
                    p.logln(1, "USBSerial - failed to claim interface for " + usbDevice);
                    return;
                }
                UsbInterface usbInterface = usbDevice.getInterface(i);
                p.logln(2, "interface  " + usbInterface);
                switch (usbInterface.getInterfaceClass()) {
                    case 2:
                    case 10:
                    case 255:
                        p.logln(2, "num endpoints " + usbInterface.getEndpointCount());
                        for (int i2 = 0; i2 < usbInterface.getEndpointCount(); i2++) {
                            if (usbInterface.getEndpoint(i2).getType() == 2) {
                                if (usbInterface.getEndpoint(i2).getDirection() == 128) {
                                    p.logln(2, "input " + usbInterface.getEndpoint(i2));
                                } else {
                                    p.logln(2, "output " + usbInterface.getEndpoint(i2));
                                }
                            }
                        }
                        b = usbDevice;
                        int vendorId = (usbDevice.getVendorId() << 16) | usbDevice.getProductId();
                        g gVar = new g(null, -1);
                        gVar.c = usbDevice.getDeviceName();
                        gVar.d = String.valueOf(vendorId);
                        int vendorId2 = usbDevice.getVendorId();
                        String str2 = " (" + p.a(vendorId2) + ":" + p.a(usbDevice.getProductId()) + ")";
                        switch (vendorId2) {
                            case 1027:
                                str = "FTDI" + str2;
                                break;
                            case 1240:
                                str = "Microchip Technology" + str2;
                                break;
                            case 1659:
                                str = "Prolific USB Serial" + str2;
                                break;
                            case 9025:
                                str = "Arduino" + str2;
                                break;
                            default:
                                str = "USB Serial " + str2;
                                break;
                        }
                        gVar.a(str);
                        a.a(7, gVar, -1);
                        usbDeviceConnectionOpenDevice.close();
                        break;
                    default:
                        p.logln(3, "USBSerial - interface class mismatch: " + usbInterface.getInterfaceClass());
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
