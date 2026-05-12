package org.jeometry.simple.geom2D;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;

/**
 * A utility class for simple 2D geometry operations.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jcommon">github.com/jorigin/jcommon</a> (<a href="mailto:contact@jorigin.org">contact@jorigin.org</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
public class SimpleGeometry2DUtils {

	/**
	 * Compares two {@link Point2D 2D Points} for geometric equality. 
	 * Two points are considered geometrically equal if they have the same coordinates or if both are <code>null</code>. 
	 * @param a the first 2D point
	 * @param b the second 2D Point
	 * @return <code>true</code> if both points are geometrically equal, <code>false</code> otherwise
	 */
	public static boolean geomEquals(Point2D a, Point2D b) {
		if (a == b) {
			return true;
		}
		
		if (a == null || b == null) {
			return false;
		}
		
		return a.getX() == b.getX() && a.getY() == b.getY();
	}
	
	/**
	 * Compares a {@link Point2D 2D Points} with a {@link Point2DContainer 2D points container} for geometric equality. 
	 * A a 2D point <code>a</code> is considered geometrically equal to a 2D points container <code>b</code> if <code>b</code> contains exactly one point and that has same coordinates as <code>a</code>, 
	 * or if both <code>a</code> and <code>b</code> are <code>null</code>. 
	 * @param a a 2D point
	 * @param b a 2D point container
	 * @return <code>true</code> if both <code>a</code> and <code>b</code> are geometrically equal, <code>false</code> otherwise
	 */
	public static boolean geomEquals(Point2D a, Point2DContainer b) {
		if (a == null && b == null) {
			return true;
		}
		
		if (b == null) {
			return false;
		}
		
		if (b.size() != 1) {
			return false;
		}
		
		return geomEquals(a, b.get(0));
	}
	
	/**
	 * Compare two {@link Geometry2D 2D geometries} for geometric equality. The implementation of this method depends on the actual types of the given geometries.
	 * @param a the first geometry
	 * @param b the second geometry
	 * @return <code></code>true</code> if both geometries are geometrically equal, <code>false</code> otherwise
	 */
	public static boolean geomEquals(Geometry2D a, Geometry2D b) {
		if (a == b) {
			return true;
		}
		
		if (a == null || b == null) {
			return false;
		}
		
		return a.geomEquals(b);
	}
}
