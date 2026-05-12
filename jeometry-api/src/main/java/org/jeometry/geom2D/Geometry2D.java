package org.jeometry.geom2D;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.LineString2D;
import org.jeometry.geom2D.primitive.Polygon2D;

/**
 * A geometry represents a subset of a space that be in relationship with other geometries.<br><br>
 * This interface focuses on representing geometric relationships. The definitions of methods within this interface are the same as the ones defined by the <a href="https://locationtech.github.io/jts">Java Topological Suite (JTS)</a>
 * and relies on the computation of <a href="https://en.wikipedia.org/wiki/DE-9IM">DE-9IM</a> matrices.<br><br>
 * The spatial relationships that are expressed by a geometry are:
 * <ul>
 *   <li>{@link #geomEquals(Geometry2D) Equals} for the topological equality
 *   <li>{@link #geomCovers(Geometry2D) Covers} that describe if a geometry is covering the other one
 *   <li>{@link #geomContains(Geometry2D) Contains} that describe if a geometry is containing the other one
 *   <li>{@link #geomIntersects(Geometry2D) Intersects} that describe if a geometry is intersecting the other one
 *   <li>{@link #geomTouches(Geometry2D) Touches} that describe if a geometry is touching the other one
 * </ul>
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version}
 * @since 2.0.1
 */
public interface Geometry2D extends SpatialLocalization2D{

	/**
	 * Tests whether this geometry is topologically equal to the given {@link Geometry2D geometry 2D} <code>g</code> as defined by the <a href="https://www.ogc.org/standards/sfs/">SFS equals</a> predicate.
	 * @param g the geometry to test against his one
	 * @return <code>true</code> if this Geometry equals <code>g</code>
	 * @see #geomCovers(Geometry2D)
	 * @see #geomContains(Geometry2D)
	 * @see #geomIntersects(Geometry2D)
	 * @see #geomTouches(Geometry2D)
	 */
	public boolean geomEquals(Geometry2D g);

	/**
	 * Tests whether this geometry intersects the given {@link Geometry2D geometry 2D} <code>g</code>.<br><br>
	 * @param g the geometry to test against his one
	 * @return <code>true</code> if this Geometry intersects <code>g</code>
	 * @see #geomEquals(Geometry2D)
	 * @see #geomCovers(Geometry2D)
	 * @see #geomContains(Geometry2D)
	 * @see #geomTouches(Geometry2D)
	 */
	public boolean geomIntersects(Geometry2D g);

	/**
	 * Tests whether this geometry touches the argument geometry.<br><br>
	 * If both geometries have dimension 0, the predicate returns <code>false</code>, since points have only interiors. This predicate is symmetric.
	 * @param g the geometry to test against his one
	 * @return <code>true</code> if this Geometry touches <code>g</code>
	 * @see #geomEquals(Geometry2D)
	 * @see #geomCovers(Geometry2D)
	 * @see #geomContains(Geometry2D)
	 * @see #geomIntersects(Geometry2D)
	 */
	public boolean geomTouches(Geometry2D g);

	/**
	 * Tests whether this geometry contains the given {@link Geometry2D geometry 2D} <code>g</code>.<br><br>
	 * An implication of the definition is that "Geometries do not contain their boundary". 
	 * In other words, if a geometry <code>A</code> is a subset of the points in the boundary of a geometry <code>B</code>, <code>B.contains(A) = false</code>. 
	 * (As a concrete example, take <code>A</code> to be a {@link LineString2D line set} which lies in the boundary of a {@link Polygon2D polygon} <code>B</code>.).<br><br>
	 * For a predicate with similar behavior but avoiding this subtle limitation see covers(org.locationtech.jts.geom.Geometry)
	 * @param g the geometry to test against his one
	 * @return <code>true</code> if this Geometry contains <code>g</code>
	 * @see #geomEquals(Geometry2D)
	 * @see #geomCovers(Geometry2D)
	 * @see #geomIntersects(Geometry2D)
	 * @see #geomTouches(Geometry2D)
	 * 
	 */
	public boolean geomContains(Geometry2D g);

	/**
	 * Tests whether this geometry covers the {@link Geometry2D geometry 2D} <code>g</code>.<br><br>
	 * If either geometry is empty, the value of this predicate is false.
	 * For most situations, covers should be used in preference to contains. As an added benefit, covers is more amenable to optimization, and hence should be more performant.
	 * @param g the geometry to test against his one
	 * @return <code>true</code> if this Geometry is within <code>g</code>
	 * @see #geomEquals(Geometry2D)
	 * @see #geomContains(Geometry2D)
	 * @see #geomIntersects(Geometry2D)
	 * @see #geomTouches(Geometry2D)
	 */
	public boolean geomCovers(Geometry2D g);

	/**
	 * Check if the geometry is empty, i-e if the geometry does not covers any point of the space. For example, a polygon with no vertices is empty, an ellipse with a 0 length axis is empty.
	 * @return <code>true</code> if the geometry is empty and <code>false</code> otherwise
	 */
	public boolean geomEmpty();

	/**
	 * Returns the dimension of this geometry.
	 * The dimension of a geometry is is the topological dimension of its embedding in the 2D Euclidean plane.
	 * Possible values are in the set {0,1,2}. For example: 
	 * <ul>
	 *   <li>a Point has a dimension of 0
	 *   <li>a Line has a dimension of 1
	 *   <li>a non empty rectangle has a dimension of 2
	 * </ul><br><br>
	 * Note that this is a different concept to the dimension of the vector (points) that compose the space.<br><br>
	 * The geometry dimension can never be greater than the coordinate dimension.
	 * For example, a 0-dimensional geometry (e.g. a Point) may have a coordinate dimension of 3 (X,Y,Z).
	 * @return the dimension of this geometry
	 */
	public int geomDimension();
	
	/**
	 * Get the bounds of this geometry.
	 * @return the bounds of this geometry
	 */
	public Box2D geomBounds();
}
