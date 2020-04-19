package org.jeometry.io.ply;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jeometry.Jeometry;

/**
 * A PLY element descriptor.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class PLYElementDescription {

  private String name = null;

  private int elementCount = -1;

  private List<PLYPropertyDescription> propertiyDescriptors = null;

  @Override
  public String toString() {
    String eol = System.getProperty("line.separator");
    String str = "element " + getName() + " " + getElementCount();
    if (propertiyDescriptors != null) {
      for (int i = 0; i < propertiyDescriptors.size(); i++) {
        str += eol + propertiyDescriptors.get(i);
      }
    }

    return str;
  }

  /**
   * Construct a new PLYElement with the given name and the given elements count.
   * @param name the name of the element.
   * @param elementCount the elements count.
   */
  public PLYElementDescription(String name, int elementCount) {
    this.name = name;
    this.elementCount = elementCount;

    propertiyDescriptors = new ArrayList<PLYPropertyDescription>();
  }

  /**
   * Get the name of the element.
   * @return the name of the element.
   * @see #setName(String)
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of the element.
   * @param name the name of the element.
   * @see #getName()
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the number of elements that are presents.
   * @return the number of elements that are presents.
   * @see #setElementCount(int)
   */
  public int getElementCount() {
    return elementCount;
  }

  /**
   * Set the number of elements that are presents.
   * @param elementCount the number of elements that are presents.
   * @see #getElementCount()
   */
  public void setElementCount(int elementCount) {
    this.elementCount = elementCount;
  }

  /**
   * Get the PLY property descriptors attached to this element.
   * @return the PLY property descriptors attached to this element.
   * @see #setPropertyDescriptors(List)
   */
  public List<PLYPropertyDescription> getPropertyDescriptors() {
    return propertiyDescriptors;
  }

  /**
   * Set the PLY property descriptors attached to this element.
   * @param propertiyDescriptors the PLY property descriptors attached to this element.
   * @see #getPropertyDescriptors()
   */
  public void setPropertyDescriptors(List<PLYPropertyDescription> propertiyDescriptors) {
    this.propertiyDescriptors = propertiyDescriptors;
  }

  /**
   * Add a PLY property descriptor to this element.
   * @param propertiyDescriptor the PLY property descriptor to add.
   * @return <code>true</code> if the descriptor is successfully added and <code>false</code> otherwise.
   * @see #removePropertyDescriptor(PLYPropertyDescription)
   */
  public boolean addPropertyDescriptor(PLYPropertyDescription propertiyDescriptor) {
    return propertiyDescriptors.add(propertiyDescriptor);
  }

  /**
   * Remove the given PLY property descriptor from this element.
   * @param propertiyDescriptor the PLY property descriptor to remove.
   * @return <code>true</code> if the descriptor is successfully removed and <code>false</code> otherwise.
   * @see #addPropertyDescriptor(PLYPropertyDescription)
   */
  public boolean removePropertyDescriptor(PLYPropertyDescription propertiyDescriptor) {
    return propertiyDescriptors.remove(propertiyDescriptor);
  }
  
  /**
   * Get the PLY element property descriptor that is present at <code>index</code>.
   * @param index the index of the property descriptor.
   * @return the  PLY element property descriptor that is present at <code>index</code>.
   */
  public PLYPropertyDescription getPropertyDescriptor(int index) {
    return propertiyDescriptors.get(index);
  }

  /**
   * Get the index of the PLY element property descriptor named <code>name</code>.
   * @param name the name of the property descriptor.
   * @return the index of the PLY element property descriptor.
   */
  public int getPropertyIndex(String name) {
    int index = -1;

    Iterator<PLYPropertyDescription> iter = propertiyDescriptors.iterator();
    int cpt = 0;
    while ((index == -1) && (iter.hasNext())) {
      if (name.equals(iter.next().getName())) {
        index = cpt;
      }
      cpt++;
    }

    return index;
  }

  /**
   * Get the index of the PLY element property descriptor for which the name contains <code>value</code>.
   * @param value the value that the property descriptor has to contain.
   * @return the index of the PLY element property descriptor for which the name contains <code>value</code>.
   */
  public int getPropertyIndexContains(String value) {
    int index = -1;

    Iterator<PLYPropertyDescription> iter = propertiyDescriptors.iterator();
    int cpt = 0;
    while ((index == -1) && (iter.hasNext())) {
      if (iter.next().getName().contains(value)) {
        index = cpt;
      }
      cpt++;
    }

    return index;
  }
}
