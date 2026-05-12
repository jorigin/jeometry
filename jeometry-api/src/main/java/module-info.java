/**
 * The Jeometry API module
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @since 1.0.3
 */
module org.jeometry.api {
	exports org.jeometry;
	exports org.jeometry.factory;
	exports org.jeometry.geom2D;
	exports org.jeometry.geom2D.point;
	exports org.jeometry.geom2D.primitive;
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
	
	requires org.jcommon.core;
}