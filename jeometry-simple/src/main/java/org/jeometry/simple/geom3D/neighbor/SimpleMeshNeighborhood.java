package org.jeometry.simple.geom3D.neighbor;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.neighbor.AdjacencyMap;
import org.jeometry.geom3D.neighbor.IncidenceMap;
import org.jeometry.geom3D.neighbor.MeshNeighborhood;
import org.jeometry.geom3D.point.Point3D;

/**
 * A simple implementation of a {@link MeshNeighborhood mesh neighborhood}.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class SimpleMeshNeighborhood<T extends Point3D> implements MeshNeighborhood<T>{

  /**
   * The adjacencies. 
   */
  AdjacencyMap<T> adjacencies = null;
  
  /**
   * The incidencies.
   */
  IncidenceMap<T> incidences  = null;
  
  @Override
  public AdjacencyMap<T> getAdjacencies() {
    return this.adjacencies;
  }

  @Override
  public void setAdjacencies(AdjacencyMap<T> adjacencies) {
    this.adjacencies = adjacencies;
  }

  @Override
  public IncidenceMap<T> getIncidences() {
    return this.incidences;
  }

  @Override
  public void setIncidences(IncidenceMap<T> incidences) {
    this.incidences = incidences;
  }

  /**
   * Create a new empty {@link MeshNeighborhood mesh neighborhood}.
   */
  public SimpleMeshNeighborhood(){
    this.adjacencies = new SimpleIndexedAdjencyMap<T>();
    this.incidences  = new SimpleIndexedIncidenceMap<T>();
  }
  
  /**
   * Create a new {@link MeshNeighborhood mesh neighborhood} with the given incidence map and the given adjacency map.
   * @param adjacencies the adjacencies that compose the neighborhood.
   * @param incidences the incidences that compose the neighborhood.
   */
  public SimpleMeshNeighborhood(AdjacencyMap<T> adjacencies, IncidenceMap<T> incidences){
    this.adjacencies = adjacencies;
    this.incidences  = incidences;
  }
}
