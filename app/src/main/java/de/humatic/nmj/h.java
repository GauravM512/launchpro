package de.humatic.nmj;

import java.net.DatagramPacket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.TimerTask;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
final class h {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private long f160a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private d f161a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private j f162a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String f163a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private DatagramPacket f164a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private MulticastSocket f165a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkInterface f166a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private SocketAddress f167a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[] f170a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private c[] f171a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String[] f172a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private long f173b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private String f174b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private DatagramPacket f175b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private MulticastSocket f176b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private SocketAddress f177b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private byte[] f180b;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private long f181c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private String f182c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private DatagramPacket f183c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private boolean f185c;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private String f187d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private boolean f189d;
    private int e;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private String f191e;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private boolean f192e;
    private boolean h;
    private boolean i;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private boolean f169a = true;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private boolean f179b = true;

    /* JADX INFO: renamed from: f, reason: collision with other field name */
    private boolean f194f = true;
    private boolean g = false;
    private int a = -1;
    private int b = 5;
    private int c = 12;
    private int d = -1;
    private int f = -1;

    /* JADX INFO: renamed from: f, reason: collision with other field name */
    private String f193f = de.bassapps.launchbuttonsP.BuildConfig.FLAVOR;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private byte[] f186c = {0, 64};

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private byte[] f190d = new byte[1460];

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Vector f168a = new Vector();

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Vector<g> f178b = new Vector<>();

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private Vector<String> f184c = new Vector<>();

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private Vector<g> f188d = new Vector<>();

