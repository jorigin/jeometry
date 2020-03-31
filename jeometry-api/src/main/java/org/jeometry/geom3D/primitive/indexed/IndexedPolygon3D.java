package org.jeometry.geom3D.primitive.indexed;

import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Polygon3D;

/**
 * An indexed polygon. 
 * @param <T> The type of the underlying 3D points.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public interface IndexedPolygon3D<T extends Point3D> extends Polygon3D<T> {
  
  /**
   * Get the vertices indexes of the polygon. Each vertex is a 3D point.
   * @return the vertices indexes of the polygon.
   * @see #setVerticesIndexes(int[])
   */
  int[] getVerticesIndexes();
  
  /**
   * Set the vertices indexes of the polygon. Each vertex is a 3D point.
   * @param indices the vertices indexes of the polygon.
   * @see #getVerticesIndexes()
   */
  void setVerticesIndexes(int[] indices);
  
  /**
   * Return the edges of the polygon. A edge is a couple points
   * @return Vector a Vector containing all the edges of a polygon.
   * @see IndexedEdge
   */
  List<IndexedEdge<T>> getEdgesIndexed();

  /**
   * Return true if the polygon given in parameter is the same this polygon.
   * @param polygon IPolygon3D the polygon to compare
   * @return boolean true if the polygons are equals, false otherwise
   */
  boolean equals(IndexedPolygon3D<?> polygon);

  /**
   * Get the vertices list from which the indexes are coming. This method has not to be confused with {@link #getVertices()} 
   * method that return only the vertices attached to this polygon. 
   * @return the vertices list from which the indexes are coming.
   */
  Point3DContainer<T> getVerticesSource();
 
  /**
   * Set the vertices list from which the indexes are coming.
   * @param verticesSource the vertices list from which the indexes are coming.
   */
  void setVerticesSource(Point3DContainer<T> verticesSource);

  /**
   * Return if the indexes of the geometry have been validated. The validation is made by calling method {@link #validateIndexes()}.
   * @return <code>true</code> if the geometry has validated indexes and <code>false</code> otherwise.
   */
  boolean isValidatedIndexes();
  
  /**
   * Validate the geometry. This method valid the link between the indexed geometry and the geometry itself.
   * @return <code>true</code> if the validation is effective, <code>false</code> otherwise.
   */
  boolean validateIndexes();
  
  /**
   * Reverse the order of the vertices that define a perimeter
   */
  @Override
  void inverseVerticesOrder();



}
