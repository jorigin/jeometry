package org.jeometry.simple.geom3D.primitive;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.primitive.Plane;

/**
 * A simple implementation of a {@link Plane plane}.
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimplePlane<T extends Point3D> implements Plane<T>{

	/**
	 * The normal.
	 */
	private T normal;
	
	/**
	 * The origin.
	 */
	private T origin;
	
	/**
	 * The D value.
	 */
	private double d;
	
	@Override
	public T getPlaneNormal() {
		return this.normal;
	}

	@Override
	public void setPlaneNormal(T normal) {
	  this.normal = normal;
	  
	  if ((normal != null) && (this.origin != null)){
		  this.d = -1.0d*normal.getX()*this.origin.getX() - normal.getY()*this.origin.getY() - normal.getZ()*this.origin.getZ();
	  } else {
		  this.d = Double.NaN;
	  }
	}

	@Override
	public T getPlaneOrigin() {
		return this.origin;
	}

	@Override
	public void setPlaneOrigin(T origin) {
      this.origin = origin;
      
      if ((this.normal != null) && (origin != null)){
		  this.d = -1.0d*this.normal.getX()*origin.getX() - this.normal.getY()*origin.getY() - this.normal.getZ()*origin.getZ();
	  } else {
		  this.d = Double.NaN;
	  }
	}

	@Override
	public void getPlaneParameters(T origin, T normal) {
		
		if (this.origin != null) {
			if (origin != null) {
				origin.setX(this.origin.getX());
				origin.setY(this.origin.getY());
				origin.setZ(this.origin.getZ());
			} else {
				throw new IllegalArgumentException("Plan origin output is null.");
			}
		} else {
			throw new IllegalStateException("Plane origin is null.");
		}
		
		
		if (this.normal != null) {
			if (normal != null) {
				normal.setX(this.normal.getX());
				normal.setY(this.normal.getY());
				normal.setZ(this.normal.getZ());
			} else {
				throw new IllegalArgumentException("Plan normal output is null.");
			}
		} else {
			throw new IllegalStateException("Plane normal is null.");
		}
	}

	@Override
	public void setPlaneParameters(T origin, T normal) {
		this.origin = origin;
		this.normal = normal;
		
	    if ((normal != null) && (origin != null)){
	    	this.d = -1.0d*normal.getX()*origin.getX() - normal.getY()*origin.getY() - normal.getZ()*origin.getZ();
		} else {
			this.d = Double.NaN;
		}
	}
	
	@Override
	public double getCoefA() {
		return (this.normal != null)?this.normal.getX():Double.NaN;
	}

	@Override
	public double getCoefB() {
		return (this.normal != null)?this.normal.getY():Double.NaN;
	}

	@Override
	public double getCoefC() {
		return (this.normal != null)?this.normal.getZ():Double.NaN;
	}

	@Override
	public double getCoefD() {
		return this.d;
	}

	@Override
	public double distance(SpatialLocalization3D spatial) {
		if (spatial != null) {
			return    Math.abs(this.normal.getX() * spatial.getX() + this.normal.getY() * spatial.getY() + this.normal.getZ()*spatial.getZ() - this.d)
					/ this.normal.norm();
		}
		return Double.NaN;
	}

}
