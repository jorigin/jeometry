package org.jeometry.geom2D.primitive;

import java.io.Serializable;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.VertexGeometry2D;
import org.jeometry.geom2D.point.Point2D;

/**
 * A line string is a succession of {@link Line2D 2D lines} that shares points (the end point of a line is the start point of the following one).
 *  This structure is compliant with the Models an <a href="https://www.ogc.org/standards/geoapi/">OGC-style line string</a>.
 *  A line string consists of a sequence of two or more vertices, along with all points along the linearly-interpolated curves
 *  (line segments) between each pair of consecutive vertices.
 *  Consecutive vertices may be equal. The line segments in the line may intersect each other (in other words, the linestring may "curl back" in itself and self-intersect.
 *  line string with exactly two identical points are invalid.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 2.0.1
 *
 */
public interface LineString2D extends VertexGeometry2D, Serializable{

	/**
	 * Return the {@link Line2D lines} that compose the line set.
	 * @return a list containing all the segments of the line set.
	 */
	List<Line2D> getLines();

	/**
	 * Add a new point to the line set. A new line is created from the last plotted point and this one.
	 * @param point the point to add as a line extremity.
	 */
	void plot(Point2D point);
	
	/**
	 * Get if the line string is closed. A closed line string is a line string where the first and last points are equal.
 	 * @return <code>true</code> if the line string is closed, <code>false</code> otherwise.
	 */
	boolean isClosed();
}