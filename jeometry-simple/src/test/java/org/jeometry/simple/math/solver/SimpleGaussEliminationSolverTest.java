package org.jeometry.simple.math.solver;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.jeometry.test.math.solver.GaussEliminationSolverTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * A Gauss Elimination based solver test.
 * Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>seint
 */
public class SimpleGaussEliminationSolverTest extends GaussEliminationSolverTest{
	
	/**
	 * Initialize the test static context.
	 */
	@BeforeAll
	public static void initClass() {
		
		solver = new SimpleGaussEliminationSolver();
		
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());
	}
}
