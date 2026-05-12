package org.jeometry.geom2D.point;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.SpatialLocalization2D;

/**
 * A container dedicated to the management of {@link Point2D 2D points}. Basically, this interface describes a list of 2D points.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public interface Point2DContainer extends List<Point2D>, Iterable<Point2D>, SpatialLocalization2D{

	  /**
	   * A set of data that describes 2D coordinates.
	   */
	  public static final int DATA_COORDINATE2D = 1;
	    
	  /**
	   * A set of data that describes coordinates.
	   */
	  public static final int DATA_IDENTIFIER     = 2;
	  
	  /**
	   * A set of data that describes RGBA color within int format.
	   */
	  public static final int DATA_COLOR          = 4;
	  
	  /**
	   * A set of data that describes normals.
	   */
	  public static final int DATA_NORMAL         = 8;
	  
	  /**
	   * Get the type of the data available within the point 2D container. 
	   * The returned value can be a combination (using logical "or") of:
	   * <ul>
	   * <li>{@link #DATA_IDENTIFIER} if the points stored within the container can provide identifiers.
	   * <li>{@link #DATA_COORDINATE2D} if the points stored within the container can provide coordinates.
	   * <li>{@link #DATA_COLOR} if the points stored within the container can provide color component (RGBA).
	   * <li>{@link #DATA_NORMAL} if the points stored within the container can provide normal.
	   * </ul> 
	   * @return the type of the data that are available within the container.
	   */
	  public int getDataType();	  
	  	  
	  /**
	   * Update the <code>output</code> point with the values of the the point at the specified position in this container. 
	   * If the <code>output</code> point is <code>null</code>, a new point is created and returned.
	   *
	   * @param index index of the point to return
	 * @param output the output point that is updated with the point at the specified position in this container
	   * @return the point at the specified position in this container.
	   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
	   */
	  Point2D get(int index, Point2D output);
	  
}
