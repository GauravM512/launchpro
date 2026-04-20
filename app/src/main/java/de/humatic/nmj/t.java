package de.humatic.nmj;

import android.os.Build;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.TimerTask;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
final class t extends w {
    private int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private long f299a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String f300a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private DatagramPacket f301a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private DatagramSocket f302a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private TimerTask f303a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Vector<s> f304a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[] f305a;
    private int b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private long f306b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private DatagramPacket f307b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private DatagramSocket f308b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private boolean f309b;
    private long c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private boolean f310c;
    private long d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private boolean f311d;
    private int e;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private boolean f312e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    t(int i, n nVar) throws Exception {
        super(nVar, i);
        byte b2 = 0;
        this.f309b = true;
        this.a = 4031;
        this.e = -1;
        this.f = -1;
        this.c = 0L;
        this.d = -1L;
        this.f305a = new byte[36];
        this.f304a = new Vector<>();
        String ip = NMJConfig.getIP(this.c);
        this.f310c = ip == null;
        int port = NMJConfig.getPort(this.c);
        if (ip != null && ip.indexOf(":") != -1) {
            port = Integer.parseInt(ip.split(":")[1]);
            ip = ip.split(":")[0];
        }
        this.a = NMJConfig.getLocalPort(this.c);
        if (this.a < 0) {
            this.a = 0;
        }
        NMJConfig.m38b(i, -1);
        try {
            NMJConfig.f29a[i] = 0;
        } catch (Exception e) {
        }
        int networkAdapter = NMJConfig.getNetworkAdapter(this.c);
        p.b();
        this.f312e = (p.b() & 16) != 0;
        this.f311d = (NMJConfig.getFlags(-1) & NMJConfig.RECONNECT) != 0;
        p.logln(2, String.valueOf(this.c) + " - Opening rtp channel " + ip + ":" + (ip == null ? this.a : port) + ", sr: " + this.f311d);
        this.f306b = System.currentTimeMillis();
        this.f300a = this.f310c ? NMJConfig.getName(this.c) : NMJConfig.m15a(this.c);
        if (this.f300a.equalsIgnoreCase("localhost")) {
            try {
                this.f300a = "nmj (" + Build.MODEL + ") Ch_" + (this.c + 1);
            } catch (Exception e2) {
            }
        }
        this.b = (int) (Math.random() * 2.147483647E9d);
        boolean z = (NMJConfig.getFlags(-1) & 4096) != 0;
        if (z && !this.f310c) {
            p.logln(2, String.valueOf(this.c) + " - RTP using fixed local port: " + this.a);
        }
        int i2 = (this.f310c || z) ? this.a : 0;
        this.f308b = p.a(networkAdapter, i2);
        p.logln(2, String.valueOf(this.c) + " - RTP ctrl socket created on: " + this.f308b.getLocalSocketAddress());
        this.f308b.setSoTimeout(5000);
        this.f301a = new DatagramPacket(new byte[64], 64);
        if (!this.f310c) {
            s sVar = new s(this, this.b, this.f306b);
            sVar.a(NMJConfig.getName(this.c));
            sVar.a(0, new InetSocketAddress(ip, port));
            sVar.a(1, new InetSocketAddress(ip, port + 1));
            this.f304a.add(sVar);
        }
        int i3 = (this.f310c || z) ? this.a + 1 : 0;
        this.f302a = p.a(networkAdapter, i3);
        p.logln(2, String.valueOf(this.c) + " - RTP midi socket created on: " + this.f302a.getLocalSocketAddress());
        this.f302a.setSoTimeout(5000);
        this.f307b = new DatagramPacket(new byte[1500], 1500);
        this.f309b = true;
        new Thread(new a(this, this, b2)).start();
        new Thread(new c(this, this, b2)).start();
        NMJConfig.a(this.c, 1, 0);
        if (this.f310c) {
            NMJConfig.a(this.c, 4, 4);
        } else if ((NMJConfig.getFlags(-1) & 8192) != 0) {
            new Thread(new Runnable() { // from class: de.humatic.nmj.t.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        Thread.sleep(1500L);
                        p.logln(2, "Ch. " + t.this.c + " - running delayed autoconnect");
                        t.this.m177a(0);
                    } catch (Exception e3) {
                    }
                }
            }).start();
        } else {
            m177a(0);
        }
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final void mo96a() {
        if (this.f310c || (this.f304a.size() > 0 && this.f304a.get(0).m138a())) {
            try {
                b((s) null);
            } catch (Exception e) {
            }
        }
        this.f309b = false;
        this.f308b.close();
        this.f302a.close();
        p.logln(1, String.valueOf(this.c) + " - RTPSession closed");
    }

    final void c() throws Exception {
        try {
            Thread.currentThread();
            Thread.sleep(100L);
        } catch (InterruptedException e) {
        }
        try {
            this.f304a.get(0).a(this.f310c);
        } catch (Exception e2) {
        }
        this.d = -1L;
        p.logln(4, String.valueOf(this.c) + " - Restarting invTask");
        this.i = 0;
        this.g = 0;
        this.j = 0;
        m177a(0);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final void m177a(int i) throws Exception {
        byte b2 = 0;
        if (i < this.g) {
            p.logln(2, String.valueOf(this.c) + " - Autoconnection: allready connected");
            return;
        }
        this.g = i;
        if (i != 1 || !this.f304a.get(0).m138a()) {
            byte[] bArr = new byte[this.f300a.length() + 17];
            bArr[1] = -1;
            bArr[0] = -1;
            System.arraycopy(new String("IN").getBytes(), 0, bArr, 2, 2);
            bArr[7] = 2;
            p.a(this.b, bArr, 12);
            System.arraycopy(this.f300a.getBytes(), 0, bArr, 16, this.f300a.length());
            if (this.e == -1) {
                this.e = (int) (Math.random() * 2.147483647E9d);
            }
            p.a(this.e, bArr, 8);
            if (i == 0) {
                DatagramPacket datagramPacketA = this.f304a.get(0).a(0);
                datagramPacketA.setData(bArr, 0, bArr.length);
                try {
                    this.f308b.send(datagramPacketA);
                } catch (Exception e) {
                    if (e.toString().toLowerCase().indexOf("closed") != -1) {
                        return;
                    }
                }
                if (this.f == -1) {
                    this.f = (int) (Math.random() * 2.147483647E9d);
                }
            } else {
                p.a(this.f, bArr, 8);
                DatagramPacket datagramPacketA2 = this.f304a.get(0).a(1);
                datagramPacketA2.setData(bArr, 0, bArr.length);
                try {
                    this.f302a.send(datagramPacketA2);
                } catch (Exception e2) {
                    if (e2.toString().toLowerCase().indexOf("closed") != -1) {
                        return;
                    }
                }
            }
            int i2 = this.h;
            this.h = i2 + 1;
            if (i2 > 6 && !this.f311d) {
                this.h = 0;
                NMJConfig.a(this.c, 4, 4096);
            } else {
                this.f303a = new b(this, b2);
                NMJConfig.a(this.f303a, this.h < 4 ? 2000 : 15000, 0);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a8 A[Catch: Exception -> 0x0129, TryCatch #0 {Exception -> 0x0129, blocks: (B:4:0x0003, B:7:0x000c, B:9:0x0020, B:11:0x0024, B:12:0x003d, B:15:0x0091, B:21:0x00ad, B:23:0x00bc, B:24:0x00cd, B:26:0x00db, B:27:0x00ec, B:28:0x00f0, B:30:0x00f7, B:31:0x0108, B:33:0x0116, B:20:0x00a8), top: B:37:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00f0 A[Catch: Exception -> 0x0129, TryCatch #0 {Exception -> 0x0129, blocks: (B:4:0x0003, B:7:0x000c, B:9:0x0020, B:11:0x0024, B:12:0x003d, B:15:0x0091, B:21:0x00ad, B:23:0x00bc, B:24:0x00cd, B:26:0x00db, B:27:0x00ec, B:28:0x00f0, B:30:0x00f7, B:31:0x0108, B:33:0x0116, B:20:0x00a8), top: B:37:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0020 A[Catch: Exception -> 0x0129, TryCatch #0 {Exception -> 0x0129, blocks: (B:4:0x0003, B:7:0x000c, B:9:0x0020, B:11:0x0024, B:12:0x003d, B:15:0x0091, B:21:0x00ad, B:23:0x00bc, B:24:0x00cd, B:26:0x00db, B:27:0x00ec, B:28:0x00f0, B:30:0x00f7, B:31:0x0108, B:33:0x0116, B:20:0x00a8), top: B:37:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(de.humatic.nmj.s r7) {
        /*
            Method dump skipped, instruction units count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.t.b(de.humatic.nmj.s):void");
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    static /* synthetic */ void m173b(t tVar) {
        int iA;
        if (tVar.f304a.size() == 0) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (int size = tVar.f304a.size() - 1; size >= 0; size--) {
            s sVar = tVar.f304a.get(size);
            if (sVar != null && sVar.a() != 0 && sVar.m138a() && ((tVar.f310c || jCurrentTimeMillis - sVar.f257a >= 25000) && (iA = sVar.a(jCurrentTimeMillis)) != 0)) {
                p.logln(6, String.valueOf(tVar.c) + " - RTPClient " + sVar.a() + ", sync pending for " + iA + " msec. Reanim attempts " + sVar.b);
                if (tVar.f310c) {
                    int i = sVar.b;
                    sVar.b = i + 1;
                    if (i >= 3) {
                        p.logln(1, String.valueOf(tVar.c) + " - RTPClient " + sVar.a() + " was lost. Removing");
                        int i2 = tVar.c;
                        sVar.m134a();
                        NMJConfig.b(i2, 128);
                        tVar.a(sVar);
                        if (tVar.f304a.size() == 0) {
                            NMJConfig.m38b(tVar.c, 4);
                        }
                    } else if (tVar.a(sVar, 0, tVar.a(), 0L) < 0) {
                        sVar.b = 3;
                    } else {
                        sVar.a(-1, -1L, jCurrentTimeMillis);
                    }
                } else {
                    int i3 = sVar.b;
                    sVar.b = i3 + 1;
                    if (i3 < 3) {
                        tVar.e();
                    } else {
                        p.logln(1, String.valueOf(tVar.c) + " - Connection lost");
                        NMJConfig.a(tVar.c, 4, 1024);
                        sVar.a(tVar.f310c);
                        if (tVar.f311d) {
                            try {
                                tVar.c();
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        if (this.f304a.size() != 0) {
            this.f305a[8] = 0;
            this.f299a = a();
            p.a(this.f299a, this.f305a, 12);
            p.a(0L, this.f305a, 20);
            p.a(0L, this.f305a, 28);
            long j = this.f299a;
            long j2 = this.c;
            this.c = this.f299a;
            try {
                DatagramPacket datagramPacketA = this.f304a.get(0).a(1);
                datagramPacketA.setData(this.f305a, 0, this.f305a.length);
                this.f302a.send(datagramPacketA);
            } catch (Exception e) {
                if (!this.f309b || e.toString().indexOf("closed") >= 0 || e.toString().indexOf("EINVAL") >= 0) {
                    return;
                }
                p.a(e, de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
            }
        }
    }

    static /* synthetic */ int a(t tVar, s sVar, int i, long j, long j2, long j3) {
        return tVar.a(sVar, i, j, j2);
    }

    private int a(s sVar, int i, long j, long j2) {
        if (this.f332a) {
            return -1;
        }
        switch (i) {
            case 0:
                this.f305a[8] = 1;
                p.a(j, this.f305a, 12);
                p.a(a(), this.f305a, 20);
                p.a(0L, this.f305a, 28);
                try {
                    DatagramPacket datagramPacketA = sVar.a(1);
                    datagramPacketA.setData(this.f305a, 0, this.f305a.length);
                    this.f302a.send(datagramPacketA);
                } catch (Exception e) {
                    return -1;
                }
                break;
            case 1:
                for (int i2 = 20; i2 < 32; i2++) {
                    this.f305a[i2] = 0;
                }
                this.f305a[8] = 2;
                p.a(j2, this.f305a, 20);
                this.f299a = a();
                p.a(this.f299a, this.f305a, 28);
                if (this.f312e) {
                    p.a("write sync ", this.f305a);
                }
                try {
                    DatagramPacket datagramPacketA2 = sVar.a(1);
                    datagramPacketA2.setData(this.f305a, 0, this.f305a.length);
                    this.f302a.send(datagramPacketA2);
                    final int i3 = sVar.c > 8 ? 10000 : 1000;
                    new Thread(new Runnable() { // from class: de.humatic.nmj.t.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (i3 > 0) {
                                try {
                                    Thread.sleep(i3);
                                } catch (InterruptedException e2) {
                                }
                            }
                            t.this.e();
                        }
                    }).start();
                } catch (Exception e2) {
                    return -1;
                }
                break;
        }
        return 0;
    }

    private long a() {
        return (System.currentTimeMillis() - this.f306b) * 10;
    }

    class a extends Thread {
        private t a;

        private a(t tVar) {
            this.a = tVar;
        }

        /* synthetic */ a(t tVar, t tVar2, byte b) {
            this(tVar2);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            while (t.this.f309b) {
                try {
                    try {
                        t.this.f301a.setLength(64);
                        t.this.f308b.receive(t.this.f301a);
                    } catch (Exception e) {
                        if (e.toString().toLowerCase().indexOf("closed") < 0 && e.toString().toLowerCase().indexOf("cancelled") < 0 && e.toString().toLowerCase().indexOf("interrupted") < 0 && e.toString().indexOf("Bad file number") == -1) {
                            e.printStackTrace();
                        }
                    }
                } catch (SocketTimeoutException e2) {
                    t.m173b(t.this);
                }
                if (t.this.f309b) {
                    byte[] data = t.this.f301a.getData();
                    if (data[0] == -1 && data[1] == -1) {
                        String str = new String(data, 2, 2);
                        if (str.equalsIgnoreCase("OK")) {
                            try {
                                t.this.f303a.cancel();
                            } catch (Exception e3) {
                            }
                            ((s) t.this.f304a.get(0)).m137a(p.a(data, 12));
                            ((s) t.this.f304a.get(0)).a(0, true);
                            if (17 < t.this.f301a.getLength()) {
                                p.logln(2, "RTP " + t.this.c + " - ctrl, invitation accepted by " + new String(data, 16, (t.this.f301a.getLength() - 16) - 1).trim());
                            }
                            t.this.h = 0;
                            t.this.i = 1;
                            t.this.m177a(t.this.i);
                            if (((s) t.this.f304a.get(0)).m138a()) {
                                t.this.e();
                            }
                        } else if (str.equalsIgnoreCase("IN")) {
                            p.a(data, 4);
                            t.this.e = p.a(data, 8);
                            int iA = p.a(data, 12);
                            s sVarA = t.this.a(iA);
                            if (sVarA == null) {
                                s sVar = new s(this.a, t.this.b, System.currentTimeMillis());
                                sVar.m137a(iA);
                                sVar.a = t.c(t.this);
                                sVar.a(0, (InetSocketAddress) t.this.f301a.getSocketAddress());
                                if (17 < t.this.f301a.getLength()) {
                                    sVar.a(new String(data, 16, (t.this.f301a.getLength() - 16) - 1));
                                    p.logln(2, String.valueOf(t.this.c) + " - RTP ctrl, connection request from " + sVar.m135a() + " ssrc: " + iA);
                                } else {
                                    p.logln(2, String.valueOf(t.this.c) + " - RTP ctrl, connection request from ssrc: " + iA);
                                }
                                sVar.a(0, true);
                                t.this.f304a.add(sVar);
                                sVarA = sVar;
                            } else {
                                p.logln(2, String.valueOf(t.this.c) + " - RTP client allready connected: " + iA);
                                t tVar = t.this;
                                int i = tVar.j;
                                tVar.j = i + 1;
                                if (i == 10) {
                                    sVarA.a(t.this.f310c);
                                    t.this.f304a.remove(sVarA);
                                    NMJConfig.a(t.this.c, -2147483647, "There seems to be a network problem.\nReceiving repeated RTP invitations.\nThe remote side does not seem to get the replies");
                                    t.this.j = 0;
                                }
                            }
                            byte[] bArr = new byte[t.this.f300a.length() + 17];
                            System.arraycopy(data, 0, bArr, 0, 16);
                            System.arraycopy(new String("OK").getBytes(), 0, bArr, 2, 2);
                            p.a(t.this.b, bArr, 12);
                            System.arraycopy(t.this.f300a.getBytes(), 0, bArr, 16, t.this.f300a.length());
                            DatagramPacket datagramPacketA = sVarA.a(0);
                            datagramPacketA.setData(bArr, 0, bArr.length);
                            t.this.f308b.send(datagramPacketA);
                        } else if (str.equalsIgnoreCase("BY")) {
                            s sVarA2 = t.this.a(p.a(data, 12, 4));
                            if (sVarA2 != null && sVarA2.m138a()) {
                                sVarA2.a(0, false);
                                p.logln(2, String.valueOf(t.this.c) + " - RTP ctrl, connection closed by client: " + sVarA2.m135a());
                                if (!t.this.f310c) {
                                    NMJConfig.a(t.this.c, 4, 128);
                                    if (t.this.f311d) {
                                        t.this.c();
                                    }
                                } else {
                                    t.this.a(sVarA2);
                                }
                            }
                        } else if (str.equalsIgnoreCase("RS")) {
                            s sVarA3 = t.this.a(p.a(data, 4));
                            if (sVarA3 != null) {
                                sVarA3.a(p.m122a(data, 8));
                            }
                        } else if (str.equalsIgnoreCase("CK")) {
                            p.a(5, "sync received on ctrl channel", data, 0, t.this.f307b.getLength());
                        } else if (str.equalsIgnoreCase("NO")) {
                            p.logln(5, String.valueOf(t.this.c) + " - RTP ctrl, connection refused by client ");
                            NMJConfig.a(t.this.c, 4, 2048);
                        } else if (str.equalsIgnoreCase("RL") && t.this.a(p.a(data, 4)) != null) {
                            p.a(data, 8);
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    class c extends Thread {
        private long a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private t f314a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private byte[] f315a;

        private c(t tVar) {
            this.f315a = new byte[12];
            this.f314a = tVar;
            byte[] bArr = t.this.f305a;
            byte[] bArr2 = t.this.f305a;
            byte[] bArr3 = this.f315a;
            this.f315a[1] = -1;
            bArr3[0] = -1;
            bArr2[1] = -1;
            bArr[0] = -1;
            System.arraycopy(new String("CK").getBytes(), 0, t.this.f305a, 2, 2);
            System.arraycopy(new String("RS").getBytes(), 0, this.f315a, 2, 2);
            p.a(t.this.b, t.this.f305a, 4);
            p.a(t.this.b, this.f315a, 4);
            t.this.f305a[8] = 1;
            setPriority(10);
        }

        /* synthetic */ c(t tVar, t tVar2, byte b) {
            this(tVar2);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            s sVar;
            while (t.this.f309b) {
                try {
                    try {
                        t.this.f307b.setLength(1500);
                        t.this.f302a.receive(t.this.f307b);
                    } catch (SocketTimeoutException e) {
                    }
                } catch (Exception e2) {
                    if (t.this.f309b && e2.toString().indexOf("closed") < 0) {
                        e2.printStackTrace();
                    }
                }
                if (t.this.f309b) {
                    byte[] data = t.this.f307b.getData();
                    if (data[0] == -1 && data[1] == -1) {
                        String str = new String(data, 2, 2);
                        if (str.equalsIgnoreCase("OK")) {
                            try {
                                t.this.f303a.cancel();
                            } catch (Exception e3) {
                            }
                            if (17 < t.this.f307b.getLength()) {
                                p.logln(2, "RTP " + t.this.c + " - midi, invitation accepted by " + new String(data, 16, (t.this.f307b.getLength() - 16) - 1).trim());
                            }
                            ((s) t.this.f304a.get(0)).m137a(p.a(data, 12));
                            ((s) t.this.f304a.get(0)).a(1, true);
                            NMJConfig.a(t.this.c, 4, 32);
                            if (((s) t.this.f304a.get(0)).m138a()) {
                                t.this.e();
                            }
                        } else if (str.equalsIgnoreCase("IN")) {
                            p.a(data, 4);
                            t.this.f = p.a(data, 8);
                            int iA = p.a(data, 12);
                            s sVarA = t.this.a(iA);
                            if (sVarA == null) {
                                s sVar2 = new s(this.f314a, t.this.b, System.currentTimeMillis());
                                sVar2.m137a(iA);
                                sVar2.a(1, (InetSocketAddress) t.this.f307b.getSocketAddress());
                                sVar2.a = t.c(t.this);
                                sVar2.a(1, true);
                                if (t.this.f304a.size() == 0) {
                                    NMJConfig.a(t.this.c, 4, 32);
                                }
                                t.this.f304a.add(sVar2);
                                if (17 < t.this.f307b.getLength()) {
                                    p.logln(2, "mrt - connection request from " + new String(data, 16, (t.this.f307b.getLength() - 16) - 1) + " ssrc: " + iA);
                                }
                                p.logln(2, "RTPClient " + sVar2.m135a() + ", " + iA + " joined session on MIDI thread");
                                int i = t.this.c;
                                sVar2.m134a();
                                NMJConfig.b(i, 32);
                                sVar = sVar2;
                            } else {
                                p.logln(2, String.valueOf(t.this.c) + " - RTP midi, connection request from ssrc: " + iA);
                                sVarA.a(1, (InetSocketAddress) t.this.f307b.getSocketAddress());
                                sVarA.a(1, true);
                                if (t.this.f304a.size() == 1) {
                                    NMJConfig.a(t.this.c, 4, 32);
                                }
                                int i2 = t.this.c;
                                sVarA.m134a();
                                NMJConfig.b(i2, 32);
                                sVar = sVarA;
                            }
                            byte[] bArr = new byte[t.this.f300a.length() + 17];
                            System.arraycopy(data, 0, bArr, 0, 16);
                            System.arraycopy(new String("OK").getBytes(), 0, bArr, 2, 2);
                            p.a(t.this.b, bArr, 12);
                            System.arraycopy(t.this.f300a.getBytes(), 0, bArr, 16, t.this.f300a.length());
                            DatagramPacket datagramPacketA = sVar.a(1);
                            datagramPacketA.setData(bArr, 0, bArr.length);
                            try {
                                t.this.f302a.send(datagramPacketA);
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        } else if (str.equalsIgnoreCase("CK")) {
                            s sVarA2 = t.this.a(p.a(data, 4, 4));
                            if (sVarA2 != null) {
                                t.this.d = System.currentTimeMillis();
                                int i3 = data[8] & 255;
                                this.a = p.m118a(data, 12);
                                long jM118a = p.m118a(data, 20);
                                long jM118a2 = p.m118a(data, 28);
                                if (t.this.f312e) {
                                    p.m126a(data, 0, t.this.f307b.getLength());
                                    p.a(String.valueOf(System.currentTimeMillis()) + " sync, valid ts: " + i3 + " " + Long.toHexString(this.a) + " " + Long.toHexString(jM118a) + " " + Long.toHexString(jM118a2));
                                }
                                sVarA2.a(i3, this.a, t.this.d);
                                t.a(t.this, sVarA2, i3, this.a, jM118a, jM118a2);
                            }
                        } else if (str.equalsIgnoreCase("BY")) {
                            s sVarA3 = t.this.a(p.a(data, 12, 4));
                            if (sVarA3 != null && sVarA3.m138a()) {
                                sVarA3.a(1, false);
                                p.logln(2, String.valueOf(t.this.c) + " - RTP midi, connection closed by client " + sVarA3.m135a() + ", sr: " + t.this.f311d);
                                if (!t.this.f310c) {
                                    NMJConfig.a(t.this.c, 4, 128);
                                    if (t.this.f311d) {
                                        t.this.c();
                                    }
                                } else {
                                    t.this.a(sVarA3);
                                }
                            }
                        } else if (str.equalsIgnoreCase("NO")) {
                            p.logln(5, String.valueOf(t.this.c) + " - RTP midi, connection refused by client " + t.this.h);
                            NMJConfig.a(t.this.c, 4, 2048);
                        }
                    } else if ((data[0] & 128) == 128) {
                        s sVarA4 = t.this.a(p.a(data, 8));
                        if (sVarA4 != null && sVarA4.m138a()) {
                            int iA2 = sVarA4.a(data, t.this.f307b.getLength(), (int) System.currentTimeMillis());
                            if (iA2 == 1) {
                                p.a(sVarA4.m136a(), this.f315a, 8, 2);
                                DatagramPacket datagramPacketA2 = sVarA4.a(0);
                                datagramPacketA2.setData(this.f315a);
                                t.this.f308b.send(datagramPacketA2);
                            } else if (iA2 == 2) {
                                NMJConfig.a(t.this.c, 4, 8192);
                            }
                        }
                    } else {
                        p.a(2, "Unknown data: ", data, 0, t.this.f307b.getLength());
                    }
                } else {
                    return;
                }
            }
        }
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final void mo191a(byte[] bArr) throws IOException {
        this.f299a = a();
        for (int i = 0; i < this.f304a.size(); i++) {
            s sVar = this.f304a.get(i);
            DatagramPacket datagramPacketA = sVar.a(1);
            if (datagramPacketA == null) {
                p.a(3, "oops, " + sVar + " - no MIDI packet", bArr);
            } else {
                byte[][] bArrA = sVar.a(bArr, (int) this.f299a);
                if (bArrA != null) {
                    for (int i2 = 0; i2 < bArrA.length; i2++) {
                        datagramPacketA.setData(bArrA[i2], 0, bArrA[i2].length);
                        this.f302a.send(datagramPacketA);
                        if (bArrA.length > 1 && bArrA[i2].length > 256) {
                            try {
                                Thread.currentThread();
                                Thread.sleep(15L);
                            } catch (Exception e) {
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    protected final void a(int i, int i2, byte[] bArr, long j) {
        if (this.f304a.size() <= 1) {
            this.a.a(i, i2, bArr, j);
        } else {
            b(i, i2, bArr, j);
        }
    }

    private synchronized void b(int i, int i2, byte[] bArr, long j) {
        this.a.a(i, i2, bArr, j);
    }

    final s a(int i) {
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < this.f304a.size()) {
                if (this.f304a.get(i3).a() == i) {
                    return this.f304a.get(i3);
                }
                i2 = i3 + 1;
            } else {
                return null;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001f, code lost:
    
        r0 = r3.c;
        r4.m134a();
        de.humatic.nmj.NMJConfig.b(r0, 64);
        r3.f304a.remove(r1);
        de.humatic.nmj.p.logln(2, java.lang.String.valueOf(r3.c) + " - RTPClient left session: " + r4.m135a() + ", " + r4.a() + ", nr. clients left: " + r3.f304a.size());
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0073, code lost:
    
        if (r3.f304a.size() != 0) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0075, code lost:
    
        de.humatic.nmj.NMJConfig.a(r3.c, 4, 128);
        de.humatic.nmj.NMJConfig.a(r3.c, 4, 4);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final synchronized void a(de.humatic.nmj.s r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            r0 = 0
            r1 = r0
        L3:
            java.util.Vector<de.humatic.nmj.s> r0 = r3.f304a     // Catch: java.lang.Throwable -> L85
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L85
            if (r1 < r0) goto Ld
        Lb:
            monitor-exit(r3)
            return
        Ld:
            java.util.Vector<de.humatic.nmj.s> r0 = r3.f304a     // Catch: java.lang.Throwable -> L85
            java.lang.Object r0 = r0.get(r1)     // Catch: java.lang.Throwable -> L85
            de.humatic.nmj.s r0 = (de.humatic.nmj.s) r0     // Catch: java.lang.Throwable -> L85
            int r0 = r0.a()     // Catch: java.lang.Throwable -> L85
            int r2 = r4.a()     // Catch: java.lang.Throwable -> L85
            if (r0 != r2) goto L88
            int r0 = r3.c     // Catch: java.lang.Throwable -> L85
            r2 = 64
            r4.m134a()     // Catch: java.lang.Throwable -> L85
            de.humatic.nmj.NMJConfig.b(r0, r2)     // Catch: java.lang.Throwable -> L85
            java.util.Vector<de.humatic.nmj.s> r0 = r3.f304a     // Catch: java.lang.Throwable -> L85
            r0.remove(r1)     // Catch: java.lang.Throwable -> L85
            r0 = 2
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L85
            int r2 = r3.c     // Catch: java.lang.Throwable -> L85
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch: java.lang.Throwable -> L85
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L85
            java.lang.String r2 = " - RTPClient left session: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L85
            java.lang.String r2 = r4.m135a()     // Catch: java.lang.Throwable -> L85
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L85
            java.lang.String r2 = ", "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L85
            int r2 = r4.a()     // Catch: java.lang.Throwable -> L85
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L85
            java.lang.String r2 = ", nr. clients left: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L85
            java.util.Vector<de.humatic.nmj.s> r2 = r3.f304a     // Catch: java.lang.Throwable -> L85
            int r2 = r2.size()     // Catch: java.lang.Throwable -> L85
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L85
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L85
            de.humatic.nmj.p.logln(r0, r1)     // Catch: java.lang.Throwable -> L85
            java.util.Vector<de.humatic.nmj.s> r0 = r3.f304a     // Catch: java.lang.Throwable -> L85
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L85
            if (r0 != 0) goto Lb
            int r0 = r3.c     // Catch: java.lang.Throwable -> L85
            r1 = 4
            r2 = 128(0x80, float:1.8E-43)
            de.humatic.nmj.NMJConfig.a(r0, r1, r2)     // Catch: java.lang.Throwable -> L85
            int r0 = r3.c     // Catch: java.lang.Throwable -> L85
            r1 = 4
            r2 = 4
            de.humatic.nmj.NMJConfig.a(r0, r1, r2)     // Catch: java.lang.Throwable -> L85
            goto Lb
        L85:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L88:
            int r0 = r1 + 1
            r1 = r0
            goto L3
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.t.a(de.humatic.nmj.s):void");
    }

    @Override // de.humatic.nmj.w
    protected final void b(int i) {
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < this.f304a.size()) {
                if (this.f304a.get(i3).a != i) {
                    i2 = i3 + 1;
                } else {
                    s sVar = this.f304a.get(i3);
                    b(sVar);
                    int i4 = this.c;
                    sVar.m134a();
                    NMJConfig.b(i4, 64);
                    this.f304a.remove(i3);
                    p.logln(2, "RTPClient left session: " + sVar.m135a() + ", " + sVar.a() + ", num clients left: " + this.f304a.size());
                    if (this.f304a.size() == 0) {
                        NMJConfig.a(this.c, 4, 128);
                        NMJConfig.a(this.c, 4, 4);
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
        }
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final v[] mo4a() {
        if (this.f304a == null || this.f304a.size() == 0) {
            return null;
        }
        v[] vVarArr = new v[this.f304a.size()];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.f304a.size()) {
                return vVarArr;
            }
            vVarArr[i2] = this.f304a.get(i2).m134a();
            i = i2 + 1;
        }
    }

    static /* synthetic */ int c(t tVar) {
        int i = 0;
        int iMax = -1;
        while (true) {
            int i2 = i;
            if (i2 >= tVar.f304a.size()) {
                return iMax + 1;
            }
            iMax = Math.max(iMax, tVar.f304a.get(i2).a);
            i = i2 + 1;
        }
    }

    @Override // de.humatic.nmj.w
    protected final void d() {
        boolean z = (NMJConfig.getFlags(-1) & NMJConfig.RECONNECT) != 0;
        if (this.f311d != z) {
            this.f311d = z;
            if (this.f311d && !this.f310c && !this.f304a.get(0).m138a()) {
                try {
                    c();
                } catch (Exception e) {
                }
            }
        }
    }

    class b extends TimerTask {
        private b() {
        }

        /* synthetic */ b(t tVar, byte b) {
            this();
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            try {
                t.this.m177a(t.this.i);
            } catch (Exception e) {
            }
        }
    }
}
