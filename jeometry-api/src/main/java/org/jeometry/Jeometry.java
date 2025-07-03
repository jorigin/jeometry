package org.jeometry;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

/**
 * The Jeometry package common class. 
 * This class enable to set general parameters and to get general information about this Jeometry implementation.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value #version} build {@value #BUILD}
 * @since 1.0.0
 *
 */
public class Jeometry {

	/**
	 * The build version.
	 */
	public static final long BUILD     = 202507041400L;

	/**
	 * The version number
	 */
	public static final String version = "2.0.0";

	/**
	 * The {@link java.util.logging.Logger logger} used for reporting.
	 */
	public static Logger logger = null;

	static {
		init();
	}

	/**
	 * Initialize the JOrigin common package.
	 */
	public static final void init(){

		logger = Logger.getLogger("org.jorigin.Geometry");

		String property = System.getProperty("java.util.logging.level");
		Level level = Level.CONFIG;
		if (property != null){
			if (property.equalsIgnoreCase("OFF")){
				level = Level.OFF;
			} else if (property.equalsIgnoreCase("SEVERE")){
				level = Level.SEVERE;
			} else if (property.equalsIgnoreCase("WARNING")){
				level = Level.WARNING;
			} else if (property.equalsIgnoreCase("INFO")){
				level = Level.INFO;
			} else if (property.equalsIgnoreCase("CONFIG")){
				level = Level.CONFIG;
			} else if (property.equalsIgnoreCase("FINE")){
				level = Level.FINE;
			} else if (property.equalsIgnoreCase("FINER")){
				level = Level.FINER;
			} else if (property.equalsIgnoreCase("FINEST")){
				level = Level.FINEST;
			} else if (property.equalsIgnoreCase("ALL")){
				level = Level.ALL;
			}
		}

		logger.setLevel(level);

	}

	/**
	 * Set the {@link java.util.logging.Logger logger} to use for reporting.
	 * @param logger the {@link java.util.logging.Logger logger} to use for reporting.
	 */
	public static void setLogger(Logger logger){
		Jeometry.logger = logger;
	}

	/**
	 * Print the given {@link Matrix matrix} onto the given stream.
	 * @param matrix the matrix to print.
	 * @param stream the stream to use as output.
	 * @param prefix a prefix to add to the print.
	 */
	public static void print(Matrix matrix, PrintStream stream, String prefix) {
		if ((stream != null) && (matrix != null)) {
			for(int row = 0; row < matrix.getRowsCount(); row++) {
				stream.print(prefix+"[");
				for(int col = 0; col < matrix.getColumnsCount(); col++) {
					stream.print(" "+matrix.getValue(row, col));
				}
				stream.print(" ]");
				stream.println();
			}
		}
	}

	/**
	 * Print the given {@link Matrix matrix} onto the given {@link Writer writer}.
	 * @param matrix the matrix to print.
	 * @param writer the writer to use as output.
	 * @param prefix a prefix to add to the print.
	 */
	public static void print(Matrix matrix, Writer writer, String prefix) {
		if ((writer != null) && (matrix != null)) {
			try {
				for(int row = 0; row < matrix.getRowsCount(); row++) {
					writer.write(prefix+"[");
					for(int col = 0; col < matrix.getColumnsCount(); col++) {
						writer.write(" "+matrix.getValue(row, col));
					}
					writer.write(" ]");
					writer.write("\n");
				}
			} catch (IllegalArgumentException e) {
				logger.log(Level.SEVERE, "Cannot write to writer: "+e.getMessage(), e);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Cannot write to writer: "+e.getMessage(), e);
			}
		}
	}

	/**
	 * Print the given {@link Matrix matrix} onto the given stream.
	 * @param matrix the matrix to print.
	 * @param stream the stream to use as output.
	 * @param prefix a prefix to add to the print.
	 * @param width the number of characters that a column can display
	 * @param fraction the number of fractional digit to display
	 */
	public static void print(Matrix matrix, PrintStream stream, String prefix, int width, int fraction) {


		DecimalFormat format = new DecimalFormat();
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		format.setMinimumIntegerDigits(1);

		if (fraction >= 0) {
			format.setMaximumFractionDigits(fraction);
			format.setMinimumFractionDigits(fraction);
		}

		format.setGroupingUsed(false);

		print(matrix, stream, prefix, width, format);
	}

	/**
	 * Print the given {@link Matrix matrix} onto the given {@link Writer writer}.
	 * @param matrix the matrix to print.
	 * @param writer the writer to use as output.
	 * @param prefix a prefix to add to the print.
	 * @param width the number of characters that a column can display
	 * @param fraction the number of fractional digit to display
	 */
	public static void print(Matrix matrix, Writer writer, String prefix, int width, int fraction) {

		DecimalFormat format = new DecimalFormat();
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		format.setMinimumIntegerDigits(1);

		if (fraction >= 0) {
			format.setMaximumFractionDigits(fraction);
			format.setMinimumFractionDigits(fraction);
		}

		format.setGroupingUsed(false);

		print(matrix, writer, prefix, width, format);
	}

