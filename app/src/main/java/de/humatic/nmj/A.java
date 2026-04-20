package de.humatic.nmj;

import android.os.Build;
import android.util.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
final class A extends w {
    private a a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private c f0a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private A f1a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private ServerSocket f2a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private TimerTask f3a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Vector<z> f4a;

    A(int i, n nVar) throws IOException {
        super(nVar, i);
        this.f4a = new Vector<>();
        if (Build.VERSION.SDK_INT < 8) {
            throw new IOException("WebSockets require Android 2.2 or greater");
        }
        this.f1a = this;
        if (NMJConfig.getIP(this.c) != null) {
            z zVar = new z(this.f1a);
            new Thread(zVar).start();
            this.f4a.add(zVar);
        } else {
            this.a = new a(this, (byte) 0);
            new Thread(this.a).start();
            NMJConfig.a(this.c, 4, 4);
        }
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final void mo96a() {
        p.logln(4, "WebSocketIO close, nr. clients: " + this.f4a.size());
        Iterator<z> it = this.f4a.iterator();
        while (it.hasNext()) {
            it.next().m201a();
        }
        if (this.a != null) {
            try {
                this.f332a = true;
                this.f2a.close();
            } catch (Exception e) {
            }
            try {
                this.f0a.cancel();
            } catch (Exception e2) {
            }
        } else {
            try {
                this.f3a.cancel();
            } catch (Exception e3) {
            }
        }
        NMJConfig.a(this.c, 4, 128);
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final void mo191a(byte[] bArr) throws IOException {
        Iterator<z> it = this.f4a.iterator();
        while (it.hasNext()) {
            it.next().a(bArr);
        }
    }

    protected final void a(byte[] bArr, int i, int i2) {
        this.a.a(bArr, i, i2);
    }

    protected final void a(z zVar, byte[] bArr, long j) {
        this.a.a(this.c, zVar.m199a(), bArr, j);
    }

    protected final void a(z zVar) {
        if (zVar.a < 0) {
            if (NMJConfig.getRTPState(this.c) == 32) {
                NMJConfig.a(this.c, 4, 128);
            }
            this.f4a.remove(zVar);
            this.f3a = new b(this, (byte) 0);
            NMJConfig.a(this.f3a, 15000, -1);
            p.logln(3, "WebSocketIO local client closed " + zVar);
            return;
        }
        int i = this.c;
        zVar.m200a();
        NMJConfig.b(i, 64);
        this.f4a.remove(zVar);
        if (this.f4a.size() == 0) {
            NMJConfig.a(this.c, 4, 128);
            NMJConfig.a(this.c, 4, 4);
            try {
                this.f0a.cancel();
            } catch (Exception e) {
            }
            this.f0a = null;
        }
        p.logln(3, "Client disconnected " + zVar + ", rem. clients: " + this.f4a.size());
        if (this.f4a.size() > 0) {
            Iterator<z> it = this.f4a.iterator();
            while (it.hasNext()) {
                p.logln(3, it.next().toString());
            }
        }
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected final v[] mo4a() {
        if (this.f4a == null || this.f4a.size() == 0) {
            return null;
        }
        v[] vVarArr = new v[this.f4a.size()];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.f4a.size()) {
                return vVarArr;
            }
            vVarArr[i2] = this.f4a.get(i2).m200a();
            i = i2 + 1;
        }
    }

    class a extends Thread {

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private StringBuffer f5a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private byte[] f6a;

        private a() {
            this.f6a = new byte[1500];
        }

        /* synthetic */ a(A a, byte b) {
            this();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                int localPort = NMJConfig.getLocalPort(A.this.c);
                A.this.f2a = new ServerSocket();
                A.this.f2a.setPerformancePreferences(0, 1, 0);
                A.this.f2a.bind(new InetSocketAddress(localPort));
                p.logln(4, "WebSocketIO, listening for connections on port " + localPort);
                while (!A.this.f332a) {
                    m5a();
                }
            } catch (Exception e) {
                if (e.toString().indexOf("closed") < 0 && e.toString().indexOf("Bad file number") < 0) {
                    e.printStackTrace();
                }
            }
            try {
                A.this.f2a.close();
            } catch (Exception e2) {
            }
            p.logln(4, "WebSocketIO, accept thread done");
        }

        /* JADX INFO: renamed from: a, reason: collision with other method in class */
        private void m5a() throws GeneralSecurityException, IOException {
            int i = 0;
            String str = de.bassapps.launchbuttonsP.BuildConfig.FLAVOR;
            Socket socketAccept = A.this.f2a.accept();
            socketAccept.setTcpNoDelay(true);
            OutputStream outputStream = socketAccept.getOutputStream();
            InputStream inputStream = socketAccept.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            String strA = a(inputStream, true);
            if (strA == null || strA.indexOf("HTTP/1.1") < 0) {
                stringBuffer.append("HTTP/1.0 404\r\n");
                outputStream.write(stringBuffer.toString().getBytes());
                return;
            }
            stringBuffer.append("HTTP/1.1 101 Switching Protocols\r\n");
            stringBuffer.append("Upgrade: websocket\r\n");
            stringBuffer.append("Connection: Upgrade\r\n");
            boolean z = false;
            while (strA != null && strA.length() > 0) {
                String strA2 = a(inputStream, true);
                if (strA2 == null) {
                    break;
                }
                if (strA2 != null && strA2.indexOf("Sec-WebSocket-Key") != -1) {
                    String strSubstring = strA2.substring(strA2.indexOf(": ") + 2, strA2.length());
                    StringBuilder sb = new StringBuilder("Sec-WebSocket-Accept: ");
                    String str2 = String.valueOf(strSubstring.trim()) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                    messageDigest.update(str2.getBytes());
                    stringBuffer.append(sb.append(Base64.encodeToString(messageDigest.digest(), 0).trim()).append("\r\n").toString());
                    strA = strA2;
                } else if (strA2.indexOf("Sec-WebSocket-Protocol") != -1) {
                    if (strA2.trim().indexOf("rtp") != -1) {
                        stringBuffer.append("Sec-WebSocket-Protocol: rtp\r\n");
                        z = true;
                        strA = strA2;
                    } else {
                        strA = strA2;
                    }
                } else if (strA2.indexOf("Cookie: ssrc=") != -1) {
                    String[] strArrSplit = strA2.substring(8).split(";");
                    int i2 = i;
                    String strTrim = str;
                    for (int i3 = 0; i3 < strArrSplit.length; i3++) {
                        try {
                            if (strArrSplit[i3].indexOf("ssrc") != -1) {
                                i2 = Integer.parseInt(strArrSplit[i3].split("=")[1].trim());
                            } else if (strArrSplit[i3].indexOf("name") != -1) {
                                strTrim = strArrSplit[i3].split("=")[1].trim();
                            }
                        } catch (Exception e) {
                        }
                    }
                    str = strTrim;
                    i = i2;
                    strA = strA2;
                } else {
                    strA = strA2;
                }
            }
            stringBuffer.append("Origin: " + NMJConfig.a(false) + "\r\n");
            stringBuffer.append("\r\n");
            outputStream.write(stringBuffer.toString().getBytes());
            outputStream.flush();
            z zVar = new z(A.this.f1a, socketAccept, a(), i, str, z ? 0 : 1);
            new Thread(zVar).start();
            A.this.f4a.add(zVar);
            if (A.this.f4a.size() == 1 && A.this.f0a == null) {
                NMJConfig.a(A.this.c, 4, 32);
                A.this.f0a = new c(A.this, (byte) 0);
                NMJConfig.a(A.this.f0a, 5000, 5000);
            }
            int i4 = A.this.c;
            zVar.m200a();
            NMJConfig.b(i4, 32);
            p.logln(3, "Client connected to local session: " + zVar);
        }

        private String a(InputStream inputStream, boolean z) throws IOException {
            if (this.f5a == null) {
                this.f5a = new StringBuffer();
            }
            this.f5a.delete(0, this.f5a.length());
            int i = inputStream.read();
            if (i == -1) {
                return null;
            }
            while (i != 10) {
                if (i != 13) {
                    this.f5a.append((char) i);
                }
                i = inputStream.read();
                if (i == -1) {
                    return null;
                }
            }
            return this.f5a.toString();
        }

        private int a() {
            int iMax = -1;
            Iterator it = A.this.f4a.iterator();
            while (true) {
                int i = iMax;
                if (it.hasNext()) {
                    iMax = Math.max(i, ((z) it.next()).a);
                } else {
                    return i + 1;
                }
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static /* synthetic */ void m3a(A a2) {
        try {
            z zVar = new z(a2.f1a);
            new Thread(zVar).start();
            a2.f4a.add(zVar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class c extends TimerTask {

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private boolean f7a;

        private c() {
        }

        /* synthetic */ c(A a, byte b) {
            this();
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            if (A.this.f4a.size() != 0) {
                this.f7a = !this.f7a;
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    for (z zVar : A.this.f4a) {
                        try {
                            if (this.f7a) {
                                zVar.a(jCurrentTimeMillis);
                            } else if (zVar.f374a) {
                                zVar.b(jCurrentTimeMillis);
                            }
                        } catch (IOException e) {
                            p.logln(2, "error sending Ping " + e.toString());
                            if (e.toString().indexOf("Broken pipe") != -1) {
                                A.this.a(zVar);
                            }
                        }
                    }
                } catch (Exception e2) {
                }
            }
        }
    }

    class b extends TimerTask {
        private b() {
        }

        /* synthetic */ b(A a, byte b) {
            this();
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            A.m3a(A.this);
        }
    }
}
