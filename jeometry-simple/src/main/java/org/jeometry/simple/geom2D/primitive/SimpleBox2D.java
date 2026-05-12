package org.jeometry.simple.geom2D.primitive;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.SpatialLocalization2D;
import org.jeometry.geom2D.VertexGeometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom2D.primitive.Box2D;

/**
 * A simple implementation of the {@link Box2D} interface.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 2.0.1
 */
public class SimpleBox2D implements Box2D{

	/**
	 * The origin of the box (by convention the point of the box with minimal coordinates).
	 * @see #width
	 * @see #height
	 */
	private Point2D origin = null;

	/**
	 * The width of the box (by convention the length of the box along the X axis).
	 * @see #origin
	 * @see #height
	 */
	private double width = 0.0d;

	/**
	 * The height of the box (by convention the length of the box along the Y axis).
	 * @see #origin
	 * @see #width
	 */
	private double height = 0.0d;

	/**
	 * Is the polygon is inverted (i-e its vertices order is reversed))
	 */
	private boolean inverted = false;

	/**
	 * Create a new empty box. The box origin coordinates are set to <code>Double.NaN</code> and the width and height are set to <code>0.0</code>.
	 * @see #SimpleBox2D(Point2D, double, double)
	 * @see #SimpleBox2D(Point2D, Point2D)
	 * @see #SimpleBox2D(double, double, double, double)
	 */
	public SimpleBox2D() {
		this.origin = JeometryFactory.createPoint2D(Double.NaN, Double.NaN);
		this.width = 0.0d;
		this.height = 0.0d;

	}

	/**
	 * Create a new axis aligned {@link Box2D 2D box} with the given minimal point (i-e the point of the box with minimal coordinates), 
	 * a <code>width</code> (i-e the length of the box along the X axis) and an <code>height</code> (i-e the length of the box along the Y axis). 
	 * The given <code>min</code> point is referenced (and not copied).
	 * @param min the point of the box with minimal coordinates
	 * @param width  the width of the box along the X axis
	 * @param height the height of the box along the Y axis
	 * @see #SimpleBox2D()
	 * @see #SimpleBox2D(Point2D, Point2D)
	 * @see #SimpleBox2D(double, double, double, double)
	 */
	public SimpleBox2D(Point2D min, double width, double height) {

		if (min == null)
			this.origin = JeometryFactory.createPoint2D(Double.NaN, Double.NaN);
		else
			this.origin = min;

		this.width = width;
		this.height = height;
	}

	/**
	 * Create a new axis aligned {@link Box2D 2D box} with the given minimal point (i-e the point of the box with minimal coordinates) 
	 * and the given maximal point ()i-e the point of the box with maximal coordinates).
	 * The given <code>min</code> and <code>max</code> points are referenced (and not copied).
	 * @param min the point of the box with minimal coordinates
	 * @param max the point of the box with maximal coordinates
	 * @see #SimpleBox2D()
	 * @see #SimpleBox2D(Point2D, Point2D)
	 * @see #SimpleBox2D(double, double, double, double)
	 */
	public SimpleBox2D(Point2D min, Point2D max) {
		if (min == null)
			this.origin = JeometryFactory.createPoint2D(Double.NaN, Double.NaN);
		else
			this.origin = min;

		if (max != null) {
			this.width = max.getX() - this.origin.getX();
			this.height = max.getY() - this.origin.getY();
		} else {
			this.width = 0.0d;
			this.height = 0.0d;
		}
	}

	/**
	 * Create a new axis aligned {@link Box2D 2D box} with the given minimal coordinates <code>minX</code> and <code>minY</code>, 
	 * a <code>width</code> (i-e the length of the box along the X axis) and an <code>height</code> (i-e the length of the box along the Y axis). 
	 * @param minX the X minimal coordinate of the box
	 * @param minY the Y minimal coordinate of the box
	 * @param width  the width of the box along the X axis
	 * @param height the height of the box along the Y axis
	 */
	public SimpleBox2D(double minX, double minY, double width, double height) {
		this.origin = JeometryFactory.createPoint2D(minX, minY);
		this.width = width;
		this.height = height;
	}

	@Override
	public double getX() {

		if ((this.origin != null) && (this.origin.getX() != Double.NaN)){
			if (this.width != Double.NaN) {
				return this.origin.getX() + this.width / 2.0d;
			} else {
				return this.origin.getX();
			}			
		}

		return Double.NaN;
	}

	@Override
	public double getY() {
		if ((this.origin != null) && (this.origin.getY() != Double.NaN)){
			if (this.width != Double.NaN) {
				return this.origin.getY() + this.height / 2.0d;
			} else {
				return this.origin.getY();
			}			
		}

		return Double.NaN;
	}

