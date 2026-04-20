package de.humatic.nmj;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public final class NMJConfigDialog extends Activity implements TextWatcher, View.OnClickListener, View.OnLongClickListener, View.OnTouchListener, AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener, TextView.OnEditorActionListener, NMJSystemListener {
    private float a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private AlertDialog.Builder f45a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private BitmapDrawable f46a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private InputMethodManager f47a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private CheckBox f48a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private EditText f49a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private LinearLayout f50a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private ListView f51a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private ScrollView f52a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Spinner f53a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private TextView f54a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private a f57a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private b f58a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkMidiInput f59a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkMidiOutput f60a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NetworkMidiSystem f61a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private e f62a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private i f63a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private BitmapDrawable f68b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private CheckBox f69b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private EditText f70b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private LinearLayout f71b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Spinner f72b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private TextView f73b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private boolean f74b;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private BitmapDrawable f78c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private CheckBox f79c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private EditText f80c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private LinearLayout f81c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private Spinner f82c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private TextView f83c;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private boolean f84c;
    private int d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private EditText f87d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private LinearLayout f88d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private TextView f89d;

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private boolean f90d;
    private int e;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private LinearLayout f92e;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private TextView f93e;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private boolean f94e;
    private int f;

    /* JADX INFO: renamed from: f, reason: collision with other field name */
    private boolean f95f;

    /* JADX INFO: renamed from: g, reason: collision with other field name */
    private boolean f96g;
    private int h;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NMJInputClient f55a = new NMJInputClient();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private NMJOutputClient f56a = new NMJOutputClient(this);

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private int f44a = -1;
    private int b = 63;
    private int c = -1;
    private int g = R.style.TextAppearance.Holo.Medium;
    private int i = 36;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private int[] f66a = {63, 32, 29, 2};

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private int[] f76b = {0, 2, 4, 5, 7, 9, 11, 12};

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private int[] f86c = {1, 3, 6, 8, 10};

    /* JADX INFO: renamed from: d, reason: collision with other field name */
    private int[] f91d = {7, 10, 74};

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String[] f67a = {"C", "D", "E", "F", "G", "A", "B", "C"};

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private String[] f77b = {"-", "C#", "D#", "3", "F#", "G#", "A#", "+"};

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private byte[] f65a = {-80, 74, 0};

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private byte[] f75b = {-80, 7, 0};

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    private byte[] f85c = {-112, 0, 0};

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private boolean f64a = true;

    static {
        new Vector();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        boolean z;
        ApplicationInfo applicationInfo;
        super.onCreate(bundle);
        try {
            try {
                applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int i = applicationInfo != null ? applicationInfo.targetSdkVersion : 0;
            this.f96g = Build.VERSION.SDK_INT >= 23 && i >= 23;
            requestWindowFeature(1);
            this.a = getResources().getDisplayMetrics().density;
            int iMax = Math.max(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
            float f = getResources().getDisplayMetrics().density;
            if (f < 1.5f && iMax > 640) {
                z = false;
            } else if (f != 1.5f || iMax <= 1000) {
                if (f >= 2.0f) {
                }
                z = true;
            } else {
                z = false;
            }
            this.f74b = z;
            if (this.f74b) {
                this.g = R.style.TextAppearance.Holo.Small;
            }
            this.f58a = new b(this, null, (byte) 0);
            try {
                this.f61a = NetworkMidiSystem.get(this);
            } catch (SocketException e2) {
                e2.printStackTrace();
                p.logln(1, "NMJConfigDialog, on get MIDI system: " + e2.toString());
                NMJConfig.a(-1, -2147483647, "NMJConfigDialog, on get MIDI system: " + e2.toString());
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            NMJConfig.a("de.humatic.nmj.NMJConfigDialog", hashCode());
            NMJConfig.addSystemListener(this);
            this.f46a = a(this, "monitor.png");
            this.f68b = a(this, "keys.png");
            this.f78c = a(this, "blank.png");
            m71a((Context) this);
        } catch (Exception e4) {
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        try {
            m71a((Context) getApplication());
        } catch (Exception e) {
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private void m71a(Context context) {
        float f;
        float f2;
        int i;
        float f3;
        float f4;
        float f5;
        Button buttonA;
        int i2;
        try {
            this.d = context.getResources().getDisplayMetrics().widthPixels;
            this.e = context.getResources().getDisplayMetrics().heightPixels;
            if (Math.max(this.d, this.e) <= 400) {
                setRequestedOrientation(1);
            }
            this.f84c = this.e > this.d;
            this.f90d = this.a <= 1.0f && Math.max(this.d, this.e) <= 480;
            this.f94e = ((double) this.a) <= 1.5d && Math.max(this.d, this.e) > 480 && Math.max(this.d, this.e) <= 854;
            this.f77b[3] = String.valueOf(this.i / 12);
            this.f63a = new i(this);
            setContentView(this.f63a);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            layoutParams.setMargins(10, 5, 10, 5);
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            LinearLayout linearLayout2 = new LinearLayout(context);
            linearLayout2.setBackgroundColor(-15198184);
            linearLayout2.setOrientation(this.f84c ? 1 : 0);
            this.f63a.addView(linearLayout2, 0, layoutParams);
            if (this.f84c) {
                linearLayout2.addView(linearLayout, 0, new LinearLayout.LayoutParams(-1, 0, 0.4f));
            } else {
                linearLayout2.addView(linearLayout, 0, new LinearLayout.LayoutParams(0, -1, 0.4f));
            }
            LinearLayout linearLayout3 = new LinearLayout(context);
            linearLayout3.setBackgroundColor(-14145496);
            TextView textView = new TextView(context);
            textView.setTextAppearance(context, this.g);
            if (this.f90d) {
                textView.setTextSize(10.0f);
            } else if (this.f94e) {
                textView.setTextSize(11.0f);
            }
            textView.setText("Defined Channels");
            if (this.f96g) {
                textView.setTextColor(-6710887);
            }
            textView.setGravity(16);
            if (this.f90d) {
                textView.setPadding(10, 1, 0, 0);
            } else {
                textView.setPadding(10, 3, 3, 3);
            }
            linearLayout3.addView(textView, 0, new LinearLayout.LayoutParams(0, -1, 0.74f));
            linearLayout3.addView(b(context), 1, new LinearLayout.LayoutParams(0, -1, 0.26f));
            linearLayout.addView(linearLayout3, 0, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.09f : 0.06f));
            this.f51a = new ListView(context);
            this.f51a.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: de.humatic.nmj.NMJConfigDialog.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView<?> adapterView, View view, int i3, long j) {
                    try {
                        NMJConfigDialog.this.a(i3);
                    } catch (Exception e) {
                        p.a(e, de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                    }
                }
            });
            this.f62a = new e(context, this.f74b, this.f61a);
            for (int i3 = 0; i3 < NMJConfig.getNumChannels(); i3++) {
                this.f62a.add(new q(i3));
            }
            this.f51a.setAdapter((ListAdapter) this.f62a);
            if (this.f74b) {
                f = this.f84c ? 0.77f : 0.83f;
            } else {
                f = this.f84c ? 0.79f : 0.85f;
            }
            linearLayout.addView(this.f51a, 1, new LinearLayout.LayoutParams(-1, 0, f));
            LinearLayout linearLayout4 = new LinearLayout(context);
            linearLayout4.setBackgroundColor(-13421773);
            linearLayout4.setGravity(17);
            new LinearLayout.LayoutParams(0, -1, 1.0f).setMargins(5, 5, 5, 5);
            linearLayout4.addView(a(context), new LinearLayout.LayoutParams(0, -1, 1.0f));
            if (this.f74b) {
                f2 = this.f84c ? 0.13f : 0.11f;
            } else {
                f2 = this.f84c ? 0.13f : 0.09f;
            }
            linearLayout.addView(linearLayout4, 2, new LinearLayout.LayoutParams(-1, 0, f2));
            LinearLayout linearLayout5 = new LinearLayout(context);
            linearLayout5.setBackgroundColor(-14145496);
            linearLayout5.setOrientation(1);
            linearLayout5.setPadding(0, 0, 10, 0);
            if (this.f84c) {
                linearLayout2.addView(linearLayout5, 1, new LinearLayout.LayoutParams(-1, 0, 0.6f));
            } else {
                linearLayout2.addView(linearLayout5, 1, new LinearLayout.LayoutParams(0, -1, 0.6f));
            }
            this.f50a = new LinearLayout(context);
            this.f50a.setBackgroundColor(-14145496);
            this.f50a.setOrientation(1);
            if (this.f74b) {
                this.f52a = new ScrollView(context);
            }
            int i4 = 13;
            int i5 = 0;
            try {
                i = context.getApplicationContext().getApplicationInfo().targetSdkVersion;
                try {
                    i5 = Build.MANUFACTURER.toLowerCase().indexOf("samsung") != -1 ? 18 : 0;
                } catch (Exception e) {
                    i4 = i;
                    i = i4;
                }
            } catch (Exception e2) {
            }
            this.f54a = new TextView(context);
            this.f54a.setTextAppearance(context, this.g);
            if (this.f96g) {
                this.f54a.setTextColor(-6710887);
            }
            this.f54a.setText("Channel 1");
            this.f54a.setPadding(0, 2, 2, 2);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
            layoutParams2.setMargins(15, 8, 0, 12);
            this.f50a.addView(this.f54a, layoutParams2);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
            LinearLayout linearLayout6 = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(0, -2, 0.4f);
            layoutParams4.setMargins(15, 1, 15, 1);
            TextView textView2 = new TextView(context);
            textView2.setTextAppearance(context, this.g);
            if (this.f96g) {
                textView2.setTextColor(-6710887);
            }
            textView2.setText("Name: ");
            linearLayout6.addView(textView2, 0, layoutParams4);
            this.f49a = new EditText(context);
            if (this.f96g) {
                this.f49a.setTextColor(-6710887);
            }
            this.f49a.setInputType(524432);
            try {
                this.f49a.setText(NMJConfig.getName(this.f44a));
            } catch (NMJException e3) {
            }
            this.f49a.setOnEditorActionListener(this);
            this.f49a.addTextChangedListener(this);
            if (this.f74b) {
                this.f49a.setTextSize(15.0f);
            }
            linearLayout6.addView(this.f49a, 1, new LinearLayout.LayoutParams(0, -2, 0.6f));
            this.f50a.addView(linearLayout6, layoutParams3);
            LinearLayout linearLayout7 = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(0, -2, 0.4f);
            layoutParams5.setMargins(15, 1, 15, 1);
            TextView textView3 = new TextView(context);
            textView3.setTextAppearance(context, this.g);
            textView3.setText("Mode: ");
            if (this.f96g) {
                textView3.setTextColor(-6710887);
            }
            linearLayout7.addView(textView3, 0, layoutParams5);
            this.f53a = new Spinner(this);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.simple_spinner_item, new String[]{"RAW / Multicast", "RTP", "BLUETOOTH", "DSMI", "ADB", "USB_HOST", "MWS", "COM"});
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            this.f53a.setAdapter((SpinnerAdapter) arrayAdapter);
            this.f53a.setOnItemSelectedListener(this);
            try {
                this.f53a.setSelection(NMJConfig.getMode(this.f44a));
            } catch (NMJException e4) {
            }
            LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(0, -2, 0.6f);
            if (i > 13) {
                layoutParams6.setMargins(1, i5, 1, i5);
            }
            linearLayout7.addView(this.f53a, 1, layoutParams6);
            this.f50a.addView(linearLayout7, layoutParams3);
            LinearLayout linearLayout8 = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(0, -2, 0.4f);
            layoutParams7.setMargins(15, 1, 15, 1);
            TextView textView4 = new TextView(context);
            textView4.setTextAppearance(context, this.g);
            if (this.f96g) {
                textView4.setTextColor(-6710887);
            }
            textView4.setText("IP: ");
            textView4.setId(4103);
            textView4.setOnTouchListener(this);
            linearLayout8.addView(textView4, 0, layoutParams7);
            this.f70b = new EditText(context);
            try {
                this.f70b.setText(NMJConfig.getIP(this.f44a));
            } catch (NMJException e5) {
            }
            this.f70b.setInputType(524432);
            this.f70b.setOnEditorActionListener(this);
            this.f70b.setOnLongClickListener(this);
            this.f70b.addTextChangedListener(this);
            this.f70b.getTextColors().getDefaultColor();
            if (this.f96g) {
                this.f70b.setTextColor(-6710887);
            }
            if (this.f74b) {
                this.f70b.setTextSize(15.0f);
            }
            linearLayout8.addView(this.f70b, 1, new LinearLayout.LayoutParams(0, -2, 0.6f));
            this.f50a.addView(linearLayout8, layoutParams3);
            LinearLayout linearLayout9 = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams8 = new LinearLayout.LayoutParams(0, -2, 0.4f);
            layoutParams8.setMargins(15, 1, 15, 1);
            TextView textView5 = new TextView(context);
            textView5.setTextAppearance(context, this.g);
            textView5.setText("Interface: ");
            if (this.f96g) {
                textView5.setTextColor(-6710887);
            }
            linearLayout9.addView(textView5, 0, layoutParams8);
            this.f72b = new Spinner(this);
            Vector vector = new Vector();
            vector.add("Auto");
            try {
                try {
                    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                    while (networkInterfaces.hasMoreElements()) {
                        vector.add(networkInterfaces.nextElement().getName());
                    }
                } catch (SocketException e6) {
                    if (e6.toString().indexOf("unkown error") != -1) {
                        System.out.println(String.valueOf(e6.toString()) + "\nDid you grant the required network permission?");
                    } else {
                        e6.printStackTrace();
                    }
                }
            } catch (Exception e7) {
                e7.printStackTrace();
            }
            String[] strArr = new String[vector.size()];
            vector.copyInto(strArr);
            ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.simple_spinner_item, strArr);
            arrayAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            this.f72b.setAdapter((SpinnerAdapter) arrayAdapter2);
            try {
                this.f72b.setSelection(NMJConfig.getNetworkAdapter(this.f44a));
            } catch (NMJException e8) {
            }
            this.f72b.setOnItemSelectedListener(this);
            LinearLayout.LayoutParams layoutParams9 = new LinearLayout.LayoutParams(0, -2, 0.6f);
            if (i > 13) {
                layoutParams9.setMargins(1, i5, 1, i5);
            }
            linearLayout9.addView(this.f72b, 1, layoutParams9);
            this.f50a.addView(linearLayout9, layoutParams3);
            LinearLayout linearLayout10 = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams10 = new LinearLayout.LayoutParams(0, -2, 0.4f);
            layoutParams10.setMargins(15, 1, 15, 1);
            TextView textView6 = new TextView(context);
            textView6.setTextAppearance(context, this.g);
            if (this.f74b) {
                textView6.setText(this.a < 1.0f ? "Port (Rem/Loc):" : "Port (Rem./Loc.):");
            } else {
                textView6.setText("Port (Remote / Local): ");
            }
            textView6.setId(4104);
            textView6.setOnTouchListener(this);
            if (this.f96g) {
                textView6.setTextColor(-6710887);
            }
            linearLayout10.addView(textView6, 0, layoutParams10);
            this.f80c = new EditText(context);
            if (this.f96g) {
                this.f80c.setTextColor(-6710887);
            }
            this.f80c.setInputType(2);
            try {
                this.f80c.setText(String.valueOf(NMJConfig.getPort(this.f44a)));
            } catch (NMJException e9) {
            }
            this.f80c.setOnEditorActionListener(this);
            this.f80c.addTextChangedListener(this);
            if (this.f74b) {
                this.f80c.setTextSize(15.0f);
            }
            linearLayout10.addView(this.f80c, 1, new LinearLayout.LayoutParams(0, -2, 0.3f));
            this.f87d = new EditText(context);
            if (this.f96g) {
                this.f87d.setTextColor(-6710887);
            }
            this.f87d.setInputType(2);
            try {
                this.f87d.setText(String.valueOf(NMJConfig.getLocalPort(this.f44a)));
            } catch (NMJException e10) {
            }
            this.f87d.setOnEditorActionListener(this);
            this.f87d.addTextChangedListener(this);
            if (this.f74b) {
                this.f87d.setTextSize(15.0f);
            }
            linearLayout10.addView(this.f87d, 2, new LinearLayout.LayoutParams(0, -2, 0.3f));
            this.f50a.addView(linearLayout10, layoutParams3);
            this.f88d = new LinearLayout(context);
            this.f88d.setGravity(16);
            LinearLayout.LayoutParams layoutParams11 = new LinearLayout.LayoutParams(0, -1, 0.4f);
            layoutParams11.setMargins(15, 1, 15, 1);
            this.f83c = new TextView(context);
            this.f83c.setGravity(16);
            this.f83c.setTextAppearance(context, this.g);
            if (this.f96g) {
                this.f83c.setTextColor(-6710887);
            }
            this.f83c.setText("IO: ");
            this.f88d.addView(this.f83c, 0, layoutParams11);
            this.f69b = new CheckBox(context);
            if (Build.VERSION.SDK_INT >= 11) {
                try {
                    this.f69b.setButtonDrawable(getResources().getIdentifier("btn_check_holo_dark", "drawable", "android"));
                } catch (Exception e11) {
                }
            }
            this.f69b.setId(4098);
            this.f69b.setText(this.f90d ? " In" : " Input");
            try {
                this.f69b.setChecked(NMJConfig.getIO(this.f44a) <= 0);
            } catch (NMJException e12) {
            }
            this.f69b.setOnClickListener(this);
            this.f69b.setTextAppearance(context, this.g);
            if (this.f96g) {
                this.f69b.setTextColor(-6710887);
            }
            this.f88d.addView(this.f69b, 1, new LinearLayout.LayoutParams(0, -2, 0.3f));
            this.f79c = new CheckBox(context);
            if (Build.VERSION.SDK_INT >= 11) {
                try {
                    this.f79c.setButtonDrawable(getResources().getIdentifier("btn_check_holo_dark", "drawable", "android"));
                } catch (Exception e13) {
                }
            }
            this.f79c.setId(4099);
            this.f79c.setText(this.f90d ? " Out" : " Output");
            try {
                this.f79c.setChecked(Math.abs(NMJConfig.getIO(this.f44a)) == 1);
            } catch (NMJException e14) {
            }
            this.f79c.setOnClickListener(this);
            this.f79c.setTextAppearance(context, this.g);
            if (this.f96g) {
                this.f79c.setTextColor(-6710887);
            }
            this.f88d.addView(this.f79c, 2, new LinearLayout.LayoutParams(0, -2, 0.3f));
            this.f50a.addView(this.f88d, layoutParams3);
            if (this.f74b) {
                f3 = this.a < 1.0f ? 0.74f : 0.78f;
            } else {
                f3 = this.f84c ? 0.63f : 0.65f;
            }
            ViewGroup.LayoutParams layoutParams12 = new LinearLayout.LayoutParams(-1, 0, f3);
            if (this.f74b) {
                this.f52a.addView(this.f50a);
                linearLayout5.addView(this.f52a, 0, layoutParams12);
                this.f52a.setVisibility(8);
            } else {
                linearLayout5.addView(this.f50a, 0, layoutParams12);
                this.f50a.setVisibility(8);
            }
            this.f71b = new LinearLayout(context);
            TextView textView7 = new TextView(context);
            if (this.a >= 1.0f && this.e >= 480) {
                if (this.e > 480) {
                    textView7.setText("\n \n ");
                    textView7.setLines(3);
                } else {
                    textView7.setText("\n");
                }
                this.f71b.addView(textView7);
            }
            this.f71b.setBackgroundColor(-14145496);
            this.f71b.setOrientation(1);
            this.f71b.setGravity(17);
            ImageView imageView = new ImageView(context);
            if (this.f90d) {
                Bitmap bitmapA = a("hm2e.png");
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmapA, bitmapA.getWidth() / (this.a < 1.0f ? 4 : 2), bitmapA.getHeight() / (this.a < 1.0f ? 4 : 2), true));
            } else {
                imageView.setImageBitmap(a("hm2e.png"));
            }
            imageView.setPadding(0, 10, 0, 10);
            this.f71b.addView(imageView);
            TextView textView8 = new TextView(context);
            int i6 = 4;
            StringBuffer stringBuffer = new StringBuffer("nmj ");
            try {
                stringBuffer.append(NMJConfig.getVersionInfo());
                if (this.a < 1.0f || this.e <= 480) {
                    stringBuffer.append("\n");
                } else {
                    stringBuffer.append("\n\n");
                }
                for (String str : NMJConfig.m32a()) {
                    i6++;
                    stringBuffer.append(String.valueOf(str) + "\n");
                }
                i6 += 6;
                if (this.a >= 1.0f && this.e > 480) {
                    stringBuffer.append("\n");
                }
                stringBuffer.append(System.getProperty("os.name"));
                stringBuffer.append(" - ");
                stringBuffer.append(String.valueOf(System.getProperty("os.version")) + "\n");
                stringBuffer.append("Android: ");
                stringBuffer.append(Build.VERSION.RELEASE);
                stringBuffer.append("\n");
                stringBuffer.append(Build.MODEL);
                stringBuffer.append(" (");
                stringBuffer.append(Build.BOARD);
                stringBuffer.append(")\n");
                stringBuffer.append(String.valueOf(Build.MANUFACTURER) + " " + Build.DISPLAY + "\n");
            } catch (Exception e15) {
            }
            textView8.setLines(i6);
            textView8.setText(stringBuffer.toString());
            textView8.setTextAppearance(context, this.g);
            textView8.setTextColor(-6710887);
            textView8.setGravity(17);
            this.f71b.addView(textView8, new LinearLayout.LayoutParams(-1, -2));
            linearLayout5.addView(this.f71b, 1, layoutParams12);
            this.f81c = new LinearLayout(context);
            this.f81c.setBackgroundColor(-14145496);
            this.f81c.setOrientation(1);
            LinearLayout linearLayout11 = new LinearLayout(context);
            linearLayout11.setGravity(17);
            LinearLayout linearLayout12 = new LinearLayout(context);
            linearLayout12.setOrientation(1);
            linearLayout12.setPadding(15, 15, 15, 15);
            linearLayout12.setGravity(17);
            ImageView imageView2 = new ImageView(context);
            imageView2.setImageBitmap(a("scan_bluetooth.png"));
            imageView2.setPadding(0, 0, 0, 10);
            linearLayout12.addView(imageView2);
            linearLayout12.addView(a(context, "Start Scan", 4101), new LinearLayout.LayoutParams(-2, -2));
            linearLayout11.addView(linearLayout12, new LinearLayout.LayoutParams(0, -1, 1.0f));
            this.f81c.addView(linearLayout11, new LinearLayout.LayoutParams(-1, 0, 1.0f));
            this.f92e = new LinearLayout(context);
            this.f92e.setGravity(17);
            this.f92e.addView(new ProgressBar(context));
            this.f92e.setVisibility(4);
            this.f81c.addView(this.f92e, new LinearLayout.LayoutParams(-1, 0, 1.0f));
            linearLayout5.addView(this.f81c, 2, layoutParams12);
            this.f81c.setVisibility(8);
            LinearLayout linearLayout13 = new LinearLayout(context);
            linearLayout13.setBackgroundColor(-14145496);
            this.f73b = new TextView(context);
            this.f73b.setTextAppearance(context, this.g);
            this.f73b.setGravity(17);
            linearLayout13.addView(this.f73b, new LinearLayout.LayoutParams(-1, -1));
            if (this.f74b) {
                f4 = 0.09f;
            } else {
                boolean z = this.f84c;
                f4 = 0.25f;
            }
            linearLayout5.addView(linearLayout13, 3, new LinearLayout.LayoutParams(-1, 0, f4));
            LinearLayout linearLayout14 = new LinearLayout(context);
            linearLayout14.setBackgroundColor(-13948117);
            LinearLayout.LayoutParams layoutParams13 = new LinearLayout.LayoutParams(0, -1, 1.0f);
            if (this.f74b) {
                layoutParams13.setMargins(5, 1, 5, 1);
            } else {
                layoutParams13.setMargins(5, 5, 5, 5);
            }
            linearLayout14.addView(a(context, this.f78c, 2048, -13421773), 0, layoutParams13);
            linearLayout14.addView(a(context, "↻", 2049), 1, layoutParams13);
            linearLayout14.addView(a(context, "+", 2050), 2, layoutParams13);
            linearLayout14.addView(a(context, this.f78c, 2051, -13421773), 3, layoutParams13);
            if (this.f74b) {
                f5 = this.a < 1.0f ? 0.14f : 0.11f;
            } else {
                f5 = this.f84c ? 0.1f : 0.09f;
            }
            linearLayout5.addView(linearLayout14, 4, new LinearLayout.LayoutParams(-1, 0, f5));
            this.f47a = (InputMethodManager) context.getSystemService("input_method");
            LinearLayout linearLayout15 = new LinearLayout(this);
            linearLayout15.setOrientation(1);
            linearLayout15.setBackgroundColor(-13421773);
            LinearLayout linearLayout16 = new LinearLayout(this);
            linearLayout16.setBackgroundColor(-14145496);
            LinearLayout.LayoutParams layoutParams14 = new LinearLayout.LayoutParams(0, -1, 1.0f);
            if (this.a >= 1.0f) {
                if (this.f74b) {
                    layoutParams14.setMargins(5, 8, 5, 0);
                } else if (this.f84c) {
                    layoutParams14.setMargins(15, 10, 5, 5);
                } else {
                    layoutParams14.setMargins(5, 5, 5, 5);
                }
            }
            this.f89d = new TextView(context);
            try {
                this.f89d.setText("1 - " + NMJConfig.getName(0));
            } catch (NMJException e16) {
            }
            this.f89d.setTextAppearance(context, this.g);
            this.f89d.setGravity(16);
            linearLayout16.addView(this.f89d, layoutParams14);
            Spinner spinner = new Spinner(this);
            ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, R.layout.simple_spinner_item, new String[]{"All datatypes", "System common", "System realtime", "System exclusive"});
            arrayAdapter3.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter((SpinnerAdapter) arrayAdapter3);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: de.humatic.nmj.NMJConfigDialog.4
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public final void onItemSelected(AdapterView<?> adapterView, View view, int i7, long j) {
                    if (!NMJConfigDialog.this.f64a) {
                        NMJConfigDialog.this.b = NMJConfigDialog.this.f66a[i7];
                        NMJConfigDialog.this.f57a.sendMessage(Message.obtain());
                    }
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public final void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            this.f48a = new CheckBox(this);
            if (Build.VERSION.SDK_INT >= 11) {
                try {
                    this.f48a.setButtonDrawable(getResources().getIdentifier("btn_check_holo_dark", "drawable", "android"));
                } catch (Exception e17) {
                }
            }
            this.f48a.setChecked(true);
            this.f48a.setText(" hex");
            this.f48a.setTextAppearance(context, this.g);
            this.f48a.setPadding(30, 0, 0, 0);
            linearLayout16.addView(spinner, layoutParams14);
            linearLayout16.addView(this.f48a, layoutParams14);
            if (this.f90d || this.f94e) {
                linearLayout15.addView(linearLayout16, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.12f : 0.17f));
            } else {
                linearLayout15.addView(linearLayout16, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.07f : 0.12f));
            }
            TextView textView9 = new TextView(this);
            textView9.setMaxLines((int) ((this.e / (textView9.getTextSize() + 6.0f)) - 6.0f));
            if (this.f90d || this.f94e) {
                linearLayout15.addView(textView9, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.76f : 0.66f));
            } else {
                linearLayout15.addView(textView9, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.86f : 0.76f));
            }
            this.f57a = new a(this, textView9, (byte) 0);
            LinearLayout linearLayout17 = new LinearLayout(this);
            linearLayout17.setBackgroundColor(-14145496);
            linearLayout17.addView(a(context, de.bassapps.launchbuttonsP.BuildConfig.FLAVOR, 259), layoutParams14);
            linearLayout17.addView(a(context, "Clear", 257), layoutParams14);
            linearLayout17.addView(a(context, de.bassapps.launchbuttonsP.BuildConfig.FLAVOR, -1), layoutParams14);
            if (this.f90d || this.f94e) {
                linearLayout15.addView(linearLayout17, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.12f : 0.17f));
            } else {
                linearLayout15.addView(linearLayout17, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.07f : 0.12f));
            }
            this.f63a.addView(linearLayout15, 1, layoutParams);
            LinearLayout linearLayout18 = new LinearLayout(this);
            linearLayout18.setBackgroundColor(-13421773);
            linearLayout18.setOrientation(1);
            LinearLayout linearLayout19 = new LinearLayout(context);
            linearLayout19.setBackgroundColor(-14145496);
            this.f93e = new TextView(context);
            try {
                this.f93e.setText("  1 - " + NMJConfig.getName(0));
            } catch (NMJException e18) {
            }
            this.f93e.setTextAppearance(context, this.g);
            this.f93e.setGravity(16);
            linearLayout19.addView(this.f93e, new LinearLayout.LayoutParams(0, -1, 1.0f));
            TextView textView10 = new TextView(context);
            textView10.setText("MIDI Ch.:   ");
            textView10.setTextAppearance(context, this.g);
            textView10.setGravity(21);
            linearLayout19.addView(textView10, new LinearLayout.LayoutParams(0, -1, 1.0f));
            this.f82c = new Spinner(this);
            ArrayAdapter arrayAdapter4 = new ArrayAdapter(this, R.layout.simple_spinner_item, new String[]{"  1", "  2", "  3", "  4", "  5", "  6", "  7", "  8", "  9", "  10", "  11", "  12", "  13", "  14", "  15", "  16"});
            arrayAdapter4.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            this.f82c.setAdapter((SpinnerAdapter) arrayAdapter4);
            if (this.f90d) {
                this.f82c.setPadding(0, 5, 0, 5);
            }
            if (this.f94e) {
                this.f82c.setPadding(0, 8, 0, 8);
            }
            linearLayout19.addView(this.f82c, new LinearLayout.LayoutParams(0, -1, 1.0f));
            if (this.f90d || this.f94e) {
                linearLayout18.addView(linearLayout19, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.12f : 0.15f));
            } else {
                linearLayout18.addView(linearLayout19, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.07f : 0.1f));
            }
            LinearLayout linearLayout20 = new LinearLayout(context);
            linearLayout20.setOrientation(1);
            ViewGroup.LayoutParams layoutParams15 = new LinearLayout.LayoutParams(-1, -2);
            int i7 = 560;
            int i8 = 0;
            int i9 = 0;
            while (true) {
                if (i8 >= ((this.f84c || !this.f74b) ? 4 : 3)) {
                    break;
                }
                LinearLayout linearLayout21 = new LinearLayout(context);
                linearLayout21.setPadding(0, 20, 0, 20);
                SeekBar seekBar = new SeekBar(context);
                seekBar.setMax(1000);
                seekBar.setOnSeekBarChangeListener(this);
                int i10 = i7 + 1;
                seekBar.setId(i7);
                if (i8 == 0) {
                    seekBar.setPadding(20, 15, 20, 15);
                } else {
                    seekBar.setPadding(20, 0, 20, 0);
                }
                linearLayout21.addView(seekBar, new LinearLayout.LayoutParams(0, -2, i8 == 0 ? 1.0f : 0.85f));
                if (i8 == 0) {
                    i2 = i10 + 1;
                    seekBar.setProgress(500);
                    linearLayout20.addView(linearLayout21, layoutParams15);
                } else {
                    EditText editText = new EditText(context);
                    editText.setInputType(2);
                    editText.setId(i10);
                    editText.setGravity(17);
                    editText.setText(String.valueOf(this.f91d[i9]));
                    linearLayout21.addView(editText, new LinearLayout.LayoutParams(0, -2, 0.15f));
                    linearLayout20.addView(linearLayout21, layoutParams15);
                    i9++;
                    i2 = i10 + 1;
                }
                i8++;
                i7 = i2;
            }
            if (this.f90d || this.f94e) {
                ScrollView scrollView = new ScrollView(context);
                scrollView.addView(linearLayout20);
                linearLayout18.addView(scrollView, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.67f : 0.54f));
            } else {
                linearLayout18.addView(linearLayout20, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.77f : 0.64f));
            }
            LinearLayout.LayoutParams layoutParams16 = new LinearLayout.LayoutParams(0, -1, 1.0f);
            layoutParams16.setMargins(8, 8, 8, 8);
            LinearLayout linearLayout22 = new LinearLayout(context);
            linearLayout22.setBackgroundColor(-14145496);
            int i11 = 0;
            int i12 = 0;
            for (int i13 = 0; i13 < 8; i13++) {
                if (i13 == 0 || i13 == 3 || i13 == 7) {
                    buttonA = a(context, this.f77b[i13], i12 + 544);
                    i12++;
                } else {
                    buttonA = b(context, this.f77b[i13], i11 + 512);
                    i11++;
                }
                if (this.f77b[i13].indexOf("#") != -1) {
                    buttonA.setBackgroundColor(-16777216);
                }
                linearLayout22.addView(buttonA, layoutParams16);
            }
            if (this.f90d || this.f94e) {
                linearLayout18.addView(linearLayout22, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.085f : 0.135f));
            } else {
                linearLayout18.addView(linearLayout22, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.06f : 0.11f));
            }
            LinearLayout linearLayout23 = new LinearLayout(context);
            linearLayout23.setBackgroundColor(-14145496);
            for (int i14 = 0; i14 < 7; i14++) {
                Button buttonB = b(context, this.f67a[i14], i14 + 528);
                buttonB.setBackgroundColor(-1118482);
                linearLayout23.addView(buttonB, layoutParams16);
            }
            if (this.f90d || this.f94e) {
                linearLayout18.addView(linearLayout23, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.085f : 0.135f));
            } else {
                linearLayout18.addView(linearLayout23, new LinearLayout.LayoutParams(-1, 0, this.f84c ? 0.06f : 0.11f));
            }
            this.f63a.addView(linearLayout18, 2, layoutParams);
            if (this.f44a != -1) {
                a(this.f44a);
            }
            this.f63a.setDisplayedChild(this.h);
        } catch (Exception e19) {
            e19.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0374  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x037a  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x037d  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0380 A[Catch: Exception -> 0x0219, TryCatch #1 {Exception -> 0x0219, blocks: (B:3:0x0006, B:5:0x000a, B:8:0x0011, B:12:0x0024, B:14:0x003d, B:15:0x0043, B:17:0x0047, B:18:0x005f, B:25:0x0081, B:27:0x0093, B:33:0x00a2, B:38:0x00d7, B:47:0x00e9, B:50:0x00f1, B:56:0x00ff, B:58:0x010f, B:60:0x0117, B:61:0x0121, B:62:0x0124, B:65:0x012f, B:68:0x0137, B:71:0x0149, B:73:0x0150, B:75:0x018c, B:76:0x018e, B:78:0x01a5, B:79:0x01a7, B:138:0x0384, B:137:0x0380, B:100:0x0270, B:102:0x0278, B:104:0x027b, B:105:0x0281, B:107:0x0284, B:109:0x0299, B:108:0x0293, B:111:0x032b, B:114:0x0330, B:117:0x033d, B:119:0x0345, B:120:0x034f, B:123:0x0357, B:124:0x035c, B:129:0x0368, B:91:0x0254, B:22:0x0078, B:86:0x022b, B:85:0x0223), top: B:142:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0384 A[Catch: Exception -> 0x0219, TRY_LEAVE, TryCatch #1 {Exception -> 0x0219, blocks: (B:3:0x0006, B:5:0x000a, B:8:0x0011, B:12:0x0024, B:14:0x003d, B:15:0x0043, B:17:0x0047, B:18:0x005f, B:25:0x0081, B:27:0x0093, B:33:0x00a2, B:38:0x00d7, B:47:0x00e9, B:50:0x00f1, B:56:0x00ff, B:58:0x010f, B:60:0x0117, B:61:0x0121, B:62:0x0124, B:65:0x012f, B:68:0x0137, B:71:0x0149, B:73:0x0150, B:75:0x018c, B:76:0x018e, B:78:0x01a5, B:79:0x01a7, B:138:0x0384, B:137:0x0380, B:100:0x0270, B:102:0x0278, B:104:0x027b, B:105:0x0281, B:107:0x0284, B:109:0x0299, B:108:0x0293, B:111:0x032b, B:114:0x0330, B:117:0x033d, B:119:0x0345, B:120:0x034f, B:123:0x0357, B:124:0x035c, B:129:0x0368, B:91:0x0254, B:22:0x0078, B:86:0x022b, B:85:0x0223), top: B:142:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x018c A[Catch: Exception -> 0x0219, TryCatch #1 {Exception -> 0x0219, blocks: (B:3:0x0006, B:5:0x000a, B:8:0x0011, B:12:0x0024, B:14:0x003d, B:15:0x0043, B:17:0x0047, B:18:0x005f, B:25:0x0081, B:27:0x0093, B:33:0x00a2, B:38:0x00d7, B:47:0x00e9, B:50:0x00f1, B:56:0x00ff, B:58:0x010f, B:60:0x0117, B:61:0x0121, B:62:0x0124, B:65:0x012f, B:68:0x0137, B:71:0x0149, B:73:0x0150, B:75:0x018c, B:76:0x018e, B:78:0x01a5, B:79:0x01a7, B:138:0x0384, B:137:0x0380, B:100:0x0270, B:102:0x0278, B:104:0x027b, B:105:0x0281, B:107:0x0284, B:109:0x0299, B:108:0x0293, B:111:0x032b, B:114:0x0330, B:117:0x033d, B:119:0x0345, B:120:0x034f, B:123:0x0357, B:124:0x035c, B:129:0x0368, B:91:0x0254, B:22:0x0078, B:86:0x022b, B:85:0x0223), top: B:142:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01a5 A[Catch: Exception -> 0x0219, TryCatch #1 {Exception -> 0x0219, blocks: (B:3:0x0006, B:5:0x000a, B:8:0x0011, B:12:0x0024, B:14:0x003d, B:15:0x0043, B:17:0x0047, B:18:0x005f, B:25:0x0081, B:27:0x0093, B:33:0x00a2, B:38:0x00d7, B:47:0x00e9, B:50:0x00f1, B:56:0x00ff, B:58:0x010f, B:60:0x0117, B:61:0x0121, B:62:0x0124, B:65:0x012f, B:68:0x0137, B:71:0x0149, B:73:0x0150, B:75:0x018c, B:76:0x018e, B:78:0x01a5, B:79:0x01a7, B:138:0x0384, B:137:0x0380, B:100:0x0270, B:102:0x0278, B:104:0x027b, B:105:0x0281, B:107:0x0284, B:109:0x0299, B:108:0x0293, B:111:0x032b, B:114:0x0330, B:117:0x033d, B:119:0x0345, B:120:0x034f, B:123:0x0357, B:124:0x035c, B:129:0x0368, B:91:0x0254, B:22:0x0078, B:86:0x022b, B:85:0x0223), top: B:142:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0260  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(int r12) {
        /*
            Method dump skipped, instruction units count: 904
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: de.humatic.nmj.NMJConfigDialog.a(int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.f62a.clear();
        int numChannels = NMJConfig.getNumChannels();
        for (int i = 0; i < numChannels; i++) {
            this.f62a.add(new q(i));
        }
        this.f51a.setAdapter((ListAdapter) this.f62a);
    }

    private ImageButton a(Context context, BitmapDrawable bitmapDrawable, int i, int i2) {
        ImageButton imageButton = new ImageButton(context);
        imageButton.setImageDrawable(bitmapDrawable);
        imageButton.setBackgroundColor(i2);
        imageButton.setId(i);
        imageButton.setOnClickListener(this);
        return imageButton;
    }

    private Button a(Context context, String str, int i) {
        Button button = new Button(context);
        button.setText(str);
        if (this.f74b) {
            if (str.length() > 1) {
                button.setTextAppearance(context, this.g);
            } else {
                button.setTextAppearance(context, R.style.TextAppearance.Medium);
            }
            if (this.f90d) {
                button.setTextSize(10.0f);
            } else if (this.f94e) {
                button.setTextSize(11.0f);
            }
            if (this.a > 2.0f) {
                button.setTextSize(11.0f);
            }
        } else {
            button.setTextAppearance(context, R.style.TextAppearance.Large);
        }
        button.setTextColor(-6710887);
        button.setBackgroundColor(-13421773);
        button.setId(i);
        button.setOnClickListener(this);
        return button;
    }

    private Button b(Context context, String str, int i) {
        Button button = new Button(context);
        button.setText(str);
        if (this.f74b) {
            if (str.length() > 1) {
                button.setTextAppearance(context, this.g);
            } else {
                button.setTextAppearance(context, R.style.TextAppearance.Medium);
            }
            if (this.a > 2.0f) {
                button.setTextSize(11.0f);
            }
        } else {
            button.setTextAppearance(context, R.style.TextAppearance.Large);
        }
        button.setTextColor(-6710887);
        button.setBackgroundColor(-13421773);
        button.setId(i);
        button.setOnTouchListener(this);
        return button;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!(view instanceof Button) || ((Button) view).getText().toString().trim().length() != 0 || view.getId() == 259) {
            if (!(view instanceof ImageButton) || !((ImageButton) view).getDrawable().equals(this.f78c)) {
                switch (view.getId()) {
                    case 257:
                        this.f57a.sendMessage(Message.obtain());
                        return;
                    case 258:
                        b();
                        return;
                    case 259:
                        NMJConfig.setFlags(-1, NMJConfig.RESTART_DNS);
                        return;
                    case 544:
                        if (this.i > 0) {
                            this.i -= 12;
                        }
                        ((Button) findViewById(545)).setText(String.valueOf(this.i / 12));
                        return;
                    case 546:
                        if (this.i < 120) {
                            this.i += 12;
                        }
                        ((Button) findViewById(545)).setText(String.valueOf(this.i / 12));
                        return;
                    case 2048:
                        try {
                            final int i = this.f44a;
                            if (this.f59a == null) {
                                new Thread(new Runnable() { // from class: de.humatic.nmj.NMJConfigDialog.3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        try {
                                            NMJConfigDialog.this.f59a = NMJConfigDialog.this.f61a.openInput(i, NMJConfigDialog.this.f55a);
                                        } catch (Exception e2) {
                                            NMJConfigDialog nMJConfigDialog = NMJConfigDialog.this;
                                            int i2 = i;
                                            nMJConfigDialog.a("Failed to open input", e2.getMessage());
                                        }
                                    }
                                }).start();
                            }
                            this.f63a.setDisplayedChild(1);
                            this.h = 1;
                            return;
                        } catch (Exception e2) {
                            int i2 = this.f44a;
                            a("Failed to open " + NMJConfig.getName(this.f44a), e2.getMessage());
                            return;
                        }
                    case 2049:
                        String strTrim = ((Button) view).getText().toString().trim();
                        if (strTrim.equals("-")) {
                            this.f73b.setText("Delete selected channel?");
                            ((Button) findViewById(2049)).setText("Cancel");
                            ((Button) findViewById(2050)).setText("OK");
                            ((ImageButton) findViewById(2048)).setImageDrawable(this.f78c);
                            ((ImageButton) findViewById(2051)).setImageDrawable(this.f78c);
                            this.c = 4096;
                            return;
                        }
                        if (strTrim.equalsIgnoreCase("Cancel") && this.c == 4096) {
                            this.f73b.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                            ((Button) findViewById(2049)).setText("-");
                            ((Button) findViewById(2050)).setText("+");
                            ((ImageButton) findViewById(2048)).setImageDrawable(this.f46a);
                            ((ImageButton) findViewById(2051)).setImageDrawable(this.f68b);
                            this.c = -1;
                            return;
                        }
                        if (strTrim.equalsIgnoreCase("Manually") && this.c == 4097) {
                            this.f73b.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                            this.f62a.m97a(-1);
                            NMJConfig.setNumChannels(NMJConfig.getNumChannels() + 1);
                            a(NMJConfig.getNumChannels() - 1);
                            ((ImageButton) findViewById(2048)).setImageDrawable(this.f78c);
                            ((ImageButton) findViewById(2051)).setImageDrawable(this.f78c);
                            ((Button) findViewById(2049)).setText("Discard");
                            ((Button) findViewById(2050)).setText("Save");
                            a((TextView) this.f49a, true);
                            a((TextView) this.f70b, true);
                            a((TextView) this.f80c, true);
                            a((TextView) this.f87d, true);
                            this.f88d.setVisibility(0);
                            this.f72b.setEnabled(true);
                            this.f53a.setEnabled(true);
                            this.c = 4100;
                            return;
                        }
                        if (strTrim.equalsIgnoreCase("Discard")) {
                            if (this.c == 4100) {
                                NMJConfig.setNumChannels(NMJConfig.getNumChannels() - 1);
                                a(NMJConfig.getNumChannels() - 1);
                            } else {
                                a(this.f44a);
                            }
                            ((Button) findViewById(2049)).setText("-");
                            ((Button) findViewById(2050)).setText("+");
                            ((ImageButton) findViewById(2048)).setImageDrawable(this.f46a);
                            ((ImageButton) findViewById(2051)).setImageDrawable(this.f68b);
                            this.c = -1;
                            return;
                        }
                        if (strTrim.equalsIgnoreCase("Cancel") && this.c == 4101) {
                            this.f62a.m97a(this.f44a);
                            this.f81c.setVisibility(8);
                            if (this.f74b) {
                                this.f52a.setVisibility(0);
                            } else {
                                this.f50a.setVisibility(0);
                            }
                            ((Button) findViewById(2049)).setText("-");
                            ((Button) findViewById(2050)).setText("+");
                            ((ImageButton) findViewById(2048)).setImageDrawable(this.f46a);
                            ((ImageButton) findViewById(2051)).setImageDrawable(this.f68b);
                            this.c = -1;
                            return;
                        }
                        if (strTrim.equalsIgnoreCase("↻")) {
                            NMJConfig.setFlags(-1, NMJConfig.RESTART_DNS);
                            return;
                        }
                        return;
                    case 2050:
                        String strTrim2 = ((Button) view).getText().toString().trim();
                        if (strTrim2.equals("+")) {
                            this.f73b.setText("Configure manually or perform scan?");
                            ((Button) findViewById(2049)).setText("Manually");
                            ((Button) findViewById(2050)).setText("Scan");
                            this.c = 4097;
                            return;
                        }
                        if (strTrim2.equalsIgnoreCase("OK") && this.c == 4096) {
                            this.f73b.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                            NMJConfig.deleteChannel(this.f44a);
                            a(this.f44a != NMJConfig.getNumChannels() ? this.f44a : 0);
                            a();
                            ((Button) findViewById(2049)).setText("-");
                            ((Button) findViewById(2050)).setText("+");
                            ((ImageButton) findViewById(2048)).setImageDrawable(this.f46a);
                            ((ImageButton) findViewById(2051)).setImageDrawable(this.f68b);
                            this.c = -1;
                            return;
                        }
                        if (strTrim2.equalsIgnoreCase("Save")) {
                            NMJConfig.setMode(this.f44a, this.f53a.getSelectedItemPosition());
                            NMJConfig.setName(this.f44a, this.f49a.getText().toString());
                            String strTrim3 = this.f70b.getText().toString().trim();
                            int i3 = this.f44a;
                            if (strTrim3.length() == 0) {
                                strTrim3 = null;
                            }
                            NMJConfig.setIP(i3, strTrim3);
                            try {
                                NMJConfig.setPort(this.f44a, Integer.parseInt(this.f80c.getText().toString().trim()));
                                break;
                            } catch (Exception e3) {
                                try {
                                    NMJConfig.setPort(this.f44a, -1);
                                    break;
                                } catch (Exception e4) {
                                }
                            }
                            try {
                                NMJConfig.setLocalPort(this.f44a, Integer.parseInt(this.f87d.getText().toString().trim()));
                                break;
                            } catch (Exception e5) {
                                try {
                                    NMJConfig.setLocalPort(this.f44a, -1);
                                    break;
                                } catch (Exception e6) {
                                }
                            }
                            NMJConfig.setNetworkAdapter(this.f44a, this.f72b.getSelectedItemPosition());
                            if (NMJConfig.getMode(this.f44a) == 0) {
                                NMJConfig.setIO(this.f44a, this.f69b.isChecked() ? 0 : 1);
                            } else {
                                NMJConfig.setIO(this.f44a, -1);
                            }
                            this.f62a.notifyDataSetChanged();
                            a(this.f44a);
                            ((Button) findViewById(2049)).setText("-");
                            ((Button) findViewById(2050)).setText("+");
                            ((ImageButton) findViewById(2048)).setImageDrawable(this.f46a);
                            ((ImageButton) findViewById(2051)).setImageDrawable(this.f68b);
                            a();
                            this.c = -1;
                            return;
                        }
                        if (strTrim2.equalsIgnoreCase("Scan")) {
                            this.f62a.m97a(-1);
                            this.f73b.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                            if (this.f74b) {
                                this.f52a.setVisibility(8);
                            } else {
                                this.f50a.setVisibility(8);
                            }
                            this.f71b.setVisibility(8);
                            this.f81c.setVisibility(0);
                            this.c = 4101;
                            ((Button) findViewById(2049)).setText("Cancel");
                            ((Button) findViewById(2050)).setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                            ((ImageButton) findViewById(2048)).setImageDrawable(this.f78c);
                            ((ImageButton) findViewById(2051)).setImageDrawable(this.f78c);
                            a();
                            return;
                        }
                        return;
                    case 2051:
                        try {
                            final int i4 = this.f44a;
                            new Thread(new Runnable() { // from class: de.humatic.nmj.NMJConfigDialog.2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    try {
                                        NMJConfigDialog.this.f60a = NMJConfigDialog.this.f61a.openOutput(i4, NMJConfigDialog.this.f56a);
                                    } catch (Exception e7) {
                                        NMJConfigDialog nMJConfigDialog = NMJConfigDialog.this;
                                        int i5 = i4;
                                        nMJConfigDialog.a("Failed to open output", e7.getMessage());
                                    }
                                }
                            }).start();
                            this.f63a.setDisplayedChild(2);
                            this.h = 2;
                            return;
                        } catch (Exception e7) {
                            int i5 = this.f44a;
                            a("Failed to open " + NMJConfig.getName(this.f44a), e7.getMessage());
                            return;
                        }
                    case 4098:
                        if (this.f53a.getSelectedItemPosition() == 0 && p.m128a(this.f70b.getText().toString())) {
                            boolean zIsChecked = ((CheckBox) view).isChecked();
                            this.f79c.setChecked(zIsChecked ? false : true);
                            ((ImageButton) findViewById(2048)).setImageDrawable(zIsChecked ? this.f46a : this.f78c);
                            ((ImageButton) findViewById(2051)).setImageDrawable(zIsChecked ? this.f78c : this.f68b);
                            return;
                        }
                        return;
                    case 4099:
                        if (this.f53a.getSelectedItemPosition() == 0 && p.m128a(this.f70b.getText().toString())) {
                            boolean zIsChecked2 = ((CheckBox) view).isChecked();
                            this.f69b.setChecked(!zIsChecked2);
                            ((ImageButton) findViewById(2048)).setImageDrawable(zIsChecked2 ? this.f78c : this.f46a);
                            ((ImageButton) findViewById(2051)).setImageDrawable(zIsChecked2 ? this.f68b : this.f78c);
                            return;
                        }
                        return;
                    case 4101:
                        this.f73b.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                        NMJConfig.m36b();
                        return;
                    case 4102:
                        return;
                    case 12288:
                        startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 0);
                        return;
                    case 12289:
                        startActivityForResult(new Intent("android.settings.WIRELESS_SETTINGS"), 0);
                        return;
                    case 12290:
                        startActivityForResult(new Intent("android.settings.BLUETOOTH_SETTINGS"), 0);
                        return;
                    case 12291:
                        try {
                            startActivityForResult(new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS"), 0);
                            return;
                        } catch (ActivityNotFoundException e8) {
                            startActivityForResult(new Intent("com.android.settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS"), 0);
                            return;
                        }
                    case 12304:
                        if ((NMJConfig.getFlags(-1) & 3) != 0) {
                            NMJConfig.setFlags(-1, NMJConfig.getFlags(-1) & (-4));
                        } else {
                            NMJConfig.setFlags(-1, NMJConfig.getFlags(-1) | 3);
                        }
                        ((ImageButton) findViewById(12304)).setImageDrawable(a(getApplication(), 0, NMJConfig.getFlags(-1)));
                        return;
                    case 12305:
                        if ((NMJConfig.getFlags(-1) & 8) != 0) {
                            NMJConfig.setFlags(-1, NMJConfig.getFlags(-1) & (-9));
                        } else {
                            NMJConfig.setFlags(-1, NMJConfig.getFlags(-1) | 8);
                            try {
                                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                                if (defaultAdapter != null && defaultAdapter.isEnabled()) {
                                    if (defaultAdapter.getScanMode() != 23) {
                                        Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
                                        intent.addFlags(268435456);
                                        intent.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 300);
                                        startActivity(intent);
                                    }
                                } else {
                                    return;
                                }
                                break;
                            } catch (Exception e9) {
                            }
                        }
                        ((ImageButton) findViewById(12305)).setImageDrawable(a(getApplication(), 1, NMJConfig.getFlags(-1)));
                        return;
                    case 12306:
                        if ((NMJConfig.getFlags(-1) & 16) != 0) {
                            NMJConfig.setFlags(-1, NMJConfig.getFlags(-1) & (-17));
                        } else {
                            NMJConfig.setFlags(-1, NMJConfig.getFlags(-1) | 16);
                        }
                        ((ImageButton) findViewById(12306)).setImageDrawable(a(getApplication(), 2, NMJConfig.getFlags(-1)));
                        return;
                    case 12307:
                        if ((NMJConfig.getFlags(-1) & 32) != 0) {
                            NMJConfig.setFlags(-1, NMJConfig.getFlags(-1) & (-33));
                        } else {
                            NMJConfig.setFlags(-1, NMJConfig.getFlags(-1) | 32);
                        }
                        ((ImageButton) findViewById(12307)).setImageDrawable(a(getApplication(), 3, NMJConfig.getFlags(-1)));
                        return;
                    default:
                        return;
                }
                e.printStackTrace();
            }
        }
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z = motionEvent.getAction() == 0;
        boolean z2 = motionEvent.getAction() == 1;
        if (!z && !z2) {
            return true;
        }
        if (view.getId() >= 528 && view.getId() <= 535) {
            this.f85c[0] = (byte) (this.f82c.getSelectedItemPosition() | 144);
            this.f85c[1] = a(view.getId() - 528, true);
            this.f85c[2] = (byte) (z ? 100 : 0);
            a(this.f85c);
        } else if (view.getId() >= 512 && view.getId() <= 517) {
            this.f85c[0] = (byte) (this.f82c.getSelectedItemPosition() | 144);
            this.f85c[1] = a(view.getId() - 512, false);
            this.f85c[2] = (byte) (z ? 100 : 0);
            a(this.f85c);
        } else if (view.getId() == 4103) {
            a((TextView) this.f70b, true);
        } else if (view.getId() == 4104) {
            a((TextView) this.f80c, true);
            a((TextView) this.f87d, true);
        }
        return false;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.f96g) {
            try {
                ((TextView) adapterView.getChildAt(0)).setTextColor(-6710887);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!this.f64a) {
            try {
                if (adapterView.equals(this.f53a)) {
                    if (i == 0) {
                        a((TextView) this.f87d, false);
                        this.f88d.setVisibility(0);
                        this.f69b.setChecked(NMJConfig.getIO(this.f44a) <= 0);
                        this.f79c.setChecked(Math.abs(NMJConfig.getIO(this.f44a)) == 1);
                        this.f72b.setEnabled(true);
                    } else if (i == 1) {
                        a((TextView) this.f70b, true);
                        a((TextView) this.f80c, true);
                        a((TextView) this.f87d, true);
                        this.f88d.setVisibility(4);
                        this.f72b.setEnabled(true);
                        NMJConfig.setIO(this.f44a, -1);
                    } else if (i == 2) {
                        this.f70b.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                        a((TextView) this.f70b, false);
                        this.f80c.setText("0");
                        a((TextView) this.f80c, true);
                        this.f87d.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                        a((TextView) this.f87d, false);
                        this.f88d.setVisibility(4);
                        this.f72b.setEnabled(false);
                        NMJConfig.setIP(this.f44a, null);
                        NMJConfig.setPort(this.f44a, 0);
                        NMJConfig.setIO(this.f44a, -1);
                    } else if (i == 3) {
                        a((TextView) this.f70b, false);
                        a((TextView) this.f80c, false);
                        a((TextView) this.f87d, false);
                        this.f88d.setVisibility(4);
                        NMJConfig.setIO(this.f44a, -1);
                    } else if (i == 4) {
                        NMJConfig.setPort(this.f44a, -1);
                        NMJConfig.setLocalPort(this.f44a, -1);
                        this.f80c.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                        this.f87d.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                        this.f70b.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                        a((TextView) this.f70b, false);
                        a((TextView) this.f80c, false);
                        a((TextView) this.f87d, false);
                        this.f88d.setVisibility(4);
                        NMJConfig.setIO(this.f44a, -1);
                    } else if (i == 5 || i == 7) {
                        p.logln(2, "Hardware dependent mode, not setable");
                        this.f53a.setSelection(NMJConfig.getMode(this.f44a));
                        return;
                    }
                    onManualEdit();
                    return;
                }
                if (adapterView.equals(this.f72b)) {
                    onManualEdit();
                }
            } catch (Exception e2) {
                p.a(e2, "CfgDlg, onItemSelected");
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if ((keyEvent != null || i != 6) && (i != 0 || keyEvent.getAction() != 0)) {
            return false;
        }
        this.f47a.hideSoftInputFromWindow(((EditText) textView).getWindowToken(), 0);
        return true;
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (!NetworkMidiSystem.get().isOpen(-1, this.f44a)) {
            onManualEdit();
        }
    }

    public final void onManualEdit() {
        if (!this.f64a && !((Button) findViewById(2050)).getText().toString().equalsIgnoreCase("Save")) {
            ((ImageButton) findViewById(2048)).setImageDrawable(this.f78c);
            ((ImageButton) findViewById(2051)).setImageDrawable(this.f78c);
            ((Button) findViewById(2049)).setText("Discard");
            ((Button) findViewById(2050)).setText("Save");
        }
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        if (!view.equals(this.f70b)) {
            return false;
        }
        if (NMJConfig.getMode(this.f44a) == 1 && NMJConfig.getIP(this.f44a) == null) {
            v[] vVarArrM31a = NMJConfig.m31a(this.f44a);
            if (vVarArrM31a == null || vVarArrM31a.length == 0) {
                return true;
            }
            final int i = this.f44a;
            this.f45a = new AlertDialog.Builder(this);
            this.f45a.setTitle("Ch " + i + " Clients");
            String[] strArr = new String[vVarArrM31a.length];
            for (int i2 = 0; i2 < vVarArrM31a.length; i2++) {
                strArr[i2] = String.valueOf(vVarArrM31a[i2].f329a) + " (" + vVarArrM31a[i2].f330b + ")";
            }
            this.f = -1;
            this.f45a.setSingleChoiceItems(strArr, this.f, new DialogInterface.OnClickListener() { // from class: de.humatic.nmj.NMJConfigDialog.6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    NMJConfigDialog.this.f = i3;
                }
            });
            this.f45a.setPositiveButton("Disconnect Selected", new DialogInterface.OnClickListener() { // from class: de.humatic.nmj.NMJConfigDialog.7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    try {
                        if (NMJConfigDialog.this.f >= 0) {
                            NMJConfig.c(i, NMJConfigDialog.this.f);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            this.f45a.setNegativeButton("Cancel", new DialogInterface.OnClickListener(this) { // from class: de.humatic.nmj.NMJConfigDialog.8
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                }
            });
            this.f45a.show();
        }
        return true;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            if (seekBar.getId() == 560) {
                this.f75b[0] = (byte) (this.f82c.getSelectedItemPosition() | 224);
                int max = (int) (16384.0f * (i / seekBar.getMax()));
                this.f75b[1] = (byte) (max & 127);
                this.f75b[2] = (byte) (max >> 7);
                a(this.f75b);
                return;
            }
            int i2 = Integer.parseInt(((TextView) findViewById(seekBar.getId() + 1)).getText().toString().trim());
            int max2 = (int) (127.0f * (i / seekBar.getMax()));
            if (this.f65a[1] != i2 || this.f65a[2] != max2) {
                this.f65a[0] = (byte) (this.f82c.getSelectedItemPosition() | 176);
                this.f65a[1] = (byte) i2;
                this.f65a[2] = (byte) max2;
                a(this.f65a);
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == 560) {
            seekBar.setProgress(seekBar.getMax() / 2);
            this.f75b[0] = (byte) (this.f82c.getSelectedItemPosition() | 224);
            this.f75b[1] = 0;
            this.f75b[2] = 64;
            a(this.f75b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        this.f64a = true;
        if (this.f59a != null) {
            try {
                ((NetworkMidiInput) this.f61a.a(0, this.f44a)).close(this.f55a);
            } catch (Exception e) {
            }
            this.f59a = null;
        }
        if (this.f60a != null) {
            this.f60a.close(this.f56a);
            this.f60a = null;
        }
        this.f63a.setDisplayedChild(0);
        this.h = 0;
        Message messageObtain = Message.obtain();
        messageObtain.what = 256;
        this.f58a.sendMessageDelayed(messageObtain, 500L);
    }

    private void a(TextView textView, boolean z) {
        textView.setEnabled(z);
        textView.setFocusableInTouchMode(z);
        textView.setFocusable(z);
        if (!z) {
            textView.clearFocus();
            this.f54a.requestFocus();
        }
    }

    private LinearLayout a(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        int connectivity = NMJConfig.getConnectivity(context);
        linearLayout.setGravity(16);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1, 1.0f);
        layoutParams.setMargins(0, this.f90d ? 1 : 5, 0, 0);
        for (int i = 0; i < 4; i++) {
            boolean z = ((1 << i) & connectivity) != 0;
            String str = "wifi.png";
            if (i == 1) {
                str = "usb.png";
            } else if (i == 2) {
                str = "bt.png";
            } else if (i == 3) {
                str = "adb.png";
            }
            BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(a(str), (int) ((r3.getWidth() / 2.0f) * this.a), (int) ((r3.getHeight() / 2.0f) * this.a), true));
            if (!z) {
                bitmapDrawable.setAlpha(100);
            }
            linearLayout.addView(a(context, bitmapDrawable, i + 12288, 0), i, layoutParams);
        }
        return linearLayout;
    }

    private LinearLayout b(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        int flags = NMJConfig.getFlags(-1);
        linearLayout.setGravity(16);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1, 1.0f);
        layoutParams.setMargins(0, this.f90d ? 0 : 5, 0, 0);
        for (int i = 0; i < 4; i++) {
            try {
                linearLayout.addView(a(context, a(context, i, flags), i + 12304, 0), i, layoutParams);
            } catch (Exception e) {
            }
        }
        return linearLayout;
    }

    private BitmapDrawable a(Context context, int i, int i2) {
        boolean z;
        String str;
        if (i == 0) {
            z = (i2 & 3) != 0;
            str = "wifi.png";
        } else if (i == 1) {
            z = (i2 & 8) != 0;
            str = "bt.png";
        } else if (i == 2) {
            z = (i2 & 16) != 0;
            str = "adb.png";
        } else if (i != 3) {
            z = false;
            str = de.bassapps.launchbuttonsP.BuildConfig.FLAVOR;
        } else {
            z = (i2 & 32) != 0;
            str = "usb.png";
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(a(str), (int) ((r0.getWidth() / 2.3f) * this.a), (int) ((r0.getHeight() / 2.3f) * this.a), true));
        if (!z) {
            bitmapDrawable.setAlpha(80);
        }
        return bitmapDrawable;
    }

    private BitmapDrawable a(Context context, String str) {
        return new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(a(str), (int) ((r0.getWidth() / 2.0f) * this.a), (int) ((r0.getHeight() / 2.0f) * this.a), true));
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private boolean m73a() {
        try {
            if (this.f72b.getCount() != NMJConfig.a() + 1) {
                Vector vector = new Vector();
                vector.add("Auto");
                try {
                    try {
                        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                        while (networkInterfaces.hasMoreElements()) {
                            vector.add(networkInterfaces.nextElement().getName());
                        }
                    } catch (SocketException e) {
                        if (e.toString().indexOf("unkown error") != -1) {
                            System.out.println(String.valueOf(e.toString()) + "\nDid you grant the required network permission?");
                        } else {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                String[] strArr = new String[vector.size()];
                vector.copyInto(strArr);
                ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.simple_spinner_item, strArr);
                arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                this.f72b.setAdapter((SpinnerAdapter) arrayAdapter);
                return true;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return false;
    }

    static /* synthetic */ void b(NMJConfigDialog nMJConfigDialog, final int i, String str, String str2) {
        nMJConfigDialog.f45a = new AlertDialog.Builder(nMJConfigDialog);
        nMJConfigDialog.f45a.setTitle(str);
        AlertDialog.Builder builder = nMJConfigDialog.f45a;
        StringBuilder sb = new StringBuilder("Connection to ");
        if (str2 == null) {
            str2 = "client";
        }
        builder.setMessage(sb.append(str2).append(" lost!").toString());
        nMJConfigDialog.f45a.setPositiveButton("Ok", new DialogInterface.OnClickListener() { // from class: de.humatic.nmj.NMJConfigDialog.9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                NMJConfigDialog.this.b();
                if (NMJConfig.getIP(i) == null) {
                    NMJConfigDialog.this.f70b.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                    NMJConfigDialog.this.f80c.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                }
            }
        });
        nMJConfigDialog.f45a.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2) {
        if (!this.f95f) {
            this.f45a = new AlertDialog.Builder(this);
            this.f45a.setTitle(str);
            this.f45a.setMessage(str2);
            this.f45a.setPositiveButton("Ok", new DialogInterface.OnClickListener() { // from class: de.humatic.nmj.NMJConfigDialog.10
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    NMJConfigDialog.this.b();
                    NMJConfigDialog.this.f95f = false;
                }
            });
            this.f95f = true;
            try {
                if (Thread.currentThread().equals(Looper.getMainLooper())) {
                    this.f45a.show();
                } else {
                    this.f58a.post(new Runnable() { // from class: de.humatic.nmj.NMJConfigDialog.11
                        @Override // java.lang.Runnable
                        public final void run() {
                            NMJConfigDialog.this.f45a.show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        if (this.f59a != null) {
            try {
                this.f59a.removeMidiListener(this.f55a);
            } catch (Exception e) {
            }
        }
        if (this.f60a != null) {
            try {
                this.f60a.close(this.f56a);
            } catch (Exception e2) {
            }
        }
        this.f59a = null;
        this.f60a = null;
        try {
            NMJConfig.removeSystemListener(this);
        } catch (Exception e3) {
        }
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        try {
            NMJConfig.addSystemListener(this);
        } catch (Exception e) {
        }
        m73a();
    }

    private void a(byte[] bArr) {
        try {
            this.f60a.sendMidiOnThread(bArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte a(int i, boolean z) {
        return (byte) ((z ? this.f76b[i] : this.f86c[i]) + this.i);
    }

    class a extends Handler {
        private int a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private TextView f101a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private StringBuffer f103a;

        private a(TextView textView) {
            this.f103a = new StringBuffer();
            this.f101a = textView;
        }

        /* synthetic */ a(NMJConfigDialog nMJConfigDialog, TextView textView, byte b) {
            this(textView);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = 0;
            try {
                Bundle data = message.getData();
                byte[] byteArray = data.getByteArray("MIDI");
                if (byteArray == null || byteArray.length == 0) {
                    this.f103a.delete(0, this.f103a.length());
                    this.f101a.setText(this.f103a.toString());
                    return;
                }
                int length = this.f103a.length();
                this.f103a.append("nmjCh: ");
                this.f103a.append(data.getInt("CH") + 1);
                this.f103a.append(" - ");
                if ((byteArray[0] & 255) != 240 || byteArray.length < 5) {
                    this.f103a.append(p.a(byteArray[0] & 255, -1, -1));
                } else {
                    this.f103a.append(p.a(byteArray[0] & 255, byteArray[3] & 255, byteArray[4] & 255));
                    this.f103a.append(" (" + byteArray.length + " bytes)");
                }
                this.f103a.append(": ");
                if (NMJConfigDialog.this.f48a.isChecked()) {
                    while (i < byteArray.length) {
                        this.f103a.append(String.valueOf(p.a(byteArray[i])) + " ");
                        i++;
                    }
                } else {
                    while (i < byteArray.length) {
                        this.f103a.append(String.valueOf(byteArray[i] & 255) + " ");
                        i++;
                    }
                }
                this.f103a.append("\n");
                this.a++;
                if (this.f101a.getLineCount() < ((int) ((this.f101a.getHeight() / this.f101a.getTextSize()) - 6.0f))) {
                    this.f101a.setText(this.f103a.toString());
                } else {
                    this.f103a.delete(0, length);
                    this.f101a.setText(this.f103a.toString());
                }
                data.clear();
            } catch (Exception e) {
            }
        }
    }

    @Override // de.humatic.nmj.NMJSystemListener
    public final void systemChanged(int i, int i2, int i3) {
        if (i < 0) {
            switch (i2) {
                case 528:
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 528;
                    Bundle bundle = new Bundle();
                    bundle.putInt("SCAN_EVENT", i3);
                    messageObtain.setData(bundle);
                    this.f58a.sendMessage(messageObtain);
                    break;
            }
            return;
        }
        p.a(4, "Cfg dlg - System changed ", i, i2, i3);
        Message messageObtain2 = Message.obtain();
        messageObtain2.what = i2;
        Bundle bundle2 = new Bundle();
        bundle2.putIntArray("CON_STATE", new int[]{i, i2, i3});
        messageObtain2.setData(bundle2);
        this.f58a.sendMessage(messageObtain2);
    }

    @Override // de.humatic.nmj.NMJSystemListener
    public final void systemError(int i, int i2, String str) {
        Message messageObtain = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("NMJ_ERR", String.valueOf(("System error: " + i + " " + i2 + " " + str).toString()) + "\n");
        messageObtain.setData(bundle);
        p.logln(1, "Cfg dlg - System error on ch: " + i + ", code: " + i2 + ", desc: " + str);
    }

    class b extends Handler {
        private int a;

        private b(TextView textView) {
            new StringBuffer();
        }

        /* synthetic */ b(NMJConfigDialog nMJConfigDialog, TextView textView, byte b) {
            this(null);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int[] intArray;
            try {
                switch (message.what) {
                    case 4:
                        Bundle data = message.getData();
                        if (data != null && (intArray = data.getIntArray("CON_STATE")) != null && intArray.length >= 3) {
                            if (intArray[2] == 512) {
                                if (NMJConfigDialog.this.f58a.hasMessages(4096)) {
                                    NMJConfigDialog.this.f58a.removeMessages(4096);
                                }
                                Message messageObtain = Message.obtain();
                                messageObtain.what = 4096;
                                NMJConfigDialog.this.f58a.sendMessageDelayed(messageObtain, 1000L);
                            } else if (intArray[2] == 8 || intArray[2] == 16 || intArray[2] == 4) {
                                NMJConfigDialog.this.a();
                            } else if (intArray[2] == 4096) {
                                NMJConfigDialog nMJConfigDialog = NMJConfigDialog.this;
                                int i = intArray[0];
                                nMJConfigDialog.a("Channel " + (intArray[0] + 1), String.valueOf(NMJConfig.getIP(intArray[0])) + " did not respond to innvitation!");
                            } else if (intArray[2] == 1024) {
                                NMJConfigDialog.b(NMJConfigDialog.this, intArray[0], "nmj Ch." + (intArray[0] + 1), NMJConfig.getIP(NMJConfigDialog.this.f44a) == null ? NMJConfig.m16a(intArray[0], 0) : NMJConfig.getIP(intArray[0]));
                            } else if (intArray[2] == 2048) {
                                NMJConfigDialog nMJConfigDialog2 = NMJConfigDialog.this;
                                int i2 = intArray[0];
                                nMJConfigDialog2.a("Channel " + (intArray[0] + 1), String.valueOf(NMJConfig.getIP(intArray[0])) + " rejected connection attempt!");
                            } else if (intArray[2] == 32) {
                                NMJConfigDialog.this.a();
                            } else if (intArray[2] == 64 || intArray[2] == 128) {
                                NMJConfigDialog.this.a();
                                if (intArray[0] == NMJConfigDialog.this.f44a) {
                                    NMJConfigDialog.this.f64a = true;
                                    v[] vVarArrM31a = NMJConfig.m31a(NMJConfigDialog.this.f44a);
                                    StringBuffer stringBuffer = new StringBuffer();
                                    if (vVarArrM31a != null) {
                                        for (v vVar : vVarArrM31a) {
                                            stringBuffer.append(vVar.f329a);
                                            stringBuffer.append(", ");
                                        }
                                    }
                                    NMJConfigDialog.this.f70b.setText(stringBuffer.toString());
                                    NMJConfigDialog.this.f64a = false;
                                }
                            }
                            break;
                        }
                        break;
                    case 256:
                        NMJConfigDialog.this.f64a = false;
                        break;
                    case 257:
                        NMJConfigDialog.this.f73b.setText(de.bassapps.launchbuttonsP.BuildConfig.FLAVOR);
                        break;
                    case 528:
                        int i3 = message.getData().getInt("SCAN_EVENT");
                        if (i3 == 0) {
                            this.a = NMJConfig.getNumChannels();
                            NMJConfigDialog.this.f92e.setVisibility(0);
                            NMJConfigDialog.this.f73b.setText("Scaning for Bluetooth MIDI servers");
                        } else if (i3 == 1) {
                            NMJConfigDialog.this.f92e.setVisibility(4);
                            if (NMJConfig.getNumChannels() != this.a) {
                                NMJConfigDialog.this.f73b.setText("Found " + (NMJConfig.getNumChannels() - this.a) + " Bluetooth server(s)");
                                NMJConfigDialog.this.f51a.setSelection(NMJConfigDialog.this.f51a.getCount() - 1);
                                NMJConfigDialog.this.f62a.m97a(NMJConfigDialog.this.f51a.getCount() - 1);
                            } else {
                                NMJConfigDialog.this.f73b.setText("No servers found");
                            }
                            Message messageObtain2 = Message.obtain();
                            messageObtain2.what = 257;
                            sendMessageDelayed(messageObtain2, 3000L);
                        }
                        break;
                    case 4096:
                        NMJConfigDialog.this.a();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap a(String str) {
        try {
            return BitmapFactory.decodeStream(getClass().getClassLoader().getResourceAsStream("de/humatic/nmj/img/" + str));
        } catch (Exception e) {
            return null;
        }
    }

    class NMJInputClient implements NetworkMidiListener {
        NMJInputClient() {
        }

        @Override // de.humatic.nmj.NetworkMidiListener
        public void midiReceived(int i, int i2, byte[] bArr, long j) {
            if (NMJConfigDialog.this.f63a.getDisplayedChild() == 1 && p.m127a(bArr[0] & 255, NMJConfigDialog.this.b)) {
                Message messageObtain = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putByteArray("MIDI", bArr);
                bundle.putInt("CH", i);
                messageObtain.setData(bundle);
                NMJConfigDialog.this.f57a.sendMessage(messageObtain);
            }
        }
    }

    class NMJOutputClient implements NetworkMidiClient {
        NMJOutputClient(NMJConfigDialog nMJConfigDialog) {
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 4 || this.h <= 0 || keyEvent.getAction() != 0) {
            return super.dispatchKeyEvent(keyEvent);
        }
        b();
        return true;
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
