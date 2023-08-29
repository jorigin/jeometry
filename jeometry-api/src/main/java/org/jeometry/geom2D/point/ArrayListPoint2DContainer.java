package org.jeometry.geom2D.point;

import java.util.ArrayList;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.SpatialLocalization2D;

/**
 * A {@link Point2DContainer Point 2D container implementation} that relies on an {@link ArrayList ArrayList}. 
 * Within this implementation, the ({@link #getX() x}, {@link #getY() y}) coordinates are the one from the barycentre of the container. 
 * The distance from this container to another spatial localization is computed relatively to the barycentre of this container.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class ArrayListPoint2DContainer extends ArrayList<Point2D> implements Cloneable, Point2DContainer{

  /**
   * 
   */
  private static final long serialVersionUID = Jeometry.BUILD;

  /**
   * The x coordinate of the container.
   */
  private double x    = Double.NaN;
  
  /**
   * The x min coordinate of the container.
   */
  private double xMin = Double.NaN;
  
  /**
   * The x max coordinate of the container.
   */
  private double xMax = Double.NaN;
  
  /**
   * The y coordinate of the container.
   */
  private double y    = Double.NaN;
  
  /**
   * The y min coordinate of the container.
   */
  private double yMin = Double.NaN;
  
  /**
   * The y max coordinate of the container.
   */
  private double yMax = Double.NaN;
  
  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public double getXMin() {
    return this.xMin;
  }

  @Override
  public double getYMin() {
    return this.yMin;
  }

  @Override
  public double getXMax() {
    return this.xMax;
  }

  @Override
  public double getYMax() {
    return this.yMax;
  }

  @Override
  public double distance(SpatialLocalization2D spatial) {
    return Math.sqrt(  (spatial.getX()-getX())*(spatial.getX()-getX())
        + (spatial.getY()-getY())*(spatial.getY()-getY()));
  }

  @Override
  public void updateLocalization() {
    this.x = 0.0d;
    this.y = 0.0d;
    
    this.xMin = Double.MAX_VALUE;
    this.yMin = Double.MAX_VALUE;
    
    this.xMax = -Double.MAX_VALUE;
    this.yMax = -Double.MAX_VALUE;
    
    for(Point2D point : this){
      
      if (point.getX() > this.xMax){
        this.xMax = point.getX();
      }
      
      if (point.getX() < this.xMin){
        this.xMin = point.getX();
      }
      
      if (point.getY() > this.yMax){
        this.yMax = point.getY();
      }
      
      if (point.getY() < this.yMin){
        this.yMin = point.getY();
      }
      
      
      this.x = this.x + point.getX();
      this.y = this.y + point.getY();
    }
    
    this.x = this.x / size();
    this.y = this.y / size();
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
