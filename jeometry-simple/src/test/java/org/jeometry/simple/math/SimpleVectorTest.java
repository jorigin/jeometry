package org.jeometry.simple.math;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.jeometry.test.math.VectorTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * A test suite dedicated to the {@link SimpleVector}.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleVectorTest extends VectorTest {


	/**
	 * Initialize the test static context.
	 */
	@BeforeAll
	public static void initClass() {
		
		vectorClass = SimpleVector.class;
		
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());
	}
}
