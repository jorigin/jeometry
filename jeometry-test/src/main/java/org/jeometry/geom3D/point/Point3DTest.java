package org.jeometry.geom3D.point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.VectorTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unitary tests dedicated to {@link Point3D} implementations.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * Create a test class that extends this one
 * </p>
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.0
 *
 */
public class Point3DTest extends VectorTest {

  /**
   * Initialize the tests.
   */
  @BeforeClass
  public static void initClass() {
	  fail("method public static void init() has to be set up with @BeforeClass annotation");
  }
  
  /**
   * Test Simple Point 3D operations.
   */
  @Test
  public void simplePoint3DOperationTest(){
    
    double xARef =    1.35965781d;
    double yARef =  100.69845235d;
    double zARef =  -89.45630147d;

    double xBRef = -236.25697136d;
    double yBRef =  192.16810102d;
    double zBRef =    0.00056989d;   
    
    Point3D pointA = JeometryFactory.createPoint3D(xARef, yARef, zARef);
    Point3D pointB = JeometryFactory.createPoint3D(xBRef, yBRef, zBRef);
    
    Point3D crossResult = JeometryFactory.createPoint3D( pointA.getY() * pointB.getZ() - pointA.getZ() * pointB.getY(),
                                                  -pointA.getX() * pointB.getZ() + pointA.getZ() * pointB.getX(),
                                                   pointA.getX() * pointB.getY() - pointA.getY() * pointB.getX());
    
    double dotResult         =  pointA.getX() * pointB.getX() + pointA.getY() * pointB.getY() + pointA.getZ() * pointB.getZ();
    
    double normSquareResult  =   pointA.getX() * pointA.getX() + pointA.getY() * pointA.getY() + pointA.getZ() * pointA.getZ();
    
    double normResult        = Math.sqrt(normSquareResult);
    
    Point3D normalizedResult =  JeometryFactory.createPoint3D();
    normalizedResult.setX(pointA.getX() / normResult);
    normalizedResult.setY(pointA.getY() / normResult);
    normalizedResult.setZ(pointA.getZ() / normResult);
    
    Point3D plusResult = JeometryFactory.createPoint3D( pointA.getX() + pointB.getX(),
    		                                pointA.getY() + pointB.getY(),
                                            pointA.getZ() + pointB.getZ());
    
    Point3D minusResult = JeometryFactory.createPoint3D( pointA.getX() - pointB.getX(),
                                             pointA.getY() - pointB.getY(),
                                             pointA.getZ() - pointB.getZ());
    
    double scalar = 2.368d;
    
    Point3D plusScalarResult = JeometryFactory.createPoint3D( pointA.getX() + scalar,
                                                  pointA.getY() + scalar,
                                                  pointA.getZ() + scalar);
    
    Point3D minusScalarResult = JeometryFactory.createPoint3D( pointA.getX() - scalar,
                                                   pointA.getY() - scalar,
                                                   pointA.getZ() - scalar);
    
    Point3D multScalarResult = JeometryFactory.createPoint3D( pointA.getX() * scalar,
                                                  pointA.getY() * scalar,
                                                  pointA.getZ() * scalar);
    
    Point3D divScalarResult = JeometryFactory.createPoint3D( pointA.getX() / scalar,
                                                 pointA.getY() / scalar,
                                                 pointA.getZ() / scalar);
    
    Point3D resultPoint3D  = null;
    Point3D resultPoint3DB = null;
    double resultDouble   = Double.NaN;
    
    resultPoint3D = pointA.cross(pointB);
    assertEquals("cross operator: Unexpected X value", crossResult.getX(), crossResult.getX(), 0.0d);
    assertEquals("cross operator: Unexpected Y value", crossResult.getY(), crossResult.getY(), 0.0d);
    assertEquals("cross operator: Unexpected Z value", crossResult.getZ(), crossResult.getZ(), 0.0d);
    
    resultPoint3D.setX(0);
    resultPoint3D.setY(0);
    resultPoint3D.setZ(0);
    Point3D reference = pointA.cross(pointB, resultPoint3D);
    assertEquals("cross operator: Unexpected return value", resultPoint3D, reference);
    assertEquals("cross operator: Unexpected X value", crossResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("cross operator: Unexpected Y value", crossResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("cross operator: Unexpected Z value", crossResult.getZ(), resultPoint3D.getZ(), 0.0d);
    
    resultDouble = pointA.dot(pointB);
    assertEquals("cross operator: Unexpected value", dotResult, resultDouble, 0.0d);

    resultDouble = pointA.normSquare();
    assertEquals("norm squared operator: Unexpected value", normSquareResult, resultDouble, 0.0d);
    
    resultDouble = pointA.norm();
    assertEquals("norm operator: Unexpected value", Math.sqrt(normSquareResult), resultDouble, 0.0d);
    
    resultPoint3D = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D.normalize();
    assertEquals("normalize operator: Unexpected X value", normalizedResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("normalize operator: Unexpected Y value", normalizedResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("normalize operator: Unexpected Z value", normalizedResult.getZ(), resultPoint3D.getZ(), 0.0d);
    
    resultPoint3D = pointA.plus(pointB);
    assertEquals("plus point operator: Unexpected X value", plusResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("plus point operator: Unexpected Y value", plusResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("plus point operator: Unexpected Z value", plusResult.getZ(), resultPoint3D.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.plus(pointB, resultPoint3DB);
    assertEquals("plus point result operator: Unexpected X value", plusResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("plus point result operator: Unexpected Y value", plusResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("plus point result operator: Unexpected Z value", plusResult.getZ(), resultPoint3D.getZ(), 0.0d);
    assertEquals("plus point result operator: Unexpected X value", plusResult.getX(), resultPoint3DB.getX(), 0.0d);
    assertEquals("plus point result operator: Unexpected Y value", plusResult.getY(), resultPoint3DB.getY(), 0.0d);
    assertEquals("plus point result operator: Unexpected Z value", plusResult.getZ(), resultPoint3DB.getZ(), 0.0d);
    
    resultPoint3D = pointA.plus(scalar);
    assertEquals("plus point result operator: Unexpected X value", plusScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("plus point result operator: Unexpected Y value", plusScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("plus point result operator: Unexpected Z value", plusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.plus(scalar, resultPoint3DB);
    assertEquals("plus point result operator: Unexpected X value", plusScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("plus point result operator: Unexpected Y value", plusScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("plus point result operator: Unexpected Z value", plusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    assertEquals("plus point result operator: Unexpected X value", plusScalarResult.getX(), resultPoint3DB.getX(), 0.0d);
    assertEquals("plus point result operator: Unexpected Y value", plusScalarResult.getY(), resultPoint3DB.getY(), 0.0d);
    assertEquals("plus point result operator: Unexpected Z value", plusScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d);
    
    resultPoint3D = pointA.minus(pointB);
    assertEquals("minus point operator: Unexpected X value", minusResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("minus point operator: Unexpected Y value", minusResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("minus point operator: Unexpected Z value", minusResult.getZ(), resultPoint3D.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.minus(pointB, resultPoint3DB);
    assertEquals("minus point result operator: Unexpected X value", minusResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("minus point result operator: Unexpected Y value", minusResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("minus point result operator: Unexpected Z value", minusResult.getZ(), resultPoint3D.getZ(), 0.0d);
    assertEquals("minus point result operator: Unexpected X value", minusResult.getX(), resultPoint3DB.getX(), 0.0d);
    assertEquals("minus point result operator: Unexpected Y value", minusResult.getY(), resultPoint3DB.getY(), 0.0d);
    assertEquals("minus point result operator: Unexpected Z value", minusResult.getZ(), resultPoint3DB.getZ(), 0.0d);
    
    resultPoint3D = pointA.minus(scalar);
    assertEquals("minus point result operator: Unexpected X value", minusScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("minus point result operator: Unexpected Y value", minusScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("minus point result operator: Unexpected Z value", minusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.minus(scalar, resultPoint3DB);
    assertEquals("minus point result operator: Unexpected X value", minusScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("minus point result operator: Unexpected Y value", minusScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("minus point result operator: Unexpected Z value", minusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    assertEquals("minus point result operator: Unexpected X value", minusScalarResult.getX(), resultPoint3DB.getX(), 0.0d);
    assertEquals("minus point result operator: Unexpected Y value", minusScalarResult.getY(), resultPoint3DB.getY(), 0.0d);
    assertEquals("minus point result operator: Unexpected Z value", minusScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.plusAffect(pointB);
    assertEquals("plus affect point operator: Unexpected X value", plusResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("plus affect point operator: Unexpected Y value", plusResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("plus affect point operator: Unexpected Z value", plusResult.getZ(), resultPoint3D.getZ(), 0.0d);
    assertEquals("plus affect point operator: Unexpected X value", plusResult.getX(), resultPoint3DB.getX(), 0.0d);
    assertEquals("plus affect point operator: Unexpected Y value", plusResult.getY(), resultPoint3DB.getY(), 0.0d);
    assertEquals("plus affect point operator: Unexpected Z value", plusResult.getZ(), resultPoint3DB.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.plusAffect(scalar);
    assertEquals("plus affect point operator: Unexpected X value", plusScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("plus affect point operator: Unexpected Y value", plusScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("plus affect point operator: Unexpected Z value", plusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.minusAffect(pointB);
    assertEquals("minus affect point result operator: Unexpected X value", minusResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("minus affect point result operator: Unexpected Y value", minusResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("minus affect point result operator: Unexpected Z value", minusResult.getZ(), resultPoint3D.getZ(), 0.0d);
    assertEquals("minus affect point result operator: Unexpected X value", minusResult.getX(), resultPoint3DB.getX(), 0.0d);
    assertEquals("minus affect point result operator: Unexpected Y value", minusResult.getY(), resultPoint3DB.getY(), 0.0d);
    assertEquals("minus affect point result operator: Unexpected Z value", minusResult.getZ(), resultPoint3DB.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.minusAffect(scalar);
    assertEquals("minus affect point result operator: Unexpected X value", minusScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("minus affect point result operator: Unexpected Y value", minusScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("minus affect point result operator: Unexpected Z value", minusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    
    resultPoint3D = pointA.mult(scalar);
    assertEquals("mult scalar operator: Unexpected X value", multScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("mult scalar operator: Unexpected Y value", multScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("mult scalar operator: Unexpected Z value", multScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.mult(scalar, resultPoint3DB);
    assertEquals("mult scalar operator: Unexpected X value", multScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("mult scalar operator: Unexpected Y value", multScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("mult scalar operator: Unexpected Z value", multScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    assertEquals("mult scalar operator: Unexpected X value", multScalarResult.getX(), resultPoint3DB.getX(), 0.0d);
    assertEquals("mult scalar operator: Unexpected Y value", multScalarResult.getY(), resultPoint3DB.getY(), 0.0d);
    assertEquals("mult scalar operator: Unexpected Z value", multScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.multAffect(scalar);
    assertEquals("mult scalar operator: Unexpected X value", multScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("mult scalar operator: Unexpected Y value", multScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("mult scalar operator: Unexpected Z value", multScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    assertEquals("mult scalar operator: Unexpected X value", multScalarResult.getX(), resultPoint3DB.getX(), 0.0d);
    assertEquals("mult scalar operator: Unexpected Y value", multScalarResult.getY(), resultPoint3DB.getY(), 0.0d);
    assertEquals("mult scalar operator: Unexpected Z value", multScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d);

    resultPoint3D = pointA.divide(scalar);
    assertEquals("div scalar operator: Unexpected X value", divScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("div scalar operator: Unexpected Y value", divScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("div scalar operator: Unexpected Z value", divScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.divide(scalar, resultPoint3DB);
    assertEquals("div scalar result operator: Unexpected X value", divScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("div scalar result operator: Unexpected Y value", divScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("div scalar result operator: Unexpected Z value", divScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    assertEquals("div scalar result operator: Unexpected X value", divScalarResult.getX(), resultPoint3DB.getX(), 0.0d);
    assertEquals("div scalar result operator: Unexpected Y value", divScalarResult.getY(), resultPoint3DB.getY(), 0.0d);
    assertEquals("div scalar result operator: Unexpected Z value", divScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d);
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.divideAffect(scalar);
    assertEquals("div scalar affect operator: Unexpected X value", divScalarResult.getX(), resultPoint3D.getX(), 0.0d);
    assertEquals("div scalar affect operator: Unexpected Y value", divScalarResult.getY(), resultPoint3D.getY(), 0.0d);
    assertEquals("div scalar affect operator: Unexpected Z value", divScalarResult.getZ(), resultPoint3D.getZ(), 0.0d);
    assertEquals("div scalar affect operator: Unexpected X value", divScalarResult.getX(), resultPoint3DB.getX(), 0.0d);
    assertEquals("div scalar affect operator: Unexpected Y value", divScalarResult.getY(), resultPoint3DB.getY(), 0.0d);
    assertEquals("div scalar affect operator: Unexpected Z value", divScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d);
  }
  
  /**
   * Testing {@link Point3D#setValues(double, double, double)}
   */
  @Test
  public void setValuesXYZTest() {
	  
	  double[] values = new double[] {1.0d, 2.0d, 3.0d};
	  
	  Point3D p = JeometryFactory.createPoint3D();
	  
	  p.setValues(values[0], values[1], values[2]);
	 
	  assertEquals("Invalid X coordinate", values[0], p.getX(), Double.MIN_VALUE);
	  assertEquals("Invalid Y coordinate", values[1], p.getY(), Double.MIN_VALUE);
	  assertEquals("Invalid Z coordinate", values[2], p.getZ(), Double.MIN_VALUE);
	  
	  p.setValues(values[1], values[2], values[0]);
		 
	  assertEquals("Invalid X coordinate", values[1], p.getX(), Double.MIN_VALUE);
	  assertEquals("Invalid Y coordinate", values[2], p.getY(), Double.MIN_VALUE);
	  assertEquals("Invalid Z coordinate", values[0], p.getZ(), Double.MIN_VALUE);
  }
  
  /**
   * Testing {@link Point3D#setValues(Point3D)}
   */
  @Test
  public void setValuesPoint3DTest() {
      double[] values = new double[] {1.0d, 2.0d, 3.0d};
	  
	  Point3D p = JeometryFactory.createPoint3D();
	  
	  p.setValues(JeometryFactory.createPoint3D(values[0], values[1], values[2]));
	 
	  assertEquals("Invalid X coordinate", values[0], p.getX(), Double.MIN_VALUE);
	  assertEquals("Invalid Y coordinate", values[1], p.getY(), Double.MIN_VALUE);
	  assertEquals("Invalid Z coordinate", values[2], p.getZ(), Double.MIN_VALUE);
	  
	  p.setValues(JeometryFactory.createPoint3D(values[1], values[2], values[0]));
		 
	  assertEquals("Invalid X coordinate", values[1], p.getX(), Double.MIN_VALUE);
	  assertEquals("Invalid Y coordinate", values[2], p.getY(), Double.MIN_VALUE);
	  assertEquals("Invalid Z coordinate", values[0], p.getZ(), Double.MIN_VALUE);
  }
}
