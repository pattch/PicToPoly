package com.pictopoly.polydemo.nav;

import android.widget.SeekBar;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.process.pointmaker.PointMaker;
import com.pictopoly.polydemo.process.pointmaker.RandomPointMaker;

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
//        Log.d(getClass().getSimpleName(),"Setting Random Points: " + numPoints );
        PointMaker pm = ImageLayerHandler.getInstance().getPolyActivityImageProcessor().getPointMaker();
        if(pm instanceof RandomPointMaker)
            ((RandomPointMaker)pm).setNumberOfRandomPoints(numPoints * numPoints);
    }
}
