package org.jeometry.geom3D.primitive.indexed;

import org.jeometry.Geometry;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;

/**
 * A tetrahedron is a convex polyhedron made of 4 triangular faces that relies on 4 vertices.<br>
 * Face naming convention are such that face <i>n</i> is facing (and is not containing) vertex <i>n</i>. 
 * @param <T> The type of underlying 3D points 
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public interface IndexedTetrahedron<T extends Point3D> {

	/**
	 * This flag identify the first face of the tetrahedron.
	 * @see #FACE_2
	 * @see #FACE_3
	 * @see #FACE_4
	 * @see #FACE_BASE
	 */
	public static final int FACE_1 = 0;

	/**
	 * This flag identify the second face of the tetrahedron.
	 * @see #FACE_1
	 * @see #FACE_3
	 * @see #FACE_4
	 * @see #FACE_BASE
	 */
	public static final int FACE_2 = 1;

	/**
	 * This flag identify the third face of the tetrahedron.
	 * @see #FACE_1
	 * @see #FACE_3
	 * @see #FACE_4
	 * @see #FACE_BASE
	 */
	public static final int FACE_3 = 2;

	/**
	 * This flag identify the fourth face of the tetrahedron.
	 * @see #FACE_1
	 * @see #FACE_2
	 * @see #FACE_3
	 * @see #FACE_BASE
	 */
	public static final int FACE_4 = 3;

	/**
	 * This flag identify the base face of the tetrahedron. By convention this is the {@link #FACE_4 fourth face}.
	 * @see #FACE_1
	 * @see #FACE_2
	 * @see #FACE_3
	 * @see #FACE_4
	 */
	public static final int FACE_BASE = FACE_4;

	/**
	 * This flag identify the first vertex that compose the tetrahedron base.
	 * @see #VERTEX_BASE_2
	 * @see #VERTEX_BASE_3
	 * @see #VERTEX_TOP
	 */
	public static final int VERTEX_BASE_1 = 0;

	/**
	 * This flag identify the second vertex that compose the tetrahedron base.
	 * @see #VERTEX_BASE_1
	 * @see #VERTEX_BASE_3
	 * @see #VERTEX_TOP
	 */
	public static final int VERTEX_BASE_2 = 1;

	/**
	 * This flag identify the third vertex that compose the tetrahedron base.
	 * @see #VERTEX_BASE_1
	 * @see #VERTEX_BASE_2
	 * @see #VERTEX_TOP
	 */	
	public static final int VERTEX_BASE_3 = 2;

	/**
	 * This flag identify the vertex that is at the top of the tetrahedron.
	 * @see #VERTEX_BASE_1
	 * @see #VERTEX_BASE_2
	 * @see #VERTEX_BASE_3
	 */
	public static final int VERTEX_TOP = 3;

	/**
	 * Get the vertices list from which the indexes are coming. This method has not to be confused with {@link #getVertices()}
	 * method that return only the vertices attached to this mesh. 
	 * @return the vertices list from which the indexes are coming.
	 */
	public Point3DContainer<T> getVerticesSource();

	/**
	 * Set the vertices list from which the indexes are coming. This method has not to be confused with {@link #getVertices()}
	 * method that return only the vertices attached to this mesh.
	 * @param verticesSource the vertices list from which the indexes are coming.
	 */
	public void setVerticesSource(Point3DContainer<T> verticesSource);

	/**
	 * Get the index within the underlying source of the vertex at the given <code>position</code> within the tetrahedron vertices.
	 * @param position the <code>position</code> within the tetrahedron vertices ({@link #VERTEX_BASE_1}, {@link #VERTEX_BASE_2}, {@link #VERTEX_BASE_3} or {@link #VERTEX_TOP})
	 * @return  the index within the vertex source of the vertex at the given <code>position</code> within the tetrahedron vertices
	 * @throws IllegalArgumentException if the given index is invalid
	 */
	public int getVertexIndice(int position);

	/**
	 * Get the vertex at the given <code>position</code> within the tetrahedron vertices.
	 * @param position the <code>position</code> within the tetrahedron vertices. ({@link #VERTEX_BASE_1}, {@link #VERTEX_BASE_2}, {@link #VERTEX_BASE_3} or {@link #VERTEX_TOP})
	 * @return the vertex at the given <code>position</code> within the tetrahedron vertices
	 * @throws IllegalArgumentException if the given index is invalid
	 */
	public Point3D getVertex(int position);

	/**
	 * Get the index of this vertex within the tetrahedron. Returned value can be {@link #VERTEX_BASE_1}, {@link #VERTEX_BASE_2}, {@link #VERTEX_BASE_3}, {@link #VERTEX_TOP} or <code><-1/code>. 
	 * The reference equality is used instead equals method for comparison.
	 * If the given point is not a vertex of the polyhedron, <code>-1</code> is returned.
	 * @param vertex the vertex to check
	 * @return the index of the vertex within the tetrahedron (-1 if the point is not a vertex of the tetrahedron)
	 */
	public int getVertexIndex(Point3D vertex);

	/**
	 * Get the vertices index of the tetrahedron.
	 * @return the vertices index of the tetrahedron
	 */
	public int[] getVerticesArray();
}
