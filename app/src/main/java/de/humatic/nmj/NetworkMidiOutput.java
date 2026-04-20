package de.humatic.nmj;

import android.os.Looper;
import java.lang.reflect.Array;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class NetworkMidiOutput extends NetworkMidiPort {
    private long a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkMidiInput f105a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkMidiListener f106a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private a f107a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private n f108a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private o f109a;
    private boolean c;

    public NetworkMidiOutput() {
        this.b = true;
    }

    NetworkMidiOutput(int i, NetworkMidiSystem networkMidiSystem, NetworkMidiClient networkMidiClient) throws Exception {
        super(i, networkMidiSystem, networkMidiClient);
        if (NMJConfig.getIO(i) > 0) {
            this.f109a = new o(i);
        } else {
            this.f105a = NetworkMidiSystem.get().openInput(i, networkMidiClient);
            if (this.f105a != null) {
                this.f108a = this.f105a.a;
            } else {
                return;
            }
        }
        this.f118a = false;
        this.a = Looper.getMainLooper().getThread().getId();
        if (this.f108a == null) {
            this.f116a.m83a(this.a);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004c A[Catch: all -> 0x0049, TRY_ENTER, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:10:0x000c, B:12:0x003a, B:14:0x0045, B:19:0x004c, B:21:0x0050, B:22:0x0055, B:23:0x0069, B:25:0x006d), top: B:27:0x0001 }] */
    @Override // de.humatic.nmj.NetworkMidiPort
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void close(de.humatic.nmj.NetworkMidiClient r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.f118a     // Catch: java.lang.Throwable -> L49
            if (r0 != 0) goto L9
            boolean r0 = r3.b     // Catch: java.lang.Throwable -> L49
            if (r0 == 0) goto Lb
        L9:
            monitor-exit(r3)
            return
        Lb:
            r0 = 5
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L49
            int r2 = r3.a     // Catch: java.lang.Throwable -> L49
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch: java.lang.Throwable -> L49
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L49
            java.lang.String r2 = " - Closing "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L49
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch: java.lang.Throwable -> L49
            java.lang.String r2 = " nr. clients: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L49
            java.util.Vector r2 = r3.f117a     // Catch: java.lang.Throwable -> L49
            int r2 = r2.size()     // Catch: java.lang.Throwable -> L49
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L49
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L49
            de.humatic.nmj.p.logln(r0, r1)     // Catch: java.lang.Throwable -> L49
            if (r4 == 0) goto L4c
            r3.b(r4)     // Catch: java.lang.Throwable -> L49
            java.util.Vector r0 = r3.f117a     // Catch: java.lang.Throwable -> L49
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L49
            if (r0 <= 0) goto L4c
            r3.mo79a()     // Catch: java.lang.Throwable -> L49
            goto L9
        L49:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L4c:
            de.humatic.nmj.o r0 = r3.f109a     // Catch: java.lang.Throwable -> L49
            if (r0 == 0) goto L69
            de.humatic.nmj.o r0 = r3.f109a     // Catch: java.lang.Throwable -> L49
            r0.a()     // Catch: java.lang.Throwable -> L49
        L55:
            java.util.Vector r0 = r3.f117a     // Catch: java.lang.Throwable -> L49
            r0.removeAllElements()     // Catch: java.lang.Throwable -> L49
            r0 = 1
            r3.f118a = r0     // Catch: java.lang.Throwable -> L49
            de.humatic.nmj.NetworkMidiSystem r0 = r3.f116a     // Catch: java.lang.Throwable -> L49
            r1 = 1
            int r2 = r3.a     // Catch: java.lang.Throwable -> L49
            r0.m84a(r1, r2)     // Catch: java.lang.Throwable -> L49
            r0 = 1
            r3.f118a = r0     // Catch: java.lang.Throwable -> L49
            goto L9
        L69:
            de.humatic.nmj.n r0 = r3.f108a     // Catch: java.lang.Throwable -> L49
            if (r0 == 0) goto L55
            de.humatic.nmj.NetworkMidiInput r0 = r3.f105a     // Catch: java.lang.Throwable -> L49
            r0.close(r4)     // Catch: java.lang.Throwable -> L49
            goto L55
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.NetworkMidiOutput.close(de.humatic.nmj.NetworkMidiClient):void");
    }

    @Override // de.humatic.nmj.NetworkMidiPort
    /* JADX INFO: renamed from: a */
    final boolean mo79a() {
        if (!this.b) {
            this.f117a.removeAllElements();
            if (this.f109a != null) {
                this.f109a.a();
            } else if (this.f108a != null) {
                this.f108a.m117a();
            }
            this.f116a.m84a(NMJConfig.getIO(this.a), this.a);
            this.f118a = true;
        }
        return true;
    }

    @Override // de.humatic.nmj.NetworkMidiPort
    final void a(int i) {
        if (!this.b) {
            this.a = i;
            if (this.f109a != null) {
                this.f109a.a(i);
            }
            if (this.f108a != null) {
                this.f108a.a(i);
            }
        }
    }

    public void sendMidi(byte[] bArr) throws Exception {
        if (!this.f118a && bArr != null && !this.b) {
            if (this.c) {
                a(bArr);
                return;
            }
            if (this.f109a != null) {
                this.f109a.a(bArr);
            }
            if (this.f108a != null) {
                this.f108a.a(bArr);
            }
        }
    }

    public void sendMidiOnThread(byte[] bArr) throws Exception {
        if (!this.b) {
            if (this.f107a == null) {
                this.f107a = new a(this, (byte) 0);
                this.f107a.start();
            }
            if (this.c) {
                a(bArr);
            } else {
                this.f107a.a(bArr);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(byte[] bArr) throws Exception {
        if (!this.f118a && bArr != null && !this.b) {
            if (this.f107a == null || Thread.currentThread().getId() != this.a) {
                if (this.f109a != null) {
                    this.f109a.a(bArr);
                }
                if (this.f108a != null) {
                    this.f108a.a(bArr);
                    return;
                }
                return;
            }
            this.f107a.a(bArr);
        }
    }

    public NetworkMidiListener asMidiListener(boolean z) {
        if (this.b) {
            return null;
        }
        this.c = z;
        if (z && this.f106a == null) {
            this.f106a = new NetworkMidiListener() { // from class: de.humatic.nmj.NetworkMidiOutput.1
                @Override // de.humatic.nmj.NetworkMidiListener
                public final void midiReceived(int i, int i2, byte[] bArr, long j) {
                    try {
                        NetworkMidiOutput.this.a(bArr);
                    } catch (Exception e) {
                        String lowerCase = e.toString().toLowerCase();
                        if (lowerCase.indexOf("closed") >= 0 || lowerCase.indexOf("cancelled") >= 0 || lowerCase.indexOf("interrupted") >= 0 || lowerCase.indexOf("bad file number") != -1) {
                            return;
                        }
                        e.printStackTrace();
                    }
                }
            };
        }
        return this.f106a;
    }

    class a extends Thread {
        private int a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private Object f111a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private Vector<byte[]> f112a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private byte[][] f113a;
        private int b;

        /* JADX INFO: renamed from: b, reason: collision with other field name */
        private byte[][] f114b;
        private int c;

        /* JADX INFO: renamed from: c, reason: collision with other field name */
        private byte[][] f115c;
        private int d;

        private a() {
            this.f112a = new Vector<>();
            this.f113a = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 32, 1);
            this.f114b = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 32, 2);
            this.f115c = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 64, 3);
            this.f111a = new Object();
        }

        /* synthetic */ a(NetworkMidiOutput networkMidiOutput, byte b) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void a(byte[] bArr) {
            byte[] bArr2;
            synchronized (this.f111a) {
                Vector<byte[]> vector = this.f112a;
                switch (bArr.length) {
                    case 1:
                        this.b++;
                        if (this.b >= this.f113a.length) {
                            this.b = 0;
                        }
                        System.arraycopy(bArr, 0, this.f113a[this.b], 0, bArr.length);
                        bArr2 = this.f113a[this.b];
                        break;
                    case 2:
                        this.c++;
                        if (this.c >= this.f114b.length - 1) {
                            this.c = 0;
                        }
                        System.arraycopy(bArr, 0, this.f114b[this.c], 0, bArr.length);
                        bArr2 = this.f114b[this.c];
                        break;
                    case 3:
                        this.d++;
                        if (this.d >= this.f115c.length) {
                            this.d = 0;
                        }
                        System.arraycopy(bArr, 0, this.f115c[this.d], 0, bArr.length);
                        bArr2 = this.f115c[this.d];
                        break;
                    default:
                        bArr2 = new byte[bArr.length];
                        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                        break;
                }
                vector.add(bArr2);
            }
            this.a = 1;
            notify();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            byte[] bArrRemove;
            while (!NetworkMidiOutput.this.f118a) {
                synchronized (this) {
                    while (this.a == 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
                try {
                    if (this.a == 1) {
                        synchronized (this.f111a) {
                            while (this.f112a.size() > 0 && (bArrRemove = this.f112a.remove(0)) != null) {
                                if (NetworkMidiOutput.this.f109a != null) {
                                    NetworkMidiOutput.this.f109a.a(bArrRemove);
                                }
                                if (NetworkMidiOutput.this.f108a != null) {
                                    NetworkMidiOutput.this.f108a.a(bArrRemove);
                                }
                            }
                        }
                    } else {
                        continue;
                    }
                } catch (Exception e2) {
                    if (e2.toString().indexOf("closed") != -1 || e2.toString().indexOf("sendto failed") != 1 || e2.toString().indexOf("EINVAL") != 1 || e2.toString().indexOf("EPIPE") != 1 || e2.toString().indexOf("ENETUNREACH") != 1) {
                        NMJConfig.a(NetworkMidiOutput.this.a, -2147483647, String.valueOf(e2.getMessage()) + ", closing");
                        try {
                            NetworkMidiOutput.this.close(null);
                        } catch (Exception e3) {
                        }
                    } else {
                        p.a(e2, "sendMidiOnThread");
                        NMJConfig.a(NetworkMidiOutput.this.a, NMJConfig.E_UNSPECIFIED, e2.getMessage());
                    }
                }
                this.a = 0;
            }
        }
    }
}
