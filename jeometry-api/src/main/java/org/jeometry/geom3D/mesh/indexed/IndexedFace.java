package org.jeometry.geom3D.mesh.indexed;

import org.jeometry.Geometry;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.primitive.indexed.IndexedPolygon3D;

/**
 * An indexed mesh face. Such a geometry is made of a set of indices that points vertex within a source. 
 * The indexes vertex describes the bounds of the face. 
 * @param <T> the type of the underlying 3D points.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public interface IndexedFace<T extends Point3D> extends Face<T>, IndexedPolygon3D<T>{

  /**
   * Return the mesh to which the face is attached
   * @return IndexedMesh The mesh from which the face is attached
   */
  @Override
  public IndexedMesh<T> getMesh();

  /**
   * Set the mesh to which the face is attached.
   * @param mesh the mesh to which attach the face
   */
  public void setMesh(IndexedMesh<T> mesh);

  
}
