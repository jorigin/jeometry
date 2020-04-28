package org.jeometry.test.geom3D.point;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.test.math.VectorTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
  @BeforeAll
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
    assertEquals(crossResult.getX(), crossResult.getX(), 0.0d, "cross operator: Unexpected X value");
    assertEquals(crossResult.getY(), crossResult.getY(), 0.0d, "cross operator: Unexpected Y value");
    assertEquals(crossResult.getZ(), crossResult.getZ(), 0.0d, "cross operator: Unexpected Z value");
    
    resultPoint3D.setX(0);
    resultPoint3D.setY(0);
    resultPoint3D.setZ(0);
    Point3D reference = pointA.cross(pointB, resultPoint3D);
    assertEquals(resultPoint3D, reference, "cross operator: Unexpected return value");
    assertEquals(crossResult.getX(), resultPoint3D.getX(), 0.0d, "cross operator: Unexpected X value");
    assertEquals(crossResult.getY(), resultPoint3D.getY(), 0.0d, "cross operator: Unexpected Y value");
    assertEquals(crossResult.getZ(), resultPoint3D.getZ(), 0.0d, "cross operator: Unexpected Z value");
    
    resultDouble = pointA.dot(pointB);
    assertEquals(dotResult, resultDouble, 0.0d, "cross operator: Unexpected value");

    resultDouble = pointA.normSquare();
    assertEquals(normSquareResult, resultDouble, 0.0d, "norm squared operator: Unexpected value");
    
    resultDouble = pointA.norm();
    assertEquals(Math.sqrt(normSquareResult), resultDouble, 0.0d, "norm operator: Unexpected value");
    
    resultPoint3D = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D.normalize();
    assertEquals(normalizedResult.getX(), resultPoint3D.getX(), 0.0d, "normalize operator: Unexpected X value");
    assertEquals(normalizedResult.getY(), resultPoint3D.getY(), 0.0d, "normalize operator: Unexpected Y value");
    assertEquals(normalizedResult.getZ(), resultPoint3D.getZ(), 0.0d, "normalize operator: Unexpected Z value");
    
    resultPoint3D = pointA.plus(pointB);
    assertEquals(plusResult.getX(), resultPoint3D.getX(), 0.0d, "plus point operator: Unexpected X value");
    assertEquals(plusResult.getY(), resultPoint3D.getY(), 0.0d, "plus point operator: Unexpected Y value");
    assertEquals(plusResult.getZ(), resultPoint3D.getZ(), 0.0d, "plus point operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.plus(pointB, resultPoint3DB);
    assertEquals(plusResult.getX(), resultPoint3D.getX(), 0.0d, "plus point result operator: Unexpected X value");
    assertEquals(plusResult.getY(), resultPoint3D.getY(), 0.0d, "plus point result operator: Unexpected Y value");
    assertEquals(plusResult.getZ(), resultPoint3D.getZ(), 0.0d, "plus point result operator: Unexpected Z value");
    assertEquals(plusResult.getX(), resultPoint3DB.getX(), 0.0d, "plus point result operator: Unexpected X value");
    assertEquals(plusResult.getY(), resultPoint3DB.getY(), 0.0d, "plus point result operator: Unexpected Y value");
    assertEquals(plusResult.getZ(), resultPoint3DB.getZ(), 0.0d, "plus point result operator: Unexpected Z value");
    
    resultPoint3D = pointA.plus(scalar);
    assertEquals(plusScalarResult.getX(), resultPoint3D.getX(), 0.0d, "plus point result operator: Unexpected X value");
    assertEquals(plusScalarResult.getY(), resultPoint3D.getY(), 0.0d, "plus point result operator: Unexpected Y value");
    assertEquals(plusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "plus point result operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.plus(scalar, resultPoint3DB);
    assertEquals(plusScalarResult.getX(), resultPoint3D.getX(), 0.0d, "plus point result operator: Unexpected X value");
    assertEquals(plusScalarResult.getY(), resultPoint3D.getY(), 0.0d, "plus point result operator: Unexpected Y value");
    assertEquals(plusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "plus point result operator: Unexpected Z value");
    assertEquals(plusScalarResult.getX(), resultPoint3DB.getX(), 0.0d, "plus point result operator: Unexpected X value");
    assertEquals(plusScalarResult.getY(), resultPoint3DB.getY(), 0.0d, "plus point result operator: Unexpected Y value");
    assertEquals(plusScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d, "plus point result operator: Unexpected Z value");
    
    resultPoint3D = pointA.minus(pointB);
    assertEquals(minusResult.getX(), resultPoint3D.getX(), 0.0d, "minus point operator: Unexpected X value");
    assertEquals(minusResult.getY(), resultPoint3D.getY(), 0.0d, "minus point operator: Unexpected Y value");
    assertEquals(minusResult.getZ(), resultPoint3D.getZ(), 0.0d, "minus point operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.minus(pointB, resultPoint3DB);
    assertEquals(minusResult.getX(), resultPoint3D.getX(), 0.0d, "minus point result operator: Unexpected X value");
    assertEquals(minusResult.getY(), resultPoint3D.getY(), 0.0d, "minus point result operator: Unexpected Y value");
    assertEquals(minusResult.getZ(), resultPoint3D.getZ(), 0.0d, "minus point result operator: Unexpected Z value");
    assertEquals(minusResult.getX(), resultPoint3DB.getX(), 0.0d, "minus point result operator: Unexpected X value");
    assertEquals(minusResult.getY(), resultPoint3DB.getY(), 0.0d, "minus point result operator: Unexpected Y value");
    assertEquals(minusResult.getZ(), resultPoint3DB.getZ(), 0.0d, "minus point result operator: Unexpected Z value");
    
    resultPoint3D = pointA.minus(scalar);
    assertEquals(minusScalarResult.getX(), resultPoint3D.getX(), 0.0d, "minus point result operator: Unexpected X value");
    assertEquals(minusScalarResult.getY(), resultPoint3D.getY(), 0.0d, "minus point result operator: Unexpected Y value");
    assertEquals(minusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "minus point result operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.minus(scalar, resultPoint3DB);
    assertEquals(minusScalarResult.getX(), resultPoint3D.getX(), 0.0d, "minus point result operator: Unexpected X value");
    assertEquals(minusScalarResult.getY(), resultPoint3D.getY(), 0.0d, "minus point result operator: Unexpected Y value");
    assertEquals(minusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "minus point result operator: Unexpected Z value");
    assertEquals(minusScalarResult.getX(), resultPoint3DB.getX(), 0.0d, "minus point result operator: Unexpected X value");
    assertEquals(minusScalarResult.getY(), resultPoint3DB.getY(), 0.0d, "minus point result operator: Unexpected Y value");
    assertEquals(minusScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d, "minus point result operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.plusAffect(pointB);
    assertEquals(plusResult.getX(), resultPoint3D.getX(), 0.0d, "plus affect point operator: Unexpected X value");
    assertEquals(plusResult.getY(), resultPoint3D.getY(), 0.0d, "plus affect point operator: Unexpected Y value");
    assertEquals(plusResult.getZ(), resultPoint3D.getZ(), 0.0d, "plus affect point operator: Unexpected Z value");
    assertEquals(plusResult.getX(), resultPoint3DB.getX(), 0.0d, "plus affect point operator: Unexpected X value");
    assertEquals(plusResult.getY(), resultPoint3DB.getY(), 0.0d, "plus affect point operator: Unexpected Y value");
    assertEquals(plusResult.getZ(), resultPoint3DB.getZ(), 0.0d, "plus affect point operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.plusAffect(scalar);
    assertEquals(plusScalarResult.getX(), resultPoint3D.getX(), 0.0d, "plus affect point operator: Unexpected X value");
    assertEquals(plusScalarResult.getY(), resultPoint3D.getY(), 0.0d, "plus affect point operator: Unexpected Y value");
    assertEquals(plusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "plus affect point operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.minusAffect(pointB);
    assertEquals(minusResult.getX(), resultPoint3D.getX(), 0.0d, "minus affect point result operator: Unexpected X value");
    assertEquals(minusResult.getY(), resultPoint3D.getY(), 0.0d, "minus affect point result operator: Unexpected Y value");
    assertEquals(minusResult.getZ(), resultPoint3D.getZ(), 0.0d, "minus affect point result operator: Unexpected Z value");
    assertEquals(minusResult.getX(), resultPoint3DB.getX(), 0.0d, "minus affect point result operator: Unexpected X value");
    assertEquals(minusResult.getY(), resultPoint3DB.getY(), 0.0d, "minus affect point result operator: Unexpected Y value");
    assertEquals(minusResult.getZ(), resultPoint3DB.getZ(), 0.0d, "minus affect point result operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.minusAffect(scalar);
    assertEquals(minusScalarResult.getX(), resultPoint3D.getX(), 0.0d, "minus affect point result operator: Unexpected X value");
    assertEquals(minusScalarResult.getY(), resultPoint3D.getY(), 0.0d, "minus affect point result operator: Unexpected Y value");
    assertEquals(minusScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "minus affect point result operator: Unexpected Z value");
    
    resultPoint3D = pointA.multiply(scalar);
    assertEquals(multScalarResult.getX(), resultPoint3D.getX(), 0.0d, "mult scalar operator: Unexpected X value");
    assertEquals(multScalarResult.getY(), resultPoint3D.getY(), 0.0d, "mult scalar operator: Unexpected Y value");
    assertEquals(multScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "mult scalar operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.multiply(scalar, resultPoint3DB);
    assertEquals(multScalarResult.getX(), resultPoint3D.getX(), 0.0d, "mult scalar operator: Unexpected X value");
    assertEquals(multScalarResult.getY(), resultPoint3D.getY(), 0.0d, "mult scalar operator: Unexpected Y value");
    assertEquals(multScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "mult scalar operator: Unexpected Z value");
    assertEquals(multScalarResult.getX(), resultPoint3DB.getX(), 0.0d, "mult scalar operator: Unexpected X value");
    assertEquals(multScalarResult.getY(), resultPoint3DB.getY(), 0.0d, "mult scalar operator: Unexpected Y value");
    assertEquals(multScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d, "mult scalar operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.multiplyAffect(scalar);
    assertEquals(multScalarResult.getX(), resultPoint3D.getX(), 0.0d, "mult scalar operator: Unexpected X value");
    assertEquals(multScalarResult.getY(), resultPoint3D.getY(), 0.0d, "mult scalar operator: Unexpected Y value");
    assertEquals(multScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "mult scalar operator: Unexpected Z value");
    assertEquals(multScalarResult.getX(), resultPoint3DB.getX(), 0.0d, "mult scalar operator: Unexpected X value");
    assertEquals(multScalarResult.getY(), resultPoint3DB.getY(), 0.0d, "mult scalar operator: Unexpected Y value");
    assertEquals(multScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d, "mult scalar operator: Unexpected Z value");

    resultPoint3D = pointA.divide(scalar);
    assertEquals(divScalarResult.getX(), resultPoint3D.getX(), 0.0d, "div scalar operator: Unexpected X value");
    assertEquals(divScalarResult.getY(), resultPoint3D.getY(), 0.0d, "div scalar operator: Unexpected Y value");
    assertEquals(divScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "div scalar operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D();
    resultPoint3D = pointA.divide(scalar, resultPoint3DB);
    assertEquals(divScalarResult.getX(), resultPoint3D.getX(), 0.0d, "div scalar result operator: Unexpected X value");
    assertEquals(divScalarResult.getY(), resultPoint3D.getY(), 0.0d, "div scalar result operator: Unexpected Y value");
    assertEquals(divScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "div scalar result operator: Unexpected Z value");
    assertEquals(divScalarResult.getX(), resultPoint3DB.getX(), 0.0d, "div scalar result operator: Unexpected X value");
    assertEquals(divScalarResult.getY(), resultPoint3DB.getY(), 0.0d, "div scalar result operator: Unexpected Y value");
    assertEquals(divScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d, "div scalar result operator: Unexpected Z value");
    
    resultPoint3DB = JeometryFactory.createPoint3D(pointA.getX(), pointA.getY(), pointA.getZ());
    resultPoint3D = resultPoint3DB.divideAffect(scalar);
    assertEquals(divScalarResult.getX(), resultPoint3D.getX(), 0.0d, "div scalar affect operator: Unexpected X value");
    assertEquals(divScalarResult.getY(), resultPoint3D.getY(), 0.0d, "div scalar affect operator: Unexpected Y value");
    assertEquals(divScalarResult.getZ(), resultPoint3D.getZ(), 0.0d, "div scalar affect operator: Unexpected Z value");
    assertEquals(divScalarResult.getX(), resultPoint3DB.getX(), 0.0d, "div scalar affect operator: Unexpected X value");
    assertEquals(divScalarResult.getY(), resultPoint3DB.getY(), 0.0d, "div scalar affect operator: Unexpected Y value");
    assertEquals(divScalarResult.getZ(), resultPoint3DB.getZ(), 0.0d, "div scalar affect operator: Unexpected Z value");
  }
  
  /**
   * Testing {@link Point3D#setValues(double, double, double)}
   */
  @Test
  public void setValuesXYZTest() {
	  
	  double[] values = new double[] {1.0d, 2.0d, 3.0d};
	  
	  Point3D p = JeometryFactory.createPoint3D();
	  
	  p.setValues(values[0], values[1], values[2]);
	 
	  assertEquals(values[0], p.getX(), Double.MIN_VALUE, "Invalid X coordinate");
	  assertEquals(values[1], p.getY(), Double.MIN_VALUE, "Invalid Y coordinate");
	  assertEquals(values[2], p.getZ(), Double.MIN_VALUE, "Invalid Z coordinate");
	  
	  p.setValues(values[1], values[2], values[0]);
		 
	  assertEquals(values[1], p.getX(), Double.MIN_VALUE, "Invalid X coordinate");
	  assertEquals(values[2], p.getY(), Double.MIN_VALUE, "Invalid Y coordinate");
	  assertEquals(values[0], p.getZ(), Double.MIN_VALUE, "Invalid Z coordinate");
  }
  
  /**
   * Testing {@link Point3D#setValues(Point3D)}
   */
  @Test
  public void setValuesPoint3DTest() {
      double[] values = new double[] {1.0d, 2.0d, 3.0d};
	  
	  Point3D p = JeometryFactory.createPoint3D();
	  
	  p.setValues(JeometryFactory.createPoint3D(values[0], values[1], values[2]));
	 
	  assertEquals(values[0], p.getX(), Double.MIN_VALUE, "Invalid X coordinate");
	  assertEquals(values[1], p.getY(), Double.MIN_VALUE, "Invalid Y coordinate");
	  assertEquals(values[2], p.getZ(), Double.MIN_VALUE, "Invalid Z coordinate");
	  
	  p.setValues(JeometryFactory.createPoint3D(values[1], values[2], values[0]));
		 
	  assertEquals(values[1], p.getX(), Double.MIN_VALUE, "Invalid X coordinate");
	  assertEquals(values[2], p.getY(), Double.MIN_VALUE, "Invalid Y coordinate");
	  assertEquals(values[0], p.getZ(), Double.MIN_VALUE, "Invalid Z coordinate");
  }
}