	/**
	 * Print the given {@link Matrix matrix} onto the given stream.
	 * @param matrix the matrix to print.
	 * @param stream the stream to use as output.
	 * @param prefix a prefix to add to the print.
	 * @param width the number of characters that a column can display
	 * @param format the number format to use for cell display
	 */
	public static void print(Matrix matrix, PrintStream stream, String prefix, int width, NumberFormat format) {

		if ((stream != null) && (matrix != null)) {

			for(int row = 0; row < matrix.getRowsCount(); row++) {
				stream.print(prefix+"[");
				for(int col = 0; col < matrix.getColumnsCount(); col++) {
					String s = format.format(matrix.getValue(row, col));
					int padding = Math.max(1,width-s.length()); // At _least_ 1 space
					for (int k = 0; k < padding; k++)
						stream.print(' ');

					stream.print(s);
				}
				stream.print(" ]");
				stream.println();
			}
		}
	}

	/**
	 * Print the given {@link Matrix matrix} onto the given {@link Writer writer}.
	 * @param matrix the matrix to print.
	 * @param writer the writer to use as output.
	 * @param prefix a prefix to add to the print.
	 * @param width the number of characters that a column can display
	 * @param format the number format to use for cell display
	 */
	public static void print(Matrix matrix, Writer writer, String prefix, int width, NumberFormat format) {

		if ((writer != null) && (matrix != null)) {

			try {
				for(int row = 0; row < matrix.getRowsCount(); row++) {
					writer.write(prefix+"[");
					for(int col = 0; col < matrix.getColumnsCount(); col++) {
						String s = format.format(matrix.getValue(row, col));
						int padding = Math.max(1,width-s.length()); // At _least_ 1 space
						for (int k = 0; k < padding; k++)
							writer.write(' ');

						writer.write(s);
					}
					writer.write(" ]");
					writer.write("\n");
				}
			} catch (IllegalArgumentException e) {
				logger.log(Level.SEVERE, "Cannot write to writer: "+e.getMessage(), e);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Cannot write to writer: "+e.getMessage(), e);
			}
		}
	}

	/**
	 * Print the given {@link Vector vector} on the given stream.
	 * @param vector the {@link Vector vector} to print.
	 * @param stream the stream to use as output.
	 * @param prefix a prefix to add to the print.
	 */
	public static void print(Vector vector, PrintStream stream, String prefix) {
		if ((stream != null) && (vector != null)) {
			stream.print(prefix+"[");
			for(int dimension = 0; dimension < vector.getDimension(); dimension++) {
				stream.print(" "+vector.getValue(dimension));
			}
			stream.print(" ]");
			stream.println();
		}
	}

	/**
	 * Print the given {@link Vector vector} on the given {@link Writer writer}.
	 * @param vector the {@link Vector vector} to print.
	 * @param writer the writer to use as output.
	 * @param prefix a prefix to add to the print.
	 */
	public static void print(Vector vector, Writer writer, String prefix) {
		if ((writer != null) && (vector != null)) {
			try {
				writer.write(prefix+"[");
				for(int dimension = 0; dimension < vector.getDimension(); dimension++) {
					writer.write(" "+vector.getValue(dimension));
				}
				writer.write(" ]");
				writer.write("\n");
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Cannot write to writer: "+e.getMessage(), e);
			}
		}
	}

	/**
	 * Print the given {@link Vector vector} onto the given {@link PrintStream print stream}.
	 * @param vector the vector to print.
	 * @param stream the stream to use as output.
	 * @param prefix a prefix to add to the print.
	 * @param width the number of characters that a column can display
	 * @param format the number format to use for number display
	 */
	public static void print(Vector vector, PrintStream stream, String prefix, int width, NumberFormat format) {

		if ((stream != null) && (vector != null)) {
			stream.print(prefix+"[");

			for(int dimension = 0; dimension < vector.getDimension(); dimension++) {

				String s = format.format(vector.getValue(dimension));
				int padding = Math.max(1,width-s.length()); // At _least_ 1 space
				for (int k = 0; k < padding; k++)
					stream.write(' ');

				stream.print(" "+vector.getValue(dimension));
			}
			stream.print(" ]");
			stream.println();
		}
	}

	/**
	 * Print the given {@link Vector vector} onto the given {@link Writer writer}.
	 * @param vector the vector to print.
	 * @param writer the writer to use as output.
	 * @param prefix a prefix to add to the print.
	 * @param width the number of characters that a column can display
	 * @param format the number format to use for number display
	 */
	public static void print(Vector vector, Writer writer, String prefix, int width, NumberFormat format) {

		if ((writer != null) && (vector != null)) {
			try {
				writer.write(prefix+"[");

				for(int dimension = 0; dimension < vector.getDimension(); dimension++) {

					String s = format.format(vector.getValue(dimension));
					int padding = Math.max(1,width-s.length()); // At _least_ 1 space
					for (int k = 0; k < padding; k++)
						writer.write(' ');

					writer.write(" "+vector.getValue(dimension));
				}
				writer.write(" ]");
				writer.write("\n");
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Cannot write to writer: "+e.getMessage(), e);
			}
		}
	}
}
