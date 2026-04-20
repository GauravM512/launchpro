package com.midi.midipad;

interface MidiByteSender {
    void sendMidi(byte[] data) throws Exception;
}
