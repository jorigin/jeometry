package org.jeometry.io.ply;

import java.util.Collection;

import org.jeometry.Geometry;

/**
 * a PLY file descriptor. This class contains metadata that describes a PLY input.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 */
public class PLYFileDescriptor {
  
  private int fileFormat = PLY.FILE_FORMAT_ASCII;

  private Collection<PLYElementDescription> elementDescriptors = null;

  private int headerLineCount = 0;

  private int vertexCount = -1;

  private int faceCount = -1;

  private int edgeCount = -1;

  private int vertexType = -1;

  /**
   * Get the format of the PLY file. There is 3 formats available:
   * <ul>
   * <li>{@link PLY#FILE_FORMAT_ASCII} for an ASCII file;
   * <li>{@link PLY#FILE_FORMAT_BINARY_LE} for a binary file with Little Endian coding;
   * <li>{@link PLY#FILE_FORMAT_BINARY_BE} for a binary file with Big Endian coding;
   * </ul>
   * 
   * @return the format of the PLY file.
   * @see #setFileFormat(int)
   */
  public int getFileFormat() {
    return this.fileFormat;
  }

  /**
   * Set the format of the PLY file. There is 3 formats available:
   * <ul>
   * <li>{@link PLY#FILE_FORMAT_ASCII} for an ASCII file;
   * <li>{@link PLY#FILE_FORMAT_BINARY_LE} for a binary file with Little Endian coding;
   * <li>{@link PLY#FILE_FORMAT_BINARY_BE} for a binary file with Big Endian coding;
   * </ul>
   * 
   * @param fileFormat the format of the PLY file.
   * @see #getFileFormat()
   */
  public void setFileFormat(int fileFormat) {
    this.fileFormat = fileFormat;
  }

  /**
   * Get the element descriptors declared within the PLY file.
   * @return the element descriptors declared within the PLY file.
   * @see #setElementDescriptors(Collection)
   */
  public Collection<PLYElementDescription> getElementDescriptors() {
    return elementDescriptors;
  }

  /**
   * Set the element descriptors declared within the PLY file.
   * @param elementDescriptors the element descriptors declared within the PLY file.
   * @see #getElementDescriptors()
   */
  public void setElementDescriptors(Collection<PLYElementDescription> elementDescriptors) {
    this.elementDescriptors = elementDescriptors;
  }

  /**
   * Get the number of lines that compose the PLY file header.
   * @return the number of lines that compose the PLY file header.
   * @see #setHeaderLineCount(int)
   */
  public int getHeaderLineCount() {
    return headerLineCount;
  }

  /**
   * Set the number of lines that compose the PLY file header.
   * @param headerLineCount the number of lines that compose the PLY file header.
   * @see #getHeaderLineCount()
   */
  public void setHeaderLineCount(int headerLineCount) {
    this.headerLineCount = headerLineCount;
  }

  /**
   * Get the number of vertices within the PLY file.
   * @return the number of vertices within the PLY file.
   * @see #setVertexCount(int)
   */
  public int getVertexCount() {
    return vertexCount;
  }

  /**
   * Set the number of vertices within the PLY file.
   * @param vertexCount the number of vertices within the PLY file.
   * @see #getVertexCount()
   */
  public void setVertexCount(int vertexCount) {
    this.vertexCount = vertexCount;
  }

  /**
   * Get the number of faces within the PLY file.
   * @return the number of faces within the PLY file.
   * @see #setFaceCount(int)
   */
  public int getFaceCount() {
    return faceCount;
  }

  /**
   * Set the number of faces within the PLY file.
   * @param faceCount the number of faces within the PLY file.
   * @see #getFaceCount()
   */
  public void setFaceCount(int faceCount) {
    this.faceCount = faceCount;
  }

  /**
   * Get the number of edges within the PLY file.
   * @return the number of edges within the PLY file.
   * @see #setEdgeCount(int)
   */
  public int getEdgeCount() {
    return edgeCount;
  }

  /**
   * Set the number of edges within the PLY file.
   * @param edgeCount the number of edges within the PLY file.
   * @see #getEdgeCount()
   */
  public void setEdgeCount(int edgeCount) {
    this.edgeCount = edgeCount;
  }

  /**
   * Get the vertex type declared within this PLY file.<br>
   * Possible values are:<br>
   * <ul>
   * <li>{@link PLY#VERTEX_TYPE_2D}
   * <li>{@link PLY#VERTEX_TYPE_3D}
   * </ul>
   * @return  the vertex type declared within this PLY file.
   * @see #setVertexType(int)
   */
  public int getVertexType() {
    return vertexType;
  }

  /**
   * Set the vertex type declared within this PLY file.<br>
   * Possible values are:<br>
   * <ul>
   * <li>{@link PLY#VERTEX_TYPE_2D}
   * <li>{@link PLY#VERTEX_TYPE_3D}
   * </ul>
   * @param vertexType the vertex type declared within this PLY file.
   * @see #getVertexType()
   */
  public void setVertexType(int vertexType) {
    this.vertexType = vertexType;
  }
}
