package com.pictopoly.polydemo.process;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

import com.pictopoly.polydemo.tri.DelaunayTriangulation;
import com.pictopoly.polydemo.tri.Point;
import com.pictopoly.polydemo.tri.Triangle;
import com.pictopoly.polydemo.tri.Triangulation;

public class ImageHandler {
	protected PointMaker pointMaker;
	protected Triangulation triangulation;
	protected Bitmap rawImage, processedImage;
    protected int[] rawPixels, processedPixels;
	
	public ImageHandler() {
		this.triangulation = new DelaunayTriangulation();
	}
	
	public ImageHandler(Bitmap bitmapToBeProcessed) {
        this.triangulation = new DelaunayTriangulation();
        setImage(bitmapToBeProcessed);
    }
	
	public void setImage(Bitmap bitmapToBeProcessed) {
        this.rawImage = bitmapToBeProcessed.copy(Bitmap.Config.ARGB_8888, true);

        this.rawPixels = new int[this.rawImage.getWidth() * this.rawImage.getHeight()];
        this.rawImage.getPixels(this.rawPixels,
                0,
                this.rawImage.getWidth(),
                0,
                0,
                this.rawImage.getWidth(),
                this.rawImage.getHeight());

        this.processedPixels = new int[this.rawPixels.length];
        System.arraycopy(this.rawPixels,
                0,
                this.processedPixels,
                0,
                this.rawPixels.length);

        this.pointMaker = new StickyPointMaker(bitmapToBeProcessed);
	}
	
	public Bitmap processImage() {
        Log.d(getClass().getSimpleName(), "pointMaker null? " + (pointMaker == null) + " rawImage null? " + (rawImage == null));
		this.triangulation = new DelaunayTriangulation(pointMaker.makePoints(rawImage));
		this.processedImage = renderTriangles(this.rawImage);
		return this.processedImage;
	}
	
	public Bitmap getProcessedImage() {
		return this.processedImage;
	}
	
	public Bitmap getRawImage() {
		return this.rawImage;
	}
	
	public Bitmap renderTriangles(Bitmap bitmapToBeRendered) {
		List<Triangle> triangles = this.triangulation.getTriangulation();
		TriangleRenderer.render(bitmapToBeRendered, triangles);
        return bitmapToBeRendered;
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
}