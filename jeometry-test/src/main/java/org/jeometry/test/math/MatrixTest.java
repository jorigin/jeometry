package org.jeometry.test.math;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
	 * Initializing tests.
	 */
	@BeforeEach
	public void init() {
	}

	/**
	 * Testing method {@link Matrix#getDataArray(int)} 
	 */
	@Test
	public void getDataArrayTest() {
		Matrix matrix = null;
		double[] dataArray = null;

		matrix = JeometryFactory.createMatrix(MathTestData.M_5x5_A);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		dataArray = matrix.getDataArray(Matrix.ROW_MAJOR);

		assertNotNull(dataArray, "Invalid row major data array");

		if (dataArray != null) {
			assertEquals(MathTestData.M_5x5_A_ROWMAJOR.length, dataArray.length, "Invalid row major data array size");

			if (dataArray.length == MathTestData.M_5x5_A_ROWMAJOR.length) {
				for(int index = 0; index < dataArray.length; index++) {
					assertEquals(MathTestData.M_5x5_A_ROWMAJOR[index], dataArray[index], Double.MIN_VALUE, "Invalid row major data at index "+index);
				}
			}
		}

		matrix = JeometryFactory.createMatrix(MathTestData.M_5x5_A);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		dataArray = matrix.getDataArray(Matrix.COLUMN_MAJOR);

		assertNotNull(dataArray, "Invalid column major data array");

		if (dataArray != null) {
			assertEquals(MathTestData.M_5x5_A_COLUMNMAJOR.length, dataArray.length, 0, "Invalid column major data array size");

			if (dataArray.length == MathTestData.M_5x5_A_COLUMNMAJOR.length) {
				for(int index = 0; index < dataArray.length; index++) {
					assertEquals(MathTestData.M_5x5_A_COLUMNMAJOR[index], dataArray[index], 0, "Invalid column major data at index "+index);
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

		matrix = JeometryFactory.createMatrix(MathTestData.M_5x5_A);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		output = new double[MathTestData.M_5x5_A_ROWMAJOR.length];
		try {
			dataArray = matrix.getDataArray(Matrix.ROW_MAJOR, output);

			assertNotNull(dataArray, "Invalid row major data array");
			assertSame(dataArray, output, "Invalid row major data array return");

			if (dataArray != null) {
				assertEquals(MathTestData.M_5x5_A_ROWMAJOR.length, dataArray.length, 0, "Invalid row major data array size");

				if (dataArray.length == MathTestData.M_5x5_A_ROWMAJOR.length) {
					for(int index = 0; index < dataArray.length; index++) {
						assertEquals(MathTestData.M_5x5_A_ROWMAJOR[index], dataArray[index], 0, "Invalid row major data at index "+index);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}

		matrix = JeometryFactory.createMatrix(MathTestData.M_5x5_A);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		output = new double[MathTestData.M_5x5_A_COLUMNMAJOR.length];
		try {
			dataArray = matrix.getDataArray(Matrix.COLUMN_MAJOR, output);

			assertNotNull(dataArray, "Invalid column major data array");
			assertSame(dataArray, output, "Invalid column major data array return");

			if (dataArray != null) {
				assertEquals(MathTestData.M_5x5_A_COLUMNMAJOR.length, dataArray.length, 0, "Invalid column major data array size");

				if (dataArray.length == MathTestData.M_5x5_A_COLUMNMAJOR.length) {
					for(int index = 0; index < dataArray.length; index++) {
						assertEquals(MathTestData.M_5x5_A_COLUMNMAJOR[index], dataArray[index], 0, "Invalid column major data at index "+index);
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
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		try {
			matrix.setDataArray(Matrix.ROW_MAJOR, MathTestData.M_5x5_A_ROWMAJOR);

			for(int row = 0; row < MathTestData.M_5x5_A.length; row++) {
				for(int col = 0; col < MathTestData.M_5x5_A[0].length; col++) {
					assertEquals(MathTestData.M_5x5_A[row][col], matrix.getValue(row, col), 0.0d, "Different values at cell ["+row+", "+col+"]");
				}
			}

		} catch (IllegalArgumentException e) {
			fail("Exception raised: "+e.getMessage());
		}

		matrix = JeometryFactory.createMatrix(5, 5);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		try {
			matrix.setDataArray(Matrix.COLUMN_MAJOR, MathTestData.M_5x5_A_COLUMNMAJOR);

			for(int row = 0; row < MathTestData.M_5x5_A.length; row++) {
				for(int col = 0; col < MathTestData.M_5x5_A[0].length; col++) {
					assertEquals(MathTestData.M_5x5_A[row][col], matrix.getValue(row, col), 0.0d, "Different values at cell ["+row+", "+col+"]");
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

		matrix = JeometryFactory.createMatrix(MathTestData.M_3x3_A);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		determinant = matrix.determinant();
		assertEquals(MathTestData.M_3x3_A_DETERMINANT, determinant, Double.MIN_VALUE, "Invalid determinant: ");

		matrix = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		determinant = matrix.determinant();
		assertEquals(MathTestData.M_4x4_A_DETERMINANT, determinant, Double.MIN_VALUE, "Invalid determinant: ");

		matrix = JeometryFactory.createMatrix(MathTestData.M_5x5_A);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		determinant = matrix.determinant();
		assertEquals(MathTestData.M_5x5_A_DETERMINANT, determinant, Double.MIN_VALUE, "Invalid determinant: ");

	}

	/**
	 * Testing {@link Matrix#transpose()} method.
	 */
	@Test
	public void transposeTest() {
		Matrix matrix = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		Matrix transpose = matrix.transpose();

		assertNotNull(transpose, "Invalid transpose result.");

		if (transpose != null) {
			assertEquals(matrix.getColumnsCount(), transpose.getRowsCount(), "Invalid transpose rows.");
			assertEquals(matrix.getRowsCount(), transpose.getColumnsCount(), "Invalid transpose columns.");

			if ((matrix.getColumnsCount() == transpose.getRowsCount()) && (matrix.getRowsCount() == transpose.getColumnsCount())) {
				for(int row = 0; row < transpose.getRowsCount(); row++) {
					for(int col = 0; col < transpose.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_4x3_A_TRANSPOSE[row][col], transpose.getValue(row, col), Double.MIN_VALUE, "Invalid transpose value ["+row+", "+col+"]");
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
		Matrix matrix    = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		Matrix transpose = JeometryFactory.createMatrix(matrix.getColumnsCount(), matrix.getRowsCount());

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		Matrix ref             = matrix.transpose(transpose);

		assertNotNull(transpose, "Invalid transpose result.");
		assertSame(transpose, ref, "Invalid transpose output reference.");

		if (transpose != null) {
			assertEquals(matrix.getColumnsCount(), transpose.getRowsCount(), "Invalid transpose rows.");
			assertEquals(matrix.getRowsCount(), transpose.getColumnsCount(), "Invalid transpose columns.");

			if ((matrix.getColumnsCount() == transpose.getRowsCount()) && (matrix.getRowsCount() == transpose.getColumnsCount())) {
				for(int row = 0; row < transpose.getRowsCount(); row++) {
					for(int col = 0; col < transpose.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_4x3_A_TRANSPOSE[row][col], transpose.getValue(row, col), Double.MIN_VALUE, "Invalid transpose value ["+row+", "+col+"]");
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

		double[][] originalData = MathTestData.M_4x4_A;
		Matrix matrix = null;
		Matrix ref    = null;

		matrix    = JeometryFactory.createMatrix(originalData);	

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		ref             = matrix.transposeAffect();

		assertNotNull(ref, "Invalid transpose result.");
		assertSame(matrix, ref, "Invalid transpose output reference.");

		if (matrix != null) {

			if ((matrix.getColumnsCount() == originalData.length) && (matrix.getRowsCount() == originalData[0].length)) {
				for(int row = 0; row < matrix.getRowsCount(); row++) {
					for(int col = 0; col < matrix.getColumnsCount(); col++) {
						assertEquals(originalData[col][row], matrix.getValue(row, col), Double.MIN_VALUE, "Invalid transpose value ["+row+", "+col+"]");
					}
				}
			}
		}

		originalData = MathTestData.M_4x3_A;

		matrix    = JeometryFactory.createMatrix(originalData);	

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(matrix.getClass()), "Unexpected matrix implementation "+matrix.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		ref             = matrix.transposeAffect();

		assertNotNull(ref, "Invalid transpose result.");
		assertSame(matrix, ref, "Invalid transpose output reference.");

		if (matrix != null) {

			if ((matrix.getColumnsCount() == originalData.length) && (matrix.getRowsCount() == originalData[0].length)) {
				for(int row = 0; row < matrix.getRowsCount(); row++) {
					for(int col = 0; col < matrix.getColumnsCount(); col++) {
						assertEquals(originalData[col][row], matrix.getValue(row, col), Double.MIN_VALUE, "Invalid transpose value ["+row+", "+col+"]");
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
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		if (matrixClass != null) {
			assertTrue(matrixClass.equals(a.getClass()), "Unexpected matrix implementation "+a.getClass().getSimpleName()+", expected "+matrixClass.getSimpleName());
		}

		Matrix b = JeometryFactory.createMatrix(MathTestData.M_3x4_A);

		try {
			Matrix result = a.multiply(b);

			assertNotNull(result, "Multiplication result is null");

			assertEquals(MathTestData.M_4x4_PRODUCT_A.length, result.getRowsCount(), "Invalid multiplication result rows");
			assertEquals(MathTestData.M_4x4_PRODUCT_A[0].length, result.getColumnsCount(), "Invalid multiplication result columns");

			if ((result.getRowsCount() == MathTestData.M_4x4_PRODUCT_A.length) && (result.getColumnsCount() == MathTestData.M_4x4_PRODUCT_A[0].length)) {
				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_4x4_PRODUCT_A[row][col], result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a      = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		Matrix b      = JeometryFactory.createMatrix(MathTestData.M_3x4_A);
		Matrix result = JeometryFactory.createMatrix(MathTestData.M_4x3_A.length, MathTestData.M_3x4_A[0].length);

		try {
			Matrix reference = a.multiply(b, result);

			assertNotNull(result, "Multiplication result is null");
			assertSame(reference, result, "Return reference and result parameters differs.");

			assertEquals(MathTestData.M_4x4_PRODUCT_A.length, result.getRowsCount(), "Invalid multiplication result rows");
			assertEquals(MathTestData.M_4x4_PRODUCT_A[0].length, result.getColumnsCount(), "Invalid multiplication result columns");

			if ((result.getRowsCount() == MathTestData.M_4x4_PRODUCT_A.length) && (result.getColumnsCount() == MathTestData.M_4x4_PRODUCT_A[0].length)) {
				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_4x4_PRODUCT_A[row][col], result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix matrix = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		Matrix b      = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		try {
			Matrix reference = matrix.multiplyAffect(b);

			assertNotNull(reference, "Multiplication result is null");
			assertSame(reference, matrix, "Return reference and result parameters differs.");

			assertEquals(MathTestData.M_4x3_A.length, matrix.getRowsCount(), "Invalid multiplication result rows");
			assertEquals(MathTestData.M_4x3_A[0].length, matrix.getColumnsCount(), "Invalid multiplication result columns");

			if ((matrix.getRowsCount() == MathTestData.M_4x3_A.length) && (matrix.getColumnsCount() == MathTestData.M_4x3_A[0].length)) {
				for(int row = 0; row < matrix.getRowsCount(); row++) {
					for(int col = 0; col < matrix.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_4x3_A_M_4x4_A_PRODUCT[row][col], matrix.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);
		Matrix m = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		Vector u = null;

		try {
			u = m.multiply(v);

			assertNotNull(v, "Invalid vector v."+v);
			assertNotNull(u, "Invalid vector u."+u);

			if ((v != null) && (u != null)) {
				assertEquals(MathTestData.V_PROD_M_4x4_A_X_V_4_A.length, u.getDimension(), "Invalid dimensions");

				if (u.getDimension() == MathTestData.V_PROD_M_4x4_A_X_V_4_A.length) {
					for (int dimension = 0; dimension < MathTestData.V_PROD_M_4x4_A_X_V_4_A.length; dimension++) {
						assertEquals(MathTestData.V_PROD_M_4x4_A_X_V_4_A[dimension], u.getValue(dimension), Double.MIN_VALUE, "Invalid dimension "+dimension+" value.");
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
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);
		Matrix m = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		Vector result = JeometryFactory.createVector(m.getRowsCount());

		Vector u = null;

		try {
			u = m.multiply(v, result);

			assertNotNull(v, "Invalid vector v.");
			assertNotNull(u, "Invalid vector u.");
			assertNotNull(result, "Invalid vector result.");
			assertSame(result, u, "Invalid vector reference result.");

			if ((v != null) && (u != null) && (result != null)) {
				assertEquals(MathTestData.V_PROD_M_4x4_A_X_V_4_A.length, u.getDimension(), "Invalid dimensions");

				if (u.getDimension() == MathTestData.V_PROD_M_4x4_A_X_V_4_A.length) {
					for (int dimension = 0; dimension < MathTestData.V_PROD_M_4x4_A_X_V_4_A.length; dimension++) {
						assertEquals(MathTestData.V_PROD_M_4x4_A_X_V_4_A[dimension], u.getValue(dimension), Double.MIN_VALUE, "Invalid dimension "+dimension+" value.");
					}
				}
			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}

		// Testing line matrix 
		v = JeometryFactory.createVector(MathTestData.V_4_A);
		m = JeometryFactory.createMatrix(MathTestData.M_4L_A);

		result = JeometryFactory.createVector(m.getRowsCount());

		try {

			u = m.multiply(v, result);

			assertNotNull(v, "Invalid vector v.");
			assertNotNull(u, "Invalid vector u.");
			assertNotNull(result, "Invalid vector result.");
			assertSame(result, u, "Invalid vector reference result.");

			if ((v != null) && (u != null) && (result != null)) {
				assertEquals(MathTestData.V_PROD_M_4L_A_V_4_A.length, u.getDimension(), "Invalid dimensions");

				for (int dimension = 0; dimension < u.getDimension(); dimension++) {						
					assertEquals(MathTestData.V_PROD_M_4L_A_V_4_A[dimension], u.getValue(dimension), Double.MIN_VALUE, "Invalid dimension "+dimension+" value.");
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
		Matrix a      = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		double scalar       = 2.3698d;

		try {
			Matrix result = a.multiply(scalar);

			assertNotNull(result, "Multiplication result is null");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid multiplication result rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid multiplication result columns");

			if ((result.getRowsCount() == a.getRowsCount()) && (result.getColumnsCount() == a.getColumnsCount())) {
				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(a.getValue(row, col)*scalar, result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a      = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		Matrix result = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());
		double scalar       = 2.3698d;

		try {
			Matrix reference = a.multiply(scalar, result);

			assertNotNull(reference, "Multiplication result is null");

			assertSame(reference, result, "Multiplication result and reference differs");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid result rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid result columns");

			if ((result.getRowsCount() == a.getRowsCount()) && (result.getColumnsCount() == a.getColumnsCount())) {
				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(a.getValue(row, col)*scalar, result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a      = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		Matrix ref    = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		double scalar       = 2.3698d;

		try {
			Matrix result = a.multiplyAffect(scalar);

			assertNotNull(result, "Multiplication result is null");
			assertSame(result, a, "Multiplication result differs from this");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid multiplication result rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid multiplication result columns");

			if ((result.getRowsCount() == a.getRowsCount()) && (result.getColumnsCount() == a.getColumnsCount())) {
				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(ref.getValue(row, col)*scalar, result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a      = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		Matrix b      = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		Matrix result = null;

		try {
			result = a.add(b);

			assertNotNull(result, "Addition result is null");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid addition result rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid addition result columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(a.getValue(row, col)+b.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a         = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		Matrix b         = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		Matrix result    = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());

		Matrix reference = null;

		try {
			reference = a.add(b, result);

			assertNotNull(result, "Addition result is null");

			assertSame(reference, result, "Addition result and reference differs");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid addition result rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid addition result columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(a.getValue(row, col)+b.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a         = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		Matrix b         = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		Matrix reference = null;

		try {

			reference = a.addAffect(b);

			assertNotNull(reference, "Addition result is null");

			assertSame(a, reference, "Addition this and reference differs");

			assertEquals(a.getRowsCount(), reference.getRowsCount(), "Invalid addition reference rows");
			assertEquals(a.getColumnsCount(), reference.getColumnsCount(), "Invalid addition reference columns");

			if ((a.getRowsCount() == reference.getRowsCount()) && (a.getColumnsCount() == reference.getColumnsCount())) {

				for(int row = 0; row < reference.getRowsCount(); row++) {
					for(int col = 0; col < reference.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_4x3_A[row][col]+b.getValue(row, col), reference.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		double s = 3.567802356d;

		Matrix result = null;

		try {
			result = a.add(s);

			assertNotNull(result, "Addition result is null");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid addition result rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid addition result columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(a.getValue(row, col)+s, result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a         = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		double s = 3.567802356d;

		Matrix result    = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());

		Matrix reference = null;

		try {
			reference = a.add(s, result);

			assertNotNull(result, "Addition result is null");

			assertSame(reference, result, "Addition result and reference differs");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid addition result rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid addition result columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(a.getValue(row, col)+s, result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a      = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		Matrix b      = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		Matrix result = null;

		try {
			result = a.subtract(b);

			assertNotNull(result, "Subtraction result is null");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid subtraction result rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid subtraction result columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(a.getValue(row, col)-b.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a         = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		Matrix b         = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		Matrix result    = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());

		Matrix reference = null;

		try {
			reference = a.subtract(b, result);

			assertNotNull(result, "Addition result is null");

			assertSame(reference, result, "Addition result and reference differs");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid addition result rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid addition result columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(a.getValue(row, col)-b.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a         = JeometryFactory.createMatrix(MathTestData.M_4x3_A);
		Matrix b         = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		Matrix reference = null;

		try {

			reference = a.subtractAffect(b);

			assertNotNull(reference, "Addition result is null");

			assertSame(a, reference, "Addition this and reference differs");

			assertEquals(a.getRowsCount(), reference.getRowsCount(), "Invalid addition reference rows");
			assertEquals(a.getColumnsCount(), reference.getColumnsCount(), "Invalid addition reference columns");

			if ((a.getRowsCount() == reference.getRowsCount()) && (a.getColumnsCount() == reference.getColumnsCount())) {

				for(int row = 0; row < reference.getRowsCount(); row++) {
					for(int col = 0; col < reference.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_4x3_A[row][col]-b.getValue(row, col), reference.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_3x3_A);

		Matrix result = null;

		try {
			result = a.invert();

			assertNotNull(result, "Invertion result is null");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid invertion rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid invertion columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_3x3_A_INV[row][col], result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
					}
				}

			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}	

		// Test 4x4 matrix
		a = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		result = null;

		try {
			result = a.invert();

			assertNotNull(result, "Invertion result is null");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid invertion rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid invertion columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_4x4_A_INV[row][col], result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_3x3_A);

		Matrix result = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());

		Matrix reference = null;

		try {
			reference = a.invert(result);

			assertNotNull(result, "Invertion result is null");
			assertSame(reference, result, "Invertion this and reference differs");
			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid invertion rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid invertion columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_3x3_A_INV[row][col], result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
					}
				}

			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}

		// Testing 4x4 matrix
		a = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		result = JeometryFactory.createMatrix(a.getRowsCount(), a.getColumnsCount());

		reference = null;

		try {
			reference = a.invert(result);

			assertNotNull(result, "Invertion result is null");
			assertSame(reference, result, "Invertion this and reference differs");
			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid invertion rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid invertion columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_4x4_A_INV[row][col], result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_3x3_A);

		Matrix result = null;

		try {
			result = a.cofactor();

			assertNotNull(result, "Invertion result is null");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid invertion rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid invertion columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_3x3_A_COFACTOR[row][col], result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
					}
				}

			}

		} catch (Exception e) {
			fail("Exception raised: "+e.getMessage());
		}	


		// 4x4 matrix cofactor
		a = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		result = null;

		try {
			result = a.cofactor();

			assertNotNull(result, "Invertion result is null");

			assertEquals(a.getRowsCount(), result.getRowsCount(), "Invalid invertion rows");
			assertEquals(a.getColumnsCount(), result.getColumnsCount(), "Invalid invertion columns");

			if ((a.getRowsCount() == result.getRowsCount()) && (a.getColumnsCount() == result.getColumnsCount())) {

				for(int row = 0; row < result.getRowsCount(); row++) {
					for(int col = 0; col < result.getColumnsCount(); col++) {
						assertEquals(MathTestData.M_4x4_A_COFACTOR[row][col], result.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
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

		Matrix a = JeometryFactory.createMatrix(MathTestData.M_3x3_A);

		a.setTo(5.0d);

		for(int row = 0; row < a.getRowsCount(); row++) {
			for(int col = 0; col < a.getColumnsCount(); col++) {
				assertEquals(5.0d, a.getValue(row, col), Double.MIN_VALUE, "Invalid value ["+row+"x"+col+"]");
			}
		}
	}

	/**
	 * Testing {@link Matrix#getColumn(int)}
	 */
	@Test
	public void getColumnTest() {
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		assertNotNull(a, "Cannot instanciate test matrix.");

		Vector v = null;
		for(int col = 0; col < a.getColumnsCount(); col++) {
			try {
				v = a.getColumn(col);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}

			assertNotNull(v, "Null extracted column.");
			assertEquals(a.getRowsCount(), v.getDimension(), "Invalid extracted column dimension.");

			for(int row = 0; row < a.getRowsCount(); row++) {
				assertEquals(a.getValue(row, col), v.getValue(row), Double.MIN_VALUE, "Invalid vector value "+row);
			}
		}

		// Test error handling
		try {
			v = a.getColumn(-1);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		try {
			v = a.getColumn(a.getRowsCount());
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#getColumn(int, Vector)}
	 */
	@Test
	public void getColumnVectorTest() {
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		assertNotNull(a, "Cannot instanciate test matrix.");

		Vector v = JeometryFactory.createVector(a.getRowsCount());
		Vector reference = null;
		for(int col = 0; col < a.getColumnsCount(); col++) {
			try {
				reference = a.getColumn(col, v);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}

			assertNotNull(v, "Null extracted column.");
			assertNotNull(reference, "Null returned reference.");
			assertSame(v, reference, "Output vector and returned reference differ.");

			assertEquals(a.getRowsCount(), v.getDimension(), "Invalid extracted column dimension.");

			for(int row = 0; row < a.getRowsCount(); row++) {
				assertEquals(a.getValue(row, col), v.getValue(row), Double.MIN_VALUE, "Invalid vector value "+row);
			}
		}

		// Test null vector
		try {
			reference = a.getColumn(0, (Vector)null);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		assertNull(reference, "Reference is not null.");

		// Test error handling
		try {
			v = a.getColumn(-1, v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		try {
			v = a.getColumn(a.getRowsCount(), v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#getColumn(int, double[])}
	 */
	@Test
	public void getColumnArrayTest() {
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		assertNotNull(a, "Cannot instanciate test matrix.");

		double[] v = new double[a.getRowsCount()];
		double[] reference = null;
		for(int col = 0; col < a.getColumnsCount(); col++) {
			try {
				reference = a.getColumn(col, v);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}

			assertNotNull(v, "Null extracted column.");
			assertNotNull(reference, "Null returned reference.");
			assertSame(v, reference, "Output vector and returned reference differ.");

			assertEquals(a.getRowsCount(), v.length, "Invalid extracted column dimension.");

			for(int row = 0; row < a.getRowsCount(); row++) {
				assertEquals(a.getValue(row, col), v[row], Double.MIN_VALUE, "Invalid vector value "+row);
			}
		}

		// Test null vector
		try {
			reference = a.getColumn(0, (double[])null);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		assertNull(reference, "Reference is not null.");

		// Test error handling
		try {
			v = a.getColumn(-1, v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		try {
			v = a.getColumn(a.getRowsCount(), v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#setColumn(int, Vector)}
	 */
	@Test
	public void setColumnVectorTest() {
		Matrix a = JeometryFactory.createMatrix(4, 4);

		assertNotNull(a, "Cannot instanciate test matrix.");

		Vector v = JeometryFactory.createVector(new double[] {1.0d, 2.0d, 3.0d, 4.0d});
		Matrix reference = null;
		for(int col = 0; col < a.getColumnsCount(); col++) {
			try {
				reference = a.setColumn(col, v);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}

			assertNotNull(reference, "Null returned reference.");
			assertSame(a, reference, "Source matrix and returned reference differ.");

			for(int row = 0; row < a.getRowsCount(); row++) {
				assertEquals(a.getValue(row, col), v.getValue(row), Double.MIN_VALUE, "Invalid vector value "+row);
			}
		}

		// Test null vector
		try {
			reference = a.setColumn(0, (Vector)null);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		// Test error handling
		try {
			reference = a.setColumn(-1, v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		try {
			reference = a.setColumn(a.getRowsCount(), v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#setColumn(int, double[])}
	 */
	@Test
	public void setColumnArrayTest() {
		Matrix a = JeometryFactory.createMatrix(4, 4);

		assertNotNull(a, "Cannot instanciate test matrix.");

		double[] v = new double[] {1.0d, 2.0d, 3.0d, 4.0d};
		Matrix reference = null;
		for(int col = 0; col < a.getColumnsCount(); col++) {
			try {
				reference = a.setColumn(col, v);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}

			assertNotNull(reference, "Null returned reference.");
			assertSame(a, reference, "Source matrix and returned reference differ.");

			for(int row = 0; row < a.getRowsCount(); row++) {
				assertEquals(a.getValue(row, col), v[row], Double.MIN_VALUE, "Invalid vector value "+row);
			}
		}

		// Test null vector
		try {
			reference = a.setColumn(0, (Vector)null);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		// Test error handling
		try {
			reference = a.setColumn(-1, v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		try {
			reference = a.setColumn(a.getRowsCount(), v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#getRow(int)}
	 */
	@Test
	public void getRowTest() {
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		assertNotNull(a, "Cannot instanciate test matrix.");

		Vector v = null;
		for(int row = 0; row < a.getRowsCount(); row++) {
			try {
				v = a.getRow(row);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}

			assertNotNull(v, "Null extracted row.");
			assertEquals(a.getColumnsCount(), v.getDimension(), "Invalid extracted row dimension.");

			for(int col = 0; col < a.getColumnsCount(); col++) {
				assertEquals(a.getValue(row, col), v.getValue(col), Double.MIN_VALUE, "Invalid vector value "+col);
			}
		}

		// Test error handling
		try {
			v = a.getRow(-1);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		try {
			v = a.getRow(a.getRowsCount());
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#getRow(int, Vector)}
	 */
	@Test
	public void getRowVectorTest() {
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		assertNotNull(a, "Cannot instanciate test matrix.");

		Vector v = JeometryFactory.createVector(a.getColumnsCount());
		Vector reference = null;
		for(int row = 0; row < a.getRowsCount(); row++) {
			try {
				reference = a.getRow(row, v);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}

			assertNotNull(v, "Null extracted row.");
			assertNotNull(reference, "Null returned reference.");
			assertSame(v, reference, "Output vector and returned reference differ.");

			assertEquals(a.getColumnsCount(), v.getDimension(), "Invalid extracted row dimension.");

			for(int col = 0; col < a.getColumnsCount(); col++) {
				assertEquals(a.getValue(row, col), v.getValue(col), Double.MIN_VALUE, "Invalid vector value "+col);
			}
		}

		// Test null vector
		try {
			reference = a.getRow(0, (Vector)null);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		assertNull(reference, "Reference is not null.");

		// Test error handling
		try {
			v = a.getRow(-1, v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		try {
			v = a.getRow(a.getRowsCount(), v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#getRow(int, double[])}
	 */
	@Test
	public void getRowArrayTest() {
		Matrix a = JeometryFactory.createMatrix(MathTestData.M_4x4_A);

		assertNotNull(a, "Cannot instanciate test matrix.");

		double[] v = new double[a.getColumnsCount()];
		double[] reference = null;
		for(int row = 0; row < a.getRowsCount(); row++) {
			try {
				reference = a.getRow(row, v);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}

			assertNotNull(v, "Null extracted row.");
			assertNotNull(reference, "Null returned reference.");
			assertSame(v, reference, "Output vector and returned reference differ.");

			assertEquals(a.getColumnsCount(), v.length, "Invalid extracted row dimension.");

			for(int col = 0; col < a.getColumnsCount(); col++) {
				assertEquals(a.getValue(row, col), v[col], Double.MIN_VALUE, "Invalid vector value "+col);
			}
		}

		// Test null vector
		try {
			reference = a.getRow(0, (double[])null);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		assertNull(reference, "Reference is not null.");

		// Test error handling
		try {
			v = a.getRow(-1, v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		try {
			v = a.getRow(a.getRowsCount(), v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#setRow(int, Vector)}
	 */
	@Test
	public void setRowVectorTest() {
		Matrix a = JeometryFactory.createMatrix(4, 4);

		assertNotNull(a, "Cannot instanciate test matrix.");

		Vector v = JeometryFactory.createVector(new double[] {1.0d, 2.0d, 3.0d, 4.0d});
		Matrix reference = null;
		for(int row = 0; row < a.getRowsCount(); row++) {
			try {
				reference = a.setRow(row, v);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}

			assertNotNull(reference, "Null returned reference.");
			assertSame(a, reference, "Source matrix and returned reference differ.");

			for(int col = 0; col < a.getRowsCount(); col++) {
				assertEquals(a.getValue(row, col), v.getValue(col), Double.MIN_VALUE, "Invalid vector value "+col);
			}
		}

		// Test null vector
		try {
			reference = a.setRow(0, (Vector)null);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		// Test error handling
		try {
			reference = a.setRow(-1, v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		try {
			reference = a.setRow(a.getRowsCount(), v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}
	}

	/**
	 * Testing {@link Matrix#setRow(int, double[])}
	 */
	@Test
	public void setRowArrayTest() {
		Matrix a = JeometryFactory.createMatrix(4, 4);

		assertNotNull(a, "Cannot instanciate test matrix.");

		double[] v = new double[] {1.0d, 2.0d, 3.0d, 4.0d};
		Matrix reference = null;
		for(int row = 0; row < a.getRowsCount(); row++) {
			try {
				reference = a.setRow(row, v);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}

			assertNotNull(reference, "Null returned reference.");
			assertSame(a, reference, "Source matrix and returned reference differ.");

			for(int col = 0; col < a.getColumnsCount(); col++) {
				assertEquals(a.getValue(row, col), v[col], Double.MIN_VALUE, "Invalid vector value "+col);
			}
		}

		// Test null vector
		try {
			reference = a.setRow(0, (Vector)null);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		// Test error handling
		try {
			reference = a.setRow(-1, v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
		}

		try {
			reference = a.setRow(a.getRowsCount(), v);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		} catch (Throwable t) {
			fail("Unexpected exception: "+t.getMessage());
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

		Matrix a = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		Matrix extraction = a.extract(rowOffset, colOffset, rowCount, colCount);

		assertNotNull(extraction, "Invertion result is null");

		for(int row = 0; row < extraction.getRowsCount(); row++) {
			for(int col = 0; col < extraction.getColumnsCount(); col++) {
				assertEquals(a.getValue(row+rowOffset, col+colOffset), extraction.getValue(row, col), Double.MIN_VALUE, "Invalid cell ["+row+"x"+col+"] value.");
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

		Matrix a = JeometryFactory.createMatrix(MathTestData.M_4x3_A);

		Matrix result = a.extract(rowOffset, colOffset, rowCount, colCount);

		Matrix extraction = a.extract(rowOffset, colOffset, rowCount, colCount, result);

		assertNotNull(extraction, "Extraction result is null");

		assertSame(extraction, result, "Extraction result and reference differs");

		for(int row = 0; row < extraction.getRowsCount(); row++) {
			for(int col = 0; col < extraction.getColumnsCount(); col++) {
				assertEquals(a.getValue(row+rowOffset, col+colOffset), extraction.getValue(row, col), Double.MIN_VALUE, "Invalid cell ["+row+"x"+col+"] value.");
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

		assertEquals(matrix.getRowsCount(), concat.getRowsCount(), "Invalid result row count ");
		assertEquals(matrix.getColumnsCount()+right.getColumnsCount(), concat.getColumnsCount(), "Invalid result column count ");

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals(matrix.getValue(row, col), concat.getValue(row, col), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+col+"]");
			}
		}

		for(int row = 0; row < right.getRowsCount(); row++) {
			for(int col = 0; col < right.getColumnsCount(); col++) {
				assertEquals(right.getValue(row, col), concat.getValue(row, col+matrix.getColumnsCount()), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+col+matrix.getColumnsCount()+"]");
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

		assertNotNull(result, "Concatenation result is null");

		assertSame(concat, result, "Concatenation result and reference differs");

		assertEquals(matrix.getRowsCount(), concat.getRowsCount(), "Invalid result row count ");
		assertEquals(matrix.getColumnsCount()+right.getColumnsCount(), concat.getColumnsCount(), "Invalid result column count ");

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals(matrix.getValue(row, col), concat.getValue(row, col), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+col+"]");
			}
		}

		for(int row = 0; row < right.getRowsCount(); row++) {
			for(int col = 0; col < right.getColumnsCount(); col++) {
				assertEquals( right.getValue(row, col), concat.getValue(row, col+matrix.getColumnsCount()), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+col+matrix.getColumnsCount()+"]");
			}
		}
	}

	/**
	 * Testing {@link Matrix#concatHorizontal(Vector)}
	 */
	@Test
	public void concatHorizontalVectorTest() {

		Matrix matrix = JeometryFactory.createMatrix(new double[][] {{ 1.0d,  2.0d,  3.0d},
			{ 5.0d,  6.0d,  7.0d},
			{ 9.0d, 10.0d, 11.0d}});

		Vector right = JeometryFactory.createVector(new double[] { 4.0d, 8.0d, 12.0d});

		Matrix concat = matrix.concatHorizontal(right);

		assertEquals(matrix.getRowsCount(), concat.getRowsCount(), "Invalid result row count ");
		assertEquals(matrix.getColumnsCount()+right.getDimension(), concat.getColumnsCount(), "Invalid result column count ");

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals(matrix.getValue(row, col), concat.getValue(row, col), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+col+"]");
			}
		}

		for(int row = 0; row < right.getDimension(); row++) {
			assertEquals(right.getValue(row), concat.getValue(row, matrix.getColumnsCount()), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+matrix.getColumnsCount()+"]");
		}
	}

	/**
	 * Testing {@link Matrix#concatHorizontal(Vector, Matrix)}
	 */
	@Test
	public void concatHorizontalVectorResultTest() {

		Matrix matrix = JeometryFactory.createMatrix(new double[][] {{ 1.0d,  2.0d,  3.0d},
			{ 5.0d,  6.0d,  7.0d},
			{ 9.0d, 10.0d, 11.0d}});

		Vector right = JeometryFactory.createVector(new double[] { 4.0d, 8.0d, 12.0d});

		Matrix concat = JeometryFactory.createMatrix(matrix.getRowsCount(), matrix.getColumnsCount()+right.getDimension());

		Matrix result = matrix.concatHorizontal(right, concat);

		assertNotNull(result, "Concatenation result is null");

		assertSame(concat, result, "Concatenation result and reference differs");

		assertEquals(matrix.getRowsCount(), concat.getRowsCount(), "Invalid result row count ");
		assertEquals(matrix.getColumnsCount()+right.getDimension(), concat.getColumnsCount(), "Invalid result column count ");

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals(matrix.getValue(row, col), concat.getValue(row, col), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+col+"]");
			}
		}

		for(int row = 0; row < right.getDimension(); row++) {
			assertEquals(right.getValue(row), concat.getValue(row, matrix.getColumnsCount()), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+matrix.getColumnsCount()+"]");
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

		assertEquals(matrix.getRowsCount()+bottom.getRowsCount(), concat.getRowsCount(), "Invalid result row count ");
		assertEquals(matrix.getColumnsCount(), concat.getColumnsCount(), "Invalid result column count ");

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals(matrix.getValue(row, col), concat.getValue(row, col), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+col+"]");
			}
		}

		for(int row = 0; row < bottom.getRowsCount(); row++) {
			for(int col = 0; col < bottom.getColumnsCount(); col++) {
				assertEquals(bottom.getValue(row, col), concat.getValue(row+matrix.getRowsCount(), col), Double.MIN_VALUE, "Invalid result cell ["+row+matrix.getRowsCount()+"x"+col+"]");
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

		assertNotNull(result, "Concatenation result is null");

		assertSame(concat, result, "Concatenation result and reference differs");

		assertEquals(matrix.getRowsCount()+bottom.getRowsCount(), result.getRowsCount(), "Invalid result row count ");
		assertEquals(matrix.getColumnsCount(), result.getColumnsCount(), "Invalid result column count ");

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals(matrix.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+col+"]");
			}
		}

		for(int row = 0; row < bottom.getRowsCount(); row++) {
			for(int col = 0; col < bottom.getColumnsCount(); col++) {
				assertEquals(bottom.getValue(row, col), result.getValue(row+matrix.getRowsCount(), col), Double.MIN_VALUE, "Invalid result cell ["+row+matrix.getRowsCount()+"x"+col+"]");
			}
		}
	}

	/**
	 * Testing {@link Matrix#concatVertical(Vector)}
	 */
	@Test
	public void concatVerticalVectorTest() {

		Matrix matrix = JeometryFactory.createMatrix(new double[][] {{ 1.0d,  2.0d,  3.0d},
			{ 5.0d,  6.0d,  7.0d},
			{ 9.0d, 10.0d, 11.0d}});

		Vector bottom = JeometryFactory.createVector(new double[] { 4.0d, 8.0d, 12.0d});

		Matrix concat = matrix.concatVertical(bottom);

		assertEquals(matrix.getRowsCount()+bottom.getDimension(), concat.getRowsCount(), "Invalid result row count ");
		assertEquals(matrix.getColumnsCount(), concat.getColumnsCount(), "Invalid result column count ");

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals(matrix.getValue(row, col), concat.getValue(row, col), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+col+"]");
			}
		}

		for(int col = 0; col < concat.getColumnsCount(); col++) {
			assertEquals(bottom.getValue(col), concat.getValue(matrix.getRowsCount(), col), Double.MIN_VALUE, "Invalid result cell ["+matrix.getRowsCount()+"x"+col+"]");
		}

	}

	/**
	 * Testing {@link Matrix#concatHorizontal(Vector, Matrix)}
	 */
	@Test
	public void concatVerticalVectorResultTest() {

		Matrix matrix = JeometryFactory.createMatrix(new double[][] {{ 1.0d,  2.0d,  3.0d},
			{ 5.0d,  6.0d,  7.0d},
			{ 9.0d, 10.0d, 11.0d}});

		Vector bottom = JeometryFactory.createVector(new double[] { 4.0d, 8.0d, 12.0d});

		Matrix concat = JeometryFactory.createMatrix(matrix.getRowsCount()+bottom.getDimension(), matrix.getColumnsCount());

		Matrix result = matrix.concatVertical(bottom, concat);

		assertNotNull(result, "Concatenation result is null");

		assertSame(concat, result, "Concatenation result and reference differs");

		assertEquals(matrix.getRowsCount()+bottom.getDimension(), result.getRowsCount(), "Invalid result row count ");
		assertEquals(matrix.getColumnsCount(), result.getColumnsCount(), "Invalid result column count ");

		for(int row = 0; row < matrix.getRowsCount(); row++) {
			for(int col = 0; col < matrix.getColumnsCount(); col++) {
				assertEquals(matrix.getValue(row, col), result.getValue(row, col), Double.MIN_VALUE, "Invalid result cell ["+row+"x"+col+"]");
			}
		}

		for(int col = 0; col < concat.getColumnsCount(); col++) {
			assertEquals(bottom.getValue(col), concat.getValue(matrix.getRowsCount(), col), Double.MIN_VALUE, "Invalid result cell ["+matrix.getRowsCount()+"x"+col+"]");
		}
	}
}
