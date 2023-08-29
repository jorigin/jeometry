package org.jeometry.simple.math;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Quaternion;
import org.jeometry.math.Vector;

/**
 * This class is a simple implementation of the {@link Quaternion} interface.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 *
 */
public class SimpleQuaternion implements Quaternion {

	/**
	 * The scalar component.
	 */
	private double scalar = Double.NaN;
	
	/**
	 * The i component.
	 */
	private double i      = Double.NaN;
	
	/**
	 * The j component.
	 */
	private double j      = Double.NaN;
	
	/**
	 * The k component.
	 */
	private double k      = Double.NaN;

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
	
	@Override
	public int getDimension() {
		return 4;
	}

	@Override
	public double getValue(int dimension) {

		if ((dimension >= 0) && (dimension < 4)) {
			if (dimension == Quaternion.QUATERNION_SCALAR_COMPONENT) {
				return this.scalar;
			} else if (dimension == Quaternion.QUATERNION_I_COMPONENT) {
				return this.i;
			} else if (dimension == Quaternion.QUATERNION_J_COMPONENT) {
				return this.j;
			} else if (dimension == Quaternion.QUATERNION_K_COMPONENT) {
				return this.k;
			} else {
				throw new IllegalArgumentException("Invalid dimension "+dimension+". Expected 0 to 3");
			}
		} else {
			throw new IllegalArgumentException("Invalid dimension "+dimension+". Expected 0 to 3");
		}
	}

	@Override
	public void setValue(int dimension, double value) {
		if ((dimension >= 0) && (dimension < 4)) {

			if (dimension == Quaternion.QUATERNION_SCALAR_COMPONENT) {
				this.scalar = value;;
			} else if (dimension == Quaternion.QUATERNION_I_COMPONENT) {
				this.i = value;
			} else if (dimension == Quaternion.QUATERNION_J_COMPONENT) {
				this.j = value;
			} else if (dimension == Quaternion.QUATERNION_K_COMPONENT) {
				this.k = value;
			} else {
				throw new IllegalArgumentException("Invalid dimension "+dimension+". Expected 0 to 3");
			}

		} else {
			throw new IllegalArgumentException("Invalid dimension "+dimension+". Expected 0 to 3");
		}

	}

	@Override
	public void setValues(Vector v) {
		if (v == null) {
			throw new IllegalArgumentException("Null input vector");
		}

		if (getDimension() != v.getDimension()) {
			throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
		}

		for(int dimension = 0; dimension < getDimension(); dimension++) {
			setValue(dimension, v.getValue(dimension));
		}
	}

	@Override
	public void setValues(double[] components) {
		if (components == null) {
			throw new IllegalArgumentException("Null input array");
		}

		if (getDimension() != components.length) {
			throw new IllegalArgumentException("Invalid input components length "+components.length+", expected "+getDimension());
		}

		for(int dimension = 0; dimension < getDimension(); dimension++) {
			setValue(dimension, components[dimension]);
		}
	}

	@Override
	public Vector extract(int start, int length) {
		Vector extracted = null;
		
		if ((start < 0) || (start >= getDimension())) {
			throw new IllegalArgumentException("Invalid first index "+start+", expected values within [0, "+(getDimension() - 1)+"]");
		}
		
		if ((length < 1) || (length > getDimension() - start)) {
			throw new IllegalArgumentException("Invalid length "+start+", expected values within [0, "+(getDimension() - start)+"[");
		}
		
		extracted = JeometryFactory.createVector(length);
		
		for(int dimension = 0; dimension < extracted.getDimension(); dimension++) {
			extracted.setValue(dimension, getValue(dimension+start));
		}
		
		return extracted;
	}
	
	@Override
	public double normSquare() {
		return this.scalar*this.scalar + this.i*this.i + this.j*this.j + this.k*this.k;
	}

	@Override
	public double norm() {
		return Math.sqrt(normSquare());
	}

