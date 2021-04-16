package org.jeometry.simple.geom3D.point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;

/**
 * A {@link Point3DContainer Point 3D container implementation} that relies on an {@link ArrayList ArrayList}.
 * @param <T> the specific type of the {@link Point3D 3D points}
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class ArrayListPoint3DContainer<T extends Point3D> implements Cloneable, Point3DContainer<T> {

	/**
	 * The underlying list.
	 */
	private List<T> data = null;

	/**
	 * The type of the container.
	 */
	private int type    = Point3DContainer.DATA_COORDINATE | Point3DContainer.DATA_IDENTIFIER;

	/**
	 * The x coordinate of the mesh.
	 */
	private double x    = Double.NaN;

	/**
	 * The y coordinate of the mesh.
	 */
	private double y    = Double.NaN;

	/**
	 * The z coordinate of the mesh.
	 */
	private double z    = Double.NaN;

	/**
	 * The x min coordinate of the mesh.
	 */
	private double xmin = Double.NaN;

	/**
	 * The y min coordinate of the mesh.
	 */
	private double ymin = Double.NaN;

	/**
	 * The z min coordinate of the mesh.
	 */
	private double zmin = Double.NaN;

	/**
	 * The xmax coordinate of the mesh.
	 */
	private double xmax = Double.NaN;

	/**
	 * The y max coordinate of the mesh.
	 */
	private double ymax = Double.NaN;

	/**
	 * The z max coordinate of the mesh.
	 */
	private double zmax = Double.NaN;


	@Override
	public int getDataType() {
		return type;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public double getXMin() {
		return xmin;
	}

	@Override
	public double getYMin() {
		return ymin;
	}

	@Override
	public double getZMin() {
		return zmin;
	}

	@Override
	public double getXMax() {
		return xmax;
	}

	@Override
	public double getYMax() {
		return ymax;
	}

	@Override
	public double getZMax() {
		return zmax;
	}

	@Override
	public double distance(SpatialLocalization3D spatial) {
		return Math.sqrt(  (spatial.getX()-getX())*(spatial.getX()-getX())
				+ (spatial.getY()-getY())*(spatial.getY()-getY())
				+ (spatial.getZ()-getZ())*(spatial.getZ()-getZ()));
	}

	@Override
	public void updateLocalization(){

		x = 0.0d;
		y = 0.0d;
		z = 0.0d;

		xmin = Double.MAX_VALUE;
		ymin = Double.MAX_VALUE;
		zmin = Double.MAX_VALUE;

		xmax = -Double.MAX_VALUE;
		ymax = -Double.MAX_VALUE;
		zmax = -Double.MAX_VALUE;

		for(Point3D point3d : this){

			if (point3d.getX() > xmax){
				xmax = point3d.getX();
			}

			if (point3d.getX() < xmin){
				xmin = point3d.getX();
			}

			if (point3d.getY() > ymax){
				ymax = point3d.getY();
			}

			if (point3d.getY() < ymin){
				ymin = point3d.getY();
			}

			if (point3d.getZ() > zmax){
				zmax = point3d.getZ();
			}

			if (point3d.getZ() < zmin){
				zmin = point3d.getZ();
			}

			x = x + point3d.getX();
			y = y + point3d.getY();
			z = z + point3d.getZ();
		}

		x = x / size();
		y = y / size();
		z = z / size();
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean contains(Point3D point) {
		return data.contains(point);
	}

	@Override
	public Iterator<T> iterator() {
		return data.iterator();
	}

	@Override
	public boolean add(T point) {
		return data.add(point);
	}

	@Override
	public boolean remove(Point3D point) {
		return data.remove(point);
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public T get(int index) {
		return data.get(index);
	}

	@Override
	public Point3D set(int index, T point) {
		return data.set(index, point);
	}

	@Override
	public T remove(int index) {
		return data.remove(index);
	}

	@Override
	public int indexOf(Point3D point) {
		return data.indexOf(point);
	}

	@Override
	public int lastIndexOf(Point3D point) {
		return data.lastIndexOf(point);
	}

	/**
	 * Create a new {@link Point3DContainer Point 3D container implementation} that relies on an {@link ArrayList array list}.
	 * It is recommended to use a {@link JeometryFactory geometry factory} in order to create instances instead of invoking this constructor.
	 * @see #ArrayListPoint3DContainer(int)
	 */
	public ArrayListPoint3DContainer(){
		data = new ArrayList<T>();
	}

	/**
	 * Create a new {@link Point3DContainer Point 3D container implementation} that relies on an {@link ArrayList array list}. 
	 * The initial capacity of the point container is given by the parameter <code>size</code>.
	 * It is recommended to use a {@link JeometryFactory geometry factory} in order to create instances instead of invoking this constructor.
	 * @param size the initial capacity of the point container.
	 */
	public ArrayListPoint3DContainer(int size){
		data = new ArrayList<T>(size);
	}

}
