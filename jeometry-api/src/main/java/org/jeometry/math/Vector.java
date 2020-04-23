package org.jeometry.math;

import org.jeometry.Jeometry;

/**
 * An interface that represents a vector as a set of <code>double</code> coordinates. Each coordinate is expressed belong a dimension.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
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
	 * Set the components of this vector with the values of the components from the given one.
	 * @param v the vector to copy
	 * @throws IllegalArgumentException if the given vector has no the same dimension as this one
	 */
	public void setComponents(Vector v);

	/**
	 * Get the values of the components of this vector as an array of double.
	 * @return the values of the components of this vector as an array of double
	 * @see #getComponents(double[])
	 */
	public double[] getComponents();

	/**
	 * Get the values of the components of this vector as an array of double.
	 * @param components the array that has to store the values of the components of this vector
	 * @return a reference on <code>components</code>
	 * @see #getComponents()
	 * @throws IllegalArgumentException if <code>components</code> is <code>null</code> or if its length does not match the vector dimension
	 */
	public double[] getComponents(double[] components);

	/**
	 * Set the components of this vector with the values of the components from the vector represented by the input array.
	 * @param components the components value to affect to this vector
	 * @throws IllegalArgumentException if the length of the input array does not match the dimension of this vector
	 */
	public void setComponents(double[] components);

	/**
	 * Set all the components of this vector to the given <code>value</code>.
	 * @param value the value to set to all components
	 */
	public void setComponents(double value);
	
	/**
	 * Set the components of this vector according to the given {@link Matrix matrix}. 
	 * The matrix has to be a single row or a single column. 
	 * @param matrix the matrix to use as the values provider
	 * @throws IllegalArgumentException if the matrix is not single row / single column or if its size does not fit the vector
	 */
	public void setComponents(Matrix matrix);
	
	/**
	 * Compute the vector that is the sum, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;+&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;+&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;+&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the vector to add to this one
	 * @return the vector that is the sum, component by component of this vector and the given one
	 * @throws IllegalArgumentException if the input vector dimension does not fit with this one
	 */
	public Vector plus(Vector v);
	
	/**
	 * Compute the vector that is the sum, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;+&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;+&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;+&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the vector to add to this one
	 * @param result the vector that holds the result
	 * @return a reference on the <code>result</code> vector
	 * @throws IllegalArgumentException if the input vector or the result vector dimension does not fit with this one
	 */
	public Vector plus(Vector v, Vector result);
	
	/**
	 * Affect this vector with the sum, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;+&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;+&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;+&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the vector to add to this one
	 * @return a reference on this vector
	 * @throws IllegalArgumentException if the input vector dimension does not fit with this one
	 */
	public Vector plusAffect(Vector v);
	
	/**
	 * Compute the vector that is the subtraction, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;-&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;-&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;-&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the vector to subtract from this one
	 * @return the vector that is the subtraction, component by component of this vector and the given one
	 * @throws IllegalArgumentException if the input vector dimension does not fit with this one
	 */
	public Vector minus(Vector v);
	
	/**
	 * Compute the vector that is the subtraction, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;-&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;-&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;-&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the vector to subtract from this one
	 * @param result the vector that holds the result
	 * @return a reference on the <code>result</code> vector
	 * @throws IllegalArgumentException if the input vector or the result vector dimension does not fit with this one
	 */
	public Vector minus(Vector v, Vector result);
	
	/**
	 * Affect this vector with the subtraction, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;-&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;-&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;-&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the vector to subtract from this one
	 * @return a reference on this vector
	 * @throws IllegalArgumentException if the input vector dimension does not fit with this one
	 */
	public Vector minusAffect(Vector v);
	
	/**
	 * Extract a vector that contains the part of this vector specified by the parameters.
	 * @param start the index of the first component to extract (inclusive)
	 * @param length the number of contiguous components to extract from the first
	 * @return a vector that contains the part of this vector delimited by the parameters
	 */
	public Vector extract(int start, int length);
	
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
	 * @param scalar the scalar to multiply
	 * @param result the vector that has to store the result
	 * @return the same reference as <code>result</code>.
	 * @throws IllegalArgumentException if the result vector does not fit for the multiplication.
	 */
	public Vector multiply(double scalar, Vector result) throws IllegalArgumentException;

	/**
	 * Affect this vector with the result of its multiplication by the scalar given in parameter.
	 * Formally, let <i>V</i>&nbsp;=&nbsp;[<i>v<sub>0</sub></i>,...,<i>v<sub>i</sub></i>,...,<i>v<sub>n</sub></i>] a vector and <i>s</i> a scalar, 
	 * the multiplication of <i>V</i> by the scalar is the vector <i>V'</i> such that:<br><br>
	 * <i>V'</i>&nbsp;=&nbsp;[<i>sv<sub>0</sub></i>,...,<i>sv<sub>i</sub></i>,...,<i>sv<sub>n</sub></i>]<br>
	 * @param scalar the scalar to multiply
	 * @return a reference to this object
	 */
	public Vector multiplyAffect(double scalar);
	
	/**
	 * Compute the vector that is the product, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;&times;&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;&times;&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;&times;&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the vector to multiply to this one
	 * @return the vector that is the product, component by component of this vector and the given one
	 * @throws IllegalArgumentException if the input vector dimension does not fit with this one
	 */
	public Vector multiply(Vector v);

	/**
	 * Compute the vector that is the product, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;&times;&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;&times;&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;&times;&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the vector to multiply to this one
	 * @param result the vector that holds the result
	 * @return a reference on the <code>result</code> vector
	 * @throws IllegalArgumentException if the input vector or the result vector dimension does not fit with this one
	 */
	public Vector multiply(Vector v, Vector result);

	/**
	 * Affect this vector with the product, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;&times;&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;&times;&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;&times;&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the vector to product from this one
	 * @return a reference on this vector
	 * @throws IllegalArgumentException if the input vector dimension does not fit with this one
	 */
	public Vector multiplyAffect(Vector v);
	
	/**
	 * Compute the vector that is the division, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;/&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;/&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;/&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the divider vector 
	 * @return the vector that is the division, component by component of this vector and the given one
	 * @throws IllegalArgumentException if the input vector dimension does not fit with this one
	 */
	public Vector divide(Vector v);

	/**
	 * Compute the vector that is the division, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;/&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;/&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;/&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the divider vector 
	 * @param result the vector that holds the result
	 * @return a reference on the <code>result</code> vector
	 * @throws IllegalArgumentException if the input vector or the result vector dimension does not fit with this one
	 */
	public Vector divide(Vector v, Vector result);

	/**
	 * Affect this vector with the division, component by component of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the vector <i>W</i> such that:<br>
	 * <div style="text-align: center;"><i>W</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;/&nbsp;<i>v</i><sub>0</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>i</sub>&nbsp;/&nbsp;<i>v</i><sub>i</sub>,&nbsp;&hellip;,&nbsp;<i>u</i><sub>n</sub>&nbsp;/&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the divider vector 
	 * @return a reference on this vector
	 * @throws IllegalArgumentException if the input vector dimension does not fit with this one
	 */
	public Vector divideAffect(Vector v);
	
	/**
	 * Compute the scalar product of this vector and the given one. 
	 * More formally, let <i>U</i> be this vector and let <i>V</i> the vector given in parameter, this method compute the scalar <i>U</i>&middot;<i>V</i> such that:<br>
	 * <div style="text-align: center;"><i>U</i>&middot;<i>V</i>&nbsp;=&nbsp;(<i>u</i><sub>0</sub>&nbsp;&times;&nbsp;<i>v</i><sub>0</sub>&nbsp;+&nbsp;&hellip;+&nbsp;<i>u</i><sub>i</sub>&nbsp;&times;&nbsp;<i>v</i><sub>i</sub>&nbsp;+&nbsp;&hellip;+&nbsp;<i>u</i><sub>n</sub>&nbsp;&times;&nbsp;<i>v</i><sub>n</sub>)</div>
	 * <br>
	 * where <i>u</i><sub>i</sub>, <i>v</i><sub>i</sub> are respectively the values of the <i>i</i><sup>th</sup> component of vector <i>U</i>, <i>V</i>.
	 * <br> 
	 * @param v the second operand
	 * @return the scalar product of this vector and the given one or {@link Double#NaN Double.Nan} if <code>v</code> is <code>null</code>
	 * @throws IllegalArgumentException if the input vector dimension does not fit with this one
	 */
	public double dot(Vector v);
}
