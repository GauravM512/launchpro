package de.humatic.nmj;

/* JADX INFO: loaded from: classes.dex */
final class q {
    private int a;

    protected q(int i) {
        this.a = i;
    }

    protected final String a() {
        return NMJConfig.getName(this.a);
    }

    public final String toString() {
        return "NMC@" + hashCode() + ", " + NMJConfig.getName(this.a) + ", " + NMJConfig.getIP(this.a);
    }
}
