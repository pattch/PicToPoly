package com.pictopoly.polydemo.nav;

import android.widget.SeekBar;

/**
 * Created by Marklar on 3/3/2015.
 */
public abstract class PointsSliderElement extends SliderElement {
    protected int numPoints;

    public PointsSliderElement(int id) {
        super(id);
    }

    public PointsSliderElement(SeekBar seekBar, int id) {
        super(seekBar,id);
    }

    @Override
    public void onStartTrackingTouch (SeekBar seekBar) {
        // Nothing to do here!
    }

    @Override
    public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser) {
        numPoints = progress;
    }
}
