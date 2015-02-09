package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;
import android.util.Log;

import com.pictopoly.polydemo.tri.Point;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Marklar on 2/3/2015.
 */
public class RandomPointMaker extends RadiusPointMaker {
    private static int numberOfRandomPoints = 0;
    public RandomPointMaker(Bitmap bitmapToBeProcessed) {super(bitmapToBeProcessed);}

    @Override
    public Collection<Point> makePoints(Bitmap bitmapToBeProcessed) {
        super.makePoints(bitmapToBeProcessed);
        int width = bitmapToBeProcessed.getWidth();
        int height = bitmapToBeProcessed.getHeight();

        Random r = new Random();
        for(int i = this.points.size(); i < numberOfRandomPoints; i++) {
            double x = r.nextDouble() * width,
                    y = r.nextDouble() * height;

            this.points.add(new Point(x,y));
        }

        Log.d(getClass().getSimpleName(), "RandomMaker points: " + this.points.size());
        return this.points;
    }

    public static void setNumberOfRandomPoints(int r) {
        numberOfRandomPoints = r;
    }

    public static int getNumberOfRandomPoints() {
        return numberOfRandomPoints;
    }
}
