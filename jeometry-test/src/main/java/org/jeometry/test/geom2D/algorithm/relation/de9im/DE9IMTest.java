package org.jeometry.test.geom2D.algorithm.relation.de9im;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.algorithm.relation.de9im.DE9IM;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.Ellipse2D;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.LineString2D;
import org.jeometry.math.Matrix;
import org.junit.jupiter.api.Test;

/**
 * A test class for the {@link DE9IM} matrix computation.
 * <br>
 * <b>Usage:</b>
 * <p>
 * Create a class that extends this one and add the method:
 * <pre>
 * &#64;BeforeAll
 * public static void initClass() {
 *     JeometryFactory.setMathBuilder(new ...);
 *     JeometryFactory.setPointBuilder(new ...);
 *     JeometryFactory.setPrimitiveBuilder(new ...);
 * }
 * </pre>
 *
 * @author Julien SEINTURIER - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
public class DE9IMTest {

	/**
	 * The dimension of an empty geometry.
	 */
	protected static final int X = DE9IM.DIM_EMPTY;

	/**
	 * The dimension of a point geometry.
	 */
	protected static final int P = DE9IM.DIM_POINT;

	/**
	 * The dimension of a line geometry.
	 */
	protected static final int L = DE9IM.DIM_LINE;

	/**
	 * The dimension of an area geometry.
	 */
	protected static final int A = DE9IM.DIM_AREA;

	/**
	 * Create the test.
	 */
	public DE9IMTest() {}

	// ---------- Point / Point ----------

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with same points.
	 */
	@Test
	public void pointPointEqualTest() {
		Point2D a = JeometryFactory.createPoint2D(1.0, 2.0);
		Point2D b = JeometryFactory.createPoint2D(1.0, 2.0);
		assertMatrix(new int[][]{
				{ P, X, X },
				{ X, X, X },
				{ X, X, A }
		}, DE9IM.computeMatrix(a, b));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with different {@link Point2D points}.
	 */
	@Test
	public void pointPointDistinctTest() {
		Point2D a = JeometryFactory.createPoint2D(0.0, 0.0);
		Point2D b = JeometryFactory.createPoint2D(1.0, 1.0);
		assertMatrix(new int[][]{
				{ X, X, P },
				{ X, X, X },
				{ P, X, A }
		}, DE9IM.computeMatrix(a, b));
	}

	// ---------- Point / Line ----------

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with a {@link Line2D line} and a {@link Point2D point}.
	 */
	@Test
	public void pointOnLineInteriorTest() {
		Line2D line = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(0.0, 0.0),
				JeometryFactory.createPoint2D(2.0, 0.0));
		Point2D mid = JeometryFactory.createPoint2D(1.0, 0.0);
		assertMatrix(new int[][]{
				{ P, X, X },
				{ X, X, X },
				{ L, P, A }
		}, DE9IM.computeMatrix(mid, line));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with a {@link Line2D line} and a {@link Point2D point} that is located at the line origin.
	 */
	@Test
	public void pointAtLineEndpointTest() {
		Line2D line = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(0.0, 0.0),
				JeometryFactory.createPoint2D(2.0, 0.0));
		Point2D end = JeometryFactory.createPoint2D(0.0, 0.0);
		assertMatrix(new int[][]{
				{ X, P, X },
				{ X, X, X },
				{ L, P, A }
		}, DE9IM.computeMatrix(end, line));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with a {@link Line2D line} and a {@link Point2D point} that is located outside the line.
	 */
	@Test
	public void pointOffLineTest() {
		Line2D line = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(0.0, 0.0),
				JeometryFactory.createPoint2D(2.0, 0.0));
		Point2D away = JeometryFactory.createPoint2D(5.0, 5.0);
		assertMatrix(new int[][]{
				{ X, X, P },
				{ X, X, X },
				{ L, P, A }
		}, DE9IM.computeMatrix(away, line));
	}

	// ---------- Point / Box ----------

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with a {@link Point2D point} and a {@link Box2D box}.
	 * The point is located inside the box.
	 */
	@Test
	public void pointInsideBoxTest() {
		Box2D box = JeometryFactory.createBox2D(0.0, 0.0, 10.0, 10.0);
		Point2D inside = JeometryFactory.createPoint2D(5.0, 5.0);
		assertMatrix(new int[][]{
				{ P, X, X },
				{ X, X, X },
				{ A, L, A }
		}, DE9IM.computeMatrix(inside, box));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with a {@link Point2D point} and a {@link Box2D box}.
	 * The point is located on the edge of the box.
	 */
	@Test
	public void pointOnBoxBoundaryTest() {
		Box2D box = JeometryFactory.createBox2D(0.0, 0.0, 10.0, 10.0);
		Point2D edge = JeometryFactory.createPoint2D(5.0, 0.0);
		assertMatrix(new int[][]{
				{ X, P, X },
				{ X, X, X },
				{ A, L, A }
		}, DE9IM.computeMatrix(edge, box));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with a {@link Point2D point} and a {@link Box2D box}.
	 * The point is located outside the box.
	 */
	@Test
	public void pointOutsideBoxTest() {
		Box2D box = JeometryFactory.createBox2D(0.0, 0.0, 10.0, 10.0);
		Point2D outside = JeometryFactory.createPoint2D(20.0, 20.0);
		assertMatrix(new int[][]{
				{ X, X, P },
				{ X, X, X },
				{ A, L, A }
		}, DE9IM.computeMatrix(outside, box));
	}

	// ---------- Line / Line ----------

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with two {@link Line2D lines}.
	 * The lines are not intersecting.
	 */
	@Test
	public void linesDisjointTest() {
		Line2D a = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(0.0, 0.0),
				JeometryFactory.createPoint2D(1.0, 0.0));
		Line2D b = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(10.0, 0.0),
				JeometryFactory.createPoint2D(11.0, 0.0));
		assertMatrix(new int[][]{
				{ X, X, L },
				{ X, X, P },
				{ L, P, A }
		}, DE9IM.computeMatrix(a, b));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with two {@link Line2D lines}.
	 * The lines are transverse cross.
	 */
	@Test
	public void linesTransverseCrossTest() {
		Line2D a = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(0.0, 0.0),
				JeometryFactory.createPoint2D(2.0, 0.0));
		Line2D b = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(1.0, -1.0),
				JeometryFactory.createPoint2D(1.0, 1.0));
		assertMatrix(new int[][]{
				{ P, X, L },
				{ X, X, P },
				{ L, P, A }
		}, DE9IM.computeMatrix(a, b));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with two {@link Line2D lines}.
	 * The lines share an endpoint.
	 */
	@Test
	public void linesShareEndpointTest() {
		Line2D a = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(0.0, 0.0),
				JeometryFactory.createPoint2D(1.0, 0.0));
		Line2D b = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(1.0, 0.0),
				JeometryFactory.createPoint2D(2.0, 0.0));
		assertMatrix(new int[][]{
				{ X, X, L },
				{ X, P, P },
				{ L, P, A }
		}, DE9IM.computeMatrix(a, b));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with two {@link Line2D lines}.
	 * The lines are the same.
	 */
	@Test
	public void linesEqualTest() {
		Line2D a = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(0.0, 0.0),
				JeometryFactory.createPoint2D(1.0, 0.0));
		Line2D b = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(0.0, 0.0),
				JeometryFactory.createPoint2D(1.0, 0.0));
		assertMatrix(new int[][]{
				{ L, X, X },
				{ X, P, X },
				{ X, X, A }
		}, DE9IM.computeMatrix(a, b));
	}

	// ---------- LineString / Box ----------

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with a {@link Line2D line} and a {@link Box2D box}.
	 * The line is crossing the box.
	 */
	@Test
	public void lineCrossesBoxTest() {
		Box2D box = JeometryFactory.createBox2D(0.0, 0.0, 10.0, 10.0);
		Line2D line = JeometryFactory.createLine2D(
				JeometryFactory.createPoint2D(-5.0, 5.0),
				JeometryFactory.createPoint2D(15.0, 5.0));
		Matrix m = DE9IM.computeMatrix(line, box);
		// I(line) crosses I(box) on a 1D arc, exits at boundary points; B(line) is outside the box.
		assertEquals(L, (int) m.getValue(DE9IM.I, DE9IM.I), "I(line) ∩ I(box)");
		assertEquals(P, (int) m.getValue(DE9IM.I, DE9IM.B), "I(line) ∩ B(box)");
		assertEquals(L, (int) m.getValue(DE9IM.I, DE9IM.E), "I(line) ∩ E(box)");
		assertEquals(P, (int) m.getValue(DE9IM.B, DE9IM.E), "B(line) ∩ E(box)");
		assertEquals(A, (int) m.getValue(DE9IM.E, DE9IM.I), "E(line) ∩ I(box)");
		assertEquals(L, (int) m.getValue(DE9IM.E, DE9IM.B), "E(line) ∩ B(box)");
		assertEquals(A, (int) m.getValue(DE9IM.E, DE9IM.E), "E ∩ E");
	}

	// ---------- Box / Box ----------

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with two {@link Box2D boxes}.
	 * The boxes are disjoint.
	 */
	@Test
	public void boxesDisjointTest() {
		Box2D a = JeometryFactory.createBox2D(0.0, 0.0, 1.0, 1.0);
		Box2D b = JeometryFactory.createBox2D(10.0, 10.0, 1.0, 1.0);
		assertMatrix(new int[][]{
				{ X, X, A },
				{ X, X, L },
				{ A, L, A }
		}, DE9IM.computeMatrix(a, b));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with two {@link Box2D boxes}.
	 * The boxes are the same.
	 */
	@Test
	public void boxesEqualTest() {
		Box2D a = JeometryFactory.createBox2D(0.0, 0.0, 5.0, 5.0);
		Box2D b = JeometryFactory.createBox2D(0.0, 0.0, 5.0, 5.0);
		assertMatrix(new int[][]{
				{ A, X, X },
				{ X, L, X },
				{ X, X, A }
		}, DE9IM.computeMatrix(a, b));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with two {@link Box2D boxes}.
	 * The first box contains the second one.
	 */
	@Test
	public void boxContainsBoxTest() {
		Box2D outer = JeometryFactory.createBox2D(0.0, 0.0, 10.0, 10.0);
		Box2D inner = JeometryFactory.createBox2D(2.0, 2.0, 4.0, 4.0);
		// inner is strictly inside outer interior, no boundary contact
		Matrix m = DE9IM.computeMatrix(outer, inner);
		assertEquals(A, (int) m.getValue(DE9IM.I, DE9IM.I), "I(outer) ∩ I(inner)");
		assertEquals(L, (int) m.getValue(DE9IM.I, DE9IM.B), "I(outer) ∩ B(inner)");
		assertEquals(A, (int) m.getValue(DE9IM.I, DE9IM.E), "I(outer) ∩ E(inner) — outer extends beyond inner");
		assertEquals(X, (int) m.getValue(DE9IM.B, DE9IM.I), "B(outer) ∩ I(inner) — outer ring is far from inner");
		assertEquals(X, (int) m.getValue(DE9IM.B, DE9IM.B), "B(outer) ∩ B(inner) — boundaries do not touch");
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with two {@link Box2D boxes}.
	 * The first box touches (meets) the second.
	 */
	@Test
	public void boxesTouchEdgeTest() {
		Box2D a = JeometryFactory.createBox2D(0.0, 0.0, 5.0, 5.0);
		Box2D b = JeometryFactory.createBox2D(5.0, 0.0, 5.0, 5.0);
		// Boxes touch on a shared edge (a's right edge is b's left edge).
		Matrix m = DE9IM.computeMatrix(a, b);
		assertEquals(L, (int) m.getValue(DE9IM.B, DE9IM.B), "B(a) ∩ B(b) (shared edge)");
		assertEquals(X, (int) m.getValue(DE9IM.I, DE9IM.I), "I(a) ∩ I(b) (interiors disjoint along the shared edge)");
		assertEquals(A, (int) m.getValue(DE9IM.I, DE9IM.E), "I(a) ∩ E(b)");
		assertEquals(A, (int) m.getValue(DE9IM.E, DE9IM.I), "E(a) ∩ I(b)");
	}

	// ---------- Custom epsilon ----------
	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} for two {@link Point2D points} with a given {@code epsilon}.
	 * The two point are equals at 1.0e-6.
	 */
	@Test
	public void epsilonAffectsEqualityTest() {
		Point2D a = JeometryFactory.createPoint2D(0.0, 0.0);
		Point2D b = JeometryFactory.createPoint2D(1.0e-6, 0.0);
		// With tight tolerance: distinct.
		assertEquals(X, (int) DE9IM.computeMatrix(a, b, 1.0e-12).getValue(DE9IM.I, DE9IM.I));
		// With loose tolerance: equal.
		assertEquals(P, (int) DE9IM.computeMatrix(a, b, 1.0e-3).getValue(DE9IM.I, DE9IM.I));
	}

	// ---------- Failure modes ----------
	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with a {@link Point2D points} and an {@code Ellipse2D ellipse}.
	 */
	@Test
	public void rejectsEllipseTest() {
		Point2D p = JeometryFactory.createPoint2D(0.0, 0.0);
		Ellipse2D e = JeometryFactory.createEllipse2D(p, 1.0, 0.5);
		assertThrows(UnsupportedOperationException.class, () -> DE9IM.computeMatrix(p, e));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with {@code null} {@link Geometry2D geometries}.
	 */
	@Test
	public void rejectsNullArgumentsTest() {
		Point2D p = JeometryFactory.createPoint2D(0.0, 0.0);
		assertThrows(IllegalArgumentException.class, () -> DE9IM.computeMatrix(null, p));
		assertThrows(IllegalArgumentException.class, () -> DE9IM.computeMatrix(p, null));
	}

	/**
	 * Testing {@link DE9IM#computeMatrix(Geometry2D, Geometry2D)} with negative {@code epsilon}.
	 */
	@Test
	public void rejectsNegativeEpsilonTest() {
		Point2D p = JeometryFactory.createPoint2D(0.0, 0.0);
		assertThrows(IllegalArgumentException.class, () -> DE9IM.computeMatrix(p, p, -1.0));
	}

	// ---------- Helpers ----------

	/**
	 * Compare a 3x3 expected matrix (as an {@code int[][]}) against the actual {@link Matrix}.
	 * Each cell of the expected matrix should hold one of the four dimension constants.
	 * @param expected the expected matrix values
	 * @param actual the actual matrix
	 */
	protected static void assertMatrix(int[][] expected, Matrix actual) {
		assertNotNull(actual, "Matrix must not be null");
		assertEquals(3, actual.getRowsCount(), "Matrix must be 3x3");
		assertEquals(3, actual.getColumnsCount(), "Matrix must be 3x3");
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				int got = (int) actual.getValue(r, c);
				int exp = expected[r][c];
				if (got != exp) {
					throw new AssertionError("Matrix mismatch at (" + r + ", " + c + "): expected "
							+ exp + " but was " + got + "\nExpected:\n" + format(expected)
							+ "Got:\n" + format(actual));
				}
			}
		}
	}

	private static String format(int[][] m) {
		StringBuilder sb = new StringBuilder();
		for (int[] row : m) {
			sb.append("  ").append(Arrays.toString(row)).append("\n");
		}
		return sb.toString();
	}

	private static String format(Matrix m) {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < m.getRowsCount(); r++) {
			sb.append("  [");
			for (int c = 0; c < m.getColumnsCount(); c++) {
				sb.append(' ').append((int) m.getValue(r, c));
			}
			sb.append(" ]\n");
		}
		return sb.toString();
	}
}
