package org.jeometry.geom2D.primitive;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.VertexGeometry2D;
import org.jeometry.geom2D.point.Point2D;

/**
 * A 2D box that represents a rectangle with axis aligned edges. A box is characterized by a minimum point (x, y) and a size (width, height). 
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
public interface Box2D extends VertexGeometry2D {

	/**
	 * Get the origin point of the box. By convention this point is the minimum point (i-e the upper left point).
	 * @return the origin point of the box
	 * @see #setOrigin(Point2D)
	 * @see #getWidth()
	 * @see #getHeight()
	 */
	public Point2D getOrigin();
	
	/**
	 * Set the origin point of the box. By convention this point is the minimum point (i-e the upper left point).
	 * @param origin the origin point of the box
	 * @return a reference to this box (enables chaining)
	 * @see #getOrigin()
	 * @see #setWidth(double)
	 * @see #setHeight(double)
	 */
	public Box2D setOrigin(Point2D origin);
	
	/**
	 * Get the width of this box. By convention the width of a box is its length along the <i>X</i> axis.
	 * @return the width of this box
	 * @see #setWidth(double)
	 * @see #getHeight()
	 * @see #getOrigin()
	 */
	public double getWidth();
	
	/**
	 * Set the width of this box. By convention the width of a box is its length along the <i>X</i> axis.
	 * @param width the width of this box
	 * @return a reference to this box (enables chaining)
	 * @see #getWidth()
	 * @see #setHeight(double)
	 * @see #setOrigin(Point2D)
	 */
	public Box2D setWidth(double width);
	
	/**
	 * Get the height of this box. By convention the height of a box is its length along the <i>Y</i> axis.
	 * @return the height of this box
	 * @see #setHeight(double)
	 * @see #getWidth()
	 * @see #getOrigin()
	 */
	public double getHeight();
	
	/**
	 * Set the height of this box. By convention the height of a box is its length along the <i>Y</i> axis.
	 * @param height the height of this box
	 * @return a reference to this box (enables chaining)
	 */
	public Box2D setHeight(double height);
}
