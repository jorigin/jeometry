/**
 * The Jeometry API module
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @since 1.0.3
 */
module org.jeometry.api {
	exports org.jeometry;
	exports org.jeometry.factory;
	exports org.jeometry.geom2D;
	exports org.jeometry.geom2D.point;
	exports org.jeometry.geom3D;
	exports org.jeometry.geom3D.mesh;
	exports org.jeometry.geom3D.mesh.indexed;
	exports org.jeometry.geom3D.neighbor;
	exports org.jeometry.geom3D.point;
	exports org.jeometry.geom3D.primitive;
	exports org.jeometry.geom3D.primitive.indexed;
	exports org.jeometry.geom3D.properties;
	exports org.jeometry.geom3D.textured;
	exports org.jeometry.geom3D.transform;
	exports org.jeometry.io.matlab;
	exports org.jeometry.io.ply;
	exports org.jeometry.math;
	exports org.jeometry.math.decomposition;
	exports org.jeometry.math.solver;

	requires transitive java.desktop;
	requires transitive java.logging;
	
	requires org.jcommon;
}