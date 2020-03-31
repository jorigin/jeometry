package org.jeometry.simple.math;

import org.jeometry.Geometry;
import org.jeometry.math.Quaternion;
import org.jeometry.math.Vector;

/**
 * This class is a simple implementation of the {@link Quaternion} interface.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} b{@value Geometry#BUILD}
 * @since 1.0.0
 *
 */
public class SimpleQuaternion implements Quaternion {

	private double scalar = Double.NaN;
	private double i      = Double.NaN;
	private double j      = Double.NaN;
	private double k      = Double.NaN;
	
	@Override
	public int getDimension() {
		return 4;
	}
	
	@Override
	public double getVectorComponent(int dimension) {
		
		if ((dimension >= 0) && (dimension < 4)) {
			if (dimension == Quaternion.QUATERNION_SCALAR_COMPONENT) {
				return scalar;
			} else if (dimension == Quaternion.QUATERNION_I_COMPONENT) {
				return i;
			} else if (dimension == Quaternion.QUATERNION_J_COMPONENT) {
				return j;
			} else if (dimension == Quaternion.QUATERNION_K_COMPONENT) {
				return k;
			} else {
				throw new IllegalArgumentException("Invalid dimension "+dimension+". Expected 0 to 3");
			}
		} else {
			throw new IllegalArgumentException("Invalid dimension "+dimension+". Expected 0 to 3");
		}
	}
	
	@Override
	public void setVectorComponent(int dimension, double value) {
		if ((dimension >= 0) && (dimension < 4)) {
			
			if (dimension == Quaternion.QUATERNION_SCALAR_COMPONENT) {
				scalar = value;;
			} else if (dimension == Quaternion.QUATERNION_I_COMPONENT) {
				i = value;
			} else if (dimension == Quaternion.QUATERNION_J_COMPONENT) {
				j = value;
			} else if (dimension == Quaternion.QUATERNION_K_COMPONENT) {
				k = value;
			} else {
				throw new IllegalArgumentException("Invalid dimension "+dimension+". Expected 0 to 3");
			}
			
		} else {
			throw new IllegalArgumentException("Invalid dimension "+dimension+". Expected 0 to 3");
		}
		
	}
	
	@Override
	public double normSquare() {
		return scalar*scalar + i*i + j*j + k*k;
	}
	
	@Override
	public double norm() {
	  return Math.sqrt(normSquare());
	}
	
	@Override
	public void normalize() {
		double norm = norm();
		
		scalar = scalar / norm;
		i = i / norm;
		j = j / norm;
		k = k / norm;
	}
	
	@Override
	public Vector orthogonal() {
		// TODO implements SimpleQuaternion.orthogonal()
		System.err.println("SimpleQuaternion.orthogonal() NOT YET IMPLEMENTED");
		return null;
	}
	
	@Override
	public Vector orthogonal(Vector result) {
		// TODO implements SimpleQuaternion.orthogonal(Vector)
		System.err.println("SimpleQuaternion.orthogonal(Vector) NOT YET IMPLEMENTED");
		return null;
	}
	
	@Override
	public Vector multiply(double scalar) {
		
		return multiply(scalar, new SimpleVector(getDimension()));
	}

	@Override
	public Vector multiply(double scalar, Vector result) throws IllegalArgumentException {
		if (result != null) {
			if (result.getDimension() >= getDimension()) {
				for(int dimension = 0; dimension < getDimension(); dimension++) {
					result.setVectorComponent(dimension, getVectorComponent(dimension)*scalar);
				}
			} else {
				throw new IllegalArgumentException("Invalid result vector dimension ("+result.getDimension()+"), expected at least "+getDimension());
			}
		}
		
		return result;
	}

	@Override
	public Vector multiplyAffect(double scalar) {
		for(int dimension = 0; dimension < getDimension(); dimension++) {
			setVectorComponent(dimension, getVectorComponent(dimension)*scalar);
		}
		return this;
	}
	
	@Override
	public double getScalar() {
		return scalar;
	}
	
	@Override
	public void setScalar(double value) {
		this.scalar = value;
	}
	
	@Override
	public double getI() {
		return i;
	}
	
	@Override
	public void setI(double value) {
	  this.i = value;
	}
	
	@Override
	public double getJ() {
		return j;
	}
	
	@Override
	public void setJ(double value) {
		j = value;
	}
	
	@Override
	public double getK() {
		return k;
	}
	
	@Override
	public void setK(double value) {
		this.k = value;
	}
	
	@Override
	public double[] getComponents() {
		return new double[] {scalar, i, j, k};
	}
	
