package org.jeometry.simple.math;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
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
	public void setComponents(Vector v) {
		if (v == null) {
			throw new IllegalArgumentException("Null input vector");
		}
		
		if (getDimension() != v.getDimension()) {
			throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
		}
		
		for(int dimension = 0; dimension < getDimension(); dimension++) {
			setVectorComponent(dimension, v.getVectorComponent(dimension));
		}
	}
	
	@Override
	public double[] getComponents() {
		return getComponents(new double[getDimension()]);
	}
	  
	@Override
	public double[] getComponents(double[] components) {
		if (components == null) {
			throw new IllegalArgumentException("Null output array");
		}
		
		if (getDimension() != components.length) {
			throw new IllegalArgumentException("Invalid output array length "+components.length+", expected "+getDimension());
		}
		
		for(int dimension = 0; dimension < getDimension(); dimension++) {
			components[dimension] = getVectorComponent(dimension);
		}
		
		return components;
	}
	
	@Override
	  public void setComponents(double[] components) {
		if (components == null) {
			throw new IllegalArgumentException("Null input array");
		}
		
		if (getDimension() != components.length) {
			throw new IllegalArgumentException("Invalid input components length "+components.length+", expected "+getDimension());
		}
		
		for(int dimension = 0; dimension < getDimension(); dimension++) {
			setVectorComponent(dimension, components[dimension]);
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
			extracted.setVectorComponent(dimension, getVectorComponent(dimension+start));
		}
		
		return extracted;
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
	 * Create a new vector from the given values (values are copied). 
	 * @param values the values that represents the components of the vector.
	 */
	public SimpleVector(double[] values) {
        components = new double[values.length];
        
        for(int i = 0; i < values.length; i++) {
        	components[i] = values[i];
        }
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
