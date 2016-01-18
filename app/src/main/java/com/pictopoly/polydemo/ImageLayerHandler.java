package com.pictopoly.polydemo;

import com.pictopoly.polydemo.process.ImageProcessor;

/**
 * Created by Samuel on 1/21/2015.
 *
 * This class ensures that the ImageProcessors used by the various activities in the app
 * persist between Activities.
 */
public class ImageLayerHandler {
    private static ImageLayerHandler ourInstance = new ImageLayerHandler();
    public static final int SPLASH_HANDLER = 0;
    public static final int POLY_HANDLER = 1;
    public static final int GRADIENT_HANDLER = 2;
    public static final int NUM_LAYERS = 3;
    protected ImageProcessor[] processors = new ImageProcessor[NUM_LAYERS];
    protected int currentProcessor = SPLASH_HANDLER;

    public static ImageLayerHandler getInstance() {
        return ourInstance;
    }

    /**
     * Initialize the ImageProcessors used in the App
     * Creates processors for Splash, Poly, and Gradient Activities
     */
    private ImageLayerHandler() {
        for(int i = 0; i < NUM_LAYERS; i++) {
            this.processors[i] = new ImageProcessor();
        }
    }

    public ImageProcessor getPolyActivityImageProcessor() {
        return this.processors[POLY_HANDLER];
    }

    public ImageProcessor getSplashActivityImageProcessor() {
        return this.processors[SPLASH_HANDLER];
    }

    public ImageProcessor getGradientActivityImageProcessor() {
        return this.processors[GRADIENT_HANDLER];
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
