package org.jeometry.simple.geom3D.neighbor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeometry.Geometry;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.neighbor.IncidenceMap;
import org.jeometry.geom3D.point.Point3D;

/**
 * A simple implementation of an {@link IncidenceMap incidence map}.
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 */
public class SimpleIndexedIncidenceMap<T extends Point3D> implements IncidenceMap<T> {

  private Map<Point3D, List<IndexedFace<T>>> map = null;
  
  @Override
  public List<IndexedFace<T>> getIncidences(Point3D vertex) {
    return map.get(vertex);
  }

  @Override
  public void setIncidences(T vertex, List<IndexedFace<T>> incidences) {
    map.put(vertex, incidences);
  }

  @Override
  public boolean addIncident(T vertex, IndexedFace<T> incident) {
    List<IndexedFace<T>> l = map.get(vertex);
    
    if (l == null){
      l = new ArrayList<IndexedFace<T>>();
      map.put(vertex, l);
    }
    
    return l.add(incident);
  }

  @Override
  public boolean removeIncident(T vertex, IndexedFace<T> incident) {
    List<IndexedFace<T>> l = map.get(vertex);
    
    if (l != null){
      return l.remove(incident);
    }
    
    return false;
  }

  /**
   * Create a new empty incidence map.
   */
  public SimpleIndexedIncidenceMap() {
	  
  }
}
