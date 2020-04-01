package org.jeometry.geom3D.algorithm.delaunay.clarkson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.indexed.IndexedTetrahedron;

/**
 * A Delaunay tetrahedralization regarding Ken Clarkson <a href="http://www.netlib.org/voronoi/hull.html">hull algorithm</a>.
 * @param <T> The type of underlying 3D points 
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 *
 */
public class DelaunayTetrahedralization<T extends Point3D> {
  
  private Point3DContainer<T> points                             = null;
  
  private List<DelaunayTetrahedron<T>> tetrahedra                                  = null;
  
  private HashMap<Point3D, Collection<DelaunayTetrahedron<T>>> incidentTetrahedra = null;
  
  private HashMap<Point3D, Collection<IndexedFace<T>>> incidentFaces         = null;
  
  private HashMap<DelaunayTetrahedron<T>, List<DelaunayTetrahedron<T>>> neighbors     = null;
   
  /**
   * Create a new Delaunay tetrahedralization on the given points.
   * @param points the input points.
   * @param tetrahedraCount the number of tetrahedra.
   */
  public DelaunayTetrahedralization(Point3DContainer<T> points, int tetrahedraCount){
    this.points = points;
    tetrahedra = new ArrayList<DelaunayTetrahedron<T>>(tetrahedraCount);
  }
  
  /**
   * Get the tetrahedra that compose this Delaunay tetrahedralization.
   * @return the tetrahedra that compose this Delaunay tetrahedralization.
   */
  public List<DelaunayTetrahedron<T>> getTetrahedra(){
    return tetrahedra;
  }
  
  /**
   * Get the points (vertices) that compose the tetrahedralized space.
   * @return the points (vertices) that compose the tetrahedralized space.
   */
  public Point3DContainer<T> getPoints(){
    return points;
  }
  
  /**
   * Add a tetrahedron to this Delaunay tetrahedralization.
   * @param vertexIndices the vertices that compose the tetrahedron.
   * @return the created tetrahedron.
   */
  public DelaunayTetrahedron<T> addTetrahedron(int[] vertexIndices){
    
    if (tetrahedra == null){
      tetrahedra = new LinkedList<DelaunayTetrahedron<T>>();
    }
    
    DelaunayTetrahedron<T> tetrahedron = new DelaunayTetrahedron<T>(vertexIndices[0], vertexIndices[1], vertexIndices[2], vertexIndices[3], true, points);
   
    boolean success = tetrahedra.add(tetrahedron);
    
    if (success){
      return tetrahedron;
    } else {
      return null;
    }
  }
  
  /**
   * Add a tetrahedron to this Delaunay tetrahedralization.
   * @param tetrahedron the tetrahedron t oadd.
   * @return the added tetrahedron or <code>null</code> if the tetrahedron was not added.
   */
  public DelaunayTetrahedron<T> addTetrahedron(DelaunayTetrahedron<T> tetrahedron){
    
    if (tetrahedra == null){
      tetrahedra = new LinkedList<DelaunayTetrahedron<T>>();
    }
   
    boolean success = tetrahedra.add(tetrahedron);
    
    if (success){
      tetrahedron.setVerticesSource(getPoints());
      return tetrahedron;
    } else {
      return null;
    }
  }
  
  /**
   * Get all the {@link DelaunayTetrahedron tetrahedra} 
   * that contain the given {@link Point3D point} as vertex.
   * @param point the {@link Point3D point} that define a vertex of the tetrahedralization.
   * @return the {@link DelaunayTetrahedron tetrahedra} 
   * that contain the given {@link Point3D point} as vertex or <code>null</code> if 
   * the given point is not a vertex of the tetrahedralization.
   */
  public Collection<DelaunayTetrahedron<T>> getIncidentTetrahedra(Point3D point){
    return incidentTetrahedra.get(point);
  }
  
