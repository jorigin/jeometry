package org.jeometry.simple.geom3D.transform;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.transform.Transform3D;
import org.jeometry.geom3D.transform.Transform3DMatrix;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

/**
 * A simple implementation of a {@link org.jeometry.geom3D.transform.Transform3D 3D transformation} that relies on a 
 * {@link org.jeometry.geom3D.transform.Transform3D 3D transformation simple matrix}.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 *
 */
public class SimpleTransform3DMatrix implements Transform3DMatrix{

	/**
	 * The underlying matrix.
	 */
	private Matrix matrix = null;
	
	/**
	 * Create a new {@link org.jeometry.geom3D.transform.Transform3D 3D transformation} set to the identity.
	 */
	public SimpleTransform3DMatrix() {
		this.matrix = JeometryFactory.createMatrixEye(4);
	}

	/**
	 * Create a new {@link org.jeometry.geom3D.transform.Transform3D 3D transformation} 
	 * that relies on the given 4x4 affine 3D transform matrix.
	 * @param matrix the affine 3D transform
	 */
	public SimpleTransform3DMatrix(Matrix matrix) {
		this.matrix = JeometryFactory.createMatrixEye(4);

		if (matrix != null) {
			if ((matrix.getRowsCount() != 4) || (matrix.getColumnsCount() != 4)) {
				throw new IllegalArgumentException("Invalid input matrix size "+matrix.getRowsCount()+"x"+matrix.getColumnsCount()+", expected 4x4.");
			}

			this.matrix.setValues(matrix);			
		} else {
			this.matrix.setValue(0, 0, 1);
			this.matrix.setValue(1, 1, 1);
			this.matrix.setValue(2, 2, 1);
			this.matrix.setValue(3, 3, 1);
		}
	}

	/**
	 * Create a new {@link org.jeometry.geom3D.transform.Transform3D 3D transformation} 
	 * that relies on the given 4x4 affine 3D transform matrix.
	 * @param matrix the affine 3D transform
	 */
	public SimpleTransform3DMatrix(double[][] matrix) {
		this.matrix = JeometryFactory.createMatrixEye(4);

		if (matrix != null) {
			if ((matrix.length != 4) || (matrix[0].length != 4)) {
				throw new IllegalArgumentException("Invalid input matrix size "+matrix.length+"x"+matrix[0].length+", expected 4x4.");
			}

			this.matrix.setDataArray2D(matrix);
		} else {
			this.matrix.setValue(0, 0, 1);
			this.matrix.setValue(1, 1, 1);
			this.matrix.setValue(2, 2, 1);
			this.matrix.setValue(3, 3, 1);

		}
	}

	/**
	 * Construct a new {@link Transform3DMatrix} that relies on the given parameters.<br><br>
	 * The 4x4 underlying matrix is defined such that:
	 * $$
	 * M = \begin{bmatrix} 
	 *        sr_{00} &amp; sr_{01} &amp; sr_{02} &amp; t_{x}  \\
	 *        sr_{10} &amp; sr_{11} &amp; sr_{12} &amp; t_{y}  \\
	 *        sr_{20} &amp; sr_{21} &amp; sr_{22} &amp; t_{z}  \\
	 *           0   &amp;    0   &amp;    0   &amp;  1  
	 *     \end{bmatrix}
	 * $$
	 * where:
	 * <ul>
	 * <li><i>r<sub>ii</sub></i> form the 3x3 rotation matrix
	 * <li><i>t<sub>k</sub></i> form the translation vector
	 * <li><i>s</i> is the scale value
	 * </ul>
	 * @param rotation the rotation
	 * @param translation the translation as a 3 dimensioned vector
	 * @param scale the scale factor (applied to all axis)
	 * @throws IllegalArgumentException if the rotation matrix is not 3x3 sized, if the translation Vector is not 3 dimensioned
	 */
	public SimpleTransform3DMatrix(Matrix rotation, Vector translation, double scale) {
		this.matrix = JeometryFactory.createMatrixEye(4);

		this.matrix.setValue(0, 0, 1);
		this.matrix.setValue(1, 1, 1);
		this.matrix.setValue(2, 2, 1);
		this.matrix.setValue(3, 3, 1);

		if (rotation != null) {

			if ((rotation.getRowsCount() != 3) || (rotation.getColumnsCount() != 3)) {
				throw new IllegalArgumentException("Invalid rotation matrix size "+rotation.getRowsCount()+"x"+rotation.getColumnsCount()+", expected 3x3.");
			}

			this.matrix.setValue(0, 0, scale*rotation.getValue(0, 0)); this.matrix.setValue(0, 1, scale*rotation.getValue(0, 1)); this.matrix.setValue(0, 2, scale*rotation.getValue(0, 2));
			this.matrix.setValue(1, 0, scale*rotation.getValue(1, 0)); this.matrix.setValue(1, 1, scale*rotation.getValue(1, 1)); this.matrix.setValue(1, 2, scale*rotation.getValue(1, 2));
			this.matrix.setValue(2, 0, scale*rotation.getValue(2, 0)); this.matrix.setValue(2, 1, scale*rotation.getValue(2, 1)); this.matrix.setValue(2, 2, scale*rotation.getValue(2, 2));
		}

		if (translation != null) {

			if ((translation.getDimension() != 3) ||((translation.getDimension() != 4))) {
				throw new IllegalArgumentException("Invalid translation vector dimension "+translation.getDimension()+", expected 3 or 4 for homogeneous.");
			}

			this.matrix.setValue(0, 3, translation.getValue(0));
			this.matrix.setValue(0, 3, translation.getValue(1));
			this.matrix.setValue(0, 3, translation.getValue(2));
		}
	}

