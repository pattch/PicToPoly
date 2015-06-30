package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

import com.pictopoly.polydemo.tri.Point;
import com.pictopoly.polydemo.tri.Triangle;

import java.util.Collection;
import java.util.Iterator;

public class TriangleRenderer {
    private static String TAG = "TriangleRenderer";
    protected static Paint paint = new Paint();
//    protected static boolean fillPaint = true;

    /*
     * Don't let anywhere call the helper method to draw a single Triangle, that way we
     * don't have to check every time whether the paint object has been instantiated.
     *
     * @param canvas
     *      The canvas to render the Triangle to
     *
     * @param map
     *      The Bitmap object to read colors from
     *
     * @param triangle
     *      The triangle to be rendered
     */
    private static void renderTriangle(Canvas canvas, Bitmap map, Triangle triangle, boolean fillPaint) {
        if(map == null || triangle == null || triangle.getA() == null || triangle.getB() == null || triangle.getC() == null)
            return;

        // Set Path based on triangle
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo((float)(triangle.getA().getX()), (float)(triangle.getA().getY()));
        path.lineTo((float) (triangle.getB().getX()), (float) (triangle.getB().getY()));
        path.lineTo((float) (triangle.getC().getX()), (float) (triangle.getC().getY()));
        path.lineTo((float) (triangle.getA().getX()), (float) (triangle.getA().getY()));
        path.close();

        // Read Colors from map
        Point triangleCenterPoint = new Point((triangle.getA().getX() + triangle.getB().getX() + triangle.getC().getX())/3,
                (triangle.getA().getY() + triangle.getB().getY() + triangle.getC().getY())/3);

        int colorSample = map.getPixel((int)triangleCenterPoint.getX(), (int)triangleCenterPoint.getY());

        if(fillPaint) {
            paint.setColor(colorSample);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setXfermode(null);
            canvas.drawPath(path, paint);
        } else {
            // First We're going to wipe out whatever's below this Triangle
            paint.setStyle(Paint.Style.FILL);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawPath(path,paint);

            // Then We're going to draw the Triangle normally.
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setXfermode(null);
            canvas.drawPath(path, paint);
        }
    }

    public static void render(Canvas canvas, Bitmap map, Collection<Triangle> triangles, boolean fillPaint) {
//        Log.d(TAG, "Rendering Triangles");
        initPaint();
        for(Triangle t : triangles)
            renderTriangle(canvas, map, t, fillPaint);
    }

    public static void render(Bitmap map, Collection<Triangle> triangles) {
//        fillPaint = true;
        Canvas c = new Canvas(map);
        render(c,map,triangles, true);
    }

    public static void renderLines(Bitmap map, Collection<Triangle> triangles) {
        Canvas c = new Canvas(map);
        c.drawARGB(0,0,0,0);
        render(c, map, triangles, false);
    }

    public static void render(Canvas canvas, Bitmap map, Iterator<Triangle> updatedTriangles, boolean fillPaint) {
//        Log.d(TAG, "Rendering Triangles");
        initPaint();
        while(updatedTriangles.hasNext())
            renderTriangle(canvas,map,updatedTriangles.next(), fillPaint);
    }

    public static void render(Bitmap map, Iterator<Triangle> updatedTriangles, boolean fillPaint) {
        Canvas c = new Canvas(map);
        render(c,map,updatedTriangles, fillPaint);
    }

    public static void render(Bitmap processedMap, Bitmap rawMap, Iterator<Triangle> updatedTriangles) {
        Canvas c = new Canvas(processedMap);
        render(c,rawMap,updatedTriangles, true);
    }

    public static void renderLines(Bitmap lineMap, Bitmap rawMap, Iterator<Triangle> updatedTriangles) {
        Canvas c = new Canvas(lineMap);
        render(c,rawMap,updatedTriangles,false);
    }

    private static void initPaint() {
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAlpha(255);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
    }
}
