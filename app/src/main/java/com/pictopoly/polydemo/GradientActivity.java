package com.pictopoly.polydemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pictopoly.polydemo.filter.GradientMaker;
import com.pictopoly.polydemo.process.ImageProcessor;
import com.pictopoly.polydemo.process.NotifyingRunnable;
import com.pictopoly.polydemo.process.PointMaker.PointMaker;
import com.pictopoly.polydemo.process.PointMaker.UniformPointMaker;
import com.pictopoly.polydemo.process.ThreadCompleteListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marklar on 3/15/2015.
 */
public class GradientActivity extends Activity implements ThreadCompleteListener {
    private final String TAG = this.getClass().getSimpleName();
    protected ImageProcessor processor;
    protected TriangleSurfaceView mTriangleSurfaceView;
    protected int[] gradActivityBackgroundColors = new int[] { Color.parseColor("#ffffff"), Color.parseColor("#aaaaaa"), };
    protected int mWidth, mHeight;

    protected int[] gradientColors = new int[5];
    protected List<String> gradientActivityColorIds;        // The Id's for the colors in activity_gradient.xml
    private final int numberOfColorsPerColorGroup = 5;
    private final int numberOfColorGroups = 7;
    private final int numberOfColors = 5;
    private final String idBase = "color_";
    protected int currentColor, currentColorPosition, gradientWidth, gradientHeight;
    protected boolean isPortrait;
    protected PointMaker pm;
    protected View lastPickedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient);
        processor = ImageLayerHandler.getInstance().getGradientProcessor();
        mTriangleSurfaceView = (TriangleSurfaceView) findViewById(R.id.grad_triangleSurfaceView);

        // Set up the Color Buttons
        initGradientActivityColorIds();
        initDefaults();
        setColorListeners();
        setDirectionListeners();
        hideColorPicker();
        setAddColorListeners();
        setColorPickerVisibilityListeners();
        setColorPickerButtonListeners();
        setMaterialTypeFaces();

        // First Time Running SplashActivity
        if (savedInstanceState == null && processor.getProcessedImage() == null) {
            Bitmap gradient = GradientMaker.makeGradient(1000, 1000, this.gradActivityBackgroundColors, true);
            processor.setImage(gradient);
            PointMaker pm = new UniformPointMaker();
            processor.setPointMaker(pm);
            processor.addListener(this);
            processor.processImage();
        }

        if (mTriangleSurfaceView != null && processor.getProcessedImage() != null) {
            mTriangleSurfaceView.setFillingScreen(true);
            mTriangleSurfaceView.setImageHandler(processor);
        } else {
            Log.d(TAG, "TriangleSurfaceView not Persisting");
        }
    }

    @Override
    public void notifyThreadComplete(final Runnable runnable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTriangleSurfaceView != null) {
                    mTriangleSurfaceView.setFillingScreen(true);
                    mTriangleSurfaceView.setImage(processor.processImage());
                    mTriangleSurfaceView.invalidate();
                }
            }
        });
    }

    private void initGradientActivityColorIds() {
        int numIds = numberOfColorGroups*numberOfColorsPerColorGroup;
        gradientActivityColorIds = new ArrayList<>(numIds);

        String temp;
        for(int colorGroup = 1; colorGroup <= numberOfColorGroups; colorGroup++) {
            for(int color = 1; color <= numberOfColorsPerColorGroup; color++) {
                temp = idBase + colorGroup;
                temp = temp + "_" + color;
                gradientActivityColorIds.add(temp);
            }
        }
    }

    private void setColorListeners() {
        for(String s : gradientActivityColorIds) {
            int resID = getResources().getIdentifier(s, "id", "com.pictopoly.polydemo");
            final View v = findViewById(resID);
            if(v != null & v instanceof Button) {
                ((Button)v).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chooseColor(view);
                    }
                });
            }
        }
    }

    private void setCurrentColor(int color) {
        this.currentColor = color;
    }

    private int getCurrentColor() {
        return this.currentColor;
    }

    private void setCurrentColorPosition(int color) {
        this.currentColorPosition = color;
    }

    private int getCurrentColorPosition() {
        return this.currentColorPosition;
    }

    private void chooseColor(View view) {
        if(lastPickedView != null) {
            if(lastPickedView instanceof TextView)
                ((TextView)lastPickedView).setText("");
        }

        int currColor = Color.parseColor((String)view.getTag());

        if(view instanceof TextView) {
            TextView tv = (TextView)view;
            tv.setText(R.string.md_done);
            if(currColor == Color.WHITE)
                tv.setTextColor(Color.BLACK);
            else
                tv.setTextColor(Color.WHITE);
        }

        setCurrentColor(currColor);

        lastPickedView = view;
    }

    private void hideColorPicker() {
        View v = findViewById(R.id.grad_pick_color);
        if(v != null)
            v.setVisibility(View.INVISIBLE);
    }

    private void showColorPicker() {
        View v = findViewById(R.id.grad_pick_color);
        if(v != null)
            v.setVisibility(View.VISIBLE);
    }

    private void assignCurrentColor() {
        this.gradientColors[this.currentColorPosition] = this.currentColor;
        int colorEl = currentColorPosition + 1;
        String id = idBase + colorEl;
        int resID = getResources().getIdentifier(id, "id", "com.pictopoly.polydemo");
        View v = findViewById(resID);
        if(v != null) {
            GradientDrawable drawable = (GradientDrawable) v.getBackground();
            drawable.setColor(currentColor);

            if(v instanceof TextView) {
                TextView textView = (TextView)v;
                if (currentColor == Color.parseColor("#ffffff"))
                    textView.setTextColor(Color.parseColor("#000000"));
                else
                    textView.setTextColor(Color.parseColor("#ffffff"));

                textView.setText(R.string.md_done);
            }
        }
    }

    private void setAddColorListeners() {
        String s;
        for(int colorEl = 1; colorEl <= numberOfColors; colorEl++) {
            s = idBase + colorEl;
            final int colorPosition = colorEl - 1;
            int resID = getResources().getIdentifier(s, "id", "com.pictopoly.polydemo");
            View v = findViewById(resID);
            if(v != null && v instanceof Button) {
                ((Button)v).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setCurrentColorPosition(colorPosition);
                        showColorPicker();
                    }
                });
            }
        }
    }

    private void setColorPickerVisibilityListeners() {
        int[] hideColorPickerIds = new int[] {R.id.grad_pick_color_ok_button, R.id.grad_pick_color_cancel_button,};
        int[] showColorPickerIds = new int[] {R.id.grad_pick_color,};

        for(int i : hideColorPickerIds)
            setColorPickerVisibilityListener(i, false);

        for(int i : showColorPickerIds)
            setColorPickerVisibilityListener(i, true);
    }

    private void setColorPickerVisibilityListener(int id, final boolean makeVisible) {
        View v = findViewById(id);
        if(v != null)
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(makeVisible) showColorPicker();
                    else hideColorPicker();
                }
            });
    }

    private void setColorPickerButtonListeners() {
        View v = findViewById(R.id.grad_pick_color_ok_button);
        if(v != null)
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assignCurrentColor();
                    hideColorPicker();
                }
            });
    }

    private void setMaterialTypeFaces() {
        Typeface materialTypeface = Typeface.createFromAsset(getAssets(), "fonts/material_design_icons.ttf");
//        int[] materialTextIds = new int[] {R.id.grad_close_image,
//                R.id.color_1,
//                R.id.color_2,
//                R.id.color_3,
//                R.id.color_4,
//                R.id.color_5,};

        List<Integer> mTextIds = new ArrayList<>();
        mTextIds.add(R.id.grad_close_image);
        mTextIds.add(R.id.grad_small_triangle);
        mTextIds.add(R.id.grad_large_triangle);
        mTextIds.add(R.id.color_1);
        mTextIds.add(R.id.color_2);
        mTextIds.add(R.id.color_3);
        mTextIds.add(R.id.color_4);
        mTextIds.add(R.id.color_5);

        String temp;
        for(int colorGroup = 1; colorGroup <= numberOfColorGroups; colorGroup++) {
            for(int color = 1; color <= numberOfColorsPerColorGroup; color++) {
                temp = idBase + colorGroup;
                temp = temp + "_" + color;
                int resID = getResources().getIdentifier(temp, "id", "com.pictopoly.polydemo");
                mTextIds.add(resID);
            }
        }

        for(int i : mTextIds) {
            View v = findViewById(i);
            if(v != null && v instanceof TextView)
                ((TextView) v).setTypeface(materialTypeface);
        }
    }

    private void initDefaults() {
        initResolution();
        initColors();
        initTriangleSize();
        initDirection();
    }

    private void initResolution() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        View v = findViewById(R.id.grad_width_edit_text);
        if(v != null && v instanceof EditText)
            ((EditText)v).setText("" + width);
        v = findViewById(R.id.grad_height_edit_text);
        if(v != null && v instanceof EditText)
            ((EditText)v).setText("" + height);

        this.gradientWidth = width;
        this.gradientHeight = height;
    }

    private void initColors() {
        for(int i = 0; i < gradientColors.length; i++)
            gradientColors[i] = 0;
    }

    private int[] fixColors() {
        int numColors = 0;
        for(int i : gradientColors)
            if(i != 0)
                numColors++;


        int[] colors = new int[numColors];
        int temp = 0;
        for(int i : gradientColors)
            if(i != 0)
                colors[temp++] = gradientColors[i];

        return colors;
    }

    private void initTriangleSize() {
        pm = new UniformPointMaker();
        updateNumPoints(50);
    }

    private void updateNumPoints(int i) {
        int temp = 110 - i;
        temp *= 5;

        ((UniformPointMaker)pm).setNumberOfPoints(temp);

        ImageProcessor processor = ImageLayerHandler.getInstance().getProcessor();
        processor.setPointMaker(pm);
    }

    private void initDirection() {
        isPortrait = true;
    }

    private void setDirectionListeners() {
        RadioGroup group=(RadioGroup) findViewById(R.id.grad_direction);
        final RadioButton verticalRadio = (RadioButton)findViewById(R.id.grad_direction_vertical_radio);
        final RadioButton horizontalRadio = (RadioButton)findViewById(R.id.grad_direction_horizontal_radio);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(verticalRadio.isChecked()) {
                    GradientActivity.this.isPortrait = true;
                    Log.d(TAG,"Vertical");
                }
                else if(horizontalRadio.isChecked()) {
                    GradientActivity.this.isPortrait = false;
                    Log.d(TAG,"Horizontal");
                }
            }
        });
    }
}
