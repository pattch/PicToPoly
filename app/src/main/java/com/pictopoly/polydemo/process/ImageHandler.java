package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.util.List;

import com.pictopoly.polydemo.tri.DelaunayTriangulation;
import com.pictopoly.polydemo.tri.Point;
import com.pictopoly.polydemo.tri.Triangle;
import com.pictopoly.polydemo.tri.Triangulation;

public class ImageHandler {
    private final String TAG = this.getClass().getSimpleName();
    public static String PICTURE_PATH = "/Pictures/PicToPoly/";
	protected PointMaker pointMaker;
	protected Triangulation triangulation;
	protected Bitmap rawImage, processedImage;
    protected int width, height;
	
	public ImageHandler() {
		this.triangulation = new DelaunayTriangulation();
	}
	
	public ImageHandler(Bitmap bitmapToBeProcessed) {
        this.triangulation = new DelaunayTriangulation();
        setImage(bitmapToBeProcessed);
    }
	
	public Bitmap setImage(Bitmap bitmapToBeProcessed) {
        this.rawImage = bitmapToBeProcessed.copy(Bitmap.Config.ARGB_8888, true);
        this.processedImage = rawImage.copy(Bitmap.Config.ARGB_8888, true);

        this.pointMaker = new RandomPointMaker(bitmapToBeProcessed);
        this.width = rawImage.getWidth();
        this.height = rawImage.getHeight();
        return this.rawImage;
	}
	
	public Bitmap processImage() {
        Log.d(getClass().getSimpleName(), "pointMaker null? " + (pointMaker == null) + " rawImage null? " + (rawImage == null));
		this.triangulation = new DelaunayTriangulation(pointMaker.makePoints(rawImage));
		this.processedImage = renderTriangles(this.rawImage);
        Log.d(TAG, "Finished Rendering");
		return this.processedImage;
	}
	
	public Bitmap getProcessedImage() {
		return this.processedImage;
	}
	
	public Bitmap getRawImage() {
		return this.rawImage;
	}
	
	public Bitmap renderTriangles(Bitmap bitmapToBeRendered) {
        Bitmap copy = bitmapToBeRendered.copy(Bitmap.Config.ARGB_8888, true);
		List<Triangle> triangles = this.triangulation.getTriangulation();
		TriangleRenderer.render(copy, triangles);
        return copy;
	}

	public void addPoint(Point point) {
        if(rawImage != null
                && point.getX() >= 0 && point.getY() >= 0
                && point.getX() < rawImage.getWidth() && point.getY() < rawImage.getHeight())
		    triangulation.insertPoint(point);
	}

	public Bitmap refreshTriangles() {
		TriangleRenderer.render(this.processedImage,this.rawImage,this.triangulation.getLastUpdatedTriangles());
		return this.processedImage;
	}

    public Bitmap rescale(int newWidth, int newHeight) {
        if(this.width != newWidth || this.height != newHeight)
            return setImage(getResizedBitmap(this.rawImage, newWidth, newHeight));
        return rawImage;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }
}