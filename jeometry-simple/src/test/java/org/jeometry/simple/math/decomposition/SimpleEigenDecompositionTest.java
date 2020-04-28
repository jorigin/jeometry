package org.jeometry.simple.math.decomposition;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.decomposition.EigenDecomposition;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.jeometry.test.math.decomposition.EigenDecompositionTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * A test suite dedicated to the {@link EigenDecomposition}.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleEigenDecompositionTest extends EigenDecompositionTest{

	/**
	 * Epsilon for numeric comparison.
	 */
	public static double EPSILON = 0.0000001d;
	
	/**
	 * Initialize the test static context.
	 */
	@BeforeAll
	public static void initClass() {
		
		decompositionClass = SimpleEigenDecomposition.class;
		
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());
	}
}
