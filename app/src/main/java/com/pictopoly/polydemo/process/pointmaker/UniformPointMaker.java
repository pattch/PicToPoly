package com.pictopoly.polydemo.process.PointMaker;

import android.graphics.Bitmap;

import com.pictopoly.polydemo.tri.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by Marklar on 2/14/2015.
 */
public class UniformPointMaker extends PixelPointMaker {
    protected Collection<Point> points = new ArrayList<>();
    protected int numberOfUniformPoints = 200;

    public void setBitmap(Bitmap bitmapToBeProcessed) {}

    @Override
    public Collection<Point> makePoints(Bitmap bitmapToBeProcessed) {
        this.points = new ArrayList<>();
        int numPoints = numberOfUniformPoints;
        int mWidth = bitmapToBeProcessed.getWidth(),
                mHeight = bitmapToBeProcessed.getHeight();
        Random r = new Random();
        for(int i = 0; i < numPoints; i++) {
            double x = r.nextDouble() * (mWidth - 1),
                    y = r.nextDouble() * (mHeight - 1);

            points.add(new Point(x,y));
        }

        addCorners(points,mWidth,mHeight);
        ensureAllPointsWithinBounds(points,mWidth,mHeight);

        return this.points;
    }

    public Collection<Point> getPoints() {
        return this.points;
    }
}
