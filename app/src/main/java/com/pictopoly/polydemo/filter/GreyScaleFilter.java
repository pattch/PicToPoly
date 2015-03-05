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
public class GreyScaleFilter implements ImageFilter {

    @Override
    public void filter(Bitmap bitmap) {
//        int width = bitmap.getWidth(), height = bitmap.getHeight();
//        Bitmap greyMap;
//        greyMap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        // First create a Canvas from the newly created Bitmap object, then configure a paint object
        // That will be used to draw the original Bitmap with no (zero) saturation, ala greyscale.

        Canvas c = new Canvas(bitmap);
        Paint p = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
        p.setColorFilter(cf);
        c.drawBitmap(bitmap,0,0,p);
    }

    @Override
    public int[] filter(int[] colorIntArray, int width, int height) {
        int length = colorIntArray.length;
        int[] greyIntArray = new int[length];
        System.arraycopy(colorIntArray,0, greyIntArray, 0, length);

        for(int i = 0; i < length; i++)
            greyIntArray[i] = getGreyScaleValue(greyIntArray[i]);

        return greyIntArray;
    }

    public static int getGreyScaleValue(int color) {
        int r = Color.red(color),
                g = Color.green(color),
                b = Color.blue(color);

        return (r + g + b) / 3;
    }
}
