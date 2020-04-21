package org.jeometry.math.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

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
		
		assertNotNull("Linear system (A) is null", a);
		assertNotNull("Constants (B) is null", b);
		assertNotNull("Expected result (Xe) is null", b);
		assertNotNull("Solvent is null", b);
		
        Matrix x = solvent.solve(b);
		
        assertNotNull("Result is null", x);
		
		assertEquals("Result size error, expected ("+result.getRowsCount()+", "+result.getColumnsCount()+") but got ("+x.getRowsCount()+", "+x.getColumnsCount()+")", result.getRowsCount(), x.getRowsCount());
		assertEquals("Result size error, expected ("+result.getRowsCount()+", "+result.getColumnsCount()+") but got ("+x.getRowsCount()+", "+x.getColumnsCount()+")", result.getColumnsCount(), x.getColumnsCount());
		
		for(int row = 0; row < x.getRowsCount(); row++) {
			for(int col = 0; col < x.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", result.getValue(row, col), x.getValue(row, col), epsilon);
			}
		}
		
		Matrix bcomputed = a.multiply(x);
		
        assertNotNull("Computed constant is null", x);
		
		assertEquals("Computed constant size error, expected ("+b.getRowsCount()+", "+bcomputed.getColumnsCount()+") but got ("+bcomputed.getRowsCount()+", "+bcomputed.getColumnsCount()+")", b.getRowsCount(), bcomputed.getRowsCount());
		assertEquals("Computed constant size error, expected ("+b.getRowsCount()+", "+bcomputed.getColumnsCount()+") but got ("+bcomputed.getRowsCount()+", "+bcomputed.getColumnsCount()+")", b.getColumnsCount(), bcomputed.getColumnsCount());

		for(int row = 0; row < b.getRowsCount(); row++) {
			for(int col = 0; col < b.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", b.getValue(row, col), bcomputed.getValue(row, col), epsilon);
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
		
		assertNotNull("Linear system (A) is null", a);
		assertNotNull("Constants (B) is null", b);
		assertNotNull("Expected result (Xe) is null", b);
		assertNotNull("Solvent is null", b);
		
		Matrix x = JeometryFactory.createMatrix(a.getColumnsCount(), 1);
		
        Matrix returned = solvent.solve(b, x);
		
        assertNotNull("Result is null", returned);
		
        assertSame("Returned reference differs from given output.", x, returned);
        
		assertEquals("Result size error, expected ("+result.getRowsCount()+", "+result.getColumnsCount()+") but got ("+x.getRowsCount()+", "+x.getColumnsCount()+")", result.getRowsCount(), x.getRowsCount());
		assertEquals("Result size error, expected ("+result.getRowsCount()+", "+result.getColumnsCount()+") but got ("+x.getRowsCount()+", "+x.getColumnsCount()+")", result.getColumnsCount(), x.getColumnsCount());
		
		for(int row = 0; row < x.getRowsCount(); row++) {
			for(int col = 0; col < x.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", result.getValue(row, col), x.getValue(row, col), epsilon);
			}
		}
		
		Matrix bcomputed = a.multiply(x);
		
        assertNotNull("Computed constant is null", x);
		
		assertEquals("Computed constant size error, expected ("+b.getRowsCount()+", "+bcomputed.getColumnsCount()+") but got ("+bcomputed.getRowsCount()+", "+bcomputed.getColumnsCount()+")", b.getRowsCount(), bcomputed.getRowsCount());
		assertEquals("Computed constant size error, expected ("+b.getRowsCount()+", "+bcomputed.getColumnsCount()+") but got ("+bcomputed.getRowsCount()+", "+bcomputed.getColumnsCount()+")", b.getColumnsCount(), bcomputed.getColumnsCount());

		for(int row = 0; row < b.getRowsCount(); row++) {
			for(int col = 0; col < b.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", b.getValue(row, col), bcomputed.getValue(row, col), epsilon);
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
		
		assertNotNull("Linear system (A) is null", a);
		assertNotNull("Constants (B) is null", b);
		assertNotNull("Expected result (Xe) is null", b);
		assertNotNull("Solvent is null", b);
		
        Vector x = solvent.solve(b);
		
        assertNotNull("Result is null", x);
		
		assertEquals("Result size error, expected ("+result.getDimension()+") but got ("+x.getDimension()+")", result.getDimension(), x.getDimension());
		
		for(int dimension = 0; dimension < x.getDimension(); dimension++) {
			assertEquals("Bad value ("+dimension+")", result.getVectorComponent(dimension), x.getVectorComponent(dimension), epsilon);
		}
		
		Vector bcomputed = a.multiply(x);
				
        assertNotNull("Computed constants are null", b);
		
		assertEquals("Computed constants size error, expected ("+b.getDimension()+") but got ("+bcomputed.getDimension()+")", b.getDimension(), bcomputed.getDimension());
		
		for(int dimension = 0; dimension < b.getDimension(); dimension++) {
			assertEquals("Bad value ("+dimension+")", b.getVectorComponent(dimension), bcomputed.getVectorComponent(dimension), epsilon);
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
		
		assertNotNull("Linear system (A) is null", a);
		assertNotNull("Constants (B) is null", b);
		assertNotNull("Expected result (Xe) is null", b);
		assertNotNull("Solvent is null", b);
		
		Vector x = JeometryFactory.createVector(a.getColumnsCount());
		
        Vector returned = solvent.solve(b, x);
		
        assertNotNull("Result is null", x);
		
        assertSame("Returned reference differs from given output.", x, returned);
        
		assertEquals("Result size error, expected ("+result.getDimension()+") but got ("+x.getDimension()+")", result.getDimension(), x.getDimension());
		
		for(int dimension = 0; dimension < x.getDimension(); dimension++) {
			assertEquals("Bad value ("+dimension+")", result.getVectorComponent(dimension), x.getVectorComponent(dimension), epsilon);
		}
		
		Vector bcomputed = a.multiply(x);
				
        assertNotNull("Computed constants are null", b);
		
		assertEquals("Computed constants size error, expected ("+b.getDimension()+") but got ("+bcomputed.getDimension()+")", b.getDimension(), bcomputed.getDimension());
		
		for(int dimension = 0; dimension < b.getDimension(); dimension++) {
			assertEquals("Bad value ("+dimension+")", b.getVectorComponent(dimension), bcomputed.getVectorComponent(dimension), epsilon);
		}
	}
}
