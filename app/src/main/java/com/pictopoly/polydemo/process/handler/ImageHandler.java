package com.pictopoly.polydemo.process.handler;

import android.graphics.Bitmap;

import com.pictopoly.polydemo.tri.DelaunayTriangulation;

/**
 * Created by Marklar on 3/14/2015.
 */
public abstract class ImageHandler {
    protected Bitmap sourceMap, processedMap;
    private int width, height;

    /**
     *
     * @param sourceMap     The source image to be processed
     *
     * @exception java.lang.OutOfMemoryError
     *  Can cause the app to crash on some devices, caused by copying the bitmap
     */
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

    /**
     * Very Hackish, this relies on the fact that when sourceMap is set, it
     * renews processedMap to a copied version of sourceMap.
     */
    public void prepareProcessedMap() {
        this.processedMap.recycle();
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
