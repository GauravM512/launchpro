package de.bassapps.launchbuttonsP;

/* JADX INFO: loaded from: classes.dex */
class Sender extends Thread {
    private int hasData;
    MidiByteSender midiOut;
    private byte[] tData;
    private byte[][] tmData;

    Sender() {
    }

    synchronized void setData(byte[] d) {
        this.tData = d;
        this.hasData = 1;
        notify();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (true) {
            synchronized (this) {
                while (this.hasData == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
            try {
                if (this.hasData == 1) {
                    if (this.midiOut != null) {
                        this.midiOut.sendMidi(this.tData);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.hasData = 0;
        }
    }

    public void systemError(int channel, int err, String description) {
    }
}