  /**
   * Get all the {@link DelaunayTetrahedron tetrahedra} 
   * that share the edge formed by vertices <code>u</code> and <code>v</code>
   * @param u the first extremity of the edge.
   * @param v the second extremity of the edge.
   * @return the {@link DelaunayTetrahedron tetrahedra} 
   * that share the given edge.
   */
  public Collection<DelaunayTetrahedron<T>> getIncidentTetrahedra(Point3D u, Point3D v) {
    // look for common cell of u,v
    Collection<DelaunayTetrahedron<T>> ic = getIncidentTetrahedra(u);
    for (DelaunayTetrahedron<T> c : ic){
      if (c.isVertex(v)){
        return getIncidentTetrahedra(c, c.getVertexIndex(u), c.getVertexIndex(v));
      }
    }
    return null;
  }   
  
  /**
   * Returns the collection of all triangulation cells incident to edge (c,i,j).
   * @param c the cell that contains the edge.
   * @param i the edge first extremity.
   * @param j the edge second extremity.
   * @return the collection of all triangulation cells incident to edge (c,i,j).
   */        
  public Collection<DelaunayTetrahedron<T>> getIncidentTetrahedra(DelaunayTetrahedron<T> c, int i, int j) {
    Point3D u = c.getVertex(i);
    Point3D v = c.getVertex(j);
    LinkedHashSet<DelaunayTetrahedron<T>> res = new LinkedHashSet<DelaunayTetrahedron<T>>();
    
    // circulate around the edge and insert the cells in order into the result
    DelaunayTetrahedron<T> cstart = c;
    DelaunayTetrahedron<T> cprev = null;
    int k=0;

    do {
      
      k = 0;
      
      // insert currently visited cell into result
      res.add(c);
      // look for next cell around edge
      
      for (; k<4; k++){
        if (c.getNeighbor(k) != cprev && c.getNeighbor(k).isVertex(u) && c.getNeighbor(k).isVertex(v)) {
          cprev = c;
          c = c.getNeighbor(k);
          break;
        }
      }
      
      
      
      // we should have changed cells here
      //if (k==4)
      //  throw new Error ("no next cell found");
    } while ((c != cstart)&&(k < 4));

    return res;
  }
  
  /**
   * Mark the given {@link DelaunayTetrahedron tetrahedron} 
   * as incident to the given {@link Point3D point}.  
   * @param point the {@link Point3D point} to mark.
   * @param tetrahedron the {@link DelaunayTetrahedron tetrahedron} to set as incident to the point.
   * @return <code>true</code> if the tetrahedron is successfully marked as incident of the point and <code>false</code> otherwise.
   */
  public boolean addIndicentTetrahedron(Point3D point, DelaunayTetrahedron<T> tetrahedron){
    if (incidentTetrahedra == null){
      incidentTetrahedra = new HashMap<Point3D, Collection<DelaunayTetrahedron<T>>>();
    }
    
    if (incidentTetrahedra.get(point) == null){
      incidentTetrahedra.put(point, new LinkedList<DelaunayTetrahedron<T>>());
    }
    
    return incidentTetrahedra.get(point).add(tetrahedron);
  }
  
  /**
   * Set the tetrahedra referenced by the given indices as incident to the given point.
   * @param point the point to process.
   * @param incidentTetrahedraIndices the indices of the trtrahedra that are incident to the point.
   */
  public void setIncidentTetrahera(Point3D point, int[] incidentTetrahedraIndices){
    for(int i = 0; i < incidentTetrahedraIndices.length; i++){
      if (incidentTetrahedraIndices[i] > -1){
        addIndicentTetrahedron(point, tetrahedra.get(incidentTetrahedraIndices[i]));
      } else {
        addIndicentTetrahedron(point, (DelaunayTetrahedron<T>)null);
      }
    }
  }
  
  /**
   * Get all the faces that are incident to the given point.
   * @param point the point to check.
   * @return the faces that are incident to the given point.
   */
  public Collection<IndexedFace<T>> getIncidentFaces(Point3D point){
	  
	if (incidentFaces != null) {
		return incidentFaces.get(point);
	} else {
		return null;
	}
  }
  
