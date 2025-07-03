/**
 * The Jeometry API module
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @since 1.0.3
 */
module org.jeometry.simple {
	
	exports org.jeometry.simple.factory;
	exports org.jeometry.simple.geom2D.point;
	exports org.jeometry.simple.geom2D.primitive;
	exports org.jeometry.simple.geom3D.mesh;
	exports org.jeometry.simple.geom3D.mesh.indexed;
	exports org.jeometry.simple.geom3D.neighbor;
	exports org.jeometry.simple.geom3D.point;
	exports org.jeometry.simple.geom3D.primitive;
	exports org.jeometry.simple.geom3D.primitive.indexed;
	exports org.jeometry.simple.geom3D.transform;
	exports org.jeometry.simple.math;
	exports org.jeometry.simple.math.decomposition;
	exports org.jeometry.simple.math.solver;

	// Authorize JeometryFactory to access JeometryImplementation
	exports org.jeometry.simple to org.jeometry.api;
	
	requires java.desktop;
	requires transitive java.logging;
	
	requires transitive org.jeometry.api;
	//requires org.jeometry.test;
}