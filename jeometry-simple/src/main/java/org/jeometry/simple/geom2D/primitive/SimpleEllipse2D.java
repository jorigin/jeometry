package org.jeometry.simple.geom2D.primitive;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.SpatialLocalization2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Ellipse2D;

/**
 * A simple implementation of an {@link Ellipse2D 2D ellipse}
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jcommon">github.com/jorigin/jcommon</a> (<a href="mailto:contact@jorigin.org">contact@jorigin.org</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.6
 */
public class SimpleEllipse2D implements Ellipse2D{

	/**
	 * the center of the ellipse (the point where the axis are crossing).
	 */
	private Point2D center;

	/**
	 * the semi-major axis length
	 */
	private double semiMajorAxis = 0.0d;

	/**
	 * the semi-minor axis length
	 */
	private double semiMinorAxis = 0.0d;

	/**
	 * the angle of rotation, in radians (rad).
	 */
	private double angle = 0.0d;

	/**
	 * Create a new simple implementation of an {@link Ellipse2D 2D ellipse}.
	 * @param center the center of the ellipse (the point where the axis are crossing)
	 * @param semiMajorAxis the semi-major axis length
	 * @param semiMinorAxis the semi-minor axis length
	 * @param angle the angle of rotation, in radians (rad).
	 */
	public SimpleEllipse2D(Point2D center, double semiMajorAxis, double semiMinorAxis, double angle) {
		this.center = center;
		this.semiMajorAxis = semiMajorAxis;
		this.semiMinorAxis = semiMinorAxis;
		this.angle = angle;
	}
	
	@Override
	public double getX() {
		return getCenter().getX();
	}

	@Override
	public double getY() {
		return getCenter().getY();
	}

	@Override
	public Point2D getMinCoordinates() {

		// No rotation applied, the minimal coordinates only relies on center and axis
		if (this.angle != 0) {

			// Normalize angle beetween [-PI; Pi]
			double nangle = this.angle - (2*Math.PI) * Math.floor((this.angle + Math.PI) / (2*Math.PI));

			// -PI or PI  rotation applied, the minimal coordinates only relies on center and axis
			if ((nangle != Math.PI) && (nangle != -Math.PI)){

				// -PI/2 or PI/2  rotation applied, the minimal coordinates only relies on center and axis but they are inverted
				if ((this.angle == Math.PI/2) || ((this.angle == -Math.PI/2))) {
					return JeometryFactory.createPoint2D(this.center.getX() - this.semiMinorAxis, this.center.getY() - this.semiMajorAxis);

					// Non specific rotation
				} else {
					double t = -this.semiMinorAxis * Math.tan(nangle) / this.semiMajorAxis;
					return JeometryFactory.createPoint2D(Math.round(this.center.getX() + this.semiMajorAxis * Math.cos(t) * Math.cos(nangle) - this.semiMinorAxis * Math.sin(t) * Math.sin(nangle)),
							Math.round(this.center.getY() + this.semiMinorAxis * Math.sin(t) * Math.cos(nangle) + this.semiMajorAxis * Math.cos(t) * Math.sin(nangle)));
				}
			}
		}

		return JeometryFactory.createPoint2D(this.center.getX() - this.semiMajorAxis, this.center.getY() - this.semiMinorAxis);
	}

	@Override
	public Point2D getMaxCoordinates() {
		// No rotation applied, the minimal coordinates only relies on center and axis
		if (this.angle != 0) {

			// Normalize angle beetween [-PI; Pi]
			double nangle = this.angle - (2*Math.PI) * Math.floor((this.angle + Math.PI) / (2*Math.PI));

			// -PI or PI  rotation applied, the minimal coordinates only relies on center and axis
			if ((nangle != Math.PI) && (nangle != -Math.PI)){

				// -PI/2 or PI/2  rotation applied, the minimal coordinates only relies on center and axis but they are inverted
				if ((this.angle == Math.PI/2) || ((this.angle == -Math.PI/2))) {
					return JeometryFactory.createPoint2D(this.center.getX() - this.semiMinorAxis, this.center.getY() - this.semiMajorAxis);

					// Non specific rotation
				} else {
					double t = Math.atan(this.semiMinorAxis * (1 / Math.tan(nangle) / this.semiMajorAxis));
					return JeometryFactory.createPoint2D(Math.round(this.center.getX() + this.semiMajorAxis * Math.cos(t) * Math.cos(nangle) - this.semiMinorAxis * Math.sin(t) * Math.sin(nangle)),
							Math.round(this.center.getY() + this.semiMinorAxis * Math.sin(t) * Math.cos(nangle) + this.semiMajorAxis * Math.cos(t) * Math.sin(nangle)));
				}
			}
		}

		return JeometryFactory.createPoint2D(this.center.getX() + this.semiMajorAxis, this.center.getY() + this.semiMinorAxis);
	}

	@Override
	public double distance(SpatialLocalization2D spatial) {
		return this.center.distance(spatial);
	}

	@Override
	public void updateLocalization() {
	}

	@Override
	public Point2D getCenter() {
		return this.center;
	}

	@Override
	public double getSemiMajorAxis() {
		return this.semiMajorAxis;
	}

	@Override
	public double getSemiMinorAxis() {
		return this.semiMinorAxis;
	}

	@Override
	public double getAngle() {
		return this.angle;
	}

}
