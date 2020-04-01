package org.jeometry.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Geometry;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.point.Point3D;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A test class for {@link GeometryFactory} methods that delegate to a {@link MeshBuilder mesh builder}.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 *  Create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * <br>
 * &nbsp;&nbsp;meshClass = [the mesh implementation];<br>
 * &nbsp;&nbsp;faceClass = [the face implementation];<br>
 * &nbsp;&nbsp;edgeClass = [the edge implementation];<br>
 *  <br>
 * &nbsp;&nbsp;indexedMeshClass = [the indexed mesh implementation];<br>
 * &nbsp;&nbsp;indexedFaceClass = [the indexed face implementation];<br>
 * &nbsp;&nbsp;indexedEdgeClass = [the indexed edge implementation];<br>
 * <br>
 * &nbsp;&nbsp;GeometryFactory.setMeshBuilder([a builder that provide suitable classes]);<br>
 * }<br>
 * </code>
 * <br>
 * If the object provided by the geometry factory are not from the same classes as the declared ones, tests will fail.
 * </p>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 *
 */
public class GeometryFactoryMeshTest {
	
    protected static Class<?> meshClass         = null;
    protected static Class<?> faceClass         = null;
    protected static Class<?> edgeClass         = null;
    
    protected static Class<?> indexedMeshClass         = null;
    protected static Class<?> indexedFaceClass         = null;
    protected static Class<?> indexedEdgeClass         = null;
    
	/**
	 * Test initialization.
	 */
	@BeforeClass
	public static void init() {
		fail("method public static void init() has to be set up with @BeforeClass annotation");
	}	
	
