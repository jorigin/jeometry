package org.jeometry.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A test suite dedicated to the {@link Matrix}.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * Create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * &nbsp;&nbsp;matrixClass = [the matrix objects class];<br>
 * &nbsp;&nbsp;vectorClass = [the the vector objects class];<br>
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
public class MatrixTest {

	/**
	 * The class that the matrix objects have to respect.
	 */
	protected static Class<? extends Matrix> matrixClass         = null;

	/**
	 * The class that the vector objects have to respect.
	 */
	protected static Class<? extends Vector> vectorClass         = null;

	/**
	 * Initialize the test static context.
	 */
	@BeforeClass
	public static void initClass() {
		fail("Test class is not initialized. method init() has to be implemented");
	}

	/**
	 * Initializing tests.
	 */
	@Before
	public void init() {
	}

	/**
	 * Testing method {@link Matrix#getDataArray(int)} 
	 */
	@Test
	public void getDataArrayTest() {
		Matrix matrix = null;
		double[] dataArray = null;

		matrix = JeometryFactory.createMatrix(MatrixTestData.M_5x5_A);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		dataArray = matrix.getDataArray(Matrix.ROW_MAJOR);

		assertNotNull("Invalid row major data array", dataArray);

		if (dataArray != null) {
			assertEquals("Invalid row major data array size", MatrixTestData.M_5x5_A_ROWMAJOR.length, dataArray.length, 0);

			if (dataArray.length == MatrixTestData.M_5x5_A_ROWMAJOR.length) {
				for(int index = 0; index < dataArray.length; index++) {
					assertEquals("Invalid row major data at index "+index, MatrixTestData.M_5x5_A_ROWMAJOR[index], dataArray[index], 0);
				}
			}
		}

		matrix = JeometryFactory.createMatrix(MatrixTestData.M_5x5_A);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		dataArray = matrix.getDataArray(Matrix.COLUMN_MAJOR);

		assertNotNull("Invalid column major data array", dataArray);

		if (dataArray != null) {
			assertEquals("Invalid column major data array size", MatrixTestData.M_5x5_A_COLUMNMAJOR.length, dataArray.length, 0);

			if (dataArray.length == MatrixTestData.M_5x5_A_COLUMNMAJOR.length) {
				for(int index = 0; index < dataArray.length; index++) {
					assertEquals("Invalid column major data at index "+index, MatrixTestData.M_5x5_A_COLUMNMAJOR[index], dataArray[index], 0);
				}
			}
		}

	}

