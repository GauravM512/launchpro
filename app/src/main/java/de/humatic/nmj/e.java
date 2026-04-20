package de.humatic.nmj;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
final class e extends ArrayAdapter<q> {
    private float a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private int f155a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkMidiSystem f156a;
    private int b;
    private int c;
    private int d;

    e(Context context, boolean z, NetworkMidiSystem networkMidiSystem) {
        super(context, 0);
        this.f155a = -1;
        this.b = R.style.TextAppearance.Medium;
        this.f156a = networkMidiSystem;
        try {
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            this.c = context.getResources().getDisplayMetrics().widthPixels;
            this.d = context.getResources().getDisplayMetrics().heightPixels;
            this.a = context.getResources().getDisplayMetrics().density;
            int i = this.d;
            int i2 = this.c;
            if (z) {
                this.b = R.style.TextAppearance.Small;
            }
        } catch (Exception e) {
        }
        setNotifyOnChange(true);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    protected final void m97a(int i) {
        this.f155a = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x01f6  */
    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View getView(int r10, android.view.View r11, android.view.ViewGroup r12) {
        /*
            Method dump skipped, instruction units count: 538
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.e.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    private Bitmap a(int i) {
        try {
            String str = "wifi.png";
            if (NMJConfig.getMode(i) == 0) {
                str = "mc.png";
            } else if (NMJConfig.getMode(i) == 4) {
                str = "adb.png";
            } else if (NMJConfig.getMode(i) == 2) {
                str = "bt.png";
            } else if (NMJConfig.getMode(i) == 5) {
                str = "usb.png";
            } else if (NMJConfig.getMode(i) == 7) {
                str = "com.png";
            } else if (NMJConfig.getMode(i) == 6) {
                str = "ws.png";
            } else if (NMJConfig.getMode(i) == 32) {
                str = "mnet.png";
            }
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("de/humatic/nmj/img/" + str);
            if (this.a <= 1.0f) {
                return Bitmap.createScaledBitmap(BitmapFactory.decodeStream(resourceAsStream), (int) ((r0.getWidth() / 2.0f) * this.a), (int) ((r0.getHeight() / 2.0f) * this.a), true);
            }
            return BitmapFactory.decodeStream(resourceAsStream);
        } catch (Exception e) {
            return null;
        }
    }

    static class a {
        public ImageView a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        public TextView f157a;
        public TextView b;
        public TextView c;

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }
}
