package org.jeometry.simple.math.decomposition;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.decomposition.SVDDecomposition;
import org.jeometry.math.decomposition.SVDDecompositionTest;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.junit.BeforeClass;

/**
 * A test suite dedicated to the {@link SVDDecomposition}.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleSVDDecompositionTest extends SVDDecompositionTest{

	/**
	 * Initialize the test static context.
	 */
	@BeforeClass
	public static void initClass() {
		
		decompositionClass = SimpleSVDDecomposition.class;
		
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());
	}
}
