package org.jeometry.sample;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Quaternion;
import org.jeometry.math.Vector;

/**
 * A first sample program.
 *
 */
public class FirstSample {

	/**
	 * The main method.
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		
		// Create a 3x3 matrix
		Matrix m = JeometryFactory.createMatrix(3, 3);
		
		// Create a vector of dimension 3
		Vector v = JeometryFactory.createVector(3);
		
		// Create a quaternion
		Quaternion q = JeometryFactory.createQuaternion();
		
		System.out.println("Matrix m: ");
		Jeometry.print(m, System.out, "  ");
		System.out.println();
		
		System.out.println("Vector v: "+v.toString());
		
		System.out.println("Quaternion q: "+q.toString());
		
	}
}
