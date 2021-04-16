package org.jeometry.geom3D.algorithm.bounds;

import java.util.Collection;
import java.util.Iterator;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.geom3D.primitive.Box;

/**
 * A class that enable to Compute the axis aligned bounding box for the spatial objects given in parameter.
 * @author Julien SEINTURIER (<a href="https://github.com/jorigin">JOrigin</a>) - <a href="https://github.com/jseinturier">https://github.com/jseinturier</a>
 *
 */
public class AxisAlignedBoundingBox {
	  /**
	   * Compute the axis aligned bounding box for the points given in parameter. If the point
	   * manager is <code>null</code> or empty, <code>null</code> is returned. 
	   * @param points the points to englobe in the box
	   * @return the axis aligned bounding box
	   */
	  public static Box computeAxisAlignedBoundingBox(Collection<? extends Point3D> points){
	    
	    Point3D pMax = null;
	    Point3D pMin = null;
	    Point3D pt   = null;
	    
	    Box polyhedron = null;
	    
	    // Une boite englobante n'est retournée que s'il existe des points à englober.
	    if ((points == null) || (points.size() < 3)){
	      polyhedron = null;
	    } else {
	      
	      // On calcule les sommets de la boite englobante
	      pMax = JeometryFactory.createPoint3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	      pMin = JeometryFactory.createPoint3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	   
	      Iterator<? extends Point3D> iter = points.iterator();
	      while(iter.hasNext()){
	        
	        pt = iter.next();
	        
	        if (pMin.getX() > pt.getX()){
	          pMin.setX(pt.getX());
	        }
	        
	        if (pMin.getY() > pt.getY()){
	          pMin.setY(pt.getY());
	        }
	        
	        if (pMin.getZ() > pt.getZ()){
	          pMin.setZ(pt.getZ());
	        }
	        
	        if (pMax.getX() < pt.getX()){
	          pMax.setX(pt.getX());
	        }
	        
	        if (pMax.getY() < pt.getY()){
	          pMax.setY(pt.getY());
	        }
	        
	        if (pMax.getZ() < pt.getZ()){
	          pMax.setZ(pt.getZ());
	        }
	        
	        pt = null;
	      }

	      // Creation d'un boite parallele aux axes.
	      polyhedron = JeometryFactory.createBox(pMin, pMax);
	    }
	    
	    return polyhedron;
	  }
	   
	  /**
	   * Compute the axis aligned bounding box for the points given in parameter. If the point
	   * manager is <code>null</code> or empty, <code>null</code> is returned. 
	   * @param points the points to englobe in the box
	   * @return the axis aligned bounding box
	   */
	  public static Box computeAxisAlignedBoundingBox(Point3DContainer<?> points){
	    
	    Point3D pMax = null;
	    Point3D pMin = null;
	    Point3D pt   = null;
	    
	    Box polyhedron = null;
	    
	    // Une boite englobante n'est retournée que s'il existe des points à englober.
	    if ((points == null) || (points.size() < 3)){
	      polyhedron = null;
	    } else {
	      
	      // On calcule les sommets de la boite englobante
	      pMax = JeometryFactory.createPoint3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	      pMin = JeometryFactory.createPoint3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	   
	      for(int i = 0; i < points.size(); i++){
	        
	        pt = points.get(i);
	        
	        if (pMin.getX() > pt.getX()){
	          pMin.setX(pt.getX());
	        }
	        
	        if (pMin.getY() > pt.getY()){
	          pMin.setY(pt.getY());
	        }
	        
	        if (pMin.getZ() > pt.getZ()){
	          pMin.setZ(pt.getZ());
	        }
	        
	        if (pMax.getX() < pt.getX()){
	          pMax.setX(pt.getX());
	        }
	        
	        if (pMax.getY() < pt.getY()){
	          pMax.setY(pt.getY());
	        }
	        
	        if (pMax.getZ() < pt.getZ()){
	          pMax.setZ(pt.getZ());
	        }
	        
	        pt = null;
	      }

	      // Creation d'un boite parallele aux axes.
	      polyhedron = JeometryFactory.createBox(pMin, pMax);
	    }
	    
	    return polyhedron;
	  }

	  
	  
	  /**
	   * Compute an axis oriented bounding box for the polyhedron given in parameter. If the polyhedron
	   * is <code>null</code> or if it contains no vertices, <code>null</code> is returned. 
	   * @param polyhedron the polyhedron to fit in the bounding box
	   * @return the axis aligned bounding box for the polyhedron.
	   */
	  public static Box computeAxisAlignedBoundingBox(Mesh<?> polyhedron){
	    if (polyhedron == null){
	      return null;
	    } else {
	      return computeAxisAlignedBoundingBox(polyhedron.getVertices());
	    }
	  }
}
