package com.pictopoly.polydemo.process.pointmaker;

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
        Collection<Point> newPoints = new LinkedList<Point>();

        addRandomPointsNearPreviousPoints(this.points,newPoints);

        double width = bitmapToBeProcessed.getWidth();
        double height = bitmapToBeProcessed.getHeight();

        // Make Sure all points
        ensureAllPointsWithinBounds(newPoints, bitmapToBeProcessed.getWidth(), bitmapToBeProcessed.getHeight());

        // For completeness, add corners
        addCorners(newPoints, width, height);

        this.points.addAll(newPoints);
//        Log.d(TAG, "RadiusMaker points: " + this.points.size());

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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int r) {
        radius = r;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int r) {
        ratio = r;
    }
}
