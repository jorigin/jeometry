package org.jeometry.simple.geom3D.primitive;


import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Box;
import org.jeometry.simple.geom3D.mesh.SimpleMesh;

/**
 * A Box is an 6 faces {@link SimpleMesh mesh} defined by its maximal and minimal vertices. 
 * A box is axis aligned (all of its faces are aligned to the underlying reference system).
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.0
 */
public class SimpleBox extends SimpleMesh<Point3D> implements Box {

  private static final long serialVersionUID = Geometry.BUILD;
  
  
  
  private Point3D bfl = null; // Bottom Front Left vertex (the min point)
  private Point3D bfr = null;
  private Point3D brr = null;
  private Point3D brl = null;
  
  private Point3D tfl = null;
  private Point3D tfr = null;
  private Point3D trr = null; // Top rear left vertex (the max point)
  private Point3D trl = null; 
  
  @Override
  public double getWidth(){
    return getMax().getX() - getMin().getX();
  }
  
  @Override
  public double getHeight(){
    return getMax().getY() - getMin().getY();
  }
  
  @Override
  public double getLength(){
    return getMax().getZ() - getMin().getZ();
  }
  
  @Override
  public Point3D getMax(){
    return tfr;
  }
  
  @Override
  public Point3D getMin(){
    return brl;
  }
  
  @Override
  public Face<Point3D> getFace(int flag){
    return super.getFaces().get(flag);
  }
  
  @Override
  public void scale(double factor){
    getMax().setX(getMax().getX() * factor);
    getMin().setX(getMin().getX() * factor);
    updateGeometry();
  }
  
  @Override
  public Point3DContainer<Point3D> getVertices() {
    return super.getVertices();
  }
   
  @Override
  public String toString(){
    return getClass().getSimpleName()+" [("+getMin().getX()+", "+getMin().getY()+", "+getMin().getZ()+"), "+"("+getMax().getX()+", "+getMax().getY()+", "+getMax().getZ()+")]";
  }
  
  /**
   * Create a new default box.
   */
  public SimpleBox(){
    this(null, null);
  }
  
  /**
   * Create a Box as a cube with the given <code>center</code> and the given <code>size</code>
   * @param center the center of the cube.
   * @param size the size of the cube (length of all edges)
   */
  public SimpleBox(Point3D center, double size){
    this(center, size, size, size);
  }
  
  /**
   * Create a box with the given <code>center</code> and the dimensions 
   * <code>width</code>, <code>length</code>, <code>height</code> respectively for X, Y and Z axis.
   * @param center the center of the box.
   * @param width the size of the box along X axis.
   * @param length the size of the box along Y axis.
   * @param height the size of the box along Z axis.
   */
  public SimpleBox(Point3D center, double width, double length, double height){
    this(GeometryFactory.createPoint3D(center.getX() - width/2.0, center.getY() - length/2.0, center.getZ() - height/2.0), 
        GeometryFactory.createPoint3D(center.getX() + width/2.0, center.getY() + length/2.0, center.getZ() + height/2.0));
  }
  
  /**
   * Construct a box with two points. The two points form the diagonal of the box.
   * @param min the minimal point.
   * @param max the maximal point.
   */
  public SimpleBox(Point3D min, Point3D max){
    
    super();
    
    brl = GeometryFactory.createPoint3D();
    bfr = GeometryFactory.createPoint3D();
    brr = GeometryFactory.createPoint3D();
    bfl = GeometryFactory.createPoint3D();
    
    tfl = GeometryFactory.createPoint3D();
    tfr = GeometryFactory.createPoint3D();
    trr = GeometryFactory.createPoint3D();
    trl = GeometryFactory.createPoint3D();

    getVertices().add(bfl); // 0
    getVertices().add(bfr); // 1
    getVertices().add(brr); // 2
    getVertices().add(brl); // 3
    getVertices().add(tfl); // 4
    getVertices().add(tfr); // 5
    getVertices().add(trr); // 6
    getVertices().add(trl); // 7
    
    // Bottom face
    Point3DContainer<Point3D> pts = GeometryFactory.createPoint3DContainer(4);
    pts.add(brl);
    pts.add(brr);
    pts.add(bfr);
    pts.add(bfl);
    addFace(GeometryFactory.createMeshFace(pts));

    // Top face
    pts = GeometryFactory.createPoint3DContainer(4);
    pts.add(tfl);
    pts.add(tfr);
    pts.add(trr);
    pts.add(trl);
    addFace(GeometryFactory.createMeshFace(pts));
    
    // Front face
    pts = GeometryFactory.createPoint3DContainer(4);
    pts.add(bfl);
    pts.add(bfr);
    pts.add(tfr);
    pts.add(tfl);
    addFace(GeometryFactory.createMeshFace(pts));
    
    // Rear face
    pts = GeometryFactory.createPoint3DContainer(4);
    pts.add(brr);
    pts.add(brl);
    pts.add(trl);
    pts.add(trr);
    addFace(GeometryFactory.createMeshFace(pts));
    
    // Left face
    pts = GeometryFactory.createPoint3DContainer(4);
    pts.add(brl);
    pts.add(bfl);
    pts.add(tfl);
    pts.add(trl);
    addFace(GeometryFactory.createMeshFace(pts));
    
    // Right face
    pts = GeometryFactory.createPoint3DContainer(4);
    pts.add(bfr);
    pts.add(brr);
    pts.add(trr);
    pts.add(tfr);
    addFace(GeometryFactory.createMeshFace(pts));
    
    if ((min != null)&&(max != null)){
      brl = min;
      tfr = max;
      
      updateGeometry();
    }
  }
  
  /**
   * Get the faces for which the given {@link Point3D point} is considered as outside. 
   * The result is a logical composition of faces identifier ({@link #FACE_FRONT}, {@link #FACE_BACK}, ...)
   * @param point the {@link Point3D point} to check.
   * @return the index pf the face for which the given {@link Point3D point} is considered as outside. 
   */
  public int getOutsideFaces(Point3D point){
    int result = 0;
    
    if (point != null){
      if (point.getX() >  getMax().getX()){
        result = result | FACE_RIGHT;
      }
      
      if (point.getX() < getMin().getX()){
        result = result | FACE_LEFT;
      }
      
      if (point.getY() >  getMax().getY()){ 
        result = result | FACE_TOP;
      }
      
      if (point.getY() < getMin().getY()){ 
        result = result | FACE_BOTTOM;
      }
      
      if (point.getZ() >  getMax().getZ()){ 
        result = result | FACE_FRONT;
      }
      
      if (point.getZ() < getMin().getZ()){ 
        result = result | FACE_BACK;
      }
    }
    
    return result;
  }

  /**
   * Update the geometry of the box.
   */
  private void updateGeometry(){
    
    if ((getMin() != null) && (getMax() != null)){
      double xMin  = getMin().getX();
      double xMax  = getMax().getX();
      double yMin  = getMin().getY();
      double yMax  = getMax().getY();
      double zMin  = getMin().getZ();
      double zMax  = getMax().getZ();

      
      bfl.setX(xMax);
      bfl.setY(yMin);
      bfl.setZ(zMin);
      
      bfr.setX(xMax);
      bfr.setY(yMax);
      bfr.setZ(zMin);
      
      brr.setX(xMin);
      brr.setY(yMax);
      brr.setZ(zMin);
      
      tfl.setX(xMax);
      tfl.setY(yMin);
      tfl.setZ(zMax);
      
      trr.setX(xMin);
      trr.setY(yMax);
      trr.setZ(zMax);
      
      trl.setX(xMin);
      trl.setY(yMin);
      trl.setZ(zMax);
      
    }
  }
}
