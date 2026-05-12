package org.jeometry.geom2D.algorithm.relation.de9im;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.Polygon2D;
import org.jeometry.math.Matrix;

/**
 * DE-9IM computer for the {@link Point2D Point} / polygonal geometry case
 * (a polygonal geometry being either a {@link Polygon2D} or a {@link Box2D}).
 *
 * @author Julien SEINTURIER - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
final class PointPolygonDE9IM {

	private PointPolygonDE9IM() {}

	/**
	 * Compute the DE-9IM matrix for a point against a polygonal geometry.
	 * @param a the point
	 * @param b the polygonal geometry
	 * @param eps the coordinate tolerance
	 * @return the DE-9IM matrix
	 */
	static Matrix compute(Point2D a, Geometry2D b, double eps) {
		Matrix m = DE9IMUtils.newMatrix();

		List<List<Point2D>> rings = DE9IMUtils.ringsOfPolygon(b);
		if (rings.isEmpty() || rings.get(0).size() < 3) {
			return m;
		}
		int loc = DE9IMUtils.locateInPolygon(a, rings, eps);

		m.setValue(DE9IM.I, DE9IM.I, loc == DE9IMUtils.LOC_INTERIOR ? DE9IM.DIM_POINT : DE9IM.DIM_EMPTY);
		m.setValue(DE9IM.I, DE9IM.B, loc == DE9IMUtils.LOC_BOUNDARY ? DE9IM.DIM_POINT : DE9IM.DIM_EMPTY);
		m.setValue(DE9IM.I, DE9IM.E, loc == DE9IMUtils.LOC_EXTERIOR ? DE9IM.DIM_POINT : DE9IM.DIM_EMPTY);

		// The point has no boundary, so row B stays -1.

		// Row E: the polygon interior (2D) and boundary (1D) are not killed by removing one point.
		m.setValue(DE9IM.E, DE9IM.I, DE9IM.DIM_AREA);
		m.setValue(DE9IM.E, DE9IM.B, DE9IM.DIM_LINE);
		return m;
	}
}
