package org.jeometry.math.decomposition;

import org.jeometry.Geometry;
import org.jeometry.math.Matrix;

/**
 * This interface describes a Singular Values Decomposition (SVD).<br>
 * <br>
 * Let <i>A</i> be a real matrix that has a matrix of {@link EigenDecomposition Eigen vectors} P that is not invertible 
 * (no {@link EigenDecomposition Eigen decomposition} can be computed). 
 * <br><br>
 * If <i>A</i> is an <i>m</i>&nbsp;&times;&nbsp;<i>n</i> real matrix with <i>m</i>&nbsp;&gt;&nbsp;<i>n</i>, then <i>A</i> can be written using a so-called singular value decomposition of the form 
 * <i>A</i>&nbsp;=&nbsp;<i>U</i><i>D</i><i>V</i><sup>T</sup><br>
 * <br>
 * where:<br>
 * <ul>
 * <li><i>U</i> is a <i>m</i>&nbsp;&times;&nbsp;<i>m</i> matrix with orthogonal columns (<i>U</i><sup>T</sup><i>U</i>&nbsp;=&nbsp;<i>I</i>)
 * <li><i>D</i> is a <i>m</i>&nbsp;&times;&nbsp;<i>n</i> diagonal matrix
 * <li><i>V</i> is a <i>n</i>&nbsp;&times;&nbsp;<i>n</i> matrix with orthogonal columns (<i>V</i><sup>T</sup><i>V</i>&nbsp;=&nbsp;<i>I</i>)
 * </ul>
 * <br>
 * <br>
 * <i>source</i>: <a href="http://mathworld.wolfram.com/SingularValueDecomposition.html">Wolfram math</a>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} b{@value Geometry#BUILD}
 * @since 1.0.0
 */
public interface SVDDecomposition extends Decomposition {

	/**
	 * Get the <i>U</i> matrix that is a <i>m</i>&nbsp;&times;&nbsp;<i>m</i> matrix with orthogonal columns (<i>U</i><sup>T</sup><i>U</i>&nbsp;=&nbsp;<i>I</i>).
	 * @return the <i>U</i> matrix
	 * @see #getD()
	 * @see #getV()
	 */
	public Matrix getU();
	
	/**
	 * Get the <i>D</i> matrix that is is a <i>m</i>&nbsp;&times;&nbsp;<i>n</i> diagonal matrix.
	 * @return the <i>D</i> matrix
	 * @see #getU()
	 * @see #getV()
	 */
	public Matrix getD();
	
	/**
	 * Get the <i>V</i> matrix that is a <i>n</i>&nbsp;&times;&nbsp;<i>n</i> matrix with orthogonal columns (<i>V</i><sup>T</sup><i>V</i>&nbsp;=&nbsp;<i>I</i>).
	 * @return the <i>V</i> matrix
	 * @see #getU()
	 * @see #getD()
	 */
	public Matrix getV();
}
