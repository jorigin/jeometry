package org.jeometry.geom2D.point;

import org.jeometry.Geometry;
import org.jeometry.math.Vector;

/**
 * A coordinates within a 2D space.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public interface Coord2D extends Vector{
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
  
  /**
   * Compute the <a href="https://en.wikipedia.org/wiki/Norm_(mathematics)#Euclidean_norm">Eucliean norm</a> of the vector represented by these coordinates. 
   * Let <i>a</i>&nbsp;=&nbsp;(<i>x<sub>a</sub></i>, <i>y<sub>a</sub></i>) be a 2D vector, the Euclidean norm of <i>a</i>, denoted ||<i>a</i>|| is such that:<br>
   * $$||a||\ =\ \sqrt{x_{a}^{2}\ +\ y_{a}^{2}}$$
   * @return the Euclidean norm of the vector represented by these coordinates.
   * @see #normSquare()
   */
  @Override
  public double norm();
  
  /**
   * Compute the squared <a href="https://en.wikipedia.org/wiki/Norm_(mathematics)#Euclidean_norm">Eucliean norm</a> of the vector represented by these coordinates. 
   * Let <i>a</i>&nbsp;=&nbsp;(<i>x<sub>a</sub></i>, <i>y<sub>a</sub></i>) be a 2D vector, the Euclidean norm of <i>a</i>, denoted ||<i>a</i>|| is such that:<br>
   * $$||a||^{2}\ =\ x_{a}^{2}\ +\ y_{a}^{2}$$
   * @return the squared Euclidean norm of the vector represented by these coordinates.
   */
  @Override
  public double normSquare();
  
  /**
   * Divide all these coordinates by their {@link #norm() Euclidean norm}. When called on coordinates c&nbsp;=&nbsp;(<i>x</i>, <i>y</i>), this method modify them as follows:<br>
   * $$v\ =\ (\frac{x}{||p||},\ \frac{y}{||p||})$$
   * @see #norm()
   */
  @Override
  public void normalize();
  
  /**
   * Return normalized coordinates that are orthogonal to these ones.
   * @return a normalized coordinates that are orthogonal to these ones.
   * @see #orthogonal(Vector)
   */
  @Override
  public Coord2D orthogonal();
  
  /**
   * Set the given <code>result</code> coordinates with the normalized normalized coordinates that are orthogonal to these ones.
   * @param result the coordinates where the result has to be stored.
   * @return the given <code>result</code> coordinates with the normalized normalized coordinates that are orthogonal to these ones.
   * @see #orthogonal()
   * @throws IllegalArgumentException if the result vector length is less than 2.
   */
  @Override
  public Coord2D orthogonal(Vector result);
}
