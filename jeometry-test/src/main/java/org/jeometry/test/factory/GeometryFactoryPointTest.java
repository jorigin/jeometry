package org.jeometry.test.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.math.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * A test class for {@link JeometryFactory} methods.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 *  Create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * <br>
 * <br>
 * &nbsp;&nbsp;GeometryFactory.setMeshBuilder([a builder that provide suitable classes]);<br>
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
public class GeometryFactoryPointTest {

	/**
	 * Testing {@link JeometryFactory#createPoint3D()}
	 */
	@Test
	public void createPoint3DTest() {
		
		try {
			Point3D point = JeometryFactory.createPoint3D();
			assertNotNull(point, "Cannot instantiate 3D point using GeometryFactory.createPoint3D().");
		} catch (Exception e) {
			fail("Cannot instantiate 3D point using GeometryFactory.createPoint3D().");
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint3D(double, double, double)}
	 */
	@Test
	public void createPoint3DCoordinatesTest() {
		
		try {
			Point3D point = JeometryFactory.createPoint3D(1.0, 2.0, 3.0);
			assertNotNull(point, "Cannot instantiate 3D point using GeometryFactory.createPoint3D(double, double, double).");
			
			assertEquals(1.0, point.getX(), 0.0, "X coordinate is invalid");
			assertEquals(2.0, point.getY(), 0.0, "Y coordinate is invalid");
			assertEquals(3.0, point.getZ(), 0.0, "Z coordinate is invalid");

		} catch (Exception e) {
			fail("Cannot instantiate 3D point using GeometryFactory.createPoint3D(double, double, double).");
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint3D(Point3D)}
	 */
	@Test
	public void createPoint3DCopyTest() {
		Point3D point = JeometryFactory.createPoint3D();
		point.setX(1.0d);
		point.setY(2.0d);
		point.setY(3.0d);
		
		Point3D test = JeometryFactory.createPoint3D(point);
		
		assertNotNull(test, "Null point");
		assertEquals(point.getX(), test.getX(), Double.MIN_VALUE, "Invalid X coordinate "+test.getX());
		assertEquals(point.getY(), test.getY(), Double.MIN_VALUE, "Invalid Y coordinate "+test.getX());
		assertEquals(point.getZ(), test.getZ(), Double.MIN_VALUE, "Invalid Y coordinate "+test.getX());
		
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint3D(Vector)}
	 */
	@Test
	public void createPoint3DVectorTest() {
		Vector vector = JeometryFactory.createVector(new double[]{1.0d, 2.0d, 3.0d});
		
		Point3D point = JeometryFactory.createPoint3D(vector);
		
		assertNotNull(point, "Null point");
		assertEquals(point.getX(), point.getX(), Double.MIN_VALUE, "Invalid X coordinate "+vector.getValue(0));
		assertEquals(point.getY(), point.getY(), Double.MIN_VALUE, "Invalid Y coordinate "+vector.getValue(1));
		assertEquals(point.getZ(), point.getZ(), Double.MIN_VALUE, "Invalid Y coordinate "+vector.getValue(2));
		
        vector = JeometryFactory.createVector(new double[]{1.0d, 2.0d, 3.0d, 4.0d, 5.0d});
		
		point = JeometryFactory.createPoint3D(vector);
		
		assertNotNull(point, "Null point");
		assertEquals(point.getX(), point.getX(), Double.MIN_VALUE, "Invalid X coordinate "+vector.getValue(0));
		assertEquals(point.getY(), point.getY(), Double.MIN_VALUE, "Invalid Y coordinate "+vector.getValue(1));
		assertEquals(point.getZ(), point.getZ(), Double.MIN_VALUE, "Invalid Y coordinate "+vector.getValue(2));
		
		try {
			JeometryFactory.createPoint3D(null);
			fail("IllegalArgumentException raise expected for null input vector.");
		} catch (Exception e) {
			assertTrue(true, "Exception raised.");
		}
		
		try {
			JeometryFactory.createPoint3D(JeometryFactory.createVector(new double[]{1.0d, 2.0d}));
			fail("IllegalArgumentException raise expected for invalid size input vector.");
		} catch (Exception e) {
			assertTrue(true, "Exception raised.");
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint2D()}
	 */
	@Test
	public void createPoint2DTest() {
		
		try {
			Point2D point = JeometryFactory.createPoint2D();
			assertNotNull(point, "Cannot instantiate 2D point using GeometryFactory.createPoint2D().");
		} catch (Exception e) {
			fail("Cannot instantiate 2D point using GeometryFactory.createPoint2D(): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint2D(double, double)}
	 */
	@Test
	public void createPoint2DCoordinatesTest() {
		
		try {
			Point2D point = JeometryFactory.createPoint2D(1.0d, 2.0d);
			assertNotNull(point, "Cannot instantiate 2D point using GeometryFactory.createPoint2D(double, double).");
			
			assertEquals(1.0, point.getX(), 0.0, "X coordinate is invalid");
			assertEquals(2.0, point.getY(), 0.0, "Y coordinate is invalid");
			
		} catch (Exception e) {
			fail("Cannot instantiate 2D point using GeometryFactory.createPoint2D(double, double): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint2D(Point2D)}
	 */
	@Test
	public void createPoint2DCopyTest() {
		Point2D point = JeometryFactory.createPoint2D();
		point.setX(1.0d);
		point.setY(2.0d);
		
		Point2D test = JeometryFactory.createPoint2D(point);
		
		assertNotNull( test, "Null point");
		assertEquals(point.getX(), test.getX(), Double.MIN_VALUE, "Invalid X coordinate "+test.getX());
		assertEquals(point.getY(), test.getY(), Double.MIN_VALUE, "Invalid Y coordinate "+test.getX());
		
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint2D(Vector)}
	 */
	@Test
	public void createPoint2DVectorTest() {
		Vector vector = JeometryFactory.createVector(new double[]{1.0d, 2.0d});
		
		Point2D point = JeometryFactory.createPoint2D(vector);
		
		assertNotNull(point, "Null point");
		assertEquals(point.getX(), point.getX(), Double.MIN_VALUE, "Invalid X coordinate "+vector.getValue(0));
		assertEquals(point.getY(), point.getY(), Double.MIN_VALUE, "Invalid Y coordinate "+vector.getValue(1));
		
        vector = JeometryFactory.createVector(new double[]{1.0d, 2.0d, 3.0d, 4.0d, 5.0d});
		
		point = JeometryFactory.createPoint2D(vector);
		
		assertNotNull(point, "Null point");
		assertEquals(point.getX(), point.getX(), Double.MIN_VALUE, "Invalid X coordinate "+vector.getValue(0));
		assertEquals(point.getY(), point.getY(), Double.MIN_VALUE, "Invalid Y coordinate "+vector.getValue(1));
		
		try {
			JeometryFactory.createPoint2D(null);
			fail("IllegalArgumentException raise expected for null input vector.");
		} catch (Exception e) {
			assertTrue(true, "Exception raised.");
		}
		
		try {
			JeometryFactory.createPoint2D(JeometryFactory.createVector(new double[]{1.0d}));
			fail("IllegalArgumentException raise expected for invalid size input vector.");
		} catch (Exception e) {
			assertTrue(true, "Exception raised.");
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint2DContainer()}
	 */
	@Test
	public void createPoint2DContainerTest() {
		//TODO implements createPoint2DContainerTest() 
		/*
		try {
			Point2DContainer container = JeometryFactory.createPoint2DContainer();
			assertNotNull(container, "Cannot instantiate 2D point container using GeometryFactory.createPoint2DContainer().");	
		} catch (Exception e) {
			fail("Cannot instantiate 2D point container using GeometryFactory.createPoint2DContainer(): "+e.getMessage());
		}
		*/
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint2DContainer(int)}
	 */
	@Test
	public void createPoint2DContainerCapacityTest() {
		//TODO implements createPoint2DContainerCapacityTest()
		/*
		try {
			Point2DContainer container = JeometryFactory.createPoint2DContainer(10);
			assertNotNull(container, "Cannot instantiate 2D point container using GeometryFactory.createPoint2DContainer(int).");	
		} catch (Exception e) {
			fail("Cannot instantiate 2D point container using GeometryFactory.createPoint2DContainer(int): "+e.getMessage());
		}
		*/
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint3DContainer()}
	 */
	@Test
	public void createPoint3DContainerTest() {
		//TODO implements createPoint3DContainerTest()
		/*
		try {
			Point3DContainer<Point3D> container = JeometryFactory.<Point3D>createPoint3DContainer();
			assertNotNull(container, "Cannot instantiate 3D point container using GeometryFactory.createPoint3DContainer().");	
		} catch (Exception e) {
			fail("Cannot instantiate 3D point container using GeometryFactory.createPoint3DContainer(): "+e.getMessage());
		}
		*/
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint3DContainer(int)}
	 */
	@Test
	public void createPoint3DContainerCapacityTest() {
		// TODO implements createPoint3DContainerCapacityTest()
		/*
		try {
			Point3DContainer<Point3D> container = JeometryFactory.createPoint3DContainer(10);
			assertNotNull(container, "Cannot instantiate 3D point container using GeometryFactory.createPoint3DContainer(int).");	
		} catch (Exception e) {
			fail("Cannot instantiate 3D point container using GeometryFactory.createPoint3DContainer(int): "+e.getMessage());
		}
		*/
	}

}
