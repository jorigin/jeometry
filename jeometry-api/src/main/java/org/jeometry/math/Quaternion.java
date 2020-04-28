package org.jeometry.math;

import org.jeometry.Jeometry;

/**
 * This interface represents a <a href="https://en.wikipedia.org/wiki/Quaternion">quaternion</a>.<br><br>
 * A quaternion <i>q</i> is a complex number defined such as:<br><br>
 * 
 * <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i><br><br>
 * 
 * where (<i>a</i>, <i>b</i>, <i>c</i>, <i>d</i>) are real numbers and (<i>i</i>, <i>j</i>, <i>k</i>) are the fundamental quaternion units.<br>
 * Number <i>a</i> is called real part (or scalar part) of the quaternion and <i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i> is called vector part (or imaginary part).
 * <br><br>
 * A multiplicative group structure, called the {@link #mult(Quaternion) Hamilton product}, denoted by juxtaposition, can be defined on the quaternions in the following way:
 * <ul>
 * <li>The real quaternion <i>1</i> is the identity element.
 * <li>The real quaternions commute with all other quaternions, that is <i>aq</i>&nbsp;=&nbsp;<i>qa</i> for every quaternion <i>q</i> and every real quaternion <i>a</i>. In algebraic terminology this is to say that the field of real quaternions are the center of this quaternion algebra.
 * <li>The product is first given for the basis elements (see next subsection), and then extended to all quaternions by using the distributive property and the center property of the real quaternions. The Hamilton product is not commutative, but associative, thus the quaternions form an associative algebra over the reals.
 * <li>Additionally, every nonzero quaternion has an inverse with respect to the {@link #mult(Quaternion) Hamilton product}, thus the quaternions form a division algebra. 
 * </ul>
 * <br>
 * A quaternion can be expressed as a {@link Vector vector} of real numbers <i>q</i>&nbsp;=&nbsp;(<i>a</i>, <i>b</i>, <i>c</i>, <i>d</i>).<br><br>
 * 
 * Within 3D computation domain, a quaternion can be represented as a vector of real number defined such as:<br><br>
 * 
 * <i>q</i>&nbsp;=&nbsp;(<i>w</i>, <i>x</i>, <i>y</i>, <i>z</i>)<br><br>
 * 
 * Where parameters (<i>w</i>, <i>x</i>, <i>y</i>, <i>z</i>) correspond to (<i>a</i>, <i>b</i>, <i>c</i>, <i>d</i>) from the mathematical form.
 * 
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface Quaternion extends Vector{

	/**
	 * The index of the real part (or scalar part) of a quaternion within an array od numbers.
	 * @see #QUATERNION_I_COMPONENT
	 * @see #QUATERNION_J_COMPONENT
	 * @see #QUATERNION_K_COMPONENT
	 */
	public static int QUATERNION_SCALAR_COMPONENT = 0;
	
	/**
	 * The index of the <i>i</i> basis component of the quaternion vector part (or imaginary part) component.<br><br>
	 * This component is denoted <i>b</i> with <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i><br><br>.
	 * @see #QUATERNION_SCALAR_COMPONENT
	 * @see #QUATERNION_J_COMPONENT
	 * @see #QUATERNION_K_COMPONENT
	 */
	public static int QUATERNION_I_COMPONENT = 1;
	
	/**
	 * The index of the <i>j</i> basis component of the quaternion vector part (or imaginary part) component.<br><br>
	 * This component is denoted <i>c</i> with <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i><br><br>.
	 * @see #QUATERNION_SCALAR_COMPONENT
	 * @see #QUATERNION_I_COMPONENT
	 * @see #QUATERNION_K_COMPONENT
	 */
	public static int QUATERNION_J_COMPONENT = 2;
	
	/**
	 * The index of the <i>k</i> basis component of the quaternion vector part (or imaginary part) component.<br><br>
	 * This component is denoted <i>d</i> with <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i><br><br>.
	 * @see #QUATERNION_SCALAR_COMPONENT
	 * @see #QUATERNION_I_COMPONENT
	 * @see #QUATERNION_J_COMPONENT
	 */
	public static int QUATERNION_K_COMPONENT = 3;
	
	
	/**
	 * Get the quaternion real part (or scalar) parameter, denoted <i>a</i> within the definition 
	 * <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * @return the quaternion real part (or scalar) parameter.
	 * @see #getI()
	 * @see #getJ()
	 * @see #getK()
	 * @see #setScalar(double)
	 */
	public double getScalar();
	
	/**
	 * Set the quaternion real part (or scalar) parameter, denoted <i>a</i> within the definition 
	 * <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * @param a the quaternion real part (or scalar) parameter.
	 * @see #setI(double)
	 * @see #setJ(double)
	 * @see #setK(double)
	 * @see #getScalar()
	 */
	public void setScalar(double a);
	
	/**
	 * Get the <i>i</i> basis component of the quaternion vector part (or imaginary part) parameter, denoted <i>b</i> within the definition 
	 * <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * @return the first component of the quaternion vector part (or imaginary part) parameter.
	 * @see #getScalar()
	 * @see #getJ()
	 * @see #getK()
	 * @see #setI(double)
	 */
	public double getI();
	
	/**
	 * Set the <i>i</i> basis component of the quaternion vector part (or imaginary part) parameter, denoted <i>b</i> within the definition 
	 * <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * @param value the first component of the quaternion vector part (or imaginary part) parameter.
	 * @see #setScalar(double)
	 * @see #setJ(double)
	 * @see #setK(double)
	 * @see #getI()
	 */
	public void setI(double value);
	
	/**
	 * Get the <i>j</i> basis component of the quaternion vector part (or imaginary part) parameter, denoted <i>c</i> within the definition 
	 * <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * @return the second component of the quaternion vector part (or imaginary part) parameter.
	 * @see #getScalar()
	 * @see #getI()
	 * @see #getK()
	 * @see #setJ(double)
	 */
	public double getJ();
	
	/**
	 * Set the <i>j</i> basis component of the quaternion vector part (or imaginary part) parameter, denoted <i>c</i> within the definition 
	 * <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * @param value the second component of the quaternion vector part (or imaginary part) parameter.
	 * @see #setScalar(double)
	 * @see #setI(double)
	 * @see #setK(double)
	 * @see #getJ()
	 */
	public void setJ(double value);
	
	/**
	 * Get the <i>k</i> basis component of the quaternion vector part (or imaginary part) parameter, denoted <i>d</i> within the definition 
	 * <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * @return the third component of the quaternion vector part (or imaginary part) parameter.
	 * @see #getScalar()
	 * @see #getI()
	 * @see #getJ()
	 * @see #setK(double)
	 */
	public double getK();
	
	/**
	 * Set the <i>k</i> basis component of the quaternion vector part (or imaginary part) parameter, denoted <i>d</i> within the definition 
	 * <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * @param value the third component of the quaternion vector part (or imaginary part) parameter.
	 * @see #setScalar(double)
	 * @see #setI(double)
	 * @see #setJ(double)
	 * @see #getK()
	 * 
	 */
	public void setK(double value);
	
	/**
	 * Get the components that compose the quaternion. The output is an array of double, denoted <code>components</code> such as:
	 * <ul>
	 * <li><code>components[{@link #QUATERNION_SCALAR_COMPONENT}]</code> = a;
	 * <li><code>components[{@link #QUATERNION_I_COMPONENT}]</code> = b;
	 * <li><code>components[{@link #QUATERNION_J_COMPONENT}]</code> = c;
	 * <li><code>components[{@link #QUATERNION_K_COMPONENT}]</code> = d;
	 * </ul>
	 * with <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * The components are stored within the given <code>components</code> array.
	 * @return a reference to the <code>components</code> array.
	 */
	public double[] getValues();
	
	/**
	 * Get the components that compose the quaternion. The output is an array of double, denoted <code>components</code> such as:
	 * <ul>
	 * <li><code>components[{@link #QUATERNION_SCALAR_COMPONENT}]</code> = a;
	 * <li><code>components[{@link #QUATERNION_I_COMPONENT}]</code> = b;
	 * <li><code>components[{@link #QUATERNION_J_COMPONENT}]</code> = c;
	 * <li><code>components[{@link #QUATERNION_K_COMPONENT}]</code> = d;
	 * </ul>
	 * with <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * The components are stored within the given <code>components</code> array.
	 * @param components the double array that store the result.
	 * @return a reference to the <code>components</code> array.
	 * @throws IllegalArgumentException if the <code>components</code> array is <code>null</code> or have a size that in inferior to 4.
	 */
	public double[] getValues(double[] components) throws IllegalArgumentException;
	
	/**
	 * Set all the components of the quaternion. The components are such that:
	 * <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>b<sub>i</sub></i>&nbsp;+&nbsp;<i>c<sub>j</sub></i>&nbsp;+&nbsp;<i>d<sub>k</sub></i>.
	 * @param a the quaternion real part (or scalar) parameter.
	 * @param b the first component of the quaternion vector part (or imaginary part) parameter.
	 * @param c the second component of the quaternion vector part (or imaginary part) parameter.
	 * @param d the third component of the quaternion vector part (or imaginary part) parameter.
	 */
	public void setComponents(double a, double b, double c, double d);
	
	/**
	 * Compute the product (called Hamilton product) of this quaternion with the quaternion <code>q</code>. <br><br>
	 * Let <i>q<sub>1</sub></i>&nbsp;=&nbsp;<i>a<sub>1</sub></i>&nbsp;+&nbsp;<i>b<sub>1</sub>i</i>&nbsp;+&nbsp;<i>c<sub>1</sub>j</i>&nbsp;+&nbsp;<i>d<sub>1</sub>k</i> and 
	 * <i>q<sub>2</sub></i>&nbsp;=&nbsp;<i>a<sub>2</sub></i>&nbsp;+&nbsp;<i>b<sub>2</sub>i</i>&nbsp;+&nbsp;<i>c<sub>2</sub>j</i>&nbsp;+&nbsp;<i>d<sub>2</sub>k</i> two quaternions. 
	 * The product <i>q<sub>1</sub></i>&times;<i>q<sub>2</sub></i> is such that: <br>
	 * $$
	 * \begin{array}{rcl}
	 *  q_{1}\times{}q_{2} &amp; = &amp; a_{1}a_{2}  + a_{1}b_{2}i     + a_{1}c_{2}j     + a_{1}d_{2}k     \\
	 *                     &amp; + &amp; b_{1}a_{2}i + b_{1}b_{2}i^{2} + b_{1}c_{2}ij    + b_{1}d_{2}ik    \\
	 *                     &amp; + &amp; c_{1}a_{2}j + c_{1}b_{2}ji    + c_{1}c_{2}j^{2} + c_{1}d_{2}jk    \\
	 *                     &amp; + &amp; d_{1}a_{2}k + d_{1}b_{2}ki    + d_{1}c_{2}kj    + d_{1}d_{2}k^{2} \\   
	 * \end{array}
	 * $$
	 * Using the quaternion product property <i>i<sup>2</sup></i>&nbsp;=&nbsp;<i>j<sup>2</sup></i><i>k<sup>2</sup></i><i>ijk</i>&nbsp;=&nbsp;-<i>1</i> previous result can be expressed such as:
	 * $$
	 * \begin{array}{rcl}
	 *  q_{1}\times{}q_{2} &amp; = &amp; (a_{1}a_{2} - b_{1}b_{2} - c_{1}c_{2} - d_{1}d_{2})  \\
	 *                     &amp; + &amp; (a_{1}b_{2} + b_{1}a_{2} + c_{1}d_{2} - d_{1}c_{2})i \\
	 *                     &amp; + &amp; (a_{1}c_{2} - b_{1}d_{2} + c_{1}a_{2} + d_{1}b_{2})j \\
	 *                     &amp; + &amp; (a_{1}d_{2} + b_{1}c_{2} + c_{1}b_{2} + d_{1}a_{2})k \\   
	 * \end{array}
	 * $$
	 * @param p the quaternion to multiply.
	 * @return the product (called Hamilton product) of this quaternion with <code>p</code>.
	 */
	public Quaternion mult(Quaternion p);
	
	/**
	 * Compute the product (called Hamilton product) of this quaternion with the quaternion <code>q</code>. <br><br>
	 * Let <i>q<sub>1</sub></i>&nbsp;=&nbsp;<i>a<sub>1</sub></i>&nbsp;+&nbsp;<i>b<sub>1</sub>i</i>&nbsp;+&nbsp;<i>c<sub>1</sub>j</i>&nbsp;+&nbsp;<i>d<sub>1</sub>k</i> and 
	 * <i>q<sub>2</sub></i>&nbsp;=&nbsp;<i>a<sub>2</sub></i>&nbsp;+&nbsp;<i>b<sub>2</sub>i</i>&nbsp;+&nbsp;<i>c<sub>2</sub>j</i>&nbsp;+&nbsp;<i>d<sub>2</sub>k</i> two quaternions. 
	 * The product <i>q<sub>1</sub></i>&times;<i>q<sub>2</sub></i> is such that: <br>
	 * $$
	 * \begin{array}{rcl}
	 *  q_{1}\times{}q_{2} &amp; = &amp; a_{1}a_{2}  + a_{1}b_{2}i     + a_{1}c_{2}j     + a_{1}d_{2}k     \\
	 *                     &amp; + &amp; b_{1}a_{2}i + b_{1}b_{2}i^{2} + b_{1}c_{2}ij    + b_{1}d_{2}ik    \\
	 *                     &amp; + &amp; c_{1}a_{2}j + c_{1}b_{2}ji    + c_{1}c_{2}j^{2} + c_{1}d_{2}jk    \\
	 *                     &amp; + &amp; d_{1}a_{2}k + d_{1}b_{2}ki    + d_{1}c_{2}kj    + d_{1}d_{2}k^{2} \\   
	 * \end{array}
	 * $$
	 * Using the quaternion product property <i>i<sup>2</sup></i>&nbsp;=&nbsp;<i>j<sup>2</sup></i><i>k<sup>2</sup></i><i>ijk</i>&nbsp;=&nbsp;-<i>1</i> previous result can be expressed such as:
	 * $$
	 * \begin{array}{rcl}
	 *  q_{1}\times{}q_{2} &amp; = &amp; (a_{1}a_{2} - b_{1}b_{2} - c_{1}c_{2} - d_{1}d_{2})  \\
	 *                     &amp; + &amp; (a_{1}b_{2} + b_{1}a_{2} + c_{1}d_{2} - d_{1}c_{2})i \\
	 *                     &amp; + &amp; (a_{1}c_{2} - b_{1}d_{2} + c_{1}a_{2} + d_{1}b_{2})j \\
	 *                     &amp; + &amp; (a_{1}d_{2} + b_{1}c_{2} + c_{1}b_{2} + d_{1}a_{2})k \\   
	 * \end{array}
	 * $$
	 * @param p the quaternion to multiply.
	 * @param result the quaternion that store the product (called Hamilton product) of this quaternion with <code>p</code>.
	 * @return the same reference as <code>result</code>.
	 */
	public Quaternion mult(Quaternion p, Quaternion result);
	
	/**
	 * Compute the product (called Hamilton product) of this quaternion with the quaternion <code>q</code> and store the result within this quaternion.<br><br>
	 * Let <i>q<sub>1</sub></i>&nbsp;=&nbsp;<i>a<sub>1</sub></i>&nbsp;+&nbsp;<i>b<sub>1</sub>i</i>&nbsp;+&nbsp;<i>c<sub>1</sub>j</i>&nbsp;+&nbsp;<i>d<sub>1</sub>k</i> and 
	 * <i>q<sub>2</sub></i>&nbsp;=&nbsp;<i>a<sub>2</sub></i>&nbsp;+&nbsp;<i>b<sub>2</sub>i</i>&nbsp;+&nbsp;<i>c<sub>2</sub>j</i>&nbsp;+&nbsp;<i>d<sub>2</sub>k</i> two quaternions. 
	 * The product <i>q<sub>1</sub></i>&times;<i>q<sub>2</sub></i> is such that: <br>
	 * $$
	 * \begin{array}{rcl}
	 *  q_{1}\times{}q_{2} &amp; = &amp; a_{1}a_{2}  + a_{1}b_{2}i     + a_{1}c_{2}j     + a_{1}d_{2}k     \\
	 *                     &amp; + &amp; b_{1}a_{2}i + b_{1}b_{2}i^{2} + b_{1}c_{2}ij    + b_{1}d_{2}ik    \\
	 *                     &amp; + &amp; c_{1}a_{2}j + c_{1}b_{2}ji    + c_{1}c_{2}j^{2} + c_{1}d_{2}jk    \\
	 *                     &amp; + &amp; d_{1}a_{2}k + d_{1}b_{2}ki    + d_{1}c_{2}kj    + d_{1}d_{2}k^{2} \\   
	 * \end{array}
	 * $$
	 * Using the quaternion product property <i>i<sup>2</sup></i>&nbsp;=&nbsp;<i>j<sup>2</sup></i><i>k<sup>2</sup></i><i>ijk</i>&nbsp;=&nbsp;-<i>1</i> previous result can be expressed such as:
	 * $$
	 * \begin{array}{rcl}
	 *  q_{1}\times{}q_{2} &amp; = &amp; (a_{1}a_{2} - b_{1}b_{2} - c_{1}c_{2} - d_{1}d_{2})  \\
	 *                     &amp; + &amp; (a_{1}b_{2} + b_{1}a_{2} + c_{1}d_{2} - d_{1}c_{2})i \\
	 *                     &amp; + &amp; (a_{1}c_{2} - b_{1}d_{2} + c_{1}a_{2} + d_{1}b_{2})j \\
	 *                     &amp; + &amp; (a_{1}d_{2} + b_{1}c_{2} + c_{1}b_{2} + d_{1}a_{2})k \\   
	 * \end{array}
	 * $$
	 * @param p the quaternion to multiply.
	 * @return a reference on this object (can be useful for computation chaining).
	 */
	public Quaternion multAffect(Quaternion p);
	
	/**
	 * Invert this quaternion with respect to the {@link #mult(Quaternion) Hamilton product}. <br><br>
	 * Let <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>bi</i>&nbsp;+&nbsp;<i>cj</i>&nbsp;+&nbsp;<i>dk</i> a non zero quaternion. 
	 * Its inverse, denoted <i>q<sup>-1</sup></i> is such that:<br>
	 * $$
	 * q^{-1}\ =\ \frac{1}{a^{2}+b^{2}+c^{2}+d^{2}}(a-bi-cj-dk)
	 * $$
	 * If the quaternion is equals to <code>0</code>, an {@link IllegalStateException} is raised as it is not invertible.
	 * @return the inverse of this quaternion with respect to the {@link #mult(Quaternion) Hamilton product}. 
	 * @throws IllegalStateException if this quaternion is equals to <i>0</i>.
	 */
	public Quaternion invertQuaternion() throws IllegalStateException;
	
	/**
	 * Invert this quaternion with respect to the {@link #mult(Quaternion) Hamilton product}. <br><br>
	 * Let <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>bi</i>&nbsp;+&nbsp;<i>cj</i>&nbsp;+&nbsp;<i>dk</i> a non zero quaternion. 
	 * Its inverse, denoted <i>q<sup>-1</sup></i> is such that:<br>
	 * $$
	 * q^{-1}\ =\ \frac{1}{a^{2}+b^{2}+c^{2}+d^{2}}(a-bi-cj-dk)
	 * $$
	 * If the quaternion is equals to <code>0</code>, an {@link IllegalStateException} is raised as it is not invertible.
	 * @param result a quaternion that can store the inverse of this quaternion with respect to the {@link #mult(Quaternion) Hamilton product}. 
	 * @return the same reference as <code>result</code>.
	 * @throws IllegalStateException if this quaternion is equals to <i>0</i>.
	 */
	public Quaternion invertQuaternion(Quaternion result) throws IllegalStateException;
	
	/**
	 * Change this quaternion by its inverse with respect to the {@link #mult(Quaternion) Hamilton product}. <br><br>
	 * Let <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>bi</i>&nbsp;+&nbsp;<i>cj</i>&nbsp;+&nbsp;<i>dk</i> a non zero quaternion. 
	 * Its inverse, denoted <i>q<sup>-1</sup></i> is such that:<br>
	 * $$
	 * q^{-1}\ =\ \frac{1}{a^{2}+b^{2}+c^{2}+d^{2}}(a-bi-cj-dk)
	 * $$
	 * If the quaternion is equals to <code>0</code>, an {@link IllegalStateException} is raised as it is not invertible.
	 * @return a reference on this quaternion.
	 * @throws IllegalStateException if this quaternion is equals to <i>0</i>.
	 */
	public Quaternion invertQuaternionAffect() throws IllegalStateException;
	
	/**
	 * Compute the conjugate of this quaternion. <br><br>
	 * Let <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>bi</i>&nbsp;+&nbsp;<i>cj</i>&nbsp;+&nbsp;<i>dk</i> a quaternion. 
	 * Its conjugate, denoted <i>q<sup>*</sup></i> is the quaternion such as <i>q<sup>*</sup></i>&nbsp;=&nbsp;<i>a</i>&nbsp;-&nbsp;<i>bi</i>&nbsp;-&nbsp;<i>cj</i>&nbsp;-&nbsp;<i>dk</i>
	 * @return the conjugate of this quaternion.
	 */
	public Quaternion conjugateQuaternion();
	
	/**
	 * Compute the conjugate of this quaternion and store it within the given <code>result</code>. <br><br>
	 * Let <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>bi</i>&nbsp;+&nbsp;<i>cj</i>&nbsp;+&nbsp;<i>dk</i> a quaternion. 
	 * Its conjugate, denoted <i>q<sup>*</sup></i> is the quaternion such as <i>q<sup>*</sup></i>&nbsp;=&nbsp;<i>a</i>&nbsp;-&nbsp;<i>bi</i>&nbsp;-&nbsp;<i>cj</i>&nbsp;-&nbsp;<i>dk</i>
	 * @param result the quaternion that has to store the result.
	 * @return the conjugate of this quaternion.
	 */
	public Quaternion conjugateQuaternion(Quaternion result);
	
	/**
	 * Change this quaternion by its conjugate.
	 * Let <i>q</i>&nbsp;=&nbsp;<i>a</i>&nbsp;+&nbsp;<i>bi</i>&nbsp;+&nbsp;<i>cj</i>&nbsp;+&nbsp;<i>dk</i> a quaternion. 
	 * Its conjugate, denoted <i>q<sup>*</sup></i> is the quaternion such as <i>q<sup>*</sup></i>&nbsp;=&nbsp;<i>a</i>&nbsp;-&nbsp;<i>bi</i>&nbsp;-&nbsp;<i>cj</i>&nbsp;-&nbsp;<i>dk</i>
	 * @return a reference on this object.
	 */
	public Quaternion conjugateQuaternionAffect();
}
