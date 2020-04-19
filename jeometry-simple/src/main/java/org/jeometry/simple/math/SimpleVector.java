package org.jeometry.simple.math;

import org.jeometry.Jeometry;
import org.jeometry.math.Vector;

/**
 * A simple implementation of the {@link Vector} interface. Underlying data are stored within an array of <code>double</code>.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleVector implements Vector {

	double[] components = null;
	
	@Override
	public int getDimension() {
		if (components != null) {
			return components.length;
		}
		return 0;
	}
	
	@Override
	public double getVectorComponent(int dimension) {
		return components[dimension];
	}

	@Override
	public void setVectorComponent(int dimension, double value) {
		 components[dimension] = value;;
	}

	@Override
	public double normSquare() {
		
		if (components != null) {
			double square = 0.0d;
			for(int i = 0; i < components.length; i++) {
				square = square + components[i]*components[i];
			}
			
			return square;
			
		} else {
			return Double.NaN;
		}
	}

	@Override
	public double norm() {
		return Math.sqrt(normSquare());
	}

	@Override
	public void normalize() {
		if (components != null) {
			double norm = norm();
			for(int i = 0; i < components.length; i++) {
				components[i] = components[i] / norm;
			}
		}
	}

	@Override
	public Vector orthogonal() {
		//TODO implements SimpleVector orthogonal() method
		throw new UnsupportedOperationException("Method orthogonal() not yet implemented.");
	}

	@Override
	public Vector orthogonal(Vector result) {
		//TODO implements SimpleVector orthogonal(Vector) method
		throw new UnsupportedOperationException("Method orthogonal(Vector) not yet implemented.");
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
	
	/**
	 * Create a new vector with the given dimensions.
	 * @param dimensions the dimensions of the vector.
	 */
	public SimpleVector(int dimensions) {
		components = new double[dimensions];
	}

	/**
	 * Create a new vector from the given values. 
	 * The values are used as reference and are not copied.
	 * @param values the values that represents the components of the vector.
	 */
	public SimpleVector(double[] values) {
        components = values;
	}

	/**
	 * Create a new vector by copying the given source. 
	 * @param source the source vector to copy
	 */
	public SimpleVector(Vector source) {
		components = new double[source.getDimension()];
		
		for(int dimension = 0; dimension < source.getDimension(); dimension++) {
			components[dimension] = source.getVectorComponent(dimension);
		}
	}
}
