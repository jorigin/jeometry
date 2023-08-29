package org.jeometry.geom3D.algorithm.delaunay.clarkson;

import org.jeometry.geom3D.point.Point3D;

/**
 * A Vorinoi triangle.
 * @author Julien Seinturier
 */
public class VoronoiTriangle {

 /**
  * The first vertex.
  */
  private Point3D vertex1 = null;

  /**
   * The second vertex.
   */
  private Point3D vertex2 = null;
  
  /**
   * The third vertex.
   */
  private Point3D vertex3 = null;
  
  /**
   * Create a new Voronoi triangle with the given vertices.
   * @param vertex1 the first vertex.
   * @param vertex2 the second vertex.
   * @param vertex3 the third vertex.
   */
  public VoronoiTriangle(Point3D vertex1, Point3D vertex2, Point3D vertex3){
    this.vertex1 = vertex1;
    this.vertex2 = vertex2;
    this.vertex3 = vertex3;
  }

  /**
   * Get the first vertex of the voronoi triangle.
   * @return the first vertex of the voronoi triangle.
   */
  public Point3D getVertex1() {
    return this.vertex1;
  }
  
  /**
   * Get the second vertex of the voronoi triangle.
   * @return the second vertex of the voronoi triangle.
   */
  public Point3D getVertex2() {
    return this.vertex2;
  }

  /**
   * Get the the third vertex of the voronoi triangle.
   * @return the the third vertex of the voronoi triangle.
   */
  public Point3D getVertex3() {
    return this.vertex3;
  }

}
