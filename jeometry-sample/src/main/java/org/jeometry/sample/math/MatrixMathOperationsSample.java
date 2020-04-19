package org.jeometry.sample.math;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;

/**
 * Matrix mathematical operations sample.
 */
public class MatrixMathOperationsSample {

	/**
	 * The main method.
	 * @param args the program arguments
	 */
	public static void main(String[] args) {
				
		Matrix a = JeometryFactory.createMatrix(new double[][] {
			{ 3.45692302, -1.36528596,    1.3685204, -459.0254136},
			{22.65974148,  0.00698741,    8.1269853,   23.5410397},
			{12.87456921, -3.12586921,   11.3685214,  -33.2154242},
			{36.25697942, -3.01127952, 6984.3652127,   12.6985412}
		});
				
		Matrix b = JeometryFactory.createMatrix(new double[][] {{6, 5, 4, 1}, {9, 8, 7, 2}, {3, 2, 1, 5}, {10, 11, 12, 4}});
		
		// Add the scalar 5.0 to all matrix cells
		Matrix ap = a.add(5.0);
		
		// Create a new matrix where each cell is the addition of the corresponding
		// cells from matrix a and b
		Matrix apb = a.add(b);
		
		// Subtract the scalar 4.0 from all matrix cells
		Matrix am = a.subtract(4.0);
		
		// Create a new matrix where each cell is the subtraction of the corresponding
		// cells from matrix a and b
		Matrix amb = a.subtract(b);
		
		// Multiply all matrix cells by the scalar 2.0
		Matrix ax = a.multiply(2.0);
		
		// The standard matrix product a x b
		Matrix axb = a.multiply(amb);
		
		// Transpose the matrix
		Matrix t = a.transpose();
		
		// Invert the matrix
		Matrix i = a.invert();
		
		System.out.println("Matrix A: ");
		Jeometry.print(a, System.out, "  ");
		System.out.println("");
		
		System.out.println("Matrix B: ");
		Jeometry.print(b, System.out, "  ");
		System.out.println("");
		
		System.out.println("A + 5.0: ");
		Jeometry.print(ap, System.out, "  ");
		System.out.println("");
		
		System.out.println("A + B: ");
		Jeometry.print(apb, System.out, "  ");
		System.out.println("");
		
		System.out.println("A - 4.0: ");
		Jeometry.print(am, System.out, "  ");
		System.out.println("");
		
		System.out.println("A - B: ");
		Jeometry.print(amb, System.out, "  ");
		System.out.println("");
		
		System.out.println("A x 2.0: ");
		Jeometry.print(ax, System.out, "  ");
		System.out.println("");
		
		System.out.println("A x B: ");
		Jeometry.print(axb, System.out, "  ");
		System.out.println("");
		
		System.out.println("Transpose of A: ");
		Jeometry.print(t, System.out, "  ");
		System.out.println("");
		
		System.out.println("Inverse of A: ");
		Jeometry.print(i, System.out, "  ");
		System.out.println("");
	}
}
