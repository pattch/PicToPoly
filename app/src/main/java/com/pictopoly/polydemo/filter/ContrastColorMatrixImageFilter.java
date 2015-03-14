package com.pictopoly.polydemo.filter;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

/**
 * Created by Marklar on 3/11/2015.
 */
public class ContrastColorMatrixImageFilter extends ColorMatrixImageFilter {
    protected float contrast;

    @Override
    protected ColorMatrixColorFilter makeColorMatrixColorFilter() {
        float scale = contrast + 1.f;
        float translate = (-.5f * scale + .5f) * 255.f;
        float[] array = new float[] {
                scale, 0, 0, 0, translate,
                0, scale, 0, 0, translate,
                0, 0, scale, 0, translate,
                0, 0, 0, 1, 0};
        ColorMatrix matrix = new ColorMatrix(array);
        return new ColorMatrixColorFilter(matrix);
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }
}
