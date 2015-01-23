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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pictopoly.polydemo.tri.Point;

import com.pictopoly.polydemo.filter.*;

public class EdgePointMaker extends PixelPointMaker implements PointMaker {
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
	public static final int MIN_COLOR = Color.GRAY;
	
	/*
     *            Instead of passing through the entire image pixel by pixel I
     *            skip pixels, this variable is how many pixels to skip.
     */
	protected int imageQuality = 2;
	protected Bitmap rawBitmap;
	
	@Override
	public Collection<Point> makePoints(Bitmap bitmapToBeProcessed) {
        getBitmapPixels(bitmapToBeProcessed);
		return makePoints(pixels,width,height);
    }

    public Collection<Point> makePoints(int[] pixels, int width, int height) {
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

        List<Point> builtPoints = new ArrayList<Point>();

        for (int yCoord = 0; yCoord < height; yCoord += imageQuality) {
            for (int xCoord = 0; xCoord < width; xCoord += imageQuality) {
                intColor = getPixel(greyPixels,xCoord, yCoord,width, height);

                if (intColor <= MAX_COLOR
                        && intColor >= MIN_COLOR)

                    builtPoints.add(new Point(xCoord, yCoord));
            }
        }

        this.points = builtPoints;
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
