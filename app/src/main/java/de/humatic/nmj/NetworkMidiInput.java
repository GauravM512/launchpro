package de.humatic.nmj;

/* JADX INFO: loaded from: classes.dex */
public class NetworkMidiInput extends NetworkMidiPort {
    n a;

    public NetworkMidiInput() {
        this.b = true;
    }

    NetworkMidiInput(int i, NetworkMidiSystem networkMidiSystem, NetworkMidiClient networkMidiClient) throws Exception {
        super(i, networkMidiSystem, networkMidiClient);
        this.a = new n(i);
        if (networkMidiClient instanceof NetworkMidiListener) {
            addMidiListener((NetworkMidiListener) networkMidiClient);
        }
        this.f118a = false;
        this.f116a.m83a(this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // de.humatic.nmj.NetworkMidiPort
    public final NetworkMidiInput a(NetworkMidiClient networkMidiClient) {
        if (this.b) {
            return null;
        }
        if (!a(networkMidiClient) || !(networkMidiClient instanceof NetworkMidiListener)) {
            return this;
        }
        addMidiListener((NetworkMidiListener) networkMidiClient);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004c A[Catch: all -> 0x0049, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:10:0x000c, B:12:0x003a, B:14:0x0045, B:19:0x004c), top: B:21:0x0001 }] */
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
            de.humatic.nmj.n r0 = r3.a     // Catch: java.lang.Throwable -> L49
            r0.m117a()     // Catch: java.lang.Throwable -> L49
            java.util.Vector r0 = r3.f117a     // Catch: java.lang.Throwable -> L49
            r0.removeAllElements()     // Catch: java.lang.Throwable -> L49
            de.humatic.nmj.NetworkMidiSystem r0 = r3.f116a     // Catch: java.lang.Throwable -> L49
            r1 = 0
            int r2 = r3.a     // Catch: java.lang.Throwable -> L49
            r0.m84a(r1, r2)     // Catch: java.lang.Throwable -> L49
            r0 = 1
            r3.f118a = r0     // Catch: java.lang.Throwable -> L49
            goto L9
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.NetworkMidiInput.close(de.humatic.nmj.NetworkMidiClient):void");
    }

    @Override // de.humatic.nmj.NetworkMidiPort
    /* JADX INFO: renamed from: a */
    final boolean mo79a() {
        if (!this.b) {
            this.f117a.removeAllElements();
            if (this.a != null) {
                this.a.m117a();
            }
            this.f116a.m84a(NMJConfig.getIO(this.a), this.a);
        }
        return true;
    }

    @Override // de.humatic.nmj.NetworkMidiPort
    final void a(int i) {
        if (!this.b) {
            this.a = i;
            if (this.a != null) {
                this.a.a(i);
            }
        }
    }

    public void addMidiListener(NetworkMidiListener networkMidiListener) {
        if (networkMidiListener != null && !this.b) {
            this.a.a(networkMidiListener);
            try {
                a((NetworkMidiClient) networkMidiListener);
            } catch (Exception e) {
            }
        }
    }

    public void removeMidiListener(NetworkMidiListener networkMidiListener) {
        if (networkMidiListener != null && !this.b) {
            int iB = this.a.b(networkMidiListener);
            try {
                b(networkMidiListener);
            } catch (Exception e) {
            }
            if (iB != 0 || this.f117a.size() != 0) {
                return;
            }
            close(null);
        }
    }

    public void setLink(NetworkMidiOutput networkMidiOutput, boolean z) {
        if (!this.b) {
            if (!z) {
                removeMidiListener(networkMidiOutput.asMidiListener(false));
            } else {
                addMidiListener(networkMidiOutput.asMidiListener(true));
            }
        }
    }
}
