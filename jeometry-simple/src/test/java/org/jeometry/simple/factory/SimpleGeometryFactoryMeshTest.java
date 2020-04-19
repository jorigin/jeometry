package org.jeometry.simple.factory;


import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.factory.GeometryFactoryMeshTest;
import org.jeometry.simple.geom3D.mesh.SimpleEdge;
import org.jeometry.simple.geom3D.mesh.SimpleFace;
import org.jeometry.simple.geom3D.mesh.SimpleMesh;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedEdge;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedFace;
import org.jeometry.simple.geom3D.mesh.indexed.SimpleIndexedMesh;
import org.junit.BeforeClass;

/**
 * A test class for {@link JeometryFactory} methods.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class SimpleGeometryFactoryMeshTest extends GeometryFactoryMeshTest {

	
	/**
	 * Test initialization.
	 */
	@BeforeClass
	public static void init() {
		
	    meshClass         = SimpleMesh.class;
	    faceClass         = SimpleFace.class;
	    edgeClass         = SimpleEdge.class;
	    
	    indexedMeshClass         = SimpleIndexedMesh.class;
	    indexedFaceClass         = SimpleIndexedFace.class;
	    indexedEdgeClass         = SimpleIndexedEdge.class;
	    
		JeometryFactory.setMeshBuilder(new SimpleMeshBuilder());
	}
}
