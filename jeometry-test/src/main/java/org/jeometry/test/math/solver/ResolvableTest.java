package org.jeometry.test.math.solver;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;
import org.jeometry.math.solver.Resolvable;

/**
 * This class provides utilities for {@link Resolvable resolvable} implementations test.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.2
 */
public class ResolvableTest {
	
	/**
	 * Check that the given resolvable object can solve the given linear system <i>AX</i>&nbsp;=&nbsp;<i>B</i>.<br>
	 * This method use the {@link Resolvable#solve(Matrix)} method.
	 * @param a the <i>A</i> matrix
	 * @param b the <i>B</i> matrix
	 * @param result the expected result <i>X</i>
	 * @param solvent the solvent to use
	 * @param epsilon the numerical precision for number comparison
	 */
	public static void testSolve(Matrix a, Matrix b, Matrix result, Resolvable solvent, double epsilon) {
		
		assertNotNull(a, "Linear system (A) is null");
		assertNotNull(b, "Constants (B) is null");
		assertNotNull(b, "Expected result (Xe) is null");
		assertNotNull(b, "Solvent is null");
		
        Matrix x = solvent.solve(b);
		
        assertNotNull(x, "Result is null");
		
		assertEquals(result.getRowsCount(), x.getRowsCount(), "Result size error, expected ("+result.getRowsCount()+", "+result.getColumnsCount()+") but got ("+x.getRowsCount()+", "+x.getColumnsCount()+")");
		assertEquals(result.getColumnsCount(), x.getColumnsCount(), "Result size error, expected ("+result.getRowsCount()+", "+result.getColumnsCount()+") but got ("+x.getRowsCount()+", "+x.getColumnsCount()+")");
		
		for(int row = 0; row < x.getRowsCount(); row++) {
			for(int col = 0; col < x.getColumnsCount(); col++) {
				assertEquals(result.getValue(row, col), x.getValue(row, col), epsilon, "Bad value ("+row+", "+col+")");
			}
		}
		
		Matrix bcomputed = a.multiply(x);
		
        assertNotNull(x, "Computed constant is null");
		
		assertEquals(b.getRowsCount(), bcomputed.getRowsCount(), "Computed constant size error, expected ("+b.getRowsCount()+", "+bcomputed.getColumnsCount()+") but got ("+bcomputed.getRowsCount()+", "+bcomputed.getColumnsCount()+")");
		assertEquals(b.getColumnsCount(), bcomputed.getColumnsCount(), "Computed constant size error, expected ("+b.getRowsCount()+", "+bcomputed.getColumnsCount()+") but got ("+bcomputed.getRowsCount()+", "+bcomputed.getColumnsCount()+")");

		for(int row = 0; row < b.getRowsCount(); row++) {
			for(int col = 0; col < b.getColumnsCount(); col++) {
				assertEquals(b.getValue(row, col), bcomputed.getValue(row, col), epsilon, "Bad value ("+row+", "+col+")");
			}
		}
	}
	
	/**
	 * Check that the given resolvable object can solve the given linear system <i>AX</i>&nbsp;=&nbsp;<i>B</i>.<br>
	 * This method use the {@link Resolvable#solve(Matrix, Matrix)} method.
	 * @param a the <i>A</i> matrix
	 * @param b the <i>B</i> matrix
	 * @param result the expected result <i>X</i>
	 * @param solvent the solvent to use
	 * @param epsilon the numerical precision for number comparison
	 */
	public static void testSolveResult(Matrix a, Matrix b, Matrix result, Resolvable solvent, double epsilon) {
		
		assertNotNull(a, "Linear system (A) is null");
		assertNotNull(b, "Constants (B) is null");
		assertNotNull(b, "Expected result (Xe) is null");
		assertNotNull(b, "Solvent is null");
		
		Matrix x = JeometryFactory.createMatrix(a.getColumnsCount(), 1);
		
        Matrix returned = solvent.solve(b, x);
		
        assertNotNull(returned, "Result is null");
		
        assertSame(x, returned, "Returned reference differs from given output.");
        
		assertEquals(result.getRowsCount(), x.getRowsCount(), "Result size error, expected ("+result.getRowsCount()+", "+result.getColumnsCount()+") but got ("+x.getRowsCount()+", "+x.getColumnsCount()+")");
		assertEquals(result.getColumnsCount(), x.getColumnsCount(), "Result size error, expected ("+result.getRowsCount()+", "+result.getColumnsCount()+") but got ("+x.getRowsCount()+", "+x.getColumnsCount()+")");
		
		for(int row = 0; row < x.getRowsCount(); row++) {
			for(int col = 0; col < x.getColumnsCount(); col++) {
				assertEquals(result.getValue(row, col), x.getValue(row, col), epsilon, "Bad value ("+row+", "+col+")");
			}
		}
		
		Matrix bcomputed = a.multiply(x);
		
        assertNotNull(x, "Computed constant is null");
		
		assertEquals(b.getRowsCount(), bcomputed.getRowsCount(), "Computed constant size error, expected ("+b.getRowsCount()+", "+bcomputed.getColumnsCount()+") but got ("+bcomputed.getRowsCount()+", "+bcomputed.getColumnsCount()+")");
		assertEquals(b.getColumnsCount(), bcomputed.getColumnsCount(), "Computed constant size error, expected ("+b.getRowsCount()+", "+bcomputed.getColumnsCount()+") but got ("+bcomputed.getRowsCount()+", "+bcomputed.getColumnsCount()+")");

		for(int row = 0; row < b.getRowsCount(); row++) {
			for(int col = 0; col < b.getColumnsCount(); col++) {
				assertEquals(b.getValue(row, col), bcomputed.getValue(row, col), epsilon, "Bad value ("+row+", "+col+")");
			}
		}
	}
	
