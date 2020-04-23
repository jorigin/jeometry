package org.jeometry.math.decomposition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.MathTestData;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void svdDecompositionTest() {
		
		Matrix k = JeometryFactory.createMatrix(MathTestData.DECOMPOSITION_SVD_INPUT);

		SVDDecomposition decomposition = JeometryFactory.createSVDDecomposition(k);
		
        assertNotNull("Decomposition is null", decomposition);
		
		if (decompositionClass != null) {
			assertEquals("Expected decomposition class "+decompositionClass.getSimpleName()+" but got "+decomposition.getClass().getSimpleName(), decompositionClass, decomposition.getClass());
		}
		
		Matrix u = decomposition.getU();
		
		assertNotNull("U is null", u);
		assertEquals("U size error, expected ("+MathTestData.DECOMPOSITION_SVD_U.length+", "+MathTestData.DECOMPOSITION_SVD_U[0].length+") but got ("+u.getRowsCount()+", "+u.getColumnsCount()+")", MathTestData.DECOMPOSITION_SVD_V.length, u.getRowsCount());
		assertEquals("U size error, expected ("+MathTestData.DECOMPOSITION_SVD_U.length+", "+MathTestData.DECOMPOSITION_SVD_U[0].length+") but got ("+u.getRowsCount()+", "+u.getColumnsCount()+")", MathTestData.DECOMPOSITION_SVD_V[0].length, u.getColumnsCount());
/*		
		for(int row = 0; row < u.getRowsCount(); row++) {
			for(int col = 0; col < u.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", u.getValue(row, col), MatrixTestData.DECOMPOSITION_SVD_U[row][col], EPSILON);
			}
		}
*/		
		
		Matrix s = decomposition.getS();

        assertNotNull("S is null", s);
		
		assertEquals("S size error, expected ("+MathTestData.DECOMPOSITION_SVD_S.length+", "+MathTestData.DECOMPOSITION_SVD_S[0].length+") but got ("+s.getRowsCount()+", "+s.getColumnsCount()+")", MathTestData.DECOMPOSITION_SVD_S.length, s.getRowsCount());
		assertEquals("S size error, expected ("+MathTestData.DECOMPOSITION_SVD_S.length+", "+MathTestData.DECOMPOSITION_SVD_S[0].length+") but got ("+s.getRowsCount()+", "+s.getColumnsCount()+")", MathTestData.DECOMPOSITION_SVD_S[0].length, s.getColumnsCount());
		
		for(int row = 0; row < s.getRowsCount(); row++) {
			for(int col = 0; col < s.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", s.getValue(row, col), MathTestData.DECOMPOSITION_SVD_S[row][col], EPSILON);
			}
		}
		

		Matrix v = decomposition.getV();

		
        assertNotNull("V is null", v);
		
		assertEquals("V size error, expected ("+MathTestData.DECOMPOSITION_SVD_V.length+", "+MathTestData.DECOMPOSITION_SVD_V[0].length+") but got ("+v.getRowsCount()+", "+v.getColumnsCount()+")", MathTestData.DECOMPOSITION_SVD_V.length, v.getRowsCount());
		assertEquals("V size error, expected ("+MathTestData.DECOMPOSITION_SVD_V.length+", "+MathTestData.DECOMPOSITION_SVD_V[0].length+") but got ("+v.getRowsCount()+", "+v.getColumnsCount()+")", MathTestData.DECOMPOSITION_SVD_V[0].length, v.getColumnsCount());
/*		
		for(int row = 0; row < v.getRowsCount(); row++) {
			for(int col = 0; col < v.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", v.getValue(row, col), MatrixTestData.DECOMPOSITION_SVD_V[row][col], EPSILON);
			}
		}
*/		
		// Check Input = USVt
		Matrix usv = u.multiply(s).multiply(v.transpose());
		
		assertEquals("USVt size error, expected ("+k.getRowsCount()+", "+k.getColumnsCount()+") but got ("+usv.getRowsCount()+", "+usv.getColumnsCount()+")", k.getRowsCount(), usv.getRowsCount());
		assertEquals("USVt size error, expected ("+k.getRowsCount()+", "+k.getColumnsCount()+") but got ("+usv.getRowsCount()+", "+usv.getColumnsCount()+")", k.getColumnsCount(), usv.getColumnsCount());
	
		for(int row = 0; row < usv.getRowsCount(); row++) {
			for(int col = 0; col < usv.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", k.getValue(row, col), usv.getValue(row, col), EPSILON);
			}
		}
	}
}
