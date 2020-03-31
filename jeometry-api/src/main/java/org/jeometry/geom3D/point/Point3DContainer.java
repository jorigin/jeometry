package org.jeometry.geom3D.point;

import java.util.Iterator;

import org.jeometry.Geometry;
import org.jeometry.geom3D.SpatialLocalization3D;

/**
 * A container dedicated to the management of {@link Point3D 3D points}. Basically, this interface describes a list of 3D points.
 * @param <T> the specific type of the {@link Point3D 3D points}
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 */
public interface Point3DContainer<T extends Point3D> extends Iterable<T>, SpatialLocalization3D{

  /**
   * A set of data that describes coordinates.
   */
  public static final int DATA_COORDINATE     = 1;
    
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
   * Get the type of the data available within the point 3D container. 
   * The returned value can be a combination (using logical "or") of:
   * <ul>
   * <li>{@link #DATA_IDENTIFIER} if the points stored within the container can provide identifiers.
   * <li>{@link #DATA_COORDINATE} if the points stored within the container can provide coordinates.
   * <li>{@link #DATA_COLOR} if the points stored within the container can provide color component (RGBA).
   * <li>{@link #DATA_NORMAL} if the points stored within the container can provide normal.
   * </ul> 
   * @return the type of the data that are available within the container.
   */
  public int getDataType();
  
  /**
   * Returns the number of points within the container.  
   * If the container contains more than {@code Integer.MAX_VALUE} elements, returns {@code Integer.MAX_VALUE}.
   * @return the number of elements in this list
   */
  int size();

  /**
   * Returns {@code true} if this container contains no elements.
   * @return {@code true} if this container contains no elements
   */
  boolean isEmpty();
  
  /**
   * Returns {@code true} if this container contains the specified point.
   * More formally, returns {@code true} if and only if this container contains
   * at least one element {@code e} such that {@code Objects.equals(o, e)}.
   *
   * @param point the point whose presence in this container is to be tested
   * @return {@code true} if this container contains the specified element
   */
  boolean contains(Point3D point);
  
  /**
   * Returns an iterator over the points in this container in proper sequence.
   * @return an iterator over the points in this container in proper sequence
   */
  Iterator<T> iterator();
  
  /**
   * Appends the specified point to the end of this container.
   * @param point the point to be appended to this container.
   * @return {@code true} if the point is successfully added and {@code false} otherwise.
   */
  boolean add(T point);
  
  /**
   * Removes the first occurrence of the specified point from this container, if it is present. 
   * If this container does not contain the point, it is unchanged. 
   * Returns {@code true} if this container contained the specified point (or equivalently, if this container changed as a result of the call).
   * @param point point to be removed from this container, if present
   * @return {@code true} if this container contained the specified point (or equivalently, if this container changed as a result of the call).
   */
  boolean remove(Point3D point);
  
  /**
   * Removes all of the points from this container.
   * The container will be empty after this call returns.
   */
  void clear();
  
  /**
   * Returns the point at the specified position in this container.
   *
   * @param index index of the point to return.
   * @return the point at the specified position in this container.
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  T get(int index);
  
  /**
   * Replaces the point at the specified position in this container with the specified one.
   *
   * @param index index of the point to replace
   * @param point point to be stored at the specified position
   * @return the point previously at the specified position
   * @throws IndexOutOfBoundsException if the index is out of range ({@code index < 0 || index >= size()})
   */
  Point3D set(int index, T point);
  
  /**
   * Removes the point at the specified position in this container.
   * Shifts any subsequent elements to the left (subtracts one
   * from their indices). Returns the point that was removed from the
   * container.
   *
   * @param index the index of the element to be removed
   * @return the element previously at the specified position
   * @throws UnsupportedOperationException if the {@code remove} operation
   *         is not supported by this list
   * @throws IndexOutOfBoundsException if the index is out of range
   *         ({@code index < 0 || index >= size()})
   */
  T remove(int index);
  
  /**
   * Returns the index of the first occurrence of the specified point
   * in this container, or -1 if it does not contain the point.
   * @param point the point to search for
   * @return the index of the first occurrence of the specified point in
   *         this container, or -1 if it does not contain the point
   */
  int indexOf(Point3D point);

  /**
   * Returns the index of the last occurrence of the specified point
   * in this container, or -1 if it does not contain the point.

   * @param point the point to search for
   * @return the index of the last occurrence of the specified point in
   *         this container, or -1 if it does not contain the point
   */
  int lastIndexOf(Point3D point);
}
