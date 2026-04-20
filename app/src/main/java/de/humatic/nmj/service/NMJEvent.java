package de.humatic.nmj.service;

import android.os.Bundle;
import de.humatic.nmj.NMJConfig;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class NMJEvent {
    public int channel;
    public String description;
    public int property;
    public int type;
    public int value;
    public int port = -1;
    public long timeStamp = -1;

    NMJEvent() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static NMJEvent fromBundle(Bundle bundle) {
        NMJEvent nMJEvent = new NMJEvent();
        Bundle bundle2 = (Bundle) bundle.getParcelable("value");
        if (bundle2 == null) {
            bundle2 = bundle;
        }
        nMJEvent.port = bundle.getInt("port", -1);
        nMJEvent.type = bundle2.getInt("type", 0);
        switch (nMJEvent.type) {
            case 16:
                int[] intArray = bundle2.getIntArray("de.humatic.nmj.service.EVT");
                nMJEvent.channel = intArray[0];
                nMJEvent.property = intArray[1];
                if (nMJEvent.property == -1 || nMJEvent.property == 1 || nMJEvent.property == 2) {
                    nMJEvent.value = nMJEvent.property;
                } else if (intArray.length > 2) {
                    nMJEvent.value = intArray[2];
                }
                return nMJEvent;
            case NMJDeviceService.NMJ_ERROR /* 17 */:
                int[] intArray2 = bundle2.getIntArray("de.humatic.nmj.service.EVT");
                nMJEvent.channel = intArray2[0];
                nMJEvent.property = intArray2[1];
                nMJEvent.description = bundle2.getString("de.humatic.nmj.service.MSG");
                nMJEvent.timeStamp = bundle2.getLong("de.humatic.nmj.service.TS", System.currentTimeMillis());
                return nMJEvent;
            default:
                return nMJEvent;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.timeStamp != -1) {
            stringBuffer.append("[" + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(this.timeStamp)) + "]\n");
        }
        if (this.port != -1) {
            stringBuffer.append("Port: " + this.port + ", ");
        }
        stringBuffer.append("nmj ch: " + this.channel);
        if (this.type == 16) {
            switch (this.property) {
                case -1:
                    stringBuffer.append(" REMOVED");
                    break;
                case 1:
                    stringBuffer.append(" OPENED");
                    break;
                case 2:
                    stringBuffer.append(" CLOSED");
                    break;
                case 4:
                    switch (this.value) {
                        case 4:
                            stringBuffer.append(" WAITING");
                            break;
                        case 8:
                            stringBuffer.append(" DISCOVERED");
                            break;
                        case 16:
                            stringBuffer.append(" PRESENT");
                            break;
                        case 32:
                            stringBuffer.append(" CONNECTED");
                            break;
                        case 64:
                            stringBuffer.append(" CLIENT_CONNECTED");
                            break;
                        case 128:
                            stringBuffer.append(" DISCONNECTED");
                            break;
                        case 256:
                            stringBuffer.append(" CLIENT_DISCONNECTED");
                            break;
                        case 512:
                            stringBuffer.append(" GONE");
                            break;
                        case 1024:
                            stringBuffer.append(" LOST");
                            break;
                        case 2048:
                            stringBuffer.append(" CONNECTION_REFUSED");
                            break;
                        case 4096:
                            stringBuffer.append(" NO_RESPONSE");
                            break;
                        case 8192:
                            stringBuffer.append(" PKT_LOSS");
                            break;
                        case NMJConfig.RTPA_REMOTE_ERR /* 16384 */:
                            stringBuffer.append(" REMOTE_ERR");
                            break;
                    }
                    break;
            }
        } else if (this.type == 17) {
            switch (this.property) {
                case NMJConfig.E_UNSPECIFIED /* -2147483648 */:
                    stringBuffer.append(" E_UNSPECIFIED");
                    break;
                case -2147483647:
                    stringBuffer.append(" E_NETWORK");
                    break;
                case NMJConfig.E_USB /* -2147483646 */:
                    stringBuffer.append(" E_USB");
                    break;
                case NMJConfig.E_BLUETOOTH /* -2147483644 */:
                    stringBuffer.append(" E_BLUETOOTH");
                    break;
                case NMJConfig.E_ADB /* -2147483640 */:
                    stringBuffer.append(" E_ADB");
                    break;
                case NMJConfig.E_DEVICE_OPEN /* -2147418111 */:
                    stringBuffer.append(" E_DEVICE_OPEN");
                    break;
                case NMJConfig.E_BIND /* -2147418110 */:
                    stringBuffer.append(" E_BIND");
                    break;
                case NMJConfig.E_INVALID_CH /* -2147418096 */:
                    stringBuffer.append(" E_INVALID_CH");
                    break;
                case NMJConfig.E_INVALID_DATA /* -2147418095 */:
                    stringBuffer.append(" E_INVALID_DATA");
                    break;
                default:
                    if ((this.property & NMJConfig.E_DNS) != 0) {
                        stringBuffer.append(" E_DNS");
                        if ((this.property & (-2147483647)) != 0) {
                            stringBuffer.append(" (WiFi)");
                        } else if ((this.property & NMJConfig.E_USB) != 0) {
                            stringBuffer.append(" (Usb)");
                        } else if ((this.property & NMJConfig.E_P2P) != 0) {
                            stringBuffer.append(" (P2P)");
                        }
                    }
                    break;
            }
            if (this.description != null && this.description.length() > 0) {
                stringBuffer.append(" - ");
                stringBuffer.append(this.description);
            }
        }
        return stringBuffer.toString();
    }

    public boolean isChangingSetup() {
        return this.type == 16 && (this.property == -1 || (this.property == 4 && this.value == 8));
    }

    public boolean isErrorCase() {
        return this.type == 17 || (this.type == 16 && this.property == 4 && (this.value == 4096 || this.value == 1024 || this.value == 2048));
    }
}
