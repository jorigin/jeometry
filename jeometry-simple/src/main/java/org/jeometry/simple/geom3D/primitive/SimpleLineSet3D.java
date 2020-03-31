package org.jeometry.simple.geom3D.primitive;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Line3D;
import org.jeometry.geom3D.primitive.LineSet3D;

/**
 * A simple implementation of the {@link LineSet3D} interface.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 *
 */
public class SimpleLineSet3D<T extends Point3D> implements LineSet3D<T>{
  
  private static final long serialVersionUID = Geometry.BUILD;

  /** The points of lines composing the line set. The order of the points is very important as it's
   * specify how the lines are created
   */
  private Point3DContainer<T> points;
  
  /**
   * The segment representing each line of the line set.
   */
  private ArrayList<Line3D<T>> segments;
  
  @Override
  public Point3DContainer<T> getVertices(){
    return points;
  }

  /**
   * Set the {@link Point3DContainer 3D points} that compose this line set. Every successive pair of points compose a line.
   * @param points {@link Point3DContainer 3D points} that compose this line set.
   */
  public void setVertices(Point3DContainer<T> points){
    this.points = points;
    
    segments = new ArrayList<Line3D<T>>();
    
    if ((points != null) && (points.size() > 1)){
      for(int i = 0; i < points.size() - 1; i++){
        segments.add(new SimpleLine3D<T>(points.get(i), points.get(i+1)));
      }
    }
  }
  
  @Override
  public List<Line3D<T>> getSegments(){
    return segments;
  }
  
  @Override
  public void plot(T point) {
	  
	int lastPointIndex = points.size() - 1;
	
    points.add(point);

    if (lastPointIndex >= 0) {
    	segments.add(new SimpleLine3D<T>(points.get(lastPointIndex), points.get(lastPointIndex + 1)));
    }
  }
  
  @Override
  public String toString(){
    String str  = "";
    Point3D pt = null;
    
    if ((points == null) || (points.size() < 1)){
      str = getClass().getSimpleName()+" [ No vertex ]";
    } else {
      str = getClass().getSimpleName()+" ("+points.size()+" points) [";
      
      for(int i = 0; i < points.size()- 1; i++){
	pt = points.get(i);
	str +=" ("+pt.getX()+", "+pt.getY()+", "+pt.getZ()+"),";
      }
      
      pt = points.get(points.size() - 1);
      str +=" ("+pt.getX()+", "+pt.getY()+", "+pt.getZ()+") ]";
    }
    
    return str;
  }

  
  /**
   * Construct a new line set from a {@link Point3DContainer set of 3D points}. The lines are created following
   * the order of the points.
   * @param points {@link Point3DContainer 3D points} that compose this line set.
   */
  public SimpleLineSet3D(Point3DContainer<T> points){
    setVertices(points);
  }
  
  /**
   * Create a default line set 3D, with no line.
   */
  public SimpleLineSet3D(){
    
  }

}
