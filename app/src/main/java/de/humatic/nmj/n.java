package de.humatic.nmj;

import android.net.wifi.WifiManager;
import android.os.Looper;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
final class n {
    private int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private long f212a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private WifiManager.MulticastLock f213a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private w f214a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private DatagramPacket f215a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    DatagramSocket f216a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private InetAddress f217a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private InetSocketAddress f218a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private MulticastSocket f219a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkInterface f220a;
    private int b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private DatagramPacket f226b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private DatagramSocket f227b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private boolean f229b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private byte[] f230b;
    private int c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private byte[] f233c;
    private int d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private boolean f235d;
    private int e;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private boolean f237e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private boolean f222a = true;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[][][] f225a = (byte[][][]) Array.newInstance((Class<?>) byte[].class, 70, 10);

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[] f223a = new byte[70];

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[][] f224a = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 32, 1);

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private byte[][] f231b = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 32, 2);

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private byte[][] f234c = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 64, 3);

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Vector<Byte> f221a = new Vector<>();

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Vector<NetworkMidiListener> f228b = new Vector<>();

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private byte[] f236d = new byte[256];

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private boolean f232c = false;

    n(int i) throws Exception {
        w a2;
        this.d = 4031;
        this.a = i;
        this.b = NMJConfig.getIO(this.a);
        this.c = NMJConfig.getMode(this.a);
        if (this.c > 0 && this.c != 3) {
            int i2 = this.c;
            int i3 = this.a;
            switch (i2) {
                case 1:
                    a2 = new t(i3, this);
                    break;
                case 2:
                    a2 = new c(i3, this);
                    break;
                case 3:
                default:
                    a2 = null;
                    break;
                case 4:
                    a2 = new ADBMidiIO(i3, this);
                    break;
                case 5:
                    a2 = new x(i3, this);
                    break;
                case 6:
                    a2 = new A(i3, this);
                    break;
                case 7:
                    a2 = new y(i3, this);
                    break;
            }
            this.f214a = a2;
            return;
        }
        String ip = NMJConfig.getIP(this.a);
        int port = NMJConfig.getPort(this.a);
        if (ip != null && ip.indexOf(":") != -1) {
            port = Integer.parseInt(ip.split(":")[1]);
            ip = ip.split(":")[0];
        }
        this.d = NMJConfig.getLocalPort(this.a);
        int networkAdapter = NMJConfig.getNetworkAdapter(this.a);
        if (this.c == 0) {
            this.f229b = true;
            try {
                this.f229b = new Integer(ip.substring(0, ip.indexOf("."))).intValue() >= 223;
            } catch (Exception e) {
            }
            if (!this.f229b) {
                a(networkAdapter, port, ip);
                return;
            } else {
                b(networkAdapter, port, ip);
                return;
            }
        }
        if (this.c == 3) {
            this.d = 9004;
            a(networkAdapter, 9000, ip);
        }
    }

    private void a(int i, int i2, String str) throws Exception {
        byte b = 0;
        if (this.f216a == null && this.f227b == null) {
            if (this.b == 1) {
                this.f227b = p.a(i, -1);
            } else if (this.b == 0) {
                this.f216a = p.a(i, this.d);
            } else if (this.b < 0) {
                this.f227b = p.a(i, -1);
                this.f216a = p.a(i, this.d);
            }
            this.f215a = new DatagramPacket(new byte[1500], 1500);
            if (Math.abs(this.b) == 1) {
                this.f226b = new DatagramPacket(new byte[1500], 1500, InetAddress.getByName(str), i2);
            }
            if (this.b <= 0 && this.c == 3) {
                this.f216a.setBroadcast(true);
                this.f216a.connect(new InetSocketAddress(9002));
                p.logln(1, "NMJClient " + this.a + " connected to " + this.f216a.getRemoteSocketAddress() + " broadcast enabled: " + this.f216a.getBroadcast());
            } else {
                p.logln(1, "NMJClient " + this.a + " up and running on port " + this.d);
            }
            p.logln(2, "NMJClient " + this.a + " up and running on port " + this.d);
            if (Math.abs(this.b) == 1) {
                p.logln(2, "NMJClient output " + this.a + " connected to " + InetAddress.getByName(str) + ":" + i2);
            }
            NMJConfig.a(this.a, 1, 0);
            new Thread(new a(this, b)).start();
        }
    }

    private void b(int i, int i2, String str) throws Exception {
        byte b = 0;
        p.logln(2, "opening multicast " + i);
        this.f219a = new MulticastSocket(i2);
        this.f219a.setTimeToLive(255);
        if (i > 0) {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            for (int i3 = 0; networkInterfaces.hasMoreElements() && i3 < i; i3++) {
                this.f220a = networkInterfaces.nextElement();
            }
        } else {
            this.f220a = p.m120a();
        }
        if (this.f220a == null) {
            throw new IOException("Failed to get interface " + (i - 1));
        }
        try {
            Enumeration<InetAddress> inetAddresses = this.f220a.getInetAddresses();
            InetAddress inetAddressNextElement = null;
            while (inetAddresses.hasMoreElements()) {
                inetAddressNextElement = inetAddresses.nextElement();
                if (inetAddressNextElement instanceof Inet4Address) {
                    break;
                }
            }
            if (inetAddressNextElement != null && (inetAddressNextElement instanceof Inet6Address)) {
                throw new IOException("Multicast client: No valid addresses on interface.");
            }
            this.f219a.setNetworkInterface(this.f220a);
            if (inetAddressNextElement == null) {
                throw new IOException("No valid addresses on " + this.f220a);
            }
            this.f219a.setInterface(inetAddressNextElement);
            this.f217a = InetAddress.getByName(str);
            this.f213a = NetworkMidiSystem.m81a().createMulticastLock("nmj_multicast_lock_" + this.a);
            this.f213a.setReferenceCounted(true);
            this.f213a.acquire();
            p.logln(2, "aquired multicast lock " + this.f213a);
            this.f218a = new InetSocketAddress(this.f217a, 0);
            this.f219a.joinGroup(this.f218a, this.f220a);
            p.logln(2, "NMJClient " + this.a + " up and running on: " + this.f220a + " port " + i2 + " joined group at " + this.f217a);
            NMJConfig.a(this.a, 1, 0);
            this.f215a = new DatagramPacket(new byte[1500], 1500);
            new Thread(new a(this, b)).start();
        } catch (NoSuchElementException e) {
            throw new IOException("No valid addresses on " + this.f220a);
        }
    }

    final int a(NetworkMidiListener networkMidiListener) {
        if (!this.f228b.contains(networkMidiListener)) {
            this.f228b.add(networkMidiListener);
        }
        return this.f228b.size();
    }

    final int b(NetworkMidiListener networkMidiListener) {
        if (this.f228b.contains(networkMidiListener)) {
            this.f228b.remove(networkMidiListener);
        }
        return this.f228b.size();
    }

    final void a(int i) {
        this.a = i;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x0081 -> B:44:0x0059). Please report as a decompilation issue!!! */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final void m117a() {
        try {
            if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
                new Thread(new Runnable() { // from class: de.humatic.nmj.n.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        n.this.m117a();
                    }
                }).start();
                return;
            }
        } catch (Exception e) {
        }
        this.f222a = false;
        if (!this.f232c) {
            try {
                if (this.c == 0 || this.c == 3) {
                    try {
                        if (this.c != 3 && this.f229b) {
                            if (this.f213a != null) {
                                try {
                                    this.f213a.release();
                                    this.f213a = null;
                                } catch (Exception e2) {
                                }
                            }
                            this.f219a.leaveGroup(this.f218a, this.f220a);
                            this.f219a.close();
                        } else {
                            try {
                                this.f227b.close();
                            } catch (Exception e3) {
                            }
                            try {
                                this.f216a.close();
                            } catch (Exception e4) {
                            }
                            this.f227b = null;
                            this.f216a = null;
                        }
                    } catch (Exception e5) {
                        p.logln(1, "NMJClient.close(): " + e5.getMessage());
                    }
                } else if (this.f214a != null) {
                    this.f214a.mo96a();
                }
                this.f228b.removeAllElements();
                NMJConfig.a(this.a, 2, 0);
                this.f232c = true;
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
    }

    class a extends Thread {
        private a() {
            n.this.f222a = true;
        }

        /* synthetic */ a(n nVar, byte b) {
            this();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            while (n.this.f222a) {
                try {
                    n.this.f215a.setLength(1500);
                    if (n.this.f229b) {
                        n.this.f219a.receive(n.this.f215a);
                    } else {
                        n.this.f216a.receive(n.this.f215a);
                    }
                    n.this.a(n.this.f215a.getData(), n.this.f215a.getLength());
                } catch (Exception e) {
                    String string = e.toString();
                    if (string.indexOf("closed") == -1 && string.indexOf("system call was cancelled") == -1 && string.indexOf("Interrupted system call") == -1 && string.indexOf("setsockopt failed: ENODEV") == -1 && string.indexOf("socket argument is not a valid") == -1) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected final void a(byte[] bArr, int i) {
        a(bArr, 0, i);
    }

    protected final void a(byte[] bArr, int i, int i2) {
        byte[] bArr2;
        this.f212a = System.currentTimeMillis();
        while (i < i2) {
            if ((bArr[i] & 128) != 0) {
                if ((bArr[i] & 255) == 240) {
                    this.f235d = true;
                    this.f237e = false;
                    b(bArr[i], this.f212a);
                } else if ((bArr[i] & 255) == 247) {
                    if (this.f235d) {
                        b(bArr[i], this.f212a);
                    }
                    this.f235d = false;
                    this.f237e = false;
                } else {
                    this.i = p.b(bArr, i) + 1;
                    switch (this.i) {
                        case 1:
                            this.e++;
                            if (this.e >= this.f224a.length) {
                                this.e = 0;
                            }
                            bArr2 = this.f224a[this.e];
                            break;
                        case 2:
                            this.f++;
                            if (this.f >= this.f231b.length) {
                                this.f = 0;
                            }
                            bArr2 = this.f231b[this.f];
                            break;
                        case 3:
                            this.g++;
                            if (this.g >= this.f234c.length) {
                                this.g = 0;
                            }
                            bArr2 = this.f234c[this.g];
                            break;
                        default:
                            bArr2 = new byte[3];
                            break;
                    }
                    this.f233c = bArr2;
                    this.h = 0;
                    a(bArr[i], this.f212a);
                    this.h = 1;
                }
                i++;
            } else {
                if (this.f235d) {
                    b(bArr[i], this.f212a);
                } else {
                    a(bArr[i], this.f212a);
                }
                i++;
            }
        }
    }

    private void a(byte b, long j) {
        if (this.f233c != null && this.h < this.f233c.length) {
            this.f233c[this.h] = b;
            this.h++;
            if (this.h == this.i) {
                a(this.a, -1, this.f233c, j);
                this.h = 1;
            }
        }
    }

    private void b(byte b, long j) {
        byte[] bArr;
        boolean z = (b & 255) == 247;
        if (this.f237e) {
            byte[] bArr2 = this.f236d;
            int i = this.j;
            this.j = i + 1;
            bArr2[i] = b;
            if (z) {
                if (this.f214a != null && (this.f236d[0] & 255) == 240 && p.m129a(this.f236d)) {
                    p.logln(3, "mnet SysEx assembled, length " + this.j + " type " + (p.a(this.f236d) >> 8));
                    if ((p.a(this.f236d) >> 8) == 1) {
                        int iMax = Math.max(this.f214a.d, p.b(this.f236d));
                        if (iMax != this.f214a.d) {
                            p.logln(1, "client - maxPktSize " + iMax);
                        }
                        this.f214a.d = iMax;
                    } else if ((p.a(this.f236d) >> 8) == 0) {
                        int iB = p.b(this.f236d);
                        if (iB == 0) {
                            p.logln(3, "assembled - New " + (this.c == 2 ? "bluettoth" : "adb") + " connection");
                            NMJConfig.a(this.a, 4, 32);
                        } else if (iB == 1) {
                            p.logln(3, "assembled - Connection closed by client");
                            this.f214a.b();
                        }
                    }
                }
                for (int i2 = 0; i2 < this.j; i2++) {
                    this.f236d[i2] = 0;
                }
                this.f221a.removeAllElements();
                return;
            }
            return;
        }
        this.f221a.add(new Byte(b));
        if (this.f221a.size() == 8 && p.a(this.f221a)) {
            for (int i3 = 0; i3 < 8; i3++) {
                this.f236d[i3] = this.f221a.get(i3).byteValue();
            }
            this.j = 8;
            this.f237e = true;
        }
        if (z) {
            if (this.f221a.size() < this.f225a.length) {
                int size = this.f221a.size();
                byte[] bArr3 = this.f223a;
                bArr3[size] = (byte) (bArr3[size] + 1);
                if (this.f223a[size] > 9) {
                    this.f223a[size] = 0;
                }
                if (this.f225a[size][this.f223a[size]] == null) {
                    this.f225a[size][this.f223a[size]] = new byte[size];
                }
                bArr = this.f225a[size][this.f223a[size]];
            } else {
                bArr = new byte[this.f221a.size()];
            }
            for (int i4 = 0; i4 < this.f221a.size(); i4++) {
                bArr[i4] = this.f221a.get(i4).byteValue();
            }
            this.f221a.removeAllElements();
            a(this.a, -1, bArr, j);
        }
    }

    protected final void a(int i, int i2, byte[] bArr, long j) {
        Iterator<NetworkMidiListener> it = this.f228b.iterator();
        while (it.hasNext()) {
            it.next().midiReceived(i, i2, bArr, j);
        }
    }

    final void a(byte[] bArr) throws Exception {
        if (this.f222a) {
            if (this.c == 0 || this.c == 3) {
                if (bArr.length < 1024) {
                    this.f226b.setData(bArr, 0, bArr.length);
                    this.f227b.send(this.f226b);
                    return;
                }
                if (this.f230b == null) {
                    this.f230b = new byte[1024];
                }
                int i = 0;
                while (i < bArr.length) {
                    int iMin = Math.min(1024, bArr.length - i);
                    System.arraycopy(bArr, i, this.f230b, 0, iMin);
                    this.f226b.setData(this.f230b, 0, iMin);
                    this.f227b.send(this.f226b);
                    try {
                        Thread.currentThread();
                        Thread.sleep(15L);
                    } catch (Exception e) {
                    }
                    i += iMin;
                }
                return;
            }
            if (this.f214a != null) {
                this.f214a.mo191a(bArr);
            }
        }
    }

    final w a() {
        return this.f214a;
    }

    final void b() {
        if (this.f214a != null) {
            this.f214a.d();
        }
    }
}
