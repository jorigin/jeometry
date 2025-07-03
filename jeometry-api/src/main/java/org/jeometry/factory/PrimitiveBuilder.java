package org.jeometry.factory;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Ellipse2D;


/**
 * An interface that describes a primitive builder. A primitive builder enables to create implementations of interfaces described within the <code>primitive</code> packages {@link org.jeometry.geom2D.primitive} and {@link org.jeometry.geom3D.primitive}.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jcommon">github.com/jorigin/jcommon</a> (<a href="mailto:contact@jorigin.org">contact@jorigin.org</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.6
 */
public interface PrimitiveBuilder {

  /**
   * Create a new {@link Ellipse2D 2D ellipse} with the given parameters. 
   * @param center the center of the ellipse (the point where the axis are crossing)
   * @param semiMajorAxis the semi-major axis length
   * @param semiMinorAxis the semi-minor axis length
   * @param angle the angle of the ellipse, in radians (rad). 
   * @return a new {@link Ellipse2D 2D ellipse} created with the given parameters
   * @see #createEllipse2D(Point2D, double, double, double)
   */
	public Ellipse2D createEllipse2D(Point2D center, double semiMajorAxis, double semiMinorAxis, double angle);
	
	/**
     * Create a new {@link Ellipse2D 2D ellipse} with the given parameters. 
     * @param center the center of the ellipse (the point where the axis are crossing)
     * @param semiMajorAxis the semi-major axis length
     * @param semiMinorAxis the semi-minor axis length
	 * @return a new {@link Ellipse2D 2D ellipse} created with the given parameters
	 * @see #createEllipse2D(Point2D, double, double, double)
	 */
	public Ellipse2D createEllipse2D(Point2D center, double semiMajorAxis, double semiMinorAxis);
}
