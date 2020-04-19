package org.jeometry.simple.math.solver;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;
import org.jeometry.math.solver.Solver;

/**
 * A linear system solver that relies on <a href="https://mathworld.wolfram.com/GaussianElimination.html">Gauss elimination method</a>.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
// TODO enhance comments to describe the method
public class SimpleGaussEliminationSolver implements Solver {

	/**
	 * The &epsilon; value for <code>0</code> approximation. Numbers under this values a considered as <code>0</code>
	 */
	public static final double EPSILON = 1e-10;

    // forward elimination
    private void forwardElimination(Matrix augmented, int rows, int columns) {
        for (int p = 0; p < Math.min(rows, columns); p++) {

            // find pivot row using partial pivoting
            int max = p;
            for (int i = p+1; i < rows; i++) {
                if (Math.abs(augmented.getValue(i, p)) > Math.abs(augmented.getValue(max, p))) {
                    max = i;
                }
            }

            // swap
            swap(augmented, p, max);

            // singular or nearly singular
            if (Math.abs(augmented.getValue(p, p)) <= EPSILON) {
                continue;
            }

            // pivot
            pivot(augmented, p, rows, columns);
        }
    }

    // swap row1 and row2
    private void swap(Matrix augmented, int row1, int row2) {
    	
    	double temp;
    	for(int col = 0; col < augmented.getColumnsCount(); col++) {
    		temp = augmented.getValue(row1, col);
    		
    		augmented.setValue(row1, col, augmented.getValue(row2, col));
    		augmented.setValue(row2, col, temp);
    		
    	}
    }

    // pivot on a[p][p]
    private void pivot(Matrix augmented, int p, int rows, int columns) {
        for (int i = p+1; i < rows; i++) {
            double alpha = augmented.getValue(i, p) / augmented.getValue(p, p);
            for (int j = p; j <= columns; j++) {
            	augmented.setValue(i, j, augmented.getValue(i, j) - alpha * augmented.getValue(p, j));
            }
        }
    }

    /**
     * Returns a solution to the linear system of equations <em>Ax</em> = <em>b</em>.   
     * @param augmented the augmented matrix
     * @param rows the number of rows of the initial matrix
     * @param columns the number of columns of the initial matrix
     * @param result the matrix where the result is stored
     * @return <code>true</code> if there is a solution and <code>false</code> otherwise
     */
    private boolean primal(Matrix augmented, int rows, int columns, Matrix result) {
        
    	// Initialisation
    	for(int row = 0; row < result.getRowsCount(); row++) {
    		result.setValue(row, 0, 0.0d);
    	}
    	
    	// back substitution
        for (int i = Math.min(columns-1, rows-1); i >= 0; i--) {
            double sum = 0.0;
            for (int j = i+1; j < columns; j++) {
                sum += augmented.getValue(i, j) * result.getValue(j, 0);
            }

            if (Math.abs(augmented.getValue(i, i)) > EPSILON) {
            	result.setValue(i, 0, (augmented.getValue(i, columns) - sum) / augmented.getValue(i, i));
            } else if (Math.abs(augmented.getValue(i, columns) - sum) > EPSILON) {
                return false;
            }
        }

        // redundant rows
        for (int i = columns; i < rows; i++) {
            double sum = 0.0;
            for (int j = 0; j < columns; j++) {
                sum += augmented.getValue(i, j) * result.getValue(j, 0);
            }
            if (Math.abs(augmented.getValue(i, columns) - sum) > EPSILON)
                return false;
        }
        return true;
    }

    /**
     * Returns a solution to the linear system of equations <em>Ax</em> = <em>b</em>.   
     * @param augmented the augmented matrix
     * @param rows the number of rows of the initial matrix
     * @param columns the number of columns of the initial matrix
     * @param result the vector where the result is stored
     * @return <code>true</code> if there is a solution and <code>false</code> otherwise
     */
    private boolean primal(Matrix augmented, int rows, int columns, Vector result) {
        
    	// Initialisation
    	for(int row = 0; row < result.getDimension(); row++) {
    		result.setVectorComponent(row, 0.0d);
    	}
 
    	// back substitution
        for (int i = Math.min(columns-1, rows-1); i >= 0; i--) {
            double sum = 0.0;
            for (int j = i+1; j < columns; j++) {
                sum += augmented.getValue(i, j) * result.getVectorComponent(j);
            }

            if (Math.abs(augmented.getValue(i, i)) > EPSILON) {
            	result.setVectorComponent(i, (augmented.getValue(i, columns) - sum) / augmented.getValue(i, i));
            } else if (Math.abs(augmented.getValue(i, columns) - sum) > EPSILON) {
                return false;
            }
        }

        // redundant rows
        for (int i = columns; i < rows; i++) {
            double sum = 0.0;
            for (int j = 0; j < columns; j++) {
                sum += augmented.getValue(i, j) * result.getVectorComponent(j);
            }
            
            if (Math.abs(augmented.getValue(i, columns) - sum) > EPSILON) {
                return false;
            }
        }
        return true;
    }
    
	@Override
	public int getMethod() {
		return Solver.METHOD_GAUSS;
	}

	@Override
	public Matrix solve(Matrix a, Matrix b) {
		return solve(a, b, JeometryFactory.createMatrix(b.getRowsCount(), 1));
	}

	@Override
	public Matrix solve(Matrix a, Matrix b, Matrix x) {
		
		if ((a != null) && (b != null) && (x != null)){
			if ((a.getRowsCount() == b.getRowsCount()) && (a.getRowsCount() == x.getRowsCount())) {
				
				int rows = a.getRowsCount();
		        int columns = a.getColumnsCount();

		        // build augmented matrix
		        Matrix augmented = JeometryFactory.createMatrix(rows, columns+1);

		        for (int i = 0; i < rows; i++) {
		        	for (int j = 0; j < columns; j++) {
		            	augmented.setValue(i, j, a.getValue(i, j));
		        	}
		        }
		            
		        for (int i = 0; i < rows; i++) {
		        	augmented.setValue(i, columns, b.getValue(i, 0));
		        }

		        // Forward elimination
		        forwardElimination(augmented, rows, columns);

		        if (primal(augmented, rows, columns, x)) {
		        	return x;
		        } else {
		        	return null;
		        }
 
			} else {
				throw new IllegalArgumentException("Rows count for A ("+a.getRowsCount()+"), B ("+b.getRowsCount()+") and X ("+x.getRowsCount()+") differ.");
			}
		}

		return null;
	}

	@Override
	public Vector solve(Matrix a, Vector b) {
		return solve(a, b, JeometryFactory.createVector(b.getDimension()));
	}

	@Override
	public Vector solve(Matrix a, Vector b, Vector x) {
		if ((a != null) && (b != null) && (x != null)){
			if ((a.getRowsCount() == b.getDimension()) && (a.getRowsCount() == x.getDimension())) {
				
				int rows = a.getRowsCount();
		        int columns = a.getColumnsCount();

		        // build augmented matrix
		        Matrix augmented = JeometryFactory.createMatrix(rows, columns+1);

		        for (int i = 0; i < rows; i++) {
		        	for (int j = 0; j < columns; j++) {
		            	augmented.setValue(i, j, a.getValue(i, j));
		        	}
		        }
		            
		        for (int i = 0; i < rows; i++) {
		        	augmented.setValue(i, columns, b.getVectorComponent(i));
		        }

		        // Forward elimination
		        forwardElimination(augmented, rows, columns);
		        
		        if (primal(augmented, rows, columns, x)) {
		        	return x;
		        } else {
		        	return null;
		        }
 
			} else {
				throw new IllegalArgumentException("Rows count for A ("+a.getRowsCount()+"), B ("+b.getDimension()+") and X ("+x.getDimension()+") differ.");
			}
		}

		return null;
	} 	
}
