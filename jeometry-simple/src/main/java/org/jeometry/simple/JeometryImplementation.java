package org.jeometry.simple;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.jeometry.simple.factory.SimpleMeshBuilder;
import org.jeometry.simple.factory.SimplePointBuilder;
import org.jeometry.simple.factory.SimplePrimitiveBuilder;
import org.jeometry.simple.factory.SimpleTransformBuilder;

/**
 * The Jeometry implementation class. This class enables to automatically register implementation classes to the global factories.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 **/
public class JeometryImplementation {

	/**
	 * The implementation initialization method. This method can be invoked by the {@link JeometryFactory geometry factory}. 
	 */
	public static void initJeometryImplementation() {
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());
		JeometryFactory.setMeshBuilder(new SimpleMeshBuilder());
		JeometryFactory.setPointBuilder(new SimplePointBuilder());
		JeometryFactory.setPrimitiveBuilder(new SimplePrimitiveBuilder());
		JeometryFactory.setTransformBuilder(new SimpleTransformBuilder());
	}
	
}
