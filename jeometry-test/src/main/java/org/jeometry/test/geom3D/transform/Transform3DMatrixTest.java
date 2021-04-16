package org.jeometry.test.geom3D.transform;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.transform.Transform3D;
import org.jeometry.geom3D.transform.Transform3DMatrix;
import org.jeometry.math.Matrix;
import org.jeometry.test.geom3D.Geom3DTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * A test suite dedicated to the {@link Transform3DMatrix}.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * Create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * <br>
 * &nbsp;&nbsp;GeometryFactory.setMathBuilder([a builder that provide suitable classes]);<br>
 * <br>
 * &nbsp;&nbsp;transformation3DClass = <i>the class of the transform to test</i>;<br>
 * }<br>
 * </code>
 * <br>
 * If the object provided by the geometry factory are not from the same classes as the declared ones, tests will fail.
 * </p>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.3
 */
public class Transform3DMatrixTest {

	/**
	 * The numerical precision.
	 */
	protected static double EPSILON = 0.000001;

	/**
	 * The class of the transformation 3D
	 */
	protected static Class<?> transformation3DClass = null;

	/**
	 * Initialize the test static context.
	 */
	@BeforeAll
	public static void initClass() {
		fail("Test class is not initialized. method initClass() has to be implemented");
	}

	/**
	 * Testing {@link Transform3DMatrix#getMatrix()}
	 */
	@Test
	public void getMatrixTest() {
		double[][] data = Geom3DTestData.TRANSFORM_MATRIX_RT;
		
		Transform3DMatrix transform = JeometryFactory.createTransform3DMatrix(data);

		assertNotNull(transform, "Unable to instanciate Transfor3D implementation.");
		
		if (transformation3DClass != null) {
			assertEquals(transformation3DClass, transform.getClass(), "Transformation 3D class "+transform.getClass().getSimpleName()+" is not valid, expected "+transformation3DClass.getSimpleName());
		}
		
		Matrix matrix = transform.getMatrix();

		assertNotNull(matrix, "Unable to get Matrix from transformation.");
		assertEquals(data.length, matrix.getRowsCount(), "Invalid returned matrix size "+matrix.getRowsCount()+"x"+matrix.getColumnsCount());
		assertEquals(data[0].length, matrix.getColumnsCount(), "Invalid returned matrix size "+matrix.getRowsCount()+"x"+matrix.getColumnsCount());
		
		for(int row = 0; row < data.length; row++) {
			for(int col = 0; col < data[0].length; col++) {
				assertEquals(matrix.getValue(row, col), data[row][col], Double.MIN_VALUE, "Invalid value ("+row+", "+col+")");
			}
		}
	}
	
	/**
	 * Testing {@link Transform3DMatrix#setMatrix(Matrix)}
	 */
	@Test
	public void setMatrixTest() {
		double[][] data = Geom3DTestData.TRANSFORM_MATRIX_RT;
		
		Transform3DMatrix transform = JeometryFactory.createTransform3DMatrix(Geom3DTestData.TRANSFORM_MATRIX_ID);

		assertNotNull(transform, "Unable to instanciate Transfor3D implementation.");
		
		if (transformation3DClass != null) {
			assertEquals(transformation3DClass, transform.getClass());
		}
		
		Matrix matrix = JeometryFactory.createMatrix(data);
				
		assertNotNull(matrix, "Unable to instanciate Matrix implementation.");
		assertEquals(data.length, matrix.getRowsCount(), "Invalid returned matrix size "+matrix.getRowsCount()+"x"+matrix.getColumnsCount());
		assertEquals(data[0].length, matrix.getColumnsCount(), "Invalid returned matrix size "+matrix.getRowsCount()+"x"+matrix.getColumnsCount());

		try {
			transform.setMatrix(matrix);
		} catch (Throwable t) {
			fail("Unexpected exception "+t.getMessage());
		}

		for(int row = 0; row < data.length; row++) {
			for(int col = 0; col < data[0].length; col++) {
				assertEquals(matrix.getValue(row, col), data[row][col], Double.MIN_VALUE, "Invalid value ("+row+", "+col+")");
			}
		}
	}
	
