package org.jeometry.math;

import org.jeometry.Geometry;

/**
 * An interface that represents a vector as a set of <code>double</code> coordinates. Each coordinate is expressed belong a dimension.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public interface Vector {

  /**
   * Get the dimension of this vector. The dimension is the number of components that this vector can holds.
   * @return the dimension of this vector. 
   */
  public int getDimension();
	
  /**
   * Get the value of the coordinate expressed within the given dimension.
   * @param dimension the dimension of the coordinate.
   * @return the value of the coordinate expressed within the given dimension.
   * @see #setVectorComponent(int, double)
   */
  public double getVectorComponent(int dimension);
  
  /**
   * Set the value of the coordinate expressed within the given dimension.
   * @param dimension the dimension of the coordinate.
   * @param value the value of the coordinate expressed within the given dimension.
   * @see #getVectorComponent(int)
   */
  public void setVectorComponent(int dimension, double value);
  
  /**
   * Compute the squared norm of the vector:<br>
   * &Sigma;<i><sup>n</sup><sub>i=1</sub>c<sub>i</sub><sup>2</sup></i><br>
   * Where <i>c<sub>i</sub></i> is the vector component at index <i>i</i> and <i>n</i> is the vector length.
   * @return the squared norm of the vector.
   */
  public double normSquare();
  
  /**
   * Compute the norm of the vector:<br>
   * \(
   * \sqrt{\sum\limits_{i=1}^n c_{i}^{2}}
   * \)<br>
   * Where <i>c<sub>i</sub></i> is the vector component at index <i>i</i> and <i>n</i> is the vector length.
   * @return The norm of the vector
   */
  public double norm();
  
  /**
   * Divide all the components of this vector by its {@link #norm() Euclidean norm}. When called on a vector v&nbsp;=&nbsp;(<i>c<sub>0</sub></i>, ..., <i>c<sub>i</sub></i>, ..., <i>c<sub>n</sub></i>), this method modify its components as follows:<br>
   * $$v\ =\ (\frac{c_{0}}{||p||},\ \ldots,\ \frac{c_{i}}{||p||},\ \ldots\ ,\ \frac{c_{n}}{||p||})$$
   * @see #norm()
   */
  public void normalize();
  
  /**
   * Return a normalized vector that is orthogonal to this one.
   * @return a normalized vector that is orthogonal to this one.
   * @see #orthogonal(Vector)
   */
  public Vector orthogonal();
  
  /**
   * Set the given <code>result</code> vector with the normalized vector that is orthogonal to this one.
   * @param result the vector where the result has to be stored.
   * @return the given <code>result</code> vector with the normalized vector that is orthogonal to this one.
   * @see #orthogonal()
   * @throws IllegalArgumentException if the <code>result</code> vector length is not equals to this one.
   */
  public Vector orthogonal(Vector result);
  
  /**
   * Return the vector made of the multiplication of this one by the scalar given in parameter.
   * Formally, let <i>V</i>&nbsp;=&nbsp;[<i>v<sub>0</sub></i>,...,<i>v<sub>i</sub></i>,...,<i>v<sub>n</sub></i>] a vector and <i>s</i> a scalar, 
   * the multiplication of <i>V</i> by the scalar is the vector <i>V'</i> such that:<br><br>
   * <i>V'</i>&nbsp;=&nbsp;[<i>sv<sub>0</sub></i>,...,<i>sv<sub>i</sub></i>,...,<i>sv<sub>n</sub></i>]<br>
   * @param scalar the scalar to multiply.
   * @return the vector made of the multiplication of this one by the scalar given in parameter.
   */
  public Vector multiply(double scalar);
  
  /**
   * Compute the multiplication of this vector by the scalar given in parameter and store the result in the given vector.
   * Formally, let <i>V</i>&nbsp;=&nbsp;[<i>v<sub>0</sub></i>,...,<i>v<sub>i</sub></i>,...,<i>v<sub>n</sub></i>] a vector and <i>s</i> a scalar, 
   * the multiplication of <i>V</i> by the scalar is the vector <i>V'</i> such that:<br><br>
   * <i>V'</i>&nbsp;=&nbsp;[<i>sv<sub>0</sub></i>,...,<i>sv<sub>i</sub></i>,...,<i>sv<sub>n</sub></i>]<br>
   * @param scalar the scalar to multiply.
   * @param result the vector that has to store the result.
   * @return the same reference as <code>result</code>.
   * @throws IllegalArgumentException if the result vector does not fit for the multiplication.
   */
  public Vector multiply(double scalar, Vector result) throws IllegalArgumentException;
  
  /**
   * Affect this vector with the result of its multiplication by the scalar given in parameter.
   * Formally, let <i>V</i>&nbsp;=&nbsp;[<i>v<sub>0</sub></i>,...,<i>v<sub>i</sub></i>,...,<i>v<sub>n</sub></i>] a vector and <i>s</i> a scalar, 
   * the multiplication of <i>V</i> by the scalar is the vector <i>V'</i> such that:<br><br>
   * <i>V'</i>&nbsp;=&nbsp;[<i>sv<sub>0</sub></i>,...,<i>sv<sub>i</sub></i>,...,<i>sv<sub>n</sub></i>]<br>
   * @param scalar the scalar to multiply.
   * @return a reference to this object.
   */
  public Vector multiplyAffect(double scalar);
  
}
