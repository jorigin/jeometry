package org.jeometry.geom3D.primitive.indexed;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Line3D;

/**
 * An interface that describe an indexed 3D line.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public interface IndexedLine3D<T extends Point3D> extends Line3D<T>{
  
  /**
   * Get the vertices of the line. A line links two 3D points as vertices.
   * @return the indices of the extremities of the line   
   */
  int[] getVerticesIndexes();

  /**
   * Get the vertices list from which the indexes are coming.
   * @return the vertices list from which the indexes are coming.
   * @see #setVerticesSource(Point3DContainer)
   */
  Point3DContainer<T> getVerticesSource();

  /**
   * Set the vertices list from which the indexes are coming. 
   * This method has not to be confused with {@link #getVertices()}
   * method that return only the vertices attached to this line (only the 2 extreminies).
   * @param verticesSource the vertices list from which the indexes are coming.
   * @see #getVerticesSource()
   */
  public void setVerticesSource(Point3DContainer<T> verticesSource);
  
  /**
   * Return if the indexes of the geometry have been validated. The validation is made by calling method {@link #validateIndexes()}.
   * @return <code>true</code> if the geometry has validated indexes and <code>false</code> otherwise.
   */
  boolean isValidatedIndexes();
  
  /**
   * Validate the geometry. This method valid the link between the indexed geometry and the geometry itself.
   * @return <code>true</code> if the validation is effective, <code>false</code> otherwise.
   */
  boolean validateIndexes();
  
  /**
   * Get the index within the {@link #getVerticesSource() vertices source} of the first extremity of the line.
   * @return the index within the {@link #getVerticesSource() vertices source} of the first extremity of the line.
   * @see #getEnd2Index()
   * @see #getEnd1()
   */
  public int getEnd1Index();
  
  /**
   * Set the index within the {@link #getVerticesSource() vertices source} of the first extremity of the line.
   * @param index the index within the {@link #getVerticesSource() vertices source} of the first extremity of the line.
   * @see #getEnd1Index()
   */
  public void setEnd1Index(int index);
  
  /**
   * Get the index within the {@link #getVerticesSource() vertices source} of the second extremity of the line.
   * @return the index within the {@link #getVerticesSource() vertices source} of the second extremity of the line.
   * @see #getEnd1Index()
   * @see #getEnd2()
   */
  public int getEnd2Index();
  
  /**
   * Set the index within the {@link #getVerticesSource() vertices source} of the second extremity of the line.
   * @param index the index within the {@link #getVerticesSource() vertices source} of the second extremity of the line.
   * @see #getEnd2Index()
   */
  public void setEnd2Index(int index);
}
