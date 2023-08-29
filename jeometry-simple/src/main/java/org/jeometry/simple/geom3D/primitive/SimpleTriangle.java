package org.jeometry.simple.geom3D.primitive;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Polygon3D;
import org.jeometry.geom3D.primitive.Triangle;

/**
 * A triangle is a 3D polygon hat relies on 3 non aligned vertices.
 * @param <T> the type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 * @see Mesh
 * @see Face
 * @see Polygon3D
 *
 */
public class SimpleTriangle<T extends Point3D> extends SimplePolygon3D<T> implements Triangle<T>{

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = Jeometry.BUILD;

	/**
	 * The mech.
	 */
	private Mesh<T> mesh = null;
	
	/**
	 * The vertices.
	 */
	private Point3DContainer<T> vertices = null;
	
	/**
	 * Create a new triangle defined by the given vertices.
	 * @param vertex1 the first vertex
	 * @param vertex2 the second vertex
	 * @param vertex3 the third vertex
	 */
	public SimpleTriangle(T vertex1, T vertex2, T vertex3) {
		this.vertices = JeometryFactory.createPoint3DContainer(3);
		this.vertices.add(vertex1);
		this.vertices.add(vertex2);
		this.vertices.add(vertex3);
	}
	
	@Override
	public void setVertices(Point3DContainer<T> vertices) {
		if ((vertices != null) && (vertices.size() == 3)) {
			this.vertices = vertices;
		} else {
			throw new IllegalArgumentException("Invalid vertices, must be a list of 3 vertex.");
		}
	}

	@Override
	public void inverseVerticesOrder() {
		if (this.vertices != null) {
			T i   = this.vertices.get(0);
			this.vertices.set(0, this.vertices.get(2));
			this.vertices.set(2, i);
		}
	}
	
	@Override
	public Mesh<T> getMesh() {
		return this.mesh;
	}

	@Override
	public void setMesh(Mesh<T> mesh) {
      this.mesh = mesh;
	}

	@Override
	public Point3DContainer<T> getVertices() {
		return this.vertices;
	}

	@Override
	public List<Edge<T>> getEdges() {
		// TODO implements SimpleTriangle.getEdges()
		return null;
	}
	
	@Override
	public T getVertex1() {
		
		if (this.vertices != null) {
			return this.vertices.get(0);
		} else {
			return null;
		}
	}

	@Override
	public T getVertex2() {
		if (this.vertices != null) {
			return this.vertices.get(1);
		} else {
			return null;
		}
	}

	@Override
	public T getVertex3() {
		if (this.vertices != null) {
			return this.vertices.get(2);
		} else {
			return null;
		}
	}

}
