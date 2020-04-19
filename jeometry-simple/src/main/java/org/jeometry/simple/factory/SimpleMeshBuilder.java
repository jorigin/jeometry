package org.jeometry.simple.factory;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.MeshBuilder;
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
import org.jeometry.simple.geom3D.mesh.SimpleEdge;
import org.jeometry.simple.geom3D.mesh.SimpleFace;
import org.jeometry.simple.geom3D.mesh.SimpleMesh;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedEdge;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedFace;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedMesh;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedTriangleMesh;
import org.jeometry.simple.geom3D.mesh.indexed.textured.SimpleIndexedTexturedFace;
import org.jeometry.simple.geom3D.mesh.indexed.textured.SimpleIndexedTexturedTriangle;
import org.jeometry.simple.geom3D.neighbor.SimpleMeshNeighborhood;
import org.jeometry.simple.geom3D.primitive.SimpleTriangle;
import org.jeometry.simple.geom3D.primitive.indexed.SimpleIndexedTriangle;

/**
 * A {@link MeshBuilder mesh builder} implementation that provide simples pure java implementations.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleMeshBuilder implements MeshBuilder {

	@Override
	public <T extends Point3D> Mesh<T> createMesh() {
		return new SimpleMesh<T>();
	}

	@Override
	public <T extends Point3D> Edge<T> createMeshEdge(Mesh<T> mesh, T vertex1, T vertex2) {
		return new SimpleEdge<T>(mesh, vertex1, vertex2);
	}

	@Override
	public <T extends Point3D> Face<T> createMeshFace(Mesh<T> mesh, Point3DContainer<T> vertices) {
		return new SimpleFace<T>(mesh, vertices);
	}

	@Override
	public <T extends Point3D> Face<T> createMeshFace(Point3DContainer<T> vertices) {
		return new SimpleFace<T>(vertices);
	}

	@Override
	public <T extends Point3D> Triangle<T> createMeshTriangle(T vertex1, T vertex2, T vertex3) {
		return new SimpleTriangle<T>(vertex1, vertex2, vertex3);
	}

	@Override
	public <T extends Point3D> IndexedMesh<T> createIndexedMesh() {
		return new SimpleIndexedMesh<T>();
	}

	@Override
	public <T extends Point3D> IndexedMesh<T> createIndexedMesh(int capacity) {
		return new SimpleIndexedMesh<T>(capacity);
	}

	@Override
	public <T extends Point3D> IndexedEdge<T> createIndexedMeshEdge(int vertex1Index, int vertex2Index) {
		return new SimpleIndexedEdge<T>(vertex1Index, vertex2Index);
	}

	@Override
	public <T extends Point3D> IndexedEdge<T> createIndexedMeshEdge(int vertex1Index, int vertex2Index,
			IndexedMesh<T> mesh) {
		return new SimpleIndexedEdge<T>(vertex1Index, vertex2Index, mesh);
	}

	@Override
	public <T extends Point3D> IndexedFace<T> createIndexedMeshFace(int[] indices) {
		return new SimpleIndexedFace<T>(indices);
	}

	@Override
	public <T extends Point3D> IndexedFace<T> createIndexedMeshFace(int[] indices, IndexedMesh<T> mesh) {
		return new SimpleIndexedFace<T>(indices, mesh);
	}

	@Override
	public <T extends Point3D> IndexedFace<T> createIndexedMeshFace(List<Integer> indices) {
		return new SimpleIndexedFace<T>(indices);
	}

	@Override
	public <T extends Point3D> IndexedFace<T> createIndexedMeshFace(List<Integer> indices, IndexedMesh<T> mesh) {
		return new SimpleIndexedFace<T>(indices, mesh);
	}

	@Override
	public <T extends Point3D> IndexedTriangle<T> createIndexedTriangle(int vertex1, int vertex2, int vertex3,
			IndexedMesh<T> mesh) {
		return new SimpleIndexedTriangle<T>(vertex1, vertex2, vertex3, mesh);
	}

	@Override
	public <T extends Point3D> IndexedTriangle<T> createIndexedTriangle(int[] vertices, IndexedMesh<T> mesh) {
		return new SimpleIndexedTriangle<T>(vertices, mesh);
	}

	@Override
	public <T extends Point3D> IndexedTriangleMesh<T> createIndexedTriangleMesh(Point3DContainer<T> source) {
		return new SimpleIndexedTriangleMesh<T>(source);
	}

	@Override
	public <T extends Point3D> IndexedTriangleMesh<T> createIndexedTriangleMesh(int size, Point3DContainer<T> source) {
		return new SimpleIndexedTriangleMesh<T>(size, source);
	}

	@Override
	public <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(int[] indices) {
		return new SimpleIndexedTexturedFace<T>(indices);
	}

	@Override
	public <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(int[] indices, IndexedMesh<T> mesh) {
		return new SimpleIndexedTexturedFace<T>(indices, mesh);
	}

	@Override
	public <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(List<Integer> indices) {
		return new SimpleIndexedTexturedFace<T>(indices);
	}

	@Override
	public <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(List<Integer> indices,
			IndexedMesh<T> mesh) {
		return new SimpleIndexedTexturedFace<T>(indices, mesh);
	}

	@Override
	public <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int vertex1, int vertex2, int vertex3,
			IndexedMesh<T> mesh) {
		return new SimpleIndexedTexturedTriangle<T>();
	}

	@Override
	public <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int vertex1, int vertex2, int vertex3) {
		return new SimpleIndexedTexturedTriangle<T>(vertex1, vertex2, vertex3);
	}

	@Override
	public <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int[] vertices) {
		return new SimpleIndexedTexturedTriangle<T>(vertices);
	}

	@Override
	public <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int[] vertices, IndexedMesh<T> mesh) {
		return new SimpleIndexedTexturedTriangle<T>(vertices, mesh);
	}

	@Override
	public <T extends Point3D> MeshNeighborhood<T> createMeshNeighborhood() {
		return new SimpleMeshNeighborhood<T>();
	}

}
