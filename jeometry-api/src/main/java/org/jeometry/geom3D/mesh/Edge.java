package org.jeometry.geom3D.mesh;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.primitive.Line3D;
import org.jeometry.geom3D.primitive.Polygon3D;

/**
 * An interface that describe an edge.  
 * An {@link Edge edge} is a {@link Line3D 3D line} that link two vertices and that delimits a polygon or that delimits faces of a polyhedron.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 * @see Polygon3D
 * @see Face
 * @see Mesh
 */
public interface Edge<T extends Point3D> extends Line3D<T>{
  
  /**
   * Return the {@link Mesh mesh} to which the edge is attached
   * @return The {@link Mesh mesh} from which the edge is attached
   */
  public Mesh<T> getMesh();

  /**
   * Set the {@link Mesh mesh} to which the edge is attached.
   * @param mesh the {@link Mesh mesh} to which attach the edge
   */
  public void setMesh(Mesh<T> mesh);
	
  /**
   * Get the {@link Face faces} attached to the edge
   * @return the {@link Face faces} attached to this edge
   */
  List<? extends Face<T>> getFaces();
}
