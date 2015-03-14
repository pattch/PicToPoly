package com.pictopoly.polydemo.process.handler;

import android.graphics.Bitmap;

/**
 * Created by Marklar on 3/14/2015.
 */
public abstract class ImageHandler {
    protected Bitmap sourceMap, processedMap;

    public ImageHandler(Bitmap sourceMap) {
        this.sourceMap = sourceMap;
        this.processedMap = this.sourceMap.copy(Bitmap.Config.ARGB_8888, true);
    }

    public Bitmap getSourceMap() {
        return this.sourceMap;
    }

    public Bitmap getProcessedMap() {
        return this.processedMap;
    }
}
