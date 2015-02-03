package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;

import com.pictopoly.polydemo.tri.Point;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Samuel on 1/23/2015.
 */
public abstract class PixelPointMaker implements PointMaker {
    public static int pointCount = 10000;
    protected Collection<Point> points;
    protected int[] pixels;
    protected int width, height;

    public void getBitmapPixels(Bitmap bitmapToBeProcessed) {
        this.width = bitmapToBeProcessed.getWidth();
        this.height = bitmapToBeProcessed.getHeight();

        this.pixels = new int[this.width * this.height];
        bitmapToBeProcessed.getPixels(this.pixels,
                0,
                bitmapToBeProcessed.getWidth(),
                0,
                0,
                bitmapToBeProcessed.getWidth(),
                bitmapToBeProcessed.getHeight());

        points = new ArrayList<>(pointCount);
    }

    public static int getPixel(int[] pixels, int x, int y, int width, int height) {
        int offset = y * width;
        int accessInt = x + offset;
        return pixels[accessInt];
    }
}
