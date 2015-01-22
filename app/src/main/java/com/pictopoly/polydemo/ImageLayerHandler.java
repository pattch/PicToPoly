package com.pictopoly.polydemo;

import com.pictopoly.polydemo.process.ImageHandler;

/**
 * Created by Samuel on 1/21/2015.
 */
public class ImageLayerHandler {
    private static ImageLayerHandler ourInstance = new ImageLayerHandler();
    protected ImageHandler processor;

    public static ImageLayerHandler getInstance() {
        return ourInstance;
    }

    private ImageLayerHandler() {
        processor = new ImageHandler();
    }

    public ImageHandler getProcessor() {
        return processor;
    }
}
