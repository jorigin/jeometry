/**
 * The Jeometry API module
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @since 1.0.5
 */
module org.jeometry.algorithm {
	
	exports org.jeometry.geom3D.algorithm;
	exports org.jeometry.geom3D.algorithm.bounds;
    exports org.jeometry.geom3D.algorithm.convexhull.quickhull;
    exports org.jeometry.geom3D.algorithm.decomposition;
    exports org.jeometry.geom3D.algorithm.delaunay.clarkson;
    exports org.jeometry.geom3D.algorithm.fitting;
	
	requires java.desktop;
	requires transitive java.logging;
	
	requires transitive org.jeometry.api;
}