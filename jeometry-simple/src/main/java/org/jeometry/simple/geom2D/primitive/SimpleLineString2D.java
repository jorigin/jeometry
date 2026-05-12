package org.jeometry.simple.geom2D.primitive;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.SpatialLocalization2D;
import org.jeometry.geom2D.VertexGeometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.LineString2D;

/**
 * A simple implementation of an {@link LineString2D 2D line string}
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jcommon">github.com/jorigin/jcommon</a> (<a href="mailto:contact@jorigin.org">contact@jorigin.org</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
public class SimpleLineString2D implements LineString2D{

	/**
	 * the serial version UID.
	 * <p>
	 * This is used to ensure that the class can be serialized and deserialized correctly.
	 * </p>
	 * <p>
	 * It is recommended to use a unique value for each class, so that the serialized data can be
	 * read back correctly.
	 * </p>
	 */
	private static final long serialVersionUID = Jeometry.BUILD;

	/**
	 * the vertices of the line string.
	 */
	private Point2DContainer vertices;

	/**
	 * is <code>true</code> if the line string is closed (i-e the first and last vertices are considered as linked by a segment).
	 */
	private boolean closed = false;


	/**
	 * Create an empty line string.
	 */
	public SimpleLineString2D() {
		this.vertices = JeometryFactory.createPoint2DContainer();
		this.closed = false;
	}

	/**
	 * Create a new simple implementation of an {@link LineString2D 2D line string} with the given vertices.
	 * Each consecutive vertices represents a consecutive line segment.
	 * If the <code>closed</code> parameter is <code>true</code>, the created line string is closed (i-e the first and last vertices are considered as linked by a segment).
	 * A closed line string is called linear ring.
	 * @param vertices the vertices of the line string
	 * @param closed is <code>true</code> if the line string is closed (i-e the first and last vertices are considered as linked by a segment)
	 */
	public SimpleLineString2D(Point2DContainer vertices, boolean closed) {
		this.vertices = vertices;
		this.closed = closed;
	}

	/**
	 * Create a new simple implementation of an {@link LineString2D 2D line string} with the given vertices.
	 * Each consecutive vertices represents a consecutive line segment.
	 * If the <code>closed</code> parameter is <code>true</code>, the created line string is closed (i-e the first and last vertices are considered as linked by a segment).
	 * A closed line string is called linear ring.
	 * @param vertices the vertices of the line string
	 * @param closed is <code>true</code> if the line string is closed (i-e the first and last vertices are considered as linked by a segment)
	 */
	public SimpleLineString2D(List<? extends Point2D> vertices, boolean closed) {
		this.vertices = JeometryFactory.createPoint2DContainer(vertices);
		this.closed = false;
	}

	/**
	 * Create a new simple implementation of an {@link LineString2D 2D line string} with the given vertices.
	 * Each consecutive vertices represents a consecutive line segment.
	 * If the <code>closed</code> parameter is <code>true</code>, the created line string is closed (i-e the first and last vertices are considered as linked by a segment).
	 * A closed line string is called linear ring.
	 * @param vertices the vertices of the line string
	 * @param closed is <code>true</code> if the line string is closed (i-e the first and last vertices are considered as linked by a segment)
	 */
	public SimpleLineString2D(Point2D[] vertices, boolean closed) {
		this.vertices = JeometryFactory.createPoint2DContainer(vertices);
		this.closed = false;
	}

	@Override
	public Point2DContainer getVertices() {
		return this.vertices;
	}

	@Override
	public int getVerticesCount() {
		return (this.vertices != null) ? this.vertices.size() : 0;
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
		
		if (this.vertices != null) {
			this.vertices.get(index, output);
			return output;
		} else {
			output.setX(Double.NaN);
			output.setY(Double.NaN);
		}

		return output;
	}

	@Override
	public VertexGeometry2D reverseVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean geomEquals(Geometry2D g) {
		// TODO Implements SimpleLineString2D.geomEquals(Geometry)
		return false;
	}

	@Override
	public boolean geomIntersects(Geometry2D g) {
		// TODO Implements SimpleLineString2D.geomIntersects(Geometry)
		return false;
	}

	@Override
	public boolean geomTouches(Geometry2D g) {
		// TODO Implements SimpleLineString2D.geomTouches(Geometry)
		return false;
	}

	@Override
	public boolean geomContains(Geometry2D g) {
		// TODO Implements SimpleLineString2D.geomContains(Geometry)
		return false;
	}

	@Override
	public boolean geomCovers(Geometry2D g) {
		// TODO Implements SimpleLineString2D.geomCovers(Geometry)
		return false;
	}

	@Override
	public boolean geomEmpty() {
		return (this.vertices == null || this.vertices.isEmpty());
	}

	@Override
	public int geomDimension() {
		return 1;
	}

	@Override
	public Box2D geomBounds() {
		// TODO Implements SimpleLineString2D.geomBounds()
		return null;
	}

	@Override
	public double getX() {
		// TODO Implements SimpleLineString2D.getX()
		return 0;
	}

	@Override
	public double getY() {
		// TODO Implements SimpleLineString2D.getY()
		return 0;
	}

	@Override
	public Point2D getMinCoordinates() {
		// TODO Implements SimpleLineString2D.getMinCoordinates()
		return null;
	}

	@Override
	public Point2D getMaxCoordinates() {
		// TODO Implements SimpleLineString2D.getMaxCoordinates()
		return null;
	}

	@Override
	public double distance(SpatialLocalization2D spatial) {
		// TODO Implements SimpleLineString2D.distance(SpatialLocalization2D)
		return 0;
	}

	@Override
	public void updateLocalization() {
		// TODO Implements SimpleLineString2D.updateLocalization()

	}

	@Override
	public List<Line2D> getLines() {
		// TODO Implements SimpleLineString2D.getLines()
		return null;
	}

	@Override
	public void plot(Point2D point) {
		// TODO Implements SimpleLineString2D.plot(Point2D)

	}

	@Override
	public boolean isClosed() {
		return this.closed;
	}

}
