package org.jeometry.simple.math.decomposition;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;
import org.jeometry.math.decomposition.QRDecomposition;

/**
 * 
 * A simple implementation of {@link QRDecomposition QRDecomposition}.<br><br>
 * This implantation is inspired by <a href="https://math.nist.gov/javanumerics/jama/">Jama</a> <a href="https://math.nist.gov/javanumerics/jama/doc/">QR Decomposition</a>.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.2
 */
public class SimpleQRDecomposition implements QRDecomposition {

	/**
	 * The QR matrix.
	 */
	private Matrix QR;

	/**
	 * The Q matrix.
	 */
	private Matrix Q = null;

	/**
	 * The R matrix.
	 */
	private Matrix R = null;

	/**
	 * The H matrix.
	 */
	private Matrix H = null;

	/**
	 * The input rows count.
	 */
	private int inputRowsCount;

	/**
	 * The input columns count.
	 */
	private int inputColumnsCount;

	/** Array for internal storage of diagonal of R.
   @serial diagonal of R.
	 */
	private double[] Rdiag;

	/**
	 * Create a new decomposition from the given matrix.
	 * @param matrix the matrix to decompose
	 */
	public SimpleQRDecomposition(Matrix matrix) {
		// Initialize.
		this.QR = JeometryFactory.createMatrix(matrix);

		this.inputRowsCount = matrix.getRowsCount();
		this.inputColumnsCount = matrix.getColumnsCount();

		this.Rdiag = new double[this.inputColumnsCount];

		// Main loop.
		for (int k = 0; k < this.inputColumnsCount; k++) {
			// Compute 2-norm of k-th column without under/overflow.
			double nrm = 0;
			for (int i = k; i < this.inputRowsCount; i++) {
				nrm = hypot(nrm,this.QR.getValue(i, k));
			}

			if (nrm != 0.0) {
				// Form k-th Householder vector.
				if (this.QR.getValue(k, k) < 0) {
					nrm = -nrm;
				}
				for (int i = k; i < this.inputRowsCount; i++) {
					this.QR.setValue(i, k,  this.QR.getValue(i, k) / nrm);
				}
				this.QR.setValue(k, k,  this.QR.getValue(k, k) + 1.0d);

				// Apply transformation to remaining columns.
				for (int j = k+1; j < this.inputColumnsCount; j++) {
					double s = 0.0; 
					for (int i = k; i < this.inputRowsCount; i++) {
						s += this.QR.getValue(i, k)*this.QR.getValue(i, j);
					}
					s = -s/this.QR.getValue(k, k);
					for (int i = k; i < this.inputRowsCount; i++) {
						this.QR.setValue(i, j,  this.QR.getValue(i, j) + s*this.QR.getValue(i, k));
					}
				}
			}
			this.Rdiag[k] = -nrm;
		}

		// Create R matrix
		this.R = JeometryFactory.createMatrix(this.inputColumnsCount,this.inputColumnsCount);
		for (int i = 0; i < this.inputColumnsCount; i++) {
			for (int j = 0; j < this.inputColumnsCount; j++) {
				if (i < j) {
					this.R.setValue(i, j, this.QR.getValue(i, j));
				} else if (i == j) {
					this.R.setValue(i, j, this.Rdiag[i]);
				} else {
					this.R.setValue(i, j, 0.0);
				}
			}
		}

		// Create Q matrix
		this.Q = JeometryFactory.createMatrix(this.inputRowsCount,this.inputColumnsCount);
		for (int k = this.inputColumnsCount-1; k >= 0; k--) {
			for (int i = 0; i < this.inputRowsCount; i++) {
				this.Q.setValue(i, k, 0.0);
			}
			this.Q.setValue(k, k, 1.0);
			for (int j = k; j < this.inputColumnsCount; j++) {
				if (this.QR.getValue(k, k) != 0) {
					double s = 0.0;
					for (int i = k; i < this.inputRowsCount; i++) {
						s += this.QR.getValue(i, k)*this.Q.getValue(i, j);
					}
					s = -s/this.QR.getValue(k, k);
					for (int i = k; i < this.inputRowsCount; i++) {
						this.Q.setValue(i, j,  this.Q.getValue(i, j) + s*this.QR.getValue(i, k));
					}
				}
			}
		}

		// Create H matrix
		this.H = JeometryFactory.createMatrix(this.inputRowsCount,this.inputColumnsCount);
		for (int i = 0; i < this.inputRowsCount; i++) {
			for (int j = 0; j < this.inputColumnsCount; j++) {
				if (i >= j) {
					this.H.setValue(i, j, this.QR.getValue(i, j));
				} else {
					this.H.setValue(i, j, 0.0);
				}
			}
		}
	}