	@Override
	public void normalize() {
		double norm = norm();

		this.scalar = this.scalar / norm;
		this.i = this.i / norm;
		this.j = this.j / norm;
		this.k = this.k / norm;
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
	public double getScalar() {
		return this.scalar;
	}

	@Override
	public void setScalar(double value) {
		this.scalar = value;
	}

	@Override
	public double getI() {
		return this.i;
	}

	@Override
	public void setI(double value) {
		this.i = value;
	}

	@Override
	public double getJ() {
		return this.j;
	}

	@Override
	public void setJ(double value) {
		this.j = value;
	}

	@Override
	public double getK() {
		return this.k;
	}

	@Override
	public void setK(double value) {
		this.k = value;
	}

	@Override
	public double[] getValues() {
		return new double[] {this.scalar, this.i, this.j, this.k};
	}

	@Override
	public double[] getValues(double[] components) throws IllegalArgumentException {
		if (components != null) {
			if (components.length >= 4) {
				components[org.jeometry.math.Quaternion.QUATERNION_SCALAR_COMPONENT] = this.scalar;
				components[org.jeometry.math.Quaternion.QUATERNION_I_COMPONENT] = this.i;
				components[org.jeometry.math.Quaternion.QUATERNION_J_COMPONENT] = this.j;
				components[org.jeometry.math.Quaternion.QUATERNION_K_COMPONENT] = this.k;
			} else {
				throw new IllegalArgumentException("Invalid components length "+components.length+". Expected at least 4");
			}
		}

		return components;
	}

	@Override
	public void setComponents(double a, double b, double c, double d) {
		this.scalar = a;
		this.i      = b;
		this.j      = c;
		this.k      = d;
	}

	@Override
	public void setValues(double value) {
		for(int dimension = 0; dimension < getDimension(); dimension++) {
			setValue(dimension, value);
		}
	}

	@Override
	public void setValues(Matrix matrix) {
		if (matrix == null) {
			throw new IllegalArgumentException("Null input.");
		}

		int min = -1;
		int max = -1;

		boolean columnMatrix = false;

		if (matrix.getRowsCount() < matrix.getColumnsCount()) {
			min = matrix.getRowsCount();
			max = matrix.getColumnsCount();
			columnMatrix = true;
		} else {
			min = matrix.getColumnsCount();
			max = matrix.getRowsCount();
			columnMatrix = false;
		}

		if (min != 1) {
			throw new IllegalArgumentException("Matrix "+matrix.getRowsCount()+"x"+ matrix.getColumnsCount()+" cannot be set to vector.");
		}

		if (max != getDimension()) {
			throw new IllegalArgumentException("Matrix "+matrix.getRowsCount()+"x"+ matrix.getColumnsCount()+" cannot be set to a "+getDimension()+" vector");
		}

		if (columnMatrix) {
			for(int dimension = 0; dimension < matrix.getColumnsCount(); dimension++) {
				setValue(dimension, matrix.getValue(0, dimension));
			}
		} else {
			for(int dimension = 0; dimension < matrix.getRowsCount(); dimension++) {
				setValue(dimension, matrix.getValue(dimension, 0));	
			}
		}

	}
	
	@Override
	public Quaternion mult(Quaternion p) {
		return mult(p, new SimpleQuaternion());
	}

	@Override
	public Quaternion mult(Quaternion p, Quaternion result) {

		if ((result != null) && (p != null)){
			result.setComponents(this.scalar * p.getScalar() - this.i * p.getI()      - this.j * p.getJ()      - this.k * p.getK(),
					this.scalar * p.getI()      + this.i * p.getScalar() + this.j * p.getK()      - this.k * p.getJ(),
					this.scalar * p.getJ()      - this.i * p.getK()      + this.j * p.getScalar() + this.k * p.getI(),
					this.scalar * p.getK()      + this.i * p.getJ()      - this.j * p.getI()      + this.k * p.getScalar());
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

		return new SimpleQuaternion(   this.scalar / normSquare,
				-1.0d * this.i / normSquare,
				-1.0d * this.j / normSquare,
				-1.0d * this.k / normSquare);
	}

	@Override
	public Quaternion invertQuaternion(Quaternion result) throws IllegalStateException {

		if (result != null) {

			double normSquare = normSquare();

			result.setComponents(this.scalar / normSquare,
					-1.0d * this.i / normSquare,
					-1.0d * this.j / normSquare,
					-1.0d * this.k / normSquare);
		}

		return result;
	}

	@Override
	public Quaternion invertQuaternionAffect() throws IllegalStateException {

		double normSquare = normSquare();

		this.scalar = this.scalar / normSquare;
		this.i      = -1.0d * this.i / normSquare;
		this.j      = -1.0d * this.j / normSquare;
		this.k      = -1.0d * this.k / normSquare;

		return this;
	}

	@Override
	public Quaternion conjugateQuaternion() {
		return new SimpleQuaternion(   this.scalar, -1.0d * this.i, -1.0d * this.j, -1.0d * this.k);
	}

	@Override
	public Quaternion conjugateQuaternion(Quaternion result) {
		if (result != null) {
			result.setComponents(this.scalar, -1.0d * this.i, -1.0d * this.j, -1.0d * this.k);
		}
		return result;
	}

	@Override
	public Quaternion conjugateQuaternionAffect() {
		this.i      = -1.0d * this.i;
		this.j      = -1.0d * this.j;
		this.k      = -1.0d * this.k;
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

	@Override
	public Quaternion plus(Vector v) {
		return (Quaternion)plus(v, JeometryFactory.createQuaternion());
	}

	@Override
	public Vector plus(Vector v, Vector result) {
		if (v != null) {

			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}

			if (result != null) {

				if (result.getDimension() != getDimension()) {
					throw new IllegalArgumentException("Invalid result vector dimension "+result.getDimension()+", expected "+getDimension());
				}

				for(int dimension = 0; dimension < getDimension(); dimension++) {
					result.setValue(dimension, getValue(dimension) + v.getValue(dimension));
				}
			}


		}

		return result;
	}

	@Override
	public Quaternion plusAffect(Vector v) {
		return (Quaternion) plus(v, this);
	}
	
	@Override
	public Vector plus(double scalar, Vector result) {
		if (result != null) {
			if (result.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid result dimension "+result.getDimension()+", expected "+getDimension());
			}
			
			for(int dimension = 0; dimension < getDimension(); dimension++) {
				result.setValue(dimension, getValue(dimension)+scalar);
			}
		}
		return result;
	}

	@Override
	public Quaternion plus(double scalar, Quaternion result) {
		
		if (result != null) {
			result.setI(getI()+scalar);
			result.setJ(getI()+scalar);
			result.setK(getI()+scalar);
			result.setScalar(getScalar()+scalar);
		}
		
		return result;
	}
	
	@Override
	public Quaternion plus(double scalar) {
		return plus(scalar, JeometryFactory.createQuaternion());
	}

	@Override
	public Quaternion plusAffect(double scalar) {
		return plus(scalar, this);
	}

	@Override
	public Vector minus(Vector v) {
		return minus(v, JeometryFactory.createVector(getDimension()));
	}

	@Override
	public Vector minus(Vector v, Vector result) {
		if (v != null) {

			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}

			if (result != null) {

				if (result.getDimension() != getDimension()) {
					throw new IllegalArgumentException("Invalid result vector dimension "+result.getDimension()+", expected "+getDimension());
				}

				for(int dimension = 0; dimension < getDimension(); dimension++) {
					result.setValue(dimension, getValue(dimension) - v.getValue(dimension));
				}
			}


		}

		return result;
	}

	@Override
	public Vector minus(double scalar, Vector result) {
		if (result != null) {
			if (result.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid result dimension "+result.getDimension()+", expected "+getDimension());
			}
			
			for(int dimension = 0; dimension < getDimension(); dimension++) {
				result.setValue(dimension, getValue(dimension) - scalar);
			}
		}
		return result;
	}
	
	@Override
	public Quaternion minus(double scalar, Quaternion result) {
		if (result != null) {
			result.setI(getI()-scalar);
			result.setJ(getI()-scalar);
			result.setK(getI()-scalar);
			result.setScalar(getScalar()-scalar);

		}
		
		return result;
	}
	
	@Override
	public Quaternion minus(double scalar) {
		return minus(scalar, JeometryFactory.createQuaternion());
	}

	@Override
	public Quaternion minusAffect(Vector v) {
		return (Quaternion) minus(v, this);
	}

	@Override
	public Quaternion minusAffect(double scalar) {
		return minus(scalar, this);
	}

	@Override
	public Vector multiply(Vector v) {
		return multiply(v, JeometryFactory.createVector(getDimension()));
	}

	@Override
	public Vector multiply(Vector v, Vector result) {
		if (v != null) {

			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}

			if (result != null) {

				if (result.getDimension() != getDimension()) {
					throw new IllegalArgumentException("Invalid result vector dimension "+result.getDimension()+", expected "+getDimension());
				}

				for(int dimension = 0; dimension < getDimension(); dimension++) {
					result.setValue(dimension, getValue(dimension) * v.getValue(dimension));
				}
			}
		}

		return result;
	}
	
