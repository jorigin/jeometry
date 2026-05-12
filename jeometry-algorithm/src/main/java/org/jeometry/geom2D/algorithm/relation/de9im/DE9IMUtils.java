package org.jeometry.geom2D.algorithm.relation.de9im;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.LineString2D;
import org.jeometry.geom2D.primitive.Polygon2D;
import org.jeometry.math.Matrix;

/**
 * Internal helpers shared by the per-pair DE-9IM computers.
 * Not part of the public API.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value org.jeometry.Jeometry#version} b{@value org.jeometry.Jeometry#BUILD}
 * @since 2.0.1
 */
final class DE9IMUtils {

	/** Result code: the located point lies in the polygon interior. */
	static final int LOC_INTERIOR = 0;
	/** Result code: the located point lies on the polygon boundary. */
	static final int LOC_BOUNDARY = 1;
	/** Result code: the located point lies in the polygon exterior. */
	static final int LOC_EXTERIOR = 2;

	private DE9IMUtils() {}

	// ---------- Matrix helpers ----------

	/**
	 * Allocate a fresh 3x3 matrix initialized with {@link DE9IM#DIM_EMPTY} everywhere except the
	 * exterior/exterior cell which is set to {@link DE9IM#DIM_AREA} (the plane minus a finite
	 * number of bounded sets is always 2-dimensional).
	 */
	static Matrix newMatrix() {
		Matrix m = JeometryFactory.createMatrix(3, 3);
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				m.setValue(r, c, DE9IM.DIM_EMPTY);
			}
		}
		m.setValue(DE9IM.E, DE9IM.E, DE9IM.DIM_AREA);
		return m;
	}

	/** Bump the value at {@code (row, col)} so it never decreases. */
	static void bump(Matrix m, int row, int col, int dim) {
		if (m.getValue(row, col) < dim) {
			m.setValue(row, col, dim);
		}
	}

	/** Return a transposed copy of the given 3x3 matrix. */
	static Matrix transpose(Matrix m) {
		Matrix t = JeometryFactory.createMatrix(3, 3);
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				t.setValue(r, c, m.getValue(c, r));
			}
		}
		return t;
	}

	// ---------- Geometry adapters ----------

	/**
	 * Extract the ordered vertices from a line-like geometry ({@link Line2D} or {@link LineString2D}).
	 */
	static List<Point2D> verticesOfLine(Geometry2D g) {
		if (g instanceof Line2D) {
			Line2D l = (Line2D) g;
			List<Point2D> out = new ArrayList<>(2);
			out.add(l.getEnd1());
			out.add(l.getEnd2());
			return out;
		}
		if (g instanceof LineString2D) {
			Point2DContainer c = ((LineString2D) g).getVertices();
			List<Point2D> out = new ArrayList<>(c.size());
			for (int i = 0; i < c.size(); i++) {
				out.add(c.get(i));
			}
			return out;
		}
		throw new IllegalArgumentException("Not a line geometry: " + g.getClass());
	}

	/** Tell whether a line-like geometry is closed (its first and last vertices match). */
	static boolean isLineClosed(Geometry2D g, double eps) {
		if (g instanceof LineString2D) {
			return ((LineString2D) g).isClosed();
		}
		List<Point2D> v = verticesOfLine(g);
		return v.size() >= 2 && pointEquals(v.get(0), v.get(v.size() - 1), eps);
	}

	/**
	 * Extract the rings of a polygonal geometry: the first list is the exterior ring,
	 * subsequent lists are holes. Each ring is returned as a closed list (last vertex equals first).
	 */
	static List<List<Point2D>> ringsOfPolygon(Geometry2D g) {
		List<List<Point2D>> rings = new ArrayList<>();
		if (g instanceof Polygon2D) {
			Polygon2D pg = (Polygon2D) g;
			rings.add(closedRing(verticesOfLine(pg.getExterior())));
			for (LineString2D hole : pg.getHoles()) {
				rings.add(closedRing(verticesOfLine(hole)));
			}
			return rings;
		}
		if (g instanceof Box2D) {
			Box2D box = (Box2D) g;
			double x0 = box.getOrigin().getX();
			double y0 = box.getOrigin().getY();
			double x1 = x0 + box.getWidth();
			double y1 = y0 + box.getHeight();
			List<Point2D> shell = new ArrayList<>(5);
			shell.add(JeometryFactory.createPoint2D(x0, y0));
			shell.add(JeometryFactory.createPoint2D(x1, y0));
			shell.add(JeometryFactory.createPoint2D(x1, y1));
			shell.add(JeometryFactory.createPoint2D(x0, y1));
			shell.add(JeometryFactory.createPoint2D(x0, y0));
			rings.add(shell);
			return rings;
		}
		throw new IllegalArgumentException("Not a polygonal geometry: " + g.getClass());
	}

	private static List<Point2D> closedRing(List<Point2D> v) {
		if (v.isEmpty()) return v;
		Point2D first = v.get(0);
		Point2D last = v.get(v.size() - 1);
		if (first.getX() == last.getX() && first.getY() == last.getY()) {
			return v;
		}
		List<Point2D> closed = new ArrayList<>(v.size() + 1);
		closed.addAll(v);
		closed.add(first);
		return closed;
	}

	// ---------- Geometric predicates ----------

	/** Coordinate-wise equality up to {@code eps}. */
	static boolean pointEquals(Point2D a, Point2D b, double eps) {
		return Math.abs(a.getX() - b.getX()) <= eps && Math.abs(a.getY() - b.getY()) <= eps;
	}

	/**
	 * Test whether the point {@code p} lies on the closed segment {@code [a, b]} within tolerance.
	 */
	static boolean isPointOnSegment(Point2D p, Point2D a, Point2D b, double eps) {
		double abx = b.getX() - a.getX();
		double aby = b.getY() - a.getY();
		double apx = p.getX() - a.getX();
		double apy = p.getY() - a.getY();
		double len2 = abx * abx + aby * aby;
		if (len2 <= eps * eps) {
			return pointEquals(p, a, eps);
		}
		double cross = abx * apy - aby * apx;
		if (Math.abs(cross) > eps * Math.sqrt(len2)) {
			return false;
		}
		double t = (apx * abx + apy * aby) / len2;
		return t >= -eps && t <= 1.0 + eps;
	}

	/** Tell whether {@code p} lies on at least one segment of the open polyline {@code vs}. */
	static boolean isPointOnLineString(Point2D p, List<Point2D> vs, double eps) {
		for (int i = 0; i + 1 < vs.size(); i++) {
			if (isPointOnSegment(p, vs.get(i), vs.get(i + 1), eps)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Locate a point relative to a polygonal geometry described as rings (exterior + holes).
	 * Returns one of {@link #LOC_INTERIOR}, {@link #LOC_BOUNDARY}, {@link #LOC_EXTERIOR}.
	 */
	static int locateInPolygon(Point2D p, List<List<Point2D>> rings, double eps) {
		for (List<Point2D> ring : rings) {
			for (int i = 0; i + 1 < ring.size(); i++) {
				if (isPointOnSegment(p, ring.get(i), ring.get(i + 1), eps)) {
					return LOC_BOUNDARY;
				}
			}
		}
		// Ray cast against the exterior ring.
		boolean insideShell = pointInRing(p, rings.get(0));
		if (!insideShell) {
			return LOC_EXTERIOR;
		}
		for (int k = 1; k < rings.size(); k++) {
			if (pointInRing(p, rings.get(k))) {
				return LOC_EXTERIOR;
			}
		}
		return LOC_INTERIOR;
	}

	/** Standard non-tolerant ray-casting against a closed ring (last vertex == first). */
	static boolean pointInRing(Point2D p, List<Point2D> ring) {
		boolean inside = false;
		double px = p.getX();
		double py = p.getY();
		for (int i = 0, j = ring.size() - 2; i < ring.size() - 1; j = i++) {
			double xi = ring.get(i).getX(), yi = ring.get(i).getY();
			double xj = ring.get(j).getX(), yj = ring.get(j).getY();
			boolean intersect = (yi > py) != (yj > py)
			        && px < (xj - xi) * (py - yi) / (yj - yi) + xi;
			if (intersect) inside = !inside;
		}
		return inside;
	}

	// ---------- Segment / segment intersection ----------

	/**
	 * Result of a segment / segment intersection.
	 * <ul>
	 *   <li>{@code kind == NONE}: no intersection</li>
	 *   <li>{@code kind == POINT}: single point intersection at {@code (x0, y0)}</li>
	 *   <li>{@code kind == OVERLAP}: collinear overlap from {@code (x0, y0)} to {@code (x1, y1)}</li>
	 * </ul>
	 * The {@code tA0/tA1} and {@code tB0/tB1} fields hold the parametric positions of the result
	 * along segments A and B respectively (in {@code [0, 1]}), useful for splitting.
	 */
	static final class SegInter {
		static final int NONE = 0;
		static final int POINT = 1;
		static final int OVERLAP = 2;

		int kind;
		double x0, y0, x1, y1;
		double tA0, tA1, tB0, tB1;
	}

	/** Compute the intersection of segments [a0,a1] and [b0,b1]. */
	static SegInter segmentIntersection(Point2D a0, Point2D a1, Point2D b0, Point2D b1, double eps) {
		SegInter r = new SegInter();
		r.kind = SegInter.NONE;

		double rx = a1.getX() - a0.getX();
		double ry = a1.getY() - a0.getY();
		double sx = b1.getX() - b0.getX();
		double sy = b1.getY() - b0.getY();
		double dx = b0.getX() - a0.getX();
		double dy = b0.getY() - a0.getY();

		double denom = rx * sy - ry * sx;
		double numerT = dx * sy - dy * sx;
		double numerU = dx * ry - dy * rx;

		double r2 = rx * rx + ry * ry;
		double s2 = sx * sx + sy * sy;
		double scale = Math.sqrt(r2 * s2);

		if (Math.abs(denom) <= eps * Math.max(1.0, scale)) {
			// Parallel segments. Either disjoint or collinear.
			if (Math.abs(numerT) > eps * Math.max(1.0, Math.sqrt(r2)) || r2 < eps * eps) {
				// Strictly parallel and non-collinear (or A is degenerate).
				return r;
			}
			// Collinear: project both segments on the longest axis of A.
			double invR2 = r2 > 0 ? 1.0 / r2 : 0.0;
			double tB0 = (dx * rx + dy * ry) * invR2;
			double tB1 = ((b1.getX() - a0.getX()) * rx + (b1.getY() - a0.getY()) * ry) * invR2;
			double lo = Math.min(tB0, tB1);
			double hi = Math.max(tB0, tB1);
			double overlapLo = Math.max(0.0, lo);
			double overlapHi = Math.min(1.0, hi);
			if (overlapHi < overlapLo - eps) {
				return r;
			}
			if (Math.abs(overlapHi - overlapLo) <= eps) {
				r.kind = SegInter.POINT;
				r.tA0 = r.tA1 = clamp01(overlapLo);
				r.x0 = r.x1 = a0.getX() + r.tA0 * rx;
				r.y0 = r.y1 = a0.getY() + r.tA0 * ry;
				r.tB0 = r.tB1 = paramOnSegment(b0, b1, r.x0, r.y0);
				return r;
			}
			r.kind = SegInter.OVERLAP;
			r.tA0 = clamp01(overlapLo);
			r.tA1 = clamp01(overlapHi);
			r.x0 = a0.getX() + r.tA0 * rx;
			r.y0 = a0.getY() + r.tA0 * ry;
			r.x1 = a0.getX() + r.tA1 * rx;
			r.y1 = a0.getY() + r.tA1 * ry;
			r.tB0 = paramOnSegment(b0, b1, r.x0, r.y0);
			r.tB1 = paramOnSegment(b0, b1, r.x1, r.y1);
			return r;
		}

		double t = numerT / denom;
		double u = numerU / denom;
		if (t < -eps || t > 1.0 + eps || u < -eps || u > 1.0 + eps) {
			return r;
		}
		t = clamp01(t);
		u = clamp01(u);
		r.kind = SegInter.POINT;
		r.tA0 = r.tA1 = t;
		r.tB0 = r.tB1 = u;
		r.x0 = r.x1 = a0.getX() + t * rx;
		r.y0 = r.y1 = a0.getY() + t * ry;
		return r;
	}

	private static double clamp01(double v) {
		if (v < 0) return 0;
		if (v > 1) return 1;
		return v;
	}

	private static double paramOnSegment(Point2D a, Point2D b, double x, double y) {
		double rx = b.getX() - a.getX();
		double ry = b.getY() - a.getY();
		double len2 = rx * rx + ry * ry;
		if (len2 == 0) return 0;
		return ((x - a.getX()) * rx + (y - a.getY()) * ry) / len2;
	}

	// ---------- Sub-segment splitting ----------

	/**
	 * Split a segment {@code [a0, a1]} at a sorted set of parametric values and return the
	 * midpoint of each resulting sub-segment together with the dimension of that sub-segment
	 * (0 if the sub-segment has zero length, 1 otherwise).
	 */
	static List<double[]> subSegmentMidpoints(Point2D a0, Point2D a1, List<Double> params, double eps) {
		List<Double> ps = new ArrayList<>(params.size() + 2);
		ps.add(0.0);
		ps.addAll(params);
		ps.add(1.0);
		Collections.sort(ps);

		List<double[]> mids = new ArrayList<>(ps.size());
		double rx = a1.getX() - a0.getX();
		double ry = a1.getY() - a0.getY();
		double len = Math.sqrt(rx * rx + ry * ry);
		for (int i = 0; i + 1 < ps.size(); i++) {
			double t0 = ps.get(i);
			double t1 = ps.get(i + 1);
			if (t1 - t0 <= 0.0) continue;
			double tm = 0.5 * (t0 + t1);
			double dim = (t1 - t0) * len > eps ? 1 : 0;
			mids.add(new double[]{ a0.getX() + tm * rx, a0.getY() + tm * ry, dim });
		}
		return mids;
	}
}
