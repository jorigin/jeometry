package org.jeometry.simple.geom3D.mesh.indexed.textured;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.textured.Texture;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.textured.Texturable;
import org.jeometry.geom3D.textured.TextureManager;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedMesh;


/**
 * An {@link IndexedMesh indexed mesh} that have texturing capabilities. 
 * It is assumed that {@link IndexedFace faces} composing this mesh are instances of {@link Texturable texturable} 
 * interfaces.
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleIndexedTexturedMesh<T extends Point3D> extends SimpleIndexedMesh<T> implements TextureManager{

  private static final long serialVersionUID = Jeometry.BUILD;

  private List<Texture> textures = null;
  
  @Override
  public List<Texture> getTextures() {
    return textures;
  }

  @Override
  public void setTextures(List<Texture> textures) {
    this.textures = textures;
  }
}
