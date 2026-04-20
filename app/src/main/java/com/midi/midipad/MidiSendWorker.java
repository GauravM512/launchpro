package com.midi.midipad;
import android.util.Log;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class MidiSendWorker extends Thread {
    private static final String TAG = "MidiSendWorker";
    MidiByteSender midiOut;
    private BlockingQueue<byte[]> notesToLog = new ArrayBlockingQueue(600);

    MidiSendWorker() {
    }

    synchronized void setData(byte[] d) {
        try {
            this.notesToLog.put(d);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Unexpected interruption");
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (true) {
            try {
                byte[] item = this.notesToLog.take();
                if (item != null && item.length == 3 && item[0] == 6 && item[1] == 6 && item[2] == 6) {
                    continue;
                }
                try {
                    if (this.midiOut != null) {
                        this.midiOut.sendMidi(item);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException iex) {
                iex.printStackTrace();
                return;
            }
        }
    }

    public void systemError(int channel, int err, String description) {
        Log.w(TAG, "systemError ch=" + channel + " err=" + err + " desc=" + description);
    }
}
