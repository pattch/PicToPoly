package com.pictopoly.polydemo.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by Samuel on 1/21/2015.
 */
public class GreyScaleFilter implements ImageFilter {

    @Override
    public Bitmap filter(Bitmap bitmap) {
        int width = bitmap.getWidth(), height = bitmap.getHeight();
        Bitmap greyMap;
        greyMap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        // First create a Canvas from the newly created Bitmap object, then configure a paint object
        // That will be used to draw the original Bitmap with no (zero) saturation, ala greyscale.

        Canvas c = new Canvas(greyMap);
        Paint p = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
        p.setColorFilter(cf);
        c.drawBitmap(bitmap,0,0,p);

        return greyMap;
    }
}
