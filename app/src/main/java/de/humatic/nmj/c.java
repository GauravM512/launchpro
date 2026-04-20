package de.humatic.nmj;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
final class c extends w {
    static int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    static BroadcastReceiver f135a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    static Vector<BluetoothDevice> f136a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static final String[] f137a = {"1E873BF0-4349-4b0f-9835-1117C7E55C8D", "D74754C7-17EB-4350-AEF9-A048188D10AB", "105BEADD-0FC0-44e6-B46F-831DE171B5E6", "A35EC3BD-8A57-4e94-8B7A-777217BF3CA3"};
    static int b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private static BluetoothAdapter f138b;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private BluetoothAdapter f139a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private a f140a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private C0004c f141a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private InputStream f142a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private OutputStream f143a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String f144a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[] f145a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private boolean f146b;
    private Vector c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private boolean f147c;
    private Vector<de.humatic.nmj.b> d;
    private int e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    c(int i, n nVar) throws Exception {
        super(nVar, i);
        byte b2 = 0;
        this.f146b = true;
        new Vector();
        this.c = new Vector();
        this.f145a = new byte[]{-16, 126, 127, 6, 2, 127, 0, 0, 0, 0, 0, 0, 0, 0, -9};
        this.f144a = "nmj Bluetooth MIDI ch.";
        this.f144a = String.valueOf(this.f144a) + i;
        this.f139a = BluetoothAdapter.getDefaultAdapter();
        if (this.f139a == null) {
            throw new IOException("Bluetooth not available");
        }
        p.a(NMJConfig.m17a(0), this.f145a, 10);
        p.a(NMJConfig.m17a(1), this.f145a, 12);
        if (NMJConfig.getIP(this.c) == null) {
            this.d = new Vector<>();
            this.f141a = new C0004c();
            new Thread(this.f141a).start();
            return;
        }
        BluetoothDevice remoteDevice = this.f139a.getRemoteDevice(NMJConfig.getIP(this.c));
        UUID uuidFromString = UUID.fromString(f137a[NMJConfig.getPort(this.c)]);
        BluetoothSocket bluetoothSocketCreateRfcommSocketToServiceRecord = remoteDevice.createRfcommSocketToServiceRecord(uuidFromString);
        p.logln(1, "BluetoothSocket created " + bluetoothSocketCreateRfcommSocketToServiceRecord.getRemoteDevice().toString() + " " + uuidFromString.toString());
        if (bluetoothSocketCreateRfcommSocketToServiceRecord == null) {
            throw new IOException("Failed to create BluetoothSocket");
        }
        this.f139a.cancelDiscovery();
        bluetoothSocketCreateRfcommSocketToServiceRecord.connect();
        this.e = 3;
        this.f140a = new a(this, bluetoothSocketCreateRfcommSocketToServiceRecord, b2);
        new Thread(this.f140a).start();
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected final void mo96a() {
        p.logln(1, "btio close");
        this.c.removeAllElements();
        try {
            mo191a(p.m130a(0, 1));
            this.f143a.write(new byte[]{-16, 125, 110, 109, 106, 0, 1, -9});
        } catch (Exception e) {
        }
        this.e = 4;
        try {
            this.f141a.a();
        } catch (Exception e2) {
        }
        try {
            this.f140a.a();
        } catch (Exception e3) {
        }
    }

    public final synchronized void a(BluetoothSocket bluetoothSocket, BluetoothDevice bluetoothDevice) {
        try {
            this.d.add(new de.humatic.nmj.b(this, this.d.size(), bluetoothDevice.getName(), bluetoothDevice.getAddress(), NMJConfig.a(this.c, 2)));
            p.logln(1, String.valueOf(this.c) + " - bluetooth client connected, uuid: \n" + f137a[NMJConfig.a(this.c, 2)]);
            NMJConfig.a(this.c, 4, 64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NMJConfig.a(this.c, 4, 32);
        try {
            this.f139a.cancelDiscovery();
        } catch (Exception e2) {
        }
        try {
            this.f141a.a();
            this.e = 3;
            this.f140a = new a(this, bluetoothSocket, (byte) 0);
            new Thread(this.f140a).start();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    @Override // de.humatic.nmj.w
    protected final void b() {
        p.logln(1, "BT - client closed, state: " + this.e);
        if (this.e == 3) {
            this.e = 4;
            NMJConfig.a(this.c, 4, 128);
            try {
                this.f140a.a();
            } catch (Exception e) {
            }
            if (NMJConfig.getIP(this.c) == null) {
                p.logln(1, "BT - restarting server thread");
                this.f141a = new C0004c();
                new Thread(this.f141a).start();
            }
        }
    }

    /* JADX INFO: renamed from: de.humatic.nmj.c$c, reason: collision with other inner class name */
    class C0004c extends Thread {
        private final BluetoothServerSocket a;

        public C0004c() {
            BluetoothServerSocket bluetoothServerSocketListenUsingRfcommWithServiceRecord;
            IOException e;
            try {
                c.this.e = 1;
                int port = NMJConfig.getPort(c.this.c);
                UUID uuidFromString = UUID.fromString(c.f137a[port]);
                if (c.this.f146b) {
                    NMJConfig.getFlags(-1);
                }
                p.logln(1, "bluetooth midi (btIdx: " + port + "): starting server thread for uuid: \n" + c.f137a[NMJConfig.getPort(c.this.c)]);
                c.this.f146b = false;
                bluetoothServerSocketListenUsingRfcommWithServiceRecord = c.this.f139a.listenUsingRfcommWithServiceRecord(c.this.f144a, uuidFromString);
                try {
                    NMJConfig.a(c.this.c, 4, 4);
                } catch (IOException e2) {
                    e = e2;
                    p.logln(1, "failed to set up RFCOMM listening: " + e.toString());
                    if (e.toString().indexOf("Error: -1") != -1 && Build.VERSION.SDK_INT < 18) {
                        NMJConfig.a(c.this.c, NMJConfig.E_BLUETOOTH, "IOException: run out of rfc control block\nThis is a leak in Android 4.2 (Bug ID 41110). To create another Bluetooth connection, restart the device");
                    } else {
                        NMJConfig.a(c.this.c, NMJConfig.E_BLUETOOTH, e.toString());
                    }
                    try {
                        NetworkMidiSystem.get().a(NMJConfig.getIO(c.this.c), c.this.c).close(null);
                    } catch (Exception e3) {
                    }
                }
            } catch (IOException e4) {
                bluetoothServerSocketListenUsingRfcommWithServiceRecord = null;
                e = e4;
            }
            this.a = bluetoothServerSocketListenUsingRfcommWithServiceRecord;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            c.this.e = 1;
            while (c.this.e != 3 && c.this.e != 4) {
                try {
                    BluetoothSocket bluetoothSocketAccept = this.a.accept();
                    if (bluetoothSocketAccept != null) {
                        p.logln(1, "socket accepted: " + bluetoothSocketAccept);
                        synchronized (c.this) {
                            switch (c.this.e) {
                                case 0:
                                case 3:
                                    p.logln(1, "oops, unexpected state " + c.this.e);
                                    try {
                                        bluetoothSocketAccept.close();
                                    } catch (IOException e) {
                                        p.logln(1, "failed to close unwanted socket: " + e.toString());
                                    }
                                    break;
                                case 1:
                                case 2:
                                    c.this.e = 3;
                                    c.this.a(bluetoothSocketAccept, bluetoothSocketAccept.getRemoteDevice());
                                    break;
                            }
                        }
                    }
                } catch (IOException e2) {
                    p.logln(1, "socket accept() failed: " + e2);
                    return;
                } catch (Exception e3) {
                    p.logln(1, "socket accept() failed: " + e3);
                    return;
                }
            }
        }

        public final void a() {
            p.logln(1, "shutting down server thread");
            try {
                this.a.close();
            } catch (IOException e) {
                p.logln(1, "failed to close server" + e.toString());
            }
        }
    }

    class a extends Thread {
        private BluetoothSocket a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private /* synthetic */ c f151a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private byte[] f152a;

        private a(c cVar, BluetoothSocket bluetoothSocket) throws IOException {
            byte b = 0;
            this.f151a = cVar;
            this.a = bluetoothSocket;
            if (NMJConfig.getIO(cVar.c) <= 0) {
                cVar.f142a = this.a.getInputStream();
            }
            if (Math.abs(NMJConfig.getIO(cVar.c)) == 1) {
                cVar.f143a = this.a.getOutputStream();
            }
            new Thread(new b(cVar, b)).start();
            this.f152a = new byte[1460];
            cVar.f147c = false;
        }

        /* synthetic */ a(c cVar, BluetoothSocket bluetoothSocket, byte b) throws IOException {
            this(cVar, bluetoothSocket);
        }

        final void a() {
            p.logln(2, "BLUETOOTH MIDI thread close");
            try {
                this.f151a.f142a.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.f151a.f143a.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                this.a.close();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            if (NMJConfig.getIO(this.f151a.c) != 1) {
                while (this.f151a.e != 4) {
                    try {
                        int i = this.f151a.f142a.read(this.f152a);
                        if (i >= 0) {
                            this.f151a.f147c = true;
                            this.f151a.a.a(this.f152a, i);
                        } else {
                            p.logln(2, "BLUETOOTH closed by client");
                            if (this.f151a.e == 3) {
                                this.f151a.b();
                                return;
                            }
                            return;
                        }
                    } catch (Exception e) {
                        if (e.toString().toLowerCase().indexOf("socket closed") != -1 || e.toString().toLowerCase().indexOf("software caused connection abort") != -1 || e.toString().toLowerCase().indexOf("connection reset by peer") != -1) {
                            if (this.f151a.e == 3) {
                                p.logln(1, "Connection closed by client " + e.getMessage());
                                if (this.f151a.f147c) {
                                    NMJConfig.a(this.f151a.c, 4, 128);
                                }
                                this.f151a.b();
                                return;
                            }
                            return;
                        }
                        if (this.f151a.e != 4) {
                            p.a(e, "BT receive ");
                        }
                    }
                }
            }
        }
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final void mo191a(byte[] bArr) throws IOException {
        if (this.e == 3 && this.f143a != null && bArr != null) {
            try {
                if (bArr.length < this.d) {
                    this.f143a.write(bArr);
                    this.f143a.flush();
                    return;
                }
                int iMin = 0;
                while (iMin < bArr.length) {
                    this.f143a.write(bArr, iMin, Math.min(this.d, bArr.length - iMin));
                    iMin += Math.min(this.d, bArr.length - iMin);
                    this.f143a.flush();
                }
            } catch (IOException e) {
                if (e.toString().toLowerCase().indexOf("software caused connection abort") != -1 || e.toString().toLowerCase().indexOf("connection reset by peer") != -1) {
                    if (this.e != 4) {
                        p.logln(1, "Connection closed by client");
                        NMJConfig.a(this.c, 4, 128);
                        b();
                        return;
                    }
                    return;
                }
                if (e.toString().toLowerCase().indexOf("socket closed") == -1 || this.f147c) {
                    throw e;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    class b extends Thread {

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private int[] f153a;

        private b() {
            this.f153a = new int[]{896, 768, 512, 256, 128, 32, 16};
        }

        /* synthetic */ b(c cVar, byte b) {
            this();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                sleep(1000L);
                for (int i = 0; i < this.f153a.length; i++) {
                    c.this.f143a.write(p.m130a(1, this.f153a[i]));
                    c.this.f143a.flush();
                    int i2 = 0;
                    while (c.this.d == 8) {
                        int i3 = i2 + 1;
                        if (i2 >= 5) {
                            break;
                        }
                        sleep(100L);
                        i2 = i3;
                    }
                    if (c.this.d != 8) {
                        break;
                    }
                }
                c.this.mo191a(p.m130a(0, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected static void a(final BluetoothDevice bluetoothDevice, final j jVar) {
        p.logln(1, "getMIDIServices for " + bluetoothDevice.getName());
        for (final int i = 0; i < 4; i++) {
            new Thread(new Runnable() { // from class: de.humatic.nmj.c.2
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothSocket bluetoothSocketCreateRfcommSocketToServiceRecord = null;
                    try {
                        bluetoothSocketCreateRfcommSocketToServiceRecord = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString(c.f137a[i]));
                        bluetoothSocketCreateRfcommSocketToServiceRecord.connect();
                        c.b++;
                        if (jVar != null) {
                            g gVar = new g("_bt-midi", -1);
                            gVar.c = bluetoothDevice.getAddress();
                            gVar.d = de.bassapps.launchbuttonsP.BuildConfig.FLAVOR;
                            gVar.a(String.valueOf(bluetoothDevice.getName()) + " BT-MIDI " + (i + 1));
                            gVar.b = i;
                            jVar.a(2, gVar, -1);
                        }
                        try {
                            bluetoothSocketCreateRfcommSocketToServiceRecord.close();
                        } catch (Exception e) {
                        }
                    } catch (IOException e2) {
                        c.b++;
                        try {
                            bluetoothSocketCreateRfcommSocketToServiceRecord.close();
                        } catch (Exception e3) {
                        }
                    }
                }
            }).start();
        }
    }

    protected static void a(final Context context, final j jVar) {
        f138b = BluetoothAdapter.getDefaultAdapter();
        p.logln(2, "Running BT discovery");
        if (f136a != null) {
            f136a.removeAllElements();
        }
        f135a = new BroadcastReceiver() { // from class: de.humatic.nmj.c.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                try {
                    String action = intent.getAction();
                    if ("android.bluetooth.device.action.FOUND".equals(action)) {
                        BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice != null && bluetoothDevice.getName() != null) {
                            p.logln(2, String.valueOf(bluetoothDevice.getName()) + "\n" + bluetoothDevice.getAddress() + "\n" + bluetoothDevice.getBluetoothClass());
                            if (c.f136a == null) {
                                c.f136a = new Vector<>();
                            }
                            c.f136a.add(bluetoothDevice);
                            return;
                        }
                        return;
                    }
                    if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                        p.logln(2, "Bluetooth client scan finished");
                        context.unregisterReceiver(c.f135a);
                        if (c.f136a == null || c.f136a.size() == 0) {
                            jVar.a(2, 1);
                            return;
                        }
                        c.a = c.f136a.size() << 2;
                        c.b = 0;
                        final j jVar2 = jVar;
                        new Thread(new Runnable(this) { // from class: de.humatic.nmj.c.1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                while (c.b < c.a) {
                                    try {
                                        Thread.sleep(200L);
                                    } catch (InterruptedException e) {
                                    }
                                }
                                if (jVar2 != null) {
                                    jVar2.a(2, 1);
                                }
                            }
                        }).start();
                        for (BluetoothDevice bluetoothDevice2 : c.f136a) {
                            Context context3 = context;
                            c.a(bluetoothDevice2, jVar);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    jVar.a(2, 1);
                    try {
                        context.unregisterReceiver(c.f135a);
                    } catch (Exception e2) {
                    }
                }
            }
        };
        try {
            f138b.cancelDiscovery();
            Thread.currentThread();
            Thread.sleep(500L);
        } catch (Exception e) {
        }
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        context.registerReceiver(f135a, intentFilter);
        f138b.startDiscovery();
        jVar.a(2, 0);
    }

    @Override // de.humatic.nmj.w
    /* JADX INFO: renamed from: a */
    protected final v[] mo4a() {
        if (this.d == null || this.d.size() == 0) {
            return null;
        }
        v[] vVarArr = new v[this.d.size()];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.d.size()) {
                return vVarArr;
            }
            de.humatic.nmj.b bVar = this.d.get(i2);
            v vVar = new v();
            vVar.a = bVar.a;
            vVar.f329a = bVar.f133a;
            vVar.b = 2;
            vVar.f330b = bVar.f134b;
            vVar.c = bVar.b;
            vVarArr[i2] = vVar;
            i = i2 + 1;
        }
    }
}
