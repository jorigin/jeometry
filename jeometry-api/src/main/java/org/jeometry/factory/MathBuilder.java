package org.jeometry.factory;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;
import org.jeometry.math.Quaternion;
import org.jeometry.math.Vector;
import org.jeometry.math.decomposition.CholeskyDecomposition;
import org.jeometry.math.decomposition.EigenDecomposition;
import org.jeometry.math.decomposition.LUDecomposition;
import org.jeometry.math.decomposition.QRDecomposition;
import org.jeometry.math.decomposition.SVDDecomposition;
import org.jeometry.math.solver.Solver;

/**
 * An interface that describes a matrix builder. A math builder enables to create implementations of interfaces described within the <code>math</code> package ({@link Matrix}, {@link Vector}, {@link Solver}, ...).
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public interface MathBuilder {
	
	/**
	 * Create a new {@link Vector} with the given <code>size</code>..
	 * @param size the size of the vector.
	 * @return a new {@link Vector} implementation with the given <code>size</code>.
	 */
	public Vector createVector(int size);
	
	/**
	 * Create a new {@link Vector} with the given <code>components</code>.
	 * @param components the components of the vector.
	 * @return a new {@link Vector} implementation with the given <code>size</code>.
	 */
	public Vector createVector(double[] components);

	/**
	 * Create a new {@link Vector} by copying the given source.
	 * @param source the source to copy
	 * @return a new {@link Vector} by copying the given source.
	 */
	public Vector createVector(Vector source);
	
	/**
	 * Create a new instance of {@link Matrix}. 
	 * @param rows the number of rows of the matrix.
	 * @param cols the number of columns of the matrix.
	 * @return a new instance of {@link Matrix}. 
	 * @throws IllegalArgumentException if an instantiation error occurs.
	 */
	public Matrix createMatrix(int rows, int cols);

	/**
	 * Create a new instance of {@link Matrix}. 
	 * @param data the values of the matrix.
	 * @return a new instance of {@link Matrix}. 
	 * @throws IllegalArgumentException if an instantiation error occurs.
	 */
	public Matrix createMatrix(double[][] data);
	
	/**
	 * Create a new instance of {@link Matrix}. 
	 * @param rows the number of rows of the matrix
	 * @param columns the number of columns of the matrix
	 * @param data the data of the matrix within an single dimensional array
	 * @param ordering the ordering of the data in order to be processed (can be either {@link Matrix#ROW_MAJOR} or {@link Matrix#COLUMN_MAJOR})
	 * @return a new instance of {@link Matrix} 
	 */
	public Matrix createMatrix(int rows, int columns, double[] data, int ordering);
	
	/**
	 * Create a new instance of {@link Matrix} by copying the given one.
	 * @param matrix the {@link Matrix matrix} to copy
	 * @return a new instance of {@link Matrix} 
	 */
	public Matrix createMatrix(Matrix matrix);
	
	/**
	 * Create a new square {@link Matrix} with the given <code>size</code> and that is initialized to identity.
	 * @param size the number of rows and columns of the matrix.
	 * @return a new square {@link Matrix} with the given <code>size</code> and that is initialized to identity.
	 */
	public Matrix createMatrixEye(int size);
	
	/**
	 * Create a new instance of {@link Quaternion}. 
	 * @return a new instance of {@link Quaternion}
	 * @throws IllegalArgumentException if an instantiation error occurs.
	 */
	public Quaternion createQuaternion();
	
	/**
	 * Create a new instance of {@link Quaternion}. 
	 * @param scalar the scalar of the parameter
	 * @param i the i component
	 * @param j the j component
	 * @param k the k component
	 * @return a new instance of {@link Quaternion} 
	 * @throws IllegalArgumentException if an instantiation error occurs.
	 */
	public Quaternion createQuaternion(double scalar, double i, double j, double k);
	
	/**
	 * Create a {@link Solver matrix solver}.
	 * @param type the type of the solver (see {@link Solver#METHOD_GAUSS}, see {@link Solver#METHOD_LU}, ...)
	 * @return the solver
	 */
	public Solver createSolver(int type);
	
	/**
	 * Create a {@link Solver matrix solver} using a default method.
	 * @return the solver
	 */
	public Solver createSolver();
	
	/**
	 * Create a new {@link LUDecomposition LU decomposition} from the given matrix.
	 * @param matrix the matrix to decompose
	 * @return the {@link LUDecomposition LU decomposition} from the given matrix
	 */
	public LUDecomposition createLUDecomposition(Matrix matrix);
	
	/**
	 * Create a new {@link EigenDecomposition Eigen decomposition} from the given matrix.
	 * @param matrix the matrix to decompose
	 * @return the {@link EigenDecomposition Eigen decomposition} from the given matrix
	 */
	public EigenDecomposition createEigenDecomposition(Matrix matrix);
	
	/**
	 * Create a new {@link SVDDecomposition Singular Values (SVD) decomposition} from the given matrix.
	 * @param matrix the matrix to decompose
	 * @return the {@link SVDDecomposition Singular Values (SVD) decomposition}  from the given matrix
	 */
	public SVDDecomposition createSVDDecomposition(Matrix matrix);
	
	/**
	 * Create a new {@link QRDecomposition QR decomposition} from the given matrix.
	 * @param matrix the matrix to decompose
	 * @return the {@link QRDecomposition QR decomposition} from the given matrix
	 */
	public QRDecomposition createQRDecomposition(Matrix matrix);
	
	/**
	 * Create a new {@link CholeskyDecomposition Cholesky decomposition} from the given matrix.
	 * @param matrix the matrix to decompose
	 * @return the {@link CholeskyDecomposition Cholesky decomposition} from the given matrix
	 */
	public CholeskyDecomposition createCholeskyDecomposition(Matrix matrix);
}
