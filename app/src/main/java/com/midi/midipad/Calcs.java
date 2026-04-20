package com.midi.midipad;

public class Calcs {
    public int d() {
        return 3;
    }

    public int doubleInt(int a) {
        return a * 2;
    }

    public static int faderLookup(int a) {
        switch (a) {
            case 0:
                return 0;
            case 1:
                return 17;
            case 2:
                return 34;
            case 3:
                return 52;
            case 4:
                return 70;
            case 5:
                return 89;
            case 6:
                return 108;
            case 7:
                return 127;
            default:
                return 69;
        }
    }

    public static int panFaderLookup(int a) {
        switch (a) {
            case 0:
                return 0;
            case 1:
                return 21;
            case 2:
                return 42;
            case 3:
                return 63;
            case 4:
                return 64;
            case 5:
                return 85;
            case 6:
                return 106;
            case 7:
                return 127;
            default:
                return 69;
        }
    }

    public static boolean[] barLookUp(int f) {
        boolean[] bars = new boolean[10];
        for (int xf = 0; xf < 8; xf++) {
            bars[xf] = false;
        }
        if (f > 0) {
            bars[0] = true;
        }
        if (f > 16) {
            bars[1] = true;
        }
        if (f > 33) {
            bars[2] = true;
        }
        if (f > 51) {
            bars[3] = true;
        }
        if (f > 69) {
            bars[4] = true;
        }
        if (f > 88) {
            bars[5] = true;
        }
        if (f > 107) {
            bars[6] = true;
        }
        if (f == 127) {
            bars[7] = true;
        }
        return bars;
    }

    public static boolean[] sliderLookUp(int f) {
        boolean[] slider = new boolean[10];
        for (int xf = 0; xf < 8; xf++) {
            slider[xf] = false;
        }
        if (f == 127) {
            slider[7] = true;
        }
        if (f > 105) {
            slider[6] = true;
        }
        if (f > 84) {
            slider[5] = true;
        }
        if (f > 64) {
            slider[3] = false;
        }
        if (f < 63) {
            slider[3] = true;
            slider[4] = false;
        }
        if (f < 43) {
            slider[2] = true;
        }
        if (f < 22) {
            slider[1] = true;
        }
        if (f == 0) {
            slider[0] = true;
        }
        if (f > 62) {
            slider[4] = true;
        }
        if (f < 65) {
            slider[3] = true;
        }
        return slider;
    }
}
