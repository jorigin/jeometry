package org.jeometry.test.math.decomposition;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.decomposition.SVDDecomposition;
import org.jeometry.test.math.MathTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * A test suite dedicated to the {@link SVDDecomposition}.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * This test class will test the SVD decomposition that is given by {@link JeometryFactory#createSVDDecomposition(Matrix)}. 
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
public class SVDDecompositionTest {

	/**
	 * Epsilon for numeric comparison.
	 */
	public static double EPSILON = 0.00000000001d;
	
	/**
	 * The class of the decomposition
	 */
	protected static Class<?> decompositionClass = null;
	
	/**
	 * A test dedicated to the {@link SVDDecomposition}.
	 */
	@Test
	public void svdDecompositionTest() {
		
		Matrix k = JeometryFactory.createMatrix(MathTestData.DECOMPOSITION_SVD_INPUT);

		SVDDecomposition decomposition = JeometryFactory.createSVDDecomposition(k);
		
        assertNotNull(decomposition, "Decomposition is null");
		
		if (decompositionClass != null) {
			assertEquals(decompositionClass, decomposition.getClass(), "Expected decomposition class "+decompositionClass.getSimpleName()+" but got "+decomposition.getClass().getSimpleName());
		}
		
		Matrix u = decomposition.getU();
		
		assertNotNull(u, "U is null");
		assertEquals(MathTestData.DECOMPOSITION_SVD_V.length, u.getRowsCount(), "U size error, expected ("+MathTestData.DECOMPOSITION_SVD_U.length+", "+MathTestData.DECOMPOSITION_SVD_U[0].length+") but got ("+u.getRowsCount()+", "+u.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_SVD_V[0].length, u.getColumnsCount(), "U size error, expected ("+MathTestData.DECOMPOSITION_SVD_U.length+", "+MathTestData.DECOMPOSITION_SVD_U[0].length+") but got ("+u.getRowsCount()+", "+u.getColumnsCount()+")");
/*		
		for(int row = 0; row < u.getRowsCount(); row++) {
			for(int col = 0; col < u.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", u.getValue(row, col), MatrixTestData.DECOMPOSITION_SVD_U[row][col], EPSILON);
			}
		}
*/		
		
		Matrix s = decomposition.getS();

        assertNotNull(s, "S is null");
		
		assertEquals(MathTestData.DECOMPOSITION_SVD_S.length, s.getRowsCount(), "S size error, expected ("+MathTestData.DECOMPOSITION_SVD_S.length+", "+MathTestData.DECOMPOSITION_SVD_S[0].length+") but got ("+s.getRowsCount()+", "+s.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_SVD_S[0].length, s.getColumnsCount(), "S size error, expected ("+MathTestData.DECOMPOSITION_SVD_S.length+", "+MathTestData.DECOMPOSITION_SVD_S[0].length+") but got ("+s.getRowsCount()+", "+s.getColumnsCount()+")");
		
		for(int row = 0; row < s.getRowsCount(); row++) {
			for(int col = 0; col < s.getColumnsCount(); col++) {
				assertEquals(s.getValue(row, col), MathTestData.DECOMPOSITION_SVD_S[row][col], EPSILON, "Bad value ("+row+", "+col+")");
			}
		}
		

		Matrix v = decomposition.getV();

		
        assertNotNull(v, "V is null");
		
		assertEquals(MathTestData.DECOMPOSITION_SVD_V.length, v.getRowsCount(), "V size error, expected ("+MathTestData.DECOMPOSITION_SVD_V.length+", "+MathTestData.DECOMPOSITION_SVD_V[0].length+") but got ("+v.getRowsCount()+", "+v.getColumnsCount()+")");
		assertEquals(MathTestData.DECOMPOSITION_SVD_V[0].length, v.getColumnsCount(), "V size error, expected ("+MathTestData.DECOMPOSITION_SVD_V.length+", "+MathTestData.DECOMPOSITION_SVD_V[0].length+") but got ("+v.getRowsCount()+", "+v.getColumnsCount()+")");
/*		
		for(int row = 0; row < v.getRowsCount(); row++) {
			for(int col = 0; col < v.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", v.getValue(row, col), MatrixTestData.DECOMPOSITION_SVD_V[row][col], EPSILON);
			}
		}
*/		
		// Check Input = USVt
		Matrix usv = u.multiply(s).multiply(v.transpose());
		
		assertEquals(k.getRowsCount(), usv.getRowsCount(), "USVt size error, expected ("+k.getRowsCount()+", "+k.getColumnsCount()+") but got ("+usv.getRowsCount()+", "+usv.getColumnsCount()+")");
		assertEquals(k.getColumnsCount(), usv.getColumnsCount(), "USVt size error, expected ("+k.getRowsCount()+", "+k.getColumnsCount()+") but got ("+usv.getRowsCount()+", "+usv.getColumnsCount()+")");
	
		for(int row = 0; row < usv.getRowsCount(); row++) {
			for(int col = 0; col < usv.getColumnsCount(); col++) {
				assertEquals(k.getValue(row, col), usv.getValue(row, col), EPSILON, "Bad value ("+row+", "+col+")");
			}
		}
	}
}
