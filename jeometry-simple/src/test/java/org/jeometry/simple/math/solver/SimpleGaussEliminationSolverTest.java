package org.jeometry.simple.math.solver;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.solver.GaussEliminationSolverTest;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.junit.BeforeClass;

/**
 * A Gauss Elimination based solver test.
 * Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>seint
 */
public class SimpleGaussEliminationSolverTest extends GaussEliminationSolverTest{
	
	/**
	 * Initialize the test static context.
	 */
	@BeforeClass
	public static void initClass() {
		
		solver = new SimpleGaussEliminationSolver();
		
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());
	}
}
