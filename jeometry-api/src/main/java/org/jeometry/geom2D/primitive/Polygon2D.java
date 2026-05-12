package org.jeometry.geom2D.primitive;

import java.io.Serializable;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.VertexGeometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;

/**
 * Represents a polygon with linear edges, which may include holes. 
 * The exterior boundary (also known as shell or hull) and inner boundaries (holes) of the polygon are represented by closed {@link LineString2D line strings}.
 * The boundary rings of the polygon may have any orientation.
 * Polygons are closed, simple geometries by definition.
 * <p>
 * The polygon model conforms to the assertions specified in the
 * <a href="http://www.opengis.org/techno/specs.htm">OpenGIS Simple Features Specification for SQL</a>.
 * <p>
 * A polygon is topologically valid if and only if:
 * <ul>
 *   <li>the coordinates which define it are valid coordinates
 *   <li>the linear rings for the shell and holes are valid (i.e. are closed and do not self-intersect)
 *   <li>holes touch the shell or another hole at at most one point (which implies that the rings of the shell and holes must not cross)
 *   <li>the interior of the polygon is connected, or equivalently no sequence of touching holes makes the interior of the polygon disconnected (i.e. effectively split the polygon into two pieces).
 * </ul>
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 2.0.1
 *
 */
public interface Polygon2D extends VertexGeometry2D, Serializable{

	  /**
	   * Set the vertices of the polygon. Each vertex is a 2D point.
	   * @param vertices the vertices of the polygon. Each vertex is a {@link Point2D 2D point}.
	   */
	  void setVertices(Point2DContainer vertices);
	  
	  /**
	   * Return the exterior boundary of the polygon as a closed {@link LineString2D 2D line string}. 
	   * Polygon exterior boundary (also named shell or hull) is made of all the lines that link consecutive vertices.
	   * @return a line string that compose the polygon exterior boundary (also named shell or hull)
	   * @see #getHoles()
	   */
	  LineString2D getExterior();

	  /**
	   * Return the closed {@link LineString2D line strings} that represents holes within the polygon.
	   * @return the closed line strings that represents holes within the polygon
	   * @see #getExterior()
	   * @see #getHolesCount()
	   */
	  List<LineString2D> getHoles();
	  
	  /**
	   * Get the number of holes that this polygon has.
	   * @return the number of holes that this polygon has
	   * @see #getHoles()
	   */
	  int getHolesCount();
	  
	  /**
	   * Get a hole of the polygon by its index.
	   * @param index the index of the hole to get. The index must be between 0 (inclusive) and {@link #getHolesCount()} (exclusive).
	   * @return the hole at the specified index.
	   * @throws IndexOutOfBoundsException if the index is out of bounds (i.e. less than 0 or greater than or equal to {@link #getHolesCount()}).
	   */
	  LineString2D getHole(int index);
}
