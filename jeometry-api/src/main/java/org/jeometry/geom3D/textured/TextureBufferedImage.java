package org.jeometry.geom3D.textured;

import java.awt.image.BufferedImage;

import org.jeometry.Jeometry;

/**
 * A {@link Texture texture} is an identified resource that can provide display information attached to a coordinate. 
 * This texture use a {@link BufferedImage Buffered image} as resource.<br><br>
 * If the underlying resource has only to be an {@link java.awt.Image image}, a 
 * {@link TextureImage TextureImage} can be used.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class TextureBufferedImage implements Texture{
  
  BufferedImage ressourceImage = null;
  
  @Override
  public Object getResource() {
    return ressourceImage;
  }

  @Override
  public void setResource(Object resource) {
    if (resource instanceof BufferedImage){
      ressourceImage = (BufferedImage) resource;
    }
  }
  
  /**
   * Get the {@link BufferedImage Buffered image} that is used as resource.
   * @return the {@link BufferedImage Buffered image} that is used as resource.
   */
  public BufferedImage getResourceImage(){
    return ressourceImage;
  }
  
  /**
   * Default constructor should not be used as texture identifier and resources are needed by various processes.
   */
  public TextureBufferedImage(){
    super();
  }
  
  /**
   * Construct a new texture identified by the <code>name</code> and the numerical identifier <code>idn</code> given in parameters 
   * and linked to the {@link BufferedImage Buffered image} given as <code>resource</code>.
   * @param name the name of the texture.
   * @param idn the numerical identifier of the resource.
   * @param resource the {@link BufferedImage Buffered image} attached as resource to the texture.
   */
  public TextureBufferedImage(String name, int idn, BufferedImage resource){
    super();
    setResource(resource);
  }
  
}
