package org.jeometry.geom3D.algorithm.delaunay.clarkson;

import org.jeometry.Geometry;

/**
 * A exception that can be raised during Delaunay triangulation computation.
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.0
 */
public class DelaunayException extends Exception {


  private static final long serialVersionUID = Geometry.BUILD;

  /**
   * Create a default Delaunay triangulation computation exception.
   */
  public DelaunayException(){
    super();
  }
 
  /**
   * Create a Delaunay triangulation computation exception with the given message.
   * @param message the message to send.
   */
  public DelaunayException(String message){
    super(message);
  }
}
