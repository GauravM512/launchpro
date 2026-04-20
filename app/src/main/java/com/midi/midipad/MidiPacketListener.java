package com.midi.midipad;

interface MidiPacketListener {
    void onMidiPacket(byte[] data, long timestamp);
}