    /* JADX WARN: Can't wrap try/catch for region: R(17:23|(1:25)|26|(3:45|(1:64)(13:63|(2:30|(1:32)(2:48|49))(2:50|(2:52|53)(2:54|(1:56)))|33|(1:35)|36|(1:38)(1:57)|39|(1:41)|42|60|43|44|66)|27)|62|(0)(0)|33|(0)|36|(0)(0)|39|(0)|42|60|43|44|66) */
    /* JADX WARN: Removed duplicated region for block: B:30:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0290  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    h(de.humatic.nmj.j r10, int r11) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 692
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.h.<init>(de.humatic.nmj.j, int):void");
    }

    final void a() {
        byte b2 = 0;
        this.f185c = false;
        this.f179b = (NMJConfig.getFlags(-1) & 1) != 0;
        this.f169a = (NMJConfig.getFlags(-1) & 2) != 0;
        if (!this.f189d) {
            if (!this.f179b || !this.f169a) {
                p.logln(2, "DNS - discover: " + this.f179b + ", announce: " + this.f169a);
            }
            if (this.f194f) {
                new Thread(new b(this, b2)).start();
            }
            this.f161a = new d(this, b2);
            if (this.f169a) {
                new Thread(new a(this, b2)).start();
            } else {
                try {
                    byte[] bArrA = a("_apple-midi", "_udp", "local");
                    m104a(bArrA, bArrA.length);
                } catch (Exception e) {
                }
            }
        } else {
            this.a = 0;
        }
        this.f189d = true;
    }

    final void b() {
        this.f185c = true;
        try {
            this.f161a.a();
        } catch (Exception e) {
        }
    }

    final void c() {
        for (int i = 0; i < this.f171a.length; i++) {
            try {
                a(this.f171a[i].a);
            } catch (Exception e) {
            }
        }
        b();
        this.f189d = false;
        try {
            this.f165a.leaveGroup(this.f167a, this.f166a);
        } catch (Exception e2) {
        }
        try {
            this.f165a.close();
        } catch (Exception e3) {
        }
        try {
            this.f176b.leaveGroup(this.f177b, this.f166a);
        } catch (Exception e4) {
        }
        try {
            this.f176b.close();
        } catch (Exception e5) {
        }
        p.logln(2, "DNS (" + this.f191e + ") closed");
        NMJConfig.m19a(this.e);
    }

    final void a(int i, int i2) {
        try {
            c(3);
        } catch (Exception e) {
        }
        if (this.a < 4) {
            this.a = 0;
        } else {
            this.a = 0;
            this.f161a.a();
        }
    }

    final void a(int i) {
        String str = "_apple-midi";
        String str2 = "_udp";
        if (NMJConfig.getMode(i) == 6) {
            str = "_ws-midi";
            str2 = "_tcp";
        }
        try {
            byte[] bArrA = a(str, str2, "local", NMJConfig.getIP(i) != null ? NMJConfig.m15a(i) : NMJConfig.getName(i));
            if (this.f194f) {
                this.f175b.setData(bArrA, 0, bArrA.length);
                this.f165a.send(this.f175b);
            }
        } catch (Exception e) {
        }
    }

    class a extends Thread {
        private a() {
        }

        /* synthetic */ a(h hVar, byte b) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x00a4  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instruction units count: 630
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.h.a.run():void");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(byte[] bArr) throws Exception {
        if (this.f175b != null && bArr != null && !this.f165a.isClosed() && !this.f185c) {
            this.f175b.setData(bArr, 0, bArr.length);
            this.f165a.send(this.f175b);
        }
    }

    private void b(byte[] bArr) throws Exception {
        if (this.f183c != null && bArr != null && !this.f176b.isClosed() && !this.f185c) {
            this.f183c.setData(bArr, 0, bArr.length);
            this.f176b.send(this.f183c);
        }
    }

    static /* synthetic */ void b(h hVar, byte[] bArr) throws Exception {
        hVar.m104a(bArr, bArr.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public void m104a(byte[] bArr, int i) throws Exception {
        if (i > 0 && !this.f185c && this.f194f && this.f175b != null && bArr != null) {
            this.f175b.setData(bArr, 0, i);
            if (!this.f165a.isClosed()) {
                this.f165a.send(this.f175b);
            }
        }
    }

    class b extends TimerTask {
        private b() {
        }

        /* synthetic */ b(h hVar, byte b) {
            this();
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            try {
                Thread.sleep(1500L);
            } catch (Exception e) {
            }
            if (h.this.f164a == null) {
                h.this.f164a = new DatagramPacket(new byte[1500], 1500);
            }
            while (!h.this.f185c) {
                try {
                    h.this.f164a.setLength(1500);
                    h.this.f165a.receive(h.this.f164a);
                    if (h.this.f164a.getLength() != 0) {
                        h.this.h = true;
                        InetAddress address = ((InetSocketAddress) h.this.f164a.getSocketAddress()).getAddress();
                        if (!(address instanceof Inet6Address) && (!p.a(address, h.this.e) || ((NMJConfig.getFlags(-1) & 4) != 0 && !NMJConfig.m27a(new String(h.this.f164a.getData(), 0, h.this.f164a.getLength()))))) {
                            h.this.a(h.this.f164a.getData(), h.this.f164a.getLength(), false, h.this.f164a.getSocketAddress());
                        }
                    }
                } catch (NullPointerException e2) {
                    h.this.f185c = true;
                } catch (SocketTimeoutException e3) {
                    if (!h.this.f169a && System.currentTimeMillis() - h.this.f173b > 10000) {
                        h.this.f173b = System.currentTimeMillis();
                        try {
                            h.b(h.this, h.a(h.this, "_apple-midi", "_udp", "local"));
                        } catch (Exception e4) {
                        }
                    }
                } catch (Exception e5) {
                    if (e5.toString().indexOf("closed") == -1 && e5.toString().indexOf("Interrupted system call") == -1 && e5.toString().indexOf("Bad file number") == -1) {
                        e5.printStackTrace();
                    }
                }
            }
        }
    }

    private static int a(byte[] bArr, String str, int i) {
        int length = str.length();
        int i2 = i + 1;
        bArr[i] = (byte) length;
        System.arraycopy(str.getBytes(), 0, bArr, i2, length);
        return length + i2;
    }

    static /* synthetic */ byte[] a(h hVar, boolean z, int i, boolean z2) {
        return hVar.a(i, z2);
    }

    private byte[] a(int i, boolean z) {
        try {
            if (this.f171a == null || this.f171a.length <= i || this.f171a[i] == null || this.f171a[i].f195a == null) {
                return null;
            }
            byte[] bArr = new byte[256];
            p.a((short) 1, bArr, 4);
            p.a((short) 1, bArr, 8);
            bArr[12] = (byte) this.f171a[i].f195a.length();
            System.arraycopy(this.f171a[i].f195a.getBytes(), 0, bArr, 13, this.f171a[i].f195a.length());
            int length = this.f171a[i].f195a.length() + 13;
            int i2 = length + 1;
            bArr[length] = (byte) this.f171a[i].f196b.length();
            System.arraycopy(this.f171a[i].f196b.getBytes(), 0, bArr, i2, this.f171a[i].f196b.length());
            int length2 = this.f171a[i].f196b.length() + i2;
            int i3 = length2 + 1;
            bArr[length2] = 4;
            System.arraycopy("_udp".getBytes(), 0, bArr, i3, 4);
            int i4 = i3 + 4;
            int i5 = i4 + 1;
            bArr[i4] = 5;
            System.arraycopy("local".getBytes(), 0, bArr, i5, 5);
            int i6 = i5 + 5;
            int i7 = i6 + 1;
            bArr[i6] = 0;
            p.a((short) 255, bArr, i7);
            int i8 = i7 + 2;
            int i9 = i8 + 1;
            bArr[i8] = z ? (byte) -128 : (byte) 0;
            int i10 = i9 + 1;
            bArr[i9] = 1;
            int i11 = i10 + 1;
            bArr[i10] = -64;
            int i12 = i11 + 1;
            bArr[i11] = 12;
            p.a((short) 33, bArr, i12);
            int i13 = i12 + 2;
            p.a((short) 1, bArr, i13);
            int i14 = i13 + 2;
            p.a(120, bArr, i14);
            int i15 = i14 + 4;
            byte length3 = (byte) (this.f163a.length() + 8 + 1);
            int i16 = i15 + 1;
            bArr[i15] = (byte) (length3 >> 8);
            bArr[i16] = length3;
            int i17 = i16 + 1 + 4;
            p.a((short) this.f171a[i].b, bArr, i17);
            int i18 = i17 + 2;
            int i19 = i18 + 1;
            bArr[i18] = (byte) this.f163a.length();
            System.arraycopy(this.f163a.getBytes(), 0, bArr, i19, this.f163a.length());
            int length4 = this.f163a.length() + i19;
            int i20 = length4 + 1;
            bArr[length4] = -64;
            int i21 = i20 + 1;
            bArr[i20] = (byte) i4;
            byte[] bArr2 = new byte[i21];
            System.arraycopy(bArr, 0, bArr2, 0, i21);
            return bArr2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static /* synthetic */ byte[] a(h hVar, String str, String str2, String str3) {
        return a(str, str2, str3);
    }

    private static byte[] a(String str, String str2, String str3) {
        byte[] bArr = new byte[str.length() + 18 + str2.length() + str3.length() + 2];
        p.a((short) 1, bArr, 4);
        bArr[12] = (byte) str.length();
        System.arraycopy(str.getBytes(), 0, bArr, 13, str.length());
        int length = str.length() + 13;
        int i = length + 1;
        bArr[length] = (byte) str2.length();
        System.arraycopy(str2.getBytes(), 0, bArr, i, str2.length());
        int length2 = str2.length() + i;
        int i2 = length2 + 1;
        bArr[length2] = (byte) str3.length();
        System.arraycopy(str3.getBytes(), 0, bArr, i2, str3.length());
        int length3 = str3.length() + i2 + 2;
        bArr[length3] = 12;
        p.a((short) 1, bArr, length3 + 1);
        return bArr;
    }

    private int a(String str, byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = 0;
        }
        p.a((short) 1, bArr, 4);
        int iA = a(bArr, "local", a(bArr, "_udp", a(bArr, "_apple-midi", a(bArr, str, 12)))) + 1;
        p.a((short) 33, bArr, iA);
        int i2 = iA + 2;
        p.a((short) 1, bArr, i2);
        return i2 + 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(int i, byte[] bArr) {
        int iA;
        if (this.f171a == null || this.f171a.length <= i || this.f171a[i] == null || this.f171a[i].f195a == null) {
            return 0;
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = 0;
        }
        p.a((short) -31744, bArr, 2);
        p.a((short) 4, bArr, 6);
        int iA2 = a(bArr, "local", a(bArr, this.f163a, 12)) + 1;
        p.a((short) 1, bArr, iA2);
        int i3 = iA2 + 2;
        p.a((short) 1, bArr, i3);
        int i4 = i3 + 2;
        p.a(3600, bArr, i4);
        int i5 = i4 + 4;
        p.a((short) 4, bArr, i5);
        int i6 = i5 + 2;
        System.arraycopy(this.f170a, 0, bArr, i6, 4);
        int i7 = i6 + 4;
        if (NMJConfig.getMode(this.f171a[i].a) == 1) {
            iA = a(bArr, "_udp", a(bArr, "_apple-midi", i7));
        } else {
            iA = NMJConfig.getMode(this.f171a[i].a) == 6 ? a(bArr, "_tcp", a(bArr, "_ws-midi", i7)) : i7;
        }
        int iA3 = a(bArr, "local", iA) + 1;
        p.a((short) 12, bArr, iA3);
        int i8 = iA3 + 2;
        p.a((short) 1, bArr, i8);
        int i9 = i8 + 2;
        p.a(3600, bArr, i9);
        int i10 = i9 + 4;
        p.a((short) (this.f171a[i].f195a.length() + 3), bArr, i10);
        int i11 = i10 + 2;
        int iA4 = a(bArr, this.f171a[i].f195a, i11);
        p.a((short) (i7 | 49152), bArr, iA4);
        int i12 = iA4 + 2;
        p.a((short) (49152 | i11), bArr, i12);
        int i13 = i12 + 2;
        p.a((short) 33, bArr, i13);
        int i14 = i13 + 2;
        p.a((short) -32767, bArr, i14);
        int i15 = i14 + 2;
        p.a(3600, bArr, i15);
        int i16 = i15 + 4;
        p.a((short) (this.f163a.length() + 14), bArr, i16);
        int i17 = i16 + 2 + 4;
        p.a((short) this.f171a[i].b, bArr, i17);
        int iA5 = a(bArr, "local", a(bArr, this.f163a, i17 + 2)) + 1;
        p.a((short) (i11 | 49152), bArr, iA5);
        int i18 = iA5 + 2;
        p.a((short) 16, bArr, i18);
        int i19 = i18 + 2;
        p.a((short) -32767, bArr, i19);
        int i20 = i19 + 2;
        p.a(4500, bArr, i20);
        int i21 = i20 + 4;
        p.a((short) 1, bArr, i21);
        return i21 + 2 + 1;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private byte[] m108a(String str) {
        int i = 0;
        try {
            if (this.f171a == null || this.f171a.length <= 0) {
                return null;
            }
            for (int i2 = 0; i2 < this.f171a.length; i2++) {
                if (this.f171a[i2].f196b.equalsIgnoreCase(str)) {
                    i++;
                }
            }
            byte[] bArr = new byte[i * 128];
            p.a((short) -31744, bArr, 2);
            p.a((short) i, bArr, 6);
            bArr[12] = 9;
            System.arraycopy("_services".getBytes(), 0, bArr, 13, 9);
            bArr[22] = 7;
            System.arraycopy("_dns_sd".getBytes(), 0, bArr, 23, 7);
            bArr[30] = 4;
            System.arraycopy("_udp".getBytes(), 0, bArr, 31, 4);
            bArr[35] = 5;
            System.arraycopy("local".getBytes(), 0, bArr, 36, 5);
            bArr[41] = 0;
            p.a((short) 12, bArr, 42);
            p.a((short) 1, bArr, 44);
            p.a(120, bArr, 46);
            p.a((short) (this.f163a.length() + 3), bArr, 50);
            bArr[52] = (byte) this.f163a.length();
            System.arraycopy(this.f163a.getBytes(), 0, bArr, 53, this.f163a.length());
            int length = this.f163a.length() + 53;
            int i3 = length + 1;
            bArr[length] = -64;
            int i4 = i3 + 1;
            bArr[i3] = 30;
            byte[] bArr2 = new byte[i4];
            System.arraycopy(bArr, 0, bArr2, 0, i4);
            return bArr2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(String str, String str2, String str3, String str4, int i, byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            try {
                bArr[i2] = 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        p.a((short) -31744, bArr, 2);
        p.a((short) 4, bArr, 6);
        int i3 = str3 != null ? 1 : 0;
        if (str4 != null) {
            i3++;
        }
        p.a((short) i3, bArr, 10);
        if (str2.indexOf(".") != -1) {
            str2 = str2.replace(".", "_");
        }
        bArr[12] = (byte) str2.length();
        System.arraycopy(str2.getBytes(), 0, bArr, 13, str2.length());
        int length = str2.length() + 13;
        int i4 = length + 1;
        bArr[length] = (byte) str.length();
        System.arraycopy(str.getBytes(), 0, bArr, i4, str.length());
        int length2 = i4 + str.length();
        int i5 = length2 + 1;
        bArr[length2] = (byte) "_udp".length();
        System.arraycopy("_udp".getBytes(), 0, bArr, i5, "_udp".length());
        int length3 = "_udp".length() + i5;
        int i6 = length3 + 1;
        bArr[length3] = (byte) "local".length();
        System.arraycopy("local".getBytes(), 0, bArr, i6, "local".length());
        int length4 = "local".length() + i6 + 1;
        p.a((short) 33, bArr, length4);
        int i7 = length4 + 2;
        p.a((short) -32767, bArr, i7);
        int i8 = i7 + 2;
        p.a(120, bArr, i8);
        int i9 = i8 + 4;
        byte length5 = (byte) (this.f163a.length() + 1 + 8);
        int i10 = i9 + 1;
        bArr[i9] = (byte) (length5 >> 8);
        bArr[i10] = length5;
        int i11 = i10 + 1 + 4;
        p.a((short) i, bArr, i11);
        int i12 = i11 + 2;
        int i13 = i12 + 1;
        bArr[i12] = (byte) this.f163a.length();
        System.arraycopy(this.f163a.getBytes(), 0, bArr, i13, this.f163a.length());
        int length6 = i13 + this.f163a.length();
        int i14 = length6 + 1;
        bArr[length6] = -64;
        int i15 = i14 + 1;
        bArr[i14] = (byte) length3;
        p.a((short) -16372, bArr, i15);
        int i16 = i15 + 2;
        p.a((short) 16, bArr, i16);
        int i17 = i16 + 2;
        p.a((short) -32767, bArr, i17);
        int i18 = i17 + 2;
        p.a(4500, bArr, i18);
        int i19 = i18 + 4;
        p.a((short) 1, bArr, i19);
        int i20 = i19 + 2;
        int i21 = i20 + 1;
        bArr[i20] = 0;
        int i22 = i21 + 1;
        bArr[i21] = 9;
        System.arraycopy("_services".getBytes(), 0, bArr, i22, 9);
        int i23 = i22 + 9;
        int i24 = i23 + 1;
        bArr[i23] = 7;
        System.arraycopy("_dns-sd".getBytes(), 0, bArr, i24, 7);
        int i25 = i24 + 7;
        p.a((short) (length2 | 49152), bArr, i25);
        int i26 = i25 + 2;
        p.a((short) 12, bArr, i26);
        int i27 = i26 + 2;
        p.a((short) 1, bArr, i27);
        int i28 = i27 + 2;
        p.a(4500, bArr, i28);
        int i29 = i28 + 4;
        p.a((short) 2, bArr, i29);
        int i30 = i29 + 2;
        p.a((short) (49152 | length), bArr, i30);
        int i31 = i30 + 2;
        p.a((short) (length | 49152), bArr, i31);
        int i32 = i31 + 2;
        p.a((short) 12, bArr, i32);
        int i33 = i32 + 2;
        p.a((short) 1, bArr, i33);
        int i34 = i33 + 2;
        p.a(4500, bArr, i34);
        int i35 = i34 + 4;
        p.a((short) 2, bArr, i35);
        int i36 = i35 + 2;
        p.a((short) -16372, bArr, i36);
        int i37 = i36 + 2;
        if (str3 != null) {
            p.a((short) (49152 | i12), bArr, i37);
            int i38 = i37 + 2;
            p.a((short) 1, bArr, i38);
            int i39 = i38 + 2;
            p.a((short) -32767, bArr, i39);
            int i40 = i39 + 2;
            p.a(120, bArr, i40);
            int i41 = i40 + 4 + 1;
            int i42 = i41 + 1;
            bArr[i41] = 4;
            System.arraycopy(this.f170a, 0, bArr, i42, 4);
            i37 = i42 + 4;
        }
        if (str4 != null) {
            p.a((short) (49152 | i12), bArr, i37);
            int i43 = i37 + 2;
            p.a((short) 28, bArr, i43);
            int i44 = i43 + 2;
            p.a((short) -32767, bArr, i44);
            int i45 = i44 + 2;
            p.a(120, bArr, i45);
            int i46 = i45 + 4 + 1;
            int i47 = i46 + 1;
            bArr[i46] = (byte) this.f180b.length;
            System.arraycopy(this.f180b, 0, bArr, i47, this.f180b.length);
            return this.f180b.length + i47;
        }
        return i37;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized int a(String str, String str2, String str3, byte[] bArr) {
        int i;
        int length;
        int i2;
        try {
            if (this.f171a == null || this.f171a.length <= 0) {
                i = 0;
            } else {
                int[] iArr = new int[this.f171a.length];
                int i3 = 0;
                for (int i4 = 0; i4 < this.f171a.length; i4++) {
                    if (this.f171a[i4].f196b.equalsIgnoreCase(str)) {
                        i3++;
                    }
                }
                if (i3 <= 0) {
                    p.logln(3, "No sessions for " + str);
                    i = 0;
                } else {
                    for (int i5 = 0; i5 < bArr.length; i5++) {
                        bArr[i5] = 0;
                    }
                    p.a((short) -31744, bArr, 2);
                    p.a((short) ((i3 * 3) + 1), bArr, 6);
                    p.a((short) (this.f194f ? 3 : 2), bArr, 10);
                    int i6 = 0;
                    int i7 = 12;
                    int length2 = 0;
                    int length3 = 0;
                    int i8 = 0;
                    int i9 = 0;
                    for (int i10 = 0; i10 < this.f171a.length; i10++) {
                        if (this.f171a[i10] != null && this.f171a[i10].f196b != null && this.f171a[i10].f196b.equalsIgnoreCase(str)) {
                            iArr[i10] = i7;
                            String strReplace = this.f171a[i10].f195a;
                            if (strReplace.indexOf(".") != -1) {
                                strReplace = strReplace.replace(".", "_");
                            }
                            int i11 = i7 + 1;
                            bArr[i7] = (byte) strReplace.length();
                            System.arraycopy(strReplace.getBytes(), 0, bArr, i11, strReplace.length());
                            int length4 = i11 + strReplace.length();
                            if (i6 == 0) {
                                int i12 = length4 + 1;
                                bArr[length4] = (byte) str.length();
                                System.arraycopy(str.getBytes(), 0, bArr, i12, str.length());
                                length3 = i12 + str.length();
                                int i13 = length3 + 1;
                                bArr[length3] = (byte) str2.length();
                                System.arraycopy(str2.getBytes(), 0, bArr, i13, str2.length());
                                length2 = str2.length() + i13;
                                int i14 = length2 + 1;
                                bArr[length2] = (byte) str3.length();
                                System.arraycopy(str3.getBytes(), 0, bArr, i14, str3.length());
                                length = i14 + str3.length() + 1;
                                i8 = length4;
                            } else {
                                p.a((short) (49152 | i8), bArr, length4);
                                length = length4 + 2;
                            }
                            p.a((short) 33, bArr, length);
                            int i15 = length + 2;
                            p.a((short) -32767, bArr, i15);
                            int i16 = i15 + 2;
                            p.a(120, bArr, i16);
                            int i17 = i16 + 4;
                            byte length5 = (byte) ((i6 == 0 ? this.f163a.length() + 1 : 0) + 8);
                            int i18 = i17 + 1;
                            bArr[i17] = (byte) (length5 >> 8);
                            bArr[i18] = length5;
                            int i19 = i18 + 1 + 4;
                            p.a((short) this.f171a[i10].b, bArr, i19);
                            int i20 = i19 + 2;
                            if (i6 == 0) {
                                int i21 = i20 + 1;
                                bArr[i20] = (byte) this.f163a.length();
                                System.arraycopy(this.f163a.getBytes(), 0, bArr, i21, this.f163a.length());
                                int length6 = i21 + this.f163a.length();
                                int i22 = length6 + 1;
                                bArr[length6] = -64;
                                i2 = i22 + 1;
                                bArr[i22] = (byte) length2;
                                i9 = i20;
                            } else {
                                int i23 = i20 + 1;
                                bArr[i20] = -64;
                                i2 = i23 + 1;
                                bArr[i23] = (byte) i9;
                            }
                            p.a((short) (49152 | iArr[i10]), bArr, i2);
                            int i24 = i2 + 2;
                            p.a((short) 16, bArr, i24);
                            int i25 = i24 + 2;
                            p.a((short) -32767, bArr, i25);
                            int i26 = i25 + 2;
                            p.a(1800, bArr, i26);
                            int i27 = i26 + 4;
                            p.a((short) 1, bArr, i27);
                            int i28 = i27 + 2;
                            int i29 = i28 + 1;
                            bArr[i28] = 0;
                            if (i6 == 0) {
                                int i30 = i29 + 1;
                                bArr[i29] = 9;
                                System.arraycopy("_services".getBytes(), 0, bArr, i30, 9);
                                int i31 = i30 + 9;
                                int i32 = i31 + 1;
                                bArr[i31] = 7;
                                System.arraycopy("_dns-sd".getBytes(), 0, bArr, i32, 7);
                                int i33 = i32 + 7;
                                p.a((short) (49152 | length3), bArr, i33);
                                int i34 = i33 + 2;
                                p.a((short) 12, bArr, i34);
                                int i35 = i34 + 2;
                                p.a((short) 1, bArr, i35);
                                int i36 = i35 + 2;
                                p.a(1800, bArr, i36);
                                int i37 = i36 + 4;
                                p.a((short) 2, bArr, i37);
                                int i38 = i37 + 2;
                                p.a((short) (49152 | i8), bArr, i38);
                                i29 = i38 + 2;
                                i6++;
                            }
                            p.a((short) (49152 | i8), bArr, i29);
                            int i39 = i29 + 2;
                            p.a((short) 12, bArr, i39);
                            int i40 = i39 + 2;
                            p.a((short) 1, bArr, i40);
                            int i41 = i40 + 2;
                            p.a(1800, bArr, i41);
                            int i42 = i41 + 4;
                            p.a((short) 2, bArr, i42);
                            int i43 = i42 + 2;
                            p.a((short) (49152 | iArr[i10]), bArr, i43);
                            i7 = i43 + 2;
                        }
                    }
                    if (this.f194f) {
                        p.a((short) (49152 | i9), bArr, i7);
                        int i44 = i7 + 2;
                        p.a((short) 1, bArr, i44);
                        int i45 = i44 + 2;
                        p.a((short) -32767, bArr, i45);
                        int i46 = i45 + 2;
                        p.a(120, bArr, i46);
                        int i47 = i46 + 4 + 1;
                        int i48 = i47 + 1;
                        bArr[i47] = 4;
                        System.arraycopy(this.f170a, 0, bArr, i48, 4);
                        i7 = i48 + 4;
                    }
                    p.a((short) (49152 | i9), bArr, i7);
                    int i49 = i7 + 2;
                    p.a((short) 47, bArr, i49);
                    int i50 = i49 + 2;
                    p.a((short) -32767, bArr, i50);
                    int i51 = i50 + 2;
                    p.a(120, bArr, i51);
                    int i52 = i51 + 4;
                    p.a((short) 8, bArr, i52);
                    int i53 = i52 + 2;
                    p.a((short) (49152 | i9), bArr, i53);
                    int i54 = i53 + 2;
                    p.a((short) 4, bArr, i54);
                    int i55 = i54 + 2;
                    p.a(1073741832, bArr, i55);
                    int i56 = i55 + 4;
                    p.a((short) -16372, bArr, i56);
                    int i57 = i56 + 2;
                    p.a((short) 47, bArr, i57);
                    int i58 = i57 + 2;
                    p.a((short) -32767, bArr, i58);
                    int i59 = i58 + 2;
                    p.a(120, bArr, i59);
                    int i60 = i59 + 4;
                    p.a((short) 9, bArr, i60);
                    int i61 = i60 + 2;
                    p.a((short) -16372, bArr, i61);
                    int i62 = i61 + 2;
                    int i63 = i62 + 1;
                    bArr[i62] = 0;
                    int i64 = i63 + 1;
                    bArr[i63] = 5;
                    int i65 = i64 + 1;
                    bArr[i64] = 0;
                    p.a(8388672, bArr, i65);
                    i = i65 + 4;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        return i;
    }

    private int b(String str, byte[] bArr) {
        int i;
        try {
            if (this.f171a == null || this.f171a.length <= 0) {
                return 0;
            }
            int i2 = 0;
            for (int i3 = 0; i3 < this.f171a.length; i3++) {
                if (this.f171a[i3].f196b.equalsIgnoreCase(str)) {
                    i2++;
                }
            }
            for (int i4 = 0; i4 < bArr.length; i4++) {
                bArr[i4] = 0;
            }
            p.a((short) -31744, bArr, 2);
            p.a((short) i2, bArr, 6);
            int i5 = (i2 * 3) + 1;
            if (this.f194f) {
                i5++;
            }
            p.a((short) i5, bArr, 10);
            int i6 = 12;
            int[] iArr = new int[this.f171a.length];
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            for (int i10 = 0; i10 < this.f171a.length; i10++) {
                if (this.f171a[i10].f196b.equalsIgnoreCase(str)) {
                    if (i7 == 0) {
                        int i11 = i6 + 1;
                        bArr[i6] = (byte) this.f171a[i10].f196b.length();
                        System.arraycopy(this.f171a[i10].f196b.getBytes(), 0, bArr, i11, this.f171a[i10].f196b.length());
                        int length = i11 + this.f171a[i10].f196b.length();
                        int i12 = length + 1;
                        bArr[length] = 4;
                        System.arraycopy("_udp".getBytes(), 0, bArr, i12, 4);
                        i8 = i12 + 4;
                        int i13 = i8 + 1;
                        bArr[i8] = 5;
                        System.arraycopy("local".getBytes(), 0, bArr, i13, 5);
                        i7++;
                        int i14 = i6;
                        i = i13 + 5 + 1;
                        i9 = i14;
                    } else {
                        p.a((short) (49152 | i9), bArr, i6);
                        i = i6 + 2;
                    }
                    p.a((short) 12, bArr, i);
                    int i15 = i + 2;
                    p.a((short) 1, bArr, i15);
                    int i16 = i15 + 2;
                    p.a(4500, bArr, i16);
                    int i17 = i16 + 4;
                    p.a((short) (this.f171a[i10].f195a.length() + 1 + 2), bArr, i17);
                    int i18 = i17 + 2;
                    iArr[i10] = i18;
                    int i19 = i18 + 1;
                    bArr[i18] = (byte) this.f171a[i10].f195a.length();
                    System.arraycopy(this.f171a[i10].f195a.getBytes(), 0, bArr, i19, this.f171a[i10].f195a.length());
                    int length2 = this.f171a[i10].f195a.length() + i19;
                    p.a((short) (49152 | i9), bArr, length2);
                    i6 = length2 + 2;
                }
            }
            int i20 = i6 + 1;
            bArr[i6] = (byte) this.f163a.length();
            System.arraycopy(this.f163a.getBytes(), 0, bArr, i20, this.f163a.length());
            int length3 = i20 + this.f163a.length();
            p.a((short) (i8 | 49152), bArr, length3);
            int i21 = length3 + 2;
            p.a((short) 1, bArr, i21);
            int i22 = i21 + 2;
            p.a((short) -32767, bArr, i22);
            int i23 = i22 + 2;
            p.a(120, bArr, i23);
            int i24 = i23 + 4;
            p.a((short) 4, bArr, i24);
            int i25 = i24 + 2;
            System.arraycopy(this.f170a, 0, bArr, i25, 4);
            int i26 = i25 + 4;
            for (int i27 = 0; i27 < this.f171a.length; i27++) {
                if (this.f171a[i27].f196b.equalsIgnoreCase(str)) {
                    p.a((short) (iArr[i27] | 49152), bArr, i26);
                    int i28 = i26 + 2;
                    p.a((short) 33, bArr, i28);
                    int i29 = i28 + 2;
                    p.a((short) -32767, bArr, i29);
                    int i30 = i29 + 2;
                    p.a(120, bArr, i30);
                    int i31 = i30 + 4;
                    p.a((short) 8, bArr, i31);
                    int i32 = i31 + 2 + 4;
                    p.a((short) this.f171a[i27].b, bArr, i32);
                    int i33 = i32 + 2;
                    p.a((short) (49152 | i6), bArr, i33);
                    int i34 = i33 + 2;
                    p.a((short) (iArr[i27] | 49152), bArr, i34);
                    int i35 = i34 + 2;
                    p.a((short) 16, bArr, i35);
                    int i36 = i35 + 2;
                    p.a((short) -32767, bArr, i36);
                    int i37 = i36 + 2;
                    p.a(4500, bArr, i37);
                    int i38 = i37 + 4;
                    p.a((short) 1, bArr, i38);
                    i26 = i38 + 2 + 1;
                }
            }
            p.a((short) (49152 | i6), bArr, i26);
            int i39 = i26 + 2;
            p.a((short) 47, bArr, i39);
            int i40 = i39 + 2;
            p.a((short) -32767, bArr, i40);
            int i41 = i40 + 2;
            p.a(120, bArr, i41);
            int i42 = i41 + 4;
            p.a((short) 8, bArr, i42);
            int i43 = i42 + 2;
            p.a((short) (49152 | i6), bArr, i43);
            int i44 = i43 + 2;
            p.a((short) 4, bArr, i44);
            int i45 = i44 + 2;
            p.a((short) 16384, bArr, i45);
            int i46 = i45 + 2;
            p.a((short) 8, bArr, i46);
            int i47 = i46 + 2;
            for (int i48 = 0; i48 < this.f171a.length; i48++) {
                if (this.f171a[i48].f196b.equalsIgnoreCase(str)) {
                    p.a((short) (iArr[i48] | 49152), bArr, i47);
                    int i49 = i47 + 2;
                    p.a((short) 47, bArr, i49);
                    int i50 = i49 + 2;
                    p.a((short) -32767, bArr, i50);
                    int i51 = i50 + 2;
                    p.a(120, bArr, i51);
                    int i52 = i51 + 4;
                    p.a((short) 9, bArr, i52);
                    int i53 = i52 + 2;
                    p.a((short) (iArr[i48] | 49152), bArr, i53);
                    int i54 = i53 + 2;
                    p.a((short) 5, bArr, i54);
                    int i55 = i54 + 2 + 1;
                    p.a((short) 128, bArr, i55);
                    int i56 = i55 + 2;
                    p.a((short) 64, bArr, i56);
                    i47 = i56 + 2;
                }
            }
            return i47;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int b(int i, byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = 0;
        }
        p.a((short) -31744, bArr, 2);
        p.a((short) 1, bArr, 6);
        bArr[12] = (byte) this.f163a.length();
        System.arraycopy(this.f163a.getBytes(), 0, bArr, 13, this.f163a.length());
        int length = this.f163a.length() + 13;
        int i3 = length + 1;
        bArr[length] = 5;
        System.arraycopy("local".getBytes(), 0, bArr, i3, 5);
        int i4 = i3 + 5 + 1;
        p.a((short) i, bArr, i4);
        int i5 = i4 + 2;
        p.a((short) -32767, bArr, i5);
        int i6 = i5 + 2;
        p.a(120, bArr, i6);
        int length2 = i6 + 4;
        if (i == 1) {
            p.a((short) 4, bArr, length2);
            int i7 = length2 + 2;
            System.arraycopy(this.f170a, 0, bArr, i7, 4);
            length2 = i7 + 4;
        } else if (i == 28) {
            if (this.f180b == null) {
                return 0;
            }
            p.a((short) this.f180b.length, bArr, length2);
            int i8 = length2 + 2;
            System.arraycopy(this.f180b, 0, bArr, i8, this.f180b.length);
            length2 = i8 + this.f180b.length;
        }
        return length2;
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    private byte[] m111b(String str) {
        int i;
        int i2 = 0;
        while (i2 < this.f171a.length) {
            if (!this.f171a[i2].f195a.equalsIgnoreCase(str) && (this.f171a[i2].f195a.indexOf(".") == -1 || !this.f171a[i2].f195a.replace(".", "_").equalsIgnoreCase(str))) {
                i2++;
            } else {
                i = this.f171a[i2].b;
                break;
            }
        }
        i2 = -1;
        i = 0;
        if (i2 < 0) {
            return null;
        }
        byte[] bArr = new byte[256];
        p.a((short) -31744, bArr, 2);
        p.a((short) 1, bArr, 6);
        p.a((short) 1, bArr, 10);
        bArr[12] = (byte) str.length();
        System.arraycopy(str.getBytes(), 0, bArr, 13, str.length());
        int length = str.length() + 13;
        int i3 = length + 1;
        bArr[length] = 11;
        System.arraycopy(this.f171a[i2].f196b.getBytes(), 0, bArr, i3, 11);
        int i4 = i3 + 11;
        int i5 = i4 + 1;
        bArr[i4] = 4;
        System.arraycopy("_udp".getBytes(), 0, bArr, i5, 4);
        int i6 = i5 + 4;
        int i7 = i6 + 1;
        bArr[i6] = 5;
        System.arraycopy("local".getBytes(), 0, bArr, i7, 5);
        int i8 = i7 + 5 + 1;
        p.a((short) 33, bArr, i8);
        int i9 = i8 + 2;
        p.a((short) -32767, bArr, i9);
        int i10 = i9 + 2;
        p.a(120, bArr, i10);
        int i11 = i10 + 4;
        p.a((short) (this.f163a.length() + 9), bArr, i11);
        int i12 = i11 + 2 + 4;
        p.a((short) i, bArr, i12);
        int i13 = i12 + 2;
        byte b2 = (byte) i13;
        int i14 = i13 + 1;
        bArr[i13] = (byte) this.f163a.length();
        System.arraycopy(this.f163a.getBytes(), 0, bArr, i14, this.f163a.length());
        int length2 = this.f163a.length() + i14;
        p.a((short) (i6 | 49152), bArr, length2);
        int i15 = length2 + 2;
        p.a((short) (49152 | b2), bArr, i15);
        int i16 = i15 + 2;
        p.a((short) 1, bArr, i16);
        int i17 = i16 + 2;
        p.a((short) -32767, bArr, i17);
        int i18 = i17 + 2;
        p.a(120, bArr, i18);
        int i19 = i18 + 4;
        p.a((short) 4, bArr, i19);
        int i20 = i19 + 2;
        System.arraycopy(this.f170a, 0, bArr, i20, 4);
        int i21 = i20 + 4;
        byte[] bArr2 = new byte[i21];
        System.arraycopy(bArr, 0, bArr2, 0, i21);
        return bArr2;
    }

    private synchronized int b(String str, String str2, String str3, byte[] bArr) {
        int iA = 0;
        synchronized (this) {
            try {
                if (this.f171a != null && this.f171a.length > 0) {
                    int i = 0;
                    while (true) {
                        if (i >= this.f171a.length) {
                            i = -1;
                            break;
                        }
                        if (this.f171a[i].f195a.equalsIgnoreCase(str) || (this.f171a[i].f195a.indexOf(".") != -1 && this.f171a[i].f195a.replace(".", "_").equalsIgnoreCase(str))) {
                            break;
                        }
                        i++;
                    }
                    if (i >= 0) {
                        iA = a(i, str2, str3, bArr);
                    }
                }
            } catch (Exception e) {
            }
        }
        return iA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized int a(int i, String str, String str2, byte[] bArr) {
        int i2 = 0;
        synchronized (this) {
            try {
                int i3 = this.f171a[i].b;
                String strReplace = this.f171a[i].f195a;
                String str3 = this.f171a[i].f196b;
                for (int i4 = 0; i4 < bArr.length; i4++) {
                    bArr[i4] = 0;
                }
                p.a((short) -31744, bArr, 2);
                p.a((short) 4, bArr, 6);
                p.a((short) (this.f194f ? 3 : 2), bArr, 10);
                if (strReplace.indexOf(".") != -1) {
                    strReplace = strReplace.replace(".", "_");
                }
                bArr[12] = (byte) strReplace.length();
                System.arraycopy(strReplace.getBytes(), 0, bArr, 13, strReplace.length());
                int length = strReplace.length() + 13;
                int i5 = length + 1;
                bArr[length] = (byte) str3.length();
                System.arraycopy(str3.getBytes(), 0, bArr, i5, str3.length());
                int length2 = i5 + str3.length();
                int i6 = length2 + 1;
                bArr[length2] = (byte) str.length();
                System.arraycopy(str.getBytes(), 0, bArr, i6, str.length());
                int length3 = i6 + str.length();
                int i7 = length3 + 1;
                bArr[length3] = (byte) str2.length();
                System.arraycopy(str2.getBytes(), 0, bArr, i7, str2.length());
                int length4 = i7 + str2.length() + 1;
                p.a((short) 16, bArr, length4);
                int i8 = length4 + 2;
                int i9 = i8 + 1;
                bArr[i8] = -128;
                int i10 = i9 + 1;
                bArr[i9] = 1;
                p.a(9000, bArr, i10);
                int i11 = i10 + 4;
                int i12 = i11 + 1;
                bArr[i11] = 0;
                int i13 = i12 + 1;
                bArr[i12] = 1;
                int i14 = i13 + 1;
                bArr[i13] = 0;
                int i15 = i14 + 1;
                bArr[i14] = 9;
                System.arraycopy("_services".getBytes(), 0, bArr, i15, 9);
                int i16 = i15 + 9;
                int i17 = i16 + 1;
                bArr[i16] = 7;
                System.arraycopy("_dns-sd".getBytes(), 0, bArr, i17, 7);
                int i18 = i17 + 7;
                p.a((short) (length2 | 49152), bArr, i18);
                int i19 = i18 + 2;
                p.a((short) 12, bArr, i19);
                int i20 = i19 + 2;
                p.a((short) 1, bArr, i20);
                int i21 = i20 + 2;
                p.a(1800, bArr, i21);
                int i22 = i21 + 4;
                p.a((short) 2, bArr, i22);
                int i23 = i22 + 2;
                p.a((short) (49152 | length), bArr, i23);
                int i24 = i23 + 2;
                p.a((short) (length | 49152), bArr, i24);
                int i25 = i24 + 2;
                p.a((short) 12, bArr, i25);
                int i26 = i25 + 2;
                p.a((short) 1, bArr, i26);
                int i27 = i26 + 2;
                p.a(4500, bArr, i27);
                int i28 = i27 + 4;
                p.a((short) 2, bArr, i28);
                int i29 = i28 + 2;
                p.a((short) -16372, bArr, i29);
                int i30 = i29 + 2;
                p.a((short) -16372, bArr, i30);
                int i31 = i30 + 2;
                p.a((short) 33, bArr, i31);
                int i32 = i31 + 2;
                p.a((short) -32767, bArr, i32);
                int i33 = i32 + 2;
                p.a(4500, bArr, i33);
                int i34 = i33 + 4;
                p.a((short) (this.f163a.length() + 9), bArr, i34);
                int i35 = i34 + 2;
                p.a(0, bArr, i35);
                int i36 = i35 + 4;
                p.a((short) i3, bArr, i36);
                int i37 = i36 + 2;
                int i38 = i37 + 1;
                bArr[i37] = (byte) this.f163a.length();
                System.arraycopy(this.f163a.getBytes(), 0, bArr, i38, this.f163a.length());
                int length5 = i38 + this.f163a.length();
                int i39 = length5 + 1;
                bArr[length5] = -64;
                int i40 = i39 + 1;
                bArr[i39] = (byte) length3;
                if (this.f194f) {
                    p.a((short) (49152 | i37), bArr, i40);
                    int i41 = i40 + 2;
                    p.a((short) 1, bArr, i41);
                    int i42 = i41 + 2;
                    p.a((short) -32767, bArr, i42);
                    int i43 = i42 + 2;
                    p.a(120, bArr, i43);
                    int i44 = i43 + 4 + 1;
                    int i45 = i44 + 1;
                    bArr[i44] = 4;
                    System.arraycopy(this.f170a, 0, bArr, i45, 4);
                    i40 = i45 + 4;
                }
                p.a((short) (49152 | i37), bArr, i40);
                int i46 = i40 + 2;
                p.a((short) 47, bArr, i46);
                int i47 = i46 + 2;
                p.a((short) -32767, bArr, i47);
                int i48 = i47 + 2;
                p.a(120, bArr, i48);
                int i49 = i48 + 4;
                p.a((short) 8, bArr, i49);
                int i50 = i49 + 2;
                p.a((short) (i37 | 49152), bArr, i50);
                int i51 = i50 + 2;
                p.a((short) 4, bArr, i51);
                int i52 = i51 + 2;
                p.a(1073741832, bArr, i52);
                int i53 = i52 + 4;
                p.a((short) -16372, bArr, i53);
                int i54 = i53 + 2;
                p.a((short) 47, bArr, i54);
                int i55 = i54 + 2;
                p.a((short) -32767, bArr, i55);
                int i56 = i55 + 2;
                p.a(4500, bArr, i56);
                int i57 = i56 + 4;
                p.a((short) 9, bArr, i57);
                int i58 = i57 + 2;
                p.a((short) -16372, bArr, i58);
                int i59 = i58 + 2;
                int i60 = i59 + 1;
                bArr[i59] = 0;
                int i61 = i60 + 1;
                bArr[i60] = 5;
                int i62 = i61 + 1;
                bArr[i61] = 0;
                p.a(8388672, bArr, i62);
                i2 = i62 + 4;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return i2;
    }

    private int c(String str, String str2, String str3, byte[] bArr) {
        if (this.f171a == null || this.f171a.length <= 0) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.f171a.length; i2++) {
            if (this.f171a[i2].f196b.equalsIgnoreCase(str)) {
                i++;
            }
        }
        if (i == 0) {
            return 0;
        }
        for (int i3 = 0; i3 < 256; i3++) {
            bArr[i3] = 0;
        }
        p.a((short) -31744, bArr, 2);
        p.a((short) 1, bArr, 6);
        int iA = a(bArr, "_dns-sd", a(bArr, "_services", 12));
        int iA2 = a(bArr, str3, a(bArr, str2, iA)) + 1;
        p.a((short) 12, bArr, iA2);
        int i4 = iA2 + 2;
        p.a((short) 1, bArr, i4);
        int i5 = i4 + 2;
        p.a(4500, bArr, i5);
        int i6 = i5 + 4;
        p.a((short) (str.length() + 3), bArr, i6);
        int iA3 = a(bArr, str, i6 + 2);
        p.a((short) (iA | 49152), bArr, iA3);
        return iA3 + 2;
    }

    private byte[] a(String str, String str2, String str3, String str4) {
        try {
            if (this.f163a == null) {
                try {
                    this.f163a = InetAddress.getLocalHost().getHostName();
                } catch (UnknownHostException e) {
                }
                if (this.f163a == null || this.f163a.indexOf("127.0.0.1") != -1 || this.f163a.indexOf("localhost") != -1) {
                    this.f163a = NMJConfig.a(false).replace(".", "_");
                }
            }
            if (str4.indexOf(".") != -1) {
                str4 = str4.replace(".", "_");
            }
            byte[] bArr = new byte[128];
            p.a((short) -31744, bArr, 2);
            p.a((short) 1, bArr, 6);
            bArr[12] = (byte) str.length();
            System.arraycopy(str.getBytes(), 0, bArr, 13, str.length());
            int length = str.length() + 13;
            int i = length + 1;
            bArr[length] = (byte) str2.length();
            System.arraycopy(str2.getBytes(), 0, bArr, i, str2.length());
            int length2 = str2.length() + i;
            int i2 = length2 + 1;
            bArr[length2] = (byte) str3.length();
            System.arraycopy(str3.getBytes(), 0, bArr, i2, str3.length());
            int length3 = str3.length() + i2 + 1;
            p.a((short) 12, bArr, length3);
            int i3 = length3 + 2;
            p.a((short) 1, bArr, i3);
            int i4 = i3 + 2;
            p.a(0, bArr, i4);
            int i5 = i4 + 4;
            p.a((byte) (str4.length() + 2 + 1), bArr, i5);
            int i6 = i5 + 2;
            int i7 = i6 + 1;
            bArr[i6] = (byte) str4.length();
            System.arraycopy(str4.getBytes(), 0, bArr, i7, str4.length());
            int length4 = str4.length() + i7;
            p.a((short) -16372, bArr, length4);
            int i8 = length4 + 2;
            byte[] bArr2 = new byte[i8];
            System.arraycopy(bArr, 0, bArr2, 0, i8);
            return bArr2;
        } catch (Exception e2) {
            return null;
        }
    }

    private static String a(byte[] bArr, int i) {
        int i2 = (bArr[i + 1] & 255) | ((bArr[i] & 47) << 8);
        if (i2 >= bArr.length) {
            return de.bassapps.launchbuttonsP.BuildConfig.FLAVOR;
        }
        return new String(bArr, i2 + 1, bArr[i2] & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(18:155|(2:704|156)|(14:158|(8:162|(3:164|(1:166)|167)(1:663)|168|692|169|170|(4:172|678|173|(4:175|719|176|(2:178|179)(1:661))(1:662))(2:285|286)|180)(1:664)|181|702|182|(3:184|185|(3:187|696|188)(1:287))(1:288)|(3:190|(1:192)|193)(1:660)|194|195|(3:197|(3:199|200|(2:202|203)(1:289))(1:290)|(3:205|(1:207)|208)(1:658))(1:659)|209|(7:211|212|(3:214|215|216)(1:291)|684|217|(1:219)|220)(1:657)|(6:723|222|(1:656)(1:226)|700|227|(1:655)(1:231))(0)|284)(2:293|294)|706|232|(1:234)|235|(1:237)|238|688|239|240|686|241|(1:243)|244|(9:246|247|694|248|249|(8:251|668|252|253|672|254|255|(1:257)(1:654))(1:295)|258|(3:260|(1:262)|263)(1:653)|(5:265|(1:(2:690|301))(1:268)|269|(2:271|272)(1:652)|(1:753)(3:674|282|(2:415|(4:419|(1:421)(1:431)|422|(2:428|756)(1:755))(2:432|(2:434|759)(1:757)))(1:754)))(0))(3:305|(5:(7:308|(1:310)|311|(1:313)|(1:315)|(1:317)|318)(1:651)|319|(2:321|(2:323|324)(1:650))(10:336|713|337|338|339|(2:(1:342)(1:648)|343)(1:649)|(1:347)|348|(1:350)|351)|325|(1:647)(3:717|329|(4:331|(1:333)|334|335)(0)))(2:352|(2:356|(14:358|359|360|361|362|363|364|365|366|(1:368)|711|369|(2:373|(1:375))|(4:377|(2:381|378)|760|380)(1:387))(6:388|(8:399|400|401|402|403|(2:405|762)(1:763)|406|389)|761|391|(1:393)|(4:395|(2:407|396)|764|398)(1:646)))(5:408|(1:410)|411|(1:413)|414))|(2:278|751)(2:278|751))|284) */
    /* JADX WARN: Can't wrap try/catch for region: R(19:155|704|156|(14:158|(8:162|(3:164|(1:166)|167)(1:663)|168|692|169|170|(4:172|678|173|(4:175|719|176|(2:178|179)(1:661))(1:662))(2:285|286)|180)(1:664)|181|702|182|(3:184|185|(3:187|696|188)(1:287))(1:288)|(3:190|(1:192)|193)(1:660)|194|195|(3:197|(3:199|200|(2:202|203)(1:289))(1:290)|(3:205|(1:207)|208)(1:658))(1:659)|209|(7:211|212|(3:214|215|216)(1:291)|684|217|(1:219)|220)(1:657)|(6:723|222|(1:656)(1:226)|700|227|(1:655)(1:231))(0)|284)(2:293|294)|706|232|(1:234)|235|(1:237)|238|688|239|240|686|241|(1:243)|244|(9:246|247|694|248|249|(8:251|668|252|253|672|254|255|(1:257)(1:654))(1:295)|258|(3:260|(1:262)|263)(1:653)|(5:265|(1:(2:690|301))(1:268)|269|(2:271|272)(1:652)|(1:753)(3:674|282|(2:415|(4:419|(1:421)(1:431)|422|(2:428|756)(1:755))(2:432|(2:434|759)(1:757)))(1:754)))(0))(3:305|(5:(7:308|(1:310)|311|(1:313)|(1:315)|(1:317)|318)(1:651)|319|(2:321|(2:323|324)(1:650))(10:336|713|337|338|339|(2:(1:342)(1:648)|343)(1:649)|(1:347)|348|(1:350)|351)|325|(1:647)(3:717|329|(4:331|(1:333)|334|335)(0)))(2:352|(2:356|(14:358|359|360|361|362|363|364|365|366|(1:368)|711|369|(2:373|(1:375))|(4:377|(2:381|378)|760|380)(1:387))(6:388|(8:399|400|401|402|403|(2:405|762)(1:763)|406|389)|761|391|(1:393)|(4:395|(2:407|396)|764|398)(1:646)))(5:408|(1:410)|411|(1:413)|414))|(2:278|751)(2:278|751))|284) */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x09b2, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:384:0x09b3, code lost:
    
        r7 = r12;
        r12 = r13;
        r13 = r4;
        r4 = r11;
        r11 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:629:0x10d5, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:630:0x10d6, code lost:
    
        r13 = r4;
        r7 = r12;
        r4 = r11;
        r12 = r10;
        r11 = r15;
        r10 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:631:0x10de, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:632:0x10df, code lost:
    
        r7 = r12;
        r10 = r14;
        r12 = r13;
        r13 = r4;
        r4 = r11;
        r11 = r15;
     */
    /* JADX WARN: Removed duplicated region for block: B:647:0x1126  */
    /* JADX WARN: Removed duplicated region for block: B:652:0x1143  */
    /* JADX WARN: Removed duplicated region for block: B:656:0x1162  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized int a(byte[] r31, int r32, boolean r33, java.net.SocketAddress r34) {
        /*
            Method dump skipped, instruction units count: 4532
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.h.a(byte[], int, boolean, java.net.SocketAddress):int");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:186:0x063c A[PHI: r2
  0x063c: PHI (r2v71 int) = (r2v27 int), (r2v26 int) binds: [B:166:0x0556, B:180:0x0614] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0219  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(byte[] r20, int r21, java.net.SocketAddress r22) {
        /*
            Method dump skipped, instruction units count: 1662
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.h.a(byte[], int, java.net.SocketAddress):void");
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private boolean m106a(String str) {
        c(3);
        for (int i = 0; i < this.f171a.length; i++) {
            if (this.f171a[i].f195a.equalsIgnoreCase(str) || (this.f171a[i].f195a.indexOf(".") != -1 && this.f171a[i].f195a.replace(".", "_").equalsIgnoreCase(str))) {
                return true;
            }
        }
        return false;
    }

    private static boolean b(String str) {
        return str.equals("_apple-midi") || str.equals("_ws-midi");
    }

    protected final void d() {
        try {
            c(3);
            if (this.f169a) {
                this.a = 0;
                p.logln(2, "DNS (" + this.f191e + ") - channel opened, nr. sessions: " + this.f171a.length);
                for (int i = 0; i < this.f171a.length; i++) {
                    p.logln(2, "-> " + NMJConfig.getName(this.f171a[i].a));
                    m104a(this.f190d, a(i, this.f190d));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected final void b(int i) {
        boolean z = false;
        if (this.f171a != null) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.f171a.length) {
                    break;
                }
                if (this.f171a[i2].a == i) {
                    z = true;
                    break;
                }
                i2++;
            }
        }
        if (z) {
            if (this.f != i) {
                p.logln(2, "DNS (" + this.f191e + ") - channel unlinked: " + i + " name " + NMJConfig.getName(i));
                this.f = i;
            } else {
                this.f = -1;
            }
            a(i);
            c(3);
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected final void m113a(int i, boolean z) {
        g gVar = new g("_apple-midi", -1);
        gVar.a(NMJConfig.m15a(i));
        gVar.b = NMJConfig.getLocalPort(i);
        if (!z) {
            for (g gVar2 : this.f188d) {
                if (gVar2.f158a.equalsIgnoreCase(gVar.f158a) && gVar2.b == gVar.b) {
                    return;
                }
            }
            p.logln(2, "Creating session announcement for client " + i);
            gVar.c = NMJConfig.b(false);
            this.f188d.add(gVar);
        } else {
            for (g gVar3 : this.f188d) {
                try {
                    if (gVar3.f158a.equalsIgnoreCase(gVar.f158a) && gVar3.b == gVar.b) {
                        p.logln(2, "Removing session announcement for client " + i);
                        this.f188d.remove(gVar3);
                        break;
                    }
                } catch (Exception e) {
                }
            }
        }
        c(3);
    }

    private synchronized void c(int i) {
        try {
            this.i = false;
            int[] iArrM30a = NMJConfig.m30a(-1, 3, this.e);
            this.f171a = new c[iArrM30a.length + this.f188d.size()];
            for (int i2 = 0; i2 < iArrM30a.length; i2++) {
                this.f171a[i2] = new c(this, iArrM30a[i2], (byte) 0);
            }
            for (int i3 = 0; i3 < this.f188d.size(); i3++) {
                g gVar = this.f188d.get(i3);
                int length = iArrM30a.length + i3;
                if (gVar.f159b.equalsIgnoreCase("_apple-midi")) {
                    int[] iArrM29a = NMJConfig.m29a(0);
                    int i4 = 0;
                    while (true) {
                        if (i4 < iArrM29a.length) {
                            if (!NMJConfig.m15a(iArrM29a[i4]).equalsIgnoreCase(gVar.f158a) || NMJConfig.getLocalPort(iArrM29a[i4]) != gVar.b) {
                                i4++;
                            } else {
                                length = iArrM29a[i4];
                                this.i = true;
                                break;
                            }
                        }
                    }
                }
                this.f171a[iArrM30a.length + i3] = new c(this, length, gVar, (byte) 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private g a(String str) {
        if (str == null) {
            return null;
        }
        for (g gVar : this.f178b) {
            if (gVar.f158a != null && gVar.f158a.equalsIgnoreCase(str)) {
                return gVar;
            }
        }
        return null;
    }

    class c {
        int a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        String f195a;
        int b;

        /* JADX INFO: renamed from: b, reason: collision with other field name */
        String f196b;

        private c(h hVar, int i) {
            this.a = i;
            this.f195a = NMJConfig.getName(i);
            NMJConfig.getIP(i);
            this.b = NMJConfig.getLocalPort(i);
            this.f196b = "_apple-midi";
            if (NMJConfig.getMode(this.a) == 6) {
                this.f196b = "_ws-midi";
            }
        }

        /* synthetic */ c(h hVar, int i, byte b) {
            this(hVar, i);
        }

        private c(h hVar, int i, g gVar) {
            this.a = i;
            this.f195a = gVar.f158a;
            String str = gVar.c;
            this.b = gVar.b;
            this.f196b = gVar.f159b;
        }

        /* synthetic */ c(h hVar, int i, g gVar, byte b) {
            this(hVar, i, gVar);
        }
    }

    class d {
        private d(h hVar) {
        }

        /* synthetic */ d(h hVar, byte b) {
            this(hVar);
        }

        public final void a() {
            synchronized (this) {
                notify();
            }
        }
    }
}
