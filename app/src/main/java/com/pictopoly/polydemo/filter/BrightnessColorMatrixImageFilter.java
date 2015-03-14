package com.pictopoly.polydemo.filter;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

/**
 * Created by Marklar on 3/11/2015.
 */
public class BrightnessColorMatrixImageFilter extends ColorMatrixImageFilter {

    protected float brightness;

    @Override
    protected ColorMatrixColorFilter makeColorMatrixColorFilter() {
        float[] array = new float[] {
                1, 0, 0, 0, brightness,
                0, 1, 0, 0,brightness,
                0, 0, 1, 0, brightness,
                0, 0, 0, 1, 0 };
        ColorMatrix matrix = new ColorMatrix(array);
        return new ColorMatrixColorFilter(matrix);
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }
}
