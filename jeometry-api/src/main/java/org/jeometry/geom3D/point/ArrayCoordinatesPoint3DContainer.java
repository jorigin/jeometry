package org.jeometry.geom3D.point;

import java.util.Iterator;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.SpatialLocalization3D;

/**
 * A Point3D container that is based on an array of coordinates.
 * @param <T> the specific type of the {@link Point3D 3D points}
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class ArrayCoordinatesPoint3DContainer<T extends Point3D> implements Cloneable, Point3DContainer<T> {

  private int type               = 0;
	
  private int size               = 0;
	
  private int[] states           = null;
	
  private int[] identifiers      = null;
  
  private double[] coordinates = null;
  
  private double x    = Double.NaN;
  private double xMin = Double.NaN;
  private double xMax = Double.NaN;
  
  private double y    = Double.NaN;
  private double yMin = Double.NaN;
  private double yMax = Double.NaN;
  
  private double z    = Double.NaN;
  private double zMin = Double.NaN;
  private double zMax = Double.NaN;
  
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
    if (coordinates != null) {
      return coordinates.length / 3;
    }
    return 0;
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
    return xMin;
  }

  @Override
  public double getYMin() {
    return yMin;
  }

  @Override
  public double getZMin() {
    return zMin;
  }

  @Override
  public double getXMax() {
    return xMax;
  }

  @Override
  public double getYMax() {
    return yMax;
  }

  @Override
  public double getZMax() {
    return zMax;
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
	    
	    xMin = Double.MAX_VALUE;
	    yMin = Double.MAX_VALUE;
	    zMin = Double.MAX_VALUE;
	    
	    xMax = -Double.MAX_VALUE;
	    yMax = -Double.MAX_VALUE;
	    zMax = -Double.MAX_VALUE;
	    
	    for(Point3D point3d : this){
	      
	      if (point3d.getX() > xMax){
	        xMax = point3d.getX();
	      }
	      
	      if (point3d.getX() < xMin){
	        xMin = point3d.getX();
	      }
	      
	      if (point3d.getY() > yMax){
	        yMax = point3d.getY();
	      }
	      
	      if (point3d.getY() < yMin){
	        yMin = point3d.getY();
	      }
	      
	      if (point3d.getZ() > zMax){
	        zMax = point3d.getZ();
	      }
	      
	      if (point3d.getZ() < zMin){
	        zMin = point3d.getZ();
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
