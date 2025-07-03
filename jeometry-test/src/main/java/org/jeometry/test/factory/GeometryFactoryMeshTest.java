package org.jeometry.test.factory;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.factory.MeshBuilder;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.point.Point3D;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * A test class for {@link JeometryFactory} methods that delegate to a {@link MeshBuilder mesh builder}.<br>
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
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class GeometryFactoryMeshTest {
	
	/**
	 * The mesh class to use.
	 */
    protected static Class<?> meshClass         = null;
    
	/**
	 * The face class to use.
	 */
    protected static Class<?> faceClass         = null;
    
	/**
	 * The edge class to use.
	 */
    protected static Class<?> edgeClass         = null;
    
	/**
	 * The indexed mesh class to use.
	 */
    protected static Class<?> indexedMeshClass         = null;
    
	/**
	 * The indexed face class to use.
	 */
    protected static Class<?> indexedFaceClass         = null;
    
	/**
	 * The indexed edge class to use.
	 */
    protected static Class<?> indexedEdgeClass         = null;
	
	/**
	 * Testing {@link JeometryFactory#createIndexedMesh()}
	 */
	@Test
	public void createIndexedMeshTest() {
		
		try {
			IndexedMesh<Point3D> mesh = JeometryFactory.createIndexedMesh();
			assertNotNull(mesh, "Cannot instantiate indexed mesh using GeometryFactory.createIndexedMesh().");	
			
			if (indexedMeshClass != null) {
				assertTrue(indexedMeshClass.isInstance(mesh), "Invalid class: "+indexedMeshClass.getSimpleName()+" expected but found "+mesh.getClass().getSimpleName());
			}
			
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed mesh using GeometryFactory.createIndexedMesh(): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createIndexedMesh(int)}
	 */
	@Test
	public void createIndexedMeshCapacityTest() {
		
		try {
			IndexedMesh<Point3D> mesh = JeometryFactory.createIndexedMesh(10);
			assertNotNull(mesh, "Cannot instantiate indexed mesh using GeometryFactory.createIndexedMesh(int).");	
			
			if (indexedMeshClass != null) {
				assertTrue(indexedMeshClass.isInstance(mesh), "Invalid class: "+indexedMeshClass.getSimpleName()+" expected but found "+mesh.getClass().getSimpleName());
			}
			
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed mesh using GeometryFactory.createIndexedMesh(int): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createIndexedMeshEdge(int, int)}
	 */
	@Test
	public void createIndexedMeshEdgeIntIntTest() {
		
		try {
			IndexedEdge<Point3D> edge = JeometryFactory.createIndexedMeshEdge(1, 2);
			
			assertNotNull(edge, "Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshEdge(int, int).");
			
			if (indexedEdgeClass != null) {
				assertTrue(indexedEdgeClass.isInstance(edge), "Invalid class: "+indexedEdgeClass.getSimpleName()+" expected but found "+edge.getClass().getSimpleName());
			}

			assertNotNull(edge.getVerticesIndexes(), "Edge vertex indices are invalid.");
			assertEquals(2, edge.getVerticesIndexes().length, 0, "Edge vertex indices number is invalid.");
			assertEquals(1, edge.getVerticesIndexes()[0], 0, "Edge extremity 1 is invalid.");
			assertEquals(2, edge.getVerticesIndexes()[1], 0, "Edge extremiti 2 is invalid.");
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshEdge(int, int): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createIndexedMeshEdge(int, int, IndexedMesh)}
	 */
	@Test
	public void createIndexedMeshEdgeIntIntMeshTest() {
		
		try {
			IndexedMesh<Point3D> mesh = JeometryFactory.createIndexedMesh();

			IndexedEdge<Point3D> edge = JeometryFactory.createIndexedMeshEdge(1, 2, mesh);
			
			assertNotNull(edge, "Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshEdge(int, int, IndexedMesh).");
			
			if (indexedEdgeClass != null) {
				assertTrue(indexedEdgeClass.isInstance(edge), "Invalid class: "+indexedEdgeClass.getSimpleName()+" expected but found "+edge.getClass().getSimpleName());
				
			}

			assertNotNull(edge.getVerticesIndexes(), "Edge vertex indices are invalid.");
			assertEquals(2, edge.getVerticesIndexes().length, 0, "Edge vertex indices number is invalid.");
			assertEquals(1, edge.getVerticesIndexes()[0], 0, "Edge extremity 1 is invalid.");
			assertEquals(2, edge.getVerticesIndexes()[1], 0, "Edge extremiti 2 is invalid.");
			assertEquals(mesh, edge.getMesh(), "Edge mesh is invalid.");
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshEdge(int, int, IndexedMesh): "+e.getMessage());
		}
	}
	
	
	/**
	 * Testing {@link JeometryFactory#createIndexedMeshFace(int[])}
	 */
	@Test
	public void createIndexedMeshFaceIntArrayTest() {
		
		try {
			int[] indices = new int[] {1, 2, 3, 4, 5, 6};
			IndexedFace<Point3D> face = JeometryFactory.createIndexedMeshFace(indices);
			
			assertNotNull(face, "Cannot instantiate indexed face using GeometryFactory.createIndexedMeshFace(int[]).");
			
			assertNotNull(face.getVerticesIndexes(), "Face vertex indices are invalid.");
			assertEquals(indices.length, face.getVerticesIndexes().length, 0, "Face vertex indices number is invalid.");

			for(int i = 0; i < indices.length; i++) {
				assertEquals(indices[i], face.getVerticesIndexes()[i], 0, "Face indice "+i+" differs from expected.");
			}
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshFace(int[]): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createIndexedMeshFace(int[], IndexedMesh)}
	 */
	@Test
	public void createIndexedMeshFaceIntArrayMeshTest() {
		
		try {
			int[] indices = new int[] {1, 2, 3, 4, 5, 6};
			IndexedMesh<Point3D> mesh = JeometryFactory.createIndexedMesh();
			IndexedFace<Point3D> face = JeometryFactory.createIndexedMeshFace(indices, mesh);
			
			assertNotNull(face, "Cannot instantiate indexed face using GeometryFactory.createIndexedMeshFace(int[], IndexedMesh).");
			
			assertNotNull(face.getVerticesIndexes(), "Face vertex indices are invalid.");
			assertEquals(indices.length, face.getVerticesIndexes().length, 0, "Face vertex indices number is invalid.");

			for(int i = 0; i < indices.length; i++) {
				assertEquals(indices[i], face.getVerticesIndexes()[i], 0, "Face indice "+i+" differs from expected.");
			}
			
			assertEquals(mesh, face.getMesh(), "Edge mesh is invalid.");
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshFace(int[], IndexedMesh): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createIndexedMeshFace(List)}
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

			IndexedFace<Point3D> face = JeometryFactory.createIndexedMeshFace(indices);
			
			assertNotNull(face, "Cannot instantiate indexed face using GeometryFactory.createIndexedMeshFace(List).");
			
			assertNotNull(face.getVerticesIndexes(), "Face vertex indices are invalid.");
			assertEquals(indices.size(), face.getVerticesIndexes().length, 0, "Face vertex indices number is invalid.");

			for(int i = 0; i < indices.size(); i++) {
				assertEquals(indices.get(i), face.getVerticesIndexes()[i], 0, "Face indice "+i+" differs from expected.");
			}
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshFace(List): "+e.getMessage());
		}
	}
	
	/**
	 * Testing {@link JeometryFactory#createIndexedMeshFace(List, IndexedMesh)}
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
			
			IndexedMesh<Point3D> mesh = JeometryFactory.createIndexedMesh();
			IndexedFace<Point3D> face = JeometryFactory.createIndexedMeshFace(indices, mesh);
			
			assertNotNull(face, "Cannot instantiate indexed face using GeometryFactory.createIndexedMeshFace(List, IndexedMesh).");
			
			assertNotNull(face.getVerticesIndexes(), "Face vertex indices are invalid.");
			assertEquals(indices.size(), face.getVerticesIndexes().length, 0, "Face vertex indices number is invalid.");

			for(int i = 0; i < indices.size(); i++) {
				assertEquals(indices.get(i), face.getVerticesIndexes()[i], 0, "Face indice "+i+" differs from expected.");
			}
			
			assertEquals(mesh, face.getMesh(), "Edge mesh is invalid.");
			
		} catch (Exception e) {
			fail("Cannot instantiate indexed edge using GeometryFactory.createIndexedMeshFace(List, IndexedMesh): "+e.getMessage());
		}
	}
}
