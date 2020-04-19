package org.jeometry.geom3D.primitive;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;

/**
 * A triangle is a 3D polygon hat relies on 3 non aligned vertices.
 * @param <T> the type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 * @see Mesh
 * @see Face
 * @see Polygon3D
 */
public interface Triangle<T extends Point3D> extends Face<T>{
  
  /**
   * Get the first vertex of the triangle.
   * @return the first vertex of the triangle.
   * @see #getVertex2()
   * @see #getVertex3()
   */
  public T getVertex1();
  
  /**
   * Get the second vertex of the triangle.
   * @return the second vertex of the triangle.
   * @see #getVertex1()
   * @see #getVertex3()
   */
  public T getVertex2();
  
  /**
   * Get the third vertex of the triangle.
   * @return the third vertex of the triangle.
   * @see #getVertex1()
   * @see #getVertex2()
   */
  public T getVertex3();
  
}