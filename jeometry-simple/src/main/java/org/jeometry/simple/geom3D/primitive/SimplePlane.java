package org.jeometry.simple.geom3D.primitive;

import org.jeometry.Geometry;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.primitive.Plane;

/**
 * A simple implementation of a {@link Plane plane}.
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public class SimplePlane<T extends Point3D> implements Plane<T>{

	private T normal;
	
	private T origin;
	
	private double d;
	
	@Override
	public T getPlaneNormal() {
		return normal;
	}

	@Override
	public void setPlaneNormal(T normal) {
	  this.normal = normal;
	  
	  if ((normal != null) && (origin != null)){
		  d = -1.0d*normal.getX()*origin.getX() - normal.getY()*origin.getY() - normal.getZ()*origin.getZ();
	  } else {
		  d = Double.NaN;
	  }
	}

	@Override
	public T getPlaneOrigin() {
		return origin;
	}

	@Override
	public void setPlaneOrigin(T origin) {
      this.origin = origin;
      
      if ((normal != null) && (origin != null)){
		  d = -1.0d*normal.getX()*origin.getX() - normal.getY()*origin.getY() - normal.getZ()*origin.getZ();
	  } else {
		  d = Double.NaN;
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
	    	d = -1.0d*normal.getX()*origin.getX() - normal.getY()*origin.getY() - normal.getZ()*origin.getZ();
		} else {
			d = Double.NaN;
		}
	}
	
	@Override
	public double getCoefA() {
		return (normal != null)?normal.getX():Double.NaN;
	}

	@Override
	public double getCoefB() {
		return (normal != null)?normal.getY():Double.NaN;
	}

	@Override
	public double getCoefC() {
		return (normal != null)?normal.getZ():Double.NaN;
	}

	@Override
	public double getCoefD() {
		return d;
	}

	@Override
	public double distance(SpatialLocalization3D spatial) {
		if (spatial != null) {
			return    Math.abs(normal.getX() * spatial.getX() + normal.getY() * spatial.getY() + normal.getZ()*spatial.getZ() - d)
					/ normal.norm();
		}
		return Double.NaN;
	}

}
