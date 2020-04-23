package org.jeometry.factory;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.math.Vector;

/**
 * An interface that describes a point builder. A point builder enables to create implementations of interfaces described within the <code>point</code> packages ({@link Point3D}, {@link Point2D}, {@link Point3DContainer}, ...).
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public interface PointBuilder {
	/**
	 * Create a new instance of {@link Vector} according to the given dimension.
	 * @param dimensions the number of dimensions of the point
	 * @param defaultValue the default values of all coordinates
	 * @return a new instance of {@link Vector}
	 */
	public Vector createPoint(int dimensions, double defaultValue);

	/**
	 * Create a new instance of {@link Point2D}. 
	 * @return a new instance of {@link Point2D}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public Point2D createPoint2D();

	/**
	 * Create a new instance of {@link Point2D}. 
	 * The coordinates of the new point are given in parameter
	 * @param x the point coordinate along the <i>X</i> axis
	 * @param y the point coordinate along the <i>Y</i> axis
	 * @return a new instance of {@link Point2D}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public Point2D createPoint2D(double x, double y);

	/**
	 * Create a new instance of {@link Point2D} that is a copy of the point given in parameter. 
	 * @param point the point to copy
	 * @return a new instance of {@link Point2D} that is a copy of the point given in parameter
	 */
	public Point2D createPoint2D(Point2D point);
	
	/**
	 * Create a new instance of {@link Point2DContainer}. 
	 * @return a new instance of {@link Point2DContainer}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public Point2DContainer createPoint2DContainer();

	/**
	 * Create a new instance of {@link Point2DContainer}. 
	 * @param capacity the initial capacity of the container (if reliable)
	 * @return a new instance of {@link Point2DContainer} 
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public Point2DContainer createPoint2DContainer(int capacity);

	/**
	 * Create a new instance of {@link Point3D}. 
	 * @return a new instance of {@link Point3D} 
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public Point3D createPoint3D();

	/**
	 * Create a new instance of {@link Point3D}. 
	 * The coordinates of the new point are given in parameter
	 * @param x the point coordinate along the <i>X</i> axis
	 * @param y the point coordinate along the <i>Y</i> axis
	 * @param z the point coordinate along the <i>Z</i> axis
	 * @return a new instance of {@link Point3D}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public Point3D createPoint3D(double x, double y, double z);

	/**
	 * Create a new instance of {@link Point3D} that is a copy of the point given in parameter. 
	 * @param point the point to copy
	 * @return a new instance of {@link Point3D} that is a copy of the point given in parameter
	 */
	public Point3D createPoint3D(Point3D point);
	
	/**
	 * Create a new instance of {@link Point3DContainer}. 
	 * @param <T> The type of the 3D points
	 * @return a new instance of {@link Point3DContainer}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public <T extends Point3D> Point3DContainer<T> createPoint3DContainer();

	/**
	 * Create a new instance of {@link Point3DContainer}.
	 * @param <T> The type of the 3D points
	 * @param capacity the initial capacity of the container (if reliable)
	 * @return a new instance of {@link Point3DContainer}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public <T extends Point3D> Point3DContainer<T> createPoint3DContainer(int capacity);

}
