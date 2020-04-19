package org.jeometry.geom3D.primitive.indexed;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.primitive.Triangle;


/**
 * A triangle is a 3D polygon hat relies on 3 non aligned vertices. This interface represents an indexed representation of a triangle. 
 * Such a representation relies on a vertices source that contains the 3D points from which the geometry is constructed.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 * @see IndexedMesh
 * @see IndexedFace
 * @see IndexedPolygon3D
 *
 */
public interface IndexedTriangle<T extends Point3D> extends Triangle<T>, IndexedFace<T> {

  /**
   * Get the index within the underlying {@link #getVerticesSource() vertices source} of the first vertex of this triangle.
   * @return the index within the underlying {@link #getVerticesSource() vertices source} of the first vertex of this triangle.
   * @see #getVertex2Index()
   * @see #getVertex3Index()
   */
  public int getVertex1Index();
  
  /**
   * Get the index within the underlying {@link #getVerticesSource() vertices source} of the second vertex of this triangle.
   * @return the index within the underlying {@link #getVerticesSource() vertices source} of the second vertex of this triangle.
   * @see #getVertex1Index()
   * @see #getVertex3Index()
   */
  public int getVertex2Index();
  
  /**
   * Get the index within the underlying {@link #getVerticesSource() vertices source} of the third vertex of this triangle.
   * @return the index within the underlying {@link #getVerticesSource() vertices source} of the third vertex of this triangle.
   * @see #getVertex1Index()
   * @see #getVertex2Index()
   */
  public int getVertex3Index();
  
}
