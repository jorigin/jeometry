package org.jeometry.simple.geom2D.primitive;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom2D.Geometry2D;
import org.jeometry.geom2D.SpatialLocalization2D;
import org.jeometry.geom2D.VertexGeometry2D;
import org.jeometry.geom2D.point.Point2D;
import org.jeometry.geom2D.point.Point2DContainer;
import org.jeometry.geom2D.primitive.Box2D;
import org.jeometry.geom2D.primitive.Line2D;
import org.jeometry.geom2D.primitive.LineString2D;
import org.jeometry.simple.geom2D.SimpleGeometry2DUtils;

/**
 * A simple implementation of an {@link Line2D 2D line}
 * @author Julien SEINTURIER - <a href="http://www.univ-tln.fr">Universit&eacute; de Toulon</a> / <a href="http://www.lis-lab.fr">CNRS LIS umr 7020</a> - <a href="https://github.com/jorigin/jcommon">github.com/jorigin/jcommon</a> (<a href="mailto:contact@jorigin.org">contact@jorigin.org</a>)
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
public class SimpleLine2D implements Line2D {

	/**
	 * the serial version UID.
	 * <p>
	 * This is used to ensure that the class can be serialized and deserialized correctly.
	 * </p>
	 * <p>
	 * It is recommended to use a unique value for each class, so that the serialized data can be
	 * read back correctly.
	 * </p>
	 */
	private static final long serialVersionUID = Jeometry.BUILD;
	
	/**
	 * The first extremity (starting point) of the line
	 */
	private Point2D start = null;

	/**
	 * The second extremity (ending point) of the line
	 */
	private Point2D end = null;
	
	/**
	 * The spatial localization of the line its middle point)
	 */
	private Point2D localization = null;
	
	/**
	 * Create a new simple implementation of an {@link Line2D 2D line}
	 * with the given extremities.
	 * @param start the first extremity (starting point) of the line
	 * @param end the second extremity (ending point) of the line
	 */
	public SimpleLine2D(final Point2D start, final Point2D end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	public Point2DContainer getVertices() {
		Point2DContainer p = JeometryFactory.createPoint2DContainer(2);
		
		p.add(this.start);
		p.add(this.end);
		
		return p;
	}

	@Override
	public int getVerticesCount() {
		return 2;
	}

	@Override
	public Point2D getVertex(int index) {
		if (index == 0) {
			return this.start;
		} else if (index == 1) {
			return this.end;
		}
		return null;
	}

	@Override
	public Point2D getVertex(int index, Point2D output) {
		
		Point2D tmp = null;
		
		if (output == null) {
			tmp = JeometryFactory.createPoint2D();
		} else {
			tmp = output;
		}
		
		if (index == 0) {
			tmp.setValues(this.start);
		} else if (index == 1) {
			tmp.setValues(this.end);
		}
		return tmp;
	}

	@Override
	public VertexGeometry2D reverseVertices() {
		Point2D tmp = this.start;
		this.start = this.end;
		this.end = tmp;
		
		return this;
	}

	@Override
	public boolean geomEquals(Geometry2D g) {
		
		if (g == null) {
			return false;
		}
		
		if (g == this) {
			return true;
		}
		
		if ((g instanceof Line2D) || (g instanceof LineString2D)) {
			VertexGeometry2D vg = (VertexGeometry2D)g;
			
			if (vg.getVerticesCount() != 2) {
				return false;
			}
			
			Point2D v0 = vg.getVertex(0);
			Point2D v1 = vg.getVertex(1);
			
			return     ( SimpleGeometry2DUtils.geomEquals(this.start, v0) && SimpleGeometry2DUtils.geomEquals(this.end, v1)) 
					|| ( SimpleGeometry2DUtils.geomEquals(this.start, v1) && SimpleGeometry2DUtils.geomEquals(this.end, v0));
		}
		
		return false;
	}

	@Override
	public boolean geomIntersects(Geometry2D g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean geomTouches(Geometry2D g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean geomContains(Geometry2D g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean geomCovers(Geometry2D g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean geomEmpty() {
		return (this.start == null) || (this.end == null) || (this.start.geomEquals(this.end));
	}

	@Override
	public int geomDimension() {
		return 1;
	}

	@Override
	public Box2D geomBounds() {
		if ((this.start == null) || (this.end == null))
			return null;
		
		if(this.start.geomEquals(this.end)) {
			return JeometryFactory.createBox2D(this.start, this.end);
		}
				
		return JeometryFactory.createBox2D(Math.min(this.start.getX(), this.end.getX()), Math.min(this.start.getY(), this.end.getY()), 
										   Math.max(this.start.getX(), this.end.getX()), Math.max(this.start.getY(), this.end.getY()));
		
	}

	@Override
	public double getX() {
		if (this.localization == null)
			return Double.NaN;
		
		return this.localization.getX();
	}

	@Override
	public double getY() {
		if (this.localization == null)
			return Double.NaN;
		
		return this.localization.getY();
	}

	@Override
	public Point2D getMinCoordinates() {
		
		if ((this.start == null) || (this.end == null))
			return null;
		
		return JeometryFactory.createPoint2D(Math.min(this.start.getX(), this.end.getX()), Math.min(this.start.getY(), this.end.getY()));
		
	}

	@Override
	public Point2D getMaxCoordinates() {
		
		if ((this.start == null) || (this.end == null))
			return null;
		
		return JeometryFactory.createPoint2D(Math.max(this.start.getX(), this.end.getX()), Math.max(this.start.getY(), this.end.getY()));
		
	}

	@Override
	public double distance(SpatialLocalization2D spatial) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateLocalization() {
		if ((this.start == null) && (this.end == null))
			this.localization = this.start.plus(this.end).divide(2.0);
		else
			this.localization = null;
	}

	@Override
	public Point2D getEnd1() {
		return this.start;
	}

	@Override
	public Point2D getEnd2() {
		return this.end;
	}

}
