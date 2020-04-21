package org.jeometry.math;

import java.awt.Dimension;

import org.jeometry.Jeometry;

/**
 * This interface describe a general two dimensional matrix. A matrix <i>A</i> is a two-dimensional array of double values that is defined such as: 
 * $$
 * A = \begin{bmatrix} 
 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
 *     \end{bmatrix}
 * $$
 * where:
 * <ul>
 * <li><i>n</i> is the number of rows.
 * <li><i>m</i> is the number of columns.
 * <li><i>a<sub>ij</sub></i> is the value located at row <i>i</i> and column <i>j</i>
 * </ul>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface Matrix {

	/**
	 * Indicates that the export of a matrix data to a linear array is row-major (made row by row from the first to the last).
	 * @see #COLUMN_MAJOR
	 */
	public static final int ROW_MAJOR    = 1;

	/**
	 * Indicates that the the export of a matrix data to a linear array is column-major (made column by column from the first to the last).
	 * @see #ROW_MAJOR
	 */
	public static final int COLUMN_MAJOR = 2;

	/**
	 * Get the matrix values as a linear array according to the given <code>ordering</code>. 
	 * If the <code>ordering</code> is {@link #ROW_MAJOR}, the output array contains the sequence of all rows from the first to the last. 
	 * On the other hand, if the <code>ordering</code> is {@link #COLUMN_MAJOR}, the output array contains the sequence of all columns from the first to the last. 
	 * @param ordering the ordering to use ({@link #ROW_MAJOR} or {@link #COLUMN_MAJOR}).
	 * @return the matrix values as a linear array.
	 * @see #setDataArray(int, double[])
	 */
	public double[] getDataArray(int ordering);

	/**
	 * Get the matrix values as a linear array according to the given <code>ordering</code>. 
	 * If the <code>ordering</code> is {@link #ROW_MAJOR}, the output array contains the sequence of all rows from the first to the last. 
	 * On the other hand, if the <code>ordering</code> is {@link #COLUMN_MAJOR}, the output array contains the sequence of all columns from the first to the last. 
	 * @param ordering the ordering to use ({@link #ROW_MAJOR} or {@link #COLUMN_MAJOR}).
	 * @param output the array where the value are stored.
	 * @return the matrix values as a linear array.
	 * @see #setDataArray(int, double[])
	 * @throws IllegalArgumentException if the output array does not fit the matrix capabilities.
	 */
	public double[] getDataArray(int ordering, double[] output) throws IllegalArgumentException;

	/**
	 * Set the matrix values from a linear array according to the given <code>ordering</code>. 
	 * If the <code>ordering</code> is {@link #ROW_MAJOR}, the output array contains the sequence of all rows from the first to the last. 
	 * On the other hand, if the <code>ordering</code> is {@link #COLUMN_MAJOR}, the output array contains the sequence of all columns from the first to the last. 
	 * @param ordering the ordering of the linear array ({@link #ROW_MAJOR} or {@link #COLUMN_MAJOR}).
	 * @param data the matrix values.
	 * @throws IllegalArgumentException if the given data does not fit the matrix capabilities.
	 */
	public void setDataArray(int ordering, double[] data) throws IllegalArgumentException;

	/**
	 * Get the matrix values as a two-dimensionnal array.
	 * The output array has the same number of rows and columns as the matrix.
	 * @return the matrix values as a two-dimensionnal array.
	 * @see #setDataArray2D(double[][])
	 */
	public double[][] getDataArray2D();

	/**
	 * Get the matrix values as a two-dimensionnal array.
	 * The output array has the same number of rows and columns as the matrix.
	 * @param output the two-dimensional array where the value are stored.
	 * @return the matrix values as a two-dimensionnal array.
	 * @see #setDataArray2D(double[][])
	 */
	public double[][] getDataArray2D(double[][] output);

	/**
	 * Set the matrix values from a two-dimensionnal array. 
	 * The input array has to have the same number of rows and columns as the matrix.
	 * @param data the matrix values
	 * @throws IllegalArgumentException if the given data does not fir the matrix capabilities.
	 */
	public void setDataArray2D(double[][] data) throws IllegalArgumentException;

	/**
	 * Get the value that is stored at the given (<code>row</code>, <code>column</code>).
	 * @param row the row that holds the value.
	 * @param col the column that holds the value.
	 * @return the value that is stored at the given (<code>row</code>, <code>column</code>).
	 * @throws IllegalArgumentException if the <code>row</code> or <code>col</code> does not fit the matrix size.
	 * @see #setValue(int, int, double)
	 */
	public double getValue(int row, int col) throws IllegalArgumentException;

	/**
	 * Set the value that is stored at the given (<code>row</code>, <code>column</code>).
	 * @param row the row that holds the value.
	 * @param col the column that holds the value.
	 * @param value value to store at the given (<code>row</code>, <code>column</code>).
	 * @throws IllegalArgumentException if the <code>row</code> or <code>col</code> does not fit the matrix size.
	 * @see #getValue(int, int)
	 */
	public void setValue(int row, int col, double value) throws IllegalArgumentException;

	/**
	 * Copy the given matrix values within this one. After a call to this method, all cells [<i>row</i>, <i>column</i>] of this matrix
	 * have the same values as the cells [<i>row</i>, <i>column</i>] from the given matrix.
	 * @param matrix the matrix from which the values are copied
	 * @throws IllegalArgumentException if the given matrix size is not compatible with this one
	 */
	public void setValues(Matrix matrix);

	/**
	 * Set all the matrix cells with the given value.
	 * @param value the value to set to all the matrix cells
	 */
	public void setTo(double value);

	/**
	 * Extract a sub-matrix from this one according to the given offsets and counts. 
	 * The extracted matrix is a copy of the original.
	 * @param rowOffset the index of first row to extract 
	 * @param columnOffset the index of first column to extract 
	 * @param rowCount the number of rows to extract from the given offset
	 * @param columnCount the number of columns to extract from the given offset
	 * @return a sub-matrix from this one according to the given offsets and counts
	 */
	public Matrix extract(int rowOffset, int columnOffset, int rowCount, int columnCount);

	/**
	 * Extract a sub-matrix from this one according to the given offsets and counts. 
	 * The extracted matrix is a copy of the original.
	 * @param rowOffset the index of first row to extract 
	 * @param columnOffset columnOffset the index of first column to extract 
	 * @param rowCount the number of rows to extract from the given offset
	 * @param columnCount the number of columns to extract from the given offset
	 * @param result the matrix that store the extraction
	 * @return a reference on <code>result</code>
	 * @throws IllegalArgumentException if the <code>result</code> does not fit
	 */
	public Matrix extract(int rowOffset, int columnOffset, int rowCount, int columnCount, Matrix result);

	/**
	 * Get the number of rows that this matrix contains.
	 * @return the number of rows that this matrix contains.
	 * @see #getColumnsCount()
	 * @see #getDimension()
	 */
	public int getRowsCount();

	/**
	 * Get the number of columns that this matrix contains.
	 * @return the number of columns that this matrix contains.
	 * @see #getRowsCount()
	 * @see #getDimension()
	 */
	public int getColumnsCount();

	/**
	 * Get the dimension (the number of rows and columns) of this matrix.
	 * @return the dimension (the number of rows and columns) of this matrix.
	 * @see #getRowsCount()
	 * @see #getColumnsCount()
	 */
	public Dimension getDimension();

	/**
	 * Compute the matrix <a href="http://mathworld.wolfram.com/Determinant.html">determinant</a>. Let <i>A</i> be a general <i>n</i>&times;<i>m</i> sized matrix such that:
	 * $$
	 * A = \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * where:
	 * <ul>
	 * <li><i>n</i> is the number of rows.
	 * <li><i>m</i> is the number of columns.
	 * <li><i>a<sub>ij</sub></i> is the value located at row <i>i</i> and column <i>j</i>
	 * </ul>
	 * Its determinant, denoted |<i>A</i>| is such that:
	 * $$
	 * |A|\ =\ \sum_{k=1}^{k}a_{ij}C_{ij}
	 * $$
	 * where <i>C<sub>ij</sub></i> is the cofactor of <i>a<sub>ij</sub></i> defined by:
	 * $$
	 * C_{ij}\ =\ (-1)^{i+j}M_{ij}
	 * $$
	 * with <i>M<sub>ij</sub></i> the minor of matrix <i>A</i> formed by eliminating row i and column j from A.<br><br>
	 * Many implementation of the determinant can be provided, this description is purely formal.
	 * @return the matrix determinant.
	 */
	public double determinant();

	/**
	 * Compute the transpose of the current matrix and return it. 
	 * Let <i>A</i> be a matrix, the transpose of <i>A</i>, denoted <i>A</i><sup>T</sup> 
	 * is the matrix that is obtained by exchanging <i>A</i> rows and columns. A matrix and its transpose satisfy:<br><br>
	 *  (<i>A</i><sup>T</sup>)<sup>-1</sup>&nbsp;=&nbsp;(<i>A</i><sup>-1</sup>)<sup>T</sup>
	 *  <br><br>
	 *  Note that the transpose of an <i>A</i>, <i>B</i> matrices product is such as:
	 *  (<i>A</i>&times;<i>B</i>)<sup>T</sup>&nbsp;=&nbsp;<i>A</i><sup>T</sup>&times;<i>B</i><sup>T</sup>
	 * @return the transpose of the current matrix.
	 */
	public Matrix transpose();

	/**
	 * Compute the transpose of the current matrix and store it within the given result. 
	 * Let <i>A</i> be a matrix, the transpose of <i>A</i>, denoted <i>A</i><sup>T</sup> 
	 * is the matrix that is obtained by exchanging <i>A</i> rows and columns. A matrix and its transpose satisfy:<br><br>
	 * (<i>A</i><sup>T</sup>)<sup>-1</sup>&nbsp;=&nbsp;(<i>A</i><sup>-1</sup>)<sup>T</sup>
	 * <br><br>
	 * Note that the transpose of an <i>A</i>, <i>B</i> matrices product is such as:
	 * (<i>A</i>&times;<i>B</i>)<sup>T</sup>&nbsp;=&nbsp;<i>A</i><sup>T</sup>&times;<i>B</i><sup>T</sup>
	 * @param result the matrix that has to store the result of the transpose.
	 * @return the same reference as <code>result</code>
	 * @throws IllegalArgumentException is the given result does not fit the transpose matrix.
	 */
	public Matrix transpose(Matrix result) throws IllegalArgumentException;

	/**
	 * Modify this matrix by its transpose.
	 * Let <i>A</i> be a matrix, the transpose of <i>A</i>, denoted <i>A</i><sup>T</sup> 
	 * is the matrix that is obtained by exchanging <i>A</i> rows and columns. A matrix and its transpose satisfy:<br><br>
	 * (<i>A</i><sup>T</sup>)<sup>-1</sup>&nbsp;=&nbsp;(<i>A</i><sup>-1</sup>)<sup>T</sup>
	 * <br><br>
	 * Note that the transpose of an <i>A</i>, <i>B</i> matrices product is such as:
	 * (<i>A</i>&times;<i>B</i>)<sup>T</sup>&nbsp;=&nbsp;<i>A</i><sup>T</sup>&times;<i>B</i><sup>T</sup>
	 * @return a reference on this matrix.
	 */
	public Matrix transposeAffect();

	/**
	 * Multiply the current matrix by the given one (regarding to <a href="http://mathworld.wolfram.com/MatrixMultiplication.html">matrix product</a>). Formally, this methods compute:<br><br>
	 * <i>M</i>&nbsp;=&nbsp;<i>A</i>&times;<i>B</i><br><br>
	 * where:
	 * <ul>
	 * <li><i>M</i> is the returned result.
	 * <li><i>A</i> is this matrix.
	 * <li><i>B</i> is the matrix given by <code>b</code>.
	 * </ul>
	 * @param b the matrix to multiply.
	 * @return the product of this matrix by the given one.
	 * @throws IllegalArgumentException if the matrix are incompatible for multiplication.
	 */
	public Matrix multiply(Matrix b) throws IllegalArgumentException;

	/**
	 * Multiply the current matrix by the given one (regarding to <a href="http://mathworld.wolfram.com/MatrixMultiplication.html">matrix product</a>). Formally, this methods compute:<br><br>
	 * <i>M</i>&nbsp;=&nbsp;<i>A</i>&times;<i>B</i><br><br>
	 * where:
	 * <ul>
	 * <li><i>M</i> is the returned result.
	 * <li><i>A</i> is this matrix.
	 * <li><i>B</i> is the matrix given by <code>b</code>.
	 * </ul>
	 * @param b the matrix to multiply.
	 * @param result the matrix that has to store the result of the multiplication.
	 * @return the same reference as <code>result</code>.
	 * @throws IllegalArgumentException if the matrix are incompatible for multiplication.
	 */
	public Matrix multiply(Matrix b, Matrix result) throws IllegalArgumentException;

	/**
	 * Modify this matrix to be the result of the multiplication of this matrix with the given one (regarding to <a href="http://mathworld.wolfram.com/MatrixMultiplication.html">matrix product</a>). 
	 * Formally, this methods compute:<br><br>
	 * <i>M</i>&nbsp;=&nbsp;<i>A</i>&times;<i>B</i><br><br>
	 * where:
	 * <ul>
	 * <li><i>M</i> is the returned result.
	 * <li><i>A</i> is this matrix.
	 * <li><i>B</i> is the matrix given by <code>b</code>.
	 * </ul>
	 * @param b the matrix to multiply.
	 * @return a reference on this matrix.
	 * @throws IllegalArgumentException if the matrix are incompatible for multiplication.
	 */
	public Matrix multiplyAffect(Matrix b) throws IllegalArgumentException;

	/**
	 * Return the {@link Vector vector} resulting of the multiplication of this matrix with the given one.
	 * (regarding to <a href="http://mathworld.wolfram.com/MatrixMultiplication.html">matrix product</a>).<br><br>
	 * Formally, this methods compute:<br><br>
	 * <i>u</i>&nbsp;=&nbsp;<i>A</i>&times;<i>v</i><br><br>
	 * where:
	 * <ul>
	 * <li><i>u</i> is the returned result.
	 * <li><i>A</i> is this matrix.
	 * <li><i>v</i> is the input vector.
	 * </ul>
	 * @param v the vector to multiply.
	 * @return the {@link Vector vector} resulting of the multiplication of this matrix with the given one.
	 */
	public Vector multiply(Vector v);

	/**
	 * Affect the <code>result</code> {@link Vector vector} with the result of the multiplication of this matrix with the given vector <code>v</code>.
	 * (regarding to <a href="http://mathworld.wolfram.com/MatrixMultiplication.html">matrix product</a>)<br><br>
	 * Formally, this methods compute:<br><br>
	 * <i>u</i>&nbsp;=&nbsp;<i>A</i>&times;<i>v</i><br><br>
	 * where:
	 * <ul>
	 * <li><i>u</i> is the returned result.
	 * <li><i>A</i> is this matrix.
	 * <li><i>v</i> is the input vector.
	 * </ul>
	 * @param v the vector to multiply.
	 * @param result the vector that store the result.
	 * @return the same reference as <code>result</code>.
	 */
	public Vector multiply(Vector v, Vector result);

	/**
	 * Return the matrix resulting of the multiplication of this matrix with a scalar. 
	 * Formally, let <i>A</i> a matrix such that:
	 * $$
	 * A = \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The result of the multiplcation of <i>A</i> by a scalar <i>s</i> is the matrix <i>A<sub>s</sub></i> that verifies:
	 * $$
	 * A_{s} = \begin{bmatrix} 
	 *        sa_{00} &amp; \dots  &amp; sa_{0i} &amp; \dots  &amp; sa_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        sa_{i0} &amp; \dots  &amp; sa_{ij} &amp; \dots  &amp; sa_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        sa_{n0} &amp; \dots  &amp; sa_{nj} &amp; \dots  &amp; sa_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param scalar the scalar to multiply.
	 * @return the matrix resulting of the multiplication of this matrix with a scalar. 
	 */
	public Matrix multiply(double scalar);

	/**
	 * Store the result of the multiplication of this matrix with a scalar within the given <code>result</code>. 
	 * Formally, let <i>M</i> a matrix such that:
	 * $$
	 * A = \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The result of the multiplcation of <i>A</i> by a scalar <i>s</i> is the matrix <i>A<sub>s</sub></i> that verifies:
	 * $$
	 * A_{s} = \begin{bmatrix} 
	 *        sa_{00} &amp; \dots  &amp; sa_{0i} &amp; \dots  &amp; sa_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        sa_{i0} &amp; \dots  &amp; sa_{ij} &amp; \dots  &amp; sa_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        sa_{n0} &amp; \dots  &amp; sa_{nj} &amp; \dots  &amp; sa_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param scalar the scalar to multiply.
	 * @param result the matrix that have to store the result of the multiplication of this matrix with the scalar.
	 * @return the same reference as <code>result</code>
	 * @throws IllegalArgumentException if the result matrix does not fit the multiplication.
	 */
	public Matrix multiply(double scalar, Matrix result) throws IllegalArgumentException;

	/**
	 * Modify this matrix with the result of the multiplication of this matrix with a scalar. 
	 * Formally, let <i>A</i> a matrix such that:
	 * $$
	 * A = \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The result of the multiplcation of <i>A</i> by a scalar <i>s</i> is the matrix <i>A<sub>s</sub></i> that verifies:
	 * $$
	 * A_{s} = \begin{bmatrix} 
	 *        sa_{00} &amp; \dots  &amp; sa_{0i} &amp; \dots  &amp; sa_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        sa_{i0} &amp; \dots  &amp; sa_{ij} &amp; \dots  &amp; sa_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        sa_{n0} &amp; \dots  &amp; sa_{nj} &amp; \dots  &amp; sa_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param scalar the scalar to multiply.
	 * @return a reference on this matrix.
	 */
	public Matrix multiplyAffect(double scalar);

	/**
	 * Compute and return the addition of this matrix with the given one.
	 * Formally, let <i>A</i> and <i>B</i> two matrices such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * and\ B\ =\ \begin{bmatrix} 
	 *        b_{00} &amp; \dots  &amp; b_{0i} &amp; \dots  &amp; b_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0} &amp; \dots  &amp; b_{ij} &amp; \dots  &amp; b_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0} &amp; \dots  &amp; b_{nj} &amp; \dots  &amp; b_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The addition of <i>A</i> and <i>B</i>, denoted <i>A</i>&nbsp;+&nbsp;<i>B</i> is such that:
	 * $$
	 * A+B\ =\ \begin{bmatrix} 
	 *        b_{00}+a_{00} &amp; \dots  &amp; b_{0i}+a_{0i} &amp; \dots  &amp; b_{0m}+a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0}+a_{i0} &amp; \dots  &amp; b_{ij}+a_{ij} &amp; \dots  &amp; b_{im}+a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0}+a_{n0} &amp; \dots  &amp; b_{nj}+a_{nj} &amp; \dots  &amp; b_{nm}+a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param b the matrix to add. This matrix has to be same sized as this matrix.
	 * @return the addition of this matrix with the given one. If the input matrix is <code>null</code> then <code>null</code> is returned.
	 * @throws IllegalArgumentException if the input matrix size differs from this one.
	 */
	public Matrix add(Matrix b) throws IllegalArgumentException;

	/**
	 * Compute the addition of this matrix with the given one and store it within the <code>result</code> matrix.
	 * Formally, let <i>A</i> and <i>B</i> two matrices such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * and\ B\ =\ \begin{bmatrix} 
	 *        b_{00} &amp; \dots  &amp; b_{0i} &amp; \dots  &amp; b_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0} &amp; \dots  &amp; b_{ij} &amp; \dots  &amp; b_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0} &amp; \dots  &amp; b_{nj} &amp; \dots  &amp; b_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The addition of <i>A</i> and <i>B</i>, denoted <i>A</i>&nbsp;+&nbsp;<i>B</i> is such that:
	 * $$
	 * A+B\ =\ \begin{bmatrix} 
	 *        b_{00}+a_{00} &amp; \dots  &amp; b_{0i}+a_{0i} &amp; \dots  &amp; b_{0m}+a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0}+a_{i0} &amp; \dots  &amp; b_{ij}+a_{ij} &amp; \dots  &amp; b_{im}+a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0}+a_{n0} &amp; \dots  &amp; b_{nj}+a_{nj} &amp; \dots  &amp; b_{nm}+a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param b the matrix to add. This matrix has to be same sized as this matrix.
	 * @param result the matrix that store the result. This matrix has to be same sized as this matrix.
	 * @return the same reference as <code>result</code>.
	 * @throws IllegalArgumentException if the input or result matrices size differs from this one.
	 */
	public Matrix add(Matrix b, Matrix result) throws IllegalArgumentException;

	/**
	 * Affect this matrix with the result of its addition with the given one.
	 * Formally, let <i>A</i> and <i>B</i> two matrices such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * and\ B\ =\ \begin{bmatrix} 
	 *        b_{00} &amp; \dots  &amp; b_{0i} &amp; \dots  &amp; b_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0} &amp; \dots  &amp; b_{ij} &amp; \dots  &amp; b_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0} &amp; \dots  &amp; b_{nj} &amp; \dots  &amp; b_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The addition of <i>A</i> and <i>B</i>, denoted <i>A</i>&nbsp;+&nbsp;<i>B</i> is such that:
	 * $$
	 * A+B\ =\ \begin{bmatrix} 
	 *        b_{00}+a_{00} &amp; \dots  &amp; b_{0i}+a_{0i} &amp; \dots  &amp; b_{0m}+a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0}+a_{i0} &amp; \dots  &amp; b_{ij}+a_{ij} &amp; \dots  &amp; b_{im}+a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0}+a_{n0} &amp; \dots  &amp; b_{nj}+a_{nj} &amp; \dots  &amp; b_{nm}+a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param b the matrix to add. This matrix has to be same sized as this matrix.
	 * @return a reference on this matrix.
	 * @throws IllegalArgumentException if the input matrix size differs from this one.
	 */
	public Matrix addAffect(Matrix b) throws IllegalArgumentException;

	/**
	 * Compute and return the addition of this matrix with the given scalar.
	 * Formally, let <i>s</i> be a scalar and <i>A</i> a matrix such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The addition of <i>A</i> and <i>s</i>, denoted <i>A<sub>s</sub></i> is such that:
	 * $$
	 * A_{s}\ =\ \begin{bmatrix} 
	 *        s+a_{00} &amp; \dots  &amp; s+a_{0i} &amp; \dots  &amp; s+a_{0m} \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\
	 *        s+a_{i0} &amp; \dots  &amp; s+a_{ij} &amp; \dots  &amp; s+a_{im} \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\     
	 *        s+a_{n0} &amp; \dots  &amp; s+a_{nj} &amp; \dots  &amp; s+a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param s the scalar to add.
	 * @return the addition of this matrix with the given scalar.
	 */
	public Matrix add(double s);

	/**
	 * Compute the addition of this matrix with the given scalar and store it within the given <code>result</code> matrix.
	 * Formally, let <i>s</i> be a scalar and <i>A</i> a matrix such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The addition of <i>A</i> and <i>s</i>, denoted <i>A<sub>s</sub></i> is such that:
	 * $$
	 * A_{s}\ =\ \begin{bmatrix} 
	 *        s+a_{00} &amp; \dots  &amp; s+a_{0i} &amp; \dots  &amp; s+a_{0m} \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\
	 *        s+a_{i0} &amp; \dots  &amp; s+a_{ij} &amp; \dots  &amp; s+a_{im} \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\     
	 *        s+a_{n0} &amp; \dots  &amp; s+a_{nj} &amp; \dots  &amp; s+a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param s the scalar to add.
	 * @param result the matrix that store the result. This matrix has to be same sized as this matrix.
	 * @return the addition of this matrix with the given scalar. If the <code>result</code> matrix is <code>null</code> then <code>null</code> is returned.
	 * @throws IllegalArgumentException if the result matrix size differs from this one.
	 */
	public Matrix add(double s, Matrix result) throws IllegalArgumentException;

	/**
	 * Affect this matrix with the result of the addition of this matrix with the given scalar.
	 * Formally, let <i>s</i> be a scalar and <i>A</i> a matrix such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The addition of <i>A</i> and <i>s</i>, denoted <i>A<sub>s</sub></i> is such that:
	 * $$
	 * A_{s}\ =\ \begin{bmatrix} 
	 *        s+a_{00} &amp; \dots  &amp; s+a_{0i} &amp; \dots  &amp; s+a_{0m} \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\
	 *        s+a_{i0} &amp; \dots  &amp; s+a_{ij} &amp; \dots  &amp; s+a_{im} \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\     
	 *        s+a_{n0} &amp; \dots  &amp; s+a_{nj} &amp; \dots  &amp; s+a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param s the scalar to add.
	 * @return a reference on this matrix.
	 */
	public Matrix addAffect(double s);

	/**
	 * Compute and return the subtraction of this matrix with the given one.
	 * Formally, let <i>A</i> and <i>B</i> two matrices such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * and\ B\ =\ \begin{bmatrix} 
	 *        b_{00} &amp; \dots  &amp; b_{0i} &amp; \dots  &amp; b_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0} &amp; \dots  &amp; b_{ij} &amp; \dots  &amp; b_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0} &amp; \dots  &amp; b_{nj} &amp; \dots  &amp; b_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The subtraction of <i>A</i> and <i>B</i>, denoted <i>A</i>&nbsp;-&nbsp;<i>B</i> is such that:
	 * $$
	 * A+B\ =\ \begin{bmatrix} 
	 *        b_{00}-a_{00} &amp; \dots  &amp; b_{0i}-a_{0i} &amp; \dots  &amp; b_{0m}-a_{0m} \\
	 *        \vdots        &amp; \ddots &amp; \vdots        &amp; \ddots &amp; \vdots        \\
	 *        b_{i0}-a_{i0} &amp; \dots  &amp; b_{ij}-a_{ij} &amp; \dots  &amp; b_{im}-a_{im} \\
	 *        \vdots        &amp; \ddots &amp; \vdots        &amp; \ddots &amp; \vdots        \\     
	 *        b_{n0}-a_{n0} &amp; \dots  &amp; b_{nj}-a_{nj} &amp; \dots  &amp; b_{nm}-a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param b the matrix to subtract. This matrix has to be same sized as this matrix.
	 * @return the subtraction of this matrix with the given one. If the input matrix is <code>null</code> then <code>null</code> is returned.
	 * @throws IllegalArgumentException if the input matrix size differs from this one.
	 */
	public Matrix subtract(Matrix b) throws IllegalArgumentException;

	/**
	 * Compute the subtraction of this matrix with the given one and store it within the <code>result</code> matrix.
	 * Formally, let <i>A</i> and <i>B</i> two matrices such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * and\ B\ =\ \begin{bmatrix} 
	 *        b_{00} &amp; \dots  &amp; b_{0i} &amp; \dots  &amp; b_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0} &amp; \dots  &amp; b_{ij} &amp; \dots  &amp; b_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0} &amp; \dots  &amp; b_{nj} &amp; \dots  &amp; b_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The subtraction of <i>A</i> and <i>B</i>, denoted <i>A</i>&nbsp;-&nbsp;<i>B</i> is such that:
	 * $$
	 * A+B\ =\ \begin{bmatrix} 
	 *        b_{00}-a_{00} &amp; \dots  &amp; b_{0i}-a_{0i} &amp; \dots  &amp; b_{0m}-a_{0m} \\
	 *        \vdots        &amp; \ddots &amp; \vdots        &amp; \ddots &amp; \vdots        \\
	 *        b_{i0}-a_{i0} &amp; \dots  &amp; b_{ij}-a_{ij} &amp; \dots  &amp; b_{im}-a_{im} \\
	 *        \vdots        &amp; \ddots &amp; \vdots        &amp; \ddots &amp; \vdots        \\     
	 *        b_{n0}-a_{n0} &amp; \dots  &amp; b_{nj}-a_{nj} &amp; \dots  &amp; b_{nm}-a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param b the matrix to subtract. This matrix has to be same sized as this matrix.
	 * @param result the matrix that store the result. This matrix has to be same sized as this matrix.
	 * @return the same reference as <code>result</code>.
	 * @throws IllegalArgumentException if the input or result matrices size differs from this one.
	 */
	public Matrix subtract(Matrix b, Matrix result) throws IllegalArgumentException;

	/**
	 * Affect this matrix with the result of its subtraction with the given one.
	 * Formally, let <i>A</i> and <i>B</i> two matrices such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * and\ B\ =\ \begin{bmatrix} 
	 *        b_{00} &amp; \dots  &amp; b_{0i} &amp; \dots  &amp; b_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0} &amp; \dots  &amp; b_{ij} &amp; \dots  &amp; b_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0} &amp; \dots  &amp; b_{nj} &amp; \dots  &amp; b_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The subtraction of <i>A</i> and <i>B</i>, denoted <i>A</i>&nbsp;-&nbsp;<i>B</i> is such that:
	 * $$
	 * A+B\ =\ \begin{bmatrix} 
	 *        b_{00}-a_{00} &amp; \dots  &amp; b_{0i}-a_{0i} &amp; \dots  &amp; b_{0m}-a_{0m} \\
	 *        \vdots        &amp; \ddots &amp; \vdots        &amp; \ddots &amp; \vdots        \\
	 *        b_{i0}-a_{i0} &amp; \dots  &amp; b_{ij}-a_{ij} &amp; \dots  &amp; b_{im}-a_{im} \\
	 *        \vdots        &amp; \ddots &amp; \vdots        &amp; \ddots &amp; \vdots        \\     
	 *        b_{n0}-a_{n0} &amp; \dots  &amp; b_{nj}-a_{nj} &amp; \dots  &amp; b_{nm}-a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * @param b the matrix to subtract. This matrix has to be same sized as this matrix.
	 * @return a reference on this matrix.
	 * @throws IllegalArgumentException if the input matrix size differs from this one.
	 */
	public Matrix subtractAffect(Matrix b) throws IllegalArgumentException;

	/**
	 * Compute and return the subtraction of this matrix with the given scalar.
	 * Formally, let <i>s</i> be a scalar and <i>A</i> a matrix such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The subtraction of <i>A</i> and <i>s</i>, denoted <i>A<sub>s</sub></i> is such that:
	 * $$
	 * A_{s}\ =\ \begin{bmatrix} 
	 *        a_{00}-s &amp; \dots  &amp; a_{0i}-s &amp; \dots  &amp; a_{0m}-s \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\
	 *        a_{i0}-s &amp; \dots  &amp; a_{ij}-s &amp; \dots  &amp; a_{im}-s \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\     
	 *        a_{n0}-s &amp; \dots  &amp; a_{nj}-s &amp; \dots  &amp; a_{nm}-s
	 *     \end{bmatrix}
	 * $$
	 * @param s the scalar to subtract.
	 * @return the subtraction of this matrix with the given scalar.
	 */
	public Matrix subtract(double s);

	/**
	 * Compute the subtraction of this matrix with the given scalar and store it within the given <code>result</code> matrix.
	 * Formally, let <i>s</i> be a scalar and <i>A</i> a matrix such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The subtraction of <i>A</i> and <i>s</i>, denoted <i>A<sub>s</sub></i> is such that:
	 * $$
	 * A_{s}\ =\ \begin{bmatrix} 
	 *        a_{00}-s &amp; \dots  &amp; a_{0i}-s &amp; \dots  &amp; a_{0m}-s \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\
	 *        a_{i0}-s &amp; \dots  &amp; a_{ij}-s &amp; \dots  &amp; a_{im}-s \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\     
	 *        a_{n0}-s &amp; \dots  &amp; a_{nj}-s &amp; \dots  &amp; a_{nm}-s
	 *     \end{bmatrix}
	 * $$
	 * @param s the scalar to subtract.
	 * @param result the matrix that store the result. This matrix has to be same sized as this matrix.
	 * @return the subtraction of this matrix with the given scalar. If the <code>result</code> matrix is <code>null</code> then <code>null</code> is returned.
	 * @throws IllegalArgumentException if the result matrix size differs from this one.
	 */
	public Matrix subtract(double s, Matrix result) throws IllegalArgumentException;

	/**
	 * Affect this matrix with the result of the subtraction of this matrix with the given scalar.
	 * Formally, let <i>s</i> be a scalar and <i>A</i> a matrix such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{im} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nm}
	 *     \end{bmatrix}
	 * $$
	 * The subtraction of <i>A</i> and <i>s</i>, denoted <i>A<sub>s</sub></i> is such that:
	 * $$
	 * A_{s}\ =\ \begin{bmatrix} 
	 *        a_{00}-s &amp; \dots  &amp; a_{0i}-s &amp; \dots  &amp; a_{0m}-s \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\
	 *        a_{i0}-s &amp; \dots  &amp; a_{ij}-s &amp; \dots  &amp; a_{im}-s \\
	 *        \vdots   &amp; \ddots &amp; \vdots   &amp; \ddots &amp; \vdots   \\     
	 *        a_{n0}-s &amp; \dots  &amp; a_{nj}-s &amp; \dots  &amp; a_{nm}-s
	 *     \end{bmatrix}
	 * $$
	 * @param s the scalar to subtract.
	 * @return a reference on this matrix.
	 */
	public Matrix subtractAffect(double s);

	/**
	 * Return the inverse of the current matrix. Let <i>A</i> be a square matrix, its inverse, denoted <i>A<sup>-1</sup></i> is such that:<br>
	 * <i>A</i>&times;<i>A<sup>-1</sup></i>&nbsp;=&nbsp;I<br>
	 * where <i>I</i> is the identity matrix and &times; is the standard matrix product.
	 * @return the inverse of the current matrix.
	 * @throws IllegalStateException if the matrix is not invertible.
	 */
	public Matrix invert() throws IllegalStateException;

	/**
	 * Compute the inverse of the current matrix and store it within the <code>result</code> matrix.
	 * Let <i>A</i> be a square matrix, its inverse, denoted <i>A<sup>-1</sup></i> is such that:<br>
	 * <i>A</i>&times;<i>A<sup>-1</sup></i>&nbsp;=&nbsp;I<br>
	 * where <i>I</i> is the identity matrix and &times; is the standard matrix product.
	 * @param result the matrix that has to store the inverse of the current matrix.
	 * @return the same reference as <code>result</code>
	 * @throws IllegalStateException if the <code>result</code> is not same sized as this matrix.
	 * @throws IllegalArgumentException if the matrix is not invertible.
	 */
	public Matrix invert(Matrix result) throws IllegalStateException, IllegalArgumentException;

	/**
	 * Compute the cofactor matrix. Formally, let <i>A</i> a square matrix defined such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0n} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{in} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nn}
	 *     \end{bmatrix}
	 * $$
	 * The cofactor matrix of <i>A</i>, denoted <i>C</i>, is such that:<br>
	 * $$
	 * C\ =\ \begin{bmatrix} 
	 *        c_{00} &amp; \dots  &amp; c_{0i} &amp; \dots  &amp; c_{0n} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        c_{i0} &amp; \dots  &amp; c_{ij} &amp; \dots  &amp; c_{in} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        c_{n0} &amp; \dots  &amp; c_{nj} &amp; \dots  &amp; c_{nn}
	 *     \end{bmatrix}
	 * $$
	 * <br>where <i>c</i><sub><i>ij</i></sub>&nbsp;=&nbsp;(<i>-1</i>)<sup>(<i>i+j</i>)</sup><i>|M</i><sub>(<i>ij</i>)</sub>|<br>
	 * with <i>M</i><sub><i>ij</i></sub> is the minor matrix from <i>A</i> obtained by ignoring row <i>i</i> and column <i>j</i> from <i>A</i>
	 * <br>
	 * @return the cofactor matrix.
	 * @throws IllegalStateException if the matrix is not square.
	 */
	public Matrix cofactor() throws IllegalStateException;

	/**
	 * Compute the cofactor matrix and store it within the given <code>result</code>. Formally, let <i>A</i> a square matrix defined such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0i} &amp; \dots  &amp; a_{0n} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{ij} &amp; \dots  &amp; a_{in} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nj} &amp; \dots  &amp; a_{nn}
	 *     \end{bmatrix}
	 * $$
	 * The cofactor matrix of <i>A</i>, denoted <i>C</i>, is such that:<br>
	 * $$
	 * C\ =\ \begin{bmatrix} 
	 *        c_{00} &amp; \dots  &amp; c_{0i} &amp; \dots  &amp; c_{0n} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        c_{i0} &amp; \dots  &amp; c_{ij} &amp; \dots  &amp; c_{in} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        c_{n0} &amp; \dots  &amp; c_{nj} &amp; \dots  &amp; c_{nn}
	 *     \end{bmatrix}
	 * $$
	 * <br>where <i>c</i><sub><i>ij</i></sub>&nbsp;=&nbsp;(<i>-1</i>)<sup>(<i>i+j</i>)</sup><i>|M</i><sub>(<i>ij</i>)</sub>|<br>
	 * with <i>M</i><sub><i>ij</i></sub> is the minor matrix from <i>A</i> obtained by ignoring row <i>i</i> and column <i>j</i> from <i>A</i>
	 * <br>
	 * @param result the matrix that have to store the result.
	 * @return the same reference as <code>result</code>
	 * @throws IllegalStateException if the matrix is not square.
	 * @throws IllegalArgumentException if the result has not the same size as this matrix.
	 */
	public Matrix cofactor(Matrix result) throws IllegalStateException, IllegalArgumentException;

	/**
	 * Construct a new matrix that is made of the horizontal concatenation of the given matrix to this one.
	 * Formally, let <i>A</i> a <i>n</i>&times;<i>j</i> matrix defined such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0l} &amp; \dots  &amp; a_{0j} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{il} &amp; \dots  &amp; a_{ij} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nl} &amp; \dots  &amp; a_{nj}
	 *     \end{bmatrix}
	 * $$
	 * and let <i>B</i> a <i>n</i>&times;<i>k</i> matrix defined such that:
	 * $$
	 * B\ =\ \begin{bmatrix} 
	 *        b_{00} &amp; \dots  &amp; b_{0s} &amp; \dots  &amp; b_{0k} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0} &amp; \dots  &amp; b_{is} &amp; \dots  &amp; b_{ik} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0} &amp; \dots  &amp; b_{ns} &amp; \dots  &amp; b_{nk}
	 *     \end{bmatrix}
	 * $$
	 * The horizontal concatenation <i>C</i><sub>h</sub> of <i>A</i> and <i>B</i> is a <i>n</i>&times;<i>j</i>+<i>k</i> matrix defined such that:
	 * $$
	 * C_{h}\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0l} &amp; \dots  &amp; a_{0j} &amp; b_{00} &amp; \dots  &amp; b_{0s} &amp; \dots  &amp; b_{0k} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots &amp; \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{il} &amp; \dots  &amp; a_{ij} &amp; b_{i0} &amp; \dots  &amp; b_{is} &amp; \dots  &amp; b_{ik} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots &amp; \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\       
	 *        a_{n0} &amp; \dots  &amp; a_{nl} &amp; \dots  &amp; a_{nj} &amp; b_{n0} &amp; \dots  &amp; b_{ns} &amp; \dots  &amp; b_{nk}
	 *     \end{bmatrix}
	 * $$
	 * @param right the matrix to concatenate on the right
	 * @return the concatenated matrix
	 * @throws IllegalArgumentException if the number of lines from the input matrix is not compatible with the number of lines of this matrix
	 */
	public Matrix concatHorizontal(Matrix right);

	/**
	 * Construct a new matrix that is made of the horizontal concatenation of the given matrix to this one.
	 * Formally, let <i>A</i> a <i>n</i>&times;<i>j</i> matrix defined such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0l} &amp; \dots  &amp; a_{0j} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{il} &amp; \dots  &amp; a_{ij} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{n0} &amp; \dots  &amp; a_{nl} &amp; \dots  &amp; a_{nj}
	 *     \end{bmatrix}
	 * $$
	 * and let <i>B</i> a <i>n</i>&times;<i>k</i> matrix defined such that:
	 * $$
	 * B\ =\ \begin{bmatrix} 
	 *        b_{00} &amp; \dots  &amp; b_{0s} &amp; \dots  &amp; b_{0k} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{i0} &amp; \dots  &amp; b_{is} &amp; \dots  &amp; b_{ik} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{n0} &amp; \dots  &amp; b_{ns} &amp; \dots  &amp; b_{nk}
	 *     \end{bmatrix}
	 * $$
	 * The horizontal concatenation <i>C</i><sub>h</sub> of <i>A</i> and <i>B</i> is a <i>n</i>&times;<i>j</i>+<i>k</i> matrix defined such that:
	 * $$
	 * C_{h}\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0l} &amp; \dots  &amp; a_{0j} &amp; b_{00} &amp; \dots  &amp; b_{0s} &amp; \dots  &amp; b_{0k} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots &amp; \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{i0} &amp; \dots  &amp; a_{il} &amp; \dots  &amp; a_{ij} &amp; b_{i0} &amp; \dots  &amp; b_{is} &amp; \dots  &amp; b_{ik} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots &amp; \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\       
	 *        a_{n0} &amp; \dots  &amp; a_{nl} &amp; \dots  &amp; a_{nj} &amp; b_{n0} &amp; \dots  &amp; b_{ns} &amp; \dots  &amp; b_{nk}
	 *     \end{bmatrix}
	 * $$
	 * @param right the matrix to concatenate on the right
	 * @param result the matrix that has to store the result (has to be well fitted)
	 * @return the concatenated matrix (a reference on <code>result</code>)
	 * @throws IllegalArgumentException if the number of lines from the input matrix is not compatible with the number of lines of this matrix or if the result matrix size is not compatible
	 */
	public Matrix concatHorizontal(Matrix right, Matrix result);

	/**
	 * Construct a new matrix that is made of the vertical concatenation of the given matrix to this one.
	 * Formally, let <i>A</i> a <i>j</i>&times;<i>m</i> matrix defined such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0l} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{t0} &amp; \dots  &amp; a_{tl} &amp; \dots  &amp; a_{tm} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{j0} &amp; \dots  &amp; a_{jl} &amp; \dots  &amp; a_{jm}
	 *     \end{bmatrix}
	 * $$
	 * and let <i>B</i> a <i>k</i>&times;<i>m</i> matrix defined such that:
	 * $$
	 * B\ =\ \begin{bmatrix} 
	 *        b_{00} &amp; \dots  &amp; b_{0v} &amp; \dots  &amp; b_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{u0} &amp; \dots  &amp; b_{uv} &amp; \dots  &amp; b_{um} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{k0} &amp; \dots  &amp; b_{kv} &amp; \dots  &amp; b_{km}
	 *     \end{bmatrix}
	 * $$
	 * The vertical concatenation <i>C</i><sub>v</sub> of <i>A</i> and <i>B</i> is a <i>j</i>+<i>k</i>&nbsp;&times;&nbsp;<i>m</i> matrix defined such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0l} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{t0} &amp; \dots  &amp; a_{tl} &amp; \dots  &amp; a_{tm} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{j0} &amp; \dots  &amp; a_{jl} &amp; \dots  &amp; a_{jm} \\
	 *        b_{00} &amp; \dots  &amp; b_{0v} &amp; \dots  &amp; b_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{u0} &amp; \dots  &amp; b_{uv} &amp; \dots  &amp; b_{um} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{k0} &amp; \dots  &amp; b_{kv} &amp; \dots  &amp; b_{km}
	 *     \end{bmatrix}
	 * $$
	 * @param bottom the matrix to concatenate on the bottom of this one
	 * @return the concatenated matrix (a reference on <code>result</code>)
	 * @throws IllegalArgumentException if the number of columns from the input matrix is not compatible with the number of columns of this matrix or if the result matrix size is not compatible
	 */
	public Matrix concatVertical(Matrix bottom);

	/**
	 * Construct a new matrix that is made of the vertical concatenation of the given matrix to this one.
	 * Formally, let <i>A</i> a <i>j</i>&times;<i>m</i> matrix defined such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0l} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{t0} &amp; \dots  &amp; a_{tl} &amp; \dots  &amp; a_{tm} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{j0} &amp; \dots  &amp; a_{jl} &amp; \dots  &amp; a_{jm}
	 *     \end{bmatrix}
	 * $$
	 * and let <i>B</i> a <i>k</i>&times;<i>m</i> matrix defined such that:
	 * $$
	 * B\ =\ \begin{bmatrix} 
	 *        b_{00} &amp; \dots  &amp; b_{0v} &amp; \dots  &amp; b_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{u0} &amp; \dots  &amp; b_{uv} &amp; \dots  &amp; b_{um} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{k0} &amp; \dots  &amp; b_{kv} &amp; \dots  &amp; b_{km}
	 *     \end{bmatrix}
	 * $$
	 * The vertical concatenation <i>C</i><sub>v</sub> of <i>A</i> and <i>B</i> is a <i>j</i>+<i>k</i>&nbsp;&times;&nbsp;<i>m</i> matrix defined such that:
	 * $$
	 * A\ =\ \begin{bmatrix} 
	 *        a_{00} &amp; \dots  &amp; a_{0l} &amp; \dots  &amp; a_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        a_{t0} &amp; \dots  &amp; a_{tl} &amp; \dots  &amp; a_{tm} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        a_{j0} &amp; \dots  &amp; a_{jl} &amp; \dots  &amp; a_{jm} \\
	 *        b_{00} &amp; \dots  &amp; b_{0v} &amp; \dots  &amp; b_{0m} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\
	 *        b_{u0} &amp; \dots  &amp; b_{uv} &amp; \dots  &amp; b_{um} \\
	 *        \vdots &amp; \ddots &amp; \vdots &amp; \ddots &amp; \vdots \\     
	 *        b_{k0} &amp; \dots  &amp; b_{kv} &amp; \dots  &amp; b_{km}
	 *     \end{bmatrix}
	 * $$
	 * @param bottom the matrix to concatenate on the bottom of this one
	 * @param result the matrix that has to store the result (has to be well fitted)
	 * @return the concatenated matrix (a reference on <code>result</code>)
	 * @throws IllegalArgumentException if the number of columns from the input matrix is not compatible with the number of columns of this matrix or if the result matrix size is not compatible
	 */
	public Matrix concatVertical(Matrix bottom, Matrix result);
}
