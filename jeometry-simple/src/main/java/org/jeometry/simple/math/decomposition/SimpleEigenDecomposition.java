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

		this.n = matrix.getColumnsCount();

		this.V = JeometryFactory.createMatrix(this.n, this.n);

		this.d = new double[this.n];
		this.e = new double[this.n];

		this.issymmetric = true;
		for (int j = 0; (j < this.n) & this.issymmetric; j++) {
			for (int i = 0; (i < this.n) & this.issymmetric; i++) {
				this.issymmetric = (A[i][j] == A[j][i]);
			}
		}

		if (this.issymmetric) {
			for (int i = 0; i < this.n; i++) {
				for (int j = 0; j < this.n; j++) {
					this.V.setValue(i, j, A[i][j]);
				}
			}

			// Tridiagonalize.
			tred2();

			// Diagonalize.
			tql2();

		} else {
			this.H = JeometryFactory.createMatrix(this.n, this.n);
			this.ort = new double[this.n];

			for (int j = 0; j < this.n; j++) {
				for (int i = 0; i < this.n; i++) {
					this.H.setValue(i, j, A[i][j]);
				}
			}

			// Reduce to Hessenberg form.
			orthes();

			// Reduce Hessenberg to real Schur form.
			hqr2();
		}

		// Compute D matrix
		this.D = JeometryFactory.createMatrix(this.n, this.n);
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				this.D.setValue(i, j, 0.0);
			}
			this.D.setValue(i, i, this.d[i]);
			if (this.e[i] > 0) {
				this.D.setValue(i, i+1, this.e[i]);
			} else if (this.e[i] < 0) {
				this.D.setValue(i, i-1, this.e[i]);
			}
		}
	}

	@Override
	public Matrix getD() {
		return this.D;
	}

	@Override
	public Matrix getV() {
		return this.V;
	}

	@Override
	public List<Matrix> getComponents() {
		List<Matrix> matrices = new ArrayList<Matrix>(2);
		matrices.add(this.V);
		matrices.add(this.D);
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
			this.cdivr = (xr + r*xi)/d;
			this.cdivi = (xi - r*xr)/d;
		} else {
			r = yr/yi;
			d = yi + r*yr;
			this.cdivr = (r*xr + xi)/d;
			this.cdivi = (r*xi - xr)/d;
		}
	}

	/**
	 * This is derived from the Algol procedures tred2 by
	 * Bowdler, Martin, Reinsch, and Wilkinson, Handbook for
	 * Auto. Comp., Vol.ii-Linear Algebra, and the corresponding
	 * Fortran subroutine in EISPACK.
	 */
	private void tred2 () {

		for (int j = 0; j < this.n; j++) {
			this.d[j] = this.V.getValue(this.n-1, j);
		}

		// Householder reduction to tridiagonal form.

		for (int i = this.n-1; i > 0; i--) {

			// Scale to avoid under/overflow.

			double scale = 0.0;
			double h = 0.0;
			for (int k = 0; k < i; k++) {
				scale = scale + Math.abs(this.d[k]);
			}
			if (scale == 0.0) {
				this.e[i] = this.d[i-1];
				for (int j = 0; j < i; j++) {
					this.d[j] = this.V.getValue(i-1, j);
					this.V.setValue(i, j, 0.0);
					this.V.setValue(j, i, 0.0);
				}
			} else {

				// Generate Householder vector.

				for (int k = 0; k < i; k++) {
					this.d[k] /= scale;
					h += this.d[k] * this.d[k];
				}
				double f = this.d[i-1];
				double g = Math.sqrt(h);
				if (f > 0) {
					g = -g;
				}
				this.e[i] = scale * g;
				h = h - f * g;
				this.d[i-1] = f - g;
				for (int j = 0; j < i; j++) {
					this.e[j] = 0.0;
				}

				// Apply similarity transformation to remaining columns.

				for (int j = 0; j < i; j++) {
					f = this.d[j];
					this.V.setValue(j, i, f);
					g = this.e[j] + this.V.getValue(j, j) * f;
					for (int k = j+1; k <= i-1; k++) {
						g += this.V.getValue(k, j) * this.d[k];
						this.e[k] += this.V.getValue(k, j) * f;
					}
					this.e[j] = g;
				}
				f = 0.0;
				for (int j = 0; j < i; j++) {
					this.e[j] /= h;
					f += this.e[j] * this.d[j];
				}
				double hh = f / (h + h);
				for (int j = 0; j < i; j++) {
					this.e[j] -= hh * this.d[j];
				}
				for (int j = 0; j < i; j++) {
					f = this.d[j];
					g = this.e[j];
					for (int k = j; k <= i-1; k++) {
						this.V.setValue(k, j, this.V.getValue(k, j) - (f * this.e[k] + g * this.d[k]));
					}
					this.d[j] = this.V.getValue(i-1, j);
					this.V.setValue(i, j, 0.0);
				}
			}
			this.d[i] = h;
		}

		// Accumulate transformations.

		for (int i = 0; i < this.n-1; i++) {
			this.V.setValue(this.n-1, i, this.V.getValue(i, i));
			this.V.setValue(i, i, 1.0);
			double h = this.d[i+1];
			if (h != 0.0) {
				for (int k = 0; k <= i; k++) {
					this.d[k] = this.V.getValue(k, i+1) / h;
				}
				for (int j = 0; j <= i; j++) {
					double g = 0.0;
					for (int k = 0; k <= i; k++) {
						g += this.V.getValue(k, i+1) * this.V.getValue(k, j);
					}
					for (int k = 0; k <= i; k++) {
						this.V.setValue(k, j,  this.V.getValue(k, j) - g * this.d[k]);
					}
				}
			}
			for (int k = 0; k <= i; k++) {
				this.V.setValue(k, i+1, 0.0);
			}
		}
		for (int j = 0; j < this.n; j++) {
			this.d[j] = this.V.getValue(this.n-1, j);
			this.V.setValue(this.n-1, j, 0.0);
		}
		this.V.setValue(this.n-1, this.n-1, 1.0);
		this.e[0] = 0.0;
	} 

	/**
	 * Symmetric tridiagonal QL algorithm.
	 */
	private void tql2 () {

		//  This is derived from the Algol procedures tql2, by
		//  Bowdler, Martin, Reinsch, and Wilkinson, Handbook for
		//  Auto. Comp., Vol.ii-Linear Algebra, and the corresponding
		//  Fortran subroutine in EISPACK.

		for (int i = 1; i < this.n; i++) {
			this.e[i-1] = this.e[i];
		}
		this.e[this.n-1] = 0.0;

		double f = 0.0;
		double tst1 = 0.0;
		double eps = Math.pow(2.0,-52.0);
		for (int l = 0; l < this.n; l++) {

			// Find small subdiagonal element

			tst1 = Math.max(tst1,Math.abs(this.d[l]) + Math.abs(this.e[l]));
			int m = l;
			while (m < this.n) {
				if (Math.abs(this.e[m]) <= eps*tst1) {
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

					double g = this.d[l];
					double p = (this.d[l+1] - g) / (2.0 * this.e[l]);
					double r = hypot(p,1.0);
					if (p < 0) {
						r = -r;
					}
					this.d[l] = this.e[l] / (p + r);
					this.d[l+1] = this.e[l] * (p + r);
					double dl1 = this.d[l+1];
					double h = g - this.d[l];
					for (int i = l+2; i < this.n; i++) {
						this.d[i] -= h;
					}
					f = f + h;

					// Implicit QL transformation.

					p = this.d[m];
					double c = 1.0;
					double c2 = c;
					double c3 = c;
					double el1 = this.e[l+1];
					double s = 0.0;
					double s2 = 0.0;
					for (int i = m-1; i >= l; i--) {
						c3 = c2;
						c2 = c;
						s2 = s;
						g = c * this.e[i];
						h = c * p;
						r = hypot(p,this.e[i]);
						this.e[i+1] = s * r;
						s = this.e[i] / r;
						c = p / r;
						p = c * this.d[i] - s * g;
						this.d[i+1] = h + s * (c * g + s * this.d[i]);

						// Accumulate transformation.

						for (int k = 0; k < this.n; k++) {
							h = this.V.getValue(k, i+1);
							this.V.setValue(k, i+1, s * this.V.getValue(k, i) + c * h);
							this.V.setValue(k, i, c * this.V.getValue(k, i) - s * h);
						}
					}
					p = -s * s2 * c3 * el1 * this.e[l] / dl1;
					this.e[l] = s * p;
					this.d[l] = c * p;

					// Check for convergence.

				} while (Math.abs(this.e[l]) > eps*tst1);
			}
			this.d[l] = this.d[l] + f;
			this.e[l] = 0.0;
		}

		// Sort eigenvalues and corresponding vectors.

		for (int i = 0; i < this.n-1; i++) {
			int k = i;
			double p = this.d[i];
			for (int j = i+1; j < this.n; j++) {
				if (this.d[j] < p) {
					k = j;
					p = this.d[j];
				}
			}
			if (k != i) {
				this.d[k] = this.d[i];
				this.d[i] = p;
				for (int j = 0; j < this.n; j++) {
					p = this.V.getValue(j, i);
					this.V.setValue(j, i, this.V.getValue(j, k));
					this.V.setValue(j, k, p);
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
		int high = this.n-1;

		for (int m = low+1; m <= high-1; m++) {

			// Scale column.

			double scale = 0.0;
			for (int i = m; i <= high; i++) {
				scale = scale + Math.abs(this.H.getValue(i, m-1));
			}
			if (scale != 0.0) {

				// Compute Householder transformation.

				double h = 0.0;
				for (int i = high; i >= m; i--) {
					this.ort[i] = this.H.getValue(i, m-1)/scale;
					h += this.ort[i] * this.ort[i];
				}
				double g = Math.sqrt(h);
				if (this.ort[m] > 0) {
					g = -g;
				}
				h = h - this.ort[m] * g;
				this.ort[m] = this.ort[m] - g;

				// Apply Householder similarity transformation
				// H = (I-u*u'/h)*H*(I-u*u')/h)

				for (int j = m; j < this.n; j++) {
					double f = 0.0;
					for (int i = high; i >= m; i--) {
						f += this.ort[i]*this.H.getValue(i, j);
					}
					f = f/h;
					for (int i = m; i <= high; i++) {
						this.H.setValue(i, j,  this.H.getValue(i, j) - f*this.ort[i]);
					}
				}

				for (int i = 0; i <= high; i++) {
					double f = 0.0;
					for (int j = high; j >= m; j--) {
						f += this.ort[j]*this.H.getValue(i, j);
					}
					f = f/h;
					for (int j = m; j <= high; j++) {
						this.H.setValue(i, j,  this.H.getValue(i, j) - f*this.ort[j]);
					}
				}
				this.ort[m] = scale*this.ort[m];

				this.H.setValue(m, m-1, scale*g);
			}
		}

		// Accumulate transformations (Algol's ortran).

		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				this.V.setValue(i, j, (i == j ? 1.0 : 0.0));
			}
		}

		for (int m = high-1; m >= low+1; m--) {
			if (this.H.getValue(m, m-1) != 0.0) {
				for (int i = m+1; i <= high; i++) {
					this.ort[i] = this.H.getValue(i, m-1);
				}
				for (int j = m; j <= high; j++) {
					double g = 0.0;
					for (int i = m; i <= high; i++) {
						g += this.ort[i] * this.V.getValue(i, j);
					}
					// Double division avoids possible underflow
					g = (g / this.ort[m]) / this.H.getValue(m, m-1);
					for (int i = m; i <= high; i++) {
						this.V.setValue(i, j,  this.V.getValue(i, j) + g * this.ort[i]);
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
				this.d[i] = this.H.getValue(i, i);
				this.e[i] = 0.0;
			}
			for (int j = Math.max(i-1,0); j < nn; j++) {
				norm = norm + Math.abs(this.H.getValue(i, j));
			}
		}

		// Outer loop over eigenvalue index

		int iter = 0;
		while (n >= low) {

			// Look for single small sub-diagonal element

			int l = n;
			while (l > low) {
				s = Math.abs(this.H.getValue(l-1, l-1)) + Math.abs(this.H.getValue(l, l));
				if (s == 0.0) {
					s = norm;
				}
				if (Math.abs(this.H.getValue(l, l-1)) < eps * s) {
					break;
				}
				l--;
			}

			// Check for convergence
			// One root found

			if (l == n) {
				this.H.setValue(n, n, this.H.getValue(n, n) + exshift);
				this.d[n] = this.H.getValue(n, n);
				this.e[n] = 0.0;
				n--;
				iter = 0;

				// Two roots found

			} else if (l == n-1) {
				w = this.H.getValue(n, n-1) * this.H.getValue(n-1, n);
				p = (this.H.getValue(n-1, n-1) - this.H.getValue(n, n)) / 2.0;
				q = p * p + w;
				z = Math.sqrt(Math.abs(q));
				this.H.setValue(n, n, this.H.getValue(n, n) + exshift);
				this.H.setValue(n-1, n-1, this.H.getValue(n-1, n-1) + exshift);
				x = this.H.getValue(n, n);

				// Real pair

				if (q >= 0) {
					if (p >= 0) {
						z = p + z;
					} else {
						z = p - z;
					}
					this.d[n-1] = x + z;
					this.d[n] = this.d[n-1];
					if (z != 0.0) {
						this.d[n] = x - w / z;
					}
					this.e[n-1] = 0.0;
					this.e[n] = 0.0;
					x = this.H.getValue(n, n-1);
					s = Math.abs(x) + Math.abs(z);
					p = x / s;
					q = z / s;
					r = Math.sqrt(p * p+q * q);
					p = p / r;
					q = q / r;

					// Row modification  
					for (int j = n-1; j < nn; j++) {
						z = this.H.getValue(n-1, j);
						this.H.setValue(n-1, j, q * z + p * this.H.getValue(n, j));
						this.H.setValue(n, j, q * this.H.getValue(n, j) - p * z);
					}

					// Column modification

					for (int i = 0; i <= n; i++) {
						z = this.H.getValue(i, n-1);
						this.H.setValue(i, n-1, q * z + p * this.H.getValue(i, n));
						this.H.setValue(i, n, q * this.H.getValue(i, n) - p * z);
					}

					// Accumulate transformations

					for (int i = low; i <= high; i++) {
						z = this.V.getValue(i, n-1);
						this.V.setValue(i, n-1, q * z + p * this.V.getValue(i, n));
						this.V.setValue(i, n, q * this.V.getValue(i, n) - p * z);
					}

					// Complex pair

				} else {
					this.d[n-1] = x + p;
					this.d[n] = x + p;
					this.e[n-1] = z;
					this.e[n] = -z;
				}
				n = n - 2;
				iter = 0;

				// No convergence yet

			} else {

				// Form shift

				x = this.H.getValue(n, n);
				y = 0.0;
				w = 0.0;
				if (l < n) {
					y = this.H.getValue(n-1, n-1);
					w = this.H.getValue(n, n-1) * this.H.getValue(n-1, n);
				}

				// Wilkinson's original ad hoc shift

				if (iter == 10) {
					exshift += x;
					for (int i = low; i <= n; i++) {
						this.H.setValue(i, i, this.H.getValue(i, i)- x);
					}
					s = Math.abs(this.H.getValue(n, n-1)) + Math.abs(this.H.getValue(n-1, n-2));
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
							this.H.setValue(i, i, this.H.getValue(i, i) - s);
						}
						exshift += s;
						x = y = w = 0.964;
					}
				}

				iter = iter + 1;   // (Could check iteration count here.)

				// Look for two consecutive small sub-diagonal elements

				int m = n-2;
				while (m >= l) {
					z = this.H.getValue(m, m);
					r = x - z;
					s = y - z;
					p = (r * s - w) / this.H.getValue(m+1, m) + this.H.getValue(m, m+1);
					q = this.H.getValue(m+1, m+1) - z - r - s;
					r = this.H.getValue(m+2, m+1);
					s = Math.abs(p) + Math.abs(q) + Math.abs(r);
					p = p / s;
					q = q / s;
					r = r / s;
					if (m == l) {
						break;
					}
					if (Math.abs(this.H.getValue(m, m-1)) * (Math.abs(q) + Math.abs(r)) <
							eps * (Math.abs(p) * (Math.abs(this.H.getValue(m-1, m-1)) + Math.abs(z) +
									Math.abs(this.H.getValue(m+1, m+1))))) {
						break;
					}
					m--;
				}

				for (int i = m+2; i <= n; i++) {
					this.H.setValue(i, i-2, 0.0);
					if (i > m+2) {
						this.H.setValue(i, i-3, 0.0);
					}
				}

				// Double QR step involving rows l:n and columns m:n


				for (int k = m; k <= n-1; k++) {
					boolean notlast = (k != n-1);
					if (k != m) {
						p = this.H.getValue(k, k-1);
						q = this.H.getValue(k+1, k-1);
						r = (notlast ? this.H.getValue(k+2, k-1) : 0.0);
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
							this.H.setValue(k, k-1, -s * x);
						} else if (l != m) {
							this.H.setValue(k, k-1, -this.H.getValue(k, k-1));
						}
						p = p + s;
						x = p / s;
						y = q / s;
						z = r / s;
						q = q / p;
						r = r / p;

						// Row modification

						for (int j = k; j < nn; j++) {
							p = this.H.getValue(k, j) + q * this.H.getValue(k+1, j);
							if (notlast) {
								p = p + r * this.H.getValue(k+2, j);
								this.H.setValue(k+2, j, this.H.getValue(k+2, j) - p * z);
							}
							this.H.setValue(k, j, this.H.getValue(k, j) - p * x);
							this.H.setValue(k+1,j, this.H.getValue(k+1, j) - p * y);
						}

						// Column modification

						for (int i = 0; i <= Math.min(n,k+3); i++) {
							p = x * this.H.getValue(i, k) + y * this.H.getValue(i, k+1);
							if (notlast) {
								p = p + z * this.H.getValue(i, k+2);
								this.H.setValue(i, k+2, this.H.getValue(i, k+2) - p * r);
							}
							this.H.setValue(i, k, this.H.getValue(i, k) - p);
							this.H.setValue(i, k+1, this.H.getValue(i, k+1) - p * q);
						}

						// Accumulate transformations

						for (int i = low; i <= high; i++) {
							p = x * this.V.getValue(i, k) + y * this.V.getValue(i, k+1);
							if (notlast) {
								p = p + z * this.V.getValue(i, k+2);
								this.V.setValue(i, k+2, this.V.getValue(i, k+2) - p * r);
							}
							this.V.setValue(i, k, this.V.getValue(i, k) - p);
							this.V.setValue(i, k+1, this.V.getValue(i, k+1) - p * q);
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
			p = this.d[n];
			q = this.e[n];

			// Real vector

			if (q == 0) {
				int l = n;
				this.H.setValue(n, n, 1.0);
				for (int i = n-1; i >= 0; i--) {
					w = this.H.getValue(i, i) - p;
					r = 0.0;
					for (int j = l; j <= n; j++) {
						r = r + this.H.getValue(i, j) * this.H.getValue(j, n);
					}
					if (this.e[i] < 0.0) {
						z = w;
						s = r;
					} else {
						l = i;
						if (this.e[i] == 0.0) {
							if (w != 0.0) {
								this.H.setValue(i, n, -r / w);
							} else {
								this.H.setValue(i, n, -r / (eps * norm));
							}

							// Solve real equations

						} else {
							x = this.H.getValue(i, i+1);
							y = this.H.getValue(i+1, i);
							q = (this.d[i] - p) * (this.d[i] - p) + this.e[i] * this.e[i];
							t = (x * s - z * r) / q;
							this.H.setValue(i, n, t);
							if (Math.abs(x) > Math.abs(z)) {
								this.H.setValue(i+1, n, (-r - w * t) / x);
							} else {
								this.H.setValue(i+1, n, (-s - y * t) / z);
							}
						}

						// Overflow control

						t = Math.abs(this.H.getValue(i, n));
						if ((eps * t) * t > 1) {
							for (int j = i; j <= n; j++) {
								this.H.setValue(j, n, this.H.getValue(j, n) / t);
							}
						}
					}
				}

				// Complex vector

			} else if (q < 0) {
				int l = n-1;

				// Last vector component imaginary so matrix is triangular

				if (Math.abs(this.H.getValue(n, n-1)) > Math.abs(this.H.getValue(n-1, n))) {
					this.H.setValue(n-1, n-1, q / this.H.getValue(n, n-1));
					this.H.setValue(n-1, n, -(this.H.getValue(n, n) - p) / this.H.getValue(n, n-1));
				} else {
					cdiv(0.0,-this.H.getValue(n-1, n),this.H.getValue(n-1, n-1)-p,q);
					this.H.setValue(n-1, n-1, this.cdivr);
					this.H.setValue(n-1, n, this.cdivi);
				}
				this.H.setValue(n, n-1, 0.0);
				this.H.setValue(n, n, 1.0);
				for (int i = n-2; i >= 0; i--) {
					double ra,sa,vr,vi;
					ra = 0.0;
					sa = 0.0;
					for (int j = l; j <= n; j++) {
						ra = ra + this.H.getValue(i, j) * this.H.getValue(j, n-1);
						sa = sa + this.H.getValue(i, j)* this.H.getValue(j, n);
					}
					w = this.H.getValue(i, i) - p;

					if (this.e[i] < 0.0) {
						z = w;
						r = ra;
						s = sa;
					} else {
						l = i;
						if (this.e[i] == 0) {
							cdiv(-ra,-sa,w,q);
							this.H.setValue(i, n-1, this.cdivr);
							this.H.setValue(i, n, this.cdivi);
						} else {

							// Solve complex equations

							x = this.H.getValue(i, i+1);
							y = this.H.getValue(i+1, i);
							vr = (this.d[i] - p) * (this.d[i] - p) + this.e[i] * this.e[i] - q * q;
							vi = (this.d[i] - p) * 2.0 * q;
							if (vr == 0.0 & vi == 0.0) {
								vr = eps * norm * (Math.abs(w) + Math.abs(q) +
										Math.abs(x) + Math.abs(y) + Math.abs(z));
							}
							cdiv(x*r-z*ra+q*sa,x*s-z*sa-q*ra,vr,vi);
							this.H.setValue(i, n-1, this.cdivr);
							this.H.setValue(i, n, this.cdivi);
							if (Math.abs(x) > (Math.abs(z) + Math.abs(q))) {
								this.H.setValue(i+1, n-1, (-ra - w * this.H.getValue(i, n-1) + q * this.H.getValue(i, n)) / x);
								this.H.setValue(i+1, n, (-sa - w * this.H.getValue(i, n) - q * this.H.getValue(i, n-1)) / x);
							} else {
								cdiv(-r-y*this.H.getValue(i, n-1),-s-y*this.H.getValue(i, n),z,q);
								this.H.setValue(i+1, n-1, this.cdivr);
								this.H.setValue(i+1, n, this.cdivi);
							}
						}

						// Overflow control

						t = Math.max(Math.abs(this.H.getValue(i, n-1)),Math.abs(this.H.getValue(i, n)));
						if ((eps * t) * t > 1) {
							for (int j = i; j <= n; j++) {
								this.H.setValue(j, n-1, this.H.getValue(j, n-1) / t);
								this.H.setValue(j, n, this.H.getValue(j, n) / t);
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
					this.V.setValue(i, j, this.H.getValue(i, j));
				}
			}
		}

		// Back transformation to get eigenvectors of original matrix

		for (int j = nn-1; j >= low; j--) {
			for (int i = low; i <= high; i++) {
				z = 0.0;
				for (int k = low; k <= Math.min(j,high); k++) {
					z = z + this.V.getValue(i, k) * this.H.getValue(k, j);
				}
				this.V.setValue(i, j, z);
			}
		}
	}


	/** Return the real parts of the eigenvalues.
	 * @return the real parts of the eigenvalues
	 */
	public double[] getRealEigenvalues () {
		return this.d;
	}

	/** Return the imaginary parts of the eigenvalues.
	 * @return the imaginary parts of the eigenvalues
	 */
	public double[] getImagEigenvalues () {
		return this.e;
	}

}