	@Override
	public double[] getComponents(double[] components) throws IllegalArgumentException {
		if (components != null) {
			if (components.length >= 4) {
				components[org.jeometry.math.Quaternion.QUATERNION_SCALAR_COMPONENT] = scalar;
				components[org.jeometry.math.Quaternion.QUATERNION_I_COMPONENT] = i;
				components[org.jeometry.math.Quaternion.QUATERNION_J_COMPONENT] = j;
				components[org.jeometry.math.Quaternion.QUATERNION_K_COMPONENT] = k;
			} else {
				throw new IllegalArgumentException("Invalid components length "+components.length+". Expected at least 4");
			}
		}
		
		return components;
	}
	
	@Override
	public void setComponents(double a, double b, double c, double d) {
		scalar = a;
		i      = b;
		j      = c;
		k      = d;
	}
	
	@Override
	public Quaternion mult(Quaternion p) {
		return mult(p, new SimpleQuaternion());
	}
	
	@Override
	public Quaternion mult(Quaternion p, Quaternion result) {
		
		if ((result != null) && (p != null)){
		  result.setComponents(scalar * p.getScalar() - i * p.getI()      - j * p.getJ()      - k * p.getK(),
			                   scalar * p.getI()      + i * p.getScalar() + j * p.getK()      - k * p.getJ(),
                               scalar * p.getJ()      - i * p.getK()      + j * p.getScalar() + k * p.getI(),
			                   scalar * p.getK()      + i * p.getJ()      - j * p.getI()      + k * p.getScalar());
		}
		
		return result;
	}
	
	@Override
	public Quaternion multAffect(Quaternion p) {
		return mult(p, this);
	}
	
	@Override
	public Quaternion invertQuaternion() throws IllegalStateException {
		double normSquare = normSquare();
		
		return new SimpleQuaternion(   scalar / normSquare,
				                    -1.0d * i / normSquare,
				                    -1.0d * j / normSquare,
				                    -1.0d * k / normSquare);
	}
	
	@Override
	public Quaternion invertQuaternion(Quaternion result) throws IllegalStateException {
		
		if (result != null) {
			
			double normSquare = normSquare();
			
			result.setComponents(scalar / normSquare,
				                 -1.0d * i / normSquare,
				                 -1.0d * j / normSquare,
				                 -1.0d * k / normSquare);
		}
		
		return result;
	}
	
	@Override
	public Quaternion invertQuaternionAffect() throws IllegalStateException {
		
		double normSquare = normSquare();
		
		scalar = scalar / normSquare;
        i      = -1.0d * i / normSquare;
        j      = -1.0d * j / normSquare;
        k      = -1.0d * k / normSquare;
		
		return this;
	}
	
	@Override
	public Quaternion conjugateQuaternion() {
		return new SimpleQuaternion(   scalar, -1.0d * i, -1.0d * j, -1.0d * k);
	}
	
	@Override
	public Quaternion conjugateQuaternion(Quaternion result) {
		if (result != null) {
			result.setComponents(scalar, -1.0d * i, -1.0d * j, -1.0d * k);
		}
		return result;
	}
	
	@Override
	public Quaternion conjugateQuaternionAffect() {
        i      = -1.0d * i;
        j      = -1.0d * j;
        k      = -1.0d * k;
		return this;
	}

	@Override
	public String toString() {
        String str = "";
		
		str += getScalar();
		
		if (getI() > 0) {
			str += " + "+getI();
		} else if (getI() < 0){
			str += " - "+Math.abs(getI());
		}
		
		if (getJ() > 0) {
			str += " + "+getJ();
		} else if (getJ() < 0){
			str += " - "+Math.abs(getJ());
		}
		
		if (getK() > 0) {
			str += " + "+getK();
		} else if (getK() < 0){
			str += " - "+Math.abs(getK());
		}
		
		return str;
	}
	
	/**
	 * Create a new simple quaternion equals to <i>0</i>
	 */
	public SimpleQuaternion() {
		this.scalar = 0.0d;
		this.i = 0.0d;
		this.j = 0.0d;
		this.k = 0.0d;
	}
	
	/**
	 * Create a new simple quaternion with the given components.
	 * @param scalar the quaternion real part (or scalar) parameter, denoted <i>a</i> within the definition .
	 * @param i the <i>i</i> basis component of the quaternion vector part (or imaginary part) parameter.
	 * @param j the <i>j</i> basis component of the quaternion vector part (or imaginary part) parameter.
	 * @param k the <i>k</i> basis component of the quaternion vector part (or imaginary part) parameter.
	 */
	public SimpleQuaternion(double scalar, double i, double j, double k) {
		this.scalar = scalar;
		this.i = i;
		this.j = j;
		this.k = k;
	}
	
	/**
	 * Create a new simple quaternion that is a copy of the given one.
	 * @param quaternion the quaternion to copy.
	 */
	public SimpleQuaternion(Quaternion quaternion) {
		if (quaternion != null) {
			this.scalar = quaternion.getScalar();
			this.i = quaternion.getI();
			this.j = quaternion.getJ();
			this.k = quaternion.getK();
		}
	}

}
