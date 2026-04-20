package de.humatic.nmj;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/* JADX INFO: renamed from: de.humatic.nmj.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0005a {
    private InputStream a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private OutputStream f125a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private ServerSocket f126a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Socket f127a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Vector<Integer> f128a = new Vector<>();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    boolean f129a;
    private ServerSocket b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private boolean f130b;

    C0005a() {
        int i;
        try {
            i = Integer.parseInt(NMJConfig.getProperty("adb_query_port", "9999"));
        } catch (Exception e) {
            i = 9999;
        }
        if (i != 9999) {
            p.logln(5, "ADBQuery, custom port " + i);
        }
        try {
            this.f126a = new ServerSocket(i);
            this.f126a.setReuseAddress(true);
            new Thread(new C0002a(this, this.f126a, (byte) 0)).start();
        } catch (BindException e2) {
            p.logln(2, "ADBQueryServer: EADDRINUSE, using existing server");
            this.f129a = true;
            m90a();
            return;
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (i == 9999) {
            try {
                this.b = new ServerSocket(9998);
                this.b.setReuseAddress(true);
                new Thread(new C0002a(this, this.b, (byte) 0)).start();
            } catch (BindException e4) {
                p.logln(2, "ADBQueryServer: " + e4.toString());
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final boolean m90a() {
        boolean z = false;
        try {
            int[] iArrA = a(true);
            if (iArrA.length == 0) {
                return false;
            }
            p.logln(2, "ADBQueryServer: connecting to running app " + iArrA.length);
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            OutputStream outputStream = socket.getOutputStream();
            String strM14a = NMJConfig.m14a();
            int length = (iArrA.length * 4) + 2;
            if (strM14a.length() > 0 && strM14a.indexOf("nmj_") < 0) {
                length += strM14a.length() + 1;
            }
            byte[] bArr = new byte[length];
            bArr[0] = 16;
            bArr[1] = (byte) iArrA.length;
            for (int i = 0; i < iArrA.length; i++) {
                p.a(iArrA[i], bArr, (i << 2) + 2);
            }
            if (strM14a.length() > 0 && strM14a.indexOf("nmj_") < 0) {
                bArr[(iArrA.length << 2) + 2] = (byte) strM14a.length();
                System.arraycopy(strM14a.getBytes(), 0, bArr, (iArrA.length << 2) + 3, strM14a.length());
            }
            outputStream.write(bArr);
            outputStream.close();
            socket.close();
            z = true;
            return true;
        } catch (ConnectException e) {
            if (e.toString().indexOf("Connection refused") != -1) {
                NMJConfig.a(z, true);
                return z;
            }
            return z;
        } catch (Exception e2) {
            e2.printStackTrace();
            return z;
        }
    }

    final void a() {
        p.logln(2, "ADBQueryServer closing");
        this.f130b = true;
        try {
            this.f126a.close();
        } catch (Exception e) {
        }
        try {
            this.b.close();
        } catch (Exception e2) {
        }
    }

    static /* synthetic */ int[] a(C0005a c0005a, boolean z) {
        return a(true);
    }

    private static int[] a(boolean z) {
        int i = 0;
        for (int i2 = 0; i2 < NMJConfig.getNumChannels(); i2++) {
            if (NMJConfig.getMode(i2) == 4) {
                if (!z) {
                    i++;
                } else if (NetworkMidiSystem.get().isOpen(-1, i2)) {
                    i++;
                }
            }
        }
        int[] iArr = new int[i];
        int i3 = 0;
        for (int i4 = 0; i4 < NMJConfig.getNumChannels(); i4++) {
            if (NMJConfig.getMode(i4) == 4) {
                if (!z) {
                    iArr[i3] = NMJConfig.getPort(i4);
                    i3++;
                } else if (NetworkMidiSystem.get().isOpen(-1, i4)) {
                    iArr[i3] = NMJConfig.getPort(i4);
                    i3++;
                }
            }
        }
        return iArr;
    }

    static /* synthetic */ boolean a(C0005a c0005a, int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < NMJConfig.getNumChannels(); i4++) {
            if (NMJConfig.getMode(i4) == 4) {
                if (i3 == i) {
                    NMJConfig.setPort(i4, i2);
                    p.logln(1, String.valueOf(i) + " - changed port " + NMJConfig.getPort(i4));
                    return true;
                }
                i3++;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: de.humatic.nmj.a$a, reason: collision with other inner class name */
    class C0002a extends Thread {

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private ServerSocket f131a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private byte[] f132a;

        private C0002a(ServerSocket serverSocket) {
            this.f132a = new byte[96];
            this.f131a = serverSocket;
        }

        /* synthetic */ C0002a(C0005a c0005a, ServerSocket serverSocket, byte b) {
            this(serverSocket);
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x0254 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:109:0x0071 A[SYNTHETIC] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instruction units count: 744
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.C0005a.C0002a.run():void");
        }
    }
}
