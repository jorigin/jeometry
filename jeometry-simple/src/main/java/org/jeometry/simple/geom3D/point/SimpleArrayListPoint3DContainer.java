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
public class SimpleArrayListPoint3DContainer<T extends Point3D> implements Cloneable, Point3DContainer<T> {

	/**
	 * The underlying list.
	 */
	private List<T> data = null;

	/**
	 * The type of the container.
	 */
	private int type    = Point3DContainer.DATA_COORDINATE | Point3DContainer.DATA_IDENTIFIER;

	/**
	 * The localization of the mesh (the barycentre of its vertices)
	 */
	private Point3D loc = null;
	
	/**
	 * The maximal coordinates of the mesh.
	 */
	private Point3D max = null;
	
	/**
	 * The minimal coordinates of the mesh.
	 */
	private Point3D min = null;
	
	@Override
	public int getDataType() {
		return this.type;
	}

	@Override
	public double getX() {
		return this.loc.getX();
	}

	@Override
	public double getY() {
		return this.loc.getY();
	}

	@Override
	public double getZ() {
		return this.loc.getZ();
	}

	@Override
    public Point3D getMinCoordinates() {
		return JeometryFactory.createPoint3D(this.min.getX(), this.min.getY(), this.min.getY());
	}
    
	@Override
    public Point3D getMaxCoordinates() {
		return JeometryFactory.createPoint3D(this.max.getX(), this.max.getY(), this.max.getY());
	}
	
	@Override
	public double distance(SpatialLocalization3D spatial) {
		return Math.sqrt(  (spatial.getX()-getX())*(spatial.getX()-getX())
				+ (spatial.getY()-getY())*(spatial.getY()-getY())
				+ (spatial.getZ()-getZ())*(spatial.getZ()-getZ()));
	}

	@Override
	public void updateLocalization(){

		this.loc = JeometryFactory.createPoint3D(0.0d, .0d, 0.0d);

		this.max = JeometryFactory.createPoint3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);

		this.min = JeometryFactory.createPoint3D(-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE);
		
		for(Point3D point3d : this){

			if (point3d.getX() > this.max.getX()){
				this.max.setX(point3d.getX());
			}

			if (point3d.getX() < this.min.getX()){
				this.min.setX(point3d.getX());
			}

			if (point3d.getY() >this.max.getY()){
				this.max.setY(point3d.getY());
			}

			if (point3d.getY() < this.min.getY()){
				this.min.setY(point3d.getY());
			}

			if (point3d.getZ() > this.max.getZ()){
				this.max.setZ(point3d.getZ());
			}

			if (point3d.getZ() < this.min.getZ()){
				this.min.setZ(point3d.getZ());
			}
			
			this.loc.setX(this.loc.getX() + point3d.getX());
			this.loc.setY(this.loc.getY() + point3d.getY());
			this.loc.setZ(this.loc.getZ() + point3d.getZ());
		}

		this.loc.setX(this.loc.getX() / size());
		this.loc.setY(this.loc.getY() / size());
		this.loc.setZ(this.loc.getZ() / size());
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
	 * @see #SimpleArrayListPoint3DContainer(int)
	 */
	public SimpleArrayListPoint3DContainer(){
		this.data = new ArrayList<T>();
		
		this.loc = JeometryFactory.createPoint3D(Double.NaN, Double.NaN, Double.NaN);

		this.max = JeometryFactory.createPoint3D(Double.NaN, Double.NaN, Double.NaN);

		this.min = JeometryFactory.createPoint3D(Double.NaN, Double.NaN, Double.NaN);
	}

	/**
	 * Create a new {@link Point3DContainer Point 3D container implementation} that relies on an {@link ArrayList array list}. 
	 * The initial capacity of the point container is given by the parameter <code>size</code>.
	 * It is recommended to use a {@link JeometryFactory geometry factory} in order to create instances instead of invoking this constructor.
	 * @param size the initial capacity of the point container.
	 */
	public SimpleArrayListPoint3DContainer(int size){
		this.data = new ArrayList<T>(size);
	}

}
