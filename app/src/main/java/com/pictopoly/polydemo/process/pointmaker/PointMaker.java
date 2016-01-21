package com.pictopoly.polydemo.process.pointmaker;

import android.graphics.Bitmap;
import java.util.Collection;
import com.pictopoly.polydemo.process.handler.ImageHandler;
import com.pictopoly.polydemo.tri.Point;

public interface PointMaker {
	/**
	 * @param handler
	 * 				The ImageHandler containing the unprocessed Image to Populate points from
	 * @return
	 * 				A collection of representative points of the given Image,
	 * 				should 
	 */
	public Collection<Point> makePoints(ImageHandler handler);
	
	public Collection<Point> getPoints();
	
	public void setBitmap(Bitmap bitmapToBeProcessed);

    public void clearPoints();
}
