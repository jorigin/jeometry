package org.jeometry.geom3D.primitive;

import java.io.Serializable;
import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;

/**
 * A set of {@link Line3D 3D lines}.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 *
 */
public interface LineSet3D<T extends Point3D> extends Serializable{

  /**
   * Get the vertices of the line set. Each vertex is a 3D point.
   * @return the vertices of the line set.
   */
  Point3DContainer<T> getVertices();

  /**
   * Return the {@link Line3D lines} that compose the line set.
   * @return a list containing all the segments of the line set.
   */
  List<Line3D<T>> getSegments();
  
  /**
   * Add a new point to the line set. A new line is created from the last plotted point and this one.
   * @param point the point to add as a line extremity.
   */
  void plot(T point);
}
