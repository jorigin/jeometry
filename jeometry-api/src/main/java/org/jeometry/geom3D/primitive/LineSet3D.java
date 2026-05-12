package org.jeometry.geom3D.primitive;

import java.io.Serializable;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;

/**
 * A set of {@link Line3D 3D lines}.
 * @param <T> The type of the underlying 3D points
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
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
  List<Line3D<T>> getLines();
  
  /**
   * Add a new point to the line set. A new line is created from the last plotted point and this one.
   * @param point the point to add as a line extremity.
   */
  void plot(T point);
}
