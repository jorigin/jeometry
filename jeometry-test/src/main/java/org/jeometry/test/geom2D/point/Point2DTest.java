package org.jeometry.test.geom2D.point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.test.math.VectorTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


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
	   * Testing {@link Point2D#setValues(double, double)}
	   */
	  @Test
	  public void setValuesXYZTest() {
		  
		  double[] values = new double[] {1.0d, 2.0d};
		  
		  Point2D p = JeometryFactory.createPoint2D();
		  
		  p.setValues(values[0], values[1]);
		 
		  assertEquals(values[0], p.getX(), Double.MIN_VALUE, "Invalid X coordinate");
		  assertEquals(values[1], p.getY(), Double.MIN_VALUE, "Invalid Y coordinate");
		  
		  p.setValues(values[1], values[0]);
			 
		  assertEquals(values[1], p.getX(), Double.MIN_VALUE, "Invalid X coordinate");
		  assertEquals(values[0], p.getY(), Double.MIN_VALUE, "Invalid Y coordinate");
	  }
	  
	  /**
	   * Testing {@link Point2D#setValues(Point2D)}
	   */
	  @Test
	  public void setValuesPoint2DTest() {
	      double[] values = new double[] {1.0d, 2.0d};
		  
		  Point2D p = JeometryFactory.createPoint2D();
		  
		  p.setValues(JeometryFactory.createPoint2D(values[0], values[1]));
		 
		  assertEquals(values[0], p.getX(), Double.MIN_VALUE, "Invalid X coordinate");
		  assertEquals(values[1], p.getY(), Double.MIN_VALUE, "Invalid Y coordinate");
		  
		  p.setValues(JeometryFactory.createPoint2D(values[1], values[0]));
			 
		  assertEquals(values[1], p.getX(), Double.MIN_VALUE, "Invalid X coordinate");
		  assertEquals(values[0], p.getY(), Double.MIN_VALUE, "Invalid Y coordinate");
	  }
	  
}
