package org.jeometry.geom3D.algorithm.delaunay.clarkson;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;


/**
 * An implementation of {@link Point3D} than embeed delaunay computation needed informations.
 * @param <T> The type of underlying 3D points 
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.0
 */
public class DelaunayVertice<T extends Point3D> implements Point3D{

	Collection<DelaunayTetrahedron<T>> tetrahedrons = null;

	Collection<IndexedFace<T>> faces               = null;

	private Point3D point = null;

	private Map<String, Object> userProperties   = null;

	/**
	 * Create a new vertex.
	 */
	public DelaunayVertice(){
		point = JeometryFactory.createPoint3D();
	}

	/**
	 * Create a new vertex.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	public DelaunayVertice(double x, double y, double z) {
		point = JeometryFactory.createPoint3D(x, y, z);
	}

	@Override
	public double getX() {
		return point.getX();
	}

	@Override
	public void setX(double x) {
		point.setX(x);
	}

	@Override
	public double getY() {
		return point.getY();
	}

	@Override
	public void setY(double y) {
		point.setY(y);
	}

	@Override
	public double getZ() {
		return point.getZ();
	}

	@Override
	public void setZ(double z) {
		point.setZ(z);
	}

	@Override
	public double getH() {
		return point.getH();
	}

	@Override
	public void setH(double h) {
		point.setH(h);
	}

	@Override
	public double norm() {
		return point.norm();
	}

	@Override
	public double normSquare() {
		return point.normSquare();
	}

	@Override
	public void normalize() {
		point.normalize();
	}

	@Override
	public int getDimension() {
		return point.getDimension();
	}

	@Override
	public double getValue(int dimension) {
		return point.getValue(dimension);
	}

	@Override
	public void setValue(int dimension, double value) {
		point.setValue(dimension, value);
	}

	@Override
	public Vector multiply(double scalar, Vector result) throws IllegalArgumentException {
		return point.multiply(scalar, result);
	}

	@Override
	public double getXMin() {
		return point.getXMin();
	}

	@Override
	public double getYMin() {
		return point.getYMin();
	}

	@Override
	public double getZMin() {
		return point.getZMin();
	}

	@Override
	public double getXMax() {
		return point.getXMax();
	}

	@Override
	public double getYMax() {
		return point.getYMax();
	}

	@Override
	public double getZMax() {
		return point.getZMax();
	}

	@Override
	public double distance(SpatialLocalization3D spatial) {
		return point.distance(spatial);
	}

	@Override
	public void updateLocalization() {
		point.updateLocalization();
	}

	@Override
	public Point3D orthogonal() {
		return point.orthogonal();
	}

	@Override
	public Point3D orthogonal(Vector result) {
		return point.orthogonal(result);
	}

	@Override
	public Point3D cross(Point3D point) {
		return point.cross(point);
	}

	@Override
	public Point3D cross(Point3D point, Point3D result) {
		return point.cross(point, result);
	}

	@Override
	public double dot(Point3D point) {
		return point.dot(point);
	}

	@Override
	public Point3D plus(Point3D point) {
		return point.plus(point);
	}

	@Override
	public Point3D plus(Point3D point, Point3D result) {
		return point.plus(point, result);
	}

	@Override
	public Point3D plus(double scalar) {
		return point.plus(scalar);
	}

	@Override
	public Point3D plus(double scalar, Point3D result) {
		return point.plus(scalar, result);
	}

	@Override
	public Point3D minus(Point3D point) {
		return point.minus(point);
	}

	@Override
	public Point3D minus(Point3D point, Point3D result) {
		return point.minus(point, result);
	}

	@Override
	public Point3D minus(double scalar) {
		return point.minus(scalar);
	}

	@Override
	public Point3D minus(double scalar, Point3D result) {
		return point.minus(scalar, result);
	}

	@Override
	public Point3D plusAffect(Point3D point) {
		point.plusAffect(point);
		return this;
	}

	@Override
	public Point3D plusAffect(double scalar) {
		point.plusAffect(scalar);
		return this;
	}

	@Override
	public Point3D minusAffect(Point3D point) {
		point.minusAffect(point);
		return this;
	}

	@Override
	public Point3D minusAffect(double scalar) {
		point.minusAffect(scalar);
		return this;
	}

	@Override
	public Point3D multiply(double scalar) {
		return point.multiply(scalar);
	}

	@Override
	public Point3D multiply(double scalar, Point3D result) {
		return point.multiply(scalar, result);
	}

	@Override
	public Point3D multiplyAffect(double scalar) {
		point.multiplyAffect(scalar);
		return this;
	}

	@Override
	public Point3D divide(double scalar) {
		return point.divide(scalar);
	}

	@Override
	public Point3D divide(double scalar, Point3D result) {
		return point.divide(scalar, result);
	}

	@Override
	public Point3D divideAffect(double scalar) {
		point.divideAffect(scalar);
		return this;
	}

	/**
	 * Set a user property for this point.
	 * @param key the key of the property.
	 * @param value the value of the property.
	 */
	public void setUserProperty(String key, Object value){
		if (value != null){

			if (getUserProperties() == null){
				userProperties = new HashMap<String, Object>();
			}

			userProperties.put(key, value);

		} else {
			if (getUserProperties() != null){
				getUserProperties().put(key, value);
			}
		}
	}

