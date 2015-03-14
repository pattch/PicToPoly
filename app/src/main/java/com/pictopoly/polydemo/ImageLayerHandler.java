package com.pictopoly.polydemo;

import com.pictopoly.polydemo.process.ImageProcessor;

/**
 * Created by Samuel on 1/21/2015.
 */
public class ImageLayerHandler {
    private static ImageLayerHandler ourInstance = new ImageLayerHandler();
    public static final int SPLASH_HANDLER = 0;
    public static final int POLY_HANDLER = 1;
    public static final int NUM_LAYERS = 2;
    protected ImageProcessor[] processors = new ImageProcessor[NUM_LAYERS];
    protected int currentProcessor = SPLASH_HANDLER;

    public static ImageLayerHandler getInstance() {
        return ourInstance;
    }

    private ImageLayerHandler() {
        for(int i = 0; i < NUM_LAYERS; i++) {
            this.processors[i] = new ImageProcessor();
        }
    }

    public ImageProcessor getProcessor() {
        return this.processors[POLY_HANDLER];
    }

    public ImageProcessor getSplashProcessor() {
        return this.processors[SPLASH_HANDLER];
    }

    public ImageProcessor getCurrentProcessor() {
        return this.processors[this.currentProcessor];
    }

    public void setCurrentProcessor(int processor) {
        if(processor >= NUM_LAYERS)
            processor = NUM_LAYERS - 1;
        if(processor < 0)
            processor = 0;

        this.currentProcessor = processor;
    }
}
