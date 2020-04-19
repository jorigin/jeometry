package org.jeometry.simple.math.decomposition;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.decomposition.SVDDecomposition;

/**
 * A simple implementation of {@link SVDDecomposition SVDDecomposition}.<br><br>
 * This implantation is inspired by <a href="https://math.nist.gov/javanumerics/jama/">Jama</a> <a href="https://math.nist.gov/javanumerics/jama/doc/">LU Decomposition</a>.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class SimpleSVDDecomposition implements SVDDecomposition {

	/** 
	 * The U matrix;
	 */
	private Matrix U;

	/**
	 * The D matrix.
	 */
	private Matrix S;

	/**
	 * The V matrix.
	 */
	private Matrix V;
	
	/** 
	 * Array for internal storage of singular values.
	 */
	private double[] s;

	/**
	 * The row dimension.
	 */
	private int inputRows;

	/**
	 * The column dimension.
	 */
	private int inputColumns;

	/** 
	 * Construct the singular value decomposition Structure to access U, S and V.
	 * @param matrix the matrix to decompose
	 */
	public SimpleSVDDecomposition (Matrix matrix) {

		// Derived from LINPACK code.
		// Initialize.
		double[][] A = matrix.getDataArray2D();
		inputRows = matrix.getRowsCount();
		inputColumns = matrix.getColumnsCount();

		int nu = Math.min(inputRows,inputColumns);

		s = new double [Math.min(inputRows+1,inputColumns)];

		U = JeometryFactory.createMatrix(inputRows, nu);
		V = JeometryFactory.createMatrix(inputColumns, inputColumns);

		double[] e = new double [inputColumns];
		double[] work = new double [inputRows];
		
		boolean wantu = true;
		boolean wantv = true;

		// Reduce A to bidiagonal form, storing the diagonal elements
		// in s and the super-diagonal elements in e.

		int nct = Math.min(inputRows-1,inputColumns);
		int nrt = Math.max(0,Math.min(inputColumns-2,inputRows));
		for (int k = 0; k < Math.max(nct,nrt); k++) {
			if (k < nct) {

				// Compute the transformation for the k-th column and
				// place the k-th diagonal in s[k].
				// Compute 2-norm of k-th column without under/overflow.
				s[k] = 0;
				for (int i = k; i < inputRows; i++) {
					s[k] = hypot(s[k],A[i][k]);
				}
				if (s[k] != 0.0) {
					if (A[k][k] < 0.0) {
						s[k] = -s[k];
					}
					for (int i = k; i < inputRows; i++) {
						A[i][k] /= s[k];
					}
					A[k][k] += 1.0;
				}
				s[k] = -s[k];
			}
			for (int j = k+1; j < inputColumns; j++) {
				if ((k < nct) & (s[k] != 0.0))  {

					// Apply the transformation.

					double t = 0;
					for (int i = k; i < inputRows; i++) {
						t += A[i][k]*A[i][j];
					}
					t = -t/A[k][k];
					for (int i = k; i < inputRows; i++) {
						A[i][j] += t*A[i][k];
					}
				}

				// Place the k-th row of A into e for the
				// subsequent calculation of the row transformation.

				e[j] = A[k][j];
			}
			if (wantu & (k < nct)) {

				// Place the transformation in U for subsequent back
				// multiplication.

				for (int i = k; i < inputRows; i++) {
					U.setValue(i, k, A[i][k]);
				}
			}
			if (k < nrt) {

				// Compute the k-th row transformation and place the
				// k-th super-diagonal in e[k].
				// Compute 2-norm without under/overflow.
				e[k] = 0;
				for (int i = k+1; i < inputColumns; i++) {
					e[k] = hypot(e[k],e[i]);
				}
				if (e[k] != 0.0) {
					if (e[k+1] < 0.0) {
						e[k] = -e[k];
					}
					for (int i = k+1; i < inputColumns; i++) {
						e[i] /= e[k];
					}
					e[k+1] += 1.0;
				}
				e[k] = -e[k];
				if ((k+1 < inputRows) & (e[k] != 0.0)) {

					// Apply the transformation.

					for (int i = k+1; i < inputRows; i++) {
						work[i] = 0.0;
					}
					for (int j = k+1; j < inputColumns; j++) {
						for (int i = k+1; i < inputRows; i++) {
							work[i] += e[j]*A[i][j];
						}
					}
					for (int j = k+1; j < inputColumns; j++) {
						double t = -e[j]/e[k+1];
						for (int i = k+1; i < inputRows; i++) {
							A[i][j] += t*work[i];
						}
					}
				}
				if (wantv) {

					// Place the transformation in V for subsequent
					// back multiplication.

					for (int i = k+1; i < inputColumns; i++) {
						V.setValue(i, k, e[i]);
					}
				}
			}
		}

		// Set up the final bidiagonal matrix or order p.

		int p = Math.min(inputColumns,inputRows+1);
		if (nct < inputColumns) {
			s[nct] = A[nct][nct];
		}
		if (inputRows < p) {
			s[p-1] = 0.0;
		}
		if (nrt+1 < p) {
			e[nrt] = A[nrt][p-1];
		}
		e[p-1] = 0.0;

		// If required, generate U.

		if (wantu) {
			for (int j = nct; j < nu; j++) {
				for (int i = 0; i < inputRows; i++) {
					U.setValue(i, j, 0.0);
				}
				U.setValue(j, j, 1.0);
			}
			for (int k = nct-1; k >= 0; k--) {
				if (s[k] != 0.0) {
					for (int j = k+1; j < nu; j++) {
						double t = 0;
						for (int i = k; i < inputRows; i++) {
							t += U.getValue(i, k)*U.getValue(i, j);
						}
						t = -t/U.getValue(k, k);
						for (int i = k; i < inputRows; i++) {
							U.setValue(i, j, U.getValue(i, j) + t*U.getValue(i, k));
						}
					}
					for (int i = k; i < inputRows; i++ ) {
						U.setValue(i, k, -U.getValue(i, k));
					}

					U.setValue(k, k, 1.0 + U.getValue(k, k));
					for (int i = 0; i < k-1; i++) {
						U.setValue(i, k, 0.0);
					}
				} else {
					for (int i = 0; i < inputRows; i++) {
						U.setValue(i, k, 0.0);
					}
					U.setValue(k, k, 1.0);
				}
			}
		}

		// If required, generate V.

		if (wantv) {
			for (int k = inputColumns-1; k >= 0; k--) {
				if ((k < nrt) & (e[k] != 0.0)) {
					for (int j = k+1; j < nu; j++) {
						double t = 0;
						for (int i = k+1; i < inputColumns; i++) {
							t += V.getValue(i, k)*V.getValue(i, j);
						}
						t = -t/V.getValue(k+1, k);
						for (int i = k+1; i < inputColumns; i++) {
							V.setValue(i, j, V.getValue(i, j) + t*V.getValue(i, k));
						}
					}
				}
				for (int i = 0; i < inputColumns; i++) {
					V.setValue(i, k, 0.0);
				}
				V.setValue(k, k, 1.0);
			}
		}

		// Main iteration loop for the singular values.

		int pp = p-1;
		int iter = 0;
		double eps = Math.pow(2.0,-52.0);
		double tiny = Math.pow(2.0,-966.0);
		while (p > 0) {
			int k,kase;

			// Here is where a test for too many iterations would go.

			// This section of the program inspects for
			// negligible elements in the s and e arrays.  On
			// completion the variables kase and k are set as follows.

			// kase = 1     if s(p) and e[k-1] are negligible and k<p
			// kase = 2     if s(k) is negligible and k<p
			// kase = 3     if e[k-1] is negligible, k<p, and
			//              s(k), ..., s(p) are not negligible (qr step).
			// kase = 4     if e(p-1) is negligible (convergence).

			for (k = p-2; k >= -1; k--) {
				if (k == -1) {
					break;
				}
				if (Math.abs(e[k]) <=
						tiny + eps*(Math.abs(s[k]) + Math.abs(s[k+1]))) {
					e[k] = 0.0;
					break;
				}
			}
			if (k == p-2) {
				kase = 4;
			} else {
				int ks;
				for (ks = p-1; ks >= k; ks--) {
					if (ks == k) {
						break;
					}
					double t = (ks != p ? Math.abs(e[ks]) : 0.) + 
							(ks != k+1 ? Math.abs(e[ks-1]) : 0.);
					if (Math.abs(s[ks]) <= tiny + eps*t)  {
						s[ks] = 0.0;
						break;
					}
				}
				if (ks == k) {
					kase = 3;
				} else if (ks == p-1) {
					kase = 1;
				} else {
					kase = 2;
					k = ks;
				}
			}
			k++;

			// Perform the task indicated by kase.

			switch (kase) {

			// Deflate negligible s(p).

			case 1: {
				double f = e[p-2];
				e[p-2] = 0.0;
				for (int j = p-2; j >= k; j--) {
					double t = hypot(s[j],f);
					double cs = s[j]/t;
					double sn = f/t;
					s[j] = t;
					if (j != k) {
						f = -sn*e[j-1];
						e[j-1] = cs*e[j-1];
					}
					if (wantv) {
						for (int i = 0; i < inputColumns; i++) {
							t = cs*V.getValue(i, j) + sn*V.getValue(i, p-1);
							V.setValue(i, p-1, -sn*V.getValue(i, j) + cs*V.getValue(i, p-1));
							V.setValue(i, j, t);
						}
					}
				}
			}
			break;

			// Split at negligible s(k).

			case 2: {
				double f = e[k-1];
				e[k-1] = 0.0;
				for (int j = k; j < p; j++) {
					double t = hypot(s[j],f);
					double cs = s[j]/t;
					double sn = f/t;
					s[j] = t;
					f = -sn*e[j];
					e[j] = cs*e[j];
					if (wantu) {
						for (int i = 0; i < inputRows; i++) {
							t = cs*U.getValue(i, j) + sn*U.getValue(i, k-1);
							U.setValue(i, k-1, -sn*U.getValue(i, j) + cs*U.getValue(i, k-1));
							U.setValue(i, j, t);
						}
					}
				}
			}
			break;

			// Perform one qr step.

			case 3: {

				// Calculate the shift.

				double scale = Math.max(Math.max(Math.max(Math.max(
						Math.abs(s[p-1]),Math.abs(s[p-2])),Math.abs(e[p-2])), 
						Math.abs(s[k])),Math.abs(e[k]));
				double sp = s[p-1]/scale;
				double spm1 = s[p-2]/scale;
				double epm1 = e[p-2]/scale;
				double sk = s[k]/scale;
				double ek = e[k]/scale;
				double b = ((spm1 + sp)*(spm1 - sp) + epm1*epm1)/2.0;
				double c = (sp*epm1)*(sp*epm1);
				double shift = 0.0;
				if ((b != 0.0) | (c != 0.0)) {
					shift = Math.sqrt(b*b + c);
					if (b < 0.0) {
						shift = -shift;
					}
					shift = c/(b + shift);
				}
				double f = (sk + sp)*(sk - sp) + shift;
				double g = sk*ek;

				// Chase zeros.

				for (int j = k; j < p-1; j++) {
					double t = hypot(f,g);
					double cs = f/t;
					double sn = g/t;
					if (j != k) {
						e[j-1] = t;
					}
					f = cs*s[j] + sn*e[j];
					e[j] = cs*e[j] - sn*s[j];
					g = sn*s[j+1];
					s[j+1] = cs*s[j+1];
					if (wantv) {
						for (int i = 0; i < inputColumns; i++) {
							t = cs*V.getValue(i, j) + sn*V.getValue(i, j+1);
							V.setValue(i, j+1,  -sn*V.getValue(i, j) + cs*V.getValue(i, j+1));
							V.setValue(i, j, t);
						}
					}
					t = hypot(f,g);
					cs = f/t;
					sn = g/t;
					s[j] = t;
					f = cs*e[j] + sn*s[j+1];
					s[j+1] = -sn*e[j] + cs*s[j+1];
					g = sn*e[j+1];
					e[j+1] = cs*e[j+1];
					if (wantu && (j < inputRows-1)) {
						for (int i = 0; i < inputRows; i++) {
							t = cs*U.getValue(i, j) + sn*U.getValue(i, j+1);
							U.setValue(i, j+1, -sn*U.getValue(i, j) + cs*U.getValue(i, j+1));
							U.setValue(i, j, t);
						}
					}
				}
				e[p-2] = f;
				iter = iter + 1;
			}
			break;

			// Convergence.

			case 4: {

				// Make the singular values positive.

				if (s[k] <= 0.0) {
					s[k] = (s[k] < 0.0 ? -s[k] : 0.0);
					if (wantv) {
						for (int i = 0; i <= pp; i++) {
							V.setValue(i, k, -V.getValue(i, k));
						}
					}
				}

				// Order the singular values.

				while (k < pp) {
					if (s[k] >= s[k+1]) {
						break;
					}
					double t = s[k];
					s[k] = s[k+1];
					s[k+1] = t;
					if (wantv && (k < inputColumns-1)) {
						for (int i = 0; i < inputColumns; i++) {
							t = V.getValue(i, k+1); 
							V.setValue(i, k+1, V.getValue(i, k)); 
							V.setValue(i, k, t);
						}
					}

					if (wantu && (k < inputRows-1)) {
						for (int i = 0; i < inputRows; i++) {
							t = U.getValue(i, k+1); 
							U.setValue(i, k+1, U.getValue(i, k)); 
							U.setValue(i, k, t);
						}
					}
					k++;
				}
				iter = 0;
				p--;
			}
			break;
			}
		}

		S = JeometryFactory.createMatrix(inputColumns,inputColumns);
		for (int i = 0; i < inputColumns; i++) {
			for (int j = 0; j < inputColumns; j++) {
				S.setValue(i, j, 0.0);
			}
			S.setValue(i, i, s[i]);

		}
	}

	/** Two norm
	 * @return max(S)
	 */
	public double norm2 () {
		return s[0];
	}

	/** Two norm condition number
	 * @return max(S)/min(S)
	 */
	public double cond() {
		return s[0]/s[Math.min(inputRows,inputColumns)-1];
	}

	/** Effective numerical matrix rank
	 * @return The number of non eligible singular values
	 */
	public int rank () {
		double eps = Math.pow(2.0,-52.0);
		double tol = Math.max(inputRows,inputColumns)*s[0]*eps;
		int r = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] > tol) {
				r++;
			}
		}
		return r;
	}

	@Override
	public List<Matrix> getComponents() {
		List<Matrix> components = new ArrayList<Matrix>(3);
		
		components.add(U);
		components.add(S);
		components.add(V);
		
		return components;
	}

	@Override
	public Matrix getU() {
		return U;
	}

	@Override
	public Matrix getS() {
		return S;
	}

	@Override
	public Matrix getV() {
		return V;
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
