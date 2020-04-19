package org.jeometry.geom3D.transform;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.point.Point3D;

/**
 * A transformation within a 3D space. Objects that implements this class can transform geometric primitives.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface Transform3D {

	/**
	 * Apply this transform to the given {@link Point3D 3D point}.
	 * @param point3d the {@link Point3D 3D point} to transform.
	 * @return the transformed point.
	 * @see #transform(Point3D, Point3D)
	 */
	public Point3D transform(Point3D point3d);
	
	/**
	 * Apply this transform to the given {@link Point3D 3D point3d} and store it within the <code>result</code>.
	 * @param point3d the {@link Point3D 3D point} to transform.
	 * @param result the point that store the transformation result.
	 * @return the transformed point (the same reference as <code>result</code>).
	 * @see #transform(Point3D)
	 */
	public Point3D transform(Point3D point3d, Point3D result);
	
	/**
	 * Return a 3D transformation that is the inverse of the current one.
	 * @return a 3D transformation that is the inverse of the current one.
	 * @throws IllegalStateException if the transform is not invertible.
	 */
	public Transform3D invertTransform() throws IllegalStateException;
	
	/**
	 * Return a 3D transformation that is the inverse of the current one.
	 * @param inverted the transformation that store the inverted transformation.
	 * @return a 3D transformation that is the inverse of the current one (reference the same object as <code>inverted</code>).
	 * @throws IllegalStateException if the transform is not invertible.
	 * @throws IllegalArgumentException if the <code>inverted</code> does not enable to store the inverted transformation.
	 */
	public Transform3D invertTransform(Transform3D inverted) throws IllegalStateException, IllegalArgumentException;
	
	
}
