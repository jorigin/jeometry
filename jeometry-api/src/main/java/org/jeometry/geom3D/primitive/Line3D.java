package org.jeometry.geom3D.primitive;

import java.io.Serializable;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;

/**
 * A class that describe a line that links two {@link Point3D 3D points}.
 * @param <T> the type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface Line3D<T extends Point3D> extends Serializable{

  /**
   * Get the vertices of the line. A line has two 3D points as vertices.
   * @return the {@link org.jeometry.geom3D.point.Point3DContainer vertices} of the line.
   */
  Point3DContainer<T> getVertices();

  /**
   * Return the first extremity of the line.
   * @return the first extremity of the line.
   */
  T getEnd1();


  /**
   * Return the second extremity of the line.
   * @return the second extremity of the line.
   */
  T getEnd2();

  
}
