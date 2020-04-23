package org.jeometry.geom2D.point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.VectorTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unitary tests dedicated to {@link Point2D} implementations.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * Create a test class that extends this one
 * </p>
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.2
 */
public class Point2DTest extends VectorTest {

	  /**
	   * Initialize the tests.
	   */
	  @BeforeClass
	  public static void initClass() {
		  fail("method public static void init() has to be set up with @BeforeClass annotation");
	  }
	  
	  /**
	   * Testing {@link Point2D#setValues(double, double)}
	   */
	  @Test
	  public void setValuesXYZTest() {
		  
		  double[] values = new double[] {1.0d, 2.0d};
		  
		  Point2D p = JeometryFactory.createPoint2D();
		  
		  p.setValues(values[0], values[1]);
		 
		  assertEquals("Invalid X coordinate", values[0], p.getX(), Double.MIN_VALUE);
		  assertEquals("Invalid Y coordinate", values[1], p.getY(), Double.MIN_VALUE);
		  
		  p.setValues(values[1], values[0]);
			 
		  assertEquals("Invalid X coordinate", values[1], p.getX(), Double.MIN_VALUE);
		  assertEquals("Invalid Y coordinate", values[2], p.getY(), Double.MIN_VALUE);
	  }
	  
	  /**
	   * Testing {@link Point2D#setValues(Point2D)}
	   */
	  @Test
	  public void setValuesPoint2DTest() {
	      double[] values = new double[] {1.0d, 2.0d};
		  
		  Point2D p = JeometryFactory.createPoint2D();
		  
		  p.setValues(JeometryFactory.createPoint2D(values[0], values[1]));
		 
		  assertEquals("Invalid X coordinate", values[0], p.getX(), Double.MIN_VALUE);
		  assertEquals("Invalid Y coordinate", values[1], p.getY(), Double.MIN_VALUE);
		  
		  p.setValues(JeometryFactory.createPoint2D(values[1], values[0]));
			 
		  assertEquals("Invalid X coordinate", values[1], p.getX(), Double.MIN_VALUE);
		  assertEquals("Invalid Y coordinate", values[2], p.getY(), Double.MIN_VALUE);
	  }
	  
}
