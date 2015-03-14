package com.pictopoly.polydemo.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import java.util.List;

/**
 * Created by Marklar on 3/6/2015.
 */
public class GradientMaker {
    protected Bitmap gradientBitmap;
    protected int width, height;
    protected int[] colors;
    protected boolean isPortrait = true;

    /**
     * Constructor to build a GradientMaker
     *
     * @param width     The width of the Bitmap to be Generated
     * @param height    The height of the Bitmap to be Generated
     * @param colors    The colors in the gradient - assumed to be equally distant from each other
     * @param isPortait Tells whether the gradient runs from top to bottom (true) or left to right (false)
     */
    public GradientMaker(int width, int height, int[] colors, boolean isPortait) {
        this.width = width;
        this.height = height;
        this.colors = colors;

    }

    public Bitmap makeGradient() {
        this.gradientBitmap =  makeGradient(this.width,this.height,this.colors,this.isPortrait);
        return this.gradientBitmap;
    }

    public Bitmap getGradient() {
        return this.makeGradient();
    }

    public static Bitmap makeGradient(int width, int height, int[] colors, boolean isPortrait) {
        Bitmap grBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(grBitmap);

        int yEnd = 0, xEnd = 0;
        if(isPortrait)
            yEnd = height;
        else
            xEnd = width;

        // Make a Gradient that covers the size of the Image
        LinearGradient grad = new LinearGradient(0, 0, xEnd, yEnd, colors, null, Shader.TileMode.CLAMP);

    /* Draw your gradient to the top of your bitmap. */
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setShader(grad);
        canvas.drawRect(0, 0, width, height, p);

        return grBitmap;
    }
}
