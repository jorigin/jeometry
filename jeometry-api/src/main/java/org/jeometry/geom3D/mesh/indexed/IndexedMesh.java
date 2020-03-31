package org.jeometry.geom3D.mesh.indexed;

import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;


/**
 * An indexed mesh is a {@link Mesh mesh} that use indices for storing faces information. 
 * Such a mesh relies on a {@link #getVerticesSource() vertices source} that contains the vertices coordinates. 
 * An indexed mesh face references its vertices with integer indices that describe the index of the 
 * vertices within the {@link #getVerticesSource() vertices source}.<br>
 * <br>
 * Please note that it is not guaranteed that all the points present within the vertices source are vertex of the mesh. 
 * To get only the points that are vertices of the mesh, you can use {@link #getVertices() getVertices()} method.  
 * @param <T> the type of the underlying 3D points.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public interface IndexedMesh<T extends Point3D> extends Mesh<T> {
  /**
   * Return the {@link IndexedFace indexed faces} 
   * of the mesh. Faces are planar 3D polygons
   * @return the {@link IndexedFace indexed faces} of the mesh
   */
  public List<? extends IndexedFace<T>> getFacesIndexes();

  /**
   * Return the {@link IndexedEdge indexed edges} of the mesh.
   * @return the {@link IndexedEdge indexed edges} of the mesh.
   */
  public List<? extends IndexedEdge<T>> getEdgesIndexes();
  
  /**
   * Return the {@link Point3DContainer vertices} of the mesh.
   * @return the {@link Point3DContainer vertices} of the mesh.
   * @throws IllegalStateException if the mesh contains {@link IndexedFace#isValidatedIndexes() invalid} faces.
   */
  @Override
  public Point3DContainer<T> getVertices();
  
  /**
   * Return the vertex indices of the mesh.
   * @return The indexes of the 3D point representing the vertices.
   */
  public List<Integer> getVerticesIndexes();
  
  /**
   * Get the vertices list from which the indexes are coming. This method has not to be confused with {@link #getVertices()}
   * method that return only the vertices attached to this mesh. 
   * @return the vertices list from which the indexes are coming.
   */
  public Point3DContainer<T> getVerticesSource();

  /**
   * Set the vertices list from which the indexes are coming. This method has not to be confused with {@link #getVertices()}
   * method that return only the vertices attached to this mesh.
   * @param verticesSource the vertices list from which the indexes are coming.
   */
  public void setVerticesSource(Point3DContainer<T> verticesSource);
  
  /**
   * Add a face made of the given <code>indices</code> to the indexed mesh. 
   * This method enable to specific mesh to provide use specific implementation of 
   * {@link IndexedFace indexed face}.
   * @param indices the indices composing the {@link IndexedFace indexed face}.
   * @return <code>true</code> if the face is successfully added to the mesh and <code>false</code> otherwise.
   */
  public boolean addFace(List<Integer> indices);
  
  /**
   * Add a face made of the given <code>indices</code> to the indexed mesh. 
   * This method enable to specific mesh to provide use specific implementation of 
   * {@link IndexedFace indexed face}.
   * @param indices the indices composing the {@link IndexedFace indexed face}.
   * @return <code>true</code> if the face is successfully added to the mesh and <code>false</code> otherwise.
   */
  public boolean addFace(int[] indices);
  
  /**
   * Return if the indexes of the geometry have been validated. The validation is made by calling method {@link #validateIndexes()}.
   * @return <code>true</code> if the geometry has validated indexes and <code>false</code> otherwise.
   */
  public boolean isValidatedIndexes();
  
  /**
   * Validate the geometry. This method valid the link between the indexed geometry and the geometry itself.
   * @return <code>true</code> if the validation is effective, <code>false</code> otherwise.
   */
  public boolean validateIndexes();
}
