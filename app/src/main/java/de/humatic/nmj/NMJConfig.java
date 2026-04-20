package de.humatic.nmj;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.net.wifi.WifiManager;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Looper;
import java.io.File;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public final class NMJConfig {
    public static final int ACCL = 2048;
    public static final int ADB = 4;
    public static final int ADB_QUERY = 16;
    public static final int ANNOUNCE_CLIENTS = 8192;
    public static final int BLE = 9;
    public static final int BLUETOOTH = 2;
    public static final int BT_DISCOVERABLE = 8;
    public static final int CFG_EVENT = 32;
    public static final int CH_CLOSED = 2;
    public static final int CH_CONNECTIVITY = 8;
    public static final int CH_OPENED = 1;
    public static final int CH_REMOVED = -1;
    public static final int CH_STATE = 4;
    public static final int COM = 7;
    public static final int CONNECTIVITY_ADB = 8;
    public static final int CONNECTIVITY_BLE = 2048;
    public static final int CONNECTIVITY_BLUETOOTH = 4;
    public static final int CONNECTIVITY_BTPAN = 64;
    public static final int CONNECTIVITY_MOBILE = 1024;
    public static final int CONNECTIVITY_MULTICAST = 128;
    public static final int CONNECTIVITY_P2P = 256;
    public static final int CONNECTIVITY_USB = 2;
    public static final int CONNECTIVITY_USB_HOST = 16;
    public static final int CONNECTIVITY_USB_SERIAL = 32;
    public static final int CONNECTIVITY_VIRTUAL = 512;
    public static final int CONNECTIVITY_WIFI = 1;
    public static final int DNS = 3;
    public static final int DNS_ANNOUNCE = 2;
    public static final int DNS_QUERY = 1;
    public static final int DSMI = 3;
    public static final int EV_CONNECTIVITY_CHANGED = 1;
    public static final int EV_QUERY_USB = 529;
    public static final int EV_SCAN_BT = 528;
    public static final int EV_SYSTEM_INIT = 0;
    public static final int E_ADB = -2147483640;
    public static final int E_BIND = -2147418110;
    public static final int E_BLUETOOTH = -2147483644;
    public static final int E_DEVICE_OPEN = -2147418111;
    public static final int E_DNS = -2147352576;
    public static final int E_INVALID_CH = -2147418096;
    public static final int E_INVALID_DATA = -2147418095;
    public static final int E_NETWORK = -2147483647;
    public static final int E_P2P = -2147483392;
    public static final int E_UNSPECIFIED = Integer.MIN_VALUE;
    public static final int E_USB = -2147483646;
    public static final int E_WIFI = -2147483647;
    public static final int FIXED_LOCAL_PORTS = 4096;
    public static final int IN = 0;
    public static final int IO = -1;
    public static final int LOCK_WIFI = 64;
    public static final int LOOPBACK = 4;
    public static final int MNET_CFG = 32;
    public static final int MWS = 6;
    public static final int NET_MON = 1024;
    public static final int OUT = 1;
    public static final int PROTECTED = 1;
    public static final int RAW = 0;
    public static final int RECONNECT = 65536;
    public static final int RESTART_DNS = 16777216;
    public static final int RTP = 1;
    public static final int RTPA = 1;
    public static final int RTPA_CH_CLIENT_CONNECT = 64;
    public static final int RTPA_CH_CLIENT_DISCONNECT = 256;
    public static final int RTPA_CH_CONNECTED = 32;
    public static final int RTPA_CH_DISCONNECTED = 128;
    public static final int RTPA_CH_DISCOVERED = 8;
    public static final int RTPA_CH_GONE = 512;
    public static final int RTPA_CH_LOST = 1024;
    public static final int RTPA_CH_PRESENT = 16;
    public static final int RTPA_CH_UNDEFINED = 0;
    public static final int RTPA_CH_WAITING = 4;
    public static final int RTPA_CONNECTION_REFUSED = 2048;
    public static final int RTPA_NO_RESPONSE = 4096;
    public static final int RTPA_PKT_LOSS = 8192;
    public static final int RTPA_REMOTE_ERR = 16384;
    public static final int SCAN_BT = 528;
    public static final int SCAN_DONE = 1;
    public static final int SCAN_EVENT = 16;
    public static final int SCAN_FAILED = 2;
    public static final int SCAN_START = 0;
    public static final int SCAN_WD = 530;
    public static final int USB_ATTACHMENT_LISTEN = 32;
    public static final String USB_DEVICE_ATTACHED = "de.humatic.nmj.USB_DEVICE_ATTACHED";
    public static final int USB_DEV_MASK_COM = 256;
    public static final int USB_DEV_MASK_MIDI = 128;
    public static final int USB_HOST = 5;
    public static final int VIRTUAL = 8;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    static long f16a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static BroadcastReceiver f17a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static SharedPreferences.Editor f19a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static SharedPreferences f20a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static WifiManager.WifiLock f21a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static C0005a f22a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static Object f24a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static Timer f26a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private static long f31b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private static BroadcastReceiver f32b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private static Vector<String> f34b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private static boolean f35b;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private static BroadcastReceiver f37c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private static boolean f39c;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private static boolean f42d;
    private static boolean e;
    private static boolean f;
    private static boolean g;
    private static boolean h;
    private static boolean i;
    private static boolean j;
    private static boolean k;
    private static boolean l;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static String f25a = "Network MIDI Ch.";

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static Context f18a = null;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private static String f33b = null;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private static String f38c = null;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private static String f41d = null;
    static int a = -1;
    private static int b = 6600;
    private static int c = 10000;
    private static int d = 8000;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    protected static boolean f28a = false;
    private static boolean m = true;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    static int[] f29a = new int[16];

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static Vector<NMJSystemListener> f27a = new Vector<>();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static j f23a = null;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    protected static int[] f36b = {1, 2, 256, 64};

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    protected static int[] f40c = {-2147352575, -2147352574, -2147352320, -2147352572};

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static h[] f30a = new h[f36b.length];

    private NMJConfig() {
    }

    @Deprecated
    public static void edit(Context context) throws Exception {
        edit(context, false);
    }

    public static void edit(Context context, boolean z) throws Exception {
        e = true;
        f18a = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("nmj", 0);
        f20a = sharedPreferences;
        f19a = sharedPreferences.edit();
        if (z) {
            f19a.clear();
            f19a.commit();
            if (p.b() >= 6) {
                g();
            }
        }
        try {
            h = f18a.getPackageManager().checkPermission("android.permission.BLUETOOTH", f18a.getPackageName()) == 0;
        } catch (Exception e2) {
        }
    }

    static void a(Context context) throws Exception {
        e = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences("nmj", 0);
        f20a = sharedPreferences;
        f19a = sharedPreferences.edit();
        f18a = context;
        f37c = null;
        f26a = null;
        f22a = null;
        f21a = null;
        g = false;
        f = false;
        f35b = false;
        f39c = false;
        i = false;
        j = false;
        k = false;
        l = false;
        f42d = false;
        f33b = null;
        f38c = null;
        f41d = null;
        f16a = System.currentTimeMillis();
        a = -1;
        f31b = 0L;
        try {
            x.e();
            y.e();
        } catch (Exception e2) {
        }
        p.m123a();
        for (int i2 = 0; i2 < getNumChannels(); i2++) {
            try {
                a(i2, "rtpState", false);
            } catch (Exception e3) {
            }
        }
        b = a(-1, "rtp_bp", 6600);
        c = a(-1, "adb_bp", 10000);
        d = a(-1, "mws_bp", 8000);
        f23a = new j();
        try {
            h = f18a.getPackageManager().checkPermission("android.permission.BLUETOOTH", f18a.getPackageName()) == 0;
        } catch (Exception e4) {
        }
        new Thread(new Runnable() { // from class: de.humatic.nmj.NMJConfig.1
            @Override // java.lang.Runnable
            public final void run() {
                int i3 = 0;
                while (!NMJConfig.f42d) {
                    int i4 = i3 + 1;
                    if (i3 >= 5) {
                        break;
                    }
                    try {
                        Thread.sleep(10L);
                        i3 = i4;
                    } catch (InterruptedException e5) {
                        i3 = i4;
                    }
                }
                int connectivity = NMJConfig.getConnectivity(NMJConfig.f18a);
                int flags = NMJConfig.getFlags(-1);
                p.logln(2, new StringBuilder("nmj, on setParentContext:\nConnectivity: ").append(p.a(connectivity)).append("\nGlobal flags: ").append(p.a(NMJConfig.getFlags(-1))).append("\nAccesspoint: ").append(NMJConfig.i).append((flags & 16) == 0 ? de.bassapps.launchbuttonsP.BuildConfig.FLAVOR : "\nUsb connected: " + NMJConfig.j + (NMJConfig.k ? " (Android 6 MIDI)" : de.bassapps.launchbuttonsP.BuildConfig.FLAVOR)).toString());
                if ((flags & 3) != 0) {
                    for (final int i5 = 0; i5 < NMJConfig.f36b.length; i5++) {
                        if ((NMJConfig.f36b[i5] & connectivity) != 0) {
                            try {
                                NMJConfig.d(1, NMJConfig.f36b[i5]);
                            } catch (Exception e6) {
                                final String string = e6.toString().indexOf("Exception") < 0 ? e6.toString() : e6.toString().substring(e6.toString().indexOf("Exception") + 10).trim();
                                new Thread(new Runnable(this) { // from class: de.humatic.nmj.NMJConfig.1.1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        NMJConfig.a(-1, NMJConfig.f40c[i5], string);
                                    }
                                }).start();
                            }
                        }
                    }
                }
                if ((connectivity & 8) != 0 && (flags & 16) != 0) {
                    NMJConfig.a(false, false);
                }
                if (Build.VERSION.SDK_INT >= 12 && (flags & 32) != 0) {
                    if ((flags & 128) == 0) {
                        NMJConfig.m23a(true);
                    } else if ((flags & 256) == 0) {
                        NMJConfig.m39b(true);
                    }
                    NMJConfig.i();
                }
                if ((flags & 1024) != 0) {
                    NMJConfig.j();
                }
                try {
                    int numChannels = NMJConfig.getNumChannels();
                    for (int i6 = 0; i6 < numChannels; i6++) {
                        if (NMJConfig.m25a(NMJConfig.getMode(i6)) && !NetworkMidiSystem.get().isOpen(-1, i6)) {
                            NMJConfig.m38b(i6, -1);
                        }
                    }
                } catch (Exception e7) {
                }
                NMJConfig.a(-1, 0, 0);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(int i2) {
        for (int i3 = 0; i3 < f36b.length; i3++) {
            if (f36b[i3] == i2) {
                return i3;
            }
        }
        return 0;
    }

    private static int a(String str) {
        try {
            String lowerCase = str.toLowerCase();
            if (lowerCase.indexOf("usb") == 0 || lowerCase.indexOf("rndis") != -1) {
                return 1;
            }
            if (lowerCase.indexOf("p2p-") != -1) {
                return 2;
            }
            if (lowerCase.indexOf("bt-pan") == 0) {
                return 3;
            }
        } catch (Exception e2) {
        }
        return 0;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected static void m19a(int i2) {
        try {
            f30a[b(i2)] = null;
        } catch (Exception e2) {
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected static Context m11a() {
        return f18a;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private static boolean m28a(boolean z) {
        if (z) {
            try {
                if (f21a != null && f21a.isHeld()) {
                    return true;
                }
                WifiManager.WifiLock wifiLockCreateWifiLock = ((WifiManager) f18a.getSystemService("wifi")).createWifiLock(Build.VERSION.SDK_INT <= 10 ? 1 : 3, "nmj_wifi_lock");
                f21a = wifiLockCreateWifiLock;
                wifiLockCreateWifiLock.acquire();
                p.logln(1, "WifiLock aquired");
                return true;
            } catch (Exception e2) {
                p.logln(1, "Failed to aquire WifiLock: " + e2.toString());
            }
        } else {
            try {
                if (f21a != null && f21a.isHeld()) {
                    f21a.release();
                    return true;
                }
            } catch (Exception e3) {
            }
        }
        return false;
    }

    public static void setProperty(String str, String str2) {
        if (str2 == null || str2.length() == 0) {
            f19a.remove(str);
        } else {
            f19a.putString(str, str2);
        }
        f19a.commit();
    }

    public static String getProperty(String str, String str2) {
        return f20a.getString(str, str2);
    }

    public static void setNumChannels(int i2) {
        setNumChannels(i2, true);
    }

    public static void setNumChannels(int i2, boolean z) {
        if (i2 >= 0 && f20a != null) {
            int numChannels = getNumChannels();
            if (i2 < numChannels) {
                for (int i3 = i2; i3 < numChannels; i3++) {
                    try {
                        m53e(i3);
                        if (z) {
                            g(i3);
                        }
                        m57f(i3);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
            if (f19a != null) {
                f19a.putInt("numCh", i2);
                f19a.commit();
            }
        }
    }

    public static int addChannel() {
        int numChannels = getNumChannels();
        setNumChannels(numChannels + 1, false);
        return numChannels;
    }

    public static int getNumChannels() {
        if (f20a == null) {
            return 0;
        }
        return f20a.getInt("numCh", 3);
    }

    private static void a(int i2, boolean z) throws NMJException {
        if (i2 < 0 || i2 > getNumChannels() - 1) {
            throw new NMJException("Invalid channel: " + i2, E_INVALID_CH);
        }
        if (z && NetworkMidiSystem.get().isOpen(-1, i2)) {
            throw new NMJException("Channel " + i2 + " is open. Can't change settings", E_DEVICE_OPEN);
        }
    }

    public static void setIP(int i2, String str) throws NMJException {
        a(i2, true);
        if (getMode(i2) != 3 && getMode(i2) != 4) {
            if (str != null && str.length() > 0 && !str.equalsIgnoreCase("null")) {
                f19a.putString("ip_" + i2, str);
            } else {
                f19a.remove("ip_" + i2);
            }
            f19a.commit();
        }
    }

    public static String getIP(int i2) throws NMJException {
        a(i2, false);
        if (getMode(i2) == 0) {
            return f20a.getString("ip_" + i2, "225.0.0.37");
        }
        if (getMode(i2) == 1 || getMode(i2) == 2 || getMode(i2) == 6) {
            return f20a.getString("ip_" + i2, null);
        }
        if (getMode(i2) == 3) {
            if (f41d == null) {
                f41d = m34b();
            }
            return f41d;
        }
        if (getMode(i2) == 4) {
            return f20a.getString("ip_" + i2, "0.0.0.0");
        }
        if (getMode(i2) == 5 || getMode(i2) == 7) {
            return f20a.getString("ip_" + i2, "dev/bus/usb/001/000");
        }
        return f20a.getString("ip_" + i2, "unknown IP");
    }

    public static void setPort(int i2, int i3) throws NMJException {
        a(i2, getMode(i2) != 7);
        if (i3 == -1) {
            f19a.remove("port_" + i2);
            f19a.commit();
        } else if (getMode(i2) != 3) {
            f19a.putInt("port_" + i2, i3);
            f19a.commit();
        }
    }

    public static int getPort(int i2) throws NMJException {
        a(i2, false);
        if (getMode(i2) == 3) {
            return 9000;
        }
        int i3 = f20a.getInt("port_" + i2, c(i2));
        return (i3 != 0 || getMode(i2) == 2) ? i3 : c(i2);
    }

    private static int c(int i2) {
        int i3 = 0;
        if (getMode(i2) == 2) {
            return a(i2, 2);
        }
        if (getMode(i2) == 4) {
            return c + a(i2, 4);
        }
        if (getMode(i2) == 6) {
            return d + (a(i2, 6) << 1);
        }
        if (getMode(i2) == 5 || getMode(i2) == 7) {
            return 0;
        }
        if (p.m128a(getIP(i2))) {
            for (int i4 = 0; i4 < getNumChannels(); i4++) {
                if (getMode(i4) == 0) {
                    if (i4 >= i2) {
                        break;
                    }
                    i3++;
                }
            }
            return i3 + 21928;
        }
        return (i2 * 2) + 5004;
    }

    static int a(int i2, int i3) {
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            if (getMode(i5) == i3) {
                i4++;
            }
        }
        return i4;
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    private static String m34b() {
        try {
            String strB = b(false);
            return String.valueOf(strB.substring(0, strB.lastIndexOf(".") + 1)) + "255";
        } catch (Exception e2) {
            return "192.168.0.255";
        }
    }

    public static void setLocalPort(int i2, int i3) throws NMJException {
        a(i2, true);
        if (i3 == -1) {
            f19a.remove("localPort_" + i2);
            f19a.commit();
        } else {
            f19a.putInt("portLocal_" + i2, i3);
            f19a.commit();
        }
    }

    public static int getLocalPort(int i2) throws NMJException {
        a(i2, false);
        if (getMode(i2) == 3) {
            return 20000;
        }
        if (getMode(i2) == 2) {
            return 0;
        }
        if (getMode(i2) == 4) {
            int i3 = f20a.getInt("portLocal_" + i2, c + a(i2, 4));
            if (i3 == 0) {
                return a(i2, 4) + c;
            }
            return i3;
        }
        if (getMode(i2) == 5 || getMode(i2) == 7) {
            return f20a.getInt("portLocal_" + i2, 0);
        }
        if (getMode(i2) == 6) {
            int i4 = f20a.getInt("portLocal_" + i2, d + (i2 << 1));
            if (i4 == 0) {
                return (i2 << 1) + d;
            }
            return i4;
        }
        int i5 = f20a.getInt("portLocal_" + i2, getMode(i2) == 1 ? b + (i2 << 1) : 0);
        if (i5 >= 0 && ((i5 <= 0 || i5 >= 1024) && i5 <= 65535)) {
            return i5;
        }
        if (getMode(i2) == 1) {
            return (i2 << 1) + b;
        }
        return 0;
    }

    public static void setBasePort(int i2, int i3) {
        if (i2 == 1) {
            if (i3 != b) {
                b = i3;
                m21a(-1, "rtp_bp", i3);
                return;
            }
            return;
        }
        if (i2 == 4) {
            if (i3 != c) {
                c = i3;
                m21a(-1, "adb_bp", i3);
                return;
            }
            return;
        }
        if (i2 == 6 && i3 != d) {
            d = i3;
            m21a(-1, "mws_bp", i3);
        }
    }

    public static void setMode(int i2, int i3) throws NMJException, SecurityException, IllegalArgumentException {
        a(i2, true);
        if (i3 != getMode(i2)) {
            if (i3 == 5 && Build.VERSION.SDK_INT < 12) {
                throw new IllegalArgumentException("USB hostmode requires Android 3.1 or greater");
            }
            if (i3 == 2 && !h) {
                throw new SecurityException("No Bluetooth permission in Manifest");
            }
            if (i3 != 5 && i3 != 7) {
                setLocalPort(i2, -1);
                setPort(i2, -1);
            }
            f19a.putInt("mode_" + i2, i3);
            f19a.commit();
            if (i3 == 1 || i3 == 6) {
                setIP(i2, null);
                setPort(i2, -1);
            } else if (getPort(i2) < 0) {
                f19a.remove("port_" + i2);
                f19a.commit();
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static void m20a(int i2, int i3) {
        if (i2 >= 0 && i2 <= getNumChannels() - 1) {
            f19a.putInt("mode_" + i2, i3);
            f19a.commit();
        }
    }

    public static int getRTPState(int i2) {
        if (i2 < 0 || i2 > getNumChannels() - 1) {
            return -1;
        }
        if (getMode(i2) == 0) {
            return NetworkMidiSystem.get().isOpen(-1, i2) ? 32 : -1;
        }
        return f20a.getInt("rtpState_" + i2, 0);
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    static void m38b(int i2, int i3) {
        if (i2 >= 0 && i2 <= getNumChannels() - 1 && getMode(i2) != 0) {
            if (i3 < 0) {
                f19a.remove("rtpState_" + i2);
                f19a.commit();
            } else if (i3 != 16 || (getRTPState(i2) & 96) == 0) {
                f19a.putInt("rtpState_" + i2, i3);
                f19a.commit();
            }
        }
    }

    public static int getMode(int i2) throws NMJException {
        int i3 = 0;
        a(i2, false);
        SharedPreferences sharedPreferences = f20a;
        String str = "mode_" + i2;
        if (i2 > 0 && (i2 + 1) % 3 == 0) {
            i3 = 1;
        }
        return sharedPreferences.getInt(str, i3);
    }

    public static void setNetworkAdapter(int i2, int i3) throws NMJException {
        a(i2, true);
        f41d = null;
        if (getNetworkAdapter(i2) == i3) {
            if (!m43c(i2).equalsIgnoreCase(e(i3 - 1))) {
                a(i2, e(i3 - 1));
                return;
            }
            return;
        }
        int iF = f();
        if (i3 <= 0) {
            i3 = iF;
        }
        if (i3 == iF) {
            f19a.remove("nwa_" + i2);
        } else {
            f19a.putInt("nwa_" + i2, i3);
        }
        f19a.commit();
        String strE = e(i3 - 1);
        a(i2, strE);
        if ((getFlags(-1) & 3) != 0 && getIP(i2) == null) {
            int iA = a(strE);
            if (m51d(f36b[iA])) {
                if (f30a[iA] == null) {
                    try {
                        d(1, f36b[iA]);
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                setFlags(-1, RESTART_DNS);
            }
        }
    }

    public static int getNetworkAdapter(int i2) throws NMJException {
        a(i2, false);
        if (getMode(i2) == 2) {
            return 0;
        }
        String strM43c = m43c(i2);
        if (strM43c != null) {
            if (strM43c.indexOf("lo") != -1) {
                int iF = f();
                f19a.putInt("nwa_" + i2, iF);
                f19a.putString("nwaName_" + i2, m42c());
                f19a.commit();
                return iF;
            }
            int iA = a(strM43c, true);
            if (iA >= 0) {
                return iA + 1;
            }
            int iF2 = f();
            f19a.putInt("nwa_" + i2, iF2);
            f19a.putString("nwaName_" + i2, m42c());
            f19a.commit();
            return iF2;
        }
        return f20a.getInt("nwa_" + i2, f());
    }

    private static void a(int i2, String str) {
        if (i2 >= 0 && i2 <= getNumChannels()) {
            f19a.putString("nwaName_" + i2, str);
            f19a.commit();
        }
    }

    /* JADX INFO: renamed from: c, reason: collision with other method in class */
    private static String m43c(int i2) {
        if (getMode(i2) == 2) {
            return de.bassapps.launchbuttonsP.BuildConfig.FLAVOR;
        }
        String string = f20a.getString("nwaName_" + i2, m42c());
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                String name = networkInterfaces.nextElement().getName();
                if (name.indexOf("p2p-") != -1 && string.indexOf("p2p-") != -1) {
                    if (name.indexOf(string.substring(0, string.lastIndexOf("-"))) != -1) {
                        return string;
                    }
                } else if (name.equalsIgnoreCase(string)) {
                    return string;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return m42c();
    }

    public static boolean canOpen(int i2) {
        boolean z = true;
        if (f18a == null) {
            return false;
        }
        int connectivity = getConnectivity(f18a);
        switch (getMode(i2)) {
            case 0:
                if ((connectivity & 1) != 0) {
                    if ((connectivity & 128) != 0 || getIO(i2) > 0) {
                    }
                }
                break;
            case 1:
                if (m41b(i2)) {
                    if ((connectivity & 1) == 0) {
                        z = false;
                    }
                } else {
                    String strM43c = m43c(i2);
                    if ((strM43c == null || (strM43c.indexOf("bt-pan") == -1 && strM43c.indexOf("bnep") == -1)) ? false : true) {
                        if ((connectivity & 64) == 0) {
                            z = false;
                        }
                    } else if ((connectivity & 2) == 0) {
                        z = false;
                    }
                }
                break;
            case 2:
                if ((connectivity & 4) != 0) {
                }
                break;
            case 3:
                if ((connectivity & 1) != 0) {
                }
                break;
            case 4:
                if ((connectivity & 8) != 0) {
                }
                break;
            case 5:
                if ((connectivity & 16) != 0) {
                }
                break;
            case 6:
                if ((connectivity & 1025) != 0) {
                }
                break;
            case 7:
                if ((connectivity & 32) != 0) {
                }
                break;
            default:
                p.logln(1, "can't open " + i2 + " " + getMode(i2) + " " + connectivity + " " + m41b(i2));
                break;
        }
        return false;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static boolean m25a(int i2) {
        return i2 == 1 || i2 == 2 || i2 == 4 || i2 == 6;
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    private static boolean m41b(int i2) {
        String strM43c = m43c(i2);
        if (strM43c == null) {
            return false;
        }
        return (strM43c.indexOf("wlan") == -1 && strM43c.indexOf("eth") == -1 && strM43c.indexOf("ra") == -1 && strM43c.indexOf("wifi") == -1 && strM43c.indexOf("wl0") == -1 && strM43c.indexOf("ap0") == -1 && strM43c.indexOf("mlan") == -1 && strM43c.indexOf("p2p-") == -1) ? false : true;
    }

    private static int a(int i2, boolean z, boolean z2) {
        int mode = getMode(i2);
        if (!(mode <= 1 || mode == 3 || mode == 6)) {
            if (mode == 4) {
                return 8;
            }
            if (mode == 2) {
                return 4;
            }
            if (mode == 5) {
                return 16;
            }
            if (mode == 7) {
                return 32;
            }
            return mode == 8 ? 512 : -1;
        }
        String strM43c = m43c(i2);
        if (z2) {
            strM43c = f20a.getString("nwaName_" + i2, m42c());
        }
        if (strM43c == null || strM43c.indexOf("eth") == 0 || strM43c.indexOf("wlan") == 0 || strM43c.indexOf("mlan") == 0 || strM43c.indexOf("tiwlan") == 0 || strM43c.indexOf("ra") == 0 || strM43c.indexOf("wl0") == 0 || strM43c.indexOf("ap0") == 0 || ((z && strM43c.indexOf("p2p-") != -1) || strM43c.indexOf("wifi") != -1)) {
            return 1;
        }
        if ((strM43c.indexOf("usb") != -1 && strM43c.indexOf("rmnet") == -1) || strM43c.indexOf("rndis") != -1) {
            return 2;
        }
        if (strM43c.indexOf("p2p-") != -1) {
            return 256;
        }
        return (strM43c.indexOf("bt-pan") == -1 && strM43c.indexOf("bnep") == -1) ? -1 : 64;
    }

    /* JADX INFO: renamed from: d, reason: collision with other method in class */
    private static void m49d(int i2) {
        if (f18a != null) {
            try {
                int numChannels = getNumChannels();
                for (int i3 = 0; i3 < numChannels; i3++) {
                    if (NetworkMidiSystem.get().isOpen(-1, i3) && a(i3, false, true) == i2) {
                        a(i3, 8, 0);
                        if ((getFlags(-1) & 2048) != 0) {
                            p.logln(3, "Closing ch." + i3 + " on connectivity loss");
                            try {
                                NetworkMidiSystem.get().a(0, i3).close(null);
                            } catch (Exception e2) {
                            }
                            try {
                                NetworkMidiSystem.get().a(1, i3).close(null);
                            } catch (Exception e3) {
                            }
                        }
                    }
                }
            } catch (Exception e4) {
            }
        }
    }

    public static void setIO(int i2, int i3) throws NMJException {
        if (getMode(i2) == 0) {
            a(i2, true);
            f19a.putInt("io_" + i2, i3);
            f19a.commit();
        }
    }

    public static int getIO(int i2) throws NMJException {
        int i3 = -1;
        a(i2, false);
        if (getMode(i2) != 0) {
            return -1;
        }
        if ((i2 <= 0 || i2 + 1 != 0) && getMode(i2) == 0) {
            i3 = (i2 + 1) % 2;
        }
        return f20a.getInt("io_" + i2, i3);
    }

    public static void setFlags(int i2, int i3) {
        if (i2 <= getNumChannels() && f18a != null) {
            if (i2 < 0) {
                if (!e && ((-16777216) & i3) != 0 && (16777215 & i3) == 0) {
                    if ((getFlags(-1) & 3) != 0 && (16777216 & i3) != 0) {
                        m56f();
                        return;
                    }
                    return;
                }
                f42d = true;
                int flags = getFlags(-1);
                f19a.putInt("flags", i3);
                f19a.commit();
                if (!e) {
                    if (flags != i3) {
                        p.logln(2, "Changing global flags, was " + p.a(flags) + " now " + p.a(i3));
                    }
                    if ((flags & 3) != (i3 & 3)) {
                        if ((i3 & 3) != 0) {
                            for (int i4 = 0; i4 < f36b.length; i4++) {
                                if (m51d(f36b[i4])) {
                                    try {
                                        d(1, f36b[i4]);
                                    } catch (Exception e2) {
                                        p.logln(-1, "on setFlags/runServiceDiscovery: " + e2.toString());
                                    }
                                }
                            }
                        } else {
                            for (int i5 = 0; i5 < f36b.length; i5++) {
                                try {
                                    f30a[i5].c();
                                    f30a[i5] = null;
                                } catch (Exception e3) {
                                }
                            }
                        }
                    }
                    if ((flags & 16) != (i3 & 16)) {
                        if ((i3 & 16) != 0 && f22a == null) {
                            a(false, false);
                        } else if ((i3 & 16) == 0 && f22a != null) {
                            f22a.a();
                            f22a = null;
                        }
                    }
                    if ((flags & 32) != (i3 & 32)) {
                        if ((i3 & 32) != 0 && f17a == null) {
                            i();
                            if ((i3 & 128) == 0) {
                                m23a(true);
                            } else if ((i3 & 256) == 0) {
                                m39b(true);
                            }
                        } else if ((i3 & 32) == 0 && f17a != null) {
                            try {
                                f18a.unregisterReceiver(f17a);
                            } catch (Exception e4) {
                            }
                            f17a = null;
                        }
                    }
                    if ((flags & 64) != (i3 & 64)) {
                        m28a((i3 & 64) != 0);
                    }
                    if ((flags & 1024) != (i3 & 1024)) {
                        if ((i3 & 1024) != 0) {
                            j();
                        } else if (f37c != null) {
                            try {
                                f18a.unregisterReceiver(f37c);
                            } catch (Exception e5) {
                            }
                            f37c = null;
                        }
                    }
                    if ((flags & RECONNECT) != (i3 & RECONNECT)) {
                        int numChannels = getNumChannels();
                        for (int i6 = 0; i6 < numChannels; i6++) {
                            try {
                                if (NetworkMidiSystem.get().isOpen(0, i6)) {
                                    ((NetworkMidiInput) NetworkMidiSystem.get().a(i6)).a.b();
                                }
                            } catch (Exception e6) {
                            }
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            f19a.putInt("flags_" + i2, i3);
            f19a.commit();
        }
    }

    public static int getFlags(int i2) {
        if (f20a == null || i2 > getNumChannels()) {
            return -1;
        }
        if (i2 < 0) {
            return f20a.getInt("flags", Build.VERSION.SDK_INT < 23 ? 315 : 19);
        }
        return f20a.getInt("flags_" + i2, 0);
    }

    public static void setName(int i2, String str) throws NMJException {
        a(i2, false);
        if (str == null) {
            f19a.remove("name_" + i2);
        } else {
            f19a.putString("name_" + i2, str);
        }
        f19a.commit();
    }

    public static String getName(int i2) throws NMJException {
        a(i2, false);
        String string = f20a.getString("name_" + i2, null);
        if (string != null && string.indexOf("unknown") != -1) {
            try {
                string = string.replace("unknown IP,", b(false).replace(".", "_"));
                setName(i2, string);
            } catch (Exception e2) {
            }
        }
        return string == null ? d(i2) : string;
    }

    private static String d(int i2) {
        int i3 = 0;
        if (i2 >= 0 && getMode(i2) == 0) {
            return String.valueOf(f25a) + (i2 + 1);
        }
        if (i2 >= 0 && getMode(i2) == 2 && h) {
            try {
                return String.valueOf(BluetoothAdapter.getDefaultAdapter().getName() == null ? de.bassapps.launchbuttonsP.BuildConfig.FLAVOR : String.valueOf(BluetoothAdapter.getDefaultAdapter().getName()) + " - ") + "BT-MIDI " + (a(i2, 2) + 1);
            } catch (Exception e2) {
                return "nmj BT MIDI " + (a(i2, 2) + 1);
            }
        }
        if (i2 >= 0 && getMode(i2) == 3) {
            return "DSMI";
        }
        if (getMode(i2) == 4) {
            return "ADB " + (a(i2, 4) + 1) + " - " + Build.MODEL;
        }
        if (getMode(i2) == 6) {
            return "WebSockets " + (a(i2, 6) + 1);
        }
        if (f33b != null) {
            if (i2 < 0) {
                return f33b;
            }
            int[] iArrM30a = m30a(1, m47c(i2) ? 1 : 0, a(i2, true, false));
            while (i3 < iArrM30a.length) {
                if (iArrM30a[i3] == i2) {
                    return String.valueOf(f33b) + ", RTP " + (i3 + 1);
                }
                i3++;
            }
            return String.valueOf(f33b) + ", RTP " + iArrM30a.length;
        }
        try {
            f33b = p.a(false).toString();
        } catch (Exception e3) {
        }
        if (f33b == null || f33b.indexOf("127.0.0.1") != -1) {
            f33b = f(getNetworkAdapter(i2));
        }
        if (f33b.equalsIgnoreCase("localhost")) {
            String strF = f(getNetworkAdapter(i2));
            f33b = strF;
            if (strF.indexOf(".") != -1) {
                f33b = "nmj (and_" + f33b.substring(f33b.lastIndexOf(".") + 1) + ")";
            }
        }
        if (f33b.indexOf("/") != -1) {
            if (f33b.indexOf("/") == 0) {
                f33b = f33b.substring(1);
            } else {
                f33b = f33b.substring(0, f33b.indexOf("/"));
            }
        }
        if (f33b.indexOf(".local") != -1) {
            f33b = f33b.substring(0, f33b.indexOf(".loc"));
        }
        if (f33b.length() == 0) {
            try {
                f33b = InetAddress.getLocalHost().getHostName();
            } catch (Exception e4) {
            }
        }
        try {
            f33b = f33b.replace(".", "_");
        } catch (Exception e5) {
        }
        if (i2 < 0) {
            return f33b;
        }
        int[] iArrM30a2 = m30a(1, m47c(i2) ? 1 : 0, a(i2, true, false));
        while (i3 < iArrM30a2.length) {
            if (iArrM30a2[i3] == i2) {
                return String.valueOf(f33b) + ", RTP " + (i3 + 1);
            }
            i3++;
        }
        return String.valueOf(f33b) + ", RTP " + iArrM30a2.length;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0017, code lost:
    
        r0 = null;
     */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static de.humatic.nmj.v[] m31a(int r6) {
        /*
            r5 = 6
            r4 = 2
            r3 = 1
            r1 = 0
            int r0 = getMode(r6)
            if (r0 == r3) goto L18
            int r0 = getMode(r6)
            if (r0 == r4) goto L18
            int r0 = getMode(r6)
            if (r0 == r5) goto L18
            r0 = r1
        L17:
            return r0
        L18:
            java.lang.String r0 = getIP(r6)
            if (r0 == 0) goto L20
            r0 = r1
            goto L17
        L20:
            de.humatic.nmj.NetworkMidiSystem r0 = de.humatic.nmj.NetworkMidiSystem.get()     // Catch: java.lang.Exception -> L6c
            r2 = 0
            boolean r0 = r0.isOpen(r2, r6)     // Catch: java.lang.Exception -> L6c
            if (r0 != 0) goto L2d
            r0 = r1
            goto L17
        L2d:
            de.humatic.nmj.NetworkMidiSystem r0 = de.humatic.nmj.NetworkMidiSystem.get()     // Catch: java.lang.Exception -> L6c
            de.humatic.nmj.NetworkMidiPort r0 = r0.a(r6)     // Catch: java.lang.Exception -> L6c
            de.humatic.nmj.NetworkMidiInput r0 = (de.humatic.nmj.NetworkMidiInput) r0     // Catch: java.lang.Exception -> L6c
            de.humatic.nmj.n r0 = r0.a     // Catch: java.lang.Exception -> L6c
            int r2 = getMode(r6)     // Catch: java.lang.Exception -> L6c
            if (r2 != r3) goto L4a
            de.humatic.nmj.w r0 = r0.a()     // Catch: java.lang.Exception -> L6c
            de.humatic.nmj.t r0 = (de.humatic.nmj.t) r0     // Catch: java.lang.Exception -> L6c
            de.humatic.nmj.v[] r0 = r0.mo4a()     // Catch: java.lang.Exception -> L6c
            goto L17
        L4a:
            int r2 = getMode(r6)     // Catch: java.lang.Exception -> L6c
            if (r2 != r4) goto L5b
            de.humatic.nmj.w r0 = r0.a()     // Catch: java.lang.Exception -> L6c
            de.humatic.nmj.c r0 = (de.humatic.nmj.c) r0     // Catch: java.lang.Exception -> L6c
            de.humatic.nmj.v[] r0 = r0.mo4a()     // Catch: java.lang.Exception -> L6c
            goto L17
        L5b:
            int r2 = getMode(r6)     // Catch: java.lang.Exception -> L6c
            if (r2 != r5) goto L70
            de.humatic.nmj.w r0 = r0.a()     // Catch: java.lang.Exception -> L6c
            de.humatic.nmj.A r0 = (de.humatic.nmj.A) r0     // Catch: java.lang.Exception -> L6c
            de.humatic.nmj.v[] r0 = r0.mo4a()     // Catch: java.lang.Exception -> L6c
            goto L17
        L6c:
            r0 = move-exception
            r0.printStackTrace()
        L70:
            r0 = r1
            goto L17
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.NMJConfig.m31a(int):de.humatic.nmj.v[]");
    }

    static void c(int i2, int i3) {
        if ((getMode(i2) == 1 || getMode(i2) == 2) && getIP(i2) == null) {
            try {
                if (NetworkMidiSystem.get().isOpen(0, i2)) {
                    ((NetworkMidiInput) NetworkMidiSystem.get().a(i2)).a.a().b(i3);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static String m16a(int i2, int i3) {
        v[] vVarArrM31a;
        v vVar = null;
        if ((getMode(i2) == 1 || getMode(i2) == 6 || getMode(i2) == 2) && (vVarArrM31a = m31a(i2)) != null && 0 <= vVarArrM31a.length) {
            vVar = vVarArrM31a[0];
        }
        return vVar == null ? "unknown" : vVar.f329a;
    }

    public static void setLocalClientName(int i2, String str) {
        m22a(i2, "localClientName", str);
    }

    public static void setLocalClientPrefix(String str) {
        m22a(0, "localClientPrefix", str);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static String m15a(int i2) {
        String strA;
        String strM14a = m14a();
        if (i2 >= 0 && a(i2, "localClientName", (String) null) != null) {
            return a(i2, "localClientName", strM14a);
        }
        try {
            strA = a(0, "localClientPrefix", m14a());
            try {
                String strF = f(getNetworkAdapter(i2));
                if (strF.equals("10.0.2.15")) {
                    strA = (Build.MODEL.equalsIgnoreCase("GT-I9300") || Build.MODEL.equalsIgnoreCase("GT-N7100") || GLES20.glGetString(7937).equalsIgnoreCase("BlueStacks")) ? String.valueOf(strA) + " (BlueStacks)" : String.valueOf(strA) + " (Emulator)";
                } else if (strF == null || strF.indexOf("unknown") != -1) {
                    strA = String.valueOf(strA) + " (" + Build.MODEL + ")";
                } else {
                    strA = String.valueOf(strA) + " (and_" + strF.substring(strF.lastIndexOf(".") + 1) + ")";
                }
            } catch (Exception e2) {
                strA = String.valueOf(strA) + " (" + Build.MODEL + ")";
            }
        } catch (Exception e3) {
            e = e3;
        }
        if (i2 >= 0) {
            try {
                strM14a = String.valueOf(strA) + " Ch_" + (i2 + 1);
            } catch (Exception e4) {
                strM14a = strA;
                e = e4;
                if (e.toString().indexOf("NetworkOnMainThread") != -1) {
                    p.logln(1, "NetworkOnMainThread error in getLocalClientName");
                }
            }
            return i2 < 0 ? "nmj (" + Build.MODEL + ")" : a(i2, "localClientName", strM14a);
        }
        return strA;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static String m14a() {
        try {
            return f18a.getApplicationInfo().loadLabel(f18a.getPackageManager()).toString();
        } catch (Exception e2) {
            return "nmj_android";
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private static void m21a(int i2, String str, int i3) {
        if (i3 == -1) {
            a(-1, str, false);
        } else {
            f19a.putInt(str, i3);
            f19a.commit();
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private static void m22a(int i2, String str, String str2) {
        if (str2 == null) {
            a(i2, str, true);
            return;
        }
        if (i2 == -1) {
            f19a.putString(str, str2);
        } else {
            f19a.putString(String.valueOf(str) + "_" + i2, str2);
        }
        f19a.commit();
    }

    private static int a(int i2, String str, int i3) {
        if (-1 > getNumChannels() - 1) {
            return -1;
        }
        return f20a.getInt(str, i3);
    }

    static String a(int i2, String str, String str2) {
        if (i2 < -1 || i2 > getNumChannels() - 1) {
            return str2;
        }
        return i2 == -1 ? f20a.getString(str, str2) : f20a.getString(String.valueOf(str) + "_" + i2, str2);
    }

    private static void a(int i2, String str, boolean z) {
        if (i2 >= -1 && i2 <= getNumChannels() - 1) {
            if (i2 == -1) {
                f19a.remove(str);
            } else {
                f19a.remove(String.valueOf(str) + "_" + i2);
            }
            if (z) {
                f19a.commit();
            }
        }
    }

    static void a(int i2, int i3, int i4) {
        boolean z = true;
        int i5 = 0;
        try {
            if (!g) {
                if (i2 >= 0 && i3 == 4) {
                    m38b(i2, i4);
                    if ((getMode(i2) == 1 || getMode(i2) == 6) && i4 < 8192) {
                        if (i4 == 4096 && getIP(i2) == null) {
                            m38b(i2, 4);
                        }
                        if (i4 > 4096) {
                            if (i2 >= f29a.length) {
                                int[] iArr = new int[f29a.length];
                                System.arraycopy(f29a, 0, iArr, 0, iArr.length);
                                f29a = new int[iArr.length << 1];
                                System.arraycopy(iArr, 0, f29a, 0, iArr.length);
                            }
                            if (f29a[i2] != i4) {
                                f29a[i2] = i4;
                                z = false;
                            }
                            if (z) {
                                return;
                            }
                        }
                        if (i4 == 512 && getIP(i2) != null && !NetworkMidiSystem.get().isOpen(-1, i2)) {
                            p.logln(1, "CH_GONE: " + i2 + ", not currently open. Deleting");
                            for (int i6 = 0; i6 < f27a.size(); i6++) {
                                f27a.get(i6).systemChanged(i2, i3, i4);
                            }
                            b(i2, true);
                            return;
                        }
                    } else if (getMode(i2) == 32 && i4 == 512) {
                        p.logln(4, "CH_GONE: " + i2 + ", deleting mnet_cfg");
                        for (int i7 = 0; i7 < f27a.size(); i7++) {
                            f27a.get(i7).systemChanged(i2, i3, i4);
                        }
                        deleteChannel(i2);
                        return;
                    }
                }
                while (true) {
                    int i8 = i5;
                    if (i8 < f27a.size()) {
                        f27a.get(i8).systemChanged(i2, i3, i4);
                        i5 = i8 + 1;
                    } else {
                        return;
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            p.logln(-1, "in postSystemEvent " + e2.toString());
        }
    }

    static void a(int i2, int i3, String str) {
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i5 < f27a.size()) {
                f27a.get(i5).systemError(i2, i3, str);
                i4 = i5 + 1;
            } else {
                return;
            }
        }
    }

    static int b(int i2, int i3) {
        m38b(i2, i3 == 32 ? 64 : 256);
        for (int i4 = 0; i4 < f27a.size(); i4++) {
            f27a.get(i4).systemChanged(i2, 4, i3 == 32 ? 64 : 256);
        }
        return 0;
    }

    public static void addSystemListener(NMJSystemListener nMJSystemListener) {
        if (!f27a.contains(nMJSystemListener)) {
            f27a.add(nMJSystemListener);
        }
    }

    public static void removeSystemListener(NMJSystemListener nMJSystemListener) {
        if (f27a.contains(nMJSystemListener)) {
            f27a.remove(nMJSystemListener);
        }
    }

    static void a(String str, long j2) {
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < f27a.size()) {
                if (f27a.get(i3).getClass().getName().equalsIgnoreCase(str) && f27a.get(i3).hashCode() != j2) {
                    f27a.remove(i3);
                }
                i2 = i3 + 1;
            } else {
                return;
            }
        }
    }

    private static boolean a(Context context, int i2) {
        if (context == null) {
            return false;
        }
        String packageName = context.getPackageName();
        if ((i2 & 3) != 0) {
            if (context.getPackageManager().checkPermission("android.permission.INTERNET", packageName) == 0 && context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", packageName) == 0) {
                return (Build.VERSION.SDK_INT >= 23 || context.getPackageManager().checkPermission("android.permission.CHANGE_NETWORK_STATE", packageName) == 0) && context.getPackageManager().checkPermission("android.permission.ACCESS_WIFI_STATE", packageName) == 0;
            }
            return false;
        }
        if ((i2 & 128) != 0) {
            return context.getPackageManager().checkPermission("android.permission.CHANGE_WIFI_MULTICAST_STATE", packageName) == 0;
        }
        if ((i2 & 4) == 0) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return true;
        }
        return context.getPackageManager().checkPermission("android.permission.BLUETOOTH", packageName) == 0 && context.getPackageManager().checkPermission("android.permission.BLUETOOTH_ADMIN", packageName) == 0;
    }

    public static int getConnectivity(Context context) {
        return a(context, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:139:0x0212 A[Catch: Exception -> 0x0242, TRY_ENTER, TRY_LEAVE, TryCatch #8 {Exception -> 0x0242, blocks: (B:18:0x0044, B:139:0x0212), top: B:229:0x0044 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x035b  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0050 A[EDGE_INSN: B:258:0x0050->B:21:0x0050 BREAK  A[LOOP:3: B:20:0x004e->B:149:0x023e], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:260:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x009c A[Catch: Exception -> 0x0248, TRY_LEAVE, TryCatch #4 {Exception -> 0x0248, blocks: (B:43:0x0096, B:45:0x009c), top: B:220:0x0096 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static int a(android.content.Context r11, boolean r12) {
        /*
            Method dump skipped, instruction units count: 865
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.NMJConfig.a(android.content.Context, boolean):int");
    }

    private static int c() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            int i2 = 1;
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if ((networkInterfaceNextElement.getName().toLowerCase().indexOf("usb") == 0 || networkInterfaceNextElement.getName().toLowerCase().indexOf("rndis") != -1) && f(i2).indexOf("unknown") < 0) {
                    return i2;
                }
                i2++;
            }
        } catch (Exception e2) {
        }
        return -1;
    }

    private static int d() {
        if (Build.VERSION.SDK_INT < 14) {
            return -1;
        }
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            int i2 = 1;
            while (networkInterfaces.hasMoreElements()) {
                if (networkInterfaces.nextElement().getName().toLowerCase().indexOf("p2p-") != -1) {
                    return i2;
                }
                i2++;
            }
        } catch (Exception e2) {
        }
        return -1;
    }

    private static int e() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            int i2 = 1;
            while (networkInterfaces.hasMoreElements()) {
                String lowerCase = networkInterfaces.nextElement().getName().toLowerCase();
                if (lowerCase.indexOf("bt-pan") != -1 || lowerCase.indexOf("bnep") != -1) {
                    return i2;
                }
                i2++;
            }
        } catch (Exception e2) {
        }
        return -1;
    }

    static String a(boolean z) {
        return b(false);
    }

    static String b(boolean z) {
        String string;
        try {
            String str = "unknown ip";
            NetworkInterface networkInterfaceM120a = p.m120a();
            if (networkInterfaceM120a == null && z) {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    networkInterfaceM120a = networkInterfaces.nextElement();
                    String name = networkInterfaceM120a.getName();
                    if (name.indexOf("usb") != -1 || name.indexOf("rndis") != -1) {
                        if (name.indexOf("usb") != 0) {
                            name.indexOf("rndis");
                        }
                    }
                }
            } else if (networkInterfaceM120a == null) {
                return "unknown ip";
            }
            networkInterfaceM120a.getName();
            Enumeration<InetAddress> inetAddresses = networkInterfaceM120a.getInetAddresses();
            while (true) {
                if (!inetAddresses.hasMoreElements()) {
                    string = str;
                    break;
                }
                InetAddress inetAddressNextElement = inetAddresses.nextElement();
                if (!(inetAddressNextElement instanceof Inet6Address)) {
                    string = inetAddressNextElement.toString();
                    if (string.indexOf("127.0.0.1") < 0) {
                        break;
                    }
                    str = string;
                }
            }
            return string.indexOf("/") != -1 ? string.substring(string.indexOf("/") + 1) : string;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "unknown IP";
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static String[] m32a() {
        boolean z;
        boolean z2;
        BluetoothAdapter defaultAdapter;
        String name;
        boolean z3 = false;
        Vector vector = new Vector();
        try {
            NetworkInterface networkInterfaceM120a = p.m120a();
            if (networkInterfaceM120a == null) {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                name = null;
                z2 = false;
                while (networkInterfaces.hasMoreElements()) {
                    try {
                        networkInterfaceM120a = networkInterfaces.nextElement();
                        name = networkInterfaceM120a.getName();
                        if (name.indexOf("usb") != -1 || name.indexOf("rndis") != -1) {
                            if (name.indexOf("usb") == 0 || name.indexOf("rndis") >= 0) {
                                z2 = true;
                            }
                        }
                        if (name.indexOf("bt-pan") >= 0) {
                            z3 = true;
                        }
                    } catch (Exception e2) {
                        z = z3;
                        z3 = z2;
                        z2 = z3;
                        z3 = z;
                    }
                }
            } else {
                name = networkInterfaceM120a.getName();
                z2 = false;
            }
            Enumeration<InetAddress> inetAddresses = networkInterfaceM120a.getInetAddresses();
            InetAddress inetAddressNextElement = null;
            while (inetAddresses.hasMoreElements()) {
                inetAddressNextElement = inetAddresses.nextElement();
                if (m && (inetAddressNextElement instanceof Inet4Address) && inetAddressNextElement.toString().indexOf("127.0.0.1") < 0) {
                    break;
                }
            }
            String string = inetAddressNextElement.toString();
            if (string.indexOf("/") != -1) {
                string = string.substring(string.indexOf("/") + 1);
            }
            if (name.indexOf("eth") == 0 || name.indexOf("wlan") == 0 || name.indexOf("tiwlan") == 0 || name.indexOf("mlan") == 0 || name.indexOf("ra") == 0 || name.indexOf("wl0") == 0 || name.indexOf("ap0") == 0 || name.indexOf("wifi") == 0) {
                vector.add("WIFI: " + string);
            } else if (name.indexOf("bt-pan") != -1) {
                if (!string.equalsIgnoreCase("127.0.0.1")) {
                    vector.add("BT-Pan: " + string);
                }
            } else if (!string.equalsIgnoreCase("127.0.0.1")) {
                vector.add("USB: " + string);
            }
        } catch (Exception e3) {
            z = false;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            try {
                Object[] objArrM131a = p.m131a(false);
                if (objArrM131a != null) {
                    String string2 = ((InetAddress) objArrM131a[1]).toString();
                    if (string2.indexOf("/") != -1) {
                        string2 = string2.substring(string2.indexOf("/") + 1);
                    }
                    vector.add("P2P: " + string2);
                }
            } catch (Exception e4) {
            }
        }
        if (!z2) {
            try {
                if (c() != -1) {
                    Enumeration<NetworkInterface> networkInterfaces2 = NetworkInterface.getNetworkInterfaces();
                    while (networkInterfaces2.hasMoreElements()) {
                        NetworkInterface networkInterfaceNextElement = networkInterfaces2.nextElement();
                        String name2 = networkInterfaceNextElement.getName();
                        if (name2.indexOf("usb") == 0 || name2.indexOf("rndis") != -1) {
                            Enumeration<InetAddress> inetAddresses2 = networkInterfaceNextElement.getInetAddresses();
                            InetAddress inetAddressNextElement2 = null;
                            while (inetAddresses2.hasMoreElements()) {
                                inetAddressNextElement2 = inetAddresses2.nextElement();
                                if (m && (inetAddressNextElement2 instanceof Inet4Address) && inetAddressNextElement2.toString().indexOf("127.0.0.1") < 0) {
                                    break;
                                }
                            }
                            String string3 = inetAddressNextElement2.toString();
                            if (string3.indexOf("/") != -1) {
                                string3 = string3.substring(string3.indexOf("/") + 1);
                            }
                            if (!string3.equalsIgnoreCase("127.0.0.1")) {
                                vector.add("USB: " + string3);
                            }
                        }
                    }
                }
            } catch (Exception e5) {
            }
        }
        if (!z3) {
            try {
                if (e() != -1) {
                    Enumeration<NetworkInterface> networkInterfaces3 = NetworkInterface.getNetworkInterfaces();
                    while (networkInterfaces3.hasMoreElements()) {
                        NetworkInterface networkInterfaceNextElement2 = networkInterfaces3.nextElement();
                        if (networkInterfaceNextElement2.getName().indexOf("bt-pan") != -1) {
                            Enumeration<InetAddress> inetAddresses3 = networkInterfaceNextElement2.getInetAddresses();
                            InetAddress inetAddressNextElement3 = null;
                            while (inetAddresses3.hasMoreElements()) {
                                inetAddressNextElement3 = inetAddresses3.nextElement();
                                if (m && (inetAddressNextElement3 instanceof Inet4Address) && inetAddressNextElement3.toString().indexOf("127.0.0.1") < 0) {
                                    break;
                                }
                            }
                            String string4 = inetAddressNextElement3.toString();
                            if (string4.indexOf("/") != -1) {
                                string4 = string4.substring(string4.indexOf("/") + 1);
                            }
                            if (!string4.equalsIgnoreCase("127.0.0.1")) {
                                vector.add("BT-Pan: " + string4);
                            }
                        }
                    }
                }
            } catch (Exception e6) {
            }
        }
        try {
            if (h && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null && defaultAdapter.isEnabled()) {
                vector.add("Bluetooth: " + defaultAdapter.getAddress());
            }
        } catch (Exception e7) {
        }
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        vector.removeAllElements();
        return strArr;
    }

    private static int a(String str, boolean z) {
        int i2 = 0;
        if (str.indexOf("rmnet") != -1) {
            return -1;
        }
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (str.indexOf("p2p-") != -1) {
                    if (networkInterfaceNextElement.getName().indexOf(str.substring(0, str.lastIndexOf("-"))) != -1) {
                        return i2;
                    }
                } else if (networkInterfaceNextElement.getName().equalsIgnoreCase(str)) {
                    return i2;
                }
                i2++;
            }
        } catch (Exception e2) {
        }
        return -1;
    }

    private static String e(int i2) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            int i3 = 0;
            while (true) {
                int i4 = i3;
                if (!networkInterfaces.hasMoreElements()) {
                    break;
                }
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (i4 == i2) {
                    return networkInterfaceNextElement.getName();
                }
                i3 = i4 + 1;
            }
        } catch (Exception e2) {
        }
        return "Auto";
    }

    static int a() {
        int i2 = 0;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                networkInterfaces.nextElement();
                i2++;
            }
        } catch (Exception e2) {
        }
        return i2;
    }

    private static String f(int i2) {
        Enumeration<NetworkInterface> networkInterfaces;
        int i3;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            i3 = 0;
        } catch (Exception e2) {
        }
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
            i3++;
            if (i3 == i2) {
                Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (inetAddressNextElement instanceof Inet4Address) {
                        String string = inetAddressNextElement.toString();
                        return string.indexOf("/") != -1 ? string.substring(string.indexOf("/") + 1) : string;
                    }
                    return "unknown IP";
                }
            }
        }
        return "unknown IP";
    }

    private static int f() {
        String name;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            int i2 = 0;
            while (networkInterfaces.hasMoreElements()) {
                i2++;
                String name2 = networkInterfaces.nextElement().getName();
                if (name2.indexOf("eth") == 0 || name2.indexOf("wlan") == 0 || name2.indexOf("mlan") == 0 || name2.indexOf("wifi") == 0 || name2.indexOf("wl0") == 0 || name2.indexOf("ap0") == 0 || name2.indexOf("ra") == 0) {
                    if (name2.indexOf("eth") == -1) {
                        return i2;
                    }
                    while (networkInterfaces.hasMoreElements()) {
                        i2++;
                        try {
                            name = networkInterfaces.nextElement().getName();
                        } catch (Exception e2) {
                        }
                        if (name.indexOf("wlan") == 0 || name.indexOf("mlan") == 0 || name.indexOf("tiwlan") == 0 || name.indexOf("wifi") == 0 || name.indexOf("ra") == 0) {
                            return i2;
                        }
                    }
                    return i2;
                }
            }
        } catch (Exception e3) {
        }
        try {
            Enumeration<NetworkInterface> networkInterfaces2 = NetworkInterface.getNetworkInterfaces();
            int i3 = 0;
            while (networkInterfaces2.hasMoreElements()) {
                i3++;
                NetworkInterface networkInterfaceNextElement = networkInterfaces2.nextElement();
                if (networkInterfaceNextElement.getName().indexOf("usb") == 0 || networkInterfaceNextElement.getName().toLowerCase().indexOf("rndis") != -1) {
                    return i3;
                }
            }
        } catch (Exception e4) {
        }
        return 0;
    }

    /* JADX INFO: renamed from: c, reason: collision with other method in class */
    private static String m42c() {
        if (f38c != null) {
            return f38c;
        }
        try {
            String name = p.m120a().getName();
            f38c = name;
            return name;
        } catch (Exception e2) {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                    if (networkInterfaceNextElement.getName().indexOf("usb") == 0 || networkInterfaceNextElement.getName().indexOf("rndis") != -1) {
                        return networkInterfaceNextElement.getName();
                    }
                }
            } catch (Exception e3) {
            }
            return null;
        }
    }

    public static int[] cleanup(int i2, int[] iArr) {
        byte b2;
        Vector vector;
        int numChannels = getNumChannels();
        if (iArr == null) {
            iArr = new int[0];
        }
        Vector vector2 = null;
        int i3 = numChannels - 1;
        while (i3 >= i2) {
            int mode = getMode(i3);
            if (mode == 4 || ((mode == 1 && getIP(i3) != null) || ((mode == 6 && getIP(i3) != null) || ((mode == 2 && getIP(i3) != null) || mode == 5 || mode == 7 || mode == 32)))) {
                if ((getFlags(i3) & 1) != 0) {
                    b2 = i3 > i2 + 1 ? (byte) 1 : (byte) -1;
                } else {
                    b2 = 0;
                }
                int i4 = 0;
                while (true) {
                    if (i4 >= iArr.length) {
                        break;
                    }
                    if (i3 == iArr[i4]) {
                        b2 = 1;
                        break;
                    }
                    i4++;
                }
                if (b2 == 1) {
                    if (i3 == i2) {
                        break;
                    }
                    vector = vector2 == null ? new Vector() : vector2;
                    p.logln(2, "relocating remote channel " + i3 + " " + getName(i3));
                    vector.add(new v(i3));
                } else {
                    p.logln(2, "removing unused remote channel " + i3 + " " + getName(i3));
                    vector = vector2;
                }
                int numChannels2 = getNumChannels();
                if (i3 != numChannels2 - 1) {
                    g(i3);
                }
                setNumChannels(numChannels2 - 1, true);
                vector2 = vector;
            }
            i3--;
        }
        int[] iArr2 = new int[iArr.length];
        if (iArr2.length == 0) {
            return iArr2;
        }
        for (int i5 = 0; i5 < iArr2.length; i5++) {
            iArr2[i5] = -1;
        }
        getNumChannels();
        if (vector2 != null) {
            while (vector2.size() > 0) {
                v vVar = (v) vector2.remove(0);
                int iAddChannel = addChannel();
                setMode(iAddChannel, vVar.b);
                setName(iAddChannel, vVar.f329a);
                setIP(iAddChannel, vVar.f330b);
                setPort(iAddChannel, vVar.c);
                setLocalPort(iAddChannel, vVar.d);
                setFlags(iAddChannel, vVar.e);
                m38b(iAddChannel, vVar.f);
                if (vVar.g <= 0 || vVar.g == f()) {
                    setNetworkAdapter(iAddChannel, -1);
                } else {
                    setNetworkAdapter(iAddChannel, vVar.g);
                }
                if (vVar.b != 1 || vVar.f330b == null || vVar.f331c == null) {
                    setLocalClientName(iAddChannel, null);
                } else {
                    setLocalClientName(iAddChannel, vVar.f331c);
                }
                for (int i6 = 0; i6 < iArr.length; i6++) {
                    if (vVar.a == iArr[i6]) {
                        iArr2[i6] = iAddChannel;
                    }
                }
            }
        }
        return iArr2;
    }

    static synchronized void a(String str, int i2, String str2) {
        try {
            for (int numChannels = getNumChannels() - 1; numChannels > 0; numChannels--) {
                if (getIP(numChannels) != null && getMode(numChannels) == i2 && (getName(numChannels).equalsIgnoreCase(str2) || (getName(numChannels).indexOf(".") != -1 && getName(numChannels).replace(".", "_").equalsIgnoreCase(str2)))) {
                    p.logln(1, "DNS (" + str + ") - channel gone " + str2 + " " + getMode(numChannels));
                    a(numChannels, 4, 512);
                    break;
                }
            }
        } catch (Exception e2) {
        }
    }

    public static void deleteChannel(int i2) {
        b(i2, false);
    }

    private static void b(int i2, boolean z) {
        if (i2 >= 0 && i2 <= getNumChannels() - 1) {
            m53e(i2);
            if (z && (getFlags(i2) & 1) != 0) {
                p.logln(2, "Channel is protected");
                return;
            }
            g(i2);
            m57f(i2);
            if (f19a != null) {
                f19a.putInt("numCh", getNumChannels() - 1);
                f19a.commit();
            }
            a(i2, -1, 0);
        }
    }

    /* JADX INFO: renamed from: e, reason: collision with other method in class */
    private static void m53e(int i2) {
        try {
            if (getMode(i2) == 1 && getIP(i2) == null) {
                if ((getFlags(-1) & 3) != 0) {
                    for (int i3 = 0; i3 < f36b[i3]; i3++) {
                        if (m51d(f36b[i3]) && f30a[i3] != null && getRTPState(i2) != -1) {
                            f30a[i3].a(i2);
                        }
                    }
                }
                m38b(i2, -1);
            }
            if ((NetworkMidiSystem.get().isOpen(getIO(i2), i2) ? (char) 1 : (char) 0) > 0) {
                try {
                    if (getIO(i2) <= 0) {
                        NetworkMidiSystem.get().a(i2).close(null);
                    } else {
                        NetworkMidiSystem.get().a(i2).close(null);
                    }
                } catch (Exception e2) {
                    p.logln(2, "Failed to close channel " + i2);
                }
            }
        } catch (Exception e3) {
        }
    }

    /* JADX INFO: renamed from: f, reason: collision with other method in class */
    private static void m57f(int i2) {
        NetworkMidiSystem.get().m85a(i2);
    }

    public static void copyChannel(int i2, int i3) {
        setIP(i3, getIP(i2));
        setPort(i3, getPort(i2));
        setLocalPort(i3, getLocalPort(i2));
        m20a(i3, getMode(i2));
        setNetworkAdapter(i3, getNetworkAdapter(i2));
        setIO(i3, getIO(i2));
        setFlags(i3, getFlags(i2));
        setName(i3, getName(i2).indexOf(f25a) != -1 ? String.valueOf(f25a) + (i3 + 1) : getName(i2));
        if (getMode(i2) == 1) {
            m38b(i3, getRTPState(i2));
        }
    }

    private static void g(int i2) {
        int i3;
        char c2;
        String string;
        int i4;
        try {
            if (getMode(i2) == 1) {
                f29a[i2] = 0;
                setLocalClientName(i2, null);
            }
        } catch (Exception e2) {
        }
        if (f34b == null) {
            f34b = new Vector<>();
        } else {
            f34b.removeAllElements();
        }
        boolean z = i2 == getNumChannels() + (-1);
        Iterator<Map.Entry<String, ?>> it = f20a.getAll().entrySet().iterator();
        while (it.hasNext()) {
            String string2 = it.next().toString();
            try {
                int i5 = Integer.parseInt(string2.substring(string2.indexOf("_") + 1, string2.indexOf("=")));
                if (i5 == i2) {
                    f19a.remove(string2.substring(0, string2.indexOf("=")));
                } else if (!z && i5 > i2) {
                    f34b.add(string2);
                }
            } catch (NumberFormatException e3) {
            }
        }
        f19a.commit();
        if (!z) {
            int i6 = 0;
            while (true) {
                if (f34b.size() <= 0) {
                    i3 = i6;
                    break;
                }
                i3 = i6 + 1;
                if (i6 >= 1000) {
                    break;
                }
                int i7 = 0;
                int i8 = Integer.MAX_VALUE;
                while (i7 < f34b.size()) {
                    int iMin = Math.min(i8, Integer.parseInt(f34b.get(i7).substring(f34b.get(i7).indexOf("_") + 1, f34b.get(i7).indexOf("="))));
                    i7++;
                    i8 = iMin;
                }
                for (int size = f34b.size() - 1; size >= 0; size--) {
                    int i9 = Integer.parseInt(f34b.get(size).substring(f34b.get(size).indexOf("_") + 1, f34b.get(size).indexOf("=")));
                    if (i9 == i8) {
                        String strSubstring = f34b.get(size).substring(0, f34b.get(size).indexOf("="));
                        try {
                            string = null;
                            i4 = f20a.getInt(strSubstring, Integer.MAX_VALUE);
                            c2 = 0;
                        } catch (ClassCastException e4) {
                            try {
                                string = f20a.getString(strSubstring, null);
                                i4 = 0;
                                c2 = 1;
                            } catch (ClassCastException e5) {
                                c2 = 2;
                                string = null;
                                i4 = 0;
                            }
                        }
                        f19a.remove(strSubstring);
                        if (c2 < 2) {
                            String strReplace = strSubstring.replace(String.valueOf(i9), String.valueOf(i9 - 1));
                            if (c2 == 0 && i4 != Integer.MAX_VALUE) {
                                f19a.putInt(strReplace, i4);
                            } else if (string != null) {
                                f19a.putString(strReplace, string);
                            }
                        }
                        f19a.commit();
                        try {
                            f34b.remove(size);
                        } catch (Exception e6) {
                            i6 = i3;
                        }
                    }
                }
                i6 = i3;
            }
            if (i3 > 1000) {
                p.a("timed out, prefs unchanged " + f34b.size());
            }
        }
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    static void m37b(int i2) {
        try {
            if (getMode(i2) == 1 || getMode(i2) == 6) {
                int iA = a(m43c(i2));
                if (f30a[iA] == null) {
                    try {
                        Thread.currentThread();
                        Thread.sleep(500L);
                    } catch (InterruptedException e2) {
                    }
                    if (f30a[iA] == null) {
                        m56f();
                    }
                }
                if (getIP(i2) != null) {
                    if ((getFlags(-1) & 8192) != 0) {
                        f30a[iA].m113a(i2, false);
                        return;
                    }
                    return;
                }
                f30a[iA].d();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: c, reason: collision with other method in class */
    static void m45c(int i2) {
        try {
            NetworkMidiSystem.get();
            if (!NetworkMidiSystem.f122a && (getMode(i2) == 1 || getMode(i2) == 6)) {
                if (getIP(i2) == null) {
                    f30a[a(m43c(i2))].b(i2);
                } else if ((getFlags(-1) & 8192) != 0) {
                    f30a[a(m43c(i2))].m113a(i2, true);
                }
            }
        } catch (Exception e2) {
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static int[] m29a(int i2) {
        return m30a(-1, 0, 1);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static int[] m30a(int i2, int i3, int i4) {
        int i5 = 0;
        for (int i6 = 0; i6 < getNumChannels(); i6++) {
            try {
                try {
                    if ((getMode(i6) == i2 || (i2 < 0 && (getMode(i6) == 1 || getMode(i6) == 6))) && a(i6, false, false) == i4) {
                        if (i3 < 0) {
                            i5++;
                        } else if ((i3 & 1) == 0 || getIP(i6) != null) {
                            if ((i3 & 1) == 0 && getIP(i6) != null) {
                                if ((i3 & 2) == 0) {
                                    i5++;
                                } else if (NetworkMidiSystem.get().isOpen(-1, i6)) {
                                    i5++;
                                }
                            }
                        } else if ((i3 & 2) == 0) {
                            i5++;
                        } else if (NetworkMidiSystem.get().isOpen(-1, i6)) {
                            i5++;
                        }
                    }
                } catch (Exception e2) {
                }
            } catch (Exception e3) {
                return null;
            }
        }
        if (i5 == 0) {
            return new int[0];
        }
        int[] iArr = new int[i5];
        int i7 = 0;
        for (int i8 = 0; i8 < getNumChannels(); i8++) {
            try {
                if ((getMode(i8) == i2 || (i2 < 0 && (getMode(i8) == 1 || getMode(i8) == 6))) && a(i8, false, false) == i4) {
                    if (i3 < 0) {
                        iArr[i7] = i8;
                        i7++;
                    } else if ((i3 & 1) == 0 || getIP(i8) != null) {
                        if ((i3 & 1) == 0 && getIP(i8) != null) {
                            if ((i3 & 2) == 0) {
                                iArr[i7] = i8;
                                i7++;
                            } else if (NetworkMidiSystem.get().isOpen(-1, i8)) {
                                int i9 = i7 + 1;
                                iArr[i7] = i8;
                                i7 = i9;
                            }
                        }
                    } else if ((i3 & 2) == 0) {
                        iArr[i7] = i8;
                        i7++;
                    } else if (NetworkMidiSystem.get().isOpen(-1, i8)) {
                        iArr[i7] = i8;
                        i7++;
                    }
                }
            } catch (Exception e4) {
            }
        }
        return iArr;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static boolean m27a(String str) {
        String name;
        for (int i2 = 0; i2 < getNumChannels(); i2++) {
            if (getMode(i2) == 1 && getIP(i2) == null && (name = getName(i2)) != null && str.indexOf(name) != -1) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: c, reason: collision with other method in class */
    private static boolean m47c(int i2) {
        return getMode(i2) == 1 && getIP(i2) == null;
    }

    static synchronized void d(final int i2, final int i3) throws Exception {
        if (!g && (getFlags(-1) & 3) != 0) {
            if (!p.c("de.humatic.nmj.DNS_SDImpl")) {
                if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
                    new Thread(new Runnable() { // from class: de.humatic.nmj.NMJConfig.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            try {
                                NMJConfig.d(i2, i3);
                            } catch (Exception e2) {
                                try {
                                    NMJConfig.a(-1, NMJConfig.f40c[NMJConfig.b(i3)], e2.getMessage());
                                } catch (Exception e3) {
                                }
                            }
                        }
                    }).start();
                } else if (m51d(i3)) {
                    f = false;
                    int iB = b(i3);
                    if (f30a[iB] == null) {
                        if (f23a == null) {
                            f23a = new j();
                        }
                        f30a[iB] = new h(f23a, i3);
                    }
                    switch (i2) {
                        case -1:
                            f30a[iB].b();
                            break;
                        case 1:
                            f30a[iB].a();
                            break;
                    }
                }
            } else {
                p.logln(-1, "========= Oops, called from DNS =========");
            }
        }
    }

    /* JADX INFO: renamed from: d, reason: collision with other method in class */
    private static boolean m51d(int i2) {
        switch (i2) {
            case 1:
                int iA = p.a();
                return iA != -1 ? f(iA + 1).indexOf("unknown") < 0 : iA != -1;
            case 2:
                return c() != -1;
            case 64:
                return e() != -1;
            case 256:
                return Build.VERSION.SDK_INT >= 14 && p.m131a(false) != null;
            default:
                return false;
        }
    }

    /* JADX INFO: renamed from: f, reason: collision with other method in class */
    private static void m56f() {
        if ((getFlags(-1) & 3) == 0) {
            return;
        }
        for (int i2 = 0; i2 < f36b.length; i2++) {
            try {
                if (m51d(f36b[i2])) {
                    if (f30a[i2] != null) {
                        f30a[i2].a(0, 0);
                    } else {
                        d(1, f36b[i2]);
                    }
                } else if (f30a[i2] != null) {
                    f30a[i2].c();
                    f30a[i2] = null;
                }
            } catch (Exception e2) {
                p.a(e2.toString());
            }
        }
    }

    static void a(boolean z, boolean z2) {
        if (f22a == null || z2) {
            if (z2 && f22a != null) {
                f22a.a();
            }
            f22a = new C0005a();
            return;
        }
        if (!z || !f22a.f129a) {
            return;
        }
        f22a.m90a();
    }

    public static void resetAll() {
        try {
            if (f19a != null) {
                f19a.clear();
                f19a.commit();
            }
            setNumChannels(3);
            if (getMode(2) != 1) {
                try {
                    setMode(2, 1);
                } catch (NMJException e2) {
                }
            }
            NetworkMidiSystem.m82a();
            m56f();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static void m18a() {
        if (!g) {
            f = true;
            for (int i2 = 0; i2 < f30a.length; i2++) {
                try {
                    if (f30a[i2] != null) {
                        f30a[i2].c();
                        f30a[i2] = null;
                    }
                } catch (Exception e2) {
                }
            }
            try {
                if (f26a != null) {
                    f26a.cancel();
                }
            } catch (Exception e3) {
            }
            try {
                if (f22a != null) {
                    f22a.a();
                }
            } catch (Exception e4) {
            }
            try {
                if (f21a != null) {
                    f21a.release();
                }
            } catch (Exception e5) {
            }
            try {
                f19a.putLong("lastClosed", System.currentTimeMillis());
                f19a.commit();
            } catch (Exception e6) {
            }
            f16a = 0L;
            try {
                f27a.removeAllElements();
            } catch (Exception e7) {
            }
            if (Build.VERSION.SDK_INT >= 12) {
                try {
                    f18a.unregisterReceiver(f17a);
                } catch (Exception e8) {
                }
                try {
                    f18a.unregisterReceiver(f32b);
                } catch (Exception e9) {
                }
                try {
                    x.c();
                } catch (Exception e10) {
                }
                try {
                    y.c();
                } catch (Exception e11) {
                }
            }
            try {
                if (f37c != null) {
                    p.logln(2, "Unregistering NetworkMonitor");
                    f18a.unregisterReceiver(f37c);
                }
            } catch (Exception e12) {
                p.a(e12, de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
            }
            g = true;
            try {
                int numChannels = getNumChannels();
                for (int i3 = 0; i3 < numChannels; i3++) {
                    f19a.remove("rtpState_" + i3);
                }
                f19a.commit();
            } catch (Exception e13) {
            }
            a = -1;
            f18a = null;
            p.logln(2, "Config - exited");
        }
    }

    static int b() {
        return p.b();
    }

    public static void setDebugLevel(int i2) {
        p.m125a(i2);
        if (p.b() <= 6 || f20a == null) {
            return;
        }
        g();
    }

    private static void g() {
        p.a(f20a.toString());
        Iterator<Map.Entry<String, ?>> it = f20a.getAll().entrySet().iterator();
        while (it.hasNext()) {
            p.logln(3, "pref " + it.next().toString());
        }
    }

    public static void setLogFile(File file) {
        p.a(file);
    }

    public static void setLogStream(PrintStream printStream) {
        p.a(printStream);
    }

    public static String getVersionInfo() {
        return "0.916";
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static short m17a(int i2) {
        try {
            return i2 == 0 ? Short.valueOf("0.916".substring(0, "0.916".indexOf("."))).shortValue() : Short.valueOf("0.916".substring("0.916".indexOf("."))).shortValue();
        } catch (Exception e2) {
            return (short) -1;
        }
    }

    public static String getSystemInfo() {
        StringBuffer stringBuffer = new StringBuffer("nmj ");
        try {
            stringBuffer.append(getVersionInfo());
            stringBuffer.append("\n\n");
            for (String str : m32a()) {
                stringBuffer.append(String.valueOf(str) + "\n");
            }
            stringBuffer.append("\n");
            stringBuffer.append(System.getProperty("os.name"));
            stringBuffer.append(" - ");
            stringBuffer.append(String.valueOf(System.getProperty("os.version")) + "\n");
            stringBuffer.append("Android: ");
            stringBuffer.append(Build.VERSION.RELEASE);
            stringBuffer.append("\n");
            stringBuffer.append(Build.MODEL);
            stringBuffer.append(" (");
            stringBuffer.append(Build.BOARD);
            stringBuffer.append(")\n");
            stringBuffer.append(String.valueOf(Build.MANUFACTURER) + " " + Build.DISPLAY + "\n");
        } catch (Exception e2) {
        }
        return stringBuffer.toString();
    }

    static void a(TimerTask timerTask, int i2, int i3) {
        if (!f) {
            if (f26a == null) {
                f26a = new Timer();
            }
            try {
                if (i3 > 0) {
                    f26a.scheduleAtFixedRate(timerTask, i2 >= 0 ? i2 : 0, i3);
                } else {
                    f26a.schedule(timerTask, i2);
                }
            } catch (IllegalStateException e2) {
                p.logln(7, "on schedule " + e2.toString());
                f26a = null;
                if (f31b == -1 || System.currentTimeMillis() - f31b >= 100) {
                    f31b = System.currentTimeMillis();
                    a(timerTask, i2, i3);
                }
            } catch (Exception e3) {
                p.logln(2, e3.toString());
            }
        }
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    static void m36b() {
        c.a(f18a, f23a);
    }

    /* JADX INFO: renamed from: e, reason: collision with other method in class */
    private static boolean m55e(int i2) {
        try {
            if (Build.VERSION.SDK_INT < 12) {
                return false;
            }
            HashMap<String, UsbDevice> deviceList = ((UsbManager) f18a.getSystemService("usb")).getDeviceList();
            if (deviceList.isEmpty()) {
                return false;
            }
            int flags = getFlags(-1);
            for (UsbDevice usbDevice : deviceList.values()) {
                if (usbDevice.getVendorId() != 1478) {
                    for (int numChannels = getNumChannels() - 1; numChannels > 0; numChannels--) {
                        if (getMode(numChannels) == i2) {
                            int localPort = (getLocalPort(numChannels) >> 16) & 65535;
                            int localPort2 = getLocalPort(numChannels) & 65535;
                            if (usbDevice.getVendorId() == localPort && usbDevice.getProductId() == localPort2) {
                                return true;
                            }
                        }
                    }
                    if (i2 == 5 && (flags & 128) == 0) {
                        if (usbDevice.getDeviceClass() == 1 && usbDevice.getDeviceSubclass() == 3) {
                            return true;
                        }
                        if (usbDevice.getDeviceClass() == 0 && usbDevice.getDeviceSubclass() == 0) {
                            if (usbDevice.getInterfaceCount() < 2) {
                                p.logln(2, "only 1 interface on " + usbDevice);
                            } else {
                                UsbManager usbManager = (UsbManager) f18a.getSystemService("usb");
                                if (!usbManager.hasPermission(usbDevice)) {
                                    p.logln(2, "qc host devices, no permission for " + usbDevice);
                                } else {
                                    UsbDeviceConnection usbDeviceConnectionOpenDevice = usbManager.openDevice(usbDevice);
                                    if (!usbDeviceConnectionOpenDevice.claimInterface(usbDevice.getInterface(1), true)) {
                                        p.logln(2, "USBMidi - failed to claim interface for " + usbDevice);
                                        usbDeviceConnectionOpenDevice.close();
                                    } else {
                                        UsbInterface usbInterface = usbDevice.getInterface(1);
                                        if (usbInterface.getInterfaceClass() != 1 || usbInterface.getInterfaceSubclass() != 3) {
                                            usbDeviceConnectionOpenDevice.close();
                                        } else {
                                            usbDeviceConnectionOpenDevice.close();
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    } else if (i2 == 7 && (flags & 256) == 0 && ((usbDevice.getDeviceClass() == 255 && usbDevice.getDeviceSubclass() == 255) || ((usbDevice.getDeviceClass() == 2 && usbDevice.getDeviceSubclass() == 0) || (usbDevice.getDeviceClass() == 0 && usbDevice.getDeviceSubclass() == 0)))) {
                        int interfaceClass = usbDevice.getInterface(0).getInterfaceClass();
                        if (interfaceClass == 255 || interfaceClass == 2) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e2) {
        }
        return false;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static void m23a(boolean z) {
        boolean z2;
        try {
            UsbManager usbManager = (UsbManager) f18a.getSystemService("usb");
            HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            if (deviceList.isEmpty()) {
                p.logln(3, "No Usb Interfaces present");
                for (int numChannels = getNumChannels() - 1; numChannels > 0; numChannels--) {
                    if (getMode(numChannels) == 5) {
                        deleteChannel(numChannels);
                    }
                    if (getMode(numChannels) == 7) {
                        deleteChannel(numChannels);
                    }
                }
                return;
            }
            for (int numChannels2 = getNumChannels() - 1; numChannels2 > 0; numChannels2--) {
                if (getMode(numChannels2) == 5) {
                    int localPort = (getLocalPort(numChannels2) >> 16) & 65535;
                    int localPort2 = getLocalPort(numChannels2) & 65535;
                    Iterator<UsbDevice> it = deviceList.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z2 = false;
                            break;
                        }
                        UsbDevice next = it.next();
                        if (next.getVendorId() != 1478 && next.getVendorId() == localPort && next.getProductId() == localPort2) {
                            p.logln(3, "Previously known Usb Interface is present: " + next);
                            p.logln(3, "Permission: " + usbManager.hasPermission(next));
                            h();
                            z2 = true;
                            break;
                        }
                    }
                    if (!z2) {
                        deleteChannel(numChannels2);
                    }
                }
            }
            x.a(f23a);
        } catch (Exception e2) {
        }
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    static void m39b(boolean z) {
        boolean z2;
        if (z) {
            try {
                UsbManager usbManager = (UsbManager) f18a.getSystemService("usb");
                HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
                if (deviceList.isEmpty()) {
                    p.logln(3, "No Usb Interfaces present");
                    for (int numChannels = getNumChannels() - 1; numChannels > 0; numChannels--) {
                        if (getMode(numChannels) == 7) {
                            deleteChannel(numChannels);
                        }
                        if (getMode(numChannels) == 5) {
                            deleteChannel(numChannels);
                        }
                    }
                    return;
                }
                for (int numChannels2 = getNumChannels() - 1; numChannels2 > 0; numChannels2--) {
                    if (getMode(numChannels2) == 7) {
                        int localPort = (getLocalPort(numChannels2) >> 16) & 65535;
                        int localPort2 = getLocalPort(numChannels2) & 65535;
                        Iterator<UsbDevice> it = deviceList.values().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z2 = false;
                                break;
                            }
                            UsbDevice next = it.next();
                            if (next.getVendorId() != 1478 && next.getVendorId() == localPort && next.getProductId() == localPort2) {
                                p.logln(3, "Previously known Usb Serial Interface is present: " + next);
                                p.logln(3, "Permission: " + usbManager.hasPermission(next));
                                h();
                                z2 = true;
                                break;
                            }
                        }
                        if (!z2) {
                            deleteChannel(numChannels2);
                        }
                    }
                }
            } catch (Exception e2) {
                return;
            }
        }
        y.a(f23a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void h() {
        if (!f35b) {
            try {
                IntentFilter intentFilter = new IntentFilter("android.hardware.usb.action.USB_DEVICE_DETACHED");
                f32b = new BroadcastReceiver() { // from class: de.humatic.nmj.NMJConfig.3
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(intent.getAction())) {
                            synchronized (this) {
                                p.logln(3, "USB_DEVICE_DETACHED " + intent);
                                UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                                for (int numChannels = NMJConfig.getNumChannels() - 1; numChannels > 0; numChannels--) {
                                    if (NMJConfig.getMode(numChannels) == 5 || NMJConfig.getMode(numChannels) == 7) {
                                        int localPort = (NMJConfig.getLocalPort(numChannels) >> 16) & 65535;
                                        int localPort2 = NMJConfig.getLocalPort(numChannels) & 65535;
                                        if (usbDevice.getVendorId() == localPort && usbDevice.getProductId() == localPort2) {
                                            p.logln(2, "Removing USB_HOST channel " + NMJConfig.getName(numChannels));
                                            if (NetworkMidiSystem.get().isOpen(0, numChannels)) {
                                                NetworkMidiSystem.get().a(0, numChannels).close(null);
                                            }
                                            if (NetworkMidiSystem.get().isOpen(1, numChannels)) {
                                                NetworkMidiSystem.get().a(1, numChannels).close(null);
                                            }
                                            if (NMJConfig.getMode(numChannels) == 5) {
                                                x.e();
                                            } else {
                                                y.e();
                                            }
                                            NMJConfig.deleteChannel(numChannels);
                                        }
                                    }
                                }
                                try {
                                    x.e();
                                } catch (Exception e2) {
                                    try {
                                        y.e();
                                    } catch (Exception e3) {
                                    }
                                }
                                if ((NMJConfig.getFlags(-1) & 32) != 0) {
                                    NMJConfig.i();
                                }
                                int i2 = 0;
                                for (int i3 = 0; i3 < NMJConfig.getNumChannels(); i3++) {
                                    if (NMJConfig.getMode(i3) == 5) {
                                        i2++;
                                    }
                                }
                                if (i2 == 0) {
                                    NMJConfig.f18a.unregisterReceiver(this);
                                }
                                NMJConfig.f35b = false;
                            }
                        }
                    }
                };
                f18a.registerReceiver(f32b, intentFilter);
                p.logln(3, "USB detachment receiver registered");
                f35b = true;
            } catch (Exception e2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void i() {
        if (!f39c) {
            try {
                f17a = new BroadcastReceiver() { // from class: de.humatic.nmj.NMJConfig.4
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if (NMJConfig.USB_DEVICE_ATTACHED.equals(intent.getAction())) {
                            synchronized (this) {
                                try {
                                    p.logln(3, "DEVICE_ATTACHED " + intent);
                                    if ((NMJConfig.getFlags(-1) & 128) == 0) {
                                        NMJConfig.m23a(true);
                                    } else if ((NMJConfig.getFlags(-1) & 256) == 0) {
                                        NMJConfig.m39b(true);
                                    }
                                    NMJConfig.f18a.unregisterReceiver(this);
                                    NMJConfig.f39c = false;
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }
                };
                f18a.registerReceiver(f17a, new IntentFilter(USB_DEVICE_ATTACHED));
                f39c = true;
                p.logln(3, "USB attachment receiver running ");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static /* synthetic */ boolean m26a(int i2, int i3) {
        int iE;
        if (i3 == 1) {
            int iF = f();
            if (f20a.getInt("nwa_" + i2, -1) > 0 && f20a.getInt("nwa_" + i2, -1) != iF) {
                if (f20a.getInt("nwa_" + i2, -1) != -1) {
                    p.logln(4, "updating adapter on " + getName(i2) + ": Wifi");
                }
                setNetworkAdapter(i2, -1);
                return true;
            }
        } else if (i3 == 2) {
            int iC = c();
            if (f20a.getInt("nwa_" + i2, -1) != iC) {
                if (f20a.getInt("nwa_" + i2, -1) != -1) {
                    p.logln(4, "updating adapter on " + getName(i2) + ": Usb, idx: " + iC);
                }
                setNetworkAdapter(i2, iC);
                return true;
            }
        } else if (i3 == 256) {
            int iD = d();
            if (f20a.getInt("nwa_" + i2, -1) != iD) {
                if (f20a.getInt("nwa_" + i2, -1) != -1) {
                    p.logln(4, "updating adapter on " + getName(i2) + ": P2P, idx: " + iD);
                }
                setNetworkAdapter(i2, iD);
                return true;
            }
        } else if (i3 == 64 && f20a.getInt("nwa_" + i2, -1) != (iE = e())) {
            if (f20a.getInt("nwa_" + i2, -1) != -1) {
                p.logln(4, "updating adapter on " + getName(i2) + ": BT-pan, idx: " + iE);
            }
            setNetworkAdapter(i2, iE);
            return true;
        }
        return false;
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    static /* synthetic */ String m35b(int i2) {
        switch (i2) {
            case 1:
                return "WiFi";
            case 2:
                return "Usb";
            case 64:
                return "BT-pan";
            case 256:
                return "P2P";
            default:
                return "Unknown connectivity " + String.valueOf(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void j() {
        try {
            f18a.unregisterReceiver(f37c);
        } catch (Exception e2) {
        }
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            if (Build.VERSION.SDK_INT >= 14) {
                intentFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
            }
            intentFilter.addAction("android.net.conn.TETHER_STATE_CHANGED");
            if ((getFlags(-1) & 16) != 0) {
                intentFilter.addAction("android.hardware.usb.action.USB_STATE");
            }
            f37c = new BroadcastReceiver() { // from class: de.humatic.nmj.NMJConfig.5
                /* JADX WARN: Removed duplicated region for block: B:133:0x01e2  */
                @Override // android.content.BroadcastReceiver
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onReceive(android.content.Context r13, android.content.Intent r14) {
                    /*
                        Method dump skipped, instruction units count: 507
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.NMJConfig.AnonymousClass5.onReceive(android.content.Context, android.content.Intent):void");
                }
            };
            p.logln(2, "Registering NetworkMonitor");
            f18a.registerReceiver(f37c, intentFilter);
        } catch (Exception e3) {
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static Object m13a() {
        if (f24a == null) {
            f24a = new Object();
        }
        return f24a;
    }
}
