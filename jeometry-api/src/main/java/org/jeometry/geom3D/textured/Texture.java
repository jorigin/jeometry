package org.jeometry.geom3D.textured;

import org.jeometry.Geometry;

/**
 * A texture is an identified resource that can provide display information attached to a coordinate.
 * (the resource can be a path to an image, a dynamic raster, ...)
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 */
public interface Texture{
  
  /**
   * Get the resource attached to the texture.
   * @return the resource attached to the texture.
   * @see #setResource(Object)
   */
  public Object getResource();
  
  /**
   * Set the resource attached to the texture.
   * @param resource the resource attached to the texture.
   * @see #getResource()
   */
  public void setResource(Object resource);
}
