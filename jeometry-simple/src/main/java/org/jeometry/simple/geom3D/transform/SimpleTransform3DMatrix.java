package org.jeometry.simple.geom3D.transform;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.transform.Transform3D;
import org.jeometry.geom3D.transform.Transform3DMatrix;
import org.jeometry.math.Matrix;
import org.jeometry.simple.math.SimpleMatrix;

/**
 * A simple implementation of a {@link org.jeometry.geom3D.transform.Transform3D 3D transformation} that relies on a 
 * {@link org.jeometry.geom3D.transform.Transform3D 3D transformation simple matrix}.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 *
 */
public class SimpleTransform3DMatrix extends SimpleMatrix implements Transform3DMatrix{

	/**
	 * Create a new {@link org.jeometry.geom3D.transform.Transform3D 3D transformation} set to the identity.
	 */
	public SimpleTransform3DMatrix() {
		super(new double[][] {{1, 0, 0, 0},
			                  {0, 1, 0, 0},
		                      {0, 0, 1, 0},
		                      {0, 0, 0, 1}
		                      });
	}

	@Override
	public Point3D transform(Point3D point3d) {
		
		if ((point3d != null)){
			Point3D result = JeometryFactory.createPoint3D();
			return (Point3D) multiply(point3d, result);
		}

		return null;
	}

	@Override
	public Point3D transform(Point3D point3d, Point3D result) {
        if (point3d != null){
			multiply(point3d, result);
		}
        
		return result;
	}

	@Override
	public Transform3D invertTransform() throws IllegalStateException {
		SimpleTransform3DMatrix result = new SimpleTransform3DMatrix();
		return invertTransform(result);
	}

	@Override
	public Transform3D invertTransform(Transform3D inverted) throws IllegalStateException, IllegalArgumentException {

		if (inverted != null) {
			if (inverted instanceof Transform3DMatrix) {
				
				
				
			} else {
				throw new IllegalArgumentException("Transform3DMatrix expected but got "+inverted.getClass().getSimpleName());
			}
		}
		
		return inverted;
	}

	@Override
	public Matrix getMatrix() {
		return this;
	}

	@Override
	public Matrix getMatrix(Matrix matrix) {
		
		if (matrix != null) {
			if ((matrix.getRowsCount() == 4) && (matrix.getColumnsCount() == 4)) {
				
			} else {
				throw new IllegalArgumentException("Expected [4x4] matrix but got ["+matrix.getRowsCount()+"x"+matrix.getColumnsCount()+"] one.");
			}
		}
		
		return matrix;
	}
	
	@Override
	public void setMatrix(Matrix matrix) {
		if (matrix != null) {
			if ((matrix.getRowsCount() == 4) && (matrix.getColumnsCount() == 4)) {
				for(int row = 0; row < 4; row++) {
					for(int col = 0; col < 4; col++) {
						setValue(row, col, matrix.getValue(row, col));
					}
				}
			} else {
				throw new IllegalArgumentException("Expected [4x4] matrix but got ["+matrix.getRowsCount()+"x"+matrix.getColumnsCount()+"] one.");
			}
		}
	}

}
