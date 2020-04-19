package org.jeometry.math.decomposition;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;

/**
 *  This interface describes an Eigen decomposition.<br>
 *  Let <i>A</i> be a square matrix. According to the <a href="https://mathworld.wolfram.com/EigenDecompositionTheorem.html">Eigen decomposition theorem</a>, we can write:<br>
 *  <div style="text-align: center;"><i>A</i>&nbsp;=&nbsp;<i>V</i><i>D</i><i>V</i><sup>-1</sup><br></div>
 *  Where:
 *  <ul>
 *  <li><i>V</i> is a square matrix made of eigenvectors
 *  <li><i>D</i> is a diagonal matrix made of eigenvalues
 *  </ul>
 *  If <i>V</i> is not a square matrix, then it cannot have a matrix inverse and <i>A</i> does not have an eigen decomposition. 
 *  In this case, if <i>V</i> is <i>m</i>&times;<i>n</i> (with <i>m</i>&gt;<i>n</i>), then <i>A</i> can be written using a {@link SVDDecomposition Singular Values Decomposition}. 
 * <p>
 *  If <i>A</i> is symmetric, then: <br>
 *  <div style="text-align: center;"><i>A</i>&nbsp;=&nbsp;<i>VDV<sup>T</sup></i></div><br>
 *  where <i>D</i> is diagonal and <i>V</i> is orthogonal (<i>VV<sup>T</sup></i>&nbsp;=&nbsp;<i>I</i>).
 * <p>
 *  If <i>A</i> is not symmetric, then the eigenvalue matrix <i>D</i> is block diagonal
 *  with:
 *  <ul>
 *  <li>the real eigenvalues in 1-by-1 blocks
 *  <li>any complex eigenvalues &lambda;&nbsp;+&nbsp;<i>i</i>&times;&mu; in 2-by-2 blocks [&lambda;, &mu;; -&mu;, &lambda;]
 *  </ul>
 *  
 *  The columns of <i>V</i> represent the eigenvectors in the sense that:<br>
 *  <div style="text-align: center;"><i>AV</i>&nbsp;= <i>VD</i></div>
 *  <br>
 *  The matrix <i>V</i> may be badly conditioned, or even singular, so the validity of the equation cannot be sure for all matrices.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface EigenDecomposition extends Decomposition {
	
	/**
	 * The index of the eigenvectors <i>V</i> matrix within the {@link Decomposition#getComponents() decomposition components}. 
	 */
	public static final int COMPONENT_V_INDEX = 0;
	
	/**
	 * The index of the diagonal eigenvalues <i>D</i> matrix within the {@link Decomposition#getComponents() decomposition components}. 
	 */
	public static final int COMPONENT_D_INDEX = 1;
	
	/**
	 * Get the diagonal matrix of eigenvalues, denoted <code>D</code>.
	 * @return  the diagonal matrix of eigenvalues, denoted <code>D</code>
	 */
	public Matrix getD();
	
	/**
	 * Get the matrix of eigenvectors, denoted <i>V</i>.
	 * @return the matrix of eigenvectors, denoted <i>V</i> 
	 */
	public Matrix getV();
}
