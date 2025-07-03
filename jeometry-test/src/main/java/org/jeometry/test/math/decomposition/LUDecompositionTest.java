package org.jeometry.test.math.decomposition;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.decomposition.LUDecomposition;
import org.jeometry.test.math.MathTestData;
import org.jeometry.test.math.solver.ResolvableTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



/**
 * A test suite dedicated to the {@link LUDecomposition}.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * This test class will test the LU decomposition that is given by {@link JeometryFactory#createLUDecomposition(Matrix)}. 
 * To test a specific implementation you can create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * &nbsp;&nbsp;GeometryFactory.setMathBuilder([the math builder]);<br>
 * <br>
 * &nbsp;&nbsp;decompositionClass = [the decomposition to test];<br>
 * }<br><br>
 * </code>
 * If the decomposition that is provided by {@link JeometryFactory#createLUDecomposition(Matrix)} is not from the same class as <code>decompositionClass</code>, tests will fail. 
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class LUDecompositionTest {

	/**
	 * Epsilon for numeric comparison.
	 */
	public static double EPSILON = 0.0000001d;

	/**
	 * The class of the decomposition
	 */
	protected static Class<?> decompositionClass = null;

	/**
	 * A test dedicated to the {@link LUDecomposition}.
	 */
	@Test
	public void luDecompositionTest() {
		Matrix k = JeometryFactory.createMatrix(MathTestData.DECOMPOSITION_LU_INPUT);

		LUDecomposition decomposition = JeometryFactory.createLUDecomposition(k);

		assertNotNull(decomposition, "Decomposition is null");

		if (decompositionClass != null) {
			assertEquals(decompositionClass, decomposition.getClass(), "Expected decomposition class "+decompositionClass.getSimpleName()+" but got "+decomposition.getClass().getSimpleName());
		}

		Matrix l = decomposition.getLower();

		assertNotNull(l, "L is null");

		assertEquals(MathTestData.DECOMPOSITION_LU_L.length, l.getRowsCount(), "L size error, expected ("+MathTestData.DECOMPOSITION_LU_L.length+", "+MathTestData.DECOMPOSITION_LU_L[0].length+") but got ("+l.getRowsCount()+", "+l.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_LU_L[0].length, l.getColumnsCount(), "L size error, expected ("+MathTestData.DECOMPOSITION_LU_L.length+", "+MathTestData.DECOMPOSITION_LU_L[0].length+") but got ("+l.getRowsCount()+", "+l.getColumnsCount()+")");

		for(int row = 0; row < l.getRowsCount(); row++) {
			for(int col = 0; col < l.getColumnsCount(); col++) {
				assertEquals(MathTestData.DECOMPOSITION_LU_L[row][col], l.getValue(row, col), EPSILON, "Bad value ("+row+", "+col+")");
			}
		}

		Matrix u = decomposition.getUpper();

		assertNotNull(l, "U is null");

		assertEquals(MathTestData.DECOMPOSITION_LU_U.length, u.getRowsCount(), "U size error, expected ("+MathTestData.DECOMPOSITION_LU_U.length+", "+MathTestData.DECOMPOSITION_LU_U[0].length+") but got ("+u.getRowsCount()+", "+u.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_LU_U[0].length, u.getColumnsCount(), "U size error, expected ("+MathTestData.DECOMPOSITION_LU_U.length+", "+MathTestData.DECOMPOSITION_LU_U[0].length+") but got ("+u.getRowsCount()+", "+u.getColumnsCount()+")");

		for(int row = 0; row < u.getRowsCount(); row++) {
			for(int col = 0; col < u.getColumnsCount(); col++) {
				assertEquals(MathTestData.DECOMPOSITION_LU_U[row][col], u.getValue(row, col), EPSILON, "Bad value ("+row+", "+col+")");
			}
		}


		Matrix p = decomposition.getPivot();

		assertNotNull(l, "P is null");

		assertEquals(MathTestData.DECOMPOSITION_LU_P.length, p.getRowsCount(), "P size error, expected ("+MathTestData.DECOMPOSITION_LU_P.length+", "+MathTestData.DECOMPOSITION_LU_P[0].length+") but got ("+p.getRowsCount()+", "+p.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_LU_P[0].length, p.getColumnsCount(), "P size error, expected ("+MathTestData.DECOMPOSITION_LU_P.length+", "+MathTestData.DECOMPOSITION_LU_P[0].length+") but got ("+p.getRowsCount()+", "+p.getColumnsCount()+")");

		for(int row = 0; row < p.getRowsCount(); row++) {
			for(int col = 0; col < p.getColumnsCount(); col++) {
				assertEquals(p.getValue(row, col), MathTestData.DECOMPOSITION_LU_P[row][col], EPSILON, "Bad value ("+row+", "+col+")");
			}
		}

		// Check PK = LU
		Matrix pk = p.multiply(k);

		Matrix lu = l.multiply(u);

		assertEquals(pk.getRowsCount(), lu.getRowsCount(), "PK size error, expected ("+pk.getRowsCount()+", "+pk.getColumnsCount()+") but got ("+lu.getRowsCount()+", "+lu.getColumnsCount()+")");
		assertEquals(pk.getColumnsCount(), lu.getColumnsCount(), "PK size error, expected ("+pk.getRowsCount()+", "+pk.getColumnsCount()+") but got ("+lu.getRowsCount()+", "+lu.getColumnsCount()+")");

		for(int row = 0; row < p.getRowsCount(); row++) {
			for(int col = 0; col < p.getColumnsCount(); col++) {
				assertEquals(pk.getValue(row, col), lu.getValue(row, col), EPSILON, "Bad value ("+row+", "+col+")");
			}
		}

		// Test solve
		double[][] solveInput  = MathTestData.DECOMPOSITION_LU_INPUT;
		double[] solveConstant = MathTestData.DECOMPOSITION_LU_SOLVE_CONSTANT;
		double[] solveResult   = MathTestData.DECOMPOSITION_LU_SOLVE_RESULT;

		ResolvableTest.testSolve(JeometryFactory.createMatrix(solveInput),
				JeometryFactory.createMatrix(solveConstant.length, 1, solveConstant, Matrix.ROW_MAJOR),
				JeometryFactory.createMatrix(solveResult.length, 1, solveResult, Matrix.ROW_MAJOR),
				decomposition,
				EPSILON);

		ResolvableTest.testSolve(JeometryFactory.createMatrix(solveInput),
				JeometryFactory.createVector(solveConstant),
				JeometryFactory.createVector(solveResult),
				decomposition,
				EPSILON);

		ResolvableTest.testSolveResult(JeometryFactory.createMatrix(solveInput),
				JeometryFactory.createMatrix(solveConstant.length, 1, solveConstant, Matrix.ROW_MAJOR),
				JeometryFactory.createMatrix(solveResult.length, 1, solveResult, Matrix.ROW_MAJOR),
				decomposition,
				EPSILON);

		ResolvableTest.testSolveResult(JeometryFactory.createMatrix(solveInput),
				JeometryFactory.createVector(solveConstant),
				JeometryFactory.createVector(solveResult),
				decomposition,
				EPSILON);
	}
}
