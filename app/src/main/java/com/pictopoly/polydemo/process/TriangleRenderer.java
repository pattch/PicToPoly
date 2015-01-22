package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.pictopoly.polydemo.tri.Point;
import com.pictopoly.polydemo.tri.Triangle;

import java.util.Collection;
import java.util.Iterator;

public class TriangleRenderer {
    protected static Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

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
        int colorSample = (map.getPixel((int)(triangle.getA().getX()), (int)(triangle.getA().getY())) +
                map.getPixel((int)(triangle.getB().getX()), (int)(triangle.getB().getY())) +
                map.getPixel((int)(triangle.getC().getX()), (int)(triangle.getC().getY())) +
                map.getPixel((int)(triangleCenterPoint.getX()), (int)(triangleCenterPoint.getY())))/4;
        paint.setColor(colorSample);

        // Draw to canvas
        canvas.drawPath(path, paint);
    }

    public static void render(Canvas canvas, Bitmap map, Collection<Triangle> triangles) {
        initPaint();
        for(Triangle t : triangles)
            renderTriangle(canvas, map, t);
    }

    public static void render(Bitmap map, Collection<Triangle> triangles) {
        Canvas c = new Canvas(map);
        render(c,map,triangles);
    }

    public static void render(Canvas canvas, Bitmap map, Iterator<Triangle> updatedTriangles) {
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
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
    }
}
