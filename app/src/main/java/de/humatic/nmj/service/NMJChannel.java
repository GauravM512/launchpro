package de.humatic.nmj.service;

import android.os.Bundle;
import de.humatic.nmj.NMJConfig;

/* JADX INFO: loaded from: classes.dex */
public class NMJChannel {
    public int flags;
    public int id;
    public int io;
    public String ip;
    public int localPort;
    public int mode;
    public String name;
    public int nwa;
    public int port;

    NMJChannel() {
        this.mode = -1;
        this.io = -1;
    }

    NMJChannel(int i) {
        this.mode = -1;
        this.io = -1;
        this.id = i;
        this.name = NMJConfig.getName(this.id);
        this.ip = NMJConfig.getIP(this.id);
        this.mode = NMJConfig.getMode(this.id);
        this.io = NMJConfig.getIO(this.id);
        this.port = NMJConfig.getPort(this.id);
        this.localPort = NMJConfig.getLocalPort(this.id);
        this.flags = NMJConfig.getFlags(this.id);
        this.nwa = NMJConfig.getNetworkAdapter(this.id);
    }

    public static NMJChannel fromBundle(Bundle bundle) {
        NMJChannel nMJChannel = new NMJChannel();
        nMJChannel.id = bundle.getInt("id");
        nMJChannel.ip = bundle.getString("ip", null);
        nMJChannel.name = bundle.getString("name", null);
        nMJChannel.mode = bundle.getInt("mode", (nMJChannel.id + 1) % 3 == 0 ? 1 : 0);
        nMJChannel.io = bundle.getInt("io", (nMJChannel.id + 1) % 3 == 0 ? -1 : (nMJChannel.id + 1) % 2);
        nMJChannel.port = bundle.getInt("port");
        nMJChannel.localPort = bundle.getInt("local_port");
        nMJChannel.flags = bundle.getInt("flags");
        nMJChannel.nwa = bundle.getInt("nwa");
        return nMJChannel;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("NMJChannel @" + hashCode() + " id " + this.id);
        stringBuffer.append(", name " + this.name);
        stringBuffer.append(", mode " + this.mode);
        stringBuffer.append(", ip " + this.ip);
        stringBuffer.append(", io " + this.io);
        stringBuffer.append(", port " + Long.toHexString(this.port));
        stringBuffer.append(", local port " + this.localPort);
        stringBuffer.append(", flags " + this.flags);
        stringBuffer.append(", nwa " + this.nwa);
        return stringBuffer.toString();
    }
}
