package org.jeometry.simple.geom3D.mesh.indexed.textured;

import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.textured.Texturable;
import org.jeometry.geom3D.textured.Texture;
import org.jeometry.simple.geom3D.primitive.indexed.SimpleIndexedTriangle;

/**
 * A default textured and indexed triangle. This class extends {@link SimpleIndexedTriangle indexed triangle} 
 * for handling indexed geometry 
 * and implements {@link Texturable Texturable} for texturing capability. 
 * Instances of this class can compose a {@link SimpleIndexedTexturedMesh textured indexed mesh}. 
 * @param <T> The type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 *
 */
public class SimpleIndexedTexturedTriangle<T extends Point3D> extends SimpleIndexedTriangle<T> implements Texturable{

	private static final long serialVersionUID = Geometry.BUILD;

	private Texture texture = null;

	private int textureInformation = 0;
	
	private Point2DContainer textureCoords = null;
	
	@Override
	public Texture getTexture() {
		return texture;
	}

	@Override
	public void setTexture(Texture texture) {
      this.texture = texture;
	}

	@Override
	public Point2DContainer getTextureCoodinates() {
		return textureCoords;
	}

	@Override
	public void setTextureCoodinates(Point2DContainer coordinates) {
		this.textureCoords = coordinates;
	}

	@Override
	public int getTextureCoordinatesNature() {
      return textureInformation & Texturable.COORDINATE_MASK;
	}

	@Override
	public void setTextureCoordinatesNature(int nature) {
		textureInformation = textureInformation &  (nature | Texturable.ORIGIN_MASK | Texturable.AXIS_MASK);
	}

	@Override
	public int getTextureCoordinatesOrigin() {
		return textureInformation & Texturable.ORIGIN_MASK;
	}

	@Override
	public void setTextureCoordinatesOrigin(int origin) {
		textureInformation = textureInformation &  (origin | Texturable.COORDINATE_MASK | Texturable.AXIS_MASK);
	}

	@Override
	public int getTextureCoordinatesAxisXDirection() {
		return textureInformation & Texturable.AXIS_X_MASK;
	}

	@Override
	public void setTextureCoordinatesAxisXDirection(int direction) {
		textureInformation = textureInformation &  (direction | Texturable.COORDINATE_MASK | Texturable.ORIGIN_MASK);
	}

	@Override
	public int getTextureCoordinatesAxisYDirection() {
		return textureInformation & Texturable.AXIS_Y_MASK;
	}

	@Override
	public void setTextureCoordinatesAxisYDirection(int direction) {
		textureInformation = textureInformation &  (direction | Texturable.COORDINATE_MASK | Texturable.ORIGIN_MASK);
	}

	/**
	 * Create a new default textured indexed triangle.
	 */
	public SimpleIndexedTexturedTriangle() {
	}
	
	/**
	 * Create a new indexed textured triangles based on given vertex indices.
	 * @param vertex1 the first vertex index
	 * @param vertex2 the second vertex index
	 * @param vertex3 the third vertex index
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTexturedTriangle(int vertex1, int vertex2, int vertex3) {
		super(new int[] {vertex1, vertex2, vertex3}, null);
	}
	
	/**
	 * Create a new textured indexed triangles based on given vertex indices.
	 * @param indices an array of 3 indices that point the vertex of the triangle. 
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTexturedTriangle(int[] indices) {
		this(indices, null);
	}
	
	/**
	 * Create a new textured indexed triangles based on given vertex indices.
	 * @param indices a list of 3 indices that point the vertex of the triangle.
	 * @throws IllegalArgumentException is the number of indices differs from 3
	 */
	public SimpleIndexedTexturedTriangle(List<Integer> indices) {
		this(indices, null);
	}
	
	/**
	 * Create a new indexed textured triangles based on given vertex indices.
	 * @param vertex1 the first vertex index
	 * @param vertex2 the second vertex index
	 * @param vertex3 the third vertex index
	 * @param mesh the mesh that hold the triangle
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTexturedTriangle(int vertex1, int vertex2, int vertex3, IndexedMesh<T> mesh) {
		super(new int[] {vertex1, vertex2, vertex3}, mesh);
	}
	
	/**
	 * Create a new textured indexed triangles based on given vertex indices.
	 * @param indices an array of 3 indices that point the vertex of the triangle. 
	 * @param mesh the mesh that hold the triangle
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTexturedTriangle(int[] indices, IndexedMesh<T> mesh) {
		super(indices, mesh);
	}
	
	/**
	 * Create a new textured indexed triangles based on given vertex indices.
	 * @param indices a list of 3 indices that point the vertex of the triangle. 
	 * @param mesh the mesh that hold the triangle.
	 * @throws IllegalArgumentException is the number of indices differs from 3.
	 */
	public SimpleIndexedTexturedTriangle(List<Integer> indices , IndexedMesh<T> mesh) {
		super(indices, mesh);
	}
}