	/**
	 * Testing method {@link Matrix#getDataArray(int, double[])} 
	 */
	@Test
	public void getDataArrayOutputTest() {
		Matrix matrix = null;
		double[] output     = null;
		double[] dataArray  = null;

		matrix = JeometryFactory.createMatrix(MatrixTestData.M_5x5_A);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		output = new double[MatrixTestData.M_5x5_A_ROWMAJOR.length];
		try {
			dataArray = matrix.getDataArray(Matrix.ROW_MAJOR, output);

			assertNotNull("Invalid row major data array", dataArray);
			assertSame("Invalid row major data array return", dataArray, output);

			if (dataArray != null) {
				assertEquals("Invalid row major data array size", MatrixTestData.M_5x5_A_ROWMAJOR.length, dataArray.length, 0);

				if (dataArray.length == MatrixTestData.M_5x5_A_ROWMAJOR.length) {
					for(int index = 0; index < dataArray.length; index++) {
						assertEquals("Invalid row major data at index "+index, MatrixTestData.M_5x5_A_ROWMAJOR[index], dataArray[index], 0);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}

		matrix = JeometryFactory.createMatrix(MatrixTestData.M_5x5_A);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		output = new double[MatrixTestData.M_5x5_A_COLUMNMAJOR.length];
		try {
			dataArray = matrix.getDataArray(Matrix.COLUMN_MAJOR, output);

			assertNotNull("Invalid column major data array", dataArray);
			assertSame("Invalid column major data array return", dataArray, output);

			if (dataArray != null) {
				assertEquals("Invalid column major data array size", MatrixTestData.M_5x5_A_COLUMNMAJOR.length, dataArray.length, 0);

				if (dataArray.length == MatrixTestData.M_5x5_A_COLUMNMAJOR.length) {
					for(int index = 0; index < dataArray.length; index++) {
						assertEquals("Invalid column major data at index "+index, MatrixTestData.M_5x5_A_COLUMNMAJOR[index], dataArray[index], 0);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing method {@link Matrix#setDataArray(int, double[])} 
	 */
	@Test
	public void setDataArrayTest() {
		Matrix matrix = null;

		matrix = JeometryFactory.createMatrix(5, 5);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		try {
			matrix.setDataArray(Matrix.ROW_MAJOR, MatrixTestData.M_5x5_A_ROWMAJOR);

			for(int row = 0; row < MatrixTestData.M_5x5_A.length; row++) {
				for(int col = 0; col < MatrixTestData.M_5x5_A[0].length; col++) {
					assertEquals("Different values at cell ["+row+", "+col+"]", MatrixTestData.M_5x5_A[row][col], matrix.getValue(row, col), 0.0d);
				}
			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}

		matrix = JeometryFactory.createMatrix(5, 5);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		try {
			matrix.setDataArray(Matrix.COLUMN_MAJOR, MatrixTestData.M_5x5_A_COLUMNMAJOR);

			for(int row = 0; row < MatrixTestData.M_5x5_A.length; row++) {
				for(int col = 0; col < MatrixTestData.M_5x5_A[0].length; col++) {
					assertEquals("Different values at cell ["+row+", "+col+"]", MatrixTestData.M_5x5_A[row][col], matrix.getValue(row, col), 0.0d);
				}
			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Test the method {@link Matrix#determinant()}
	 */
	@Test
	public void determinantTest() {

		Matrix matrix = null;
		double determinant  = Double.NaN;

		matrix = JeometryFactory.createMatrix(MatrixTestData.M_3x3_A);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		determinant = matrix.determinant();
		assertEquals("Invalid determinant: ", MatrixTestData.M_3x3_A_DETERMINANT, determinant, Double.MIN_VALUE);

		matrix = JeometryFactory.createMatrix(MatrixTestData.M_4x4_A);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		determinant = matrix.determinant();
		assertEquals("Invalid determinant: ", MatrixTestData.M_4x4_A_DETERMINANT, determinant, Double.MIN_VALUE);

		matrix = JeometryFactory.createMatrix(MatrixTestData.M_5x5_A);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		determinant = matrix.determinant();
		assertEquals("Invalid determinant: ", MatrixTestData.M_5x5_A_DETERMINANT, determinant, Double.MIN_VALUE);

	}

	/**
	 * Testing {@link Matrix#transpose()} method.
	 */
	@Test
	public void transposeTest() {
		Matrix matrix = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		Matrix transpose = matrix.transpose();

		assertNotNull("Invalid transpose result.", transpose);

		if (transpose != null) {
			assertEquals("Invalid transpose rows.", matrix.getColumnsCount(), transpose.getRowsCount());
			assertEquals("Invalid transpose columns.", matrix.getRowsCount(), transpose.getColumnsCount());

			if ((matrix.getColumnsCount() == transpose.getRowsCount()) && (matrix.getRowsCount() == transpose.getColumnsCount())) {
				for(int row = 0; row < transpose.getRowsCount(); row++) {
					for(int col = 0; col < transpose.getColumnsCount(); col++) {
						assertEquals("Invalid transpose value ["+row+", "+col+"]", MatrixTestData.M_4x3_A_TRANSPOSE[row][col], transpose.getValue(row, col), Double.MIN_VALUE);
					}
				}
			}
		}
	}

	/**
	 * Testing {@link Matrix#transpose(Matrix)} method.
	 */
	@Test
	public void transposeResultTest() {
		Matrix matrix    = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		Matrix transpose = JeometryFactory.createMatrix(matrix.getColumnsCount(), matrix.getRowsCount());

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		Matrix ref             = matrix.transpose(transpose);

		assertNotNull("Invalid transpose result.", transpose);
		assertSame("Invalid transpose output reference.", transpose, ref);

		if (transpose != null) {
			assertEquals("Invalid transpose rows.", matrix.getColumnsCount(), transpose.getRowsCount());
			assertEquals("Invalid transpose columns.", matrix.getRowsCount(), transpose.getColumnsCount());

			if ((matrix.getColumnsCount() == transpose.getRowsCount()) && (matrix.getRowsCount() == transpose.getColumnsCount())) {
				for(int row = 0; row < transpose.getRowsCount(); row++) {
					for(int col = 0; col < transpose.getColumnsCount(); col++) {
						assertEquals("Invalid transpose value ["+row+", "+col+"]", MatrixTestData.M_4x3_A_TRANSPOSE[row][col], transpose.getValue(row, col), Double.MIN_VALUE);
					}
				}
			}
		}
	}

	/**
	 * Testing {@link Matrix#transposeAffect()} method.
	 */
	@Test
	public void transposeAffectTest() {
		Matrix matrix    = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);	

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(matrix.getClass()));
		}

		Matrix ref             = matrix.transposeAffect();

		assertNotNull("Invalid transpose result.", ref);
		assertSame("Invalid transpose output reference.", matrix, ref);

		if (matrix != null) {

			if ((matrix.getColumnsCount() == MatrixTestData.M_4x3_A.length) && (matrix.getRowsCount() == MatrixTestData.M_4x3_A[0].length)) {
				for(int row = 0; row < matrix.getRowsCount(); row++) {
					for(int col = 0; col < matrix.getColumnsCount(); col++) {
						assertEquals("Invalid transpose value ["+row+", "+col+"]", MatrixTestData.M_4x3_A_TRANSPOSE[row][col], matrix.getValue(row, col), Double.MIN_VALUE);
					}
				}
			}
		}
	}

	/**
	 * Testing method {@link Matrix#multiply(Matrix)}
	 */
	@Test
	public void multiplyTest() {
		Matrix a = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);

		if (matrixClass != null) {
			assertTrue("Unexpected matrix implementation "+a.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName(), matrixClass.equals(a.getClass()));
		}

		Matrix b = JeometryFactory.createMatrix(MatrixTestData.M_3x4_A);

		try {
			Matrix result = a.multiply(b);

			assertNotNull("Multiplication result is null", result);

			assertEquals("Invalid multiplication result rows", MatrixTestData.M_4x4_PRODUCT_A.length, result.getRowsCount());
			assertEquals("Invalid multiplication result columns", MatrixTestData.M_4x4_PRODUCT_A[0].length, result.getColumnsCount());

			if ((result.getRowsCount() == MatrixTestData.M_4x4_PRODUCT_A.length) && (result.getColumnsCount() == MatrixTestData.M_4x4_PRODUCT_A[0].length)) {
				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_4x4_PRODUCT_A[row][col], result.getValue(row, col), Double.MIN_VALUE);
					}
				}
			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing method {@link Matrix#multiply(Matrix, Matrix)}
	 */
	@Test
	public void multiplyResultTest() {
		Matrix a      = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		Matrix b      = JeometryFactory.createMatrix(MatrixTestData.M_3x4_A);
		Matrix result = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A.length, MatrixTestData.M_3x4_A[0].length);

		try {
			Matrix reference = a.multiply(b, result);

			assertNotNull("Multiplication result is null", result);
			assertSame("Return reference and result parameters differs.", reference, result);

			assertEquals("Invalid multiplication result rows", MatrixTestData.M_4x4_PRODUCT_A.length, result.getRowsCount());
			assertEquals("Invalid multiplication result columns", MatrixTestData.M_4x4_PRODUCT_A[0].length, result.getColumnsCount());

			if ((result.getRowsCount() == MatrixTestData.M_4x4_PRODUCT_A.length) && (result.getColumnsCount() == MatrixTestData.M_4x4_PRODUCT_A[0].length)) {
				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_4x4_PRODUCT_A[row][col], result.getValue(row, col), Double.MIN_VALUE);
					}
				}
			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing method {@link Matrix#multiplyAffect(Matrix)}
	 */
	@Test
	public void multiplyAffectTest() {
		Matrix matrix = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		Matrix b      = JeometryFactory.createMatrix(MatrixTestData.M_4x4_A);

		try {
			Matrix reference = matrix.multiplyAffect(b);

			assertNotNull("Multiplication result is null", reference);
			assertSame("Return reference and result parameters differs.", reference, matrix);

			assertEquals("Invalid multiplication result rows",MatrixTestData.M_4x3_A.length, matrix.getRowsCount());
			assertEquals("Invalid multiplication result columns", MatrixTestData.M_4x3_A[0].length, matrix.getColumnsCount());

			if ((matrix.getRowsCount() == MatrixTestData.M_4x3_A.length) && (matrix.getColumnsCount() == MatrixTestData.M_4x3_A[0].length)) {
				for(int row = 0; row < matrix.getRowsCount(); row++) {
					for(int col = 0; col < matrix.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_4x3_A_M_4x4_A_PRODUCT[row][col], matrix.getValue(row, col), Double.MIN_VALUE);
					}
				}
			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing the {@link Matrix#multiply(Vector)} method.
	 */
	@Test
	public void multiplyVectorTest() {
		Vector v = JeometryFactory.createVector(MatrixTestData.V_4_A);
		Matrix m = JeometryFactory.createMatrix(MatrixTestData.M_4x4_A);

		Vector u = null;

		try {
			u = m.multiply(v);

			assertNotNull("Invalid vector v."+v, v);
			assertNotNull("Invalid vector u."+u, u);

			if ((v != null) && (u != null)) {
				assertEquals("Invalid dimensions", MatrixTestData.V_PROD_M_4x4_A_X_V_4_A.length, u.getDimension());

				if (u.getDimension() == MatrixTestData.V_PROD_M_4x4_A_X_V_4_A.length) {
					for (int dimension = 0; dimension < MatrixTestData.V_PROD_M_4x4_A_X_V_4_A.length; dimension++) {
						assertEquals("Invalid dimension "+dimension+" value.", MatrixTestData.V_PROD_M_4x4_A_X_V_4_A[dimension], u.getVectorComponent(dimension), Double.MIN_VALUE);
					}
				}
			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing the {@link Matrix#multiply(Vector, Vector)} method.
	 */
	@Test
	public void multiplyVectorResultTest() {
		Vector v = JeometryFactory.createVector(MatrixTestData.V_4_A);
		Matrix m = JeometryFactory.createMatrix(MatrixTestData.M_4x4_A);

		Vector result = JeometryFactory.createVector(m.getRowsCount());

		Vector u = null;

		try {
			u = m.multiply(v, result);

			assertNotNull("Invalid vector v.", v);
			assertNotNull("Invalid vector u.", u);
			assertNotNull("Invalid vector result.", result);
			assertSame("Invalid vector reference result.", result, u);

			if ((v != null) && (u != null) && (result != null)) {
				assertEquals("Invalid dimensions", MatrixTestData.V_PROD_M_4x4_A_X_V_4_A.length, u.getDimension());

				if (u.getDimension() == MatrixTestData.V_PROD_M_4x4_A_X_V_4_A.length) {
					for (int dimension = 0; dimension < MatrixTestData.V_PROD_M_4x4_A_X_V_4_A.length; dimension++) {
						assertEquals("Invalid dimension "+dimension+" value.", MatrixTestData.V_PROD_M_4x4_A_X_V_4_A[dimension], u.getVectorComponent(dimension), Double.MIN_VALUE);
					}
				}
			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}

		// Testing line matrix 
		v = JeometryFactory.createVector(MatrixTestData.V_4_A);
		m = JeometryFactory.createMatrix(MatrixTestData.M_4L_A);

		result = JeometryFactory.createVector(m.getRowsCount());

		try {

			u = m.multiply(v, result);
			
			assertNotNull("Invalid vector v.", v);
			assertNotNull("Invalid vector u.", u);
			assertNotNull("Invalid vector result.", result);
			assertSame("Invalid vector reference result.", result, u);

			if ((v != null) && (u != null) && (result != null)) {
				assertEquals("Invalid dimensions", MatrixTestData.V_PROD_M_4L_A_V_4_A.length, u.getDimension());

				for (int dimension = 0; dimension < u.getDimension(); dimension++) {						
					assertEquals("Invalid dimension "+dimension+" value.", MatrixTestData.V_PROD_M_4L_A_V_4_A[dimension], u.getVectorComponent(dimension), Double.MIN_VALUE);
				}
			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}

	}

	/**
	 * Testing method {@link Matrix#multiply(double)}
	 */
	@Test
	public void multiplyScalarTest() {
		Matrix a      = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		double scalar       = 2.3698d;

		try {
			Matrix result = a.multiply(scalar);

			assertNotNull("Multiplication result is null", result);

			assertEquals("Invalid multiplication result rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid multiplication result columns", a.getColumnsCount(), result.getColumnsCount());

			if ((result.getRowsCount() == a.getRowsCount()) && (result.getColumnsCount() == a.getColumnsCount())) {
				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", a.getValue(row, col)*scalar, result.getValue(row, col), Double.MIN_VALUE);
					}
				}
			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing method {@link Matrix#multiply(double, Matrix)}
	 */
	@Test
	public void multiplyScalarResultTest() {
		Matrix a      = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		Matrix result = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());
		double scalar       = 2.3698d;

		try {
			Matrix reference = a.multiply(scalar, result);

			assertNotNull("Multiplication result is null", reference);

			assertSame("Multiplication result and reference differs", reference, result);

			assertEquals("Invalid result rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid result columns", a.getColumnsCount(), result.getColumnsCount());

			if ((result.getRowsCount() == a.getRowsCount()) && (result.getColumnsCount() == a.getColumnsCount())) {
				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", a.getValue(row, col)*scalar, result.getValue(row, col), Double.MIN_VALUE);
					}
				}
			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing method {@link Matrix#multiply(double)}
	 */
	@Test
	public void multiplyScalarAffectTest() {
		Matrix a      = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		Matrix ref    = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		double scalar       = 2.3698d;

		try {
			Matrix result = a.multiplyAffect(scalar);

			assertNotNull("Multiplication result is null", result);
			assertSame("Multiplication result differs from this", result, a);

			assertEquals("Invalid multiplication result rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid multiplication result columns", a.getColumnsCount(), result.getColumnsCount());

			if ((result.getRowsCount() == a.getRowsCount()) && (result.getColumnsCount() == a.getColumnsCount())) {
				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", ref.getValue(row, col)*scalar, result.getValue(row, col), Double.MIN_VALUE);
					}
				}
			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing method {@link Matrix#add(Matrix)}
	 */
	@Test
	public void addMatrixTest() {
		Matrix a      = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		Matrix b      = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);

		Matrix result = null;

		try {
			result = a.add(b);

			assertNotNull("Addition result is null", result);

			assertEquals("Invalid addition result rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid addition result columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", a.getValue(row, col)+b.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}

	}

	/**
	 * Testing method {@link Matrix#add(Matrix, Matrix)}
	 */
	@Test
	public void addMatrixResultTest() {
		Matrix a         = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		Matrix b         = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);

		Matrix result    = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());

		Matrix reference = null;

		try {
			reference = a.add(b, result);

			assertNotNull("Addition result is null", result);

			assertSame("Addition result and reference differs", reference, result);

			assertEquals("Invalid addition result rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid addition result columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", a.getValue(row, col)+b.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}

	}

	/**
	 * Testing method {@link Matrix#addAffect(Matrix)}
	 */
	@Test
	public void addMatrixAffectTest() {
		Matrix a         = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		Matrix b         = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);

		Matrix reference = null;

		try {

			reference = a.addAffect(b);

			assertNotNull("Addition result is null", reference);

			assertSame("Addition this and reference differs", a, reference);

			assertEquals("Invalid addition reference rows", a.getRowsCount(), reference.getRowsCount());
			assertEquals("Invalid addition reference columns", a.getColumnsCount(), reference.getColumnsCount());

			if ((a.getRowsCount() == reference.getRowsCount()) && (a.getColumnsCount() == reference.getColumnsCount())) {

				for(int row = 0; row < reference.getRowsCount(); row++) {
					for(int col = 0; col < reference.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_4x3_A[row][col]+b.getValue(row, col), reference.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing method {@link Matrix#add(double)} 
	 */
	@Test
	public void addScalarTest() {
		Matrix a = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		double s = 3.567802356d;

		Matrix result = null;

		try {
			result = a.add(s);

			assertNotNull("Addition result is null", result);

			assertEquals("Invalid addition result rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid addition result columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", a.getValue(row, col)+s, result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing method {@link Matrix#add(double, Matrix)}
	 */
	@Test
	public void addScalarResultTest() {
		Matrix a         = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		double s = 3.567802356d;

		Matrix result    = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());

		Matrix reference = null;

		try {
			reference = a.add(s, result);

			assertNotNull("Addition result is null", result);

			assertSame("Addition result and reference differs", reference, result);

			assertEquals("Invalid addition result rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid addition result columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", a.getValue(row, col)+s, result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}

	}

	/**
	 * Testing method {@link Matrix#subtract(Matrix)}
	 */
	@Test
	public void subtractMatrixTest() {
		Matrix a      = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		Matrix b      = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);

		Matrix result = null;

		try {
			result = a.subtract(b);

			assertNotNull("Subtraction result is null", result);

			assertEquals("Invalid subtraction result rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid subtraction result columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", a.getValue(row, col)-b.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}

	}

	/**
	 * Testing method {@link Matrix#subtract(Matrix, Matrix)}
	 */
	@Test
	public void subtractMatrixResultTest() {
		Matrix a         = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		Matrix b         = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);

		Matrix result    = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());

		Matrix reference = null;

		try {
			reference = a.subtract(b, result);

			assertNotNull("Addition result is null", result);

			assertSame("Addition result and reference differs", reference, result);

			assertEquals("Invalid addition result rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid addition result columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", a.getValue(row, col)-b.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}

	}

	/**
	 * Testing method {@link Matrix#subtractAffect(Matrix)}
	 */
	@Test
	public void subtractMatrixAffectTest() {
		Matrix a         = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		Matrix b         = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);

		Matrix reference = null;

		try {

			reference = a.subtractAffect(b);

			assertNotNull("Addition result is null", reference);

			assertSame("Addition this and reference differs", a, reference);

			assertEquals("Invalid addition reference rows", a.getRowsCount(), reference.getRowsCount());
			assertEquals("Invalid addition reference columns", a.getColumnsCount(), reference.getColumnsCount());

			if ((a.getRowsCount() == reference.getRowsCount()) && (a.getColumnsCount() == reference.getColumnsCount())) {

				for(int row = 0; row < reference.getRowsCount(); row++) {
					for(int col = 0; col < reference.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_4x3_A[row][col]-b.getValue(row, col), reference.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#invert()}
	 */
	@Test
	public void invertMatrixTest() {

		// Test 3x3 matrix
		Matrix a = JeometryFactory.createMatrix(MatrixTestData.M_3x3_A);

		Matrix result = null;

		try {
			result = a.invert();

			assertNotNull("Invertion result is null", result);

			assertEquals("Invalid invertion rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid invertion columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_3x3_A_INV[row][col], result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}	

		// Test 4x4 matrix
		a = JeometryFactory.createMatrix(MatrixTestData.M_4x4_A);

		result = null;

		try {
			result = a.invert();

			assertNotNull("Invertion result is null", result);

			assertEquals("Invalid invertion rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid invertion columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_4x4_A_INV[row][col], result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}	
	}

	/**
	 * Testing {@link Matrix#invert(Matrix)}
	 */
	@Test
	public void invertMatrixResultTest() {

		// Testing 3x3 matrix
		Matrix a = JeometryFactory.createMatrix(MatrixTestData.M_3x3_A);

		Matrix result = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());

		Matrix reference = null;

		try {
			reference = a.invert(result);

			assertNotNull("Invertion result is null", result);
			assertSame("Invertion this and reference differs", reference, result);
			assertEquals("Invalid invertion rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid invertion columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_3x3_A_INV[row][col], result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}

		// Testing 4x4 matrix
		a = JeometryFactory.createMatrix(MatrixTestData.M_4x4_A);

		result = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());

		reference = null;

		try {
			reference = a.invert(result);

			assertNotNull("Invertion result is null", result);
			assertSame("Invertion this and reference differs", reference, result);
			assertEquals("Invalid invertion rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid invertion columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_4x4_A_INV[row][col], result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#cofactor()}
	 */
	@Test
	public void cofactorMatrixTest() {
		Matrix a = JeometryFactory.createMatrix(MatrixTestData.M_3x3_A);

		Matrix result = null;

		try {
			result = a.cofactor();

			assertNotNull("Invertion result is null", result);

			assertEquals("Invalid invertion rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid invertion columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_3x3_A_COFACTOR[row][col], result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}	


		// 4x4 matrix cofactor
		a = JeometryFactory.createMatrix(MatrixTestData.M_4x4_A);

		result = null;

		try {
			result = a.cofactor();

			assertNotNull("Invertion result is null", result);

			assertEquals("Invalid invertion rows", a.getRowsCount(), result.getRowsCount());
			assertEquals("Invalid invertion columns", a.getColumnsCount(), result.getColumnsCount());

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals("Invalid value ["+row+"x"+col+"]", MatrixTestData.M_4x4_A_COFACTOR[row][col], result.getValue(row, col), Double.MIN_VALUE);
					}
				}

			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}	
	}

	/**
	 * Testing {@link Matrix#setTo(double)}
	 */
	@Test
	public void setToTest() {

		Matrix a = JeometryFactory.createMatrix(MatrixTestData.M_3x3_A);

		a.setTo(5.0d);

		for(int row = 0; row < a.getRowsCount(); row++) {
			for(int col = 0; col < a.getColumnsCount(); col++) {
				assertEquals("Invalid value ["+row+"x"+col+"]", 5.0d, a.getValue(row, col), Double.MIN_VALUE);
			}
		}
	}

	/**
	 * Testing {@link Matrix#extract(int, int, int, int)}
	 */
	@Test
	public void extractTest() {
		
		int rowOffset = 1;
		int colOffset = 1;
		int rowCount = 3;
		int colCount = 2;
		
		Matrix a = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		
		Matrix extraction = a.extract(rowOffset, colOffset, rowCount, colCount);
		
		assertNotNull("Invertion result is null", extraction);
		
		for(int row = 0; row < extraction.getRowsCount(); row++) {
			for(int col = 0; col < extraction.getColumnsCount(); col++) {
				assertEquals("Invalid cell ["+row+"x"+col+"] value.", a.getValue(row+rowOffset, col+colOffset), extraction.getValue(row, col), Double.MIN_VALUE);
			}
		}
	}
	
	/**
	 * Testing {@link Matrix#extract(int, int, int, int, Matrix)}
	 */
	@Test
	public void extractResultTest() {
		int rowOffset = 1;
		int colOffset = 1;
		int rowCount = 3;
		int colCount = 2;
		
		Matrix a = JeometryFactory.createMatrix(MatrixTestData.M_4x3_A);
		
		Matrix result = a.extract(rowOffset, colOffset, rowCount, colCount);
		
		Matrix extraction = a.extract(rowOffset, colOffset, rowCount, colCount, result);
		
		assertNotNull("Extraction result is null", extraction);
		
		assertSame("Extraction result and reference differs", extraction, result);
		
		for(int row = 0; row < extraction.getRowsCount(); row++) {
			for(int col = 0; col < extraction.getColumnsCount(); col++) {
				assertEquals("Invalid cell ["+row+"x"+col+"] value.", a.getValue(row+rowOffset, col+colOffset), extraction.getValue(row, col), Double.MIN_VALUE);
			}
		}
	}
	
	/**
	 * Testing {@link Matrix#concatHorizontal(Matrix)}
	 */
	@Test
	public void concatHorizontalTest() {

		Matrix matrix = JeometryFactory.createMatrix(new double[][] {{ 1.0d,  2.0d,  3.0d},
			{ 6.0d,  7.0d,  8.0d},
			{11.0d, 12.0d, 13.0d}});

		Matrix right = JeometryFactory.createMatrix(new double[][] {{ 4.0d,  5.0d},
			{ 9.0d, 10.0d},
			{14.0d, 15.0d}});

		Matrix concat = matrix.concatHorizontal(right);

		assertEquals("Invalid result row count ", matrix.getRowsCount(), concat.getRowsCount());
		assertEquals("Invalid result column count ", matrix.getColumnsCount()+right.getColumnsCount(), concat.getColumnsCount());

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals("Invalid result cell ["+row+"x"+col+"]", matrix.getValue(row, col), concat.getValue(row, col), Double.MIN_VALUE);
			}
		}

		for(int row = 0; row < right.getRowsCount(); row++) {
			for(int col = 0; col < right.getColumnsCount(); col++) {
				assertEquals("Invalid result cell ["+row+"x"+col+matrix.getColumnsCount()+"]", right.getValue(row, col), concat.getValue(row, col+matrix.getColumnsCount()), Double.MIN_VALUE);
			}
		}
	}

	/**
	 * Testing {@link Matrix#concatHorizontal(Matrix, Matrix)}
	 */
	@Test
	public void concatHorizontalResultTest() {

		Matrix matrix = JeometryFactory.createMatrix(new double[][] {{ 1.0d,  2.0d,  3.0d},
			{ 6.0d,  7.0d,  8.0d},
			{11.0d, 12.0d, 13.0d}});

		Matrix right = JeometryFactory.createMatrix(new double[][] {{ 4.0d,  5.0d},
			{ 9.0d, 10.0d},
			{14.0d, 15.0d}});

		Matrix concat = JeometryFactory.createMatrix(matrix.getRowsCount(), matrix.getColumnsCount()+right.getColumnsCount());

		Matrix result = matrix.concatHorizontal(right, concat);

		assertNotNull("Concatenation result is null", result);

		assertSame("Concatenation result and reference differs", concat, result);

		assertEquals("Invalid result row count ", matrix.getRowsCount(), concat.getRowsCount());
		assertEquals("Invalid result column count ", matrix.getColumnsCount()+right.getColumnsCount(), concat.getColumnsCount());

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals("Invalid result cell ["+row+"x"+col+"]", matrix.getValue(row, col), concat.getValue(row, col), Double.MIN_VALUE);
			}
		}

		for(int row = 0; row < right.getRowsCount(); row++) {
			for(int col = 0; col < right.getColumnsCount(); col++) {
				assertEquals("Invalid result cell ["+row+"x"+col+matrix.getColumnsCount()+"]", right.getValue(row, col), concat.getValue(row, col+matrix.getColumnsCount()), Double.MIN_VALUE);
			}
		}
	}

	/**
	 * Testing {@link Matrix#concatVertical(Matrix)}
	 */
	@Test
	public void concatVerticalTest() {

		Matrix matrix = JeometryFactory.createMatrix(new double[][] {{ 1.0d,  2.0d,  3.0d},
			{ 4.0d,  5.0d,  6.0d},
			{ 7.0d,  8.0d,  9.0d}});

		Matrix bottom = JeometryFactory.createMatrix(new double[][] {{ 10.0d, 11.0d, 12.0d},
			{ 13.0d, 14.0d, 15.0d}});

		Matrix concat = matrix.concatVertical(bottom);

		assertEquals("Invalid result row count ", matrix.getRowsCount()+bottom.getRowsCount(), concat.getRowsCount());
		assertEquals("Invalid result column count ", matrix.getColumnsCount(), concat.getColumnsCount());

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals("Invalid result cell ["+row+"x"+col+"]", matrix.getValue(row, col), concat.getValue(row, col), Double.MIN_VALUE);
			}
		}

		for(int row = 0; row < bottom.getRowsCount(); row++) {
			for(int col = 0; col < bottom.getColumnsCount(); col++) {
				assertEquals("Invalid result cell ["+row+matrix.getRowsCount()+"x"+col+"]", bottom.getValue(row, col), concat.getValue(row+matrix.getRowsCount(), col), Double.MIN_VALUE);
			}
		}
	}

	/**
	 * Testing {@link Matrix#concatHorizontal(Matrix, Matrix)}
	 */
	@Test
	public void concatVerticalResultTest() {

		Matrix matrix = JeometryFactory.createMatrix(new double[][] {{ 1.0d,  2.0d,  3.0d},
			                                                         { 4.0d,  5.0d,  6.0d},
			                                                         { 7.0d,  8.0d,  9.0d}});

		Matrix bottom = JeometryFactory.createMatrix(new double[][] {{ 10.0d, 11.0d, 12.0d},
			                                                         { 13.0d, 14.0d, 15.0d}});

		Matrix concat = JeometryFactory.createMatrix(matrix.getRowsCount()+bottom.getRowsCount(), matrix.getColumnsCount());

		Matrix result = matrix.concatVertical(bottom, concat);

		assertNotNull("Concatenation result is null", result);

		assertSame("Concatenation result and reference differs", concat, result);
		
		assertEquals("Invalid result row count ", matrix.getRowsCount()+bottom.getRowsCount(), result.getRowsCount());
		assertEquals("Invalid result column count ", matrix.getColumnsCount(), result.getColumnsCount());

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals("Invalid result cell ["+row+"x"+col+"]", matrix.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE);
			}
		}

		for(int row = 0; row < bottom.getRowsCount(); row++) {
			for(int col = 0; col < bottom.getColumnsCount(); col++) {
				assertEquals("Invalid result cell ["+row+matrix.getRowsCount()+"x"+col+"]", bottom.getValue(row, col), result.getValue(row+matrix.getRowsCount(), col), Double.MIN_VALUE);
			}
		}
	}
}
