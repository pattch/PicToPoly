package com.pictopoly.polydemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pictopoly.polydemo.filter.GradientMaker;
import com.pictopoly.polydemo.nav.CameraIntentNavigationElement;
import com.pictopoly.polydemo.nav.IntentNavigationElement;
import com.pictopoly.polydemo.nav.MakeGradientIntentNavigationElement;
import com.pictopoly.polydemo.nav.NavigationElement;
import com.pictopoly.polydemo.nav.OpenImageIntentNavigationElement;
import com.pictopoly.polydemo.nav.ReturnToImageIntentNavigationElement;
import com.pictopoly.polydemo.process.ImageProcessor;
import com.pictopoly.polydemo.process.PointMaker.PointMaker;
import com.pictopoly.polydemo.process.ThreadCompleteListener;
import com.pictopoly.polydemo.process.PointMaker.UniformPointMaker;

/**
 * This is the opening Activity that users are first exposed to.
 */
public class SplashActivity extends Activity implements ThreadCompleteListener {
    protected ImageProcessor handler;
    private static String TAG = "SplashActivity";
    protected NavigationElement[] navElements = new NavigationElement[] {
            new CameraIntentNavigationElement(R.id.splash_camera),
            new OpenImageIntentNavigationElement(R.id.splash_open_image),
            new ReturnToImageIntentNavigationElement(R.id.splash_back),
            new MakeGradientIntentNavigationElement(R.id.splash_gradient),
    };
    protected TriangleSurfaceView mTriangleSurfaceView;
    protected int[] splashActivityBackgroundColors = new int[] { Color.parseColor("#E65100"), Color.parseColor("#311B92"), };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = ImageLayerHandler.getInstance().getSplashProcessor();
        mTriangleSurfaceView = (TriangleSurfaceView) findViewById(R.id.splash_triangleSurfaceView);

        ImageProcessor ph = ImageLayerHandler.getInstance().getProcessor();
        if(ph.getProcessedImage() == null) {
            // Make Back Button Invisible, since we haven't opened/captured an Image yet
            View v = findViewById(R.id.splash_back);
            if(v != null)
                v.setVisibility(View.INVISIBLE);
        } else {
            // Expose Back Button, since we have a previous opened/captured Image
            View v = findViewById(R.id.splash_back);
            if(v != null)
                v.setVisibility(View.VISIBLE);
        }

        // First Time Running SplashActivity
        if (savedInstanceState == null && handler.getProcessedImage() == null) {
            Log.d(TAG, "Making new pointMaker, loading bitmap etc.");
//            Bitmap gradient = BitmapFactory.decodeResource(getResources(), R.drawable.gradient);
//            Display display = getWindowManager().getDefaultDisplay();
//            android.graphics.Point size = new android.graphics.Point();
//            display.getSize(size);
            Bitmap gradient = GradientMaker.makeGradient(1000, 1000, this.splashActivityBackgroundColors, true);
            handler.setImage(gradient);
            PointMaker pm = new UniformPointMaker();
            handler.setPointMaker(pm);
            handler.addListener(this);
            handler.processImage();
        }

        if (mTriangleSurfaceView != null && handler.getProcessedImage() != null) {
            mTriangleSurfaceView.setFillingScreen(true);
            mTriangleSurfaceView.setImageHandler(handler);
        } else {
            Log.d(TAG, "TriangleSurfaceView not Persisting");
        }

        Typeface materialTypeface = Typeface.createFromAsset(getAssets(), "fonts/material_design_icons.ttf"),
                jsLightTypeface = Typeface.createFromAsset(getAssets(), "fonts/josefin_light.ttf"),
                lobsterTypeface = Typeface.createFromAsset(getAssets(), "fonts/lobster.ttf");

        // Change Fonts of Splash Text
        TextView textView = (TextView)findViewById(R.id.splash_logo_text_1);
        textView.setTypeface(jsLightTypeface);
        textView = (TextView)findViewById(R.id.splash_logo_text_2);
        textView.setTypeface(lobsterTypeface);
        textView = (TextView)findViewById(R.id.splash_logo_text_3);
        textView.setTypeface(jsLightTypeface);

        for(final NavigationElement navEl : navElements) {
            View v = findViewById(navEl.getId());
            navEl.setView(v);

            if(v instanceof TextView)
                ((TextView) v).setTypeface(materialTypeface);

            if(null == v)
                Log.d(TAG, navEl.getClass().getSimpleName() + " " + navEl.getId());
            else
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navEl.onClick(v);
                    }
                });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Returned From Activity");
        if(resultCode == Activity.RESULT_OK) {
            int rCode = requestCode;

            for (NavigationElement navEl : navElements) {
                if (navEl instanceof IntentNavigationElement) {
                    IntentNavigationElement intentNav = (IntentNavigationElement) navEl;
                    if (intentNav.getRequestCode() == rCode) {
                        Log.d(TAG,navEl.getClass().getSimpleName());
                        intentNav.onActivityResult(requestCode,resultCode,data);
                        Intent i = new Intent(SplashActivity.this, PolyActivity.class);
//                        i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, mQuestionBank[mCurrentIndex].getTrueQuestion());
                        startActivity(i);
                    }
                }
            }
        } else
            Log.d(TAG, "Result: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void notifyThreadComplete(final Runnable runnable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTriangleSurfaceView != null) {
                    mTriangleSurfaceView.setFillingScreen(true);
                    mTriangleSurfaceView.setImage(handler.processImage());
                    mTriangleSurfaceView.invalidate();
                }
            }
        });
    }
}
