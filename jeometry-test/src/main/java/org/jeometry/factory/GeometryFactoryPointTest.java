package org.jeometry.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.junit.BeforeClass;
import org.junit.Test;

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
	 * Test initialization.
	 */
	@BeforeClass
	public static void init() {
		fail("Test class is not initialized. method init() has to be implemented");
	}

	/**
	 * Testing {@link JeometryFactory#createPoint3D()}
	 */
	@Test
	public void createPoint3DTest() {
		
		try {
			Point3D point = JeometryFactory.createPoint3D();
			assertNotNull("Cannot instantiate 3D point using GeometryFactory.createPoint3D().", point);
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
			assertNotNull("Cannot instantiate 3D point using GeometryFactory.createPoint3D(double, double, double).", point);
			
			assertEquals("X coordinate is invalid", 1.0, point.getX(), 0.0);
			assertEquals("Y coordinate is invalid", 2.0, point.getY(), 0.0);
			assertEquals("Z coordinate is invalid", 3.0, point.getZ(), 0.0);

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
		
		assertNotNull("Null point", test);
		assertEquals("Invalid X coordinate "+test.getX(), point.getX(), test.getX(), Double.MIN_VALUE);
		assertEquals("Invalid Y coordinate "+test.getX(), point.getY(), test.getY(), Double.MIN_VALUE);
		assertEquals("Invalid Y coordinate "+test.getX(), point.getZ(), test.getZ(), Double.MIN_VALUE);
		
	}
	
	
	/**
	 * Testing {@link JeometryFactory#createPoint2D()}
	 */
	@Test
	public void createPoint2DTest() {
		
		try {
			Point2D point = JeometryFactory.createPoint2D();
			assertNotNull("Cannot instantiate 2D point using GeometryFactory.createPoint2D().", point);
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
			assertNotNull("Cannot instantiate 2D point using GeometryFactory.createPoint2D(double, double).", point);
			
			assertEquals("X coordinate is invalid", 1.0, point.getX(), 0.0);
			assertEquals("Y coordinate is invalid", 2.0, point.getY(), 0.0);
			
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
		
		assertNotNull("Null point", test);
		assertEquals("Invalid X coordinate "+test.getX(), point.getX(), test.getX(), Double.MIN_VALUE);
		assertEquals("Invalid Y coordinate "+test.getX(), point.getY(), test.getY(), Double.MIN_VALUE);
		
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint2DContainer()}
	 */
	@Test
	public void createPoint2DContainerTest() {
		
		try {
			Point2DContainer container = JeometryFactory.createPoint2DContainer();
			assertNotNull("Cannot instantiate 2D point container using GeometryFactory.createPoint2DContainer().", container);	
		} catch (Exception e) {
			fail("Cannot instantiate 2D point container using GeometryFactory.createPoint2DContainer(): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint2DContainer(int)}
	 */
	@Test
	public void createPoint2DContainerCapacityTest() {
		
		try {
			Point2DContainer container = JeometryFactory.createPoint2DContainer(10);
			assertNotNull("Cannot instantiate 2D point container using GeometryFactory.createPoint2DContainer(int).", container);	
		} catch (Exception e) {
			fail("Cannot instantiate 2D point container using GeometryFactory.createPoint2DContainer(int): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint3DContainer()}
	 */
	@Test
	public void createPoint3DContainerTest() {
		
		try {
			Point3DContainer<Point3D> container = JeometryFactory.<Point3D>createPoint3DContainer();
			assertNotNull("Cannot instantiate 3D point container using GeometryFactory.createPoint3DContainer().", container);	
		} catch (Exception e) {
			fail("Cannot instantiate 3D point container using GeometryFactory.createPoint3DContainer(): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createPoint3DContainer(int)}
	 */
	@Test
	public void createPoint3DContainerCapacityTest() {
		
		try {
			Point3DContainer<Point3D> container = JeometryFactory.createPoint3DContainer(10);
			assertNotNull("Cannot instantiate 3D point container using GeometryFactory.createPoint3DContainer(int).", container);	
		} catch (Exception e) {
			fail("Cannot instantiate 3D point container using GeometryFactory.createPoint3DContainer(int): "+e.getMessage());
		}
	}

}