  /**
   * Add the given face as incident to the given point.
   * @param point the point to set.
   * @param face the face to add as the point incident.
   * @return <code>true</code> if the face is successfully added as an incident of the point and <code>false</code> otherwise.
   */
  public boolean addIndicentTetrahedron(Point3D point, IndexedFace<T> face){
    if (incidentFaces == null){
      incidentFaces = new HashMap<Point3D, Collection<IndexedFace<T>>>();
    }
    
    if (incidentFaces.get(point) == null){
      incidentFaces.put(point, new LinkedList<IndexedFace<T>>());
    }
    
    return incidentFaces.get(point).add(face);
  }
  
  /**
   * Get the neighbors of the given tetrahedron.
   * @param tetrahedron the tetrahedron to check.
   * @return the neighbors of the given tetrahedron.
   * @see #setNeighbors(DelaunayTetrahedron, List)
   */
  public List<DelaunayTetrahedron<T>> getNeighbors(DelaunayTetrahedron<T> tetrahedron){
    return neighbors.get(tetrahedron);
  }
  
  /**
   * Set the neighbors of the given tetrahedron.
   * @param tetrahedron the tetrahedron to check.
   * @param neighbors the neighbors of the given tetrahedron.
   * @see #getNeighbors(DelaunayTetrahedron)
   */
  public void setNeighbors(DelaunayTetrahedron<T> tetrahedron, List<DelaunayTetrahedron<T>> neighbors){
    

    if (tetrahedron != null){
      if (neighbors != null){
        
        if (this.neighbors == null){
          this.neighbors = new HashMap<DelaunayTetrahedron<T>, List<DelaunayTetrahedron<T>>>();
        }
        
        for(int i = 0; i < 4; i++){
          tetrahedron.setNeighbor(i, neighbors.get(i));
        }
        
        this.neighbors.put(tetrahedron, neighbors);
        
      } else {
        Geometry.logger.warning("Tetrahedron  has no neighbor.");
      }
    } else {
      Geometry.logger.warning("Cannot add neightbors to null tetrahedron.");
    }
    

  }
  
  @Override
  public String toString(){
    String str   = "";
    int index    = 0;
    
    str += "Tetrahedra: ";
    Iterator<? extends IndexedTetrahedron<T>> tetrahedronIter = tetrahedra.iterator();
    IndexedTetrahedron<T> tetrahedron = null;
    while(tetrahedronIter.hasNext()){
      tetrahedron = tetrahedronIter.next();
      str += "  "+index+": "+tetrahedron;
      index++;
    }
    str += "";

    str += "Vertex incidences: "+System.getProperty("line.separator");
    Iterator<? extends Point3D> pointIter = points.iterator();
    Point3D point = null;
    Collection<? extends IndexedTetrahedron<T>> indidentTetrahedra = null;
    while(pointIter.hasNext()){
      point = pointIter.next();
      str += "    "+points.indexOf(point)+": "+point+System.getProperty("line.separator");;
      indidentTetrahedra = getIncidentTetrahedra(point);
      tetrahedronIter = indidentTetrahedra.iterator();
      while(tetrahedronIter.hasNext()){
        tetrahedron = tetrahedronIter.next();
        str += "      "+tetrahedra.indexOf(tetrahedron)+": "+tetrahedron;
      }
      
    }
    str +="";
    
    return str;
  }
  
  
  /**
   * Outputs the (triangulated) boundary of the Voronoi region dual to vertex v. 
   * Each Voronoi face forming the Voronoi region boundary is triangulated. 
   * When vertex v lies on the boundary of the convex hull, only the finite faces 
   * of the boundary of its region are output.
   * Throws a RuntimeException if v is infinite.
   * @param v the vertex.
   * @return the (triangulated) boundary of the Voronoi region dual to given vertex. 
   */
  public Collection<VoronoiTriangle> dual(Point3D v) {

    VoronoiTriangle triangle = null;
    
    // compute incident edges from incident cells
    Collection<DelaunayTetrahedron<T>> ic = getIncidentTetrahedra(v);
    
    HashSet<Point3D[]> ie = new HashSet<Point3D[]>();
    int index              = -1;
    for (DelaunayTetrahedron<T> c : ic){
      index = c.getVertexIndex(v);
      for (int i=1; i<4; i++){ 
        ie.add(new Point3D[]{v, c.getVertex((index+i)&3)});
      }
    }
    
    LinkedList<VoronoiTriangle> res = new LinkedList<VoronoiTriangle> ();

    // for each edge, compute sorted list of incident finite cells and then triangulate Voronoi face
    for (Point3D[] e : ie) {
      ic = getIncidentTetrahedra(e[0], e[1]);
      if ((ic == null) || (ic.size() < 3))
        continue;
      
      Iterator<DelaunayTetrahedron<T>> cit = ic.iterator();
      DelaunayTetrahedron<T> cstart        = cit.next();
      DelaunayTetrahedron<T> c             = cit.next();
      DelaunayTetrahedron<T> cprev         = null;
      
      do {
        
        cprev = c;
        c = cit.next();
        
        triangle = new VoronoiTriangle(dual(cstart), dual(cprev), dual(c));
        
        res.add(triangle);
      } while (cit.hasNext());
    }
    
    return res;
  }

