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
		n = matrix.getRowsCount();
		R = new double[n][n];
		isspd = (matrix.getColumnsCount() == n);
		// Main loop.
		for (int j = 0; j < n; j++) {
			double[] Lrowj = R[j];
			double d = 0.0;
			for (int k = 0; k < j; k++) {
				double[] Lrowk = R[k];
				double s = 0.0;
				for (int i = 0; i < k; i++) {
					s += Lrowk[i]*Lrowj[i];
				}
				Lrowj[k] = s = (matrix.getValue(j, k) - s)/R[k][k];
				d = d + s*s;
				isspd = isspd & (matrix.getValue(k, j) == matrix.getValue(j, k)); 
			}
			d = matrix.getValue(j, j) - d;
			isspd = isspd & (d > 0.0);
			R[j][j] = Math.sqrt(Math.max(d,0.0));
			for (int k = j+1; k < n; k++) {
				R[j][k] = 0.0;
			}
		}
		
		u = JeometryFactory.createMatrix(R);
	}


	@Override
	public List<Matrix> getComponents() {
		List<Matrix> list = new ArrayList<Matrix>(1);
		list.add(u);
		return list;
	}

	@Override
	public Matrix getR() {
		return u;
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
		
		if (b.getRowsCount() != n) {
			throw new IllegalArgumentException("Invalid constant matrix rows count "+b.getRowsCount()+", expected "+n);
		}
		
		if (x == null) {
			throw new IllegalArgumentException("Null result vector.");
		}
		
		if (x.getRowsCount() != b.getRowsCount()) {
			throw new IllegalArgumentException("Invalid constant matrix rows count "+b.getRowsCount()+", expected "+n);
		}
		
		if (!isspd) {
			throw new RuntimeException("Cholesky cannot resolve from non symmetric positive definite matrix.");
		}

		// Copy right hand side.
		int nx = b.getColumnsCount();

		x.setValues(b);
		
		// Solve L*Y = B;
		for (int k = 0; k < n; k++) {
			for (int j = 0; j < nx; j++) {
				for (int i = 0; i < k ; i++) {
					x.setValue(k, j,  x.getValue(k, j) - x.getValue(i, j)*R[k][i]);
				}
				x.setValue(k, j,  x.getValue(k,  j) / R[k][k]);
			}
		}

		// Solve L'*X = Y;
		for (int k = n-1; k >= 0; k--) {
			for (int j = 0; j < nx; j++) {
				for (int i = k+1; i < n ; i++) {
					x.setValue(k, j,  x.getValue(k, j) - x.getValue(i, j)*R[i][k]);
				}
				x.setValue(k, j, x.getValue(k, j) / R[k][k]);
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
		
		if (b.getDimension() != n) {
			throw new IllegalArgumentException("Invalid constant matrix rows count "+b.getDimension()+", expected "+n);
		}
		
		if (x == null) {
			throw new IllegalArgumentException("Null result vector.");
		}
		
		if (x.getDimension() != n) {
			throw new IllegalArgumentException("Invalid constant matrix rows count "+b.getDimension()+", expected "+n);
		}
		
		if (!isspd) {
			throw new RuntimeException("Cholesky cannot resolve from non symmetric positive definite matrix.");
		}

		x.setValues(b);
		
		// Solve L*Y = B;
		for (int k = 0; k < n; k++) {
				for (int i = 0; i < k ; i++) {
					x.setValue(k,  x.getValue(k) - x.getValue(i)*R[k][i]);
				}
				x.setValue(k,  x.getValue(k) / R[k][k]);
		}

		// Solve L'*X = Y;
		for (int k = n-1; k >= 0; k--) {
				for (int i = k+1; i < n ; i++) {
					x.setValue(k,  x.getValue(k) - x.getValue(i)*R[i][k]);
				}
				x.setValue(k, x.getValue(k) / R[k][k]);
		}

		//return new Matrix(X,n,nx);
		return x;
	}

	/** Check if the input matrix symmetric and positive definite.
	  * @return <code>true</code> if the input matrix is symmetric and positive definite and <code>false</code> otherwise
	  */
	public boolean isSPD () {
		return isspd;
	}

}
