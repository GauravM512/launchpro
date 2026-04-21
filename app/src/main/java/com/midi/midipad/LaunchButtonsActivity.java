package com.midi.midipad;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LaunchButtonsActivity extends Activity implements View.OnTouchListener, MidiPacketListener {
    private static final String TAG = "LaunchPlus";
    private static final String TAG_MIDI_IN = "MIDI_IN";
    private static final String TAG_MIDI_OUT = "MIDI_OUT";
    Button Back;
    Button Close;
    Button Quit;
    TableLayout TLayout;
    Button Usb;
    int height;
    boolean knobs_built;
    public PopupWindow kwindo;
    private int lastPosition;
    View layout;
    boolean lightshow;
    Handler mHandler;
    private MidiLogger midiLogger;
    private UsbMidiBridge usbMidiBridge;
    int mode;
    public PopupWindow pwindo;
    int sends;
    Spinner spinner;
    int width;
    private int txOkCount;
    private int txFailCount;
    private byte[] myNote = {-112, 36, 0};
    ImageButton[][] but = (ImageButton[][]) Array.newInstance((Class<?>) ImageButton.class, 16, 16);
    byte[] quit = new byte[3];
    boolean[] transporttoogle = new boolean[5];
    int knob_open = 0;
    int[][] blinks = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 30, 30);
    MidiSendWorker sBB = new MidiSendWorker();
    RoundKnobButton[][] knobs = (RoundKnobButton[][]) Array.newInstance((Class<?>) RoundKnobButton.class, 8, 8);
    private final Map<String, Integer> buttonColorCache = new HashMap<>();

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        this.width = display.getWidth();
        this.height = display.getHeight();
        this.quit[0] = 6;
        this.quit[1] = 6;
        this.quit[2] = 6;
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setRequestedOrientation(0);
        setContentView(R.layout.but);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.sBB.start();
        TextView tv = (TextView) findViewById(R.id.textView01);
        this.midiLogger = new MidiLogger(tv);
        tv.setVisibility(8);
        midiSystemLoad(this);
        getWindow().addFlags(128);
        createButtons();
    }

    public void buildUI() {
        final Button button = (Button) findViewById(R.id.Button01);
        this.spinner = (Spinner) findViewById(R.id.Spinner01);
        if (this.usbMidiBridge == null || !this.usbMidiBridge.isSupported()) {
            button.setEnabled(false);
            this.spinner.setEnabled(false);
            ((TextView) findViewById(R.id.TextView01)).setText("Android USB MIDI requires Android 6+");
            return;
        }

        final String[] outputs = this.usbMidiBridge.getOutputDisplayNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, outputs.length == 0 ? new String[]{"No USB MIDI output"} : outputs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter((SpinnerAdapter) adapter);
        button.setEnabled(outputs.length > 0);

        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if (position != LaunchButtonsActivity.this.lastPosition && outputs.length > 0) {
                    LaunchButtonsActivity.this.lastPosition = position;
                    boolean connected = LaunchButtonsActivity.this.usbMidiBridge.openOutputByIndex(position);
                    LaunchButtonsActivity.this.sBB.midiOut = LaunchButtonsActivity.this.usbMidiBridge;
                    ((TextView) LaunchButtonsActivity.this.findViewById(R.id.TextView01)).setText(LaunchButtonsActivity.this.connectionStateText(connected));
                    button.setEnabled(connected);
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (outputs.length > 0) {
            int recommended = this.usbMidiBridge.getRecommendedTargetIndex();
            this.lastPosition = recommended < 0 ? 0 : recommended;
            boolean connected = this.usbMidiBridge.openOutputByIndex(this.lastPosition);
            this.sBB.midiOut = this.usbMidiBridge;
            button.setEnabled(connected);
            this.spinner.setSelection(this.lastPosition);
            ((TextView) findViewById(R.id.TextView01)).setText(connectionStateText(connected));
        }

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent me) {
                try {
                    if (me.getAction() == 0) {
                        LaunchButtonsActivity.this.myNote[2] = 100;
                        LaunchButtonsActivity.this.sendMidiNew(LaunchButtonsActivity.this.myNote);
                    } else if (me.getAction() == 1) {
                        LaunchButtonsActivity.this.myNote[2] = 0;
                        LaunchButtonsActivity.this.sendMidiNew(LaunchButtonsActivity.this.myNote);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return true;
            }
        });
        TextView tv = (TextView) findViewById(R.id.TextView01);
        this.midiLogger = new MidiLogger(tv);
    }

    public void createButtons() {
        TableRow trunten = new TableRow(this);
        for (int i = 0; i < 5; i++) {
            this.transporttoogle[i] = false;
        }
        int tag = 0;
        int transportTag = 300;
        boolean small = false;
        int size = this.height / 8;
        if (this.width < (this.height / 8) * 14) {
            size = this.height / 9;
            small = true;
        }
        Log.d(TAG, String.valueOf("aaaaaa " + size));
        TableLayout layout = new TableLayout(this);
        layout.setLayoutParams(new TableLayout.LayoutParams(40000, 40000));
        layout.setPadding(1, 1, 1, 1);
        layout.setBackgroundColor(61685);
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.rel);
        ll.addView(layout);
        for (int y = 0; y < 8; y++) {
            TableRow tr = new TableRow(this);
            for (int x = 0; x < 14; x++) {
                this.but[x][y] = new ImageButton(this);
                if (x == 0 || x >= 10) {
                    this.but[x][y].setImageResource(R.drawable.nroundmask6);
                } else if (x == 9) {
                    this.but[x][y].setImageResource(R.drawable.roundmaskpl);
                } else {
                    this.but[x][y].setImageResource(R.drawable.mssmask);
                }
                if (y == 0 && x == 13) {
                    this.but[x][y].setBackgroundColor(-65281);
                } else if (x < 13) {
                    setButtonBackground2(x, y, 0);
                } else {
                    this.but[x][y].setBackgroundColor(0);
                }
                if (x < 9) {
                    this.but[x][y].setTag(Integer.valueOf(tag));
                    tag++;
                    if (x == 8) {
                        tag += 7;
                    }
                } else if (x == 9) {
                    this.but[x][y].setTag(Integer.valueOf(y + 200));
                } else {
                    this.but[x][y].setTag(100);
                }
                this.but[x][y].setOnTouchListener(this);
                this.but[x][y].setScaleType(ImageView.ScaleType.CENTER_CROP);
                this.but[x][y].setPadding(0, 0, 0, 0);
                if (x != 12 && y < 8) {
                    if (small) {
                        if (x != 11 && x != 12 && x != 13) {
                            tr.addView(this.but[x][y], size, size);
                        }
                    } else {
                        tr.addView(this.but[x][y], size, size);
                    }
                }
                if (y >= 0 && x == 12 && y < 11) {
                    this.but[12][y] = new ImageButton(this);
                    switch (y) {
                        case 0:
                            this.but[12][y].setImageResource(R.drawable.knobtr);
                            break;
                        case 1:
                            this.but[12][y].setImageResource(R.drawable.play);
                            break;
                        case 2:
                            this.but[12][y].setImageResource(R.drawable.stop);
                            break;
                        case 3:
                            this.but[12][y].setImageResource(R.drawable.record);
                            break;
                        case 4:
                            this.but[12][y].setImageResource(R.drawable.overdub);
                            break;
                        case 5:
                            this.but[12][y].setImageResource(R.drawable.metronome);
                            break;
                        case 6:
                            this.but[12][y].setImageResource(R.drawable.a);
                            break;
                        case 7:
                            this.but[12][y].setImageResource(R.drawable.b);
                            break;
                    }
                    this.but[12][y].setTag(Integer.valueOf(transportTag));
                    this.but[12][y].setOnTouchListener(this);
                    this.but[12][y].setBackgroundColor(-65536);
                    this.but[12][y].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    this.but[12][y].setPadding(0, 0, 0, 0);
                    Log.d(TAG, String.valueOf("x" + x + "y" + y));
                    tr.addView(this.but[12][y], size, size);
                    if (y == 7) {
                        transportTag++;
                        this.but[13][y] = new ImageButton(this);
                        Log.d(TAG, String.valueOf("1234"));
                        this.but[13][y].setBackgroundColor(-52225);
                        this.but[13][y].setImageResource(R.drawable.info);
                        this.but[13][y].setTag(Integer.valueOf(transportTag));
                        this.but[13][y].setOnTouchListener(this);
                        this.but[13][y].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        this.but[13][y].setPadding(0, 0, 0, 0);
                        if (small) {
                            trunten.addView(this.but[13][y], size, size);
                        } else {
                            tr.addView(this.but[13][y], size, size);
                        }
                    }
                    transportTag++;
                }
            }
            layout.addView(tr);
        }
        this.but[13][0].setTag(555);
        this.but[13][0].setImageResource(R.drawable.nroundmask6);
        this.but[10][0].setImageResource(R.drawable.roundmasktoans);
        this.but[10][1].setImageResource(R.drawable.roundmasktuans);
        this.but[10][2].setImageResource(R.drawable.roundmasktlans);
        this.but[10][3].setImageResource(R.drawable.roundmasktrans);
        if (small) {
            for (int i2 = 0; i2 < 8; i2++) {
                trunten.addView(this.but[11][i2], size, size);
            }
            trunten.addView(this.but[13][0], size, size);
            layout.addView(trunten);
        }
        setTags();
        makeKnobs();
    }

    public void setTags() {
        int tag = 1;
        for (int y = 0; y < 8; y++) {
            this.but[11][y].setTag(Integer.valueOf(tag));
            tag++;
        }
        int tag2 = 91;
        for (int y2 = 0; y2 < 8; y2++) {
            this.but[10][y2].setTag(Integer.valueOf(tag2));
            tag2++;
        }
        int tag3 = 10;
        for (int y3 = 7; y3 >= 0; y3--) {
            for (int x = 0; x < 10; x++) {
                this.but[x][y3].setTag(Integer.valueOf(tag3));
                tag3++;
            }
        }
    }

    public void makeKnobs() {
        this.TLayout = new TableLayout(this);
        this.TLayout.setLayoutParams(new TableLayout.LayoutParams(40000, 40000));
        this.TLayout.setPadding(1, 1, 1, 1);
        this.TLayout.setBackgroundColor(289686596);
        for (int x = 0; x < 2; x++) {
            TableRow tr = new TableRow(this);
            for (int y = 0; y < 8; y++) {
                int id = (x * 8) + y + 1;
                this.knobs[x][y] = new RoundKnobButton(this, null, id);
                tr.addView(this.knobs[x][y], this.height / 6, this.height / 6);
                ViewGroup.LayoutParams kek = this.knobs[x][y].getLayoutParams();
                ((ViewGroup.MarginLayoutParams) kek).setMargins(this.width / 100, 0, 0, 0);
                this.knobs[x][y].setLayoutParams(kek);
                this.knobs[x][y].SetListener(new RoundKnobButton.RoundKnobButtonListener() {
                    @Override // com.midi.midipad.RoundKnobButton.RoundKnobButtonListener
                    public void onStateChange(boolean newstate) {
                    }

                    @Override // com.midi.midipad.RoundKnobButton.RoundKnobButtonListener
                    public void onRotate(RoundKnobButton roundKnobButton, int percentage, int ID) {
                        try {
                            Log.d(TAG, String.valueOf("local" + ID));
                            LaunchButtonsActivity.this.sendMidiB(new byte[]{-80, (byte) ID, (byte) percentage});
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            this.TLayout.addView(tr);
        }
        this.knobs_built = true;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent a) {
        int action = a.getActionMasked();
        int tag = ((Integer) v.getTag()).intValue();
        if (handleLaunchpadLayoutTouch(tag, action)) {
            return true;
        }
        if (handleUiOnlyTouch(v, action)) {
            return true;
        }
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            Log.d(TAG, String.valueOf(tag));
            Log.d(TAG, String.valueOf("tag   " + tag));
            byte tagb = (byte) tag;
            if ((tag < 10 || ((tag > 90 && tag < 300) || tag % 10 == 0 || tag % 10 == 9)) && tag != 300) {
                Log.d(TAG, String.valueOf("ye" + this.mode + " " + tag));
                try {
                    if (this.mode == 0) {
                        sendMidiB(new byte[]{-80, tagb, 127});
                    } else if (tag % 10 == 9) {
                        sendMidiB(new byte[]{-107, (byte) (109 - (tagb / 9)), 127});
                    } else {
                        sendMidiB(new byte[]{-80, tagb, 127});
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else if (tag < 100) {
                if (this.mode == 0) {
                    try {
                        sendMidiB(new byte[]{-112, tagb, 127});
                    } catch (Exception e12) {
                        e12.printStackTrace();
                    }
                } else if (this.mode == 1) {
                    int x = tag % 10;
                    int y = 8 - (tag / 10);
                    int toSend = 0;
                    if (x < 5) {
                        int toSend2 = x + 1 + ((8 - y) * 4);
                        toSend = toSend2 + 30;
                    } else if (x < 9) {
                        int toSend3 = x + 1 + ((8 - y) * 4);
                        toSend = toSend3 + 58;
                    }
                    sendMidiNew(new byte[]{-107, (byte) toSend, 127});
                } else if (this.mode == 6) {
                    int vel = (tag - 11) / 10;
                    int chn = (tag - 11) % 10;
                    sendMidiNew(new byte[]{-80, (byte) (chn + 21), (byte) Calcs.panFaderLookup(vel)});
                } else {
                    int vel2 = (tag - 11) / 10;
                    int chn2 = (tag - 11) % 10;
                    sendMidiNew(new byte[]{-80, (byte) (chn2 + 21), (byte) Calcs.faderLookup(vel2)});
                }
            } else if (tag >= 0 && tag < 121) {
                if (this.lightshow) {
                    int x2 = tag % 16;
                    int y2 = tag / 16;
                    int toSend4 = 0;
                    if (x2 < 4) {
                        int toSend5 = x2 + ((7 - y2) * 4);
                        toSend4 = toSend5 + 36;
                    } else if (x2 < 8) {
                        int toSend6 = x2 - 4;
                        toSend4 = toSend6 + ((7 - y2) * 4) + 68;
                    } else if (x2 == 8) {
                        toSend4 = (y2 - 7) + 107;
                    }
                    try {
                        sendMidiB(new byte[]{-112, (byte) toSend4, 127});
                        Log.d(TAG, String.valueOf("aaaaaaa"));
                    } catch (Exception e13) {
                        e13.printStackTrace();
                    }
                } else {
                    try {
                        sendMidiB(new byte[]{-112, tagb, 127});
                        Log.d(TAG, String.valueOf("aaaaaaa"));
                    } catch (Exception e14) {
                        e14.printStackTrace();
                    }
                }
            } else if (tag >= 200 && tag <= 220) {
                try {
                    sendMidiB(new byte[]{-80, (byte) (tagb - 96), 127});
                } catch (Exception e15) {
                    e15.printStackTrace();
                }
            } else if (tag == 555) {
                try {
                    handshake(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (tag == 300) {
                Log.d(TAG, String.valueOf("300 ye"));
                if (!this.knobs_built) {
                    makeKnobs();
                }
                if (this.knob_open != 1) {
                    initiateKnobWindow();
                }
            } else if (tag == 308) {
                initiatePopupWindow();
            } else if (tag > 299 && tag < 600) {
                byte tagb2 = (byte) (tagb - 207);
                Log.d(TAG, String.valueOf("tagb " + ((int) tagb2)));
                try {
                    if (tagb2 >= 95) {
                        if (!this.transporttoogle[tagb2 - 95]) {
                            sendMidiB(new byte[]{-76, tagb2, 127});
                            this.transporttoogle[tagb2 - 95] = true;
                        } else {
                            sendMidiB(new byte[]{-76, tagb2, 0});
                            this.transporttoogle[tagb2 - 95] = false;
                        }
                    } else if (tag < 600) {
                        sendMidiB(new byte[]{-76, tagb2, 127});
                        sendMidiB(new byte[]{-76, tagb2, 0});
                    }
                } catch (Exception e16) {
                    e16.printStackTrace();
                }
            }
            if (v == this.but[11][0]) {
                try {
                    if (!this.knobs_built) {
                        makeKnobs();
                        Toast.makeText(getApplicationContext(), "Acquiring connection.. pls click again", 0).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL) {
            int tag2 = tag;
            if (tag2 < 10 || ((tag2 > 90 && tag2 < 300) || tag2 % 10 == 0 || tag2 % 10 == 9)) {
                Log.d(TAG, String.valueOf("UP first if"));
                try {
                    if (this.mode == 0) {
                        sendMidiB(new byte[]{-80, (byte) tag2, 0});
                    }
                    if (this.mode == 1) {
                        if (tag2 % 10 == 9) {
                            sendMidiB(new byte[]{-107, (byte) (109 - (tag2 / 9)), 0});
                        } else {
                            sendMidiB(new byte[]{-69, (byte) tag2, 0});
                        }
                    }
                } catch (Exception e17) {
                    e17.printStackTrace();
                }
            } else if (tag2 < 300) {
                if (this.mode == 0) {
                    sendMidiNew(new byte[]{-128, (byte) tag2, 0});
                } else if (this.mode == 1) {
                    int x3 = tag2 % 10;
                    int y3 = 9 - (tag2 / 10);
                    int toSend7 = 0;
                    if (x3 < 5) {
                        int toSend8 = x3 + 1 + ((9 - y3) * 4);
                        toSend7 = toSend8 + 30;
                    } else if (x3 < 9) {
                        int toSend9 = x3 + 1 + ((9 - y3) * 4);
                        toSend7 = toSend9 + 58;
                    }
                    sendMidiNew(new byte[]{-107, (byte) toSend7, 0});
                }
            } else if (tag2 > 300) {
                sendMidiNew(new byte[]{-76, (byte) (tag2 - 207), 0});
            }
        }
        return true;
    }

    private boolean handleUiOnlyTouch(View v, int action) {
        boolean down = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN;
        if (!down) {
            return isUiOnlyControl(v);
        }
        if (v == this.Usb) {
            rest();
            return true;
        }
        if (v == this.Close) {
            this.pwindo.dismiss();
            return true;
        }
        if (v == this.Back) {
            ((ViewGroup) this.layout).removeView(this.TLayout);
            this.kwindo.dismiss();
            this.knob_open = 2;
            return true;
        }
        if (v == this.Quit) {
            moveTaskToBack(true);
            Handler exitHandler = new Handler();
            exitHandler.postDelayed(new Runnable() {
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        LaunchButtonsActivity.this.sendMidiB(new byte[]{6, 6, 6});
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }, 5000L);
            return true;
        }
        return false;
    }

    private boolean isUiOnlyControl(View v) {
        return v == this.Usb
                || v == this.Close
                || v == this.Back
                || v == this.Quit;
    }

    private boolean handleLaunchpadLayoutTouch(int tag, int action) {
        boolean press = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN;
        boolean release = action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL;
        if (!press && !release) {
            return false;
        }
        int velocity = press ? 127 : 0;
        try {
            if (isLaunchpadGridTag(tag)) {
                int status = press ? -112 : -128;
                sendMidiB(new byte[]{(byte) status, (byte) tag, (byte) velocity});
                return true;
            }
            if (isLaunchpadEdgeCcTag(tag)) {
                if (this.mode == 1) {
                    // Programmer/Drum mode: CC on Channel 9
                    if (tag % 10 == 9) {
                        sendMidiB(new byte[]{-107, (byte) (109 - (tag / 9)), (byte) velocity});
                    } else {
                        sendMidiB(new byte[]{-69, (byte) tag, (byte) velocity});
                    }
                } else {
                    // Session/other modes: CC on Channel 0
                    sendMidiB(new byte[]{-80, (byte) tag, (byte) velocity});
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isLaunchpadGridTag(int tag) {
        int x = tag % 10;
        int y = tag / 10;
        return tag >= 11 && tag <= 88 && x >= 1 && x <= 8 && y >= 1 && y <= 8;
    }

    private boolean isLaunchpadEdgeCcTag(int tag) {
        if (tag >= 1 && tag <= 8) {
            return true;
        }
        if (tag >= 91 && tag <= 98) {
            return true;
        }
        int x = tag % 10;
        int y = tag / 10;
        return y >= 1 && y <= 8 && (x == 0 || x == 9);
    }

    public void initiateKnobWindow() {
        try {
            this.knob_open = 1;
            LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
            this.layout = inflater.inflate(R.layout.knobs, (ViewGroup) findViewById(R.id.RelativeKnobLayout));
            float w = (this.height / 6) * 10;
            float h = (this.height / 6) * 3;
            int wa = (int) Math.floor(w);
            int ha = (int) Math.floor(h);
            this.kwindo = new PopupWindow(this.layout, wa, ha, true);
            this.kwindo.showAtLocation(this.layout, 17, 0, 0);
            this.Back = (Button) this.layout.findViewById(R.id.back);
            this.Back.setBackgroundDrawable(null);
            this.Back.setPadding(0, 0, 0, 0);
            this.Back.setOnTouchListener(this);
            this.Back.setTag(125);
            ((ViewGroup) this.layout).addView(this.TLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initiatePopupWindow() {
        if (this.pwindo != null && this.pwindo.isShowing()) {
            return;
        }
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
            View layout = inflater.inflate(R.layout.pop, (ViewGroup) findViewById(R.id.popup_element));
            float w = (float) (((double) this.width) / 1.35d);
            float h = (float) (((double) this.height) / 1.8d);
            int wa = (int) Math.floor(w);
            int ha = (int) Math.floor(h);
            this.pwindo = new PopupWindow(layout, wa, ha, true);
            this.pwindo.showAtLocation(layout, 17, 0, 0);
            this.Close = (Button) layout.findViewById(R.id.textClose);
            this.Quit = (Button) layout.findViewById(R.id.quit);
            this.Close.setBackgroundDrawable(null);
            this.Close.setPadding(0, 0, 0, 0);
            this.Close.setTag(-125);
            this.Quit.setTag(121);
            this.Close.setOnTouchListener(this);
            this.Quit.setOnTouchListener(this);
            this.Usb = (Button) layout.findViewById(R.id.usb);
            this.Usb.setTag(1000);
            this.Usb.setOnTouchListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (this.usbMidiBridge != null) {
            this.usbMidiBridge.close();
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.usbMidiBridge != null && this.usbMidiBridge.isSupported()) {
            this.mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!LaunchButtonsActivity.this.isFinishing() && LaunchButtonsActivity.this.spinner != null) {
                        LaunchButtonsActivity.this.rest();
                    }
                }
            }, 300L);
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item01 /* 2131230750 */:
                rest();
                return true;
            case R.id.item02 /* 2131230751 */:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void midiSystemLoad(final Context ctx) {
        final Context appContext = ctx.getApplicationContext();
        java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
            final UsbMidiBridge bridge = new UsbMidiBridge(appContext);
            bridge.setPacketListener(LaunchButtonsActivity.this);
            this.mHandler.post(() -> {
                if (!isFinishing() && !isDestroyed()) {
                    this.usbMidiBridge = bridge;
                    this.buildUI();
                }
            });
        });
    }

    public void midiReceived2(int channel, int ssrc, byte[] data, long timestamp) {
        Message msg = Message.obtain();
        msg.what = 0;
        Bundle b = new Bundle();
        b.putByteArray("MIDI", data);
        b.putInt("CH", channel);
        msg.setData(b);
        this.midiLogger.sendMessage(msg);
    }

    public void midiReceived(int channel, int ssrc, byte[] data, long timestamp) {
        if (data == null || data.length == 0) {
            return;
        }
        Message msg = Message.obtain();
        Bundle b = new Bundle();
        b.putByteArray("MIDI", data);
        if (data.length >= 6 && data[0] == -16 && data[1] == 126 && data[2] == 127 && data[3] == 6 && data[4] == 1 && data[5] == -9) {
            Log.d(TAG, String.valueOf("REQUEST"));
            Log.d(TAG, String.valueOf("REQUEST"));
            Log.d(TAG, String.valueOf("REQUEST"));
            Log.d(TAG, String.valueOf("REQUEST"));
            handshake(0);
        }
        if (data.length >= 12 && data[0] == -16 && data[1] == 0 && data[2] == 32 && data[3] == 41 && data[4] == 2 && data[5] == 16 && data[6] == 64 && data[7] == 0 && data[8] == 0 && data[9] == 0 && data[10] == 0 && data[11] == -9) {
            handshake(1);
        }
        if (data.length >= 3) {
            int status = data[0] & 255;
            int hi = status & 240;
            if (hi == 128 || hi == 144 || hi == 176) {
                applyDirectLightFeedback(status, data[1] & 255, data[2] & 255);
                eightoeight(status, data[1] & 255, data[2] & 255);
            }
        }
        if (data.length >= 10 && data[0] == -16 && data[1] == 0 && data[2] == 32 && data[3] == 41 && data[4] == 2 && data[5] == 16) {
            Log.d(TAG, String.valueOf("MODE CHANGE"));
            if (data[6] == 44 && data[7] == 1) {
                Log.d(TAG, String.valueOf(this.mode + "d"));
                this.mode = 0;
                Log.d(TAG, String.valueOf(this.mode + "z"));
            }
            if (data[6] == 34 && data[7] == 3) {
                this.mode = 1;
                userMode(0);
                Log.d(TAG, String.valueOf("MODE 1"));
                resetButtons(this.mode);
            }
            if (data[6] == 46 && data[7] == 0) {
                this.mode = 1;
                userMode(0);
                Log.d(TAG, String.valueOf("MODE 1"));
                resetButtons(this.mode);
            }
            if (data[6] == 34 && data[7] == 0) {
                this.mode = 0;
                Log.d(TAG, String.valueOf("MODE NUL normal"));
            }
            if (data[6] == 43 && data[7] == 1 && data[8] == 0 && data[9] == 21) {
                this.mode = 5;
                Log.d(TAG, String.valueOf("MODE VOL volume1"));
            }
            if (data[6] == 43 && data[7] == 0) {
                if (data[8] == 0 && data[9] == 21) {
                    this.mode = 5;
                    resetButtons(this.mode);
                    Log.d(TAG, String.valueOf("MODE VOL volume2"));
                }
                if (data[8] == 1 && data[9] == 9) {
                    this.mode = 6;
                    resetButtons(this.mode);
                    Log.d(TAG, String.valueOf("MODE PAN panning"));
                }
                if (data[8] == 0 && data[9] < 50 && data[9] % 4 == 1 && data[9] != 21 && data[9] != 25) {
                    this.mode = 7;
                    resetButtons(this.mode);
                    Log.d(TAG, String.valueOf("array mode"));
                    switch (data[9]) {
                        case 5:
                            this.sends = 0;
                            break;
                        case 9:
                            this.sends = 1;
                            break;
                        case 13:
                            this.sends = 2;
                            break;
                        case 17:
                            this.sends = 3;
                            break;
                        case 29:
                            this.sends = 4;
                            break;
                        case 37:
                            this.sends = 5;
                            break;
                        case 45:
                            this.sends = 6;
                            break;
                        case 49:
                            this.sends = 7;
                            break;
                    }
                    int color = Color.HSVToColor(new float[]{(this.sends / 9.0f) * 360.0f, 1.0f, 1.0f});
                    if (8 - this.sends == 6) {
                        color = Color.HSVToColor(new float[]{59.976f, 1.0f, 1.0f});
                    }
                    final int c = color;
                    this.mHandler.post(new Runnable() {
                        @Override // java.lang.Runnable
                        public void run() {
                            LaunchButtonsActivity.this.but[9][7 - LaunchButtonsActivity.this.sends].setBackgroundColor(c);
                        }
                    });
                }
            }
        }
        msg.setData(b);
        this.midiLogger.sendMessage(msg);
    }

    @Override
    public void onMidiPacket(byte[] data, long timestamp) {
        if (data == null || data.length == 0) {
            return;
        }
        Log.d(TAG_MIDI_IN, "rx bytes=" + bytesToHex(data));
        int i = 0;
        int runningStatus = 0;
        while (i < data.length) {
            int status = data[i] & 255;
            int msgLen;

            if (status < 128) {
                if (runningStatus == 0) {
                    i++;
                    continue;
                }
                int hiRun = runningStatus & 240;
                if (hiRun == 192 || hiRun == 208) {
                    byte[] msg = new byte[]{(byte) runningStatus, data[i]};
                    midiReceived(0, 0, msg, timestamp);
                    i++;
                    continue;
                }
                if (i + 1 < data.length && (data[i + 1] & 128) == 0) {
                    byte[] msg = new byte[]{(byte) runningStatus, data[i], data[i + 1]};
                    midiReceived(0, 0, msg, timestamp);
                    i += 2;
                } else {
                    i++;
                }
                continue;
            }

            if (status >= 248) {
                msgLen = 1;
            } else if (status >= 240) {
                runningStatus = 0;
                if (status == 240) {
                    int end = i + 1;
                    while (end < data.length && (data[end] & 255) != 247) {
                        end++;
                    }
                    if (end < data.length) {
                        end++;
                    }
                    msgLen = end - i;
                } else if (status == 241 || status == 243) {
                    msgLen = 2;
                } else if (status == 242) {
                    msgLen = 3;
                } else {
                    msgLen = 1;
                }
            } else {
                runningStatus = status;
                int hi = status & 240;
                msgLen = (hi == 192 || hi == 208) ? 2 : 3;
            }

            if (msgLen <= 0 || i + msgLen > data.length) {
                break;
            }
            byte[] msg = Arrays.copyOfRange(data, i, i + msgLen);
            midiReceived(0, 0, msg, timestamp);
            i += msgLen;
        }
    }

    private void applyDirectLightFeedback(int status, int noteOrCc, int value) {
        int hi = status & 240;
        int x = noteOrCc % 10;
        int y = 8 - (noteOrCc / 10);
        int color = (hi == 128 || (hi == 144 && value == 0)) ? 0 : value;

        if (noteOrCc >= 11 && noteOrCc <= 88 && x >= 1 && x <= 8) {
            setButtonBackground(x, y, color);
            return;
        }

        if (noteOrCc >= 1 && noteOrCc <= 8) {
            setButtonBackground(11, noteOrCc - 1, color);
            return;
        }

        if (noteOrCc >= 91 && noteOrCc <= 98) {
            setButtonBackground(10, noteOrCc - 91, color);
        }
    }

    void handshake(int stage) {
        switch (stage) {
            case 0:
                try {
                    Log.d(TAG, String.valueOf("handshake 0"));
                    sendMidiB(new byte[]{-16, 126, 0, 6, 2, 0, 32, 41, 81, 0, 0, 0, 0, 1, 5, 4, -9});
                } catch (Exception e1) {
                    e1.printStackTrace();
                    return;
                }
                break;
            case 1:
                try {
                    Log.d(TAG, String.valueOf("handshake 1"));
                    sendMidiB(new byte[]{-16, 0, 32, 41, 2, 16, 64, 47, 72, -9});
                    sendMidiB(new byte[]{-16, 0, 32, 41, 2, 16, 45, 0, -9});
                    sendMidiB(new byte[]{-16, 0, 32, 41, 2, 16, 46, 0, -9});
                } catch (Exception e12) {
                    e12.printStackTrace();
                    return;
                }
                break;
            case 2:
                try {
                    Log.d(TAG, String.valueOf("handshake 0"));
                    sendMidiB(new byte[]{-16, 0, 32, 41, 2, 16, 45, 0, -9});
                } catch (Exception e13) {
                    e13.printStackTrace();
                    return;
                }
                break;
        }
    }

    public void eightoeight(int s, int n, int c) {
        int hi = s & 240;
        int channel = s & 15;
        int x = n % 10;
        int y = 8 - (n / 10);
        if (this.mode == 1) {
            if (n > 35 && n < 68) {
                int n2 = n - 36;
                x = (n2 % 4) + 1;
                y = 7 - (n2 / 4);
            } else if (n < 100) {
                int n3 = n - 68;
                x = (n3 % 4) + 5;
                y = 7 - (n3 / 4);
            } else if (n < 108) {
                y = n - 99;
                x = 9;
            } else if (n < 116) {
                y = n - 107;
                x = 0;
            } else if (n < 124) {
                y = 9;
                x = n - 116;
            }
            if (hi == 128 || (hi == 144 && c == 0)) {
                setButtonBackground(x, y, 0);
                return;
            } else {
                setButtonBackground(x, y, c);
                return;
            }
        }
        if (this.mode == 0) {
            Log.d(TAG, String.valueOf("mod0    s"));
            if (hi == 176) {
                if (n > 90) {
                    setButtonBackground(10, n - 91, c);
                } else if (n < 10) {
                    setButtonBackground(11, n - 1, c);
                } else {
                    setButtonBackground(x, y, c);
                }
            }
            Log.d(TAG, String.valueOf("mod1    s"));
            if (hi == 144 && c > 0 && x < 12 && y < 12 && x >= 0 && y >= 0) {
                setButtonBackground(x, y, c);
            }
            Log.d(TAG, String.valueOf("mod2    s"));
            if (x > 0 && y > 0) {
                if (hi == 144 && channel == 1) {
                    this.blinks[x][y] = 1;
                } else if (hi == 144 && channel == 2) {
                    this.blinks[x][y] = 2;
                } else {
                    this.blinks[x][y] = 0;
                }
            }
            if (hi == 128 || (hi == 144 && c == 0)) {
                setButtonBackground(x, y, 0);
                return;
            }
            return;
        }
        if (this.mode == 5) {
            if (n > 20 && n < 29) {
                boolean[] barLevel = Arrays.copyOf(Calcs.barLookUp(c), 10);
                for (int bj = 0; bj < 8; bj++) {
                    if (barLevel[bj]) {
                        setButtonBackground(n - 20, 7 - bj, 21);
                    } else {
                        setButtonBackground(n - 20, 7 - bj, 0);
                    }
                }
                return;
            }
            return;
        }
        if (this.mode == 6) {
            if (n > 20 && n < 29) {
                boolean[] sliderHeight = Arrays.copyOf(Calcs.sliderLookUp(c), 10);
                for (int bj2 = 0; bj2 < 8; bj2++) {
                    if (sliderHeight[bj2]) {
                        setButtonBackground(n - 20, 7 - bj2, 9);
                    } else {
                        setButtonBackground(n - 20, 7 - bj2, 0);
                    }
                }
                return;
            }
            return;
        }
        if (this.mode == 7 && n > 20 && n < 29) {
            boolean[] barHeight = Arrays.copyOf(Calcs.barLookUp(c), 10);
            for (int bj3 = 0; bj3 < 8; bj3++) {
                if (barHeight[bj3]) {
                    setButtonHue(n - 20, 7 - bj3);
                } else {
                    setButtonBackground(n - 20, 7 - bj3, 0);
                }
            }
        }
    }

    public void setButtonBackground2(final int x, final int y, final int ve) {
        updateButtonBackground(x, y, ve);
    }

    public void setButtonBackground(final int x, final int y, final int ve) {
        updateButtonBackground(x, y, ve);
    }

    public void setButtonHue(final int x, final int y) {
        int color = Color.HSVToColor(new float[]{(this.sends / 9.0f) * 360.0f, 1.0f, 1.0f});
        if (8 - this.sends == 6) {
            color = Color.HSVToColor(new float[]{59.976f, 1.0f, 1.0f});
        }
        final int c = color;
        cacheButtonColor(x, y, c);
        this.mHandler.post(new Runnable() {
            @Override // java.lang.Runnable
            public void run() {
                LaunchButtonsActivity.this.but[x][y].setBackgroundColor(c);
            }
        });
    }

    public void userMode(int m) {
        switch (m) {
            case 0:
                sendMidiNew(new byte[]{-16, 0, 32, 41, 2, 16, 46, 0, -9});
                break;
        }
    }

    public void resetButtons(int m) {
        int xMin = 1;
        int xMax = 9;
        int yMin = 1;
        int yMax = 9;
        if (m == 1) {
            xMin = 0;
            xMax = 10;
            yMin = 0;
            yMax = 10;
        }
        for (int y = yMin; y < yMax; y++) {
            for (int x = xMin; x < xMax; x++) {
                int xx = x;
                int yy = y;
                setButtonBackground(xx, yy - 1, 0);
                this.blinks[x][y] = 0;
            }
        }
    }

    private class MidiLogger extends Handler {
        private StringBuffer sb;
        private TextView tv;

        private MidiLogger(TextView tv) {
            this.sb = new StringBuffer();
            this.tv = tv;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bundle b = msg.getData();
                    this.sb.delete(0, this.sb.length());
                    byte[] data = b.getByteArray("MIDI");
                    this.sb.append("MIDI received: ");
                    for (byte b2 : data) {
                        this.sb.append(String.format("%X", Integer.valueOf(b2 & 255)) + " ");
                    }
                    this.sb.append("\n");
                    this.tv.setText(this.sb.toString());
                    break;
                case 1:
                    LaunchButtonsActivity.this.findViewById(R.id.Button01).setEnabled(false);
                    break;
            }
        }
    }

    public void systemChanged(int channel, int property, int value) {
        Log.i(TAG, " System changed " + channel + " " + property + " " + value);
        if (channel >= 0 && ((property == 4 && value == 8) || property == -1)) {
            rest();
        }
    }

    public void sendMidiNew(byte[] data) {
        try {
            sendMidiB(data);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void sendMidiB(byte[] data) throws Exception {
        if (!isSendableMidi(data)) {
            return;
        }

        if (this.usbMidiBridge != null && this.usbMidiBridge.isSupported()) {
            try {
                if (!this.usbMidiBridge.canSend()) {
                    this.usbMidiBridge.reopenBestTxTarget(this.lastPosition);
                }
                this.usbMidiBridge.sendMidi(data);
                Log.d(TAG_MIDI_OUT, "tx via usb bridge bytes=" + bytesToHex(data));
                onTxResult(true, null);
                return;
            } catch (Exception firstError) {
                boolean reopened = this.usbMidiBridge.reopenBestTxTarget(this.lastPosition);
                if (!reopened) {
                    reopened = this.usbMidiBridge.reopenBestTxTarget(-1);
                }
                if (reopened) {
                    try {
                        this.usbMidiBridge.sendMidi(data);
                        Log.d(TAG_MIDI_OUT, "tx via usb bridge retry bytes=" + bytesToHex(data));
                        onTxResult(true, null);
                        return;
                    } catch (Exception retryError) {
                        Log.e(TAG_MIDI_OUT, "usb bridge retry tx failed bytes=" + bytesToHex(data), retryError);
                        onTxResult(false, retryError);
                        throw retryError;
                    }
                }
                Log.e(TAG_MIDI_OUT, "usb bridge tx failed bytes=" + bytesToHex(data), firstError);
                onTxResult(false, firstError);
                throw firstError;
            }
        }
        IllegalStateException noRoute = new IllegalStateException("No MIDI Tx route available");
        Log.e(TAG_MIDI_OUT, "tx failed: no route bytes=" + bytesToHex(data), noRoute);
        onTxResult(false, noRoute);
        throw noRoute;
    }

    private boolean isSendableMidi(byte[] data) {
        if (data == null || data.length == 0) {
            return false;
        }
        if (data.length == 3 && data[0] == 6 && data[1] == 6 && data[2] == 6) {
            return false;
        }
        int status = data[0] & 255;
        if (status < 128) {
            return false;
        }
        if (status == 240) {
            return data[data.length - 1] == -9;
        }
        if (status >= 248) {
            return data.length == 1;
        }
        int high = status & 240;
        if (high == 192 || high == 208) {
            return data.length == 2;
        }
        if (high >= 128 && high <= 224) {
            return data.length == 3;
        }
        return true;
    }

    private void onTxResult(final boolean ok, final Exception error) {
        this.mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (ok) {
                    LaunchButtonsActivity.this.txOkCount++;
                } else {
                    LaunchButtonsActivity.this.txFailCount++;
                }
                TextView tv = (TextView) LaunchButtonsActivity.this.findViewById(R.id.TextView01);
                if (tv != null) {
                    if (ok) {
                        tv.setText("Output OK: " + LaunchButtonsActivity.this.txOkCount + " | Fail: " + LaunchButtonsActivity.this.txFailCount);
                    } else {
                        String reason = error == null ? "unknown" : error.getClass().getSimpleName();
                        tv.setText("Output FAIL (" + reason + ") | OK: " + LaunchButtonsActivity.this.txOkCount + " Fail: " + LaunchButtonsActivity.this.txFailCount);
                    }
                }
            }
        });
    }

    public void systemError(int channel, int err, String description) {
        Log.e(TAG, " Error on ch " + channel + ", code: " + err + ", desc: " + description);
    }

    public void rest() {
        if (this.usbMidiBridge == null || !this.usbMidiBridge.isSupported()) {
            if (this.Usb != null) {
                this.Usb.setText("USB MIDI unsupported");
            }
            return;
        }
        if (this.spinner == null) {
            return;
        }
        if (this.Usb != null) {
            this.Usb.setText("USB MIDI scanning...");
        }
        String[] outputs = this.usbMidiBridge.getOutputDisplayNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, outputs.length == 0 ? new String[]{"No USB MIDI output"} : outputs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter((SpinnerAdapter) adapter);
        if (outputs.length > 0) {
            int recommended = this.usbMidiBridge.getRecommendedTargetIndex();
            int targetIndex = recommended < 0 ? 0 : recommended;
            boolean connected = this.usbMidiBridge.openOutputByIndex(targetIndex);
            this.sBB.midiOut = this.usbMidiBridge;
            this.spinner.setSelection(targetIndex);
            if (this.Usb != null) {
                this.Usb.setText(connectionStateText(connected));
            }
        } else if (this.Usb != null) {
            this.Usb.setText("No USB MIDI device found");
        }
    }

    private String connectionStateText(boolean connected) {
        if (!connected || this.usbMidiBridge == null) {
            return "MIDI open failed";
        }
        boolean tx = this.usbMidiBridge.canSend();
        boolean rx = this.usbMidiBridge.canReceive();
        if (!tx) {
            return "MIDI open failed (no Output endpoint)";
        }
        if (tx && rx) {
            return "Connected (Input/Output)";
        }
        if (tx) {
            return "Connected (Output only)";
        }
        return rx ? "Connected (Input only - open host output for Output)" : "Connected";
    }

    private String bytesToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(String.format("%02X", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }

    private void updateButtonBackground(final int x, final int y, final int ve) {
        if (x < 0 || y < 0 || x > 12 || y > 8) {
            return;
        }
        final int color = Colors.col(ve);
        if (!shouldUpdateColor(x, y, color)) {
            return;
        }
        this.mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (LaunchButtonsActivity.this.but[x][y] != null) {
                    LaunchButtonsActivity.this.but[x][y].setBackgroundColor(color);
                }
            }
        });
    }

    private synchronized boolean shouldUpdateColor(int x, int y, int color) {
        String key = x + ":" + y;
        Integer current = this.buttonColorCache.get(key);
        if (current != null && current.intValue() == color) {
            return false;
        }
        this.buttonColorCache.put(key, Integer.valueOf(color));
        return true;
    }

    private synchronized void cacheButtonColor(int x, int y, int color) {
        this.buttonColorCache.put(x + ":" + y, Integer.valueOf(color));
    }
}
