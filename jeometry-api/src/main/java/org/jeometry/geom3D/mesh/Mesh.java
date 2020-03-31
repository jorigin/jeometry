package org.jeometry.geom3D.mesh;

import java.io.Serializable;
import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Line3D;
import org.jeometry.geom3D.primitive.Polygon3D;

/**
 * This interface represent a mesh within a 3D space.
 * A mesh is made of a {@link Point3DContainer collection of 3D points} (the vertices) that are linked by a collection of {@link Edge edges} 
 * and a collection of faces {@link Face faces}. <br>
 * An {@link Edge} is a {@link Line3D 3D line} that link two vertices. 
 * A {@link Face face} is a {@link Polygon3D 3D polygon}, the set of all faces make the surface of the mesh.<br>
 * A mesh can represent a polyhedral object (convex or not) or a surface.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 */
public interface Mesh<T extends Point3D> extends Serializable, SpatialLocalization3D{
  
  /**
   * Return the {@link Face faces} of the mesh. Faces are planar 3D polygons
   * @return the list containing the {@link Face faces} of the mesh
   */
  List<? extends Face<T>> getFaces();

  /**
   * Return the {@link Edge edges} of the mesh. Edges are couple of vertices.
   * @return the list containing the {@link Edge edges} of the mesh
   */
  List<? extends Edge<T>> getEdges();

  /**
   * Return the {@link Point3DContainer vertices} of the mesh.
   * @return the {@link Point3DContainer vertices} of the mesh.
   */
  Point3DContainer<T> getVertices();

  /**
   * Add a new {@link Face face} to the mesh
   * @param face The {@link Face face} to add to the mesh
   * @return <code>true</code> if the {@link Face face} is added, <code>false</code> otherwise
   */
  boolean addFace(Face<T> face);

  /**
   * Remove the given {@link Face face} from the mesh
   * @param face the {@link Face face} to remove.
   * @return <code>true</code> if the {@link Face face} is removed, <code>false</code> otherwise
   */
  boolean removeFace(Face<?> face);
}
