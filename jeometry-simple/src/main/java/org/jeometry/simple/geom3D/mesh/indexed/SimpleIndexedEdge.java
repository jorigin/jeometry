package org.jeometry.simple.geom3D.mesh.indexed;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Polygon3D;

/**
 * A simple implementation of an {@link IndexedEdge indexed edge}. An Edge is a segment that delimits a {@link Polygon3D polygon} 
 * or that link {@link Face faces} of a mesh. Within an indexed geometry representation, an indexed edge holds only indices 
 * to the underlying points that compose its vertices.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleIndexedEdge<T extends Point3D> implements IndexedEdge<T>{

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = Jeometry.BUILD;

	/**
	 * The vertices.
	 */
	private int[] verticesIndexes = null;

	/**
	 * Is the indices have to be validated.
	 */
	private boolean validatedIndexes = false;

	/**
	 * The mesh.
	 */
	private IndexedMesh<T> mesh = null;

	/**
	 * Create a new empty indexed edge.
	 */
	public SimpleIndexedEdge() {
	}

	/**
	 * Create a new empty indexed edge.
	 * @param vertex1Index the index of the first extremity of the edge.
	 * @param vertex2Index the index of the second extremity of the edge.
	 */
	public SimpleIndexedEdge(int vertex1Index, int vertex2Index) {
	  this.verticesIndexes = new int[]{vertex1Index, vertex2Index};
	}
	
	/**
	 * Construct an indexed edge that relies on the given indices as extremities.
	 * @param vertex1Index the index of the first extremity of the edge.
	 * @param vertex2Index the index of the second extremity of the edge.
	 * @param mesh the mesh that holds this edge.
	 */
	public SimpleIndexedEdge(int vertex1Index, int vertex2Index, IndexedMesh<T> mesh){
		this.verticesIndexes = new int[]{vertex1Index, vertex2Index};
		this.mesh = mesh;
	}

	@Override
	public int[] getVerticesIndexes() {
		return this.verticesIndexes;
	}

	@Override
	public Point3DContainer<T> getVerticesSource() {
		if (this.mesh != null) {
		  return this.mesh.getVerticesSource();
		} else {
		  return null;
		}
	}

	@Override
	public void setVerticesSource(Point3DContainer<T> verticesSource) {
      throw new IllegalStateException("Cannot set vertices source for indexed edge, prefer changing underlying mesh source.");
	}

	@Override
	public boolean validateIndexes() {
		if (   (this.verticesIndexes != null)&&(this.verticesIndexes.length == 2)&&(getVerticesSource() != null)
				&&(this.verticesIndexes[0]>-1)&&(this.verticesIndexes[1]>-1)
				&&(this.verticesIndexes[0]<getVerticesSource().size())&&(this.verticesIndexes[1]<getVerticesSource().size())){

			this.validatedIndexes = true;
		} else {
			this.validatedIndexes = false;
		}

		return this.validatedIndexes;
	}

	@Override
	public boolean isValidatedIndexes() {
		return this.validatedIndexes;
	}

	@Override
	public List<Face<T>> getFaces() {
		// TODO implements SimpleIndexedEdge.getFaces()
		return null;
	}

	@Override
	public Point3DContainer<T> getVertices() {
		Point3DContainer<T> points = JeometryFactory.createPoint3DContainer();
		points.add(getVerticesSource().get(this.verticesIndexes[0]));
		points.add(getVerticesSource().get(this.verticesIndexes[1]));

		return points;
	}

	@Override
	public T getEnd1() {
		return getVerticesSource().get(this.verticesIndexes[0]);
	}

	@Override
	public T getEnd2() {
		return getVerticesSource().get(this.verticesIndexes[1]);
	}

	@Override
	public IndexedMesh<T> getMesh() {
		return this.mesh;
	}

	@Override
	public void setMesh(Mesh<T> mesh) {
		if (mesh instanceof IndexedMesh) {
			this.mesh = (IndexedMesh<T>)mesh;
		} else {
			throw new IllegalArgumentException("Cannot attach indexed edge to non indexed mesh.");
		}

	}

}
