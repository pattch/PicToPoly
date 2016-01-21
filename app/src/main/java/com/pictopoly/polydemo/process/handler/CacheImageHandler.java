package com.pictopoly.polydemo.process.handler;

import android.graphics.Bitmap;

import com.pictopoly.polydemo.process.ImageProcessor;

/**
 * Created by Sam on 1/21/16.
 *
 * Handles Logic specific to ImageHandling when edge detection is used
 * Mostly useful for cacheing the Bitmap created when Edge Detection is done
 */
public class CacheImageHandler extends BitmapImageHandler {
    protected Bitmap edgeMap;
    protected boolean isCacheMapValid = false;
    public static int defaultCacheMapScale = 4;
    protected int currentScale = defaultCacheMapScale;

    public CacheImageHandler(Bitmap sourceMap) {
        super(sourceMap);
    }

    /**
     *
     * @param sourceImage the Image to be processed
     * This method initializes the EdgeMap as well
     */
    @Override public void setImage(Bitmap sourceImage) {
        super.setImage(sourceImage);
        initializeDefaultEdgeMap();
    }

    /**
     * Initializes the edgeMap to be a set fraction of the source map
     *
     * @param edgeMapScale The ratio of the size
     */
    public void initializeEdgeMapByScale(int edgeMapScale) {
        this.currentScale = edgeMapScale;

        int newWidth = this.sourceMap.getWidth() / edgeMapScale,
                newHeight = this.sourceMap.getHeight() / edgeMapScale;
        this.edgeMap = ImageProcessor.getResizedBitmap(this.sourceMap, newWidth, newHeight);

        this.isCacheMapValid = false;
    }

    /**
     * Initializes the edgeMap to the default size
     */
    public void initializeDefaultEdgeMap() {
        initializeEdgeMapByScale(defaultCacheMapScale);
    }

    /**
     * Recycles all Images being handled
     */
    @Override public void flush() {
        super.flush();

        if(this.edgeMap != null) {
            this.edgeMap.recycle();
            this.edgeMap = null;
            this.isCacheMapValid = false;
        }
    }

    /**
     * To be used after processing occurs
     *
     * @param em The processed map
     */
    public void setEdgeMap(Bitmap em) {
        this.edgeMap = em;
        this.isCacheMapValid = true;
    }

    public Bitmap getEdgeMap() {
        return this.edgeMap;
    }

    public boolean hasValidEdgeMap() {
        return this.isCacheMapValid;
    }

    public int getCurrentScale() {
        return this.currentScale;
    }
}
