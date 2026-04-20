package com.midi.midipad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RoundKnobButton extends RelativeLayout implements GestureDetector.OnGestureListener {
    private static final String TAG = "RoundKnobButton";
    int Mode;
    int[] Values;
    int arcColor;
    int arcEQ;
    int arcFX;
    BlurMaskFilter blurMaskFilter;
    private Bitmap bmpRotorOff;
    private Bitmap bmpRotorOn;
    Context contextd;
    int count;
    Canvas d;
    int degree;
    private GestureDetector gestureDetector;
    int h;
    ImageView ivBack;
    private ImageView ivRotor;
    int knob_ID;
    private float mAngleDown;
    private float mAngleUp;
    private boolean mState;
    public RoundKnobButtonListener m_listener;
    private int m_nHeight;
    private int m_nWidth;
    Paint paint;
    Paint paint2;
    Paint paintCenter;
    Bitmap srcoff;
    Bitmap srcon;
    long startMillis;
    int w;

    public interface RoundKnobButtonListener {
        void onRotate(RoundKnobButton roundKnobButton, int i, int i2);

        void onStateChange(boolean z);
    }

    public void setMode(int m) {
        switch (m) {
            case 0:
                setEQ();
                break;
            case 1:
                setFX();
                break;
        }
        this.Mode = m;
        this.paint.setColor(this.arcColor);
        postInvalidate();
    }

    private void setEQ() {
        this.arcColor = this.arcEQ;
        this.Mode = 0;
        setRotorPosAngle(this.Values[0]);
    }

    private void setFX() {
        this.arcColor = this.arcFX;
        this.Mode = 1;
        setRotorPosAngle(this.Values[1]);
    }

    public void SetListener(RoundKnobButtonListener l) {
        this.m_listener = l;
    }

    public void SetState(boolean state) {
    }

    public RoundKnobButton(Context context, AttributeSet attrs, int id) {
        super(context, attrs);
        this.mState = false;
        this.m_nWidth = 0;
        this.m_nHeight = 0;
        this.Values = new int[3];
        this.w = 130;
        this.h = 130;
        this.arcFX = -26368;
        this.arcEQ = -12281345;
        this.arcColor = this.arcEQ;
        this.degree = 0;
        setBackgroundColor(10044450);
        this.contextd = context;
        this.knob_ID = id;
        Log.d(TAG, "init knobId=" + id);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        this.srcon = BitmapFactory.decodeResource(this.contextd.getResources(), R.drawable.rotor, options);
        this.Values[0] = 0;
        this.Values[1] = 0;
        this.Values[2] = 0;
        this.paint = new Paint();
        this.paint.setColor(this.arcColor);
        this.paint.setStrokeWidth(8.0f);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint2 = new Paint();
        this.paint2.setColor(-10066330);
        this.paint2.setStrokeWidth(10.0f);
        this.paint2.setStyle(Paint.Style.STROKE);
        this.paintCenter = new Paint();
        this.paintCenter = this.paint;
        this.gestureDetector = new GestureDetector(getContext(), this);
        setOnTouchListener(new View.OnTouchListener() {
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == 0) {
                    Log.d(TAG, "double-tap candidate");
                    RoundKnobButton.this.doubleClick();
                    return false;
                }
                return false;
            }
        });
        this.blurMaskFilter = new BlurMaskFilter(2.0f, BlurMaskFilter.Blur.SOLID);
    }

    private float cartesianToPolar(float x, float y) {
        return (float) (-Math.toDegrees(Math.atan2(x - 0.5f, y - 0.5f)));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent event) {
        float x = event.getX() / getWidth();
        float y = event.getY() / getHeight();
        this.mAngleDown = cartesianToPolar(1.0f - x, 1.0f - y);
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent e) {
        float x = e.getX() / getWidth();
        float y = e.getY() / getHeight();
        this.mAngleUp = cartesianToPolar(1.0f - x, 1.0f - y);
        if (!Float.isNaN(this.mAngleDown) && !Float.isNaN(this.mAngleUp) && Math.abs(this.mAngleUp - this.mAngleDown) < 10.0f) {
            SetState(!this.mState);
            if (this.m_listener != null) {
                this.m_listener.onStateChange(this.mState);
            }
        }
        return true;
    }

    public int getImgWidth() {
        int x = this.ivBack.getWidth();
        return x;
    }

    public int getImgHeight() {
        int y = this.ivBack.getHeight();
        return y;
    }

    public void setRotorPosAngle(float deg) {
        this.degree = (int) deg;
        if (deg >= 225.0f || deg <= 135.0f) {
            if (deg > 180.0f) {
                deg -= 360.0f;
            }
            Matrix matrix = new Matrix();
            this.ivRotor.setScaleType(ImageView.ScaleType.MATRIX);
            matrix.postRotate(deg, getImgHeight() / 2, getImgWidth() / 2);
            this.ivRotor.setImageMatrix(matrix);
            switch (this.Mode) {
                case 0:
                    this.Values[0] = this.degree;
                    break;
                case 1:
                    this.Values[1] = this.degree;
                    break;
                case 2:
                    this.Values[2] = this.degree;
                    break;
            }
        }
        invalidate();
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int w2 = getWidth();
        int h2 = getHeight();
        this.m_nWidth = w2;
        this.m_nHeight = h2;
        this.ivBack = new ImageView(this.contextd);
        RelativeLayout.LayoutParams lp_ivBack = new RelativeLayout.LayoutParams(w2, h2);
        lp_ivBack.addRule(13);
        addView(this.ivBack, lp_ivBack);
        float scaleWidth = w2 / this.srcon.getWidth();
        float scaleHeight = h2 / this.srcon.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        this.bmpRotorOn = Bitmap.createBitmap(this.srcon, 0, 0, this.srcon.getWidth(), this.srcon.getHeight(), matrix, true);
        this.ivRotor = new ImageView(this.contextd);
        this.ivRotor.setImageBitmap(this.bmpRotorOn);
        RelativeLayout.LayoutParams lp_ivKnob = new RelativeLayout.LayoutParams(w2, h2);
        lp_ivKnob.addRule(13);
        addView(this.ivRotor, lp_ivKnob);
        SetState(this.mState);
        for (int in = 1; in < 2; in++) {
        }
        setRotorPosAngle(0.0f);
    }

    public Paint returnPaint(int i) {
        switch (i) {
            case 0:
                Paint b = this.paintCenter;
                return b;
            case 1:
                Paint b2 = this.paint;
                return b2;
            default:
                return null;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas c) {
        RectF r = new RectF(156.0f, 152.0f, 166.0f, 170.0f);
        c.drawOval(r, this.paint);
        this.paint.setMaskFilter(this.blurMaskFilter);
        this.paint2.setMaskFilter(this.blurMaskFilter);
        RectF Arc = new RectF(5.0f, 5.0f, getHeight() - 5, getWidth() - 5);
        c.drawArc(Arc, 135.0f, 270.0f, false, this.paint2);
        switch (this.Mode) {
            case 0:
                if (this.degree < 3 || this.degree > 357) {
                }
                if (this.degree < 179) {
                    c.drawArc(Arc, 270.0f, this.degree, false, this.paint);
                } else {
                    c.drawArc(Arc, this.degree - 90, 360 - this.degree, false, this.paint);
                }
                break;
            case 1:
                if (this.degree < 179) {
                    c.drawArc(Arc, 136.0f, this.degree + 134, false, this.paint);
                } else {
                    c.drawArc(Arc, 136.0f, this.degree - 224, false, this.paint);
                }
                break;
        }
        this.d = c;
        super.onDraw(c);
    }

    public void setRotorPercentage(int percentage) {
        int posDegree = (percentage * 3) - 150;
        if (posDegree < 0) {
            posDegree += 360;
        }
        setRotorPosAngle(posDegree);
    }

    void systemPrint() {
    }

    void doubleClick() {
        long time = System.currentTimeMillis();
        if (this.startMillis == 0 || time - this.startMillis > 350) {
            this.startMillis = time;
            this.count = 1;
        } else {
            this.count++;
        }
        if (this.count == 2) {
            if (this.m_listener != null) {
                this.m_listener.onRotate(this, 64, this.knob_ID);
            }
            setRotorPosAngle(0.0f);
        }
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float x = e2.getX() / getWidth();
        float y = e2.getY() / getHeight();
        float rotDegrees = cartesianToPolar(1.0f - x, 1.0f - y);
        if (Float.isNaN(rotDegrees)) {
            return false;
        }
        float posDegrees = rotDegrees;
        if (rotDegrees < 0.0f) {
            posDegrees = 360.0f + rotDegrees;
        }
        if (posDegrees <= 210.0f && posDegrees >= 150.0f) {
            return false;
        }
        setRotorPosAngle(posDegrees);
        float scaleDegrees = rotDegrees + 150.0f;
        int percent = (int) (scaleDegrees / 3.0f);
        if (this.m_listener != null) {
            this.m_listener.onRotate(this, (percent * 127) / 100, this.knob_ID);
        }
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent e) {
        doubleClick();
    }

    public void onClick() {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent e) {
    }
}
