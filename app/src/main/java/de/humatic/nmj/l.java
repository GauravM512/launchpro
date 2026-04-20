package de.humatic.nmj;

import java.lang.reflect.Array;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
final class l {
    private byte a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private int f199a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private long f200a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private f f201a;
    private int b;
    private int d;
    private int f;
    private int g;
    private int h;
    private int i;
    private int c = (int) (Math.random() * 65536.0d);
    private int e = 256;
    private int j = -1;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private long f205b = -1;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[][] f204a = new byte[70][];

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private byte[][] f207b = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 32, 1);

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private byte[][] f209c = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 32, 2);

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private byte[][] f210d = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 64, 3);

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[] f203a = {-16, 127, 127, 1, 1, 0, 0, 0, 0, 0, -9};

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Vector f202a = new Vector();

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Vector<byte[]> f206b = new Vector<>();

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private Vector<byte[]> f208c = new Vector<>();

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private byte[][] f211e = new byte[1][];

    l(f fVar, int i, int i2, long j) {
        this.f199a = i;
        this.b = i2;
        this.f201a = fVar;
    }

    final void a(byte[] bArr, int i) {
        a(bArr, 0, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:181:0x04a2, code lost:
    
        r4 = r0 / 10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x04a9, code lost:
    
        if (r23.i < 0) goto L228;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x04ab, code lost:
    
        r4 = r23.i;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0090, code lost:
    
        if (r6 != false) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0092, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01e9, code lost:
    
        de.humatic.nmj.p.logln(1, "RTP - Invalid MIDI at " + (r11 - 1));
     */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0487 A[LOOP:5: B:176:0x0453->B:179:0x0487, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:223:0x045d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void a(byte[] r24, int r25, int r26) {
        /*
            Method dump skipped, instruction units count: 1218
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.l.a(byte[], int, int):void");
    }

    private void a(int i, byte[] bArr, long j) {
        this.f201a.a(bArr, 0, bArr.length, j);
    }

    final void a(int i) {
        this.i = i;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final byte[][] m114a(byte[] bArr, int i) {
        this.d = i;
        this.f206b.removeAllElements();
        this.f208c.removeAllElements();
        int iB = 0;
        byte b = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= bArr.length || this.f206b.size() >= 256) {
                break;
            }
            if ((bArr[i2] & 128) != 0) {
                b = bArr[i2];
                iB = p.b(bArr, i2);
                i2++;
            }
            if (iB <= 0) {
                if (iB != 0) {
                    byte[] bArr2 = new byte[bArr.length - i2];
                    bArr2[0] = b;
                    System.arraycopy(bArr, i2, bArr2, 1, bArr.length - i2);
                    this.f206b.add(bArr2);
                    break;
                }
                if (b == 0) {
                    p.a(2, "RTP encode - invalid status byte at idx: " + i2, bArr);
                    NMJConfig.a(this.f199a, NMJConfig.E_INVALID_DATA, "RTP encode - invalid status byte at idx: " + i2);
                    break;
                }
                this.f206b.add(new byte[]{b});
            } else {
                if (i2 + iB > bArr.length) {
                    p.a(2, "RTP encode - invalid data on input at idx: " + i2, bArr);
                    NMJConfig.a(this.f199a, NMJConfig.E_INVALID_DATA, "RTP encode - invalid data on input at idx: " + i2);
                    break;
                }
                byte[] bArr3 = new byte[iB + 1];
                bArr3[0] = b;
                System.arraycopy(bArr, i2, bArr3, 1, iB);
                this.f206b.add(bArr3);
                i2 += iB;
            }
        }
        if (this.f206b.size() >= 255) {
            p.a(2, "potential overflow", bArr);
            NMJConfig.a(this.f199a, NMJConfig.E_INVALID_DATA, "RTP encode - overflow");
            while (this.f206b.size() > 1) {
                this.f206b.remove(this.f206b.size() - 1);
            }
        }
        if (this.f206b.size() == 0) {
            return null;
        }
        for (int i3 = 0; i3 < this.f206b.size(); i3++) {
            if ((this.f206b.get(i3)[0] & 255) == 240) {
                a(this.f206b.get(i3));
            }
        }
        a();
        if (this.f211e.length != this.f208c.size()) {
            this.f211e = new byte[this.f208c.size()][];
        }
        this.f208c.copyInto(this.f211e);
        return this.f211e;
    }

    private void a() {
        int length;
        int i;
        int i2;
        int length2;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.f206b.size(); i6++) {
            if ((this.f206b.get(i6)[0] & 255) != 240) {
                if (i3 > 0) {
                    i5++;
                }
                if ((this.f206b.get(i6)[0] & 255) == i4) {
                    length2 = (this.f206b.get(i6).length - 1) + i5;
                } else {
                    int length3 = i5 + this.f206b.get(i6).length;
                    i4 = this.f206b.get(i6)[0] & 255;
                    length2 = length3;
                }
                i3++;
                i5 = length2;
            }
        }
        if (i5 != 0 && i3 != 0) {
            byte[] bArr = new byte[(i5 > 15 ? 1 : 0) + i5 + 13];
            bArr[0] = -128;
            bArr[1] = 97;
            this.c++;
            if (this.c > 65535) {
                this.c = 0;
            }
            p.a(this.c, bArr, 2, 2);
            p.a(this.d, bArr, 4);
            p.a(this.b, bArr, 8);
            char c = i5 > 15 ? (char) 2 : (char) 1;
            if (c == 1) {
                bArr[12] = (byte) i5;
            } else {
                bArr[12] = (byte) ((i5 >> 8) | 128);
                bArr[13] = (byte) i5;
            }
            int i7 = 0;
            int i8 = 0;
            int i9 = c == 1 ? 13 : 14;
            for (int i10 = 0; i10 < this.f206b.size(); i10++) {
                if ((this.f206b.get(i10)[0] & 255) != 240) {
                    if (i7 > 0) {
                        bArr[i9] = 0;
                        i9++;
                    }
                    if ((this.f206b.get(i10)[0] & 255) == i8) {
                        i = i8;
                        length = this.f206b.get(i10).length - 1;
                        i2 = 1;
                    } else {
                        length = this.f206b.get(i10).length;
                        i = this.f206b.get(i10)[0] & 255;
                        i2 = 0;
                    }
                    System.arraycopy(this.f206b.get(i10), i2, bArr, i9, length);
                    i9 += length;
                    i7++;
                    i8 = i;
                }
            }
            this.f208c.add(bArr);
        }
    }

    private void a(byte[] bArr) {
        int i;
        int length = (bArr.length / this.e) + 1;
        byte[][] bArr2 = new byte[length][];
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr.length) {
            int iMin = Math.min(this.e, bArr.length - i2);
            if (length <= 1) {
                i = 0;
            } else {
                i = (i3 == 0 || i3 == bArr2.length + (-1)) ? 1 : 2;
            }
            int i4 = iMin + i <= 15 ? 13 : 14;
            bArr2[i3] = new byte[i4 + iMin + i];
            bArr2[i3][0] = -128;
            bArr2[i3][1] = 97;
            this.c++;
            if (this.c > 65535) {
                this.c = 0;
            }
            p.a(this.c, bArr2[i3], 2, 2);
            p.a(this.d, bArr2[i3], 4);
            p.a(this.b, bArr2[i3], 8);
            byte b = i3 == 0 ? (byte) -16 : (byte) -9;
            byte b2 = i3 == bArr2.length + (-1) ? (byte) -9 : (byte) -16;
            if (iMin + i > 15) {
                bArr2[i3][12] = (byte) (((iMin + i) >> 8) | 128);
                bArr2[i3][13] = (byte) (iMin + i);
            } else {
                bArr2[i3][12] = (byte) (iMin + i);
            }
            System.arraycopy(bArr, i2, bArr2[i3], (i3 == 0 ? 0 : 1) + i4, iMin);
            if (i3 != 0) {
                bArr2[i3][i4] = b;
            }
            i2 += iMin;
            if (i3 != bArr2.length - 1) {
                bArr2[i3][i4 + iMin + (i - 1)] = b2;
            }
            this.f208c.add(bArr2[i3]);
            i3++;
        }
    }
}
