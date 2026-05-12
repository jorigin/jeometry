package org.jeometry.geom2D.algorithm.relation.de9im;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.LineString2D;
import org.jeometry.geom2D.primitive.Polygon2D;
import org.jeometry.math.Matrix;

/**
 * DE-9IM computer for the line-like ({@link Line2D} or {@link LineString2D}) /
 * polygonal ({@link Polygon2D} or {@link Box2D}) case.
 *
 * <p>The algorithm walks each segment of the line geometry, splits it at every intersection with
 * the rings of the polygon, and classifies the midpoint of each resulting sub-segment as inside,
 * on the boundary, or outside the polygon. A symmetric pass walks the polygon rings and classifies
 * sub-segments of those rings against the line.</p>
 *
 * @author Julien SEINTURIER - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
final class LineStringPolygonDE9IM {

	private LineStringPolygonDE9IM() {}

	static Matrix compute(Geometry2D a, Geometry2D b, double eps) {
		Matrix m = DE9IMUtils.newMatrix();

		List<Point2D> va = DE9IMUtils.verticesOfLine(a);
		if (va.size() < 2) return m;
		boolean closedA = DE9IMUtils.isLineClosed(a, eps);
		List<Point2D> Ba = closedA ? new ArrayList<>(0)
		        : new ArrayList<>(List.of(va.get(0), va.get(va.size() - 1)));

		List<List<Point2D>> rings = DE9IMUtils.ringsOfPolygon(b);
		if (rings.isEmpty() || rings.get(0).size() < 3) return m;

		// Pass 1: walk the line segments, split at polygon-boundary intersections,
		// classify each sub-segment midpoint against the polygon. Single-point intersections
		// with the polygon boundary are also classified explicitly (those isolated points are
		// missed by the midpoint test).
		for (int i = 0; i + 1 < va.size(); i++) {
			Point2D s0 = va.get(i), s1 = va.get(i + 1);
			List<Double> ts = new ArrayList<>();
			for (List<Point2D> ring : rings) {
				for (int j = 0; j + 1 < ring.size(); j++) {
					DE9IMUtils.SegInter it = DE9IMUtils.segmentIntersection(
							s0, s1, ring.get(j), ring.get(j + 1), eps);
					if (it.kind == DE9IMUtils.SegInter.POINT) {
						ts.add(it.tA0);
						Point2D p = JeometryFactory.createPoint2D(it.x0, it.y0);
						int row = pointInSet(p, Ba, eps) ? DE9IM.B : DE9IM.I;
						DE9IMUtils.bump(m, row, DE9IM.B, DE9IM.DIM_POINT);
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
				int loc = DE9IMUtils.locateInPolygon(p, rings, eps);
				switch (loc) {
					case DE9IMUtils.LOC_INTERIOR: DE9IMUtils.bump(m, DE9IM.I, DE9IM.I, dim); break;
					case DE9IMUtils.LOC_BOUNDARY: DE9IMUtils.bump(m, DE9IM.I, DE9IM.B, dim); break;
					case DE9IMUtils.LOC_EXTERIOR: DE9IMUtils.bump(m, DE9IM.I, DE9IM.E, dim); break;
					default: break;
				}
			}
		}

		// Pass 2: walk every ring segment of the polygon, split at line-segment intersections,
		// classify each sub-segment midpoint against the line.
		for (List<Point2D> ring : rings) {
			for (int i = 0; i + 1 < ring.size(); i++) {
				Point2D s0 = ring.get(i), s1 = ring.get(i + 1);
				List<Double> ts = collectIntersectionParamsAgainstPolyline(s0, s1, va, eps);
				List<double[]> mids = DE9IMUtils.subSegmentMidpoints(s0, s1, ts, eps);
				for (double[] mid : mids) {
					Point2D p = JeometryFactory.createPoint2D(mid[0], mid[1]);
					int dim = (int) mid[2];
					boolean onA = DE9IMUtils.isPointOnLineString(p, va, eps);
					boolean onBa = onA && pointInSet(p, Ba, eps);
					if (onBa)      DE9IMUtils.bump(m, DE9IM.B, DE9IM.B, DE9IM.DIM_POINT);
					else if (onA)  DE9IMUtils.bump(m, DE9IM.I, DE9IM.B, dim);
					else           DE9IMUtils.bump(m, DE9IM.E, DE9IM.B, dim);
				}
			}
		}

		// Pass 3: classify each boundary point of the line against the polygon.
		for (Point2D p : Ba) {
			int loc = DE9IMUtils.locateInPolygon(p, rings, eps);
			switch (loc) {
				case DE9IMUtils.LOC_INTERIOR: DE9IMUtils.bump(m, DE9IM.B, DE9IM.I, DE9IM.DIM_POINT); break;
				case DE9IMUtils.LOC_BOUNDARY: DE9IMUtils.bump(m, DE9IM.B, DE9IM.B, DE9IM.DIM_POINT); break;
				case DE9IMUtils.LOC_EXTERIOR: DE9IMUtils.bump(m, DE9IM.B, DE9IM.E, DE9IM.DIM_POINT); break;
				default: break;
			}
		}

		// The polygon interior (2D) and boundary (1D) survive the removal of the 1D line.
		DE9IMUtils.bump(m, DE9IM.E, DE9IM.I, DE9IM.DIM_AREA);
		DE9IMUtils.bump(m, DE9IM.E, DE9IM.B, DE9IM.DIM_LINE);
		return m;
	}

	private static List<Double> collectIntersectionParamsAgainstPolyline(Point2D s0, Point2D s1, List<Point2D> verts, double eps) {
		List<Double> ts = new ArrayList<>();
		for (int j = 0; j + 1 < verts.size(); j++) {
			DE9IMUtils.SegInter it = DE9IMUtils.segmentIntersection(s0, s1, verts.get(j), verts.get(j + 1), eps);
			if (it.kind == DE9IMUtils.SegInter.POINT) {
				ts.add(it.tA0);
			} else if (it.kind == DE9IMUtils.SegInter.OVERLAP) {
				ts.add(it.tA0);
				ts.add(it.tA1);
			}
		}
		return ts;
	}

	private static boolean pointInSet(Point2D p, List<Point2D> set, double eps) {
		for (Point2D q : set) {
			if (DE9IMUtils.pointEquals(p, q, eps)) return true;
		}
		return false;
	}
}
