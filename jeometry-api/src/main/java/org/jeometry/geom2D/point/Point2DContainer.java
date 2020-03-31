package org.jeometry.geom2D.point;

import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom2D.SpatialLocalization2D;

/**
 * A container dedicated to the management of {@link Point2D 2D points}. Basically, this interface describes a list of 2D points.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public interface Point2DContainer extends List<Point2D>, SpatialLocalization2D {
  
}
