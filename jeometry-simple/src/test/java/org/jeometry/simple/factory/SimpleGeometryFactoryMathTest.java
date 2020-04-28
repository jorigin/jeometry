package org.jeometry.simple.factory;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.simple.math.SimpleMatrix;
import org.jeometry.simple.math.SimpleQuaternion;
import org.jeometry.simple.math.SimpleVector;
import org.jeometry.test.factory.GeometryFactoryMathTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * A test class for {@link JeometryFactory} methods.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class SimpleGeometryFactoryMathTest extends GeometryFactoryMathTest {

	/**
	 * Test initialization.
	 */
	@BeforeAll
	public static void init() {
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());
		
		matrixClass = SimpleMatrix.class;
		vectorClass = SimpleVector.class;
		quaternionClass = SimpleQuaternion.class;
	}

}
