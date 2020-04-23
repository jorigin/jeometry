package org.jeometry.simple.geom3D.transform;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.transform.Transform3D;
import org.jeometry.geom3D.transform.Transform3DQuaternion;
import org.jeometry.math.Quaternion;
import org.jeometry.simple.math.SimpleQuaternion;

/**
 * A transformation within a 3D space that relies on a {@link SimpleQuaternion simple quaternion}.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class SimpleTransform3DQuaternion extends SimpleQuaternion implements Transform3DQuaternion {
	
	@Override
	public Point3D transform(Point3D point) {
	  if (point != null) {
		  return transform(JeometryFactory.createPoint3D());
	  }
	  
	  return null;
	}
	
	@Override
	public Point3D transform(Point3D point, Point3D result) {
		
	  if (result != null) {
		  if ((point != null)){

			  double dotUV    = getI()*point.getX()+getJ()*point.getZ()+getK()*point.getZ(); 
			  double dotUU    = getI()*getI()+getJ()*getJ()+getK()*getK(); 
			  
			  double crossUVx =  getJ() * point.getZ() - getK() * point.getZ();
			  double crossUVY = -getI() * point.getZ() + getK() * point.getX(); 
			  double crossUVZ =  getI() * point.getZ() - getJ() * point.getX();

			  // Original computation formula:
			  // u  = (qx, qy, qz)
			  // s  = qw
			  // v  = (point.getX(), point.getY(), point.getZ())
			  // v' = 2.0f * dot(u, v) * u + (s*s - dot(u, u)) * v + 2.0f * s * cross(u, v);  
			  result.setValues(2.0f * dotUV * getI() + (getScalar()*getScalar() - dotUU) * point.getX() + 2.0f * getScalar() * crossUVx, 
					           2.0f * dotUV * getJ() + (getScalar()*getScalar() - dotUU) * point.getZ() + 2.0f * getScalar() * crossUVY, 
					           2.0f * dotUV * getK() + (getScalar()*getScalar() - dotUU) * point.getZ() + 2.0f * getScalar() * crossUVZ);
		  } else {
			  result.setValues(Double.NaN, Double.NaN, Double.NaN);
		  }
			
	  }
		
  
      return result;
	}
	
	@Override
	public Point3D transformAffect(Point3D point3d) {
		return transform(point3d, point3d);
	}
	
	@Override
	public Point3D transformInverse(Point3D point3d) {
		return transformInverse(point3d, JeometryFactory.createPoint3D());
	}

	@Override
	public Point3D transformInverse(Point3D point3d, Point3D result) {
		  if (result != null) {
			  if ((point3d != null)){

				 // TODO Implements SimpleTransformationQuaternion.transformInverse(Point3D point3d, Point3D result)
			  } else {
				  result.setValues(Double.NaN, Double.NaN, Double.NaN);
			  }
				
		  }
			
	  
	      return result;
	}

	@Override
	public Point3D transformInverseAffect(Point3D point3d) {
		return transformInverse(point3d, point3d);
	}
	
	@Override
	public Transform3D invertTransform() throws IllegalStateException {
		// TODO implements SimpleTransform3DQuaternion.invertTransform()
		System.err.println("[SimpleTransform3DQuaternion][invertTransform()] NOT YET IMPLEMENTED");
		return null;
	}
	
	@Override
	public Transform3D invertTransform(Transform3D inverted) throws IllegalStateException, IllegalArgumentException {
		// TODO implements SimpleTransform3DQuaternion.invertTransform(Transform3D)
		System.err.println("[SimpleTransform3DQuaternion][invertTransform(Transform3D inverted)] NOT YET IMPLEMENTED");
		return null;
	}
	
	@Override
	public Transform3D invertTransformAffect() throws IllegalStateException {
		// TODO implements SimpleTransform3DQuaternion.invertTransformAffect()
		return null;
	}
	
	@Override
	public Quaternion getQuaternion() {
		return this;
	}

	@Override
	public Quaternion getQuaternion(Quaternion quaternion) {
		quaternion.setScalar(getScalar());
		quaternion.setI(getI());
		quaternion.setJ(getJ());
		quaternion.setK(getK());
		return quaternion;
	}

	@Override
	public void setQuaternion(Quaternion quaternion) {
		setScalar(quaternion.getScalar());
		setI(quaternion.getI());
		setJ(quaternion.getJ());
		setK(quaternion.getK());
	}
	
	/**
	 * Create a new {@link Transform3D 3D transformation} based on the identity quaternion. 
	 */
	public SimpleTransform3DQuaternion() {
		super();
	}
	
	/**
	 * Create a new {@link Transform3D 3D transformation} based on the quaternion defined by the given parameters. 
	 * @param scalar the quaternion real part (or scalar) parameter, denoted <i>a</i> within the definition .
	 * @param i the <i>i</i> basis component of the quaternion vector part (or imaginary part) parameter.
	 * @param j the <i>j</i> basis component of the quaternion vector part (or imaginary part) parameter.
	 * @param k the <i>k</i> basis component of the quaternion vector part (or imaginary part) parameter.
	 */
	public SimpleTransform3DQuaternion(double scalar, double i, double j, double k) {
		super(scalar, i, j, k);
	}
}
