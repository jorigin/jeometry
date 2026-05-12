package org.jeometry.geom2D.algorithm.relation.de9im;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.LineString2D;
import org.jeometry.math.Matrix;

/**
 * DE-9IM computer for the line-like / line-like case
 * (each input being either a {@link Line2D} or a {@link LineString2D}).
 *
 * <p>The implementation handles the most common configurations: disjoint, transverse crossing,
 * shared endpoint, partial overlap, full overlap and touch on a vertex. Self-intersecting
 * line strings are processed using the Mod-2 boundary rule reduced to its first/last vertices,
 * so degenerate self-touch configurations are not specifically modelled.</p>
 *
 * @author Julien SEINTURIER - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
final class LineStringLineStringDE9IM {

	private LineStringLineStringDE9IM() {}

	static Matrix compute(Geometry2D a, Geometry2D b, double eps) {
		Matrix m = DE9IMUtils.newMatrix();

		List<Point2D> va = DE9IMUtils.verticesOfLine(a);
		List<Point2D> vb = DE9IMUtils.verticesOfLine(b);
		if (va.size() < 2 || vb.size() < 2) return m;

		boolean closedA = DE9IMUtils.isLineClosed(a, eps);
		boolean closedB = DE9IMUtils.isLineClosed(b, eps);
		List<Point2D> Ba = boundary(va, closedA);
		List<Point2D> Bb = boundary(vb, closedB);

		// 1. Pairwise segment / segment intersections — fills m[I][I] for transverse crossings
		//    and full overlaps (point or 1D contributions).
		for (int i = 0; i + 1 < va.size(); i++) {
			Point2D a0 = va.get(i), a1 = va.get(i + 1);
			for (int j = 0; j + 1 < vb.size(); j++) {
				Point2D b0 = vb.get(j), b1 = vb.get(j + 1);
				DE9IMUtils.SegInter inter = DE9IMUtils.segmentIntersection(a0, a1, b0, b1, eps);
				if (inter.kind == DE9IMUtils.SegInter.NONE) continue;
				if (inter.kind == DE9IMUtils.SegInter.POINT) {
					Point2D p = JeometryFactory.createPoint2D(inter.x0, inter.y0);
					int row = onPointSet(p, Ba, eps) ? DE9IM.B : DE9IM.I;
					int col = onPointSet(p, Bb, eps) ? DE9IM.B : DE9IM.I;
					DE9IMUtils.bump(m, row, col, DE9IM.DIM_POINT);
				} else { // OVERLAP
					DE9IMUtils.bump(m, DE9IM.I, DE9IM.I, DE9IM.DIM_LINE);
				}
			}
		}

		// 2. For each a-segment, find sub-segments fully on b (→ m[I][I] = 1) and off b (→ m[I][E] = 1).
		fillRowAgainst(m, va, vb, /*aSide=*/true, eps);
		// 3. Mirror for b → m[E][I].
		fillRowAgainst(m, vb, va, /*aSide=*/false, eps);

		// 4. Classify each boundary point of a against b.
		for (Point2D p : Ba) {
			if (onPointSet(p, Bb, eps))                       DE9IMUtils.bump(m, DE9IM.B, DE9IM.B, DE9IM.DIM_POINT);
			else if (DE9IMUtils.isPointOnLineString(p, vb, eps)) DE9IMUtils.bump(m, DE9IM.B, DE9IM.I, DE9IM.DIM_POINT);
			else                                              DE9IMUtils.bump(m, DE9IM.B, DE9IM.E, DE9IM.DIM_POINT);
		}
		// 5. Classify each boundary point of b against a.
		for (Point2D p : Bb) {
			if (onPointSet(p, Ba, eps))                       DE9IMUtils.bump(m, DE9IM.B, DE9IM.B, DE9IM.DIM_POINT);
			else if (DE9IMUtils.isPointOnLineString(p, va, eps)) DE9IMUtils.bump(m, DE9IM.I, DE9IM.B, DE9IM.DIM_POINT);
			else                                              DE9IMUtils.bump(m, DE9IM.E, DE9IM.B, DE9IM.DIM_POINT);
		}
		return m;
	}

	/**
	 * For each segment of {@code source}, split it at every intersection with the segments of
	 * {@code other}, then classify each resulting sub-segment as on-other or off-other and
	 * update the matrix accordingly. {@code aSide} drives whether the result feeds row-A or row-E:
	 * <ul>
	 *   <li>true: source = a — fills m[I][I] (overlap with b interior) and m[I][E] (off b).</li>
	 *   <li>false: source = b — fills m[I][I] (already) and m[E][I] (off a).</li>
	 * </ul>
	 */
	private static void fillRowAgainst(Matrix m, List<Point2D> source, List<Point2D> other, boolean aSide, double eps) {
		for (int i = 0; i + 1 < source.size(); i++) {
			Point2D s0 = source.get(i), s1 = source.get(i + 1);
			List<Double> ts = new ArrayList<>();
			for (int j = 0; j + 1 < other.size(); j++) {
				DE9IMUtils.SegInter it = DE9IMUtils.segmentIntersection(s0, s1, other.get(j), other.get(j + 1), eps);
				if (it.kind == DE9IMUtils.SegInter.POINT) {
					ts.add(it.tA0);
				} else if (it.kind == DE9IMUtils.SegInter.OVERLAP) {
					ts.add(it.tA0);
					ts.add(it.tA1);
				}
			}
			List<double[]> mids = DE9IMUtils.subSegmentMidpoints(s0, s1, ts, eps);
			for (double[] mid : mids) {
				Point2D p = JeometryFactory.createPoint2D(mid[0], mid[1]);
				int dim = (int) mid[2];
				boolean onOther = DE9IMUtils.isPointOnLineString(p, other, eps);
				if (aSide) {
					if (onOther) DE9IMUtils.bump(m, DE9IM.I, DE9IM.I, dim);
					else         DE9IMUtils.bump(m, DE9IM.I, DE9IM.E, dim);
				} else {
					if (onOther) DE9IMUtils.bump(m, DE9IM.I, DE9IM.I, dim);
					else         DE9IMUtils.bump(m, DE9IM.E, DE9IM.I, dim);
				}
			}
		}
	}

	private static List<Point2D> boundary(List<Point2D> v, boolean closed) {
		if (closed) return new ArrayList<>(0);
		return new ArrayList<>(Arrays.asList(v.get(0), v.get(v.size() - 1)));
	}

	private static boolean onPointSet(Point2D p, List<Point2D> set, double eps) {
		for (Point2D q : set) {
			if (DE9IMUtils.pointEquals(p, q, eps)) return true;
		}
		return false;
	}
}
