package org.jeometry.geom2D;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.point.Point2D;

/**
 * A spatial localization within a 2D space. Such an item is represented by a set of coordinates and a set of bounds.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public interface SpatialLocalization2D {

  /**
   * Get the minimal coordinates of the localization.
   * @return  the minimal coordinates of the localization.
   * @see #getMaxCoordinates()
   */
  public Point2D getMinCoordinates();
  
  /**
   * Get the maximal coordinates of the localization.
   * @return  the maximal coordinates of the localization.
   * @see #getMinCoordinates()
   */
  public Point2D getMaxCoordinates();
	
  /**
   * Get the X coordinate of this localization.
   * @return the X coordinate of this localization.
   */
  public double getX();

  /**
   * Get the Y coordinate of this localization.
   * @return the Y coordinate of this localization.
   */
  public double getY();

  /**
   * Compute the distance between this spatial localization and the given one.
   * @param spatial the spatial localization.
   * @return the distance between this spatial localization and the given one.
   */
  public double distance(SpatialLocalization2D spatial);
  
  /**
   * Update the localization values. It is implied that after a call to this method, all the informations given
   * by the spatial localization are consistent.
   */
  public void updateLocalization();

}
