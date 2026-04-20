package de.humatic.nmj.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.media.midi.MidiDeviceService;
import android.media.midi.MidiDeviceStatus;
import android.media.midi.MidiReceiver;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import de.humatic.nmj.NMJConfig;
import de.humatic.nmj.NMJSystemListener;
import de.humatic.nmj.NetworkMidiClient;
import de.humatic.nmj.NetworkMidiInput;
import de.humatic.nmj.NetworkMidiListener;
import de.humatic.nmj.NetworkMidiOutput;
import de.humatic.nmj.NetworkMidiSystem;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class NMJDeviceService extends MidiDeviceService implements NetworkMidiClient {
    public static final int AMM_INPUT = 272;
    public static final int AMM_STATE = 6;
    public static final int CHANNELS = 2;
    public static final int CONNECT = 0;
    public static final int ERROR_LOG = 7;
    public static final int LINK = 3;
    public static final int NMJ_ERROR = 17;
    public static final int NMJ_EVENT = 16;
    public static final int NMJ_INPUT = 256;
    public static final int NMJ_STATE = 5;
    public static final int PORTS = 1;
    public static final int SET_LINK = 4;
    private byte a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private SharedPreferences.Editor f270a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private SharedPreferences f271a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Messenger f272a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkMidiSystem f273a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private b f274a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private boolean f275a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private int[] f276a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private long[] f277a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private MidiReceiver[] f278a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkMidiInput[] f279a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkMidiOutput[] f280a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Exception[] f281a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Vector<Bundle>[] f282a;
    private byte b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private boolean f285b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private long[] f286b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private MidiReceiver[] f287b;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private boolean f288c;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private boolean f289d;
    private boolean e;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private int f269a = -1;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private int f283b = 3;
    private int c = 0;
    private int d = 4;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Messenger f284b = new Messenger(new d());

    @Override // android.media.midi.MidiDeviceService, android.app.Service
    public void onCreate() {
        byte b2 = 0;
        super.onCreate();
        if (Build.VERSION.SDK_INT < 23) {
            a(-1, "NMJDeviceService requires Android 6 or larger");
            return;
        }
        this.f271a = PreferenceManager.getDefaultSharedPreferences(this);
        this.f270a = this.f271a.edit();
        this.f275a = this.f271a.getLong("firstRun", -1L) == -1;
        this.f285b = this.f271a.getBoolean("reset", false);
        if (this.f275a || this.f285b) {
            try {
                Vector<NMJChannel> vectorM145a = m145a();
                this.d = vectorM145a.size();
                NMJConfig.edit(this, true);
                NMJConfig.setNumChannels(this.d);
                for (int i = 0; i < this.d; i++) {
                    if (vectorM145a.get(i).mode >= 0) {
                        NMJConfig.setMode(i, vectorM145a.get(i).mode);
                    }
                    NMJConfig.setName(i, vectorM145a.get(i).name);
                    NMJConfig.setIP(i, vectorM145a.get(i).ip);
                    if (vectorM145a.get(i).port > 0) {
                        NMJConfig.setPort(i, vectorM145a.get(i).port);
                    }
                    if (vectorM145a.get(i).localPort > 0) {
                        NMJConfig.setLocalPort(i, vectorM145a.get(i).localPort);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        m146a();
        if (this.f269a > 0) {
            this.f279a = new NetworkMidiInput[this.f269a];
            this.f280a = new NetworkMidiOutput[this.f269a];
            this.f276a = new int[this.f269a];
            this.f282a = new Vector[this.f269a];
            for (int i2 = 0; i2 < this.f269a; i2++) {
                this.f282a[i2] = new Vector<>();
            }
            this.f277a = new long[this.f269a];
            this.f286b = new long[this.f269a];
            int i3 = this.f269a;
            this.f281a = new Exception[this.f269a * 2];
            if (this.f278a == null) {
                this.f278a = new MidiReceiver[this.f269a];
                this.f287b = new MidiReceiver[this.f269a];
                for (int i4 = 0; i4 < this.f269a; i4++) {
                    this.f278a[i4] = new e(this, i4, "VIO in " + (i4 + 1), b2);
                }
            }
            new AsyncTask() { // from class: de.humatic.nmj.service.NMJDeviceService.1
                @Override // android.os.AsyncTask
                protected final Object doInBackground(Object... objArr) {
                    try {
                        NMJDeviceService.this.f273a = NetworkMidiSystem.get(this);
                        NMJDeviceService nMJDeviceService = NMJDeviceService.this;
                        NMJConfig.setBasePort(1, NMJDeviceService.this.f271a.getInt("rtp_base_port", 6100));
                        NMJConfig.setBasePort(6, NMJDeviceService.this.f271a.getInt("mws_base_port", 8100));
                        NMJConfig.setBasePort(4, NMJDeviceService.this.f271a.getInt("adb_base_port", 10100));
                        NMJConfig.setFlags(-1, NMJDeviceService.this.f271a.getInt("global_flags", NMJDeviceService.this.f283b));
                        NMJConfig.addSystemListener(new c(NMJDeviceService.this, (byte) 0));
                        return NMJDeviceService.this.f273a;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        return null;
                    }
                }

                @Override // android.os.AsyncTask
                protected final void onPostExecute(Object obj) {
                }
            }.execute(new Object[1]);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        try {
            if (this.f273a != null) {
                this.e = true;
                this.f273a.exit();
                this.d = this.f271a.getInt("defChCnt", 3);
                int[] iArr = new int[this.f269a * 2];
                boolean z = false;
                for (int i = 0; i < this.f269a; i++) {
                    if (this.f271a.getInt("nmj_in_" + i, 2) > this.d - 1) {
                        iArr[i << 1] = this.f271a.getInt("nmj_in_" + i, 2);
                        iArr[(i << 1) + 1] = this.f271a.getInt("nmj_out_" + i, 2);
                        z = true;
                    }
                }
                if (z) {
                    int[] iArrCleanup = NMJConfig.cleanup(this.d - 1, iArr);
                    for (int i2 = 0; i2 < this.f269a; i2++) {
                        if (iArrCleanup[i2 << 1] != -1) {
                            this.f270a.putInt("nmj_in_" + i2, iArrCleanup[i2 << 1]);
                        }
                        if (iArrCleanup[(i2 << 1) + 1] != -1) {
                            this.f270a.putInt("nmj_out_" + i2, iArrCleanup[(i2 << 1) + 1]);
                        }
                    }
                    this.f270a.commit();
                    return;
                }
                NMJConfig.cleanup(this.d - 1, null);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    class c implements NMJSystemListener {
        private c() {
        }

        /* synthetic */ c(NMJDeviceService nMJDeviceService, byte b) {
            this();
        }

        @Override // de.humatic.nmj.NMJSystemListener
        public final void systemChanged(int i, int i2, int i3) {
            int iA = NMJDeviceService.a(NMJDeviceService.this, i);
            if (i >= 0 && i2 == -1 && (i == NMJDeviceService.this.f271a.getInt("nmj_in_" + iA, -1) || i == NMJDeviceService.this.f271a.getInt("nmj_out_" + iA, -1))) {
                NMJDeviceService.this.f270a.putInt("nmj_in_" + iA, -1);
                NMJDeviceService.this.f270a.putInt("nmj_out_" + iA, -1);
                NMJDeviceService.this.f270a.commit();
                NMJDeviceService.this.m147a(iA);
            }
            if (i2 == 4 && iA >= 0 && iA < NMJDeviceService.this.f269a - 1) {
                switch (i3) {
                    case 1024:
                        NMJDeviceService.a(NMJDeviceService.this, iA, 17, new int[]{i, i3}, "Connection dropped");
                        break;
                    case 2048:
                        NMJDeviceService.a(NMJDeviceService.this, iA, 17, new int[]{i, i3}, "Connection refused by remote session");
                        break;
                    case 4096:
                        NMJDeviceService.a(NMJDeviceService.this, iA, 17, new int[]{i, i3}, "Remote side did not respond to invitation");
                        break;
                }
            }
            if (i2 == -1 || (i2 == 4 && i3 == 8)) {
                iA = -1;
            }
            NMJDeviceService.a(NMJDeviceService.this, iA, 16, new int[]{i, i2, i3}, null);
        }

        @Override // de.humatic.nmj.NMJSystemListener
        public final void systemError(int i, int i2, String str) {
            NMJDeviceService.a(-1, " Error on ch " + i + ", code: " + i2 + ", desc: " + str);
            NMJDeviceService.a(NMJDeviceService.this, NMJDeviceService.a(NMJDeviceService.this, i), 17, new int[]{i, i2}, str);
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private static NMJChannel[] m156a() {
        int numChannels = NMJConfig.getNumChannels();
        Vector vector = new Vector();
        for (int i = 0; i < numChannels; i++) {
            try {
                vector.add(new NMJChannel(i));
            } catch (Exception e2) {
            }
        }
        NMJChannel[] nMJChannelArr = new NMJChannel[vector.size()];
        vector.copyInto(nMJChannelArr);
        return nMJChannelArr;
    }

    static /* synthetic */ int a(NMJDeviceService nMJDeviceService, int i, int i2) {
        if (i2 == 0) {
            return nMJDeviceService.f271a.getInt("nmj_in_" + i, a(i, i2));
        }
        if (i2 == 1) {
            return nMJDeviceService.f271a.getInt("nmj_out_" + i, a(i, i2));
        }
        return 0;
    }

    private static int a(int i, int i2) {
        int numChannels = NMJConfig.getNumChannels();
        int i3 = -1;
        for (int i4 = 0; i4 < numChannels; i4++) {
            if (NMJConfig.getIO(i4) == i2 || NMJConfig.getIO(i4) < 0) {
                i3++;
            }
            if (i3 == i) {
                return i4;
            }
        }
        return -1;
    }

    static /* synthetic */ int a(NMJDeviceService nMJDeviceService, int i) {
        int i2 = 0;
        while (i2 < nMJDeviceService.f269a) {
            if (nMJDeviceService.f271a.getInt("nmj_in_" + i2, -1) == i || nMJDeviceService.f271a.getInt("nmj_out_" + i2, -1) == i) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0031 A[Catch: all -> 0x0041, TRY_ENTER, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:23:0x0031, B:25:0x0038, B:30:0x0044, B:7:0x0009, B:10:0x000e, B:12:0x0012, B:19:0x0024, B:14:0x0018, B:16:0x001c), top: B:32:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void a(final int r5, final int r6, boolean r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            de.humatic.nmj.NetworkMidiSystem r0 = r4.f273a     // Catch: java.lang.Throwable -> L41
            if (r0 == 0) goto L9
            byte r0 = r4.a     // Catch: java.lang.Throwable -> L41
            if (r0 != 0) goto L31
        L9:
            de.humatic.nmj.NetworkMidiSystem r0 = r4.f273a     // Catch: java.lang.Throwable -> L41
            if (r0 != 0) goto L22
            r0 = 0
        Le:
            de.humatic.nmj.NetworkMidiSystem r1 = r4.f273a     // Catch: java.lang.Throwable -> L41
            if (r1 != 0) goto L18
            int r1 = r0 + 1
            r2 = 50
            if (r0 < r2) goto L24
        L18:
            de.humatic.nmj.NetworkMidiSystem r0 = r4.f273a     // Catch: java.lang.Throwable -> L41
            if (r0 != 0) goto L31
            r0 = 1
            java.lang.String r1 = "NetworkMidiSystem is and remains to be null"
            a(r0, r1)     // Catch: java.lang.Throwable -> L41
        L22:
            monitor-exit(r4)
            return
        L24:
            java.lang.Thread.currentThread()     // Catch: java.lang.Exception -> L2e java.lang.Throwable -> L41
            r2 = 10
            java.lang.Thread.sleep(r2)     // Catch: java.lang.Exception -> L2e java.lang.Throwable -> L41
            r0 = r1
            goto Le
        L2e:
            r0 = move-exception
            r0 = r1
            goto Le
        L31:
            de.humatic.nmj.service.NMJDeviceService$2 r0 = new de.humatic.nmj.service.NMJDeviceService$2     // Catch: java.lang.Throwable -> L41
            r0.<init>()     // Catch: java.lang.Throwable -> L41
            if (r7 == 0) goto L44
            java.lang.Thread r1 = new java.lang.Thread     // Catch: java.lang.Throwable -> L41
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L41
            r1.start()     // Catch: java.lang.Throwable -> L41
            goto L22
        L41:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L44:
            r0.run()     // Catch: java.lang.Throwable -> L41
            goto L22
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.service.NMJDeviceService.a(int, int, boolean):void");
    }

    @Override // android.media.midi.MidiDeviceService
    public MidiReceiver[] onGetInputPortReceivers() {
        if (this.f278a == null) {
            try {
                m146a();
                if (this.f269a <= 0) {
                    return null;
                }
                this.f278a = new MidiReceiver[this.f269a];
                this.f287b = new MidiReceiver[this.f269a];
                for (int i = 0; i < this.f269a; i++) {
                    this.f278a[i] = new e(this, i, "nmj in " + (i + 1), (byte) 0);
                }
            } catch (Exception e2) {
            }
        }
        return this.f278a;
    }

    @Override // android.media.midi.MidiDeviceService
    public void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            a(midiDeviceStatus);
            return;
        }
        if (this.f274a == null) {
            this.f274a = new b(this, (byte) 0);
            this.f274a.start();
        }
        this.f274a.a(midiDeviceStatus);
    }

    class b extends Thread {

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private Vector<MidiDeviceStatus> f293a;

        private b() {
            this.f293a = new Vector<>();
        }

        /* synthetic */ b(NMJDeviceService nMJDeviceService, byte b) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void a(MidiDeviceStatus midiDeviceStatus) {
            this.f293a.add(midiDeviceStatus);
            notify();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            while (!NMJDeviceService.this.e) {
                synchronized (this) {
                    while (this.f293a.size() == 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
                try {
                    NMJDeviceService.this.a(this.f293a.remove(0));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MidiDeviceStatus midiDeviceStatus) {
        if (midiDeviceStatus.getDeviceInfo().getInputPortCount() > this.f269a || midiDeviceStatus.getDeviceInfo().getOutputPortCount() > this.f269a) {
            Log.e("nmj", "Invalid port count in MidiDeviceInfo");
        }
        for (int i = 0; i < this.f269a; i++) {
            byte b2 = this.a;
            int i2 = this.c;
            if (midiDeviceStatus.getOutputPortOpenCount(i) > 0 && (this.a & (1 << ((i * 2) + 1))) == 0) {
                this.a = (byte) (this.a | (1 << ((i * 2) + 1)));
                this.f287b[i] = getOutputPortReceivers()[i];
                a(1, i, false);
            } else if (midiDeviceStatus.getOutputPortOpenCount(i) == 0 && (this.a & (1 << ((i * 2) + 1))) != 0) {
                if (this.f279a[i] != null) {
                    try {
                        this.f279a[i].close(null);
                        this.f279a[i] = null;
                        this.c &= (1 << (i * 2)) ^ (-1);
                    } catch (Exception e2) {
                    }
                }
                this.a = (byte) (this.a & ((1 << ((i * 2) + 1)) ^ (-1)));
            }
            if (midiDeviceStatus.isInputPortOpen(i) && (this.a & (1 << (i * 2))) == 0) {
                this.a = (byte) (this.a | (1 << (i * 2)));
                a(0, i, false);
            } else if (!midiDeviceStatus.isInputPortOpen(i) && (this.a & (1 << (i * 2))) != 0) {
                if (this.f280a[i] != null) {
                    try {
                        this.f280a[i].close(null);
                        this.f280a[i] = null;
                        this.c &= (1 << ((i * 2) + 1)) ^ (-1);
                    } catch (Exception e3) {
                    }
                }
                this.a = (byte) (this.a & ((1 << (i * 2)) ^ (-1)));
            }
            if (this.a != b2) {
                a(i, 6, this.a);
            }
            if (this.c != i2) {
                a(i, 5, this.c);
            }
        }
    }

    class d extends Handler {
        d() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Message messageObtain;
            int i = 0;
            int i2 = message.getData().getInt("port", -1);
            switch (message.what) {
                case 0:
                    NMJDeviceService.this.f272a = message.replyTo;
                    Message messageObtain2 = Message.obtain();
                    messageObtain2.what = 0;
                    while (NMJDeviceService.this.f273a == null) {
                        int i3 = i + 1;
                        if (i < 50) {
                            try {
                                Thread.currentThread();
                                Thread.sleep(10L);
                                i = i3;
                            } catch (Exception e) {
                                i = i3;
                            }
                        } else {
                            NMJDeviceService.m148a(NMJDeviceService.this);
                            messageObtain = messageObtain2;
                            break;
                        }
                    }
                    NMJDeviceService.m148a(NMJDeviceService.this);
                    messageObtain = messageObtain2;
                    break;
                case 1:
                    messageObtain = NMJDeviceService.createMessage(-1, 1, new Integer(NMJDeviceService.this.f269a));
                    break;
                case 2:
                    messageObtain = Message.obtain();
                    messageObtain.what = 2;
                    messageObtain.setData(NMJDeviceService.this.a());
                    break;
                case 3:
                    NMJDeviceService.this.m147a(i2);
                    messageObtain = null;
                    break;
                case 4:
                    Bundle data = message.getData();
                    if (i2 < 0) {
                        Log.e("nmj", "can't select channel for port -1");
                        messageObtain = null;
                    } else {
                        int[] intArray = data.getIntArray("value");
                        if (NMJDeviceService.this.f279a[i2] != null && NMJDeviceService.this.f279a[i2].getChannelIndex() != intArray[0]) {
                            try {
                                NMJDeviceService.this.f279a[i2].close(null);
                                NMJDeviceService.this.f279a[i2] = null;
                                break;
                            } catch (Exception e2) {
                            }
                        }
                        if (NMJDeviceService.this.f280a[i2] != null && NMJDeviceService.this.f280a[i2].getChannelIndex() != intArray[1]) {
                            try {
                                NMJDeviceService.this.f280a[i2].close(null);
                                NMJDeviceService.this.f280a[i2] = null;
                                break;
                            } catch (Exception e3) {
                            }
                        }
                        NMJDeviceService.this.f270a.putInt("nmj_in_" + i2, intArray[0]);
                        NMJDeviceService.this.f270a.putInt("nmj_out_" + i2, intArray[1]);
                        NMJDeviceService.this.f270a.commit();
                        if (intArray[0] >= 0 || intArray[1] >= 0) {
                            NMJDeviceService.this.a(-1, i2, true);
                            messageObtain = null;
                        } else {
                            messageObtain = null;
                        }
                    }
                    break;
                case 5:
                    for (int i4 = 0; i4 < NMJDeviceService.this.f269a; i4++) {
                        if (NMJDeviceService.this.f279a[i4] != null) {
                            NMJDeviceService.this.c |= 1 << (i4 << 1);
                        }
                        if (NMJDeviceService.this.f280a[i4] != null) {
                            NMJDeviceService.this.c |= 1 << ((i4 << 1) + 1);
                        }
                        NMJDeviceService.this.a(i4, 5, NMJDeviceService.this.c);
                    }
                    messageObtain = null;
                    break;
                case 6:
                    for (int i5 = 0; i5 < NMJDeviceService.this.f269a; i5++) {
                        NMJDeviceService.this.a(i5, 6, NMJDeviceService.this.a);
                    }
                    messageObtain = null;
                    break;
                case 7:
                    NMJDeviceService.this.b(i2);
                    messageObtain = null;
                    break;
                case 256:
                    NMJDeviceService.this.f288c = message.getData().getInt("value", 0) != 0;
                    messageObtain = null;
                    break;
                case NMJDeviceService.AMM_INPUT /* 272 */:
                    NMJDeviceService.this.f289d = message.getData().getInt("value", 0) != 0;
                    messageObtain = null;
                    break;
                default:
                    super.handleMessage(message);
                    messageObtain = null;
                    break;
            }
            if (messageObtain != null && NMJDeviceService.this.f272a != null) {
                try {
                    NMJDeviceService.this.f272a.send(messageObtain);
                } catch (RemoteException e4) {
                    e4.printStackTrace();
                }
            }
        }
    }

    @Override // android.media.midi.MidiDeviceService, android.app.Service
    public IBinder onBind(Intent intent) {
        IBinder iBinderOnBind = null;
        if (Build.VERSION.SDK_INT >= 23 && this.f269a > 0) {
            a(2, "MidiDeviceService, bind request: " + intent + " " + intent.getPackage() + "  " + intent.getExtras() + " " + this.f269a);
            if (intent.getAction() == null) {
                iBinderOnBind = this.f284b.getBinder();
            } else if (intent.getAction().equalsIgnoreCase("android.media.midi.MidiDeviceService")) {
                iBinderOnBind = super.onBind(intent);
            }
            a(2, "MidiDeviceService, binder: " + iBinderOnBind);
        }
        return iBinderOnBind;
    }

    static /* synthetic */ void a(NMJDeviceService nMJDeviceService, int i, int i2, int[] iArr, String str) {
        if (nMJDeviceService.f272a == null && i2 != 17) {
            return;
        }
        Bundle bundle = new Bundle();
        if (iArr != null) {
            bundle.putIntArray("de.humatic.nmj.service.EVT", iArr);
        }
        if (str != null) {
            bundle.putString("de.humatic.nmj.service.MSG", str);
        }
        if (nMJDeviceService.f272a == null || i2 == 17) {
            bundle.putLong("de.humatic.nmj.service.TS", System.currentTimeMillis());
            bundle.putInt("port", i);
            bundle.putInt("type", 17);
            int i3 = i < 0 ? 0 : i;
            while (true) {
                if (i3 >= (i < 0 ? nMJDeviceService.f269a : i + 1)) {
                    break;
                }
                if (!nMJDeviceService.a(i < 0 ? i3 : i, bundle)) {
                    nMJDeviceService.f282a[i < 0 ? i3 : i].add(bundle);
                }
                i3++;
            }
            if (nMJDeviceService.f272a == null) {
                return;
            }
        }
        bundle.putInt("type", i2);
        int i4 = i >= 0 ? i : 0;
        while (true) {
            if (i4 >= (i < 0 ? nMJDeviceService.f269a : i + 1)) {
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("value", bundle);
            bundle2.putInt("port", i < 0 ? i4 : i);
            Message messageObtain = Message.obtain();
            messageObtain.what = i2;
            messageObtain.setData(bundle2);
            try {
                nMJDeviceService.f272a.send(messageObtain);
            } catch (RemoteException e2) {
                e2.printStackTrace();
                nMJDeviceService.f272a = null;
            }
            i4++;
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static /* synthetic */ void m148a(NMJDeviceService nMJDeviceService) {
        if (nMJDeviceService.f272a != null) {
            try {
                nMJDeviceService.f272a.send(createMessage(-1, 1, new Integer(nMJDeviceService.f269a)));
                Message messageObtain = Message.obtain();
                messageObtain.what = 2;
                messageObtain.setData(nMJDeviceService.a());
                nMJDeviceService.f272a.send(messageObtain);
                for (int i = 0; i < nMJDeviceService.f269a; i++) {
                    nMJDeviceService.m147a(i);
                    if (nMJDeviceService.f279a[i] != null) {
                        nMJDeviceService.c |= 1 << (i << 1);
                    } else {
                        nMJDeviceService.c &= (1 << (i << 1)) ^ (-1);
                    }
                    if (nMJDeviceService.f280a[i] != null) {
                        nMJDeviceService.c |= 1 << ((i << 1) + 1);
                    } else {
                        nMJDeviceService.c &= (1 << ((i << 1) + 1)) ^ (-1);
                    }
                    nMJDeviceService.a(i, 5, nMJDeviceService.c);
                    nMJDeviceService.a(i, 6, nMJDeviceService.a);
                    nMJDeviceService.b(i);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                nMJDeviceService.f272a = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, int i2, int i3) {
        if (this.f272a != null && i >= 0) {
            Message messageObtain = Message.obtain();
            messageObtain.what = i2;
            Bundle bundle = new Bundle();
            bundle.putInt("port", i);
            bundle.putInt("value", (i3 >> (i * 2)) & 3);
            messageObtain.setData(bundle);
            try {
                this.f272a.send(messageObtain);
            } catch (RemoteException e2) {
                e2.printStackTrace();
                this.f272a = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public void m147a(int i) {
        if (this.f272a != null) {
            int i2 = i < 0 ? 0 : i;
            while (true) {
                if (i2 < (i < 0 ? this.f269a : i + 1)) {
                    Message messageObtain = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("port", i2);
                    int i3 = this.f271a.getInt("nmj_in_" + i2, -1);
                    if (i3 >= NMJConfig.getNumChannels()) {
                        this.f270a.putInt("nmj_in_" + i2, -1);
                        this.f270a.commit();
                        i3 = -1;
                    }
                    int i4 = this.f271a.getInt("nmj_out_" + i2, -1);
                    if (i4 >= NMJConfig.getNumChannels()) {
                        this.f270a.putInt("nmj_out_" + i2, -1);
                        this.f270a.commit();
                        i4 = -1;
                    }
                    bundle.putIntArray("value", new int[]{i3, i4});
                    messageObtain.what = 3;
                    messageObtain.setData(bundle);
                    try {
                        this.f272a.send(messageObtain);
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        this.f272a = null;
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i) {
        if (this.f272a != null) {
            int i2 = i < 0 ? 0 : i;
            while (true) {
                if (i2 < (i < 0 ? this.f269a : i + 1)) {
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 7;
                    if (this.f282a[i2].size() != 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("port", i2);
                        Bundle[] bundleArr = new Bundle[this.f282a[i2].size()];
                        this.f282a[i2].copyInto(bundleArr);
                        this.f282a[i2].removeAllElements();
                        bundle.putParcelableArray("value", bundleArr);
                        messageObtain.setData(bundle);
                        try {
                            this.f272a.send(messageObtain);
                        } catch (RemoteException e2) {
                            e2.printStackTrace();
                            this.f272a = null;
                        }
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    private boolean a(int i, Bundle bundle) {
        for (int i2 = 0; i2 < this.f282a[i].size(); i2++) {
            Bundle bundle2 = this.f282a[i].get(i2);
            if (bundle2.containsKey("de.humatic.nmj.service.MSG") && bundle2.getString("de.humatic.nmj.service.MSG").equalsIgnoreCase(bundle.getString("de.humatic.nmj.service.MSG")) && bundle2.containsKey("de.humatic.nmj.service.EVT") && bundle.containsKey("de.humatic.nmj.service.EVT") && bundle2.getIntArray("de.humatic.nmj.service.EVT")[0] == bundle.getIntArray("de.humatic.nmj.service.EVT")[0]) {
                if (bundle.getLong("de.humatic.nmj.service.TS") - bundle2.getLong("de.humatic.nmj.service.TS") < 200) {
                    return true;
                }
                if (bundle.getLong("de.humatic.nmj.service.TS") - bundle2.getLong("de.humatic.nmj.service.TS") < 3000) {
                    bundle2.putLong("de.humatic.nmj.service.TS", bundle.getLong("de.humatic.nmj.service.TS"));
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle a() {
        NMJChannel[] nMJChannelArrM156a = m156a();
        Bundle bundle = new Bundle();
        Bundle[] bundleArr = new Bundle[nMJChannelArrM156a.length];
        for (int i = 0; i < nMJChannelArrM156a.length; i++) {
            NMJChannel nMJChannel = nMJChannelArrM156a[i];
            Bundle bundle2 = new Bundle();
            bundle2.putInt("id", nMJChannel.id);
            bundle2.putString("name", nMJChannel.name);
            bundle2.putInt("mode", nMJChannel.mode);
            bundle2.putString("ip", nMJChannel.ip);
            bundle2.putInt("io", nMJChannel.io);
            bundle2.putInt("port", nMJChannel.port);
            bundle2.putInt("local_port", nMJChannel.localPort);
            bundle2.putInt("flags", nMJChannel.flags);
            bundle2.putInt("nwa", nMJChannel.nwa);
            bundleArr[i] = bundle2;
        }
        bundle.putParcelableArray("value", bundleArr);
        return bundle;
    }

    class e extends MidiReceiver {
        private int a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private Vector<byte[]> f295a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private byte[][] f296a;
        private int b;

        /* JADX INFO: renamed from: b, reason: collision with other field name */
        private byte[][] f297b;
        private int c;

        /* JADX INFO: renamed from: c, reason: collision with other field name */
        private byte[][] f298c;
        private int d;
        private int e;

        private e(int i, String str) {
            super(1460);
            new StringBuffer();
            this.a = 0;
            this.f296a = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 32, 3);
            this.f297b = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 32, 2);
            this.f298c = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 8, 1);
            this.a = i;
        }

        /* synthetic */ e(NMJDeviceService nMJDeviceService, int i, String str, byte b) {
            this(i, str);
        }

        /* JADX WARN: Removed duplicated region for block: B:83:0x0198 A[PHI: r0
  0x0198: PHI (r0v58 byte[]) = (r0v53 byte[]), (r0v56 byte[]), (r0v66 byte[]) binds: [B:53:0x00ff, B:49:0x00e5, B:28:0x0095] A[DONT_GENERATE, DONT_INLINE]] */
        @Override // android.media.midi.MidiReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onSend(byte[] r11, int r12, int r13, long r14) throws java.io.IOException {
            /*
                Method dump skipped, instruction units count: 412
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.service.NMJDeviceService.e.onSend(byte[], int, int, long):void");
        }
    }

    class a implements NetworkMidiListener {
        private int a;

        private a(int i) {
            this.a = i;
        }

        /* synthetic */ a(NMJDeviceService nMJDeviceService, int i, byte b) {
            this(i);
        }

        @Override // de.humatic.nmj.NetworkMidiListener
        public final void midiReceived(int i, int i2, byte[] bArr, long j) {
            try {
                NMJDeviceService.this.f287b[this.a].send(bArr, 0, bArr.length);
                if (NMJDeviceService.this.f288c && NMJDeviceService.this.f272a != null && j > NMJDeviceService.this.f277a[this.a] + 50 && this.a != -1) {
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 256;
                    Bundle bundle = new Bundle();
                    bundle.putInt("port", this.a);
                    messageObtain.setData(bundle);
                    NMJDeviceService.this.f277a[this.a] = j;
                    try {
                        NMJDeviceService.this.f272a.send(messageObtain);
                    } catch (RemoteException e) {
                        NMJDeviceService.this.f272a = null;
                        e.printStackTrace();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0067, code lost:
    
        r5.f269a = r2;
     */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized void m146a() {
        /*
            r5 = this;
            monitor-enter(r5)
            int r0 = r5.f269a     // Catch: java.lang.Throwable -> L6f
            if (r0 < 0) goto L7
        L5:
            monitor-exit(r5)
            return
        L7:
            android.content.res.Resources r0 = r5.getResources()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> La1
            android.content.res.Resources r1 = r5.getResources()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> La1
            java.lang.String r2 = "nmj"
            java.lang.String r3 = "xml"
            java.lang.String r4 = r5.getPackageName()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> La1
            int r1 = r1.getIdentifier(r2, r3, r4)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> La1
            android.content.res.XmlResourceParser r3 = r0.getXml(r1)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> La1
            int r0 = r3.getEventType()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> La1
            java.lang.String r1 = ""
            r2 = r0
        L26:
            r0 = 1
            if (r2 != r0) goto L72
        L29:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            java.lang.String r1 = r5.getPackageName()     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            r0.<init>(r1)     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            java.lang.String r1 = ".R$xml"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            java.lang.reflect.Field[] r1 = r0.getDeclaredFields()     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            r0 = 0
        L49:
            int r2 = r1.length     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            if (r0 >= r2) goto L5
            r2 = r1[r0]     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            java.lang.String r3 = "nmj"
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            if (r2 != 0) goto La6
            r2 = r1[r0]     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            r3 = 0
            int r2 = r2.getInt(r3)     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            int r2 = r5.a(r2)     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            if (r2 <= 0) goto La6
            r5.f269a = r2     // Catch: java.lang.Exception -> L6a java.lang.Throwable -> L6f
            goto L5
        L6a:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L6f
            goto L5
        L6f:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        L72:
            r0 = 2
            if (r2 == r0) goto L8e
            r0 = 3
            if (r2 != r0) goto L94
            java.lang.String r0 = r3.getName()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L9c
            java.lang.String r4 = "debug"
            boolean r0 = r0.equalsIgnoreCase(r4)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L9c
            if (r0 == 0) goto L8e
            int r0 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L9c
            byte r4 = (byte) r0     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L9c
            r5.b = r4     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L9c
            de.humatic.nmj.NMJConfig.setDebugLevel(r0)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L9c
        L8e:
            int r0 = r3.next()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L9c
            r2 = r0
            goto L26
        L94:
            r0 = 4
            if (r2 != r0) goto L8e
            java.lang.String r1 = r3.getText()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L9c
            goto L8e
        L9c:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> La1
            goto L26
        La1:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L6f
            goto L29
        La6:
            int r0 = r0 + 1
            goto L49
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.service.NMJDeviceService.m146a():void");
    }

    private int a(int i) {
        int i2 = 1;
        int i3 = 0;
        if (i <= 0) {
            return 1;
        }
        try {
            XmlResourceParser xml = getResources().getXml(i);
            int i4 = 0;
            int eventType = xml.getEventType();
            while (eventType != i2) {
                if (eventType == 2) {
                    try {
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (xml.getName().equalsIgnoreCase("device")) {
                        String attributeValue = xml.getAttributeValue(null, "private");
                        if ((attributeValue == null || !Boolean.parseBoolean(attributeValue)) && getPackageName().indexOf("de.humatic") != 0) {
                            a(-1, "NMJDeviceService subclasses must be application private. Please add a private=\"true\" attribute to your device info's <device/> node.");
                            this.f269a = 0;
                            i2 = -1;
                            return -1;
                        }
                    } else if (xml.getName().equalsIgnoreCase("input-port")) {
                        i4++;
                    } else if (xml.getName().equalsIgnoreCase("output-port")) {
                        i3++;
                    }
                }
                eventType = xml.next();
            }
            return Math.max(i4, i3);
        } catch (Exception e3) {
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x022a A[Catch: Exception -> 0x0223, TRY_ENTER, TryCatch #1 {Exception -> 0x0223, blocks: (B:3:0x0005, B:6:0x002e, B:83:0x022a, B:85:0x022e, B:86:0x0239, B:88:0x023d, B:89:0x0245, B:78:0x021e, B:12:0x0039, B:14:0x0045, B:15:0x0057, B:18:0x0062, B:20:0x0068, B:22:0x0070, B:24:0x007d, B:25:0x00a6, B:27:0x00ae, B:28:0x00b4, B:30:0x00bc, B:32:0x00c7, B:34:0x00cc, B:39:0x00d8, B:41:0x00e0, B:42:0x00eb, B:44:0x00f3, B:45:0x00fe, B:47:0x0106, B:48:0x0111, B:50:0x0119, B:51:0x0124, B:53:0x012c, B:54:0x0137, B:56:0x013f, B:57:0x014a, B:61:0x0155, B:62:0x0176, B:64:0x017e, B:65:0x01c7, B:67:0x01cf, B:68:0x01f1, B:70:0x01f9, B:72:0x0203, B:75:0x0214), top: B:94:0x0005, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x025b A[PHI: r2
  0x025b: PHI (r2v10 boolean) = (r2v4 boolean), (r2v4 boolean), (r2v4 boolean), (r2v4 boolean), (r2v4 boolean), (r2v18 boolean), (r2v4 boolean) binds: [B:13:0x0043, B:74:0x0212, B:69:0x01f7, B:71:0x0201, B:58:0x0150, B:60:0x0153, B:23:0x007b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.Vector<de.humatic.nmj.service.NMJChannel> m145a() {
        /*
            Method dump skipped, instruction units count: 608
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.service.NMJDeviceService.m145a():java.util.Vector");
    }

    public static Message createMessage(int i, int i2, Object obj) {
        Message messageObtain = Message.obtain();
        messageObtain.what = i2;
        Bundle bundle = new Bundle();
        if (i >= 0) {
            bundle.putInt("port", i);
        }
        if (obj != null) {
            if (obj instanceof Integer) {
                bundle.putInt("value", ((Integer) obj).intValue());
            } else if (obj instanceof int[]) {
                bundle.putIntArray("value", (int[]) obj);
            } else if (obj instanceof String) {
                bundle.putString("value", obj.toString());
            } else if (obj instanceof Parcelable) {
                bundle.putParcelable("value", (Parcelable) obj);
            } else if (obj instanceof Parcelable[]) {
                bundle.putParcelableArray("value", (Parcelable[]) obj);
            }
        }
        messageObtain.setData(bundle);
        return messageObtain;
    }

    static void a(int i, String str) {
        try {
            Method declaredMethod = Class.forName("de.humatic.nmj.p").getDeclaredMethod("logln", Integer.TYPE, String.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(null, Integer.valueOf(i), str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
