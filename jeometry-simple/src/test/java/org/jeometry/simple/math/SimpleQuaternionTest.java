package org.jeometry.simple.math;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.jeometry.test.math.QuaternionTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * A test suite for the {@link SimpleQuaternion} implementation.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class SimpleQuaternionTest extends QuaternionTest {

	/**
	 * Initialize the test static context.
	 */
	@BeforeAll
	public static void initClass() {
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());
	}
}
