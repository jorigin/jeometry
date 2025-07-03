package org.jeometry.test.geom2D.primitive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.primitive.Ellipse2D;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unitary tests dedicated to {@link Ellipse2D} implementations.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.6
 */
public class Ellipse2DTest {

	/**
	 * Test the access to the property of the {@link Ellipse2D} implementation.
	 */
	@Test
	public void propertiesAccessTest() {

		Point2D center = JeometryFactory.createPoint2D(1.0d, 2.0d);
		double angle = Math.PI / 6.0d;

		double semiMajorAxis = 2.0d;
		double semiMinorAxis = 1.3d;

		Ellipse2D e = JeometryFactory.createEllipse2D(center, semiMajorAxis, semiMinorAxis);

		assertNotNull(e, "Cannot create Ellipse2D implementation");

		assertNotNull(e.getCenter(), "Invalid center for Ellipse3D implementation.");

		assertEquals(center.getX(), e.getCenter().getX(), Double.MIN_VALUE, "Got "+e.getCenter().getX()+" for center X coordinate but "+center.getX()+" was expected.");
		assertEquals(center.getY(), e.getCenter().getY(), Double.MIN_VALUE, "Got "+e.getCenter().getY()+" for center Y coordinate but "+center.getY()+" was expected.");

		assertEquals(e.getCenter().getX(), e.getX(), Double.MIN_VALUE, "getCenter().getX() and getX() must return the same value.");
		assertEquals(e.getCenter().getY(), e.getY(), Double.MIN_VALUE, "getCenter().getY() and getY() must return the same value.");

		assertEquals(semiMajorAxis, e.getSemiMajorAxis(), Double.MIN_VALUE, "Got "+e.getSemiMajorAxis()+" as semi-major axis length but "+semiMajorAxis+" was expected.");
		assertEquals(semiMinorAxis, e.getSemiMinorAxis(), Double.MIN_VALUE, "Got "+e.getSemiMinorAxis()+" as semi-major axis length but "+semiMinorAxis+" was expected.");

		e = JeometryFactory.createEllipse2D(center, semiMajorAxis, semiMinorAxis, angle);

		assertNotNull(e, "Cannot create Ellipse2D implementation");

		assertNotNull(e.getCenter(), "Invalid center for Ellipse3D implementation.");

		assertEquals(center.getX(), e.getCenter().getX(), Double.MIN_VALUE, "Got "+e.getCenter().getX()+" for center X coordinate but "+center.getX()+" was expected.");
		assertEquals(center.getY(), e.getCenter().getY(), Double.MIN_VALUE, "Got "+e.getCenter().getY()+" for center Y coordinate but "+center.getY()+" was expected.");

		assertEquals(e.getCenter().getX(), e.getX(), Double.MIN_VALUE, "getCenter().getX() and getX() must return the same value.");
		assertEquals(e.getCenter().getY(), e.getY(), Double.MIN_VALUE, "getCenter().getY() and getY() must return the same value.");

		assertEquals(semiMajorAxis, e.getSemiMajorAxis(), Double.MIN_VALUE, "Got "+e.getSemiMajorAxis()+" as semi-major axis length but "+semiMajorAxis+" was expected.");
		assertEquals(semiMinorAxis, e.getSemiMinorAxis(), Double.MIN_VALUE, "Got "+e.getSemiMinorAxis()+" as semi-major axis length but "+semiMinorAxis+" was expected.");

		assertEquals(angle, e.getAngle(), Double.MIN_VALUE, "Got "+e.getAngle()+" as angle but "+angle+" was expected.");
	}
}
