package org.jeometry.math.solver;

import org.jeometry.Geometry;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

/**
 * This interface describes a linear system solver using matrices. 
 * Let <i>A</i> a general matrix and <i>B</i> a column matrix with the same number of columns as <i>A</i>. 
 * Solving a linear system consists in the determination of the matrix <i>X</i> that is such that:<br>
 * <br>
 * <i>A</i>&times;<i>X</i>&nbsp;=&nbsp;<i>B</i>
 * <br>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} b{@value Geometry#BUILD}
 * @since 1.0.0
 */
public interface Solver {

	/**
	 * Identifies a linear solver that uses <a href="https://mathworld.wolfram.com/GaussianElimination.html">Gauss elimination</a>. 
	 */
	public static int METHOD_GAUSS    = 1;
	
	/**
	 * Identifies a linear solver that uses LU decomposition.
	 */
	public static int METHOD_LU       = 2;
	
	/**
	 * Identifies a linear solver that uses QR decomposition.
	 */
	public static int METHOD_QR       = 3;	
	
	/**
	 * Identifies a linear solver that uses Cholesky decomposition.
	 */
	public static int METHOD_CHOLESKY = 4;
	
	/**
	 * Return the method used by the solver.
	 * @return the method used by the solver
	 * @see #METHOD_GAUSS
	 * @see #METHOD_LU
	 * @see #METHOD_QR
	 */
	public int getMethod();
	
	/**
	 * Solve the system defined by <i>a</i>&times;<i>x</i>&nbsp;=&nbsp;<i>b</i>.
	 * @param a the <i>a</i> matrix that contains the coefficients
	 * @param b the <i>b</i> matrix that contains the constants
	 * @return the <i>x</i> matrix that contains the solution or <code>null</code> if no solution has been found
	 * @throws IllegalArgumentException if the input matrices cannot lead to a solution (singular, non invertible, ...)
	 */
	public Matrix solve(Matrix a, Matrix b);
	
	/**
	 * Solve the system defined by <i>a</i>&times;<i>x</i>&nbsp;=&nbsp;<i>b</i>.
	 * @param a the <i>a</i> matrix that contains the coefficients
	 * @param b the <i>b</i> matrix that contains the constants
	 * @param x the <i>x</i> matrix that contains the solution
	 * @return the same reference as <code>x</code> or <code>null</code> if no solution has been found
	 * @throws IllegalArgumentException if the input matrices cannot lead to a solution (singular, non invertible, ...)
	 */
	public Matrix solve(Matrix a, Matrix b, Matrix x);
	
	/**
	 * Solve the system defined by <i>a</i>&times;<i>x</i>&nbsp;=&nbsp;<i>b</i>.
	 * @param a the <i>a</i> matrix that contains the coefficients
	 * @param b the <i>b</i> vector that contains the constants
	 * @return the <i>x</i> vector that contains the solution or <code>null</code> if no solution has been found
	 * @throws IllegalArgumentException if the input matrices cannot lead to a solution (singular, non invertible, ...)
	 */
	public Vector solve(Matrix a, Vector b);
	
	/**
	 * Solve the system defined by <i>a</i>&times;<i>x</i>&nbsp;=&nbsp;<i>b</i>.
	 * @param a the  matrix that contains the coefficients
	 * @param b  the vector that contains the constants
	 * @param x the vector that contains the solution
	 * @return a reference on <code>x</code> or <code>null</code> if no solution has been found
	 * @throws IllegalArgumentException if the input matrices cannot lead to a solution (singular, non invertible, ...)
	 */
	public Vector solve(Matrix a, Vector b, Vector x);
}