	@Override
	public List<Matrix> getComponents() {
		List<Matrix> list = new ArrayList<Matrix>(2);
		list.add(QRDecomposition.COMPONENT_Q_INDEX, getQ());
		list.add(QRDecomposition.COMPONENT_R_INDEX, getR());
		return null;
	}

	@Override
	public Matrix getQ() {
		return this.Q;
	}

	@Override
	public Matrix getR() {
		return this.R;
	}

	/**
	 * Compute the matrix <i>X</i> that minimizes the linear system:<br>
	 * <div style="text-align: center"><i>AX</i>&nbsp;=&nbsp;<i>B</i></div>
	 * <br>
	 * where <i>A</i> is the matrix from which this decomposition is computed.<br><br>
	 * The computation is performed using least squares.
	 * <br><br>
	 * This linear solving is equivalent to find the <i>X</i> that minimizes the linear system:<br><br>
	 * <div style="text-align: center">Q*R*X-B</div>
	 * <br>
	 * @param b the constants parameters
	 * @return the matrix <i>X</i> that solve the linear system <i>AX</i>&nbsp;=&nbsp;<i>B</i>
	 * @throws IllegalArgumentException ix the dimension of <i>B</i> does not match the system
	 * @throws IllegalStateException if the <i>A</i> matrix is rank deficient
	 */
	@Override
	public Matrix solve(Matrix b) {
		return solve(b, JeometryFactory.createMatrix(this.inputColumnsCount, 1));
	}

