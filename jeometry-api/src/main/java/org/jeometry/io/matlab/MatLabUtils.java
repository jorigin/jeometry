package org.jeometry.io.matlab;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

/**
 * A class that contains <a href="https://fr.mathworks.com/products/matlab.html">MathWorks MatLab(c)</a> related utilities.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class MatLabUtils {

	/**
	 * A flag that indicates that a vector has to be exported as a column vector.
	 * @see #VECTOR_ROW
	 */
	public static final int VECTOR_COLUMN = 1;
	
	/**
	 * A flag that indicates that a vector has to be exported as a row vector.
	 * @see #VECTOR_COLUMN
	 */
	public static final int VECTOR_ROW    = 2;
	
	/**
	 * Get the <a href="https://fr.mathworks.com/products/matlab.html">MathWorks MatLab(c)</a> code corresponding to the given {@link Matrix matrix}.
	 * @param matrix the matrix to export
	 * @param name the name of the matrix (ie the name of the matlab variable), if <code>null</code> is given, no variable is declared
	 * @return the <a href="https://fr.mathworks.com/products/matlab.html">MathWorks MatLab(c)</a> code corresponding to the given matrix
	 */
	public static String toMatLab(Matrix matrix, String name) {
		
		String code = "";
		
		if ((matrix != null)){
			if ((name != null) && !("".equalsIgnoreCase(name))){
				code = name+" = ";
			}
			
			code += "[";
			for(int row = 0; row < matrix.getRowsCount(); row++) {
				for(int col = 0; col < matrix.getColumnsCount(); col++) {
					code += " "+matrix.getValue(row, col);
				}
				
				if (row < matrix.getRowsCount() - 1) {
					code += ";";
				}
			}
			code += " ]";
		}
		
		
		return code;
	}
	
	/**
	 * Get the <a href="https://fr.mathworks.com/products/matlab.html">MathWorks MatLab(c)</a> code corresponding to the given {@link Vector vector}.
	 * @param vector the vector to export
	 * @param name the name of the vector (ie the name of the matlab variable), if <code>null</code> is given, no variable is declared
	 * @param representation is the vector has to be represented as a {@link #VECTOR_COLUMN column} or a {@link #VECTOR_ROW row}
	 * @return the <a href="https://fr.mathworks.com/products/matlab.html">MathWorks MatLab(c)</a> code corresponding to the given {@link Vector vector}.
	 * @see #VECTOR_COLUMN
	 * @see #VECTOR_ROW
	 */
	public static String toMatlab(Vector vector, String name, int representation) {
		String code = "";
		
		if (vector != null) {
			if ((name != null) && !("".equalsIgnoreCase(name))){
				code = name+" = ";
			}
			
			if (representation == VECTOR_ROW) {
				code += "[";
				for(int dimension = 0; dimension < vector.getDimension(); dimension++) {
					code += " "+vector.getVectorComponent(dimension);
				}
				code += "]";
			} else if (representation == VECTOR_COLUMN) {
				code += "[";
				for(int dimension = 0; dimension < vector.getDimension(); dimension++) {
					code += " "+vector.getVectorComponent(dimension);
					
					if (dimension < (vector.getDimension() - 1)) {
						code +=";";
					}
				}
				code += "]";
			} else {
				throw new IllegalArgumentException("Invalid vector representation ("+representation+"). Possible vaues are MatLab.VECTOR_COLUMN or MatLab.VECTOR_ROW.");
			}
		}
		
		return code;
	}
}
