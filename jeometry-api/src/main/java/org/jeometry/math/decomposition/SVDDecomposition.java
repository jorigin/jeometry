package org.jeometry.math.decomposition;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;

/**
 * This interface describes a Singular Values Decomposition (SVD).<br>
 * <br>
 * Let <i>A</i> be a real matrix that has a matrix of {@link EigenDecomposition Eigen vectors} P that is not invertible 
 * (no {@link EigenDecomposition Eigen decomposition} can be computed). 
 * <br><br>
 * If <i>A</i> is an <i>m</i>&nbsp;&times;&nbsp;<i>n</i> real matrix with <i>m</i>&nbsp;&gt;&nbsp;<i>n</i>, then <i>A</i> can be written using a so-called singular value decomposition of the form 
 * <i>A</i>&nbsp;=&nbsp;<i>U</i><i>S</i><i>V</i><sup>T</sup><br>
 * <br>
 * where:<br>
 * <ul>
 * <li><i>U</i> is a <i>m</i>&nbsp;&times;&nbsp;<i>m</i> matrix with orthogonal columns (<i>U</i><sup>T</sup><i>U</i>&nbsp;=&nbsp;<i>I</i>)
 * <li><i>S</i> is a <i>m</i>&nbsp;&times;&nbsp;<i>n</i> diagonal matrix made of the singular values
 * <li><i>V</i> is a <i>n</i>&nbsp;&times;&nbsp;<i>n</i> matrix with orthogonal columns (<i>V</i><sup>T</sup><i>V</i>&nbsp;=&nbsp;<i>I</i>)
 * </ul>
 * <br>
 * <br>
 * <i>source</i>: <a href="http://mathworld.wolfram.com/SingularValueDecomposition.html">Wolfram math</a>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface SVDDecomposition extends Decomposition {

	/**
	 * The index of the <i>U</i> matrix within the {@link Decomposition#getComponents() decomposition components}. 
	 */
	public static final int COMPONENT_U_INDEX = 0;
	
	/**
	 * The index of the <i>S</i> matrix within the {@link Decomposition#getComponents() decomposition components}. 
	 */
	public static final int COMPONENT_S_INDEX = 1;
	
	/**
	 * The index of the <i>V</i> matrix within the {@link Decomposition#getComponents() decomposition components}. 
	 */
	public static final int COMPONENT_V_INDEX = 2;
	
	/**
	 * Get the <i>U</i> matrix that is a <i>m</i>&nbsp;&times;&nbsp;<i>m</i> matrix with orthogonal columns (<i>U</i><sup>T</sup><i>U</i>&nbsp;=&nbsp;<i>I</i>).
	 * @return the <i>U</i> matrix
	 * @see #getS()
	 * @see #getV()
	 */
	public Matrix getU();
	
	/**
	 * Get the <i>S</i> matrix that is is a <i>m</i>&nbsp;&times;&nbsp;<i>n</i> diagonal matrix made of singular values.
	 * @return the <i>S</i> matrix
	 * @see #getU()
	 * @see #getV()
	 */
	public Matrix getS();
	
	/**
	 * Get the <i>V</i> matrix that is a <i>n</i>&nbsp;&times;&nbsp;<i>n</i> matrix with orthogonal columns (<i>V</i><sup>T</sup><i>V</i>&nbsp;=&nbsp;<i>I</i>).
	 * @return the <i>V</i> matrix
	 * @see #getU()
	 * @see #getS()
	 */
	public Matrix getV();
}
