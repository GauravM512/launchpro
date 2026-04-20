package de.humatic.nmj;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/* JADX INFO: loaded from: classes.dex */
final class s implements f {
    int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    long f257a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private l f258a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private t f259a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private DatagramPacket f261a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private short f262a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private boolean f263a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private long f264b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private DatagramPacket f265b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private boolean f266b;
    int c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private long f267c;
    private int d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private long f268d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    int b = 0;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String f260a = "RTPClient";

    s(t tVar, int i, long j) {
        this.f259a = tVar;
        this.e = this.f259a.c;
        this.f258a = new l(this, this.e, i, j);
    }

    final int a() {
        return this.d;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final String m135a() {
        return this.f260a;
    }

    final void a(String str) {
        this.f260a = str;
    }

    final void a(boolean z) {
        this.d = 0;
        if (z) {
            this.f261a = null;
            this.f265b = null;
            this.f260a = "unknown RTP Client";
            this.a = -1;
        }
        this.f263a = false;
        this.f266b = false;
        this.f268d = 0L;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final void m137a(int i) {
        this.d = i;
        this.f268d = System.currentTimeMillis();
    }

    final void a(int i, boolean z) {
        if (i == 0) {
            this.f263a = z;
        } else if (i == 1) {
            this.f266b = z;
        }
        if (m138a()) {
            this.f257a = System.currentTimeMillis();
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final boolean m138a() {
        return this.f263a && this.f266b;
    }

    final void a(int i, InetSocketAddress inetSocketAddress) throws SocketException {
        if (i != 0) {
            this.f265b = new DatagramPacket(new byte[64], 64, inetSocketAddress);
        } else {
            this.f261a = new DatagramPacket(new byte[64], 64, inetSocketAddress);
        }
    }

    final DatagramPacket a(int i) {
        return i == 0 ? this.f261a : this.f265b;
    }

    final void a(short s) {
        this.f262a = s;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final short m136a() {
        return this.f262a;
    }

    final byte[][] a(byte[] bArr, int i) {
        return this.f258a.m114a(bArr, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final int a(byte[] r11, int r12, int r13) {
        /*
            r10 = this;
            r4 = 2
            r1 = 1
            r2 = 0
            r0 = r11[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 << 8
            r3 = 3
            r3 = r11[r3]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r0 = r0 | r3
            short r5 = (short) r0
            short r0 = r10.f262a
            int r0 = r5 - r0
            int r0 = java.lang.Math.abs(r0)
            if (r0 == r1) goto L97
            if (r5 == 0) goto L97
            short r0 = r10.f262a
            if (r0 == 0) goto L97
            int r0 = r10.g
            if (r0 != 0) goto L26
            r10.i = r13
        L26:
            int r0 = r10.h
            int r0 = r5 - r0
            if (r0 <= 0) goto L73
            int r0 = r10.h
            int r0 = r5 - r0
            r3 = 300(0x12c, float:4.2E-43)
            if (r0 >= r3) goto L73
            int r0 = r10.g
            int r0 = r0 + 1
            r10.g = r0
            int r0 = r10.g
            r3 = 50
            if (r0 <= r3) goto L85
            long r6 = (long) r13
            long r8 = r10.f257a
            long r6 = r6 - r8
            r8 = 10000(0x2710, double:4.9407E-320)
            int r0 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r0 <= 0) goto L71
            int r0 = r10.i
            int r0 = r13 - r0
            r3 = 30000(0x7530, float:4.2039E-41)
            if (r0 >= r3) goto L71
            r0 = r1
        L53:
            r10.g = r2
        L55:
            r10.h = r5
            de.humatic.nmj.l r3 = r10.f258a
            if (r3 == 0) goto L62
            de.humatic.nmj.l r3 = r10.f258a
            short r6 = r10.f262a
            r3.a(r6)
        L62:
            r3 = r1
        L63:
            de.humatic.nmj.l r6 = r10.f258a
            r6.a(r11, r12)
            r10.f262a = r5
            long r6 = (long) r13
            r10.f264b = r6
            if (r0 == 0) goto L87
            r2 = r4
        L70:
            return r2
        L71:
            r0 = r2
            goto L53
        L73:
            int r0 = r10.h
            int r0 = r5 - r0
            r3 = 200(0xc8, float:2.8E-43)
            if (r0 <= r3) goto L85
            int r0 = r10.g
            if (r0 <= 0) goto L85
            int r0 = r10.g
            int r0 = r0 + (-1)
            r10.g = r0
        L85:
            r0 = r2
            goto L55
        L87:
            if (r3 != 0) goto L93
            int r0 = r10.f
            int r3 = r0 + 1
            r10.f = r3
            r3 = 80
            if (r0 <= r3) goto L70
        L93:
            r10.f = r2
            r2 = r1
            goto L70
        L97:
            r0 = r2
            r3 = r2
            goto L63
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.s.a(byte[], int, int):int");
    }

    public final String toString() {
        String string;
        String string2;
        try {
            string = ((InetSocketAddress) this.f261a.getSocketAddress()).getAddress().toString();
        } catch (Exception e) {
            string = null;
        }
        try {
            string2 = ((InetSocketAddress) this.f265b.getSocketAddress()).getAddress().toString();
        } catch (Exception e2) {
            string2 = null;
        }
        try {
            return String.valueOf(this.f260a) + " " + this.d + ":\n   " + string + "\n   " + string2;
        } catch (Exception e3) {
            return "RTPClient";
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected final v m134a() {
        v vVar = new v();
        vVar.a = this.a;
        vVar.f329a = this.f260a;
        vVar.b = 1;
        try {
            vVar.f330b = a(0).getAddress().toString();
            vVar.c = a(0).getPort();
        } catch (Exception e) {
        }
        return vVar;
    }

    final void a(int i, long j, long j2) {
        if (j != -1) {
            this.f267c = j;
        }
        this.f268d = j2;
        if (i >= 0) {
            this.b = 0;
            if (i > 0) {
                this.c++;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final int a(long r8) {
        /*
            r7 = this;
            r0 = 1
            r1 = 0
            long r2 = r7.f268d
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 == 0) goto L34
            long r2 = r7.f268d
            long r4 = r8 - r2
            int r2 = r7.c
            r3 = 5
            if (r2 <= r3) goto L31
            r2 = 15000(0x3a98, float:2.102E-41)
        L15:
            long r2 = (long) r2
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 <= 0) goto L34
            r2 = r0
        L1b:
            if (r2 == 0) goto L38
            long r2 = r7.f264b
            long r2 = r8 - r2
            r4 = 5000(0x1388, double:2.4703E-320)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L36
        L27:
            long r2 = r7.f267c
            if (r0 == 0) goto L30
            long r0 = r7.f268d
            long r0 = r8 - r0
            int r1 = (int) r0
        L30:
            return r1
        L31:
            r2 = 5000(0x1388, float:7.006E-42)
            goto L15
        L34:
            r2 = r1
            goto L1b
        L36:
            r0 = r1
            goto L27
        L38:
            r0 = r2
            goto L27
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.s.a(long):int");
    }

    @Override // de.humatic.nmj.f
    public final void a(byte[] bArr, int i, int i2, long j) {
        this.f259a.a(this.e, this.d, bArr, j);
    }
}
