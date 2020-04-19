package org.jeometry.geom3D.point;

import org.jeometry.Jeometry;
import org.jeometry.math.Vector;

/**
 * A coordinates within a 3D space. For computational purposes, this representation is homogeneous and store an homogeneous factor.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface Coord3D extends Vector {
  /**
   * The index of the dimension of the X coordinates.
   * @see #DIMENSION_Y
   * @see #DIMENSION_Z
   * @see #DIMENSION_H
   */
  public static final int DIMENSION_X = 0;
  
  /**
   * The index of the dimension of the Y coordinates.
   * @see #DIMENSION_X
   * @see #DIMENSION_Z
   * @see #DIMENSION_H
   */
  public static final int DIMENSION_Y = 1;
  
  /**
   * The index of the dimension of the Z coordinates.
   * @see #DIMENSION_X
   * @see #DIMENSION_Y
   * @see #DIMENSION_H
   */
  public static final int DIMENSION_Z = 2;
  
  /**
   * The index of the dimension of the homogeneous coordinates.
   * @see #DIMENSION_X
   * @see #DIMENSION_Y
   * @see #DIMENSION_Z
   */
  public static final int DIMENSION_H = 3;

  /**
   * Get the point coordinate along the <i>X</i> axis.
   * @return the point coordinate along the <i>X</i> axis.
   * @see #getY()
   * @see #getZ()
   * @see #setX(double)
   */
  public double getX();
  
  /**
   * Set the point coordinate along the <i>X</i> axis.
   * @param x the point coordinate along the <i>X</i> axis.
   * @see #setY(double)
   * @see #setZ(double)
   * @see #getX()
   */
  public void setX(double x);
  

  /**
   * Get the point coordinate along the <i>Y</i> axis.
   * @return the point coordinate along the <i>Y</i> axis.
   * @see #getX()
   * @see #getZ()
   * @see #setY(double)
   */
  public double getY();
  
  /**
   * Set the point coordinate along the <i>Y</i> axis.
   * @param y the point coordinate along the <i>Y</i> axis.
   * @see #setX(double)
   * @see #setZ(double)
   * @see #getY()
   */
  public void setY(double y);
  
  /**
   * Get the point coordinate along the <i>Z</i> axis.
   * @return the point coordinate along the <i>Z</i> axis.
   * @see #getX()
   * @see #getY()
   * @see #setZ(double)
   */
  public double getZ();
  
  /**
   * Set the point coordinate along the <i>Z</i> axis.
   * @param z the point coordinate along the <i>Z</i> axis.
   * @see #setX(double)
   * @see #setY(double)
   * @see #getZ()
   */
  public void setZ(double z);
  
  /**
   * Get the point homogeneous coordinate.
   * @return the point homogeneous coordinate.
   * @see #getX()
   * @see #getY()
   * @see #getZ()
   * @see #setH(double)
   */
  public double getH();
  
  /**
   * Set the point homogeneous coordinate.
   * @param h the point homogeneous coordinate.
   * @see #setX(double)
   * @see #setY(double)
   * @see #setZ(double)
   * @see #getH()
   */
  public void setH(double h);
 
  
  /**
   * Compute the <a href="https://en.wikipedia.org/wiki/Norm_(mathematics)#Euclidean_norm">Eucliean norm</a> of the vector represented by these coordinates. 
   * Let <i>v</i>&nbsp;=&nbsp;(<i>x</i>, <i>y</i>, <i>z</i>) be a 3D vector, the Euclidean norm of <i>v</i>, denoted ||<i>v</i>|| is such that:<br>
   * $$||v||\ =\ \sqrt{x^{2}\ +\ y^{2}\ +\ z^{2}}$$
   * @return the Euclidean norm of the vector represented by these coordinates.
   * @see #normSquare()
   */
  @Override
  public double norm();
  
  /**
   * Compute the squared <a href="https://en.wikipedia.org/wiki/Norm_(mathematics)#Euclidean_norm">Eucliean norm</a> of the vector represented by these coordinates. 
   * Let <i>v</i>&nbsp;=&nbsp;(<i>x</i>, <i>y</i>, <i>z</i>) be a 3D vector, the Euclidean norm of <i>v</i>, denoted ||<i>v</i>|| is such that:<br>
   * $$||v||^{2}\ =\ x^{2}\ +\ y^{2}\ +\ z^{2}$$
   * @return the squared Euclidean norm of the vector represented by these coordinates.
   */
  @Override
  public double normSquare();
  
  /**
   * Divide all these coordinates by their {@link #norm() Euclidean norm}. When called on coordinates c&nbsp;=&nbsp;(<i>x</i>, <i>y</i>, <i>z</i>), this method modify them as follows:<br>
   * $$v\ =\ (\frac{x}{||c||},\ \frac{y}{||c||}, \ \frac{z}{||c||})$$
   * where \(||c||\) is the {@link #norm() norm} of <i>c</i>.
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
  public Coord3D orthogonal();
  
  /**
   * Set the given <code>result</code> coordinates with the normalized normalized coordinates that are orthogonal to these ones.
   * @param result the coordinates where the result has to be stored.
   * @return the given <code>result</code> coordinates with the normalized normalized coordinates that are orthogonal to these ones.
   * @see #orthogonal()
   * @throws IllegalArgumentException if the result vector length is less than 3.
   */
  @Override
  public Coord3D orthogonal(Vector result);
}
