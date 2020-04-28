package org.jeometry.simple.geom2D.point;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.junit.jupiter.api.BeforeAll;

/**
 * Unitary tests dedicated to {@link SimplePoint2D} class.
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.0
 *
 */
public class SimplePoint2DTest{

	  /**
	   * Initialize the tests.
	   */
	  @BeforeAll
	  public static void initClass() {
	    JeometryFactory.setMathBuilder(new SimpleMathBuilder());
	  }
}
