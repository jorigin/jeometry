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

	/**
	 * The linked tetrahedra.
	 */
	Collection<DelaunayTetrahedron<T>> tetrahedrons = null;

	/**
	 * The lined faces.
	 */
	Collection<IndexedFace<T>> faces               = null;

	/**
	 * The underlying point.
	 */
	private Point3D point = null;

	/**
	 * The user properties.
	 */
	private Map<String, Object> userProperties   = null;

	/**
	 * Create a new vertex.
	 */
	public DelaunayVertice(){
		this.point = JeometryFactory.createPoint3D();
	}

	/**
	 * Create a new vertex.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	public DelaunayVertice(double x, double y, double z) {
		this.point = JeometryFactory.createPoint3D(x, y, z);
	}

	@Override
	public double getX() {
		return this.point.getX();
	}

	@Override
	public void setX(double x) {
		this.point.setX(x);
	}

	@Override
	public double getY() {
		return this.point.getY();
	}

	@Override
	public void setY(double y) {
		this.point.setY(y);
	}

	@Override
	public double getZ() {
		return this.point.getZ();
	}

	@Override
	public void setZ(double z) {
		this.point.setZ(z);
	}

	@Override
	public double getH() {
		return this.point.getH();
	}

	@Override
	public void setH(double h) {
		this.point.setH(h);
	}

	@Override
	public double norm() {
		return this.point.norm();
	}

	@Override
	public double normSquare() {
		return this.point.normSquare();
	}

	@Override
	public void normalize() {
		this.point.normalize();
	}

	@Override
	public int getDimension() {
		return this.point.getDimension();
	}

	@Override
	public double getValue(int dimension) {
		return this.point.getValue(dimension);
	}

	@Override
	public void setValue(int dimension, double value) {
		this.point.setValue(dimension, value);
	}

	@Override
	public Vector multiply(double scalar, Vector result) throws IllegalArgumentException {
		return this.point.multiply(scalar, result);
	}

	@Override
	public double getXMin() {
		return this.point.getXMin();
	}

	@Override
	public double getYMin() {
		return this.point.getYMin();
	}

	@Override
	public double getZMin() {
		return this.point.getZMin();
	}

	@Override
	public double getXMax() {
		return this.point.getXMax();
	}

	@Override
	public double getYMax() {
		return this.point.getYMax();
	}

	@Override
	public double getZMax() {
		return this.point.getZMax();
	}

	@Override
	public double distance(SpatialLocalization3D spatial) {
		return this.point.distance(spatial);
	}

	@Override
	public void updateLocalization() {
		this.point.updateLocalization();
	}

	@Override
	public Point3D orthogonal() {
		return this.point.orthogonal();
	}

	@Override
	public Point3D orthogonal(Vector result) {
		return this.point.orthogonal(result);
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
		return this.point.plus(scalar);
	}

	@Override
	public Vector plus(double scalar, Vector result) {
		return this.point.plus(scalar, result);
	}
	
	@Override
	public Point3D plus(double scalar, Point3D result) {
		return this.point.plus(scalar, result);
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
		return this.point.minus(scalar);
	}

	@Override
	public Vector minus(double scalar, Vector result) {
		return this.point.minus(scalar, result);
	}
	
	@Override
	public Point3D minus(double scalar, Point3D result) {
		return this.point.minus(scalar, result);
	}

	@Override
	public Point3D plusAffect(Point3D point) {
		point.plusAffect(point);
		return this;
	}

	@Override
	public Point3D plusAffect(double scalar) {
		this.point.plusAffect(scalar);
		return this;
	}

	@Override
	public Point3D minusAffect(Point3D point) {
		point.minusAffect(point);
		return this;
	}

	@Override
	public Point3D minusAffect(double scalar) {
		this.point.minusAffect(scalar);
		return this;
	}

	@Override
	public Point3D multiply(double scalar) {
		return this.point.multiply(scalar);
	}

	@Override
	public Point3D multiply(double scalar, Point3D result) {
		return this.point.multiply(scalar, result);
	}

	@Override
	public Point3D multiplyAffect(double scalar) {
		this.point.multiplyAffect(scalar);
		return this;
	}

	@Override
	public Point3D divide(double scalar) {
		return this.point.divide(scalar);
	}

	@Override
	public Vector divide(double scalar, Vector result) throws IllegalArgumentException {
		return this.point.divide(scalar, result);
	}
	
	@Override
	public Point3D divide(double scalar, Point3D result) {
		return this.point.divide(scalar, result);
	}

	@Override
	public Point3D divideAffect(double scalar) {
		this.point.divideAffect(scalar);
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
				this.userProperties = new HashMap<String, Object>();
			}

			this.userProperties.put(key, value);

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
		return this.userProperties;
	}

	/**
	 * Attach this point to the given {@link DelaunayTetrahedron tetrahedron}.
	 * @param tetrahedron the tetrahedron attached to this point.
	 * @return <code>true</code> if this point is successfully attached to the tetrahedron and <code>false</code> otherwise.
	 */
	public boolean addTetrahedron(DelaunayTetrahedron<T> tetrahedron){
		if (this.tetrahedrons == null){
			this.tetrahedrons = new LinkedList<DelaunayTetrahedron<T>>();
		}

		return this.tetrahedrons.add(tetrahedron);
	}

	/**
	 * Attach this point to the given {@link IndexedFace mesh face}.
	 * @param face the face to attach to this point.
	 * @return <code>true</code> if this point is successfully attached to the face and <code>false</code> otherwise.
	 */
	public boolean addFace(IndexedFace<T> face){
		if (this.faces == null){
			this.faces = new LinkedList<IndexedFace<T>>();
		}

		return this.faces.add(face);
	}

	@Override
	public void setValues(Vector v) {
		this.point.setValues(v);
	}

	@Override
	public double[] getValues() {
		return this.point.getValues();
	}

	@Override
	public double[] getValues(double[] components) {
		return this.point.getValues(components);
	}

	@Override
	public void setValues(double[] components) {
		this.point.setValues(components);
	}

	@Override
	public Vector extract(int start, int length) {
		return this.point.extract(start, length);
	}

	@Override
	public void setValues(double value) {
		this.point.setValues(value);
	}

	@Override
	public void setValues(Matrix matrix) {
		this.point.setValues(matrix);
	}

	@Override
	public Vector plus(Vector v) {
		return this.point.plus(v);
	}

	@Override
	public Vector plus(Vector v, Vector result) {
		return this.point.plus(v, result);
	}

	@Override
	public DelaunayVertice<T> plusAffect(Vector v) {
		this.point.plusAffect(v);
		return this;
	}

	@Override
	public Vector minus(Vector v) {
		return this.point.minus(v);
	}

	@Override
	public Vector minus(Vector v, Vector result) {
		return this.point.minus(v, result);
	}

	@Override
	public DelaunayVertice<T> minusAffect(Vector v) {
		this.point.minusAffect(v);
		return this;
	}

	@Override
	public Vector multiply(Vector v) {
		return this.point.multiply(v);
	}

	@Override
	public Vector multiply(Vector v, Vector result) {
		return this.point.multiply(v, result);
	}

	@Override
	public Vector multiplyAffect(Vector v) {
		this.point.multiplyAffect(v);
		return this;
	}

	@Override
	public Vector divide(Vector v) {
		return this.point.divide(v);
	}

	@Override
	public Vector divide(Vector v, Vector result) {
		return this.point.divide(v);
	}

	@Override
	public Vector divideAffect(Vector v) {
		this.point.divideAffect(v);
		return this;
	}

	@Override
	public double dot(Vector v) {
		return this.point.dot(v);
	}

	@Override
	public void setValues(double x, double y, double z) {
		this.point.setValues(x, y, z);
	}

	@Override
	public void setValues(Point3D point) {
		point.setValues(point);
	}
}
