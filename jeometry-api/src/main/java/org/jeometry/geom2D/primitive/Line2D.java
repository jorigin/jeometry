package org.jeometry.geom2D.primitive;

import java.io.Serializable;

import org.jeometry.Jeometry;
import org.jeometry.geom2D.VertexGeometry2D;
import org.jeometry.geom2D.point.Point2D;

/**
 * A class that describe a line that links two {@link Point2D 2D points}.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 2.0.1
 */
public interface Line2D extends VertexGeometry2D, Serializable{

  /**
   * Return the first extremity of the line.
   * @return the first extremity of the line.
   */
  Point2D getEnd1();


  /**
   * Return the second extremity of the line.
   * @return the second extremity of the line.
   */
  Point2D getEnd2();

}