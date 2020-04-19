package org.jeometry.geom3D.algorithm;

import java.util.Iterator;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.mesh.indexed.IndexedEdge;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.mesh.indexed.IndexedMesh;
import org.jeometry.geom3D.neighbor.MeshNeighborhood;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;

/**
 * Computation of the neighborhood of a mesh. The neighborhood is made of an adjacency map and an incidence map. 
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class MeshNeighborhoodComputation {
  
  /**
   * Compute the neighborhood for the given {@link IndexedMesh indexed mesh}.
   * The neighborhood of a mesh is the set of all adjacency and all incidences available for a mesh.
   * @param <T> the type of underlying points
   * @param mesh the {@link IndexedMesh mesh} to process.
   * @return the mesh neighborhood.
   */
  public static <T extends Point3D> MeshNeighborhood<T> computeNeighborhood(IndexedMesh<T> mesh){

    MeshNeighborhood<T> neighborhood = null;
    
    List<? extends IndexedFace<T>> faces = mesh.getFacesIndexes();

    if ((faces != null)&&(mesh.getFacesIndexes().size() > 1)){
      
      Point3DContainer<?>  pm = mesh.getVerticesSource();
      
      if (pm != null){
        
        // 1. Create a mesh neighbothood
        neighborhood = JeometryFactory.createMeshNeighborhood();
        

        // 2. Populate incidences
        //    A face is incident to a vertex if this vertex compose a face.
        Iterator<? extends IndexedFace<T>> faceIter = faces.iterator();
        IndexedFace<T> face = null;
        while(faceIter.hasNext()){
          
          face = faceIter.next();
          
          for(int i = 0; i < face.getVerticesIndexes().length; i++){
            neighborhood.getIncidences().addIncident(face.getVerticesSource().get(face.getVerticesIndexes()[i]), face);
          }
        }
        
        // 3. Populate adjacences
        //    A face fa is adjacent to a face fb if fa is incident to the two extremity of a edge of fb. 
        faceIter = faces.iterator();
        face = null;
        while(faceIter.hasNext()){
          face = faceIter.next();
          
          // Parse the edges of the face
          Iterator<IndexedEdge<T>> edgeIter = face.getEdgesIndexed().iterator();
          IndexedEdge<T> edge = null;
          
          List<IndexedFace<T>> incidencesEnd1 = null;
          List<IndexedFace<T>> incidencesEnd2 = null;
          
          int incidencesEnd1Count = 0;
          int incidencesEnd2Count = 0;
          
          while(edgeIter.hasNext()){
            edge = edgeIter.next();
            
            // Looking for a face that is incident with both edge extremities.
            incidencesEnd1 = neighborhood.getIncidences().getIncidences(edge.getEnd1());
            incidencesEnd2 = neighborhood.getIncidences().getIncidences(edge.getEnd2());
            
            if ((incidencesEnd1 != null) && (incidencesEnd2 != null)){
              
              incidencesEnd1Count = incidencesEnd1.size();
              incidencesEnd2Count = incidencesEnd2.size();
              
              if ((incidencesEnd1Count > 0)&&(incidencesEnd2Count > 0)){
                
                List<IndexedFace<T>> reference = null;
                List<IndexedFace<T>> target    = null;
                
                // We parse the minimal incidence set in order to optimize computation time
                if (incidencesEnd1Count < incidencesEnd2Count){
                  reference = incidencesEnd1;
                  target    = incidencesEnd2;
                } else {
                  reference = incidencesEnd2;
                  target    = incidencesEnd1;
                }
                
                // Parsing the reference incidences and searching correspondence within the target.
                Iterator<IndexedFace<T>> referenceIter = reference.iterator();
                IndexedFace<T> referenceFace = null;
                while(referenceIter.hasNext()){
                  referenceFace = referenceIter.next();
                  
                  // A face does not adjacent to itself
                  if (referenceFace != face){
                    if (target.contains(referenceFace)){
                      neighborhood.getAdjacencies().addAdjacent(face, referenceFace);
                    }
                  }
                }
              }
              
            }
            
          }
        }
      }
    }
    
    return neighborhood;
  }

}
