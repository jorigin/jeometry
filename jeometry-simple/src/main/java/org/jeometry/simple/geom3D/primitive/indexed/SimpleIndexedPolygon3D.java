package org.jeometry.simple.geom3D.primitive.indexed;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.indexed.IndexedPolygon3D;
import org.jeometry.simple.geom3D.point.ArrayListPoint3DContainer;
import org.jeometry.simple.geom3D.primitive.SimplePolygon3D;

/**
 * A simple {@link IndexedPolygon3D indexed 3D polygon}.
 * @param <T> The type of the underlying 3D points.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class SimpleIndexedPolygon3D<T extends Point3D> extends SimplePolygon3D<T> implements IndexedPolygon3D<T>{

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = Jeometry.BUILD;

	/**
	 * The vertices indexes.
	 */
	private int[] verticesIndexes = null;

	/**
	 * The edges indices.
	 */
	List<IndexedEdge<T>> edgesIndexed = null;

	/**
	 * The vertice source.
	 */
	Point3DContainer<T> verticesSource = null;

	/**
	 * Is the indices have to be validated.
	 */
	boolean validatedIndexes = false;

	@Override
	public String toString() {
		String str = getClass().getSimpleName()+" ";

		if (this.verticesIndexes != null){
			str +="["+this.verticesIndexes[0];
			for(int i = 1; i < this.verticesIndexes.length; i++){
				str += ", "+this.verticesIndexes[i];
			}
			str += "]";
		} else {
			str +="[]";
		}

		if (this.validatedIndexes){
			str += " - "+super.toString();
		}

		return str;
	}

	@Override
	public int[] getVerticesIndexes() {
		return this.verticesIndexes;
	}

	@Override
	public void setVerticesIndexes(int[] indices) {
		this.verticesIndexes = indices;
		this.validatedIndexes = false;
	}

	@Override
	public List<IndexedEdge<T>> getEdgesIndexed() {
		return this.edgesIndexed;
	}

	@Override
	public boolean equals(IndexedPolygon3D<?> polygon) {
		System.out.println("SimpleIndexedPolygon3D.equals(IndexedPolygon3D) NOT YET IMPLEMENTED");
		// TODO implements SimpleIndexedPolygon3D.equals(IndexedPolygon3D)
		return false;
	}

	@Override
	public Point3DContainer<T> getVerticesSource() {
		return this.verticesSource;
	}

	@Override
	public void setVerticesSource(Point3DContainer<T> verticesSource) {
		this.verticesSource = verticesSource;
	}

	@Override
	public boolean isValidatedIndexes() {
		return this.validatedIndexes;
	}

	@Override
	public boolean validateIndexes() {

		this.validatedIndexes = false;

		if ((getVerticesIndexes() != null)&&(getVerticesSource() != null)){

			Point3DContainer<T> vertices = new ArrayListPoint3DContainer<T>();

			for(int i = 0; i < getVerticesIndexes().length; i++){
				if ((getVerticesIndexes()[i] > -1)&&(getVerticesIndexes()[i] < getVerticesSource().size())){
					vertices.add(getVerticesSource().get(getVerticesIndexes()[i]));
				} else {
					this.validatedIndexes = false;
				}
			}

			super.setVertices(vertices);
			this.validatedIndexes = true;

		} else {
			this.validatedIndexes = false;
		}

		return this.validatedIndexes;
	}

	@Override
	public void inverseVerticesOrder(){
		int[] verticesIndex = this.getVerticesIndexes();
		int orig = -1;
		int lastindex = verticesIndex.length - 1;
		for (int i = 0; i < verticesIndex.length / 2; i++) {
			orig = verticesIndex[i];
			verticesIndex[i] = verticesIndex[lastindex - i];
			verticesIndex[lastindex - i] =  orig;
		}

		validateIndexes();
	}

	/**
	 * Create a new empty indexed 3D polygon.
	 */
	public SimpleIndexedPolygon3D(){
      super();
	}

	/**
	 * Create an indexed 3D polygon that is delimited by the vertices referenced by the given indices.
	 * @param indices the indices that reference the vertex of the polygon.
	 */
	public SimpleIndexedPolygon3D(int[] indices){
		super();
		this.verticesIndexes = indices;
	}

	/**
	 * Create an indexed 3D polygon that is delimited by the vertices referenced by the given indices.
	 * @param indices the indices that reference the vertex of the polygon.
	 */
	public SimpleIndexedPolygon3D(List<Integer> indices){
		super();
		
		if (indices != null) {
		  this.verticesIndexes = indices.stream().mapToInt(i->i).toArray();
		} else {
		  this.verticesIndexes = null;
		}
	}
	
	/**
	 * Create an indexed 3D polygon that relies on the given vertices source and that is delimited by the vertices referenced 
	 * by the given indices.
	 * @param indices the indices that reference the vertex of the polygon.
	 * @param verticesSource the source of the vertices that compose the polygon.
	 */
	public SimpleIndexedPolygon3D(int[] indices, Point3DContainer<T> verticesSource){
		super();
		setVerticesSource(verticesSource);
		this.verticesIndexes = indices;
		validateIndexes();
	}

	/**
	 * Create an indexed 3D polygon that relies on the given vertices source and that is delimited by the vertices referenced 
	 * by the given indices.
	 * @param indices the indices that reference the vertex of the polygon.
	 * @param verticesSource the source of the vertices that compose the polygon.
	 */
	public SimpleIndexedPolygon3D(List<Integer> indices, Point3DContainer<T> verticesSource){
		super();
		setVerticesSource(verticesSource);
		
		if (indices != null) {
		  this.verticesIndexes = indices.stream().mapToInt(i->i).toArray();
		} else {
		  this.verticesIndexes = null;
		}
		
		validateIndexes();
	}
}
