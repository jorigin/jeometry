package org.jeometry.simple.geom3D.mesh.indexed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.Geom3D;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.mesh.indexed.IndexedTriangleMesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.indexed.IndexedTriangle;
import org.jeometry.simple.geom3D.primitive.indexed.SimpleIndexedTriangle;

/**
 * A simple implementation of an {@link IndexedTriangleMesh indexed triangle mesh}.
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class SimpleIndexedTriangleMesh<T extends Point3D> implements IndexedTriangleMesh<T> {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = Jeometry.BUILD;

	/**
	 * The vertices source.
	 */
	private Point3DContainer<T> verticesSource = null;
	
	/**
	 * The edges.
	 */
	List<IndexedEdge<T>> edges = null;

	/**
	 * The faces.
	 */
	List<IndexedTriangle<T>> faces = null;

	/**
	 * Is the indices have to be validated.
	 */
	private boolean validatedIndexes = false;
	
	/**
	 * The x coordinate of the mesh.
	 */
	private double x    = Double.NaN;
	
	/**
	 * The y coordinate of the mesh.
	 */
	private double y    = Double.NaN;
	
	/**
	 * The z coordinate of the mesh.
	 */
	private double z    = Double.NaN;
	
	/**
	 * The x min coordinate of the mesh.
	 */
	private double xmin = Double.NaN;
	
	/**
	 * The y min coordinate of the mesh.
	 */
	private double ymin = Double.NaN;
	
	/**
	 * The z min coordinate of the mesh.
	 */
	private double zmin = Double.NaN;
	
	/**
	 * The xmax coordinate of the mesh.
	 */
	private double xmax = Double.NaN;
	
	/**
	 * The y max coordinate of the mesh.
	 */
	private double ymax = Double.NaN;
	
	/**
	 * The z max coordinate of the mesh.
	 */
	private double zmax = Double.NaN;
	
	@Override
	public List<? extends IndexedEdge<T>> getEdgesIndexes() {
		return this.edges;
	}

	@Override
	public List<Integer> getVerticesIndexes() {
		if ((this.faces != null) && (this.faces.size() > 0)){
			List<Integer> indices = new ArrayList<Integer>();

			for(IndexedFace<?> face : this.faces) {
				if ((face.getVerticesIndexes() != null) && (face.getVerticesIndexes().length > 0)){
					for(int i = 0; i < face.getVerticesIndexes().length; i++) {
						if (! indices.contains(face.getVerticesIndexes()[i])) {
							indices.add(face.getVerticesIndexes()[i]);
						}
					}

					Collections.sort(indices);

					return indices;
				}
			}
		}
		
		return null;
	}

	@Override
	public Point3DContainer<T> getVerticesSource() {
		return this.verticesSource;
	}

	@Override
	public void setVerticesSource(Point3DContainer<T> verticesSource) {
		this.verticesSource = verticesSource;
		this.validatedIndexes = false;
	}

	/**
	  * Add a face made of the given <code>indices</code> to the indexed mesh. 
	  * The given face has to be a triangle (with exactly 3 vertices).
	  * @param indices the indices composing the {@link IndexedFace indexed face}.
	  * @return <code>true</code> if the face is successfully added to the mesh and <code>false</code> otherwise.
	  * @throws IllegalArgumentException if the given face is not a triangle.
	  */
	@Override
	public boolean addFace(List<Integer> indices) {
        if (indices != null) {
          if (indices.size() == 3) {
        	  
        	  try {
				IndexedTriangle<T> triangle = new SimpleIndexedTriangle<T>(indices.stream().mapToInt(i->i).toArray(), this);
				  
				  boolean result = this.faces.add(triangle);
				  
				  if (result) {
					  triangle.setMesh(this);
				  }
			} catch (Exception e) {
				throw new IllegalArgumentException("Cannot create triangle face from given indices ["+indices.get(0)+", "+indices.get(1)+", "+indices.get(2)+"].");
			}
        	  
          } else {
        	  throw new IllegalArgumentException("Only triangle faces are permitted ("+indices.size()+" vertices face given).");
          }
        } else {
      	  throw new IllegalArgumentException("Cannot create triangle from null indices.");
        }
        
		return false;
	}

	/**
	  * Add a face made of the given <code>indices</code> to the indexed mesh. 
	  * The given face has to be a triangle (with exactly 3 vertices).
	  * @param indices the indices composing the {@link IndexedFace indexed face}.
	  * @return <code>true</code> if the face is successfully added to the mesh and <code>false</code> otherwise.
	  * @throws IllegalArgumentException if the given face is not a triangle.
	  */
	@Override
	public boolean addFace(int[] indices) {
		if (indices != null) {
			if (indices.length == 3) {
				IndexedTriangle<T> triangle = new SimpleIndexedTriangle<T>(indices, this);
				boolean result =  this.faces.add(triangle);
				
				if (result) {
					triangle.setMesh(this);
				}
				
				return result;
				
	          } else {
	        	  throw new IllegalArgumentException("Only triangular faces are permitted ("+indices.length+" vertices face given).");
	          }
		}
		return false;
	}

	@Override
	public boolean isValidatedIndexes() {
		return this.validatedIndexes;
	}

	@Override
	public boolean validateIndexes() {
		this.validatedIndexes = true;

		if (this.verticesSource != null){

			List<? extends IndexedFace<T>> faces =  getFacesIndexes();

			// validate face
			if (faces != null){
				for(int i = 0; i < faces.size(); i++){
					faces.get(i).setVerticesSource(getVerticesSource());
					this.validatedIndexes &= faces.get(i).validateIndexes();
				}
			}

			List<? extends IndexedEdge<T>> edges =  getEdgesIndexes();

			// validate edges
			if (edges != null){
				for(int i = 0; i < edges.size(); i++){
					edges.get(i).setVerticesSource(getVerticesSource());
					this.validatedIndexes &= edges.get(i).validateIndexes();
				}
			}

			return this.validatedIndexes;

		} else {
			return this.validatedIndexes;
		}
	}

	@Override
	public List<? extends Face<T>> getFaces() {
		return this.faces;
	}

	@Override
	public List<? extends Edge<T>> getEdges() {
		return this.edges;
	}

	@Override
	public Point3DContainer<T> getVertices() {
		if ((this.faces != null) && (this.faces.size() > 0)){
			Point3DContainer<T> vertices = JeometryFactory.createPoint3DContainer(this.faces.size()*6);

			for(IndexedFace<T> face : this.faces) {
				if (face.isValidatedIndexes()) {
					if ((face.getVerticesIndexes() != null) && (face.getVerticesIndexes().length > 0)){
						T vertex = null;
						for(int i = 0; i < face.getVerticesIndexes().length; i++) {
							vertex = face.getVerticesSource().get(face.getVerticesIndexes()[i]);
							if (! vertices.contains(vertex)) {
								vertices.add(vertex);
							}
						}

						return vertices;
					}
				} else {
					throw new IllegalStateException("Face "+face.toString()+" is not valid.");
				}

			}
		}

		return null;
	}

	@Override
	public boolean addFace(Face<T> face) {
		if (face != null) {
			
			if (face instanceof IndexedTriangle) {
				
				boolean result =  this.faces.add((IndexedTriangle<T>)face);

				if (result) {
					((IndexedTriangle<T>) face).setMesh(this);
					((IndexedTriangle<T>) face).setVerticesSource(this.verticesSource);
					((IndexedTriangle<T>) face).validateIndexes();
				}
				return result;
				
			} else if (face instanceof IndexedFace){
				
				if (((IndexedFace<T>) face).getVerticesIndexes() != null){
					try {
						IndexedTriangle<T> triangle = new SimpleIndexedTriangle<T>(((IndexedFace<T>) face).getVerticesIndexes(), this);
						boolean result =  this.faces.add(triangle);

						if (result) {
							((IndexedFace<T>) face).setMesh(this);
						}
						return result;
					} catch (Exception e) {
						throw new IllegalArgumentException("Cannot create indexed triangle face from given indexed face: "+e.getMessage());
					}
				} else {
					return false;
				}

			} else {

				Point3DContainer<? extends Point3D> faceVertices = face.getVertices();
				int[] faceIndices = null;

				if ((this.verticesSource != null) && (faceVertices != null)){
					
					if (faceVertices.size() ==3) {
						faceIndices = new int[faceVertices.size()];
						int i = 0;
						boolean result = true;
						for(Point3D vertex : faceVertices) {
							faceIndices[i] = faceVertices.indexOf(vertex);
							result = result && faceIndices[i] != -1;
							i = i + 1;
						}

						if (result) {
							try {
								IndexedTriangle<T> triangle = new SimpleIndexedTriangle<T>(faceIndices, this);
								triangle.setMesh(this);
							} catch (Exception e) {
								throw new IllegalArgumentException("Cannot create indexed triangle from given face.");
							}
						}

						return result;
					} else {
						throw new IllegalArgumentException("Cannot create indexed triangle face from non triangular face.");
					}

				} else {
					throw new IllegalArgumentException("Cannot create indexed triangle face from face with no vertex.");
				}
			}
		}

		return false;
	}

	@Override
	public boolean removeFace(Face<?> face) {
		boolean result = this.faces.remove(face);
		
		if (result) {
			face.setMesh(null);
		}
		
		return result;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public double getZ() {
		return this.z;
	}

	@Override
	public double getXMin() {
		return this.xmin;
	}

	@Override
	public double getYMin() {
		return this.ymin;
	}

	@Override
	public double getZMin() {
		return this.zmin;
	}

	@Override
	public double getXMax() {
		return this.xmax;
	}

	@Override
	public double getYMax() {
		return this.ymax;
	}

	@Override
	public double getZMax() {
		return this.zmax;
	}

    /**
     * Compute the distance between this spatial localization and the given one.
     * The point that represents this mesh localization is set to its barycentre.
     * @param spatial the spatial localization.
     * @return the distance between this spatial localization and the given one or <code>Double.Nan</code> if the input spatial is <code>null</code> or if this location is not known.
     */
	@Override
	public double distance(SpatialLocalization3D spatial) {
		if (spatial != null) {
			return Geom3D.computeDistance(getX(), getY(), getZ(), spatial.getX(), spatial.getY(), spatial.getZ());
		}
		return Double.NaN;
	}

	@Override
	public void updateLocalization() {
		if ((this.faces != null) && (this.faces.size() > 0)){

			this.x    = 0.0d;
			this.y    = 0.0d;
			this.z    = 0.0d;
			
			this.xmin = Double.MAX_VALUE;
			this.ymin = Double.MAX_VALUE;
			this.zmin = Double.MAX_VALUE;
			
			this.xmax = -Double.MAX_VALUE;
			this.ymax = -Double.MAX_VALUE;
		    this.zmax = -Double.MAX_VALUE;
			
			for(IndexedFace<?> face : this.faces) {
				if (face.isValidatedIndexes()) {
					if ((face.getVerticesIndexes() != null) && (face.getVerticesIndexes().length > 0)){
						Point3D vertex = null;
						int verticesCount = 0;
						for(int i = 0; i < face.getVerticesIndexes().length; i++) {
							vertex = face.getVerticesSource().get(face.getVerticesIndexes()[i]);
							
							verticesCount = verticesCount + 1;
							
							this.x    = this.x + vertex.getX();
							this.y    = this.y + vertex.getY();
							this.z    = this.z + vertex.getZ();
							
							if (this.x < this.xmin) {
								this.xmin = this.x;
							}
							
							if (this.x > this.xmax) {
								this.xmax = this.x;
							}
							
							if (this.y < this.ymin) {
								this.ymin = this.y;
							}
							
							if (this.y > this.ymax) {
								this.ymax = this.y;
							}
							
							if (this.z < this.zmin) {
								this.zmin = this.z;
							}
							
							if (this.z > this.zmax) {
								this.zmax = this.z;
							}
						}
						
						this.x = this.x / verticesCount;
						this.y = this.y / verticesCount;
						this.z = this.z / verticesCount;
					}
				} else {
					throw new IllegalStateException("Face "+face.toString()+" is not valid.");
				}

			}
		} else {
			this.x    = Double.NaN;
			this.y    = Double.NaN;
			this.z    = Double.NaN;
			
			this.xmin = Double.NaN;
			this.ymin = Double.NaN;
			this.zmin = Double.NaN;
			
			this.xmax = Double.NaN;
			this.ymax = Double.NaN;
		    this.zmax = Double.NaN;
		}
	}

	@Override
	public List<? extends IndexedTriangle<T>> getFacesIndexes() {
		return this.faces;
	}

	@Override
	public boolean addTriangle(List<Integer> indices) {
		return addFace(indices);
	}

	@Override
	public boolean addTriangle(int[] indices) {
		return addFace(indices);
	}

	/**
	 * Create a new empty {@link IndexedTriangleMesh indexed triangle mesh}.
	 */
	public SimpleIndexedTriangleMesh() {
		this.faces = new ArrayList<IndexedTriangle<T>>();
		this.edges = new ArrayList<IndexedEdge<T>>();
	}
	
	/**
	 * Create a new {@link IndexedTriangleMesh indexed triangle mesh} with the given initial faces storage capacity.
	 * @param capacity the initial capacity of faces storage
	 */
	public SimpleIndexedTriangleMesh(int capacity) {
		this.faces = new ArrayList<IndexedTriangle<T>>(capacity);
		this.edges = new ArrayList<IndexedEdge<T>>(capacity*3);
	}
	
	/**
	 * Create a new {@link IndexedTriangleMesh indexed triangle mesh} that relies on the given vertex source.
	 * @param source the vertex source
	 */
	public SimpleIndexedTriangleMesh(Point3DContainer<? extends Point3D> source) {
		this();
		setVerticesSource(this.verticesSource);
	}
	
	/**
	 * Create a new {@link IndexedTriangleMesh indexed triangle mesh} that relies on the given vertex source with the given initial capacity.
	 * @param capacity the initial capacity of the container
	 * @param source the vertex source
	 */
	public SimpleIndexedTriangleMesh(int capacity, Point3DContainer<? extends Point3D> source) {
		this(capacity);
		setVerticesSource(this.verticesSource);
	}
}
