package org.jeometry.geom2D;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.Polygon2D;

/**
 * This interface describes {@link Geometry2D 2D geometries} that relies on vertices. For example, {@link Point2D Point}, {@link Line2D lines}, {@link Polygon2D polygons} are vertex based geometries.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version}
 * @since 2.0.1
 */
public interface VertexGeometry2D extends Geometry2D {

	/**
	 * Get the {@link Point2DContainer vertices} that compose this geometry.
	 * @return the vertices that compose this geometry
	 */
	public Point2DContainer getVertices();
	
	/**
	 * Get the number of vertices that compose this geometry.
	 * @return the number of vertices that compose this geometry
	 */
	public int getVerticesCount();
	
	/**
	 * Get the vertex at the given <code>index</code>
	 * @param index the index of the vertex to get
	 * @return the vertex at the given <code>index</code>
	 */
	public Point2D getVertex(int index);
	
	/**
	 * Get the vertex at the given <code>index</code> and copy it into the given <code>output</code> point.
	 * @param index the index of the vertex to get
	 * @param output the point to copy the vertex into
	 * @return a reference to <code>output</code>
	 */
	public Point2D getVertex(int index, Point2D output);
	
	/**
	 * Reverse the order of the vertices that compose this geometry.
	 * @return the current geometry, for chaining purposes
	 */
	public VertexGeometry2D reverseVertices();
}
