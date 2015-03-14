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
    protected int drawingState = 1;
    protected Bitmap mTriangleMap, mLineMap, mRawMap;

    protected final Paint paint = new Paint();
    //protected Rect surfaceBounds;
    protected ImageHandler handler;

    protected int mWidth, mHeight;
    protected float mScale, xPivot, yPivot;

    protected boolean changingSinglePoint = false;
    protected boolean addingPoints = true;
    protected boolean fillingScreen = false;

    public TriangleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        //surfaceBounds = new Rect(0,0,getWidth(),getHeight()); // Might be all 0's right here
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);

        if(handler.getProcessedImage() != null) {
            if(!paint.isFilterBitmap())
                paint.setFilterBitmap(true);

            // Initialize Maps
            initMaps();

            // Get The Scale
            mWidth = mTriangleMap.getWidth();
            mHeight = mTriangleMap.getHeight();
            if(!fillingScreen)
                mScale = (float)getMinRescaleFactor(getWidth(),getHeight(),mWidth,mHeight);
            else
                mScale = (float)getMaxRescaleFactor(getWidth(),getHeight(),mWidth,mHeight);
            mWidth *= mScale;
            mHeight *= mScale;

            // Update Maps Based on the new Image Scale
            // resizeMaps(mWidth,mHeight);

            // Make the Rectangle to use to draw the Map
            xPivot = (getWidth() - mWidth) / 2;
            yPivot = (getHeight() - mHeight) / 2;
            // Instead of resizing Bitmaps, Which causes memory leak, Scale the Canvas!!!
            canvas.save();
            canvas.scale(mScale, mScale, xPivot, yPivot);
            //surfaceBounds.set((int)(xPad / 2),(int)(yPad / 2),(int)(xPad / 2) + mWidth,(int)(yPad / 2) + mHeight);

            // Draw Triangles Based on the TriangleSurfaceView's State to canvas
            drawMapByState(canvas);
            canvas.restore();
        }
    }

    private void drawMapByState(Canvas canvas) {
        switch(drawingState) {
            case DRAW_TRIANGLES_TRANSPARENT:
                paint.setAlpha(255);
                canvas.drawBitmap(mRawMap, xPivot, yPivot, paint);
                paint.setAlpha(200);
                canvas.drawBitmap(mTriangleMap, xPivot, yPivot, paint);
                break;
            case DRAW_TRIANGLES_LINES:
                paint.setAlpha(255);
                canvas.drawBitmap(mTriangleMap, xPivot, yPivot, paint);
                paint.setAlpha(200);
                canvas.drawBitmap(mLineMap, xPivot, yPivot, paint);
                break;
            case DRAW_LINES:
                paint.setAlpha(255);
                canvas.drawBitmap(mRawMap, xPivot, yPivot, paint);
                canvas.drawBitmap(mLineMap, xPivot, yPivot, paint);
                break;
            case DRAW_RAW:
                paint.setAlpha(255);
                canvas.drawBitmap(mRawMap, xPivot, yPivot, paint);
                break;
            case DRAW_TRIANGLES_OPAQUE:
            default:
                paint.setAlpha(255);
                canvas.drawBitmap(mTriangleMap, xPivot, yPivot, paint);
                break;
        }
    }

    public void setImageHandler(ImageHandler handler) {
        this.handler = handler;
    }

    public void initMaps() {
        mTriangleMap = handler.getProcessedImage();
        mLineMap = handler.getLineImage();
        mRawMap = handler.getRawImage();
    }

    public void resizeMaps(int width, int height) {
        mTriangleMap = ImageHandler.getResizedBitmap(mTriangleMap,width,height);
        mLineMap = ImageHandler.getResizedBitmap(mLineMap,width,height);
        mRawMap = ImageHandler.getResizedBitmap(mRawMap,width,height);
    }

    public void flushMaps() {
        mTriangleMap.recycle();
        mLineMap.recycle();
        mRawMap.recycle();
    }

    private double getMinRescaleFactor(double containerWidth, double containerHeight, int imageWidth, int imageHeight) {
        double wScale = containerWidth / imageWidth,
                hScale = containerHeight / imageHeight;
        return Math.min(wScale,hScale);
    }

    private double getMaxRescaleFactor(double containerWidth, double containerHeight, int imageWidth, int imageHeight) {
        double wScale = containerWidth / imageWidth,
                hScale = containerHeight / imageHeight;
        return Math.max(wScale,hScale);
    }

    public void setImage(Bitmap triangleMap) {
        this.mTriangleMap = triangleMap;
    }

    public Bitmap getImage() {
        return this.mTriangleMap;
    }

    public boolean handleTouch(MotionEvent event) {
        Log.d(TAG,"adding pt (" + event.getX() + "," + event.getY() + ") - adding single? " + changingSinglePoint);
        //int offsetX = surfaceBounds.left,
        //        offsetY = surfaceBounds.top;
        double ptX = (int)(event.getX()/mScale),
            ptY = (int)(event.getY()/mScale);
        ptX -= xPivot;
        ptY -= yPivot;

        if(addingPoints)
            handler.addPoint(new Point(ptX, ptY));
        else
            handler.removePoint(new Point(ptX, ptY));
        handler.refreshTriangles();

        this.invalidate();
        return true;
    }

    public void setRenderType(int state) {
        if(drawingState >= 0 && state <= DRAW_RAW)
            drawingState = state;
        else
            drawingState = DRAW_TRIANGLES_OPAQUE;
    }

    public void changeRenderType() {
        setRenderType(drawingState * 2);
    }

    public boolean isChangingSinglePoint() {
        return changingSinglePoint;
    }

    public void setChangingSinglePoint(boolean changingSinglePoint) {
        this.changingSinglePoint = changingSinglePoint;
    }

    public void setAddingPoints(boolean addingPoints) {
        this.addingPoints = addingPoints;
    }

    public void setFillingScreen(boolean fillingScreen) {
        this.fillingScreen = fillingScreen;
    }
}
