package de.humatic.nmj;

import android.hardware.usb.UsbDevice;
import android.os.Build;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class r extends u {
    private int e;

    r(int i, UsbDevice usbDevice) throws IOException {
        super(i, usbDevice);
        this.e = 0;
        if (Build.VERSION.SDK_INT >= 13) {
            byte[] rawDescriptors = this.f317a.getRawDescriptors();
            p.a(5, "Prolific, raw descriptors:", rawDescriptors);
            int i2 = -1;
            int i3 = -1;
            int i4 = 0;
            while (i4 < rawDescriptors.length) {
                int i5 = i4 + 1;
                int i6 = rawDescriptors[i4] & 255;
                int i7 = i5 + 1;
                int i8 = rawDescriptors[i5] & 255;
                if (i6 <= 2) {
                    break;
                }
                if (i8 == 1) {
                    i3 = rawDescriptors[i7 + 5] & 255;
                    p.logln(3, "   Ctrl mps " + i3);
                } else if (i8 == 5) {
                    i2 = ((rawDescriptors[i7 + 3] & 255) << 8) | (rawDescriptors[i7 + 2] & 255);
                    p.logln(3, "   Endpoint mps " + i2);
                }
                i4 = (i6 - 2) + i7;
            }
            if (i2 < 0 && i3 < 0) {
                i3 = 4;
            } else if (i2 >= 0) {
                i3 = i2;
            }
            this.b = i3;
            p.logln(5, "nr. ports: " + this.c + ", max pkt size " + this.b);
            if (this.f316a.getDeviceClass() == 2) {
                this.e = 1;
            } else if (this.b == 64 || !(this.f316a.getDeviceClass() == 0 || this.f316a.getDeviceClass() == 255)) {
                this.e = 0;
            } else {
                this.e = 2;
            }
        }
        a(64, 1, 0, 0, (byte[]) null);
        a(64, 1, 8, 0, (byte[]) null);
        a(64, 1, 9, 0, (byte[]) null);
        a(192, 1, 33924, 0, 1);
        a(64, 1, 1028, 0, (byte[]) null);
        a(192, 1, 33924, 0, 1);
        a(192, 1, 33667, 0, 1);
        a(192, 1, 33924, 0, 1);
        a(64, 1, 1028, 1, (byte[]) null);
        a(192, 1, 33924, 0, 1);
        a(192, 1, 33667, 0, 1);
        a(64, 1, 0, 1, (byte[]) null);
        a(64, 1, 1, 0, (byte[]) null);
        a(64, 1, 2, this.e == 0 ? 68 : 36, (byte[]) null);
    }
}
