package org.jeometry.io.ply;

import org.jeometry.Geometry;

/**
 * Some specification values coming from the
 * <a href="http://paulbourke.net/dataformats/ply/">Stanford Ploygon Format</a>.
 * 
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 *
 */
public class PLY {

  /**
   * Specifies an ASCII (textual) format for PLY file.
   * @see #FILE_FORMAT_BINARY_BE
   * @see #FILE_FORMAT_BINARY_LE
   */
  public static final int FILE_FORMAT_ASCII = 0;

  /**
   * Specifies a Little Endian (binary) format for PLY file.
   * @see #FILE_FORMAT_ASCII
   * @see #FILE_FORMAT_BINARY_BE
   */
  public static final int FILE_FORMAT_BINARY_LE = 1;

  /**
   * Specifies a Big Endian (binary) format for PLY file.
   * @see #FILE_FORMAT_ASCII
   * @see #FILE_FORMAT_BINARY_LE
   */
  public static final int FILE_FORMAT_BINARY_BE = 2;

  /**
   * Specifies an unknown property type or a property type that is not handled by this implementation.
   */
  public static final int TYPE_UNKNOWN = 0;
  
  /**
   * Specifies an unknown type size (<i>0</i>).
   */
  public static final int TYPE_UNKNOWN_SIZE = 0;

  /**
   * The PLY char property type.
   */
  public static final int TYPE_CHAR = 1;
  
  /**
   * The PLY char property type size (1 byte)
   */
  public static final int TYPE_CHAR_SIZE = 1;

  /**
   * The PLY <code>uchar</code> (unsigned char) property type.
   */
  public static final int TYPE_UCHAR = 2;
  
  /**
   * The PLY <code>uchar</code> property type size (1 byte)
   */
  public static final int TYPE_UCHAR_SIZE = 1;

  /**
   * The PLY <code>short</code> property type.
   */
  public static final int TYPE_SHORT = 3;
  
  /**
   * The PLY <code>short</code> property type size (2 bytes).
   */
  public static final int TYPE_SHORT_SIZE = 2;

  /**
   * The PLY <code>ushort</code> (unsigned short) property type.
   */
  public static final int TYPE_USHORT = 4;
  
  /**
   * The PLY <code>ushort</code> property type size (2 bytes).
   */
  public static final int TYPE_USHORT_SIZE = 2;

  /**
   * The PLY <code>int</code> property type.
   */
  public static final int TYPE_INT = 5;
  
  /**
   * The PLY <code>int</code> property type size (4 bytes).
   */
  public static final int TYPE_INT_SIZE = 4;

  /**
   * The PLY <code>uint</code> (unsigned int) property type.
   */
  public static final int TYPE_UINT = 6;
  
  /**
   * The PLY <code>uint</code> property type size (4 bytes).
   */
  public static final int TYPE_UINT_SIZE = 4;

  /**
   * The PLY <code>float</code> property type.
   */
  public static final int TYPE_FLOAT = 7;
  
  /**
   * The PLY <code>float</code> property type size (4 bytes).
   */
  public static final int TYPE_FLOAT_SIZE = 4;

  /**
   * The PLY <code>double</code> property type.
   */
  public static final int TYPE_DOUBLE = 8;
  
  /**
   * The PLY <code>double</code> property type size (8 bytes).
   */
  public static final int TYPE_DOUBLE_SIZE = 8;

  /**
   * The PLY <code>list</code> element type.
   */
  public static final int TYPE_LIST = 9;

  /**
   * Specifies the use of 2D vertices.
   * @see #VERTEX_TYPE_3D
   */
  public static final int VERTEX_TYPE_2D = 1;

  /**
   * Specifies the use of 3D vertices.
   * @see #VERTEX_TYPE_2D
   */
  public static final int VERTEX_TYPE_3D = 2;

  /**
   * The set of PLY type names that are handled by this implementation.
   */
  public static final String[] TYPE_NAMES = { "unknown", "char", "uchar", "short", "ushort", "int", "uint", "float", "double", "list" };
  
  /**
   * The set of type sizes that are handled by this implementation.
   */
  public static final int[] TYPE_SIZES = { TYPE_UNKNOWN_SIZE, TYPE_CHAR_SIZE, TYPE_UCHAR_SIZE, TYPE_SHORT_SIZE, TYPE_USHORT_SIZE, TYPE_INT_SIZE, TYPE_UINT_SIZE,
      TYPE_FLOAT_SIZE, TYPE_DOUBLE_SIZE, -1 };

  /**
   * Get the name of the type given in parameter (see {@link #TYPE_CHAR}, {@link #TYPE_UCHAR}, ...)
   * @param type the given type.
   * @return the name of the type given in parameter.
   * @see #getType(String)
   */
  public static final String getTypeName(int type) {
    if ((type > 0) && (type < TYPE_NAMES.length)) {
      return TYPE_NAMES[type];
    } else {
      return TYPE_NAMES[0];
    }
  }

  /**
   * Get the type corresponding to the given name.
   * @param typeName a PLY type name (see {@link #TYPE_NAMES}).
   * @return the type corresponding to the given name.
   * @see #getTypeName(int)
   */
  public static final int getType(String typeName) {

    for (int i = 0; i < TYPE_NAMES.length; i++) {
      if (TYPE_NAMES[i].equals(typeName)) {
        return i;
      }
    }

    return 0;
  }

  /**
   * Get the size in bytes of the given type.
   * @param type the PLY type.
   * @return the size in bytes of the given type.
   * @see #getType(String)
   * @see #getTypeName(int)
   */
  public static final int getTypeSize(int type) {
    if ((type > 0) && (type < TYPE_SIZES.length)) {
      return TYPE_SIZES[type];
    } else {
      return TYPE_SIZES[0];
    }
  }
}
