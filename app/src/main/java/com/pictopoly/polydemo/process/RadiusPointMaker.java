package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;
import android.util.Log;

import com.pictopoly.polydemo.tri.Point;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Marklar on 1/29/2015.
 */
public class RadiusPointMaker extends EdgePointMaker {
    private final String TAG = this.getClass().getSimpleName();
    private static int ratio = 3;
    private static int radius = 200;

    public RadiusPointMaker(Bitmap bitmapToBeProcessed) {
        super(bitmapToBeProcessed);
    }

    @Override
    public Collection<Point> makePoints(Bitmap bitmapToBeProcessed) {
        super.makePoints(bitmapToBeProcessed);              // Stores to super.points, clears previous points
        Collection<Point> newPoints = new LinkedList<>();

        addRandomPointsNearPreviousPoints(this.points,newPoints);

        double width = bitmapToBeProcessed.getWidth();
        double height = bitmapToBeProcessed.getHeight();

        // Make Sure all points
        ensureAllPointsWithinBounds(newPoints, bitmapToBeProcessed.getWidth(), bitmapToBeProcessed.getHeight());

        // For completeness, add corners
        addCorners(newPoints, width, height);

        this.points.addAll(newPoints);
        Log.d(TAG, "RadiusMaker points: " + this.points.size());

        return this.points;
    }

    /**
     * Pre-condition:           previousPoints, newPoints have been initialized
     * Post-condition:
     *
     * @param previousPoints    The reference points to add points near to
     * @param newPoints         Where the new points will be populated
     */
    private void addRandomPointsNearPreviousPoints(Collection<Point> previousPoints, Collection<Point> newPoints) {
        Random r = new Random();
        for(Point p : previousPoints) {
            double referenceX = p.getX();
            double referenceY = p.getY();
            for(int i = 0; i < ratio; i++) {
                double newX = r.nextGaussian() * radius;
                double newY = r.nextGaussian() * radius;
                newPoints.add(new Point(referenceX + newX, referenceY + newY));
            }
        }
    }

    protected void addCorners(Collection<Point> points, double width, double height) {
        points.add(new Point(0,0));
        points.add(new Point(0,height - 1));
        points.add(new Point(width - 1,0));
        points.add(new Point(width - 1, height - 1));
    }

    /**
     * Pre-condition:   Points is non-null, width and height are the dimensions of a Bitmap object
     * Post-condition:  Every point is within 0..(width-1) and 0..(height-1) in order to be within
     *                  the proper bounds of a Bitmap object.
     *
     * @param points    The set of Points to be checked
     * @param width     The width constraint to be checked against
     * @param height    The height constraint to be checked against
     */
    protected void ensureAllPointsWithinBounds(Collection<Point> points, double width, double height) {
        double localWidth = width;
        double localHeight = height;
        for(Point p : points) {
            double x = p.getX(), y = p.getY();
            if(x < 0)
                p.setX(0);
            else if(x >= localWidth)
                p.setX(localWidth - 1);

            if(y < 0)
                p.setY(0);
            else if(y >= localHeight)
                p.setY(localHeight - 1);
//            if(x < 0 || y < 0 || x >= localWidth || y >= localHeight)
//                points.remove(p);
        }
    }

    @Override
    public Collection<Point> makePoints(int[] pixels, int width, int height) {
        return null;
    }

    public static int getRadius() {
        return radius;
    }

    public static void setRadius(int r) {
        radius = r;
    }

    public static int getRatio() {
        return ratio;
    }

    public static void setRatio(int r) {
        ratio = r;
    }
}
