package org.jeometry.geom3D.primitive;

import org.jeometry.Geometry;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.point.Point3D;

/**
 * An infinite plane that represents a two-dimensional doubly ruled surface spanned by two linearly independent vectors.<br><br>
 * A plane can be defined by a normal vector, denoted <i>n</i>, and an origin point, denoted <i>p<sub>0</sub></i>.  
 * Let <i>n</i>&nbsp;=&nbsp;(<i>a</i>,&nbsp;<i>b</i>,&nbsp;<i>c</i>) a vector and let <i>p<sub>0</sub></i>&nbsp;=&nbsp;(x<sub>0</sub>,&nbsp;y<sub>0</sub>,&nbsp;z<sub>0</sub>) a point.
 * A point <i>p</i>&nbsp;=&nbsp;(x,&nbsp;y,&nbsp;z) lies on the plane if and only if:<br>
 * <i>n</i>&nbsp;&middot;&nbsp;(p&nbsp;-&nbsp;p<sub>0</sub>)&nbsp;=&nbsp;<i>0</i><br>
 * The equation of a place can be developped into:<br><br>
 * <i>ax</i>&nbsp;+&nbsp;<i>by</i>&nbsp;+&nbsp;<i>cz</i>&nbsp;+&nbsp;<i>d</i>&nbsp;=&nbsp;<i>0</i><br>
 * where <i>d</i>&nbsp;=&nbsp;-<i>ax<sub>0</sub></i>&nbsp;-&nbsp;<i>by<sub>0</sub></i>&nbsp;-&nbsp;<i>cz<sub>0</sub></i>.<br><br>
 * source: <a href="http://mathworld.wolfram.com/Plane.html">http://mathworld.wolfram.com/Plane.html</a>
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public interface Plane<T extends Point3D> {

	/**
	 * Get the normal vector of the plane.
	 * @return the normal vector of the plane.
	 * @see #setPlaneNormal(Point3D)
	 * @see #setPlaneParameters(Point3D, Point3D)
	 */
	public T getPlaneNormal();
	
	/**
	 * Set the normal vector of the plane.
	 * @param normal the normal vector of the plane.
	 * @see #getPlaneNormal()
	 */
	public void setPlaneNormal(T normal);
	
	/**
	 * Get the plane origin.
	 * @return the plane origin
	 * @see #setPlaneOrigin(Point3D)
	 * @see #setPlaneParameters(Point3D, Point3D)
	 */
	public T getPlaneOrigin();
	
	/**
	 * Set the plane origin.
	 * @param origin the plane origin
	 * @see #getPlaneOrigin()
	 */
	public void setPlaneOrigin(T origin);
	
	/**
	 * Get the plane parameters (normal and origin) by copying it within the given ones.
	 * @param origin the point that will hold the copy of the plane origin.
	 * @param normal the point that will hold the copy of the plane normal.
	 * @see #setPlaneParameters(Point3D, Point3D)
	 */
	public void getPlaneParameters(T origin, T normal);
	
	/**
	 * Set the plane origin and normal.
	 * @param origin the plane origin
	 * @param normal the plane normal
	 */
	public void setPlaneParameters(T origin, T normal);
	
	/**
	 * Get the <i>a</i> coefficient within the plane equation <i>ax</i>&nbsp;+&nbsp;<i>by</i>&nbsp;+&nbsp;<i>cz</i>&nbsp;+&nbsp;<i>d</i>&nbsp;=&nbsp;<i>0</i>.
	 * @return the <i>a</i> coefficient
	 * @see #getCoefB()
	 * @see #getCoefC()
	 * @see #getCoefD()
	 */
	public double getCoefA();
	
	/**
	 * Get the <i>b</i> coefficient within the plane equation <i>ax</i>&nbsp;+&nbsp;<i>by</i>&nbsp;+&nbsp;<i>cz</i>&nbsp;+&nbsp;<i>d</i>&nbsp;=&nbsp;<i>0</i>.
	 * @return the <i>b</i> coefficient
	 * @see #getCoefA()
	 * @see #getCoefC()
	 * @see #getCoefD()
	 */
	public double getCoefB();
	
	/**
	 * Get the <i>c</i> coefficient within the plane equation <i>ax</i>&nbsp;+&nbsp;<i>by</i>&nbsp;+&nbsp;<i>cz</i>&nbsp;+&nbsp;<i>d</i>&nbsp;=&nbsp;<i>0</i>.
	 * @return the <i>c</i> coefficient
	 * @see #getCoefA()
	 * @see #getCoefB()
	 * @see #getCoefD()
	 */
	public double getCoefC();
	
	/**
	 * Get the <i>d</i> coefficient within the plane equation <i>ax</i>&nbsp;+&nbsp;<i>by</i>&nbsp;+&nbsp;<i>cz</i>&nbsp;+&nbsp;<i>d</i>&nbsp;=&nbsp;<i>0</i>.
	 * @return the <i>d</i> coefficient
	 * @see #getCoefA()
	 * @see #getCoefB()
	 * @see #getCoefC()
	 */
	public double getCoefD();
	
    /**
     * Compute the distance between this plane and the given {@link SpatialLocalization3D spatial localization}. 
     * The result is the minimal distance between the plane and the localization.
     * @param spatial the spatial localization.
     * @return the distance between this plane and the given {@link SpatialLocalization3D spatial localization} or <code>Double.Nan</code> if the given spatial is <code>null</code> or if this localization is not known.
     */
    public double distance(SpatialLocalization3D spatial);
}
