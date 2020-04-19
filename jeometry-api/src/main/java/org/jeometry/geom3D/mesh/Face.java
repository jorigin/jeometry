package org.jeometry.geom3D.mesh;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.primitive.Polygon3D;

/**
 * A face is a {@link Polygon3D 3D polygon} that is part of a {@link Mesh mesh}.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 * @see Mesh
 * @see Polygon3D
 */
public interface Face<T extends Point3D> extends Polygon3D<T>{

  /**
   * Return the {@link Mesh mesh} to which the face is attached
   * @return The {@link Mesh mesh} from which the face is attached
   */
  public Mesh<T> getMesh();

  /**
   * Set the {@link Mesh mesh} to which the face is attached.
   * @param mesh the {@link Mesh mesh} to which attach the face
   */
  public void setMesh(Mesh<T> mesh);
  
  /**
   * Get the {@link Edge edges} of the face.
   * @return the {@link Edge edges} of the face.
   */
  public List<? extends Edge<T>> getEdges();
}
