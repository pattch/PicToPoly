package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;
import android.util.Log;

import com.pictopoly.polydemo.tri.Point;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Marklar on 2/13/2015.
 */
public class GridPointMaker extends PixelPointMaker implements PointMaker {
    protected int numBoxes = 50;
    Collection<Point> points;
    protected Bitmap map;

    public GridPointMaker(Bitmap bitmapToBeProcessed) {
        this.map = bitmapToBeProcessed;
    }

    public Collection<Point> makePoints(Bitmap bitmapToBeProcessed) {
        this.points = new ArrayList<>();
        double mWidth = bitmapToBeProcessed.getWidth(),
                mHeight = bitmapToBeProcessed.getHeight(),
                gridSize = mWidth / numBoxes;
        for(double i = 0; i < mWidth; i = i + gridSize) {
            for(double j = 0; j < mHeight; j = j + gridSize) {
                this.points.add(new Point(i,j));                                 // Add the Grid Point
                if((i + (gridSize/2)) < mWidth && (j + (gridSize/2)) < mHeight)
                    this.points.add(new Point(i + (gridSize/2), j + (gridSize/2)));  // Add the Point in the middle
            }
        }

        ensureAllPointsWithinBounds(this.points,mWidth,mHeight);
        return this.points;
    }

    public Collection<Point> getPoints() {
        return this.points;
    }

    public void setBitmap(Bitmap bitmapToBeProcessed) {
        this.map = bitmapToBeProcessed.copy(Bitmap.Config.ARGB_8888, true);
    }
}
