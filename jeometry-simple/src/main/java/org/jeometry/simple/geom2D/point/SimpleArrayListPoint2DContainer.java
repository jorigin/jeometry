package org.jeometry.simple.geom2D.point;

import java.util.ArrayList;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.SpatialLocalization2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;

/**
 * A {@link Point2DContainer Point 2D container implementation} that relies on an {@link ArrayList ArrayList}. 
 * Within this implementation, the ({@link #getX() x}, {@link #getY() y}) coordinates are the one from the barycentre of the container. 
 * The distance from this container to another spatial localization is computed relatively to the barycentre of this container.
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a> (<a href="mailto:julien.seinturier@univ-tln.fr">julien.seinturier@univ-tln.fr</a>)
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class SimpleArrayListPoint2DContainer extends ArrayList<Point2D> implements Cloneable, Point2DContainer{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = Jeometry.BUILD;

	/**
	 * The type of the container.
	 */
	private int type    = Point2DContainer.DATA_COORDINATE2D | Point2DContainer.DATA_IDENTIFIER;

	/**
	 * The x coordinate of the mesh.
	 */
	private double x    = Double.NaN;

	/**
	 * The y coordinate of the mesh.
	 */
	private double y    = Double.NaN;

	/**
	 * The x min coordinate of the mesh.
	 */
	private double xmin = Double.NaN;

	/**
	 * The y min coordinate of the mesh.
	 */
	private double ymin = Double.NaN;

	/**
	 * The xmax coordinate of the mesh.
	 */
	private double xmax = Double.NaN;

	/**
	 * The y max coordinate of the mesh.
	 */
	private double ymax = Double.NaN;

	/**
	 * Create a new {@link Point2DContainer Point 2D container implementation} that relies on an {@link ArrayList array list}.
	 * It is recommended to use a {@link JeometryFactory geometry factory} in order to create instances instead of invoking this constructor.
	 * @see #SimpleArrayListPoint2DContainer(int)
	 */
	public SimpleArrayListPoint2DContainer(){
		super();
	}

	/**
	 * Create a new {@link Point2DContainer Point 2D container implementation} that relies on an {@link ArrayList array list}.
	 * The initial capacity of the point container is given by the parameter <code>size</code>.
	 * It is recommended to use a {@link JeometryFactory geometry factory} in order to create instances instead of invoking this constructor.
	 * @param size the initial capacity of the point container.
	 */
	public SimpleArrayListPoint2DContainer(int size){
		super(size);
	}

	@Override
	public int getDataType() {
		return this.type;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public Point2D getMinCoordinates() {
		return JeometryFactory.createPoint2D(this.xmin,  this.ymin);
	}

	@Override
	public Point2D getMaxCoordinates() {
		return JeometryFactory.createPoint2D(this.xmax,  this.ymax);
	}

	@Override
	public double distance(SpatialLocalization2D spatial) {
		return Math.sqrt(  (spatial.getX()-getX())*(spatial.getX()-getX())
				+ (spatial.getY()-getY())*(spatial.getY()-getY()));
	}

	@Override
	public void updateLocalization(){

		this.x = 0.0d;
		this.y = 0.0d;

		this.xmin = Double.MAX_VALUE;
		this.ymin = Double.MAX_VALUE;

		this.xmax = -Double.MAX_VALUE;
		this.ymax = -Double.MAX_VALUE;

		for(Point2D point3d : this){

			if (point3d.getX() > this.xmax){
				this.xmax = point3d.getX();
			}

			if (point3d.getX() < this.xmin){
				this.xmin = point3d.getX();
			}

			if (point3d.getY() > this.ymax){
				this.ymax = point3d.getY();
			}

			if (point3d.getY() < this.ymin){
				this.ymin = point3d.getY();
			}

			this.x = this.x + point3d.getX();
			this.y = this.y + point3d.getY();
		}

		this.x = this.x / size();
		this.y = this.y / size();
	}

	@Override
	public Point2D get(int index, Point2D output) {

		if (output == null) {
			return null;
		}

		if (index >= 0 && index < size()) {
			Point2D point =get(index);
			output.setValues(point);
			return output;
		}

		return null;
	}

}
