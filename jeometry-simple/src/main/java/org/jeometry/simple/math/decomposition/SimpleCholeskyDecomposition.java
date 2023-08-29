package org.jeometry.simple.math.decomposition;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;
import org.jeometry.math.decomposition.CholeskyDecomposition;

/**
 * A simple implementation of {@link CholeskyDecomposition Cholesky decomposition}.<br><br>
 * This implantation is inspired by <a href="https://math.nist.gov/javanumerics/jama/">Jama</a> <a href="https://math.nist.gov/javanumerics/jama/doc/">Cholesky Decomposition</a>.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.2
 */
public class SimpleCholeskyDecomposition implements CholeskyDecomposition {

	/**
	 * The U matrix.
	 */
	private Matrix u = null;

	/** 
	 * Array for internal storage of decomposition.
	 */
	private double[][] R;

	/**
	 * Row and column dimension (square matrix).
	 */
	private int n;

	/** 
	 * Symmetric and positive definite flag.
	 */
	private boolean isspd;

	/**
	 * Create a new {@link CholeskyDecomposition Cholesky decomposition} of the given matrix.
	 * @param matrix the matrix to decompose
	 */
	public SimpleCholeskyDecomposition(Matrix matrix) {

		// Initialize.
		this.n = matrix.getRowsCount();
		this.R = new double[this.n][this.n];
		this.isspd = (matrix.getColumnsCount() == this.n);
		// Main loop.
		for (int j = 0; j < this.n; j++) {
			double[] Lrowj = this.R[j];
			double d = 0.0;
			for (int k = 0; k < j; k++) {
				double[] Lrowk = this.R[k];
				double s = 0.0;
				for (int i = 0; i < k; i++) {
					s += Lrowk[i]*Lrowj[i];
				}
				Lrowj[k] = s = (matrix.getValue(j, k) - s)/this.R[k][k];
				d = d + s*s;
				this.isspd = this.isspd & (matrix.getValue(k, j) == matrix.getValue(j, k)); 
			}
			d = matrix.getValue(j, j) - d;
			this.isspd = this.isspd & (d > 0.0);
			this.R[j][j] = Math.sqrt(Math.max(d,0.0));
			for (int k = j+1; k < this.n; k++) {
				this.R[j][k] = 0.0;
			}
		}
		
		this.u = JeometryFactory.createMatrix(this.R);
	}


	@Override
	public List<Matrix> getComponents() {
		List<Matrix> list = new ArrayList<Matrix>(1);
		list.add(this.u);
		return list;
	}

	@Override
	public Matrix getR() {
		return this.u;
	}

	@Override
	public Matrix solve(Matrix b) {
		return solve(b, JeometryFactory.createMatrix(b));
	}

	@Override
	public Matrix solve(Matrix b, Matrix x) {
		if (b == null) {
			throw new IllegalArgumentException("Null constant vector.");
		}
		
		if (b.getRowsCount() != this.n) {
			throw new IllegalArgumentException("Invalid constant matrix rows count "+b.getRowsCount()+", expected "+this.n);
		}
		
		if (x == null) {
			throw new IllegalArgumentException("Null result vector.");
		}
		
		if (x.getRowsCount() != b.getRowsCount()) {
			throw new IllegalArgumentException("Invalid constant matrix rows count "+b.getRowsCount()+", expected "+this.n);
		}
		
		if (!this.isspd) {
			throw new RuntimeException("Cholesky cannot resolve from non symmetric positive definite matrix.");
		}

		// Copy right hand side.
		int nx = b.getColumnsCount();

		x.setValues(b);
		
		// Solve L*Y = B;
		for (int k = 0; k < this.n; k++) {
			for (int j = 0; j < nx; j++) {
				for (int i = 0; i < k ; i++) {
					x.setValue(k, j,  x.getValue(k, j) - x.getValue(i, j)*this.R[k][i]);
				}
				x.setValue(k, j,  x.getValue(k,  j) / this.R[k][k]);
			}
		}

		// Solve L'*X = Y;
		for (int k = this.n-1; k >= 0; k--) {
			for (int j = 0; j < nx; j++) {
				for (int i = k+1; i < this.n ; i++) {
					x.setValue(k, j,  x.getValue(k, j) - x.getValue(i, j)*this.R[i][k]);
				}
				x.setValue(k, j, x.getValue(k, j) / this.R[k][k]);
			}
		}

		return x;
	}

	@Override
	public Vector solve(Vector b) {
		return solve(b, JeometryFactory.createVector(b));
	}

	@Override
	public Vector solve(Vector b, Vector x) {
		if (b == null) {
			throw new IllegalArgumentException("Null constant vector.");
		}
		
		if (b.getDimension() != this.n) {
			throw new IllegalArgumentException("Invalid constant matrix rows count "+b.getDimension()+", expected "+this.n);
		}
		
		if (x == null) {
			throw new IllegalArgumentException("Null result vector.");
		}
		
		if (x.getDimension() != this.n) {
			throw new IllegalArgumentException("Invalid constant matrix rows count "+b.getDimension()+", expected "+this.n);
		}
		
		if (!this.isspd) {
			throw new RuntimeException("Cholesky cannot resolve from non symmetric positive definite matrix.");
		}

		x.setValues(b);
		
		// Solve L*Y = B;
		for (int k = 0; k < this.n; k++) {
				for (int i = 0; i < k ; i++) {
					x.setValue(k,  x.getValue(k) - x.getValue(i)*this.R[k][i]);
				}
				x.setValue(k,  x.getValue(k) / this.R[k][k]);
		}

		// Solve L'*X = Y;
		for (int k = this.n-1; k >= 0; k--) {
				for (int i = k+1; i < this.n ; i++) {
					x.setValue(k,  x.getValue(k) - x.getValue(i)*this.R[i][k]);
				}
				x.setValue(k, x.getValue(k) / this.R[k][k]);
		}

		//return new Matrix(X,n,nx);
		return x;
	}

	/** Check if the input matrix symmetric and positive definite.
	  * @return <code>true</code> if the input matrix is symmetric and positive definite and <code>false</code> otherwise
	  */
	public boolean isSPD () {
		return this.isspd;
	}

}
