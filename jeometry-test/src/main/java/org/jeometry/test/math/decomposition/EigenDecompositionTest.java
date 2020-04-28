package org.jeometry.test.math.decomposition;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.decomposition.EigenDecomposition;
import org.jeometry.test.math.MathTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * A test suite dedicated to the {@link EigenDecomposition}.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * This test class will test the Eigen decomposition that is given by {@link JeometryFactory#createEigenDecomposition(Matrix)}. 
 * To test a specific implementation you can create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * &nbsp;&nbsp;GeometryFactory.setMathBuilder([the math builder]);<br>
 * <br>
 * &nbsp;&nbsp;decompositionClass = [the decomposition to test];<br>
 * }<br><br>
 * </code>
 * If the decomposition that is provided by {@link JeometryFactory#createEigenDecomposition(Matrix)} is not from the same class as <code>decompositionClass</code>, tests will fail. 
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class EigenDecompositionTest {

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
		fail("method public static void initClass() has to be set up with @BeforeClass annotation");
	}
	
	/**
	 * A test dedicated to the {@link EigenDecomposition}.
	 */
	@Test
	public void eigenDecompositionTest() {
		Matrix k = JeometryFactory.createMatrix(MathTestData.DECOMPOSITION_EIGEN_INPUT);

		EigenDecomposition decomposition = JeometryFactory.createEigenDecomposition(k);
		
		assertNotNull(decomposition, "Decomposition is null");
		
		if (decompositionClass != null) {
			assertEquals(decompositionClass, decomposition.getClass(), "Expected decomposition class "+decompositionClass.getSimpleName()+" but got "+decomposition.getClass().getSimpleName());
		}
		
		Matrix v = decomposition.getV();

		assertNotNull(v, "V is null");
		
		assertEquals(MathTestData.DECOMPOSITION_EIGEN_V.length, v.getRowsCount(), "V size error, expected ("+MathTestData.DECOMPOSITION_EIGEN_V.length+", "+MathTestData.DECOMPOSITION_EIGEN_V[0].length+") but got ("+v.getRowsCount()+", "+v.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_EIGEN_V[0].length, v.getColumnsCount(), "V size error, expected ("+MathTestData.DECOMPOSITION_EIGEN_V.length+", "+MathTestData.DECOMPOSITION_EIGEN_V[0].length+") but got ("+v.getRowsCount()+", "+v.getColumnsCount()+")");
		
		for(int row = 0; row < v.getRowsCount(); row++) {
			for(int col = 0; col < v.getColumnsCount(); col++) {
				assertEquals(MathTestData.DECOMPOSITION_EIGEN_V[row][col], v.getValue(row, col), EPSILON, "Bad value ("+row+", "+col+")");
			}
		}
		
		Matrix d = decomposition.getD();
		
        assertNotNull(d, "D is null");
		
		assertEquals(MathTestData.DECOMPOSITION_EIGEN_D.length, d.getRowsCount(), "D size error, expected ("+MathTestData.DECOMPOSITION_EIGEN_D.length+", "+MathTestData.DECOMPOSITION_EIGEN_D[0].length+") but got ("+d.getRowsCount()+", "+d.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_EIGEN_D[0].length, d.getColumnsCount(), "D size error, expected ("+MathTestData.DECOMPOSITION_EIGEN_D.length+", "+MathTestData.DECOMPOSITION_EIGEN_D[0].length+") but got ("+d.getRowsCount()+", "+d.getColumnsCount()+")");
		
		for(int row = 0; row < d.getRowsCount(); row++) {
			for(int col = 0; col < d.getColumnsCount(); col++) {
				assertEquals(d.getValue(row, col), MathTestData.DECOMPOSITION_EIGEN_D[row][col], EPSILON, "Bad value ("+row+", "+col+")");
			}
		}
		
		// Check A = VDV-1
		Matrix vdv = v.multiply(d).multiply(v.invert());
		
		assertEquals(k.getRowsCount(), vdv.getRowsCount(), "PK size error, expected ("+k.getRowsCount()+", "+k.getColumnsCount()+") but got ("+vdv.getRowsCount()+", "+vdv.getColumnsCount()+")");
		assertEquals(k.getColumnsCount(), vdv.getColumnsCount(), "PK size error, expected ("+k.getRowsCount()+", "+k.getColumnsCount()+") but got ("+vdv.getRowsCount()+", "+vdv.getColumnsCount()+")");
	
		for(int row = 0; row < k.getRowsCount(); row++) {
			for(int col = 0; col < k.getColumnsCount(); col++) {
				assertEquals(k.getValue(row, col), vdv.getValue(row, col), EPSILON, "Bad value ("+row+", "+col+")");
			}
		}
	}
}
