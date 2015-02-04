package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.pictopoly.polydemo.tri.Point;
import com.pictopoly.polydemo.tri.Triangle;

import java.util.Collection;
import java.util.Iterator;

public class TriangleRenderer {
    private static String TAG = "TriangleRenderer";
    protected static Paint paint = new Paint();

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
    private static void renderTriangle(Canvas canvas, Bitmap map, Triangle triangle) {
        if(map == null || triangle == null || triangle.getA() == null || triangle.getB() == null || triangle.getC() == null)
            return;

//        Log.d("TriangleRenderer",
//                "Map null?" + (null == map) + " Map mutable? " + (map.isMutable()) +
//                "Triangle null? " + (null == triangle));

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
        Point[] pts = new Point[] {triangle.getA(), triangle.getB(), triangle.getC(), triangleCenterPoint};

        int colorSample, redSample=0, blueSample=0, greenSample=0;
        for(Point p : pts) {
            colorSample = map.getPixel((int)(p.getX()), (int)(p.getY()));
            redSample += Color.red(colorSample);
            blueSample += Color.blue(colorSample);
            greenSample += Color.green(colorSample);
        }

        colorSample = Color.rgb(redSample / pts.length,
                greenSample / pts.length,
                blueSample / pts.length);

        paint.setColor(colorSample);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, paint);
    }

    public static void render(Canvas canvas, Bitmap map, Collection<Triangle> triangles) {
        Log.d(TAG, "Rendering Triangles");
        initPaint();
        for(Triangle t : triangles)
            renderTriangle(canvas, map, t);
    }

    public static void render(Bitmap map, Collection<Triangle> triangles) {
        Canvas c = new Canvas(map);
        render(c,map,triangles);
    }

    public static void render(Canvas canvas, Bitmap map, Iterator<Triangle> updatedTriangles) {
        Log.d(TAG, "Rendering Triangles");
        initPaint();
        while(updatedTriangles.hasNext())
            renderTriangle(canvas,map,updatedTriangles.next());
    }

    public static void render(Bitmap map, Iterator<Triangle> updatedTriangles) {
        Canvas c = new Canvas(map);
        render(c,map,updatedTriangles);
    }

    public static void render(Bitmap processedMap, Bitmap rawMap, Iterator<Triangle> updatedTriangles) {
        Canvas c = new Canvas(processedMap);
        render(c,rawMap,updatedTriangles);
    }

    private static void initPaint() {
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
    }
}
