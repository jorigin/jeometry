package org.jeometry.simple.geom3D.point;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.jeometry.test.geom3D.point.Point3DTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * Unitary tests dedicated to {@link SimplePoint3D} class.
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.0
 *
 */
public class SimplePoint3DTest extends Point3DTest{

  /**
   * Initialize the tests.
   */
  @BeforeAll
  public static void initClass() {
    JeometryFactory.setMathBuilder(new SimpleMathBuilder());
  }
  
}
