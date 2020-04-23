package org.jeometry.simple.factory;

import org.jeometry.Jeometry;
import org.jeometry.factory.TransformBuilder;
import org.jeometry.geom3D.transform.Transform3DMatrix;
import org.jeometry.geom3D.transform.Transform3DQuaternion;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;
import org.jeometry.simple.geom3D.transform.SimpleTransform3DMatrix;
import org.jeometry.simple.geom3D.transform.SimpleTransform3DQuaternion;

/**
 * A {@link TransformBuilder transform builder} implementation that provide simples pure java implementations.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.3
 */
public class SimpleTransformBuilder implements TransformBuilder{

	@Override
	public Transform3DMatrix createTransform3DMatrix() {
		return new SimpleTransform3DMatrix();
	}

	@Override
	public Transform3DMatrix createTransform3DMatrix(Matrix matrix) {
		return new SimpleTransform3DMatrix(matrix);
	}

	@Override
	public Transform3DMatrix createTransform3DMatrix(Matrix rotation, Vector translation, double scale) {
	    return new SimpleTransform3DMatrix(rotation, translation, scale);
	}

	@Override
	public Transform3DMatrix createTransform3DMatrix(double[][] matrix) {
		return new SimpleTransform3DMatrix(matrix);
	}

	@Override
	public Transform3DQuaternion createTransform3DQuaternion() {
		return new SimpleTransform3DQuaternion();
	}

}
