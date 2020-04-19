package org.jeometry.math.decomposition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.MatrixTestData;
import org.junit.BeforeClass;
import org.junit.Test;

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
	@BeforeClass
	public static void initClass() {
		fail("method public static void initClass() has to be set up with @BeforeClass annotation");
	}
	
	/**
	 * A test dedicated to the {@link EigenDecomposition}.
	 */
	@Test
	public void eigenDecompositionTest() {
		Matrix k = JeometryFactory.createMatrix(MatrixTestData.DECOMPOSITION_EIGEN_INPUT);

		EigenDecomposition decomposition = JeometryFactory.createEigenDecomposition(k);
		
		assertNotNull("Decomposition is null", decomposition);
		
		if (decompositionClass != null) {
			assertEquals("Expected decomposition class "+decompositionClass.getSimpleName()+" but got "+decomposition.getClass().getSimpleName(), decompositionClass, decomposition.getClass());
		}
		
		Matrix v = decomposition.getV();

		assertNotNull("V is null", v);
		
		assertEquals("V size error, expected ("+MatrixTestData.DECOMPOSITION_EIGEN_V.length+", "+MatrixTestData.DECOMPOSITION_EIGEN_V[0].length+") but got ("+v.getRowsCount()+", "+v.getColumnsCount()+")", MatrixTestData.DECOMPOSITION_EIGEN_V.length, v.getRowsCount());
		assertEquals("V size error, expected ("+MatrixTestData.DECOMPOSITION_EIGEN_V.length+", "+MatrixTestData.DECOMPOSITION_EIGEN_V[0].length+") but got ("+v.getRowsCount()+", "+v.getColumnsCount()+")", MatrixTestData.DECOMPOSITION_EIGEN_V[0].length, v.getColumnsCount());
		
		for(int row = 0; row < v.getRowsCount(); row++) {
			for(int col = 0; col < v.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", v.getValue(row, col), MatrixTestData.DECOMPOSITION_EIGEN_V[row][col], EPSILON);
			}
		}
		
		Matrix d = decomposition.getD();
		
        assertNotNull("D is null", d);
		
		assertEquals("D size error, expected ("+MatrixTestData.DECOMPOSITION_EIGEN_D.length+", "+MatrixTestData.DECOMPOSITION_EIGEN_D[0].length+") but got ("+d.getRowsCount()+", "+d.getColumnsCount()+")", MatrixTestData.DECOMPOSITION_EIGEN_D.length, d.getRowsCount());
		assertEquals("D size error, expected ("+MatrixTestData.DECOMPOSITION_EIGEN_D.length+", "+MatrixTestData.DECOMPOSITION_EIGEN_D[0].length+") but got ("+d.getRowsCount()+", "+d.getColumnsCount()+")", MatrixTestData.DECOMPOSITION_EIGEN_D[0].length, d.getColumnsCount());
		
		for(int row = 0; row < d.getRowsCount(); row++) {
			for(int col = 0; col < d.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", d.getValue(row, col), MatrixTestData.DECOMPOSITION_EIGEN_D[row][col], EPSILON);
			}
		}
		
		// Check A = VDV-1
		Matrix vdv = v.multiply(d).multiply(v.invert());
		
		assertEquals("PK size error, expected ("+k.getRowsCount()+", "+k.getColumnsCount()+") but got ("+vdv.getRowsCount()+", "+vdv.getColumnsCount()+")", k.getRowsCount(), vdv.getRowsCount());
		assertEquals("PK size error, expected ("+k.getRowsCount()+", "+k.getColumnsCount()+") but got ("+vdv.getRowsCount()+", "+vdv.getColumnsCount()+")", k.getColumnsCount(), vdv.getColumnsCount());
	
		for(int row = 0; row < k.getRowsCount(); row++) {
			for(int col = 0; col < k.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", k.getValue(row, col), vdv.getValue(row, col), EPSILON);
			}
		}
	}
}
