package com.pictopoly.polydemo.process;

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

import com.pictopoly.polydemo.tri.Point;

import com.pictopoly.polydemo.filter.*;

public class EdgePointMaker extends PixelPointMaker implements PointMaker {
    protected int numberOfEdgePoints = pointCount / RadiusPointMaker.getRatio();
    private final String TAG = this.getClass().getSimpleName();

	/**
	 * 				This represents the upper bound of colors considered to be an edge
     *            	point. Setting this to a darker grey will allow less points to be 
     *            	considered edge points.
	 */
	public static final int MAX_COLOR = Color.WHITE;
	
	/**k
     *            	This represents the lower bound of colors considered to be an edge
     *            	point. Setting this to a darker grey will allow more
     *            	points to be considered edge points.
	 */
	public static final int MIN_COLOR = Color.argb(255,20,20,20);
	
	/*
     *            Instead of passing through the entire image pixel by pixel I
     *            skip pixels, this variable is how many pixels to skip.
     */
	protected int imageQuality = 10;
	protected Bitmap rawBitmap;
	
	@Override
	public Collection<Point> makePoints(Bitmap bitmapToBeProcessed) {
//        getBitmapPixels(bitmapToBeProcessed);
//		return makePoints(pixels,width,height);
        if(this.points != null && this.points.size() > 0)
            this.points.clear();
        else if(this.points == null)
            this.points = new ArrayList<>(pointCount);

        int newWidth = bitmapToBeProcessed.getWidth() / imageQuality,
                newHeight = bitmapToBeProcessed.getHeight() / imageQuality;

        Bitmap filteredMap = ImageHandler.getResizedBitmap(bitmapToBeProcessed, newWidth, newHeight);
        filteredMap = new GreyScaleFilter().filter(filteredMap);
        filteredMap = new SobelFilter().filter(filteredMap);

        Collection<Point> newPoints = new ArrayList<>();
//        for(int i =0; i < newWidth; i ++) {
//            for(int j = 0; j < newHeight; j ++) {
//                int mapColor = filteredMap.getPixel(i,j);
//                if(mapColor <= MAX_COLOR && mapColor >= MIN_COLOR) {
////                    Log.d(TAG, "adding Point: (" + (i * imageQuality) + ", " + (j * imageQuality) + ")");
//                    this.points.add(new Point(i * imageQuality, j * imageQuality));
//                }
//            }
//        }

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

        Log.d(TAG, "EdgeMaker points: " + this.points.size());

        return this.points;
    }

    // This currently doesn't work.
    public Collection<Point> makePoints(int[] pixels, int width, int height) {
        if(this.points.size() > 0)
            this.points.clear();
        int[] greyPixels = new GreyScaleFilter().filter(pixels, width, height);
        greyPixels = new SobelFilter().filter(greyPixels,width,height);

		/*
		 *        This is the integer representation of a RGB value, every pixel
		 *        color is stored here, and compared to minColor and maxColor.
		 *        Since the image is Black and White the only colors will be
     	 *        black -> white. If it is found to be a color between minColor
     	 *        and maxColor it is considered a edge point.
		 */
        int intColor;

        Collection<Point> builtPoints = new ArrayList<>();

        for (int yCoord = 0; yCoord < height; yCoord += imageQuality) {
            for (int xCoord = 0; xCoord < width; xCoord += imageQuality) {
                intColor = getPixel(greyPixels,xCoord, yCoord,width, height);

                if (intColor <= MAX_COLOR
                        && intColor >= MIN_COLOR)

                    builtPoints.add(new Point(xCoord, yCoord));
            }
        }

        Log.d(TAG, "Points: " + builtPoints.size());

        this.points.addAll(builtPoints);
        return this.points;
    }

	
	public EdgePointMaker(Bitmap bitmapToBeProcessed) {
		getBitmapPixels(bitmapToBeProcessed);
	}
	
	public void setBitmap(Bitmap bitmapToBeProcessed) {
		getBitmapPixels(bitmapToBeProcessed);
	}
	
	public Collection<Point> makePoints() {
		if(this.pixels == null)
			return null;
		this.points = makePoints(pixels, width, height);
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
}
