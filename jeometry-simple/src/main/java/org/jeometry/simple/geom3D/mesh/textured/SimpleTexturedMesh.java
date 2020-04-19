package org.jeometry.simple.geom3D.mesh.textured;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.textured.Texturable;
import org.jeometry.geom3D.textured.Texture;
import org.jeometry.geom3D.textured.TextureManager;
import org.jeometry.simple.geom3D.mesh.SimpleMesh;


/**
 * An {@link Mesh mesh} that have texturing capabilities. 
 * It is assumed that {@link Face faces} composing this mesh are instances of {@link Texturable Texturable} interfaces.
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class SimpleTexturedMesh<T extends Point3D> extends SimpleMesh<T> implements TextureManager{


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