	/**
	 * Testing {@link GeometryFactory#createIndexedMesh()}
	 */
	@Test
	public void createIndexedMeshTest() {
		
		try {
			IndexedMesh<Point3D> mesh = GeometryFactory.createIndexedMesh();
			assertNotNull("Cannot instantiate indexed mesh using GeometryFactory.createIndexedMesh().", mesh);	
			
			if (indexedMeshClass != null) {
				assertTrue("Invalid class: "+indexedMeshClass.getSimpleName()+" expected but found "+mesh.getClass().getSimpleName(), indexedMeshClass.isInstance(mesh));
			}
			
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed mesh using GeometryFactory.createIndexedMesh(): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link GeometryFactory#createIndexedMesh(int)}
	 */
	@Test
	public void createIndexedMeshCapacityTest() {
		
		try {
			IndexedMesh<Point3D> mesh = GeometryFactory.createIndexedMesh(10);
			assertNotNull("Cannot instantiate indexed mesh using GeometryFactory.createIndexedMesh(int).", mesh);	
			
			if (indexedMeshClass != null) {
				assertTrue("Invalid class: "+indexedMeshClass.getSimpleName()+" expected but found "+mesh.getClass().getSimpleName(), indexedMeshClass.isInstance(mesh));
			}
			
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed mesh using GeometryFactory.createIndexedMesh(int): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link GeometryFactory#createIndexedMeshEdge(int, int)}
	 */
	@Test
	public void createIndexedMeshEdgeIntIntTest() {
		
		try {
			IndexedEdge<Point3D> edge = GeometryFactory.createIndexedMeshEdge(1, 2);
			
			assertNotNull("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshEdge(int, int).", edge);
			
			if (indexedEdgeClass != null) {
				assertTrue("Invalid class: "+indexedEdgeClass.getSimpleName()+" expected but found "+edge.getClass().getSimpleName(), indexedEdgeClass.isInstance(edge));
			}

			assertNotNull("Edge vertex indices are invalid.", edge.getVerticesIndexes());
			assertEquals("Edge vertex indices number is invalid.", 2, edge.getVerticesIndexes().length, 0);
			assertEquals("Edge extremity 1 is invalid.", 1, edge.getVerticesIndexes()[0], 0);
			assertEquals("Edge extremiti 2 is invalid.", 2, edge.getVerticesIndexes()[1], 0);
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshEdge(int, int): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link GeometryFactory#createIndexedMeshEdge(int, int, IndexedMesh)}
	 */
	@Test
	public void createIndexedMeshEdgeIntIntMeshTest() {
		
		try {
			IndexedMesh<Point3D> mesh = GeometryFactory.createIndexedMesh();

			IndexedEdge<Point3D> edge = GeometryFactory.createIndexedMeshEdge(1, 2, mesh);
			
			assertNotNull("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshEdge(int, int, IndexedMesh).", edge);
			
			if (indexedEdgeClass != null) {
				assertTrue("Invalid class: "+indexedEdgeClass.getSimpleName()+" expected but found "+edge.getClass().getSimpleName(), indexedEdgeClass.isInstance(edge));
				
			}

			assertNotNull("Edge vertex indices are invalid.", edge.getVerticesIndexes());
			assertEquals("Edge vertex indices number is invalid.", 2, edge.getVerticesIndexes().length, 0);
			assertEquals("Edge extremity 1 is invalid.", 1, edge.getVerticesIndexes()[0], 0);
			assertEquals("Edge extremiti 2 is invalid.", 2, edge.getVerticesIndexes()[1], 0);
			assertEquals("Edge mesh is invalid.", mesh, edge.getMesh());
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshEdge(int, int, IndexedMesh): "+e.getMessage());
		}
	}
	
	
	/**
	 * Testing {@link GeometryFactory#createIndexedMeshFace(int[])}
	 */
	@Test
	public void createIndexedMeshFaceIntArrayTest() {
		
		try {
			int[] indices = new int[] {1, 2, 3, 4, 5, 6};
			IndexedFace<Point3D> face = GeometryFactory.createIndexedMeshFace(indices);
			
			assertNotNull("Cannot instantiate indexed face using GeometryFactory.createIndexedMeshFace(int[]).", face);
			
			assertNotNull("Face vertex indices are invalid.", face.getVerticesIndexes());
			assertEquals("Face vertex indices number is invalid.", indices.length, face.getVerticesIndexes().length, 0);

			for(int i = 0; i < indices.length; i++) {
				assertEquals("Face indice "+i+" differs from expected.", indices[i], face.getVerticesIndexes()[i], 0);
			}
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshFace(int[]): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link GeometryFactory#createIndexedMeshFace(int[], IndexedMesh)}
	 */
	@Test
	public void createIndexedMeshFaceIntArrayMeshTest() {
		
		try {
			int[] indices = new int[] {1, 2, 3, 4, 5, 6};
			IndexedMesh<Point3D> mesh = GeometryFactory.createIndexedMesh();
			IndexedFace<Point3D> face = GeometryFactory.createIndexedMeshFace(indices, mesh);
			
			assertNotNull("Cannot instantiate indexed face using GeometryFactory.createIndexedMeshFace(int[], IndexedMesh).", face);
			
			assertNotNull("Face vertex indices are invalid.", face.getVerticesIndexes());
			assertEquals("Face vertex indices number is invalid.", indices.length, face.getVerticesIndexes().length, 0);

			for(int i = 0; i < indices.length; i++) {
				assertEquals("Face indice "+i+" differs from expected.", indices[i], face.getVerticesIndexes()[i], 0);
			}
			
			assertEquals("Edge mesh is invalid.", mesh, face.getMesh());
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshFace(int[], IndexedMesh): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link GeometryFactory#createIndexedMeshFace(List)}
	 */
	@Test
	public void createIndexedMeshFaceIntListTest() {
		
		try {
			List<Integer> indices = new ArrayList<Integer>(6); 
			indices.add(1);
			indices.add(2);
			indices.add(3);
			indices.add(4);
			indices.add(5);
			indices.add(6);

			IndexedFace<Point3D> face = GeometryFactory.createIndexedMeshFace(indices);
			
			assertNotNull("Cannot instantiate indexed face using GeometryFactory.createIndexedMeshFace(List).", face);
			
			assertNotNull("Face vertex indices are invalid.", face.getVerticesIndexes());
			assertEquals("Face vertex indices number is invalid.", indices.size(), face.getVerticesIndexes().length, 0);

			for(int i = 0; i < indices.size(); i++) {
				assertEquals("Face indice "+i+" differs from expected.", indices.get(i), face.getVerticesIndexes()[i], 0);
			}
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshFace(List): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link GeometryFactory#createIndexedMeshFace(List, IndexedMesh)}
	 */
	@Test
	public void createIndexedMeshFaceIntListMeshTest() {
		
		try {
			List<Integer> indices = new ArrayList<Integer>(6); 
			indices.add(1);
			indices.add(2);
			indices.add(3);
			indices.add(4);
			indices.add(5);
			indices.add(6);
			
			IndexedMesh<Point3D> mesh = GeometryFactory.createIndexedMesh();
			IndexedFace<Point3D> face = GeometryFactory.createIndexedMeshFace(indices, mesh);
			
			assertNotNull("Cannot instantiate indexed face using GeometryFactory.createIndexedMeshFace(List, IndexedMesh).", face);
			
			assertNotNull("Face vertex indices are invalid.", face.getVerticesIndexes());
			assertEquals("Face vertex indices number is invalid.", indices.size(), face.getVerticesIndexes().length, 0);

			for(int i = 0; i < indices.size(); i++) {
				assertEquals("Face indice "+i+" differs from expected.", indices.get(i), face.getVerticesIndexes()[i], 0);
			}
			
			assertEquals("Edge mesh is invalid.", mesh, face.getMesh());
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshFace(List, IndexedMesh): "+e.getMessage());
		}
	}
}