	/**
	 * Compute the matrix <i>X</i> that minimizes the linear system:<br>
	 * <div style="text-align: center"><i>AX</i>&nbsp;=&nbsp;<i>B</i></div>
	 * <br>
	 * where <i>A</i> is the matrix from which this decomposition is computed.<br><br>
	 * The computation is performed using least squares.
	 * <br><br>
	 * This linear solving is equivalent to find the <i>X</i> that minimizes the linear system:<br><br>
	 * <div style="text-align: center">Q*R*X-B</div>
	 * <br>
	 * @param b the constants parameters
	 * @param x the result
	 * @return the matrix <i>X</i> that solve the linear system <i>AX</i>&nbsp;=&nbsp;<i>B</i>
	 * @throws IllegalArgumentException ix the dimension of <i>B</i> does not match the system
	 * @throws IllegalStateException if the <i>A</i> matrix is rank deficient
	 */
	@Override
	public Matrix solve(Matrix b, Matrix x) {
		if (b == null) {
			throw new IllegalArgumentException("Constant matrix is null");
		}

		if (b.getRowsCount() != this.inputRowsCount) {
			throw new IllegalArgumentException("Invalid constant matrix rows count "+b.getRowsCount()+", expected "+this.inputRowsCount);
		}

		if (x == null) {
			throw new IllegalArgumentException("Result matrix is null");
		}

		if (x.getRowsCount() != this.inputColumnsCount) {
			throw new IllegalArgumentException("Invalid result matrix rows count "+x.getRowsCount()+", expected "+this.inputColumnsCount);
		}

		if (!this.isFullRank()) {
			throw new RuntimeException("Matrix is rank deficient.");
		}

		// Copy right hand side
		int nx = b.getColumnsCount();

		Matrix xTmp = JeometryFactory.createMatrix(b);

		// Compute Y = transpose(Q)*B
		for (int k = 0; k < this.inputColumnsCount; k++) {
			for (int j = 0; j < nx; j++) {
				double s = 0.0; 
				for (int i = k; i < this.inputRowsCount; i++) {
					s += this.QR.getValue(i, k)*xTmp.getValue(i, j);
				}
				s = -s/this.QR.getValue(k, k);
				for (int i = k; i < this.inputRowsCount; i++) {
					xTmp.setValue(i, j, xTmp.getValue(i, j) + s*this.QR.getValue(i, k));
				}
			}
		}

		// Solve R*X = Y;
		for (int k = this.inputColumnsCount-1; k >= 0; k--) {
			for (int j = 0; j < nx; j++) {
				xTmp.setValue(k, j,  xTmp.getValue(k, j) / this.Rdiag[k]);
			}
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < nx; j++) {
					xTmp.setValue(i, j,  xTmp.getValue(i, j) - xTmp.getValue(k, j)*this.QR.getValue(i, k));
				}
			}
		}

		x.setValues(xTmp.extract(0, 0, this.inputColumnsCount, 1));

		return x;
	}

	/**
	 * Compute the vector <i>X</i> that minimizes the linear system:<br>
	 * <div style="text-align: center"><i>AX</i>&nbsp;=&nbsp;<i>B</i></div>
	 * <br>
	 * where <i>A</i> is the matrix from which this decomposition is computed.<br><br>
	 * The computation is performed using least squares.
	 * <br><br>
	 * This linear solving is equivalent to find the <i>X</i> that minimizes the linear system:<br><br>
	 * <div style="text-align: center">Q*R*X-B</div>
	 * <br>
	 * @param b the constants parameters vector
	 * @return the matrix <i>X</i> that solve the linear system <i>AX</i>&nbsp;=&nbsp;<i>B</i>
	 * @throws IllegalArgumentException ix the dimension of <i>B</i> does not match the system
	 * @throws IllegalStateException if the <i>A</i> matrix is rank deficient
	 */
	@Override
	public Vector solve(Vector b) {
		return solve(b, JeometryFactory.createVector(this.inputColumnsCount));
	}

	@Override
	public Vector solve(Vector b, Vector x) {
		if (b == null) {
			throw new IllegalArgumentException("Constant matrix is null");
		}

		if (b.getDimension() != this.inputRowsCount) {
			throw new IllegalArgumentException("Invalid constant matrix rows count "+b.getDimension()+", expected "+this.inputRowsCount);
		}

		if (x == null) {
			throw new IllegalArgumentException("Result matrix is null");
		}

		if (x.getDimension() != this.inputColumnsCount) {
			throw new IllegalArgumentException("Invalid result matrix rows count "+x.getDimension()+", expected "+this.inputColumnsCount);
		}

		if (!this.isFullRank()) {
			throw new RuntimeException("Matrix is rank deficient.");
		}

		Vector xTmp = JeometryFactory.createVector(b);
		
		xTmp.setValues(b);

		// Compute Y = transpose(Q)*B
		for (int k = 0; k < this.inputColumnsCount; k++) {
			double s = 0.0; 
			for (int i = k; i < this.inputRowsCount; i++) {
				s += this.QR.getValue(i, k)*xTmp.getValue(i);
			}
			s = -s/this.QR.getValue(k, k);
			for (int i = k; i < this.inputRowsCount; i++) {
				xTmp.setValue(i, xTmp.getValue(i) + s*this.QR.getValue(i, k));
			}
		}

		// Solve R*X = Y;
		for (int k = this.inputColumnsCount-1; k >= 0; k--) {

			xTmp.setValue(k,  xTmp.getValue(k) / this.Rdiag[k]);

			for (int i = 0; i < k; i++) {
				xTmp.setValue(i,  xTmp.getValue(i) - xTmp.getValue(k)*this.QR.getValue(i, k));
			}
		}

		x.setValues(xTmp.extract(0, this.inputColumnsCount));
		
		return x;
	}

	/** Return the Householder vectors (Lower trapezoidal matrix whose columns define the reflections)
	 * @return the Householder vectors
	 */
	public Matrix getH () {
		return this.H;
	}

	/** Get if the matrix full rank.
	 * @return <code>true</code> if <i>R</i>, and hence the decomposed matrix, has full rank
	 */
	public boolean isFullRank () {
		for (int j = 0; j < this.inputColumnsCount; j++) {
			if (this.Rdiag[j] == 0)
				return false;
		}
		return true;
	}

	/** Compute sqrt(a^2 + b^2) without under/overflow. 
	 * @param a the first
	 * @param b the second
	 * @return the result
	 **/
	private double hypot(double a, double b) {
		double r;
		if (Math.abs(a) > Math.abs(b)) {
			r = b/a;
			r = Math.abs(a)*Math.sqrt(1+r*r);
		} else if (b != 0) {
			r = a/b;
			r = Math.abs(b)*Math.sqrt(1+r*r);
		} else {
			r = 0.0;
		}
		return r;
	}

}
