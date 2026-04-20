package de.bassapps.launchbuttonsP;

/* JADX INFO: loaded from: classes.dex */
public class Colors {
    // Keep index 0 as the app's current dark-off color.
    private static final int COLOR_0_LEGACY = 0xFF363636;
    private static final int DEFAULT_COLOR = 0xFF5A5A5A;

    private static final int[] ARGB = new int[]{
            COLOR_0_LEGACY, // 0
            0x77FAFAFA,
            0xAAFAFAFA,
            0xFFFAFAFA,
            0xFFF8BBD0,
            0xFFEF5350, // 5
            0xFFE57373,
            0xFFEF9A9A,

            0xFFFFF3E0,
            0xFFFFA726,
            0xFFFFB960, // 10
            0xFFFFCC80,
            0xFFFFE0B2,
            0xFFFFEE58,
            0xFFFFF59D,
            0xFFFFF9C4,

            0xFFDCEDC8,
            0xFF8BC34A, // 17
            0xFFAED581,
            0xFFBFDF9F,
            0xFF5EE2B0,
            0xFF00CE3C,
            0xFF00BA43,
            0xFF119C3F,

            0xFF57ECC1,
            0xFF00E864,
            0xFF00E05C,
            0xFF00D545,
            0xFF7AFDDD,
            0xFF00E4C5,
            0xFF00E0B2,
            0xFF01EEC6,

            0xFF49EFEF,
            0xFF00E7D8,
            0xFF00E5D1,
            0xFF01EFDE,
            0xFF6ADDFF,
            0xFF00DAFE,
            0xFF01D6FF,
            0xFF08ACDC,

            0xFF73CEFE,
            0xFF0D9BF7,
            0xFF148DE4,
            0xFF2A77C9,
            0xFF8693FF,
            0xFF2196F3, // 45
            0xFF4668F6,
            0xFF4153DC,

            0xFFB095FF,
            0xFF8453FD,
            0xFF634ACD,
            0xFF5749C5,
            0xFFFFB7FF,
            0xFFE863FB,
            0xFFD655ED,
            0xFFD14FE9,

            0xFFFC99E3,
            0xFFE736C2,
            0xFFE52FBE,
            0xFFE334B6,
            0xFFED353E,
            0xFFFFA726, // 61
            0xFFF4DF0B,
            0xFF8BC34A, // 63

            0xFF5CD100, // 64
            0xFF00D29E,
            0xFF2388FF,
            0xFF3669FD,
            0xFF00B4D0,
            0xFF475CDC,
            0xDDFAFAFA, // 70
            0xCCFAFAFA, // 71

            0xFFF72737,
            0xFFD2EA7B,
            0xFFC8DF10,
            0xFF7FE422,
            0xFF00C931,
            0xFF00D7A6,
            0xFF00D8FC,
            0xFF0B9BFC,

            0xFF585CF5,
            0xFFAC59F0,
            0xFFD980DC,
            0xFFB8814A,
            0xFFFF9800,
            0xFFABDF22,
            0xFF9EE154,
            0xFF66BB6A, // 87

            0xFF3BDA47,
            0xFF6FDEB9,
            0xFF27DBDA,
            0xFF9CC8FD,
            0xFF79B8F7,
            0xFFAFAFEF,
            0xFFD580EB,
            0xFFF74FCA,

            0xFFEA8A1F,
            0xFFDBDB08,
            0xFF9CD60D,
            0xFFF3D335,
            0xFFC8AF41,
            0xFF00CA69,
            0xFF24D2B0,
            0xFF757EBE,

            0xFF5388DB,
            0xFFE5C5A6,
            0xFFE93B3B,
            0xFFF9A2A1,
            0xFFED9C65,
            0xFFE1CA72,
            0xFFB8DA78,
            0xFF98D52C,

            0xFF626CBD,
            0xFFCAC8A0,
            0xFF90D4C2,
            0xFFCEDDFE,
            0xFFBECCF7,
            0x55FAFAFA, // 117
            0x77FAFAFA, // 118
            0xAAFAFAFA, // 119

            0xFFFE1624,
            0xFFCD2724,
            0xFF9CCC65, // 122
            0xFF009C1B,
            0xFFFFFF00, // 124
            0xFFBEB212,
            0xFFF5D01D, // 126
            0xFFE37829
    };

    public static int col(int v) {
        if (v >= 0 && v < ARGB.length) {
            return ARGB[v];
        }
        return DEFAULT_COLOR;
    }

    public static int getIntFromColor(int Red, int Green, int Blue) {
        if (Red < 0) {
            Red = 0;
        }
        if (Green < 0) {
            Green = 0;
        }
        if (Blue < 0) {
            Blue = 0;
        }
        if (Red > 255) {
            Red = 255;
        }
        if (Green > 255) {
            Green = 255;
        }
        if (Blue > 255) {
            Blue = 255;
        }
        int Green2 = (Green << 8) & 65280;
        return (-16777216) | ((Red << 16) & 16711680) | Green2 | (Blue & 255);
    }
}
