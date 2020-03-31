package org.jeometry.geom3D.algorithm.delaunay.clarkson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jeometry.factory.GeometryFactory;
import org.jeometry.geom3D.Geom3D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Box;



/**
 * This class is an implementation of the Ken Clarkson <a href="http://www.netlib.org/voronoi/hull.html">hull algorithm</a> that enable to compute convex hull in n-dimension.<br><br>
 * This algorithm compute a convex hull for a set of <code>n</code> points in a <code>O(n x log(n))</code> average time 
 * with a worst case in <code>O(n x n)</code>. <br>
 * This algorithm is based on an <b>integer representation</b> of input point coordinates, so if your points are closer than a coordinate unit, 
 * it is required to apply a scale on them. This scale can be given in parameter to computation method
 * ({@link #compute(double[][], float) compute(double[][], float)})
 * @param <T> The type of underlying 3D points 
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.0
 *
 */
public class DelaunayClarkson3D<T extends Point3D> extends DelaunayClarkson{

  private Point3D[] ifinitePoints = null;
  
  private int originalPointCount   = -1;

  private boolean filterInfinite   = false;
  
  private boolean generateInfinite = true;
  
  /**
   * Is the delaunay tetrahedralization has to generate infinite tetrahedra. Generation of tetrahedra relies 
   * on the integration of infinite points to the original point cloud. By convention, infinite points are the vertices of a scaled
   * axis aligned bounding box computed from original point cloud. 
   * All tetrahedra that contain an infinite point as vertex are marked as infinite.
   * @return <code>true</code> if the the delaunay tetrahedralization has to generate infinite tetrahedra and <code>false</code> otherwise.
   * @see #setGenerateInfinite(boolean)
   * @see #isFilterInfinite()
   */
  public boolean isGenerateInfinite() {
    return generateInfinite;
  }

  /**
   * Set if the delaunay tetrahedralization has to generate infinite tetrahedra. Generation of tetrahedra relies 
   * on the integration of infinite points to the original point cloud. By convention, infinite points are the vertices of a scaled
   * axis aligned bounding box computed from original point cloud. 
   * All tetrahedra that contain an infinite point as vertex are marked as infinite.
   * @param generateInfinite <code>true</code> if the the delaunay tetrahedralization has to generate infinite tetrahedra and <code>false</code> otherwise.
   * @see #isGenerateInfinite()
   * @see #isFilterInfinite()
   */
  public void setGenerateInfinite(boolean generateInfinite) {
    this.generateInfinite = generateInfinite;
  }

  /**
   * Is the result of the delaunay tetrahedralization has to ignore infinite tetrahedra.
   * @return <code>true</code> if the result of the delaunay tetrahedralization has to ignore infinite tetrahedra and <code>false</code> otherwise.
   * @see #setFilterInfinite(boolean)
   */
  public boolean isFilterInfinite() {
    return filterInfinite;
  }

  /**
   * Set if the result of the delaunay tetrahedralization has to ignore infinite tetrahedra.
   * @param filterInfinite <code>true</code> if the result of the delaunay tetrahedralization has to ignore infinite tetrahedra and <code>false</code> otherwise.
   * @see #isFilterInfinite()
   */
  public void setFilterInfinite(boolean filterInfinite) {
    this.filterInfinite = filterInfinite;
  }

  /**
   * Create a new instance of this algorithm.
   */
  public DelaunayClarkson3D(){
    super();
  }

