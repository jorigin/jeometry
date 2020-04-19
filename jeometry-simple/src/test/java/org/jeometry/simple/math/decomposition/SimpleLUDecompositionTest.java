package org.jeometry.simple.math.decomposition;


import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.decomposition.LUDecomposition;
import org.jeometry.math.decomposition.LUDecompositionTest;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.junit.BeforeClass;


/**
 * A test suite dedicated to the {@link LUDecomposition}.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleLUDecompositionTest extends LUDecompositionTest {

	/**
	 * Epsilon for numeric comparison.
	 */
	public static double EPSILON = 0.0000001d;
	
	/**
	 * Initialize the test static context.
	 */
	@BeforeClass
	public static void initClass() {
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());
	}
}
