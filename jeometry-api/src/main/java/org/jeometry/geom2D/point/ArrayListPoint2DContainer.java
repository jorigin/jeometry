package org.jeometry.geom2D.point;

import java.util.ArrayList;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
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
   * The y coordinate of the container.
   */
  private double y    = Double.NaN;
  
  /**
   * The minimal coordinates of the points within the container.
   */
  private Point2D minCoordinates = null;
  
  /**
   * The maximal coordinates of the points within the container.
   */
  private Point2D maxCoordinates = null;
  
  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public Point2D getMinCoordinates() {
	  return this.minCoordinates;
  }
  
  @Override
  public Point2D getMaxCoordinates() {
	  return this.maxCoordinates;
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
    
    this.minCoordinates = JeometryFactory.createPoint2D(Double.MAX_VALUE, Double.MAX_VALUE);
    
    this.maxCoordinates = JeometryFactory.createPoint2D(-Double.MAX_VALUE, -Double.MAX_VALUE);
    
    
    for(Point2D point : this){
      
      if (point.getX() > this.maxCoordinates.getX()){
        this.maxCoordinates.setX(point.getX());
      }
      
      if (point.getX() < this.minCoordinates.getX()){
        this.minCoordinates.setX(point.getX());
      }
      
      if (point.getY() > this.maxCoordinates.getY()){
        this.maxCoordinates.setY(point.getY());
      }
      
      if (point.getY() < this.minCoordinates.getY()){
        this.minCoordinates.setY(point.getY());
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
