package org.jeometry.simple.factory;

import org.jeometry.Jeometry;
import org.jeometry.factory.PrimitiveBuilder;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Ellipse2D;
import org.jeometry.simple.geom2D.primitive.SimpleEllipse2D;

/**
 * 
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jcommon">github.com/jorigin/jcommon</a> (<a href="mailto:contact@jorigin.org">contact@jorigin.org</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.6
 */
public class SimplePrimitiveBuilder implements PrimitiveBuilder {

	@Override
	public Ellipse2D createEllipse2D(Point2D center, double semiMajorAxis, double semiMinorAxis, double angle) {
		return new SimpleEllipse2D(center, semiMajorAxis, semiMinorAxis, angle);
	}

	@Override
	public Ellipse2D createEllipse2D(Point2D center, double semiMajorAxis, double semiMinorAxis) {
		return new SimpleEllipse2D(center, semiMajorAxis, semiMinorAxis, 0.0d);
	}

}
