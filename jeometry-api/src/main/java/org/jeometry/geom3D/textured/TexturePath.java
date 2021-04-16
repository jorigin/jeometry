package org.jeometry.geom3D.textured;

import org.jeometry.Jeometry;

/**
 * A {@link Texture texture} is an identified resource that can provide display information attached to a coordinate. 
 * This texture is linked with a resource described by its path (file, URL, ...) 
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class TexturePath implements Texture {
  
  /**
   * The resource path. 
   */
  String ressourcePath = null;
  
  @Override
  public Object getResource() {
    return ressourcePath;
  }

  @Override
  public void setResource(Object resource) {
    if (resource instanceof String){
      ressourcePath = (String) resource;
    }
  }
  
  /**
   * Get the path that describe the attached resource.
   * @return the path that describe the attached resource.
   */
  public String getResourcePath(){
    return ressourcePath;
  }
  
  /**
   * Default constructor should not be used as texture identifier and resources are needed by various processes.
   */
  public TexturePath(){
    super();
  }
  
  /**
   * Construct a new texture identified by the <code>name</code> and the numerical identifier <code>idn</code> given in parameters 
   * and linked to the given <code>resource</code>. A texture resource can be an image file path, a raster image, ...
   * @param name the name of the texture.
   * @param idn the numerical identifier of the resource.
   * @param resource the resource attached to the texture.
   */
  public TexturePath(String name, int idn, String resource){
    super();
    setResource(resource);
  }
}
