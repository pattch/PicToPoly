package com.pictopoly.polydemo.nav;

import android.util.Log;
import android.widget.SeekBar;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.process.PointMaker;
import com.pictopoly.polydemo.process.RadiusPointMaker;

/**
 * Created by Marklar on 3/3/2015.
 */
public class NearEdgeDistanceSliderElement extends PointsSliderElement {
    public NearEdgeDistanceSliderElement(int id) {
        super(id);
    }

    public NearEdgeDistanceSliderElement(SeekBar seekBar, int id) {
        super(seekBar,id);
    }

    @Override
    public void onStopTrackingTouch (SeekBar seekBar) {
        Log.d(getClass().getSimpleName(), "Setting Near-Edge Points: " + numPoints);
        PointMaker pm = ImageLayerHandler.getInstance().getProcessor().getPointMaker();
        if(pm instanceof RadiusPointMaker)
            ((RadiusPointMaker)pm).setRadius(numPoints / 20);
    }
}
