package org.jeometry.simple.geom3D.primitive.indexed;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.LineSet3D;
import org.jeometry.geom3D.primitive.indexed.IndexedPolygon3D;
import org.jeometry.geom3D.primitive.indexed.IndexedTriangle;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedEdge;
import org.jeometry.simple.geom3D.primitive.SimpleLineSet3D;

/**
 * A simple implementation of the {@link IndexedTriangle }
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class SimpleIndexedTriangle<T extends Point3D> implements IndexedTriangle<T> {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = Jeometry.BUILD;

	/**
	 * The indices.
	 */
	private int[] indices = null;
	
	/**
	 * The mesh.
	 */
	private IndexedMesh<T> mesh = null;
	
	/**
	 * Is the indices have to be validated.
	 */
	private boolean validatedIndices = false;
	
	@Override
	public T getVertex1() {
		
		if (getVerticesSource() != null) {
			return getVerticesSource().get(0);
		}
		
		return null;
	}

	@Override
	public T getVertex2() {
		if (getVerticesSource() != null) {
			return getVerticesSource().get(1);
		}
		
		return null;
	}

	@Override
	public T getVertex3() {
		if (getVerticesSource() != null) {
			return getVerticesSource().get(2);
		}
		
		return null;
	}

	@Override
	public void setMesh(Mesh<T> mesh) {
		if ((mesh != null) && (mesh instanceof IndexedMesh)) {
			this.mesh = (IndexedMesh<T>) mesh;
		} else {
			throw new IllegalArgumentException("Cannot set non indexed mesh as indexed geometry mesh.");
		}
	}

	@Override
	public Point3DContainer<T> getVertices() {
		
		if (isValidatedIndexes()) {
			if (getVerticesSource() != null) {
				Point3DContainer<T> vertices = JeometryFactory.createPoint3DContainer(3);
				vertices.add(getVertex1());
				vertices.add(getVertex2());
				vertices.add(getVertex3());
				
				return vertices;
			}
		} else {
			throw new IllegalStateException("Cannot access to vertices from indices with not valid geometry.");
		}

		return null;
	}

	@Override
	public void setVertices(Point3DContainer<T> vertices) {
		if (vertices != null) {
			if (getVerticesSource() != null) {
				
				if (vertices.size() == 3) {
					int[] indices = new int[3];
					
					indices[0] = getVerticesSource().indexOf(vertices.get(0));
					indices[1] = getVerticesSource().indexOf(vertices.get(1));
					indices[2] = getVerticesSource().indexOf(vertices.get(2));
					
					if ((indices[0] != -1) && (indices[1] != -1) && (indices[2] != -1)) {
						setVerticesIndexes(indices);
						this.validatedIndices = true;
					}
					
				} else {
					throw new IllegalArgumentException("Triangular face only accept 3 vertices ("+vertices.size()+" proposed).");
				}

			} else {
				throw new IllegalStateException("Cannot set vertices to an indexed geometry with no vertex source attached.");
			}
		}
	}

	@Override
	public LineSet3D<T> getSegments() {
		if ((this.indices != null) && (getVerticesSource() != null)){
			LineSet3D<T> segments = new SimpleLineSet3D<T>();
			segments.plot(getVerticesSource().get(this.indices[0]));
			segments.plot(getVerticesSource().get(this.indices[1]));
			segments.plot(getVerticesSource().get(this.indices[2]));
			return segments;
		}
		return null;
	}

	@Override
	public void inverseVerticesOrder() {
      int tmp    = this.indices[0];
      this.indices[0] = this.indices[2];
      this.indices[2] = tmp;
	}

	@Override
	public IndexedMesh<T> getMesh() {
		return this.mesh;
	}

	@Override
	public List<? extends Edge<T>> getEdges() {
		return getEdgesIndexed();
	}
	
	@Override
	public void setMesh(IndexedMesh<T> mesh) {
		this.mesh = mesh;
	}

	@Override
	public int[] getVerticesIndexes() {
		return this.indices;
	}

	@Override
	public void setVerticesIndexes(int[] indices) {
		if (indices != null) {
			if (indices.length == 3) {
				this.indices = indices;
				this.validatedIndices = false;
			} else {
				throw new IllegalArgumentException("Triangular face only accept 3 vertex ("+indices.length+") proposed.");
			}
		}
	}
	
	@Override
	public List<IndexedEdge<T>> getEdgesIndexed() {
		
		if (this.indices != null) {
			List<IndexedEdge<T>> edges = new ArrayList<IndexedEdge<T>>(3);
			edges.add(new SimpleIndexedEdge<T>(this.indices[0], this.indices[1], this.mesh));
			edges.add(new SimpleIndexedEdge<T>(this.indices[1], this.indices[2], this.mesh));
			edges.add(new SimpleIndexedEdge<T>(this.indices[2], this.indices[0], this.mesh));
			return edges;
		}
		
		return null;
	}

	@Override
	public boolean equals(IndexedPolygon3D<?> polygon) {
		return this == polygon;
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
		throw new IllegalArgumentException("Cannot change indexed triangle vertice source, prefer changing the underlying mesh.");
	}

	@Override
	public boolean isValidatedIndexes() {
		return this.validatedIndices;
	}

	@Override
	public boolean validateIndexes() {
		return this.validatedIndices =    (getVerticesSource() != null) && (this.indices != null) 
				                  && (this.indices[0] > 0) && (this.indices[0] < getVerticesSource().size())
		                          && (this.indices[1] > 0) && (this.indices[1] < getVerticesSource().size())
				                  && (this.indices[2] > 0) && (this.indices[2] < getVerticesSource().size());
	}

	@Override
	public int getVertex1Index() {
		if (this.indices != null) {
			  return this.indices[0];
			} else {
			  throw new IllegalStateException("Indices of the geometry are null.");
			}
	}

	@Override
	public int getVertex2Index() {
		if (this.indices != null) {
			  return this.indices[1];
			} else {
			  throw new IllegalStateException("Indices of the geometry are null.");
			}
	}

	@Override
	public int getVertex3Index() {
		if (this.indices != null) {
			  return this.indices[2];
			} else {
			  throw new IllegalStateException("Indices of the geometry are null.");
			}
	}
	
	/**
	 * Create a new default indexed triangle.
	 */
	public SimpleIndexedTriangle() {
	}
	
	/**
	 * Create a new indexed triangles based on given vertex indices.
	 * @param vertex1 the first vertex index
	 * @param vertex2 the second vertex index
	 * @param vertex3 the third vertex index
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTriangle(int vertex1, int vertex2, int vertex3) {
		this(new int[] {vertex1, vertex2, vertex3}, null);
	}
	
	/**
	 * Create a new indexed triangles based on given vertex indices.
	 * @param indices an array of 3 indices that point the vertex of the triangle. 
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTriangle(int[] indices) {
		this(indices, null);
	}
	
	/**
	 * Create a new indexed triangles based on given vertex indices.
	 * @param indices a list of 3 indices that point the vertex of the triangle. 
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTriangle(List<Integer> indices) {
		this(indices, null);
	}
	
	/**
	 * Create a new indexed triangles based on given vertex indices.
	 * @param vertex1 the first vertex index
	 * @param vertex2 the second vertex index
	 * @param vertex3 the third vertex index
	 * @param mesh the mesh that holds the triangle
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTriangle(int vertex1, int vertex2, int vertex3, IndexedMesh<T> mesh) {
		this(new int[] {vertex1, vertex2, vertex3}, null);
	}
	
	/**
	 * Create a new indexed triangles based on given vertex indices.
	 * @param indices an array of 3 indices that point the vertex of the triangle. 
	 * @param mesh the mesh that hold the triangle.
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTriangle(int[] indices, IndexedMesh<T> mesh) {
		if (indices != null) {
			if (indices.length == 3) {
				this.indices = indices;
				this.mesh = mesh;
			} else {
				throw new IllegalArgumentException("Cannot instanciate a triangle with "+indices.length+" vertex indices.");
			}
		} else {
			throw new IllegalArgumentException("Cannot instanciate a triangle with null indices.");
		}
	}
	
	/**
	 * Create a new indexed triangles based on given vertex indices.
	 * @param indices a list of 3 indices that point the vertex of the triangle. 
	 * @param mesh the mesh that hold the triangle.
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTriangle(List<Integer> indices , IndexedMesh<T> mesh) {
		if (indices != null) {
			this.indices = indices.stream().mapToInt(i->i).toArray();
			this.mesh = mesh;
		} else {
			throw new IllegalArgumentException("Cannot instanciate a triangle with null indices.");
		}
	}
}
