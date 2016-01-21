package com.pictopoly.polydemo.process.pointmaker;

/*
 * This Class takes an Image and generates Points using a Sobel Filter. This
 * implementation simply applies the Edge Detection Operator to the Image and
 * then populates a set of points directly from the output of the filter. The
 * amount of points can be tweaked by changing the upper/lower thresholds for
 * what is considered a relevant point. The image can be further tweaked by
 * changing a constant named IMAGE_QUALITY that corresponds to how frequently
 * the image is polled for relevant points.
 */

//import java.awt.Color;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.pictopoly.polydemo.process.ImageProcessor;
import com.pictopoly.polydemo.process.handler.CacheImageHandler;
import com.pictopoly.polydemo.process.handler.ImageHandler;
import com.pictopoly.polydemo.tri.Point;

import com.pictopoly.polydemo.filter.*;

public class EdgePointMaker extends PixelPointMaker implements PointMaker {
    protected int numberOfEdgePoints = pointCount / 5; // Default
    private final String TAG = this.getClass().getSimpleName();

	/**
	 * 				This represents the upper bound of colors considered to be an edge
     *            	point. Setting this to a darker grey will allow less points to be 
     *            	considered edge points.
	 */
	public static final int MAX_COLOR = Color.WHITE;
	
	/**
     *            	This represents the lower bound of colors considered to be an edge
     *            	point. Setting this to a darker grey will allow more
     *            	points to be considered edge points.
	 */
	public static final int MIN_COLOR = Color.argb(255,5,5,5);

//	protected Bitmap rawBitmap;

    /**
     * Kind of a bad paradigm to be doing Image Processing inside of a point population algorithm
     * Would be much better to have a separate method to do Image Processing, and then do point population
     *
     * @param handler
     * 				The unprocessed Image to Populate points from
     * @return A collection of points along edges of bitmapToBeProcessed
     */
	@Override
	public Collection<Point> makePoints(ImageHandler handler) {
        preparePoints();
        Bitmap edgeMap = processEdges(handler);
        int edgeWidth = edgeMap.getWidth(),
                edgeHeight = edgeMap.getHeight(),
                quality = CacheImageHandler.defaultCacheMapScale;

        if(handler instanceof CacheImageHandler)
            quality = ((CacheImageHandler)handler).getCurrentScale();

        Collection<Point> newPoints = new ArrayList<Point>();

        Random r = new Random();
        // Go through either all of the points in the new Image, or until we have enough uniformly distributed Edge Points
        for(int i = 0; (i < (edgeWidth * edgeHeight)) && (newPoints.size() <= numberOfEdgePoints); i++) {
            double x = r.nextDouble() * edgeWidth,
                    y = r.nextDouble() * edgeHeight;
            int mapColor = edgeMap.getPixel((int)x,(int)y);
            if(mapColor <= MAX_COLOR && mapColor >= MIN_COLOR) {
                newPoints.add(new Point(x * quality, y * quality));
            }
        }

        this.points.addAll(newPoints);

//        Log.d(TAG, "EdgeMaker points: " + this.points.size());

        if(!(handler instanceof CacheImageHandler))
            edgeMap.recycle();

        return this.points;
    }

    /**
     * Makes sure that points is in the proper starting state for processing
     */
    private void preparePoints() {
        // Initialize points ArrayList
        if(null == this.points)
            this.points = new ArrayList<Point>(pointCount);
        // Clear previous points
        if(this.points.size() > 0)
            this.points.clear();
    }

    private Bitmap processEdges(ImageHandler handler) {
        Bitmap filteredMap;

        // Branch to use Cacheing of Edge Detection
        if(handler instanceof CacheImageHandler)
            filteredMap = getFilterMapWithCache((CacheImageHandler) handler);
        // Branch to process without Cacheing
        else
            filteredMap = getFilterMap(handler);

        new GreyScaleFilter().filter(filteredMap); // Kind of awful using it like a static method
        new SobelFilter().filter(filteredMap);

        // Save the filtered Image
        if(handler instanceof CacheImageHandler)
            ((CacheImageHandler)handler).setEdgeMap(filteredMap);
        return filteredMap;
    }

    private Bitmap getFilterMapWithCache(CacheImageHandler cacheHandler) {
        Log.d(TAG, "CacheHandler is being used.");
        // Use previous edges
        if(cacheHandler.hasValidEdgeMap())
            return cacheHandler.getEdgeMap();

        // Ensure that the edgeMap is valid
        if(cacheHandler.getEdgeMap() == null)
            cacheHandler.initializeDefaultEdgeMap();

        return cacheHandler.getEdgeMap();
    }

    private Bitmap getFilterMap(ImageHandler handler) {
        Log.d(TAG, "CacheHandler is NOT being used.");
        int quality = CacheImageHandler.defaultCacheMapScale,
                newWidth = handler.getWidth() / quality,
                newHeight = handler.getHeight() / quality;

        return ImageProcessor.getResizedBitmap(handler.getSourceMap(), newWidth, newHeight);
    }
	
	public EdgePointMaker(Bitmap bitmapToBeProcessed) {
//        this.rawBitmap = bitmapToBeProcessed;
	}
	
	public void setBitmap(Bitmap bitmapToBeProcessed) {
//		this.rawBitmap = bitmapToBeProcessed;
	}
	
//	public Collection<Point> makePoints() {
//        if(this.rawBitmap == null)
//            return null;
//		this.points = makePoints(this.rawBitmap);
//		return this.points;
//	}
	
	public Collection<Point> getPoints() {
		return this.points;
	}
	
//	public void setImageQuality(int imageQuality) {
//		this.imageQuality = imageQuality;
//	}
//
//	public int getImageQuality() {
//		return this.imageQuality;
//	}

    public void setNumberOfEdgePoints(int numberOfEdgePoints) {
        this.numberOfEdgePoints = numberOfEdgePoints;
    }
}
