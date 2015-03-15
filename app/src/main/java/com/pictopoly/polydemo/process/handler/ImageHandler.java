package com.pictopoly.polydemo.process.handler;

import android.graphics.Bitmap;

import com.pictopoly.polydemo.tri.DelaunayTriangulation;

/**
 * Created by Marklar on 3/14/2015.
 */
public abstract class ImageHandler {
    protected Bitmap sourceMap, processedMap;
    private int width, height;

    public ImageHandler(Bitmap sourceMap) {
        this.sourceMap = sourceMap;
        this.processedMap = this.sourceMap.copy(Bitmap.Config.ARGB_8888, true);
        this.width = this.sourceMap.getWidth();
        this.height = this.sourceMap.getHeight();
    }

    public Bitmap getSourceMap() {
        return this.sourceMap;
    }

    public Bitmap getProcessedMap() {
        return this.processedMap;
    }

    public void setImage(Bitmap sourceImage) {
        this.sourceMap = sourceImage;
        this.processedMap = this.sourceMap.copy(Bitmap.Config.ARGB_8888,true);
    }

    public void setProcessedImage(Bitmap processedImage) {
        this.processedMap = processedImage;
    }

    public void flush() {
        if(this.sourceMap != null) {
            this.sourceMap.recycle();
            this.sourceMap = null;
        }
        if(this.processedMap != null) {
            this.processedMap.recycle();
            this.processedMap = null;
        }
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }
}
