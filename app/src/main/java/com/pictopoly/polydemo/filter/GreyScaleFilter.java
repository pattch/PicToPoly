package com.pictopoly.polydemo.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by Samuel on 1/21/2015.
 */
public class GreyScaleFilter extends ColorMatrixImageFilter {
    @Override
    protected ColorMatrixColorFilter makeColorMatrixColorFilter() {
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
        return cf;
    }

    public static int getGreyScaleValue(int color) {
        int r = Color.red(color),
                g = Color.green(color),
                b = Color.blue(color);

        return (r + g + b) / 3;
    }
}
