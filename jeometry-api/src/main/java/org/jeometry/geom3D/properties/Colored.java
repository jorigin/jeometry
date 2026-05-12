package org.jeometry.geom3D.properties;

import java.awt.Color;

import org.jeometry.Jeometry;

/**
 * Specify that an object holds color information expressed as a {@link Color 3D color}
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface Colored {

  /**
   * Get the color attached to this object.
   * @return the color attached to this object.
   */
  public Color getColor();
  
  /**
   * Set the color attached to this object.
   * @param color the color of the object.
   */
  public void setColor(Color color);
  
}
