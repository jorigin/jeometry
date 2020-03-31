package org.jeometry.geom3D.neighbor;

import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.point.Point3D;

/**
 * An adjacency map that enable to describe faces adjacencies for a given {@link IndexedMesh indexed mesh}.
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 */
public interface AdjacencyMap<T extends Point3D> {

  /**
   * Get the faces that are adjacent to the given one.
   * @param face the face for which adjacencies are needed.
   * @return the faces that are adjacent to the given one.
   * @see #setAdjacencies(IndexedFace, List)
   */
  List<IndexedFace<T>> getAdjacencies(IndexedFace<T> face);
  
  /**
   * Set the faces that are adjacent to the given one.
   * @param face the face for which adjacency has to be set.
   * @param adjacencies the list of all the faces that are adjacent.
   * @see #getAdjacencies(IndexedFace)
   */
  void setAdjacencies(IndexedFace<T> face, List<IndexedFace<T>> adjacencies);
  
  /**
   * Add the given <code>adjacent</code> as an adjacent of the given <code>face</code>.
   * @param face the face that is updated.
   * @param adjacent the new adjacent to add.
   * @return <code>true</code> if the adjacent is successfully added and <code>false</code> otherwise.
   */
  boolean addAdjacent(IndexedFace<T> face, IndexedFace<T> adjacent);
  
  /**
   * Remove the given <code>adjacent</code> as an adjacent of the given <code>face</code>.
   * @param face face the face that is updated.
   * @param adjacent the adjacent to remove.
   * @return <code>true</code> if the adjacent is successfully removed and <code>false</code> otherwise.
   */
  boolean removeAdjacent(IndexedFace<T> face, IndexedFace<T> adjacent);
}
