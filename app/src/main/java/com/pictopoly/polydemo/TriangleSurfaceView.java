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
    public static final int BG_COLOR = Color.DKGRAY;
    public static final int DRAW_TRIANGLES_OPAQUE = 1;
    public static final int DRAW_TRIANGLES_TRANSPARENT = 2;
    public static final int DRAW_TRIANGLES_LINES = 4;
    public static final int DRAW_LINES = 8;
    public static final int DRAW_RAW = 16;
    protected static int drawingState = 1;
    protected Bitmap mTriangleMap, mLineMap, mRawMap;

    protected final Paint paint = new Paint();
    protected Rect surfaceBounds;
    protected ImageHandler handler = ImageLayerHandler.getInstance().getProcessor();

    protected int mWidth, mHeight;
    protected double mScale;

    protected boolean changingSinglePoint = false;

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

            // Initialize Maps
            initMaps();

            // Get The Scale
            mWidth = mTriangleMap.getWidth();
            mHeight = mTriangleMap.getHeight();
            mScale = getRescaleFactor(getWidth(),getHeight(),mWidth,mHeight);
            mWidth *= mScale;
            mHeight *= mScale;

            // Update Maps Based on the new Image Scale
            resizeMaps(mWidth,mHeight);

            // Make the Rectangle to use to draw the Map
            double xPad = getWidth() - mWidth,
                    yPad = getHeight() - mHeight;
            surfaceBounds.set((int)(xPad / 2),(int)(yPad / 2),(int)(xPad / 2) + mWidth,(int)(yPad / 2) + mHeight);

            // Draw Triangles Based on the TriangleSurfaceView's State to canvas
            drawMapByState(canvas);
        }
    }

    private void drawMapByState(Canvas canvas) {
        switch(drawingState) {
            case DRAW_TRIANGLES_TRANSPARENT:
                paint.setAlpha(255);
                canvas.drawBitmap(mRawMap, null, surfaceBounds, paint);
                paint.setAlpha(200);
                canvas.drawBitmap(mTriangleMap, null, surfaceBounds, paint);
                break;
            case DRAW_TRIANGLES_LINES:
                paint.setAlpha(255);
                canvas.drawBitmap(mTriangleMap, null, surfaceBounds, paint);
                paint.setAlpha(200);
                canvas.drawBitmap(mLineMap, null, surfaceBounds, paint);
                break;
            case DRAW_LINES:
                paint.setAlpha(255);
                canvas.drawBitmap(mLineMap, null, surfaceBounds, paint);
                break;
            case DRAW_RAW:
                paint.setAlpha(255);
                canvas.drawBitmap(mRawMap, null, surfaceBounds, paint);
                break;
            case DRAW_TRIANGLES_OPAQUE:
            default:
                paint.setAlpha(255);
                canvas.drawBitmap(mTriangleMap, null, surfaceBounds, paint);
                break;
        }
    }

    private void initMaps() {
        mTriangleMap = handler.getProcessedImage();
        mLineMap = handler.getLineImage();
        mRawMap = handler.getRawImage();
    }

    private void resizeMaps(int width, int height) {
        mTriangleMap = ImageHandler.getResizedBitmap(mTriangleMap,width,height);
        mLineMap = ImageHandler.getResizedBitmap(mLineMap,width,height);
        mRawMap = ImageHandler.getResizedBitmap(mRawMap,width,height);
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
        Log.d(TAG,"adding pt (" + event.getX() + "," + event.getY() + ") - adding single? " + changingSinglePoint);
        int offsetX = surfaceBounds.left,
                offsetY = surfaceBounds.top;
        double ptX = (int)(event.getX()/mScale),
            ptY = (int)(event.getY()/mScale);
        ptX -= offsetX;
        ptY -= offsetY;
        handler.addPoint(new Point(ptX, ptY));
        handler.refreshTriangles();
        this.invalidate();
        return true;
    }

    public static void setRenderType(int state) {
        if(drawingState >= 0 && state <= DRAW_RAW)
            drawingState = state;
        else
            drawingState = DRAW_TRIANGLES_OPAQUE;
    }

    public static void changeRenderType() {
        setRenderType(drawingState * 2);
    }

    public boolean isChangingSinglePoint() {
        return changingSinglePoint;
    }

    public void setChangingSinglePoint(boolean changingSinglePoint) {
        this.changingSinglePoint = changingSinglePoint;
    }
}
