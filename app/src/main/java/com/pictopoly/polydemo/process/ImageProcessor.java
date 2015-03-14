package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.*;
import android.os.Process;
import android.util.Log;

import java.util.List;

import com.pictopoly.polydemo.tri.DelaunayTriangulation;
import com.pictopoly.polydemo.tri.Point;
import com.pictopoly.polydemo.tri.Triangle;
import com.pictopoly.polydemo.tri.Triangulation;

public class ImageProcessor extends NotifyingRunnable {
    private final String TAG = this.getClass().getSimpleName();
    public static String PICTURE_PATH = "/Pictures/PicToPoly/";
	protected PointMaker pointMaker;
	protected Triangulation triangulation;
	protected Bitmap rawImage, processedImage, lineImage;
    protected int width, height;

    public static final boolean EXTRA_IMAGES = false;
	
	public ImageProcessor() {
		this.triangulation = new DelaunayTriangulation();
	}
	
	public ImageProcessor(Bitmap bitmapToBeProcessed) {
        setImage(bitmapToBeProcessed);
    }
	
	public Bitmap setImage(Bitmap bitmapToBeProcessed) {
        this.flush();
        this.rawImage = bitmapToBeProcessed.copy(Bitmap.Config.ARGB_8888, true);
        this.processedImage = rawImage.copy(Bitmap.Config.ARGB_8888, true);
        this.lineImage = rawImage.copy(Bitmap.Config.ARGB_8888, true);
        this.pointMaker = new RandomPointMaker(bitmapToBeProcessed);
        this.pointMaker = new GridPointMaker(bitmapToBeProcessed);
        this.width = rawImage.getWidth();
        this.height = rawImage.getHeight();
        return this.rawImage;
	}

    @Override
    public void doRun() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        processImage();
    }
	
	public Bitmap processImage() {
        if(rawImage != null) {
            triangulation = new DelaunayTriangulation(pointMaker.makePoints(rawImage));
            this.processedImage = renderTriangles(this.rawImage);
            this.lineImage = renderLines(this.rawImage);
            Log.d(TAG, "Finished Processing");
            return this.processedImage;
        } else return null;
	}
	
	public Bitmap getProcessedImage() {
		return this.processedImage;
	}

    public Bitmap getLineImage() {
        return this.lineImage;
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

    public Bitmap renderLines(Bitmap bitmapToBeRendered) {
        Bitmap copy = bitmapToBeRendered.copy(Bitmap.Config.ARGB_8888, true); // I don't think we even care about the background image here.
//        Bitmap blank = Bitmap.createBitmap(bitmapToBeRendered.getWidth(),bitmapToBeRendered.getHeight(), Bitmap.Config.ARGB_8888);
        List<Triangle> triangles = this.triangulation.getTriangulation();
        TriangleRenderer.renderLines(copy, triangles);
        return copy;
    }

	public void addPoint(Point point) {
        if(rawImage != null
                && point.getX() >= 0 && point.getY() >= 0
                && point.getX() < rawImage.getWidth() && point.getY() < rawImage.getHeight())
		    triangulation.insertPoint(point);
	}

    // Needs Work!
    public void removePoint(Point point) {
        if(rawImage != null
                && point.getX() >= 0 && point.getY() >= 0
                && point.getX() < rawImage.getWidth() && point.getY() < rawImage.getHeight()) {
            Point p = triangulation.findClosePoint(point);
            if(p != null)
                triangulation.deletePoint(p);
        }
    }

	public Bitmap refreshTriangles() {
        if(rawImage != null) {
            TriangleRenderer.render(this.processedImage, this.rawImage, this.triangulation.getLastUpdatedTriangles());
            TriangleRenderer.renderLines(this.lineImage, this.rawImage, this.triangulation.getLastUpdatedTriangles());
//        this.processedImage = renderTriangles(this.rawImage);
//        this.lineImage = renderLines(this.rawImage);
            return this.processedImage;
        } else return null;
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

    public void flush() {
        this.triangulation = new DelaunayTriangulation();
        if(this.rawImage != null) {
            this.rawImage.recycle();
            this.rawImage = null;
        }
        if(this.processedImage != null) {
            this.processedImage.recycle();
            this.processedImage = null;
        }
        if(this.lineImage != null) {
            this.lineImage.recycle();
            this.lineImage = null;
        }
    }

    public void setPointMaker(PointMaker pointMaker) {
        this.pointMaker = pointMaker;
    }

    public PointMaker getPointMaker() {
        return this.pointMaker;
    }


}