package com.pictopoly.polydemo.process.handler;

import android.graphics.Bitmap;

/**
 * Created by Marklar on 3/14/2015.
 */
public class BitmapImageHandler extends ImageHandler {
    public BitmapImageHandler(Bitmap sourceMap) {
        super(sourceMap);
    }

    @Override
    public Bitmap processImage() {
        return null;
    }

    public void setImage(Bitmap sourceImage) {
        this.sourceMap = sourceImage;
        this.processedMap = this.sourceMap.copy(Bitmap.Config.ARGB_8888,true);
    }
}
