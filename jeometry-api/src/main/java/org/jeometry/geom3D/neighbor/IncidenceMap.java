package org.jeometry.geom3D.neighbor;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.point.Point3D;

/**
 * A map that handles incidence map for a set of vertices. 
 * Such a map can be used for example for path computation algorithm.
 * @param <T> The type of underlying 3D points.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface IncidenceMap<T extends Point3D> {

  /**
   * Get the faces that are incident to the given vertex.
   * @param vertex the vertex to check.
   * @return the faces that are incident to the given vertex.
   * @see #setIncidences(Point3D, List)
   */
  List<IndexedFace<T>> getIncidences(Point3D vertex);
  
  /**
   * Set the faces that are incident to the given vertex.
   * @param vertex the vertex to set.
   * @param incidences the faces that are incident to the given vertex.
   */
  void setIncidences(T vertex, List<IndexedFace<T>> incidences);
  
  /**
   * Add the given <code>face</code> as an incident to the given <code>vertex</code> 
   * @param vertex the vertex to update.
   * @param incident the face to add to incidences.
   * @return <code>true</code> if the face is successfully added as an incident and <code>false</code> otherwise.
   * @see #removeIncident(Point3D, IndexedFace)
   */
  boolean addIncident(T vertex, IndexedFace<T> incident);
  
  /**
   * Remove the given <code>face</code> from the incidences the given <code>vertex</code> .
   * @param vertex the vertex to update.
   * @param incident the face to remove from incidences.
   * @return <code>true</code> if the face is successfully removed from incidences and <code>false</code> otherwise.
   */
  boolean removeIncident(T vertex, IndexedFace<T> incident);
  
}