	@Override
	public Quaternion multiply(double scalar, Quaternion result) {
		if (result != null) {
			result.setI(getI()*scalar);
			result.setJ(getI()*scalar);
			result.setK(getI()*scalar);
			result.setScalar(getScalar()*scalar);
		}
		
		return result;
	}
	
	@Override
	public Vector multiply(double scalar) {
		return multiply(scalar, JeometryFactory.createVector(getDimension()));
	}

	@Override
	public Vector multiply(double scalar, Vector result) throws IllegalArgumentException {
		if (result != null) {
			if (result.getDimension() >= getDimension()) {
				for(int dimension = 0; dimension < getDimension(); dimension++) {
					result.setValue(dimension, getValue(dimension)*scalar);
				}
			} else {
				throw new IllegalArgumentException("Invalid result vector dimension ("+result.getDimension()+"), expected at least "+getDimension());
			}
		}

		return result;
	}
	
	@Override
	public Quaternion multiplyAffect(Vector v) {
		return (Quaternion) multiply(v, this);
	}

	@Override
	public Quaternion multiplyAffect(double scalar) {
		for(int dimension = 0; dimension < getDimension(); dimension++) {
			setValue(dimension, getValue(dimension)*scalar);
		}
		return this;
	}
	
	@Override
	public Vector divide(Vector v) {
		return divide(v, JeometryFactory.createVector(getDimension()));
	}

