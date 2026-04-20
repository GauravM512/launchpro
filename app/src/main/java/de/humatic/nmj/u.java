package de.humatic.nmj;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class u extends Thread {
    protected int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    protected UsbDevice f316a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    protected UsbDeviceConnection f317a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    protected y f319a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    protected boolean f320a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    protected byte[] f321a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    protected boolean f323b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    protected byte[] f324b;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private boolean f325c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private byte[] f326c;
    protected int d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private byte[] f327d;
    private int e;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private byte[] f328e = {126, 8, 1, 0, 1, -25};
    protected int b = 32;
    protected int c = 1;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private UsbEndpoint f318a = null;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private UsbEndpoint f322b = null;
    private int f = 20;

    protected u(int i, UsbDevice usbDevice) {
        this.a = i;
        this.d = NMJConfig.getPort(i);
        if (this.d > 3) {
            this.d = 0;
        }
        this.f316a = usbDevice;
        this.f317a = ((UsbManager) NMJConfig.m11a().getSystemService("usb")).openDevice(usbDevice);
        b();
    }

    static u a(int i, UsbDevice usbDevice) throws IOException {
        switch (usbDevice.getVendorId()) {
            case 1027:
                return new k(i, usbDevice);
            case 1240:
                return new m(i, usbDevice);
            case 1659:
                return new r(i, usbDevice);
            case 9025:
                return new d(i, usbDevice);
            default:
                return new u(i, usbDevice);
        }
    }

    final void a() {
        this.f325c = true;
        this.f317a.close();
        p.logln(2, "Serial IOThread closed");
    }

    final void a(y yVar) {
        this.f319a = yVar;
        p.logln(3, "SerialIO - client added " + yVar + " " + yVar.m197a());
    }

    protected final byte[] a(int i, int i2, int i3, int i4, int i5) throws IOException {
        byte[] bArr = new byte[1];
        int iControlTransfer = this.f317a.controlTransfer(192, 1, i3, 0, bArr, 1, 500);
        if (iControlTransfer != 1) {
            throw new IOException("input control transfer failed: " + iControlTransfer);
        }
        return bArr;
    }

    protected final void a(int i, int i2, int i3, int i4, byte[] bArr) throws IOException {
        int iControlTransfer = this.f317a.controlTransfer(64, i2, i3, i4, null, 0, 0);
        if (iControlTransfer != 0) {
            throw new IOException("output control transfer failed: " + iControlTransfer);
        }
    }

    private void b() {
        try {
            int i = this.f316a.getDeviceClass() != 2 ? 0 : 1;
            if (!this.f317a.claimInterface(this.f316a.getInterface(i), true)) {
                p.a("USBSerial - failed to claim interface");
                return;
            }
            UsbInterface usbInterface = this.f316a.getInterface(i);
            p.logln(2, "USBSerial - Interface aquired");
            p.logln(2, "class " + usbInterface.getInterfaceClass() + " subclass " + usbInterface.getInterfaceSubclass() + " protocol " + usbInterface.getInterfaceProtocol());
            for (int i2 = 0; i2 < this.f316a.getInterfaceCount(); i2++) {
                usbInterface = this.f316a.getInterface(i2);
                p.logln(2, "SerialDevice - interface class: " + usbInterface.getInterfaceClass() + ", interface subclass: " + usbInterface.getInterfaceSubclass());
                if (usbInterface.getInterfaceClass() == 255) {
                    if (usbInterface.getInterfaceSubclass() != 255 && usbInterface.getInterfaceSubclass() != 0) {
                        this.f317a.close();
                        return;
                    }
                } else if (usbInterface.getInterfaceClass() != 10) {
                    if (usbInterface.getInterfaceSubclass() != 0) {
                        this.f317a.close();
                        return;
                    }
                } else if (usbInterface.getInterfaceClass() == 2) {
                    if (usbInterface.getInterfaceSubclass() != 2) {
                        this.f317a.close();
                        return;
                    }
                } else if (usbInterface.getInterfaceClass() == 10) {
                    if (usbInterface.getInterfaceSubclass() != 0) {
                        this.f317a.close();
                        return;
                    }
                } else {
                    this.f317a.close();
                    return;
                }
                for (int i3 = 0; i3 < usbInterface.getEndpointCount(); i3++) {
                    if (usbInterface.getEndpoint(i3).getType() == 2) {
                        this.b = usbInterface.getEndpoint(i3).getMaxPacketSize();
                        if (usbInterface.getEndpoint(i3).getDirection() == 128) {
                            this.f318a = usbInterface.getEndpoint(i3);
                        } else {
                            this.f322b = usbInterface.getEndpoint(i3);
                        }
                    }
                }
                if (this.f318a != null || this.f322b != null) {
                    break;
                }
            }
            this.f327d = new byte[this.b];
            p.logln(2, "num endpoints " + usbInterface.getEndpointCount());
            p.logln(2, "Input: " + this.f318a + "\nOutput: " + this.f322b);
            NMJConfig.a(this.a, 4, 32);
        } catch (Exception e) {
            if (!this.f325c) {
                NMJConfig.a(this.a, NMJConfig.E_USB, e.toString());
            }
        }
    }

    protected void a(boolean z) throws IOException {
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected byte[] mo178a(byte[] bArr) {
        return bArr;
    }

    protected byte[] a(byte[] bArr, int i, int i2) throws IOException {
        this.f319a.a(bArr, i, i2);
        return bArr;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            a(true);
            this.f326c = new byte[this.b];
            while (!this.f325c) {
                try {
                    if (this.d == 2) {
                        int i = this.e;
                        this.e = i - 1;
                        if (i == 0) {
                            a(this.f328e, this.f328e.length);
                        }
                    }
                    int iBulkTransfer = this.f317a.bulkTransfer(this.f318a, this.f326c, this.b, 500);
                    if (iBulkTransfer > 0) {
                        if (this.f323b) {
                            if (iBulkTransfer > 2) {
                                a(this.f326c, 2, iBulkTransfer);
                            }
                        } else {
                            a(this.f326c, 0, iBulkTransfer);
                        }
                    }
                } catch (Exception e) {
                    if (e.toString().indexOf("closed") < 0) {
                        e.printStackTrace();
                    }
                    if (!(e instanceof IndexOutOfBoundsException)) {
                        return;
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    protected final void a(byte[] bArr) throws IOException {
        byte[] bArrMo178a = mo178a(bArr);
        if (bArrMo178a != null) {
            a(bArrMo178a, bArrMo178a.length);
            if (this.d == 2) {
                this.e = 2;
            }
        }
    }

    protected final void a(byte[] bArr, int i) {
        int iMin;
        if (i <= this.b) {
            int iBulkTransfer = this.f317a.bulkTransfer(this.f322b, bArr, i, this.f);
            if (iBulkTransfer != i) {
                p.a("SerialDevice - write failed " + iBulkTransfer);
                return;
            }
            return;
        }
        int i2 = 0;
        int i3 = 0;
        while (i3 < i) {
            int i4 = i2 + 1;
            if (i2 < 100 && (iMin = Math.min(this.b, i - i3)) != 0) {
                System.arraycopy(bArr, i3, this.f327d, 0, iMin);
                int iBulkTransfer2 = this.f317a.bulkTransfer(this.f322b, this.f327d, iMin, this.f);
                if (iBulkTransfer2 != iMin) {
                    p.a("SerialDevice - write failed " + iBulkTransfer2);
                    return;
                } else {
                    i3 = iMin + i3;
                    i2 = i4;
                }
            } else {
                return;
            }
        }
    }
}
