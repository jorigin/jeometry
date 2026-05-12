package org.jeometry.geom2D.algorithm.relation.de9im;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.math.Matrix;

/**
 * DE-9IM computer for the {@link Point2D Point} / {@link Point2D Point} case.
 *
 * @author Julien SEINTURIER - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
final class PointPointDE9IM {

	private PointPointDE9IM() {}

	/**
	 * Compute the DE-9IM matrix for two {@link Point2D points}.
	 * @param a the first point
	 * @param b the second point
	 * @param eps the coordinate tolerance
	 * @return the DE-9IM matrix
	 */
	static Matrix compute(Point2D a, Point2D b, double eps) {
		Matrix m = DE9IMUtils.newMatrix();
		boolean equal = DE9IMUtils.pointEquals(a, b, eps);

		// Both points have empty boundaries, so the entire B row and B column stay -1.
		m.setValue(DE9IM.I, DE9IM.I, equal ? DE9IM.DIM_POINT : DE9IM.DIM_EMPTY);
		// I(a) ∩ E(b) is non-empty (and 0-dimensional) iff the two points differ.
		m.setValue(DE9IM.I, DE9IM.E, equal ? DE9IM.DIM_EMPTY : DE9IM.DIM_POINT);
		m.setValue(DE9IM.E, DE9IM.I, equal ? DE9IM.DIM_EMPTY : DE9IM.DIM_POINT);
		// E ∩ E is the plane minus a finite set ⇒ 2-dimensional (already set in newMatrix).
		return m;
	}
}
