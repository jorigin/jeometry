package org.jeometry.geom3D.point;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.math.Vector;

/**
 * This interface describes a point expressed within a three-dimensional <a href="https://en.wikipedia.org/wiki/Three-dimensional_space">Euclidean space</a>.
 * The coordinates of the point (x, y and z) are expressed within a <a href="https://en.wikipedia.org/wiki/Cartesian_coordinate_system">Cartesian coordinate system</a> and are stored as double values.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface Point3D extends Coord3D, SpatialLocalization3D {
	
  /**
   * Return a normalized point that is orthogonal to this one.
   * @return a normalized point that is orthogonal to this one.
   * @see #orthogonal(Vector)
   */
  @Override
  public Point3D orthogonal();
  
  /**
   * Set the given <code>result</code> point with the normalized point that is orthogonal to this one.
   * @param result the point where the result has to be stored.
   * @return the given <code>result</code> point with the normalized point that is orthogonal to this one.
   * @see #orthogonal()
   * @throws IllegalArgumentException if the <code>result</code> vector length is not equals to 3.
   */
  @Override
  public Point3D orthogonal(Vector result);
  
  /**
   * Set the coordinates values of this point according to the given ones.
   * @param x the X coordinate
   * @param y the Y coordinate
   * @param z the Z coordinate
   */
  public void setValues(double x, double y, double z);
  
  /**
   * Set this 3D point coordinates as a copy of those from the given 3D point.
   * @param point the 3D point to copy
   */
  public void setValues(Point3D point);
  
  /**
   * Compute the <a href="https://en.wikipedia.org/wiki/Cross_product">cross product</a> between this point and the given one. 
   * The cross product, denoted <i>x</i> is such that for two point 
   * <i>a</i>&nbsp;=&nbsp;(<i>x<sub>a</sub></i>, <i>y<sub>a</sub></i>, <i>z<sub>a</sub></i>) 
   * and 
   * <i>b</i>&nbsp;=&nbsp;(<i>x<sub>b</sub></i>, <i>y<sub>b</sub></i>, <i>z<sub>b</sub></i>) 
   * we have:<br>
   * $$a\ \times\ b\ =\ ( y_a\ \times\ z_b\ -\ z_a\ \times\ y_b,\ -x_a\ \times\ z_b\ +\ z_a\ \times\ x_b,\ -x_a\ \times\ y_b\ -\ y_a\ \times\ x_b)$$
   * The creation of the cross product result as an instance of {@link Point3D} is delegated to implementations.
   * @param point the second operand of the cross product.
   * @return the cross product between this point and the given one.
   * @see #cross(Point3D, Point3D)
   */
  public Point3D cross(Point3D point);

  /**
   * Compute the <a href="https://en.wikipedia.org/wiki/Cross_product">cross product</a> between this point and the given one. 
   * The cross product, denoted <i>x</i> is such that for two point 
   * <i>a</i>&nbsp;=&nbsp;(<i>x<sub>a</sub></i>, <i>y<sub>a</sub></i>, <i>z<sub>a</sub></i>) 
   * and 
   * <i>b</i>&nbsp;=&nbsp;(<i>x<sub>b</sub></i>, <i>y<sub>b</sub></i>, <i>z<sub>b</sub></i>) 
   * we have:<br>
   * $$a\ \times\ b\ =\ ( y_a\ \times\ z_b\ -\ z_a\ \times\ y_b,\ -x_a\ \times\ z_b\ +\ z_a\ \times\ x_b,\ -x_a\ \times\ y_b\ -\ y_a\ \times\ x_b)$$
   * The result the cross product is stored within the given <code>result</code> {@link Point3D}. The return value of this method is a reference on the <code>result</code> parameter. 
   * If the parameter is <code>null</code>, a new point is created and returned as the cross product result. The instantiation of the result is delegated to implementations.
   * @param point the second operand of the cross product.
   * @param result the result of the cross product.
   * @return the cross product between this point and the given one.
   * @see #cross(Point3D, Point3D)
   * @see #dot(Point3D)
   */
  public Point3D cross(Point3D point, Point3D result);
  
  /**
   * Compute the <a href="https://en.wikipedia.org/wiki/Dot_product">dot product</a>, or scalar product, of the vector represented by this point and the given one.
   * The dot product, denoted &bull;, is such that for two points
   * <i>a</i>&nbsp;=&nbsp;(<i>x<sub>a</sub></i>, <i>y<sub>a</sub></i>, <i>z<sub>a</sub></i>) 
   * and 
   * <i>b</i>&nbsp;=&nbsp;(<i>x<sub>b</sub></i>, <i>y<sub>b</sub></i>, <i>z<sub>b</sub></i>) 
   * we have:<br>
   * $$a\ \boldsymbol{\cdot}\ b\ =\ x_a\ \times\ x_b\ +\ y_a\ \times\ y_b + z_a\ \times\ z_b $$
   * We can denote that the dot product can also be expressed by:<br>
   * $$a\ \boldsymbol{\cdot}\ b\ =\ ||a||\ \times\ ||b||\ \times\ cos(\varphi)$$
   * where ||<i>a</i>|| is the {@link #norm() Euclidean norm} of <i>a</i> and &phi; is the angle between the vectors <i>a</i> and <i>b</i>.
   * @param point the second operand of the dot product.
   * @return the dot product between this point and the given one.
   * @see #cross(Point3D)
   * @see #norm()
   */
  public double dot(Point3D point);
  
  /**
   * Compute the sum of the vector represented by this point and the vector represented by the given one and return it as a new point.
   * Let <i>a</i> and <i>b</i> be two vector, their sum, denoted <i>a</i>&nbsp;+&nbsp;<i>b</i>, is a vector such that:<br>
   * $$ a + b\ =\ (x_a + x_b,\ y_a + y_b,\ z_a + z_b)$$  
   * If this point has to be modified, the {@link #plusAffect(Point3D)} method can be used.
   * @param point the point to sum with the current point.
   * @return the sum of the vector represented by this point and the vector represented by the given one.
   * @see #plus(Point3D, Point3D)
   * @see #plus(double, Point3D)
   * @see #plus(double)
   */
  public Point3D plus(Point3D point);
  
  /**
   * Compute the sum of the vector represented by this point and the vector represented by the given one and store the values within <code>result</code>.
   * Let <i>a</i> and <i>b</i> be two vector, their sum, denoted <i>a</i>&nbsp;+&nbsp;<i>b</i>, is a vector such that:<br>
   * $$ a + b\ =\ (x_a + x_b,\ y_a + y_b,\ z_a + z_b)$$ 
   * @param point the point to sum with the current point.
   * @param result the point that represents the resulting vector.
   * @return a reference on the <code>result</code> object.
   * @see #plus(Point3D)
   * @see #plus(double, Point3D)
   * @see #plus(double)
   */
  public Point3D plus(Point3D point, Point3D result);
  
  /**
   * Compute the sum of the vector represented by this point and the scalar given in parameter and return the result as a new point.
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the sum, denoted <i>a</i>&nbsp;+&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ a + b\ =\ (x_a + x_b,\ y_a + y_b,\ z_a + z_b)$$ 
   * If this point has to be modified, the {@link #plusAffect(double)} method can be used.
   * @param scalar the scalar to sum.
   * @return  the sum of the vector represented by this point and the given <code>scalar</code>.
   * @see #plus(Point3D)
   * @see #plus(Point3D, Point3D)
   * @see #plus(double, Point3D)
   */
  public Point3D plus(double scalar);
  
  /**
   * Compute the sum of the vector represented by this point and the scalar given in parameter one and then store the values within <code>result</code>.
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the sum, denoted <i>a</i>&nbsp;+&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ a + b\ =\ (x_a + x_b,\ y_a + y_b,\ z_a + z_b)$$ 
   * @param scalar the scalar to sum.
   * @param result the point that represents the resulting vector.
   * @return a reference on the <code>result</code> object.
   * @see #plus(Point3D)
   * @see #plus(Point3D, Point3D)
   * @see #plus(double)
   */
  public Point3D plus(double scalar, Point3D result);

  /**
   * Compute the difference between the vector represented by this point and the vector represented by the given one and return it as a new point.
   * Let <i>a</i> and <i>b</i> be two vector, their difference, denoted <i>a</i>&nbsp;-&nbsp;<i>b</i>, is a vector such that:<br>
   * $$ a - b\ =\ (x_a - x_b,\ y_a - y_b,\ z_a - z_b)$$ 
   * If this point has to be modified, the {@link #minusAffect(Point3D)} method can be used.
   * @param point the point to differentiate with the current point.
   * @return the difference between the vector represented by this point and the vector represented by the given one.
   * @see #minus(Point3D, Point3D)
   * @see #minus(double, Point3D)
   * @see #minus(double)
   */
  public Point3D minus(Point3D point);
  
  /**
   * Compute the difference between the vector represented by this point and the vector represented by the given one and then store the values within <code>result</code>.
   * Let <i>a</i> and <i>b</i> be two vector, their difference, denoted <i>a</i>&nbsp;-&nbsp;<i>b</i>, is a vector such that:<br>
   * $$ a - b\ =\ (x_a - x_b,\ y_a - y_b,\ z_a - z_b)$$
   * @param point the point to differentiate with the current point.
   * @param result the point that represents the resulting vector.
   * @return a reference on the <code>result</code> object.
   * @see #minus(Point3D)
   * @see #minus(double, Point3D)
   * @see #minus(double)
   */
  public Point3D minus(Point3D point, Point3D result);
 
  /**
   * Compute the difference between the vector represented by this point and the scalar given in parameter and return the result as a new point.
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the difference, denoted <i>a</i>&nbsp;-&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ a - b\ =\ (x_a - x_b,\ y_a - y_b,\ z_a - z_b)$$
   * If this point has to be modified, the {@link #minusAffect(double)} method can be used.
   * @param scalar the scalar to differentiate.
   * @return the difference between the vector represented by this point and the given <code>scalar</code>.
   * @see #minus(Point3D)
   * @see #minus(Point3D, Point3D)
   * @see #minus(double, Point3D)
   */
  public Point3D minus(double scalar);
  
  /**
   * Compute the difference between the vector represented by this point and the scalar given in parameter one and then store the values within <code>result</code>.
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the difference, denoted <i>a</i>&nbsp;-&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ a - b\ =\ (x_a - x_b,\ y_a - y_b,\ z_a - z_b)$$
   * @param scalar the scalar to differentiate.
   * @param result the point that represents the resulting vector.
   * @return a reference on the <code>result</code> object.
   * @see #minus(Point3D)
   * @see #minus(Point3D, Point3D)
   * @see #minus(double)
   */
  public Point3D minus(double scalar, Point3D result);
  
  /**
   * Affect this point with the result of the sum of this point and the one given in parameter. 
   * Let <i>a</i> and <i>b</i> be two vector, their sum, denoted <i>a</i>&nbsp;+&nbsp;<i>b</i>, is a vector such that:<br>
   * $$ a + b\ =\ (x_a + x_b,\ y_a + y_b,\ z_a + z_b)$$
   * This point is modified by this operation. If its is not needed to modify this point, the method {@link #plus(Point3D)} can be used.
   * @param point the point to sum with the this one.
   * @return a reference on this point (can be useful for chaining operations).
   * @see #plusAffect(double)
   */
  public Point3D plusAffect(Point3D point);
  
  /**
   * Affect this point with the result of the sum of this point and the <code>scalar</code> given in parameter. 
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the sum, denoted <i>a</i>&nbsp;+&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ a + b\ =\ (x_a + x_b,\ y_a + y_b,\ z_a + z_b)$$
   * This point is modified by this operation. If its is not needed to modify this point, the method {@link #plus(double)} can be used.
   * @param scalar the scalar to sum with this point.
   * @return a reference on this point (can be useful for chaining operations).
   * @see #plusAffect(Point3D)
   */
  public Point3D plusAffect(double scalar);
  
  /**
   * Affect this point with the result of the difference of this point and the one given in parameter. 
   * Let <i>a</i> and <i>b</i> be two vector, their difference, denoted <i>a</i>&nbsp;-&nbsp;<i>b</i>, is a vector such that:<br>
   * $$ a - b\ =\ (x_a - x_b,\ y_a - y_b,\ z_a - z_b)$$
   * This point is modified by this operation. If its is not needed to modify this point, the method {@link #minus(Point3D)} can be used.
   * @param point the point to differentiate with the this one.
   * @return a reference on this point (can be useful for chaining operations).
   * @see #minusAffect(double)
   */
  public Point3D minusAffect(Point3D point);
  
  /**
   * Affect this point with the result of the difference of this point and the <code>scalar</code> given in parameter. 
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the sum, denoted <i>a</i>&nbsp;-&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ a - b\ =\ (x_a - x_b,\ y_a - y_b,\ z_a - z_b)$$
   * This point is modified by this operation. If its is not needed to modify this point, the method {@link #plus(double)} can be used.
   * @param scalar the scalar to differentiate with this point.
   * @return a reference on this point (can be useful for chaining operations).
   * @see #minusAffect(Point3D)
   */
  public Point3D minusAffect(double scalar);
  
  /**
   * Compute the product of the vector represented by this point and the scalar given in parameter and return the result as a new point.
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the product, denoted <i>a</i>&nbsp;&times;&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ a \times \lambda\ =\ (x \times \lambda,\ y \times \lambda,\ z \times \lambda)$$
   * If this point has to be modified, the {@link #multAffect(double)} method can be used.
   * @param scalar the scalar to multiply.
   * @return the product of the vector represented by this point and the scalar given in parameter.
   * @see #mult(double, Point3D)
   */
  public Point3D mult(double scalar);
  
  /**
   * Compute the product of the vector represented by this point and the scalar given in parameter and store it as the given <code>result</code>.
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the product, denoted <i>a</i>&nbsp;&times;&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ a \times \lambda\ =\ (x \times \lambda,\ y \times \lambda,\ z \times \lambda)$$
   * @param scalar the scalar to multiply.
   * @param result the point that represents the resulting vector.
   * @return a reference on the given <code>result</code> (can be useful for chaining operations).
   * @see #mult(double)
   */
  public Point3D mult(double scalar, Point3D result);
  
  /**
   * Affect this point with the product of the vector represented by this point and the scalar given in parameter.
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the product, denoted <i>a</i>&nbsp;&times;&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ a \times \lambda\ =\ (x \times \lambda,\ y \times \lambda,\ z \times \lambda)$$
   *  This point is modified by this operation. If its is not needed to modify this point, the method {@link #mult(double)} can be used.
   * @param scalar the scalar to multiply.
   * @return a reference on this point (can be useful for chaining operations).
   */
  public Point3D multAffect(double scalar);
  
  /**
   * Compute the division of the vector represented by this point and the scalar given in parameter and return the result as a new point.
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the division, denoted <i>a</i>&nbsp;/&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ \frac{a}{\lambda}\ =\ (\frac{x}{\lambda},\ \frac{y}{\lambda},\ \frac{z}{\lambda})$$
   * If this point has to be modified, the {@link #divideAffect(double)} method can be used.
   * @param scalar the scalar to divide.
   * @return the division of the vector represented by this point and the scalar given in parameter.
   * @see #divide(double, Point3D)
   */
  public Point3D divide(double scalar);
  
  /**
   * Compute the division of the vector represented by this point and the scalar given in parameter and store it as the given <code>result</code>.
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the division, denoted <i>a</i>&nbsp;/&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ \frac{a}{\lambda}\ =\ (\frac{x}{\lambda},\ \frac{y}{\lambda},\ \frac{z}{\lambda})$$
   * @param scalar the scalar to divide.
   * @param result the point that represents the resulting vector.
   * @return a reference on the given <code>result</code> (can be useful for chaining operations).
   * @see #divide(double)
   */
  public Point3D divide(double scalar, Point3D result);
  
  /**
   * Affect this point with the division of the vector represented by this point and the scalar given in parameter.
   * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the division, denoted <i>a</i>&nbsp;/&nbsp;<i>&lambda;</i>, is a vector such that:<br>
   * $$ \frac{a}{\lambda}\ =\ (\frac{x}{\lambda},\ \frac{y}{\lambda},\ \frac{z}{\lambda})$$
   * This point is modified by this operation. If its is not needed to modify this point, the method {@link #divide(double)} can be used.
   * @param scalar the scalar to divide.
   * @return a reference on this point (can be useful for chaining operations).
   */
  public Point3D divideAffect(double scalar);
}
