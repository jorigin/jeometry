package org.jeometry.geom3D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Box;
import org.jeometry.geom3D.primitive.Polygon3D;
import org.jeometry.geom3D.primitive.Triangle;


/**
 * This classes provides various 3D geometry algorithm and functions.<br>
 * <br>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class Geom3D {

  /**
   * The 0 limit. Under this value, a value is considered as equal to 0
   */
  public static double ZERO_LIMIT = 0.00000001d;

  /**
   * The numeric precision used in value comparison.
   */
  public static double NUMERIC_PRECISION = 0.000001d;

  /**
   * The 0 limit. Under this value, a value is considered as equal to 0
   */
  public static double EPSILON = 0.00000001d;
  
  /**
   * Return <code>true</code> if the three points given in parameter are aligned.
   * @param p1 the first point.
   * @param p2 the second point.
   * @param p3 the third point.
   * @return <code>true</code> if the three points are aligned and <code>false</code> otherwise.
   */
  public static boolean collinear(Point3D p1, Point3D p2, Point3D p3){
    double x1 = p1.getX();
    double y1 = p1.getY();
    double z1 = p1.getZ();
    double x2 = p2.getX();
    double y2 = p2.getY();
    double z2 = p2.getZ();
    double x3 = p3.getX();
    double y3 = p3.getY();
    double z3 = p3.getZ();

    return (z3 - z1) * (y2 - y1) - (z2 - z1) * (y3 - y1) == 0 &&
           (z2 - z1) * (x3 - x1) - (x2 - x1) * (z3 - z1) == 0 &&
           (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1) == 0;
  }


  /**
   * Check if the given two points are equals (i-e) are at the same location. 
   * points coordinates are considered as equals if their difference lies under the {@link #NUMERIC_PRECISION numeric precision}.
   * @param p1 the first point.
   * @param p2 the second point.
   * @return <code>true</code> is the two points are considered as equals and <code>false</code> otherwise.
   * @see #equals(Point3D, Point3D, double)
   * @see #NUMERIC_PRECISION
   */
  public static boolean equals(Point3D p1, Point3D p2){
    return equals(p1, p2, NUMERIC_PRECISION);
  }

  /**
   * Check if the given two points are equals (i-e) are at the same location. 
   * points coordinates are considered as equals if their difference lies under the given <code>precision</code>.
   * @param p1 the first point.
   * @param p2 the second point.
   * @param precision the numeric precision to use.
   * @return <code>true</code> is the two points are considered as equals and <code>false</code> otherwise.
   * @see #equals(Point3D, Point3D)
   */
  public static boolean equals(Point3D p1, Point3D p2, double precision){
    boolean r = p1 == p2;
    if (!r) {
      r = ( (Math.abs(p1.getX() - p2.getX())) < precision) &&
          ( (Math.abs(p1.getY() - p2.getY())) < precision) &&
          ( (Math.abs(p1.getZ() - p2.getZ())) < precision);
      }
    return r;
  }

  /**
   * Compute and return the normal vector for the polygon given in parameter.
   * This method need three points for the computation of the normal. The points used are:
   * - P1: The first vertex of the polygon.<br>
   * - P2: The vertex in the middle of the vertices list obtained by polygon.getVertices().get(polygon.getVertices()/2)<br>
   * - P3: The last vertex of the polygon.<br>
   * The normal is the vector obtained by the computation of the vectorial product of P1P2 and P2P3.
   * @param polygon IPolygone3D A planar polygon
   * @return IPoint3D The normal vector of the polygon
   */
  public static Point3D normal(Polygon3D<?> polygon){

    Point3D normal = null;
    Point3D p1p2 = null;
    Point3D p2p3 = null;

    // Si le polygone est nul ou compte moins de 3 points, il n'y a pas de normale
    if ((polygon == null)||(polygon.getVertices().size() < 3)){
      return null;
    }


    // Calcul du vecteur formé par le premier points du polygone et le point du milieu
    // de la liste des sommets
    p1p2 = polygon.getVertices().get(polygon.getVertices().size() / 2).minus(polygon.getVertices().get(0));

    // Calcul du vecteur formé par le point du milieu de la liste des sommets et
    // le dernier sommet du polygone
    p2p3 = polygon.getVertices().get(polygon.getVertices().size() - 1).minus(polygon.getVertices().get(polygon.getVertices().size() / 2));

    // Calcul du produit vectoriel
    normal = p1p2.cross(p2p3);

    return normal;
  }
  
  /**
   * Compute and return the barycenter of the given {@link Point3D points} collection.
   * @param points the {@link Point3D points} collection to process
   * @return the barycenter of the points collection.
   */
  public static Point3D computeBarycenter(Point3DContainer<?> points){
    Point3D barycenter = null;
    if ((points != null)&&(points.size() >= 1)){
      barycenter = JeometryFactory.createPoint3D();
      barycenter.setX(0.0d);
      barycenter.setY(0.0d);
      barycenter.setZ(0.0d);
      Iterator<? extends Point3D> iter = points.iterator();
      while(iter.hasNext()){
        barycenter = barycenter.plus(iter.next());
      }
      barycenter = barycenter.divide(points.size());
    }  

    return barycenter;
  }

  /**
   * Compute and return the volume of a box given in parameter.
   * @param box the box to use
   * @return the volume of the box, 0 if the parameter is <code>null</code>
   */
  public static double volume(Box box){
    if (box == null){
      return 0.0d;
    } else {
      return box.getHeight()*box.getWidth()*box.getHeight();
    }
  }
  
  /**
   * Compute and return the volume of the mesh given in parameter. The mesh
   * must be convex and each face must be a convex polygon.
   * @param mesh the convex mesh.
   * @return double the volume of the mesh given in parameter
   */
  public static double volume(Mesh<?> mesh){

    // Volume du polthedre
    double volume = 0.0d;

    // Points composant une face (les faces doivent être triangulaires)
    Point3D p1 = null;
    Point3D p2 = null;
    Point3D p3 = null;

    double bxdx = 0.0d;
    double bydy = 0.0d;
    double bzdz = 0.0d;
    double cxdx = 0.0d;
    double cydy = 0.0d;
    double czdz = 0.0d;
    
    // Barycentre du polygone
    Point3D barycenter = null;

    // Iterateur pour parcourir la liste de face
    Iterator<? extends Face<?>> iter = null;

    // La face courante
    Face<?> currentFace = null;

    // La methode consiste à ajouter tous les volumes de tétrahèdres formés par
    // une face du polyhedre et son barycentre. Ceci est valable seulement si les
    // faces sont des triangles.
    Point3DContainer<?> vertices = null;

    // Le polyhedre doit être non null et avoir au moins 4 faces.
    if ((mesh != null)&&(mesh.getFaces().size() > 3)){

//      System.out.println("Faces: "+polyhedron.getFaces().size());
      
      // Calcul du barycentre du polyhedre
      barycenter = Geom3D.computeBarycenter(mesh.getVertices());

      // Parcours itéré de la liste des faces
      iter = mesh.getFaces().iterator();
   
      while(iter.hasNext()){

        // Recuperation de la face courante
        currentFace = iter.next();

        vertices = currentFace.getVertices();
        
        // Une face doit avoir au moins 3 points
        if (vertices.size() < 3){
          volume = -1.0d;
          break;
        }

        p1 = vertices.get(0);
        
        // On parcours les sommets par groupe de 3 avec le premier
        for (int i = 1; i < vertices.size() - 1; i++){
          p2 = vertices.get(i);
          p3 = vertices.get(i+1);
          
          // Calcul du volume pour le tetrahedre formé par p1, p2, p3, barycentre
          bxdx = p2.getX() - barycenter.getX();
          bydy = p2.getY() - barycenter.getY();
          bzdz = p2.getZ() - barycenter.getZ();
          cxdx = p3.getX() - barycenter.getX();
          cydy = p3.getY() - barycenter.getY();
          czdz = p3.getZ() - barycenter.getZ();

          // Le volume est la somme des volumes des tetrahedres
          volume += ( (p1.getZ() - barycenter.getZ())*(bxdx*cydy-bydy*cxdx)
                    + (p1.getY() - barycenter.getY())*(bzdz*cxdx-bxdx*czdz)
                    + (p1.getX() - barycenter.getX())*(bydy*czdz-bzdz*cydy)) / 6;
        
          p2          = null;
          p3          = null;
        }
        
        p1          = null;
        vertices    = null;
        currentFace = null;
      }
      iter = null;
      barycenter  = null;
    }

    return volume;
  }

  /**
   * Compute if a 3D point <code>p3d</code> given in parameter is inside a convex polyhedron represented by the given <code>mesh</code>. 
   * A point is inside a convex polyhedron if it is on the invisible side of all its faces. 
   * The computation of all the normals of the face and the scalar product with the vector given by the point
   * <code>p3d</code> and the barycenter of the face show if the point is on the visible side.
   * @param polyhedron the mesh that represents a convex polyhedron.
   * @param p3d the point 3D to check.
   * @return boolean <code>true</code> if the point is inside the polyhedron, <code>false</code> otherwise
   */
  public static boolean contains(Mesh<?> polyhedron, Point3D p3d){
    boolean contains = true;

    // Vecteur composé par le point donné en paramêtre et le barycentre de
    // de la face courante du polyhedre.
    Point3D vector;

    // Variables de parcours de la liste des faces
    Iterator<? extends Face<?>> iter = null;
    Face<?> currentFace = null;

    if ((polyhedron != null) && (polyhedron.getFaces().size() > 0)){

      // Pour chaque face du polyhedre, le produit scalaire entre le vecteur normal
      // de la face est le vecteur donné par le barycenytre des points de la face
      // et le point donné en paramêtre est calculé. Si le produit scalaire est
      // positif le point est du coté de la face visible (et donc en dehors)
      // du polyhedre. Si le produit scalaire est négatif ou nul, le point est à
      // l'intérieur.
      iter = polyhedron.getFaces().iterator();
      while(iter.hasNext() && (contains == true)){

        currentFace = iter.next();

        // Calcul du vector par rapport au point pour cette face
        vector = Geom3D.computeBarycenter(currentFace.getVertices()).minus(p3d);

        contains = contains && (vector.dot(Geom3D.normal(currentFace)) >= 0);
      }
    } else{
      contains = false;
    }
    return contains;
  }

  /**
   * Check if the given {@link Box box} contains 
   * the given {@link SpatialLocalization3D spatial localization}.
   * @param box box the {@link Box box} to check.
   * @param spatial the {@link SpatialLocalization3D spatial localization} to check.
   * @return <code>true</code> if the box contains the {@link SpatialLocalization3D spatial localization}, <code>false</code> otherwise.
   */
  public static boolean contains(Box box, SpatialLocalization3D spatial){
    if (spatial != null){
      if (spatial instanceof Point3D){
        return contains(box, (Point3D) spatial);
      } else if (spatial instanceof Triangle){
        Point3DContainer<?> pts = ((Triangle<?>) spatial).getVertices();
        return contains(box, pts);
      }
    }
    
    return false;
  }
  
  /**
   * Check if the given {@link Box box} contains the given {@link Point3D point}.<br><br>
   * Please not that to conform to algebric semantic of <i>contains</i>, a <code>null</code> box cannot contains any points, on the other hand, a not <code>null</code> box 
   * always contains the <code>null</code> point.
   * @param box the {@link Box box} to check.
   * @param p3d the {@link Point3D point} to check.
   * @return <code>true</code> if the box contains the point, <code>false</code> otherwise.
   */
  public static boolean contains(Box box, Point3D p3d){
    if (box != null){
      if (p3d != null){
        
        Point3D max = box.getMax();
        Point3D min = box.getMin();
        
        return    p3d.getX() >= min.getX() && p3d.getX() <= max.getX()
               && p3d.getY() >= min.getY() && p3d.getY() <= max.getY()
               && p3d.getZ() >= min.getZ() && p3d.getZ() <= max.getZ();
      } else {
        return true;
      }
    } else {
      return false;
    }
  }
  
  /**
   * Check if the given {@link Box box} contains all the points presents 
   * within the given {@link Point3DContainer point manager}.
   * @param box the {@link Box box} to check.
   * @param points the {@link Point3DContainer point manager} to check.
   * @return <code>true</code> if the box contains all the points, <code>false</code> otherwise.
   */
  public static boolean contains(Box box, Point3DContainer<? extends Point3D> points){
    
    if (points != null){
      
      boolean ok = true;
      
      Iterator<? extends Point3D> iter = points.iterator();
      while(iter.hasNext()&&ok){
        ok = contains(box, iter.next());
      }
      
      return ok;
    }
    
    return false;
  }
  
  /**
   * Compute if a 3D point cloud <code>points</code> given in parameter is inside the convex polyhedron represented by the given <code>mesh</code>. 
   * A point is inside a convex polyhedron if it is on the invisible side of all the faces of the polyhedron. The computation of all the
   * normals of the face and the scalar product with the vector given by the point <code>p3d</code> and the barycenter of the face show if the point is on the
   * visible side.
   * @param polyhedron the polyhedron
   * @param points the points that should all be in the polyedron
   * @return <code>true</code> if all points are in the polyhedron, <code>false</code> otherwise.
   */
  public static boolean contains(Mesh<?> polyhedron, Point3DContainer<?> points){
    boolean contains       = true;
   
    Point3D[] normals     = null;
    Point3D[] barycenters = null;
    
    Iterator<? extends Face<?>> iter   = null;
    
    Face<?> currentFace      = null;
    
    Point3D p3d           = null;
    
    int i = 0;
    int j = 0;
    
    if (polyhedron == null){
      return false;
    }
    
    if ((points == null) || (points.size() == 0)){
      return true;
    }
    
    // Calcul des normales et des barycentres des faces.
    normals     = new Point3D[polyhedron.getFaces().size()];
    barycenters = new Point3D[polyhedron.getFaces().size()];
    i = 0;
    iter = polyhedron.getFaces().iterator();
    while(iter.hasNext()){
      currentFace    = iter.next();
      normals[i]     = Geom3D.normal(currentFace);
      barycenters[i] = Geom3D.computeBarycenter(currentFace.getVertices());
      i++;
      currentFace = null;
    }
    
    // Parcours de la liste des points et calcul du scalaire
    j = 0;
    while((j < points.size()) && (contains)){
      // Parcours de la liste des normales / barycentres
      i = 0;
      while((i < normals.length) &&(contains)){
        contains = contains && (barycenters[i].minus(p3d).dot(normals[i]) >= 0);
      }
      
      j++;
    }
    
    return contains;
  }
  

  /**
   * Check if the given {@link Box box} intersects the given 
   * {@link SpatialLocalization3D spatial localization}. This method is computed at this time for the following 
   * items:
   * <ul>
   * <li>{@link Point3D Point3D}</li>
   * <li>{@link Triangle triangle}</li>
   * </ul>
   * @param box {@link Box box} the box to check.
   * @param spatial the {@link SpatialLocalization3D spatial localization} to check.
   * @return <code>true</code> if the given {@link Box box} geometry intersects the given 
   * {@link SpatialLocalization3D spatial localization} geometry and <code>false</code> otherwise.
   */
  public static boolean isIntersect(Box box, SpatialLocalization3D spatial){
    if ((box != null)&&(spatial != null)){
      if (spatial instanceof Point3D){
        return isIntersect(box, (Point3D) spatial);
      } else if (spatial instanceof Triangle){
        return isIntersect(box, (Triangle<?>) spatial);
      }
    }
    
    return false;
  }
  
  /**
   * Check if the given {@link Triangle triangle} intersect 
   * the given {@link Box box}.
   * @param box the box to check.
   * @param triangle the triangle to check.
   * @return <code>true</code> if the given triangle intersect the box and <code>false</code> otherwise.
   */
  public static boolean isIntersect(Box box, Triangle<?> triangle){
    
    if ((box != null)||(triangle != null)){
   
      
      // Triangle is on the "right" of the box
      if (   (triangle.getVertex1().getX() > box.getMax().getX())
          && (triangle.getVertex2().getX() > box.getMax().getX())
          && (triangle.getVertex3().getX() > box.getMax().getX())){
            return false;
      }
      
      // Triangle is on the "left" of the box
      if (   (triangle.getVertex1().getX() < box.getMin().getX())
          && (triangle.getVertex2().getX() < box.getMin().getX())
          && (triangle.getVertex3().getX() < box.getMin().getX())){
            return false;
      }
      
      // Triangle is in "above" the box
      if (   (triangle.getVertex1().getY() > box.getMax().getY())
          && (triangle.getVertex2().getY() > box.getMax().getY())
          && (triangle.getVertex3().getY() > box.getMax().getY())){
            return false;
      }
      
      // Triangle is "below" the box
      if (   (triangle.getVertex1().getZ() < box.getMin().getZ())
          && (triangle.getVertex2().getZ() < box.getMin().getZ())
          && (triangle.getVertex3().getZ() < box.getMin().getZ())){
            return false;
      }
      
      // Triangle is in "front" of the box
      if (   (triangle.getVertex1().getZ() > box.getMax().getZ())
          && (triangle.getVertex2().getZ() > box.getMax().getZ())
          && (triangle.getVertex3().getZ() > box.getMax().getZ())){
            return false;
      }
      
      // Triangle is "behind" the box
      if (   (triangle.getVertex1().getY() < box.getMin().getY())
          && (triangle.getVertex2().getY() < box.getMin().getY())
          && (triangle.getVertex3().getY() < box.getMin().getY())){
            return false;
      }
      
      // The first vertex of the triangle lies within the box
      if (   (triangle.getVertex1().getX() <= box.getMax().getX())
          && (triangle.getVertex1().getX() >= box.getMin().getX())
          && (triangle.getVertex1().getY() <= box.getMax().getY())
          && (triangle.getVertex1().getY() >= box.getMin().getY())
          && (triangle.getVertex1().getZ() <= box.getMax().getZ())
          && (triangle.getVertex1().getZ() >= box.getMin().getZ())){
        return true;
        
      // The second vertex of the triangle lies within the box  
      } else if (   (triangle.getVertex2().getX() <= box.getMax().getX())
                 && (triangle.getVertex2().getX() >= box.getMin().getX())
                 && (triangle.getVertex2().getY() <= box.getMax().getY())
                 && (triangle.getVertex2().getY() >= box.getMin().getY())
                 && (triangle.getVertex2().getZ() <= box.getMax().getZ())
                 && (triangle.getVertex2().getZ() >= box.getMin().getZ())){
        return true;
        
     // The third vertex of the triangle lies within the box  
      } else if (   (triangle.getVertex3().getX() <= box.getMax().getX())
                 && (triangle.getVertex3().getX() >= box.getMin().getX())
                 && (triangle.getVertex3().getY() <= box.getMax().getY())
                 && (triangle.getVertex3().getY() >= box.getMin().getY())
                 && (triangle.getVertex3().getZ() <= box.getMax().getZ())
                 && (triangle.getVertex3().getZ() >= box.getMin().getZ())){
        return true;
        
      }
 
      Collection<Point3D> boxpoints = new ArrayList<Point3D>(8);
      Point3D boxCenter = JeometryFactory.createPoint3D((box.getMin().getX()+box.getMax().getX())/2.0d, 
                                               (box.getMin().getY()+box.getMax().getY())/2.0d, 
                                               (box.getMin().getZ()+box.getMax().getZ())/2.0d);
      //IPoint3D boxCenter = Geometry.newPoint3D(0.0, 0.0, 0.0);
      
      Point3D translatedBoxMin = box.getMin().minus(boxCenter);
      Point3D translatedBoxMax = box.getMax().minus(boxCenter);
      
      boxpoints.add(JeometryFactory.createPoint3D(box.getMax().minus(boxCenter).getX(), box.getMax().minus(boxCenter).getY(), box.getMax().minus(boxCenter).getZ()));
      boxpoints.add(JeometryFactory.createPoint3D(box.getMax().minus(boxCenter).getX(), box.getMax().minus(boxCenter).getY(), box.getMin().minus(boxCenter).getZ()));
      boxpoints.add(JeometryFactory.createPoint3D(box.getMax().minus(boxCenter).getX(), box.getMin().minus(boxCenter).getY(), box.getMax().minus(boxCenter).getZ()));
      boxpoints.add(JeometryFactory.createPoint3D(box.getMax().minus(boxCenter).getX(), box.getMin().minus(boxCenter).getY(), box.getMin().minus(boxCenter).getZ()));
      boxpoints.add(JeometryFactory.createPoint3D(box.getMin().minus(boxCenter).getX(), box.getMax().minus(boxCenter).getY(), box.getMax().minus(boxCenter).getZ()));
      boxpoints.add(JeometryFactory.createPoint3D(box.getMin().minus(boxCenter).getX(), box.getMax().minus(boxCenter).getY(), box.getMin().minus(boxCenter).getZ()));
      boxpoints.add(JeometryFactory.createPoint3D(box.getMin().minus(boxCenter).getX(), box.getMin().minus(boxCenter).getY(), box.getMax().minus(boxCenter).getZ()));
      boxpoints.add(JeometryFactory.createPoint3D(box.getMin().minus(boxCenter).getX(), box.getMin().minus(boxCenter).getY(), box.getMin().minus(boxCenter).getZ()));
  
      
      List<Point3D> tripoints = new ArrayList<Point3D>(3);
      tripoints.add(triangle.getVertex1().minus(boxCenter));
      tripoints.add(triangle.getVertex2().minus(boxCenter));
      tripoints.add(triangle.getVertex3().minus(boxCenter));
     
      
      // test the x, y, and z axes
      if (!isIntersect(boxpoints, tripoints, JeometryFactory.createPoint3D(translatedBoxMax.getX() - translatedBoxMin.getX(), 0, 0))){
        return false;
      }
      
      if (!isIntersect(boxpoints, tripoints, JeometryFactory.createPoint3D(0, translatedBoxMax.getY() - translatedBoxMin.getY(), 0))){
        return false;
      }
      
      if (!isIntersect(boxpoints, tripoints, JeometryFactory.createPoint3D(0, 0, translatedBoxMax.getZ() - translatedBoxMin.getZ()))){
        return false;
      }

      // test the triangle normal
      Point3D triedge1  = tripoints.get(1).minus(tripoints.get(0));
      Point3D triedge2  = tripoints.get(2).minus(tripoints.get(1));
      Point3D trinormal = triedge1.cross(triedge2);
      if (!isIntersect(boxpoints, tripoints, trinormal)){ 
        return false;
      }

      // test the 9 edge cross products
      Point3D triedge3 = tripoints.get(0).minus(tripoints.get(2));

      Point3D boxedge1 = JeometryFactory.createPoint3D(translatedBoxMax.getX() - translatedBoxMin.getX(), 0, 0);
      Point3D boxedge2 = JeometryFactory.createPoint3D(0, translatedBoxMax.getY() - translatedBoxMin.getY(), 0);
      Point3D boxedge3 = JeometryFactory.createPoint3D(0, 0, translatedBoxMax.getZ() - translatedBoxMin.getZ());

      if (!isIntersect(boxpoints, tripoints, boxedge1.cross(triedge1))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge1.cross(triedge2))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge1.cross(triedge3))) return false;
 
      if (!isIntersect(boxpoints, tripoints, boxedge2.cross(triedge1))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge2.cross(triedge2))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge2.cross(triedge3))) return false;
   
      if (!isIntersect(boxpoints, tripoints, boxedge3.cross(triedge1))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge3.cross(triedge2))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge3.cross(triedge3))) return false;
      
      // Inverted normals
      triedge1  = tripoints.get(2).minus(tripoints.get(0));
      triedge2  = tripoints.get(1).minus(tripoints.get(2));
      trinormal = triedge1.cross(triedge2);
      if (!isIntersect(boxpoints, tripoints, trinormal)){ 
        return false;
      }

      // test the 9 edge cross products with inverted normals
      triedge3 = tripoints.get(0).minus(tripoints.get(1));

      if (!isIntersect(boxpoints, tripoints, boxedge1.cross(triedge1))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge1.cross(triedge2))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge1.cross(triedge3))) return false;
 
      if (!isIntersect(boxpoints, tripoints, boxedge2.cross(triedge1))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge2.cross(triedge2))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge2.cross(triedge3))) return false;
   
      if (!isIntersect(boxpoints, tripoints, boxedge3.cross(triedge1))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge3.cross(triedge2))) return false;
      if (!isIntersect(boxpoints, tripoints, boxedge3.cross(triedge3))) return false;
      
      return true;
    }

    return false;
  }
  
  /**
   * This method check if a {@link Point3D 3D point} intersects 
   * the given {@link Box box}. 
   * From a formal point of view, checking if a point intersects a box is equivalent to checking if the bonx contains the point. 
   * For this reason this method delegates to {@link #contains(Box, Point3D)} method for computation.
   * @param box the {@link Box box} to check.
   * @param point the {@link Point3D 3D point} to check.
   * @return <code>true</code> if the point intersect the box and <code>false</code> otherwise.
   * @see #contains(Box, Point3D)
   */
  public static boolean isIntersect(Box box, Point3D point){
    return contains(box, point);
  }

  /**
   * Test if a the points given in parameter are coplanar.
   * @param p3dm Point3DManagerI the set of points to test
   * @return boolean true if the points are coplanar, false otherwise
   */
  public static boolean coplanar(Point3DContainer<? extends Point3D> p3dm) {

    boolean test = false;

    Point3D point    = null;
    Point3D vector1  = null;
    Point3D vector2  = null;
    Point3D vector3  = null;

    double norm = 0.0d;
    double d    = 0.0d;

    // Trois points sont forcement coplanaires
    if (p3dm.size() < 4){
      return true;
    }

    // Calcul du vecteur entre le premier et le deuxième point
    point   = p3dm.get(0);
    vector1 = p3dm.get(1).minus(point);


    // Recherche d'un vecteur normal de référence
    for(int i=2;i<p3dm.size();i++){

      // Calcul du vecteur formé par le ieme point et le premier
      vector2 = p3dm.get(i).minus(point);

      vector3 = vector1.cross(vector2);

      norm = vector3.norm();

      if(norm>ZERO_LIMIT){
        break;
      }
    }

    // Calcul du produit scalaire entre  le vecteur normal de reference et le premier point
    d = Math.abs(vector3.dot(point));
    //System.out.println("d = "+d);

    test = true;

    for(int i=1;i<p3dm.size();i++){
      if(Math.abs(vector3.dot(p3dm.get(i)))-d>ZERO_LIMIT){
        test = false;
        break;
      }
    }
    return test;
  }

  /**
   * Compute the farthest points in the point manager given in parameter. If the point manager
   * is <code>null</code> or if it contains less than 2 points, <code>null</code> is returned.
   * @param p3dm the point manager used for computation
   * @return an array containing the farthest points of the manager.
   */
  public static Point3D[] farthestPoints(Point3DContainer<? extends Point3D> p3dm){
    Point3D[] result  = null;
    Point3D p1        = null;
    Point3D p2        = null;
    double distance    = 0.0d;
    double distanceMax = 0.0d;
    
    
    if ((p3dm == null) || (p3dm.size() < 2)){
      return null;
    } else {
      result = new Point3D[2];
      p1       = null;
      p2       = null;
      
      // Toutes les distances sont calculées et comparées
      for(int i = 0; i < p3dm.size(); i++){
        p1 = p3dm.get(i);
        for(int j = i+1; j < p3dm.size(); j++){
          p2 = p3dm.get(j); 
          if (p1 != p2){
            distance = p1.distance(p2);
            if (distance > distanceMax){
              distanceMax = distance;
              result[0] = p1;
              result[1] = p2;
            }
          }
        } 
      }
    } 
    p1 = null;
    p2 = null;
    return result;
  }


  /**
   * Return the point of the manager given in parameter that is the furthest from the polygon
   * given in parameter. The distance is computed between the barycenter of the polygon and a
   * point.
   * @param polygon IPolygon3D a polygon
   * @param points Point3DManagerI the set of points
   * @return IPoint3D the furthest point from the polygon
   */
  public static Point3D furthest(Polygon3D<?> polygon, Point3DContainer<? extends Point3D> points){

    // Le point courant
    Point3D current = null;

    // Le point le plus éloigné du polygone
    Point3D furthest = null;

    // Le barycentre du polygone
    Point3D barycenter    = null;

    double currentDistance = 0.0d;
    double maxDistance     = 0.0d;

    if ((polygon == null)||(points == null)){
      return null;
    }

    barycenter = computeBarycenter(polygon.getVertices());
    
    // Initialisation avec le premier point considéré comme le plus éloigné
    furthest = points.get(0);
    currentDistance = barycenter.distance(furthest);
    maxDistance = currentDistance;


    // Calcul des distances et verification du maximum
    for(int i = 1; i < points.size(); i++){
      current = points.get(i);
      currentDistance = barycenter.distance(current);

      // Si la distance courant est supérieure à la distance maximale,
      // le point courant devient le point le plus eloigne
      if (currentDistance > maxDistance){
        furthest = current;
        maxDistance = currentDistance;
      }
    }

    return furthest;
  }
  
  /**
   * Compute the euclidean distance between the two given {@link SpatialLocalization3D spatial localization}. 
   * The distance computation method depends on the nature of the items. In most of the case, the returned value is 
   * the minimal distance between the two items. It is assumed that if one the item contains the second or if the two items intersects, 
   * the distance should be considered as <code>0</code>.
   * @param item1 the first {@link SpatialLocalization3D spatial localization}. 
   * @param item2 the second {@link SpatialLocalization3D spatial localization}. 
   * @return the euclidean distance between the two items.
   */
  public static double computeDistance(SpatialLocalization3D item1, SpatialLocalization3D item2){
    return 0.0d;
  }
  
  /**
   * Compute the euclidean distance between the two given 3D coordinates.
   * @param x1 the X coordinate of the point 1.
   * @param y1 the Y coordinate of the point 1.
   * @param z1 the Z coordinate of the point 1.
   * @param x2 the X coordinate of the point 2.
   * @param y2 the Y coordinate of the point 2.
   * @param z2 the Z coordinate of the point 2.
   * @return the euclidean distance between the two given 3D coordinates.
   */
  public static double computeDistance(double x1, double y1, double z1, double x2, double y2, double z2){
	    return Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) + (z2 - z1)*(z2 - z1));
	  }
  
  /**
   * Compute the intersection between the given {@link Triangle triangle} and the ray represented 
   * by the given direction and the given origin.<br><br>
   * This method is an implementation of "Fast, Minimum Storage Ray / Triangle Intersection", <i>Journal of Graphics Tools</i>, 2(1):21--28, 1997 
   * published by Tomas Muller and Ben Trumbore.
   * @param triangle triangle the {@link Triangle triangle} to check.
   * @param dir the direction of the ray.
   * @param orig the origin of the ray.
   * @return the intersection point or <code>null</code> if no intersection is detected.
   * @see #computeIntersection(Triangle, Point3D, Point3D, boolean)
   */
  public static Point3D computeIntersection(Triangle<?> triangle, Point3D dir, Point3D orig){
    return computeIntersection(triangle, dir, orig, false);
  }
  
  /**
   * Compute the intersection between the given {@link Triangle triangle} and the ray represented by the given direction and the given origin.<br><br>
   * This method is an implementation of "Fast, Minimum Storage Ray / Triangle Intersection", <i>Journal of Graphics Tools</i>, 2(1):21--28, 1997 published by Tomas Muller and Ben Trumbore.<br><br>
   * Lets a triangle defined by its 3 vertices (V<sub>0</sub>, V<sub>1</sub>, V<sub>2</sub>). Lets <i>R</i> a ray defined by its origin <i>O</i> and its direction <i>D</i>. A point within a triangle is denoted <i>T(u, v)</i> where <i>u</i> and <i>v</i> are the barycentric coordinates of the point such that:<br>
   * <i>T(u, v)</i>&nbsp;=&nbsp;(1 - <i>u</i> - <i>v</i>)V<sub>0</sub>&nbsp;+&nbsp;<i>u</i>V<sub>1</sub>&nbsp;+&nbsp;<i>v</i>V<sub>2</sub><br>
   * The intersection between the triangle and the ray is given by the following formula:<br>
   * O + <i>t</i>D = <i>T(u, v)</i><br>
   * where <i>t</i> is the distance of the intersection from the ray origin along the ray direction.<br>
   * @param triangle the {@link Triangle triangle} to check.
   * @param dir the direction of the ray.
   * @param orig the origin of the ray.
   * @param cullback is the facing of the triangle is determinant (if <code>true</code>, intersection is discarded if triangle visible face is not facing the ray origin.)
   * @return the intersection point or <code>null</code> if no intersection is detected.
   * @see #computeIntersection(Triangle, Point3D, Point3D)
   */
  public static Point3D computeIntersection(Triangle<?> triangle, Point3D dir, Point3D orig, boolean cullback){
    Point3D result = null;
    
    Point3D edge1 = null;
    Point3D edge2 = null;
    
    Point3D tvec  = null;
    Point3D pvec  = null;
    Point3D qvec  = null;
    
    double det     = 0.0d;
    double invDet  = 0.0d;
    
    double u       = 0.0d;
    double v       = 0.0d;
    double t       = 0.0d;
    
    /* find vectors for two edges sharing vertO */
    edge1 = triangle.getVertex2().minus(triangle.getVertex1());
    edge2 = triangle.getVertex3().minus(triangle.getVertex1());
    
    /* begin calculating determinant - also used to calculate U parameter */
    pvec = dir.cross(edge2);
    
    /* if determinant is near zero, ray lies in plane of triangle */
    det = edge1.dot(pvec);
    
    /* If the back culling is used */
    if (cullback){
      
      if (det < EPSILON){
        return null;
      }
      
      /* calculate distance from vertex 1 to ray origin */
      tvec = orig.minus(triangle.getVertex1());

      /* calculate U parameter and test bounds */
      u = tvec.dot(pvec);
      if ((u < 0.0) || (u > det)){
        return null;
      }
      
      /* prepare to test V parameter */
      qvec = tvec.cross(edge1);
      
      /* calculate V parameter and test bounds */
      v = dir.dot(qvec);
      if ((v < 0.0) || ((u + v) > det)){
        return null;
      }
      
      /* calculate t, scale parameters, ray intersects triangle */
      t = edge2.dot(qvec);
      
      invDet = 1.0 / det;
      t *= invDet;
      u *= invDet;
      v *= invDet;

    } else {
      
      if (det > -EPSILON && det < EPSILON){
        return null;
      }
      
      invDet = 1.0 / det;
      
      /* calculate distance from vertO to ray origin */
      tvec = orig.minus(triangle.getVertex1());
      
      /* calculate U parameter and test bounds */
      u = tvec.dot(pvec) * invDet;
      if ((u < 0.0) || (u > 1.0)){
        return null;
      }
      
      /* prepare to test V parameter */
      qvec = tvec.cross(edge1);
      
      /* calculate V parameter and test bounds */
      v = dir.dot(qvec) * invDet;
      if ((v < 0.0) || ((u + v) > 1.0)){
        return null;
      }
      
      /* calculate t, ray intersects triangle */
      t = edge2.dot(qvec) * invDet;
    }
    
    
    // Translating barycentric coordinates into cartesian coordinates.
    /*
    double factor = (1-u-v);
   
    result = GeometryFactory.createPoint3D(factor*triangle.getVertex1().getX() + u*triangle.getVertex2().getX() + v*triangle.getVertex3().getX(), 
                                 factor*triangle.getVertex1().getY() + u*triangle.getVertex2().getY() + v*triangle.getVertex3().getY(), 
                                 factor*triangle.getVertex1().getZ() + u*triangle.getVertex2().getZ() + v*triangle.getVertex3().getZ());
    */
    
    result = orig.plus(dir.multiply(t));
    
    return result;
  }
  

  /**
   * Get the minimum scalar product (dot product) for the given point collection following the given axis. 
   * @param points the points collection to test.
   * @param axis the direction of the axis to check.
   * @return the minimum scalar product (dot product) for the given point collection following the given axis.
   * @see #getMaxScalar(Collection, Point3D) 
   */
  public static double getMinScalar(Collection<Point3D> points, Point3D axis){
    double min = Double.POSITIVE_INFINITY; 

    Iterator<Point3D> iter = points.iterator();
    Point3D point          = null;
    double dotprod          = 0.0d;
    while(iter.hasNext()){
      point = iter.next();
      dotprod =  point.dot(axis);
      if (dotprod < min){ 
        min = dotprod;
      }
    }
    
    return min;
  }

  /**
   * Get the maximum scalar product (dot product) for the given point collection following the given axis. 
   * @param points the points collection to test.
   * @param axis the direction of the axis to check. 
   * @return the maximum scalar product (dot product) for the given point collection following the given axis. 
   * @see #getMinScalar(Collection, Point3D)
   */
  public static double getMaxScalar(Collection<Point3D> points, Point3D axis){
    double max = Double.NEGATIVE_INFINITY; 

    Iterator<Point3D> iter = points.iterator();
    Point3D point          = null;
    double dotprod          = 0.0d;
    while(iter.hasNext()){
      point = iter.next();
      dotprod = point.dot(axis);
      if (dotprod > max){ 
        max = dotprod;
      }
    }
    
    return max;
  }
 
  /**
   * Check if the two given point collections are intersecting following the given axis.
   * @param ref the first point collection considered as reference.
   * @param check the second collection.
   * @param axis the axis for the check.
   * @return <code>true</code> if the collections are intersecting and <code>false</code> otherwise.
   */
  public static boolean isIntersect(Collection<Point3D> ref, Collection<Point3D> check, Point3D axis){
    if (getMinScalar(ref, axis) > getMaxScalar(check, axis)){
      return false;
    }
    
    if (getMaxScalar(ref, axis) < getMinScalar(check, axis)){
      return false;
    }
    
    return true;     
  }

