package com.pictopoly.polydemo.process.handler;

import android.graphics.Bitmap;

import com.pictopoly.polydemo.filter.GradientMaker;

/**
 * Created by Marklar on 3/14/2015.
 */
public class GradientImageHandler extends ImageHandler {
    protected GradientMaker gm;

    public GradientImageHandler(Bitmap sourceImage) {
        super(sourceImage);
        gm = new GradientMaker(sourceImage.getWidth(), sourceImage.getHeight(),
                new int[] {sourceImage.getPixel(0,0), sourceImage.getPixel(sourceImage.getWidth(), sourceImage.getHeight()), }, true);
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
        super(GradientMaker.makeGradient(width, height, colors, isPortrait));
        this.gm = new GradientMaker(width, height, colors, isPortrait);
    }

    public GradientImageHandler(int[] colors, int width, int height) {
        this(colors, width, height, true);
    }

    public GradientImageHandler(GradientMaker gm) {
        super(gm.makeGradient());
        this.gm = gm;
    }

    public void setColors(int[] colors) {
        this.gm.setColors(colors);
        makeGradient();
    }

    public void setDimensions(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int width) {
        gm.setWidth(width);
    }

    public void setHeight(int height) {
        gm.setHeight(height);
    }

    private void makeGradient() {
        this.sourceMap = gm.makeGradient();
        this.processedMap = this.sourceMap.copy(Bitmap.Config.ARGB_8888, true);
    }
}
