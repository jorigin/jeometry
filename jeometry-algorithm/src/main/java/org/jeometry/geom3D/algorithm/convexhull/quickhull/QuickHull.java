package org.jeometry.geom3D.algorithm.convexhull.quickhull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.Geom3D;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Polygon3D;

/**
 * This class provide convex hull computation for a set of points using the convex hull algorithm given in
 * <i>"The Quickhull Algorithm for Convex Hulls." ACM Trans. Mathematical Software 22, 469-483, 1996.</i>
 * by Barber, C. B.; Dobkin, D. P.; and Huhdanpaa, H. T. . The behavior of the algorithm follows:<br>
 * <ul>
 * <li>
 * At each step we select a triangle (shown in light blue), find the associated point that is furthest away
 * (coloured differently) and add it to the hull. Then we delete the the selected triangle and any other
 * triangles (shown in yellow) that can see this point.
 * </li>
 *
 * <li>
 * Deleting the triangles makes a hole in the convex hull. Add a triangle to connect the new point to an edge
 * of the hole. The points associated with the deleted triangles are reassigned to the new triangle if they
 * can see it.
 * </li>
 *
 * <li>
 * Repeat for each edge of the hole to finish connecting the new point to the remainder of the old hull.
 * The points associated with the deleted triangles are reassigned to the first new triangle they can see.
 * Points that can't see any of the new triangle are inside the hull and can be discarded.
 * </li>
 *
 * <li>
 * Select a new triangle and repeat.
 * </li>
 * </ul>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class QuickHull {
  
  /**
   * Returns the sign of the volume of the tetrahedron formed by a triangle and a vertex.  Volume sign is positive if and only if the vertex is
   * on the negative side of the triangle, where the positive side is
   * determined by the <a href="https://en.wikipedia.org/wiki/Right-hand_rule">right hand rule</a>.  So the volume is positive if the ccw
   * normal to points outside the tetrahedron.  The final fewer-multiplications form is due to Bob Williamson.
   * @param polygon the face of the mesh (should be a triangle)
   * @param p the vertex that form the tetrahedron.
   * @return -1, 0 or1 if vertex is respectively on the negative, coplanar or positive side of the triangle according to the right hand rule.
   */
  private static int volumeSign(Polygon3D<?> polygon, Point3D p){

    Point3D v1 = polygon.getVertices().get(0);
    Point3D v2 = polygon.getVertices().get(1);
    Point3D v3 = polygon.getVertices().get(2);

    double ax = v1.getX() - p.getX();
    double ay = v1.getY() - p.getY();
    double az = v1.getZ() - p.getZ();
    double bx = v2.getX() - p.getX();
    double by = v2.getY() - p.getY();
    double bz = v2.getZ() - p.getZ();
    double cx = v3.getX() - p.getX();
    double cy = v3.getY() - p.getY();
    double cz = v3.getZ() - p.getZ();

    // Calcul du volume du tétraedre créé
    double vol = ax * (by * cz - bz * cy)
                 + ay * (bz * cx - bx * cz)
                 + az * (bx * cy - by * cx);

    // Retourne le signe du volume
    if (vol > 0) {
      return 1;
    } else if (vol < 0) {
      return -1;
    } else {
      return 0;
    }
  }

 
  /**
   * Compute the Akl-Toussain points from a set of points.
   * @param points the set of points.
   * @return the Akl-Toussain points.
   */
  private static <T extends Point3D> Point3DContainer<T> aklToussaintVertices(Point3DContainer<T> points){
    
    Point3DContainer<T> aklToussaintPoints = null;
    double xmin                     = 0.0d;
    double xmax                     = 0.0d;
    double ymin                     = 0.0d;
    double ymax                     = 0.0d;
    double zmin                     = 0.0d;
    double zmax                     = 0.0d;
    T pt                            = null;
    
    if ((points == null) ||(points.size() < 1)){
      return null;
    }
   
    pt = points.get(0);
    xmin = pt.getX();
    xmax = pt.getX();
    ymin = pt.getY();
    ymax = pt.getY();
    zmin = pt.getZ();
    zmax = pt.getZ();
    
    // Determination des maximaux / minimaux
    for(int i = 1; i < points.size(); i++ ){
      pt = points.get(i);
      
      if (pt.getX() < xmin){
        xmin = pt.getX();
      }
      
      if (pt.getY() < ymin){
        ymin = pt.getY();
      }
      
      if (pt.getZ() < zmin){
        zmin = pt.getZ();
      }
      
      if (pt.getX() > xmax){
        xmax = pt.getX();
      }
      
      if (pt.getY() > ymax){
        ymax = pt.getY();
      }
      
      if (pt.getZ() > zmax){
        zmax = pt.getZ();
      }
      
      pt = null;
    }
    
    // Deuxieme parcours des points pour determiner les maximaux / minimaux
    aklToussaintPoints = JeometryFactory.createPoint3DContainer();
    for(int i = 1; i < points.size(); i++ ){
      pt = points.get(i);
      
      if (pt.getX() <= xmin){
        aklToussaintPoints.add(pt);
      } else if (pt.getY() <= ymin){
        aklToussaintPoints.add(pt);
      } else if (pt.getZ() <= zmin){
        aklToussaintPoints.add(pt);
      } else if (pt.getX() >= xmax){
        aklToussaintPoints.add(pt);
      } else if (pt.getY() >= ymax){
        aklToussaintPoints.add(pt);
      } else if (pt.getZ() >= zmax){
        aklToussaintPoints.add(pt);
      }
    }
    
    return aklToussaintPoints;
  }

  /**
   * This method compute the akl-toussaint points. These points are
   * the points who have a maximal or minimal coordinate. These points are used
   * in the computation of akl-toussaint heuristic for the computation of a
   * bounding box. This heuristic enable to exclude from the final convex hull
   * computation all points contained in the convex hull of the akl-toussaint points.<br>
   * http://en.wikipedia.org/wiki/Convex_hull#Akl-Toussaint_heuristics<br>
   * S.G Akl and G. T. Toussaint, "A Fast convex hull algorithm", Inform, Proc, Lat, 1978
   * @param points the points
   * @param <T> the point class
   * @return the akl-toussaint points.
   */
  private static <T extends Point3D> Point3DContainer<T> computeAklToussainPoints(Point3DContainer<T> points){
    
    Point3DContainer<T> aklToussaintPoints = null;
    Point3DContainer<T> filteredVertices   = null;
    T pt                       = null;
    
    Mesh<T> convexHull = null;
    
    aklToussaintPoints = aklToussaintVertices(points);
     
    // Calcul de la convex hull sur les points de akl-toussaint
    if (aklToussaintPoints != null){
      
      convexHull = computeConvexHull(aklToussaintPoints, false);
      
      if (convexHull != null){
        filteredVertices = JeometryFactory.createPoint3DContainer();
        for(int i = 0; i < points.size(); i++){
          pt = points.get(i);
          if (!Geom3D.contains(convexHull, pt)){
            filteredVertices.add(pt);
          }
          pt = null;
        }
      }
    }
    
    aklToussaintPoints = null;
    convexHull         = null;
    
    return filteredVertices;
  }
  

  /**
   * Add a vertex to a convex hull.  Determine all faces visible from
   * the vertex.  If none are visible then the point is marked as
   * inside the hull.  Delete the visible faces and construct faces
   * between the vertex and the edges that border the visible
   * faces.
   * @param convexHull the convex hull to which add vertex.
   * @param vertex the vertex to add.
   */
  private static <T extends Point3D> void addVertexToConvexHull(Mesh<T> convexHull, T vertex){

    // Cette fonction détermine toutes les faces visibles depuis le point
    // passé en paramètre. Si aucune face n'est visible, le point est à
    // l'intérieur du polyedre. Dans le cas contraire, il est à l'extérieur.
    // Les faces visibles par le point sont supprimées puis de nouvelles faces
    // sont créées à partir des arrêtes des faces retirées et du point sont
    // ajoutées au polyhèdre.

    List<Edge<T>> visEdges = new LinkedList<Edge<T>>();
    List<Face<T>> visFaces = new LinkedList<Face<T>>();

    // Points formant une face triangulaire
    Point3DContainer<T> points = JeometryFactory.createPoint3DContainer(3);

    // Variables de parcours de l'ensemble des faces
    Iterator<? extends Face<T>> iter = convexHull.getFaces().iterator();

    // Recherche des faces vues par le point passé en paramètre. Si le point ne voit
    // aucune face, il est alors à l'intérieur du polyedre sinon il est à l'extérieur
    while(iter.hasNext()){
      Face<T> face = iter.next();
      try {
        if (volumeSign(face, vertex) < 0){
          visFaces.add(face);
        }
      } catch (Exception e) {

      }
    }

    // Efface les faces vues depuis le point et construit la liste des arrêtes
    // bordant les faces effacées.
    for(int i = 0; i <visFaces.size(); i++){
      Face<T> face = visFaces.get(i);
      deleteVisibleFace(convexHull, face, visEdges);
    }

    // Construit de nouvelles faces à partir des arrêtes trouvées et du point

    for(int i = 0; i< visEdges.size(); i++){
      Edge<T> edge = visEdges.get(i);

      points.add(edge.getVertices().get(0));
      points.add(edge.getVertices().get(1));
      points.add(vertex);
      
      convexHull.addFace(JeometryFactory.createMeshFace(points));
    }
    
    visEdges = null;
    visFaces = null; 
    points   = null;
    iter     = null;
  }


  /**
   * Delete a visible face from the convex hull.  Adjust the list
   * of visible edges accordingly.
   * @param convexHull the convex hull
   * @param face a face visible from a vertex to be deleted
   * @param visibleEdges the list of hull edges visible from a vertex
   * @param <T> the type of the underlying points
   */
  private static <T extends Point3D> void deleteVisibleFace(Mesh<T> convexHull, Face<T> face, List<Edge<T>> visibleEdges){
	  
	Point3DContainer<T> p3dm = face.getVertices();
    
    T v1 = p3dm.get(0);
    T v2 = p3dm.get(1);
    T v3 = p3dm.get(2);
    Edge<T> e1 = JeometryFactory.createMeshEdge(convexHull, v1, v2);
    Edge<T> e2 = JeometryFactory.createMeshEdge(convexHull, v2, v3);
    Edge<T> e3 = JeometryFactory.createMeshEdge(convexHull, v3, v1);
    updateVisibleEdges(e1, visibleEdges);
    updateVisibleEdges(e2, visibleEdges);
    updateVisibleEdges(e3, visibleEdges);
    convexHull.removeFace(face);
  }


  /**
   * Update the visible edge list.  If e is not in the list then add
   * it if it is then delete it from the list
   *
   * @param e - a visible edge
   * @param visibleEdges - a list of edges visible from a vertex
   */
  private static <T extends Point3D> void updateVisibleEdges(Edge<T> e, List<Edge<T>> visibleEdges){

    boolean same = false;

    for (Iterator<? extends Edge<T>> f = visibleEdges.iterator(); f.hasNext();){
      Edge<T> edge = f.next();
      if (e.equals(edge)){
        same = true;
        e = edge;
        break;
      }
    }
    if (same){
      visibleEdges.remove(e);
    } else{
      visibleEdges.add(e);
    }
  }

  
  /**
   * Constructs the convex hull of a set of vertices. This method is the implementation of the
   * algorithm given in:
   * <i>"The Quickhull Algorithm for Convex Hulls." ACM Trans. Mathematical Software 22, 469-483, 1996.</i>
   * by Barber, C. B.; Dobkin, D. P.; and Huhdanpaa, H. T.. The behavior of the algorithm follows:<br>
   * <ul>
   * <li>
   * At each step we select a triangle (shown in light blue), find the associated point that is furthest away
   * (coloured differently) and add it to the hull. Then we delete the the selected triangle and any other
   * triangles (shown in yellow) that can see this point.
   *
   * <li>
   * Deleting the triangles makes a hole in the convex hull. Add a triangle to connect the new point to an edge
   * of the hole. The points associated with the deleted triangles are reassigned to the new triangle if they
   * can see it.
   *
   * <li>
   * Repeat for each edge of the hole to finish connecting the new point to the remainder of the old hull.
   * The points associated with the deleted triangles are reassigned to the first new triangle they can see.
   * Points that can't see any of the new triangle are inside the hull and can be discarded.
   *
   * <li>
   * Select a new triangle and repeat.
   * </ul>
   * @param <T> The type of underlying 3D points
   * @param points the set of points used for computing the convex hull.
   * @param useAklToussaint <code>true</code> if the algorithm has to use Akl-Toussaint heuristic or <code>false</code> otherwise.
   * @return the computed convex hull represented as a {@link Mesh}.
   */
  public static <T extends Point3D> Mesh<T> computeConvexHull(Point3DContainer<T> points, boolean useAklToussaint){

    // Le polyedre convexe
    Mesh<T> convexHull = JeometryFactory.createMesh();
    
    if (points.size() < 4){
      return null;
    }

    
    // Utilisation de l'heuristique de akl-toussaint
    // Cette heuristique ennonce qu'il est possible de retirer
    // du calculd e l'enveloppe convexe finale tous les points
    // contenu dans l'enveloppe convexe formée par les Points
    // donc une des coordonnée est maximale ou minimale.
    if (useAklToussaint){
      Point3DContainer<T> aklToussaintPoints = computeAklToussainPoints(points);
      
      if (aklToussaintPoints !=  null){
        points = aklToussaintPoints;
      }
    }
    
    Iterator<T> e = points.iterator();

    if (e.hasNext()){
      T v1 = e.next();
      T v2 = null;

      for (; e.hasNext(); ){
        v2 = e.next();
        if (!Geom3D.equals(v1, v2)){
          break;
        }
      }

      T v3 = null;
      Face<T> t = null;
      
      T pt0 = null;
      T pt1 = null;
      T pt2 = null;
      
      List<T> coVerts = new LinkedList<T>();
      for (; e.hasNext(); ){
        v3 = e.next();
        if (Geom3D.collinear(v1, v2, v3)){
          coVerts.add(v3);
        } else {
          pt0 = v1;
          pt1 = v2;
          pt2 = v3;
          t = JeometryFactory.createMeshTriangle(v1, v2, v3);
          break;
        }
      }

      T v4 = null;

      Point3DContainer<T> pointsFace = null;

      for (; e.hasNext(); ){
        v4 = e.next();
        int volSign = volumeSign(t, v4);
        if (volSign == 0){
          coVerts.add(v4);
        } else {

          // Ajout de la face au polyedre convexe
          convexHull.addFace(t);

          // Creation d'un tetrahedre avec la face ajoutée et le sommet
          T tv1 = pt0;
          T tv2 = pt1;
          T tv3 = pt2;

          // Range les sommets dans l'ordre CCW en inversant l'ordre des sommets
          // si le sens est négatif
          if (volSign < 0 ){
            t.inverseVerticesOrder();
            T tv = tv1;
            tv1 = tv3;
            tv3 = tv;
          }

          pointsFace = JeometryFactory.createPoint3DContainer(3);
          pointsFace.add(tv3);
          pointsFace.add(tv2);
          pointsFace.add(v4);
          convexHull.addFace(JeometryFactory.createMeshFace(pointsFace));

          pointsFace = JeometryFactory.createPoint3DContainer(3);
          pointsFace.add(tv2);
          pointsFace.add(tv1);
          pointsFace.add(v4);
          convexHull.addFace(JeometryFactory.createMeshFace(pointsFace));

          pointsFace = JeometryFactory.createPoint3DContainer(3);
          pointsFace.add(tv1);
          pointsFace.add(tv3);
          pointsFace.add(v4); 
          convexHull.addFace(JeometryFactory.createMeshFace(pointsFace));
          break;
        }
      }


      // Add vertices to the hull one at a time
      for (; e.hasNext(); ){
        addVertexToConvexHull(convexHull, e.next());
      }

      // Reprocess the previously found co-linear/planar vertices
      if (convexHull.getFaces().size() > 0){
        for (e = coVerts.iterator();e.hasNext(); ){
          addVertexToConvexHull(convexHull, e.next());
        }
      } else {
        return null;
      }
    }

    return convexHull;
  }

  

  
}
