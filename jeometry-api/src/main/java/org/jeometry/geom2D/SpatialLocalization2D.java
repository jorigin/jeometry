package org.jeometry.geom2D;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.point.Point2D;

/**
 * A spatial localization within a 2D space. Such an item is represented by a set of coordinates and a set of bounds.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public interface SpatialLocalization2D {

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
     * Return a {@link Point2D 2D Point} that holds the minimal coordinates of this spatial localization.
     * @return a 2D Point that holds the minimal coordinates of this spatial localization
     */
    public Point2D getMinCoordinates();
    
    /**
     * Return a {@link Point2D 2D Point} that holds the maximal coordinates of this spatial localization.
     * @return a 2D Point that holds the maximal coordinates of this spatial localization
     */
    public Point2D getMaxCoordinates();
 
  /**
   * Compute the distance between this spatial localization and the given one.
   * @param spatial the spatial localization.
   * @return the distance between this spatial localization and the given one.
   */
  public double distance(SpatialLocalization2D spatial);
  
  /**
   * Update the localization values. It is implied that after a call to this method, all information given
   * by the spatial localization are consistent.
   */
  public void updateLocalization();

}
