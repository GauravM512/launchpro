package de.bassapps.launchbuttonsP;

interface MidiPacketListener {
    void onMidiPacket(byte[] data, long timestamp);
}
