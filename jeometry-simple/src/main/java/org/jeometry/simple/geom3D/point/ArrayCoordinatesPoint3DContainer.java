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
	  
	  coordinates = new double[3*size];
    
	  states = new int[size];
    
	  identifiers = new int[size];
    
  }
  
  @Override
  public int getDataType() {
  	return type;
  }
  
  @Override
  public int size() {
    return size;
  }
 
  @Override
  public boolean isEmpty() {
    return (coordinates == null) || (coordinates.length) == 0;
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
	  return Math.sqrt(  (spatial.getX()-x)*(spatial.getX()-x)
                       + (spatial.getY()-y)*(spatial.getY()-y)
                       + (spatial.getZ()-z)*(spatial.getZ()-z));
  }

  @Override
  public void updateLocalization() {
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

}
