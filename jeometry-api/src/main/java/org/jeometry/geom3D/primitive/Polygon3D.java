package org.jeometry.geom3D.primitive;

import java.io.Serializable;

import org.jeometry.Geometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;


/**
 * A polygon with vertices expressed within a 3D space.
 * @param <T> the type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 *
 */
public interface Polygon3D<T extends Point3D> extends Serializable{
  
  /**
   * Get the vertices of the polygon. Each vertex is a {@link Point3D 3D point}.
   * @return the vertices of the polygon
   */
  Point3DContainer<T> getVertices();

  /**
   * Set the vertices of the polygon. Each vertex is a 3D point.
   * @param vertices the vertices of the polygon. Each vertex is a {@link Point3D 3D point}.
   */
  void setVertices(Point3DContainer<T> vertices);
  
  /**
   * Return the segments of the polygon as a 3D line set. 
   * Polygon segments are the lines that link consecutive vertices.
   * @return Vector a Vector containing all the edges of a polygon
   * @see Line3D
   */
  LineSet3D<T> getSegments();


  /**
   * Reverse the order of the vertices that define the polygon.
   */
  void inverseVerticesOrder();

}
