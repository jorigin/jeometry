package org.jeometry.simple.geom3D.mesh.indexed.textured;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.textured.Texture;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedFace;
import org.jeometry.geom3D.textured.Texturable;

/**
 * A default textured and indexed face. This class extends {@link SimpleIndexedFace FaceIndexed} 
 * for handling indexed geometry 
 * and implements {@link Texturable Texturable} for texturing capability. Instances of this class 
 * can compose a {@link SimpleIndexedTexturedMesh textured indexed mesh}. 
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class SimpleIndexedTexturedFace<T extends Point3D> extends SimpleIndexedFace<T> implements Texturable{

    /**
     * The srial version UID.
     */
	private static final long serialVersionUID = Jeometry.BUILD;

	/**
	 * The texture.
	 */
	private Texture texture = null;

	/**
	 * The texture information.
	 */
	private int textureInformation = 0;
	
	/**
	 * The texture coordinates.
	 */
	private Point2DContainer textureCoords = null;

	@Override
	public Texture getTexture() {
		return this.texture;
	}

	@Override
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	@Override
	public Point2DContainer getTextureCoodinates() {
		return this.textureCoords;
	}

	@Override
	public void setTextureCoodinates(Point2DContainer coordinates) {
		this.textureCoords = coordinates;
	}


	/**
	 * Create a new textured face.
	 */
	public SimpleIndexedTexturedFace(){
		super();
	}
	
	/**
	 * Create a new textured face.
	 * @param indices the indices of the vertices that compose the face.
	 */
	public SimpleIndexedTexturedFace(List<Integer> indices){
		super(indices);
	}

	/**
	 * Create a new textured face.
	 * @param verticesIndexes the indices of the vertices that compose the face.
	 */
	public SimpleIndexedTexturedFace(int[] verticesIndexes){
		super(verticesIndexes);
	}
	
	/**
	 * Create a new textured face.
	 * @param indices the indices of the vertices that compose the face.
	 * @param mesh the mesh that holds this face.
	 */
	public SimpleIndexedTexturedFace(List<Integer> indices, IndexedMesh<T> mesh){
		super(indices, mesh);
	}

	/**
	 * Create a new textured face.
	 * @param verticesIndexes the indices of the vertices that compose the face.
	 * @param mesh the mesh that holds this face.
	 */
	public SimpleIndexedTexturedFace(int[] verticesIndexes, IndexedMesh<T> mesh){
		super(verticesIndexes, mesh);
	}


	@Override
	public int getTextureCoordinatesNature() {
      return this.textureInformation & Texturable.COORDINATE_MASK;
	}

	@Override
	public void setTextureCoordinatesNature(int nature) {
		this.textureInformation = this.textureInformation &  (nature | Texturable.ORIGIN_MASK | Texturable.AXIS_MASK);
	}

	@Override
	public int getTextureCoordinatesOrigin() {
		return this.textureInformation & Texturable.ORIGIN_MASK;
	}

	@Override
	public void setTextureCoordinatesOrigin(int origin) {
		this.textureInformation = this.textureInformation &  (origin | Texturable.COORDINATE_MASK | Texturable.AXIS_MASK);
	}

	@Override
	public int getTextureCoordinatesAxisXDirection() {
		return this.textureInformation & Texturable.AXIS_X_MASK;
	}

	@Override
	public void setTextureCoordinatesAxisXDirection(int direction) {
		this.textureInformation = this.textureInformation &  (direction | Texturable.COORDINATE_MASK | Texturable.ORIGIN_MASK);
	}

	@Override
	public int getTextureCoordinatesAxisYDirection() {
		return this.textureInformation & Texturable.AXIS_Y_MASK;
	}

	@Override
	public void setTextureCoordinatesAxisYDirection(int direction) {
		this.textureInformation = this.textureInformation &  (direction | Texturable.COORDINATE_MASK | Texturable.ORIGIN_MASK);
	}

}
