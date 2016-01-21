package com.pictopoly.polydemo.process.pointmaker;

import android.graphics.Bitmap;

import com.pictopoly.polydemo.process.handler.ImageHandler;
import com.pictopoly.polydemo.tri.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by Marklar on 2/14/2015.
 */
public class UniformPointMaker extends PixelPointMaker {
    protected Collection<Point> points = new ArrayList<Point>();
    protected int numberOfUniformPoints = 200;

    public void setBitmap(Bitmap bitmapToBeProcessed) {}

    @Override
    public Collection<Point> makePoints(ImageHandler handler) {
        this.points = new ArrayList<Point>();
        int numPoints = numberOfUniformPoints;
        int mWidth = handler.getWidth(),
                mHeight = handler.getHeight();
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

    public void setNumberOfPoints(int i) {
        this.numberOfUniformPoints = i;
    }

    public int getNumberOfPoints() {
        return this.numberOfUniformPoints;
    }
}
