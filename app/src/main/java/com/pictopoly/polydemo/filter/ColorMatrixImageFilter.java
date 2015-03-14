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
public abstract class ColorMatrixImageFilter implements ImageFilter {

    @Override
    public void filter(Bitmap bitmap) {
        Canvas c = new Canvas(bitmap);
        Paint p = new Paint();
        ColorMatrixColorFilter cf = makeColorMatrixColorFilter();
        p.setColorFilter(cf);
        c.drawBitmap(bitmap,0,0,p);
    }

    protected abstract ColorMatrixColorFilter makeColorMatrixColorFilter();
}
