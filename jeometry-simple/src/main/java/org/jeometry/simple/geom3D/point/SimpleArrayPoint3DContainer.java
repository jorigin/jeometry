package org.jeometry.simple.geom3D.point;

import java.util.Iterator;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.SpatialLocalization3D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;

/**
 * A Point3D container that is based on an array of coordinates.
 * @param <T> the specific type of the {@link Point3D 3D points}
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class ArrayCoordinatesPoint3DContainer<T extends Point3D> implements Cloneable, Point3DContainer<T> {

  /**
   * The data type.
   * The returned value can be a combination (using logical "or") of:
   * <ul>
   * <li>{@link #DATA_IDENTIFIER} if the points stored within the container can provide identifiers.
   * <li>{@link #DATA_COORDINATE} if the points stored within the container can provide coordinates.
   * <li>{@link #DATA_COLOR} if the points stored within the container can provide color component (RGBA).
   * <li>{@link #DATA_NORMAL} if the points stored within the container can provide normal.
   * </ul> 
   * Specific data types can be added.
   */
  private int type               = 0;
	
  /**
   * The size of the point container.
   */
  private int size               = 0;
	
  /**
   * The states of the points.
   */
  private int[] states           = null;
	
  /**
   * The identifiers of the points.
   */
  private int[] identifiers      = null;
  
  /**
   * The coordinates.
   */
  private double[] coordinates = null;
  
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
	
  
  /**
   * Create a new {@link Point3DContainer Point 3D container implementation} that relies on an array of coordinates. 
   * The initial capacity of the point container is given by the parameter <code>size</code>.
   * It is recommended to use a {@link JeometryFactory geometry factory} in order to create instances instead of invoking this constructor.
   * @param size the initial capacity of the point container.
   */
  public ArrayCoordinatesPoint3DContainer(int size){
	  
	  this.size = size;
	  
	  this.coordinates = new double[3*size];
    
	  this.states = new int[size];
    
	  this.identifiers = new int[size];
    
  }
  
  @Override
  public int getDataType() {
  	return this.type;
  }
  
  @Override
  public int size() {
    return this.size;
  }
 
  @Override
  public boolean isEmpty() {
    return (this.coordinates == null) || (this.coordinates.length) == 0;
  }

  @Override
  public Iterator<T> iterator() {
    // TODO implements ArrayCoordinatesPoint3DContainer.iterator()
    return null;
  }

  @Override
  public boolean add(Point3D e) {
    // TODO implements ArrayCoordinatesPoint3DContainer.add(Point3D)
    return false;
  }

  @Override
  public boolean remove(Point3D point) {
	// TODO implements ArrayCoordinatesPoint3DContainer.remove(Point3D)
  	return false;
  }
  
  @Override
  public void clear() {
	// TODO implements ArrayCoordinatesPoint3DContainer.clear()
    
  }

  @Override
  public T get(int index) {
	// TODO implements ArrayCoordinatesPoint3DContainer.get(int)
    return null;
  }

  @Override
  public Point3D set(int index, Point3D element) {
	// TODO implements ArrayCoordinatesPoint3DContainer.set(int, Point3D)
    return null;
  }

  @Override
  public T remove(int index) {
	// TODO implements ArrayCoordinatesPoint3DContainer.remove(Point3D)
    return null;
  }

  @Override
  public boolean contains(Point3D point) {
	// TODO implements ArrayCoordinatesPoint3DContainer.contains(Point3D)
  	return false;
  }

  @Override
  public int indexOf(Point3D point) {
	// TODO implements ArrayCoordinatesPoint3DContainer.indexOf(Point3D)
  	return 0;
  }

  @Override
  public int lastIndexOf(Point3D point) {
	// TODO implements ArrayCoordinatesPoint3DContainer.lastIndexOf(Point3D)
  	return 0;
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
	  return Math.sqrt(  (spatial.getX()-this.x)*(spatial.getX()-this.x)
                       + (spatial.getY()-this.y)*(spatial.getY()-this.y)
                       + (spatial.getZ()-this.z)*(spatial.getZ()-this.z));
  }

  @Override
  public void updateLocalization() {
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

}
