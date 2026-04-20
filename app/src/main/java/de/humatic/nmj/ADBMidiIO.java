package de.humatic.nmj;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/* JADX INFO: loaded from: classes.dex */
final class ADBMidiIO extends w {
    private int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private ADBServer f8a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private InputStream f9a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private OutputStream f10a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String f11a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Socket f12a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ADBMidiIO(int i, n nVar) throws Exception {
        super(nVar, i);
        byte b2 = 0;
        this.f11a = "ADB ";
        this.f11a = String.valueOf(this.f11a) + i;
        for (int i2 = 0; i2 < this.c; i2++) {
            if (NMJConfig.getMode(i2) == 4) {
                this.a++;
            }
        }
        new Thread(new a(this, b2)).start();
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final void mo96a() {
        this.f332a = true;
        NMJConfig.a(this.c, 4, 128);
        try {
            this.f12a.close();
        } catch (Exception e) {
        }
        try {
            ADBServer.a(this.f8a);
        } catch (Exception e2) {
        }
        p.logln(1, "ADBMidi closed");
    }

    class a extends Thread {

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private byte[] f14a;

        private a() {
            this.f14a = new byte[1560];
        }

        /* synthetic */ a(ADBMidiIO aDBMidiIO, byte b) {
            this();
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x011a, code lost:
        
            r8.a.f12a.close();
            de.humatic.nmj.NMJConfig.a(r8.a.c, 4, 128);
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instruction units count: 355
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.ADBMidiIO.a.run():void");
        }
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final void mo191a(byte[] bArr) throws IOException {
        if (this.f10a != null) {
            if (bArr.length <= this.d) {
                this.f10a.write(bArr);
                this.f10a.flush();
                return;
            }
            int iMin = 0;
            while (iMin < bArr.length) {
                this.f10a.write(bArr, iMin, Math.min(this.d, bArr.length - iMin));
                iMin += Math.min(this.d, bArr.length - iMin);
                this.f10a.flush();
            }
        }
    }

    class b extends Thread {

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private int[] f15a;

        private b() {
            this.f15a = new int[]{1460, 1024, 512, 256, 128, 32, 16};
        }

        /* synthetic */ b(ADBMidiIO aDBMidiIO, byte b) {
            this();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                sleep(2000L);
                ADBMidiIO.this.mo191a(p.m130a(0, 0));
                for (int i = 0; i < this.f15a.length; i++) {
                    ADBMidiIO.this.f10a.write(p.m130a(1, this.f15a[i]));
                    ADBMidiIO.this.f10a.flush();
                    int i2 = 0;
                    while (ADBMidiIO.this.d == 8) {
                        int i3 = i2 + 1;
                        if (i2 >= 5) {
                            break;
                        }
                        sleep(100L);
                        i2 = i3;
                    }
                    if (ADBMidiIO.this.d != 8) {
                        break;
                    }
                }
                ADBMidiIO.this.mo191a(p.m130a(0, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ADBServer {
        private int a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private ServerSocket f13a;

        private ADBServer(ADBMidiIO aDBMidiIO, int i, int i2) throws Exception {
            this.a = i2;
            this.f13a = new ServerSocket(i2);
            this.f13a.setReuseAddress(true);
        }

        /* synthetic */ ADBServer(ADBMidiIO aDBMidiIO, int i, int i2, byte b) throws Exception {
            this(aDBMidiIO, i, i2);
        }

        protected final Socket a() throws IOException {
            return this.f13a.accept();
        }

        /* JADX INFO: renamed from: a, reason: collision with other method in class */
        static /* synthetic */ boolean m10a(ADBServer aDBServer) {
            return aDBServer.f13a.isClosed();
        }

        public String toString() {
            return "ADBServer " + hashCode() + ", port: " + this.a;
        }

        static /* synthetic */ void a(ADBServer aDBServer) throws IOException {
            aDBServer.f13a.close();
        }
    }
}
