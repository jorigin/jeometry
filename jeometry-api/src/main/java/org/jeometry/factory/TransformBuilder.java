package org.jeometry.factory;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.transform.Transform3D;
import org.jeometry.geom3D.transform.Transform3DMatrix;
import org.jeometry.geom3D.transform.Transform3DQuaternion;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

/**
 * An interface that describes a transform builder. 
 * A transform builder enables to create implementations of interfaces described within the <code>transform</code> packages ({@link Transform3D}, ...).
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.2
 */
public interface TransformBuilder {

	/**
	 * Create a new {@link Transform3DMatrix} that represents the identity transform.
	 * @return a new {@link Transform3DMatrix} that represents the identity transform
	 */
	public Transform3DMatrix createTransform3DMatrix();

	/**
	 * Create a new {@link Transform3DMatrix} that relies on the 4x4 given matrix. <br><br>
	 * The 4x4 matrix represents a 3D affine transform defined such that:
	 * $$
	 * M = \begin{bmatrix} 
	 *        s_{x}r_{00} &amp; s_{x}r_{01} &amp; s_{x}r_{02} &amp; t_{x}  \\
	 *        s_{y}r_{10} &amp; s_{y}r_{11} &amp; s_{y}r_{12} &amp; t_{y}  \\
	 *        s_{z}r_{20} &amp; s_{z}r_{21} &amp; s_{z}r_{22} &amp; t_{z}  \\
	 *           0   &amp;    0   &amp;    0   &amp;  1  
	 *     \end{bmatrix}
	 * $$
	 * where:
	 * <ul>
	 * <li><i>r<sub>ii</sub></i> form the 3x3 rotation matrix
	 * <li><i>t<sub>k</sub></i> form the translation vector
	 * <li><i>s<sub>x</sub></i>, <i>s<sub>y</sub></i>, <i>s<sub>z</sub></i> are the scales values along axis
	 * </ul>
	 * @param matrix the 4x4 matrix that represents a 3D affine transform
	 * @return a new {@link Transform3DMatrix}
	 * @throws IllegalArgumentException if the input matrix is <code>null</code> or is not 4x4 sized
	 */
	public Transform3DMatrix createTransform3DMatrix(Matrix matrix);

	/**
	 * Create a new {@link Transform3DMatrix} that relies on the given parameters.<br><br>
	 * The 4x4 underlying matrix is defined such that:
	 * $$
	 * M = \begin{bmatrix} 
	 *        sr_{00} &amp; sr_{01} &amp; sr_{02} &amp; t_{x}  \\
	 *        sr_{10} &amp; sr_{11} &amp; sr_{12} &amp; t_{y}  \\
	 *        sr_{20} &amp; sr_{21} &amp; sr_{22} &amp; t_{z}  \\
	 *           0   &amp;    0   &amp;    0   &amp;  1  
	 *     \end{bmatrix}
	 * $$
	 * where:
	 * <ul>
	 * <li><i>r<sub>ii</sub></i> form the 3x3 rotation matrix
	 * <li><i>t<sub>k</sub></i> form the translation vector
	 * <li><i>s</i> is the scale value
	 * </ul>
	 * @param rotation the rotation
	 * @param translation the translation as a 3 dimensioned vector
	 * @param scale the scale factor (applied to all axis)
	 * @return a new {@link Transform3DMatrix}
	 * @throws IllegalArgumentException if the rotation matrix is not 3x3 sized, if the translation Vector is not 3 dimensioned
	 */
	public Transform3DMatrix createTransform3DMatrix(Matrix rotation, Vector translation, double scale);
	
	/**
	 * Create a new {@link Transform3DMatrix} that relies on the 4x4 given matrix. <br><br>
	 * The 4x4 matrix represents a 3D affine transform defined such that:
	 * $$
	 * M = \begin{bmatrix} 
	 *        s_{x}r_{00} &amp; s_{x}r_{01} &amp; s_{x}r_{02} &amp; t_{x}  \\
	 *        s_{y}r_{10} &amp; s_{y}r_{11} &amp; s_{y}r_{12} &amp; t_{y}  \\
	 *        s_{z}r_{20} &amp; s_{z}r_{21} &amp; s_{z}r_{22} &amp; t_{z}  \\
	 *           0   &amp;    0   &amp;    0   &amp;  1  
	 *     \end{bmatrix}
	 * $$
	 * where:
	 * <ul>
	 * <li><i>r<sub>ii</sub></i> form the 3x3 rotation matrix
	 * <li><i>t<sub>k</sub></i> form the translation vector
	 * <li><i>s<sub>x</sub></i>, <i>s<sub>y</sub></i>, <i>s<sub>z</sub></i> are the scales values along axis
	 * </ul>
	 * @param matrix the 4x4 matrix that represents a 3D affine transform
	 * @return a new {@link Transform3DMatrix}
	 * @throws IllegalArgumentException if the input matrix is <code>null</code> or is not 4x4 sized
	 */
	public Transform3DMatrix createTransform3DMatrix(double[][] matrix);
	
	/**
	 * Create a new {@link Transform3DQuaternion} that represents the identity transform.
	 * @return a new {@link Transform3DQuaternion} that represents the identity transform
	 */
	public Transform3DQuaternion createTransform3DQuaternion();

}
