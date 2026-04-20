package de.humatic.nmj;

import android.content.Context;
import android.widget.ViewFlipper;

/* JADX INFO: loaded from: classes.dex */
final class i extends ViewFlipper {
    i(Context context) {
        super(context);
    }

    @Override // android.widget.ViewFlipper, android.view.ViewGroup, android.view.View
    protected final void onDetachedFromWindow() {
        try {
            super.onDetachedFromWindow();
        } catch (IllegalArgumentException e) {
            stopFlipping();
        }
    }
}
