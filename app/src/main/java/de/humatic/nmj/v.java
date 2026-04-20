package de.humatic.nmj;

/* JADX INFO: loaded from: classes.dex */
final class v {
    public int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    String f329a;
    int b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    String f330b;
    int c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    String f331c;
    int d;
    int e;
    int f;
    int g;

    v() {
    }

    v(int i) {
        this.a = i;
        this.f329a = NMJConfig.getName(this.a);
        this.f330b = NMJConfig.getIP(this.a);
        this.b = NMJConfig.getMode(this.a);
        this.c = NMJConfig.getPort(this.a);
        this.d = NMJConfig.getLocalPort(this.a);
        this.e = NMJConfig.getFlags(this.a);
        this.f = NMJConfig.getRTPState(this.a);
        this.g = NMJConfig.getNetworkAdapter(this.a);
        if (this.b != 1 || this.f330b == null) {
            return;
        }
        this.f331c = NMJConfig.a(this.a, "localClientName", (String) null);
    }
}
