package de.humatic.nmj;

/* JADX INFO: loaded from: classes.dex */
public class NMJException extends RuntimeException {
    private int a;

    public NMJException(Throwable th) {
        super(th);
        this.a = 0;
    }

    public NMJException(String str) {
        super(str);
        this.a = 0;
    }

    public NMJException(String str, int i) {
        super(str);
        this.a = 0;
        this.a = i;
    }

    public int getErrorCode() {
        return this.a;
    }
}
