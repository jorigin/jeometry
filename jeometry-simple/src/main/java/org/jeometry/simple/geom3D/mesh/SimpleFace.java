package org.jeometry.simple.geom3D.mesh;

import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Polygon3D;
import org.jeometry.simple.geom3D.primitive.SimplePolygon3D;

/**
 * A face is a {@link Polygon3D 3D polygon} that compose a {@link Mesh mesh}.
 * @param <T> the type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 */
public class SimpleFace<T extends Point3D> extends SimplePolygon3D<T> implements Face<T>{

  private static final long serialVersionUID = Geometry.BUILD;
  
  /**
   * The mesh to which the face is attached to.
   */
  protected Mesh<T> mesh = null;

  /**
   * Construct a new empty face.
   */
  public SimpleFace() {
    super();
  }

  /**
   * Construct a new empty face attached to the mesh given in parameter
   * @param mesh the mesh to which the face is attached to.
   */
  public SimpleFace(Mesh<T> mesh){
    this();
    this.mesh = mesh;
  }

  /**
   * Construct a new face associated to the given {@link Polygon3D 3D polygon}. 
   * The vertices of the polygon are used as vertices for the face.
   * @param polygon the {@link Polygon3D 3D polygon} to use.
   */
  public SimpleFace(Polygon3D<T> polygon){
    this(null, polygon);
  }

  /**
   * Construct a face from an array of {@link Point3DContainer points}.
   * @param points the points forming the face perimeter.
   */
  public SimpleFace(Point3DContainer<T> points){
    super(points);
    this.mesh = null;
  }
  
  /**
   * Construct a new face with the {@link Point3DContainer 3D points container} given in parameter
   * @param mesh the mesh to which the face is attached to.
   * @param points the points composing the perimeter of the face.
   */
  public SimpleFace(Mesh<T> mesh, Point3DContainer<T> points) {
    super(points);
    this.mesh = mesh;
  }

  /**
   * Construct a new face with the {@link Polygon3D 3D polygon} given in parameter and attached to the given {@link Mesh mesh}.
   * @param mesh the {@link Mesh mesh} to which this face is attached.
   * @param polygon the {@link Polygon3D 3D polygon} that describe the face.
   */
  public SimpleFace(Mesh<T> mesh, Polygon3D<T> polygon){
    super(polygon.getVertices());
    this.mesh = mesh;
  }

  @Override
  public Mesh<T> getMesh(){
    return this.mesh;
  }

  @Override
  public void setMesh(Mesh<T> mesh){
    this.mesh = mesh;
  }

  @Override
  public List<Edge<T>> getEdges() {
  	// TODO implements SimpleFace.getEdges()
  	return null;
  }
  
  @Override
  public boolean equals(Object obj) {
     // Si les references sont identiques, les objets sont identiques
     if (this == obj) {
       return true;
     } else {
       return super.equals(obj);
    }
  }

}
