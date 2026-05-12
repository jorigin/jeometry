package org.jeometry.geom2D.algorithm.relation.de9im;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.Ellipse2D;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.LineString2D;
import org.jeometry.geom2D.primitive.Polygon2D;
import org.jeometry.math.Matrix;

/**
 * A utility class that enables to compute the <a href="https://en.wikipedia.org/wiki/DE-9IM">Dimensionally Extended 9-Intersection Model (DE-9IM)</a>, also known as Clementini matrix [1], for two {@link Geometry2D 2D geometries}.
 * This matrix enables to compute spatial relations between geometries.
 * <br><br>
 *
 * <h2>Geometry</h2>
 * This class enables to compute <a href="https://en.wikipedia.org/wiki/DE-9IM">DE-9IM</a> matrix for {@link Geometry2D geometries}. Supported geometries are:
 * <ul>
 *     <li>{@link Point2D Point}</li>
 *     <li>{@link Line2D Line}</li>
 *     <li>{@link LineString2D LineString}</li>
 *     <li>{@link Polygon2D Polygon}</li>
 * </ul>
 *
 * <h2>Geometry dimension</h2>
 * The computations rely on the concept of geometry dimension. Each kind of geometry has a defined dimension that is such as:<br>
 * <table style="text-align: center; border-collapse: collapse;">
 *     <caption>Geometries dimension</caption>
 *     <tr style="border: 2px solid black; bgcolor: #FEFEFE;"><th>Geometry</th><th>Dimension</th></tr>
 *     <tr style="border: 2px solid black;"><td>Empty</td><td>-1</td></tr>
 *     <tr style="border: 2px solid black;"><td>{@link Point2D Point}</td><td>0</td></tr>
 *     <tr style="border: 2px solid black;"><td>{@link Line2D Line}</td><td>1</td></tr>
 *     <tr style="border: 2px solid black;"><td>{@link LineString2D LineString}</td><td>1</td></tr>
 *     <tr style="border: 2px solid black;"><td>{@link Polygon2D Polygon}</td><td>2</td></tr>
 * </table>
 * Let <i>g</i> be a geometry, the dimension of <i>g</i> is denoted dim(<i>g</i>).
 * If a geometry is multiple (set of points, lines, polygons, ...), it can be considered as a set of simple geometries.
 *
 * <h2>Geometry interior, exterior and boundary</h2>
 * For all geometry it can be defined its interior, its exterior and its boundary.
 * <h3>Interior</h3>
 * The interior of a geometry is defined by the geometry itself at the exception to its limits. The interior of a geometry <i>g</i> is denoted I(<i>g</i>).
 *
 * <h3>Exterior</h3>
 * The exterior of a geometry is defined by the space that rely on outside  the geometry limits. The exterior of a geometry <i>g</i> is denoted E(<i>g</i>).
 *
 * <h3>Boundary</h3>
 * The boundary of a geometry is defined by the geometry limits. The boundary of a geometry <i>g</i> is denoted B(<i>g</i>).
 * <br>
 * For a geometry <i>g</i>, its exterior E(<i>g</i>) always has a dimension of 2 in the sense that there is no polygon with infinite area.
 *
 * <h2>Matrix representation</h2>
 * Let <i>a</i> and <i>b</i> two geometries, the DE-9IM model is based on a 3&times;3 intersection matrix with the form:
 * $$
 * A = \begin{bmatrix}
 *        dim(I(a) \cap I(b)) &amp; dim(I(a) \cap B(b)) &amp; dim(I(a) \cap E(b)) \\
 *        dim(B(a) \cap I(b)) &amp; dim(B(a) \cap B(b)) &amp; dim(B(a) \cap E(b))  \\
 *        dim(E(a) \cap I(b)) &amp; dim(E(a) \cap B(b)) &amp; dim(E(a) \cap E(b))
 *     \end{bmatrix}
 * $$
 * where:
 * <ul>
 *     <li><i>I(x)</i> is the interior of the geometry <i>x</i></li>
 *     <li><i>E(x)</i> is the exterior of the geometry <i>x</i></li>
 *     <li><i>B(x)</i> is the boundary of the geometry <i>x</i></li>
 *     <li>dim(<i>x</i>) is the dimension of <i>x</i> with
 *         <ul>
 *             <li>{@value #DIM_EMPTY} if <i>x</i> is empty</li>
 *             <li>{@value #DIM_POINT} if <i>x</i> is a point</li>
 *             <li>{@value #DIM_LINE} if <i>x</i> is a line</li>
 *             <li>{@value #DIM_AREA} if <i>x</i> is an area (polygon)</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * <h2>Conventions</h2>
 * The terms interior and boundary are used in the sense of algebraic topology, so for instance the boundary of a non-closed line string is its two extremity points,
 * and the boundary of a closed line string is empty.
 *
 * <h2>Spatial relations</h2>
 * From a DE9IM matrix it is possible to express spatial relations for two geometries.
 *
 * <h3>Equals</h3>
 * The <a href="https://www.ogc.org/standards/sfs/">equals</a> predicate is satisfied if the two geometries have at least one point in common, and no point of either geometry lies in the exterior of the other geometry.
 * The <a href="https://en.wikipedia.org/wiki/DE-9IM">DE-9IM</a> Intersection Matrix for the two geometries matches the pattern:
 * <p style="text-align: center;">{@code T*F**FFF*}</p>
 *
 * <h3>Disjoint</h3>
 * The <a href="https://www.ogc.org/standards/sfs/">disjoint</a> predicate is satisfied if the two geometries have no point in common, and no point of either geometry lies in the interior or the boundary of the other geometry.
 * The <a href="https://en.wikipedia.org/wiki/DE-9IM">DE-9IM</a> Intersection Matrix for the two geometries matches the pattern:<br>
 * <p style="text-align: center;">{@code T*F**FFF*}</p>
 *
 * <h3>Intersects</h3>
 * The <a href="https://www.ogc.org/standards/sfs/">intersects</a> predicate is satisfied if the two geometries have at least one point in common.
 *
 * <h3>Touches</h3>
 * The touches predicate has the following equivalent definitions:<br>
 * - The geometries have at least one point in common, but their interiors do not intersect.<br>
 * - The <a href="https://en.wikipedia.org/wiki/DE-9IM">DE-9IM</a> Intersection Matrix for the two geometries matches at least one of the following patterns:<br>
 * <p style="text-align: center;">
 * {@code FT*******}<br>
 * {@code F**T*****}<br>
 * {@code F***T****}<br>
 * </p>
 *
 * <h3>Contains</h3>
 * The contains predicate has the following equivalent definitions:<br>
 * - Every point of the other geometry is a point of this geometry, and the interiors of the two geometries have at least one point in common.<br>
 * - The <a href="https://en.wikipedia.org/wiki/DE-9IM">DE-9IM</a> Intersection Matrix for the two geometries matches the pattern {@code [T*****FF*]}<br>
 *
 * <h3>Covers</h3>
 * The covers predicate has the following equivalent definitions:<br>
 * - Every point of the other geometry is a point of this geometry.<br>
 * - The <a href="https://en.wikipedia.org/wiki/DE-9IM">DE-9IM</a> Intersection Matrix for the two geometries matches at least one of the following patterns:<br>
 * {@code [T*****FF*]}<br>
 * {@code [*T****FF*]}<br>
 * {@code [***T**FF*]}<br>
 * {@code [****T*FF*]}<br>
 *
 * <h2>Supported geometries</h2>
 * The current implementation supports {@link Point2D points}, {@link Line2D lines}, {@link LineString2D line strings}, {@link Polygon2D polygons} and {@link Box2D boxes}.
 * The result is returned as a 3&times;3 {@link Matrix matrix} since {@link org.jeometry.math.Matrix3x3 Matrix3x3} has no concrete implementation registered to {@link org.jeometry.factory.JeometryFactory JeometryFactory}.
 * Calling this method with an {@link Ellipse2D} produces an {@link UnsupportedOperationException}.
 *
 * <br><br>[1] Eliseo Clementini, Paolino Di Felice, <i>A model for representing topological relationships between complex geometric features in spatial databases</i>, Information Sciences, Volume 90, Issues 1–4, 1996, Pages 121-136, ISSN 0020-0255.
 *
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
public final class DE9IM {

	/** Dimension value used when the considered set is empty. */
	public static final int DIM_EMPTY = -1;
	/** Dimension value used when the considered set is 0-dimensional (point set). */
	public static final int DIM_POINT = 0;
	/** Dimension value used when the considered set is 1-dimensional (curve). */
	public static final int DIM_LINE  = 1;
	/** Dimension value used when the considered set is 2-dimensional (area). */
	public static final int DIM_AREA  = 2;

	/** Row/column index for the Interior part of a geometry. */
	public static final int I = 0;
	/** Row/column index for the Boundary part of a geometry. */
	public static final int B = 1;
	/** Row/column index for the Exterior part of a geometry. */
	public static final int E = 2;

	/** Default tolerance applied to coordinate comparisons when the two-argument overload is used. */
	public static final double DEFAULT_EPSILON = 1.0e-10;

	/**
	 * private default constructor, make the class static.
	 */
	private DE9IM() {}

	/**
	 * Compute the DE-9IM matrix from the given {@link Geometry2D 2D geometries} using the {@link #DEFAULT_EPSILON default tolerance}.
	 * @param a the first geometry
	 * @param b the second geometry
	 * @return the DE-9IM matrix as a 3&times;3 {@link Matrix matrix}
	 * @see #computeMatrix(Geometry2D, Geometry2D, double)
	 * @throws IllegalArgumentException when one of the geometries is {@code null} or {@code epsilon} is negative
	 * @throws UnsupportedOperationException when one of the geometries is an {@link Ellipse2D} or any unsupported geometry kind
	 */
	public static Matrix computeMatrix(Geometry2D a, Geometry2D b) {
		return computeMatrix(a, b, DEFAULT_EPSILON);
	}

	/**
	 * Compute the DE-9IM matrix from the given {@link Geometry2D 2D geometries} using the given coordinate tolerance.
	 * @param a the first geometry
	 * @param b the second geometry
	 * @param epsilon the tolerance used to compare coordinates (must be {@code >= 0})
	 * @return the DE-9IM matrix as a 3&times;3 {@link Matrix matrix}
	 * @throws IllegalArgumentException when one of the geometries is {@code null} or {@code epsilon} is negative
	 * @throws UnsupportedOperationException when one of the geometries is an {@link Ellipse2D} or any unsupported geometry kind
	 */
	public static Matrix computeMatrix(Geometry2D a, Geometry2D b, double epsilon) {
		if (a == null || b == null) {
			throw new IllegalArgumentException("Geometries must not be null");
		}
		if (epsilon < 0.0) {
			throw new IllegalArgumentException("epsilon must be >= 0");
		}
		if (a instanceof Ellipse2D || b instanceof Ellipse2D) {
			throw new UnsupportedOperationException("DE-9IM is not yet supported for Ellipse2D");
		}

		Kind ka = kindOf(a);
		Kind kb = kindOf(b);

		// Always dispatch with ka <= kb in the canonical order; transpose if we swapped.
		if (ka.ordinal() <= kb.ordinal()) {
			return dispatch(a, ka, b, kb, epsilon);
		}
		return DE9IMUtils.transpose(dispatch(b, kb, a, ka, epsilon));
	}

	private static Matrix dispatch(Geometry2D a, Kind ka, Geometry2D b, Kind kb, double eps) {
		switch (ka) {
			case POINT:
				switch (kb) {
					case POINT:   return PointPointDE9IM.compute((Point2D) a, (Point2D) b, eps);
					case LINE:    return PointLineStringDE9IM.compute((Point2D) a, b, eps);
					case POLYGON: return PointPolygonDE9IM.compute((Point2D) a, b, eps);
					default:      break;
				}
				break;
			case LINE:
				switch (kb) {
					case LINE:    return LineStringLineStringDE9IM.compute(a, b, eps);
					case POLYGON: return LineStringPolygonDE9IM.compute(a, b, eps);
					default:      break;
				}
				break;
			case POLYGON:
				if (kb == Kind.POLYGON) {
					return PolygonPolygonDE9IM.compute(a, b, eps);
				}
				break;
			default:
				break;
		}
		throw new UnsupportedOperationException("Unsupported geometry pair: " + a.getClass().getSimpleName()
		        + " / " + b.getClass().getSimpleName());
	}

	/** Internal coarse classification used by the dispatcher. The order is significant (canonical pair order). */
	enum Kind { POINT, LINE, POLYGON }

	private static Kind kindOf(Geometry2D g) {
		if (g instanceof Point2D) return Kind.POINT;
		if (g instanceof Line2D || g instanceof LineString2D) return Kind.LINE;
		if (g instanceof Polygon2D || g instanceof Box2D) return Kind.POLYGON;
		throw new UnsupportedOperationException("Unsupported geometry: " + g.getClass().getName());
	}
}
