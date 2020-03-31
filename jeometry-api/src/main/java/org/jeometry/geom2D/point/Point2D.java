package org.jeometry.geom2D.point;

import org.jeometry.Geometry;
import org.jeometry.geom2D.SpatialLocalization2D;

/**
 * This interface describes a point expressed within a two-dimensional <a href="https://en.wikipedia.org/wiki/Two-dimensional_space">Euclidean space</a>.
 * The coordinates of the point (x, y) are expressed within a <a href="https://en.wikipedia.org/wiki/Cartesian_coordinate_system">Cartesian coordinate system</a> and are stored as double values.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public interface Point2D extends Coord2D, SpatialLocalization2D {

  /**
   * The index of the dimension of the X coordinates.
   * @see #DIMENSION_Y
   */
  public static final int DIMENSION_X = 0;
  
  /**
   * The index of the dimension of the Y coordinates.
   * @see #DIMENSION_X
   */
  public static final int DIMENSION_Y = 1;
  
  /**
   * Get the point coordinate along the <i>X</i> axis.
   * @return the point coordinate along the <i>X</i> axis.
   * @see #getY()
   * @see #setX(double)
   */
  public double getX();
  
  /**
   * Set the point coordinate along the <i>X</i> axis.
   * @param x the point coordinate along the <i>X</i> axis.
   * @see #setY(double)
   * @see #getX()
   */
  public void setX(double x);
  

  /**
   * Get the point coordinate along the <i>Y</i> axis.
   * @return the point coordinate along the <i>Y</i> axis.
   * @see #getX()
   * @see #setY(double)
   */
  public double getY();
  
  /**
   * Set the point coordinate along the <i>Y</i> axis.
   * @param y the point coordinate along the <i>Y</i> axis.
   * @see #setX(double)
   * @see #getY()
   */
  public void setY(double y);
  
}
