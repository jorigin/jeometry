package org.jeometry.simple;

import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.jeometry.simple.factory.SimpleMeshBuilder;
import org.jeometry.simple.factory.SimplePointBuilder;

/**
 * The Jeometry implementation class. This class enables to automatically register implementation classes to the global factories.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 *
 **/
public class JeometryImplementation {

	/**
	 * The implementation initialization method. This method can be invoked by the {@link GeometryFactory geometry factory}. 
	 */
	public static void initJeometryImplementation() {
		GeometryFactory.setMathBuilder(new SimpleMathBuilder());
		GeometryFactory.setMeshBuilder(new SimpleMeshBuilder());
		GeometryFactory.setPointBuilder(new SimplePointBuilder());
	}
	
}
