package com.pictopoly.polydemo.tri;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface Triangulation {
	/**
	 * Insert the point to the Triangulation. If p is null or
	 * already is contained in this Triangulation, p should be ignored.
	 * 
	 * @param p
	 *            	The new vertex to be inserted into the Triangulation
	 */
	public void insertPoint(Point p);
	
	/**
	 * Insert the Collection of points into the Triangulation. If a
	 * point in the Collection is null or already is contained in this
	 * Triangulation, the point should be ignored
	 * 
	 * @param points
	 * 				Set of points to be inserted into the Triangulation
	 */
	public void insertPoints(Collection<Point> points);
	
	/**
	 * Returns the current Triangulation as a List of Triangles
	 * 
	 * @return
	 * 				
	 */
	public List<Triangle> getTriangulation();
	
	/**
	 * Returns an Iterator object related to the last update.
	 * 
	 * @return 
	 * 				Iterator to all triangles involved in the last update of the
	 *         		Triangulation 
	 */
	public Iterator<Triangle> getLastUpdatedTriangles();
	
	/**
	 * Deletes the given point from this Triangulation.
	 * 
	 * @param pointToDelete
	 *            	The given point to delete.
	 */
	public void deletePoint(Point pointToDelete);
	
	/**
	 * return a point from the Triangulation that is closest to pointToDelete
	 * 
	 * @param pointToDelete
	 *            	the point that the user wants to delete
	 * @return 
	 * 				The point from the Triangulation that is closest to 
	 * 				the given Point
	 */
	public Point findClosePoint(Point pointToDelete);
	
	/**
	 * Returns the number of unique vertices in this Triangulation.
	 * 
	 * @return 
	 * 				The number of vertices in the Triangulation, duplicates 
	 * 				should be ignored.
	 */
	public int size();
	
	/**
	 * Returns the number of triangles currently in the Triangulation.
	 * 
	 * @return 
	 * 				The number of triangles in the Triangulation.
	 */
	public int trianglesSize();
	
	/**
	 * Returns the Bounding Box for this Triangulation
	 * 
	 * @return
	 * 				This Triangulation's BoundingBox
	 */
	public BoundingBox getBoundingBox();
}
