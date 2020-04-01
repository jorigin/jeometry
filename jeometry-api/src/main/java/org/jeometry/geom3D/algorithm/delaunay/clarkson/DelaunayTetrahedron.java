package org.jeometry.geom3D.algorithm.delaunay.clarkson;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.factory.GeometryFactory;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.indexed.IndexedTetrahedron;

/**
 * A {@link IndexedTetrahedron tetrahedron} 
 * that comes from a Delaunay tetrahedralization. This tetrahedron can provide its neighbourhood and its incidencies. 
 * @param <T> The type of underlying 3D points 
 * @author Julien Seinturier
 *
 */
public class DelaunayTetrahedron<T extends Point3D> implements IndexedTetrahedron<T>{

  List<DelaunayTetrahedron<T>> neighbors = null;

  private boolean[] convexFaces   = null;
  
  private boolean infinite        = false;

  private IndexedTetrahedron<T> tetrahedron = null;
  
  /**
   * Construct a new Delaunay tetrahedron from the given point source and the given vertex indices.
   * @param vertex1 the vertex1 index.
   * @param vertex2 the vertex2 index.
   * @param vertex3 the vertex3 index.
   * @param vertex4 the vertex4 index.
   * @param source the point source.
   */
  public DelaunayTetrahedron(int vertex1, int vertex2, int vertex3, int vertex4, Point3DContainer<T> source){
    this(vertex1, vertex2, vertex3, vertex4, true, source);
    convexFaces = new boolean[4];
  }
  
  /**
   * Construct a new Delaunay tetrahedron from the given point source and the given vertex indices.
   * @param base1 the first vertex index.
   * @param base2 the second vertex index.
   * @param base3 the third vertex index.
   * @param top the fourth vertex index.
   * @param source the vertex source.
   * @param validate if the tetrahedron has to be validated
   */
  public DelaunayTetrahedron(int base1, int base2, int base3, int top, boolean validate, Point3DContainer<T> source){
    tetrahedron = GeometryFactory.createIndexedTetrahedron(base1, base2, base3, top, source);
    neighbors   = new ArrayList<DelaunayTetrahedron<T>>(4);
    convexFaces = new boolean[4];
  }
  
  /**
   * Get the neighbor of this tetrahedron that is linked by the face in front of the vertex indexed by given <code>vertexIndex</code>.
   * @param vertexIndex the index of the vertex in front of the shared face between this tetrahedron and its neighbor.
   * @return the neighbor of this tetrahedron.
   * @see #setNeighbor(int, DelaunayTetrahedron)
   */
  public DelaunayTetrahedron<T> getNeighbor(int vertexIndex){
    if ((vertexIndex > -1)&&(vertexIndex < 4)){
      return neighbors.get(vertexIndex);
    }
    
    return null;
  }
  
  @Override
  public Point3DContainer<T> getVerticesSource() {
  	return tetrahedron.getVerticesSource();
  }

  @Override
  public void setVerticesSource(Point3DContainer<T> verticesSource) {
  tetrahedron.setVerticesSource(verticesSource);
  }
  
  @Override
  public int getVertexIndice(int position) {
  	return tetrahedron.getVertexIndice(position);
  }

  @Override
  public Point3D getVertex(int position) {
  	return tetrahedron.getVertex(position);
  }

  @Override
  public int getVertexIndex(Point3D vertex) {
  	return tetrahedron.getVertexIndex(vertex);
  }

  @Override
  public int[] getVerticesArray() {
  	return tetrahedron.getVerticesArray();
  }
  
  /**
   * Set the neighbor of this tetrahedron that is linked by the face in front of the vertex indexed by given <code>vertexIndex</code>.
   * @param vertexIndex the index of the vertex in front of the shared face between this tetrahedron and its neighbor.
   * @param tetrahedron the neighbor.
   * @see #getNeighbor(int)
   */
  public void setNeighbor(int vertexIndex, DelaunayTetrahedron<T> tetrahedron){
    if ((vertexIndex > -1)&&(vertexIndex < 4)){
      neighbors.set(vertexIndex, tetrahedron);
    }
  }
  
  /**
   * Check if the given {@link Point3D point} is a vertex of the tetrahedron.
   * @param point the {@link Point3D point} to check.
   * @return <code>true</code> if the given point is a vertex of the tetrahedron and <code>false</code> otherwise.
   */
  public boolean isVertex(Point3D point){
    return getVertex(0) == point || getVertex(1) == point || getVertex(2) == point || getVertex(3) == point;
  }
  
  /**
   * Get if the face indexed by the given <code>index</code> lies on the underlying points convex hull.
   * @param index the index of the face to check (between 0 and 3).
   * @return <code>true</code> if the face indexed by the given <code>index</code> lies on the underlying points convex hull and <code>false</code> otherwise.
   */
  public boolean isConvexHullFace(int index){
    boolean convexHull = false;
    
    if ((index >= 0)&&(index <= 3)){
      convexHull = convexFaces[index];
    }
    
    return convexHull;
  }
  
  /**
   * Set if the face indexed by the given <code>index</code> lies on the underlying points convex hull.
   * @param index the index of the face to check (between 0 and 3).
   * @param isConxehHull <code>true</code> if the face indexed by the given <code>index</code> lies on the underlying points convex hull and <code>false</code> otherwise.
   */
  public void setConvexHullFace(int index, boolean isConxehHull){
    if ((index >= 0)&&(index <= 3)){
      convexFaces[index] = isConxehHull;
    }
  }
  
  /**
   * Get the indexes of faces of this tetrahedron that lie on the underlying point set convex hull.
   * @return the indexes of faces of this tetrahedron that lie on the underlying point set convex hull.
   */
  public int[] getConvexHullFaces(){
    int count = 0;
    
    int[] faces = null;
    
    for(int i = 0; i < convexFaces.length; i++){
      if (convexFaces[i] == true){
        count++;
      }
    }
    
    faces = new int[count];
    
    count = 0;
    for(int i = 0; i < convexFaces.length; i++){
      if (convexFaces[i] == true){
        faces[count] = i;
        count++;
      }
    }
    
    return faces;
  }
  
  /**
   * Check if this tetrahedron contains a face that lies on the underlying points convex hull. 
   * @return <code>true</code> if this tetrahedron contains a face that lies on the underlying points convex hull and <code>false</code> otherwise. 
   */
  public boolean isConvexHull(){
    return ((convexFaces != null) &&((convexFaces[0] == true)||(convexFaces[1] == true)||(convexFaces[2] == true)||(convexFaces[3] == true)));
  }
  
  /**
   * Get if this tetrahedron is infinite. An infinite tetrahedron is by convention a tetrahedron that relies 
   * on a point that is par of the bounding volume of the underlying points.
   * @return <code>true</code> if the tetrahedron is infinite and <code>false</code> otherwise.
   * @see #setInfinite(boolean)
   */
  public boolean isInfinite() {
    return infinite;
  }

  /**
   * Set if this tetrahedron is infinite. An infinite tetrahedron is by convention a tetrahedron that relies 
   * on a point that is par of the bounding volume of the underlying points.
   * @param infinite <code>true</code> if the tetrahedron is infinite and <code>false</code> otherwise.
   * @see #isInfinite()
   */
  public void setInfinite(boolean infinite) {
    this.infinite = infinite;
  }
 
}
