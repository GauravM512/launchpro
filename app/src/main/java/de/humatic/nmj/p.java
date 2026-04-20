package de.humatic.nmj;

import android.os.Build;
import android.util.Base64;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class p {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static String f250a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static final byte[] f253a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static int[] f254a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static String[] f255a;
    private static String b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private static byte[] f256b;
    private static String c;
    private static String d;
    private static String e;
    private static String f;
    private static String g;
    private static String h;
    private static String i;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static PrintStream f249a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static StringBuffer f251a = new StringBuffer();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static boolean f252a = true;
    private static int a = 0;

    p() {
    }

    static {
        new SimpleDateFormat("yy/MM/dd;HH:mm:ss.SS");
        f254a = new int[]{128, 144, 160, 176, 192, 208, 224, 240, 248, 249, 250, 251, 252, 255, 256};
        f255a = new String[]{"Note off", "Note on", "poly.Aftertouch", "Controller", "Prg.Change", "Aftertouch", "Pitch", "SysEx", de.bassapps.launchbuttonsP.BuildConfig.FLAVOR, "Measure", "   Play", "  Play / Continue", "   Stop", "undefined", de.bassapps.launchbuttonsP.BuildConfig.FLAVOR};
        f253a = new byte[]{-16, 125, 0, 0, 109, 110, 101, 116};
        f256b = new byte[1];
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static void m123a() {
        f250a = null;
        b = null;
        c = null;
        d = null;
        e = null;
        f = null;
        g = null;
        h = null;
        i = null;
    }

    static String a(byte b2) {
        return (b2 & 255) < 16 ? "0" + Long.toHexString(b2 & 255) : Long.toHexString(b2 & 255);
    }

    static String a(short s) {
        return (s & 255) < 16 ? "00" + Long.toHexString(s & 15) : (s & 255) < 256 ? "0" + Long.toHexString(s & 255) : Long.toHexString(65535 & s);
    }

    static String a(int i2) {
        return i2 == -1 ? "-1" : i2 < 16 ? "0" + Long.toHexString(i2) : Long.toHexString(i2);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static void m126a(byte[] bArr, int i2, int i3) {
        a(-1, de.bassapps.launchbuttonsP.BuildConfig.FLAVOR, bArr, i2, i3);
    }

    static void a(String str, byte[] bArr) {
        a(-1, str, bArr, 0, bArr.length);
    }

    static void a(String str, byte[] bArr, int i2, int i3) {
        a(-1, str, bArr, i2, i3);
    }

    static void a(int i2, String str, byte[] bArr) {
        a(i2, str, bArr, 0, bArr.length);
    }

    static synchronized void a(int i2, String str, byte[] bArr, int i3, int i4) {
        if (i2 <= a) {
            if (str.length() > 0) {
                a(-1, str);
                if (str.length() >= 8) {
                    a(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                } else {
                    b("  ");
                }
            }
            int i5 = i3 < 0 ? 0 : i3;
            if (i4 <= i5) {
                i4 += i5;
            }
            int iMin = Math.min(i4, bArr.length);
            int i6 = 0;
            boolean z = false;
            for (int i7 = i5; i7 < iMin; i7++) {
                z = false;
                try {
                    String strA = a(bArr[i7]);
                    a(-1, String.valueOf(strA.length() < 2 ? "0" : de.bassapps.launchbuttonsP.BuildConfig.FLAVOR) + strA + " ");
                    if ((i6 + 1) % 8 == 0 && (i6 + 1) % 16 != 0) {
                        a(-1, "  ");
                    } else if ((i6 + 1) % 16 == 0) {
                        z = true;
                        b("  ");
                        for (int i8 = i7 - 15; i8 <= i7; i8++) {
                            try {
                                if ((bArr[i8] & 255) <= 32 || (bArr[i8] & 255) >= 128) {
                                    b(".");
                                } else {
                                    m124a(bArr[i8]);
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                        a(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                i6++;
            }
            if (!z) {
                for (int i9 = (iMin - i5) % 16; i9 < 15; i9++) {
                    b("   ");
                }
                if ((iMin - i5) % 16 > 8) {
                    b("  ");
                }
                b(" ");
                b("  ");
                for (int i10 = iMin - ((iMin - i5) % 16); i10 < iMin; i10++) {
                    try {
                        if (bArr[i10] <= 32 || (bArr[i10] & 255) >= 128) {
                            b(".");
                        } else {
                            m124a(bArr[i10]);
                        }
                    } catch (Exception e4) {
                    }
                }
                a(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
            }
            if ((iMin - i5) % 16 != 0) {
                a(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static short m122a(byte[] bArr, int i2) {
        return (short) a(bArr, i2, 2);
    }

    static int a(byte[] bArr, int i2) {
        return a(bArr, i2, 4);
    }

    static int a(byte[] bArr, int i2, int i3) {
        int i4 = i3 - 1;
        int i5 = 0;
        int i6 = i2;
        while (i6 < i2 + i3) {
            i5 |= (bArr[i6] & 255) << (i4 << 3);
            i6++;
            i4--;
        }
        return i5;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static long m118a(byte[] bArr, int i2) {
        long j = 0;
        int i3 = 7;
        int i4 = i2;
        while (i4 < i2 + 8) {
            j |= ((long) (bArr[i4] & 255)) << (i3 << 3);
            i4++;
            i3--;
        }
        return j;
    }

    static void a(short s, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < 2; i3++) {
            bArr[i2 + i3] = (byte) (s >> ((1 - i3) << 3));
        }
    }

    static void a(int i2, byte[] bArr, int i3) {
        for (int i4 = 0; i4 < 4; i4++) {
            bArr[i3 + i4] = (byte) (i2 >> ((3 - i4) << 3));
        }
    }

    static void a(int i2, byte[] bArr, int i3, int i4) {
        for (int i5 = 0; i5 < 2; i5++) {
            bArr[i3 + i5] = (byte) (i2 >> ((1 - i5) << 3));
        }
    }

    static void a(long j, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < 8; i3++) {
            bArr[i2 + i3] = (byte) ((j >> ((7 - i3) << 3)) & 255);
        }
    }

    static int a(byte[] bArr, int i2, int i3, byte[] bArr2) {
        for (int i4 = i2; i4 < i3; i4++) {
            for (int i5 = 0; i5 < bArr2.length && bArr[i4 + i5] == bArr2[i5]; i5++) {
                try {
                    if (i5 == bArr2.length - 1) {
                        return i4;
                    }
                } catch (ArrayIndexOutOfBoundsException e2) {
                }
            }
        }
        return -1;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static boolean m128a(String str) {
        try {
            return Integer.valueOf(str.substring(0, str.indexOf("."))).intValue() >= 223;
        } catch (Exception e2) {
            return false;
        }
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    static boolean m133b(String str) {
        if (str == null) {
            return false;
        }
        return str.indexOf("eth") == 0 || str.indexOf("wlan") == 0 || str.indexOf("mlan") == 0 || str.indexOf("tiwlan") == 0 || str.indexOf("ra") == 0 || str.indexOf("wl0") == 0 || str.indexOf("ap0") == 0 || str.indexOf("wifi") == 0;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static NetworkInterface m121a(int i2) throws Exception {
        if (i2 == 1) {
            return m120a();
        }
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (i2 == 2) {
                    if ((networkInterfaceNextElement.getName().indexOf("usb") == -1 || networkInterfaceNextElement.getName().indexOf("rmnet") != -1) && networkInterfaceNextElement.getName().toLowerCase().indexOf("rndis") == -1) {
                    }
                    return networkInterfaceNextElement;
                }
                if (i2 == 256) {
                    if (networkInterfaceNextElement.getName().indexOf("p2p-") != -1) {
                        return networkInterfaceNextElement;
                    }
                } else if (i2 == 64 && networkInterfaceNextElement.getName().indexOf("bt-pan") != -1) {
                    return networkInterfaceNextElement;
                }
            }
        } catch (Exception e2) {
        }
        return null;
    }

    static int a() {
        try {
            NetworkInterface networkInterfaceM120a = m120a();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            int i2 = -1;
            while (networkInterfaces.hasMoreElements()) {
                i2++;
                if (networkInterfaces.nextElement().getName().equalsIgnoreCase(networkInterfaceM120a.getName())) {
                    return i2;
                }
            }
        } catch (Exception e2) {
        }
        return -1;
    }

    static InetAddress a(boolean z) {
        try {
            try {
                Enumeration<InetAddress> inetAddresses = m120a().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (z || !(inetAddressNextElement instanceof Inet4Address)) {
                        if (z && (inetAddressNextElement instanceof Inet6Address)) {
                            return inetAddressNextElement;
                        }
                    } else {
                        return inetAddressNextElement;
                    }
                }
            } catch (Exception e2) {
            }
            return InetAddress.getLocalHost();
        } catch (Exception e3) {
            return null;
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static NetworkInterface m120a() {
        String name;
        String name2;
        int i2 = 0;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                int i3 = i2 + 1;
                try {
                    NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                    try {
                        name = networkInterfaceNextElement.getName();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        i2 = i3;
                    }
                    if (name.indexOf("eth") == 0 || name.indexOf("wlan") == 0 || name.indexOf("tiwlan") == 0 || name.indexOf("mlan") == 0 || name.indexOf("wifi") == 0 || name.indexOf("wl0") == 0 || name.indexOf("ap0") == 0 || name.indexOf("ra") == 0) {
                        if (name.indexOf("eth") == -1) {
                            return networkInterfaceNextElement;
                        }
                        while (networkInterfaces.hasMoreElements()) {
                            NetworkInterface networkInterfaceNextElement2 = networkInterfaces.nextElement();
                            try {
                                name2 = networkInterfaceNextElement2.getName();
                            } catch (Exception e3) {
                            }
                            if (name2.indexOf("wlan") == 0 || name2.indexOf("mlan") == 0 || name2.indexOf("tiwlan") == 0 || name2.indexOf("wifi") == 0 || name2.indexOf("ra") == 0) {
                                return networkInterfaceNextElement2;
                            }
                        }
                        return networkInterfaceNextElement;
                    }
                    i2 = i3;
                } catch (Exception e4) {
                    i2 = i3;
                }
            }
        } catch (Exception e5) {
        }
        if (i2 == 0) {
            logln(5, "Oops, interface list is empty");
        }
        return null;
    }

    static boolean a(InetAddress inetAddress, int i2) throws Exception {
        switch (i2) {
            case 1:
                if (inetAddress instanceof Inet6Address) {
                    if (!((Inet6Address) inetAddress).isAnyLocalAddress() && !((Inet6Address) inetAddress).isLoopbackAddress()) {
                        if (c == null) {
                            c = a(true).toString();
                        }
                        if (c.indexOf("/") != -1) {
                            c = c.substring(c.indexOf("/") + 1);
                        }
                        if (inetAddress.toString().equalsIgnoreCase(c) || inetAddress.toString().equalsIgnoreCase(Inet6Address.getLocalHost().getHostAddress())) {
                            return true;
                        }
                    }
                    return true;
                }
                if (f250a == null) {
                    f250a = NMJConfig.a(false);
                }
                if (!((Inet4Address) inetAddress).isAnyLocalAddress() && !((Inet4Address) inetAddress).isLoopbackAddress()) {
                    if (b == null) {
                        b = a(false).toString();
                    }
                    if (b.indexOf("/") != -1) {
                        b = b.substring(b.indexOf("/") + 1);
                    }
                    if (inetAddress.toString().equalsIgnoreCase(b) || inetAddress.toString().equalsIgnoreCase(InetAddress.getLocalHost().getHostAddress()) || inetAddress.toString().equalsIgnoreCase("127.0.0.1") || inetAddress.toString().equalsIgnoreCase(f250a)) {
                        return true;
                    }
                }
                return true;
            case 2:
                if (inetAddress instanceof Inet6Address) {
                    if (e != null) {
                        return inetAddress.toString().equalsIgnoreCase(e);
                    }
                    Object[] objArrB = b(true);
                    if (objArrB != null) {
                        try {
                            e = objArrB[1].toString();
                            return inetAddress.toString().equalsIgnoreCase(e);
                        } catch (Exception e2) {
                        }
                    }
                } else {
                    if (d != null) {
                        return inetAddress.toString().equalsIgnoreCase(d);
                    }
                    Object[] objArrB2 = b(false);
                    if (objArrB2 != null) {
                        try {
                            d = objArrB2[1].toString();
                            return inetAddress.toString().equalsIgnoreCase(d);
                        } catch (Exception e3) {
                        }
                    }
                }
                break;
            case 64:
                if (inetAddress instanceof Inet6Address) {
                    if (i != null) {
                        return inetAddress.toString().equalsIgnoreCase(i);
                    }
                    Object[] objArrC = c(true);
                    if (objArrC != null) {
                        try {
                            i = objArrC[1].toString();
                            return inetAddress.toString().equalsIgnoreCase(i);
                        } catch (Exception e4) {
                        }
                    }
                } else {
                    if (h != null) {
                        return inetAddress.toString().equalsIgnoreCase(h);
                    }
                    Object[] objArrM131a = m131a(false);
                    if (objArrM131a != null) {
                        try {
                            h = objArrM131a[1].toString();
                            return inetAddress.toString().equalsIgnoreCase(h);
                        } catch (Exception e5) {
                        }
                    }
                }
                break;
            case 256:
                if (inetAddress instanceof Inet6Address) {
                    if (Build.VERSION.SDK_INT >= 14) {
                        if (g != null) {
                            return inetAddress.toString().equalsIgnoreCase(g);
                        }
                        Object[] objArrM131a2 = m131a(true);
                        if (objArrM131a2 != null) {
                            try {
                                g = objArrM131a2[1].toString();
                                return inetAddress.toString().equalsIgnoreCase(g);
                            } catch (Exception e6) {
                            }
                        }
                    }
                } else if (Build.VERSION.SDK_INT >= 14) {
                    if (f != null) {
                        return inetAddress.toString().equalsIgnoreCase(f);
                    }
                    Object[] objArrM131a3 = m131a(false);
                    if (objArrM131a3 != null) {
                        try {
                            f = objArrM131a3[1].toString();
                            return inetAddress.toString().equalsIgnoreCase(f);
                        } catch (Exception e7) {
                        }
                    }
                }
                break;
        }
        return false;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static Object[] m131a(boolean z) {
        Object[] objArr;
        if (Build.VERSION.SDK_INT < 14) {
            return null;
        }
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                try {
                    if (networkInterfaceNextElement.getName().indexOf("p2p") != -1) {
                        try {
                            Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                            while (inetAddresses.hasMoreElements()) {
                                InetAddress inetAddressNextElement = inetAddresses.nextElement();
                                if ((inetAddressNextElement instanceof Inet6Address) && z) {
                                    objArr = new Object[]{networkInterfaceNextElement, inetAddressNextElement};
                                } else if ((inetAddressNextElement instanceof Inet4Address) && !z) {
                                    objArr = new Object[]{networkInterfaceNextElement, inetAddressNextElement};
                                }
                                return objArr;
                            }
                        } catch (Exception e2) {
                        }
                    } else {
                        continue;
                    }
                } catch (Exception e3) {
                }
            }
        } catch (Exception e4) {
        }
        return null;
    }

    private static Object[] b(boolean z) {
        Object[] objArr;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                try {
                    String name = networkInterfaceNextElement.getName();
                    if (name.indexOf("usb") == 0 || name.indexOf("rndis") != -1) {
                        try {
                            Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                            while (inetAddresses.hasMoreElements()) {
                                InetAddress inetAddressNextElement = inetAddresses.nextElement();
                                if ((inetAddressNextElement instanceof Inet6Address) && z) {
                                    objArr = new Object[]{networkInterfaceNextElement, inetAddressNextElement};
                                } else if ((inetAddressNextElement instanceof Inet4Address) && !z) {
                                    objArr = new Object[]{networkInterfaceNextElement, inetAddressNextElement};
                                }
                                return objArr;
                            }
                        } catch (Exception e2) {
                        }
                    }
                } catch (Exception e3) {
                }
            }
        } catch (Exception e4) {
        }
        return null;
    }

    private static Object[] c(boolean z) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                try {
                    if (networkInterfaceNextElement.getName().indexOf("bt-pan") != -1) {
                        try {
                            Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                            while (inetAddresses.hasMoreElements()) {
                                InetAddress inetAddressNextElement = inetAddresses.nextElement();
                                if (inetAddressNextElement instanceof Inet6Address) {
                                    return new Object[]{networkInterfaceNextElement, inetAddressNextElement};
                                }
                            }
                        } catch (Exception e2) {
                        }
                    } else {
                        continue;
                    }
                } catch (Exception e3) {
                }
            }
        } catch (Exception e4) {
        }
        return null;
    }

    static DatagramSocket a(int i2, int i3) throws Exception {
        NetworkInterface networkInterfaceM120a;
        InetAddress inetAddressNextElement;
        InetAddress inetAddress;
        NetworkInterface networkInterfaceM120a2;
        if (i3 < 0 || ((i3 > 0 && i3 < 1024) || i3 > 65535)) {
            i3 = (int) (10000.0d + (Math.random() * 1000.0d));
        }
        if (i2 > 0) {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            int i4 = 0;
            NetworkInterface networkInterfaceNextElement = null;
            while (true) {
                if (!networkInterfaces.hasMoreElements()) {
                    networkInterfaceM120a = networkInterfaceNextElement;
                    break;
                }
                if (i4 >= i2) {
                    networkInterfaceM120a = networkInterfaceNextElement;
                    break;
                }
                networkInterfaceNextElement = networkInterfaces.nextElement();
                i4++;
            }
        } else {
            networkInterfaceM120a = m120a();
        }
        try {
            Enumeration<InetAddress> inetAddresses = networkInterfaceM120a.getInetAddresses();
            inetAddress = null;
            while (true) {
                if (!inetAddresses.hasMoreElements()) {
                    break;
                }
                InetAddress inetAddressNextElement2 = inetAddresses.nextElement();
                if (inetAddressNextElement2 instanceof Inet4Address) {
                    inetAddress = inetAddressNextElement2;
                    break;
                }
                inetAddress = inetAddressNextElement2;
            }
        } catch (NoSuchElementException e2) {
            inetAddressNextElement = NetworkInterface.getNetworkInterfaces().nextElement().getInetAddresses().nextElement();
        }
        if (inetAddress == null || (inetAddress != null && !(inetAddress instanceof Inet4Address))) {
            if (networkInterfaceM120a.getName().indexOf("eth") != -1 && (networkInterfaceM120a2 = m120a()) != null && networkInterfaceM120a2.getName().indexOf("eth") < 0) {
                try {
                    Enumeration<InetAddress> inetAddresses2 = networkInterfaceM120a2.getInetAddresses();
                    if (inetAddresses2.hasMoreElements()) {
                        InetAddress inetAddressNextElement3 = inetAddresses2.nextElement();
                        if (!(inetAddressNextElement3 instanceof Inet4Address)) {
                            return new DatagramSocket(i3, inetAddressNextElement3);
                        }
                    }
                    throw new NoSuchElementException();
                } catch (Exception e3) {
                }
            }
            throw new IOException("Invalid address on " + networkInterfaceM120a + ": " + inetAddress);
        }
        inetAddressNextElement = inetAddress;
        return new DatagramSocket(i3, inetAddressNextElement);
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    static void m132b() {
        if (a >= 2) {
            try {
                a("Network interfaces:");
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                    a("   " + networkInterfaceNextElement.toString());
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        a("      " + inetAddresses.nextElement().toString());
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    static String a(int i2, int i3, int i4) {
        switch (i2) {
            case 240:
                String str = "SysEx";
                if (i3 == 1 && i4 == 1) {
                    str = String.valueOf("SysEx") + ": std. full frame";
                }
                if (i3 == 6 && i4 == 68) {
                    str = String.valueOf(str) + ": MMC position";
                }
                if (i3 == 6 && i4 == 1) {
                    str = String.valueOf(str) + ": MMC Stop";
                }
                if (i3 == 6 && i4 == 2) {
                    str = String.valueOf(str) + ": MMC Play";
                }
                if (i3 == 6 && i4 == 3) {
                    str = String.valueOf(str) + ": MMC Deferred Play";
                }
                if (i3 == 6 && i4 == 4) {
                    str = String.valueOf(str) + ": MMC Fast Forward";
                }
                if (i3 == 6 && i4 == 5) {
                    str = String.valueOf(str) + ": MMC Rewind";
                }
                if (i3 == 6 && i4 == 6) {
                    str = String.valueOf(str) + ": MMC Record Strobe (Punch In)";
                }
                if (i3 == 6 && i4 == 7) {
                    str = String.valueOf(str) + ": MMC Record Exit (Punch out)";
                }
                return (i3 == 6 && i4 == 9) ? String.valueOf(str) + ": MMC Pause" : str;
            case 241:
                return "MIDI Timecode";
            case 242:
                return "Song Position Pointer";
            case 243:
            case 244:
            case 245:
            case 246:
            case 247:
            case 249:
            default:
                return b(i2);
            case 248:
                return "MIDI Clock";
            case 250:
                return "MIDI start";
            case 251:
                return "MIDI continue";
            case 252:
                return "MIDI stop";
        }
    }

    private static String b(int i2) {
        int i3 = 0;
        while (true) {
            int[] iArr = f254a;
            if (i3 < 14) {
                if (i2 < f254a[i3] || i2 >= f254a[i3 + 1]) {
                    i3++;
                } else {
                    return new String("  " + f255a[i3] + "  Ch." + new Integer((i2 - f254a[i3]) + 1).toString());
                }
            } else {
                return new String("  unknown statusbyte");
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static boolean m127a(int i2, int i3) {
        switch (i2) {
            case 240:
                if ((i3 & 2) == 0) {
                }
                break;
            case 241:
                if ((i3 & 1) == 0) {
                }
                break;
            case 242:
                if ((i3 & 16) == 0) {
                }
                break;
            case 243:
            case 244:
            case 245:
            case 246:
            case 247:
            case 249:
            default:
                if ((i3 & 32) == 0) {
                    break;
                }
                break;
            case 248:
                if ((i3 & 8) == 0) {
                }
                break;
            case 250:
            case 251:
            case 252:
                if ((i3 & 4) == 0) {
                }
                break;
        }
        return true;
    }

    static int b(byte[] bArr, int i2) {
        byte b2 = bArr[i2];
        if ((b2 & 255) < 240) {
            return ((b2 & 255) < 192 || (b2 & 255) >= 224) ? 2 : 1;
        }
        if ((b2 & 255) > 240) {
            if ((b2 & 255) == 242) {
                return 2;
            }
            return ((b2 & 255) == 241 || (b2 & 255) == 243) ? 1 : 0;
        }
        if ((b2 & 255) != 240) {
            return -1;
        }
        int i3 = i2 + 1;
        while (i3 < bArr.length && (bArr[i3] & 255) != 247) {
            i3++;
        }
        if (i3 != bArr.length || (bArr[bArr.length - 1] & 255) == 247) {
            return i3 - i2;
        }
        return -1;
    }

    protected static void a(byte[] bArr, Vector<byte[]> vector) {
        int iB = 0;
        byte b2 = 0;
        int i2 = 0;
        while (i2 < bArr.length) {
            if ((bArr[i2] & 128) != 0) {
                b2 = bArr[i2];
                iB = b(bArr, i2);
                i2++;
            }
            if (iB > 0) {
                byte[] bArr2 = new byte[iB + 1];
                bArr2[0] = b2;
                System.arraycopy(bArr, i2, bArr2, 1, iB);
                vector.add(bArr2);
                i2 += iB;
            } else if (iB == 0) {
                vector.add(new byte[]{b2});
            } else {
                byte[] bArr3 = new byte[bArr.length - i2];
                bArr3[0] = b2;
                System.arraycopy(bArr, i2, bArr3, 1, bArr.length - i2);
                vector.add(bArr3);
                return;
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static byte[] m130a(int i2, int i3) {
        switch (i2) {
            case 0:
                return new byte[]{-16, 125, 0, 0, 109, 110, 101, 116, (byte) i2, (byte) i3, -9};
            case 1:
                byte[] bArr = new byte[i3];
                byte[] bArr2 = f253a;
                byte[] bArr3 = f253a;
                System.arraycopy(bArr2, 0, bArr, 0, 8);
                bArr[8] = (byte) i2;
                bArr[i3 - 1] = -9;
                return bArr;
            default:
                return null;
        }
    }

    static boolean a(Vector<Byte> vector) {
        if (vector.size() < 7) {
            return false;
        }
        int i2 = 4;
        int i3 = 24;
        int iByteValue = 0;
        while (true) {
            int i4 = i2;
            if (i4 >= Math.min(vector.size(), 8)) {
                return false;
            }
            iByteValue |= vector.get(i4).byteValue() << i3;
            i3 -= 8;
            if (i4 == 6 && iByteValue == 7236970) {
                return true;
            }
            if (i4 == 7 && iByteValue == 1835951476) {
                return true;
            }
            i2 = i4 + 1;
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static boolean m129a(byte[] bArr) {
        if (bArr.length <= 8 || a(bArr, 4, 4) != 1835951476) {
            return bArr.length >= 7 && a(bArr, 4, 3) == 7236970;
        }
        return true;
    }

    static int a(byte[] bArr) {
        return (bArr.length <= 8 || a(bArr, 4, 4) != 1835951476) ? (bArr.length < 7 || a(bArr, 4, 3) != 7236970) ? -1 : 0 : (bArr[8] << 8) | bArr[9];
    }

    static int b(byte[] bArr) {
        if (bArr.length > 8 && a(bArr, 4, 4) == 1835951476) {
            switch (bArr[8]) {
                case 0:
                    return bArr[9] & 255;
                case 1:
                    return (bArr[10] << 7) | bArr[11];
            }
        }
        if (bArr.length >= 7 && a(bArr, 4, 3) == 7236970) {
            return bArr[6] & 255;
        }
        return -1;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static String m119a() {
        if (Build.VERSION.SDK_INT < 8) {
            return "NO_BASE64_IN_API<8";
        }
        byte[] bArr = new byte[16];
        for (int i2 = 0; i2 < 16; i2++) {
            bArr[i2] = (byte) (Math.random() * 256.0d);
        }
        return Base64.encodeToString(bArr, 0).trim();
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private static void m124a(byte b2) {
        f256b[0] = b2;
        if (f249a != null) {
            f249a.print(new String(f256b));
        } else {
            f251a.append(new String(f256b));
        }
        f252a = false;
    }

    private static void b(String str) {
        f252a = str.indexOf("\n") != -1;
        if (f249a == null) {
            f251a.append(str);
            if (f252a) {
                Log.i("nmj", f251a.toString());
                f251a.setLength(0);
                return;
            }
            return;
        }
        f249a.print(str);
    }

    public static synchronized void a(int i2, String str) {
        if (i2 >= 0) {
            if (a >= i2) {
            }
        }
        b(str);
    }

    public static synchronized void a(String str) {
        if (f249a != null) {
            f249a.println(str);
        } else if (f251a.length() > 0) {
            f251a.append(str);
            Log.i("nmj", f251a.toString());
            f251a.setLength(0);
        } else {
            Log.i("nmj", str);
        }
        f252a = true;
    }

    static synchronized void logln(int i2, String str) {
        if (i2 >= 0) {
            if (a >= i2) {
            }
        }
        a(str);
    }

    public static void a(int i2, String str, int i3, int i4, int i5) {
        if (a >= 4) {
            b(str);
            switch (i4) {
                case -1:
                    a("ch " + i3 + " REMOVED");
                    break;
                case 1:
                    a("ch " + i3 + " OPENED");
                    break;
                case 2:
                    a("ch " + i3 + " CLOSED");
                    break;
                case 4:
                    switch (i5) {
                        case 4:
                            a("ch " + i3 + " WAITING");
                            break;
                        case 8:
                            a("ch " + i3 + " DISCOVERED");
                            break;
                        case 16:
                            a("ch " + i3 + " PRESENT");
                            break;
                        case 32:
                            a("ch " + i3 + " CONNECTED");
                            break;
                        case 64:
                            a("ch " + i3 + " CLIENT_CONNECTED");
                            break;
                        case 128:
                            a("ch " + i3 + " DISCONNECTED");
                            break;
                        case 256:
                            a("ch " + i3 + " CLIENT_DISCONNECTED");
                            break;
                        case 512:
                            a("ch " + i3 + " CH_GONE");
                            break;
                        case 1024:
                            a("ch " + i3 + " CH_LOST");
                            break;
                        case 2048:
                            a("ch " + i3 + " CONNECTION_REFUSED");
                            break;
                        case 4096:
                            a("ch " + i3 + " NO_RESPONSE");
                            break;
                        case 8192:
                            a("ch " + i3 + " PKT_LOSS");
                            break;
                        case NMJConfig.RTPA_REMOTE_ERR /* 16384 */:
                            a("ch " + i3 + " REMOTE_ERR");
                            break;
                    }
                    break;
            }
        }
    }

    public static boolean c(String str) {
        try {
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (stackTraceElement.getClassName().equalsIgnoreCase(str)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public static void a(Exception exc, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(exc.toString());
        stringBuffer.append("\n");
        StackTraceElement[] stackTrace = exc.getStackTrace();
        int[] iArr = new int[stackTrace.length];
        for (int i2 = 0; i2 < Math.min(Integer.MAX_VALUE, stackTrace.length); i2++) {
            stringBuffer.append("at: ");
            stringBuffer.append(stackTrace[i2].getClassName());
            stringBuffer.append(".");
            stringBuffer.append(stackTrace[i2].getMethodName());
            stringBuffer.append(", line ");
            stringBuffer.append(stackTrace[i2].getLineNumber());
            stringBuffer.append("\n");
            iArr[i2] = stringBuffer.length();
        }
        Throwable cause = exc.getCause();
        if (cause != null) {
            stringBuffer.setLength(iArr[2]);
            stringBuffer.append("Caused by: ");
            stringBuffer.append(cause.toString());
            stringBuffer.append("\n");
            StackTraceElement[] stackTrace2 = cause.getStackTrace();
            for (int i3 = 0; i3 < Math.min(2147483645, stackTrace2.length); i3++) {
                stringBuffer.append("at: ");
                stringBuffer.append(stackTrace2[i3].getClassName());
                stringBuffer.append(".");
                stringBuffer.append(stackTrace2[i3].getMethodName());
                stringBuffer.append(", line ");
                stringBuffer.append(stackTrace2[i3].getLineNumber());
                stringBuffer.append("\n");
            }
        }
        a(stringBuffer.toString());
    }

    static void a(PrintStream printStream) {
        f249a = printStream;
    }

    static void a(File file) {
        try {
            if (!new File(file.getParent()).exists()) {
                new File(file.getParent()).mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            f249a = new PrintStream((OutputStream) new FileOutputStream(file, false), false);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    static void m125a(int i2) {
        a = i2;
    }

    static int b() {
        return a;
    }
}
