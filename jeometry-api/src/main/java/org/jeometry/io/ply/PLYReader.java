package org.jeometry.io.ply;

import static org.jeometry.Jeometry.logger; 

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.mesh.indexed.IndexedTriangleMesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Triangle;
import org.jeometry.geom3D.properties.Colored;
import org.jeometry.geom3D.properties.HasNormal;
import org.jeometry.geom3D.textured.Texturable;
import org.jeometry.geom3D.textured.Texture;
import org.jeometry.geom3D.textured.TextureManager;
import org.jeometry.geom3D.textured.TexturePath;
import org.jeometry.math.Vector;
import org.jorigin.identification.Identified;
import org.jorigin.identification.Named;
import org.jorigin.io.IOStreamUtil;
import org.jorigin.lang.PathUtil;
import org.jorigin.property.HandleUserProperties;

/**
 * This class enable to read a geometric object from a PLY file. Due to the data
 * contained within the file, the nature of the object can be:
 * <ul>
 * <li>{@link Point3DContainer 3D point container} if the PLY file only contains 3D vertex information;
 * <li>{@link Point2DContainer 2D point container} if the PLY file only contains 2D vertex information;
 * <li>{@link IndexedMesh Indexed mesh} if the PLY file contains vertex and faces information;
 * <li>{@link IndexedTriangleMesh an indexed triangle mesh} if the PLY file contains vertex and faces information and if all faces are triangular;
 * </ul>
 * The class used to wrap PLY vertex into point can be set by using {@link #setPoint3DClass(Class)} method.
 * @param <T> the type of the underlying 3D points. It should be ensured that 3D point created from from {@link JeometryFactory geometry factory} delivers objects that are compatible with the type <code>T</code>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class PLYReader<T extends Point3D> {

	/**
	 * The standard name of a vertex.
	 */
  public static final String VERTEX_NAME = "VERTEX";

  /**
   * The standard name of an edge.
   */
  public static final String EDGE_NAME = "EDGE";

  /**
   * The standard name of a face.
   */
  public static final String FACE_NAME = "FACE";

  /**
   * The flag that describes points.
   */
  private static final int GEOM_POINTS = 1;

  /**
   * The flag that describes polyhedron.
   */
  private static final int GEOM_POLYHEDRON = 2;

  /**
   * The flag that describes X index.
   */
  private static final int X_INDEX = 0;

  /**
   * The flag that describes Y index.
   */
  private static final int Y_INDEX = 1;

  /**
   * The flag that describes Z index.
   */
  private static final int Z_INDEX = 2;

  /**
   * The flag that describes R index.
   */
  private static final int R_INDEX = 3;

  /**
   * The flag that describes G index.
   */
  private static final int G_INDEX = 4;

  /**
   * The flag that describes B index.
   */
  private static final int B_INDEX = 5;

  /**
   * The flag that describes A index.
   */
  private static final int A_INDEX = 6;

  /**
   * The flag that describes NX index.
   */
  private static final int NX_INDEX = 7;

  /**
   * The flag that describes NY index.
   */
  private static final int NY_INDEX = 8;

  /**
   * The flag that describes NZ index.
   */
  private static final int NZ_INDEX = 9;

  /**
   * The flag that describes vertex list index.
   */
  private static final int VERTEX_LIST_INDEX = 10;

  /**
   * The flag that describes texture index.
   */
  private static final int TEX_INDEX = 11;

  /**
   * The flag that describes texture coordinates index.
   */
  private static final int TEX_COORDS_INDEX = 12;

//  private static final int ID_INDEX = 13;

  /**
   * The flag that describes texture u index.
   */
  private static final int TEX_U_INDEX = 14;

  /**
   * The flag that describes texture v index.
   */
  private static final int TEX_V_INDEX = 15;

  /**
   * The default points name.
   */
  private String pointName = "PLY";

  /**
   * The PLY X property name.
   */
  private final String plyXPropName = "x";
  
  /**
   * The PLY Y property name.
   */
  private final String plyYPropName = "y";
  
  /**
   * The PLY Z property name.
   */
  private final String plyZPropName = "z";

  /**
   * The PLY color red property name.
   */
  private final String plyRPropName = "red";
  
  /**
   * The PLY color green property name.
   */
  private final String plyGPropName = "green";
  
  /**
   * The PLY color blue property name.
   */
  private final String plyBPropName = "blue";
  
  /**
   * The PLY X property name.
   */
  private final String plyAPropName = "alpha";

  /**
   * The PLY normal X property name.
   */
  private final String plyNxPropName = "nx";

  /**
   * The PLY normal y property name.
   */
  private final String plyNyPropName = "ny";
  
  /**
   * The PLY normal z property name.
   */
  private final String plyNzPropName = "nz";

  /**
   * The PLY vertex indices property name.
   */
  private final String plyVertexListPropName = "vertex_indices";
  
  /**
   * The PLY vertex index list property name.
   */
  private final String plyVertexListPropNameAlt = "vertex_index";

  /**
   * The PLY texture coordinate property name.
   */
  private final String plyTexCoordPropName = "texcoord";
  
  /**
   * The PLY texture number property name.
   */
  private final String plyTexIndexPropName = "texnumber";

  /**
   * The PLY texture u property name.
   */
  private final String plyVertexTexCoordUPropName = "texture_u";

  /**
   * The PLY texture v property name.
   */
  private final String plyVertexTexCoordVPropName = "texture_v";

  /**
   * The PLY ID property name.
   */
  private final String plyIDPropName = "id";

  /**
   * The PLY reader attached listeners.
   */
  private Collection<PLYReaderListener> listeners = null;

  /**
   * The PLY read textures.
   */
  private List<Texture> textures = null;

  /**
   * The PLY reader has to read point color.
   */
  private boolean readPointColor = true;

  /**
   * Is the PLY reader has to read point normal.
   */
  private boolean readPointNormal = true;

  /**
   * Is the PLY reader has to identifies points.
   */
  private boolean identifyPoints = true;

  /**
   * The read vertex count.
   */
  private int vertexCount = -1;

  /**
   * The read mesh faces.
   */
  private int faceCurrentIndex = -1;

  /**
   * The face default name.
   */
  private final String faceGenericName = "F";
  
  /**
   * The triangle default name.
   */
  private final String faceTriangleName = "T";
//  private final String vertexTriangleName = "V";

  /**
   * The path to the external resources.
   */
  private String resourcePath = null;

  /**
   * The End Of Line (eol) character. By default, this character is obtained
   * from property <code>line.separator</code> of the Java Environment (
   * {@link System#getProperty(String)}).
   */
  private String eol = System.getProperty("line.separator");

  /**
   * The charset used for low level data encoding. By default the charset is set
   * to UTF-8.
   * 
   * @see #setCharset(String)
   * @see #getCharset()
   */
  private String charset = "UTF-8";

  /**
   * The separator between two fields within a PLY file.
   */
  private final String fieldSeparator = " ";

  /**
   * The class used for instantiating vertices.
   */
  private Class<? extends Point3D> pointClass = null;

  /**
   * The class used for instantiating 2D points (texture, ...)
   */
  private Class<? extends Point2D> point2DClass = null;

  /**
   * The PLY file format. Can be {@link PLY#FILE_FORMAT_ASCII}, {@link PLY#FILE_FORMAT_BINARY_BE} or {@link PLY#FILE_FORMAT_BINARY_LE}.
   */
  private int fileFormat = PLY.FILE_FORMAT_ASCII;

  /**
   * The geometry type.
   */
  private int geometryType = -1;

  /**
   * The properties indices.
   */
  private int[] propertyIndexes = null;

  /**
   * Is the texture has to be flipped in Y.
   */
  private boolean flipTextureY = true;

  /**
   * Create a new default PLY reader.
   */
  public PLYReader() {
  }

  /**
   * Get the format of the PLY file. There is 3 formats available:
   * <ul>
   * <li>{@link PLY#FILE_FORMAT_ASCII} for an ASCII file;
   * <li>{@link PLY#FILE_FORMAT_BINARY_LE} for a binary file with Little Endian coding;</li>
   * <li>{@link PLY#FILE_FORMAT_BINARY_BE} for a binary file with Big Endian coding;</li>
   * </ul>
   * 
   * @return the format of the PLY file.
   */
  public int getFileFormat() {
    return this.fileFormat;
  }

  /**
   * Get the charset used for low level data encoding. By default the charset is
   * set to UTF-8.
   * @return the charset used for low level data encoding.
   * @see #setCharset(String)
   */
  public String getCharset() {
    return this.charset;
  }

  /**
   * Set the charset used for low level data encoding. By default the charset is
   * set to UTF-8.
   * 
   * @param charset the charset used for low level data encoding.
   * @see #getCharset()
   */
  public void setCharset(String charset) {
    this.charset = charset;
  }

  /**
   * Get The end Of Line (eol) character. By default, this character is obtained
   * from property <code>line.separator</code> of the Java Environment (
   * {@link System#getProperty(String)}).
   * 
   * @return the end Of Line (eol) character.
   * @see #setEol(String)
   */
  public String getEol() {
    return this.eol;
  }

  /**
   * Set The end Of Line (eol) character. By default, this character is obtained
   * from property <code>line.separator</code> of the Java Environment ({@link System#getProperty(String)}).
   * 
   * @param eol the end Of Line (eol) character.
   * @see #getEol()
   */
  public void setEol(String eol) {
    this.eol = eol;
  }

  /**
   * Get the class that has to be instantiated to represent 3D points. 
   * The given <code>pointClass</code> is an implementation of {@link Point3D} interface. 
   * If this method return <code>null</code>, points are instantiated through {@link JeometryFactory#createPoint3D() GeometryFactory.createPoint3D()} method.
   * 
   * @return the class that has to be instantiated to represent the vertices. 
   * @see #setPoint3DClass(Class)
   */
  public Class<? extends Point3D> getPoint3DClass() {
    return this.pointClass;
  }
  
  /**
   * Set the class that has to be instantiated to represent the vertices. 
   * The given <code>pointClass</code> has to be an implementation of {@link Point3D} interface. 
   * If this method return <code>null</code>, points are instantiated through {@link JeometryFactory#createPoint3D() GeometryFactory.createPoint3D()} method.
   * 
   * @param pointClass the class that has to be instantiated to represent the vertices. 
   * @see #getPoint3DClass()
   */
  public void setPoint3DClass(Class<? extends Point3D> pointClass) {
    this.pointClass = pointClass;
  }

  /**
   * Get the class that is instantiated to represent the 2D points (such as Texture coordinates). 
   * The given <code>pointClass</code> has to be an implementation of {@link Point2D} interface. 
   * @return the class that is instantiated to represent the 2D points (such as Texture coordinates). 
   * @see #setPoint2DClass(Class)
   */
  public Class<? extends Point2D> getPoint2DClass() {
    return this.point2DClass;
  }

  /**
   * Set the class that has to be instantiated to represent the 2D points (such as Texture coordinates). 
   * The given <code>pointClass</code> has to be an implementation of {@link Point2D} interface. 
   * @param point2DClass the class that has to be instantiated to represent the 2D points (such as Texture coordinates). 
   * @see #getPoint2DClass()
   */
  public void setPoint2DClass(Class<? extends Point2D> point2DClass) {
    this.point2DClass = point2DClass;
  }

  /**
   * Get if the PLY reader is taking take into account point colors during read.
   * @return <code>true</code> if the reader is taking take into  account point colors during read or <code>false</code> otherwise.
   * @see #setReadPointColor(boolean)
   */
  public boolean isReadPointColor() {
    return this.readPointColor;
  }

  /**
   * Set if the PLY reader has to take in account point colors during read.
   * @param readPointColor <code>true</code> if the reader has to take in account point colors during read or <code>false</code> otherwise.
   * @see #isReadPointColor()
   */
  public void setReadPointColor(boolean readPointColor) {
    this.readPointColor = readPointColor;
  }

  /**
   * Get if the PLY reader is taking into account point normals during read.
   * @return <code>true</code> if the reader is taking into account point normals during read or <code>false</code> otherwise.
   * @see #setReadPointNormal(boolean)
   */
  public boolean isReadPointNormal() {
    return this.readPointNormal;
  }

  /**
   * Set if the PLY reader has to take in account point normals during read.
   * @param readPointNormal <code>true</code> if the reader has to take in account point normals during read or <code>false</code> otherwise.
   * @see #isReadPointNormal()
   */
  public void setReadPointNormal(boolean readPointNormal) {
    this.readPointNormal = readPointNormal;
  }

  /**
   * Get if the read points have to be affected by an identifier. By default
   * points are identified by an integer identifier that represents the rank of the point within the PLY file.<br>
   * The point identification can be deactivated to save memory when dealing
   * with huge points set.
   * 
   * @return <code>true</code> if the read points have to be affected by an identifier and <code>false</code> otherwise.
   * @see #setIdentifyPoints(boolean)
   */
  public boolean isIdentifyPoints() {
    return this.identifyPoints;
  }

  /**
   * Set if the read points have to be affected by an identifier. By default
   * points are identified by an integer identifier that represents the rank of the point within the PLY file.<br>
   * The point identification can be deactivated to save memory when dealing
   * with huge points set.
   * 
   * @param identifyPoints <code>true</code> if the read points have to be affected by an identifier and <code>false</code> otherwise.
   * @see #isIdentifyPoints()
   */
  public void setIdentifyPoints(boolean identifyPoints) {
    this.identifyPoints = identifyPoints;
  }

  /**
   * Check if the reader has to flip Y coordinates of texture.
   * 
   * @return <code>true</code> if the reader has to flip Y coordinates of
   *         texture and <code>false</code> otherwise.
   * @see #setFlipTextureY(boolean)
   */
  public boolean isFlipTextureY() {
    return this.flipTextureY;
  }

  /**
   * Set if the reader has to flip Y coordinates of texture.
   * 
   * @param flip <code>true</code> if the reader has to flip Y coordinates of texture and <code>false</code> otherwise.
   * @see #isFlipTextureY()
   */
  public void setFlipTextureY(boolean flip) {
    this.flipTextureY = flip;
  }

  /**
   * Read a geometric object from a reader opened onto a PLY file. Due to the
   * data within the PLY file, returned object can be:
   * <ul>
   *   <li>{@link Point3DContainer Point3DContainer} if the PLY file only contains vertex information;
   *   <li>{@link Mesh Mesh} if the PLY file contains vertex and faces information;
   *   <li>{@link IndexedTriangleMesh} if the PLY file contains vertex and faces information and if all faces are triangles;
   * </ul>
   * 
   * @param reader the reader to use.
   * @return the IPolyhedron described by the PLY stream provided by the reader.
   * @throws IOException if an error occurs.
   */
  @SuppressWarnings("unchecked")