	/**
	 * Testing {@link Transform3D#transform(Point3D)}
	 */
	@Test
	public void transformTest() {

		Transform3D transform = JeometryFactory.createTransform3DMatrix(Geom3DTestData.TRANSFORM_MATRIX_RT);

		assertNotNull(transform, "Unable to instanciate Transfor3D implementation.");

		if (transformation3DClass != null) {
			assertEquals(transformation3DClass, transform.getClass(), "Transformation 3D class "+transform.getClass().getSimpleName()+" is not valid, expected "+transformation3DClass.getSimpleName());
		}

		Point3D source   = null;
		Point3D expected = null;

		// TEST simple point
		source = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINTA[0], 
				Geom3DTestData.GEOM3D_POINTA[1], 
				Geom3DTestData.GEOM3D_POINTA[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[2]);

		Transform3DTest.transformTest(transform, source, expected, EPSILON);

		// TEST 0 coordinates point
		source = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINT0[0], 
				Geom3DTestData.GEOM3D_POINT0[1], 
				Geom3DTestData.GEOM3D_POINT0[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[2]);

		Transform3DTest.transformTest(transform, source, expected, EPSILON);

		// TEST null point
		source = null;

		expected = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[2]);

		Transform3DTest.transformTest(transform, source, expected, EPSILON);
	}

	/**
	 * Testing {@link Transform3D#transform(Point3D)}
	 */
	@Test
	public void transformResultTest() {

		Transform3D transform = JeometryFactory.createTransform3DMatrix(Geom3DTestData.TRANSFORM_MATRIX_RT);

		assertNotNull(transform, "Unable to instanciate Transfor3D implementation.");

		if (transformation3DClass != null) {
			assertEquals(transformation3DClass, transform.getClass(), "Transformation 3D class "+transform.getClass().getSimpleName()+" is not valid, expected "+transformation3DClass.getSimpleName());
		}

		Point3D source   = null;
		Point3D expected = null;

		// TEST simple point
		source = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINTA[0], 
				Geom3DTestData.GEOM3D_POINTA[1], 
				Geom3DTestData.GEOM3D_POINTA[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[2]);

		Transform3DTest.transformResultTest(transform, source, expected, EPSILON);

		// TEST 0 coordinates point
		source = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINT0[0], 
				Geom3DTestData.GEOM3D_POINT0[1], 
				Geom3DTestData.GEOM3D_POINT0[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[2]);

		Transform3DTest.transformResultTest(transform, source, expected, EPSILON);

		// TEST null point
		source = null;

		expected = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[2]);

		Transform3DTest.transformResultTest(transform, source, expected, EPSILON);
	}
	
	/**
	 * Testing {@link Transform3D#transform(Point3D)}
	 */
	@Test
	public void transformAffectTest() {

		Transform3D transform = JeometryFactory.createTransform3DMatrix(Geom3DTestData.TRANSFORM_MATRIX_RT);

		assertNotNull(transform, "Unable to instanciate Transfor3D implementation.");

		if (transformation3DClass != null) {
			assertEquals(transformation3DClass, transform.getClass(), "Transformation 3D class "+transform.getClass().getSimpleName()+" is not valid, expected "+transformation3DClass.getSimpleName());
		}

		Point3D source   = null;
		Point3D expected = null;

		// TEST simple point
		source = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINTA[0], 
				Geom3DTestData.GEOM3D_POINTA[1], 
				Geom3DTestData.GEOM3D_POINTA[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[2]);

		Transform3DTest.transformAffectTest(transform, source, expected, EPSILON);

		// TEST 0 coordinates point
		source = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINT0[0], 
				Geom3DTestData.GEOM3D_POINT0[1], 
				Geom3DTestData.GEOM3D_POINT0[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[2]);

		Transform3DTest.transformAffectTest(transform, source, expected, EPSILON);

		// TEST null point
		source = null;

		expected = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[2]);

		Transform3DTest.transformAffectTest(transform, source, expected, EPSILON);
	}
	
	/**
	 * Testing {@link Transform3D#transformInverse(Point3D)}
	 */
	@Test
	public void transformInverseTest() {
		Transform3D transform = JeometryFactory.createTransform3DMatrix(Geom3DTestData.TRANSFORM_MATRIX_RT);

		assertNotNull(transform, "Unable to instanciate Transfor3D implementation.");

		if (transformation3DClass != null) {
			assertEquals(transformation3DClass, transform.getClass(), "Transformation 3D class "+transform.getClass().getSimpleName()+" is not valid, expected "+transformation3DClass.getSimpleName());
		}

		Point3D source   = null;
		Point3D expected = null;

		// TEST simple point
		source = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINTA[0], 
				Geom3DTestData.GEOM3D_POINTA[1], 
				Geom3DTestData.GEOM3D_POINTA[2]);

		Transform3DTest.transformInverseTest(transform, source, expected, EPSILON);
		Transform3DTest.transformInverseResultTest(transform, source, expected, EPSILON);
		Transform3DTest.transformInverseAffectTest(transform, source, expected, EPSILON);

		// TEST 0 coordinates point
		source = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINT0[0], 
				Geom3DTestData.GEOM3D_POINT0[1], 
				Geom3DTestData.GEOM3D_POINT0[2]);

		Transform3DTest.transformInverseTest(transform, source, expected, EPSILON);
		Transform3DTest.transformInverseResultTest(transform, source, expected, EPSILON);
		Transform3DTest.transformInverseAffectTest(transform, source, expected, EPSILON);

		// TEST null point
		source = null;

		expected = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINTA[0], 
				Geom3DTestData.GEOM3D_POINTA[1], 
				Geom3DTestData.GEOM3D_POINTA[2]);

		Transform3DTest.transformInverseTest(transform, source, expected, EPSILON);
		Transform3DTest.transformInverseResultTest(transform, source, expected, EPSILON);
		Transform3DTest.transformInverseAffectTest(transform, source, expected, EPSILON);
	}
	
	/**
	 * Testing {@link Transform3D#transformInverse(Point3D)}
	 */
	@Test
	public void transformInverseResultTest() {
		Transform3D transform = JeometryFactory.createTransform3DMatrix(Geom3DTestData.TRANSFORM_MATRIX_RT);

		assertNotNull(transform, "Unable to instanciate Transfor3D implementation.");

		if (transformation3DClass != null) {
			assertEquals(transformation3DClass, transform.getClass(), "Transformation 3D class "+transform.getClass().getSimpleName()+" is not valid, expected "+transformation3DClass.getSimpleName());
		}

		Point3D source   = null;
		Point3D expected = null;

		// TEST simple point
		source = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINTA[0], 
				Geom3DTestData.GEOM3D_POINTA[1], 
				Geom3DTestData.GEOM3D_POINTA[2]);

		Transform3DTest.transformInverseResultTest(transform, source, expected, EPSILON);

		// TEST 0 coordinates point
		source = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINT0[0], 
				Geom3DTestData.GEOM3D_POINT0[1], 
				Geom3DTestData.GEOM3D_POINT0[2]);

		Transform3DTest.transformInverseResultTest(transform, source, expected, EPSILON);

		// TEST null point
		source = null;

		expected = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINTA[0], 
				Geom3DTestData.GEOM3D_POINTA[1], 
				Geom3DTestData.GEOM3D_POINTA[2]);

		Transform3DTest.transformInverseResultTest(transform, source, expected, EPSILON);
	}

	/**
	 * Testing {@link Transform3D#transformInverse(Point3D)}
	 */
	@Test
	public void transformInverseAffectTest() {
		Transform3D transform = JeometryFactory.createTransform3DMatrix(Geom3DTestData.TRANSFORM_MATRIX_RT);

		assertNotNull(transform, "Unable to instanciate Transfor3D implementation.");

		if (transformation3DClass != null) {
			assertEquals(transformation3DClass, transform.getClass(), "Transformation 3D class "+transform.getClass().getSimpleName()+" is not valid, expected "+transformation3DClass.getSimpleName());
		}

		Point3D source   = null;
		Point3D expected = null;

		// TEST simple point
		source = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINTA[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINTA[0], 
				Geom3DTestData.GEOM3D_POINTA[1], 
				Geom3DTestData.GEOM3D_POINTA[2]);

		Transform3DTest.transformInverseAffectTest(transform, source, expected, EPSILON);

		// TEST 0 coordinates point
		source = JeometryFactory.createPoint3D(Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[0], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[1], 
				Geom3DTestData.TRANSFORM_MATRIX_RT_POINT0[2]);

		expected = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINT0[0], 
				Geom3DTestData.GEOM3D_POINT0[1], 
				Geom3DTestData.GEOM3D_POINT0[2]);

		Transform3DTest.transformInverseAffectTest(transform, source, expected, EPSILON);

		// TEST null point
		source = null;

		expected = JeometryFactory.createPoint3D(Geom3DTestData.GEOM3D_POINTA[0], 
				Geom3DTestData.GEOM3D_POINTA[1], 
				Geom3DTestData.GEOM3D_POINTA[2]);

		Transform3DTest.transformInverseAffectTest(transform, source, expected, EPSILON);
	}

	/**
	 * Testing {@link Transform3DMatrix#invertTransform()}
	 */
    @Test
	public void invertTransformTest() {
    	Transform3DMatrix transform = JeometryFactory.createTransform3DMatrix(Geom3DTestData.TRANSFORM_MATRIX_RT);

		assertNotNull(transform, "Unable to instanciate Transfor3D implementation.");

		if (transformation3DClass != null) {
			assertEquals(transformation3DClass, transform.getClass(),"Transformation 3D class "+transform.getClass().getSimpleName()+" is not valid, expected "+transformation3DClass.getSimpleName());
		}
		
		Transform3DMatrix inverted = null;
		
		try {
			inverted = transform.invertTransform();
		} catch (Throwable t) {
			fail("Unable to invert transformation: "+t.getMessage());
		}
		
		assertNotNull(inverted, "Unable to invert transform.");
		assertEquals(transform.getMatrix().getRowsCount(), inverted.getMatrix().getRowsCount(), "Invalid returned matrix size "+inverted.getMatrix().getRowsCount()+"x"+inverted.getMatrix().getColumnsCount());
		assertEquals(transform.getMatrix().getColumnsCount(), inverted.getMatrix().getColumnsCount(), "Invalid returned matrix size "+inverted.getMatrix().getRowsCount()+"x"+inverted.getMatrix().getColumnsCount());
		
		Matrix m = null;
		
		try {
			m = transform.getMatrix().multiply(inverted.getMatrix());
		} catch (Throwable t) {
			fail("Unable to compute transform x inverted: "+t.getMessage());
		}
		
		assertNotNull(m, "Unable to compute transform x inverted.");
		assertEquals(Geom3DTestData.TRANSFORM_MATRIX_ID.length, m.getRowsCount(), "Invalid transform x inverted matrix size "+m.getRowsCount()+"x"+m.getColumnsCount());
		assertEquals(Geom3DTestData.TRANSFORM_MATRIX_ID[0].length, m.getColumnsCount(), "Invalid transform x inverted matrix size "+m.getRowsCount()+"x"+m.getColumnsCount());
		
		for(int row = 0; row < Geom3DTestData.TRANSFORM_MATRIX_ID.length; row++) {
			for(int col = 0; col < Geom3DTestData.TRANSFORM_MATRIX_ID[0].length; col++) {
				assertEquals(Geom3DTestData.TRANSFORM_MATRIX_ID[row][col], m.getValue(row, col), EPSILON, "Invalid transform x inverted value ("+row+", "+col+")");
			}
		}
    }
		
    /**
     * Testing {@link Transform3DMatrix#invertTransformAffect()}
     */
    @Test
	public void invertTransformAffectTest() {
    	Transform3DMatrix transform = JeometryFactory.createTransform3DMatrix(Geom3DTestData.TRANSFORM_MATRIX_RT);

    	Transform3DMatrix original = JeometryFactory.createTransform3DMatrix(Geom3DTestData.TRANSFORM_MATRIX_RT);
    	
		assertNotNull(transform, "Unable to instanciate Transfor3D implementation.");
		assertNotNull(original, "Unable to instanciate Transfor3D implementation.");

		if (transformation3DClass != null) {
			assertEquals(transformation3DClass, transform.getClass(), "Transformation 3D class "+transform.getClass().getSimpleName()+" is not valid, expected "+transformation3DClass.getSimpleName());
		}
		
		Transform3DMatrix inverted = null;
		
		try {
			inverted = transform.invertTransformAffect();
		} catch (Throwable t) {
			fail("Unable to invert transformation: "+t.getMessage());
		}
		
		assertNotNull(inverted, "Unable to invert transform.");
		assertSame(transform, inverted, "Inverted matrix and source transforms differ.");
		assertEquals(transform.getMatrix().getRowsCount(), inverted.getMatrix().getRowsCount(), "Invalid returned matrix size "+inverted.getMatrix().getRowsCount()+"x"+inverted.getMatrix().getColumnsCount());
		assertEquals(transform.getMatrix().getColumnsCount(), inverted.getMatrix().getColumnsCount(), "Invalid returned matrix size "+inverted.getMatrix().getRowsCount()+"x"+inverted.getMatrix().getColumnsCount());
		
		Matrix m = null;
		
		try {
			m = original.getMatrix().multiply(inverted.getMatrix());
		} catch (Throwable t) {
			fail("Unable to compute transform x inverted: "+t.getMessage());
		}
		
		assertNotNull(m, "Unable to compute transform x inverted.");
		assertEquals(Geom3DTestData.TRANSFORM_MATRIX_ID.length, m.getRowsCount(), "Invalid transform x inverted matrix size "+m.getRowsCount()+"x"+m.getColumnsCount());
		assertEquals(Geom3DTestData.TRANSFORM_MATRIX_ID[0].length, m.getColumnsCount(), "Invalid transform x inverted matrix size "+m.getRowsCount()+"x"+m.getColumnsCount());
		
		for(int row = 0; row < Geom3DTestData.TRANSFORM_MATRIX_ID.length; row++) {
			for(int col = 0; col < Geom3DTestData.TRANSFORM_MATRIX_ID[0].length; col++) {
				assertEquals(Geom3DTestData.TRANSFORM_MATRIX_ID[row][col], m.getValue(row, col), EPSILON, "Invalid transform x inverted value ("+row+", "+col+")");
			}
		}
    }
}
