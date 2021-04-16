package org.jeometry.simple.geom3D.primitive;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.LineSet3D;
import org.jeometry.geom3D.primitive.Polygon3D;


/**
 * A simple implementation of the {@link Polygon3D Polygon3D interface}.
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 *
 */
public class SimplePolygon3D<T extends Point3D> implements Polygon3D<T> {

  /**
   * The serial version UID.
   */
  private static final long serialVersionUID = Jeometry.BUILD;
  
  /**
   * The points of the polygon perimeter. The order of the points is very
   * important for the computations of visible face, convex hull, ...
   */
  private Point3DContainer<T> points;

  /**
   * Construct a new polygon with the {@link Point3DContainer points} given in parameter
   * @param points the points composing the perimeter of the polygon.
   */
  public SimplePolygon3D(Point3DContainer<T> points) {
    this.points = points;
  }

  /**
   * Construct an empty polygon.
   */
  public SimplePolygon3D() {
    this.points = JeometryFactory.<T>createPoint3DContainer();
  }

  @Override
  public Point3DContainer<T> getVertices() {
    return this.points;
  }

  @Override
  public void setVertices(Point3DContainer<T> vertices) {
    this.points = vertices;
  }

  @Override
  public LineSet3D<T> getSegments() {
	LineSet3D<T> edges = new SimpleLineSet3D<T>();

    // S'il n'y a pas au moins 2 points, il n'y a pas d'arrête
    if (points.size() < 2) {
      return null;
    }
    
    // Les points sont lié deux à deux jusqu'a l'avant dernier
    for (int i = 0; i < points.size(); i++) {
      edges.plot(points.get(i));
    }

    return edges;
  }

  @Override
  public void inverseVerticesOrder() {
	  
	Point3DContainer<T> points = getVertices();
    
    T orig = null;
    int lastindex = points.size() - 1;
    for (int i = 0; i < points.size() / 2; i++) {
      orig = points.get(i);
      points.set(i, points.get(lastindex - i));
      points.set(lastindex - i, orig);
    }
  }

  /**
   * Check if this polygon is equal with the given {@link Object object}. This method return <code>true</code> if the given object has the same reference that this polygon. 
   * Otherwise, this method return <code>true</code> if the given object is an instance of {@link Polygon3D} and if the two perimeters are the same (same vertices in the same order).
   * @param obj the object to compare
   * @return <code>true</code> if the two objects are equals and <code>false</code> otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    // Si les references sont identiques, les objets sont identiques
    if (this == obj) {
      return true;
    } else {
      // Si les deux objets sont bien des polygones, la comparaison géométrique
      // est
      // effectuée
      if (obj instanceof Polygon3D) {
        Point3DContainer<?> p1p3dm = null;
        Point3DContainer<?> p2p3dm = null;
        // Si les polygones n'ont pas le même nombre de points, ils sont
        // différents
        p1p3dm = this.getVertices();
        p2p3dm = ((Polygon3D<?>) obj).getVertices();
        if ((p2p3dm == null) || (p1p3dm.size() != p2p3dm.size())) {
          return false;
        } else {
          // deux polygones sont identiques si ils contiennent la même sequence
          // de points (même prérimètre). Le sens du périmètre est important
          // donc si deux polygones dont les périmètres ont même sequence de
          // points
          // mais dans un sens différent ils sont considérés comme différents.
          boolean found = false;
          boolean sequenceOk = true;
          int i = 0;
          int j = 0;
          // On recherche si le premier point du périmètre du polygone p1 est
          // bien présent dans le prérimètre du polygone p2.
          while ((!found) && (i < p2p3dm.size())) {
            if (p1p3dm.get(0).equals(p2p3dm.get(i))) {
              found = true;
            }
            i++;
          }
          // On recupère la position du premier point du perimètre du polygone 1
          // dans le perimetre du polygone 2
          i--;
          // Si le premier point de p1 n'est pas dans p2, les polygones ne
          // peuvent être
          // egaux.
          if (!found) {
            return false;
          } else {
            // Si le premier point de p1 est dans p2, on verifie si les
            // sequences sont
            // bien identiques.
            j = 0;
            while ((sequenceOk) && (j < p1p3dm.size())) {
              if (!(p1p3dm.get(j).equals(p2p3dm.get((j + i) % p2p3dm.size())))) {
                sequenceOk = false;
              }
              j++;
            }
            return sequenceOk;
          }
        }
        // Sinon les deux objets sont différents.
      } else {
        return false;
      }
    }
  }

  @Override
  public String toString() {
    String str = getClass().getSimpleName()+" ";
    Point3D pt = null;
    if ((points == null) || (points.size() < 1)) {
      str = "[ No vertex ]";
    } else {
      str = "Polygon3D (" + points.size() + " points) [";
      for (int i = 0; i < points.size() - 1; i++) {
        pt = points.get(i);
        str += " (" + pt.getX() + ", " + pt.getY() + ", " + pt.getZ() + "),";
      }
      pt = points.get(points.size() - 1);
      str += " (" + pt.getX() + ", " + pt.getY() + ", " + pt.getZ() + ") ]";
    }
    return str;
  }

  /**
   * Add a 3D point to this polygon.
   * @param point the new point to add.
   */
  public void addPoint(T point) {
    this.points.add(point);
  }

}
