package org.jeometry.simple.factory;

import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.factory.MathBuilderTest;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.jeometry.simple.math.SimpleMatrix;
import org.jeometry.simple.math.SimpleQuaternion;
import org.jeometry.simple.math.SimpleVector;
import org.junit.BeforeClass;

/**
 * A test suite for {@link SimpleMathBuilder} class.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public class SimpleMathBuilderTest extends MathBuilderTest {

	/**
	 * Test initialization.
	 */
	@BeforeClass
	public static void init() {
		mathBuilder = new SimpleMathBuilder();
		
		matrixClass = SimpleMatrix.class;
		vectorClass = SimpleVector.class;
		quaternionClass = SimpleQuaternion.class;
		
		GeometryFactory.setMathBuilder(mathBuilder);
	}
}
