package org.jeometry.geom3D;

import org.jeometry.Jeometry;

/**
 * A spatial localization within a 3D space. Such an item is represented by a set of coordinates and a set of bounds.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public interface SpatialLocalization3D {

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
     * Get the Z coordinate of this localization.
     * @return the Z coordinate of this localization.
     */
    public double getZ();

    /**
     * Get the minimal X coordinate of this spatial item.
     * @return the minimal X coordinate of this spatial item.
     */
    public double getXMin();

    /**
     * Get the minimal Y coordinate of this spatial item.
     * @return the minimal Y coordinate of this spatial item.
     */
    public double getYMin();

    /**
     * Get the minimal Z coordinate of this spatial item.
     * @return the minimal Z coordinate of this spatial item.
     */
    public double getZMin();

    /**
     * Get the maximal X coordinate of this spatial item.
     * @return the maximal X coordinate of this spatial item.
     */
    public double getXMax();

    /**
     * Get the maximal Y coordinate of this spatial item.
     * @return the maximal Y coordinate of this spatial item.
     */
    public double getYMax();

    /**
     * Get the maximal Z coordinate of this spatial item.
     * @return the maximal Z coordinate of this spatial item.
     */
    public double getZMax();
    
    /**
     * Compute the distance between this spatial localization and the given one.
     * @param spatial the spatial localization.
     * @return the distance between this spatial localization and the given one or <code>Double.Nan</code> if the given spatial is <code>null</code> or if this localization is not known.
     */
    public double distance(SpatialLocalization3D spatial);
    
    /**
     * Update the localization values. It is implied that after a call to this method, all the informations given
     * by the spatial localization are consistent.
     */
    public void updateLocalization();
    
}
