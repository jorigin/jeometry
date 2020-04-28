package org.jeometry.simple.factory;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.simple.math.SimpleMatrix;
import org.jeometry.simple.math.SimpleQuaternion;
import org.jeometry.simple.math.SimpleVector;
import org.jeometry.test.factory.MathBuilderTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * A test suite for {@link SimpleMathBuilder} class.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleMathBuilderTest extends MathBuilderTest {

	/**
	 * Test initialization.
	 */
	@BeforeAll
	public static void init() {
		mathBuilder = new SimpleMathBuilder();
		
		matrixClass = SimpleMatrix.class;
		vectorClass = SimpleVector.class;
		quaternionClass = SimpleQuaternion.class;
		
		JeometryFactory.setMathBuilder(mathBuilder);
	}
}
