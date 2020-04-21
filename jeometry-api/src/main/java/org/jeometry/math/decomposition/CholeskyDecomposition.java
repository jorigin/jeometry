package org.jeometry.math.decomposition;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;
import org.jeometry.math.solver.Resolvable;

/**
 * This interface describes a Cholesky decomposition.<br>
 * <br>
 * Let <i>A</i> be a symmetric positive definite matrix, the Cholesky decomposition is a lower triangular matrix <i>R</i> with strictly positive diagonal entries such that:
 * <div style="text-align: center;"><i>A</i>&nbsp;=&nbsp;<i>R</i><i>R</i><sup>t</sup></div>
 * <br>
 * where <i>R</i><sup>t</sup> is the transpose of <i>R</i>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.2
 */
public interface CholeskyDecomposition extends Decomposition, Resolvable {

	/**
	 * The index of the lower triangular matrix matrix <i>R</i> within the {@link Decomposition#getComponents() decomposition components}. 
	 */
	public static final int COMPONENT_R_INDEX = 0;
	
	/**
	 * Get the lower triangular matrix <i>U</i>.
	 * @return the lower triangular matrix <i>U</i>
	 */
	public Matrix getR();
}
