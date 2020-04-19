package org.jeometry.geom3D.properties;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.point.Point3D;

/**
 * Specify that an object have a normal vector expressed as a {@link Point3D 3D point}
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface HasNormal {
  
  /**
   * Get the normal vector attached to this object.
   * @return the normal vector attached to this object.
   */
  public Point3D getNormal();
  
  /**
   * Set the normal vector attached to this object.
   * @param normal the normal of the object.
   */
  public void setNormal(Point3D normal);
}
