package org.jeometry.simple.math;

import java.awt.Dimension;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

/**
 * A simple implementation of the {@link Matrix} interface. 
 * This class relies on a two-dimensional double array for the matrix data storage.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleMatrix implements Matrix {

	private double[][] data = null;

	private int rows = 0;

	private int cols = 0;

	@Override
	public double[] getDataArray(int ordering) {
		return getDataArray(ordering, new double[rows*cols]);
	}

	@Override
	public double[] getDataArray(int ordering, double[] output) throws IllegalArgumentException {

		if (output != null) {
			if (output.length >= rows * cols) {
				if (ordering == Matrix.ROW_MAJOR) {

					int index = 0;
					for(int row = 0; row < rows; row++) {
						for(int col = 0; col < cols; col++) {
							output[index] = data[row][col];
							index = index + 1;
						}
					}

				} else if (ordering == Matrix.COLUMN_MAJOR) {
					int index = 0;
					for(int col = 0; col < cols; col++) {
						for(int row = 0; row < rows; row++) {
							output[index] = data[row][col];
							index = index + 1;
						}
					}
				}
			} else {
				throw new IllegalArgumentException("Invalid output size ("+output.length+") as it must be superior to "+(rows*cols));
			}


		}

		return output;
	}

	@Override
	public void setDataArray(int ordering, double[] data) throws IllegalArgumentException {
		if (data != null) {
			if (data.length >= (rows*cols)) {
				if (ordering == Matrix.ROW_MAJOR) {

					for(int row = 0; row < rows; row++) {
						for(int col = 0; col < cols; col++) {
							this.data[row][col] = data[row*cols+col];
						}
					}

				} else if (ordering == Matrix.COLUMN_MAJOR) {
					for(int col = 0; col < cols; col++) {
						for(int row = 0; row < rows; row++) {
							this.data[row][col] = data[col*rows+row];
						}
					}
				}
			} else {
				throw new IllegalArgumentException("Invalid input size ("+data.length+") as it must be superior to "+(rows*cols));
			}
		} else {
			this.data = new double[rows][cols];
		}
	}

	@Override
	public double[][] getDataArray2D() {
		return getDataArray2D(new double[rows][cols]);
	}

	@Override
	public double[][] getDataArray2D(double[][] output) {
		if ((output != null) && (output.length > 0)){
			if ((output.length >= rows) && (output[0].length >= cols)) {
				for(int row = 0; row < rows; row++) {
					for(int col = 0; col < cols; col++) {
						output[row][col] = data[row][col];
					}
				}
			} else {
				throw new IllegalArgumentException("Invalid output size ["+output.length+"x"+output[0].length+"] as it must be superior to ["+rows+"x"+cols+"]");
			}
		}
		return output;
	}

	@Override
	public void setDataArray2D(double[][] data) throws IllegalArgumentException {
		if ((data != null) && (data.length > 0)) {

			for(int row = 0; row < rows; row++) {
				for(int col = 0; col < cols; col++) {
					this.data[row][col] = data[row][col];
				}
			}

		} else {
			throw new IllegalArgumentException("Invalid input size ["+data.length+"x"+data[0].length+"] as it must be superior to ["+rows+"x"+cols+"]");
		}
	}

	@Override
	public double getValue(int row, int col) throws IllegalArgumentException {
		if (row < rows) {

			if (col < cols) {
				return data[row][col];
			} else {
				throw new IllegalArgumentException("Invalid column index "+col+" as it must be positive and inferior to "+cols);
			}

		} else {
			throw new IllegalArgumentException("Invalid row index "+row+" as it must be positive and inferior to "+rows);
		}
	}

	@Override
	public void setValue(int row, int col, double value) throws IllegalArgumentException {
		if (row < rows) {

			if (col < cols) {
				data[row][col] = value;
			} else {
				throw new IllegalArgumentException("Invalid column index "+col+" as it must be positive and inferior to "+cols);
			}

		} else {
			throw new IllegalArgumentException("Invalid row index "+row+" as it must be positive and inferior to "+rows);
		}
	}

	@Override
	public void setValues(Matrix matrix) {

		if (matrix == null) {
			throw new IllegalArgumentException("Invalid null input.");
		}

		if ((matrix.getRowsCount() > this.getRowsCount()) || (matrix.getColumnsCount() > this.getColumnsCount())) {
			throw new IllegalArgumentException("Input ["+matrix.getRowsCount()+"x"+matrix.getColumnsCount()+"] matrix has incorrect size.");
		}

		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				setValue(row, col, matrix.getValue(row, col));
			}
		}
	}

	@Override
	public void setTo(double value) {
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				data[row][col] = value;
			}
		}
	}

	@Override
	public Vector getColumn(int index) {
		return getColumn(index, JeometryFactory.createVector(getRowsCount()));
	}

	@Override
	public Vector getColumn(int index, Vector output) {
		if ((index < 0) || (index >= getColumnsCount())) {
			throw new IllegalArgumentException("Invalid column index "+index+", expected values between 0 to "+(getColumnsCount()-1));
		}
		
		if (output != null) {
			if (output.getDimension() != getRowsCount()) {
				throw new IllegalArgumentException("Invalid output size "+output.getDimension()+", expected "+getRowsCount());
			}
			
			for(int row = 0; row < getRowsCount(); row++) {
				output.setValue(row, getValue(row, index));
			}
		}
		return output;
	}

	@Override
	public double[] getColumn(int index, double[] output) {
		if ((index < 0) || (index >= getColumnsCount())) {
			throw new IllegalArgumentException("Invalid column index "+index+", expected values between 0 to "+(getColumnsCount()-1));
		}
		
		if (output != null) {
			if (output.length != getRowsCount()) {
				throw new IllegalArgumentException("Invalid output size "+output.length+", expected "+getRowsCount());
			}
			
			for(int row = 0; row < getRowsCount(); row++) {
				output[row] = getValue(row, index);
			}
		}
		return output;
	}

	@Override
	public Matrix setColumn(int index, Vector input) {
		if ((index < 0) || (index >= getColumnsCount())) {
			throw new IllegalArgumentException("Invalid column index "+index+", expected values between 0 to "+(getColumnsCount()-1));
		}
		
		if (input != null) {
			if (input.getDimension() != getRowsCount()) {
				throw new IllegalArgumentException("Invalid input size "+input.getDimension()+", expected "+getRowsCount());
			}
			
			for(int row = 0; row < getRowsCount(); row++) {
				setValue(row, index, input.getValue(row));
			}
		}
		return this;
	}

	@Override
	public Matrix setColumn(int index, double[] input) {
		if ((index < 0) || (index >= getColumnsCount())) {
			throw new IllegalArgumentException("Invalid column index "+index+", expected values between 0 to "+(getColumnsCount()-1));
		}
		
		if (input != null) {
			if (input.length != getRowsCount()) {
				throw new IllegalArgumentException("Invalid input size "+input.length+", expected "+getRowsCount());
			}
			
			for(int row = 0; row < getRowsCount(); row++) {
				setValue(row, index, input[row]);
			}
		}
		return this;
	}

	@Override
	public Vector getRow(int index) {
       return getRow(index, JeometryFactory.createVector(getColumnsCount()));
	}

	@Override
	public Vector getRow(int index, Vector output) {
		if ((index < 0) || (index >= getRowsCount())) {
			throw new IllegalArgumentException("Invalid row index "+index+", expected values between 0 to "+(getRowsCount()-1));
		}
		
		if (output != null) {
			if (output.getDimension() != getColumnsCount()) {
				throw new IllegalArgumentException("Invalid output size "+output.getDimension()+", expected "+getColumnsCount());
			}
			
			for(int column = 0; column < getColumnsCount(); column++) {
				output.setValue(column, getValue(index, column));
			}
		}
		return output;
	}

	@Override
	public double[] getRow(int index, double[] output) {
		if ((index < 0) || (index >= getRowsCount())) {
			throw new IllegalArgumentException("Invalid row index "+index+", expected values between 0 to "+(getRowsCount()-1));
		}
		
		if (output != null) {
			if (output.length != getColumnsCount()) {
				throw new IllegalArgumentException("Invalid output size "+output.length+", expected "+getColumnsCount());
			}
			
			for(int column = 0; column < getColumnsCount(); column++) {
				output[column] = getValue(index, column);
			}
		}
		return output;
	}

	@Override
	public Matrix setRow(int index, Vector input) {
		if ((index < 0) || (index >= getRowsCount())) {
			throw new IllegalArgumentException("Invalid row index "+index+", expected values between 0 to "+(getRowsCount()-1));
		}
		
		if (input != null) {
			if (input.getDimension() != getColumnsCount()) {
				throw new IllegalArgumentException("Invalid input size "+input.getDimension()+", expected "+getColumnsCount());
			}
			
			for(int column = 0; column < getColumnsCount(); column++) {
				setValue(index, column, input.getValue(column));
			}
		}
		return this;
	}

	@Override
	public Matrix setRow(int index, double[] input) {
		if ((index < 0) || (index >= getRowsCount())) {
			throw new IllegalArgumentException("Invalid row index "+index+", expected values between 0 to "+(getRowsCount()-1));
		}
		
		if (input != null) {
			if (input.length != getColumnsCount()) {
				throw new IllegalArgumentException("Invalid input size "+input.length+", expected "+getColumnsCount());
			}
			
			for(int column = 0; column < getColumnsCount(); column++) {
				setValue(index, column, input[column]);
			}
		}
		return this;
	}
	
	@Override
	public Matrix extract(int rowOffset, int columnOffset, int rowCount, int columnCount) {
		return extract(rowOffset, columnOffset, rowCount, columnCount, JeometryFactory.createMatrix(rowCount, columnCount));
	}

	@Override
	public Matrix extract(int rowOffset, int columnOffset, int rowCount, int columnCount, Matrix result) {
		if ((rowOffset < 0) || (rowOffset >= getRowsCount())){
			throw new IllegalArgumentException("Invalid row offset "+rowOffset+" extpected ["+0+", "+(getRowsCount()-1)+"] values.");
		}

		if ((columnOffset < 0) || (columnOffset >= getColumnsCount())){
			throw new IllegalArgumentException("Invalid column offset "+columnOffset+" extpected ["+0+", "+(getColumnsCount()-1)+"] values.");
		}

		if ((rowCount < 0) || (rowOffset +rowCount > getRowsCount())){
			throw new IllegalArgumentException("Invalid row count "+rowCount+" extpected ["+0+", "+(getRowsCount()-rowOffset)+"] values.");
		}

		if ((columnCount < 0) || (columnOffset +columnCount > getColumnsCount())){
			throw new IllegalArgumentException("Invalid column count "+columnCount+" extpected ["+0+", "+(getColumnsCount()-columnOffset)+"] values.");
		}


		if (result != null) {

			if ((result.getRowsCount() < rowCount) || (result.getColumnsCount() < columnCount)){
				throw new IllegalArgumentException("Invalid result size ["+result.getRowsCount()+"x"+result.getColumnsCount()+"], expected ["+rowCount+"x"+columnCount+"].");
			}

			for(int row = 0; row < result.getRowsCount(); row = row +1 ) {
				for(int col = 0; col < result.getColumnsCount(); col = col +1 ) {
					result.setValue(row, col, getValue(row+rowOffset, col+columnOffset));
				}
			}

		} else {
			throw new IllegalArgumentException("Invalid (null) result");
		}

		return result;
	}

	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public int getColumnsCount() {
		return cols;
	}

	@Override
	public Dimension getDimension() {
		return new Dimension(rows, cols);
	}

	@Override
	public double determinant() {

		if (rows == cols) {
			return determinant(data, rows);
		} else {
			return Double.NaN;
		}
	}

	/**
	 * Compute the determinant of the submatrix represented by the given <code>data</code> and determinated by the given offsets.
	 * @param data the original data (of the original matrix)
	 * @param rowOffset te offset of the row.
	 * @param colOffset the offset of the column.
	 * @param size the size of the submatrix.
	 * @return the determinant of the submatrix.
	 */
	private double determinant(double[][] data, int size) {

		if (size == 1) {
			return data[0][0];
		} else if (size == 2) {
			return ((data[0][0] * data[1][1]) - (data[1][0] * data[0][1]));
		} else if (size == 3){

			return    data[0][0] * (data[1][1] * data[2][2] - data[1][2] * data[2][1]) 
					- data[0][1] * (data[1][0] * data[2][2] - data[1][2] * data[2][0]) 
					+ data[0][2] * (data[1][0] * data[2][1] - data[1][1] * data[2][0]);

		} else {
			double det = 0;

			double[][] submatrix = new double[size - 1][size - 1];

			for (int x = 0; x < size; x++) {
				int subi = 0; 
				for (int i = 1; i < size; i++) {
					int subj = 0;
					for (int j = 0; j < size; j++) {
						if (j == x)
							continue;
						submatrix[subi][subj] = data[i][j];
						subj++;
					}
					subi++;
				}
				det = det + (Math.pow(-1, x) * data[0][x] * determinant( submatrix, size - 1 ));
			}

			return det;
		}
	}

	@Override
	public Matrix transpose() {
		SimpleMatrix transpose = new SimpleMatrix(getColumnsCount(), getRowsCount());
		return transpose(transpose);
	}

	@Override
	public Matrix transpose(Matrix result) throws IllegalArgumentException {
		if (result != null) {
			if ((result.getRowsCount() == getColumnsCount()) && (result.getColumnsCount() == getRowsCount())) {
				for(int row = 0; row < getRowsCount(); row++) {
					for(int col = 0; col < getColumnsCount(); col++) {
						result.setValue(col, row, data[row][col]);
					}
				}
			} else {
				throw new IllegalArgumentException("Invalid result matrix size ["+result.getRowsCount()+", "+result.getColumnsCount()+"]. Expected ["+getColumnsCount()+", "+getRowsCount()+"]");
			}
		}
		return result;
	}

	@Override
	public Matrix transposeAffect() {
		double[][] transpose = new double[getColumnsCount()][getRowsCount()];

		for(int row = 0; row < getRowsCount(); row++) {
			for(int col = 0; col < getColumnsCount(); col++) {
				transpose[col][row] = data[row][col];		
			}
		}

		data = transpose;

		return this;
	}

	@Override
	public Matrix multiply(Matrix b) throws IllegalArgumentException{
		if (b != null) {

			if (getColumnsCount() == b.getRowsCount()) {
				return  multiply(b, new SimpleMatrix(getRowsCount(), b.getColumnsCount()));
			} else {
				throw new IllegalArgumentException("Invalid matrix sizes, first matrix columns ("+getColumnsCount()+") differs from second matrix rows ("+b.getRowsCount()+").");
			}

		} else {
			throw new IllegalArgumentException("Second operand matrix cannot be null.");
		}
	}

	@Override
	public Matrix multiply(Matrix b, Matrix result) throws IllegalArgumentException{
		if (b != null) {

			if (result != null) {
				if (getColumnsCount() == b.getRowsCount()) {

					if ((result.getRowsCount() == getRowsCount()) && (result.getColumnsCount() == b.getColumnsCount())) {

						double value = 0.0d;
						for(int resultRow = 0; resultRow < result.getRowsCount(); resultRow++) {
							for(int resultColumn = 0; resultColumn < result.getColumnsCount(); resultColumn++) {
								value = 0.0d;
								for(int commonIndex = 0; commonIndex < getColumnsCount(); commonIndex++) {
									value = value + getValue(resultRow, commonIndex)*b.getValue(commonIndex, resultColumn);
								}

								result.setValue(resultRow, resultColumn, value);
							}
						}

					} else {
						throw new IllegalArgumentException("Invalid result matrix size ["+result.getRowsCount()+"x"+result.getColumnsCount()+"]. Expected ["+getRowsCount()+"x"+b.getColumnsCount()+"].");
					}

				} else {
					throw new IllegalArgumentException("Invalid matrix sizes, first matrix columns ("+getColumnsCount()+") differs from second matrix rows ("+b.getRowsCount()+").");
				}
			}

			return result;
		} else {
			throw new IllegalArgumentException("Second operand matrix cannot be null.");
		}
	}

	@Override
	public Matrix multiplyAffect(Matrix b) throws IllegalArgumentException {

		if (b != null) {
			if ((getColumnsCount() == b.getRowsCount()) && (getColumnsCount() == b.getColumnsCount())) {

				double[][] result = new double[getRowsCount()][getColumnsCount()];

				double value = 0.0d;
				for(int row = 0; row < getRowsCount(); row++) {
					for(int col = 0; col < getColumnsCount(); col++) {
						value = 0.0d;
						for(int commonIndex = 0; commonIndex < getColumnsCount(); commonIndex++) {
							value = value + getValue(row, commonIndex)*b.getValue(commonIndex, col);
						}
						result[row][col] = value;
					}
				}

				data = result;
			} else {
				new IllegalArgumentException("Invalid matrices size ["+getRowsCount()+"x"+getColumnsCount()+"] and ["+b.getRowsCount()+"x"+b.getColumnsCount()+"]");
			}
		}

		return this;
	}

	@Override
	public Vector multiply(Vector v) {
		if (v != null) {
			return multiply(v, new SimpleVector(getRowsCount()));
		}
		return null;
	}

	@Override
	public Vector multiply(Vector v, Vector result) {
		if (v != null) {
			if (result != null) {

				if (v.getDimension() != getColumnsCount()) {
					throw new IllegalArgumentException("Invalid vector operand dimension ("+v.getDimension()+"), expected "+getColumnsCount());
				} 

				if (result.getDimension() != getRowsCount()) {
					throw new IllegalArgumentException("Invalid vector operand dimension ("+v.getDimension()+"), expected "+getRowsCount());
				} 

				double value = 0.0d;
				for(int dimension = 0; dimension < result.getDimension(); dimension++) {
					value = 0.0d;
					for(int commonIndex = 0; commonIndex < getColumnsCount(); commonIndex++) {
						value = value + getValue(dimension, commonIndex) * v.getValue(commonIndex);
					}

					result.setValue(dimension, value);

				}

				return result;
			}
		}
		return null;
	}

	@Override
	public Matrix multiply(double scalar) {
		Matrix s = new SimpleMatrix(this.getDataArray2D());

		for(int row = 0; row < getRowsCount(); row++) {
			for(int col = 0; col < getColumnsCount(); col++) {
				s.setValue(row, col, getValue(row, col)*scalar);
			}
		}

		return s;
	}

	@Override
	public Matrix multiply(double scalar, Matrix result) throws IllegalArgumentException {
		if (result != null) {

			if ((result.getRowsCount() == getRowsCount() && (result.getColumnsCount() == getColumnsCount()))) {
				for(int row = 0; row < getRowsCount(); row++) {
					for(int col = 0; col < getColumnsCount(); col++) {
						result.setValue(row, col, getValue(row, col)*scalar);
					}
				}
			} else {
				throw new IllegalArgumentException("Invalid output size ["+result.getRowsCount()+"x"+result.getColumnsCount()+"]. Expected ["+getRowsCount()+"x"+getColumnsCount()+"]");
			}

			return result;
		}
		return null;
	}

	@Override
	public Matrix multiplyAffect(double scalar) {
		for(int row = 0; row < getRowsCount(); row++) {
			for(int col = 0; col < getColumnsCount(); col++) {
				setValue(row, col, getValue(row, col)*scalar);
			}
		}
		return this;
	}

	@Override
	public Matrix add(Matrix b) throws IllegalArgumentException {
		return add(b, JeometryFactory.createMatrix(getRowsCount(), getColumnsCount()));
	}

	@Override
	public Matrix add(Matrix b, Matrix result) throws IllegalArgumentException {
		if (b != null) {

			if ((b.getRowsCount() == getRowsCount()) && (b.getColumnsCount() == getColumnsCount())) {

				if (result != null){

					if ((result.getRowsCount() == getRowsCount()) && (result.getColumnsCount() == getColumnsCount())) {
						for(int row = 0; row < getRowsCount(); row++) {
							for(int col = 0; col < getColumnsCount(); col++) {
								result.setValue(row, col, getValue(row, col)+ b.getValue(row, col));
							}
						}
					} else {
						throw new IllegalArgumentException("Invalid result matrix size ["+result.getRowsCount()+"x"+result.getColumnsCount()+"]. Expected ["+getRowsCount()+"x"+getColumnsCount()+"]");
					}

					return result;
				} else {
					throw new IllegalArgumentException("Invalid null output matrix.");
				}

			} else {
				throw new IllegalArgumentException("Invalid matrix size ["+b.getRowsCount()+"x"+b.getColumnsCount()+"]. Expected ["+getRowsCount()+"x"+getColumnsCount()+"]");
			}

		}
		return null;
	}

	@Override
	public Matrix addAffect(Matrix b) throws IllegalArgumentException {
		return add(b, this);
	}

	@Override
	public Matrix add(double s) {
		return add(s, JeometryFactory.createMatrix(getRowsCount(), getColumnsCount()));
	}

	@Override
	public Matrix add(double s, Matrix result) throws IllegalArgumentException {

		if (result != null){

			if ((result.getRowsCount() == getRowsCount()) && (result.getColumnsCount() == getColumnsCount())) {
				for(int row = 0; row < getRowsCount(); row++) {
					for(int col = 0; col < getColumnsCount(); col++) {
						result.setValue(row, col, getValue(row, col)+ s);
					}
				}
			} else {
				throw new IllegalArgumentException("Invalid result matrix size ["+result.getRowsCount()+"x"+result.getColumnsCount()+"]. Expected ["+getRowsCount()+"x"+getColumnsCount()+"]");
			}

			return result;
		} else {
			throw new IllegalArgumentException("Invalid null output matrix.");
		}
	}

	@Override
	public Matrix addAffect(double s) {
		return add(s, this);
	}

	@Override
	public Matrix subtract(Matrix b) throws IllegalArgumentException {
		return subtract(b, JeometryFactory.createMatrix(getRowsCount(), getColumnsCount()));
	}

	@Override
	public Matrix subtract(Matrix b, Matrix result) throws IllegalArgumentException {
		if (b != null) {

			if ((b.getRowsCount() == getRowsCount()) && (b.getColumnsCount() == getColumnsCount())) {

				if (result != null){

					if ((result.getRowsCount() == getRowsCount()) && (result.getColumnsCount() == getColumnsCount())) {
						for(int row = 0; row < getRowsCount(); row++) {
							for(int col = 0; col < getColumnsCount(); col++) {
								result.setValue(row, col, getValue(row, col) - b.getValue(row, col));
							}
						}
					} else {
						throw new IllegalArgumentException("Invalid result matrix size ["+result.getRowsCount()+"x"+result.getColumnsCount()+"]. Expected ["+getRowsCount()+"x"+getColumnsCount()+"]");
					}

					return result;
				} else {
					throw new IllegalArgumentException("Invalid null output matrix.");
				}

			} else {
				throw new IllegalArgumentException("Invalid matrix size ["+b.getRowsCount()+"x"+b.getColumnsCount()+"]. Expected ["+getRowsCount()+"x"+getColumnsCount()+"]");
			}

		}
		return null;
	}

	@Override
	public Matrix subtractAffect(Matrix b) throws IllegalArgumentException {
		return subtract(b, this);
	}

	@Override
	public Matrix subtract(double s) {
		return subtract(s, JeometryFactory.createMatrix(getRowsCount(), getColumnsCount()));
	}

	@Override
	public Matrix subtract(double s, Matrix result) throws IllegalArgumentException {
		if (result != null){

			if ((result.getRowsCount() == getRowsCount()) && (result.getColumnsCount() == getColumnsCount())) {
				for(int row = 0; row < getRowsCount(); row++) {
					for(int col = 0; col < getColumnsCount(); col++) {
						result.setValue(row, col, getValue(row, col)- s);
					}
				}
			} else {
				throw new IllegalArgumentException("Invalid result matrix size ["+result.getRowsCount()+"x"+result.getColumnsCount()+"]. Expected ["+getRowsCount()+"x"+getColumnsCount()+"]");
			}

			return result;
		} else {
			throw new IllegalArgumentException("Invalid null output matrix.");
		}
	}

	@Override
	public Matrix subtractAffect(double s) {
		return subtract(s, this);
	}

	@Override
	public Matrix invert() throws IllegalStateException {
		return invert(new SimpleMatrix(getRowsCount(), getColumnsCount()));
	}

	@Override
	public Matrix invert(Matrix result) throws IllegalStateException, IllegalArgumentException{

		if (result != null) {

			if ((result.getRowsCount() == getRowsCount()) && (result.getColumnsCount() == getColumnsCount())) {

				double det = determinant();

				if (det != 0) {

					// Compute the cofactor matrix
					cofactor(result);

					// Transpose the cofactor matrix
					result.transposeAffect();

					// Divide the transposed cofactor matrix by the inverse of the determinant
					result.multiplyAffect(1.0d/det);

				} else {
					throw new IllegalStateException("MAtrix is not invertible (determinant is 0)");
				}

			} else {
				throw new IllegalArgumentException("Invalid result matrix size ["+result.getRowsCount()+"x"+result.getColumnsCount()+"]. Expected ["+getRowsCount()+"x"+getColumnsCount()+"]");
			}

		}

		return result;
	}

	@Override
	public Matrix cofactor() throws IllegalStateException{
		return cofactor(new SimpleMatrix(getRowsCount(), getColumnsCount()));
	}

	@Override
	public Matrix cofactor(Matrix result) throws IllegalStateException, IllegalArgumentException{

		if (result != null) {

			if ((result.getRowsCount() == getRowsCount()) && (result.getColumnsCount() == getColumnsCount())) {
				for (int row = 0; row < result.getRowsCount(); row++) {
					for (int col = 0; col < result.getColumnsCount(); col++) {
						result.setValue(row, col, Math.pow(-1, row + col) * removeRowCol(row, col).determinant());
					}
				}

			} else {
				throw new IllegalArgumentException("Invalid result matrix size ["+result.getRowsCount()+"x"+result.getColumnsCount()+"]. Expected ["+getRowsCount()+"x"+getColumnsCount()+"]");
			}
		}

		return result;
	}

	@Override
	public Matrix concatHorizontal(Matrix right) {
		return concatHorizontal(right, JeometryFactory.createMatrix(getRowsCount(), getColumnsCount()+right.getColumnsCount()));
	}

	@Override
	public Matrix concatHorizontal(Matrix right, Matrix result) {
		if (getRowsCount() == right.getRowsCount()) {
			if (getRowsCount() == result.getRowsCount()) {
				for (int row = 0; row < getRowsCount(); row++) {
					for (int col = 0; col < getColumnsCount(); col++) {
						result.setValue(row, col, getValue(row, col));
					}
				}

				for (int row = 0; row < right.getRowsCount(); row++) {
					for (int col = 0; col < right.getColumnsCount(); col++) {
						result.setValue(row, col+getColumnsCount(), right.getValue(row, col));
					}
				}

			} else {
				throw new IllegalArgumentException("Incompatible result matrix size, expected "+getRowsCount()+"x"+getColumnsCount()+right.getColumnsCount()+" and got "+result.getRowsCount()+"x"+result.getColumnsCount());
			}
		} else {
			throw new IllegalArgumentException("Incompatible right matrix size, expected "+getRowsCount()+" x n and got "+right.getRowsCount()+" x "+right.getColumnsCount());
		}
		return result;
	}

	@Override
	public Matrix concatHorizontal(Vector right) {
		return concatHorizontal(right, JeometryFactory.createMatrix(getRowsCount(), getColumnsCount()+right.getDimension()));
	}

	@Override
	public Matrix concatHorizontal(Vector right, Matrix result) {
		if (getRowsCount() == right.getDimension()) {
			if (getRowsCount() == result.getRowsCount()) {
				for (int row = 0; row < getRowsCount(); row++) {
					for (int col = 0; col < getColumnsCount(); col++) {
						result.setValue(row, col, getValue(row, col));
					}
				}

				for (int row = 0; row < right.getDimension(); row++) {
					result.setValue(row, getColumnsCount(), right.getValue(row));
				}

			} else {
				throw new IllegalArgumentException("Incompatible result matrix size, expected "+getRowsCount()+"x"+getColumnsCount()+right.getDimension()+" and got "+result.getRowsCount()+"x"+result.getColumnsCount());
			}
		} else {
			throw new IllegalArgumentException("Incompatible right vector dimension, expected "+getRowsCount()+" and got "+right.getDimension());
		}
		return result;
	}
	
	@Override
	public Matrix concatVertical(Matrix bottom) {
		return concatVertical(bottom, JeometryFactory.createMatrix(getRowsCount()+bottom.getRowsCount(), getColumnsCount()));
	}

	@Override
	public Matrix concatVertical(Matrix bottom, Matrix result) {
		if (getColumnsCount() == bottom.getColumnsCount()) {
			if (getColumnsCount() == result.getColumnsCount()) {
				for (int row = 0; row < getRowsCount(); row++) {
					for (int col = 0; col < getColumnsCount(); col++) {
						result.setValue(row, col, getValue(row, col));
					}
				}

				for (int row = 0; row < bottom.getRowsCount(); row++) {
					for (int col = 0; col < bottom.getColumnsCount(); col++) {
						result.setValue(row+getRowsCount(), col, bottom.getValue(row, col));
					}
				}

			} else {
				throw new IllegalArgumentException("Incompatible result matrix size, expected "+(getRowsCount()+bottom.getRowsCount())+"x"+getColumnsCount()+" and got "+result.getRowsCount()+"x"+result.getColumnsCount());
			}
		} else {
			throw new IllegalArgumentException("Incompatible bottom matrix size, expected n x "+getColumnsCount()+" and got "+bottom.getColumnsCount()+" x "+bottom.getColumnsCount());
		}
		return result;
	}

	@Override
	public Matrix concatVertical(Vector bottom) {
		return concatVertical(bottom, JeometryFactory.createMatrix(getRowsCount()+bottom.getDimension(), getColumnsCount()));
	}

	@Override
	public Matrix concatVertical(Vector bottom, Matrix result) {
		if (getColumnsCount() == bottom.getDimension()) {
			if (getColumnsCount() == result.getColumnsCount()) {
				for (int row = 0; row < getRowsCount(); row++) {
					for (int col = 0; col < getColumnsCount(); col++) {
						result.setValue(row, col, getValue(row, col));
					}
				}

				for (int col = 0; col < getColumnsCount(); col++) {
					result.setValue(getRowsCount(), col, bottom.getValue(col));
				}
			} else {
				throw new IllegalArgumentException("Incompatible result matrix size, expected "+(getRowsCount()+bottom.getDimension())+"x"+getColumnsCount()+" and got "+result.getRowsCount()+"x"+result.getColumnsCount());
			}
		} else {
			throw new IllegalArgumentException("Incompatible bottom vector dimension, expected "+getColumnsCount()+" and got "+bottom.getDimension());
		}
		return result;
	}
	
	/**
	 * Create a new simple matrix with the given size.
	 * @param rows the number of rows.
	 * @param cols  the number of cols.
	 */
	public SimpleMatrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		data = new double[rows][cols];
	}

	/**
	 * Create a new simple matrix from the given data. 
	 * The data are copied from the input.
	 * @param data the data to use.
	 */
	public SimpleMatrix(double[][] data) {
		if ((data != null) && (data.length > 0)){
			rows = data.length;
			cols = data[0].length;

			this.data = new double[rows][cols];

			for(int row = 0; row < rows; row++) {
				for(int col = 0; col < cols; col++) {
					this.data[row][col] = data[row][col];
				}
			}

		}
	}

	/**
	 * Create a new simple matrix from the given parameters.
	 * @param rows the number of rows.
	 * @param cols the number of columns.
	 * @param data the matrix values as a single dimensional array.
	 * @param ordering the ordering of the data within the arra (can be either {@link Matrix#ROW_MAJOR} or {@link Matrix#COLUMN_MAJOR})
	 */
	public SimpleMatrix(int rows, int cols, double[] data, int ordering) {
		if ((rows > 0) && (cols > 0) && (data != null) && (data.length > 0)){
			this.rows = rows;
			this.cols = cols;

			this.data = new double[rows][cols];

			setDataArray(ordering, data);

		}
	}

	/**
	 * Create a new simple matrix by copying the given one.
	 * @param matrix the {@link Matrix matrix} to copy
	 */
	public SimpleMatrix(Matrix matrix) {
		if ((matrix != null) && (matrix.getRowsCount() > 0) && (matrix.getColumnsCount() > 0)){
			rows = matrix.getRowsCount();
			cols = matrix.getColumnsCount();

			this.data = new double[rows][cols];

			for(int row = 0; row < rows; row++) {
				for(int col = 0; col < cols; col++) {
					this.data[row][col] = matrix.getValue(row, col);
				}
			}

		}
	}

	/**
	 * Remove the given row and column from the current matrix. 
	 * @param row the row to remove
	 * @param col the column to remove
	 * @return a matrix that is a copy of the current one without the removec row / column.
	 */
	private Matrix removeRowCol(int row, int col) {
		Matrix result = new SimpleMatrix(this.rows - 1, this.cols - 1);

		int k = 0, l = 0;
		for (int i = 0; i < this.rows; i++) {
			if (i == row) continue;
			for (int j = 0; j < this.cols; j++) {
				if (j == col) continue;
				result.setValue(l, k, this.getValue(i, j));

				k = (k + 1) % (this.rows - 1);
				if (k == 0) l++;
			}
		}

		return result;
	}

}
