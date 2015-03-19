package com.pictopoly.polydemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pictopoly.polydemo.filter.GradientMaker;
import com.pictopoly.polydemo.process.ImageProcessor;
import com.pictopoly.polydemo.process.NotifyingRunnable;
import com.pictopoly.polydemo.process.PointMaker.PointMaker;
import com.pictopoly.polydemo.process.PointMaker.UniformPointMaker;
import com.pictopoly.polydemo.process.ThreadCompleteListener;

/**
 * Created by Marklar on 3/15/2015.
 */
public class GradientActivity extends Activity implements ThreadCompleteListener {
    private final String TAG = this.getClass().getSimpleName();
    protected ImageProcessor processor;
    protected TriangleSurfaceView mTriangleSurfaceView;
    protected int[] gradActivityBackgroundColors = new int[] { Color.parseColor("#ffffff"), Color.parseColor("#aaaaaa"), };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient);
        processor = ImageLayerHandler.getInstance().getGradientProcessor();
        mTriangleSurfaceView = (TriangleSurfaceView) findViewById(R.id.grad_triangleSurfaceView);

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

    public void onRadioButtonClicked(View view) {

    }

    @Override
    public void notifyThreadComplete(final Runnable runnable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTriangleSurfaceView != null) {
                    mTriangleSurfaceView.setFillingScreen(true);
                    mTriangleSurfaceView.setImage(processor.processImage());
                    mTriangleSurfaceView.invalidate();
                }
            }
        });
    }
}
