package com.pictopoly.polydemo.nav;

import android.util.Log;
import android.widget.SeekBar;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.process.PointMaker;
import com.pictopoly.polydemo.process.RandomPointMaker;

import java.util.Random;

/**
 * Created by Marklar on 3/2/2015.
 */
public class RandomPointsSliderElement extends PointsSliderElement {

    public RandomPointsSliderElement(int id) {
        super(id);
    }

    public RandomPointsSliderElement(SeekBar seekBar, int id) {
        super(seekBar,id);
    }

    @Override
    public void onStopTrackingTouch (SeekBar seekBar) {
        Log.d(getClass().getSimpleName(),"Setting Random Points: " + numPoints );
        PointMaker pm = ImageLayerHandler.getInstance().getProcessor().getPointMaker();
        if(pm instanceof RandomPointMaker)
            ((RandomPointMaker)pm).setNumberOfRandomPoints(numPoints * numPoints);
    }
}
