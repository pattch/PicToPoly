package com.pictopoly.polydemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pictopoly.polydemo.filter.GradientMaker;
import com.pictopoly.polydemo.nav.CloseActivityNavigationElement;
import com.pictopoly.polydemo.nav.CloseImageIntentNavigationElement;
import com.pictopoly.polydemo.nav.NavigationElement;
import com.pictopoly.polydemo.process.ImageProcessor;
import com.pictopoly.polydemo.process.PointMaker.PointMaker;
import com.pictopoly.polydemo.process.PointMaker.UniformPointMaker;
import com.pictopoly.polydemo.process.ThreadCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class GradientActivity extends Activity implements ThreadCompleteListener {
    private final String TAG = this.getClass().getSimpleName();
    protected ImageProcessor gradientProcessor;
    protected TriangleSurfaceView mTriangleSurfaceView;
    protected int[] gradActivityBackgroundColors = new int[] { Color.parseColor("#ffffff"), Color.parseColor("#aaaaaa"), };
//    protected int mWidth, mHeight;

    // Array of Colors to be used in Gradient
    protected int[] gradientColors = new int[5];

    // Orientation of the Gradient
    protected boolean isPortrait = true;

    // PointMaker to be used to make the Gradient, uses Triangle Size
    protected PointMaker pm;

    // The dimensions of the Gradient to be made
    protected int gradientWidth, gradientHeight;

    protected List<String> gradientActivityColorIds;        // The Id's for the colors in activity_gradient.xml
    private final int numberOfColorsPerColorGroup       = 5;
    private final int numberOfColorGroups               = 7;
    private final int numberOfColors                    = 5;
    private final int PointScale                        = 5;
    private final int MaxPointCount                     = 110;
    private final int DefaultTriangleSize               = 50;
    private final int minimumAmountOfColorsInGradient   = 2;
    private final int minimumGradientWidth              = 2;
    private final int minimumGradientHeight             = 2;
    private final String idBase = "color_";
    protected int currentColor, currentColorPosition, backgroundGradientWidth, backgroundGradientHeight, triangleSize;
    protected View lastPickedView;

    protected NavigationElement closeGradient = new CloseActivityNavigationElement(R.id.grad_close_image);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient);
        gradientProcessor = ImageLayerHandler.getInstance().getGradientProcessor();
        mTriangleSurfaceView = (TriangleSurfaceView) findViewById(R.id.grad_triangleSurfaceView);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        String colors = "Colors: ";