/*  
  public static void computeFaceOrientation(IMeshIndexed mesh, IFaceIndexed start) {
    
    if (mesh != null){
      
      List<? extends IFaceIndexed> faces = mesh.getFacesIndexes();
      
      List<IFaceIndexed> wavefront  = new LinkedList<IFaceIndexed>();
      List<IFaceIndexed> wavefront2 = new LinkedList<IFaceIndexed>();
      
      if ((start != null)&&(start.getMesh() == mesh)){
        wavefront.add(start);
      } else {
        wavefront.add(faces.get(0));
      }
      
      HashMap<Triangle, Integer> flag = new HashMap<Triangle, Integer>();
      
      boolean oneflagactive=(faces.size() > 0);

      int processedTriangles          = 0;
      
      int orientedFaces               = 1;
      int lastIterationOrientedFaces  = 0;
      
      List<Triangle>[] adjacentsArray = null;
      
      List<Triangle>[] incidentsArray = null;
      
      int commonPoint1IndexOnAdj      = -1;
      int commonPoint2IndexOnAdj      = -1;
      
      int tmp                         = -1;
      
      int invertedEdge                = -1;

      while (oneflagactive==true) {
        
        Iterator<IFaceIndexed> wavefrontIter = wavefront.iterator();
        IFaceIndexed wave                    = null;
        IFaceIndexed adjacent                = null;
        while(wavefrontIter.hasNext()){

          wave = wavefrontIter.next();
          
          IFaceIndexed[] adjacents={wave.getAdjacent(0), wave.getAdjacent(1), wave.getAdjacent(2)};

          for(int i = 0; i < 3; i++){
            
            adjacent = adjacents[i];

            // We check each adjacent of the wave
            if ((adjacent!=null) &&(flag.get(adjacent) == null)){
              
              int[] waveIndices     = wave.getIndice();     
              int[] adjacentIndices = adjacent.getIndice();
              
              // We retrieve the indices of the vertices forming the edge of adjacency for the wave triangle
              // within the adjacent triangle. for an edge i, the attached vertices indices are (i, (i+1)%3)
              // ie: edge 0: (0, 1), edge 1: (1, 2), edge 2: (2, 0)          
              if (adjacentIndices[0] == waveIndices[i]){
                commonPoint1IndexOnAdj = 0;
              } else if (adjacentIndices[1] == waveIndices[i]){
                commonPoint1IndexOnAdj = 1;
              } else if (adjacentIndices[2] == waveIndices[i]){
                commonPoint1IndexOnAdj = 2;
              } else {
                commonPoint1IndexOnAdj = -1;
              }
              
              if (adjacentIndices[0] == waveIndices[(i+1)%3]){
                commonPoint2IndexOnAdj = 0;
              } else if (adjacentIndices[1] == waveIndices[(i+1)%3]){
                commonPoint2IndexOnAdj = 1;
              } else if (adjacentIndices[2] == waveIndices[(i+1)%3]){
                commonPoint2IndexOnAdj = 2;
              } else {
                commonPoint1IndexOnAdj = -1;
              }
              
              // We check if the common points are in the same order (natural ordering relation on indices is the same
              // within wave and its adjacent.) 
              boolean samedir = (adjacentIndices[(commonPoint1IndexOnAdj+1)%3] == adjacentIndices[commonPoint2IndexOnAdj]);
              
              // If the edge is listed within the same order for the two triangle, the adjacent edge has to be 
              // inverted. The adjacents relative to the inverted edge have also to be inverted.
              if (samedir){
                
                // Saving actual neighborhood for each edge (adjacent) and each vertex (incident)
                adjacentsArray = new List[]{adjacent.getAdjacents(0), adjacent.getAdjacents(1), adjacent.getAdjacents(2)};
                incidentsArray = new List[]{ adjacent.getIncidents(0), adjacent.getIncidents(1), adjacent.getIncidents(2)};

                // Inverting vertices
                tmp = adjacentIndices[commonPoint1IndexOnAdj];
                adjacentIndices[commonPoint1IndexOnAdj] = adjacentIndices[commonPoint2IndexOnAdj];
                adjacentIndices[commonPoint2IndexOnAdj] = tmp;
                adjacent.setIndice(adjacentIndices);
                
                // Inverting incidents attached to inverted vertices
                adjacent.setIncidents(commonPoint1IndexOnAdj, incidentsArray[commonPoint2IndexOnAdj]);
                adjacent.setIncidents(commonPoint2IndexOnAdj, incidentsArray[commonPoint1IndexOnAdj]);
                
                // Inverting adjacency to match the new edges order.
                if (commonPoint1IndexOnAdj == 0){
                  if (commonPoint2IndexOnAdj == 1){
                    invertedEdge = 0;
                  } else if (commonPoint2IndexOnAdj == 2){
                    invertedEdge = 2;
                  }
                } else if (commonPoint1IndexOnAdj == 1){
                  if (commonPoint2IndexOnAdj == 0){
                    invertedEdge = 0;
                  } else if (commonPoint2IndexOnAdj == 2){
                    invertedEdge = 1;
                  }
                } else if (commonPoint1IndexOnAdj == 2){
                  if (commonPoint2IndexOnAdj == 0){
                    invertedEdge = 2;
                  } else if (commonPoint2IndexOnAdj == 1){
                    invertedEdge = 1;
                  }
                }
                
                if (invertedEdge == 0){
                  adjacent.setAdjacents(1, adjacentsArray[2]);
                  adjacent.setAdjacents(2, adjacentsArray[1]);
                } else if (invertedEdge == 1){
                  adjacent.setAdjacents(0, adjacentsArray[2]);
                  adjacent.setAdjacents(2, adjacentsArray[0]);
                } else if (invertedEdge == 2){
                  adjacent.setAdjacents(1, adjacentsArray[0]);
                  adjacent.setAdjacents(0, adjacentsArray[1]);
                  
                }
                
                adjacentsArray = null;
                incidentsArray = null;
                tmp            = -1;
                invertedEdge   = -1;
              }
              
              // Compute the adjacent normal
              adjacent.computeAndSetNormal();
              processedTriangles++;

              // Mark the adjacent as processed.
              flag.put(adjacent, 1);
              
              // Set the adjacent as part of the next wave front.
              wavefront2.add(adjacent);
              
              // Update the processed faces count.
              orientedFaces++;
            }
            
          }
          
          wave.computeAndSetNormal();
          processedTriangles++;
        }

        if (processedTriangles < faces.size()){
          processedTriangles = faces.size();
        }
        
        wavefront.clear();
        wavefront.addAll(wavefront2);
        wavefront2.clear();

        // Check if all the faces are oriented or if the face orientation does not provide
        // new computation
        if (orientedFaces == faces.size() || (lastIterationOrientedFaces == orientedFaces)){
          oneflagactive = false;
        }
        
        lastIterationOrientedFaces = orientedFaces;
      }
      
      //
      // Computation of vertices normals
      //
      HashMap<IPoint3D, Boolean> normalescalculees = new HashMap<IPoint3D, Boolean>();
      
      Iterator<Triangle> triangleIter        = faces.iterator();
      Triangle triangle                      = null;
      
      Collection<Triangle> pointNeighborhood = new LinkedList<Triangle>();
      Point3DManager<IPoint3D> vertices      = null;
      IPoint3D vertex                        = null;
      
      while(triangleIter.hasNext()){

        triangle = triangleIter.next();

        if (triangle.getNormale() == null){
          triangle.computeAndSetNormal();  
        }
        
        vertices = triangle.getVertices();
        
        if (vertices != null){
          Iterator<IPoint3D> vertexIter = vertices.iterator();
          int cpt = 0;
          while(vertexIter.hasNext()){
            
            vertex = vertexIter.next();

            if ((vertex instanceof HasNormal)&&(normalescalculees.get(vertex) == null)){
              
              ((HasNormal) vertex).setNormal(GeometryFactory.createPoint3D(
                                             triangle.getNormale().getX(), 
                                             triangle.getNormale().getY(), 
                                             triangle.getNormale().getZ()));
              
              // Get adjacent from the first edge containing the vertex
              if (triangle.getAdjacents(cpt) != null){
                pointNeighborhood.addAll(triangle.getAdjacents(cpt));
              }
              
              // Get adjacent from the second edge containing the vertex
              if (triangle.getAdjacents((cpt+2)%3) != null){
                pointNeighborhood.addAll(triangle.getAdjacents((cpt+2)%3));
              }
              
              // Get incidents faces linked to the current vertex
              if (triangle.getIncidents(cpt) != null){
                pointNeighborhood.addAll(triangle.getIncidents(cpt));
              }
              
              // Summing all neighbors faces normal to compute the vertex normal.
              Iterator<Triangle> faceIter = pointNeighborhood.iterator();
              IPoint3D tmpNormal          = null;
              while(faceIter.hasNext()){
                tmpNormal = faceIter.next().getNormale();
                ((HasNormal) vertex).getNormal().plusAffect(tmpNormal);
              }
              
              ((HasNormal) vertex).getNormal().normalize();
              
              cpt++;
              
              normalescalculees.put(vertex, true);
              
              pointNeighborhood.clear();
              tmpNormal = null;
              vertex    = null;
            }
          }
          
          vertexIter = null;
        }
        
        vertices = null;
        triangle = null;
        
        processedTriangles++;
      }

      normalescalculees.clear();
      normalescalculees = null;
      
      triangleIter = null;
    }
  }
 */
} 
////////////////////////////////////////////////////////////////////////////////
// FIN COMPATIBILITE AVEC LES CLASSES DE GEOMETRIE DE HAUT NIVEAU D'ARPENTEUR //
////////////////////////////////////////////////////////////////////////////////

