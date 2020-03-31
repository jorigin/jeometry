package org.jeometry.geom3D.mesh.indexed;

import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.primitive.indexed.IndexedTriangle;

/**
 * An {@link IndexedMesh indexed mesh} that contains only {@link IndexedTriangle triangular faces}.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 *
 */
public interface IndexedTriangleMesh<T extends Point3D> extends IndexedMesh<T> {

	  /**
	   * Return the indexed {@link IndexedTriangle indexed triangles} that compose the mesh.
	   * @return the indexed {@link IndexedTriangle indexed triangles} that compose the mesh. 
	   */
	  @Override
	  public List<? extends IndexedTriangle<T>> getFacesIndexes();
	
	  /**
	   * Add a {@link IndexedTriangle triangular face} made of the given <code>indices</code> to the indexed triangle mesh. 
	   * This method enable to specific mesh to provide use specific implementation of 
	   * {@link IndexedFace indexed face}.
	   * @param indices the indices composing the {@link IndexedFace indexed face}.
	   * @return <code>true</code> if the face is successfully added to the mesh and <code>false</code> otherwise.
	   */
	  public boolean addTriangle(List<Integer> indices);
	  
	  /**
	   * Add a {@link IndexedTriangle triangular face} made of the given <code>indices</code> to the indexed triangle mesh. 
	   * This method enable to specific mesh to provide use specific implementation of 
	   * {@link IndexedFace indexed face}.
	   * @param indices the indices composing the {@link IndexedFace indexed face}.
	   * @return <code>true</code> if the face is successfully added to the mesh and <code>false</code> otherwise.
	   */
	  public boolean addTriangle(int[] indices);
	
}
