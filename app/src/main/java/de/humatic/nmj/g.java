package de.humatic.nmj;

/* JADX INFO: loaded from: classes.dex */
final class g {
    int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    String f158a;
    int b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    String f159b;
    String c;
    String d;
    String e;

    protected g(String str, int i) {
        byte[] bArr = {-30, -128, -103};
        this.f159b = str;
        this.a = i;
    }

    final void a(String str) {
        if (str.indexOf(" _") == -1) {
            this.f158a = str;
        } else {
            this.f158a = str.substring(0, str.indexOf(" _"));
        }
    }

    public final boolean equals(Object obj) {
        try {
            g gVar = (g) obj;
            if (gVar.f159b != null && this.f159b != null && !gVar.f159b.equalsIgnoreCase(this.f159b)) {
                return false;
            }
            if (gVar.c != null && this.c != null && !gVar.c.equals(this.c)) {
                return false;
            }
            if (gVar.d != null && this.d != null && !gVar.d.equals(this.d)) {
                return false;
            }
            if (gVar.f158a != null && this.f158a != null && !gVar.f158a.equals(this.f158a)) {
                return false;
            }
            if (gVar.e == null || this.e == null || gVar.e.equals(this.e)) {
                return gVar.b == this.b;
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("=========================\nName: ");
        stringBuffer.append(this.f158a);
        stringBuffer.append("\nServiceName: ");
        stringBuffer.append(this.f159b);
        stringBuffer.append("\nDomain: ");
        stringBuffer.append(this.e == null ? "local" : this.e);
        stringBuffer.append("\nIP4: ");
        stringBuffer.append(this.c);
        stringBuffer.append("\nIP6: ");
        stringBuffer.append(this.d);
        stringBuffer.append("\nPort: ");
        stringBuffer.append(this.b);
        stringBuffer.append("\n=========================\n");
        return stringBuffer.toString();
    }
}
