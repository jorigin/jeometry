package org.jeometry.simple.geom3D.point;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.point.Coord3D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

/**
 * A simple implementation of the {@link Point3D} interface. The coordinates of the point are stored as 3 variables with <code>double</code> type.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class SimplePoint3D implements Point3D{

	/**
	 * The coordinates.
	 */
	double[] coordinates;

	@Override
	public int getDimension() {
		return 3;
	}

	@Override
	public double getValue(int dimension) {

		if ((dimension >= 0)&&(dimension < 4)){
			return this.coordinates[dimension];
		} else {
			throw new IllegalArgumentException(getClass().getSimpleName()+": invalid dimension "+dimension);
		}


	}

	@Override
	public void setValue(int dimension, double value) {
		if ((dimension >= 0)&&(dimension < 4)){
			this.coordinates[dimension] = value;
		} else {
			throw new IllegalArgumentException(getClass().getSimpleName()+": invalid dimension "+dimension);
		}
	}

	@Override
	public double getX() {
		return this.coordinates[DIMENSION_X];
	}

	@Override
	public void setX(double x) {
		this.coordinates[DIMENSION_X] = x;
	}

	@Override
	public double getY() {
		return this.coordinates[DIMENSION_Y];
	}

	@Override
	public void setY(double y) {
		this.coordinates[DIMENSION_Y] = y;
	}

	@Override
	public double getZ() {
		return this.coordinates[DIMENSION_Z];
	}

	@Override
	public void setZ(double z) {
		this.coordinates[DIMENSION_Z] = z;
	}

	@Override
	public double getH() {
		return this.coordinates[DIMENSION_H];
	}

	@Override
	public void setH(double h) {
		this.coordinates[DIMENSION_H] = h;
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
	public double getZMin() {
		return getZ();
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
	public double getZMax() {
		return getZ();
	}

	@Override
	public double distance(SpatialLocalization3D spatial) {
		if (spatial != null){

			return Math.sqrt(  (spatial.getX() - getX())*(spatial.getX() - getX())
					+ (spatial.getY() - getY())*(spatial.getY() - getY())
					+ (spatial.getZ() - getZ())*(spatial.getZ() - getZ()));
		} else {
			return Double.NaN;
		}
	}

	@Override
	public void updateLocalization(){
		return;
	}

	/**
	 * Compute the <a href="https://en.wikipedia.org/wiki/Cross_product">cross product</a> between this point and the given one. 
	 * The cross product, denoted <i>x</i> is such that for two point 
	 * <i>a</i>&nbsp;=&nbsp;(<i>x<sub>a</sub></i>, <i>y<sub>a</sub></i>, <i>z<sub>a</sub></i>) 
	 * and 
	 * <i>b</i>&nbsp;=&nbsp;(<i>x<sub>b</sub></i>, <i>y<sub>b</sub></i>, <i>z<sub>b</sub></i>) 
	 * we have:<br>
	 * $$a\ \times\ b\ =\ ( y_a\ \times\ z_b\ -\ z_a\ \times\ y_b,\ -x_a\ \times\ z_b\ +\ z_a\ \times\ x_b,\ -x_a\ \times\ y_b\ -\ y_a\ \times\ x_b)$$
	 * The cross product result is a new {@link Point3D Point3D} that is instantiated by the {@link JeometryFactory geometry factory} using the {@link JeometryFactory#createPoint3D(double, double, double) createPoint3D() method}.
	 * @param point the second operand of the cross product.
	 * @return the cross product between this point and the given one.
	 */
	@Override
	public Point3D cross(Point3D point){
		return JeometryFactory.createPoint3D( getY() * point.getZ() - getZ() * point.getY(), 
				-getX() * point.getZ() + getZ() * point.getX(), 
				getX() * point.getY() - getY() * point.getX());
	}

	/**
	 * Compute the <a href="https://en.wikipedia.org/wiki/Cross_product">cross product</a> between this point and the given one. 
	 * The cross product, denoted <i>x</i> is such that for two point 
	 * <i>a</i>&nbsp;=&nbsp;(<i>x<sub>a</sub></i>, <i>y<sub>a</sub></i>, <i>z<sub>a</sub></i>) 
	 * and 
	 * <i>b</i>&nbsp;=&nbsp;(<i>x<sub>b</sub></i>, <i>y<sub>b</sub></i>, <i>z<sub>b</sub></i>) 
	 * we have:<br>
	 * $$a\ \times\ b\ =\ ( y_a\ \times\ z_b\ -\ z_a\ \times\ y_b,\ -x_a\ \times\ z_b\ +\ z_a\ \times\ x_b,\ -x_a\ \times\ y_b\ -\ y_a\ \times\ x_b)$$
	 * The result the cross product is stored within the given <code>result</code> {@link Point3D}. The return value of this method is a reference on the <code>result</code> parameter. 
	 * If the parameter is <code>null</code>, a new point is created and returned as the cross product result. The instantiation of the result is done by the {@link JeometryFactory geometry factory} using the {@link JeometryFactory#createPoint3D(double, double, double) createPoint3D() method}.
	 * @param point the second operand of the cross product.
	 * @param result the result of the cross product.
	 * @return the cross product between this point and the given one.
	 * @see #cross(Point3D, Point3D)
	 */
	@Override
	public Point3D cross(Point3D point, Point3D result) {
		if (result != null){
			result.setX(getY() * point.getZ() - getZ() * point.getY());
			result.setY(-getX() * point.getZ() + getZ() * point.getX());
			result.setZ(getX() * point.getY() - getY() * point.getX());
			return result;
		} else {
			return cross(point);
		}
	}

	@Override
	public double dot(Point3D point) {
		return getX()*point.getX() + getY()*point.getY() + getZ()*point.getZ();
	}

	@Override
	public Vector multiply(double scalar, Vector result) throws IllegalArgumentException {
		
		if (result != null) {
			result.setValues(new double[] {getX()*scalar, getY()*scalar, getZ()*scalar});
		}
		
		return result;
	}

	@Override
	public double norm() {
		return Math.sqrt(normSquare());
	}

	@Override
	public void normalize(){
		double norm = norm();

		setX(getX() / norm);
		setY(getY() / norm);
		setZ(getZ() / norm);
	}

	@Override
	public double normSquare() {
		return getX()*getX() + getY()*getY() + getZ()*getZ();
	}

	@Override
	public Point3D orthogonal() {
		return orthogonal(JeometryFactory.createPoint3D());
	}

	@Override
	public Point3D orthogonal(Vector result) {

		if (Math.abs(getX()) <= Math.abs(getY()) && Math.abs(getX()) <= Math.abs(getX())) {
			result.setValue(Coord3D.DIMENSION_X, 0); 
			result.setValue(Coord3D.DIMENSION_Y, getZ()); 
			result.setValue(Coord3D.DIMENSION_Z, -getY());
		} else if (Math.abs(getY()) <= Math.abs(getX()) && Math.abs(getY()) <= Math.abs(getZ())) {
			result.setValue(Coord3D.DIMENSION_X, -getZ()); 
			result.setValue(Coord3D.DIMENSION_Y, 0); 
			result.setValue(Coord3D.DIMENSION_Z, getX());
		} else {
			result.setValue(Coord3D.DIMENSION_X, getY()); 
			result.setValue(Coord3D.DIMENSION_Y, -getX()); 
			result.setValue(Coord3D.DIMENSION_Z, 0);
		}

		result.normalize();

		if (result instanceof Point3D) {
			return (Point3D)result;
		} else {
			return null;
		}
	}

	/**
	 * Compute the sum of the vector represented by this point and the vector represented by the given one and return it as a new point.
	 * Let <i>a</i> and <i>b</i> be two vector, their sum, denoted <i>a</i>&nbsp;+&nbsp;<i>b</i>, is a vector such that:<br>
	 * $$ a + b\ =\ (x_a + x_b,\ y_a + y_b,\ z_a + z_b)$$ 
	 * If this point has to be modified, the {@link #plusAffect(Point3D)} method can be used.<br><br>
	 * The returned object is a new {@link Point3D IPoint3D} that is instantiated by the {@link JeometryFactory geometry factory} using the {@link JeometryFactory#createPoint3D(double, double, double) createPoint3D(double, double, double)} method.
	 * @param point the point to sum with the current point.
	 * @return the sum of the vector represented by this point and the vector represented by the given one.
	 * @see #plus(Point3D, Point3D)
	 * @see #plus(double, Point3D)
	 * @see #plus(double)
	 */
	@Override
	public Point3D plus(Point3D point) {
		return JeometryFactory.createPoint3D(getX()+point.getX(), getY()+point.getY(), getZ()+point.getZ());
	}

	@Override
	public Point3D plus(Point3D point, Point3D result) {
		result.setX(getX()+point.getX());
		result.setY(getY()+point.getY());
		result.setZ(getZ()+point.getZ());
		return result;
	}

	/**
	 * Compute the sum of the vector represented by this point and the scalar given in parameter and return the result as a new point.
	 * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the sum, denoted <i>a</i>&nbsp;+&nbsp;<i>&lambda;</i>, is a vector such that:<br>
	 * $$ a + b\ =\ (x_a + x_b,\ y_a + y_b,\ z_a + z_b)$$ 
	 * If this point has to be modified, the {@link #plusAffect(double)} method can be used.<br><br>
	 * The returned object is a new {@link Point3D IPoint3D} that is instantiated by the {@link JeometryFactory geometry factory} using the {@link JeometryFactory#createPoint3D(double, double, double) createPoint3D(double, double, double)} method.
	 * @param scalar the scalar to sum.
	 * @return  the sum of the vector represented by this point and the given <code>scalar</code>.
	 * @see #plus(Point3D)
	 * @see #plus(Point3D, Point3D)
	 * @see #plus(double, Point3D)
	 */
	@Override
	public Point3D plus(double scalar) {
		return JeometryFactory.createPoint3D(getX()+scalar, getY()+scalar, getZ()+scalar);
	}

	@Override
	public Point3D plus(double scalar, Point3D result) {
		result.setX(getX()+scalar);
		result.setY(getY()+scalar);
		result.setZ(getZ()+scalar);
		return result;
	}

	@Override
	public Point3D plusAffect(Point3D point) {
		setX(getX() + point.getX());
		setY(getY() + point.getY());
		setZ(getZ() + point.getZ());
		return this;
	}

	@Override
	public SimplePoint3D plusAffect(double scalar) {
		setX(getX() + scalar);
		setY(getY() + scalar);
		setZ(getZ() + scalar);
		return this;
	}

	@Override
	public Vector plus(double scalar, Vector result) {
		
		if (result != null) {
			if (result.getDimension() != 3) {
				throw new IllegalArgumentException("Invalid result vector size "+result.getDimension()+", expected 3.");
			}
			
			result.setValue(0, getX()+scalar);
			result.setValue(1, getY()+scalar);
			result.setValue(2, getZ()+scalar);
		}
		
		return result;
	}
	
	/**
	 * Compute the difference between the vector represented by this point and the vector represented by the given one and return it as a new point.
	 * Let <i>a</i> and <i>b</i> be two vector, their difference, denoted <i>a</i>&nbsp;-&nbsp;<i>b</i>, is a vector such that:<br>
	 * $$ a - b\ =\ (x_a - x_b,\ y_a - y_b,\ z_a - z_b)$$ 
	 * If this point has to be modified, the {@link #minusAffect(Point3D)} method can be used.<br><br>
	 * The returned object is a new {@link Point3D IPoint3D} that is instantiated by the {@link JeometryFactory geometry factory} using the {@link JeometryFactory#createPoint3D(double, double, double) createPoint3D(double, double, double)} method.
	 * @param point the point to differentiate with the current point.
	 * @return the difference between the vector represented by this point and the vector represented by the given one.
	 * @see #minus(Point3D, Point3D)
	 * @see #minus(double, Point3D)
	 * @see #minus(double)
	 */
	@Override
	public Point3D minus(Point3D point) {
		return JeometryFactory.createPoint3D(getX()-point.getX(), getY()-point.getY(), getZ()-point.getZ());
	}

	@Override
	public Point3D minus(Point3D point, Point3D result) {
		result.setX(getX()-point.getX());
		result.setY(getY()-point.getY());
		result.setZ(getZ()-point.getZ());
		return result;
	}

	/**
	 * Compute the difference between the vector represented by this point and the scalar given in parameter and return the result as a new point.
	 * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the difference, denoted <i>a</i>&nbsp;-&nbsp;<i>&lambda;</i>, is a vector such that:<br>
	 * $$ a - b\ =\ (x_a - x_b,\ y_a - y_b,\ z_a - z_b)$$ 
	 * If this point has to be modified, the {@link #minusAffect(double)} method can be used.<br><br>
	 * The returned object is a new {@link Point3D IPoint3D} that is instantiated by the {@link JeometryFactory geometry factory} using the {@link JeometryFactory#createPoint3D(double, double, double) createPoint3D(double, double, double)} method.
	 * @param scalar the scalar to differentiate.
	 * @return the difference between the vector represented by this point and the given <code>scalar</code>.
	 * @see #minus(Point3D)
	 * @see #minus(Point3D, Point3D)
	 * @see #minus(double, Point3D)
	 */
	@Override
	public Point3D minus(double scalar) {
		return JeometryFactory.createPoint3D(getX()-scalar, getY()-scalar, getZ()-scalar);
	}

	@Override
	public Point3D minus(double scalar, Point3D result) {
		result.setX(getX()-scalar);
		result.setY(getY()-scalar);
		result.setZ(getZ()-scalar);
		return result;
	}
	
	@Override
	public SimplePoint3D minusAffect(Point3D point) {
		setX(getX() - point.getX());
		setY(getY() - point.getY());
		setZ(getZ() - point.getZ());
		return this;
	}

	@Override
	public SimplePoint3D minusAffect(double scalar) {
		setX(getX() - scalar);
		setY(getY() - scalar);
		setZ(getZ() - scalar);
		return this;
	}

	@Override
	public Point3D multiply(double scalar) {
		return JeometryFactory.createPoint3D(getX()*scalar, getY()*scalar, getZ()*scalar);
	}

	@Override
	public Point3D multiply(double scalar, Point3D result) {
		result.setX(getX()*scalar);
		result.setY(getY()*scalar);
		result.setZ(getZ()*scalar);
		return result;
	}

	@Override
	public SimplePoint3D multiplyAffect(double scalar) {
		setX(getX() * scalar);
		setY(getY() * scalar);
		setZ(getZ() * scalar);
		return this;
	}

	/**
	 * Compute the division of the vector represented by this point and the scalar given in parameter and return the result as a new point.
	 * Let <i>a</i> be a vector and let <i>&lambda;</i> be a scalar, the division, denoted <i>a</i>&nbsp;/&nbsp;<i>&lambda;</i>, is a vector such that:<br>
	 * $$ \frac{a}{\lambda}\ =\ (\frac{x}{\lambda},\ \frac{y}{\lambda},\ \frac{z}{\lambda})$$
	 * If this point has to be modified, the {@link #divideAffect(double)} method can be used.<br><br>
	 * The returned object is a new {@link Point3D IPoint3D} that is instantiated by the {@link JeometryFactory geometry factory} using the {@link JeometryFactory#createPoint3D(double, double, double) createPoint3D(double, double, double)} method.
	 * @param scalar the scalar to divide.
	 * @return the division of the vector represented by this point and the scalar given in parameter.
	 * @see #divide(double, Point3D)
	 */
	@Override
	public Point3D divide(double scalar) {
		return JeometryFactory.createPoint3D(getX()/scalar, getY()/scalar, getZ()/scalar);
	}

	@Override
	public Point3D divide(double scalar, Point3D result) {
		result.setX(getX()/scalar);
		result.setY(getY()/scalar);
		result.setZ(getZ()/scalar);
		return result;
	}

	@Override
	public SimplePoint3D divideAffect(double scalar) {
		setX(getX() / scalar);
		setY(getY() / scalar);
		setZ(getZ() / scalar);
		return this;
	}


	/**
	 * Create a new 3D point with all of its coordinates set to <code>Double.Nan</code>.<br>
	 * The direct instantiation of an {@link Point3D} implementation using its own constructors is not recommended. 
	 * Prefer using the {@link JeometryFactory geometry factory} to create instances.
	 * @see #SimplePoint3D(double, double, double)
	 */
	public SimplePoint3D(){
		this.coordinates = new double[3];
		setX(Double.NaN);
		setY(Double.NaN);
		setZ(Double.NaN);
	}

	/**
	 * Create a new 3D point with given coordinates.<br>
	 * The direct instantiation of an {@link Point3D} implementation using its own constructors is not recommended. Prefer using the {@link JeometryFactory geometry factory} to create instances.
	 * @param x the point coordinate along the <i>X</i> axis.
	 * @param y the point coordinate along the <i>Y</i> axis.
	 * @param z the point coordinate along the <i>Z</i> axis.
	 * @see #SimplePoint3D()
	 */
	public SimplePoint3D(double x, double y, double z){
		this.coordinates = new double[3];
		setX(x);
		setY(y);
		setZ(z);
	}

	/**
	 * Create a new 3D point by copying the point given in parameter.<br>
	 * The direct instantiation of an {@link Point3D} implementation using its own constructors is not recommended. Prefer using the {@link JeometryFactory geometry factory} to create instances.
	 * @param point the point to copy
	 * @see #SimplePoint3D()
	 */
	public SimplePoint3D(Point3D point){
		this.coordinates = new double[3];
		setX(point.getX());
		setY(point.getY());
		setZ(point.getZ());
	}
	
	@Override
	public void setValues(double x, double y, double z) {
		setX(x);
		setY(y);
		setZ(z);
	}

	@Override
	public void setValues(Point3D point) {
		setX(point.getX());
		setY(point.getY());
		setZ(point.getZ());
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
	public Point3D plus(Vector v) {
		return (Point3D) plus(v, JeometryFactory.createPoint3D());
	}

	@Override
	public Vector plus(Vector v, Vector result) {
		if (v != null) {

			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}

			if (result != null) {

				if (v.getDimension() != getDimension()) {
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
	public SimplePoint3D plusAffect(Vector v) {
		if (v != null) {
			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}

			for(int dimension = 0; dimension < getDimension(); dimension++) {
				setValue(dimension, getValue(dimension) + v.getValue(dimension));
			}
		}

		return this;
	}


	@Override
	public Vector minus(Vector v) {
		return (Point3D) minus(v, JeometryFactory.createPoint3D());
	}

	@Override
	public Vector minus(Vector v, Vector result) {
		if (v != null) {

			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}

			if (result != null) {

				if (v.getDimension() != getDimension()) {
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
	public SimplePoint3D minusAffect(Vector v) {
		return (SimplePoint3D) minus(v, this);
	}

	@Override
	public Vector minus(double scalar, Vector result) {
		if (result != null) {
			if (result.getDimension() != 3) {
				throw new IllegalArgumentException("Invalid result vector size "+result.getDimension()+", expected 3.");
			}
			
			result.setValue(0, getX()-scalar);
			result.setValue(1, getY()-scalar);
			result.setValue(2, getZ()-scalar);
		}
		
		return result;
	}
	
	@Override
	public Point3D multiply(Vector v) {
		return (Point3D) multiply(v, JeometryFactory.createPoint3D());
	}

	@Override
	public Vector multiply(Vector v, Vector result) {
		if (v != null) {

			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}

			if (result != null) {

				if (v.getDimension() != getDimension()) {
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
	public SimplePoint3D multiplyAffect(Vector v) {
		return (SimplePoint3D) multiply(v, this);
	}

	@Override
	public Point3D divide(Vector v) {
		return (Point3D) divide(v, JeometryFactory.createPoint3D());
	}

	@Override
	public Vector divide(Vector v, Vector result) {
		if (v != null) {

			if (v.getDimension() != getDimension()) {
				throw new IllegalArgumentException("Invalid input vector dimension "+v.getDimension()+", expected "+getDimension());
			}

			if (result != null) {

				if (v.getDimension() != getDimension()) {
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
	public SimplePoint3D divideAffect(Vector v) {
		return (SimplePoint3D) divide(v, this);
	}

	@Override
	public Vector divide(double scalar, Vector result) throws IllegalArgumentException {
		if (result != null) {
			if (result.getDimension() != 3) {
				throw new IllegalArgumentException("Invalid result vector size "+result.getDimension()+", expected 3.");
			}
			
			result.setValue(0, getX()/scalar);
			result.setValue(1, getY()/scalar);
			result.setValue(2, getZ()/scalar);
		}
		
		return result;
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
