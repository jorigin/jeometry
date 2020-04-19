package org.jeometry.math.decomposition;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;

/**
 * This interface describes a Lower Upper (LU) decomposition.<br>
 * <br>
 * Let <i>A</i> be a square matrix, the LU decomposition compute a lower triangular matrix <i>L</i> and an upper triangular matrix <i>U</i> such that: <br>
 * <br>
 * <i>L</i>&times;<i>U</i>&nbsp;=&nbsp;<i>A</i><br>
 * <br>
 * where &times; is the standard matrix product.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface LUDecomposition extends Decomposition {

	/**
	 * The index of the triangular lower <i>L</i> matrix within the {@link Decomposition#getComponents() decomposition components}. 
	 */
	public static final int COMPONENT_L_INDEX = 0;
	
	/**
	 * The index of the triangular upper <i>U</i> matrix within the {@link Decomposition#getComponents() decomposition components}. 
	 */
	public static final int COMPONENT_U_INDEX = 1;
	
	/**
	 * Get the lower (<i>L</i>) triangular matrix from the decomposition.
	 * @return the lower triangular matrix from the decomposition.
	 * @see #getUpper()
	 */
	public Matrix getLower();
	
	/**
	 * Get the upper (<i>U</i>) triangular matrix from the decomposition.
	 * @return the upper (<i>U</i>) triangular matrix from the decomposition.
	 * @see #getLower()
	 */
	public Matrix getUpper();
	
	/**
	 * Get the permutation matrix (pivot) that has been used if the decomposition is formally:<br>
	 * <br>
	 * <i>A</i>&nbsp;=&nbsp;<i>P</i><i>L</i><i>U</i>
	 * <br>
	 * @return the pivot that has been used.
	 */
	public Matrix getPivot();
}
