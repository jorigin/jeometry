package org.jeometry.geom3D.primitive;

import java.io.Serializable;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;


/**
 * A polygon with vertices expressed within a 3D space.
 * @param <T> the type of the underlying 3D points
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
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
   * Return the hull of the polygon as a {@link LineSet3D 3D line set}. 
   * Polygon hull is made of all the lines that link consecutive vertices.
   * @return Vector a Vector containing all the edges of a polygon
   * @see LineSet3D
   */
  LineSet3D<T> getHull();


  /**
   * Reverse the order of the vertices that define the polygon.
   */
  void inverseVerticesOrder();

}
