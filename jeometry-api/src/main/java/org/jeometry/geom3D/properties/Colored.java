package org.jeometry.geom3D.properties;

import java.awt.Color;

import org.jeometry.Geometry;

/**
 * Specify that an object holds color information expressed as a {@link Color 3D color}
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
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
