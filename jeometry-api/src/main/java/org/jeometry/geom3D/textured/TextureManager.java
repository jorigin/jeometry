package org.jeometry.geom3D.textured;

import java.util.List;

/**
 * A texture manager is an object that can handle a  {@link Texture texture} list. 
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.0
 */
public interface TextureManager {

  /**
   * Get the {@link Texture textures} handled by this texture manager.
   * @return the {@link Texture textures} managed by this texture manager.
   */
  public List<Texture> getTextures();
  
  /**
   * Set the {@link Texture textures} handled by this texture manager.
   * @param textures the {@link Texture textures} managed by this texture manager.
   */
  public void setTextures(List<Texture> textures);
}
