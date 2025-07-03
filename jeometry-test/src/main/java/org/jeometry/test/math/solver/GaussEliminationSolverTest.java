package org.jeometry.test.math.solver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;
import org.jeometry.math.solver.Solver;
import org.jeometry.test.math.MathTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 * */
public class GaussEliminationSolverTest {
	
	/**
	 * The solver to use.
	 */
	protected static Solver solver = null;
	
	/**
	 * Test the underlying solver
	 */
	@Test
	public void solveTest() {
		
		assertNotNull(solver, "Solver is not initialized");
		
		Matrix a = JeometryFactory.createMatrix(MathTestData.SOLVER_GAUSS_ELIMINATION_A);
		Vector b = JeometryFactory.createVector(MathTestData.SOLVER_GAUSS_ELIMINATION_B);
		
		Vector x;
		try {
			x = solver.solve(a, b);

			assertNotNull(x, "No solution found");
			
			assertEquals(a.getRowsCount(), x.getDimension(), "Invalid x size");
			
			assertEquals(MathTestData.SOLVER_GAUSS_ELIMINATION_X[0], x.getValue(0), 0.000000000001d, "Invalid x vector ("+x.getValue(0)+", "+x.getValue(1)+", "+x.getValue(2)+")");
			assertEquals(MathTestData.SOLVER_GAUSS_ELIMINATION_X[1], x.getValue(1), 0.000000000001d, "Invalid x vector ("+x.getValue(0)+", "+x.getValue(1)+", "+x.getValue(2)+")");
			assertEquals(MathTestData.SOLVER_GAUSS_ELIMINATION_X[2], x.getValue(2), 0.000000000001d, "Invalid x vector ("+x.getValue(0)+", "+x.getValue(1)+", "+x.getValue(2)+")");
		} catch (Exception e) {
			fail("Unexpected excpetion "+e.getMessage());
		}
		
		
	}
}
