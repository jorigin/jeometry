package org.jeometry.geom3D.transform;

import org.jeometry.Jeometry;
import org.jeometry.math.Quaternion;

/**
 * A transformation within a 3D space that relies on a {@link Quaternion quaternion}.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version}
 * @since 1.0.0
 * */
public interface Transform3DQuaternion extends Transform3D, Quaternion{

	/**
	 * Get the underlying {@link Quaternion quaternion}.
	 * @return the underlying {@link Quaternion quaternion}.
	 */
	public Quaternion getQuaternion();

	/**
	 * Copy the {@link Quaternion quaternion} that describes this 3D transformation into the given one.
	 * @param quaternion the {@link Quaternion quaternion} that has to store the copy.
	 * @return a reference to the <code>quaternion</code> parameter.
	 */
	public Quaternion getQuaternion(Quaternion quaternion);
	
	/**
	 * Set the underlying {@link Quaternion quaternion}.
	 * @param quaternion the underlying {@link Quaternion quaternion}.
	 */
	public void setQuaternion(Quaternion quaternion);
	
}
