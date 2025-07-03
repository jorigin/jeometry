package org.jeometry.simple.geom2D.primitive;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.simple.factory.SimplePrimitiveBuilder;
import org.jeometry.test.geom2D.primitive.Ellipse2DTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * Unitary tests dedicated to {@link SimpleEllipse2D} class.
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.0
 *
 */
public class SimpleEllipse2DTest extends Ellipse2DTest {

	  /**
	   * Initialize the tests.
	   */
	  @BeforeAll
	  public static void initClass() {
	    JeometryFactory.setPrimitiveBuilder(new SimplePrimitiveBuilder());
	  }
}

