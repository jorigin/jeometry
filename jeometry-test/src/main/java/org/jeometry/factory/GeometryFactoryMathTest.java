package org.jeometry.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;
import org.jeometry.math.MathTestData;
import org.jeometry.math.Quaternion;
import org.jeometry.math.Vector;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A test class for {@link JeometryFactory} methods that delegate to a {@link MathBuilder math builder}.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * Create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
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
 *
 */
public class GeometryFactoryMathTest {
	
	protected static Class<? extends Matrix> matrixClass         = null;
	protected static Class<? extends Vector> vectorClass         = null;
	protected static Class<? extends Quaternion> quaternionClass = null;
	
	/**
	 * Test initialization.
	 */
	@BeforeClass
	public static void init() {
		fail("Test class is not initialized. method init() has to be implemented");
	}
	
	
	/**
	 * Testing {@link JeometryFactory#createVector(int)}
	 */
	@Test
	public void createVectorSizeTest() {
		try {
			Vector vector = JeometryFactory.createVector(10);
			assertNotNull("Cannot instantiate vector using GeometryFactory.createVector(int).", vector);
			
			assertEquals("Invalid class, got "+vector.getClass().getSimpleName()+" but exptected "+vectorClass.getSimpleName(), vectorClass, vector.getClass());
			
			assertEquals("Invalid class, got "+vector.getClass().getSimpleName()+" but exptected "+vectorClass.getSimpleName(), vectorClass, vector.getClass());
			
			assertEquals("Invalid vector dimension", 10, vector.getDimension());
			
		} catch (Exception e) {
			fail("Cannot instantiate vector using GeometryFactory.createVector(int).");
		}
	}
	
	
	/**
	 * Testing {@link JeometryFactory#createVector(double[])}
	 */
	@Test
	public void createVectorComponentsTest() {
		try {
			Vector vector = JeometryFactory.createVector(MathTestData.V_4_A);
			assertNotNull("Cannot instantiate vector using GeometryFactory.createVector(double[]).", vector);
			assertEquals("Invalid class, got "+vector.getClass().getSimpleName()+" but exptected "+vectorClass.getSimpleName(), vectorClass, vector.getClass());
			
			assertEquals("Invalid vector dimension", MathTestData.V_4_A.length, vector.getDimension());

			
			
			for(int dimension = 0; dimension < vector.getDimension(); dimension++) {
				assertEquals("Invalid vector component "+dimension, MathTestData.V_4_A[dimension], vector.getVectorComponent(dimension), Double.MIN_VALUE);
			}
			
		} catch (Exception e) {
			fail("Cannot instantiate vector using GeometryFactory.createVector(int).");
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createMatrix(int, int)}
	 */
	@Test
	public void createMatrixSizeTest() {
		try {
			Matrix matrix = JeometryFactory.createMatrix(10, 10);
			assertNotNull("Cannot instantiate matrix using GeometryFactory.createMatrix().", matrix);
			
			assertEquals("Invalid class, got "+matrix.getClass().getSimpleName()+" but exptected "+matrixClass.getSimpleName(), matrixClass, matrix.getClass());
			
			assertEquals("Invalid matrix rows number", 10, matrix.getRowsCount());
			assertEquals("Invalid matrix columns number", 10, matrix.getColumnsCount());
			
		} catch (Exception e) {
			fail("Cannot instantiate matrix using GeometryFactory.createMatrix().");
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createMatrix(double[][])}
	 */
	@Test
	public void createMatrixDataTest() {
		try {
			
			double[][] data = new double[][] {
				{1.0d, 2.0d, 3.0d},
				{4.0d, 5.0d, 6.0d},
				{7.0d, 8.0d, 9.0d}
			};
			
			Matrix matrix = JeometryFactory.createMatrix(data);
			assertNotNull("Cannot instantiate matrix using GeometryFactory.createMatrix(double[][]).", matrix);
			
			assertEquals("Invalid class, got "+matrix.getClass().getSimpleName()+" but exptected "+matrixClass.getSimpleName(), matrixClass, matrix.getClass());
			
			assertEquals("Invalid matrix rows number", 3, matrix.getRowsCount());
			assertEquals("Invalid matrix columns number", 3, matrix.getColumnsCount());
			
			for(int row = 0; row < data.length; row++) {
				for(int col = 0; col < data[0].length; col++) {
					assertEquals("Invalid matrix value ["+row+"x"+col+"]", data[row][col], matrix.getValue(row, col), 0.0d);
				}
			}
			
		} catch (Exception e) {
			fail("Cannot instantiate matrix using GeometryFactory.createMatrix().");
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createMatrix(int, int, double[], int)}
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
			
			Matrix matrix = JeometryFactory.createMatrix(rows, cols, data, Matrix.ROW_MAJOR);
			assertNotNull("Cannot instantiate matrix using GeometryFactory.createMatrix(int, int, double[], int).", matrix);
			
			assertEquals("Invalid class, got "+matrix.getClass().getSimpleName()+" but exptected "+matrixClass.getSimpleName(), matrixClass, matrix.getClass());

			assertEquals("Invalid matrix rows number", rows, matrix.getRowsCount());
			assertEquals("Invalid matrix columns number", cols, matrix.getColumnsCount());
			
			for(int row = 0; row < rows; row++) {
				for(int col = 0; col < cols; col++) {
					assertEquals("Invalid matrix value ["+row+"x"+col+"]", data[row*cols+col], matrix.getValue(row, col), 0.0d);
				}
			}
			
			
			matrix = JeometryFactory.createMatrix(rows, cols, data, Matrix.COLUMN_MAJOR);
			assertNotNull("Cannot instantiate matrix using GeometryFactory.createMatrix(int, int, double[], int).", matrix);
			
			assertEquals("Invalid matrix rows number", rows, matrix.getRowsCount());
			assertEquals("Invalid matrix columns number", cols, matrix.getColumnsCount());
			
			for(int row = 0; row < rows; row++) {
				for(int col = 0; col < cols; col++) {
					assertEquals("Invalid matrix value ["+row+"x"+col+"]", data[col*rows+row], matrix.getValue(row, col), 0.0d);
				}
			}
			
		} catch (Exception e) {
			fail("Cannot instantiate matrix using GeometryFactory.createMatrix().");
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createQuaternion()}
	 */
	@Test
	public void createQuaternionTest() {
		
		try {
			Quaternion quaternion = JeometryFactory.createQuaternion();
			assertNotNull("Cannot instantiate quaternion using GeometryFactory.createQuaternion().", quaternion);
			
			assertEquals("Invalid class, got "+quaternion.getClass().getSimpleName()+" but exptected "+quaternionClass.getSimpleName(), quaternionClass, quaternion.getClass());

		} catch (Exception e) {
			fail("Cannot instantiate quaternion using GeometryFactory.createQuaternion().");
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createQuaternion(double, double, double, double)}
	 */
	@Test
	public void createQuaternionParamsTest() {
		
		try {
			Quaternion quaternion = JeometryFactory.createQuaternion(1.0, 2.0, 3.0, 4.0);
			assertNotNull("Cannot instantiate quaternion using GeometryFactory.createQuaternion().", quaternion);
			
			assertEquals("Invalid class, got "+quaternion.getClass().getSimpleName()+" but exptected "+quaternionClass.getSimpleName(), quaternionClass, quaternion.getClass());
			
			assertEquals("Invalid scalar parameter.", 1.0, quaternion.getScalar(), 0.0d);
			assertEquals("Invalid i parameter.", 2.0, quaternion.getI(), 0.0d);
			assertEquals("Invalid j parameter.", 3.0, quaternion.getJ(), 0.0d);
			assertEquals("Invalid k parameter.", 4.0, quaternion.getK(), 0.0d);
			
		} catch (Exception e) {
			fail("Cannot instantiate quaternion using GeometryFactory.createQuaternion(): "+e.getMessage());
		}
	}
}
