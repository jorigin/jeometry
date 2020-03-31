package org.jeometry.simple.factory;

import org.jeometry.Geometry;
import org.jeometry.factory.PointBuilder;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom2D.point.SimplePoint2D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.math.Vector;
import org.jeometry.simple.geom3D.point.SimplePoint3D;
import org.jeometry.simple.math.SimpleVector;

/**
 * A {@link PointBuilder point builder} implementation that provide simples pure java implementations.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public class SimplePointBuilder implements PointBuilder {

	@Override
	public Vector createPoint(int dimensions, double defaultValue) {
		Vector simpleVector = new SimpleVector(dimensions);
		
		for(int dimension = 0; dimension < simpleVector.getDimension(); dimension++) {
			simpleVector.setVectorComponent(dimension, defaultValue);
		}
		
		return simpleVector;
	}

	@Override
	public Point2D createPoint2D() {
		return new SimplePoint2D();
	}

	@Override
	public Point2D createPoint2D(double x, double y) {
		return new SimplePoint2D(x, y);
	}

	@Override
	public Point2DContainer createPoint2DContainer() {
        // TODO
		return null;
	}

	@Override
	public Point2DContainer createPoint2DContainer(int capacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point3D createPoint3D() {
		return new SimplePoint3D();
	}

	@Override
	public Point3D createPoint3D(double x, double y, double z) {
		return new SimplePoint3D(x, y, z);
	}

	@Override
	public <T extends Point3D> Point3DContainer<T> createPoint3DContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Point3D> Point3DContainer<T> createPoint3DContainer(int capacity) {
		// TODO Auto-generated method stub
		return null;
	}

}
