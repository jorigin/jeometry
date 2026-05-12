package org.jeometry.geom3D.neighbor;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;

/**
 * A {@link Mesh mesh} neighborhood that is made of an {@link AdjacencyMap adjacency map} and an {@link IncidenceMap incidence map}.
 * @param <T> The type of underlying 3D points
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 *
 */
public interface MeshNeighborhood<T extends Point3D> {

  /**
   * Get the adjacency map of the neighborhood.
   * @return the adjacency map of the neighborhood.
   * @see #setAdjacencies(AdjacencyMap)
   */
  public AdjacencyMap<T> getAdjacencies();
  
  /**
   * Set the adjacencies map of the neighborhood.
   * @param adjacencies the adjacency map of the neighborhood.
   * @see #getAdjacencies()
   */
  public void setAdjacencies(AdjacencyMap<T> adjacencies);
  
  /**
   * Get the incidences map of the neighborhood.
   * @return the incidences map of the neighborhood.
   * @see #setIncidences(IncidenceMap)
   */
  public IncidenceMap<T> getIncidences();
  
  /**
   * Set the incidences map of the neighborhood.
   * @param incidences the incidences map of the neighborhood.
   * @see #getIncidences()
   */
  public void setIncidences(IncidenceMap<T> incidences);
}
