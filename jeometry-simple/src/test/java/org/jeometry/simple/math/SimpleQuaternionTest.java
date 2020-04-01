package org.jeometry.simple.math;

import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.junit.BeforeClass;

/**
 * A test suite for the {@link SimpleQuaternion} implementation.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} b{@value Geometry#BUILD}
 * @since 1.0.0
 */
public class SimpleQuaternionTest {

	/**
	 * Initialize the test static context.
	 */
	@BeforeClass
	public static void initClass() {
		GeometryFactory.setMathBuilder(new SimpleMathBuilder());
	}
}
