package org.jeometry.simple.math.decomposition;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.decomposition.EigenDecomposition;

/**
 * A simple implementation of {@link EigenDecomposition Eigen decomposition}.<br><br>
 * This implantation is inspired by <a href="https://math.nist.gov/javanumerics/jama/">Jama</a> <a href="https://math.nist.gov/javanumerics/jama/doc/">Eigen Decomposition</a>.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class SimpleEigenDecomposition implements EigenDecomposition {

	/** Row and column dimension (square matrix).
	 * @serial matrix dimension.
	 */
	private int n;

	/** Symmetry flag.
	 * @serial internal symmetry flag.
	 */
	private boolean issymmetric;

	/** 
	 * Arrays for internal storage of eigenvalues.
	 */
	private double[] d;

	/** 
	 * Arrays for internal storage of eigenvalues.
	 */
	private double[] e;

	/** 
	 * Array for internal storage of eigenvectors.
	 */
	private Matrix V = null;

	/** 
	 * Array for internal storage of eigenvectors.
	 */
	private Matrix D = null;

	/** 
	 * Array for internal storage of nonsymmetric Hessenberg form.
	 * @serial internal storage of nonsymmetric Hessenberg form.
	 */
	private Matrix H;

	/** 
	 * Working storage for nonsymmetric algorithm.
	 * @serial working storage for nonsymmetric algorithm.
	 */
	private double[] ort;

	/**
	 * Temporary variable.
	 */
	private transient double cdivr;

	/**
	 * Temporary variable.
	 */
	private transient double cdivi;

	/**
	 * Construct a new {@link EigenDecomposition Eigen decomposition} from the given {@link Matrix matrix}
	 * @param matrix the matrix to decompose
	 */
	public SimpleEigenDecomposition(Matrix matrix){
		double[][] A = matrix.getDataArray2D();

		n = matrix.getColumnsCount();

		V = JeometryFactory.createMatrix(n, n);

		d = new double[n];
		e = new double[n];

		issymmetric = true;
		for (int j = 0; (j < n) & issymmetric; j++) {
			for (int i = 0; (i < n) & issymmetric; i++) {
				issymmetric = (A[i][j] == A[j][i]);
			}
		}

		if (issymmetric) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					V.setValue(i, j, A[i][j]);
				}
			}

			// Tridiagonalize.
			tred2();

			// Diagonalize.
			tql2();

		} else {
			H = JeometryFactory.createMatrix(n, n);
			ort = new double[n];

			for (int j = 0; j < n; j++) {
				for (int i = 0; i < n; i++) {
					H.setValue(i, j, A[i][j]);
				}
			}

			// Reduce to Hessenberg form.
			orthes();

			// Reduce Hessenberg to real Schur form.
			hqr2();
		}

		// Compute D matrix
		D = JeometryFactory.createMatrix(n, n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				D.setValue(i, j, 0.0);
			}
			D.setValue(i, i, d[i]);
			if (e[i] > 0) {
				D.setValue(i, i+1, e[i]);
			} else if (e[i] < 0) {
				D.setValue(i, i-1, e[i]);
			}
		}
	}

	@Override
	public Matrix getD() {
		return D;
	}

	@Override
	public Matrix getV() {
		return V;
	}

	@Override
	public List<Matrix> getComponents() {
		List<Matrix> matrices = new ArrayList<Matrix>(2);
		matrices.add(V);
		matrices.add(D);
		return matrices;
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

	/**
	 * Compute the complex scalar divisio <i>x</i>/<i>y</i>.
	 * @param xr real part of x
	 * @param xi imaginary part of x
	 * @param yr real part of y
	 * @param yi imaginary part of y
	 */
	private void cdiv(double xr, double xi, double yr, double yi) {
		double r,d;
		if (Math.abs(yr) > Math.abs(yi)) {
			r = yi/yr;
			d = yr + r*yi;
			cdivr = (xr + r*xi)/d;
			cdivi = (xi - r*xr)/d;
		} else {
			r = yr/yi;
			d = yi + r*yr;
			cdivr = (r*xr + xi)/d;
			cdivi = (r*xi - xr)/d;
		}
	}

	/**
	 * This is derived from the Algol procedures tred2 by
	 * Bowdler, Martin, Reinsch, and Wilkinson, Handbook for
	 * Auto. Comp., Vol.ii-Linear Algebra, and the corresponding
	 * Fortran subroutine in EISPACK.
	 */
	private void tred2 () {

		for (int j = 0; j < n; j++) {
			d[j] = V.getValue(n-1, j);
		}

		// Householder reduction to tridiagonal form.

		for (int i = n-1; i > 0; i--) {

			// Scale to avoid under/overflow.

			double scale = 0.0;
			double h = 0.0;
			for (int k = 0; k < i; k++) {
				scale = scale + Math.abs(d[k]);
			}
			if (scale == 0.0) {
				e[i] = d[i-1];
				for (int j = 0; j < i; j++) {
					d[j] = V.getValue(i-1, j);
					V.setValue(i, j, 0.0);
					V.setValue(j, i, 0.0);
				}
			} else {

				// Generate Householder vector.

				for (int k = 0; k < i; k++) {
					d[k] /= scale;
					h += d[k] * d[k];
				}
				double f = d[i-1];
				double g = Math.sqrt(h);
				if (f > 0) {
					g = -g;
				}
				e[i] = scale * g;
				h = h - f * g;
				d[i-1] = f - g;
				for (int j = 0; j < i; j++) {
					e[j] = 0.0;
				}

				// Apply similarity transformation to remaining columns.

				for (int j = 0; j < i; j++) {
					f = d[j];
					V.setValue(j, i, f);
					g = e[j] + V.getValue(j, j) * f;
					for (int k = j+1; k <= i-1; k++) {
						g += V.getValue(k, j) * d[k];
						e[k] += V.getValue(k, j) * f;
					}
					e[j] = g;
				}
				f = 0.0;
				for (int j = 0; j < i; j++) {
					e[j] /= h;
					f += e[j] * d[j];
				}
				double hh = f / (h + h);
				for (int j = 0; j < i; j++) {
					e[j] -= hh * d[j];
				}
				for (int j = 0; j < i; j++) {
					f = d[j];
					g = e[j];
					for (int k = j; k <= i-1; k++) {
						V.setValue(k, j, V.getValue(k, j) - (f * e[k] + g * d[k]));
					}
					d[j] = V.getValue(i-1, j);
					V.setValue(i, j, 0.0);
				}
			}
			d[i] = h;
		}

		// Accumulate transformations.

		for (int i = 0; i < n-1; i++) {
			V.setValue(n-1, i, V.getValue(i, i));
			V.setValue(i, i, 1.0);
			double h = d[i+1];
			if (h != 0.0) {
				for (int k = 0; k <= i; k++) {
					d[k] = V.getValue(k, i+1) / h;
				}
				for (int j = 0; j <= i; j++) {
					double g = 0.0;
					for (int k = 0; k <= i; k++) {
						g += V.getValue(k, i+1) * V.getValue(k, j);
					}
					for (int k = 0; k <= i; k++) {
						V.setValue(k, j,  V.getValue(k, j) - g * d[k]);
					}
				}
			}
			for (int k = 0; k <= i; k++) {
				V.setValue(k, i+1, 0.0);
			}
		}
		for (int j = 0; j < n; j++) {
			d[j] = V.getValue(n-1, j);
			V.setValue(n-1, j, 0.0);
		}
		V.setValue(n-1, n-1, 1.0);
		e[0] = 0.0;
	} 

	/**
	 * Symmetric tridiagonal QL algorithm.
	 */
	private void tql2 () {

		//  This is derived from the Algol procedures tql2, by
		//  Bowdler, Martin, Reinsch, and Wilkinson, Handbook for
		//  Auto. Comp., Vol.ii-Linear Algebra, and the corresponding
		//  Fortran subroutine in EISPACK.

		for (int i = 1; i < n; i++) {
			e[i-1] = e[i];
		}
		e[n-1] = 0.0;

		double f = 0.0;
		double tst1 = 0.0;
		double eps = Math.pow(2.0,-52.0);
		for (int l = 0; l < n; l++) {

			// Find small subdiagonal element

			tst1 = Math.max(tst1,Math.abs(d[l]) + Math.abs(e[l]));
			int m = l;
			while (m < n) {
				if (Math.abs(e[m]) <= eps*tst1) {
					break;
				}
				m++;
			}

			// If m == l, d[l] is an eigenvalue,
			// otherwise, iterate.

			if (m > l) {
				int iter = 0;
				do {
					iter = iter + 1;  // (Could check iteration count here.)

					// Compute implicit shift

					double g = d[l];
					double p = (d[l+1] - g) / (2.0 * e[l]);
					double r = hypot(p,1.0);
					if (p < 0) {
						r = -r;
					}
					d[l] = e[l] / (p + r);
					d[l+1] = e[l] * (p + r);
					double dl1 = d[l+1];
					double h = g - d[l];
					for (int i = l+2; i < n; i++) {
						d[i] -= h;
					}
					f = f + h;

					// Implicit QL transformation.

					p = d[m];
					double c = 1.0;
					double c2 = c;
					double c3 = c;
					double el1 = e[l+1];
					double s = 0.0;
					double s2 = 0.0;
					for (int i = m-1; i >= l; i--) {
						c3 = c2;
						c2 = c;
						s2 = s;
						g = c * e[i];
						h = c * p;
						r = hypot(p,e[i]);
						e[i+1] = s * r;
						s = e[i] / r;
						c = p / r;
						p = c * d[i] - s * g;
						d[i+1] = h + s * (c * g + s * d[i]);

						// Accumulate transformation.

						for (int k = 0; k < n; k++) {
							h = V.getValue(k, i+1);
							V.setValue(k, i+1, s * V.getValue(k, i) + c * h);
							V.setValue(k, i, c * V.getValue(k, i) - s * h);
						}
					}
					p = -s * s2 * c3 * el1 * e[l] / dl1;
					e[l] = s * p;
					d[l] = c * p;

					// Check for convergence.

				} while (Math.abs(e[l]) > eps*tst1);
			}
			d[l] = d[l] + f;
			e[l] = 0.0;
		}

		// Sort eigenvalues and corresponding vectors.

		for (int i = 0; i < n-1; i++) {
			int k = i;
			double p = d[i];
			for (int j = i+1; j < n; j++) {
				if (d[j] < p) {
					k = j;
					p = d[j];
				}
			}
			if (k != i) {
				d[k] = d[i];
				d[i] = p;
				for (int j = 0; j < n; j++) {
					p = V.getValue(j, i);
					V.setValue(j, i, V.getValue(j, k));
					V.setValue(j, k, p);
				}
			}
		}
	}

	/**
	 * Nonsymmetric reduction to Hessenberg form.
	 */
	private void orthes () {

		//  This is derived from the Algol procedures orthes and ortran,
		//  by Martin and Wilkinson, Handbook for Auto. Comp.,
		//  Vol.ii-Linear Algebra, and the corresponding
		//  Fortran subroutines in EISPACK.

		int low = 0;
		int high = n-1;

		for (int m = low+1; m <= high-1; m++) {

			// Scale column.

			double scale = 0.0;
			for (int i = m; i <= high; i++) {
				scale = scale + Math.abs(H.getValue(i, m-1));
			}
			if (scale != 0.0) {

				// Compute Householder transformation.

				double h = 0.0;
				for (int i = high; i >= m; i--) {
					ort[i] = H.getValue(i, m-1)/scale;
					h += ort[i] * ort[i];
				}
				double g = Math.sqrt(h);
				if (ort[m] > 0) {
					g = -g;
				}
				h = h - ort[m] * g;
				ort[m] = ort[m] - g;

				// Apply Householder similarity transformation
				// H = (I-u*u'/h)*H*(I-u*u')/h)

				for (int j = m; j < n; j++) {
					double f = 0.0;
					for (int i = high; i >= m; i--) {
						f += ort[i]*H.getValue(i, j);
					}
					f = f/h;
					for (int i = m; i <= high; i++) {
						H.setValue(i, j,  H.getValue(i, j) - f*ort[i]);
					}
				}

				for (int i = 0; i <= high; i++) {
					double f = 0.0;
					for (int j = high; j >= m; j--) {
						f += ort[j]*H.getValue(i, j);
					}
					f = f/h;
					for (int j = m; j <= high; j++) {
						H.setValue(i, j,  H.getValue(i, j) - f*ort[j]);
					}
				}
				ort[m] = scale*ort[m];

				H.setValue(m, m-1, scale*g);
			}
		}

		// Accumulate transformations (Algol's ortran).

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				V.setValue(i, j, (i == j ? 1.0 : 0.0));
			}
		}

		for (int m = high-1; m >= low+1; m--) {
			if (H.getValue(m, m-1) != 0.0) {
				for (int i = m+1; i <= high; i++) {
					ort[i] = H.getValue(i, m-1);
				}
				for (int j = m; j <= high; j++) {
					double g = 0.0;
					for (int i = m; i <= high; i++) {
						g += ort[i] * V.getValue(i, j);
					}
					// Double division avoids possible underflow
					g = (g / ort[m]) / H.getValue(m, m-1);
					for (int i = m; i <= high; i++) {
						V.setValue(i, j,  V.getValue(i, j) + g * ort[i]);
					}
				}
			}
		}
	}

	/**
	 * Nonsymmetric reduction from Hessenberg to real Schur form.
	 */
	private void hqr2 () {

		//  This is derived from the Algol procedure hqr2,
		//  by Martin and Wilkinson, Handbook for Auto. Comp.,
		//  Vol.ii-Linear Algebra, and the corresponding
		//  Fortran subroutine in EISPACK.

		// Initialize

		int nn = this.n;
		int n = nn-1;
		int low = 0;
		int high = nn-1;
		double eps = Math.pow(2.0,-52.0);
		double exshift = 0.0;
		double p=0,q=0,r=0,s=0,z=0,t,w,x,y;

		// Store roots isolated by balanc and compute matrix norm

		double norm = 0.0;
		for (int i = 0; i < nn; i++) {
			if (i < low | i > high) {
				d[i] = H.getValue(i, i);
				e[i] = 0.0;
			}
			for (int j = Math.max(i-1,0); j < nn; j++) {
				norm = norm + Math.abs(H.getValue(i, j));
			}
		}

		// Outer loop over eigenvalue index

		int iter = 0;
		while (n >= low) {

			// Look for single small sub-diagonal element

			int l = n;
			while (l > low) {
				s = Math.abs(H.getValue(l-1, l-1)) + Math.abs(H.getValue(l, l));
				if (s == 0.0) {
					s = norm;
				}
				if (Math.abs(H.getValue(l, l-1)) < eps * s) {
					break;
				}
				l--;
			}

			// Check for convergence
			// One root found

			if (l == n) {
				H.setValue(n, n, H.getValue(n, n) + exshift);
				d[n] = H.getValue(n, n);
				e[n] = 0.0;
				n--;
				iter = 0;

				// Two roots found

			} else if (l == n-1) {
				w = H.getValue(n, n-1) * H.getValue(n-1, n);
				p = (H.getValue(n-1, n-1) - H.getValue(n, n)) / 2.0;
				q = p * p + w;
				z = Math.sqrt(Math.abs(q));
				H.setValue(n, n, H.getValue(n, n) + exshift);
				H.setValue(n-1, n-1, H.getValue(n-1, n-1) + exshift);
				x = H.getValue(n, n);

				// Real pair

				if (q >= 0) {
					if (p >= 0) {
						z = p + z;
					} else {
						z = p - z;
					}
					d[n-1] = x + z;
					d[n] = d[n-1];
					if (z != 0.0) {
						d[n] = x - w / z;
					}
					e[n-1] = 0.0;
					e[n] = 0.0;
					x = H.getValue(n, n-1);
					s = Math.abs(x) + Math.abs(z);
					p = x / s;
					q = z / s;
					r = Math.sqrt(p * p+q * q);
					p = p / r;
					q = q / r;

					// Row modification  
					for (int j = n-1; j < nn; j++) {
						z = H.getValue(n-1, j);
						H.setValue(n-1, j, q * z + p * H.getValue(n, j));
						H.setValue(n, j, q * H.getValue(n, j) - p * z);
					}

					// Column modification

					for (int i = 0; i <= n; i++) {
						z = H.getValue(i, n-1);
						H.setValue(i, n-1, q * z + p * H.getValue(i, n));
						H.setValue(i, n, q * H.getValue(i, n) - p * z);
					}

					// Accumulate transformations

					for (int i = low; i <= high; i++) {
						z = V.getValue(i, n-1);
						V.setValue(i, n-1, q * z + p * V.getValue(i, n));
						V.setValue(i, n, q * V.getValue(i, n) - p * z);
					}

					// Complex pair

				} else {
					d[n-1] = x + p;
					d[n] = x + p;
					e[n-1] = z;
					e[n] = -z;
				}
				n = n - 2;
				iter = 0;

				// No convergence yet

			} else {

				// Form shift

				x = H.getValue(n, n);
				y = 0.0;
				w = 0.0;
				if (l < n) {
					y = H.getValue(n-1, n-1);
					w = H.getValue(n, n-1) * H.getValue(n-1, n);
				}

				// Wilkinson's original ad hoc shift

				if (iter == 10) {
					exshift += x;
					for (int i = low; i <= n; i++) {
						H.setValue(i, i, H.getValue(i, i)- x);
					}
					s = Math.abs(H.getValue(n, n-1)) + Math.abs(H.getValue(n-1, n-2));
					x = y = 0.75 * s;
					w = -0.4375 * s * s;
				}

				// MATLAB's new ad hoc shift

				if (iter == 30) {
					s = (y - x) / 2.0;
					s = s * s + w;
					if (s > 0) {
						s = Math.sqrt(s);
						if (y < x) {
							s = -s;
						}
						s = x - w / ((y - x) / 2.0 + s);
						for (int i = low; i <= n; i++) {
							H.setValue(i, i, H.getValue(i, i) - s);
						}
						exshift += s;
						x = y = w = 0.964;
					}
				}

				iter = iter + 1;   // (Could check iteration count here.)

				// Look for two consecutive small sub-diagonal elements

				int m = n-2;
				while (m >= l) {
					z = H.getValue(m, m);
					r = x - z;
					s = y - z;
					p = (r * s - w) / H.getValue(m+1, m) + H.getValue(m, m+1);
					q = H.getValue(m+1, m+1) - z - r - s;
					r = H.getValue(m+2, m+1);
					s = Math.abs(p) + Math.abs(q) + Math.abs(r);
					p = p / s;
					q = q / s;
					r = r / s;
					if (m == l) {
						break;
					}
					if (Math.abs(H.getValue(m, m-1)) * (Math.abs(q) + Math.abs(r)) <
							eps * (Math.abs(p) * (Math.abs(H.getValue(m-1, m-1)) + Math.abs(z) +
									Math.abs(H.getValue(m+1, m+1))))) {
						break;
					}
					m--;
				}

				for (int i = m+2; i <= n; i++) {
					H.setValue(i, i-2, 0.0);
					if (i > m+2) {
						H.setValue(i, i-3, 0.0);
					}
				}

				// Double QR step involving rows l:n and columns m:n


				for (int k = m; k <= n-1; k++) {
					boolean notlast = (k != n-1);
					if (k != m) {
						p = H.getValue(k, k-1);
						q = H.getValue(k+1, k-1);
						r = (notlast ? H.getValue(k+2, k-1) : 0.0);
						x = Math.abs(p) + Math.abs(q) + Math.abs(r);
						if (x == 0.0) {
							continue;
						}
						p = p / x;
						q = q / x;
						r = r / x;
					}

					s = Math.sqrt(p * p + q * q + r * r);
					if (p < 0) {
						s = -s;
					}
					if (s != 0) {
						if (k != m) {
							H.setValue(k, k-1, -s * x);
						} else if (l != m) {
							H.setValue(k, k-1, -H.getValue(k, k-1));
						}
						p = p + s;
						x = p / s;
						y = q / s;
						z = r / s;
						q = q / p;
						r = r / p;

						// Row modification

						for (int j = k; j < nn; j++) {
							p = H.getValue(k, j) + q * H.getValue(k+1, j);
							if (notlast) {
								p = p + r * H.getValue(k+2, j);
								H.setValue(k+2, j, H.getValue(k+2, j) - p * z);
							}
							H.setValue(k, j, H.getValue(k, j) - p * x);
							H.setValue(k+1,j, H.getValue(k+1, j) - p * y);
						}

						// Column modification

						for (int i = 0; i <= Math.min(n,k+3); i++) {
							p = x * H.getValue(i, k) + y * H.getValue(i, k+1);
							if (notlast) {
								p = p + z * H.getValue(i, k+2);
								H.setValue(i, k+2, H.getValue(i, k+2) - p * r);
							}
							H.setValue(i, k, H.getValue(i, k) - p);
							H.setValue(i, k+1, H.getValue(i, k+1) - p * q);
						}

						// Accumulate transformations

						for (int i = low; i <= high; i++) {
							p = x * V.getValue(i, k) + y * V.getValue(i, k+1);
							if (notlast) {
								p = p + z * V.getValue(i, k+2);
								V.setValue(i, k+2, V.getValue(i, k+2) - p * r);
							}
							V.setValue(i, k, V.getValue(i, k) - p);
							V.setValue(i, k+1, V.getValue(i, k+1) - p * q);
						}
					}  // (s != 0)
				}  // k loop
			}  // check convergence
		}  // while (n >= low)

		// Backsubstitute to find vectors of upper triangular form

		if (norm == 0.0) {
			return;
		}

		for (n = nn-1; n >= 0; n--) {
			p = d[n];
			q = e[n];

			// Real vector

			if (q == 0) {
				int l = n;
				H.setValue(n, n, 1.0);
				for (int i = n-1; i >= 0; i--) {
					w = H.getValue(i, i) - p;
					r = 0.0;
					for (int j = l; j <= n; j++) {
						r = r + H.getValue(i, j) * H.getValue(j, n);
					}
					if (e[i] < 0.0) {
						z = w;
						s = r;
					} else {
						l = i;
						if (e[i] == 0.0) {
							if (w != 0.0) {
								H.setValue(i, n, -r / w);
							} else {
								H.setValue(i, n, -r / (eps * norm));
							}

							// Solve real equations

						} else {
							x = H.getValue(i, i+1);
							y = H.getValue(i+1, i);
							q = (d[i] - p) * (d[i] - p) + e[i] * e[i];
							t = (x * s - z * r) / q;
							H.setValue(i, n, t);
							if (Math.abs(x) > Math.abs(z)) {
								H.setValue(i+1, n, (-r - w * t) / x);
							} else {
								H.setValue(i+1, n, (-s - y * t) / z);
							}
						}

						// Overflow control

						t = Math.abs(H.getValue(i, n));
						if ((eps * t) * t > 1) {
							for (int j = i; j <= n; j++) {
								H.setValue(j, n, H.getValue(j, n) / t);
							}
						}
					}
				}

				// Complex vector

			} else if (q < 0) {
				int l = n-1;

				// Last vector component imaginary so matrix is triangular

				if (Math.abs(H.getValue(n, n-1)) > Math.abs(H.getValue(n-1, n))) {
					H.setValue(n-1, n-1, q / H.getValue(n, n-1));
					H.setValue(n-1, n, -(H.getValue(n, n) - p) / H.getValue(n, n-1));
				} else {
					cdiv(0.0,-H.getValue(n-1, n),H.getValue(n-1, n-1)-p,q);
					H.setValue(n-1, n-1, cdivr);
					H.setValue(n-1, n, cdivi);
				}
				H.setValue(n, n-1, 0.0);
				H.setValue(n, n, 1.0);
				for (int i = n-2; i >= 0; i--) {
					double ra,sa,vr,vi;
					ra = 0.0;
					sa = 0.0;
					for (int j = l; j <= n; j++) {
						ra = ra + H.getValue(i, j) * H.getValue(j, n-1);
						sa = sa + H.getValue(i, j)* H.getValue(j, n);
					}
					w = H.getValue(i, i) - p;

					if (e[i] < 0.0) {
						z = w;
						r = ra;
						s = sa;
					} else {
						l = i;
						if (e[i] == 0) {
							cdiv(-ra,-sa,w,q);
							H.setValue(i, n-1, cdivr);
							H.setValue(i, n, cdivi);
						} else {

							// Solve complex equations

							x = H.getValue(i, i+1);
							y = H.getValue(i+1, i);
							vr = (d[i] - p) * (d[i] - p) + e[i] * e[i] - q * q;
							vi = (d[i] - p) * 2.0 * q;
							if (vr == 0.0 & vi == 0.0) {
								vr = eps * norm * (Math.abs(w) + Math.abs(q) +
										Math.abs(x) + Math.abs(y) + Math.abs(z));
							}
							cdiv(x*r-z*ra+q*sa,x*s-z*sa-q*ra,vr,vi);
							H.setValue(i, n-1, cdivr);
							H.setValue(i, n, cdivi);
							if (Math.abs(x) > (Math.abs(z) + Math.abs(q))) {
								H.setValue(i+1, n-1, (-ra - w * H.getValue(i, n-1) + q * H.getValue(i, n)) / x);
								H.setValue(i+1, n, (-sa - w * H.getValue(i, n) - q * H.getValue(i, n-1)) / x);
							} else {
								cdiv(-r-y*H.getValue(i, n-1),-s-y*H.getValue(i, n),z,q);
								H.setValue(i+1, n-1, cdivr);
								H.setValue(i+1, n, cdivi);
							}
						}

						// Overflow control

						t = Math.max(Math.abs(H.getValue(i, n-1)),Math.abs(H.getValue(i, n)));
						if ((eps * t) * t > 1) {
							for (int j = i; j <= n; j++) {
								H.setValue(j, n-1, H.getValue(j, n-1) / t);
								H.setValue(j, n, H.getValue(j, n) / t);
							}
						}
					}
				}
			}
		}

		// Vectors of isolated roots

		for (int i = 0; i < nn; i++) {
			if (i < low | i > high) {
				for (int j = i; j < nn; j++) {
					V.setValue(i, j, H.getValue(i, j));
				}
			}
		}

		// Back transformation to get eigenvectors of original matrix

		for (int j = nn-1; j >= low; j--) {
			for (int i = low; i <= high; i++) {
				z = 0.0;
				for (int k = low; k <= Math.min(j,high); k++) {
					z = z + V.getValue(i, k) * H.getValue(k, j);
				}
				V.setValue(i, j, z);
			}
		}
	}


	/** Return the real parts of the eigenvalues.
	 * @return the real parts of the eigenvalues
	 */
	public double[] getRealEigenvalues () {
		return d;
	}

	/** Return the imaginary parts of the eigenvalues.
	 * @return the imaginary parts of the eigenvalues
	 */
	public double[] getImagEigenvalues () {
		return e;
	}

}