//        for(int colorEl = 0; colorEl < numberOfColors; colorEl++)
//            colors += gradientColors[colorEl] + " ";
//        Log.d(TAG, colors);

        // Set up the Color Buttons
        initDefaults();
        setListeners();

        // First Time Running GradientActivity
        if (savedInstanceState == null && gradientProcessor.getProcessedImage() == null) {
            Bitmap gradient = GradientMaker.makeGradient(this.backgroundGradientWidth, this.backgroundGradientHeight, this.gradActivityBackgroundColors, true);
            gradientProcessor.setImage(gradient);
            PointMaker gradientPointMaker = new UniformPointMaker();
            gradientProcessor.setPointMaker(gradientPointMaker);
            gradientProcessor.addListener(this);
            gradientProcessor.processImage();
        }

        if (mTriangleSurfaceView != null && gradientProcessor.getProcessedImage() != null) {
            mTriangleSurfaceView.setFillingScreen(true);
            mTriangleSurfaceView.setImageHandler(gradientProcessor);
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
                    mTriangleSurfaceView.setImage(gradientProcessor.processImage());
                    mTriangleSurfaceView.invalidate();
                }
            }
        });
    }

    private void setListeners() {
        setColorListeners();
        setDirectionListeners();
        setAddColorListeners();
        setColorPickerVisibilityListeners();
        setColorPickerButtonListeners();
        setTriangleSizeListener();
        setDimensionListener();
        setApplyListener();

        View view = findViewById(closeGradient.getId());
        closeGradient.setView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeGradient.onClick(v);
            }
        });
    }

    /**
     * Sets up the List gradientActivityColorIds. Uses the constants numberOfColorGroups and
     * numberOfColorsPerColorGroup to build up Strings that look like "color_1_1" to "color_m_n"
     * where m = numberOfColorGroups, n = numberOfColorsPerColorGroup. These Strings are to be used
     * to get the Color, as a String of the Views in the color picker. This is done by accessing the Tag
     * field of the views with the Ids built here.
     */
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

    private void setDimensionListener() {
        setWidthListener();
        setHeightListener();
    }

    private void setWidthListener() {
        View widthView = findViewById(R.id.grad_width_edit_text);
        if(widthView != null && widthView instanceof EditText) {
            final EditText widthText = (EditText)widthView;
            widthText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String text = s.toString();
                    int width;
                    try {
                        width = Integer.parseInt(text);
                        gradientWidth = width;
                    } catch (NumberFormatException e) {
                        Log.d(TAG, "Width not a number. gradientWidth: " + gradientWidth);
                        Toast.makeText(GradientActivity.this, R.string.grad_width_not_number_error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setHeightListener() {
        View heightView = findViewById(R.id.grad_height_edit_text);
        if(heightView != null && heightView instanceof EditText) {
            final EditText heightText = (EditText)heightView;
            heightText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String text = s.toString();
                    int height;
                    try {
                        height = Integer.parseInt(text);
                        gradientHeight = height;
                    } catch (NumberFormatException e) {
                        Log.d(TAG, "Height not a number. gradientHeight: " + gradientHeight);
                        Toast.makeText(GradientActivity.this, R.string.grad_height_not_number_error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
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

    /**
     * Sets the current color (currentColor) to the value given by the Tag in the View colorView
     *
     * @param colorView     The View with the color chosen by the user.
     */
    private void chooseColor(View colorView) {
        if(lastPickedView != null) {
            if(lastPickedView instanceof TextView)
                ((TextView)lastPickedView).setText("");
        }

        int currColor = Color.parseColor((String)colorView.getTag());

        if(colorView instanceof TextView) {
            TextView tv = (TextView)colorView;
            tv.setText(R.string.md_done);

            // These color options are too light, so set the text to black.
            int[] lightColors = new int[] {
                    Color.WHITE,
                    Color.parseColor("#FFFF8D"),    // Not magic at all
                    Color.parseColor("#F4FF81"),
                    Color.parseColor("#E0E0E0"),
                    Color.parseColor("#B9F6CA"),
                    Color.parseColor("#FFEB3B"),
                    Color.parseColor("#FFD180"),
                    Color.parseColor("#CDDC39"),
            };
            tv.setTextColor(Color.WHITE);
            for(int lc : lightColors)
                if(currColor == lc)
                    tv.setTextColor(Color.BLACK);
        }

        setCurrentColor(currColor);

        lastPickedView = colorView;
    }

    /**
     * Updates the number of points in the pointMaker to be used in the Gradient
     */
    private void setTriangleSizeListener() {
        SeekBar seekBar = (SeekBar)findViewById(R.id.grad_triangle_size);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                triangleSize = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(GradientActivity.this,"Changed number of points by triangle size: " + triangleSize, Toast.LENGTH_SHORT).show();
                updateNumPoints(triangleSize);
            }
        });
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
        if(v != null && v instanceof TextView) {
            GradientDrawable drawable = (GradientDrawable) v.getBackground();
            drawable.setColor(currentColor);

            TextView textView = (TextView)v;
            if (currentColor == Color.parseColor("#ffffff"))
                textView.setTextColor(Color.parseColor("#000000"));
            else
                textView.setTextColor(Color.parseColor("#ffffff"));

            textView.setText(R.string.md_done);
        }
    }

    private void setApplyListener() {
        // Apply Button Listener
        View view = findViewById(R.id.grad_apply);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageProcessor processor = ImageLayerHandler.getInstance().getProcessor();
                int[] fixedColors = fixColors();
                fixDimensions();
                Bitmap map = GradientMaker.makeGradient(gradientWidth,gradientHeight,fixedColors,isPortrait);
                processor.setImage(map);
                processor.setPointMaker(pm);
                processor.processImage();

                Log.d(TAG,"processor's pm is a " + processor.getPointMaker().getClass().getSimpleName());
                Intent i = new Intent(GradientActivity.this, PolyActivity.class);
                startActivity(i);
            }
        });
    }

    private void fixDimensions() {
        if(gradientWidth < minimumGradientWidth)
            gradientWidth = minimumGradientWidth;
        if(gradientHeight < minimumGradientHeight)
            gradientHeight = minimumGradientHeight;
    }

    private int[] fixColors() {
        int pickedColors = 0;
        for(int i : gradientColors)
            if(i != 0) pickedColors++;

        int[] fixedColors;
        if(pickedColors >= minimumAmountOfColorsInGradient) {   // 2
            fixedColors = new int[pickedColors];
            pickedColors = 0;
            for(int i : gradientColors)
                if(i != 0)
                    fixedColors[pickedColors++] = i;
        } else if(pickedColors == 0) {                          // Default if no Colors Picked
            fixedColors = new int[2];
            fixedColors[0] = Color.WHITE;
            fixedColors[1] = Color.BLACK;
        } else {                                                // Only one Color Picked
            int color = 0;
            for(int i : gradientColors)
                if(i != 0)
                    color = i;
            fixedColors = new int[2];
            fixedColors[0] = color;
            fixedColors[1] = Color.BLACK;
        }

        return fixedColors;
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
        int[] hideColorPickerIds = new int[] {R.id.grad_pick_color_ok_button, R.id.grad_pick_color_cancel_button, R.id.grad_root};
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

    private void setLogoTypeFaces() {
        Typeface materialTypeface = Typeface.createFromAsset(getAssets(), "fonts/material_design_icons.ttf"),
                jsLightTypeface = Typeface.createFromAsset(getAssets(), "fonts/josefin_light.ttf"),
                lobsterTypeface = Typeface.createFromAsset(getAssets(), "fonts/lobster.ttf");

        // Change Fonts of Splash Text
        TextView textView = (TextView)findViewById(R.id.grad_logo_text_1);
        textView.setTypeface(jsLightTypeface);
        textView = (TextView)findViewById(R.id.grad_logo_text_2);
        textView.setTypeface(lobsterTypeface);
        textView = (TextView)findViewById(R.id.grad_logo_text_3);
        textView.setTypeface(jsLightTypeface);
    }

    private void initDefaults() {
        initGradientActivityColorIds();
        initResolution();
        initColors();
        initTriangleSize();
        initDirection();
        hideColorPicker();
        setMaterialTypeFaces();
        setLogoTypeFaces();
    }

    /**
     * Sets the defaults for the Gradient to be built according to the screen size.
     * Modifies the variables backgroundGradientWidth and backgroundGradientHeight, as well as the text
     * values for the EditText Views exposed to the user. Also initializes gradientWidth as well as gradientHeight
     */
    private void initResolution() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        gradientWidth = size.x;
        gradientHeight = size.y;

        View v = findViewById(R.id.grad_width_edit_text);
        if(v != null && v instanceof EditText)
            ((EditText)v).setText("" + gradientWidth);
        v = findViewById(R.id.grad_height_edit_text);
        if(v != null && v instanceof EditText)
            ((EditText)v).setText("" + gradientHeight);

        this.backgroundGradientWidth = gradientWidth;
        this.backgroundGradientHeight = gradientHeight;
    }

    private void initColors() {
        for(int i = 0; i < gradientColors.length; i++)
            gradientColors[i] = 0;

        for(int colorEl = 1; colorEl <= numberOfColors; colorEl++) {
            String id = idBase + colorEl;
            int resID = getResources().getIdentifier(id, "id", "com.pictopoly.polydemo");
            View v = findViewById(resID);
            if(v != null && v instanceof TextView) {
                GradientDrawable drawable = (GradientDrawable) v.getBackground();
                drawable.setColor(Color.parseColor("#666666"));
            }
        }

    }

    /**
     * Sets the default size of the triangles in the generated Gradient.
     */
    private void initTriangleSize() {
        this.pm = new UniformPointMaker();
        updateNumPoints(DefaultTriangleSize);
    }

    /**
     * This method updates the abount of points the pointMaker will generate in the triangulation for
     * the gradient. Since the amount of points varies inversely with  triangleSize, the number used
     * to set the amountof points is equal to the MaxPointCount less the triangleSize. This number is
     * then scaled by a factor of PointScale.
     *
     * @param triangleSize      Indicates how large the triangles will be in the generated Gradient
     */
    private void updateNumPoints(int triangleSize) {
        int temp = MaxPointCount - triangleSize; // Not Magic at all!
        temp *= PointScale;

        ((UniformPointMaker)pm).setNumberOfPoints(temp);
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
