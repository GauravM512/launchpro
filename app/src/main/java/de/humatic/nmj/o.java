package de.humatic.nmj;

import android.os.Looper;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
final class o {
    private int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private ADBMidiIO f238a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private c f239a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private x f240a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private DatagramPacket f241a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private DatagramSocket f242a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private InetAddress f243a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private InetSocketAddress f244a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private MulticastSocket f245a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkInterface f246a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private boolean f247a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[] f248a;

    o(int i) throws Exception {
        this.a = i;
        String ip = NMJConfig.getIP(this.a);
        int port = NMJConfig.getPort(this.a);
        if (NMJConfig.getMode(this.a) == 2) {
            this.f239a = new c(this.a, null);
            return;
        }
        if (NMJConfig.getMode(this.a) == 4) {
            this.f238a = new ADBMidiIO(this.a, null);
            return;
        }
        if (NMJConfig.getMode(this.a) == 5) {
            this.f240a = new x(this.a, null);
            return;
        }
        int localPort = NMJConfig.getLocalPort(this.a);
        int networkAdapter = NMJConfig.getNetworkAdapter(this.a);
        this.f247a = true;
        try {
            this.f247a = new Integer(ip.substring(0, ip.indexOf("."))).intValue() >= 223;
        } catch (Exception e) {
        }
        if (!this.f247a) {
            try {
                if (NMJConfig.getMode(this.a) != 3) {
                    this.f242a = new DatagramSocket(localPort);
                } else {
                    this.f242a = p.a(networkAdapter, -1);
                }
                this.f241a = new DatagramPacket(new byte[16], 16, InetAddress.getByName(ip), port);
                NMJConfig.a(this.a, 1, 0);
                p.logln(2, "Unicast client up and running");
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        a(networkAdapter, port, ip);
    }

    private void a(int i, int i2, String str) throws IOException {
        p.logln(2, "Multicast server, opening adapter " + i);
        this.f245a = new MulticastSocket();
        if (i <= 0) {
            this.f246a = p.m120a();
        } else {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            for (int i3 = 0; networkInterfaces.hasMoreElements() && i3 < i; i3++) {
                this.f246a = networkInterfaces.nextElement();
            }
        }
        if (this.f246a == null) {
            throw new IOException("No interface");
        }
        try {
            this.f245a.setInterface(this.f246a.getInetAddresses().nextElement());
            this.f243a = InetAddress.getByName(str);
            try {
                this.f244a = new InetSocketAddress(this.f243a, 0);
                this.f245a.joinGroup(this.f244a, this.f246a);
                this.f241a = new DatagramPacket(new byte[256], 256, InetAddress.getByName(str), i2);
                p.logln(2, "Multicast server up and running via:" + this.f246a.toString() + " " + InetAddress.getByName(str) + " " + i2);
                NMJConfig.a(this.a, 1, 0);
            } catch (SecurityException e) {
                throw e;
            } catch (SocketException e2) {
                p.logln(2, "Multicast server, error on group join: " + e2.getMessage() + "\nParams:" + this.f246a.toString() + " " + InetAddress.getByName(str) + " " + i2);
                throw e2;
            }
        } catch (NoSuchElementException e3) {
            throw new IOException("No valid addresses on " + this.f246a);
        }
    }

    final void a(int i) {
        this.a = i;
    }

    final void a() {
        try {
            if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
                new Thread(new Runnable() { // from class: de.humatic.nmj.o.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        o.this.a();
                    }
                }).start();
                return;
            }
        } catch (Exception e) {
        }
        try {
            if (this.f247a) {
                this.f245a.leaveGroup(this.f244a, this.f246a);
                this.f245a.close();
            } else {
                this.f242a.close();
            }
            NMJConfig.a(this.a, 2, 0);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (this.f239a != null) {
                this.f239a.mo96a();
            } else if (this.f238a != null) {
                this.f238a.mo96a();
            } else if (this.f240a == null) {
                return;
            } else {
                this.f240a.mo96a();
            }
            NMJConfig.a(this.a, 2, 0);
        } catch (Exception e3) {
        }
    }

    final void a(byte[] bArr) throws Exception {
        if (this.f239a != null) {
            this.f239a.mo191a(bArr);
            return;
        }
        if (this.f238a != null) {
            this.f238a.mo191a(bArr);
            return;
        }
        if (this.f240a != null) {
            this.f240a.mo191a(bArr);
            return;
        }
        if (bArr.length < 256) {
            this.f241a.setData(bArr);
            if (!this.f247a) {
                this.f242a.send(this.f241a);
                return;
            } else {
                this.f245a.send(this.f241a);
                return;
            }
        }
        if (this.f248a == null) {
            this.f248a = new byte[1024];
        }
        int i = 0;
        while (i < bArr.length) {
            int iMin = Math.min(1024, bArr.length - i);
            System.arraycopy(bArr, i, this.f248a, 0, iMin);
            this.f241a.setData(this.f248a, 0, iMin);
            if (this.f247a) {
                this.f245a.send(this.f241a);
            } else {
                this.f242a.send(this.f241a);
            }
            i += iMin;
        }
    }
}
