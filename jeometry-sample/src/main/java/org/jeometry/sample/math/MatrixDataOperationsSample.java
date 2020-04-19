package org.jeometry.sample.math;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;

/**
 * Matrix data operations samples.
 *
 */
public class MatrixDataOperationsSample {

	/**
	 * The main method.
	 * @param args the program arguments
	 */
	public static void main(String[] args) {
		
		
		//Matrix creation using 2D array
		double[][] bdata = new double[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
				
		Matrix a = JeometryFactory.createMatrix(bdata);
		
		// Matrix creation using dimension 
		Matrix b = JeometryFactory.createMatrix(4, 3);
		
		// Display matrix dimension
		System.out.println("A["+a.getRowsCount()+"x"+a.getColumnsCount()+"]");
		
		// Get matrix value
		System.out.println("A[2x3] = "+a.getValue(2, 3));

		// Set matrix value
		for(int row = 0; row < b.getRowsCount(); row++) {
			for(int col = 0; col < b.getColumnsCount(); col++) {
				b.setValue(row, col, row*col);
			}
		}
	}
}