public Object read(Reader reader) throws IOException {

	Object ret = null;
	  
    T point = null;

    List<Face<T>> faces = null;
    Face<T> face = null;

    IndexedMesh<T> mesh = null;

    boolean headerFinished = false;

    boolean vertexElementOk = false;

    boolean faceElementOk = false;

    boolean edgeElementOk = false;

    boolean allElementsRead = false;

    String line = null;
    int lineNumber = 0;
    String[] splittedLine = null;

    int faceCount = 0;

    int edgeCount = -1;

    this.textures = null;
    this.faceCurrentIndex = 0;

    boolean isTriangleMesh = true;

    boolean noReadLine = false;

    long startTime = 0;
    long endTime = 0;

    List<PLYElementDescription> elementDescriptions = null;
    PLYElementDescription elementDescription = null;
    PLYPropertyDescription propertyDescriptor = null;

    if (reader != null) {

      startTime = System.currentTimeMillis();

      // Instanciate line reader
      BufferedReader br = null;
      if (reader instanceof BufferedReader) {
        br = (BufferedReader) reader;
      } else {
        br = new BufferedReader(reader);
      }

      dispatchReadStarted();
      dispatchReadHeaderStarted();

      line = br.readLine();

      if (line != null) {
        lineNumber++;
        if (line.equalsIgnoreCase("PLY")) {
        } else {
          throw new IOException("Cannot read PLY data: at line " + lineNumber + " expected \"ply\" but found " + line);
        }
      } else {
        throw new IOException("Cannot read PLY data: unexpected end of file at line " + lineNumber);
      }

      line = br.readLine();
      if (line != null) {
        lineNumber++;
        if (line.startsWith("format ascii 1.0")) {
          this.fileFormat = PLY.FILE_FORMAT_ASCII;
        } else if (line.startsWith("format binary_little_endian")) {
          this.fileFormat = PLY.FILE_FORMAT_BINARY_LE;
        } else if (line.startsWith("format binary_big_endian")) {
          this.fileFormat = PLY.FILE_FORMAT_BINARY_BE;
        } else {
          throw new IOException("Cannot read PLY data: unknown format \"" + line + "\" at line " + lineNumber);
        }
      } else {
        throw new IOException("Cannot read PLY data: unexpected end of file at line " + lineNumber);
      }

      elementDescriptions = new ArrayList<PLYElementDescription>();

      // Reading header
      headerFinished = false;

      line = br.readLine();

      while ((line != null) && (!headerFinished)) {
        line = line.trim();
        lineNumber++;

        splittedLine = line.split(this.fieldSeparator);

        // Read line is not a comment
        if (!line.startsWith("comment")) {

          // Read an element description
          if (splittedLine[0].equals("element")) {
            if (elementDescription != null) {
              elementDescriptions.add(elementDescription);
              dispatchReadElementDescription(elementDescription);
            }

            try {
              elementDescription = new PLYElementDescription(splittedLine[1], Integer.parseInt(splittedLine[2]));

              if ((FACE_NAME.equalsIgnoreCase(elementDescription.getName())) && (!faceElementOk)) {
                faceElementOk = true;
                faceCount = elementDescription.getElementCount();
              } else if ((VERTEX_NAME.equalsIgnoreCase(elementDescription.getName())) && (!vertexElementOk)) {
                vertexElementOk = true;
                this.vertexCount = elementDescription.getElementCount();
              } else if ((EDGE_NAME.equalsIgnoreCase(elementDescription.getName())) && (!vertexElementOk)) {
                edgeElementOk = true;
                edgeCount = elementDescription.getElementCount();
              }

            } catch (NumberFormatException e) {
              elementDescription = null;
              throw new IOException("Cannot read PLY data: Invalid element declaration \"" + line + "\" at line " + lineNumber, e);
            }

            // Read an element property description
          } else if (splittedLine[0].equals("property")) {
            if (elementDescription != null) {
              if (splittedLine[1].equalsIgnoreCase("list")) {
                propertyDescriptor = new PLYPropertyListDescription(splittedLine[4], PLY.getType(splittedLine[2]), PLY.getType(splittedLine[3]));
                elementDescription.addPropertyDescriptor(propertyDescriptor);
              } else if (splittedLine.length == 3) {
                propertyDescriptor = new PLYPropertyDescription(PLY.getType(splittedLine[1]), splittedLine[2]);
                elementDescription.addPropertyDescriptor(propertyDescriptor);
              } else {
                throw new IOException("Cannot read PLY data: invalid property declaration \"" + line + "\" at line " + lineNumber);
              }
            } else {
              throw new IOException("Cannot read PLY data: property declared outside element \"" + line + "\" at line " + lineNumber);
            }

          } else if (splittedLine[0].equals("end_header")) {
            if (elementDescription != null) {
              elementDescriptions.add(elementDescription);
              dispatchReadElementDescription(elementDescription);
              elementDescription = null;
            }

            if (vertexElementOk && !((faceElementOk && (faceCount > 0)) || (edgeElementOk && (edgeCount > 0)))) {
              this.geometryType = GEOM_POINTS;
            } else if (vertexElementOk && ((faceElementOk && (faceCount > 0)) || (edgeElementOk && (edgeCount > 0)))) {
              this.geometryType = GEOM_POLYHEDRON;
            }

            headerFinished = true;
            noReadLine = true;
          }

          // Reading comment
        } else {

          // Special case if a texture is read
          if (splittedLine.length >= 3) {
            if ("TextureFile".equalsIgnoreCase(splittedLine[1])) {
              String resource = splittedLine[2];

              if (this.textures == null) {
                this.textures = new ArrayList<Texture>();
              }
              this.textures.add(new TexturePath(PathUtil.getFileName(resource), this.textures.size(), resource));
            }
          }
        }

        if (noReadLine) {
          noReadLine = false;
        } else {
          line = br.readLine();
        }
      }

      // Post process header (remove empty elements, ...)
      this.propertyIndexes = processHeader(elementDescriptions);

      dispatchReadHeaderFinished();

      // Reading data with respect to element order
      if ((elementDescriptions != null) && (elementDescriptions.size() > 0)) {

        dispatchReadDataStarted();

        if (getFileFormat() == PLY.FILE_FORMAT_ASCII) {

          line = br.readLine();

          Iterator<PLYElementDescription> descriptorIter = elementDescriptions.iterator();
          int readElementCount = 0;
          while (descriptorIter.hasNext()) {
            elementDescription = descriptorIter.next();
            readElementCount = 0;

            dispatchReadElementsStarted(elementDescription);

            // Reading points
            if (VERTEX_NAME.equalsIgnoreCase(elementDescription.getName())) {
            	
              Point3DContainer<T> points = null;
            	
              if (getPoint3DClass() != null) {
                if (Point3D.class.isAssignableFrom(getPoint3DClass())) {
                  points = JeometryFactory.createPoint3DContainer(this.vertexCount);
                } else if (Point2D.class.isAssignableFrom(getPoint3DClass())) {
                  Jeometry.logger.log(Level.WARNING, "2D point container read from PLY is not yet implemented.");
                  System.err.println("2D point container read from PLY is not yet implemented.");
                  return null;
                }
              } else {
                points = JeometryFactory.createPoint3DContainer(this.vertexCount);
              }
              
              ret = points;
              
              while ((line != null) && (!allElementsRead) && (readElementCount < elementDescription.getElementCount())) {
                line = line.trim();
                lineNumber++;

                splittedLine = line.split(this.fieldSeparator);

                try {
                  point = readPoint3D(splittedLine, this.propertyIndexes);

                  if (accept(point)) {

                    if (isIdentifyPoints()) {
                      
                      if (point instanceof Identified) {
                          if (((Identified)point).getIdentification() == -1) {
                        	  ((Identified)point).setIdentification(readElementCount);
                          }
                      }
                      
                      if (point instanceof Named) {
                    	  ((Named)point).setName(this.pointName);
                      }
                    }

                    points.add(point);
                  }
                  dispatchReadVertex(point);
                  point = null;
                } catch (Exception e) {
                  throw new IOException("Cannot read PLY data: invalid vertex element \"" + line + "\" at line " + lineNumber, e);
                }

                readElementCount++;
                if (noReadLine) {
                  noReadLine = false;
                } else {
                  line = br.readLine();
                }
              }

              // Reading faces
            } else if (FACE_NAME.equalsIgnoreCase(elementDescription.getName())) {
              while ((line != null) && (!allElementsRead) && (readElementCount < elementDescription.getElementCount())) {
                line = line.trim();
                lineNumber++;

                splittedLine = line.split(this.fieldSeparator);

                if (faces == null) {
                  faces = new ArrayList<Face<T>>();
                }

                try {
                  face = readFace(splittedLine, elementDescription);
                  faceCount++;
                  if (face instanceof Triangle) {
                    isTriangleMesh &= true;
                    
                    if (face instanceof Identified) {
                    	((Identified)face).setIdentification(faceCount);
                    }
                    
                    if (face instanceof Named) {
                    	((Named)face).setName(this.faceTriangleName);
                    }

                  } else {
                    isTriangleMesh &= false;
                  }

                  faces.add(face);
                  dispatchReadFace(face);
                } catch (Exception e) {
                  throw new IOException("Cannot read PLY data: invalid face element \"" + line + "\" at line " + lineNumber, e);
                }

                readElementCount++;
                if (noReadLine) {
                  noReadLine = false;
                } else {
                  line = br.readLine();
                }

                splittedLine = null;
              }
            } else {
              while ((line != null) && (!allElementsRead) && (readElementCount < elementDescription.getElementCount())) {
                line = line.trim();
                lineNumber++;

                readElementCount++;
                if (noReadLine) {
                  noReadLine = false;
                } else {
                  line = br.readLine();
                }
              }
            }

            // Check if the read elements are consistents with descriptor
            // declared count
            if (readElementCount != elementDescription.getElementCount()) {
              throw new IOException("Cannot read PLY data: inconsistent " + elementDescription.getName() + " count. Should be "
                  + elementDescription.getElementCount() + " but read " + readElementCount + " \"" + line + "\" at line " + lineNumber);
            }

            dispatchReadElementsFinished(elementDescription);
          }
        } else {

        }

        dispatchReadDataFinished();
        dispatchReadFinished();

      } else {
        throw new IOException("Cannot read PLY data: no descriptors provided (error within PLY header file) \"" + line + "\" at line " + lineNumber);
      }

    } else {
      throw new IOException("Cannot read PLY data: at line " + lineNumber + " expected \"ply\" but found " + line);
    }

    endTime = System.currentTimeMillis();

    if (this.geometryType == GEOM_POINTS) {
      logger.log(Level.INFO, "PLY points file read in " + (endTime - startTime) / 1000.0f + "s");
      return ret;
    } else if (this.geometryType == GEOM_POLYHEDRON) {
      if (isTriangleMesh) {
        mesh = JeometryFactory.createIndexedTriangleMesh((Point3DContainer<T>)ret);
        
        ((IndexedTriangleMesh<T>) mesh).setVerticesSource((Point3DContainer<T>)ret);
        
        if (this.textures != null) {
        	if (mesh instanceof TextureManager) {
        		((TextureManager) mesh).setTextures(this.textures);
        	}
        }
      } else {
        mesh = JeometryFactory.createIndexedMesh();
        
        if (mesh != null) {
        	((IndexedMesh<T>)mesh).setVerticesSource((Point3DContainer<T>)ret);
        } else {
        	logger.log(Level.SEVERE, "Cannot create indexed mesh from geometry factory.");
        }
      }

      if (faces != null) {

        for (int i = 0; i < faces.size(); i++) {
          mesh.addFace(faces.get(i));
        }
      }
      

      mesh.validateIndexes();
      logger.log(Level.INFO, "PLY file read in " + (endTime - startTime) / 1000.0f + "s");

      return mesh;
    } else {
      logger.log(Level.INFO, "PLY file read with no result in " + (endTime - startTime) / 1000.0f + "s");
      return null;
    }
  }

  /**
   * Read a geometric object from an input stream opened onto a PLY file. Due to
   * the data within the PLY file, returned object can be:
   * <ul>
   * <li>{@link Point3DContainer a 3D point container} if the PLY file only contains vertex information;</li>
   * <li>{@link IndexedMesh an indexed mesh} if the PLY file contains vertex and faces information;</li>
   * <li>{@link IndexedTriangleMesh an indexed triangle mesh} if the PLY file contains vertex and faces information and
   * if all faces are triangles;</li>
   * </ul>
   * @param is the input stream to use.
   * @return the IPolyhedron described by the PLY stream provided by the reader.
   * @throws UnsupportedEncodingException if an error occurs.
   * @throws IOException if an error occurs.
   */
  public Object read(InputStream is) throws UnsupportedEncodingException, IOException {
    if (is != null) {
      Reader r = null;
      try {

        is.mark(65536);

        this.faceCurrentIndex = 0;

        PLYFileDescriptor fileDescriptor = readHeader(is);
        if (fileDescriptor.getFileFormat() == PLY.FILE_FORMAT_ASCII) {
          is.reset();
          r = new InputStreamReader(is, this.charset);
          return read(r);
        } else {
          return readBinaryData(is, fileDescriptor);
        }

      } catch (IOException e) {
        throw e;
      } catch (Exception e) {
        throw new IOException("Error reading ply file : " + e.getMessage(), e);
      } finally {
        try {
          r.close();
        } catch (Exception e) {
          r = null;
        }
      }
    } else {
      throw new IOException("Cannot read PLY data to null output stream");
    }
  }

  /**
   * Read a geometric object from the PLY file pointed by the <code>uri</code>
   * given in parameter. Due to the data within the PLY file, returned object
   * can be:
   * <ul>
   * <li>{@link Point3DContainer 3D point container} if the PLY file only contains vertex information;
   * <li>{@link IndexedMesh indexed mesh} if the PLY file contains vertex and faces information;
   * <li>{@link IndexedTriangleMesh triangle mesh} if the PLY file contains vertex and faces information and if all faces are triangles;
   * </ul>
   * @param uri the URI of the input source.
   * @return the geometric object described by the PLY stream provided by the reader.
   * @throws UnsupportedEncodingException if the PLY source provide unsupported encoding.
   * @throws IOException if an error occurs.
   */
  public Object read(String uri) throws UnsupportedEncodingException, IOException {

    Object result = null;

    if (uri != null) {

      if ("ZIP".equalsIgnoreCase(PathUtil.getExtension(uri))) {
        ZipFile zipFile = null;
        Enumeration<? extends ZipEntry> entries = null;
        InputStream stream = null;

        try {
          zipFile = new ZipFile(new File(uri));
          entries = zipFile.entries();

          if (entries != null) {
            stream = zipFile.getInputStream(entries.nextElement());

            result = read(stream);

            try {
              stream.close();
              stream = null;
              zipFile.close();
              zipFile = null;
            } catch (Exception e) {
            }

            return result;

          } else {
            zipFile.close();
            zipFile = null;
            throw new IOException("No entries available within ZIP file " + uri);
          }
        } catch (Exception e) {
          try {
            zipFile.close();
            zipFile = null;
          } catch (IOException e1) {
          }
          throw new IOException("Problem during read from ZIP file " + uri, e);
        }

      } else {

        InputStream is = null;
        is = IOStreamUtil.getBufferedInputStream(uri);

        this.resourcePath = PathUtil.URIToPath(uri);
        return read(is);
      }
    } else {
      logger.log(Level.WARNING, "Cannot read PLY data from " + uri);
      return null;
    }
  }

  /**
   * Read a geometric object from a PLY file. Due to the data within the PLY
   * file, returned object can be:
   * <ul>
   * <li>{@link Point3DContainer 3D point container} if the PLY file only contains vertex information;
   * <li>{@link IndexedMesh indexed mesh} if the PLY file contains vertex and faces information;
   * <li>{@link IndexedTriangleMesh triangle mesh} if the PLY file contains vertex and faces information and if all faces are triangles;
   * </ul>
   * 
   * @param file the PLY file to read (can be a ZIP file that contains the PLY file itself).
   * @return the geometric object described by the PLY stream provided by the reader.
   * @throws UnsupportedEncodingException if the PLY source provide unsupported encoding.
   * @throws IOException if an error occurs.
   */
  public Object read(File file) throws UnsupportedEncodingException, IOException {

    Object result = null;

    if (file != null) {

      if ("ZIP".equalsIgnoreCase(PathUtil.getExtension(file.getPath()))) {
        ZipFile zipFile = null;
        Enumeration<? extends ZipEntry> entries = null;
        InputStream stream = null;

        try {
          zipFile = new ZipFile(file);
          entries = zipFile.entries();

          if (entries != null) {
            stream = zipFile.getInputStream(entries.nextElement());

            result = read(stream);

            try {
              stream.close();
              stream = null;
              zipFile.close();
              zipFile = null;
            } catch (Exception e) {
            }

            return result;

          } else {
            zipFile.close();
            zipFile = null;
            throw new IOException("No entries available within ZIP file " + file.getPath());
          }
        } catch (Exception e) {
          try {
            zipFile.close();
            zipFile = null;
          } catch (IOException e1) {
          }
          throw new IOException("Problem during read from ZIP file " + file.getPath() + ":" + e.getMessage(), e);
        }

      } else {
        
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);) {
          this.resourcePath = PathUtil.URIToPath(file.getPath());
          Object object = read(bis);
          this.resourcePath = null;
          return object;
        } catch (UnsupportedEncodingException e) {
          this.resourcePath = null;
          throw new IOException("Problem during read from file " + file.getPath() + ":" + e.getMessage(), e);
        } catch (IOException e) {
          this.resourcePath = null;
          throw e;
        } finally {
          this.resourcePath = null;
        }
      }
    } else {
      this.resourcePath = null;
      logger.log(Level.WARNING, "Cannot read PLY data from file " + file);
      throw new IOException("Cannot read PLY data from null file");
    }
  }

  /**
   * Attache the given {@link PLYReaderListener listener} to the reader.
   * @param listener the {@link PLYReaderListener listener} to add to the reader.
   * @see #removePLYReaderListener(PLYReaderListener)
   */
  public void addPLYReaderListener(PLYReaderListener listener) {
    if (listener != null) {
      if (this.listeners == null) {
        this.listeners = new HashSet<PLYReaderListener>();
        this.listeners.add(listener);
      } else {
        if (!this.listeners.contains(listener)) {
          this.listeners.add(listener);
        }
      }
    }
  }

  /**
   * Read the description of a PLY file (header) from an {@link java.io.InputStream input stream}.
   * @param stream the {@link java.io.InputStream input stream} that point the PLY file.
   * @return the description of a PLY file (header).
   * @throws IOException if an error occurs.
   */
  public PLYFileDescriptor readHeader(InputStream stream) throws IOException {

    PLYFileDescriptor fileDescriptor = null;

    if (stream != null) {

      fileDescriptor = new PLYFileDescriptor();

      Collection<PLYElementDescription> elementDescriptions = null;
      PLYElementDescription elementDescription = null;

      PLYPropertyDescription propertyDescriptor = null;

      boolean headerFinished = false;

      String[] splittedLine = null;

      boolean vertexElementOk = false;

      boolean faceElementOk = false;

      boolean edgeElementOk = false;

      boolean noReadLine = false;

      boolean vertexXOk = false;
      boolean vertexYOk = false;
      boolean vertexZOk = false;

      this.textures = null;
      this.faceCurrentIndex = 0;

      int faceCount = 0;

      int edgeCount = 0;

      int lineNumber = 0;

      dispatchReadHeaderStarted();

      String line = readLine(stream);

      if (line != null) {
        lineNumber++;
        if (line.equalsIgnoreCase("PLY")) {
        } else {
          throw new IOException("Cannot read PLY data: at line " + lineNumber + " expected \"ply\" but found " + line);
        }
      } else {
        throw new IOException("Cannot read PLY data: unexpected end of file at line " + lineNumber);
      }

      line = readLine(stream);
      if (line != null) {
        lineNumber++;
        if (line.contains("ascii")) {
          fileDescriptor.setFileFormat(PLY.FILE_FORMAT_ASCII);
        } else if (line.contains("binary_little_endian")) {
          fileDescriptor.setFileFormat(PLY.FILE_FORMAT_BINARY_LE);
        } else if (line.contains("binary_big_endian")) {
          fileDescriptor.setFileFormat(PLY.FILE_FORMAT_BINARY_BE);
        } else {
          throw new IOException("Cannot read PLY data: unknown format \"" + line + "\" at line " + lineNumber);
        }
      } else {
        throw new IOException("Cannot read PLY data: unexpected end of file at line " + lineNumber);
      }

      elementDescriptions = new ArrayList<PLYElementDescription>();

      // Reading header
      headerFinished = false;

      line = readLine(stream);
      while ((line != null) && (!headerFinished)) {
        line = line.trim();
        lineNumber++;

        splittedLine = line.split(this.fieldSeparator);

        // Read line is not a comment
        if (!line.startsWith("comment")) {

          // Read an element description
          if (splittedLine[0].equals("element")) {
            if (elementDescription != null) {
              elementDescriptions.add(elementDescription);
              dispatchReadElementDescription(elementDescription);
            }

            try {
              elementDescription = new PLYElementDescription(splittedLine[1], Integer.parseInt(splittedLine[2]));

              if ((FACE_NAME.equalsIgnoreCase(elementDescription.getName())) && (!faceElementOk)) {
                faceElementOk = true;
                faceCount = elementDescription.getElementCount();
                fileDescriptor.setFaceCount(faceCount);
              } else if ((VERTEX_NAME.equalsIgnoreCase(elementDescription.getName())) && (!vertexElementOk)) {
                vertexElementOk = true;
                this.vertexCount = elementDescription.getElementCount();
                fileDescriptor.setVertexCount(this.vertexCount);
              } else if ((EDGE_NAME.equalsIgnoreCase(elementDescription.getName())) && (!vertexElementOk)) {
                edgeElementOk = true;
                edgeCount = elementDescription.getElementCount();
                fileDescriptor.setEdgeCount(edgeCount);
              }

            } catch (NumberFormatException e) {
              elementDescription = null;
              throw new IOException("Cannot read PLY data: Invalid element declaration \"" + line + "\" at line " + lineNumber, e);
            }

            // Read an element property description
          } else if (splittedLine[0].equals("property")) {
            if (elementDescription != null) {
              if (splittedLine[1].equalsIgnoreCase("list")) {
                propertyDescriptor = new PLYPropertyListDescription(splittedLine[4], PLY.getType(splittedLine[2]), PLY.getType(splittedLine[3]));
                elementDescription.addPropertyDescriptor(propertyDescriptor);
              } else if (splittedLine.length == 3) {
                propertyDescriptor = new PLYPropertyDescription(PLY.getType(splittedLine[1]), splittedLine[2]);
                elementDescription.addPropertyDescriptor(propertyDescriptor);

                if (VERTEX_NAME.equalsIgnoreCase(elementDescription.getName())) {
                  if (this.plyXPropName.equalsIgnoreCase(propertyDescriptor.getName())) {
                    vertexXOk = true;
                  } else if (this.plyYPropName.equalsIgnoreCase(propertyDescriptor.getName())) {
                    vertexYOk = true;
                  } else if (this.plyZPropName.equalsIgnoreCase(propertyDescriptor.getName())) {
                    vertexZOk = true;
                  }
                }

              } else {
                throw new IOException("Cannot read PLY data: invalid property declaration \"" + line + "\" at line " + lineNumber);
              }
            } else {
              throw new IOException("Cannot read PLY data: property declared outside element \"" + line + "\" at line " + lineNumber);
            }

          } else if (splittedLine[0].equals("end_header")) {
            if (elementDescription != null) {
              elementDescriptions.add(elementDescription);
              dispatchReadElementDescription(elementDescription);
              elementDescription = null;
            }

            if (vertexElementOk && !((faceElementOk && (faceCount > 0)) || (edgeElementOk && (edgeCount > 0)))) {
              this.geometryType = GEOM_POINTS;
            } else if (vertexElementOk && ((faceElementOk && (faceCount > 0)) || (edgeElementOk && (edgeCount > 0)))) {
              this.geometryType = GEOM_POLYHEDRON;
            }

            headerFinished = true;
            noReadLine = true;
          }

          // Reading comment
        } else {

          // Special case if a texture is read
          if (splittedLine.length >= 3) {
            if ("TextureFile".equalsIgnoreCase(splittedLine[1])) {
              String resource = splittedLine[2];

              if (this.textures == null) {
                this.textures = new ArrayList<Texture>();
              }

              if (!PathUtil.isAbsolutePath(resource) && (this.resourcePath == null)) {
                logger.log(Level.WARNING, "Cannot determine absolute location of texture " + resource);
              } else {
                String str = PathUtil.URIToPath(PathUtil.getDirectory(this.resourcePath));
                str = PathUtil.URIToPath(str + File.separator + resource);
                resource = str;
              }

              this.textures.add(new TexturePath(PathUtil.getFileName(resource), this.textures.size(), resource));
            }
          }
        }

        if (noReadLine) {
          noReadLine = false;
        } else {
          line = readLine(stream);
        }
      }

      // Save the vertex type:
      if ((vertexXOk) && (vertexYOk) && (vertexZOk)) {
        fileDescriptor.setVertexType(PLY.VERTEX_TYPE_3D);
      } else if ((vertexXOk) && (vertexYOk)) {
        fileDescriptor.setVertexType(PLY.VERTEX_TYPE_2D);
      }

      fileDescriptor.setHeaderLineCount(lineNumber);
      fileDescriptor.setElementDescriptors(elementDescriptions);

      // Post process header (remove empty elements, ...)
      this.propertyIndexes = processHeader(elementDescriptions);

      dispatchReadHeaderFinished();
    }

    return fileDescriptor;
  }

  /**
   * Read binary PLY data.
   * @param is the input stream to use
   * @param fileDescriptor the description of the PLY file
   * @return the read object 
   * @throws IOException  if an error occurs
   */
  private Object readBinaryData(InputStream is, PLYFileDescriptor fileDescriptor) throws IOException {

    Point3DContainer<T> points3D = null;

    Point2DContainer points2D = null;
    
    List<Face<T>> faces = null;
    int faceCurrentIndex = -1;
    Face<T> face = null;

    IndexedMesh<T> mesh = null;

    long startTime = 0;
    long endTime = 0;

    boolean allElementsRead = false;

    boolean isTriangleMesh = true;

    BufferedInputStream bis = null;

    dispatchReadDataStarted();

    int lastReadBytes = Integer.MAX_VALUE;

    if (is != null) {

      if (((fileDescriptor.getFileFormat() == PLY.FILE_FORMAT_BINARY_LE) || (fileDescriptor.getFileFormat() == PLY.FILE_FORMAT_BINARY_BE))
          && (fileDescriptor != null)) {

        if (getPoint3DClass() != null) {
          if (Point3D.class.isAssignableFrom(getPoint3DClass())) {
            if (fileDescriptor.getVertexCount() > 0) {
              points3D = JeometryFactory.createPoint3DContainer(fileDescriptor.getVertexCount());
            } else {
              points3D = JeometryFactory.createPoint3DContainer();
            }
          } else if (Point2D.class.isAssignableFrom(getPoint3DClass())) {
            if (fileDescriptor.getVertexCount() > 0) {
            	points2D = JeometryFactory.createPoint2DContainer(fileDescriptor.getVertexCount());
            } else {
            	points2D = JeometryFactory.createPoint2DContainer();
            }
          }
        } else {

          if (fileDescriptor.getVertexType() == PLY.VERTEX_TYPE_3D) {
            if (fileDescriptor.getVertexCount() > 0) {
              points3D = JeometryFactory.createPoint3DContainer(fileDescriptor.getVertexCount());
            } else {
              points3D = JeometryFactory.createPoint3DContainer();
            }
          } else if (fileDescriptor.getVertexType() == PLY.VERTEX_TYPE_2D) {
            if (fileDescriptor.getVertexCount() > 0) {
            	points2D = JeometryFactory.createPoint2DContainer(fileDescriptor.getVertexCount());
            } else {
            	points2D = JeometryFactory.createPoint2DContainer();
            }
          }
        }

        if (is instanceof BufferedInputStream) {
          bis = (BufferedInputStream) is;
        } else {
          bis = new BufferedInputStream(is);
        }

        byte[] bytes = null;

        boolean eof = false;

        Collection<PLYElementDescription> elementDescriptions = fileDescriptor.getElementDescriptors();
        PLYElementDescription elementDescription = null;

        if (elementDescriptions != null) {

          Iterator<PLYElementDescription> descriptorIter = elementDescriptions.iterator();
          int readElementCount = 0;
          while (descriptorIter.hasNext()) {
            elementDescription = descriptorIter.next();
            readElementCount = 0;

            dispatchReadElementsStarted(elementDescription);

            // Reading points
            if (VERTEX_NAME.equalsIgnoreCase(elementDescription.getName())) {

              int elementBinarySize = 0;

              // Compute properties binary size
              if (elementDescription.getPropertyDescriptors() != null) {
                Iterator<PLYPropertyDescription> iter = elementDescription.getPropertyDescriptors().iterator();
                PLYPropertyDescription description = null;
                while (iter.hasNext()) {
                  description = iter.next();

                  if (PLY.getTypeSize(description.getType()) > 0) {
                    elementBinarySize += PLY.getTypeSize(description.getType());
                  } else {
                    throw new IOException("Unknown PLY type " + description.getType());
                  }
                }
              }

              bytes = new byte[elementBinarySize];

              T point3d = null;
              Point2D point2d = null;
              
              eof = (lastReadBytes <= 0);
              while ((!eof) && (!allElementsRead) && (readElementCount < elementDescription.getElementCount())) {

                lastReadBytes = bis.read(bytes);
                
                if (fileDescriptor.getVertexType() == PLY.VERTEX_TYPE_3D) {
                    point3d = readPoint3D(bytes, elementDescription, fileDescriptor);

                    if (accept(point3d)) {

                      if (isIdentifyPoints()) {
                    	  
                    	if (point3d instanceof Identified) {
                    		((Identified)point3d).setIdentification(readElementCount);
                    	}
                    	  
                    	if (point3d instanceof Named) {
                    		((Named)point3d).setName(this.pointName);
                    	}

                      }
                    	
                      points3D.add(point3d);
                } else if ((fileDescriptor.getVertexType() == PLY.VERTEX_TYPE_2D)) {
                	point2d = readPoint2D(bytes, elementDescription, fileDescriptor);
                	
                	if (accept(point2d)) {
                		if (isIdentifyPoints()) {
                      	  
                        	if (point2d instanceof Identified) {
                        		((Identified)point2d).setIdentification(readElementCount);
                        	}
                        	  
                        	if (point2d instanceof Named) {
                        		((Named)point2d).setName(this.pointName);
                        	}

                          }
                	}
                	
                }
                

                }
                dispatchReadVertex(point3d);
                point3d = null;
                point2d = null;
                
                readElementCount++;

              }

              // Reading faces
            } else if (FACE_NAME.equalsIgnoreCase(elementDescription.getName())) {

              // Get the list property count field binary size
              if ((elementDescription.getPropertyDescriptors() != null) && (elementDescription.getPropertyDescriptors().size() > 0)) {

                PLYPropertyDescription description = elementDescription.getPropertyDescriptors().get(0);

                if (description instanceof PLYPropertyListDescription) {

                  readElementCount = 0;

                  faces = new ArrayList<Face<T>>(elementDescription.getElementCount());

                  eof = (lastReadBytes < 0);
                  while ((!eof) && (!allElementsRead) && (readElementCount < elementDescription.getElementCount())) {

                    face = readFace(bis, elementDescription, fileDescriptor);

                    if (face != null) {
                      faceCurrentIndex = faceCurrentIndex + 1;
                      faces.set(faceCurrentIndex, face);
                    } else {
                      logger.log(Level.INFO, "Cannot read face.");
                    }
                    dispatchReadFace(face);

                    readElementCount++;
                  }

                } else {
                  throw new IOException("Unknown PLY type " + description.getType() + " as a list type is expected for faces description");
                }
              }
            } else {

            }

            // Check if the read elements are consistents with descriptor
            // declared count
            if (readElementCount != elementDescription.getElementCount()) {
              throw new IOException("Cannot read PLY data: inconsistent " + elementDescription.getName() + " count. Should be "
                  + elementDescription.getElementCount() + " but read " + readElementCount);
            }

            dispatchReadElementsFinished(elementDescription);
          }
        }
      } else {
        throw new IOException("Cannot read PLY binary data from non binary file.");
      }
    } else {
      throw new IOException("Cannot read PLY binary data from null input stream.");
    }

    if (this.geometryType == GEOM_POINTS) {
    	
      if (fileDescriptor.getVertexType() == PLY.VERTEX_TYPE_3D) {
          logger.log(Level.FINE, "PLY 3D points file read in " + (endTime - startTime) / 1000.0f + "s");
          dispatchReadDataFinished();
          return points3D;
      } else if (fileDescriptor.getVertexType() == PLY.VERTEX_TYPE_2D) {
    	  logger.log(Level.FINE, "PLY 2D points file read in " + (endTime - startTime) / 1000.0f + "s");
          dispatchReadDataFinished();
          return points2D;
      } else {
    	  logger.log(Level.FINE, "PLY unknown points file read in " + (endTime - startTime) / 1000.0f + "s");
          dispatchReadDataFinished();
          return null;
      }

    } else if (this.geometryType == GEOM_POLYHEDRON) {

      if (fileDescriptor.getVertexType() == PLY.VERTEX_TYPE_3D) {
       
        if (isTriangleMesh) {
          mesh = JeometryFactory.createIndexedTriangleMesh(points3D);
          
          if (this.textures != null) {
        	  if (mesh instanceof TextureManager) {
            	  ((TextureManager) mesh).setTextures(this.textures);
              } else {
            	  logger.log(Level.WARNING, "PLY file contains "+this.textures.size()+" but output mesh implementation does not support it.");
              }
          }
          
        } else {
          mesh = JeometryFactory.createIndexedMesh();
          if (mesh != null) {
        	  mesh.setVerticesSource(points3D);
          }
        }

        if (faces != null) {
          for (int i = 0; i < faces.size(); i++) {
            mesh.addFace(faces.get(i));
          }

          faces = null;

        }

        mesh.validateIndexes();
        logger.log(Level.INFO, "PLY file read in " + (endTime - startTime) / 1000.0f + "s");
        dispatchReadDataFinished();

        return mesh;
      } else {
        dispatchReadDataFinished();
        throw new IOException("PLY reader cannot handle 2D polygon.");
      }

    } else {
      logger.log(Level.INFO, "PLY file read with no result in " + (endTime - startTime) / 1000.0f + "s");
      dispatchReadDataFinished();
      return null;
    }
  }

  /**
   * Detach the the given {@link PLYReaderListener listener} from the reader.
   * @param listener the {@link PLYReaderListener listener} to detach from the reader.
   * @see #addPLYReaderListener(PLYReaderListener)
   */
  public void removePLYReaderListener(PLYReaderListener listener) {
    if ((listener != null) && (this.listeners != null)) {
      this.listeners.remove(listener);
    }
  }

  /**
   * Check if the file located by the given path is a valid PLY file.
   * 
   * @param uri the path of the file to check.
   * @return <code>true</code> if the file is a valid PLY and <code>false</code> otherwise.
   * @throws IOException if an I/O error occurs.
   * @see #checkValidity(Reader)
   * @see #checkValidity(InputStream)
   */
  public boolean checkValidity(String uri) throws IOException {
    boolean ok = false;
    if (uri != null) {
      BufferedInputStream bis = null;
      bis = IOStreamUtil.getBufferedInputStream(uri);
      ok = checkValidity(bis);
    } else {
      logger.log(Level.WARNING, "Cannot read PLY data from " + uri);
    }
    return ok;
  }

  /**
   * Check if the given {@link java.io.InputStream input stream} is a valid PLY
   * input stream.
   * 
   * @param stream the {@link java.io.InputStream input stream} to check.
   * @return <code>true</code> if the  {@link java.io.InputStream input stream} is a valid PLY input stream and <code>false</code> otherwise.
   * @throws IOException if an I/O error occurs.
   * @see #checkValidity(Reader)
   * @see #checkValidity(String)
   */
  public boolean checkValidity(InputStream stream) throws IOException {
    boolean ok = false;

    if (stream != null) {
      Reader r = null;
      try {
        r = new InputStreamReader(stream, this.charset);
        return checkValidity(r);
      } catch (Exception e) {
        throw new IOException("Error reading file : " + e.getMessage(), e);
      } finally {
        try {
          r.close();
        } catch (Exception e) {
          r = null;
        }
      }
    }

    return ok;
  }

  /**
   * Check if the given {@link java.io.Reader reader} is a valid PLY reader.
   * @param reader the {@link java.io.Reader reader} to check.
   * @return <code>true</code> if the  {@link java.io.Reader reader} is a valid PLY reader and <code>false</code> otherwise.
   * @throws IOException if an I/O error occurs.
   * @see #checkValidity(InputStream)
   * @see #checkValidity(String)
   */
  public boolean checkValidity(Reader reader) throws IOException {
    boolean ok = false;

    if (reader != null) {

      BufferedReader br = null;

      if (reader instanceof BufferedReader) {
        br = (BufferedReader) reader;
      } else {
        // Instanciate line reader
        br = new BufferedReader(reader);
      }

      String line = br.readLine();

      if ("PLY".equalsIgnoreCase(line)) {
        ok = true;
      }

    }

    return ok;
  }

  /**
   * Dispatch a read started.
   */
  protected void dispatchReadStarted() {
    if (this.listeners != null) {
      Iterator<PLYReaderListener> iter = this.listeners.iterator();
      while (iter.hasNext()) {
        iter.next().readStarted();
      }
    }
  }

  /**
   * Dispatch a read finished.
   */
  protected void dispatchReadFinished() {
    if (this.listeners != null) {
      Iterator<PLYReaderListener> iter = this.listeners.iterator();
      while (iter.hasNext()) {
        iter.next().readFinished();
      }
    }
  }

  /**
   * Dispatch a header read started.
   */
  protected void dispatchReadHeaderStarted() {
    if (this.listeners != null) {
      Iterator<PLYReaderListener> iter = this.listeners.iterator();
      while (iter.hasNext()) {
        iter.next().readHeaderStarted();
      }
    }
  }

  /**
   * Dispatch a read header finished.
   */
  protected void dispatchReadHeaderFinished() {
    if (this.listeners != null) {
      Iterator<PLYReaderListener> iter = this.listeners.iterator();
      while (iter.hasNext()) {
        iter.next().readHeaderFinished();
      }
    }
  }

  /**
   * Dispatch an element description read.
   * @param description the element description
   */
  protected void dispatchReadElementDescription(PLYElementDescription description) {
    if (this.listeners != null) {
      Iterator<PLYReaderListener> iter = this.listeners.iterator();
      while (iter.hasNext()) {
        iter.next().readElementDescription(description);
      }
    }
  }

  /**
   * Dispatch data read started.
   */
  protected void dispatchReadDataStarted() {
    if (this.listeners != null) {
      Iterator<PLYReaderListener> iter = this.listeners.iterator();
      while (iter.hasNext()) {
        iter.next().readDataStarted();
      }
    }
  }

  /**
   * Dispatch a data read finished.
   */
  protected void dispatchReadDataFinished() {
    if (this.listeners != null) {
      Iterator<PLYReaderListener> iter = this.listeners.iterator();
      while (iter.hasNext()) {
        iter.next().readDataFinished();
      }
    }
  }

  /**
   * Dispatch an element read started.
   * @param description the element description
   */
  protected void dispatchReadElementsStarted(PLYElementDescription description) {
    if (this.listeners != null) {
      Iterator<PLYReaderListener> iter = this.listeners.iterator();
      while (iter.hasNext()) {
        iter.next().readElementsStarted(description);
      }
    }
  }

  /**
   * Dispatch an element read finished.
   * @param description the element description
   */
  protected void dispatchReadElementsFinished(PLYElementDescription description) {
    if (this.listeners != null) {
      Iterator<PLYReaderListener> iter = this.listeners.iterator();
      while (iter.hasNext()) {
        iter.next().readElementsFinished(description);
      }
    }
  }

  /**
   * Dispatch a vertex read start.
   * @param vertex the read vertex
   */
  protected void dispatchReadVertex(Point3D vertex) {
    if (this.listeners != null) {
      Iterator<PLYReaderListener> iter = this.listeners.iterator();
      while (iter.hasNext()) {
        iter.next().readVertex(vertex);
      }
    }
  }

  /**
   * Dispatch a face read start.
   * @param face the read face
   */
  protected void dispatchReadFace(Face<T> face) {

	if (this.listeners != null){ Iterator<PLYReaderListener> iter = this.listeners.iterator(); 
	  while(iter.hasNext()){ 
		  iter.next().readFace(face);
	  } 
	}
  }

  /**
   * Process the given header descriptions.
   * @param descriptors the header descriptors
   * @return the properties indices
   */
  private int[] processHeader(Collection<PLYElementDescription> descriptors) {

    int[] propertiesIndex = null;

    if (descriptors != null) {

      propertiesIndex = new int[16];

      Iterator<PLYElementDescription> iter = descriptors.iterator();
      PLYElementDescription descriptor = null;
      while (iter.hasNext()) {
        descriptor = iter.next();

        if (VERTEX_NAME.equalsIgnoreCase(descriptor.getName())) {
          propertiesIndex[X_INDEX] = descriptor.getPropertyIndex(this.plyXPropName);
          propertiesIndex[Y_INDEX] = descriptor.getPropertyIndex(this.plyYPropName);
          propertiesIndex[Z_INDEX] = descriptor.getPropertyIndex(this.plyZPropName);

          propertiesIndex[R_INDEX] = descriptor.getPropertyIndexContains(this.plyRPropName);
          propertiesIndex[G_INDEX] = descriptor.getPropertyIndexContains(this.plyGPropName);
          propertiesIndex[B_INDEX] = descriptor.getPropertyIndexContains(this.plyBPropName);
          propertiesIndex[A_INDEX] = descriptor.getPropertyIndexContains(this.plyAPropName);

          propertiesIndex[NX_INDEX] = descriptor.getPropertyIndex(this.plyNxPropName);
          propertiesIndex[NY_INDEX] = descriptor.getPropertyIndex(this.plyNyPropName);
          propertiesIndex[NZ_INDEX] = descriptor.getPropertyIndex(this.plyNzPropName);

          propertiesIndex[TEX_U_INDEX] = descriptor.getPropertyIndex(this.plyVertexTexCoordUPropName);
          propertiesIndex[TEX_V_INDEX] = descriptor.getPropertyIndex(this.plyVertexTexCoordVPropName);
        } else if (FACE_NAME.equals(descriptor.getName())) {

          propertiesIndex[VERTEX_LIST_INDEX] = descriptor.getPropertyIndex(this.plyVertexListPropName);

          propertiesIndex[TEX_INDEX] = descriptor.getPropertyIndex(this.plyTexIndexPropName);
          propertiesIndex[TEX_COORDS_INDEX] = descriptor.getPropertyIndex(this.plyTexCoordPropName);
        }

        if (descriptor.getElementCount() < 1) {
          iter.remove();
        }
      }
    }

    return propertiesIndex;
  }

  /**
   * Read a point3D.
   * @param values the values
   * @param propertyIndexes the properties indices
   * @return the read point
   * @throws IOException if an error occurs
   */
  @SuppressWarnings("unchecked")
  private T readPoint3D(String[] values, int[] propertyIndexes) throws IOException {
    T pt = null;

    if (getPoint3DClass() != null) {
      try {
        pt = (T)getPoint3DClass().getDeclaredConstructor().newInstance();

        if (pt instanceof Point3D) {
          ((Point3D) pt).setX(Double.parseDouble(values[propertyIndexes[X_INDEX]]));
          ((Point3D) pt).setY(Double.parseDouble(values[propertyIndexes[Y_INDEX]]));
          ((Point3D) pt).setZ(Double.parseDouble(values[propertyIndexes[Z_INDEX]]));
        } else if (pt instanceof Point2D) {
          ((Point2D) pt).setX(Double.parseDouble(values[propertyIndexes[X_INDEX]]));
          ((Point2D) pt).setY(Double.parseDouble(values[propertyIndexes[Y_INDEX]]));
        } else {
          Jeometry.logger.log(Level.WARNING, "Instanciated point class is not a 3D/2D point.");
          return null;
        }

      } catch (InstantiationException e) {
        throw new IOException("Declared point class " + getPoint3DClass().getSimpleName() + " cannot be instanciated", e);
      } catch (IllegalAccessException e) {
        throw new IOException("Declared point class " + getPoint3DClass().getSimpleName() + " cannot be instanciated", e);
      } catch (Exception e) {
        throw new IOException("Declared point class " + getPoint3DClass().getSimpleName() + " cannot be instanciated", e);
      }
    } else if ((propertyIndexes[X_INDEX] != -1) && (propertyIndexes[Y_INDEX] != -1) && (propertyIndexes[Z_INDEX] != -1)) {
      try {
        pt = (T)JeometryFactory.createPoint3D(Double.parseDouble(values[propertyIndexes[X_INDEX]]), 
                                           Double.parseDouble(values[propertyIndexes[Y_INDEX]]),
                                           Double.parseDouble(values[propertyIndexes[Z_INDEX]]));
 
      } catch (Exception e) {
        throw new IOException("Cannot instanciate 3D point: " + e.getMessage(), e);
      }
    } else {
      try {
        pt = (T)JeometryFactory.createPoint3D(Double.parseDouble(values[propertyIndexes[X_INDEX]]), 
                                           Double.parseDouble(values[propertyIndexes[Y_INDEX]]),
                                           Double.parseDouble(values[propertyIndexes[Z_INDEX]]));
      } catch (Exception e) {
        throw new IOException("Cannot instanciate 3D point from GeometryFactory: " + e.getMessage(), e);
      }
    }

    if (pt != null) {

      // Set point normal
      if (isReadPointNormal() && (propertyIndexes[NX_INDEX] != -1) &&  (propertyIndexes[NY_INDEX] != -1) &  (propertyIndexes[NZ_INDEX] != -1)) {
        if (pt instanceof HasNormal) {
          ((HasNormal)pt).setNormal(JeometryFactory.createPoint3D(Double.parseDouble(values[propertyIndexes[NX_INDEX]]), 
                                                                  Double.parseDouble(values[propertyIndexes[NY_INDEX]]),
                                                                  Double.parseDouble(values[propertyIndexes[NZ_INDEX]])));
        } else {
          Jeometry.logger.log(Level.WARNING, "Ignoring point normal as class "+pt.getClass().getSimpleName()+" does not implements HasNormal"); 
        }
      }
      
      // Set Pt Color
      if ((pt instanceof Colored) && isReadPointColor() && (propertyIndexes[R_INDEX] != -1) && (propertyIndexes[G_INDEX] != -1)
          && (propertyIndexes[B_INDEX] != -1)) {
        try {
          int r = Integer.parseInt(values[propertyIndexes[R_INDEX]]);
          int g = Integer.parseInt(values[propertyIndexes[G_INDEX]]);
          int b = Integer.parseInt(values[propertyIndexes[B_INDEX]]);

          Color c = new Color(r, g, b);

          ((Colored) pt).setColor(c);
        } catch (Exception e) {
          throw new IOException("Cannot set 3D point color: " + e.getMessage(), e);
        }
      }
    }

    return pt;

  }

  /**
   * Read a 3D point.
   * @param bytes the bytes to read
   * @param description the description
   * @param fileDescriptor the file descriptor
   * @return the read point
   * @throws IOException if an error occurs
   */
  @SuppressWarnings("unchecked")
  private T readPoint3D(byte[] bytes, PLYElementDescription description, PLYFileDescriptor fileDescriptor) throws IOException {

    T pt = null;

    int offset = 0;

    int rgbR = -1;
    int rgbG = -1;
    int rgbB = -1;
    int rgbA = -1;

    Point3D normal = null;

    if (getPoint3DClass() != null) {
      try {
        pt = (T)getPoint3DClass().getDeclaredConstructor().newInstance();
      } catch (InstantiationException e) {
        throw new IOException("Declared point class " + getPoint3DClass().getSimpleName() + " cannot be instanciated", e);
      } catch (IllegalAccessException e) {
        throw new IOException("Declared point class " + getPoint3DClass().getSimpleName() + " cannot be instanciated", e);
      } catch (Exception e) {
        throw new IOException("Declared point class " + getPoint3DClass().getSimpleName() + " cannot be instanciated", e);
      }
    } else {
      if (fileDescriptor.getVertexType() == PLY.VERTEX_TYPE_3D) {
        pt = (T)JeometryFactory.createPoint3D();
      } else if (fileDescriptor.getVertexType() == PLY.VERTEX_TYPE_2D) {
        Jeometry.logger.log(Level.WARNING, "2D point support is not yet implemented.");
        return null;
      } else {
        throw new IOException("Cannot determine vertex type. Vertex type can be either 2D or 3D");
      }
    }

    Iterator<PLYPropertyDescription> iter = description.getPropertyDescriptors().iterator();
    PLYPropertyDescription property = null;
    byte[] tmp = null;
    ByteBuffer buffer = ByteBuffer.wrap(bytes);

    Object value = null;

    while (iter.hasNext()) {
      property = iter.next();

      if (PLY.TYPE_CHAR == property.getType()) {
        tmp = new byte[PLY.TYPE_CHAR_SIZE];
        buffer.get(tmp, offset, PLY.TYPE_CHAR_SIZE);
        value = readChar(tmp);
      } else if (PLY.TYPE_UCHAR == property.getType()) {
        tmp = new byte[PLY.TYPE_UCHAR_SIZE];
        buffer.get(tmp, offset, PLY.TYPE_UCHAR_SIZE);
        value = readUChar(tmp);
      } else if (PLY.TYPE_SHORT == property.getType()) {
        tmp = new byte[PLY.TYPE_SHORT_SIZE];
        buffer.get(tmp, offset, PLY.TYPE_SHORT_SIZE);
        value = readShort(tmp, fileDescriptor.getFileFormat());
      } else if (PLY.TYPE_USHORT == property.getType()) {
        tmp = new byte[PLY.TYPE_USHORT_SIZE];
        buffer.get(tmp, offset, PLY.TYPE_USHORT_SIZE);
        value = readShort(tmp, fileDescriptor.getFileFormat());
      } else if (PLY.TYPE_INT == property.getType()) {
        tmp = new byte[PLY.TYPE_INT_SIZE];
        buffer.get(tmp, offset, PLY.TYPE_INT_SIZE);
        value = readInt(tmp, fileDescriptor.getFileFormat());
      } else if (PLY.TYPE_UINT == property.getType()) {
        tmp = new byte[PLY.TYPE_UINT_SIZE];
        buffer.get(tmp, offset, PLY.TYPE_UINT_SIZE);
        value = readInt(tmp, fileDescriptor.getFileFormat());
      } else if (PLY.TYPE_FLOAT == property.getType()) {
        tmp = new byte[PLY.TYPE_FLOAT_SIZE];
        buffer.get(tmp, offset, PLY.TYPE_FLOAT_SIZE);
        value = readFloat(tmp, fileDescriptor.getFileFormat());
      } else if (PLY.TYPE_DOUBLE == property.getType()) {
        tmp = new byte[PLY.TYPE_DOUBLE_SIZE];
        buffer.get(tmp, offset, PLY.TYPE_DOUBLE_SIZE);
        value = readDouble(tmp, fileDescriptor.getFileFormat());
      } else {

        if (pt instanceof HandleUserProperties) {
        	((HandleUserProperties)pt).setUserProperty("PLY_" + property.getName(), value);
        }
        
      }

      if (this.plyXPropName.equalsIgnoreCase(property.getName())) {
        if (value instanceof Number) {
          pt.setX(((Number) value).doubleValue());
        } else {
          Jeometry.logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyXPropName + " component");
        }
      } else if (this.plyYPropName.equalsIgnoreCase(property.getName())) {
        if (value instanceof Number) {
          pt.setY(((Number) value).doubleValue());
        } else {
          Jeometry.logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyYPropName + " component");
        }
      } else if (this.plyZPropName.equalsIgnoreCase(property.getName()) && (pt instanceof Point3D)) {
        if (value instanceof Number) {
          pt.setZ(((Number) value).doubleValue());
        } else {
          Jeometry.logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyZPropName + " component");
        }
      } else if (this.plyRPropName.equalsIgnoreCase(property.getName())) {
        rgbR = (int) value;
      } else if (this.plyGPropName.equalsIgnoreCase(property.getName())) {
        rgbG = (int) value;
      } else if (this.plyBPropName.equalsIgnoreCase(property.getName())) {
        rgbB = (int) value;
      } else if (this.plyAPropName.equalsIgnoreCase(property.getName())) {
        rgbA = (int) value;
      } else if (this.plyNxPropName.equalsIgnoreCase(property.getName())) {
        if (normal == null) {
          normal = JeometryFactory.createPoint3D();
        }

        if (value instanceof Number) {
          normal.setX(((Number) value).doubleValue());
        } else {
          logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyNxPropName + " component");
        }
      } else if (this.plyNyPropName.equalsIgnoreCase(property.getName())) {
        if (normal == null) {
          normal = JeometryFactory.createPoint3D();
        }

        if (value instanceof Number) {
          normal.setY(((Number) value).doubleValue());
        } else {
          logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyNyPropName + " component");
        }
      } else if (this.plyNzPropName.equalsIgnoreCase(property.getName())) {
        if (normal == null) {
          normal = JeometryFactory.createPoint3D();
        }

        if (value instanceof Number) {
          normal.setZ(((Number) value).doubleValue());
        } else {
          logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyNzPropName + " component");
        }
      } else if (this.plyIDPropName.equalsIgnoreCase(property.getName())) {

    	if (pt instanceof Identified) {
    		((Identified)pt).setIdentification((int) value);
    	} else if (pt instanceof HandleUserProperties) {
    		((HandleUserProperties)pt).setUserProperty("PLY_" + property.getName(), value);
    	}

      } else if (this.plyVertexTexCoordUPropName.equalsIgnoreCase(property.getName())) {
    	  if (pt instanceof Identified) {
      		((Identified)pt).setIdentification((int) value);
      	} else if (pt instanceof HandleUserProperties) {
      		((HandleUserProperties)pt).setUserProperty("PLY_" + property.getName(), value);
      	}
      } else if (this.plyVertexTexCoordVPropName.equalsIgnoreCase(property.getName())) {
    	  if (pt instanceof Identified) {
      		((Identified)pt).setIdentification((int) value);
      	} else if (pt instanceof HandleUserProperties) {
      		((HandleUserProperties)pt).setUserProperty("PLY_" + property.getName(), value);
      	}
      } else {
        if (pt instanceof HandleUserProperties) {
      		((HandleUserProperties)pt).setUserProperty("PLY_" + property.getName(), value);
      	}
      }

    }

    if (pt instanceof Colored) {
      if ((rgbR != -1) && (rgbG != -1) && (rgbB != -1)) {
        if (rgbA != -1) {
          ((Colored) pt).setColor(new Color(rgbR, rgbG, rgbB, rgbA));
        } else {
          ((Colored) pt).setColor(new Color(rgbR, rgbG, rgbB));
        }
      }
    }

    if (normal != null) {
      if (pt instanceof HasNormal) {
        ((HasNormal) pt).setNormal(normal);
      }
    }

    return pt;
  }

  /**
   * Read a 2D point.
   * @param bytes the bytes to read
   * @param description the description
   * @param fileDescriptor the file descriptor
   * @return the read 2D point
   * @throws IOException if an error occurs
   */
  private Point2D readPoint2D(byte[] bytes, PLYElementDescription description, PLYFileDescriptor fileDescriptor) throws IOException {

	    Point2D pt = null;

	    int offset = 0;

	    int rgbR = -1;
	    int rgbG = -1;
	    int rgbB = -1;
	    int rgbA = -1;

	    Point3D normal = null;

	    if (getPoint2DClass() != null) {
	      try {
	        pt = getPoint2DClass().getDeclaredConstructor().newInstance();
	      } catch (InstantiationException e) {
	        throw new IOException("Declared point class " + getPoint2DClass().getSimpleName() + " cannot be instanciated", e);
	      } catch (IllegalAccessException e) {
	        throw new IOException("Declared point class " + getPoint2DClass().getSimpleName() + " cannot be instanciated", e);
	      } catch (Exception e) {
	        throw new IOException("Declared point class " + getPoint2DClass().getSimpleName() + " cannot be instanciated", e);
	      }
	    } else {
	      Jeometry.logger.log(Level.WARNING, "2D point support is not yet implemented.");
	      return null;
	    }

	    Iterator<PLYPropertyDescription> iter = description.getPropertyDescriptors().iterator();
	    PLYPropertyDescription property = null;
	    byte[] tmp = null;
	    ByteBuffer buffer = ByteBuffer.wrap(bytes);

	    Object value = null;

	    while (iter.hasNext()) {
	      property = iter.next();

	      if (PLY.TYPE_CHAR == property.getType()) {
	        tmp = new byte[PLY.TYPE_CHAR_SIZE];
	        buffer.get(tmp, offset, PLY.TYPE_CHAR_SIZE);
	        value = readChar(tmp);
	      } else if (PLY.TYPE_UCHAR == property.getType()) {
	        tmp = new byte[PLY.TYPE_UCHAR_SIZE];
	        buffer.get(tmp, offset, PLY.TYPE_UCHAR_SIZE);
	        value = readUChar(tmp);
	      } else if (PLY.TYPE_SHORT == property.getType()) {
	        tmp = new byte[PLY.TYPE_SHORT_SIZE];
	        buffer.get(tmp, offset, PLY.TYPE_SHORT_SIZE);
	        value = readShort(tmp, fileDescriptor.getFileFormat());
	      } else if (PLY.TYPE_USHORT == property.getType()) {
	        tmp = new byte[PLY.TYPE_USHORT_SIZE];
	        buffer.get(tmp, offset, PLY.TYPE_USHORT_SIZE);
	        value = readShort(tmp, fileDescriptor.getFileFormat());
	      } else if (PLY.TYPE_INT == property.getType()) {
	        tmp = new byte[PLY.TYPE_INT_SIZE];
	        buffer.get(tmp, offset, PLY.TYPE_INT_SIZE);
	        value = readInt(tmp, fileDescriptor.getFileFormat());
	      } else if (PLY.TYPE_UINT == property.getType()) {
	        tmp = new byte[PLY.TYPE_UINT_SIZE];
	        buffer.get(tmp, offset, PLY.TYPE_UINT_SIZE);
	        value = readInt(tmp, fileDescriptor.getFileFormat());
	      } else if (PLY.TYPE_FLOAT == property.getType()) {
	        tmp = new byte[PLY.TYPE_FLOAT_SIZE];
	        buffer.get(tmp, offset, PLY.TYPE_FLOAT_SIZE);
	        value = readFloat(tmp, fileDescriptor.getFileFormat());
	      } else if (PLY.TYPE_DOUBLE == property.getType()) {
	        tmp = new byte[PLY.TYPE_DOUBLE_SIZE];
	        buffer.get(tmp, offset, PLY.TYPE_DOUBLE_SIZE);
	        value = readDouble(tmp, fileDescriptor.getFileFormat());
	      } else {
	        if (pt instanceof HandleUserProperties) {
	    		((HandleUserProperties)pt).setUserProperty("PLY_" + property.getName(), value);
	    	}  
	      }

	      if (this.plyXPropName.equalsIgnoreCase(property.getName())) {
	        if (value instanceof Number) {
	          pt.setX(((Number) value).doubleValue());
	        } else {
	          Jeometry.logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyXPropName + " component");
	        }
	      } else if (this.plyYPropName.equalsIgnoreCase(property.getName())) {
	        if (value instanceof Number) {
	          pt.setY(((Number) value).doubleValue());
	        } else {
	          Jeometry.logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyYPropName + " component");
	        }
	      } else if (this.plyRPropName.equalsIgnoreCase(property.getName())) {
	        rgbR = (int) value;
	      } else if (this.plyGPropName.equalsIgnoreCase(property.getName())) {
	        rgbG = (int) value;
	      } else if (this.plyBPropName.equalsIgnoreCase(property.getName())) {
	        rgbB = (int) value;
	      } else if (this.plyAPropName.equalsIgnoreCase(property.getName())) {
	        rgbA = (int) value;
	      } else if (this.plyNxPropName.equalsIgnoreCase(property.getName())) {
	        if (normal == null) {
	          normal = JeometryFactory.createPoint3D();
	        }

	        if (value instanceof Number) {
	          normal.setX(((Number) value).doubleValue());
	        } else {
	          logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyNxPropName + " component");
	        }
	      } else if (this.plyNyPropName.equalsIgnoreCase(property.getName())) {
	        if (normal == null) {
	          normal = JeometryFactory.createPoint3D();
	        }

	        if (value instanceof Number) {
	          normal.setY(((Number) value).doubleValue());
	        } else {
	          logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyNyPropName + " component");
	        }
	      } else if (this.plyNzPropName.equalsIgnoreCase(property.getName())) {
	        if (normal == null) {
	          normal = JeometryFactory.createPoint3D();
	        }

	        if (value instanceof Number) {
	          normal.setZ(((Number) value).doubleValue());
	        } else {
	          logger.log(Level.WARNING, "Ignoring unknown numerical type " + value.getClass() + " for " + this.plyNzPropName + " component");
	        }
	      } else if (this.plyIDPropName.equalsIgnoreCase(property.getName())) {
	    	  if (pt instanceof Identified) {
	      		((Identified)pt).setIdentification((int) value);
	      	} else if (pt instanceof HandleUserProperties) {
	      		((HandleUserProperties)pt).setUserProperty("PLY_" + property.getName(), value);
	      	}
	      } else if (this.plyVertexTexCoordUPropName.equalsIgnoreCase(property.getName())) {
	    	  if (pt instanceof Identified) {
	      		((Identified)pt).setIdentification((int) value);
	      	} else if (pt instanceof HandleUserProperties) {
	      		((HandleUserProperties)pt).setUserProperty("PLY_" + property.getName(), value);
	      	}
	      } else if (this.plyVertexTexCoordVPropName.equalsIgnoreCase(property.getName())) {
	    	  if (pt instanceof Identified) {
	      		((Identified)pt).setIdentification((int) value);
	      	} else if (pt instanceof HandleUserProperties) {
	      		((HandleUserProperties)pt).setUserProperty("PLY_" + property.getName(), value);
	      	}
	      } else {
	    	if (pt instanceof HandleUserProperties) {
	      		((HandleUserProperties)pt).setUserProperty("PLY_" + property.getName(), value);
	      	}
	      }

	    }

	    if (pt instanceof Colored) {
	      if ((rgbR != -1) && (rgbG != -1) && (rgbB != -1)) {
	        if (rgbA != -1) {
	          ((Colored) pt).setColor(new Color(rgbR, rgbG, rgbB, rgbA));
	        } else {
	          ((Colored) pt).setColor(new Color(rgbR, rgbG, rgbB));
	        }
	      }
	    }

	    if (normal != null) {
	      if (pt instanceof HasNormal) {
	        ((HasNormal) pt).setNormal(normal);
	      }
	    }

	    return pt;
	  }
  
  /**
   * Read a face.
   * @param values the values to read
   * @param description the description
   * @return the read face
   * @throws IOException if an error occurs
   */
  private Face<T> readFace(String[] values, PLYElementDescription description) throws IOException {
    Face<T> face = null;

    // a face is at least a triangle (3 vertexes) plus a vertex count field
    if (values != null) {

      int[] indexes = null;

      int textureIndex = -1;

      Point2DContainer textCoords = null;

      int currentValueIndex = 0;
      Iterator<PLYPropertyDescription> propIter = description.getPropertyDescriptors().iterator();
      PLYPropertyDescription property = null;
      while (propIter.hasNext()) {
        property = propIter.next();

        // Reading vertex list property
        if ((this.plyVertexListPropName.equalsIgnoreCase(property.getName())) || (this.plyVertexListPropNameAlt.equalsIgnoreCase(property.getName()))) {

          try {
            indexes = new int[Integer.parseInt(values[currentValueIndex])];

            currentValueIndex++;
            for (int i = 1; i <= indexes.length; i++) {
              indexes[i - 1] = Integer.parseInt(values[i]);
              currentValueIndex++;
            }
          } catch (Exception e) {
            throw new IOException("Cannot instanciate face: " + e.getMessage(), e);
          }

          // Reading a texture coordinates
        } else if (this.plyTexCoordPropName.equalsIgnoreCase(property.getName())) {
          int textCoordCount = Integer.parseInt(values[currentValueIndex]);
          textCoords = JeometryFactory.createPoint2DContainer(textCoordCount);
          currentValueIndex++;

          try {

            for (int i = 0; i < textCoordCount; i += 2) {
              textCoords.add(JeometryFactory.createPoint2D(Double.parseDouble(values[currentValueIndex]), Double.parseDouble(values[currentValueIndex + 1])));
              currentValueIndex += 2;
            }

          } catch (Exception e) {
            throw new IOException("Cannot instanciate face texture coordinates: " + e.getMessage(), e);
          }

          // Reading a texture index
        } else if (this.plyTexIndexPropName.equalsIgnoreCase(property.getName())) {
          try {
            textureIndex = Integer.parseInt(values[currentValueIndex]);
            currentValueIndex++;
          } catch (NumberFormatException e) {
            throw new IOException("Cannot read face texture index: " + e.getMessage(), e);
          }
        }
      }

      if (indexes != null) {

        if (indexes.length == 3) {
          face = JeometryFactory.createIndexedTriangle(indexes, null);
        } else {

          if ((textureIndex > -1) || (textCoords != null)) {
            face = JeometryFactory.createTexturedIndexedMeshFace(indexes);
          } else {
            face = JeometryFactory.createIndexedMeshFace(indexes);
          }
        }
      }

      if ((face != null) && (face instanceof Texturable)) {

        if (textCoords != null) {
          ((Texturable) face).setTextureCoodinates(textCoords);
        }

        if ((textureIndex > -1) && (this.textures != null) && (textureIndex < this.textures.size())) {
          ((Texturable) face).setTexture(this.textures.get(textureIndex));
        }
      }

    } else {
      throw new IOException("Cannot instanciate face: not enough indexes");
    }

    return face;
  }

  /**
   * Read a face.
   * @param is the input stream
   * @param description the face description
   * @param fileDescriptor the file descriptor
   * @return the read face
   * @throws IOException if an error occurs
   */
  private Face<T> readFace(InputStream is, PLYElementDescription description, PLYFileDescriptor fileDescriptor) throws IOException {

    Face<T> face = null;

    if (is != null) {
      if (description != null) {

        if (description.getPropertyDescriptors() != null) {

          byte[] bytes = null;

          int lastReadBytes = 0;

          boolean eof = false;

          int faceVerticesCount = 0;

          int offset = 0;

          byte[] tmp = null;

          int[] facesIndices = null;

          int textureCoordinatesCount = 0;

          int textureIndex = 0;

          Point2DContainer textureCoordinates = null;

          Iterator<PLYPropertyDescription> propertyIterator = description.getPropertyDescriptors().iterator();
          PLYPropertyDescription property = null;
          while (propertyIterator.hasNext()) {
            property = propertyIterator.next();

            // Reading face vertex indexes
            if (this.plyVertexListPropName.equals(property.getName()) || (this.plyVertexListPropNameAlt.equalsIgnoreCase(property.getName()))) {

              // Read vertex count for the given face
              if (PLY.TYPE_CHAR == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_CHAR)];
                lastReadBytes = is.read(bytes);

                eof = (lastReadBytes < 0);
                if (!eof) {
                  faceVerticesCount = readChar(bytes);
                }

              } else if (PLY.TYPE_UCHAR == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_UCHAR)];
                lastReadBytes = is.read(bytes);

                eof = (lastReadBytes < 0);
                if (!eof) {
                  faceVerticesCount = readUChar(bytes);
                }
              } else if (PLY.TYPE_SHORT == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_SHORT)];
                lastReadBytes = is.read(bytes);
                eof = (lastReadBytes < 0);
                if (!eof) {
                  faceVerticesCount = readShort(bytes, fileDescriptor.getFileFormat());
                }
              } else if (PLY.TYPE_USHORT == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_USHORT)];
                lastReadBytes = is.read(bytes);
                eof = (lastReadBytes < 0);
                if (!eof) {
                  faceVerticesCount = readShort(bytes, fileDescriptor.getFileFormat());
                }
              } else if (PLY.TYPE_INT == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_INT)];
                lastReadBytes = is.read(bytes);
                eof = (lastReadBytes < 0);
                if (!eof) {
                  faceVerticesCount = readInt(bytes, fileDescriptor.getFileFormat());
                }
              } else if (PLY.TYPE_UINT == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_UINT)];
                lastReadBytes = is.read(bytes);

                eof = (lastReadBytes < 0);
                if (!eof) {
                  faceVerticesCount = readInt(bytes, fileDescriptor.getFileFormat());
                }
              } else {
                throw new IOException(
                    "Unknown PLY count type " + ((PLYPropertyListDescription) property).getCountType() + " for face verteices list description");
              }

              // Read face indices as bytes (this is not include the face vertex
              // count).
              // A buffer is created to aggregate all face related bytes.
              bytes = new byte[getBinaryTypeSize(((PLYPropertyListDescription) property).getValueType()) * faceVerticesCount];
              lastReadBytes = is.read(bytes);
              ByteBuffer buffer = ByteBuffer.wrap(bytes);

              eof = (lastReadBytes < 0);
              if (!eof) {

                int indexTypeSize = getBinaryTypeSize(((PLYPropertyListDescription) property).getValueType());

                facesIndices = new int[faceVerticesCount];

                try {

                  if (PLY.TYPE_CHAR == ((PLYPropertyListDescription) property).getValueType()) {
                    for (int i = 0; i < facesIndices.length; i++) {

                      tmp = new byte[indexTypeSize];
                      buffer.get(tmp, offset, indexTypeSize);

                      facesIndices[i] = readChar(tmp);
                    }
                  } else if (PLY.TYPE_DOUBLE == ((PLYPropertyListDescription) property).getValueType()) {
                    logger.log(Level.WARNING, "Invalid face index type " + ((PLYPropertyListDescription) property).getValueType() + " for faces description " + property.getName());
                    throw new IOException("Invalid face index type " + ((PLYPropertyListDescription) property).getValueType() + " for faces description " + property.getName());
                  } else if (PLY.TYPE_FLOAT == ((PLYPropertyListDescription) property).getValueType()) {
                    logger.log(Level.WARNING, "Invalid face index type " + ((PLYPropertyListDescription) property).getValueType() + " for faces description " + property.getName());
                    throw new IOException("Invalid face index type " + ((PLYPropertyListDescription) property).getValueType() + " for faces description " + property.getName());
                  } else if (PLY.TYPE_INT == ((PLYPropertyListDescription) property).getValueType()) {

                    for (int i = 0; i < facesIndices.length; i++) {

                      tmp = new byte[indexTypeSize];
                      buffer.get(tmp, offset, indexTypeSize);

                      facesIndices[i] = readInt(tmp, fileDescriptor.getFileFormat());
                    }
                  } else if (PLY.TYPE_SHORT == ((PLYPropertyListDescription) property).getValueType()) {
                    for (int i = 0; i < facesIndices.length; i++) {

                      tmp = new byte[indexTypeSize];
                      buffer.get(tmp, offset, indexTypeSize);

                      facesIndices[i] = readShort(tmp, fileDescriptor.getFileFormat());
                    }
                  } else if (PLY.TYPE_UCHAR == ((PLYPropertyListDescription) property).getValueType()) {
                    for (int i = 0; i < facesIndices.length; i++) {

                      tmp = new byte[indexTypeSize];
                      buffer.get(tmp, offset, indexTypeSize);

                      facesIndices[i] = readChar(tmp);
                    }
                  } else if (PLY.TYPE_UINT == ((PLYPropertyListDescription) property).getValueType()) {
                    for (int i = 0; i < facesIndices.length; i++) {

                      tmp = new byte[indexTypeSize];
                      buffer.get(tmp, offset, indexTypeSize);

                      facesIndices[i] = readInt(tmp, fileDescriptor.getFileFormat());
                    }
                  } else if (PLY.TYPE_USHORT == ((PLYPropertyListDescription) property).getValueType()) {
                    for (int i = 0; i < facesIndices.length; i++) {

                      tmp = new byte[indexTypeSize];
                      buffer.get(tmp, offset, indexTypeSize);

                      facesIndices[i] = readShort(tmp, fileDescriptor.getFileFormat());
                    }
                  } else {
                    throw new IOException(
                        "Invalid face index type " + ((PLYPropertyListDescription) property).getValueType() + " for faces description " + property.getName());
                  }

                } catch (Exception e) {
                  throw new IOException("Cannot read face: " + e.getMessage(), e);
                }
              } else {
                throw new IOException("Unexpected end of file while reading face vertex indices");
              }

              // Reading face texture indexes list
            } else if (this.plyTexCoordPropName.equals(property.getName())) {

              // Read texture coordinates count for the given face
              if (PLY.TYPE_CHAR == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_CHAR)];
                lastReadBytes = is.read(bytes);

                eof = (lastReadBytes < 0);
                if (!eof) {
                  textureCoordinatesCount = readChar(bytes);
                }

              } else if (PLY.TYPE_UCHAR == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_UCHAR)];
                lastReadBytes = is.read(bytes);

                eof = (lastReadBytes < 0);
                if (!eof) {
                  textureCoordinatesCount = readUChar(bytes);
                }
              } else if (PLY.TYPE_SHORT == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_SHORT)];
                lastReadBytes = is.read(bytes);
                eof = (lastReadBytes < 0);
                if (!eof) {
                  textureCoordinatesCount = readShort(bytes, fileDescriptor.getFileFormat());
                }
              } else if (PLY.TYPE_USHORT == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_USHORT)];
                lastReadBytes = is.read(bytes);
                eof = (lastReadBytes < 0);
                if (!eof) {
                  textureCoordinatesCount = readShort(bytes, fileDescriptor.getFileFormat());
                }
              } else if (PLY.TYPE_INT == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_INT)];
                lastReadBytes = is.read(bytes);
                eof = (lastReadBytes < 0);
                if (!eof) {
                  textureCoordinatesCount = readInt(bytes, fileDescriptor.getFileFormat());
                }
              } else if (PLY.TYPE_UINT == ((PLYPropertyListDescription) property).getCountType()) {
                bytes = new byte[PLY.getTypeSize(PLY.TYPE_UINT)];
                lastReadBytes = is.read(bytes);

                eof = (lastReadBytes < 0);
                if (!eof) {
                  textureCoordinatesCount = readInt(bytes, fileDescriptor.getFileFormat());
                }
              } else {
                throw new IOException(
                    "Unknown PLY count type " + ((PLYPropertyListDescription) property).getCountType() + " for texture coordinates list description");
              }

              // Reading coordinates
              if (textureCoordinatesCount == 2 * faceVerticesCount) {

                textureCoordinates = JeometryFactory.createPoint2DContainer(textureCoordinatesCount);
                
                double[] values = new double[textureCoordinatesCount];

                for (int i = 0; i < textureCoordinatesCount; i++) {

                  bytes = new byte[PLY.getTypeSize(((PLYPropertyListDescription) property).getValueType())];
                  lastReadBytes = is.read(bytes);

                  eof = (lastReadBytes < 0);
                  if (eof) {
                    throw new IOException("Unexpected end of file while reading texture coordinate.");
                  }

                  if (PLY.TYPE_CHAR == ((PLYPropertyListDescription) property).getValueType()) {
                    values[i] = readChar(bytes);
                  } else if (PLY.TYPE_UCHAR == ((PLYPropertyListDescription) property).getValueType()) {
                    values[i] = readUChar(bytes);
                  } else if (PLY.TYPE_SHORT == ((PLYPropertyListDescription) property).getValueType()) {
                    values[i] = readShort(bytes, fileDescriptor.getFileFormat());
                  } else if (PLY.TYPE_USHORT == ((PLYPropertyListDescription) property).getValueType()) {
                    values[i] = readShort(bytes, fileDescriptor.getFileFormat());
                  } else if (PLY.TYPE_INT == ((PLYPropertyListDescription) property).getValueType()) {
                    values[i] = readInt(bytes, fileDescriptor.getFileFormat());
                  } else if (PLY.TYPE_UINT == ((PLYPropertyListDescription) property).getValueType()) {
                    values[i] = readInt(bytes, fileDescriptor.getFileFormat());
                  } else if (PLY.TYPE_FLOAT == ((PLYPropertyListDescription) property).getValueType()) {
                    values[i] = readFloat(bytes, fileDescriptor.getFileFormat());
                  } else if (PLY.TYPE_DOUBLE == ((PLYPropertyListDescription) property).getValueType()) {
                    values[i] = readDouble(bytes, fileDescriptor.getFileFormat());
                  } else {
                    throw new IOException("Unsupported property value type " + ((PLYPropertyListDescription) property).getValueType());
                  }
                }

                // Converting read values
                Point2D point2D = null;
                for (int i = 0; i < values.length - 1; i = i + 2) {

                  if (isFlipTextureY()) {

                    if (getPoint2DClass() != null) {
                      if (Point2D.class.isAssignableFrom(getPoint2DClass())) {

                        try {
                          point2D = getPoint2DClass().getDeclaredConstructor().newInstance();
                          point2D.setX(values[i]);
                          point2D.setY(1.0f - values[i + 1]);
                          textureCoordinates.add(point2D);
                        } catch (InstantiationException e) {
                          throw new IOException("Declared point class " + getPoint2DClass() + " cannot be instanciated", e);
                        } catch (IllegalAccessException e) {
                          throw new IOException("Declared point class " + getPoint2DClass() + " cannot be instanciated", e);
                        } catch (Exception e) {
                          throw new IOException("Declared point class " + getPoint2DClass() + " cannot be instanciated", e);
                        }
                      }
                    } else {
                      textureCoordinates.add(JeometryFactory.createPoint2D(values[i], 1.0f - values[i + 1]));
                    }
                  } else {

                    if (getPoint2DClass() != null) {
                      if (Point2D.class.isAssignableFrom(getPoint2DClass())) {

                        try {
                          point2D = getPoint2DClass().getDeclaredConstructor().newInstance();
                          point2D.setX(values[i]);
                          point2D.setY(values[i + 1]);
                          textureCoordinates.add(point2D);
                        } catch (InstantiationException e) {
                          throw new IOException("Declared point class " + getPoint2DClass() + " cannot be instanciated", e);
                        } catch (IllegalAccessException e) {
                          throw new IOException("Declared point class " + getPoint2DClass() + " cannot be instanciated", e);
                        } catch (Exception e) {
                          throw new IOException("Declared point class " + getPoint2DClass() + " cannot be instanciated", e);
                        }
                      }
                    } else {
                      textureCoordinates.add(JeometryFactory.createPoint2D(values[i], values[i + 1]));
                    }
                  }

                  point2D = null;
                }

              } else {
                logger.log(Level.WARNING, "Unsupported texture coordinates size " + textureCoordinatesCount + ", expected 2D texture with " + 2 * faceVerticesCount + " coordinates");

                // Cleaning buffer
                for (int i = 0; i < textureCoordinatesCount; i++) {

                  bytes = new byte[PLY.getTypeSize(property.getType())];
                  lastReadBytes = is.read(bytes);

                  eof = (lastReadBytes < 0);
                  if (eof) {
                    throw new IOException("Unexpected end of file while reading texture coordinate.");
                  }
                }
              }
            } else {

              // Skipping unknown property and discarding the involved bytes
              bytes = new byte[PLY.getTypeSize(property.getType())];
              lastReadBytes = is.read(bytes);

              eof = (lastReadBytes < 0);
              if (eof) {
                throw new IOException("Unexpected end of file while reading " + property.getName());
              }
            }

          }

          // Create face with vertex indices         
          if (facesIndices != null) {

            // Read a triangle
            if (facesIndices.length == 3) {
              face = JeometryFactory.createTexturedIndexedTriangle(facesIndices[0], facesIndices[1], facesIndices[2]);
              
              if (face != null) {
            	  
            	  if ((face instanceof Identified)) {
            		  ((Identified)face).setIdentification(this.faceCurrentIndex);
            	  }
            	  
            	  if ((face instanceof Named)) {
            		  ((Named)face).setName(this.faceTriangleName);
            	  }
              }
              
              this.faceCurrentIndex++;
            } else {
              if (textureCoordinates != null) {
                face = JeometryFactory.createTexturedIndexedMeshFace(facesIndices);
                
                if (face != null) {
              	  
              	  if ((face instanceof Identified)) {
              		  ((Identified)face).setIdentification(this.faceCurrentIndex);
              	  }
              	  
              	  if ((face instanceof Named)) {
              		  ((Named)face).setName(this.faceGenericName);
              	  }
                }
                
                this.faceCurrentIndex++;

              } else {
                face = JeometryFactory.createIndexedMeshFace(facesIndices);
                
                if (face != null) {
              	  
              	  if ((face instanceof Identified)) {
              		  ((Identified)face).setIdentification(this.faceCurrentIndex);
              	  }
              	  
              	  if ((face instanceof Named)) {
              		  ((Named)face).setName(this.faceGenericName);
              	  }
                }
                
                this.faceCurrentIndex++;
              }
            }
          } else {
            throw new IOException("Face with no vertex indices is not supported.");
          }

          // Adding texture if available
          if ((face != null) && (face instanceof Texturable) && (textureCoordinates != null)) {
            if (textureCoordinates != null) {
              ((Texturable) face).setTextureCoodinates(textureCoordinates);
              if ((this.textures != null) && (this.textures.size() > 0)) {
                ((Texturable) face).setTexture(this.textures.get(textureIndex));
              }
            }
          }

        } else {
          throw new IOException("No property descriptor available for element " + description.getName());
        }

      } else {
        throw new IOException("No element description available.");
      }
    } else {
      throw new IOException("No input stream available.");
    }

    return face;
  }

  /**
   * Check if the given vector is acceptable.
   * @param point the point to check
   * @return <code>true</code> if the point is acceptable and <code>false</code> otherwise
   */
  private boolean accept(Vector point) {

    // return Geom3D.contains(box, point);
    return true;
  }

  /**
   * Read the first <code>char</code> from the given bytes as a <code>int</code>.
   * @param bytes the bytes to read
   * @return the first <code>char</code> from the given bytes as a <code>int</code>
   */
  private int readChar(byte[] bytes) {
    return bytes[0];
  }

  /**
   * Read the first <code>unsigned char</code> from the given bytes as a <code>int</code>.
   * @param bytes the bytes to read
   * @return the first <code>unsigned char</code> from the given bytes as a <code>int</code>
   */
  private int readUChar(byte[] bytes) {
    return (bytes[0] & 0xFF);
  }

  /**
   * Read the first <code>short</code> from the given bytes as a <code>int</code>.
   * @param bytes the bytes to read
   * @param byteOrder the byte order ({@link PLY#FILE_FORMAT_BINARY_LE Little endian}  or {@link PLY#FILE_FORMAT_BINARY_BE Big endian})
   * @return the first <code>short</code> from the given bytes as a <code>int</code>
   */
  private short readShort(byte[] bytes, int byteOrder) {
    // create a byte buffer and wrap the array
    ByteBuffer bb = ByteBuffer.wrap(bytes);

    // if the file uses little endian as apposed to network
    // (big endian, Java's native) format,
    // then set the byte order of the ByteBuffer
    if (byteOrder == PLY.FILE_FORMAT_BINARY_LE) {
      bb.order(ByteOrder.LITTLE_ENDIAN);
    } else if (byteOrder == PLY.FILE_FORMAT_BINARY_BE) {
      bb.order(ByteOrder.BIG_ENDIAN);
    }

    return bb.getShort();
  }

  /**
   * Read the first <code>int</code> from the given bytes.
   * @param bytes the bytes to read
   * @param byteOrder the byte order ({@link PLY#FILE_FORMAT_BINARY_LE Little endian}  or {@link PLY#FILE_FORMAT_BINARY_BE Big endian})
   * @return the first <code>int</code> from the given bytes
   */
  private int readInt(byte[] bytes, int byteOrder) {
    // create a byte buffer and wrap the array
    ByteBuffer bb = ByteBuffer.wrap(bytes);

    // if the file uses little endian as apposed to network
    // (big endian, Java's native) format,
    // then set the byte order of the ByteBuffer
    if (byteOrder == PLY.FILE_FORMAT_BINARY_LE) {
      bb.order(ByteOrder.LITTLE_ENDIAN);
    } else if (byteOrder == PLY.FILE_FORMAT_BINARY_BE) {
      bb.order(ByteOrder.BIG_ENDIAN);
    }

    return bb.getInt();
  }

  /**
   * Read the first <code>float</code> from the given bytes.
   * @param bytes the bytes to read
   * @param byteOrder the byte order ({@link PLY#FILE_FORMAT_BINARY_LE Little endian}  or {@link PLY#FILE_FORMAT_BINARY_BE Big endian})
   * @return the first <code>float</code> from the given bytes
   */
  private float readFloat(byte[] bytes, int byteOrder) {

    // create a byte buffer and wrap the array
    ByteBuffer bb = ByteBuffer.wrap(bytes);

    // if the file uses little endian as apposed to network
    // (big endian, Java's native) format,
    // then set the byte order of the ByteBuffer
    if (byteOrder == PLY.FILE_FORMAT_BINARY_LE) {
      bb.order(ByteOrder.LITTLE_ENDIAN);
    } else if (byteOrder == PLY.FILE_FORMAT_BINARY_BE) {
      bb.order(ByteOrder.BIG_ENDIAN);
    }

    return bb.getFloat();
  }

  /**
   * Read the first <code>double</code> from the given bytes.
   * @param bytes the bytes to read
   * @param byteOrder the byte order ({@link PLY#FILE_FORMAT_BINARY_LE Little endian}  or {@link PLY#FILE_FORMAT_BINARY_BE Big endian})
   * @return the first <code>double</code> from the given bytes
   */
  private double readDouble(byte[] bytes, int byteOrder) {
    // create a byte buffer and wrap the array
    ByteBuffer bb = ByteBuffer.wrap(bytes);

    // if the file uses little endian as apposed to network
    // (big endian, Java's native) format,
    // then set the byte order of the ByteBuffer
    if (byteOrder == PLY.FILE_FORMAT_BINARY_LE) {
      bb.order(ByteOrder.LITTLE_ENDIAN);
    } else if (byteOrder == PLY.FILE_FORMAT_BINARY_BE) {
      bb.order(ByteOrder.BIG_ENDIAN);
    }

    return bb.getDouble();
  }

  /**
   * Read a line from the given input stream.
   * @param is the input stream
   * @return the read line
   * @throws IOException if an error occurs
   */
  private String readLine(InputStream is) throws IOException {

    if (is != null) {

      int readInt = is.read();
      char character = 0;

      StringBuffer buffer = new StringBuffer();

      boolean eol = false;

      while ((readInt != -1) && (!eol)) {
        character = (char) readInt;
        if (character == '\r') {
          // eol = true;
          readInt = is.read();
        } else if (character == '\n') {
          eol = true;
        } else {
          buffer.append(character);
          readInt = is.read();
        }
      }

      return buffer.toString();

    }

    return null;
  }

  /**
   * Get the default name applied to vertex if needed.
   * @return the default name applied to vertex if needed.
   * @see #setDefaultPointName(String)
   */
  public String getDefaultPointName() {
	  return this.pointName;
  }
  
  /**
   * Set the default name applied to vertex if needed.
   * @param name the default name applied to vertex if needed.
   * @see #getDefaultPointName()
   */
  public void setDefaultPointName(String name) {
	  this.pointName = name;
  }
  
  /**
   * Get the binary size of the given type.
   * 
   * @param plyType
   *          the type.
   * @return the binary size of the type.
   */
  public int getBinaryTypeSize(int plyType) {
    if (PLY.TYPE_CHAR == plyType) {
      return PLY.TYPE_CHAR_SIZE;
    } else if (PLY.TYPE_DOUBLE == plyType) {
      return PLY.TYPE_DOUBLE_SIZE;
    } else if (PLY.TYPE_FLOAT == plyType) {
      return PLY.TYPE_FLOAT_SIZE;
    } else if (PLY.TYPE_INT == plyType) {
      return PLY.TYPE_INT_SIZE;
    } else if (PLY.TYPE_SHORT == plyType) {
      return PLY.TYPE_SHORT_SIZE;
    } else if (PLY.TYPE_UCHAR == plyType) {
      return PLY.TYPE_UCHAR_SIZE;
    } else if (PLY.TYPE_UINT == plyType) {
      return PLY.TYPE_UINT_SIZE;
    } else if (PLY.TYPE_USHORT == plyType) {
      return PLY.TYPE_USHORT_SIZE;
    }

    return -1;

  }
}