	@Override
	public Point3D transform(final Point3D point3d) {
		if (point3d != null) {
			return transform(point3d, JeometryFactory.createPoint3D());
		} else {
			return null;
		}
	}

	@Override
	public Point3D transform(final Point3D point3d, Point3D result) {

		if (result != null) {
			if (point3d != null){
				result.setValues(this.matrix.getValue(0, 0)*point3d.getX() + this.matrix.getValue(0, 1)*point3d.getY() + this.matrix.getValue(0, 2)*point3d.getZ() + this.matrix.getValue(0, 3), 
						this.matrix.getValue(1, 0)*point3d.getX() + this.matrix.getValue(1, 1)*point3d.getY() + this.matrix.getValue(1, 2)*point3d.getZ() + this.matrix.getValue(1, 3), 
						this.matrix.getValue(2, 0)*point3d.getX() + this.matrix.getValue(2, 1)*point3d.getY() + this.matrix.getValue(2, 2)*point3d.getZ() + this.matrix.getValue(2, 3));
			} else {
				result.setX(Double.NaN);
				result.setY(Double.NaN);
				result.setZ(Double.NaN);
			}
		}



		return result;
	}

	@Override
	public Point3D transformAffect(Point3D point3d) {
		return transform(point3d, point3d);
	}

	@Override
	public Point3D transformInverse(Point3D point3d) {
		if (point3d != null) {
			return transformInverse(point3d, JeometryFactory.createPoint3D());
		} else {
			return null;
		}

	}

	@Override
	public Point3D transformInverse(Point3D point3d, Point3D result) {

		if (result != null) {
			if ((point3d != null)){

				// Inverse transform F' can be obtained with::
				//
				// F' = [  R' | R'T]
				//      [0 0 0   1 ]
				// Where R and T are the original rotation / translation
				result.setValues(this.matrix.getValue(0, 0)*point3d.getX() + this.matrix.getValue(1, 0)*point3d.getY() + this.matrix.getValue(2, 0)*point3d.getZ() - this.matrix.getValue(0, 0)*this.matrix.getValue(0, 3) - this.matrix.getValue(1, 0)*this.matrix.getValue(1, 3) - this.matrix.getValue(2, 0)*this.matrix.getValue(2, 3), 
						this.matrix.getValue(0, 1)*point3d.getX() + this.matrix.getValue(1, 1)*point3d.getY() + this.matrix.getValue(2, 1)*point3d.getZ() - this.matrix.getValue(0, 1)*this.matrix.getValue(0, 3) - this.matrix.getValue(1, 1)*this.matrix.getValue(1, 3) - this.matrix.getValue(2, 1)*this.matrix.getValue(2, 3), 
						this.matrix.getValue(0, 2)*point3d.getX() + this.matrix.getValue(1, 2)*point3d.getY() + this.matrix.getValue(2, 2)*point3d.getZ() - this.matrix.getValue(0, 2)*this.matrix.getValue(0, 3) - this.matrix.getValue(1, 2)*this.matrix.getValue(1, 3) - this.matrix.getValue(2, 2)*this.matrix.getValue(2, 3));
			} else {
				result.setX(Double.NaN);
				result.setY(Double.NaN);
				result.setZ(Double.NaN);
			}
		}

		return result;
	}

	@Override
	public Point3D transformInverseAffect(Point3D point3d) {
		return transformInverse(point3d, point3d);
	}

	@Override
	public Transform3DMatrix invertTransform() throws IllegalStateException {
		Transform3DMatrix result = JeometryFactory.createTransform3DMatrix();
		return (Transform3DMatrix) invertTransform(result);
	}

	@Override
	public Transform3D invertTransform(Transform3D inverted) throws IllegalStateException, IllegalArgumentException {

		if (inverted != null) {
			if (inverted instanceof Transform3DMatrix) {

				((Transform3DMatrix) inverted).getMatrix().setDataArray2D(new double[][] {
					{this.matrix.getValue(0, 0), this.matrix.getValue(1, 0), this.matrix.getValue(2, 0), -1.0d*this.matrix.getValue(0, 0)*this.matrix.getValue(0, 3) - this.matrix.getValue(1, 0)*this.matrix.getValue(1, 3) - this.matrix.getValue(2, 0)*this.matrix.getValue(2, 3)},
					{this.matrix.getValue(0, 1), this.matrix.getValue(1, 1), this.matrix.getValue(2, 1), -1.0d*this.matrix.getValue(0, 1)*this.matrix.getValue(0, 3) - this.matrix.getValue(1, 1)*this.matrix.getValue(1, 3) - this.matrix.getValue(2, 1)*this.matrix.getValue(2, 3)},
					{this.matrix.getValue(0, 2), this.matrix.getValue(1, 2), this.matrix.getValue(2, 2), -1.0d*this.matrix.getValue(0, 2)*this.matrix.getValue(0, 3) - this.matrix.getValue(1, 2)*this.matrix.getValue(1, 3) - this.matrix.getValue(2, 2)*this.matrix.getValue(2, 3)},
					{0, 0, 0, 1},
				});

			} else {
				throw new IllegalArgumentException(Transform3DMatrix.class.getSimpleName()+" expected but got "+inverted.getClass().getSimpleName());
			}
		}

		return inverted;
	}

	@Override
	public Transform3DMatrix invertTransformAffect() throws IllegalStateException {
		return (Transform3DMatrix)invertTransform(this);
	}

	@Override
	public Matrix getMatrix() {
		return this.matrix;
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
		this.matrix = matrix;
	}

}
