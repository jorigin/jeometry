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
	 * If the given input is <code>null</code>, then <code>null</code> is returned.
	 * @param point3d the {@link Point3D 3D point} to transform
	 * @return the transformed point or <code>null</code> if the input is <code>null</code>
	 * @see #transform(Point3D, Point3D)
	 * @see #transformAffect(Point3D)
	 */
	public Point3D transform(Point3D point3d);
	
	/**
	 * Apply this transform to the given {@link Point3D 3D point3d} and store it within the <code>result</code>.<br><br>
	 * If <code>point3d</code> is <code>null</code>, then all <code>result</code> coordinates are set to <code>Double.Nan</code>.
	 * @param point3d the {@link Point3D 3D point} to transform
	 * @param result the point that store the transformation result
	 * @return the transformed point (the same reference as <code>result</code>)
	 * @see #transform(Point3D)
	 * @see #transformAffect(Point3D)
	 */
	public Point3D transform(Point3D point3d, Point3D result);
	
	/**
	 * Apply this transform to the given {@link Point3D 3D point}. <br>
	 * The input point is modified.
	 * @param point3d the {@link Point3D 3D point} to transform
	 * @return the reference of the input point that has been modified
	 * @see #transform(Point3D)
	 * @see #transform(Point3D, Point3D)
	 */
	public Point3D transformAffect(Point3D point3d);
	
	/**
	 * Apply this transform inverse to the given {@link Point3D 3D point}.
	 * @param point3d the {@link Point3D 3D point} to transform
	 * @return the transformed point
	 */
	public Point3D transformInverse(Point3D point3d);
	
	/**
	 * Apply this transform inverse to the given {@link Point3D 3D point3d} and store it within the <code>result</code>.<br><br>
	 * If <code>point3d</code> is <code>null</code>, then all <code>result</code> coordinates are set to <code>Double.Nan</code>.
	 * @param point3d the {@link Point3D 3D point} to transform
	 * @param result the point that store the transformation result
	 * @return the transformed point (the same reference as <code>result</code>)
	 * @see #transformInverse(Point3D, Point3D)
	 * @see #transformInverseAffect(Point3D)
	 */
	public Point3D transformInverse(Point3D point3d, Point3D result);
	
	/**
	 * Apply this transform inverse to the given {@link Point3D 3D point}. <br>
	 * The input point is modified.
	 * @param point3d the {@link Point3D 3D point} to transform
	 * @return the reference of the input point that has been modified
	 * @see #transformInverse(Point3D)
	 * @see #transformInverseAffect(Point3D)
	 */
	public Point3D transformInverseAffect(Point3D point3d);
	
	/**
	 * Return a 3D transformation that is the inverse of the current one.
	 * @return a 3D transformation that is the inverse of the current one
	 * @throws IllegalStateException if the transform is not invertible
	 * @see #transformInverse(Point3D)
	 * @see #transformInverse(Point3D, Point3D)
	 */
	public Transform3D invertTransform() throws IllegalStateException;
	
	/**
	 * Return a 3D transformation that is the inverse of the current one.
	 * @param inverted the transformation that store the inverted transformation.
	 * @return a 3D transformation that is the inverse of the current one (reference the same object as <code>inverted</code>)
	 * @throws IllegalStateException if the transform is not invertible
	 * @throws IllegalArgumentException if the <code>inverted</code> does not enable to store the inverted transformation
	 */
	public Transform3D invertTransform(Transform3D inverted) throws IllegalStateException, IllegalArgumentException;
	
	/**
	 * Invert this transformation in place. The transformation is modified.
	 * @return a reference on this transformation
	 * @throws IllegalStateException if the transformation is not invertible
	 */
	public Transform3D invertTransformAffect() throws IllegalStateException;
	
}
