package org.jeometry.simple.geom3D.primitive;


import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
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

  /**
   * The serial version UID.
   */
  private static final long serialVersionUID = Jeometry.BUILD;
  
  /**
   * The bottom front left vertex (the minimal point).
   */
  private Point3D bfl = null;
  
  /**
   * The bottom front right vertex.
   */
  private Point3D bfr = null;
  
  /**
   * The bottom rear right vertex.
   */
  private Point3D brr = null;
  
  /**
   * The bottom rear left vertex.
   */
  private Point3D brl = null;
  
  /**
   * The top front left vertex
   */
  private Point3D tfl = null;
  
  /**
   * The top front right vertex
   */
  private Point3D tfr = null;
  
  /**
   * The top rear right vertex
   */
  private Point3D trr = null; // Top rear left vertex (the max point)
  
  /**
   * The top rear left vertex
   */
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
    return this.tfr;
  }
  
  @Override
  public Point3D getMin(){
    return this.brl;
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
    this(JeometryFactory.createPoint3D(center.getX() - width/2.0, center.getY() - length/2.0, center.getZ() - height/2.0), 
        JeometryFactory.createPoint3D(center.getX() + width/2.0, center.getY() + length/2.0, center.getZ() + height/2.0));
  }
  
  /**
   * Construct a box with two points. The two points form the diagonal of the box.
   * @param min the minimal point.
   * @param max the maximal point.
   */
  public SimpleBox(Point3D min, Point3D max){
    
    super();
    
    this.brl = JeometryFactory.createPoint3D();
    this.bfr = JeometryFactory.createPoint3D();
    this.brr = JeometryFactory.createPoint3D();
    this.bfl = JeometryFactory.createPoint3D();
    
    this.tfl = JeometryFactory.createPoint3D();
    this.tfr = JeometryFactory.createPoint3D();
    this.trr = JeometryFactory.createPoint3D();
    this.trl = JeometryFactory.createPoint3D();

    getVertices().add(this.bfl); // 0
    getVertices().add(this.bfr); // 1
    getVertices().add(this.brr); // 2
    getVertices().add(this.brl); // 3
    getVertices().add(this.tfl); // 4
    getVertices().add(this.tfr); // 5
    getVertices().add(this.trr); // 6
    getVertices().add(this.trl); // 7
    
    // Bottom face
    Point3DContainer<Point3D> pts = JeometryFactory.createPoint3DContainer(4);
    pts.add(this.brl);
    pts.add(this.brr);
    pts.add(this.bfr);
    pts.add(this.bfl);
    addFace(JeometryFactory.createMeshFace(pts));

    // Top face
    pts = JeometryFactory.createPoint3DContainer(4);
    pts.add(this.tfl);
    pts.add(this.tfr);
    pts.add(this.trr);
    pts.add(this.trl);
    addFace(JeometryFactory.createMeshFace(pts));
    
    // Front face
    pts = JeometryFactory.createPoint3DContainer(4);
    pts.add(this.bfl);
    pts.add(this.bfr);
    pts.add(this.tfr);
    pts.add(this.tfl);
    addFace(JeometryFactory.createMeshFace(pts));
    
    // Rear face
    pts = JeometryFactory.createPoint3DContainer(4);
    pts.add(this.brr);
    pts.add(this.brl);
    pts.add(this.trl);
    pts.add(this.trr);
    addFace(JeometryFactory.createMeshFace(pts));
    
    // Left face
    pts = JeometryFactory.createPoint3DContainer(4);
    pts.add(this.brl);
    pts.add(this.bfl);
    pts.add(this.tfl);
    pts.add(this.trl);
    addFace(JeometryFactory.createMeshFace(pts));
    
    // Right face
    pts = JeometryFactory.createPoint3DContainer(4);
    pts.add(this.bfr);
    pts.add(this.brr);
    pts.add(this.trr);
    pts.add(this.tfr);
    addFace(JeometryFactory.createMeshFace(pts));
    
    if ((min != null)&&(max != null)){
      this.brl = min;
      this.tfr = max;
      
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

      
      this.bfl.setX(xMax);
      this.bfl.setY(yMin);
      this.bfl.setZ(zMin);
      
      this.bfr.setX(xMax);
      this.bfr.setY(yMax);
      this.bfr.setZ(zMin);
      
      this.brr.setX(xMin);
      this.brr.setY(yMax);
      this.brr.setZ(zMin);
      
      this.tfl.setX(xMax);
      this.tfl.setY(yMin);
      this.tfl.setZ(zMax);
      
      this.trr.setX(xMin);
      this.trr.setY(yMax);
      this.trr.setZ(zMax);
      
      this.trl.setX(xMin);
      this.trl.setY(yMin);
      this.trl.setZ(zMax);
      
    }
  }
}
