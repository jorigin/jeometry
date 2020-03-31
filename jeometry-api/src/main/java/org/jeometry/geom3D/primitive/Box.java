package org.jeometry.geom3D.primitive;

import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;

/**
 * A Box is an 6 faces {@link Mesh mesh} defined by its maximal and minimal vertices. 
 * A box is axis aligned (all of its faces are aligned to the underlying reference system).
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.0
 */
public interface Box {

	/**
	 * Flag used to identify the bottom face of the box. The bottom face is 
	 * the face composed by minimal points following Z.
	 * @see #getFace(int)
	 */
	public static final int FACE_BOTTOM = 0;

	/**
	 * Flag used to identify the top face of the box. The top face is 
	 * the face composed by maximal points following Z.
	 * @see #getFace(int)
	 */
	public static final int FACE_TOP    = 1;

	/**
	 * Flag used to identify the left face of the box. The left face is 
	 * the face composed by minimal points following Y.
	 * @see #getFace(int)
	 */
	public static final int FACE_LEFT   = 2;


	/**
	 * Flag used to identify the right face of the box. The right face is 
	 * the face composed by maximal points following Y.
	 * @see #getFace(int)
	 */
	public static final int FACE_RIGHT  = 3;

	/**
	 * Flag used to identify the front face of the box. The front face is 
	 * the face composed by maximal points following X.
	 * @see #getFace(int)
	 */
	public static final int FACE_FRONT  = 4;

	/**
	 * Flag used to identify the back face of the box. The back face is 
	 * the face composed by minimal points following X.
	 * @see #getFace(int)
	 */
	public static final int FACE_BACK   = 5;

	/**
	 * Get the width of the box. Width of the box is the measure
	 * of the box following the X axis.
	 * @return double the height of the box
	 */
	public double getWidth();

	/**
	 * Get the height of the box. Height of the box is the measure
	 * of the box following the Y axis.
	 * @return double the height of the box
	 */
	public double getHeight();

	/**
	 * Get the length of the box. Length of the box is the measure
	 * of the box following the Z axis.
	 * @return double the height of the box
	 */
	public double getLength();

	/**
	 * Get the maximal point of the box.
	 * @return the point with maximal X, y and Z.
	 */
	public Point3D getMax();


	/**
	 * Get the minimal point of the box.
	 * @return the point with minimal X, Y and Z.
	 */
	public Point3D getMin();

	/**
	 * Get a face of the box. Face are identified by a flag (FACE_BOTTOM, FACE_TOP, 
	 * FACE_LEFT, FACE_RIGHT, FACE_FRONT, FACE_BACK)
	 * @param flag the flag
	 * @return the face corresponding to the flag.
	 * @see #FACE_BOTTOM
	 * @see #FACE_TOP
	 * @see #FACE_LEFT
	 * @see #FACE_RIGHT
	 * @see #FACE_FRONT
	 * @see #FACE_BACK
	 */
	public Face<Point3D> getFace(int flag);

	/**
	 * Scale the box by a factor given in parameter. This method multiply each vertex of the box by the <code>factor</code> and 
	 * then update all other values (dimensions, ...)
	 * @param factor the scale factor.
	 */
	public void scale(double factor);

}
