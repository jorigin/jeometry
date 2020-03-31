package org.jeometry.simple.math.decomposition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.MatrixTestData;
import org.jeometry.math.decomposition.LUDecomposition;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * A test suite dedicated to the {@link LUDecomposition}.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public class SimpleLUDecompositionTest {

	/**
	 * Epsilon for numeric comparison.
	 */
	public static double EPSILON = 0.0000001d;
	
	/**
	 * Initialize the test static context.
	 */
	@BeforeClass
	public static void initClass() {
		GeometryFactory.setMathBuilder(new SimpleMathBuilder());
	}
	
	/**
	 * A test dedicated to the {@link LUDecomposition}.
	 */
	@Test
	public void luDecompositionTest() {
		Matrix k = GeometryFactory.createMatrix(MatrixTestData.DECOMPOSITION_LU_INPUT);

		LUDecomposition decomposition = GeometryFactory.createLUDecomposition(k);
		
		Matrix l = decomposition.getLower();

		assertNotNull("L is null", l);
		
		assertEquals("L size error, expected ("+MatrixTestData.DECOMPOSITION_LU_L.length+", "+MatrixTestData.DECOMPOSITION_LU_L[0].length+") but got ("+l.getRowsCount()+", "+l.getColumnsCount()+")", MatrixTestData.DECOMPOSITION_LU_L.length, l.getRowsCount());
		assertEquals("L size error, expected ("+MatrixTestData.DECOMPOSITION_LU_L.length+", "+MatrixTestData.DECOMPOSITION_LU_L[0].length+") but got ("+l.getRowsCount()+", "+l.getColumnsCount()+")", MatrixTestData.DECOMPOSITION_LU_L[0].length, l.getColumnsCount());
		
		for(int row = 0; row < l.getRowsCount(); row++) {
			for(int col = 0; col < l.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", l.getValue(row, col), MatrixTestData.DECOMPOSITION_LU_L[row][col], EPSILON);
			}
		}
		
		Matrix u = decomposition.getUpper();
		
        assertNotNull("U is null", l);
		
		assertEquals("U size error, expected ("+MatrixTestData.DECOMPOSITION_LU_U.length+", "+MatrixTestData.DECOMPOSITION_LU_U[0].length+") but got ("+u.getRowsCount()+", "+u.getColumnsCount()+")", MatrixTestData.DECOMPOSITION_LU_U.length, u.getRowsCount());
		assertEquals("U size error, expected ("+MatrixTestData.DECOMPOSITION_LU_U.length+", "+MatrixTestData.DECOMPOSITION_LU_U[0].length+") but got ("+u.getRowsCount()+", "+u.getColumnsCount()+")", MatrixTestData.DECOMPOSITION_LU_U[0].length, u.getColumnsCount());
		
		for(int row = 0; row < u.getRowsCount(); row++) {
			for(int col = 0; col < u.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", u.getValue(row, col), MatrixTestData.DECOMPOSITION_LU_U[row][col], EPSILON);
			}
		}
		

		Matrix p = decomposition.getPivot();
		
        assertNotNull("P is null", l);
		
		assertEquals("P size error, expected ("+MatrixTestData.DECOMPOSITION_LU_P.length+", "+MatrixTestData.DECOMPOSITION_LU_P[0].length+") but got ("+p.getRowsCount()+", "+p.getColumnsCount()+")", MatrixTestData.DECOMPOSITION_LU_P.length, p.getRowsCount());
		assertEquals("P size error, expected ("+MatrixTestData.DECOMPOSITION_LU_P.length+", "+MatrixTestData.DECOMPOSITION_LU_P[0].length+") but got ("+p.getRowsCount()+", "+p.getColumnsCount()+")", MatrixTestData.DECOMPOSITION_LU_P[0].length, p.getColumnsCount());
		
		for(int row = 0; row < p.getRowsCount(); row++) {
			for(int col = 0; col < p.getColumnsCount(); col++) {
				assertEquals("Bad value ("+row+", "+col+")", p.getValue(row, col), MatrixTestData.DECOMPOSITION_LU_P[row][col], EPSILON);
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
	}
}
