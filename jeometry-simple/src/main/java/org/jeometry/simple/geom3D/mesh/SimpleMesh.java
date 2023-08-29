package org.jeometry.simple.geom3D.mesh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;

/**
 * A default implementation of the {@link Mesh Mesh} interface. See the interface documentation for details.
 * @param <T> the type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class SimpleMesh<T extends Point3D> implements Mesh<T> {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = Jeometry.BUILD;


	/** Faces of the mesh. A mesh face is a planar polygon */
	private ArrayList<Face<T>> faces = null;

	/** The mesh vertices **/
	private Point3DContainer<T> vertices = null;

	/**
	 * Construct an empty mesh.
	 */
	public SimpleMesh(){
		//  vertices = new Point3DManager();
		this.faces = new ArrayList<Face<T>>();
	}

	/**
	 * Construct a mesh composed by the face given in parameter
	 * @param faces Vector the faces of the mesh
	 */
	public SimpleMesh(Collection<Face<T>> faces) {

		if ((faces != null) && (faces.size() != 0)){
			Iterator<Face<T>> iter = faces.iterator();
			while(iter.hasNext()){
				addFace(iter.next());
			}
		}
	}

	@Override
	public List<? extends Edge<T>> getEdges() {

		// Utilisation d'un hashset car l'unicité des arrêtes est requise, par contre
		// L'ordre de stockage des arrêtes n'est pas déterminant.
		ArrayList<Edge<T>> edges = new ArrayList<Edge<T>>();
		Face<T> face = null;

		List<? extends Edge<T>> faceEdge = null;

		// Parcours des faces et récupération des arrêtes.
		for(int i = 0; i < this.faces.size(); i++){
			face = this.faces.get(i);

			// Les arrêtes de la face courante sont ajoutées à l'ensemble des arêtes.
			// Comme edges est un HashSet, il n'insère une arrête e1 que s'il n'existe pas
			// d'arrêtes e2 telle que e1.equals(e2) soit vraie.

			faceEdge = face.getEdges();

			if(faceEdge != null){
				for(int j = 0; j < faceEdge.size(); j++){
					if (!edges.contains(faceEdge.get(j))){
						edges.add(faceEdge.get(j));
					}
				}
			}

		}

		faceEdge = null;


		return edges;
	}

	@Override
	public List<Face<T>> getFaces() {
		return this.faces;
	}

	@Override
	public Point3DContainer<T> getVertices() {
		return this.vertices;
	}

	@Override
	public boolean addFace(Face<T> face){

		if (face == null){
			return false;
		}

		if (this.faces == null){
			this.faces = new ArrayList<Face<T>>();
		}


		// Le polyhedre devient le propriétaire de la face
		face.setMesh(this);

		boolean ok = this.faces.add(face);

		if (ok){

			Point3DContainer<T> v = face.getVertices();

			if (v != null){
				for(T p : v){
					this.vertices.add(p);
				}
			}
		}

		return ok;
	}

	@Override
	public boolean removeFace(Face<?> face){
		return this.faces.remove(face);
	}

	@Override
	public double getX() {
		return getVertices().getX();
	}

	@Override
	public double getY(){
		return getVertices().getY();
	}

	@Override
	public double getZ(){
		return getVertices().getZ();
	}

	@Override
	public double getXMin() {
		return getVertices().getXMin();
	}

	@Override
	public double getYMin() {
		return getVertices().getYMin();
	}

	@Override
	public double getZMin() {
		return getVertices().getZMin();
	}

	@Override
	public double getXMax() {
		return getVertices().getXMax();
	}

	@Override
	public double getYMax() {
		return getVertices().getYMax();
	}

	@Override
	public double getZMax() {
		return getVertices().getZMax();
	}

	@Override
	public double distance(SpatialLocalization3D item) {
		System.err.println("["+getClass().getSimpleName()+"][distanceTo(SpatialItem)] not yet implemented");
		return 0;
	}

	@Override
	public void updateLocalization(){
		System.err.println("["+getClass().getSimpleName()+"][distanceTo(SpatialItem)] not yet implemented");
	}
}
