package org.jeometry.simple.geom3D.primitive;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.Geom3D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Line3D;
import org.jeometry.simple.geom3D.mesh.SimpleEdge;

/**
 * A simple implementation of {@link Line3D}.
 * @param <T> the type of the underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 *
 */
public class SimpleLine3D<T extends Point3D> implements Line3D<T>{

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = Jeometry.BUILD;

	/** The origin of the edge */
	private T end1    = null;

	/** The extremity of the edge */
	private T end2 = null;

	/**
	 * Construct a line between two points.
	 * @param end1 the first point of the line.
	 * @param end2 the second point of the line.
	 */
	public SimpleLine3D(T end1, T end2){
		this.end1 = end1;
		this.end2 = end2;
	}

	@Override
	public Point3DContainer<T> getVertices(){
		Point3DContainer<T> p3dm = JeometryFactory.createPoint3DContainer(2);
		p3dm.add(end1);
		p3dm.add(end2);
		return p3dm;
	}


	@Override
	public T getEnd1(){
		return this.end1;
	}

	@Override
	public T getEnd2(){
		return this.end2;
	}

	/**
	 * Overload of the equals function of {@link Object}. This method return <code>true</code> if the reference of the
	 * this line is the same that the object given in parameter. Otherwise, the method {@link #equals(Line3D)} is called
	 * if the parameter is an instance of {@link Line3D}.
	 * @param o the object to compare
	 * @return <code>true</code> if this line and the object given in parameter are equals.
	 */
	public boolean equals(Object o){
		if (o == this){
			return true;
		} else{
			if (o instanceof SimpleEdge){
				return equals((SimpleEdge<?>) o);
			} else{
				return false;
			}
		}
	}

	@Override
	public String toString(){
		return getClass().getSimpleName()+": ["+"("+end1.getX()+", "+end1.getY()+", "+end1.getZ()+") : ("
				+end2.getX()+", "+end2.getY()+", "+end2.getZ()+")";
	}

	/**
	 * Return the two extremities of this line as an array of {@link Point3D 3D points}.
	 * @return the two extremities of this line as an array of {@link Point3D 3D points}.
	 */
	public Point3D[] getVerticesArray(){
		Point3D p3da[] = new Point3D[2];
		p3da[0] = end1;
		p3da[1] = end2;
		return p3da;
	}

	/**
	 * Check whether two lines are the same. Two lines are the same if their extremity are {@link Geom3D#equals(Point3D, Point3D) equals}.
	 * @param e the line to compare to this
	 * @return <code>true</code> if the lines are the same and <code>false</code> otherwise.
	 */
	public boolean equals(Line3D<?> e){


		// Si deux objets ont même référence, ils sont identiques
		if (this == e){
			return true;
		} else {
			return ((Geom3D.equals(this.getEnd1(), e.getVertices().get(0)) &&
					Geom3D.equals(this.getEnd2(), e.getVertices().get(1))) ||
					(Geom3D.equals(this.getEnd2(), e.getVertices().get(0)) &&
							Geom3D.equals(this.getEnd1(), e.getVertices().get(1))));
		}

	}
}
