package org.jeometry.geom3D.algorithm.delaunay.clarkson;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.point.Point3D;
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
	public double getVectorComponent(int dimension) {
		return point.getVectorComponent(dimension);
	}

	@Override
	public void setVectorComponent(int dimension, double value) {
		point.setVectorComponent(dimension, value);
	}

	@Override
	public Vector multiply(double scalar) {
		return point.multiply(scalar);
	}

	@Override
	public Vector multiply(double scalar, Vector result) throws IllegalArgumentException {
		return point.multiply(scalar, result);
	}

	@Override
	public Vector multiplyAffect(double scalar) {
		point.multiplyAffect(scalar);
		return this;
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
	public Point3D mult(double scalar) {
		return point.mult(scalar);
	}

	@Override
	public Point3D mult(double scalar, Point3D result) {
		return point.mult(scalar, result);
	}

	@Override
	public Point3D multAffect(double scalar) {
		point.multAffect(scalar);
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
	public void setComponents(Vector v) {
		point.setComponents(v);
	}

	@Override
	public double[] getComponents() {
		return point.getComponents();
	}

	@Override
	public double[] getComponents(double[] components) {
		return point.getComponents(components);
	}

	@Override
	public void setComponents(double[] components) {
		point.setComponents(components);
	}
	
	@Override
	public Vector extract(int start, int length) {
		return point.extract(start, length);
	}
}
