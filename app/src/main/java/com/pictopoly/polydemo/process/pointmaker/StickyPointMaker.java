package com.pictopoly.polydemo.process.pointmaker;

//import java.awt.Image;
//import java.awt.image.BufferedImage;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.List;
import java.util.Random;

import com.pictopoly.polydemo.process.handler.ImageHandler;
import com.pictopoly.polydemo.tri.Point;

public class StickyPointMaker extends RadiusPointMaker {
    private final String TAG = this.getClass().getSimpleName();
	protected double stickiness = 5;

	public StickyPointMaker(Bitmap bitmapToBeProcessed) {
		super(bitmapToBeProcessed);
	}
	
	@Override
	public Collection<Point> makePoints(ImageHandler handler) {
//        setImageQuality(10);
        super.makePoints(handler);
		// Generate random points.
		Random r = new Random();
		Collection<Point> builtPoints = new ArrayList<Point>();
        int width = handler.getWidth(),
                height = handler.getHeight();
		
		for(int i = 0; i < pointCount; i++) {
            builtPoints.add(new Point(
                    r.nextDouble() * width,
                    r.nextDouble() * height));
        }
		
//		for(Point p : builtPoints) {
////          Should only be used once complexity is reduced
////			movePointByNetForces(p, points);
//
//			if(p.getX() >= width)
//				p.setX(width - 1);
//			if(p.getX() < 0)
//				p.setX(0);
//			if(p.getY() >= height)
//				p.setY(height - 1);
//			if(p.getY() < 0)
//				p.setY(0);
//		}

        ensureAllPointsWithinBounds(builtPoints,width,height);
		
		points.addAll(builtPoints);
//        Log.d(TAG, "Points: " + this.points.size());
		
		return points;
	}

    /*
     * Could be and Should be improved by using a graph for the points, and approximating the sum
     * of forces by only using a fixed number of nearby points. would change complexity from O(n^2) to O(n)
     */
	private void movePointByNetForces(Point ptMoved, Collection<Point> points) {
		double sumX = 0, sumY = 0;
		for(Point p : points) {
			double distX = ptMoved.getX() - p.getX(),
					distY = ptMoved.getY() - p.getY();
			sumX += (distX >= 0) ? ((stickiness) / (distX * distX)) : -((stickiness) / (distX * distX));
			sumY += (distY >= 0) ? ((stickiness) / (distY * distY)) : -((stickiness) / (distY * distY));
		}
		
		ptMoved.setX(sumX + ptMoved.getX());
		ptMoved.setY(sumY + ptMoved.getY());
	}
	
	public void setStickiness(double stickiness) {
		this.stickiness = stickiness;
	}
	
	public double getStickiness() {
		return this.stickiness;
	}
}
