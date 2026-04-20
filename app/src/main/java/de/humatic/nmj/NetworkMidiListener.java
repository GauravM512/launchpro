package de.humatic.nmj;

/* JADX INFO: loaded from: classes.dex */
public interface NetworkMidiListener extends NetworkMidiClient {
    void midiReceived(int i, int i2, byte[] bArr, long j);
}
