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
	
	/*
     *            Instead of passing through the entire image pixel by pixel I
     *            skip pixels, this variable is how many pixels to skip.
     */
	protected int imageQuality = 4;
	protected Bitmap rawBitmap;
	
	@Override
	public Collection<Point> makePoints(Bitmap bitmapToBeProcessed) {
        if(this.points != null && this.points.size() > 0)
            this.points.clear();
        else if(this.points == null)
            this.points = new ArrayList<Point>(pointCount);

        int newWidth = bitmapToBeProcessed.getWidth() / imageQuality,
                newHeight = bitmapToBeProcessed.getHeight() / imageQuality;

        Bitmap filteredMap = ImageProcessor.getResizedBitmap(bitmapToBeProcessed, newWidth, newHeight);
        new GreyScaleFilter().filter(filteredMap); // Kind of awful using it like a static method
        new SobelFilter().filter(filteredMap);

        Collection<Point> newPoints = new ArrayList<Point>();

        Random r = new Random();
        // Go through either all of the points in the new Image, or until we have enough uniformly distributed Edge Points
        for(int i = 0; (i < (newWidth * newHeight)) && (newPoints.size() <= numberOfEdgePoints); i++) {
            double x = r.nextDouble() * newWidth,
                    y = r.nextDouble() * newHeight;
            int mapColor = filteredMap.getPixel((int)x,(int)y);
            if(mapColor <= MAX_COLOR && mapColor >= MIN_COLOR) {
                newPoints.add(new Point(x * imageQuality, y * imageQuality));
            }
        }

        this.points.addAll(newPoints);

//        Log.d(TAG, "EdgeMaker points: " + this.points.size());

        filteredMap.recycle();

        return this.points;
    }
	
	public EdgePointMaker(Bitmap bitmapToBeProcessed) {
        this.rawBitmap = bitmapToBeProcessed;
	}
	
	public void setBitmap(Bitmap bitmapToBeProcessed) {
		this.rawBitmap = bitmapToBeProcessed;
	}
	
	public Collection<Point> makePoints() {
        if(this.rawBitmap == null)
            return null;
		this.points = makePoints(this.rawBitmap);
		return this.points;
	}
	
	public Collection<Point> getPoints() {
		return this.points;
	}
	
	public void setImageQuality(int imageQuality) {
		this.imageQuality = imageQuality;
	}
	
	public int getImageQuality() {
		return this.imageQuality;
	}

    public void setNumberOfEdgePoints(int numberOfEdgePoints) {
        this.numberOfEdgePoints = numberOfEdgePoints;
    }
}
