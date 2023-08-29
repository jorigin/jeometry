package org.jeometry.io.ply;

import org.jeometry.Jeometry;

/**
 * A PLY property list descriptor.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class PLYPropertyListDescription extends PLYPropertyDescription {

  /**
   * The property count type.
   */
  private int countType = PLY.TYPE_UNKNOWN;

  /**
   * The property value type.
   */
  private int valueType = PLY.TYPE_UNKNOWN;

  @Override
  public String toString() {
    return "property " + PLY.getTypeName(getType()) + " " + PLY.getTypeName(getCountType()) + " " + PLY.getTypeName(getValueType()) + " " + getName();
  }

  /**
   * Create a new PLY property description with given parameters.
   * @param name the name of the description.
   * @param countType the type of the property count (see {@link PLY} class for more details).
   * @param valueType the type of the value (see {@link PLY} class for more details).
   */
  public PLYPropertyListDescription(String name, int countType, int valueType) {
    super(PLY.TYPE_LIST, name);
    this.countType = countType;
    this.valueType = valueType;
  }

  /**
   * Get the description count type.
   * @return the description count type.
   * @see #setCountType(int)
   */
  public int getCountType() {
    return this.countType;
  }

  /**
   * Set the description count type.
   * @param countType the description count type.
   * @see #getCountType()
   */
  public void setCountType(int countType) {
    this.countType = countType;
  }

  /**
   * Get the value type.
   * @return the value type.
   * @see #setValueType(int)
   */
  public int getValueType() {
    return this.valueType;
  }

  /**
   * Set the value type.
   * @param indexType the value type.
   * @see #getValueType()
   */
  public void setValueType(int indexType) {
    this.valueType = indexType;
  }
}