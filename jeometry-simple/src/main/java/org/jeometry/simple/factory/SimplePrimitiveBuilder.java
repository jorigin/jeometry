package org.jeometry.simple.factory;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.PrimitiveBuilder;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.Ellipse2D;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.LineString2D;
import org.jeometry.simple.geom2D.primitive.SimpleBox2D;
import org.jeometry.simple.geom2D.primitive.SimpleEllipse2D;
import org.jeometry.simple.geom2D.primitive.SimpleLine2D;
import org.jeometry.simple.geom2D.primitive.SimpleLineString2D;

/**
 * A simple implementation of the {@link PrimitiveBuilder primitive builder}.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jcommon">github.com/jorigin/jcommon</a> (<a href="mailto:contact@jorigin.org">contact@jorigin.org</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.6
 */
public class SimplePrimitiveBuilder implements PrimitiveBuilder {

	/**
	 * Create a new simple {@link PrimitiveBuilder primitive builder}.
	 */
	public SimplePrimitiveBuilder(){}

	@Override
	public Box2D createBox2D(Point2D min, double width, double height) {
		return new SimpleBox2D();
	}

	@Override
	public Box2D createBox2D(Point2D min, Point2D max) {
		return new SimpleBox2D(min, max);
	}

	@Override
	public Box2D createBox2D(double minX, double minY, double width, double height) {
		return new SimpleBox2D(minX, minY, width, height);
	}

	@Override
	public LineString2D createLineString2D(Point2D[] vertices, boolean closed) {
		return new SimpleLineString2D(vertices, closed);
	}

	@Override
	public Ellipse2D createEllipse2D(Point2D center, double semiMajorAxis, double semiMinorAxis, double angle) {
		return new SimpleEllipse2D(center, semiMajorAxis, semiMinorAxis, angle);
	}

	@Override
	public Ellipse2D createEllipse2D(Point2D center, double semiMajorAxis, double semiMinorAxis) {
		return new SimpleEllipse2D(center, semiMajorAxis, semiMinorAxis, 0.0d);
	}

	@Override
	public Line2D createLine2D(Point2D start, Point2D end) {
		return new SimpleLine2D(start, end);
	}
	
	@Override
	public LineString2D createLineString2D(Point2DContainer vertices, boolean closed) {
		return new SimpleLineString2D(vertices, closed);
	}

	@Override
	public LineString2D createLineString2D(List<? extends Point2D> vertices, boolean closed) {
		return new SimpleLineString2D(vertices, closed);
	}

}
