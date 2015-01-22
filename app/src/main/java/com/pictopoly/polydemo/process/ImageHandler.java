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
	
	public ImageHandler() {
		this.triangulation = new DelaunayTriangulation();
	}
	
	public ImageHandler(Bitmap bitmapToBeProcessed) {
        this.triangulation = new DelaunayTriangulation();
        this.pointMaker = new StickyPointMaker(bitmapToBeProcessed);
        this.rawImage = bitmapToBeProcessed.copy(Bitmap.Config.ARGB_8888, true);
    }
	
	public void setImage(Bitmap bitmapToBeProcessed) {
		this.rawImage = bitmapToBeProcessed.copy(Bitmap.Config.ARGB_8888, true);
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
		triangulation.insertPoint(point);
	}

	public Bitmap refreshTriangles() {
		TriangleRenderer.render(this.processedImage,this.rawImage,this.triangulation.getLastUpdatedTriangles());
		return this.processedImage;
	}
}