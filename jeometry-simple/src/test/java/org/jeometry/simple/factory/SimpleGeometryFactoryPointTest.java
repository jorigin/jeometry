package org.jeometry.simple.factory;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.test.factory.GeometryFactoryPointTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * A test class for {@link JeometryFactory} methods.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.4
 */
public class SimpleGeometryFactoryPointTest extends GeometryFactoryPointTest{

	/**
	 * Test initialization.
	 */
	@BeforeAll
	public static void init() {
		JeometryFactory.setPointBuilder(new SimplePointBuilder());
	}
}
