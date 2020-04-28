package org.jeometry.geom3D.transform;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;

/**
 * A transformation within a 3D space that relies on a {@link org.jeometry.math.Matrix4x4 4x4 matrix}. A transformation matrix is defined such as:
 * $$
 * T = \begin{bmatrix} 
 *        r_{00} &amp; r_{01} &amp; r_{22} &amp; t_{x}  \\
 *        r_{10} &amp; r_{11} &amp; r_{22} &amp; t_{x}  \\
 *        r_{20} &amp; r_{21} &amp; r_{22} &amp; t_{x}  \\
 *           0   &amp;    0   &amp;    0   &amp;  s  
 *     \end{bmatrix}
 * $$
 * where:
 * <ul>
 * <li><i>r<sub>ii</sub></i> form the 3x3 rotation matrix.
 * <li><i>t<sub>k</sub></i> form the translation vector.
 * <li><i>s</i> is the transformation scale.
 * </ul>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface Transform3DMatrix extends Transform3D{
	
    @Override
	public Transform3DMatrix invertTransform() throws IllegalStateException;
		
    @Override
	public Transform3DMatrix invertTransformAffect() throws IllegalStateException;
	
	/**
	 * Get the {@link org.jeometry.math.Matrix matrix} that describes this 3D transformation.
	 * @return the {@link org.jeometry.math.Matrix matrix} that describes this 3D transformation.
	 * @see #setMatrix(Matrix)
	 */
	public Matrix getMatrix();
	
	/**
	 * Copy the {@link org.jeometry.math.Matrix matrix} that describes this 3D transformation into the given one.
	 * @param matrix the matrix that has to store the copy.
	 * @return a reference to the <code>matrix</code> parameter.
	 * @throws IllegalArgumentException if the given matrix does not fit (not a 4x4 matrix).
	 * @see #setMatrix(Matrix)
	 */
	public Matrix getMatrix(Matrix matrix);
	
	/**
	 * Set the the {@link org.jeometry.math.Matrix matrix} that describes this 3D transformation. The input matrix has to be a 4x4 matrix.
	 * @param matrix the {@link org.jeometry.math.Matrix matrix} that describes this 3D transformation.
	 * @see #getMatrix()
	 * @throws IllegalArgumentException if the input matrix is not 4x4.
	 * @see #getMatrix()
	 * @see #getMatrix(Matrix)
	 */
	public void setMatrix(Matrix matrix);
	
}
