package org.jeometry.test.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.MathBuilder;
import org.jeometry.math.Matrix;
import org.jeometry.test.math.MathTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.jeometry.math.Quaternion;
import org.jeometry.math.Vector;

/**
 * A test suite for {@link MathBuilder} implementations.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 *  Create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * &nbsp;&nbsp;mathBuilder = [A math builder to test];<br>
 * &nbsp;&nbsp;matrixClass = [the matrix objects class];<br>
 * &nbsp;&nbsp;vectorClass = [the the vector objects class];<br>
 * &nbsp;&nbsp;quaternionClass = [the quaternion objects class];<br>
 * <br>
 * &nbsp;&nbsp;GeometryFactory.setMathBuilder([a builder that provide suitable classes]);<br>
 * }<br>
 * </code>
 * <br>
 * If the object provided by the geometry factory are not from the same classes as the declared ones, tests will fail.
 * </p>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class MathBuilderTest {

	/**
	 * The math builder to use.
	 */
    protected static MathBuilder mathBuilder = null;
	
    /**
     * The matrix class to use.
     */
    protected static Class<? extends Matrix> matrixClass         = null;
    
    /**
     * The vector class to use.
     */
    protected static Class<? extends Vector> vectorClass         = null;
    
    /**
     * The quaternion class to use.
     */
    protected static Class<? extends Quaternion> quaternionClass = null;
	
	/**
	 * Test initialization.
	 */
	@BeforeAll
	public static void init() {
		fail("method public static void init() has to be set up with @BeforeClass annotation");
	}
	
	/**
	 * Testing {@link MathBuilder#createVector(int)}
	 */
	@Test
	public void createVectorSizeTest() {
		try {
			Vector vector = mathBuilder.createVector(10);
			assertNotNull(vector, "Cannot instantiate vector using MathBuilder.createVector(int).");
			
			assertEquals(vectorClass, vector.getClass(), "Invalid class, got "+vector.getClass().getSimpleName()+" but exptected "+vectorClass.getSimpleName());
			
			assertEquals(10, vector.getDimension(), "Invalid vector dimension");
			
		} catch (Exception e) {
			fail("Cannot instantiate vector using MathBuilder.createVector(int).");
		}
	}
	
	/**
	 * Testing {@link MathBuilder#createVector(double[])}
	 */
	@Test
	public void createVectorComponentsTest() {
		try {
			Vector vector = mathBuilder.createVector(MathTestData.V_4_A);
			assertNotNull(vector, "Cannot instantiate vector using MathBuilder.createVector(double[]).");
			
			assertEquals(vectorClass, vector.getClass(), "Invalid class, got "+vector.getClass().getSimpleName()+" but exptected "+vectorClass.getSimpleName());
			
			assertEquals(MathTestData.V_4_A.length, vector.getDimension(), "Invalid vector dimension");

			for(int dimension = 0; dimension < vector.getDimension(); dimension++) {
				assertEquals(MathTestData.V_4_A[dimension], vector.getValue(dimension), Double.MIN_VALUE, "Invalid vector component "+dimension);
			}
			
		} catch (Exception e) {
			fail("Cannot instantiate vector using MathBuilder.createVector(int).");
		}
	}
	
	/**
	 * Testing {@link MathBuilder#createMatrix(int, int)}
	 */
	@Test
	public void createMatrixSizeTest() {
		try {
			Matrix matrix = mathBuilder.createMatrix(10, 10);
			assertNotNull(matrix, "Cannot instantiate matrix using MathBuilder.createMatrix().");
			
			assertEquals(matrixClass, matrix.getClass(), "Invalid class, got "+matrix.getClass().getSimpleName()+" but exptected "+matrixClass.getSimpleName());
			
			assertEquals(10, matrix.getRowsCount(), "Invalid matrix rows number");
			assertEquals(10, matrix.getColumnsCount(), "Invalid matrix columns number");
			
		} catch (Exception e) {
			fail("Cannot instantiate matrix using MathBuilder.createMatrix().");
		}
	}
	
	/**
	 * Testing {@link MathBuilder#createMatrix(double[][])}
	 */
	@Test
	public void createMatrixDataTest() {
		try {
			
			double[][] data = new double[][] {
				{1.0d, 2.0d, 3.0d},
				{4.0d, 5.0d, 6.0d},
				{7.0d, 8.0d, 9.0d}
			};
			
			Matrix matrix = mathBuilder.createMatrix(data);
			assertNotNull(matrix, "Cannot instantiate matrix using MathBuilder.createMatrix(double[][]).");
			
			assertEquals(matrixClass, matrix.getClass(), "Invalid class, got "+matrix.getClass().getSimpleName()+" but exptected "+matrixClass.getSimpleName());
			
			assertEquals( 3, matrix.getRowsCount(), "Invalid matrix rows number");
			assertEquals(3, matrix.getColumnsCount(), "Invalid matrix columns number");
			
			for(int row = 0; row < data.length; row++) {
				for(int col = 0; col < data[0].length; col++) {
					assertEquals(data[row][col], matrix.getValue(row, col), 0.0d, "Invalid matrix value ["+row+"x"+col+"]");
				}
			}
			
		} catch (Exception e) {
			fail("Cannot instantiate matrix using MathBuilder.createMatrix().");
		}
	}
	
	/**
	 * Testing {@link MathBuilder#createMatrix(int, int, double[], int)}
	 */
	@Test
	public void createMatrixSizeDataOrderingTest() {
		try {
			
			int rows = 3;
			int cols = 3;
			
			double[] data = new double[] {
				1.0d, 2.0d, 3.0d,
				4.0d, 5.0d, 6.0d,
				7.0d, 8.0d, 9.0d
			};
			
			Matrix matrix = mathBuilder.createMatrix(rows, cols, data, Matrix.ROW_MAJOR);
			assertNotNull(matrix, "Cannot instantiate matrix using MathBuilder.createMatrix(int, int, double[], int).");
			
			assertEquals(matrixClass, matrix.getClass(), "Invalid class, got "+matrix.getClass().getSimpleName()+" but exptected "+matrixClass.getSimpleName());
			
			assertEquals(rows, matrix.getRowsCount(), "Invalid matrix rows number");
			assertEquals(cols, matrix.getColumnsCount(), "Invalid matrix columns number");
			
			for(int row = 0; row < rows; row++) {
				for(int col = 0; col < cols; col++) {
					assertEquals(data[row*cols+col], matrix.getValue(row, col), 0.0d, "Invalid matrix value ["+row+"x"+col+"]");
				}
			}
			
			
			matrix = mathBuilder.createMatrix(rows, cols, data, Matrix.COLUMN_MAJOR);
			assertNotNull(matrix, "Cannot instantiate matrix using MathBuilder.createMatrix(int, int, double[], int).");
			
			assertEquals(rows, matrix.getRowsCount(), "Invalid matrix rows number");
			assertEquals(cols, matrix.getColumnsCount(), "Invalid matrix columns number");
			
			for(int row = 0; row < rows; row++) {
				for(int col = 0; col < cols; col++) {
					assertEquals(data[col*rows+row], matrix.getValue(row, col), 0.0d, "Invalid matrix value ["+row+"x"+col+"]");
				}
			}
			
		} catch (Exception e) {
			fail("Cannot instantiate matrix using MathBuilder.createMatrix().");
		}
	}
	
	/**
	 * Testing {@link MathBuilder#createQuaternion()}
	 */
	@Test
	public void createQuaternionTest() {
		
		try {
			Quaternion quaternion = mathBuilder.createQuaternion();
			assertNotNull(quaternion, "Cannot instantiate quaternion using MathBuilder.createQuaternion().");
			
			assertEquals(quaternionClass, quaternion.getClass(), "Invalid class, got "+quaternion.getClass().getSimpleName()+" but exptected "+quaternionClass.getSimpleName());
			
		} catch (Exception e) {
			fail("Cannot instantiate quaternion using MathBuilder.createQuaternion().");
		}
	}
	
	/**
	 * Testing {@link MathBuilder#createQuaternion(double, double, double, double)}
	 */
	@Test
	public void createQuaternionParamsTest() {
		
		try {
			Quaternion quaternion = mathBuilder.createQuaternion(1.0, 2.0, 3.0, 4.0);
			assertNotNull(quaternion, "Cannot instantiate quaternion using MathBuilder.createQuaternion().");
			
			assertEquals(quaternionClass, quaternion.getClass(), "Invalid class, got "+quaternion.getClass().getSimpleName()+" but exptected "+quaternionClass.getSimpleName());

			assertEquals(1.0, quaternion.getScalar(), 0.0d, "Invalid scalar parameter.");
			assertEquals(2.0, quaternion.getI(), 0.0d, "Invalid i parameter.");
			assertEquals(3.0, quaternion.getJ(), 0.0d, "Invalid j parameter.");
			assertEquals(4.0, quaternion.getK(), 0.0d, "Invalid k parameter.");
			
		} catch (Exception e) {
			fail("Cannot instantiate quaternion using MathBuilder.createQuaternion(): "+e.getMessage());
		}
	}
}
