package org.jeometry.geom3D.textured;

import java.awt.Image;

import org.jeometry.Geometry;


/**
 * A {@link Texture texture} is an identified resource that can provide display information attached to a coordinate. 
 * This texture use an {@link Image image} as resource. 
 * If the explicit use of {@link java.awt.image.BufferedImage buffered image} is required as texture resource, a 
 * {@link TextureBufferedImage TextureBufferedImage} can be used.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 */
public class TextureImage implements Texture{
  
  Image ressourceImage = null;
  
  @Override
  public Object getResource() {
    return ressourceImage;
  }

  @Override
  public void setResource(Object resource) {
    if (resource instanceof Image){
      ressourceImage = (Image) resource;
    }
  }
  
  /**
   * Get the {@link Image image} that is used as resource.
   * @return the {@link Image image} that is used as resource.
   */
  public Image getResourceImage(){
    return ressourceImage;
  }
  
  /**
   * Default constructor should not be used as texture identifier and resources are needed by various processes.
   */
  public TextureImage(){
    super();
  }
  
  /**
   * Construct a new texture identified by the <code>name</code> and the numerical identifier <code>idn</code> given in parameters 
   * and linked to the {@link Image image} given as <code>resource</code>.
   * @param name the name of the texture.
   * @param idn the numerical identifier of the resource.
   * @param resource the {@link Image image} attached as resource to the texture.
   */
  public TextureImage(String name, int idn, Image resource){
    super();
    setResource(resource);
  }
}