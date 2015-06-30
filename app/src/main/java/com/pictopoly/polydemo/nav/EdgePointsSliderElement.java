package com.pictopoly.polydemo.nav;

import android.util.Log;
import android.widget.SeekBar;

import com.pictopoly.polydemo.ImageLayerHandler;
import com.pictopoly.polydemo.process.pointmaker.EdgePointMaker;
import com.pictopoly.polydemo.process.pointmaker.PointMaker;

public class EdgePointsSliderElement extends PointsSliderElement {
    public EdgePointsSliderElement(int id) {
        super(id);
    }

    public EdgePointsSliderElement(SeekBar seekBar, int id) {
        super(seekBar, id);
    }

    @Override
    public void onStopTrackingTouch (SeekBar seekBar) {
//        Log.d(getClass().getSimpleName(), "Setting Edge Points: " + numPoints);
        PointMaker pm = ImageLayerHandler.getInstance().getProcessor().getPointMaker();
        if(pm instanceof EdgePointMaker) {
            ((EdgePointMaker)pm).setNumberOfEdgePoints(numPoints * numPoints);
        }
    }
}
