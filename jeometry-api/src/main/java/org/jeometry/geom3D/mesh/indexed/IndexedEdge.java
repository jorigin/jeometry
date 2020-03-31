package org.jeometry.geom3D.mesh.indexed;

import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Polygon3D;

/**
 * An interface that describe an edge within indexed geometry representation. An Edge is a segment that delimits a {@link Polygon3D polygon} 
 * or that delimits {@link Face faces} of a mesh. Within an indexed geometry representation, an indexed edge holds only indices 
 * to the underlying points that compose its vertices.
 * @param <T> The underlying type of 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IndexedEdge<T extends Point3D> extends Edge<T>{

  /**
   * Get the indices of the vertices that delimit this edge.
   * @return the indices of the vertices that delimit this edge.
   * @see #getVertices()
   * @see #getVerticesSource()
   */
  int[] getVerticesIndexes();

  /**
   * Get the {@link Point3DContainer container} which holds the points that are referenced by the indices of this geometry.
   * @return the {@link Point3DContainer container} which holds the points that are referenced by the indices of this geometry.
   */
  Point3DContainer<T> getVerticesSource();
  
  /**
   * Set {@link Point3DContainer} which holds the points that are referenced by the indices of this geometry.
   * @param source the {@link Point3DContainer} which holds the points that are referenced by the indices of this geometry.
   */
  void setVerticesSource(Point3DContainer<T> source);
  
  /**
   * Return if the indices of the geometry have been validated. The validation is made by calling method {@link #validateIndexes()}.
   * @return <code>true</code> if the geometry has validated indexes and <code>false</code> otherwise.
   */
  boolean isValidatedIndexes();
  
  /**
   * Validate the geometry. This method valid the link between the indexed geometry and the geometry itself.
   * @return <code>true</code> if the validation is effective, <code>false</code> otherwise.
   */
  boolean validateIndexes();
}
