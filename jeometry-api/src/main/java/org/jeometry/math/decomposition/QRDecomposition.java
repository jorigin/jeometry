package org.jeometry.math.decomposition;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;
import org.jeometry.math.solver.Resolvable;

/**
 * This interface describes a QR decomposition.<br>
 * <br>
 * Given a matrix <i>A</i>, its QR-decomposition is a matrix decomposition of the form
 * <div style="text-align: center;"><i>A</i>&nbsp;=&nbsp;<i>Q</i><i>R</i></div>
 * <br>
 * where:
 * <ul>
 * <li><i>R</i> is an upper triangular matrix
 * <li><i>Q</i> is an orthogonal matrix 
 * </ul>
 * The matrix <i>Q</i> is sich that:
 * <div style="text-align: center;"><i>Q</i><sup>t</sup>Q&nbsp;=&nbsp;<i>I</i></div>
 * <br>
 * where <i>Q</i><sup>t</sup> is the transpose of <i>Q</i> and <i>I</i> is the identity matrix.
 * <br>
 * <br>
 * This matrix decomposition can be used to solve linear systems of equations. 
 * <br>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.2
 */
public interface QRDecomposition extends Decomposition, Resolvable {

	/**
	 * The index of the orthogonal matrix <i>Q</i> within the {@link Decomposition#getComponents() decomposition components}. 
	 */
	public static final int COMPONENT_Q_INDEX = 0;
	
	/**
	 * The index of the upper triangular matrix <i>R</i> within the {@link Decomposition#getComponents() decomposition components}. 
	 */
	public static final int COMPONENT_R_INDEX = 1;
	
	/**
	 * Get the orthogonal matrix <i>Q</i>.
	 * @return the orthogonal matrix <i>Q</i>
	 * @see #getR()
	 */
	public Matrix getQ();

	/**
	 * Get the upper triangular matrix <i>R</i>.
	 * @return the upper triangular matrix <i>R</i>
	 */
    public Matrix getR();
    
}
