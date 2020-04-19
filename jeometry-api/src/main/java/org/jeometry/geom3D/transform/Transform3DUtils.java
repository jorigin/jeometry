package org.jeometry.geom3D.transform;

import org.jeometry.Jeometry;

/**
 * A class that provides utility methods for 3D transformation handling.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class Transform3DUtils {

	/**
	 * Get a {@link Transform3DMatrix 3D transformation matrix} from the given transform. If the input transform is already a 
	 * {@link Transform3DMatrix 3D transformation matrix}, the input reference is casted and returned.
	 * @param transform the transform to convert.
	 * @return a {@link Transform3DMatrix 3D transformation matrix} that represents the input transform.
	 */
	public static Transform3DMatrix toTransform3DMatrix(Transform3D transform) {
		Transform3DMatrix transformMatrix = null;
		
		if (transform != null) {
			if (transform instanceof Transform3DMatrix) {
				return transformMatrix = (Transform3DMatrix)transform;
			} else if (transform instanceof Transform3DQuaternion) {
				// TODO implements Transform3DUtils.toMatrix(Transform3D transform) for Transform3DQuaternion input.
			} else {
				throw new IllegalArgumentException("Cannot convert "+transform.getClass().getSimpleName()+" to "+Transform3DMatrix.class.getSimpleName());
			}
		}
		
		return transformMatrix;
	}
	
	/**
	 * Get a {@link Transform3DQuaternion 3D transformation quaternion} from the given transform. If the input transform is already a 
	 * {@link Transform3DQuaternion 3D transformation quaternion}, the input reference is casted and returned.
	 * @param transform the transform to convert.
	 * @return a {@link Transform3DQuaternion 3D transformation quaternion} that represents the input transform.
	 */
	public static Transform3DQuaternion toTransform3DQuaternion(Transform3D transform) {
		Transform3DQuaternion transformQuaternion = null;
		
		if (transform != null) {
			if (transform instanceof Transform3DQuaternion) {
				return transformQuaternion = (Transform3DQuaternion)transform;
			} else if (transform instanceof Transform3DMatrix) {
				// TODO implements Transform3DUtils.toTransform3DQuaternion(Transform3D transform) for Transform3DMatrix input.
			} else {
				throw new IllegalArgumentException("Cannot convert "+transform.getClass().getSimpleName()+" to "+Transform3DQuaternion.class.getSimpleName());
			}
		}
		
		return transformQuaternion;
	}
}
