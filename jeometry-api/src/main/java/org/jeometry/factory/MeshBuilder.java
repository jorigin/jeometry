package org.jeometry.factory;

import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.mesh.indexed.IndexedTriangleMesh;
import org.jeometry.geom3D.neighbor.MeshNeighborhood;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Triangle;
import org.jeometry.geom3D.primitive.indexed.IndexedTriangle;
import org.jeometry.geom3D.textured.Texturable;

/**
 * An interface that describes a mesh builder. A mesh builder enables to create implementations of interfaces described within the <code>mesh</code> package ({@link Mesh}, {@link Face}, {@link Edge}, ...).
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public interface MeshBuilder {
	
	/**
	 * Create a new default empty mesh.
	 * @param <T> The type of underlying 3D points
	 * @return a new default empty mesh
	 */
	public <T extends Point3D> Mesh<T> createMesh();

	/**
	 * Create a new instance of {@link Edge}.
	 * @param <T> The type of underlying 3D points
	 * @param mesh the mesh that contains the edge
	 * @param vertex1 the first extremity of the edge
	 * @param vertex2 the second extremity of the edge
	 * @return a new instance of {@link Edge} using the given <code>implementation</code>
	 */
	public <T extends Point3D> Edge<T> createMeshEdge(Mesh<T> mesh, T vertex1, T vertex2);

	/**
	 * Create a new mesh {@link Face} from the given vertices attached to the given {@link Mesh mesh}. 
	 * The face contour is obtained by linking vertices from the first to the last.
	 * @param <T> The type of underlying 3D points
	 * @param mesh the mesh that holds the face
	 * @param vertices the vertices of the face
	 * @return a new mesh {@link Face} from the given vertices. 
	 */
	public <T extends Point3D> Face<T> createMeshFace(Mesh<T> mesh, Point3DContainer<T> vertices);

	/**
	 * Create a new mesh {@link Face} from the given vertices. 
	 * @param <T> The type of underlying 3D points
	 * @param vertices the vertices of the face
	 * @return a new mesh {@link Face} from the given vertices
	 */
	public <T extends Point3D> Face<T> createMeshFace(Point3DContainer<T> vertices);
	
	/**
	 * Create a new triangle {@link Face} from the given vertices. 
	 * @param <T> The type of underlying 3D points
	 * @param vertex1 the first vertex
	 * @param vertex2 the second vertex
	 * @param vertex3 the third vertex
	 * @return a new {@link Triangle triangle} from the given vertices. 
	 */
	public <T extends Point3D> Triangle<T> createMeshTriangle(T vertex1, T vertex2, T vertex3);

	/**
	 * Create a new {@link IndexedMesh indexed mesh}.
	 * @param <T> The type of underlying 3D points
	 * @return the newly created indexed mesh
	 */
	public <T extends Point3D> IndexedMesh<T> createIndexedMesh();

	/**
	 * Create a new {@link IndexedMesh indexed mesh}.
	 * @param <T> The type of underlying 3D points
	 * @param capacity the initial capacity of the mesh in term of faces storage.
	 * @return the newly created indexed mesh
	 */
	public <T extends Point3D> IndexedMesh<T> createIndexedMesh(int capacity);

	/**
	 * Create an {@link IndexedEdge indexed edge} between the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param vertex1Index the index of the first extremity
	 * @param vertex2Index the index of the second extremity
	 * @return an indexed edge between the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedEdge<T> createIndexedMeshEdge(int vertex1Index, int vertex2Index);

	/**
	 * Create an {@link IndexedEdge indexed edge} between the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param vertex1Index the index of the first extremity
	 * @param vertex2Index the index of the second extremity
	 * @param mesh the mesh that hold the edge
	 * @return an indexed edge between the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedEdge<T> createIndexedMeshEdge(int vertex1Index, int vertex2Index, IndexedMesh<T> mesh);

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedFace<T> createIndexedMeshFace(int[] indices);

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @param mesh the indexed mesh that hold the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedFace<T> createIndexedMeshFace(int[] indices, IndexedMesh<T> mesh);

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedFace<T> createIndexedMeshFace(List<Integer> indices);

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @param mesh the indexed mesh that hold the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices using the given implementation
	 */
	public <T extends Point3D> IndexedFace<T> createIndexedMeshFace(List<Integer> indices, IndexedMesh<T> mesh);

	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param vertex1 the first vertex index of the face
	 * @param vertex2 the second vertex index of the face
	 * @param vertex3 the third vertex index of the face
	 * @param mesh the mesh that holds the triangle
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedTriangle<T> createIndexedTriangle(int vertex1, int vertex2, int vertex3, IndexedMesh<T> mesh);

	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param vertices  the vertices of the face (a 3 element array)
	 * @param mesh the mesh that holds the triangle
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedTriangle<T> createIndexedTriangle(int[] vertices, IndexedMesh<T> mesh);

	/**
	 * Create a new {@link IndexedTriangleMesh indexed triangle mesh} that relies on the given vertices source.
	 * @param <T> The type of underlying 3D points
	 * @param source the indexed mesh vertices source
	 * @return a new {@link IndexedTriangleMesh indexed triangle mesh}
	 */
	public <T extends Point3D> IndexedTriangleMesh<T> createIndexedTriangleMesh(Point3DContainer<T> source);

	/**
	 * Create a new {@link IndexedTriangleMesh indexed triangle mesh} that relies on the given vertices source 
	 * with the given initial faces capacity.
	 * @param <T> The type of underlying 3D points
	 * @param size the initial faces capacity
	 * @param source  the indexed mesh vertices source
	 * @return a new {@link IndexedTriangleMesh indexed triangle mesh}
	 */
	public <T extends Point3D> IndexedTriangleMesh<T> createIndexedTriangleMesh(int size, Point3DContainer<T> source);

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(int[] indices);
	
	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @param mesh the indexed mesh that hold the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(int[] indices, IndexedMesh<T> mesh);

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(List<Integer> indices);

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @param mesh the indexed mesh that hold the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(List<Integer> indices, IndexedMesh<T> mesh);

	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param vertex1 the first vertex index of the face
	 * @param vertex2 the second vertex index of the face
	 * @param vertex3 the third vertex index of the face
	 * @param mesh the mesh that holds the triangle
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int vertex1, int vertex2, int vertex3, IndexedMesh<T> mesh);
	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param vertex1 the first vertex index of the face
	 * @param vertex2 the second vertex index of the face
	 * @param vertex3 the third vertex index of the face
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int vertex1, int vertex2, int vertex3);

	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param vertices the vertices of the face (a 3 element array)
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int[] vertices);

	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param vertices  the vertices of the face (a 3 element array)
	 * @param mesh the mesh that holds the triangle
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int[] vertices, IndexedMesh<T> mesh);
	
	/**
	 * Create a new Create a new {@link MeshNeighborhood mesh neighborhood}.
	 * @param <T> The type of underlying 3D points
	 * @return a new mesh neighborhood
	 */
	public <T extends Point3D> MeshNeighborhood<T> createMeshNeighborhood();
}
