package org.jeometry.simple.math.decomposition;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.decomposition.LUDecomposition;

/**
 * A simple implementation of {@link LUDecomposition LUDecomposition}.<br><br>
 * This implantation is inspired by <a href="https://math.nist.gov/javanumerics/jama/"a>Jama</a> <a href="https://math.nist.gov/javanumerics/jama/doc/">LU Decomposition</a>.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} b{@value Geometry#BUILD}
 * @since 1.0.0
 */
public class SimpleLUDecomposition implements LUDecomposition {

	/** Internal storage of decomposition.
	 */
	public Matrix LU;

	private Matrix L = null;

	private Matrix U = null;

	private Matrix P = null;

	private int m;
	
	private int n;
	
	private int pivsign; 

	/**
	 * Create a new decomposition from the given matrix.
	 * @param matrix the matrix to decompose
	 */
	public SimpleLUDecomposition(Matrix matrix) {

		LU = GeometryFactory.createMatrix(matrix);

		m = matrix.getRowsCount();
		n = matrix.getColumnsCount();

		pivsign = 1;

		double[] LUcolj = new double[m];

		P = GeometryFactory.createMatrixEye(m);
		
		for (int j = 0; j < n; j++) {

			// Make a copy of the j-th column to localize references.
			for (int i = 0; i < m; i++) {
				LUcolj[i] = LU.getValue(i, j);
			}

			// Apply previous transformations.
			for (int i = 0; i < m; i++) {
				
				// Most of the time is spent in the following dot product.

				int kmax = Math.min(i,j);
				double s = 0.0;
				for (int k = 0; k < kmax; k++) {
					s += LU.getValue(i, k)*LUcolj[k];
				}

				LU.setValue(i, j, LUcolj[i] -= s);

			}

			// Find pivot and exchange if necessary.
			int p = j;
			for (int i = j+1; i < m; i++) {
				if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p])) {
					p = i;
				}
			}
			if (p != j) {
				for (int k = 0; k < n; k++) {
					double t = LU.getValue(p, k); 
					LU.setValue(p, k, LU.getValue(j, k)); 
					LU.setValue(j, k, t);
				
					t = P.getValue(p, k);
					P.setValue(p, k, P.getValue(j, k));
					P.setValue(j, k, t);
				}

				pivsign = -pivsign;
			}

			// Compute multipliers.
			if (j < m & LU.getValue(j, j) != 0.0) {
				for (int i = j+1; i < m; i++) {
					LU.setValue(i, j,  LU.getValue(i, j) / LU.getValue(j, j));
				}
			}
			
			
		}
		
		// Compute L matrix
		L = GeometryFactory.createMatrix(m,n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i > j) {
					L.setValue(i, j, LU.getValue(i, j));
				} else if (i == j) {
					L.setValue(i, j, 1.0);
				} else {
					L.setValue(i, j, 0.0);
				}
			}
		}

		// Compute U matrix
		U = GeometryFactory.createMatrix(n,n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i <= j) {
					U.setValue(i, j, LU.getValue(i, j));
				} else {
					U.setValue(i, j, 0.0);
				}
			}
		}		
	}

	/**
	 * Check if the matrix is non singular.
	 * @return <code>true</code> if the matrix is not singular and <code>false</code> otherwise
	 */
	public boolean isNonsingular () {
		for (int j = 0; j < n; j++) {
			if (LU.getValue(j, j) == 0)
				return false;
		}
		return true;
	}


	/**
	 * Compute the determinant of the matrix that is decomposed.
	 * @return the determinant of the matrix that is decomposed
	 * @throws IllegalArgumentException if the decomposed matrix is not suare
	 */
	public double det() {
		if (m != n) {
			throw new IllegalArgumentException("Matrix must be square.");
		}
		double d = (double) pivsign;
		for (int j = 0; j < n; j++) {
			d *= LU.getValue(j, j);
		}
		return d;
	}

	/**
	 * Compute the matrix <i>X</i> that solve the linear system:<br>
	 * <center><i>AX</i>&nbsp;=&nbsp;<i>B</i></center>
	 * <br>
	 * where <i>A</i> is the matrix from which this decomposition is computed.<br><br>
	 * 
	 * This linear solving is equivalent to find the <i>X</i> that solve the linear system:<br><br>
	 * <center><i>LUX</i>&nbsp;=&nbsp;<i>PB</i></center>
	 * <br>
	 * @param B the constants parameters
	 * @return the matrix <i>X</i> that solve the linear system <i>AX</i>&nbsp;=&nbsp;<i>B</i>
	 * @throws IllegalArgumentException ix the dimension of <i>B</i> does not match the system
	 * @throws IllegalStateException if the <i>A</i> matrix is singular
	 */
   public Matrix solve(Matrix B) {
      if (B.getRowsCount() != m) {
         throw new IllegalArgumentException("Matrix row dimensions must agree.");
      }
      if (!this.isNonsingular()) {
         throw new IllegalStateException("Matrix is singular.");
      }

      // Copy right hand side with pivoting
      Matrix x = P.multiply(B);

      // Solve L*Y = B(piv,:)
      for (int k = 0; k < n; k++) {
         for (int i = k+1; i < n; i++) {
            for (int j = 0; j < B.getColumnsCount(); j++) {
            	x.setValue(i, j, x.getValue(i, j) - x.getValue(k, j)*LU.getValue(i, k));
            }
         }
      }
      
      // Solve U*X = Y;
      for (int k = n-1; k >= 0; k--) {
         for (int j = 0; j < B.getColumnsCount(); j++) {
        	 x.setValue(k, j, x.getValue(k, j) / LU.getValue(k, k));
         }
         for (int i = 0; i < k; i++) {
            for (int j = 0; j < B.getColumnsCount(); j++) {
               x.setValue(i, j,  x.getValue(i, j) - x.getValue(k, j)*LU.getValue(i, k));
            }
         }
      }
      return x;
   }

	@Override
	public List<Matrix> getComponents() {

		List<Matrix> components = new ArrayList<Matrix>(2);
		components.add(L);
		components.add(U);

		return components;
	}

	@Override
	public Matrix getLower() {
		return L;
	}

	@Override
	public Matrix getUpper() {
		return U;
	}

	@Override
	public Matrix getPivot() {
		return P;
	}
}
