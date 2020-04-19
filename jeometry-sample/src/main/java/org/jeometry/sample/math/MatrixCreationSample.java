package org.jeometry.sample.math;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;

/**
 * A matrix creation sample
 *
 */
public class MatrixCreationSample {

	/**
	 * The main method
	 * @param args the program arguments
	 */
	public static void main(String[] args) {
	
		// 1. Matrix creation using dimensions.
		Matrix a = JeometryFactory.createMatrix(3, 3);
		
		// 2. Matrix creation using 2D array
		double[][] bdata = new double[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		
		Matrix b = JeometryFactory.createMatrix(bdata);
		
		// 3. Matrix creation by copy 
		Matrix c = JeometryFactory.createMatrix(b);
		
		// 4. Matrix creation using a linear array, row major and column major
		double[] ddata = new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
	
		Matrix d = JeometryFactory.createMatrix(3, 3, ddata, Matrix.ROW_MAJOR);
		
		Matrix e = JeometryFactory.createMatrix(3, 3, ddata, Matrix.COLUMN_MAJOR);
		
		System.out.println("A: ");
		Jeometry.print(a, System.out, "");
		System.out.println();
		
		System.out.println("B: ");
		Jeometry.print(b, System.out, "");
		System.out.println();
		
		System.out.println("C: ");
		Jeometry.print(c, System.out, "");
		System.out.println();
		
		System.out.println("D: ");
		Jeometry.print(d, System.out, "");
		System.out.println();
		
		System.out.println("E: ");
		Jeometry.print(e, System.out, "");
		System.out.println();
		
	}
	
}
