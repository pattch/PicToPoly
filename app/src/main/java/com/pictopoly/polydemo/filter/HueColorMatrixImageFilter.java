package com.pictopoly.polydemo.filter;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

/**
 * Created by Marklar on 3/11/2015.
 */
public class HueColorMatrixImageFilter extends ColorMatrixImageFilter {
    protected float hue = 0f;

    @Override
    protected ColorMatrixColorFilter makeColorMatrixColorFilter()
    {
        if (hue == 0)
            return null;

        float cosVal = (float) Math.cos(hue),
                sinVal = (float) Math.sin(hue),
                lumR = 0.213f,
                lumG = 0.715f,
                lumB = 0.072f;
        float[] array = new float[] {
                lumR + cosVal * (1 - lumR) + sinVal * (-lumR),      lumG + cosVal * (-lumG) + sinVal * (-lumG),         lumB + cosVal * (-lumB) + sinVal * (1 - lumB),  0,  0,
                lumR + cosVal * (-lumR) + sinVal * (0.143f),        lumG + cosVal * (1 - lumG) + sinVal * (0.140f),     lumB + cosVal * (-lumB) + sinVal * (-0.283f),   0,  0,
                lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)),   lumG + cosVal * (-lumG) + sinVal * (lumG),          lumB + cosVal * (1 - lumB) + sinVal * (lumB),   0,  0,
                0f, 0f, 0f, 1f, 0f,
                0f, 0f, 0f, 0f, 1f };

        ColorMatrix matrix = new ColorMatrix(array);
        return new ColorMatrixColorFilter(matrix);
    }

    public void setHue(float hue) {
        hue = this.cleanValue(hue, 180f) / 180f * (float) Math.PI;
    }

    private float cleanValue(float p_val, float p_limit)
    {
        return Math.min(p_limit, Math.max(-p_limit, p_val));
    }
}
