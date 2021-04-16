package org.jeometry.geom3D.algorithm.fitting;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.point.Coord3D;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Plane;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;

/**
 * Compute a plane from a {@link Point3DContainer set of 3D points} using a <a href="http://mathworld.wolfram.com/LeastSquaresFitting.html">Linear Least Square</a> based method.
 * The method works as follows:<br><br>
 * Let a plane defined by <i>C</i>&nbsp;+&nbsp;<i>X</i>&nbsp;&times;&nbsp;<i>N</i>&nbsp;=&nbsp;<i>0</i> where:
 * <ul>
 * <li><i>C</i> is the center of the plane.
 * <li><i>N</i> is the normal vector to the plane.
 * <li><i>X</i> is a 3D point that lies on the plane.
 * </ul>
 * Let <i>P</i> a 3D point, the expression of <i>P</i> within plane coordinate can be written:<br><br>
 * <i>P</i>&nbsp;=&nbsp;<i>C</i>&nbsp;+&nbsp;<i>&mu;N</i>&nbsp;+&nbsp;<i>pN</i><sup>&perp;</sup><br><br>
 * Where:<br>
 * <ul>
 * <li>&mu; is the distance from the point to the plane.
 * <li><i>N</i><sup>&perp;</sup> is a 2-by-3 matrix representing the perpendicular to the plane's normal.
 * <li><i>p</i> is a 2-vector of co-factors.
 * </ul>
 * Finding a place from a set of points can be assimilated to the minimization of the quantity E defined such as:<br><br>
 * E&nbsp;=&nbsp;&Sigma;(&mu;<sup>2</sup>,&nbsp;points)<br><br>
 * 
 * 
 * Source: <a href="http://missingbytes.blogspot.com/2012/06/fitting-plane-to-point-cloud.html">http://missingbytes.blogspot.com/2012/06/fitting-plane-to-point-cloud.html</a>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class PlaneFitting {
	
	/**
	 * Compute the square distance between the two given vectors.
	 * @param u the first vector
	 * @param v the second vector
	 * @return the square distance between the two vectors
	 */
	private static double DistanceSquared(Vector u, Vector v) {
		
		return    (v.getValue(0) - u.getValue(0)) * (v.getValue(0) - u.getValue(0))
				+ (v.getValue(1) - u.getValue(1)) * (v.getValue(1) - u.getValue(1))
				+ (v.getValue(2) - u.getValue(2)) * (v.getValue(2) - u.getValue(2));
	}
	
	/**
	 * Compute the largest entry from the given matrix.
	 * @param m the matrix
	 * @return the largest entry
	 */
	private static double FindLargestEntry(Matrix m){
	    double result=0.0f;
	    for(int i=0;i<3;i++){
	        for(int j=0;j<3;j++){
	            double entry=Math.abs(m.getValue(i,j));
	            result=Math.max(entry,result);
	        }
	    }
	    return result;
	}

	/**
	 * Compute the Eigen vector associated with the largest Eigen value for the given matrix. 
	 * This function will perform badly if the largest eigenvalue is complex.
	 * @param m the matrix 
	 * @param iterationMax the maximum number of iterations to process
	 * @param limit the numerical limit
	 * @return the Eigen vector associated with the largest Eigen value for the given matrix
	 */
	private static Point3D FindEigenVectorAssociatedWithLargestEigenValue(Matrix m, double iterationMax, double limit){
	    //pre-condition
	    double scale=FindLargestEntry(m);
	    Matrix mc = m.multiply(1.0f/scale);
	    mc=mc.multiply(mc);
	    mc=mc.multiply(mc);
	    mc=mc.multiply(mc);
	    Vector v = JeometryFactory.createPoint3D(1.0, 1.0, 1.0);
	    Vector lastV = v;
	    for(int i=0; i < iterationMax; i++){
	    	v = mc.multiply(v);
	        v.normalize();
	      
	        if(DistanceSquared(v,lastV) < limit){
	            break;
	        }
	        lastV=v;
	    }
	    
	    return JeometryFactory.createPoint3D(v.getValue(Coord3D.DIMENSION_X), 
	    		                             v.getValue(Coord3D.DIMENSION_Y), 
	    		                             v.getValue(Coord3D.DIMENSION_Z));
	} 

   /**
    * Fit a {@link Plane plane} from a set of  {@link Point3DContainer set of 3D points} using a <a href="http://mathworld.wolfram.com/LeastSquaresFitting.html">Linear Least Square</a> based method.
    * @param points the input points (at least 3 points are needed.)
    * @param iterationMax the maximum number of iterations durint the LEast Square computation
    * @param limit the numerical limit under which a distance is considered as <i>0</i>
    * @return The fitted {@link Plane plane} or <code>null</code> if no plane can be fitted.
    */
	public static Plane<Point3D> fit(Point3DContainer<?> points, double iterationMax, double limit){

		Plane<Point3D> plane = null;
		
		if ((points != null) && (points.size() >= 3)){
			
			Point3D origin = JeometryFactory.createPoint3D(0.0d, 0.0d, 0.0d);
			Point3D normal = null;
			
		    Point3D sum = JeometryFactory.createPoint3D(0.0d, 0.0d, 0.0d);
		    for(int i=0;i<points.size();i++){
		    	sum.plusAffect(points.get(i));
		    }
		    
		    Point3D center = sum.multiply(1.0f/points.size());
		    if(origin != null){
		    	origin=center;
		    }
		    
		    double sumXX = 0.0f;
		    double sumXY = 0.0f;
		    double sumXZ = 0.0f;
		    double sumYY = 0.0f;
		    double sumYZ = 0.0f;
		    double sumZZ = 0.0f;
		    
		    for(int i=0;i<points.size();i++){
		    	double diffX = points.get(i).getX() - center.getX();
		    	double diffY = points.get(i).getY() - center.getY();
		    	double diffZ = points.get(i).getZ() - center.getZ();
		        
		        sumXX+=diffX*diffX;
		        sumXY+=diffX*diffY;
		        sumXZ+=diffX*diffZ;
		        sumYY+=diffY*diffY;
		        sumYZ+=diffY*diffZ;
		        sumZZ+=diffZ*diffZ;
		        
		    }
		    
		    Matrix m = JeometryFactory.createMatrix(new double[][] {{sumXX,sumXY,sumXZ},
		    	                                                    {sumXY,sumYY,sumYZ}, 
		    	                                                    {sumXZ,sumYZ,sumZZ}});

		    double det=m.determinant();
		    
		    if(det==0.0d){
		    	//TODO Implements PlaneFitting for 0 determinant
		    	//m.GetNullSpace(destNormal,NULL,NULL);
		        return null;
		    }
		    
		    Matrix mInverse = m.invert();
		    normal = FindEigenVectorAssociatedWithLargestEigenValue(mInverse, iterationMax, limit);

		    plane = JeometryFactory.createPlane(origin, normal);
		    
		}
		
		return plane;
	}
}
