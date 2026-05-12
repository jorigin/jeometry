package org.jeometry.geom2D.algorithm.relation.de9im;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.LineString2D;
import org.jeometry.math.Matrix;

/**
 * DE-9IM computer for the {@link Point2D Point} / line-like geometry case
 * (a line-like geometry being either a {@link Line2D} or a {@link LineString2D}).
 *
 * @author Julien SEINTURIER - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
final class PointLineStringDE9IM {

	private PointLineStringDE9IM() {}

	/**
	 * Compute the DE-9IM matrix for a point against a line-like geometry.
	 * @param a the point
	 * @param b the line-like geometry
	 * @param eps the coordinate tolerance
	 * @return the DE-9IM matrix
	 */
	static Matrix compute(Point2D a, Geometry2D b, double eps) {
		Matrix m = DE9IMUtils.newMatrix();

		List<Point2D> verts = DE9IMUtils.verticesOfLine(b);
		if (verts.size() < 2) {
			// Degenerate line geometry: treat as empty for the purpose of DE-9IM.
			return m;
		}
		boolean closed = DE9IMUtils.isLineClosed(b, eps);
		Point2D first = verts.get(0);
		Point2D last  = verts.get(verts.size() - 1);

		boolean onLine = DE9IMUtils.isPointOnLineString(a, verts, eps);
		boolean onBoundary = !closed && (DE9IMUtils.pointEquals(a, first, eps) || DE9IMUtils.pointEquals(a, last, eps));
		boolean onInterior = onLine && !onBoundary;

		// Row I: the point's interior is the point itself.
		m.setValue(DE9IM.I, DE9IM.I, onInterior  ? DE9IM.DIM_POINT : DE9IM.DIM_EMPTY);
		m.setValue(DE9IM.I, DE9IM.B, onBoundary  ? DE9IM.DIM_POINT : DE9IM.DIM_EMPTY);
		m.setValue(DE9IM.I, DE9IM.E, onLine      ? DE9IM.DIM_EMPTY : DE9IM.DIM_POINT);

		// Row B is empty (a point has no boundary), already -1 everywhere.

		// Row E: the exterior of a point is the plane minus that single point.
		// I(b) is 1-dimensional and removing one point keeps it 1-dimensional.
		m.setValue(DE9IM.E, DE9IM.I, DE9IM.DIM_LINE);
		// B(b) is empty when closed, otherwise it has at least one element distinct from `a`.
		if (!closed) {
			boolean someBoundaryAwayFromA = !DE9IMUtils.pointEquals(a, first, eps)
			        || !DE9IMUtils.pointEquals(a, last, eps);
			m.setValue(DE9IM.E, DE9IM.B, someBoundaryAwayFromA ? DE9IM.DIM_POINT : DE9IM.DIM_EMPTY);
		}
		// E ∩ E is already 2.
		return m;
	}
}