  /**
   * Compute the delaunay triangulation for the given points and at the given scale.
   * @param points the points.
   * @param scale the scale to apply before computation.
   * @return the delaunay tetrahedralization
   * @throws DelaunayException if an error occurs.
   */
  public DelaunayTetrahedralization<T> compute(Point3DContainer<T> points, float scale) throws DelaunayException{
    if ((points != null)&&(points.size() > 3)){
      
      originalPointCount = points.size();
      
      Map<Integer, DelaunayTetrahedron<T>> map = new LinkedHashMap<Integer, DelaunayTetrahedron<T>>();
      
      double[][] samples = null;
      
      int i = 0;
      
      if (isGenerateInfinite()){
        samples = new double[3][points.size()+8];
        Iterator<? extends Point3D> iter = points.iterator();
        Point3D pt                       = null;
        i = 0;
        while(iter.hasNext()){
          pt = iter.next();
          samples[0][i] = pt.getX();
          samples[1][i] = pt.getY();
          samples[2][i] = pt.getZ();
          //samples[3][i] = pt.getNormSquare();
          i++;
        }
        
        // Adding infinite points (bounding box *10 points)
        Box box = Geom3D.computeAxisAlignedBoundingBox(points);
        
        double boxXMax = box.getMax().getX()*scale*10;
        double boxYMax = box.getMax().getY()*scale*10;
        double boxZMax = box.getMax().getZ()*scale*10;
        double boxXMin = box.getMin().getX()*scale*10;
        double boxYMin = box.getMin().getY()*scale*10;
        double boxZMin = box.getMin().getZ()*scale*10;
        
        ifinitePoints = new Point3D[8];
        
        ifinitePoints[0] = GeometryFactory.createPoint3D(boxXMin, boxYMin, boxZMin);
        ifinitePoints[1] = GeometryFactory.createPoint3D(boxXMin, boxYMin, boxZMax);
        ifinitePoints[2] = GeometryFactory.createPoint3D(boxXMin, boxYMax, boxZMin);
        ifinitePoints[3] = GeometryFactory.createPoint3D(boxXMin, boxYMax, boxZMax);
        ifinitePoints[4] = GeometryFactory.createPoint3D(boxXMax, boxYMin, boxZMin);
        ifinitePoints[5] = GeometryFactory.createPoint3D(boxXMax, boxYMin, boxZMax);
        ifinitePoints[6] = GeometryFactory.createPoint3D(boxXMax, boxYMax, boxZMin);
        ifinitePoints[7] = GeometryFactory.createPoint3D(boxXMax, boxYMax, boxZMax);
        
        samples[0][points.size()]   = boxXMin;
        samples[1][points.size()]   = boxYMin;
        samples[2][points.size()]   = boxZMin;
        
        samples[0][points.size()+1] = boxXMin;
        samples[1][points.size()+1] = boxYMin;
        samples[2][points.size()+1] = boxZMax;
        
        samples[0][points.size()+2] = boxXMin;
        samples[1][points.size()+2] = boxYMax;
        samples[2][points.size()+2] = boxZMin;
        
        samples[0][points.size()+3] = boxXMin;
        samples[1][points.size()+3] = boxYMax;
        samples[2][points.size()+3] = boxZMax;
        
        samples[0][points.size()+4] = boxXMax;
        samples[1][points.size()+4] = boxYMin;
        samples[2][points.size()+4] = boxZMin;
        
        samples[0][points.size()+5] = boxXMax;
        samples[1][points.size()+5] = boxYMin;
        samples[2][points.size()+5] = boxZMax;
        
        samples[0][points.size()+6] = boxXMax;
        samples[1][points.size()+6] = boxYMax;
        samples[2][points.size()+6] = boxZMin;
        
        samples[0][points.size()+7] = boxXMax;
        samples[1][points.size()+7] = boxYMax;
        samples[2][points.size()+7] = boxZMax;
      } else {
        
        samples = new double[3][points.size()];
        Iterator<? extends Point3D> iter = points.iterator();
        Point3D pt                       = null;
        i = 0;
        while(iter.hasNext()){
          pt = iter.next();
          samples[0][i] = pt.getX();
          samples[1][i] = pt.getY();
          samples[2][i] = pt.getZ();
          //samples[3][i] = pt.getNormSquare();
          i++;
        }
      }
      

      System.out.print("Computing delaunay");
      long s = System.currentTimeMillis();
      compute(samples, scale);
      System.out.println("  ["+((System.currentTimeMillis() - s)/1000.0d)+"s]");
      
      System.out.print("Generating tetrahedra");
      s = System.currentTimeMillis();
      DelaunayTetrahedralization<T> result = new DelaunayTetrahedralization<T>(points, getSimplexes().length);
      DelaunayTetrahedron<T> tetrahedron   = null;
      
      // Generating tetrahedra
      for (i=0; i<getSimplexes().length; i++) {
        
        tetrahedron = new DelaunayTetrahedron<T>(getSimplexes()[i][0], getSimplexes()[i][1], getSimplexes()[i][2], getSimplexes()[i][3], true, points);
        
        map.put(i, tetrahedron);
        tetrahedron.setInfinite(isInfinite(getSimplexes()[i]));
        
        if ((isFilterInfinite())&&(!tetrahedron.isInfinite())){
          tetrahedron = result.addTetrahedron(tetrahedron);
        } else {
          tetrahedron = result.addTetrahedron(tetrahedron);
        }
      }
      
      // Generating tetrahedra incidency to vertices.
      // Infinite vertices (last 8 vertices) are not integrated.
      for(i=0; i < getVertices().length - 8; i++){
        
        if (getVertices()[i] != null){
          for(int j = 0; j < getVertices()[i].length; j++){
            
            tetrahedron = map.get(getVertices()[i][j]);
            
            if (getVertices()[i][j] > -1){
              
              if (!(isFilterInfinite() && tetrahedron.isInfinite())){
                result.addIndicentTetrahedron(points.get(i), tetrahedron);
              }
            } else {
              //result.addIndicentTetrahedron(points.get(i), (DelaunayTetrahedron)null);
            }
            
            tetrahedron = null;
          }
        } else {
          result.addIndicentTetrahedron(points.get(i), (DelaunayTetrahedron<T>)null);
        }
      }
      
      // Generating neighborhood
      List<DelaunayTetrahedron<T>> neighbors = new ArrayList<DelaunayTetrahedron<T>>(4);
      DelaunayTetrahedron<T> candidate   = null;
      int index                       = -1;
      
      for(i = 0; i < getNeighbors().length; i++){

        tetrahedron = map.get(i);
          
        if ((getNeighbors()[i] != null)&&(getNeighbors()[i].length > 0)){
            
          for(int j = 0; j < 4; j++){
            if (getNeighbors()[i][j] > -1){
              
              candidate = map.get(getNeighbors()[i][j]);
              index     = getDifferentVertex(tetrahedron, candidate);
              
              if (isFilterInfinite()){
                if (!candidate.isInfinite()){
                  neighbors.set(index, candidate);
                } else {
                  neighbors.set(index, null);
                  tetrahedron.setConvexHullFace(index, true);
                }
              } else {
                neighbors.set(index, candidate);
              } 
            } else {
              neighbors.set(j, null);
            }
          }
            
          result.setNeighbors(tetrahedron, neighbors);
          
        } else {
          result.setNeighbors(tetrahedron, null);
        } 
      }
      System.out.println("  ["+((System.currentTimeMillis() - s)/1000.0d)+"s]");
      
      neighbors.set(0, null);
      neighbors.set(1, null);
      neighbors.set(2, null);
      neighbors.set(3, null);
      neighbors = null;
      
      return result;
      
    } else {
      throw new DelaunayException("Cannot triangulate less than 3 points");
    }
  }
  
  /**
   * Get the index in t1 of the vertex that does not appear within t1. this method is used for 
   * determining the vertex that located in front of the face shared by t1 and t2.
   * @param t1 
   * @param t2
   * @return
   */
  private int getDifferentVertex(DelaunayTetrahedron<T> t1, DelaunayTetrahedron<T> t2){
    int index = -1;
    
    for(int i = 0; (i < 4) && (index == -1); i++){

      if (   (t1.getVertexIndice(i) != t2.getVertexIndice(0))
           &&(t1.getVertexIndice(i) != t2.getVertexIndice(1))
           &&(t1.getVertexIndice(i) != t2.getVertexIndice(2))
           &&(t1.getVertexIndice(i) != t2.getVertexIndice(3))){
        index = i;
      }
    }
    
    return index;
  }
  
  private boolean isInfinite(int[] indices){
    
    boolean infinite = false;
    
    if (indices != null){
      infinite =    (indices[0] >= originalPointCount)
                 || (indices[1] >= originalPointCount)
                 || (indices[2] >= originalPointCount)
                 || (indices[3] >= originalPointCount);
    } else {
      infinite = false;
    }
    
    return infinite;
  }
}

