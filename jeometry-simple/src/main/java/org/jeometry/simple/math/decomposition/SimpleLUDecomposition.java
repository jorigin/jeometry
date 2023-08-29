package org.jeometry.simple.math.decomposition;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;
import org.jeometry.math.decomposition.LUDecomposition;

/**
 * A simple implementation of {@link LUDecomposition LUDecomposition}.<br><br>
 * This implantation is inspired by <a href="https://math.nist.gov/javanumerics/jama/">Jama</a> <a href="https://math.nist.gov/javanumerics/jama/doc/">LU Decomposition</a>.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class SimpleLUDecomposition implements LUDecomposition {

	/** Internal storage of decomposition.
	 */
	public Matrix LU;

	/**
	 * The L matrix.
	 */
	private Matrix L = null;

	/**
	 * The U Matrix.
	 */
	private Matrix U = null;

	/**
	 * The P matrix.
	 */
	private Matrix P = null;

	/**
	 * The input rows count.
	 */
	private int inputRowsCount;
	
	/**
	 * The input columns count.
	 */
	private int inputColumnsCount;
	
	/**
	 * The pivot sign.
	 */
	private int pivsign; 

	/**
	 * Create a new decomposition from the given matrix.
	 * @param matrix the matrix to decompose
	 */
	public SimpleLUDecomposition(Matrix matrix) {

		this.LU = JeometryFactory.createMatrix(matrix);

		this.inputRowsCount = matrix.getRowsCount();
		this.inputColumnsCount = matrix.getColumnsCount();

		this.pivsign = 1;

		double[] LUcolj = new double[this.inputRowsCount];

		this.P = JeometryFactory.createMatrixEye(this.inputRowsCount);
		
		for (int j = 0; j < this.inputColumnsCount; j++) {

			// Make a copy of the j-th column to localize references.
			for (int i = 0; i < this.inputRowsCount; i++) {
				LUcolj[i] = this.LU.getValue(i, j);
			}

			// Apply previous transformations.
			for (int i = 0; i < this.inputRowsCount; i++) {
				
				// Most of the time is spent in the following dot product.

				int kmax = Math.min(i,j);
				double s = 0.0;
				for (int k = 0; k < kmax; k++) {
					s += this.LU.getValue(i, k)*LUcolj[k];
				}

				this.LU.setValue(i, j, LUcolj[i] -= s);

			}

			// Find pivot and exchange if necessary.
			int p = j;
			for (int i = j+1; i < this.inputRowsCount; i++) {
				if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p])) {
					p = i;
				}
			}
			if (p != j) {
				for (int k = 0; k < this.inputColumnsCount; k++) {
					double t = this.LU.getValue(p, k); 
					this.LU.setValue(p, k, this.LU.getValue(j, k)); 
					this.LU.setValue(j, k, t);
				
					t = this.P.getValue(p, k);
					this.P.setValue(p, k, this.P.getValue(j, k));
					this.P.setValue(j, k, t);
				}

				this.pivsign = -this.pivsign;
			}

			// Compute multipliers.
			if (j < this.inputRowsCount & this.LU.getValue(j, j) != 0.0) {
				for (int i = j+1; i < this.inputRowsCount; i++) {
					this.LU.setValue(i, j,  this.LU.getValue(i, j) / this.LU.getValue(j, j));
				}
			}
			
			
		}
		
		// Compute L matrix
		this.L = JeometryFactory.createMatrix(this.inputRowsCount,this.inputColumnsCount);
		for (int i = 0; i < this.inputRowsCount; i++) {
			for (int j = 0; j < this.inputColumnsCount; j++) {
				if (i > j) {
					this.L.setValue(i, j, this.LU.getValue(i, j));
				} else if (i == j) {
					this.L.setValue(i, j, 1.0);
				} else {
					this.L.setValue(i, j, 0.0);
				}
			}
		}

		// Compute U matrix
		this.U = JeometryFactory.createMatrix(this.inputColumnsCount,this.inputColumnsCount);
		for (int i = 0; i < this.inputColumnsCount; i++) {
			for (int j = 0; j < this.inputColumnsCount; j++) {
				if (i <= j) {
					this.U.setValue(i, j, this.LU.getValue(i, j));
				} else {
					this.U.setValue(i, j, 0.0);
				}
			}
		}		
	}

	/**
	 * Check if the matrix is non singular.
	 * @return <code>true</code> if the matrix is not singular and <code>false</code> otherwise
	 */
	public boolean isNonsingular () {
		for (int j = 0; j < this.inputColumnsCount; j++) {
			if (this.LU.getValue(j, j) == 0)
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
		if (this.inputRowsCount != this.inputColumnsCount) {
			throw new IllegalArgumentException("Matrix must be square.");
		}
		double d = (double) this.pivsign;
		for (int j = 0; j < this.inputColumnsCount; j++) {
			d *= this.LU.getValue(j, j);
		}
		return d;
	}

	@Override
	public List<Matrix> getComponents() {

		List<Matrix> components = new ArrayList<Matrix>(2);
		components.add(this.L);
		components.add(this.U);

		return components;
	}

	@Override
	public Matrix getLower() {
		return this.L;
	}

	@Override
	public Matrix getUpper() {
		return this.U;
	}

	@Override
	public Matrix getPivot() {
		return this.P;
	}

	/**
	 * Compute the matrix <i>X</i> that solve the linear system:<br>
	 * <div style="text-align: center"><i>AX</i>&nbsp;=&nbsp;<i>B</i></div>
	 * <br>
	 * where <i>A</i> is the matrix from which this decomposition is computed.<br><br>
	 * 
	 * This linear solving is equivalent to find the <i>X</i> that solve the linear system:<br><br>
	 * <div style="text-align: center"><i>LUX</i>&nbsp;=&nbsp;<i>PB</i></div>
	 * <br>
	 * @param b the constants parameters
	 * @return the matrix <i>X</i> that solve the linear system <i>AX</i>&nbsp;=&nbsp;<i>B</i>
	 * @throws IllegalArgumentException ix the dimension of <i>B</i> does not match the system
	 * @throws IllegalStateException if the <i>A</i> matrix is singular
	 */
   @Override
   public Matrix solve(Matrix b) {
	   return solve(b, JeometryFactory.createMatrix(this.inputRowsCount, 1));
   }
	
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
		
		if (x.getRowsCount() != this.inputRowsCount) {
		  throw new IllegalArgumentException("Invalid result matrix rows count "+x.getRowsCount()+", expected "+this.inputRowsCount);
		}
		
	      if (!this.isNonsingular()) {
	         throw new IllegalStateException("Matrix is singular.");
	      }

	      // Copy right hand side with pivoting
	      this.P.multiply(b, x);

	      // Solve L*Y = B(piv,:)
	      for (int k = 0; k < this.inputColumnsCount; k++) {
	         for (int i = k+1; i < this.inputColumnsCount; i++) {
	            for (int j = 0; j < b.getColumnsCount(); j++) {
	            	x.setValue(i, j, x.getValue(i, j) - x.getValue(k, j)*this.LU.getValue(i, k));
	            }
	         }
	      }
	      
	      // Solve U*X = Y;
	      for (int k = this.inputColumnsCount-1; k >= 0; k--) {
	         for (int j = 0; j < b.getColumnsCount(); j++) {
	        	 x.setValue(k, j, x.getValue(k, j) / this.LU.getValue(k, k));
	         }
	         for (int i = 0; i < k; i++) {
	            for (int j = 0; j < b.getColumnsCount(); j++) {
	               x.setValue(i, j,  x.getValue(i, j) - x.getValue(k, j)*this.LU.getValue(i, k));
	            }
	         }
	      }
	      return x;
	}

	@Override
	public Vector solve(Vector b) {
		return solve(b, JeometryFactory.createVector(this.inputRowsCount));
	}

	@Override
	public Vector solve(Vector b, Vector x) {
		if (b == null) {
			throw new IllegalArgumentException("Constant vector is null");
		}

		if (b.getDimension() != this.inputRowsCount) {
	      throw new IllegalArgumentException("Invalid constant vector rows count "+b.getDimension()+", expected "+this.inputRowsCount);
	    }
		
		if (x == null) {
			throw new IllegalArgumentException("Result vector is null");
		}
		
		if (x.getDimension() != this.inputColumnsCount) {
		  throw new IllegalArgumentException("Invalid result vector rows count "+x.getDimension()+", expected "+this.inputColumnsCount);
		}
		
	      if (!this.isNonsingular()) {
	         throw new IllegalStateException("Matrix is singular.");
	      }

	      // Copy right hand side with pivoting
	      this.P.multiply(b, x);

	      // Solve L*Y = B(piv,:)
	      for (int k = 0; k < this.inputColumnsCount; k++) {
	         for (int i = k+1; i < this.inputColumnsCount; i++) {
	            x.setValue(i, x.getValue(i) - x.getValue(k)*this.LU.getValue(i, k));
	         }
	      }
	      
	      // Solve U*X = Y;
	      for (int k = this.inputColumnsCount-1; k >= 0; k--) {
	         x.setValue(k, x.getValue(k) / this.LU.getValue(k, k));
	         
	         for (int i = 0; i < k; i++) {
	            x.setValue(i,  x.getValue(i) - x.getValue(k)*this.LU.getValue(i, k));
	         }
	      }
	      return x;
	}
}
