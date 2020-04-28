/**
 * The Jeometry Test module
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @since 1.0.3
 */
module org.jeometry.test {

	exports org.jeometry.test.factory;
	exports org.jeometry.test.geom2D.point;
	exports org.jeometry.test.geom3D;
	exports org.jeometry.test.geom3D.point;
	exports org.jeometry.test.geom3D.transform;
	exports org.jeometry.test.math;
	exports org.jeometry.test.math.decomposition;
	exports org.jeometry.test.math.solver;
	
	requires transitive org.jeometry.api;
	
	requires transitive java.desktop;
	requires transitive java.logging;
	
	requires org.jcommon;
	
	requires transitive org.junit.jupiter.api;
}