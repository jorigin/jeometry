package org.jeometry.geom3D.point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.geom3D.SpatialLocalization3D;

/**
 * A {@link Point3DContainer Point 3D container implementation} that relies on an {@link ArrayList ArrayList}.
 * @param <T> the specific type of the {@link Point3D 3D points}
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 */
public class ArrayListPoint3DContainer<T extends Point3D> implements Cloneable, Point3DContainer<T> {

  private List<T> data = null;
  
  private int type    = Point3DContainer.DATA_COORDINATE | Point3DContainer.DATA_IDENTIFIER;
  
  private double x    = Double.NaN;
  private double xMin = Double.NaN;
  private double xMax = Double.NaN;
  
  private double y    = Double.NaN;
  private double yMin = Double.NaN;
  private double yMax = Double.NaN;
  
  private double z    = Double.NaN;
  private double zMin = Double.NaN;
  private double zMax = Double.NaN;
  
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
    return Math.sqrt(  (spatial.getX()-getX())*(spatial.getX()-getX())
                     + (spatial.getY()-getY())*(spatial.getY()-getY())
                     + (spatial.getZ()-getZ())*(spatial.getZ()-getZ()));
  }

  @Override
  public void updateLocalization(){
    
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
   * It is recommended to use a {@link GeometryFactory geometry factory} in order to create instances instead of invoking this constructor.
   * @see #ArrayListPoint3DContainer(int)
   */
  public ArrayListPoint3DContainer(){
	  data = new ArrayList<T>();
  }
  
  /**
   * Create a new {@link Point3DContainer Point 3D container implementation} that relies on an {@link ArrayList array list}. 
   * The initial capacity of the point container is given by the parameter <code>size</code>.
   * It is recommended to use a {@link GeometryFactory geometry factory} in order to create instances instead of invoking this constructor.
   * @param size the initial capacity of the point container.
   */
  public ArrayListPoint3DContainer(int size){
    data = new ArrayList<T>(size);
  }

}
