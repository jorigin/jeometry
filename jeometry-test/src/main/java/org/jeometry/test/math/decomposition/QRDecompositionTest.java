package org.jeometry.test.math.decomposition;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.decomposition.QRDecomposition;
import org.jeometry.test.math.MathTestData;
import org.jeometry.test.math.solver.ResolvableTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * A test suite dedicated to the {@link QRDecomposition}.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * This test class will test the QR decomposition that is given by {@link JeometryFactory#createQRDecomposition(Matrix)}. 
 * To test a specific implementation you can create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * &nbsp;&nbsp;GeometryFactory.setMathBuilder([the math builder]);<br>
 * <br>
 * &nbsp;&nbsp;decompositionClass = [the decomposition to test];<br>
 * }<br><br>
 * </code>
 * If the decomposition that is provided by {@link JeometryFactory#createQRDecomposition(Matrix)} is not from the same class as <code>decompositionClass</code>, tests will fail. 
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class QRDecompositionTest {
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
	@BeforeAll
	public static void initClass() {
		fail("method public static void init() has to be set up with @BeforeClass annotation");
	}

	/**
	 * A test dedicated to the {@link QRDecomposition}.
	 */
	@Test
	public void qrDecompositionTest() {
		Matrix input = JeometryFactory.createMatrix(MathTestData.DECOMPOSITION_QR_INPUT);

		QRDecomposition decomposition = JeometryFactory.createQRDecomposition(input);

		assertNotNull(decomposition, "Decomposition is null");

		if (decompositionClass != null) {
			assertEquals(decompositionClass, decomposition.getClass(), "Expected decomposition class "+decompositionClass.getSimpleName()+" but got "+decomposition.getClass().getSimpleName());
		}

		Matrix q = decomposition.getQ();

		assertNotNull(q, "Q is null");

		assertEquals(MathTestData.DECOMPOSITION_QR_Q.length, q.getRowsCount(), "Q size error, expected ("+MathTestData.DECOMPOSITION_QR_Q.length+", "+MathTestData.DECOMPOSITION_QR_Q[0].length+") but got ("+q.getRowsCount()+", "+q.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_QR_Q[0].length, q.getColumnsCount(), "Q size error, expected ("+MathTestData.DECOMPOSITION_QR_Q.length+", "+MathTestData.DECOMPOSITION_QR_Q[0].length+") but got ("+q.getRowsCount()+", "+q.getColumnsCount()+")");
		/*		
		for(int row = 0; row < q.getRowsCount(); row++) {
			for(int col = 0; col < q.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", MatrixTestData.DECOMPOSITION_QR_Q[row][col], q.getValue(row, col), EPSILON);
			}
		}

		 */
		Matrix r = decomposition.getR();


		assertNotNull(r, "R is null");

		assertEquals(MathTestData.DECOMPOSITION_QR_R.length, r.getRowsCount(), "R size error, expected ("+MathTestData.DECOMPOSITION_QR_R.length+", "+MathTestData.DECOMPOSITION_QR_R[0].length+") but got ("+r.getRowsCount()+", "+r.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_QR_R[0].length, r.getColumnsCount(), "R size error, expected ("+MathTestData.DECOMPOSITION_QR_R.length+", "+MathTestData.DECOMPOSITION_QR_R[0].length+") but got ("+r.getRowsCount()+", "+r.getColumnsCount()+")");
		/*		
		for(int row = 0; row < r.getRowsCount(); row++) {
			for(int col = 0; col < r.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", MatrixTestData.DECOMPOSITION_QR_R[row][col], r.getValue(row, col), EPSILON);
			}
		}
		 */		

		// Test composition
		Matrix composed = q.multiply(r);

		assertNotNull(composed, "Composed is null");

		assertEquals(MathTestData.DECOMPOSITION_QR_INPUT.length, composed.getRowsCount(), "Composed size error, expected ("+MathTestData.DECOMPOSITION_QR_INPUT.length+", "+MathTestData.DECOMPOSITION_QR_INPUT[0].length+") but got ("+composed.getRowsCount()+", "+composed.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_QR_INPUT[0].length, composed.getColumnsCount(), "Composed size error, expected ("+MathTestData.DECOMPOSITION_QR_INPUT.length+", "+MathTestData.DECOMPOSITION_QR_INPUT[0].length+") but got ("+composed.getRowsCount()+", "+composed.getColumnsCount()+")");

		for(int row = 0; row < composed.getRowsCount(); row++) {
			for(int col = 0; col < composed.getColumnsCount(); col++) {
				assertEquals(MathTestData.DECOMPOSITION_QR_INPUT[row][col], composed.getValue(row, col), EPSILON, "Bad value ("+row+", "+col+")");
			}
		}

		// Test solve
		double[][] solveInput  = MathTestData.DECOMPOSITION_QR_INPUT;
		double[] solveConstant = MathTestData.DECOMPOSITION_QR_SOLVE_CONSTANT;
		double[] solveResult   = MathTestData.DECOMPOSITION_QR_SOLVE_RESULT;
		
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
