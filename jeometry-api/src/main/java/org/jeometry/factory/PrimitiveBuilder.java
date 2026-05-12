package org.jeometry.factory;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.Ellipse2D;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.LineString2D;


/**
 * An interface that describes a primitive builder. A primitive builder enables to create implementations of interfaces described within the <code>primitive</code> packages {@link org.jeometry.geom2D.primitive} and {@link org.jeometry.geom3D.primitive}.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.6
 */
public interface PrimitiveBuilder {

	/**
	 * Create a new axis aligned {@link Box2D 2D box} with the given minimal point (i-e the point of the box with minimal coordinates), 
	 * a <code>width</code> (i-e the length of the box along the X axis) and an <code>height</code> (i-e the length of the box along the Y axis). 
	 * The given <code>min</code> point is referenced (and not copied).
	 * @param min the point of the box with minimal coordinates
	 * @param width  the width of the box along the X axis
	 * @param height the height of the box along the Y axis
	 * @return a new axis aligned 2D box
	 * @see #createBox2D(Point2D, Point2D)
	 * @see #createBox2D(double, double, double, double)
	 */
	public Box2D createBox2D(Point2D min, double width, double height);
	
	/**
	 * Create a new axis aligned {@link Box2D 2D box} with the given minimal point (i-e the point of the box with minimal coordinates) 
	 * and the given maximal point ()i-e the point of the box with maximal coordinates).
	 * The given <code>min</code> and <code>max</code> points are referenced (and not copied).
	 * @param min the point of the box with minimal coordinates
	 * @param max the point of the box with maximal coordinates
	 * @return a new axis aligned 2D box
	 * @see #createBox2D(Point2D, double, double)
	 * @see #createBox2D(double, double, double, double)
	 */
	public Box2D createBox2D(Point2D min, Point2D max);
	
	/**
	 * Create a new {@link Line2D 2D line} with the given extremities.
	 * @param start the first extremity (starting point) of the line
	 * @param end the second extremity (ending point) of the line
	 * @return a new {@link Line2D 2D line} with the given extremities
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public Line2D createLine2D(Point2D start, Point2D end);
	
	/**
	 * Create a new {@link LineString2D 2D line string} with the given vertices. Each consecutive vertices represents a consecutive line segment. 
	 * If the <code>closed</code> parameter is <code>true</code>, the created line string is closed (i-e the first and last vertices are considered as linked by a segment). 
	 * A closed line string is called linear ring.
	 * @param vertices the vertices of the line string
	 * @param closed is <code>true</code> if the line string is closed (i-e the first and last vertices are considered as linked by a segment)
	 * @return a new {@link LineString2D 2D line string} with the given vertices
	 * @throws IllegalArgumentException if an instantiation error occurs
	 * @see #createLineString2D(List, boolean)
	 * @see #createLineString2D(Point2DContainer, boolean)
	 */
	public LineString2D createLineString2D(Point2DContainer vertices, boolean closed);
	
	/**
	 * Create a new {@link LineString2D 2D line string} with the given vertices. Each consecutive vertices represents a consecutive line segment. 
	 * If the <code>closed</code> parameter is <code>true</code>, the created line string is closed (i-e the first and last vertices are considered as linked by a segment). 
	 * A closed line string is called linear ring.
	 * @param vertices the vertices of the line string
	 * @param closed is <code>true</code> if the line string is closed (i-e the first and last vertices are considered as linked by a segment)
	 * @return a new {@link LineString2D 2D line string} with the given vertices
	 * @throws IllegalArgumentException if an instantiation error occurs
	 * @see #createLineString2D(Point2DContainer, boolean)
	 * @see #createLineString2D(Point2D[], boolean)
	 */
	public LineString2D createLineString2D(List<? extends Point2D> vertices, boolean closed);
	
	/**
	 * Create a new {@link LineString2D 2D line string} with the given vertices. Each consecutive vertices represents a consecutive line segment. 
	 * If the <code>closed</code> parameter is <code>true</code>, the created line string is closed (i-e the first and last vertices are considered as linked by a segment). 
	 * A closed line string is called linear ring.
	 * @param vertices the vertices of the line string
	 * @param closed is <code>true</code> if the line string is closed (i-e the first and last vertices are considered as linked by a segment)
	 * @return a new {@link LineString2D 2D line string} with the given vertices
	 * @throws IllegalArgumentException if an instantiation error occurs
	 * @see #createLineString2D(Point2DContainer, boolean)
	 * @see #createLineString2D(List, boolean)
	 */
	public LineString2D createLineString2D(Point2D[] vertices, boolean closed);
	
	/**
	 * Create a new axis aligned {@link Box2D 2D box} with the given minimal coordinates <code>minX</code> and <code>minY</code>, 
	 * a <code>width</code> (i-e the length of the box along the X axis) and an <code>height</code> (i-e the length of the box along the Y axis). 
	 * @param minX the X minimal coordinate of the box
	 * @param minY the Y minimal coordinate of the box
	 * @param width  the width of the box along the X axis
	 * @param height the height of the box along the Y axis
	 * @return a new axis aligned 2D box
	 * @see #createBox2D(Point2D, double, double)
	 * @see #createBox2D(Point2D, Point2D)
	 */
	public Box2D createBox2D(double minX, double minY, double width, double height);
	
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
