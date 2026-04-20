package de.humatic.nmj;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Looper;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class NetworkMidiSystem {
    private static Context a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static WifiManager.MulticastLock f119a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    static boolean f122a;
    private static boolean b;
    private static boolean c;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Vector<NetworkMidiInput> f123a = new Vector<>();

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Vector<NetworkMidiOutput> f124b = new Vector<>();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static NetworkMidiSystem f121a = new NetworkMidiSystem();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static a f120a = new a();

    NetworkMidiSystem() {
    }

    public static NetworkMidiSystem get(Context context) throws Exception {
        if (context == null) {
            throw new IllegalArgumentException("Context can not be null");
        }
        if (!context.equals(a)) {
            if (context.toString().indexOf("NMJConfigDialog") != -1) {
                try {
                    if (a.getApplicationContext().equals(context.getApplicationContext())) {
                        return f121a;
                    }
                } catch (Exception e) {
                }
            }
            f120a = new a();
            f119a = null;
            b = false;
            c = false;
        }
        f122a = false;
        if (!b || c) {
            p.logln(2, "NetworkMidiSystem init");
            if (!c) {
                a = context;
                p.m132b();
                if (f119a == null) {
                    try {
                        WifiManager.MulticastLock multicastLockCreateMulticastLock = ((WifiManager) context.getSystemService("wifi")).createMulticastLock("nmj_wifi_lock");
                        f119a = multicastLockCreateMulticastLock;
                        multicastLockCreateMulticastLock.setReferenceCounted(true);
                        p.logln(2, "Multicast lock aquired " + f119a);
                    } catch (Exception e2) {
                    }
                }
                NMJConfig.a(context);
            }
            b = true;
            if (c) {
                c = false;
            }
        }
        return f121a;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static WifiManager m81a() {
        return (WifiManager) a.getSystemService("wifi");
    }

    static WifiManager.MulticastLock a() {
        return f119a;
    }

    public static NetworkMidiSystem get() {
        return f121a;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static NetworkMidiSystem m82a() throws Exception {
        c = true;
        f122a = false;
        return get(a);
    }

    public synchronized NetworkMidiInput openInput(int i, NetworkMidiClient networkMidiClient) throws Exception {
        NetworkMidiInput networkMidiInput;
        synchronized (this) {
            if (i >= 0) {
                if (i <= NMJConfig.getNumChannels()) {
                    if ((NMJConfig.getMode(i) & 240) != 0) {
                        throw new IllegalArgumentException("Not a MIDI channel");
                    }
                    if (NMJConfig.getIO(i) > 0) {
                        throw new IllegalArgumentException("Channel not configured for input");
                    }
                    if (!NMJConfig.canOpen(i)) {
                        if (!(NMJConfig.getMode(i) <= 1 && (NMJConfig.getConnectivity(a) & 3) != 0)) {
                            throw new IOException("Channel " + i + " - can't open, no connectivity or missing permission (" + NMJConfig.getMode(i) + " / " + p.a((short) NMJConfig.getConnectivity(a)) + ")");
                        }
                    }
                    if (networkMidiClient == null) {
                        networkMidiClient = f120a;
                    }
                    Iterator<NetworkMidiInput> it = this.f123a.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            NetworkMidiInput next = it.next();
                            if (next.a == i) {
                                networkMidiInput = next.a(networkMidiClient);
                                break;
                            }
                        } else {
                            networkMidiInput = new NetworkMidiInput(i, this, networkMidiClient);
                            this.f123a.add(networkMidiInput);
                            break;
                        }
                    }
                }
            }
            throw new IndexOutOfBoundsException();
        }
        return networkMidiInput;
    }

    public synchronized NetworkMidiOutput openOutput(int i, NetworkMidiClient networkMidiClient) throws Exception {
        NetworkMidiOutput networkMidiOutput;
        if (i >= 0) {
            if (i <= NMJConfig.getNumChannels()) {
                if ((NMJConfig.getMode(i) & 240) != 0) {
                    throw new IllegalArgumentException("Not a MIDI channel");
                }
                if (NMJConfig.getIO(i) == 0) {
                    throw new IllegalArgumentException("Channel not configured for output");
                }
                if (!NMJConfig.canOpen(i)) {
                    throw new IOException("Channel " + i + " - can't open, no connectivity or missing permission (" + NMJConfig.getMode(i) + " / " + p.a((short) NMJConfig.getConnectivity(a)) + ")");
                }
                if (networkMidiClient == null) {
                    networkMidiClient = f120a;
                }
                Iterator<NetworkMidiOutput> it = this.f124b.iterator();
                while (true) {
                    if (it.hasNext()) {
                        NetworkMidiOutput next = it.next();
                        if (next.a == i) {
                            networkMidiOutput = (NetworkMidiOutput) next.a(networkMidiClient);
                            break;
                        }
                    } else {
                        networkMidiOutput = new NetworkMidiOutput(i, this, networkMidiClient);
                        this.f124b.add(networkMidiOutput);
                        break;
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
        return networkMidiOutput;
    }

    public boolean isOpen(int i, int i2) {
        if (i <= 0) {
            Iterator<NetworkMidiInput> it = this.f123a.iterator();
            while (it.hasNext()) {
                if (it.next().a == i2) {
                    return true;
                }
            }
        }
        if (Math.abs(i) == 1) {
            Iterator<NetworkMidiOutput> it2 = this.f124b.iterator();
            while (it2.hasNext()) {
                if (it2.next().a == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    final NetworkMidiPort a(int i) {
        if (NMJConfig.getIO(i) <= 0) {
            for (NetworkMidiInput networkMidiInput : this.f123a) {
                if (networkMidiInput.a == i) {
                    return networkMidiInput;
                }
            }
        } else {
            for (NetworkMidiOutput networkMidiOutput : this.f124b) {
                if (networkMidiOutput.a == i) {
                    return networkMidiOutput;
                }
            }
        }
        return null;
    }

    final NetworkMidiPort a(int i, int i2) {
        if (i <= 0) {
            for (NetworkMidiInput networkMidiInput : this.f123a) {
                if (networkMidiInput.a == i2) {
                    return networkMidiInput;
                }
            }
        } else {
            for (NetworkMidiOutput networkMidiOutput : this.f124b) {
                if (networkMidiOutput.a == i2) {
                    return networkMidiOutput;
                }
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final void m83a(final int i) {
        new Thread(new Runnable(this) { // from class: de.humatic.nmj.NetworkMidiSystem.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    Thread.sleep(20L);
                } catch (InterruptedException e) {
                }
                NMJConfig.m37b(i);
            }
        }).start();
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final void m84a(int i, final int i2) {
        try {
            if (i == 0) {
                for (NetworkMidiInput networkMidiInput : this.f123a) {
                    if (networkMidiInput.a == i2) {
                        this.f123a.remove(networkMidiInput);
                    }
                }
            } else {
                for (NetworkMidiOutput networkMidiOutput : this.f124b) {
                    if (networkMidiOutput.a == i2) {
                        this.f124b.remove(networkMidiOutput);
                    }
                }
            }
            new Thread(new Runnable(this) { // from class: de.humatic.nmj.NetworkMidiSystem.2
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        Thread.sleep(20L);
                    } catch (InterruptedException e) {
                    }
                    NMJConfig.m45c(i2);
                }
            }).start();
        } catch (Exception e) {
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final boolean m85a(int i) {
        for (int i2 = 0; i2 < this.f123a.size(); i2++) {
            if (this.f123a.get(i2).a == i) {
                this.f123a.get(i2).mo79a();
            }
        }
        for (int i3 = 0; i3 < this.f123a.size(); i3++) {
            NetworkMidiInput networkMidiInput = this.f123a.get(i3);
            if (networkMidiInput.a > i) {
                networkMidiInput.a(networkMidiInput.a - 1);
            }
        }
        for (int i4 = 0; i4 < this.f124b.size(); i4++) {
            if (this.f124b.get(i4).a == i) {
                this.f124b.get(i4).mo79a();
            }
        }
        for (int i5 = 0; i5 < this.f124b.size(); i5++) {
            NetworkMidiOutput networkMidiOutput = this.f124b.get(i5);
            if (networkMidiOutput.a > i) {
                networkMidiOutput.a(networkMidiOutput.a - 1);
            }
        }
        return true;
    }

    public void exit() {
        try {
            if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
                new Thread(new Runnable() { // from class: de.humatic.nmj.NetworkMidiSystem.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        NetworkMidiSystem.this.exit();
                    }
                }).start();
                int i = 0;
                while (b) {
                    int i2 = i + 1;
                    if (i < 10) {
                        try {
                            Thread.currentThread();
                            Thread.sleep(100L);
                            i = i2;
                        } catch (InterruptedException e) {
                            i = i2;
                        }
                    } else {
                        return;
                    }
                }
                return;
            }
        } catch (Exception e2) {
        }
        f122a = true;
        try {
            for (int size = this.f123a.size() - 1; size >= 0; size--) {
                try {
                    this.f123a.get(size).close(null);
                } catch (Exception e3) {
                }
            }
        } catch (Exception e4) {
        }
        try {
            for (int size2 = this.f124b.size() - 1; size2 >= 0; size2--) {
                try {
                    this.f124b.get(size2).close(null);
                } catch (Exception e5) {
                }
            }
        } catch (Exception e6) {
        }
        try {
            if (f119a != null) {
                f119a.release();
                f119a = null;
                p.logln(5, "Multicast lock released");
            }
        } catch (Exception e7) {
        }
        NMJConfig.m18a();
        if (b) {
            p.logln(2, "NetworkMidiSystem closed");
        }
        b = false;
    }

    static class a implements NetworkMidiClient {
        a() {
        }
    }
}