	/**
	 * Get the user properties attached to this point.
	 * @return the user properties attached to this point.
	 */
	public Map<String, Object> getUserProperties(){
		return userProperties;
	}

	/**
	 * Attach this point to the given {@link DelaunayTetrahedron tetrahedron}.
	 * @param tetrahedron the tetrahedron attached to this point.
	 * @return <code>true</code> if this point is successfully attached to the tetrahedron and <code>false</code> otherwise.
	 */
	public boolean addTetrahedron(DelaunayTetrahedron<T> tetrahedron){
		if (tetrahedrons == null){
			tetrahedrons = new LinkedList<DelaunayTetrahedron<T>>();
		}

		return tetrahedrons.add(tetrahedron);
	}

	/**
	 * Attach this point to the given {@link IndexedFace mesh face}.
	 * @param face the face to attach to this point.
	 * @return <code>true</code> if this point is successfully attached to the face and <code>false</code> otherwise.
	 */
	public boolean addFace(IndexedFace<T> face){
		if (faces == null){
			faces = new LinkedList<IndexedFace<T>>();
		}

		return faces.add(face);
	}

	@Override
	public void setValues(Vector v) {
		point.setValues(v);
	}

	@Override
	public double[] getValues() {
		return point.getValues();
	}

	@Override
	public double[] getValues(double[] components) {
		return point.getValues(components);
	}

	@Override
	public void setValues(double[] components) {
		point.setValues(components);
	}

	@Override
	public Vector extract(int start, int length) {
		return point.extract(start, length);
	}

	@Override
	public void setValues(double value) {
		point.setValues(value);
	}

	@Override
	public void setValues(Matrix matrix) {
		point.setValues(matrix);
	}

	@Override
	public Vector plus(Vector v) {
		return point.plus(v);
	}

	@Override
	public Vector plus(Vector v, Vector result) {
		return point.plus(v, result);
	}

	@Override
	public DelaunayVertice<T> plusAffect(Vector v) {
		point.plusAffect(v);
		return this;
	}

	@Override
	public Vector minus(Vector v) {
		return point.minus(v);
	}

	@Override
	public Vector minus(Vector v, Vector result) {
		return point.minus(v, result);
	}

	@Override
	public DelaunayVertice<T> minusAffect(Vector v) {
		point.minusAffect(v);
		return this;
	}

	@Override
	public Vector multiply(Vector v) {
		return point.multiply(v);
	}

	@Override
	public Vector multiply(Vector v, Vector result) {
		return point.multiply(v, result);
	}

	@Override
	public Vector multiplyAffect(Vector v) {
		point.multiplyAffect(v);
		return this;
	}

	@Override
	public Vector divide(Vector v) {
		return point.divide(v);
	}

	@Override
	public Vector divide(Vector v, Vector result) {
		return point.divide(v);
	}

	@Override
	public Vector divideAffect(Vector v) {
		point.divideAffect(v);
		return this;
	}

	@Override
	public double dot(Vector v) {
		return point.dot(v);
	}

	@Override
	public void setValues(double x, double y, double z) {
		point.setValues(x, y, z);
	}

	@Override
	public void setValues(Point3D point) {
		point.setValues(point);
	}
}
