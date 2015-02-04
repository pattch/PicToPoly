package com.pictopoly.polydemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.pictopoly.polydemo.process.ImageHandler;
import com.pictopoly.polydemo.tri.Point;

/**
 * Created by Marklar on 1/26/2015.
 */
public class TriangleSurfaceView extends SurfaceView {
    private final String TAG = this.getClass().getSimpleName();
    protected Bitmap mTriangleMap;
    protected final Paint paint = new Paint();
    protected Rect surfaceBounds;
    protected ImageHandler handler = ImageLayerHandler.getInstance().getProcessor();

    public TriangleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        surfaceBounds = new Rect(0,0,getWidth(),getHeight()); // Might be all 0's right here
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);

        if(mTriangleMap != null) {
            if(!paint.isFilterBitmap())
                paint.setFilterBitmap(true);

            mTriangleMap = handler.getProcessedImage();
            int width = mTriangleMap.getWidth(),
                    height = mTriangleMap.getHeight();

            double scale = getRescaleFactor(getWidth(),getHeight(),width,height);
            width *= scale;
            height *= scale;

            mTriangleMap = ImageHandler.getResizedBitmap(handler.getProcessedImage(),(int)width,(int)height);
            double xPad = getWidth() - width,
                    yPad = getHeight() - height;
            surfaceBounds.set((int)(xPad / 2),(int)(yPad / 2),(int)(xPad / 2) + width,(int)(yPad / 2) + height);

            canvas.drawBitmap(mTriangleMap, null, surfaceBounds, paint);
        }
    }

    private double getRescaleFactor(double containerWidth, double containerHeight, int imageWidth, int imageHeight) {
        double wScale = containerWidth / imageWidth,
                hScale = containerHeight / imageHeight;
        return Math.min(wScale,hScale);
    }

    public void setImage(Bitmap triangleMap) {
        this.mTriangleMap = triangleMap;
    }

    public Bitmap getImage() {
        return this.mTriangleMap;
    }

    public boolean handleTouch(MotionEvent event) {
        int offsetX = surfaceBounds.centerX() - (surfaceBounds.width() / 2),
                offsetY = surfaceBounds.centerY() - (surfaceBounds.height() / 2);
        handler.addPoint(new Point(event.getX() - offsetX,event.getY() - offsetY));
        handler.refreshTriangles();
        this.invalidate();
        return true;
    }
}
