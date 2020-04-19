package org.jeometry.math.solver;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.MatrixTestData;
import org.jeometry.math.Vector;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A Gauss Elimination based solver test.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * Create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * &nbsp;&nbsp;solver = [the solver to test];<br>
 * }<br>
 * </code>
 * </p>
 * Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>seint
 */
public class GaussEliminationSolverTest {
	
	protected static Solver solver = null;
	
	/**
	 * Initialize the test static context.
	 */
	@BeforeClass
	public static void initClass() {
		fail("method public static void init() has to be set up with @BeforeClass annotation");
	}
	
	/**
	 * Test the underlying solver
	 */
	@Test
	public void solveTest() {
		
		assertNotNull("Solver is not initialized", solver);
		
		Matrix a = JeometryFactory.createMatrix(MatrixTestData.SOLVER_GAUSS_ELIMINATION_A);
		Vector b = JeometryFactory.createVector(MatrixTestData.SOLVER_GAUSS_ELIMINATION_B);
		
		Vector x;
		try {
			x = solver.solve(a, b);

			assertNotNull("No solution found", x);
			
			assertEquals("Invalid x size", a.getRowsCount(), x.getDimension());
			
			assertEquals("Invalid x vector ("+x.getVectorComponent(0)+", "+x.getVectorComponent(1)+", "+x.getVectorComponent(2)+")", MatrixTestData.SOLVER_GAUSS_ELIMINATION_X[0], x.getVectorComponent(0), 0.000000000001d);
			assertEquals("Invalid x vector ("+x.getVectorComponent(0)+", "+x.getVectorComponent(1)+", "+x.getVectorComponent(2)+")", MatrixTestData.SOLVER_GAUSS_ELIMINATION_X[1], x.getVectorComponent(1), 0.000000000001d);
			assertEquals("Invalid x vector ("+x.getVectorComponent(0)+", "+x.getVectorComponent(1)+", "+x.getVectorComponent(2)+")", MatrixTestData.SOLVER_GAUSS_ELIMINATION_X[2], x.getVectorComponent(2), 0.000000000001d);
		} catch (Exception e) {
			fail("Unexpected excpetion "+e.getMessage());
		}
		
		
	}
}
