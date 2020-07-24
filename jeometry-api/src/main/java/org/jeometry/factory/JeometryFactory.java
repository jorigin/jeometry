package org.jeometry.factory;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.mesh.indexed.IndexedTriangleMesh;
import org.jeometry.geom3D.neighbor.MeshNeighborhood;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Box;
import org.jeometry.geom3D.primitive.Plane;
import org.jeometry.geom3D.primitive.Triangle;
import org.jeometry.geom3D.primitive.indexed.IndexedTetrahedron;
import org.jeometry.geom3D.primitive.indexed.IndexedTriangle;
import org.jeometry.geom3D.textured.Texturable;
import org.jeometry.geom3D.transform.Transform3DMatrix;
import org.jeometry.geom3D.transform.Transform3DQuaternion;
import org.jeometry.math.Matrix;
import org.jeometry.math.Quaternion;
import org.jeometry.math.Vector;
import org.jeometry.math.decomposition.CholeskyDecomposition;
import org.jeometry.math.decomposition.EigenDecomposition;
import org.jeometry.math.decomposition.LUDecomposition;
import org.jeometry.math.decomposition.QRDecomposition;
import org.jeometry.math.decomposition.SVDDecomposition;
import org.jeometry.math.solver.Solver;

/**
 * A factory dedicated to the creation of geometric instances.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class JeometryFactory {

	/**
	 * A system property that enables to set the global implementation to use.
	 * The value of this property is used to locate the initialization class for the given implementation.<br><br>
     * 
	 * For example, if the Java application is launched with <code>-D{@value #JEOMETRY_IMPLEMENTATION_PROPERTY}=val</code>, Jeometry will:
	 * <ul>
	 * <li>Load the class <code>org.jeometry.<b>val</b>.ImplemenationInit</code> (where <code>val</code> value is lower cased)
	 * <li>Call the static method <code>void initJeometryImplementation()</code> that have to provide the registering of the builders
	 * </ul>
	 * <br>
	 * By default, Jeometry use the <code>simple</code> implementation that will provide simple implementations of all the API interfaces..
	 */
	public static final String JEOMETRY_IMPLEMENTATION_PROPERTY = "org.jeometry.math.implementation";
	
	/**
	 * The Initialization class of a Jeometry implementation. 
	 */
	public static final String JEOMETRY_IMPLEMENTATION_INIT_CLASS  = "JeometryImplementation";
	
	/**
	 * The static initialization method contained within the Jeometry {@link #JEOMETRY_IMPLEMENTATION_INIT_CLASS implementation initialization class}
	 */
	public static final String JEOMETRY_IMPLEMENTATION_INIT_METHOD = "initJeometryImplementation";
	
	private static MathBuilder mathBuilder = null;

	private static MeshBuilder meshBuilder = null;

	private static PointBuilder pointBuilder = null;

	private static TransformBuilder transformBuilder = null;
	
	static {init();}

	private  static void init(){
		
		boolean initialized = false;
		
		// Check if a specific implementation is set
		String implementation = System.getProperty(JEOMETRY_IMPLEMENTATION_PROPERTY);
		
		if (implementation == null) {
			implementation = "simple";
		}
		
		// Invoke the initialization methid
		String initClassName = "org.jeometry."+implementation.toLowerCase()+"."+JEOMETRY_IMPLEMENTATION_INIT_CLASS;
		Class<?> initClass = null;
		Method initMethod  = null;
		
		try {
			initClass = Class.forName(initClassName);
			
			initMethod = initClass.getMethod(JEOMETRY_IMPLEMENTATION_INIT_METHOD);
			
			initMethod.invoke(null);
			
			initialized = true;
			
			Jeometry.logger.log(Level.CONFIG, "Using "+implementation+" implementation.");			
		} catch (ClassNotFoundException e) {
			Jeometry.logger.log(Level.SEVERE, "Cannot find \""+implementation+"\" implementation initialization class "+initClassName+": "+e.getMessage(), e);
			initMethod = null;
		} catch (NoSuchMethodException e) {
			Jeometry.logger.log(Level.SEVERE, "Cannot find \""+implementation+"\" implementation initialization method public static "+JEOMETRY_IMPLEMENTATION_INIT_METHOD+"(): "+e.getMessage(), e);
		} catch (SecurityException e) {
			Jeometry.logger.log(Level.SEVERE, "Cannot access \""+implementation+"\" implementation initialization class "+initClassName+": "+e.getMessage(), e);
		} catch (IllegalAccessException e) {
			Jeometry.logger.log(Level.SEVERE, "Cannot access \""+implementation+"\" implementation initialization class "+initClassName+": "+e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			Jeometry.logger.log(Level.SEVERE, "Cannot execute \""+implementation+"\" implementation initialization method public static "+JEOMETRY_IMPLEMENTATION_INIT_METHOD+"(): "+e.getMessage(), e);
		} catch (InvocationTargetException e) {
			Jeometry.logger.log(Level.SEVERE, "Cannot invoke \""+implementation+"\" implementation initialization method public static "+JEOMETRY_IMPLEMENTATION_INIT_METHOD+"(): "+e.getMessage(), e);
		}
		
		if ((!initialized) && (!"SIMPLE".equalsIgnoreCase(implementation))) {
			Jeometry.logger.log(Level.SEVERE, "Implementation \""+implementation+"\" is not available. Using simple implementation.");
		
			initClassName = "org.geometry."+implementation.toLowerCase()+".ImplementationInit";
			initClass = null;
			initMethod  = null;
			
			try {
				initClass = Class.forName(initClassName);
				
				initMethod = initClass.getMethod(JEOMETRY_IMPLEMENTATION_INIT_METHOD, new Class<?>[] {null});
				
				initMethod.invoke(null, new Object[] {null});
				
				initialized = true;
				
				Jeometry.logger.log(Level.CONFIG, "Using "+implementation+" implementation.");			
			} catch (ClassNotFoundException e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot find \""+implementation+"\" implementation initialization class "+initClassName+": "+e.getMessage(), e);
				initMethod = null;
			} catch (NoSuchMethodException e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot find \""+implementation+"\" implementation initialization method public static "+JEOMETRY_IMPLEMENTATION_INIT_METHOD+"(): "+e.getMessage(), e);
			} catch (SecurityException e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot access \""+implementation+"\" implementation initialization class "+initClassName+": "+e.getMessage(), e);
			} catch (IllegalAccessException e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot access \""+implementation+"\" implementation initialization class "+initClassName+": "+e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot execute \""+implementation+"\" implementation initialization method public static "+JEOMETRY_IMPLEMENTATION_INIT_METHOD+"(): "+e.getMessage(), e);
			} catch (InvocationTargetException e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot invoke \""+implementation+"\" implementation initialization method public static "+JEOMETRY_IMPLEMENTATION_INIT_METHOD+"(): "+e.getMessage(), e);
			}
		    
			if (!initialized) {
				Jeometry.logger.log(Level.SEVERE, "Cannot find \""+implementation+"\" implementation, jeometry-simple.jar may not be accessible.");
			}
		}
	}
	
	/**
	 * Get the {@link MathBuilder math builder} that is used by this factory.
	 * @return the {@link MathBuilder math builder} that is used by this factory
	 * @see #setMathBuilder(MathBuilder)
	 */
	public static MathBuilder getMathBuilder() {
		return mathBuilder;
	}

	/**
	 * Set the {@link MathBuilder math builder} that this factory has to use.
	 * @param builder  the {@link MathBuilder math builder} that this factory has to use
	 * @see #getMathBuilder()
	 */
	public static void setMathBuilder(MathBuilder builder) {
		mathBuilder = builder;
	}

	/**
	 * Get the {@link MeshBuilder mesh builder} that is used by this factory.
	 * @return the {@link MeshBuilder mesh builder} that is used by this factory
	 * @see #setMeshBuilder(MeshBuilder)
	 */
	public static MeshBuilder getMeshBuilder() {
		return meshBuilder;
	}

	/**
	 * Set the {@link MeshBuilder mesh builder} that this factory has to use.
	 * @param builder  the {@link MeshBuilder mesh builder} that this factory has to use
	 * @see #getMeshBuilder()
	 */
	public static void setMeshBuilder(MeshBuilder builder) {
		meshBuilder = builder;
	}
	
	/**
	 * Get the {@link PointBuilder point builder} that is used by this factory.
	 * @return the {@link PointBuilder point builder} that is used by this factory
	 * @see #setPointBuilder(PointBuilder)
	 */
	public static PointBuilder getPointBuilder() {
		return pointBuilder;
	}

	/**
	 * Set the {@link PointBuilder point builder} that this factory has to use.
	 * @param builder  the {@link PointBuilder point builder} that this factory has to use
	 * @see #getPointBuilder()
	 */
	public static void setPointBuilder(PointBuilder builder) {
		pointBuilder = builder;
	}
	
	/**
	 * Get the {@link TransformBuilder transform builder} that is used by this factory.
	 * @return the {@link TransformBuilder transform builder} that is used by this factory
	 * @see #setTransformBuilder(TransformBuilder)
	 */
	public static TransformBuilder getTransformBuilder() {
		return transformBuilder;
	}

	/**
	 * Set the {@link TransformBuilder transform builder} that this factory has to use.
	 * @param builder  the {@link TransformBuilder transform builder} that this factory has to use
	 * @see #getTransformBuilder()
	 */
	public static void setTransformBuilder(TransformBuilder builder) {
		transformBuilder = builder;
	}

	
	/**
	 * Create a new {@link Vector} using the underlying {@link #getMathBuilder() math builder}.
	 * @param size the size of the vector
	 * @return a new {@link Vector} using the underlying {@link #getMathBuilder() math builder}
	 * @throws IllegalStateException if the {@link #getMathBuilder() math builder} is <code>null</code>
	 */
	public static Vector createVector(int size) {
		if (mathBuilder != null) {
			return mathBuilder.createVector(size);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}

	/**
	 * Create a new {@link Vector} using the underlying {@link #getMathBuilder() math builder}.
	 * @param components the components of the vector
	 * @return a new {@link Vector} with the given <code>size</code>
	 * @throws IllegalStateException if the {@link #getMathBuilder() math builder} is <code>null</code>
	 */
	public static Vector createVector(double[] components) {
		if (mathBuilder != null) {
			return mathBuilder.createVector(components);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}

	/**
	 * Create a new {@link Vector} using the underlying {@link #getMathBuilder() math builder}.
	 * @param source the vector to copy
	 * @return a new {@link Vector} using the underlying {@link #getMathBuilder() math builder}
	 */
	public static Vector createVector(Vector source) {
		if (mathBuilder != null) {
			return mathBuilder.createVector(source);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}
	
	/**
	 * Create a new instance of {@link Matrix} using the underlying {@link #getMathBuilder() math builder}.
	 * @param rows the number of rows of the matrix
	 * @param cols the number of columns of the matrix
	 * @return a new instance of {@link Matrix} using the underlying {@link #getMathBuilder() math builder}
	 * @throws IllegalStateException if the {@link #getMathBuilder() math builder} is <code>null</code>
	 * @throws IllegalArgumentException if an instantiation error occurs.
	 */
	public static Matrix createMatrix(int rows, int cols){
		if (mathBuilder != null) {
			return mathBuilder.createMatrix(rows, cols);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Matrix} using the underlying {@link #getMathBuilder() math builder}.
	 * @param data the values of the matrix
	 * @return a new instance of {@link Matrix} using the underlying {@link #getMathBuilder() math builder}
	 * @throws IllegalStateException if the {@link #getMathBuilder() math builder} is <code>null</code>
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public static Matrix createMatrix(double[][] data){
		if (mathBuilder != null) {
			return mathBuilder.createMatrix(data);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Matrix} using the underlying {@link #getMathBuilder() math builder}.
	 * @param rows the number of rows of the matrix
	 * @param columns the number of columns of the matrix
	 * @param data the data of the matrix within an single dimensional array
	 * @param ordering the ordering of the data in order to be processed (can be either {@link Matrix#ROW_MAJOR} or {@link Matrix#COLUMN_MAJOR})
	 * @return a new instance of {@link Matrix}using the underlying {@link #getMathBuilder() math builder}
	 * @throws IllegalStateException if the {@link #getMathBuilder() math builder} is <code>null</code>
	 */
	public static Matrix createMatrix(int rows, int columns, double[] data, int ordering){
		if (mathBuilder != null) {
			return mathBuilder.createMatrix(rows, columns, data, ordering);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Matrix} by copying the given one.
	 * @param matrix the {@link Matrix matrix} to copy
	 * @return a new instance of {@link Matrix} 
	 */
	public static Matrix createMatrix(Matrix matrix) {
		if (mathBuilder != null) {
			return mathBuilder.createMatrix(matrix);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}
	
	/**
	 * Create a new square {@link Matrix} with the given <code>size</code> and that is initialized to identity using the underlying {@link #getMathBuilder() math builder}.
	 * @param size the number of rows and columns of the matrix.
	 * @return a new square {@link Matrix} with the given <code>size</code> and that is initialized to identity
	 * @throws IllegalStateException if the {@link #getMathBuilder() math builder} is <code>null</code>
	 */
	public static Matrix createMatrixEye(int size) {
		if (mathBuilder != null) {
			return mathBuilder.createMatrixEye(size);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Quaternion} using the underlying {@link #getMathBuilder() math builder}.
	 * @return a new instance of {@link Quaternion} using the underlying {@link #getMathBuilder() math builder}
	 * @throws IllegalStateException if the {@link #getMathBuilder() math builder} is <code>null</code>
	 */
	public static Quaternion createQuaternion(){
		if (mathBuilder != null) {
			return mathBuilder.createQuaternion();
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Quaternion} using the underlying {@link #getMathBuilder() math builder}.
	 * @param scalar the scalar of the parameter
	 * @param i the i component
	 * @param j the j component
	 * @param k the k component
	 * @return a new instance of {@link Quaternion} using the underlying {@link #getMathBuilder() math builder}
	 * @throws IllegalStateException if the {@link #getMathBuilder() math builder} is <code>null</code>
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public static Quaternion createQuaternion(double scalar, double i, double j, double k){
		if (mathBuilder != null) {
			return mathBuilder.createQuaternion(scalar, i, j, k);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Vector} according to the given dimension.
	 * @param dimensions the number of dimensions of the point
	 * @param defaultValue the default values of all coordinates
	 * @return a new instance of {@link Vector}
	 */
	public static Vector createPoint(int dimensions, double defaultValue){
		if (pointBuilder != null) {
			return pointBuilder.createPoint(dimensions, defaultValue);
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Point2D}.
	 * @return a new instance of {@link Point2D}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public static Point2D createPoint2D(){
		if (pointBuilder != null) {
			return pointBuilder.createPoint2D();
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Point2D}. 
	 * The coordinates of the new point are given in parameter
	 * @param x the point coordinate along the <i>X</i> axis
	 * @param y the point coordinate along the <i>Y</i> axis
	 * @return a new instance of {@link Point2D} 
	 * @throws IllegalArgumentException if an instantiation error occurs.
	 */
	public static Point2D createPoint2D(double x, double y){
		if (pointBuilder != null) {
			return pointBuilder.createPoint2D(x, y);
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Point2D} that is a copy of the point given in parameter. 
	 * @param point the point to copy
	 * @return a new instance of {@link Point2D} that is a copy of the point given in parameter
	 */
	public static Point2D createPoint2D(Point2D point) {
		if (pointBuilder != null) {
			return pointBuilder.createPoint2D(point);
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}
	
	/**
	 * Create a new instance of {@link Point2D} with the values obtained from the {@link Vector} given in parameter. 
	 * The first component of the vector is considered as <i>x</i> value and the second component is considered as <i>y</i> value.
	 * <br><br>
	 * If the given <code>vector</code> has more than 2 components, only the first two are considered.
	 * @param vector the vector to use as values source
	 * @return a new instance of {@link Point2D} with the values obtained from the vector given in parameter.
	 * @throws IllegalArgumentException if the given <code>vector</code> is <code>null</code> or has less than 2 components. 
	 */
	public static Point2D createPoint2D(Vector vector) {
		if (pointBuilder != null) {
			return pointBuilder.createPoint2D(vector);
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}
	
	/**
	 * Create a new instance of {@link Point2DContainer}. 
	 * @return a new instance of {@link Point2DContainer}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public static Point2DContainer createPoint2DContainer(){
		if (pointBuilder != null) {
			return pointBuilder.createPoint2DContainer();
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Point2DContainer}. 
	 * @param capacity the initial capacity of the container (if reliable)
	 * @return a new instance of {@link Point2DContainer}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public static Point2DContainer createPoint2DContainer(int capacity){
		if (pointBuilder != null) {
			return pointBuilder.createPoint2DContainer(capacity);
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Point3D}. 
	 * @return a new instance of {@link Point3D}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public static Point3D createPoint3D(){
		if (pointBuilder != null) {
			return pointBuilder.createPoint3D();
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Point3D}. 
	 * The coordinates of the new point are given in parameter
	 * @param x the point coordinate along the <i>X</i> axis
	 * @param y the point coordinate along the <i>Y</i> axis
	 * @param z the point coordinate along the <i>Z</i> axis
	 * @return a new instance of {@link Point3D}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public static Point3D createPoint3D(double x, double y, double z){
		if (pointBuilder != null) {
			return pointBuilder.createPoint3D(x, y, z);
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}
	

	/**
	 * Create a new instance of {@link Point3D} that is a copy of the point given in parameter. 
	 * @param point the point to copy
	 * @return a new instance of {@link Point3D} that is a copy of the point given in parameter
	 */
	public static Point3D createPoint3D(Point3D point) {
		if (pointBuilder != null) {
			return pointBuilder.createPoint3D(point);
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}
	
	/**
	 * Create a new instance of {@link Point3D} with the values obtained from the {@link Vector} given in parameter. 
	 * The first component of the vector is considered as <i>x</i> value, the second component is considered as <i>y</i> value and the third component is considered as <i>z</i> value.
	 * <br><br>
	 * If the given <code>vector</code> has more than 3 components, only the first three are considered.
	 * @param vector the vector to use as values source
	 * @return a new instance of {@link Point3D} with the values obtained from the vector given in parameter.
	 * @throws IllegalArgumentException if the given <code>vector</code> is <code>null</code> or has less than 3 components. 
	 */
	public static Point3D createPoint3D(Vector vector) throws IllegalArgumentException{
		if (pointBuilder != null) {
			return pointBuilder.createPoint3D(vector);
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}
	
	/**
	 * Create a new instance of {@link Point3DContainer}. 
	 * @param <T> The type of the 3D points
	 * @return a new instance of {@link Point3DContainer}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public static <T extends Point3D> Point3DContainer<T> createPoint3DContainer(){
		if (pointBuilder != null) {
			return pointBuilder.createPoint3DContainer();
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Point3DContainer}. 
	 * @param <T> The type of the 3D points
	 * @param capacity the initial capacity of the container (if reliable)
	 * @return a new instance of {@link Point3DContainer}
	 * @throws IllegalArgumentException if an instantiation error occurs
	 */
	public static <T extends Point3D> Point3DContainer<T> createPoint3DContainer(int capacity){
		if (pointBuilder != null) {
			return pointBuilder.createPoint3DContainer(capacity);
		} else {
			throw new IllegalStateException("No point builder available.");
		}
	}

	
	/**
	 * Create a new default empty mesh.
	 * @param <T> The type of underlying 3D points
	 * @return a new default empty mesh.
	 */
	public static <T extends Point3D> Mesh<T> createMesh(){
		if (meshBuilder != null) {
			return meshBuilder.createMesh();
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create a new instance of {@link Edge}.<br>
	 * @param <T> The type of underlying 3D points
	 * @param mesh the mesh that contains the edge
	 * @param vertex1 the first extremity of the edge
	 * @param vertex2 the second extremity of the edge
	 * @return a new instance of {@link Edge} using the given <code>implementation</code>
	 */
	public static <T extends Point3D> Edge<T> createMeshEdge(Mesh<T> mesh, T vertex1, T vertex2) {
		if (meshBuilder != null) {
			return meshBuilder.createMeshEdge(mesh, vertex1, vertex2);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create a new mesh {@link Face} from the given vertices attached to the given {@link Mesh mesh}. 
	 * The face contour is obtained by linking vertices from the first to the last.
	 * @param <T> The type of underlying 3D points
	 * @param mesh the mesh that holds the face
	 * @param vertices the vertices of the face
	 * @return a new mesh {@link Face} from the given vertices
	 */
	public static <T extends Point3D> Face<T> createMeshFace(Mesh<T> mesh, Point3DContainer<T> vertices) {
		if (meshBuilder != null) {
			return meshBuilder.createMeshFace(mesh, vertices);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create a new mesh {@link Face} from the given vertices. 
	 * @param <T> The type of underlying 3D points
	 * @param vertices the vertices of the face
	 * @return a new mesh {@link Face} from the given vertices
	 */
	public static <T extends Point3D> Face<T> createMeshFace(Point3DContainer<T> vertices) {
		if (meshBuilder != null) {
			return meshBuilder.createMeshFace(vertices);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}
	
	/**
	 * Create a new triangle {@link Face} from the given vertices. 
	 * @param <T> The type of underlying 3D points
	 * @param vertex1 the first vertex
	 * @param vertex2 the second vertex
	 * @param vertex3 the third vertex
	 * @return a new {@link Triangle triangle} from the given vertices
	 */
	public static <T extends Point3D> Triangle<T> createMeshTriangle(T vertex1, T vertex2, T vertex3){
		if (meshBuilder != null) {
			return meshBuilder.createMeshTriangle(vertex1, vertex2, vertex3);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create a new {@link IndexedMesh indexed mesh}.
	 * @param <T> The type of underlying 3D points
	 * @return the newly created indexed mesh
	 */
	public static <T extends Point3D> IndexedMesh<T> createIndexedMesh(){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedMesh();
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create a new {@link IndexedMesh indexed mesh}.
	 * @param <T> The type of underlying 3D points
	 * @param capacity the initial capacity of the mesh in term of faces storage
	 * @return the newly created indexed mesh
	 */
	public static <T extends Point3D> IndexedMesh<T> createIndexedMesh(int capacity){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedMesh(capacity);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedEdge indexed edge} between the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param vertex1Index the index of the first extremity
	 * @param vertex2Index the index of the second extremity
	 * @return an indexed edge between the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedEdge<T> createIndexedMeshEdge(int vertex1Index, int vertex2Index){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedMeshEdge(vertex1Index, vertex2Index);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedEdge indexed edge} between the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param vertex1Index the index of the first extremity
	 * @param vertex2Index the index of the second extremity
	 * @param mesh the mesh that hold the edge.
	 * @return an indexed edge between the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedEdge<T> createIndexedMeshEdge(int vertex1Index, int vertex2Index, IndexedMesh<T> mesh){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedMeshEdge(vertex1Index, vertex2Index, mesh);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedFace<T> createIndexedMeshFace(int[] indices){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedMeshFace(indices);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @param mesh the indexed mesh that hold the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedFace<T> createIndexedMeshFace(int[] indices, IndexedMesh<T> mesh){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedMeshFace(indices, mesh);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedFace<T> createIndexedMeshFace(List<Integer> indices){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedMeshFace(indices);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @param mesh the indexed mesh that hold the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedFace<T> createIndexedMeshFace(List<Integer> indices, IndexedMesh<T> mesh){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedMeshFace(indices, mesh);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param vertex1 the first vertex index of the face
	 * @param vertex2 the second vertex index of the face
	 * @param vertex3 the third vertex index of the face
	 * @param mesh the mesh that holds the triangle
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedTriangle<T> createIndexedTriangle(int vertex1, int vertex2, int vertex3, IndexedMesh<T> mesh){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedTriangle(vertex1, vertex2, vertex3, mesh);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * @param <T> The type of underlying 3D points
	 * @param vertices  the vertices of the face (a 3 element array)
	 * @param mesh the mesh that holds the triangle
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedTriangle<T> createIndexedTriangle(int[] vertices, IndexedMesh<T> mesh){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedTriangle(vertices, mesh);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create a new {@link IndexedTriangleMesh indexed triangle mesh} that relies on the given vertices source.
	 * @param <T> The type of underlying 3D points
	 * @param source the indexed mesh vertices source
	 * @return a new {@link IndexedTriangleMesh indexed triangle mesh}
	 */
	public static <T extends Point3D> IndexedTriangleMesh<T> createIndexedTriangleMesh(Point3DContainer<T> source){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedTriangleMesh(source);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create a new {@link IndexedTriangleMesh indexed triangle mesh} that relies on the given vertices source 
	 * with the given initial faces capacity.
	 * @param <T> The type of underlying 3D points
	 * @param size the initial faces capacity
	 * @param source  the indexed mesh vertices source
	 * @return a new {@link IndexedTriangleMesh indexed triangle mesh}
	 */
	public static <T extends Point3D> IndexedTriangleMesh<T> createIndexedTriangleMesh(int size, Point3DContainer<T> source){
		if (meshBuilder != null) {
			return meshBuilder.createIndexedTriangleMesh(size, source);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(int[] indices){
		if (meshBuilder != null) {
			return meshBuilder.createTexturedIndexedMeshFace(indices);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}
	
	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face.
	 * @param mesh the indexed mesh that hold the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(int[] indices, IndexedMesh<T> mesh){
		if (meshBuilder != null) {
			return meshBuilder.createTexturedIndexedMeshFace(indices, mesh);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(List<Integer> indices){
		if (meshBuilder != null) {
			return meshBuilder.createTexturedIndexedMeshFace(indices);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedFace indexed face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param indices the indices that reference the vertices of the face
	 * @param mesh the indexed mesh that hold the face
	 * @return an {@link IndexedFace indexed face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedFace<T> createTexturedIndexedMeshFace(List<Integer> indices, IndexedMesh<T> mesh){
		if (meshBuilder != null) {
			return meshBuilder.createTexturedIndexedMeshFace(indices, mesh);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param vertex1 the first vertex index of the face
	 * @param vertex2 the second vertex index of the face
	 * @param vertex3 the third vertex index of the face
	 * @param mesh the mesh that holds the triangle
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int vertex1, int vertex2, int vertex3, IndexedMesh<T> mesh){
		if (meshBuilder != null) {
			return meshBuilder.createTexturedIndexedTriangle(vertex1, vertex2, vertex3, mesh);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}
	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param vertex1 the first vertex index of the face
	 * @param vertex2 the second vertex index of the face
	 * @param vertex3 the third vertex index of the face
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int vertex1, int vertex2, int vertex3){
		if (meshBuilder != null) {
			return meshBuilder.createTexturedIndexedTriangle(vertex1, vertex2, vertex3);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param vertices the vertices of the face (a 3 element array)
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int[] vertices){
		if (meshBuilder != null) {
			return meshBuilder.createTexturedIndexedTriangle(vertices);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices.
	 * The returned object is also an instance of {@link Texturable} interface.
	 * @param <T> The type of underlying 3D points
	 * @param vertices  the vertices of the face (a 3 element array)
	 * @param mesh the mesh that holds the triangle
	 * @return an {@link IndexedTriangle indexed triangular mesh face} made of the vertices designed by the given indices
	 */
	public static <T extends Point3D> IndexedTriangle<T> createTexturedIndexedTriangle(int[] vertices, IndexedMesh<T> mesh){
		if (meshBuilder != null) {
			return meshBuilder.createTexturedIndexedTriangle(vertices, mesh);
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}
	
	/**
	 * Create a new {@link MeshNeighborhood mesh neighborhood}.
	 * @param <T> The type of underlying 3D points
	 * @return a new mesh neighborhood
	 */
	public static <T extends Point3D> MeshNeighborhood<T> createMeshNeighborhood(){
		if (meshBuilder != null) {
			return meshBuilder.createMeshNeighborhood();
		} else {
			throw new IllegalStateException("No mesh builder available.");
		}
	}

	/**
	 * Create a default {@link Plane}. 
	 * @param <T> The type of underlying 3D points
	 * @return a default {@link Plane} 
	 */
	public static <T extends Point3D> Plane<T> createPlane(){
		// TODO Implements GeometryFactory.createPlane()
		return null;
	}

	/**
	 * Create a new {@link Plane} with the given parameters.
	 * @param <T> The type of underlying 3D points
	 * @param a the <i>a</i> coefficient within the plane equation
	 * @param b the <i>b</i> coefficient within the plane equation
	 * @param c the <i>d</i> coefficient within the plane equation
	 * @param d the <i>d</i> coefficient within the plane equation
	 * @return a new {@link Plane} with the given parameters
	 */
	public static <T extends Point3D> Plane<T> createPlane(double a, double b, double c, double d){
		// TODO Implements GeometryFactory.createPlane(double, double, double, double)
		return null;
	}

	/**
	 * Create a new {@link Plane} with the given parameters.
	 * @param <T> The type of underlying 3D points
	 * @param origin the origin of the plane
	 * @param normal the normal to the plane
	 * @return a new {@link Plane} with the given parameters
	 */
	public static <T extends Point3D> Plane<T> createPlane(T origin, T normal){
		// TODO Implements GeometryFactory.createPlane(T, T)
		return null;
	}

	/**
	 * Create a new axis aligned {@link Box box} with the given minimal and maximal vertices.
	 * @param <T> the type of the vertices
	 * @param min the minimal vertex
	 * @param max the maximal vertex
	 * @return a new axis aligned {@link Box box}
	 */
	public static <T extends Point3D> Box createBox(T min, T max) {
		// TODO implements createBox(T min, T max)
		return null;
	}
	
   /**
    * Create a new {@link IndexedTetrahedron indexed tetrahedron}.
    * @param <T> the type of the vertices
    * @param base1 the index within the source of the first vertex of the tetrahedron base
    * @param base2 the index within the source of the first vertex of the tetrahedron base
    * @param base3 the index within the source of the first vertex of the tetrahedron base
    * @param top the index within the source of the tetrahedron top
    * @param source the indexed mesh vertices source
    * @return an indexed {@link IndexedTetrahedron indexed tetrahedron}
    */
	public static <T extends Point3D> IndexedTetrahedron<T> createIndexedTetrahedron(int base1, int base2, int base3, int top, Point3DContainer<T> source) {
		// TODO implements createBox(T min, T max)
		return null;
	}
	
	/**
	 * Create a {@link Solver matrix solver} using a default method.
	 * @return the created solver
	 */
	public static Solver createSolver() {
		if (mathBuilder != null) {
			return mathBuilder.createSolver();
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}
	
	/**
	 * Create a new {@link LUDecomposition LU decomposition} from the given matrix.
	 * @param matrix the matrix to decompose
	 * @return the {@link LUDecomposition LU decomposition} from the given matrix
	 */
	public static LUDecomposition createLUDecomposition(Matrix matrix) {
		if (mathBuilder != null) {
			return mathBuilder.createLUDecomposition(matrix);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}
	
	/**
	 * Create a new {@link EigenDecomposition Eigen decomposition} from the given matrix.
	 * @param matrix the matrix to decompose
	 * @return the {@link EigenDecomposition Eigen decomposition} from the given matrix
	 */
	public static EigenDecomposition createEigenDecomposition(Matrix matrix) {
		if (mathBuilder != null) {
			return mathBuilder.createEigenDecomposition(matrix);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}
	
	/**
	 * Create a new {@link SVDDecomposition Singular Values (SVD) decomposition} from the given matrix.
	 * @param matrix the matrix to decompose
	 * @return the {@link SVDDecomposition Singular Values (SVD) decomposition}  from the given matrix
	 */
	public static SVDDecomposition createSVDDecomposition(Matrix matrix) {
		if (mathBuilder != null) {
			return mathBuilder.createSVDDecomposition(matrix);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}
		
	/**
	 * Create a new {@link QRDecomposition QR decomposition} from the given matrix.
	 * @param matrix the matrix to decompose
	 * @return the {@link QRDecomposition QR decomposition} from the given matrix
	 */
	public static QRDecomposition createQRDecomposition(Matrix matrix) {
		if (mathBuilder != null) {
			return mathBuilder.createQRDecomposition(matrix);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}
	
	/**
	 * Create a new {@link CholeskyDecomposition Cholesky decomposition} from the given matrix.
	 * @param matrix the matrix to decompose
	 * @return the {@link CholeskyDecomposition Cholesky decomposition} from the given matrix
	 */
	public static CholeskyDecomposition createCholeskyDecomposition(Matrix matrix) {
		if (mathBuilder != null) {
			return mathBuilder.createCholeskyDecomposition(matrix);
		} else {
			throw new IllegalStateException("No math builder available.");
		}
	}
	
	/**
	 * Create a new {@link Transform3DMatrix} that represents the identity transform.
	 * @return a new {@link Transform3DMatrix} that represents the identity transform
	 */
	public static Transform3DMatrix createTransform3DMatrix() {
		if (transformBuilder != null) {
			return transformBuilder.createTransform3DMatrix();
		} else {
			throw new IllegalStateException("No transform builder available.");
		}
	}

	/**
	 * Create a new {@link Transform3DMatrix} that relies on the 4x4 given matrix. <br><br>
	 * The 4x4 matrix represents a 3D affine transform defined such that:
	 * $$
	 * M = \begin{bmatrix} 
	 *        s_{x}r_{00} &amp; s_{x}r_{01} &amp; s_{x}r_{02} &amp; t_{x}  \\
	 *        s_{y}r_{10} &amp; s_{y}r_{11} &amp; s_{y}r_{12} &amp; t_{y}  \\
	 *        s_{z}r_{20} &amp; s_{z}r_{21} &amp; s_{z}r_{22} &amp; t_{z}  \\
	 *           0   &amp;    0   &amp;    0   &amp;  1  
	 *     \end{bmatrix}
	 * $$
	 * where:
	 * <ul>
	 * <li><i>r<sub>ii</sub></i> form the 3x3 rotation matrix
	 * <li><i>t<sub>k</sub></i> form the translation vector
	 * <li><i>s<sub>x</sub></i>, <i>s<sub>y</sub></i>, <i>s<sub>z</sub></i> are the scales values along axis
	 * </ul>
	 * @param matrix the 4x4 matrix that represents a 3D affine transform
	 * @return a new {@link Transform3DMatrix}
	 * @throws IllegalArgumentException if the input matrix is <code>null</code> or is not 4x4 sized
	 */
	public static Transform3DMatrix createTransform3DMatrix(Matrix matrix) {
		if (transformBuilder != null) {
			return transformBuilder.createTransform3DMatrix(matrix);
		} else {
			throw new IllegalStateException("No transform builder available.");
		}
	}

	/**
	 * Create a new {@link Transform3DMatrix} that relies on the given parameters.<br><br>
	 * The 4x4 underlying matrix is defined such that:
	 * $$
	 * M = \begin{bmatrix} 
	 *        sr_{00} &amp; sr_{01} &amp; sr_{02} &amp; t_{x}  \\
	 *        sr_{10} &amp; sr_{11} &amp; sr_{12} &amp; t_{y}  \\
	 *        sr_{20} &amp; sr_{21} &amp; sr_{22} &amp; t_{z}  \\
	 *           0   &amp;    0   &amp;    0   &amp;  1  
	 *     \end{bmatrix}
	 * $$
	 * where:
	 * <ul>
	 * <li><i>r<sub>ii</sub></i> form the 3x3 rotation matrix
	 * <li><i>t<sub>k</sub></i> form the translation vector
	 * <li><i>s</i> is the scale value
	 * </ul>
	 * @param rotation the rotation
	 * @param translation the translation as a 3 dimensioned vector
	 * @param scale the scale factor (applied to all axis)
	 * @return a new {@link Transform3DMatrix}
	 * @throws IllegalArgumentException if the rotation matrix is not 3x3 sized, if the translation Vector is not 3 dimensioned
	 */
	public static Transform3DMatrix createTransform3DMatrix(Matrix rotation, Vector translation, double scale) {
		if (transformBuilder != null) {
			return transformBuilder.createTransform3DMatrix(rotation, translation, scale);
		} else {
			throw new IllegalStateException("No transform builder available.");
		}
	}
	
	/**
	 * Create a new {@link Transform3DMatrix} that relies on the 4x4 given matrix. <br><br>
	 * The 4x4 matrix represents a 3D affine transform defined such that:
	 * $$
	 * M = \begin{bmatrix} 
	 *        s_{x}r_{00} &amp; s_{x}r_{01} &amp; s_{x}r_{02} &amp; t_{x}  \\
	 *        s_{y}r_{10} &amp; s_{y}r_{11} &amp; s_{y}r_{12} &amp; t_{y}  \\
	 *        s_{z}r_{20} &amp; s_{z}r_{21} &amp; s_{z}r_{22} &amp; t_{z}  \\
	 *           0   &amp;    0   &amp;    0   &amp;  1  
	 *     \end{bmatrix}
	 * $$
	 * where:
	 * <ul>
	 * <li><i>r<sub>ii</sub></i> form the 3x3 rotation matrix
	 * <li><i>t<sub>k</sub></i> form the translation vector
	 * <li><i>s<sub>x</sub></i>, <i>s<sub>y</sub></i>, <i>s<sub>z</sub></i> are the scales values along axis
	 * </ul>
	 * @param matrix the 4x4 matrix that represents a 3D affine transform
	 * @return a new {@link Transform3DMatrix}
	 * @throws IllegalArgumentException if the input matrix is <code>null</code> or is not 4x4 sized
	 */
	public static Transform3DMatrix createTransform3DMatrix(double[][] matrix) {
		if (transformBuilder != null) {
			return transformBuilder.createTransform3DMatrix(matrix);
		} else {
			throw new IllegalStateException("No transform builder available.");
		}
	}
	
	/**
	 * Create a new {@link Transform3DQuaternion} that represents the identity transform.
	 * @return a new {@link Transform3DQuaternion} that represents the identity transform
	 */
	public static Transform3DQuaternion createTransform3DQuaternion() {
		if (transformBuilder != null) {
			return transformBuilder.createTransform3DQuaternion();
		} else {
			throw new IllegalStateException("No transform builder available.");
		}
	}
}
