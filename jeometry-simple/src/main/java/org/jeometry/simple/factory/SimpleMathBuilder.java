package org.jeometry.simple.factory;

import org.jeometry.Jeometry;
import org.jeometry.factory.MathBuilder;
import org.jeometry.math.Matrix;
import org.jeometry.math.Quaternion;
import org.jeometry.math.Vector;
import org.jeometry.math.decomposition.CholeskyDecomposition;
import org.jeometry.math.decomposition.EigenDecomposition;
import org.jeometry.math.decomposition.LUDecomposition;
import org.jeometry.math.decomposition.QRDecomposition;
import org.jeometry.math.decomposition.SVDDecomposition;
import org.jeometry.math.solver.Solver;
import org.jeometry.simple.math.SimpleMatrix;
import org.jeometry.simple.math.SimpleQuaternion;
import org.jeometry.simple.math.SimpleVector;
import org.jeometry.simple.math.decomposition.SimpleCholeskyDecomposition;
import org.jeometry.simple.math.decomposition.SimpleEigenDecomposition;
import org.jeometry.simple.math.decomposition.SimpleLUDecomposition;
import org.jeometry.simple.math.decomposition.SimpleQRDecomposition;
import org.jeometry.simple.math.decomposition.SimpleSVDDecomposition;
import org.jeometry.simple.math.solver.SimpleGaussEliminationSolver;

/**
 * A {@link MathBuilder math builder} implementation that provide simples pure java implementations.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class SimpleMathBuilder implements MathBuilder{

	@Override
	public Vector createVector(int size) {
		return new SimpleVector(size);
	}

	@Override
	public Vector createVector(double[] components) {
		return new SimpleVector(components);
	}

	@Override
	public Vector createVector(Vector source) {
		return new SimpleVector(source);
	}
	
	@Override
	public Matrix createMatrix(int rows, int cols) {
		return new SimpleMatrix(rows, cols);
	}

	@Override
	public Matrix createMatrix(double[][] data) {
		return new SimpleMatrix(data);
	}

	@Override
	public Matrix createMatrix(int rows, int columns, double[] data, int ordering) {
		return new SimpleMatrix(rows, columns, data, ordering);
	}

	@Override
	public Matrix createMatrix(Matrix matrix) {
		return new SimpleMatrix(matrix);
	}
	
	@Override
	public Matrix createMatrixEye(int size) {
		SimpleMatrix m = new SimpleMatrix(size, size);
		
		for(int row = 0; row < m.getRowsCount(); row++) {
			for(int col = 0; col < m.getColumnsCount(); col++) {
				if (row == col) {
					m.setValue(row, col, 1.0d);
				} else {
					m.setValue(row, col, 0.0d);
				}
			}
		}
		
		return m;
	}

	@Override
	public Quaternion createQuaternion() {
		return new SimpleQuaternion();
	}

	@Override
	public Quaternion createQuaternion(double scalar, double i, double j, double k) {
		return new SimpleQuaternion(scalar, i, j, k);
	}

	@Override
	public Solver createSolver(int type) {
		if (type == Solver.METHOD_GAUSS) {
			return new SimpleGaussEliminationSolver();
		}
		return null;
	}

	@Override
	public Solver createSolver() {
		return new SimpleGaussEliminationSolver();
	}

	@Override
	public LUDecomposition createLUDecomposition(Matrix matrix) {
		return new SimpleLUDecomposition(matrix);
	}

	@Override
	public EigenDecomposition createEigenDecomposition(Matrix matrix) {
		return new SimpleEigenDecomposition(matrix);
	}

	@Override
	public SVDDecomposition createSVDDecomposition(Matrix matrix) {
		return new SimpleSVDDecomposition(matrix);
	}

	@Override
	public QRDecomposition createQRDecomposition(Matrix matrix) {
		return new SimpleQRDecomposition(matrix);
	}
	
	@Override
	public CholeskyDecomposition createCholeskyDecomposition(Matrix matrix) {
		return new SimpleCholeskyDecomposition(matrix);
	}
}
