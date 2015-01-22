package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;

import java.util.Collection;

import com.pictopoly.polydemo.tri.Point;

public interface PointMaker {
	/**
	 * @param bitmapToBeProcessed
	 * 				The unprocessed Image to Populate points from
	 * @return 
	 * 				A collection of representative points of the given Image,
	 * 				should 
	 */
	public Collection<Point> makePoints(Bitmap bitmapToBeProcessed);
	
	public Collection<Point> getPoints();
	
	public void setBitmap(Bitmap bitmapToBeProcessed);
}
