package org.jeometry.geom2D.algorithm.relation.de9im;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.Polygon2D;
import org.jeometry.math.Matrix;

/**
 * DE-9IM computer for two polygonal geometries ({@link Polygon2D} or {@link Box2D}).
 *
 * <p>The algorithm walks every ring segment of <i>a</i>, splits it at every intersection with
 * the rings of <i>b</i>, and classifies the midpoint of each resulting sub-segment as inside,
 * on the boundary, or outside <i>b</i>. From the location of a B(a) sub-segment we infer the
 * adjacent I(a) and E(a) cells (a 2D neighbourhood lies on the same side of the boundary
 * locally). The symmetric pass walks the rings of <i>b</i> against <i>a</i>.</p>
 *
 * <p>Cases handled: disjoint, point/edge boundary touch, partial overlap, full containment in
 * either direction, and equality. Self-intersecting polygons or coincident-with-opposite-sense
 * boundaries are not specifically modelled.</p>
 *
 * @author Julien SEINTURIER - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
final class PolygonPolygonDE9IM {

	private PolygonPolygonDE9IM() {}

	static Matrix compute(Geometry2D a, Geometry2D b, double eps) {
		Matrix m = DE9IMUtils.newMatrix();

		List<List<Point2D>> ra = DE9IMUtils.ringsOfPolygon(a);
		List<List<Point2D>> rb = DE9IMUtils.ringsOfPolygon(b);
		if (ra.isEmpty() || rb.isEmpty() || ra.get(0).size() < 3 || rb.get(0).size() < 3) {
			return m;
		}

		// 1. Boundary / boundary intersection contributions: every segment-pair intersection
		//    between a and b rings sits on B(a) ∩ B(b).
		for (List<Point2D> rA : ra) {
			for (int i = 0; i + 1 < rA.size(); i++) {
				Point2D a0 = rA.get(i), a1 = rA.get(i + 1);
				for (List<Point2D> rB : rb) {
					for (int j = 0; j + 1 < rB.size(); j++) {
						DE9IMUtils.SegInter it = DE9IMUtils.segmentIntersection(
						        a0, a1, rB.get(j), rB.get(j + 1), eps);
						if (it.kind == DE9IMUtils.SegInter.POINT) {
							DE9IMUtils.bump(m, DE9IM.B, DE9IM.B, DE9IM.DIM_POINT);
						} else if (it.kind == DE9IMUtils.SegInter.OVERLAP) {
							DE9IMUtils.bump(m, DE9IM.B, DE9IM.B, DE9IM.DIM_LINE);
						}
					}
				}
			}
		}

		// 2. Walk every B(a) ring segment, split at intersections with B(b),
		//    classify each sub-segment's midpoint location with respect to b.
		walkRingsAgainstPolygon(m, ra, rb, /*aIsSource=*/true, eps);
		// 3. Mirror: walk every B(b) ring segment against polygon a.
		walkRingsAgainstPolygon(m, rb, ra, /*aIsSource=*/false, eps);

		// 4. Representative-interior-point completion. The boundary-only passes miss the
		//    case where B(a) is fully covered by B(b) (e.g. equal polygons): no sub-segment
		//    is classified as INTERIOR or EXTERIOR, so I(a)∩I(b) is never inferred.
		//    Locating one representative interior point of each polygon in the other
		//    closes those gaps without harming the cases already handled.
		Point2D repA = representativeInteriorPoint(ra);
		Point2D repB = representativeInteriorPoint(rb);
		int aInB = DE9IMUtils.locateInPolygon(repA, rb, eps);
		int bInA = DE9IMUtils.locateInPolygon(repB, ra, eps);
		if (aInB == DE9IMUtils.LOC_INTERIOR)      DE9IMUtils.bump(m, DE9IM.I, DE9IM.I, DE9IM.DIM_AREA);
		else if (aInB == DE9IMUtils.LOC_EXTERIOR) DE9IMUtils.bump(m, DE9IM.I, DE9IM.E, DE9IM.DIM_AREA);
		if (bInA == DE9IMUtils.LOC_INTERIOR)      DE9IMUtils.bump(m, DE9IM.I, DE9IM.I, DE9IM.DIM_AREA);
		else if (bInA == DE9IMUtils.LOC_EXTERIOR) DE9IMUtils.bump(m, DE9IM.E, DE9IM.I, DE9IM.DIM_AREA);
		return m;
	}

	/**
	 * Compute a representative interior point of a polygon by averaging the unique vertices
	 * of its exterior shell. Works for any convex polygon (and Box2D); may fall outside the
	 * polygon for strongly non-convex shapes.
	 */
	private static Point2D representativeInteriorPoint(List<List<Point2D>> rings) {
		List<Point2D> shell = rings.get(0);
		int n = shell.size();
		if (n > 1 && shell.get(0).getX() == shell.get(n - 1).getX()
		        && shell.get(0).getY() == shell.get(n - 1).getY()) {
			n--; // skip duplicated closing vertex
		}
		double sx = 0, sy = 0;
		for (int i = 0; i < n; i++) {
			sx += shell.get(i).getX();
			sy += shell.get(i).getY();
		}
		return JeometryFactory.createPoint2D(sx / n, sy / n);
	}

	private static void walkRingsAgainstPolygon(Matrix m, List<List<Point2D>> sourceRings,
	        List<List<Point2D>> otherRings, boolean aIsSource, double eps) {
		for (List<Point2D> ring : sourceRings) {
			for (int i = 0; i + 1 < ring.size(); i++) {
				Point2D s0 = ring.get(i), s1 = ring.get(i + 1);
				List<Double> ts = new ArrayList<>();
				for (List<Point2D> oRing : otherRings) {
					for (int j = 0; j + 1 < oRing.size(); j++) {
						DE9IMUtils.SegInter it = DE9IMUtils.segmentIntersection(
						        s0, s1, oRing.get(j), oRing.get(j + 1), eps);
						if (it.kind == DE9IMUtils.SegInter.POINT) {
							ts.add(it.tA0);
						} else if (it.kind == DE9IMUtils.SegInter.OVERLAP) {
							ts.add(it.tA0);
							ts.add(it.tA1);
						}
					}
				}
				List<double[]> mids = DE9IMUtils.subSegmentMidpoints(s0, s1, ts, eps);
				for (double[] mid : mids) {
					Point2D p = JeometryFactory.createPoint2D(mid[0], mid[1]);
					int dim = (int) mid[2];
					int loc = DE9IMUtils.locateInPolygon(p, otherRings, eps);
					applyClassification(m, loc, dim, aIsSource);
				}
			}
		}
	}

	/**
	 * Update the matrix from a B(source) sub-segment classified at location {@code loc}
	 * relative to the other polygon. When {@code aIsSource} is true, the source is <i>a</i>;
	 * otherwise it is <i>b</i> and rows/columns are swapped accordingly.
	 *
	 * <p>For a sub-segment lying inside / outside the other polygon, both the boundary cell
	 * (B-side, dimension 1) and the adjacent interior/exterior cell of the source (I or E,
	 * dimension 2) are bumped because a 2D neighbourhood of the sub-segment lies on either side
	 * of the boundary.</p>
	 */
	private static void applyClassification(Matrix m, int loc, int dim, boolean aIsSource) {
		if (aIsSource) {
			switch (loc) {
				case DE9IMUtils.LOC_INTERIOR:
					DE9IMUtils.bump(m, DE9IM.B, DE9IM.I, dim);
					DE9IMUtils.bump(m, DE9IM.I, DE9IM.I, DE9IM.DIM_AREA);
					break;
				case DE9IMUtils.LOC_BOUNDARY:
					DE9IMUtils.bump(m, DE9IM.B, DE9IM.B, dim);
					break;
				case DE9IMUtils.LOC_EXTERIOR:
					DE9IMUtils.bump(m, DE9IM.B, DE9IM.E, dim);
					DE9IMUtils.bump(m, DE9IM.I, DE9IM.E, DE9IM.DIM_AREA);
					break;
				default: break;
			}
		} else {
			switch (loc) {
				case DE9IMUtils.LOC_INTERIOR:
					DE9IMUtils.bump(m, DE9IM.I, DE9IM.B, dim);
					DE9IMUtils.bump(m, DE9IM.I, DE9IM.I, DE9IM.DIM_AREA);
					break;
				case DE9IMUtils.LOC_BOUNDARY:
					DE9IMUtils.bump(m, DE9IM.B, DE9IM.B, dim);
					break;
				case DE9IMUtils.LOC_EXTERIOR:
					DE9IMUtils.bump(m, DE9IM.E, DE9IM.B, dim);
					DE9IMUtils.bump(m, DE9IM.E, DE9IM.I, DE9IM.DIM_AREA);
					break;
				default: break;
			}
		}
	}
}
