package com.pictopoly.polydemo.process.PointMaker;

import com.pictopoly.polydemo.tri.Point;

import java.util.Collection;

/**
 * Created by Samuel on 1/23/2015.
 */
public abstract class PixelPointMaker implements PointMaker {
    public static int pointCount = 2500;
    protected Collection<Point> points;
    protected int width, height;

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

    protected void addCorners(Collection<Point> points, double width, double height) {
        points.add(new Point(0,0));
        points.add(new Point(0,height - 1));
        points.add(new Point(width - 1,0));
        points.add(new Point(width - 1, height - 1));
    }
}
