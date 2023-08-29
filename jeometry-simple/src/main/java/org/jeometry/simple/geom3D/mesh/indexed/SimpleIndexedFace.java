package org.jeometry.simple.geom3D.mesh.indexed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.indexed.IndexedPolygon3D;
import org.jeometry.simple.geom3D.primitive.indexed.SimpleIndexedPolygon3D;


/**
 * A mesh face within indexed geometry context.
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleIndexedFace<T extends Point3D> extends SimpleIndexedPolygon3D<T> implements IndexedFace<T>{

    /**
     * The serial version UID.
     */
	private static final long serialVersionUID = Jeometry.BUILD;

	/**
	 * The edges.
	 */
	private List<IndexedEdge<T>> edgesIndexed = null;

	/**
	 * The mesh.
	 */
	private IndexedMesh<T> meshIndexed        = null;


	@Override
	public Point3DContainer<T> getVerticesSource() {
		if (this.meshIndexed != null) {
			return this.meshIndexed.getVerticesSource();
		} else {
			return null;
		}
	}

	@Override
	public void setVerticesSource(Point3DContainer<T> verticesSource) { 
		throw new IllegalArgumentException("Cannot change indexed face vertice source, prefer changing the underlying mesh.");
	}

	@Override
	public List<Edge<T>> getEdges() {
		// TODO implements SimpleIndexedFace.getEdges()
		return null;
	}
	
	@Override
	public List<IndexedEdge<T>> getEdgesIndexed() {
		return this.edgesIndexed;
	}

	@Override
	public boolean equals(IndexedPolygon3D<?> polygon) {
		System.out.println(getClass().getSimpleName()+".equals() only take in account references equality.");
		Jeometry.logger.log(Level.WARNING, " only take in account references equality.");

		if (this == polygon) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public IndexedMesh<T> getMesh(){
		return this.meshIndexed;
	}

	@Override
	public void setMesh(IndexedMesh<T> mesh) {
		this.meshIndexed = mesh;

		if (getVerticesSource() != null){
			if (this.edgesIndexed != null){
				Iterator<IndexedEdge<T>> edgeIter = this.edgesIndexed.iterator();
				while(edgeIter.hasNext()){
					edgeIter.next().setVerticesSource(getVerticesSource());
				}
			}
		}
	}

	@Override
	public void setMesh(Mesh<T> mesh) {
		if (mesh instanceof IndexedMesh){
			setMesh((IndexedMesh<T>)mesh);
		} else {
			throw new IllegalArgumentException("Indexed face has to be attached to an indexed mesh.");
		}
	}

	/**
	 * Create a new empty indexed mesh face.
	 * As long as a mesh and vertex indices are not attached to this face, it is considered as invalid.
	 */
	public SimpleIndexedFace() {
	}

	/**
	 * Create a new face made of the given indices. 
	 * As long as a mesh is not attached to this face, it is considered as invalid.
	 * @param indices the indices of the vertices of the face.
	 */
	public SimpleIndexedFace(int[] indices) {
		this(indices, null);
	}

	/**
	 * Create a new indexed mesh face.
	 * @param indices the indices that compose the face (cannot be <code>null</code>).
	 */
	public SimpleIndexedFace(List<Integer> indices){
		this(indices.stream().mapToInt(i->i).toArray(), null);
	}


	/**
	 * Create a new indexed mesh face.
	 * @param indices the indices that compose the face (cannot be <code>null</code>).
	 * @param mesh the mesh that hold this face.
	 */
	public SimpleIndexedFace(List<Integer> indices, IndexedMesh<T> mesh){
		
		super();
		
		if (indices != null) {
			setVerticesIndexes(indices.stream().mapToInt(i->i).toArray());
			this.meshIndexed = mesh;

			if (indices.size() > 0){

				this.edgesIndexed    = new ArrayList<IndexedEdge<T>>(indices.size());
				for(int i = 0; i < indices.size(); i++){
					this.edgesIndexed.add(new SimpleIndexedEdge<T>(indices.get(i), indices.get((i+1)%indices.size()), mesh));
				}     
			}

			if (mesh != null) {
				validateIndexes();
			}
		}
	}

	/**
	 * Create a new indexed mesh face.
	 * @param indices the indices that compose the face.
	 * @param mesh the mesh that hold this face.
	 */
	public SimpleIndexedFace(int[] indices, IndexedMesh<T> mesh){
		super();

		if (indices != null) {
			setVerticesIndexes(indices);
			this.meshIndexed = mesh;

			if (indices.length > 0) {

				this.edgesIndexed    = new ArrayList<IndexedEdge<T>>(indices.length);
				for(int i = 0; i < indices.length; i++){
					this.edgesIndexed.add(new SimpleIndexedEdge<T>(indices[i], indices[(i+1)%indices.length], mesh));
				}     
			}

			if (mesh != null) {
				validateIndexes();
			}
		}
	}
}