	@Override
	public Vector divide(Vector v, Vector result) {
		if (v != null) {

			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}

			if (result != null) {

				if (result.getDimension() != getDimension()) {
					throw new IllegalArgumentException("Invalid result vector dimension "+result.getDimension()+", expected "+getDimension());
				}

				for(int dimension = 0; dimension < getDimension(); dimension++) {
					result.setValue(dimension, getValue(dimension) / v.getValue(dimension));
				}
			}


		}

		return result;
	}

	@Override
	public Quaternion divideAffect(Vector v) {
		return (Quaternion) divide(v, this);
	}
	
	@Override
	public Vector divide(double scalar, Vector result) throws IllegalArgumentException {
		if (result != null) {
			if (result.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid result dimension "+result.getDimension()+", expected "+getDimension());
			}
			
			for(int dimension = 0; dimension < getDimension(); dimension++) {
				result.setValue(dimension, getValue(dimension) / scalar);
			}
		}
		return result;
	}

	@Override
	public Quaternion divide(double scalar, Quaternion result) {
		if (result != null) {
			result.setI(getI()/scalar);
			result.setJ(getI()/scalar);
			result.setK(getI()/scalar);
			result.setScalar(getScalar()/scalar);
		}
		
		return result;
	}

	@Override
	public Quaternion divide(double scalar) {
		return divide(scalar, JeometryFactory.createQuaternion());
	}

	@Override
	public Quaternion divideAffect(double scalar) {
		return divide(scalar, this);
	}
	
	@Override
	public double dot(Vector v) {
		
		if (v != null) {
			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}
			
			double d = 0.0d;
			
			for(int dimension = 0; dimension < getDimension(); dimension++) {
				d = d + dimension + getValue(dimension) * v.getValue(dimension);
			}
			
			return d;
		}
		
		return Double.NaN;
	}

	
}
