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
		return this.type;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public double getZ() {
		return this.z;
	}

	@Override
	public double getXMin() {
		return this.xmin;
	}

	@Override
	public double getYMin() {
		return this.ymin;
	}

	@Override
	public double getZMin() {
		return this.zmin;
	}

	@Override
	public double getXMax() {
		return this.xmax;
	}

	@Override
	public double getYMax() {
		return this.ymax;
	}

	@Override
	public double getZMax() {
		return this.zmax;
	}

	@Override
	public double distance(SpatialLocalization3D spatial) {
		return Math.sqrt(  (spatial.getX()-getX())*(spatial.getX()-getX())
				+ (spatial.getY()-getY())*(spatial.getY()-getY())
				+ (spatial.getZ()-getZ())*(spatial.getZ()-getZ()));
	}

	@Override
	public void updateLocalization(){

		this.x = 0.0d;
		this.y = 0.0d;
		this.z = 0.0d;

		this.xmin = Double.MAX_VALUE;
		this.ymin = Double.MAX_VALUE;
		this.zmin = Double.MAX_VALUE;

		this.xmax = -Double.MAX_VALUE;
		this.ymax = -Double.MAX_VALUE;
		this.zmax = -Double.MAX_VALUE;

		for(Point3D point3d : this){

			if (point3d.getX() > this.xmax){
				this.xmax = point3d.getX();
			}

			if (point3d.getX() < this.xmin){
				this.xmin = point3d.getX();
			}

			if (point3d.getY() > this.ymax){
				this.ymax = point3d.getY();
			}

			if (point3d.getY() < this.ymin){
				this.ymin = point3d.getY();
			}

			if (point3d.getZ() > this.zmax){
				this.zmax = point3d.getZ();
			}

			if (point3d.getZ() < this.zmin){
				this.zmin = point3d.getZ();
			}

			this.x = this.x + point3d.getX();
			this.y = this.y + point3d.getY();
			this.z = this.z + point3d.getZ();
		}

		this.x = this.x / size();
		this.y = this.y / size();
		this.z = this.z / size();
	}

	@Override
	public int size() {
		return this.data.size();
	}

	@Override
	public boolean isEmpty() {
		return this.data.isEmpty();
	}

	@Override
	public boolean contains(Point3D point) {
		return this.data.contains(point);
	}

	@Override
	public Iterator<T> iterator() {
		return this.data.iterator();
	}

	@Override
	public boolean add(T point) {
		return this.data.add(point);
	}

	@Override
	public boolean remove(Point3D point) {
		return this.data.remove(point);
	}

	@Override
	public void clear() {
		this.data.clear();
	}

	@Override
	public T get(int index) {
		return this.data.get(index);
	}

	@Override
	public Point3D set(int index, T point) {
		return this.data.set(index, point);
	}

	@Override
	public T remove(int index) {
		return this.data.remove(index);
	}

	@Override
	public int indexOf(Point3D point) {
		return this.data.indexOf(point);
	}

	@Override
	public int lastIndexOf(Point3D point) {
		return this.data.lastIndexOf(point);
	}

	/**
	 * Create a new {@link Point3DContainer Point 3D container implementation} that relies on an {@link ArrayList array list}.
	 * It is recommended to use a {@link JeometryFactory geometry factory} in order to create instances instead of invoking this constructor.
	 * @see #ArrayListPoint3DContainer(int)
	 */
	public ArrayListPoint3DContainer(){
		this.data = new ArrayList<T>();
	}

	/**
	 * Create a new {@link Point3DContainer Point 3D container implementation} that relies on an {@link ArrayList array list}. 
	 * The initial capacity of the point container is given by the parameter <code>size</code>.
	 * It is recommended to use a {@link JeometryFactory geometry factory} in order to create instances instead of invoking this constructor.
	 * @param size the initial capacity of the point container.
	 */
	public ArrayListPoint3DContainer(int size){
		this.data = new ArrayList<T>(size);
	}

}
