package org.jeometry.simple.math;


import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.MatrixTest;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.junit.BeforeClass;

/**
 * A test suite dedicated to the {@link SimpleMatrix}.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleMatrixTest extends MatrixTest {

	/**
	 * Initialize the test static context.
	 */
	@BeforeClass
	public static void initClass() {
		
		matrixClass = SimpleMatrix.class;
		
		vectorClass = SimpleVector.class;
		
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());
	}
}
