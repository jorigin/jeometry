package org.jeometry.simple.factory;

import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.factory.GeometryFactoryMathTest;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.jeometry.simple.math.SimpleMatrix;
import org.jeometry.simple.math.SimpleQuaternion;
import org.jeometry.simple.math.SimpleVector;
import org.junit.BeforeClass;

/**
 * A test class for {@link GeometryFactory} methods.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 *
 */
public class SimpleGeometryFactoryMathTest extends GeometryFactoryMathTest {

	/**
	 * Test initialization.
	 */
	@BeforeClass
	public static void init() {
		GeometryFactory.setMathBuilder(new SimpleMathBuilder());
		
		matrixClass = SimpleMatrix.class;
		vectorClass = SimpleVector.class;
		quaternionClass = SimpleQuaternion.class;
	}

}
