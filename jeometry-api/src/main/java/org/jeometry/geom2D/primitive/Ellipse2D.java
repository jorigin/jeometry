package org.jeometry.geom2D.primitive;


import org.jeometry.Jeometry;
import org.jeometry.geom2D.SpatialLocalization2D;
import org.jeometry.geom2D.point.Point2D;


/**
 * An ellipse within a 2D space. In mathematics, an ellipse is a plane curve surrounding two focal points, such that for all points on the curve, the sum of the two distances to the focal points is a constant. 
 * An ellipse can be characterized by a semi-major axis, a semi-minor axis, a center (where the axis are intersecting) and an angle (that represents the inclination of the ellipse).
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jcommon">github.com/jorigin/jcommon</a> (<a href="mailto:contact@jorigin.org">contact@jorigin.org</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.6
 */
public interface Ellipse2D extends SpatialLocalization2D {

	/**
	 * Get the center of the ellipse (the location of the intersection of its axis))
	 * @return the center of the ellipse (the location of the intersection of its axis))
	 * @see #getSemiMajorAxis()
	 * @see #getSemiMinorAxis()
	 */
	public Point2D getCenter();
	
	/**
	 * Get the length of the semi-major axis.
	 * @return the length of the semi-major axis.
	 * @see #getSemiMinorAxis()
	 * @see #getCenter()
	 */
	public double getSemiMajorAxis();
	
	/**
	 * Get the length of the semi-minor axis.
	 * @return the length of the semi-minor axis.
	 * @see #getSemiMajorAxis()
	 * @see #getCenter()
	 */
	public double getSemiMinorAxis();
	
	/**
	 * Get the angle of the ellipse in radian (rad). The angle represents the inclination of the ellipse.
	 * @return the angle of the ellipse in radian (rad)
	 */
	public double getAngle();
	
}