  /**
   * Outputs the Voronoi vertex dual to cell c. 
   * By definition, this vertex coincides with the circumcenter of c. 
   * Throws a RuntimeException if c is infinite.
   * @param c the cell.
   * @return the Voronoi vertex dual to the given cell. 
   */
  public Point3D dual(DelaunayTetrahedron<T> c) {
    return circumCenter(c.getVertex(0), c.getVertex(1), c.getVertex(2), c.getVertex(3));
  }

  private Point3D circumCenter (Point3D p, Point3D q, Point3D r, Point3D s) {

    
    // Translate p to origin to simplify the expression.
    double qpx = q.getX()-p.getX();
    double qpy = q.getY()-p.getY();
    double qpz = q.getZ()-p.getZ();
    double qp2 = qpx*qpx + qpy*qpy + qpz*qpz;
    double rpx = r.getX()-p.getX();
    double rpy = r.getY()-p.getY();
    double rpz = r.getZ()-p.getZ();
    double rp2 = rpx*rpx + rpy*rpy + rpz*rpz;
    double spx = s.getX()-p.getX();
    double spy = s.getY()-p.getY();
    double spz = s.getZ()-p.getZ();
    double sp2 = spx*spx + spy*spy + spz*spz;

    double num_x = det33 (new double[] {qpy,qpz,qp2,
        rpy,rpz,rp2, spy,spz,sp2});
    double num_y = det33 (new double[] {qpx,qpz,qp2,
        rpx,rpz,rp2, spx,spz,sp2});
    double num_z = det33 (new double[] {qpx,qpy,qp2,
        rpx,rpy,rp2, spx,spy,sp2});
    double den = det33 (new double[] {qpx,qpy,qpz,
        rpx,rpy,rpz, spx,spy,spz});
    double inv = 1.0 / (2.0 * den);

    double x = p.getX() + num_x*inv;
    double y = p.getY() - num_y*inv;
    double z = p.getZ() + num_z*inv;

    return GeometryFactory.createPoint3D(x, y, z);

}
  
  /** 
   * Approximate computation of 3x3 determinant
   */
  private double det33( double... m ) {
    double det33 = 0;
    det33 += m[0]*(m[4]*m[8]-m[5]*m[7]);
    det33 -= m[1]*(m[3]*m[8]-m[5]*m[6]);
    det33 += m[2]*(m[3]*m[7]-m[4]*m[6]);
    return det33;
  }
}