	/**
	 * Check that the given solvent can solve the given linear system <i>AX</i>&nbsp;=&nbsp;<i>B</i>.<br>
	 * This method use the {@link Resolvable#solve(Vector)} method.
	 * @param a the <i>A</i> matrix
	 * @param b the <i>B</i> vector
	 * @param result the expected result <i>X</i>
	 * @param solvent the solvent to use
	 * @param epsilon the numerical precision for number comparison
	 */
	public static void testSolve(Matrix a, Vector b, Vector result, Resolvable solvent, double epsilon) {
		
		assertNotNull(a, "Linear system (A) is null");
		assertNotNull(b, "Constants (B) is null");
		assertNotNull(b, "Expected result (Xe) is null");
		assertNotNull(b, "Solvent is null");
		
        Vector x = solvent.solve(b);
		
        assertNotNull(x, "Result is null");
		
		assertEquals(result.getDimension(), x.getDimension(), "Result size error, expected ("+result.getDimension()+") but got ("+x.getDimension()+")");
		
		for(int dimension = 0; dimension < x.getDimension(); dimension++) {
			assertEquals(result.getValue(dimension), x.getValue(dimension), epsilon, "Bad value ("+dimension+")");
		}
		
		Vector bcomputed = a.multiply(x);
				
        assertNotNull(b, "Computed constants are null");
		
		assertEquals(b.getDimension(), bcomputed.getDimension(), "Computed constants size error, expected ("+b.getDimension()+") but got ("+bcomputed.getDimension()+")");
		
		for(int dimension = 0; dimension < b.getDimension(); dimension++) {
			assertEquals(b.getValue(dimension), bcomputed.getValue(dimension), epsilon, "Bad value ("+dimension+")");
		}
	}
	
	/**
	 * Check that the given solvent can solve the given linear system <i>AX</i>&nbsp;=&nbsp;<i>B</i>.<br>
	 * This method use the {@link Resolvable#solve(Vector, Vector)} method.
	 * @param a the <i>A</i> matrix
	 * @param b the <i>B</i> vector
	 * @param result the expected result <i>X</i>
	 * @param solvent the solvent to use
	 * @param epsilon the numerical precision for number comparison
	 */
	public static void testSolveResult(Matrix a, Vector b, Vector result, Resolvable solvent, double epsilon) {
		
		assertNotNull(a, "Linear system (A) is null");
		assertNotNull(b, "Constants (B) is null");
		assertNotNull(b, "Expected result (Xe) is null");
		assertNotNull(b, "Solvent is null");
		
		Vector x = JeometryFactory.createVector(a.getColumnsCount());
		
        Vector returned = solvent.solve(b, x);
		
        assertNotNull(x, "Result is null");
		
        assertSame(x, returned, "Returned reference differs from given output.");
        
		assertEquals(result.getDimension(), x.getDimension(), "Result size error, expected ("+result.getDimension()+") but got ("+x.getDimension()+")");
		
		for(int dimension = 0; dimension < x.getDimension(); dimension++) {
			assertEquals(result.getValue(dimension), x.getValue(dimension), epsilon, "Bad value ("+dimension+")");
		}
		
		Vector bcomputed = a.multiply(x);
				
        assertNotNull(b, "Computed constants are null");
		
		assertEquals(b.getDimension(), bcomputed.getDimension(), "Computed constants size error, expected ("+b.getDimension()+") but got ("+bcomputed.getDimension()+")");
		
		for(int dimension = 0; dimension < b.getDimension(); dimension++) {
			assertEquals(b.getValue(dimension), bcomputed.getValue(dimension), epsilon, "Bad value ("+dimension+")");
		}
	}
}
