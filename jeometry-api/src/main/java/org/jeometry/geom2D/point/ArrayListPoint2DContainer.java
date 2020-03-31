package org.jeometry.geom2D.point;

import java.util.ArrayList;

import org.jeometry.Geometry;
import org.jeometry.geom2D.SpatialLocalization2D;

/**
 * A {@link Point2DContainer Point 2D container implementation} that relies on an {@link ArrayList ArrayList}. 
 * Within this implementation, the ({@link #getX() x}, {@link #getY() y}) coordinates are the one from the barycentre of the container. 
 * The distance from this container to another spatial localization is computed relatively to the barycentre of this container.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public class ArrayListPoint2DContainer extends ArrayList<Point2D> implements Cloneable, Point2DContainer{

  /**
   * 
   */
  private static final long serialVersionUID = Geometry.BUILD;

  private double x    = Double.NaN;
  private double xMin = Double.NaN;
  private double xMax = Double.NaN;
  
  private double y    = Double.NaN;
  private double yMin = Double.NaN;
  private double yMax = Double.NaN;
  
  @Override
  public double getX() {
    return x;
  }

  @Override
  public double getY() {
    return y;
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
  public double getXMax() {
    return xMax;
  }

  @Override
  public double getYMax() {
    return yMax;
  }

  @Override
  public double distance(SpatialLocalization2D spatial) {
    return Math.sqrt(  (spatial.getX()-getX())*(spatial.getX()-getX())
        + (spatial.getY()-getY())*(spatial.getY()-getY()));
  }

  @Override
  public void updateLocalization() {
    x = 0.0d;
    y = 0.0d;
    
    xMin = Double.MAX_VALUE;
    yMin = Double.MAX_VALUE;
    
    xMax = -Double.MAX_VALUE;
    yMax = -Double.MAX_VALUE;
    
    for(Point2D point : this){
      
      if (point.getX() > xMax){
        xMax = point.getX();
      }
      
      if (point.getX() < xMin){
        xMin = point.getX();
      }
      
      if (point.getY() > yMax){
        yMax = point.getY();
      }
      
      if (point.getY() < yMin){
        yMin = point.getY();
      }
      
      
      x = x + point.getX();
      y = y + point.getY();
    }
    
    x = x / size();
    y = y / size();
  }

  /**
   * Create a new empty 2D point container based on an {@link ArrayList}.
   */
  public ArrayListPoint2DContainer() {
    super();
  }
  
  /**
   * Create a new empty 2D point container based on an {@link ArrayList} and initialized to the given size.
   * @param size the size of the point container.
   */
  public ArrayListPoint2DContainer(int size) {
    super(size);
  }
}
