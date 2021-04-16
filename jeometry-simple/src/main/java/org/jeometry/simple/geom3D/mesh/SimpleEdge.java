package org.jeometry.simple.geom3D.mesh;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.Geom3D;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Line3D;

/**
 * An implementation of a {@link Edge edge}. 
 * An {@link Edge edge} is a {@link Line3D 3D line} that link two vertices and that delimits a polygon or that delimits faces of a polyhedron.
 * @param <T> the type of the underlying 3D point
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class SimpleEdge<T extends Point3D> implements Edge<T>{

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = Jeometry.BUILD;

	/** The attached mesh **/
	private Mesh<T> mesh = null;

	/** The origin of the edge */
	private T end1    = null;

	/** The extremity of the edge */
	private T end2 = null;

	/** The first face attached to this edge */
	private final Face<T> face1 = null;

	/** The second face attached to this edge */
	private final Face<T> face2 = null;

	/**
	 * Construct a new edge that is not related to vertices.
	 */
	public SimpleEdge(){
	}

	/**
	 * Construct a new edge between two vertices.
	 * @param end1 the first vertex of the edge.
	 * @param end2 the second vertex of the edge.
	 */
	public SimpleEdge(T end1, T end2){
		this.end1 = end1;
		this.end2 = end2;
	}

	/**
	 * Construct a new edge between two vertices.
	 * @param mesh the mesh that hold this edge.
	 * @param end1 the first vertex of the edge.
	 * @param end2 the second vertex of the edge.
	 */
	public SimpleEdge(Mesh<T> mesh, T end1, T end2){
		
		this.mesh = mesh;
		
		this.end1 = end1;
		this.end2 = end2;
	}

	/**
	 * Construct a new edge between two faces.
	 * @param face1 the first face.
	 * @param face2 the second face.
	 */
	public SimpleEdge(Face<T> face1, Face<T> face2){

		Point3DContainer<T> p3dm = null;

		if ((face1 == null)||(face2 == null)){
			return;
		} else{
			p3dm = JeometryFactory.createPoint3DContainer();
			for(int i = 0; i < face1.getVertices().size(); i++){
				for(int j = 0; i < face2.getVertices().size(); j++){
					if (Geom3D.equals(face1.getVertices().get(i), face2.getVertices().get(j))){
						p3dm.add(face1.getVertices().get(i));
					}
				}
			}

			if (p3dm.size() == 2){
				this.end1 = p3dm.get(0);
				this.end1 = p3dm.get(1);
			}
		}
	}

	/**
	 * Get the vertices of the edge. An edge has two 3D points as vertices.
	 * @return the vertices of the edge.
	 */
	public Point3DContainer<T> getVertices(){
		Point3DContainer<T> p3dm = JeometryFactory.<T>createPoint3DContainer(2);
		p3dm.add(end1);
		p3dm.add(end2);
		return p3dm;
	}

	/**
	 * Get the faces attached to this edge.
	 * @return the two faces attached to this edge.
	 */
	@Override
	public List<Face<T>> getFaces(){
		List<Face<T>> faces = new ArrayList<Face<T>>(2);
		faces.add(face1);
		faces.add(face2);
		return faces;
	}

	/**
	 * Return the first extremity of this edge.
	 * @return the first extremity of this edge.
	 * @see #setEnd1(Point3D)
	 * @see #getEnd2()
	 */
	@Override
	public T getEnd1(){
		return this.end1;
	}

	/**
	 * Set the first extremity of this edge.
	 * @param end1 the first extremity of this edge.
	 * @see #getEnd1()
	 * @see #setEnd2(Point3D)
	 */
	public void setEnd1(T end1){
		this.end1 = end1;
	}

	/**
	 * Return the second extremity of this edge.
	 * @return the second extremity of this edge.
	 * @see #setEnd2(Point3D)
	 * @see #getEnd1()
	 */
	@Override
	public T getEnd2(){
		return this.end2;
	}

	/**
	 * Set the second extremity of this edge.
	 * @param end2 the second extremity of this edge.
	 * @see #getEnd2()
	 * @see #setEnd1(Point3D)
	 */
	public void setEnd2(T end2){
		this.end2 = end2;
	}

	/**
	 * Overload of the equals function of object. This method return true if the reference of the
	 * edge is the same that the object given in parameter. Otherwise, the method equals(Edge e) is called
	 * if the object is a edge.
	 * @param o the object to compare
	 * @return <code>true</code> if the edge and the object are equals and <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o){
		if (o == this){
			return true;
		} else{
			if (o instanceof Edge){
				
				    @SuppressWarnings("rawtypes")
					Edge e = (Edge)o;
				
				    // Si deux objets ont même référence, ils sont identiques
					return (   (Geom3D.equals(this.getEnd1(), e.getVertices().get(0)) && Geom3D.equals(this.getEnd2(), e.getVertices().get(1))) 
							|| (Geom3D.equals(this.getEnd2(), e.getVertices().get(0)) && Geom3D.equals(this.getEnd1(), e.getVertices().get(1))));
			} else{
				return false;
			}
		}
	}

	/**
	 * Return the two extremity of the edge
	 * @return IPoint3D[]
	 */
	public Point3D[] getVerticesArray(){
		Point3D p3da[] = new Point3D[2];
		p3da[0] = end1;
		p3da[1] = end2;
		return p3da;
	}


	@Override
	public Mesh<T> getMesh() {
		return mesh;
	}

	@Override
	public void setMesh(Mesh<T> mesh) {
		this.mesh = mesh;
	}


}
