package org.jeometry.math.decomposition;

import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;

/**
 * An interface that describes matrix decomposition. A decomposition enable to express a matrix as a composition of other matrices.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public interface Decomposition {

	/**
	 * Get all the components of the decomposition.
	 * @return the list of all the components that compose the decomposition of the given matrix.
	 */
	public List<Matrix> getComponents();
	
}