	@Override
	public Point2D getMinCoordinates() {
		return JeometryFactory.createPoint2D(this.origin);
	}

	@Override
	public Point2D getMaxCoordinates() {
		if (this.origin != null) {
			return JeometryFactory.createPoint2D(this.origin.getX() + this.width, this.origin.getY() + this.height);
		}
		return JeometryFactory.createPoint2D(Double.NaN, Double.NaN);
	}

	@Override
	public double distance(SpatialLocalization2D spatial) {

		if (spatial == null) {
			return Double.NaN;
		}

		return Math.sqrt(  (spatial.getX() - (this.getX()+this.width  / 2.0d)) * (spatial.getX() - (this.getX()+this.width  / 2.0d))
				+ (spatial.getY() - (this.getY()+this.height / 2.0d)) * (spatial.getY() - (this.getY()+this.height / 2.0d)));
	}

	@Override
	public void updateLocalization() {
		// No signification with 2D box
	}

	@Override
	public Point2DContainer getVertices() {
		Point2DContainer vertices = JeometryFactory.createPoint2DContainer(4);
		vertices.add(this.getVertex(0));
		vertices.add(this.getVertex(1));
		vertices.add(this.getVertex(2));
		vertices.add(this.getVertex(3));
		return vertices;
	}

	@Override
	public int getVerticesCount() {
		return 4;
	}

	@Override
	public Point2D getVertex(int index) {
		return getVertex(index, JeometryFactory.createPoint2D());
	}

	@Override
	public Point2D getVertex(int index, Point2D output) {

		if (output == null) {
			return null;
		}

		if (this.inverted) {
			switch(index) {
			case 0:
				output.setX(this.origin.getX());
				output.setY(this.origin.getY());
				return output;
			case 1:
				output.setX(this.origin.getX());
				output.setY(this.origin.getY() + this.height);
				return output;
			case 2:
				output.setX(this.origin.getX() + this.width);
				output.setY(this.origin.getY() + this.height);
				return output;
			case 3:
				output.setX(this.origin.getX() + this.width);
				output.setY(this.origin.getY());
				return output;
			default:
				throw new IllegalArgumentException("Index must be in the range [0,3] for a Box2D geometry. Given index: " + index);
			}
		} else {
			switch(index) {
			case 0:
				output.setX(this.origin.getX());
				output.setY(this.origin.getY());
				return output;
			case 1:
				output.setX(this.origin.getX() + this.width);
				output.setY(this.origin.getY());
				return output;
			case 2:
				output.setX(this.origin.getX() + this.width);
				output.setY(this.origin.getY() + this.height);
				return output;
			case 3:
				output.setX(this.origin.getX());
				output.setY(this.origin.getY() + this.height);
				return output;
			default:
				throw new IllegalArgumentException("Index must be in the range [0,3] for a Box2D geometry. Given index: " + index);
			}
		}
	}

	@Override
	public VertexGeometry2D reverseVertices() {
		this.inverted = !this.inverted;
		return this;
	}

	@Override
	public Point2D getOrigin() {
		return this.origin;
	}

	@Override
	public Box2D setOrigin(Point2D origin) {
		this.origin = origin;
		return this;
	}

	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public Box2D setWidth(double width) {
		this.width = width;
		return this;
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	@Override
	public Box2D setHeight(double height) {
		this.height = height;
		return this;
	}

	@Override
	public boolean geomEquals(Geometry2D g) {
		// TODO implements SimplePoint2D.geomEquals(Geometry2D)
		return false;
	}

	@Override
	public boolean geomIntersects(Geometry2D g) {
		// TODO implements SimplePoint2D.geomIntersects(Geometry2D)
		return false;
	}

	@Override
	public boolean geomTouches(Geometry2D g) {
		// TODO implements SimplePoint2D.geomTouches(Geometry2D)
		return false;
	}

	@Override
	public boolean geomContains(Geometry2D g) {
		// TODO implements SimplePoint2D.geomContains(Geometry2D)
		return false;
	}

	@Override
	public boolean geomCovers(Geometry2D g) {
		// TODO implements SimplePoint2D.geomCovers(Geometry2D)
		return false;
	}

	@Override
	public boolean geomEmpty() {
		// A box limited to its origin is not empty (it covers its origin point itself)
		return this.origin == null || Double.isNaN(this.origin.getX()) || Double.isNaN(this.origin.getY());
	}

	@Override
	public int geomDimension() {
		return 2;
	}

	@Override
	public Box2D geomBounds() {
		return JeometryFactory.createBox2D(this.origin, this.width, this.height);
	}
}
