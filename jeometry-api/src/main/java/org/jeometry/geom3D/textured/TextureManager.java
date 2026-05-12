package org.jeometry.geom3D.textured;

import java.util.List;

/**
 * A texture manager is an object that can handle a  {@link Texture texture} list. 
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
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
