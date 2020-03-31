package org.jeometry.geom2D.point;

import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.geom2D.SpatialLocalization2D;
import org.jeometry.geom3D.point.Coord3D;
import org.jeometry.math.Vector;

/**
 * A simple implementation of the {@link Point2D} interface.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public class SimplePoint2D implements Point2D{

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

	@Override
	public int getDimension() {
		return 2;
	}
	
	@Override
	public double getVectorComponent(int dimension) {
		return coordinates[dimension];
	}

	@Override
	public void setVectorComponent(int dimension, double value) {
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
		return orthogonal(GeometryFactory.createPoint2D());
	}

	@Override
	public Coord2D orthogonal(Vector result) {

		result.setVectorComponent(Coord3D.DIMENSION_X,  getY()); 
		result.setVectorComponent(Coord3D.DIMENSION_Y, -getX()); 

		result.normalize();

		if (result instanceof Point2D) {
			return (Point2D)result;
		} else {
			return null;
		}
	}

	@Override
	public Vector multiply(double scalar) {
		return GeometryFactory.createPoint2D(getX()*scalar, getY()*scalar);
	}

	@Override
	public Vector multiply(double scalar, Vector result) throws IllegalArgumentException {
		if (result instanceof Point2D) {
			((Point2D)result).setX(getX()*scalar);
			((Point2D)result).setY(getY()*scalar);
		} else {
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

}
