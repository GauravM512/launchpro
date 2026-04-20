package de.humatic.nmj;

import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class NetworkMidiPort {
    int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    NetworkMidiSystem f116a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    Vector f117a = new Vector();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    boolean f118a;
    boolean b;

    NetworkMidiPort() {
    }

    NetworkMidiPort(int i, NetworkMidiSystem networkMidiSystem, NetworkMidiClient networkMidiClient) throws Exception {
        this.a = i;
        this.f116a = networkMidiSystem;
        m80a(networkMidiClient);
        p.b();
    }

    public void close(NetworkMidiClient networkMidiClient) {
    }

    NetworkMidiPort a(NetworkMidiClient networkMidiClient) {
        if (!m80a(networkMidiClient)) {
        }
        return this;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    final boolean m80a(NetworkMidiClient networkMidiClient) {
        boolean z = false;
        for (int i = 0; i < this.f117a.size(); i++) {
            if (this.f117a.get(i).getClass().getName().equalsIgnoreCase(networkMidiClient.getClass().getName()) && this.f117a.get(i).hashCode() == networkMidiClient.hashCode()) {
                z = true;
            }
        }
        if (z) {
            return false;
        }
        this.f117a.add(networkMidiClient);
        p.logln(5, this + " added " + networkMidiClient.getClass().getName() + "[" + Long.toHexString(networkMidiClient.hashCode()) + "], nr clients: " + this.f117a.size());
        return true;
    }

    final boolean b(NetworkMidiClient networkMidiClient) {
        boolean z = false;
        for (int i = 0; i < this.f117a.size(); i++) {
            if (this.f117a.get(i).getClass().getName().equalsIgnoreCase(networkMidiClient.getClass().getName()) && this.f117a.get(i).hashCode() == networkMidiClient.hashCode()) {
                z = true;
            }
        }
        if (!z) {
            return false;
        }
        this.f117a.remove(networkMidiClient);
        p.logln(5, String.valueOf(this.a) + " - Removed " + networkMidiClient.getClass().getName() + "[" + Long.toHexString(networkMidiClient.hashCode()) + "], nr clients: " + this.f117a.size());
        return true;
    }

    final void a() {
        p.logln(5, "Clients remaining: " + this.f117a.size());
        for (int i = 0; i < this.f117a.size(); i++) {
            p.logln(5, String.valueOf(this.f117a.get(i).getClass().getName()) + " " + this.f117a.get(i).hashCode());
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    boolean mo79a() {
        return true;
    }

    void a(int i) {
        this.a = i;
    }

    public int getChannelIndex() {
        return this.a;
    }

    public String toString() {
        String name = getClass().getName();
        return String.valueOf(name.substring(name.lastIndexOf(".") + 1)) + "[" + Long.toHexString(hashCode()) + "] ID: " + this.a + " name: " + NMJConfig.getName(this.a);
    }
}
