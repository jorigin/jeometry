package org.jeometry.geom3D.textured;

import org.jeometry.Jeometry;

/**
 * A texture is an identified resource that can provide display information attached to a coordinate.
 * (the resource can be a path to an image, a dynamic raster, ...)
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
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
