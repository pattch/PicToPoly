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
        canvas.drawColor(Color.BLACK);

        if(mTriangleMap != null) {
            if(!paint.isFilterBitmap())
                paint.setFilterBitmap(true);

            mTriangleMap = handler.rescale(getWidth(),getHeight());
            surfaceBounds.set(0,0,getWidth(),getHeight());

            canvas.drawBitmap(mTriangleMap, null, surfaceBounds, paint);
        }
    }

    public void setImage(Bitmap triangleMap) {
        this.mTriangleMap = triangleMap;
    }

    public Bitmap getImage() {
        return this.mTriangleMap;
    }

    public boolean handleTouch(MotionEvent event) {


        return true;
    }

    public void autoPopulate() {
        // TODO: Process the Image here
    }

//    public void renderTriangles(Canvas canvas) {
//
//    }
}
