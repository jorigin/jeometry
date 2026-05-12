package org.jeometry.math.solver;

import org.jeometry.Jeometry;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

/**
 * This interface describes a mathematical system that can be solved as a linear system.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.2
 */
public interface Resolvable {

    /**
     * Compute the matrix <i>X</i> that solve the linear system <i>A</i><i>X</i>&nbsp;=&nbsp;<i>B</i>, 
     * where <i>A</i> is a linear system represented by this object.
     * @param b the constants parameters
     * @return the solution <i>X</i>
     */
    public Matrix solve(Matrix b);
    
    /**
     * Compute the matrix <i>X</i> that solve the linear system <i>A</i><i>X</i>&nbsp;=&nbsp;<i>B</i>, 
     * where <i>A</i> is a linear system represented by this object.
     * @param b the constants parameters
     * @param x the matrix that store the solution of the linear system
     * @return a reference on <code>x</code>
     */
    public Matrix solve(Matrix b, Matrix x);
    
    /**
     * Compute the vector <i>X</i> that solve the linear system <i>A</i><i>X</i>&nbsp;=&nbsp;<i>B</i>, 
     * where <i>A</i> is a linear system represented by this object.
     * @param b the constants parameters
     * @return the solution <i>X</i>
     */
    public Vector solve(Vector b);
    
    /**
     * Compute the vector <i>X</i> that solve the linear system <i>A</i><i>X</i>&nbsp;=&nbsp;<i>B</i>, 
     * where <i>A</i> is a linear system represented by this object.
     * @param b the constants parameters
     * @param x the vector that store the solution of the linear system
     * @return a reference on <code>x</code>
     */
    public Vector solve(Vector b, Vector x);
    
}
