package org.jeometry.io.ply;

import org.jeometry.Jeometry;

/**
 * A PLY property descriptor.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class PLYPropertyDescription {
	
  /**
   * The property type. 
   */
  private int type = PLY.TYPE_UNKNOWN;

  /**
   * The property name.
   */
  private String name = null;

  @Override
  public String toString() {
    return "property " + PLY.getTypeName(getType()) + " " + getName();
  }

  /**
   * Create a property descriptor with the given type and the given name.
   * @param type the type of the property (see {@link PLY#TYPE_CHAR}, {@link PLY#TYPE_UCHAR}, ...).
   * @param name the name of the property.
   */
  public PLYPropertyDescription(int type, String name) {
    this.type = type;
    this.name = name;
  }

  /**
   * Get the type of the property (see {@link PLY#TYPE_CHAR}, {@link PLY#TYPE_UCHAR}, ...).
   * @return the type of the property.
   * @see #setType(int)
   */
  public int getType() {
    return type;
  }

  /**
   * Set the type of the property (see {@link PLY#TYPE_CHAR}, {@link PLY#TYPE_UCHAR}, ...).
   * @param type the type of the property.
   * @see #getType()
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Get the name of the property.
   * @return the name of the property.
   * @see #setName(String)
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of the property.
   * @param name the name of the property.
   * @see #getName()
   */
  public void setName(String name) {
    this.name = name;
  }
}
