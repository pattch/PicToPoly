package com.pictopoly.polydemo.nav;

import android.widget.SeekBar;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.process.pointmaker.PointMaker;
import com.pictopoly.polydemo.process.pointmaker.RadiusPointMaker;

public class NearEdgePointsSliderElement extends PointsSliderElement {
    public NearEdgePointsSliderElement(int id) {
        super(id);
    }

    public NearEdgePointsSliderElement(SeekBar seekBar, int id) {
        super(seekBar,id);
    }

    @Override
    public void onStopTrackingTouch (SeekBar seekBar) {
//        Log.d(getClass().getSimpleName(), "Setting Near-Edge Points: " + numPoints);
        PointMaker pm = ImageLayerHandler.getInstance().getPolyActivityImageProcessor().getPointMaker();
        if(pm instanceof RadiusPointMaker)
            ((RadiusPointMaker)pm).setRadius(numPoints * 5);
    }
}
