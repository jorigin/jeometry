package org.jeometry.simple.geom2D.point;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.SpatialLocalization2D;
import org.jeometry.geom2D.point.Coord2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom3D.point.Coord3D;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

/**
 * A simple implementation of the {@link Point2D} interface.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimplePoint2D implements Point2D{

	/**
	 * The point coordinates.
	 */
	double[] coordinates;

	/**
	 * Create a new empty {@link Point2D}. with its coordinates initialized to <code>Double.Nan</code>
	 */
	public SimplePoint2D() {
		coordinates = new double[2];
		coordinates[DIMENSION_X] = Double.NaN;
		coordinates[DIMENSION_Y] = Double.NaN;
	}

	/**
	 * Create a new {@link Point2D} with the given coordinates. 
	 * @param x the x coordinate of the point.
	 * @param y the y coordinate of the point.
	 */
	public SimplePoint2D(double x, double y) {
		coordinates = new double[2];
		coordinates[DIMENSION_X] = x;
		coordinates[DIMENSION_Y] = y;
	}

	/**
	 * Create a new {@link Point2D} by copying the given point. 
	 * @param point the point to copy
	 */
	public SimplePoint2D(Point2D point) {
		coordinates = new double[2];
		coordinates[DIMENSION_X] = point.getX();
		coordinates[DIMENSION_Y] = point.getY();
	}

	@Override
	public void setValues(double x, double y) {
		setX(x);
		setY(y);
	}

	@Override
	public void setValues(Point2D point) {
		setX(point.getX());
		setY(point.getY());
	}

	@Override
	public int getDimension() {
		return 2;
	}

	@Override
	public double getValue(int dimension) {
		return coordinates[dimension];
	}

	@Override
	public void setValue(int dimension, double value) {
		coordinates[dimension] = value;
	}

	@Override
	public double normSquare() {
		return getX()*getX() + getY()*getY();
	}

	@Override
	public double norm() {
		return Math.sqrt(normSquare());
	}

	@Override
	public void normalize() {
		double norm = norm();
		coordinates[DIMENSION_X] = coordinates[DIMENSION_X] / norm;
		coordinates[DIMENSION_Y] = coordinates[DIMENSION_Y] / norm;
	}

	@Override
	public Coord2D orthogonal() {
		return orthogonal(JeometryFactory.createPoint2D());
	}

	@Override
	public Coord2D orthogonal(Vector result) {

		result.setValue(Coord3D.DIMENSION_X,  getY()); 
		result.setValue(Coord3D.DIMENSION_Y, -getX()); 

		result.normalize();

		if (result instanceof Point2D) {
			return (Point2D)result;
		} else {
			return null;
		}
	}

	@Override
	public Point2D multiply(double scalar) {
		return JeometryFactory.createPoint2D(getX()*scalar, getY()*scalar);
	}

	@Override
	public Vector multiply(double scalar, Vector result) throws IllegalArgumentException {
		if (result instanceof Point2D) {
			((Point2D)result).setX(getX()*scalar);
			((Point2D)result).setY(getY()*scalar);
		} else {
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
	public Point2D multiplyAffect(double scalar) {
		setX(getX()*scalar);
		setY(getY()*scalar);
		return this;
	}

	@Override
	public double getX() {
		return coordinates[DIMENSION_X];
	}

	@Override
	public void setX(double x) {
		coordinates[DIMENSION_X] = x;
	}

	@Override
	public double getY() {
		return coordinates[DIMENSION_Y];
	}

	@Override
	public void setY(double y) {
		coordinates[DIMENSION_Y] = y;
	}

	@Override
	public double getXMin() {
		return getX();
	}

	@Override
	public double getYMin() {
		return getY();
	}

	@Override
	public double getXMax() {
		return getX();
	}

	@Override
	public double getYMax() {
		return getY();
	}

	@Override
	public double distance(SpatialLocalization2D spatial) {

		if (spatial != null) {
			return Math.sqrt((spatial.getX() - getX())*(spatial.getX() - getX()) + (spatial.getY() - getY())*(spatial.getY() - getY()));
		}

		return Double.NaN;
	}

	@Override
	public void updateLocalization() {
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
	public double[] getValues() {
		return getValues(new double[getDimension()]);
	}

	@Override
	public double[] getValues(double[] components) {
		if (components == null) {
			throw new IllegalArgumentException("Null output array");
		}

		if (getDimension() != components.length) {
			throw new IllegalArgumentException("Invalid output array length "+components.length+", expected "+getDimension());
		}

		for(int dimension = 0; dimension < getDimension(); dimension++) {
			components[dimension] = getValue(dimension);
		}

		return components;
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
	public Point2D plus(Vector v) {
		return (Point2D)plus(v, JeometryFactory.createPoint2D());
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
	public Point2D plusAffect(Vector v) {
		return (SimplePoint2D) plus(v, this);
	}

	@Override
	public Point2D plus(double scalar) {
		return (Point2D)plus(scalar, JeometryFactory.createPoint2D());
	}

	@Override
	public Vector plus(double scalar, Vector result) {

		if (result != null) {

			if (getDimension() != result.getDimension()) {
				throw new IllegalArgumentException("Invalid result vector dimension "+result.getDimension()+", expected "+getDimension());
			}

			for(int dimension = 0; dimension < getDimension(); dimension++) {
				result.setValue(dimension, getValue(dimension) + scalar);
			}
		}

		return result;
	}

	@Override
	public Point2D plusAffect(double scalar) {
		return (SimplePoint2D) plus(scalar, this);
	}

	@Override
	public Point2D minus(Vector v) {
		return (Point2D) minus(v, JeometryFactory.createPoint2D());
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
	public SimplePoint2D minusAffect(Vector v) {
		return (SimplePoint2D) minus(v, this);
	}


	@Override
	public Vector minus(double scalar, Vector result) {


		if (result != null) {

			if (result.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid result vector dimension "+result.getDimension()+", expected "+getDimension());
			}

			for(int dimension = 0; dimension < getDimension(); dimension++) {
				result.setValue(dimension, getValue(dimension) - scalar);
			}
		}

		return result;
	}

	@Override
	public Point2D minus(double scalar) {
		return (SimplePoint2D) minus(scalar, JeometryFactory.createPoint2D());
	}

	@Override
	public Point2D minusAffect(double scalar) {
		return (SimplePoint2D) minus(scalar, this);
	}

	@Override
	public Point2D multiply(Vector v) {
		return (Point2D) multiply(v, JeometryFactory.createPoint2D());
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
	public SimplePoint2D multiplyAffect(Vector v) {
		return (SimplePoint2D) multiply(v, this);
	}

	@Override
	public Point2D divide(Vector v) {
		return (Point2D) divide(v, JeometryFactory.createPoint2D());
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
					result.setValue(dimension, getValue(dimension) * v.getValue(dimension));
				}
			}


		}

		return result;
	}

	@Override
	public SimplePoint2D divideAffect(Vector v) {
		return (SimplePoint2D) divide(v, this);
	}

	@Override
	public Vector divide(double scalar, Vector result) throws IllegalArgumentException {
		if (result instanceof Point2D) {
			((Point2D)result).setX(getX()/scalar);
			((Point2D)result).setY(getY()/scalar);
		} else {
			if (result.getDimension() >= getDimension()) {
				for(int dimension = 0; dimension < getDimension(); dimension++) {
					result.setValue(dimension, getValue(dimension)/scalar);
				}
			} else {
				throw new IllegalArgumentException("Invalid result vector dimension ("+result.getDimension()+"), expected at least "+getDimension());
			}
		}
		return result;
	}

	@Override
	public Point2D divide(double scalar) {
		return (SimplePoint2D) divide(scalar, JeometryFactory.createPoint2D());
	}

	@Override
	public Point2D divideAffect(double scalar) {
		return (SimplePoint2D) divide(scalar, this);
	}

	@Override
	public double dot(Vector v) {

		if (v != null) {
			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}

			double d = 0.0d;

			for(int dimension = 0; dimension < getDimension(); dimension++) {
				d = d + getValue(dimension) * v.getValue(dimension);
			}

			return d;
		}

		return Double.NaN;
	}

}
