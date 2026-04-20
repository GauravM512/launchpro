package de.humatic.nmj;

import android.hardware.usb.UsbDevice;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class k extends u {
    private int[] a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[][] f197a;
    private boolean c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private byte[] f198c;
    private byte[] d;
    private int e;
    private int f;

    k(int i, UsbDevice usbDevice) throws IOException {
        super(i, usbDevice);
        this.f198c = new byte[520];
        this.d = new byte[3];
        this.a = new int[]{26, 52, 49230, 16696};
        this.f323b = true;
    }

    @Override // de.humatic.nmj.u
    protected final void a(boolean z) throws IOException {
        if (z) {
            a(64, 0, 0, 2, (byte[]) null);
            a(64, 0, 1, 2, (byte[]) null);
            a(64, 0, 2, 2, (byte[]) null);
            a(64, 2, 0, 2, (byte[]) null);
            a(64, 3, this.a[0], 2, (byte[]) null);
            a(64, 4, 8, 2, (byte[]) null);
        }
        if (this.d == 0) {
            new Thread(new Runnable() { // from class: de.humatic.nmj.k.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        Thread.sleep(1000L);
                        k.this.f320a = true;
                        if (k.this.d == 0) {
                            k.this.a(new byte[]{126, 3, 2, 0, 0, 1, -25}, 7);
                        }
                        Thread.sleep(1000L);
                        k.this.f320a = false;
                    } catch (Exception e) {
                    }
                }
            }).start();
            return;
        }
        if (this.d == 1) {
            this.f321a = new byte[512];
            this.f324b = new byte[512];
            a(new byte[]{13}, 1);
            a("'".getBytes(), 1);
            a(new String("[\r").getBytes(), 2);
            a(new String("/1\r").getBytes(), 3);
            return;
        }
        if (this.d == 2) {
            this.f321a = new byte[512];
            this.f324b = new byte[512];
            a(new byte[]{126, 3, 2, 0, 0, 1, -25}, 7);
            a(new byte[]{126, 8, 1, 0, 1, -25}, 6);
            this.f197a = new byte[5][];
            for (int i = 0; i < this.f197a.length; i++) {
                int i2 = (32 << i) + 1;
                this.f197a[i] = new byte[i2 + 5];
                this.f197a[i][0] = 126;
                this.f197a[i][1] = 6;
                this.f197a[i][2] = (byte) i2;
                this.f197a[i][3] = (byte) (i2 >> 8);
                this.f197a[i][4] = 0;
                this.f197a[i][this.f197a[i].length - 1] = -25;
            }
        }
    }

    @Override // de.humatic.nmj.u
    protected final byte[] a(byte[] bArr, int i, int i2) throws IOException {
        if (this.d == 0) {
            p.m126a(bArr, i, i2);
            if (this.f320a) {
                if ((bArr[i] == 63 && bArr[i + 1] == 13) || (bArr[i] == 62 && bArr[i + 1] == 63 && bArr[i + 2] == 13)) {
                    this.d = 1;
                    NMJConfig.setPort(this.a, this.d);
                    this.f320a = false;
                    p.logln(2, "Cinetix USB DMX interface detected");
                    a(false);
                } else if ((bArr[i] == 126 && bArr[i + 1] == 3) || (bArr[i] & 255) == 231 || bArr[i + 1] == 126) {
                    this.d = 2;
                    NMJConfig.setPort(this.a, this.d);
                    this.f320a = false;
                    p.logln(2, "Enttec USB DMX Pro detected");
                    a(false);
                }
            } else {
                this.f319a.a(bArr, i, i2);
            }
        } else if (this.d == 1) {
            this.e = i;
            int i3 = 0;
            while (this.e < i2) {
                while (true) {
                    if (bArr[this.e] != 62 && bArr[this.e] != 13) {
                        break;
                    }
                    int i4 = this.e;
                    this.e = i4 + 1;
                    if (i4 >= i2) {
                        break;
                    }
                    i++;
                }
                if (this.e >= i2) {
                    break;
                }
                while (bArr[this.e] < 64) {
                    int i5 = this.e;
                    this.e = i5 + 1;
                    if (i5 >= i2) {
                        break;
                    }
                }
                if (this.e >= i2) {
                    break;
                }
                int i6 = i + 1;
                byte b = bArr[i];
                if (b == 78) {
                    while (bArr[this.e] != 58) {
                        int i7 = this.e;
                        this.e = i7 + 1;
                        if (i7 >= i2) {
                            break;
                        }
                    }
                    if (this.e >= i2) {
                        break;
                    }
                    i3 = this.e - i6 == 3 ? ((bArr[this.e - 3] & 15) * 100) + 0 : 0;
                    if (this.e - i6 >= 2) {
                        i3 += (bArr[this.e - 2] & 15) * 10;
                    }
                    if (this.e - i6 > 0) {
                        i3 += bArr[this.e - 1] & 15;
                    }
                    if (i3 > 0) {
                        i3--;
                    }
                    i6 = this.e + 1;
                } else if (b == 82) {
                    int i8 = i6 + 1;
                    StringBuilder sbAppend = new StringBuilder("Cinetix, Firmware revision R").append(bArr[i6] & 15);
                    i6 = i8 + 1;
                    p.logln(1, sbAppend.append(bArr[i8] & 15).toString());
                } else if (b == 100) {
                    p.logln(1, "Cinetix, Firmware version: " + (bArr[i6] & 15) + (bArr[i6 + 1] & 15));
                    this.e = i6 + 5;
                    i = i6 + 5;
                } else if (b == 68) {
                    p.logln(1, "Cinetix, merge mode: " + new String(bArr, i6 - 1, i6 + 5));
                    this.e = i6 + 6;
                    i = i6 + 6;
                }
                while (bArr[this.e] != 42) {
                    int i9 = this.e;
                    this.e = i9 + 1;
                    if (i9 >= i2) {
                        break;
                    }
                }
                if (this.e >= i2) {
                    break;
                }
                int i10 = this.e - i6 == 3 ? ((bArr[this.e - 3] & 15) * 100) + 0 : 0;
                if (this.e - i6 >= 2) {
                    i10 += (bArr[this.e - 2] & 15) * 10;
                }
                if (this.e - i6 > 0) {
                    i10 += bArr[this.e - 1] & 15;
                }
                i = this.e + 1;
                if (this.f321a[i3] != ((byte) i10)) {
                    this.f321a[i3] = (byte) i10;
                    this.d[0] = (byte) ((i3 / 128) | 176);
                    this.d[1] = (byte) (i3 % 128);
                    this.d[2] = (byte) (i10 / 2);
                    this.f319a.a(this.d, 0, 3);
                }
            }
        } else if (this.d == 2) {
            while (i < i2) {
                if ((bArr[i] & 255) == 126) {
                    this.c = true;
                    this.f198c[0] = bArr[i];
                    this.e = 1;
                } else if ((bArr[i] & 255) == 231) {
                    this.c = false;
                    byte[] bArr2 = this.f198c;
                    int i11 = this.e;
                    this.e = i11 + 1;
                    bArr2[i11] = bArr[i];
                    b(this.f198c, this.e);
                } else if (this.c) {
                    byte[] bArr3 = this.f198c;
                    int i12 = this.e;
                    this.e = i12 + 1;
                    bArr3[i12] = bArr[i];
                }
                i++;
            }
        }
        return bArr;
    }

    private void b(byte[] bArr, int i) {
        int i2;
        byte b;
        switch (bArr[1]) {
            case 3:
                p.logln(2, "ENTTEC USB DMX pro:\nFirmware version: " + (bArr[4] | (bArr[5] << 8)) + "\nDMX output breaktime: " + (bArr[6] * 10.67f) + " msec\nDMX output mark: " + (bArr[7] * 10.67f) + " msec\nDMX output rate: " + ((int) bArr[8]) + " packets/sec\n");
                break;
            case 5:
            case NMJConfig.BLE /* 9 */:
                int i3 = 2;
                int i4 = 0;
                while (i3 < i) {
                    int i5 = i3 + 1;
                    int i6 = bArr[i3] & 255;
                    i3 = i5 + 1;
                    if (i3 + (i6 | ((bArr[i5] & 255) << 8)) < i) {
                        if (bArr[1] == 9) {
                            int i7 = i3 + 1;
                            int i8 = (bArr[i3] & 255) << 3;
                            int i9 = 0;
                            while (true) {
                                int i10 = i9;
                                int i11 = i7;
                                if (i10 < 5) {
                                    int i12 = bArr[i4 + 5 + i10] & 255;
                                    int i13 = 0;
                                    int i14 = i11 + 1;
                                    int i15 = 0;
                                    while (i15 < 8) {
                                        if (((1 << i15) & i12) != 0) {
                                            i2 = i13 + 1;
                                            int i16 = bArr[i13 + i4 + 10] & 255;
                                            int i17 = (i10 << 3) + i8 + i15;
                                            if (i17 < 512 && this.f321a[i17] != ((byte) i16) && (i16 % 8 != 0 || this.f321a[i17] % 8 != 0)) {
                                                this.f321a[i17] = (byte) i16;
                                                this.d[0] = (byte) ((i17 / 128) | 176);
                                                this.d[1] = (byte) (i17 % 128);
                                                this.d[2] = (byte) (i16 / 2);
                                                this.f319a.a(this.d, 0, 3);
                                            }
                                        } else {
                                            i2 = i13;
                                        }
                                        i15++;
                                        i13 = i2;
                                    }
                                    i7 = i13 + i14;
                                    i9 = i10 + 1;
                                } else {
                                    int i18 = i11 + 1;
                                    i3 = i11 + 3;
                                    i4 = i18;
                                }
                            }
                        } else {
                            if (bArr[1] == 5) {
                                int i19 = i3 + 1;
                                if (bArr[i3] == 0) {
                                    int i20 = i19 + 1;
                                    int i21 = (bArr[i19] & 255) << 3;
                                    for (int i22 = i20; i22 < (i20 + r2) - 1; i22++) {
                                        int i23 = (i22 - i20) + i21;
                                        if (i23 <= this.f321a.length - 1 && this.f321a[i23] != (b = bArr[i22])) {
                                            this.f321a[i23] = b;
                                            this.d[0] = (byte) ((i23 / 128) | 176);
                                            this.d[1] = (byte) (i23 % 128);
                                            this.d[2] = (byte) (b / 2);
                                            this.f319a.a(this.d, 0, 3);
                                        }
                                    }
                                    i3 = i;
                                }
                            } else {
                                continue;
                            }
                            break;
                        }
                    } else {
                        break;
                    }
                }
                break;
        }
    }

    @Override // de.humatic.nmj.u
    /* JADX INFO: renamed from: a */
    protected final byte[] mo178a(byte[] bArr) {
        byte b;
        if (this.d == 1) {
            if ((bArr[0] & 15) > 3) {
                return null;
            }
            switch (bArr[0] & 255) {
                case 128:
                    bArr[2] = 0;
                    break;
                case 144:
                case 176:
                    break;
                default:
                    return null;
            }
            int i = bArr[1] + ((bArr[0] & 15) << 7);
            if (i < 512 && this.f324b[i] != (bArr[2] << 1)) {
                if (i < 256 || i == 512) {
                    bArr[0] = 2;
                    bArr[1] = (byte) (i != 512 ? i + 1 : 0);
                    bArr[2] = (byte) (bArr[2] << 1);
                } else {
                    bArr[0] = 3;
                    bArr[1] = (byte) (i - 256);
                    bArr[2] = (byte) (bArr[2] << 1);
                }
                this.f324b[i] = bArr[2];
                return bArr;
            }
            return null;
        }
        if (this.d == 2) {
            switch (bArr[0] & 240) {
                case 128:
                    if ((bArr[0] & 15) > 3) {
                        return null;
                    }
                    b = 0;
                    break;
                case 144:
                case 176:
                    if ((bArr[0] & 15) > 3) {
                        return null;
                    }
                    b = (byte) (bArr[2] << 1);
                    break;
                default:
                    return null;
            }
            int i2 = bArr[1] + ((bArr[0] & 15) << 7);
            if (this.f324b[i2] == b) {
                return null;
            }
            while (true) {
                if (i >= this.f197a.length) {
                    i = -1;
                } else if (i2 >= this.f197a[i].length - 6) {
                    i++;
                }
            }
            if (i != this.f) {
                System.arraycopy(this.f197a[this.f], 5, this.f197a[i], 5, Math.min(this.f197a[i].length - 6, this.f197a[this.f].length - 6));
                this.f = i;
            }
            this.f197a[i][i2 + 5] = b;
            this.f324b[i2] = b;
            return this.f197a[i];
        }
        return bArr;
    }
}
