package com.pictopoly.polydemo.process.handler;

import android.graphics.Bitmap;

import com.pictopoly.polydemo.filter.GradientMaker;

/**
 * Created by Marklar on 3/14/2015.
 */
public class GradientImageHandler extends ImageHandler {
    protected int[] colors;

    public GradientImageHandler(Bitmap sourceImage) {
        super(sourceImage);
    }

    /**
     * Generates a Gradient Bitmap of the specified width/height using the specified colors,
     * and makes an ImageHandler with the generated Gradient Bitmap
     *
     * @param colors        An array of ints that specifies the colors to be used for the gradient.
     * @param width         The width of the Gradient Image to be made
     * @param height        The height of the Gradient Image to be made
     * @param isPortrait    Whether the Gradient Image is to be portrait or landscape
     */
    public GradientImageHandler(int[] colors, int width, int height, boolean isPortrait) {
        super(new GradientMaker(width,height,colors,isPortrait).makeGradient());
    }

    /**
     * This should generate a triangulation using only random points to the gradient Image already made
     * @return The triangulated gradient image
     */
    @Override
    public Bitmap processImage() {
        return null;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }
}
