package org.jeometry.simple.geom3D.primitive.indexed;

import java.util.Iterator;
import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.indexed.IndexedTetrahedron;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedMesh;


/**
 * A tetrahedron described as indexed geometry. A tetrahedron is a convex polyhedron made 
 * of 4 triangular faces that relies on 4 vertices.<br>
 * Face naming convention are such that face <code>n</code> is facing (and is not containing) vertex <code>n</code>. 
 * @param <T> The type of underlying 3D points 
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public class SimpleIndexedTetrahedron<T extends Point3D> extends SimpleIndexedMesh<T> implements IndexedTetrahedron<T> {
  
  private static final long serialVersionUID = Geometry.BUILD;

  private int[] verticesIndex = null;
  
  private Point3D[] vertices = null;
  
  /**
   * Create a new empty indexed tetrahedron.
   */
  public SimpleIndexedTetrahedron(){
    super();
  }
  
  /**
   * Create a new indexed tetrahedron with the given vertex indices.
   * @param base1 the base first vertex index within the source.
   * @param base2 the base second vertex index within the source.
   * @param base3 the base third vertex index within the source.
   * @param top the top vertex index within the source.
   * @param source the source of indexed vertex within the source. 
   * @param validate <code>true</code> if the indexed geometry has to be valid and <code>false</code> otherwise.
   */
  public SimpleIndexedTetrahedron(int base1, int base2, int base3, int top, boolean validate, Point3DContainer<T> source){
    super(source);
    
    verticesIndex    = new int[4];
    verticesIndex[0] = base1;
    verticesIndex[1] = base2;
    verticesIndex[2] = base3;
    verticesIndex[3] = top;
    
    addFace(new int[]{base2, base3, top}); // Facing vertex 0
    addFace(new int[]{base1, base3, top}); // Facing vertex 1
    addFace(new int[]{base1, top, base2}); // Facing vertex 2
    addFace(new int[]{base1, base2, base3}); // Facing vertex 3
    
    if (validate){
      validateIndexes();
    }
  }

  
  @Override
  public String toString(){
    String str = "";
    
    String lineSep = System.getProperty("line.separator");

    str += " ("+getClass().getSimpleName()+") "
        +"["+verticesIndex[0]+", "+verticesIndex[1]+", "+verticesIndex[2]+", "+verticesIndex[3]+"]";
    
    if (getVerticesSource() != null){
      str += ", "+getVerticesSource().size()+" points in source";
    } else {
      str += ", no points in source";
    }
    
        
    List<? extends IndexedFace<T>> faces = getFacesIndexes();
    int i = 0;
    if (faces != null){
      Iterator<? extends Face<T>> iter = faces.iterator();
      while(iter.hasNext()){
        str += lineSep+"  "+(i+1)+"/"+faces.size()+": "+iter.next();
        i++;
      }
    } else {
      str += lineSep;
    }
    
    return str;
  }
  
  @Override
  public boolean validateIndexes(){
    boolean ok = super.validateIndexes();
    
    if (ok){
      Point3DContainer<? extends Point3D> vertices = getVertices();
      
      if (vertices != null){
        
        if (this.vertices == null){
          this.vertices = new Point3D[4];
        }
        
        this.vertices[0] = vertices.get(0);
        this.vertices[1] = vertices.get(1);
        this.vertices[2] = vertices.get(2);
        this.vertices[3] = vertices.get(3);
      }
      
      vertices.clear();
      vertices = null;
    }
    
    return ok;
  }
  
  /**
   * Create a new indexed tetrahedron with the given vertex indices.
   * @param vertex1 the first vertex index within the source.
   * @param vertex2 the second vertex index within the source.
   * @param vertex3 the third vertex index within the source.
   * @param vertex4 the fourth vertex index within the source.
   * @param source the source of indexed vertex within the source. 
   */
  public SimpleIndexedTetrahedron(int vertex1, int vertex2, int vertex3, int vertex4, Point3DContainer<T> source){
    this(vertex1, vertex2, vertex3, vertex4, false, source);
    if (vertices != null){
      
      if (this.vertices == null){
        this.vertices = new Point3D[4];
      }
      
      this.vertices[0] = source.get(vertex1);
      this.vertices[1] = source.get(vertex2);
      this.vertices[2] = source.get(vertex3);
      this.vertices[3] = source.get(vertex4);
    }
  }  
  
  @Override
  public boolean addFace(List<Integer> indices) {
    
    if ((indices != null)&&(indices.size() == 3)&&(getFacesIndexes().size() < 4)){
      return super.addFace(new SimpleIndexedTriangle<T>(new int[] {indices.get(0).intValue(), indices.get(1).intValue(), indices.get(2).intValue()}, this));
    }
    
    return false;
  }

  @Override
  public boolean addFace(int[] indices) {
    if ((indices != null)&&(indices.length == 3)&&(getFacesIndexes().size() < 4)){
      return super.addFace(new SimpleIndexedTriangle<T>(new int[] {indices[0], indices[1], indices[2]}, this));
    }
    
    return false;
  }
  
  @Override
  public boolean addFace(Face<T> face){
    if ((face instanceof IndexedFace)&&(face != null)&&(face.getVertices().size() == 3)&&(getFacesIndexes().size() < 4)){
      boolean result =  super.addFace(face);
      ((IndexedFace<T>) face).setVerticesSource(getVerticesSource());
      return result;
    } else {
      return false;
    }
  }
  
  @Override
  public int getVertexIndice(int position){
    if ((position > -1)&&(position < 4)){
      return verticesIndex[position];
    } else {
    	throw new IllegalArgumentException("Invalid index "+position+", expected values are 0, 1, 2 or 3.");
    }
  }
  
  @Override
  public Point3D getVertex(int position){
    if ((vertices != null) && (position > -1)&&(position < 4)){
      return vertices[position];
    } else {
    	throw new IllegalArgumentException("Invalid index "+position+", expected values are 0, 1, 2 or 3.");
    }
  }
  
  @Override
  public int getVertexIndex(Point3D vertex){
    int index = -1;
    
    if ((vertices != null)){
      int cpt = 0;
      while((index == -1)&&(cpt < vertices.length)){
        if (vertices[cpt] == vertex){
          index = cpt;
        }
        cpt++;
      }
    }
    
    return index;
  }
  
  @Override
  public int[] getVerticesArray(){
    return verticesIndex;
  }
}
