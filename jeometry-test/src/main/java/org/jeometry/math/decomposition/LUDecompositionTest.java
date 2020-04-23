package org.jeometry.math.decomposition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.MathTestData;
import org.jeometry.math.solver.ResolvableTest;
import org.junit.BeforeClass;
import org.junit.Test;


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
	 * Initialize the test static context.
	 */
	@BeforeClass
	public static void initClass() {
		fail("method public static void init() has to be set up with @BeforeClass annotation");
	}

	/**
	 * A test dedicated to the {@link LUDecomposition}.
	 */
	@Test
	public void luDecompositionTest() {
		Matrix k = JeometryFactory.createMatrix(MathTestData.DECOMPOSITION_LU_INPUT);

		LUDecomposition decomposition = JeometryFactory.createLUDecomposition(k);

		assertNotNull("Decomposition is null", decomposition);

		if (decompositionClass != null) {
			assertEquals("Expected decomposition class "+decompositionClass.getSimpleName()+" but got "+decomposition.getClass().getSimpleName(), decompositionClass, decomposition.getClass());
		}

		Matrix l = decomposition.getLower();

		assertNotNull("L is null", l);

		assertEquals("L size error, expected ("+MathTestData.DECOMPOSITION_LU_L.length+", "+MathTestData.DECOMPOSITION_LU_L[0].length+") but got ("+l.getRowsCount()+", "+l.getColumnsCount()+")", MathTestData.DECOMPOSITION_LU_L.length, l.getRowsCount());
		assertEquals("L size error, expected ("+MathTestData.DECOMPOSITION_LU_L.length+", "+MathTestData.DECOMPOSITION_LU_L[0].length+") but got ("+l.getRowsCount()+", "+l.getColumnsCount()+")", MathTestData.DECOMPOSITION_LU_L[0].length, l.getColumnsCount());

		for(int row = 0; row < l.getRowsCount(); row++) {
			for(int col = 0; col < l.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", l.getValue(row, col), MathTestData.DECOMPOSITION_LU_L[row][col], EPSILON);
			}
		}

		Matrix u = decomposition.getUpper();

		assertNotNull("U is null", l);

		assertEquals("U size error, expected ("+MathTestData.DECOMPOSITION_LU_U.length+", "+MathTestData.DECOMPOSITION_LU_U[0].length+") but got ("+u.getRowsCount()+", "+u.getColumnsCount()+")", MathTestData.DECOMPOSITION_LU_U.length, u.getRowsCount());
		assertEquals("U size error, expected ("+MathTestData.DECOMPOSITION_LU_U.length+", "+MathTestData.DECOMPOSITION_LU_U[0].length+") but got ("+u.getRowsCount()+", "+u.getColumnsCount()+")", MathTestData.DECOMPOSITION_LU_U[0].length, u.getColumnsCount());

		for(int row = 0; row < u.getRowsCount(); row++) {
			for(int col = 0; col < u.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", u.getValue(row, col), MathTestData.DECOMPOSITION_LU_U[row][col], EPSILON);
			}
		}


		Matrix p = decomposition.getPivot();

		assertNotNull("P is null", l);

		assertEquals("P size error, expected ("+MathTestData.DECOMPOSITION_LU_P.length+", "+MathTestData.DECOMPOSITION_LU_P[0].length+") but got ("+p.getRowsCount()+", "+p.getColumnsCount()+")", MathTestData.DECOMPOSITION_LU_P.length, p.getRowsCount());
		assertEquals("P size error, expected ("+MathTestData.DECOMPOSITION_LU_P.length+", "+MathTestData.DECOMPOSITION_LU_P[0].length+") but got ("+p.getRowsCount()+", "+p.getColumnsCount()+")", MathTestData.DECOMPOSITION_LU_P[0].length, p.getColumnsCount());

		for(int row = 0; row < p.getRowsCount(); row++) {
			for(int col = 0; col < p.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", p.getValue(row, col), MathTestData.DECOMPOSITION_LU_P[row][col], EPSILON);
			}
		}

		// Check PK = LU
		Matrix pk = p.multiply(k);

		Matrix lu = l.multiply(u);

		assertEquals("PK size error, expected ("+pk.getRowsCount()+", "+pk.getColumnsCount()+") but got ("+lu.getRowsCount()+", "+lu.getColumnsCount()+")", pk.getRowsCount(), lu.getRowsCount());
		assertEquals("PK size error, expected ("+pk.getRowsCount()+", "+pk.getColumnsCount()+") but got ("+lu.getRowsCount()+", "+lu.getColumnsCount()+")", pk.getColumnsCount(), lu.getColumnsCount());

		for(int row = 0; row < p.getRowsCount(); row++) {
			for(int col = 0; col < p.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", pk.getValue(row, col), lu.getValue(row, col), EPSILON);
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
