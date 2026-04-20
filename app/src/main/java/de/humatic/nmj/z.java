package de.humatic.nmj;

import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import javax.net.ssl.SSLContext;

/* JADX INFO: loaded from: classes.dex */
final class z extends Thread implements f {
    int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private A f365a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private l f366a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private ByteArrayOutputStream f367a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private InputStream f368a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private OutputStream f369a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String f370a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private StringBuffer f371a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private InetSocketAddress f372a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Socket f373a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    boolean f374a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[] f375a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private int[] f376a;
    private int b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private String f377b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private boolean f378b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private byte[] f379b;
    private int c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private String f380c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private boolean f381c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private byte[] f382c;
    private int d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private boolean f383d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private byte[] f384d;
    private int e;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private boolean f385e;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private byte[] f386e;
    private int f;

    /* JADX INFO: renamed from: f, reason: collision with other field name */
    private byte[] f387f;
    private int g;

    /* JADX INFO: renamed from: g, reason: collision with other field name */
    private byte[] f388g;
    private byte[] h;

    z(A a, Socket socket, int i, int i2, String str, int i3) {
        this.f = 65535;
        this.f375a = new byte[1500];
        this.f379b = new byte[1500];
        this.f382c = new byte[1500];
        this.f384d = new byte[128];
        this.f386e = new byte[16];
        this.f387f = new byte[8];
        this.h = new byte[1500];
        this.f377b = "WSClient ";
        this.f380c = "unknown";
        this.g = 0;
        this.f376a = new int[3];
        this.f365a = a;
        this.b = a.c;
        this.f373a = socket;
        this.a = i;
        this.c = i2;
        this.f377b = str == null ? "WSClient " + i : str;
        this.f374a = (i3 & 1) == 0;
        try {
            this.f380c = ((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress().toString();
        } catch (Exception e) {
        }
    }

    z(A a) throws IOException {
        this.f = 65535;
        this.f375a = new byte[1500];
        this.f379b = new byte[1500];
        this.f382c = new byte[1500];
        this.f384d = new byte[128];
        this.f386e = new byte[16];
        this.f387f = new byte[8];
        this.h = new byte[1500];
        this.f377b = "WSClient ";
        this.f380c = "unknown";
        this.g = 0;
        this.f376a = new int[3];
        this.f365a = a;
        this.b = a.c;
        this.f378b = true;
        this.a = -1;
        this.f377b = String.valueOf(this.f377b) + NMJConfig.m15a(this.b);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected final int m199a() {
        return this.c;
    }

    @Override // java.lang.Thread
    public final String toString() {
        return "WSClient " + this.a + ", ch:" + this.b + " ip: " + (this.a >= 0 ? this.f380c : NMJConfig.getIP(this.b)) + " rtp: " + this.f374a + " ssrc: " + this.c;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        URI uri;
        int i;
        Socket socket;
        try {
            if (this.c == 0) {
                this.c = (int) (Math.random() * 2.147483647E9d);
            }
            System.arraycopy("CK:".getBytes(), 0, this.f386e, 0, 3);
            this.f386e[3] = 0;
            p.a(this.c, this.f386e, 4);
            if (this.f378b) {
                String ip = NMJConfig.getIP(this.b);
                if (ip.indexOf("ws") < 0) {
                    ip = "ws://" + ip;
                }
                if (ip.indexOf(":", ip.indexOf("://") + 3) < 0) {
                    if (ip.indexOf("/", ip.indexOf("://") + 3) < 0) {
                        ip = String.valueOf(ip) + ":" + NMJConfig.getPort(this.b);
                    } else {
                        int iIndexOf = ip.indexOf("/", ip.indexOf("://") + 3);
                        ip = String.valueOf(ip.substring(0, iIndexOf)) + ":" + NMJConfig.getPort(this.b) + ip.substring(iIndexOf);
                    }
                }
                URI uriCreate = URI.create(ip);
                String path = uriCreate.getPath();
                String str = path.trim().length() == 0 ? "/" : path;
                if (uriCreate.getHost() != null) {
                    uri = new URI(uriCreate.getScheme().equals("wss") ? "https" : "http", "//" + uriCreate.getHost(), null);
                } else {
                    uri = null;
                }
                try {
                    int port = uriCreate.getPort();
                    if (port < 0) {
                        i = uriCreate.getScheme().equals("wss") ? 443 : 80;
                    } else {
                        i = port;
                    }
                    if (uriCreate.getScheme().equals("wss")) {
                        SSLContext sSLContext = SSLContext.getInstance("TLS");
                        sSLContext.init(null, null, null);
                        socket = sSLContext.getSocketFactory().createSocket(uriCreate.getHost(), i);
                    } else {
                        socket = new Socket();
                        InetSocketAddress inetSocketAddress = new InetSocketAddress(uriCreate.getHost(), i);
                        socket.setTcpNoDelay(true);
                        socket.connect(inetSocketAddress, 2000);
                    }
                    this.f373a = socket;
                    this.f373a.setSoTimeout(5000);
                    this.f373a.setTcpNoDelay(true);
                    this.f369a = this.f373a.getOutputStream();
                    this.f368a = this.f373a.getInputStream();
                    NMJConfig.a(this.b, 1, 0);
                    this.f370a = p.m119a();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("GET " + str + " HTTP/1.1\r\n");
                    stringBuffer.append("Upgrade: websocket\r\n");
                    stringBuffer.append("Connection: Upgrade\r\n");
                    stringBuffer.append("Host: " + uriCreate.getHost() + "\r\n");
                    if (uri != null) {
                        stringBuffer.append("Origin: " + uri.toString() + "\r\n");
                    }
                    stringBuffer.append("Sec-WebSocket-Version: 13\r\n");
                    stringBuffer.append("Sec-WebSocket-Protocol: rtp\r\n");
                    stringBuffer.append("Sec-WebSocket-Key: " + this.f370a + "\r\n");
                    stringBuffer.append("Cookie: ssrc=" + this.c + ";name=" + this.f377b + "\r\n");
                    stringBuffer.append("\r\n");
                    this.f369a.write(stringBuffer.toString().getBytes());
                    this.f369a.flush();
                    if (!a()) {
                        p.logln(4, "MWS - Failed to verify connection");
                        return;
                    } else {
                        p.logln(4, "MWS " + this.b + " - connection verified, rtp: " + this.f374a + ", starting socket io");
                        NMJConfig.a(this.b, 4, 32);
                    }
                } catch (SocketTimeoutException e) {
                    this.f365a.a(this);
                    return;
                }
            } else {
                this.f369a = this.f373a.getOutputStream();
                this.f368a = this.f373a.getInputStream();
                this.f372a = (InetSocketAddress) this.f373a.getRemoteSocketAddress();
            }
            if (this.f374a) {
                this.f366a = new l(this, this.b, this.c, System.currentTimeMillis());
            }
            while (!this.f381c) {
                try {
                    try {
                        int iAvailable = this.f368a.available();
                        if (iAvailable == 0) {
                            try {
                                Thread.sleep(10L);
                            } catch (InterruptedException e2) {
                            }
                        } else {
                            int i2 = this.f368a.read(this.f375a, 0, Math.min(iAvailable, this.f375a.length));
                            if (i2 < 0) {
                                break;
                            } else {
                                a(this.f375a, i2);
                            }
                        }
                    } catch (SocketTimeoutException e3) {
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                } catch (IllegalArgumentException e5) {
                    if (e5.toString().indexOf("base-64") != -1) {
                        p.a(3, "bad base-64:", this.f375a, 0, 16);
                    } else {
                        e5.printStackTrace();
                    }
                } catch (SocketException e6) {
                    if (e6.toString().indexOf("timed out") == -1) {
                        if (e6.toString().indexOf("recvfrom failed") != -1) {
                            p.logln(-1, "WS " + e6.toString());
                            this.f381c = true;
                            this.f365a.a(this);
                        } else {
                            e6.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        if (!this.f378b && !this.f383d && !this.f385e) {
            p.logln(2, "WSClient - Client missed to send CLOSE event");
            this.f365a.a(this);
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final void m201a() {
        this.f385e = true;
        try {
            p.logln(3, "WSClient " + this.a + " sending CLOSE");
            b(this.f384d, a(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR, 8, 1000, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.f381c = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001d, code lost:
    
        if (r0 != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
    
        throw new java.io.IOException("Sec-WebSocket-Accept header missing");
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00ac, code lost:
    
        de.humatic.nmj.p.a("verified...");
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:?, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a() throws java.security.GeneralSecurityException, java.io.IOException {
        /*
            r7 = this;
            r6 = 4
            r1 = 0
            r2 = 1
            java.lang.String r0 = r7.a(r2)
            de.humatic.nmj.p.logln(r6, r0)
            if (r0 == 0) goto L14
            java.lang.String r3 = "HTTP/1.1 101"
            int r0 = r0.indexOf(r3)
            if (r0 >= 0) goto L16
        L14:
            r0 = r1
        L15:
            return r0
        L16:
            r0 = r1
        L17:
            java.lang.String r3 = r7.a(r2)
            if (r3 != 0) goto L27
        L1d:
            if (r0 != 0) goto Lac
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Sec-WebSocket-Accept header missing"
            r0.<init>(r1)
            throw r0
        L27:
            java.lang.String r4 = r3.trim()
            int r4 = r4.length()
            if (r4 == 0) goto L1d
            java.lang.String r4 = ":"
            java.lang.String[] r4 = r3.split(r4)
            de.humatic.nmj.p.logln(r6, r3)
            r3 = r4[r1]
            java.lang.String r3 = r3.trim()
            java.lang.String r5 = "Sec-WebSocket-Accept"
            boolean r3 = r3.equalsIgnoreCase(r5)
            if (r3 == 0) goto L8c
            r0 = r4[r2]
            java.lang.String r0 = r0.trim()
            java.lang.String r3 = "SHA-1"
            java.security.MessageDigest r3 = java.security.MessageDigest.getInstance(r3)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = r7.f370a
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r4.<init>(r5)
            java.lang.String r5 = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            byte[] r4 = r4.getBytes()
            r3.update(r4)
            byte[] r3 = r3.digest()
            java.lang.String r3 = android.util.Base64.encodeToString(r3, r1)
            java.lang.String r3 = r3.trim()
            boolean r0 = r0.equals(r3)
            if (r0 != 0) goto L8a
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Accept header value mismatch"
            r0.<init>(r1)
            throw r0
        L8a:
            r0 = r2
            goto L17
        L8c:
            r3 = r4[r1]
            java.lang.String r3 = r3.trim()
            java.lang.String r5 = "Sec-WebSocket-Protocol"
            boolean r3 = r3.equalsIgnoreCase(r5)
            if (r3 == 0) goto L17
            r3 = r4[r2]
            java.lang.String r3 = r3.trim()
            java.lang.String r4 = "rtp"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L17
            r7.f374a = r2
            goto L17
        Lac:
            java.lang.String r1 = "verified..."
            de.humatic.nmj.p.a(r1)
            goto L15
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.z.a():boolean");
    }

    private synchronized int a(Object obj, int i, int i2, int i3) throws IOException {
        int i4;
        int i5;
        if (this.f381c) {
            i5 = -1;
        } else {
            boolean z = this.f378b;
            byte[] bArr = this.f384d;
            if (i == 2) {
                bArr = this.f379b;
            } else if (i == 1) {
                bArr = this.f382c;
            }
            byte[] bytes = obj instanceof String ? ((String) obj).getBytes("UTF-8") : (byte[]) obj;
            int i6 = i2 > 0 ? 2 : 0;
            int length = bytes.length + i6;
            if (length <= 125) {
                i4 = 2;
            } else {
                i4 = length <= 65535 ? 4 : 10;
            }
            int i7 = i4 + (z ? 4 : 0);
            int i8 = z ? 128 : 0;
            if (bArr.length < length + i7) {
                bArr = new byte[length + i7];
            }
            bArr[0] = (byte) (((byte) i) | (-128));
            if (length <= 125) {
                bArr[1] = (byte) (i8 | length);
            } else if (length <= 65535) {
                bArr[1] = (byte) (i8 | 126);
                p.a((short) length, bArr, 2);
            } else {
                bArr[1] = (byte) (i8 | 127);
                p.a(length, bArr, 2);
            }
            if (i2 > 0) {
                bArr[i7] = (byte) Math.floor(i2 / 256);
                bArr[i7 + 1] = (byte) i2;
            }
            System.arraycopy(bytes, 0, bArr, i6 + i7, bytes.length);
            if (z) {
                int iRandom = (int) (Math.random() * 2.147483647E9d);
                p.a(iRandom, bArr, i4);
                int i9 = 24;
                for (int i10 = i7; i10 < i7 + length; i10++) {
                    bArr[i10] = (byte) (bArr[i10] ^ (iRandom >> i9));
                    i9 -= 8;
                    if (i9 < 0) {
                        i9 = 24;
                    }
                }
            }
            i5 = i7 + length;
        }
        return i5;
    }

    private void a(byte[] bArr, int i) throws IOException {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        this.d++;
        int i8 = 0;
        int iA = 0;
        for (int i9 = i; iA >= 0 && iA < i9; i9 = i4) {
            if (this.d == this.e) {
                i2 = i8 + 1;
                if (i8 > 100) {
                    return;
                }
            } else {
                i2 = i8;
            }
            if ((bArr[iA] & 112) != 0) {
                i3 = iA;
                while (i3 < i9) {
                    if ((bArr[i3] & 240) == 128) {
                        int i10 = bArr[i3] & 15;
                        if ((i10 == 1 || i10 == 2 || i10 == 9 || i10 == 10) && (bArr[i3] & 112) == 0 && (this.f378b || i3 + 1 >= i9 || (bArr[i3 + 1] & 128) != 0)) {
                            p.a(-1, "WS - Invalid reserved fields. Resync, fs: " + i3 + " " + this.f378b, bArr, i3, 16);
                            break;
                        }
                    }
                    i3++;
                }
            } else {
                i3 = iA;
            }
            this.e = this.d;
            if (i3 > i9 - 1) {
                p.a("WS - frameStart beyond length " + i3 + " " + i9);
                return;
            }
            int i11 = i3 + 2;
            if (i3 == i9 - 1) {
                bArr[0] = bArr[i3];
                bArr[1] = (byte) this.f368a.read();
                i5 = 0;
                i4 = 2;
            } else {
                i4 = i9;
                i5 = i3;
            }
            if (i5 + 1 <= i4) {
                int[] iArrM198a = m198a(bArr, i5);
                i7 = iArrM198a[1];
                i11 = iArrM198a[0];
                i6 = iArrM198a[2];
            } else {
                i6 = 0;
                i7 = 0;
            }
            if (i7 < 0 || i11 + i6 + i7 > i4 || (i7 > 0 && i11 >= i4)) {
                System.arraycopy(bArr, i5, this.h, 0, i4 - i5);
                int i12 = i4 - i5;
                if (i7 < 0) {
                    p.logln(-1, "Could not read dataLength, missing " + Math.abs(i7));
                    this.f368a.read(this.h, i12, Math.abs(i7));
                    int[] iArrM198a2 = m198a(this.h, 0);
                    i7 = iArrM198a2[1];
                    i12 = iArrM198a2[0];
                    i6 = iArrM198a2[2];
                    i11 = i12;
                    i5 = 0;
                }
                int i13 = i7 + ((i6 + i11) - i5);
                int i14 = 0;
                while (i14 != -1 && i12 < i13) {
                    i14 = this.f368a.read(this.h, i12, i13 - i12);
                    if (i14 >= 0) {
                        i12 += i14;
                    } else {
                        return;
                    }
                }
                int[] iArrM198a3 = m198a(this.h, 0);
                a(this.h, 0, iArrM198a3[0], iArrM198a3[1]);
                return;
            }
            iA = a(bArr, i5, i11, i7);
            i8 = i2;
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private int[] m198a(byte[] bArr, int i) {
        this.f376a[1] = bArr[i + 1] & 127;
        this.f376a[0] = i + 2;
        this.f376a[2] = (bArr[i + 1] & 128) != 0 ? 4 : 0;
        if (this.f376a[1] == 126) {
            if (i + 4 < bArr.length) {
                this.f376a[1] = p.a(bArr, i + 2, 2);
                this.f376a[0] = i + 4;
            } else {
                this.f376a[1] = (i + 4) - bArr.length;
            }
        } else if (this.f376a[1] == 127) {
            if (i + 10 < bArr.length) {
                this.f376a[1] = (int) p.m118a(bArr, i + 2);
                this.f376a[0] = i + 10;
            } else {
                this.f376a[1] = (i + 10) - bArr.length;
            }
        }
        return this.f376a;
    }

    private int a(byte[] bArr, int i, int i2, int i3) throws IOException {
        byte[] bArrDecode;
        boolean z = false;
        boolean z2 = (bArr[i] & 128) != 0;
        if ((bArr[i + 1] & 128) != 0) {
            int iA = p.a(bArr, i2);
            i2 += 4;
            int i4 = 24;
            for (int i5 = i2; i5 < i2 + i3; i5++) {
                bArr[i5] = (byte) (bArr[i5] ^ (iA >> i4));
                i4 -= 8;
                if (i4 < 0) {
                    i4 = 24;
                }
            }
        }
        switch (bArr[i] & 15) {
            case 0:
                if (Math.abs(this.d) >= 3) {
                    p.logln(2, "CONTINUE at " + i);
                    if (this.f367a == null) {
                        p.logln(2, "Fragmented message not opened");
                    } else if (this.f367a.size() <= 65535) {
                        this.f367a.write(bArr, i2, i3);
                        if (z2) {
                            byte[] byteArray = this.f367a.toByteArray();
                            try {
                                if (this.f374a) {
                                    this.f366a.a(byteArray, 0, byteArray.length);
                                } else {
                                    this.f365a.a(byteArray, 0, byteArray.length);
                                }
                                break;
                            } catch (Exception e) {
                            }
                            this.f367a.reset();
                        }
                    }
                }
                break;
            case 1:
                if (i3 >= 2 && (bArrDecode = Base64.decode(new String(bArr, i2, i3), 0)) != null && bArrDecode.length >= 2 && p.a(bArrDecode, 0, 2) == 17227) {
                    byte b = bArrDecode[3];
                    if (this.f378b) {
                        if (b < 2) {
                            this.f386e[3] = (byte) (b + 1);
                            p.a((int) System.currentTimeMillis(), this.f386e, 8);
                            b(this.f382c, a((Object) Base64.encode(this.f386e, 0), 1, 0, 0));
                        } else {
                            System.currentTimeMillis();
                            p.a(this.f386e, 8);
                        }
                    } else if (b != 1) {
                        p.logln(2, "oops wrong sync count: " + ((int) b));
                    } else {
                        this.f386e[3] = 2;
                        p.a((int) System.currentTimeMillis(), this.f386e, 8);
                        b(this.f382c, a((Object) Base64.encode(this.f386e, 0), 1, 0, 0));
                    }
                }
                break;
            case 2:
                if (z2) {
                    if (this.f374a) {
                        int iA2 = p.a(bArr, i2 + 2, 2);
                        if (this.f != 65535 && iA2 - this.f > 1 && Math.abs(this.d) > 3) {
                            p.logln(-3, "WS framedrop - seqNr: " + iA2 + " (pkt: " + this.d + "), last: " + this.f + " (pkt: " + this.g + ") " + i);
                        }
                        this.g = this.d;
                        this.f = iA2;
                        this.f366a.a(bArr, i2, i2 + i3);
                    } else {
                        this.f365a.a(bArr, i2, i2 + i3);
                    }
                } else {
                    p.a(2, "non terminated at " + i + " " + (bArr[i] & 255) + ", length" + (i3 - i2), bArr, 0, i2 + i3);
                    if (this.f367a == null) {
                        this.f367a = new ByteArrayOutputStream();
                    }
                    this.f367a.write(bArr, i2, i3);
                }
                break;
            case 8:
                if (i3 >= 2) {
                    int iA3 = p.a(bArr, i2, 2);
                    if (iA3 == 0 || (iA3 >= 1000 && iA3 <= 1015)) {
                        z = true;
                    }
                    if (z) {
                        p.logln(2, "WS - received CLOSE, cause: " + iA3);
                    }
                } else {
                    p.logln(2, "WS - received CLOSE, no data");
                }
                this.f383d = true;
                this.f381c = true;
                this.f365a.a(this);
                break;
            case NMJConfig.BLE /* 9 */:
                if (i3 > 0) {
                    if (this.f388g == null || this.f388g.length != i3) {
                        this.f388g = new byte[i3];
                    }
                    System.arraycopy(bArr, i2, this.f388g, 0, i3);
                    b(this.f384d, a((Object) this.f388g, 10, 0, 0));
                } else {
                    b(this.f384d, a(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR, 10, 0, 0));
                }
                break;
            case 10:
                if (i3 == 8 && p.m118a(bArr, i2) == p.m118a(this.f387f, 0)) {
                    System.currentTimeMillis();
                    p.m118a(this.f387f, 0);
                }
                break;
        }
        return i2 + i3;
    }

    private String a(boolean z) throws IOException {
        if (this.f371a == null) {
            this.f371a = new StringBuffer();
        }
        this.f371a.delete(0, this.f371a.length());
        int i = this.f368a.read();
        if (i == -1) {
            return null;
        }
        while (i != 10) {
            if (i != 13) {
                this.f371a.append((char) i);
            }
            i = this.f368a.read();
            if (i == -1) {
                return null;
            }
        }
        return this.f371a.toString();
    }

    protected final void a(byte[] bArr) throws IOException {
        if (this.f374a) {
            byte[][] bArrM114a = this.f366a.m114a(bArr, (int) System.currentTimeMillis());
            if (bArrM114a != null) {
                for (byte[] bArr2 : bArrM114a) {
                    b(this.f379b, a((Object) bArr2, 2, 0, 0));
                }
                return;
            }
            return;
        }
        b(this.f379b, a((Object) bArr, 2, 0, 0));
    }

    protected final void a(long j) throws IOException {
        p.a(j, this.f387f, 0);
        b(this.f384d, a((Object) this.f387f, 9, 0, 0));
    }

    protected final void b(long j) throws IOException {
        this.f386e[3] = 0;
        p.a((int) j, this.f386e, 8);
        b(this.f382c, a((Object) Base64.encode(this.f386e, 0), 1, 0, 0));
    }

    private synchronized void b(byte[] bArr, int i) throws IOException {
        if (i > 0) {
            if (this.f369a != null) {
                try {
                    this.f369a.write(bArr, 0, i);
                    this.f369a.flush();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    @Override // de.humatic.nmj.f
    public final void a(byte[] bArr, int i, int i2, long j) {
        this.f365a.a(this, bArr, j);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected final v m200a() {
        v vVar = new v();
        vVar.a = this.a;
        vVar.f329a = this.f377b;
        vVar.b = 6;
        try {
            vVar.f330b = this.f372a.getAddress().toString();
            vVar.c = this.f372a.getPort();
        } catch (Exception e) {
        }
        return vVar;
    }
}
