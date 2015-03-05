package com.pictopoly.polydemo.nav;

import android.util.Log;
import android.widget.SeekBar;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.process.PointMaker;
import com.pictopoly.polydemo.process.RadiusPointMaker;

/**
 * Created by Marklar on 3/2/2015.
 */
public class NearEdgePointsSliderElement extends PointsSliderElement {
    public NearEdgePointsSliderElement(int id) {
        super(id);
    }

    public NearEdgePointsSliderElement(SeekBar seekBar, int id) {
        super(seekBar,id);
    }

    @Override
    public void onStopTrackingTouch (SeekBar seekBar) {
        Log.d(getClass().getSimpleName(), "Setting Near-Edge Points: " + numPoints);
        PointMaker pm = ImageLayerHandler.getInstance().getProcessor().getPointMaker();
        if(pm instanceof RadiusPointMaker)
            ((RadiusPointMaker)pm).setRadius(numPoints * 5);
    }
}
