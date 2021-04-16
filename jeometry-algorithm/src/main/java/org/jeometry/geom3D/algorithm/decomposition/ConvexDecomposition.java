package org.jeometry.geom3D.algorithm.decomposition;

import java.util.ArrayList;
import java.util.List;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.Geom3D;
import org.jeometry.geom3D.mesh.Edge;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;

/**
 * This class enable to compute a convex decomposition for a given polyhedron.<br><br>
 * 
 * The convex decomposition split a non convex polyhedron is a set of convex polyhedra such
 * that their union is the original polyhedra.<br><br>
 * This class implements the algorithm given in
 * <i>"An Algorithm for Convex Decomposition for polyedral object", Proceeding of the Seventh
 * International conference on computer aided Design
 * and Computer Graphics, International Academic Publishers, August 22-24 2001</i>
 * by Wang Fei, LIU Wen-Yu, Li Huain<br>.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class ConvexDecomposition {
	
	  /**
	   * Compute a return a set of convex polyhedra which are the convex decomposition
	   * of the polyhedron given in parameter. A convex decomposition is a set of
	   * polyhedron that their disjunction compose the initial polyhedron. The algoritm
	   * used for the computation is given by Wang Fei, LIU Wen-Yu, Li Huain<br>
	   * A Algorithm for Convex Decomposition for polyedral object<br>
	   * <i>Proceeding of the Seventh International conference on computer aided Design
	   * and Computer Graphics</i><br>
	   * International Academic Publishers, August 22-24 2001<br>
	   * @param <T> The type of underlying 3D points
	   * @param polyhedron the polyhedron to decompose
	   * @return the list of convex polyedra composing the initial polyhedron
	   */
	  public static <T extends Point3D> List<Mesh<T>> computeConvexDecomposition(Mesh<T> polyhedron){
	    List<Mesh<T>> convexPolyhedrons = null;
	    List<Edge<T>> concaveEdges = null;


	    List<? extends Edge<T>> edges = new ArrayList<Edge<T>>(polyhedron.getEdges().size());

	    List<? extends Face<T>> faces = new ArrayList<Face<T>>(polyhedron.getFaces().size());

	    int faceCpt = 0;

	    // table de correspondance arrête / face. Les lignes du tableaux correspondent
	    // aux indices des arrêtes du polyhedre. Les colonnes sont les indices des faces
	    // du polyhedre attaché à l'arrête.
	    int[][] edgeFaces = new int[polyhedron.getEdges().size()][2];


	    // Recuperation des tableaux des faces et des arrêtes
	    edges = polyhedron.getEdges();
	    faces = polyhedron.getFaces();


	    // 1 - Calcul des sommets et arêtes concaves
	    //   A) Calcul des faces attach�es à chaque arrêtes.
	    for(int i = 0; i < edges.size(); i++){
	      for(int j = 0; j < faces.size(); j++){
	        faceCpt = 0;
	        for(int k = 0; k < faces.get(j).getEdges().size(); k++) {
	          if (faces.get(j).getEdges().get(k).equals(edges.get(i))){
	            // La jieme face du polyedre est adjacente à l'arrête i.
	            edgeFaces[i][faceCpt] = j;
	            faceCpt++;

	            // Si deux faces on été trouvées pour l'arrête, inutile de chercher plus
	            if (faceCpt >= 2){
	              break;
	            }
	          }
	        }
	      }
	    }

	    //  B) D�termination de la concavité des arrêtes.
	    //     Une arr�te est concave ssi pour une arr�te (P1,P2) reliant 2 faces
	    //     A et B avec P3 un point de A et P4 un point de B la matrice:
	    //
	    //     [ x1  y1  z1  1 ]
	    //     [ x2  y2  z2  1 ]
	    //     [ x3  y3  z3  1 ]   avec (xi, yi, zi) sont les coordonn�es de Pi
	    //     [ x4  y4  z4  1 ]
	    //
	    //     on a det(M) < 0. Dans le cas contraire l'arrête est convexe.
	    Point3D p1 = null;
	    Point3D p2 = null;
	    Point3D p3 = null;
	    Point3D p4 = null;
	    concaveEdges = new ArrayList<Edge<T>>();
	    double[][] matrice = new double[4][4];
	    double det = 0.0d;
	    for(int i = 0; i < edgeFaces.length; i++){
	      // Recuperation des sommets de l'arrête
	      p1 = edges.get(i).getVertices().get(0);
	      p2 = edges.get(i).getVertices().get(1);

	      // Utilisation des barycentres des faces comme référence.
	      p3 = Geom3D.computeBarycenter(faces.get(edgeFaces[i][0]).getVertices());
	      p4 = Geom3D.computeBarycenter(faces.get(edgeFaces[i][1]).getVertices());

	      // Remplissage de la matrice 4*4 avant calcul du déterminant
	      matrice[0][0] = p1.getX();
	      matrice[0][1] = p1.getY();
	      matrice[0][2] = p1.getZ();
	      matrice[0][3] = 1;
	      matrice[1][0] = p2.getX();
	      matrice[1][1] = p2.getY();
	      matrice[1][2] = p3.getZ();
	      matrice[1][3] = 1;
	      matrice[2][0] = p3.getX();
	      matrice[2][1] = p3.getY();
	      matrice[2][2] = p3.getZ();
	      matrice[2][3] = 1;
	      matrice[4][0] = p4.getX();
	      matrice[4][1] = p4.getY();
	      matrice[4][2] = p4.getZ();
	      matrice[4][3] = 1;

	      det =   matrice[2][0] * matrice[3][1]
	            + matrice[1][0] * matrice[2][1] * matrice[3][2]
	            + matrice[0][0] * matrice[1][1] * matrice[2][2] * matrice[3][3]
	            + matrice[0][1] * matrice[1][2] * matrice[2][3]
	            + matrice[0][2] * matrice[1][3]
	            - matrice[0][1] * matrice[1][0]
	            - matrice[0][2] * matrice[1][1] * matrice[2][0]
	            - matrice[0][3] * matrice[1][2] * matrice[2][1] * matrice[3][0]
	            - matrice[1][3] * matrice[2][2] * matrice[3][1]
	            - matrice[2][3] * matrice[3][2];

	      // Si le déterminant est strictement positif, l'arrête est concave
	      if (det > 0){
	        concaveEdges.add(edges.get(i));
	      }
	    }

	    // 2 - Selection d'une arrête convexe et sauvegarde des deux sommets qui seront
	    //     arbitrairemenbt le départ et la fin d'une boucle
	    Edge<T> concaveEdge = concaveEdges.get(0);
	    T start = concaveEdge.getVertices().get(0);
	    T end   = concaveEdge.getVertices().get(1);

	    // 3 - Recherche d'une boucle dont les points de départ et d'arrivée sont les
	    //    sommets de l'arrête choisie.
	    Point3DContainer<Point3D> loop = JeometryFactory.createPoint3DContainer();
	    while(Geom3D.equals(start, end)){

	      //  3.1> Pour le sommet d'une arrête, recupération de tous les points avec
	      //       lesquels le sommet forme une face.
	      Point3DContainer<Point3D> linkedVertices = JeometryFactory.createPoint3DContainer();
	      boolean isVertexInFace = false;
	      for(int j = 0; j < polyhedron.getFaces().size(); j++){
	        // Recherche si la face contient le point d�sir�
	        for(int k = 0; k < faces.get(j).getVertices().size(); k++){
	          if (Geom3D.equals(start, faces.get(j).getVertices().get(k))){
	            isVertexInFace = true;
	          }
	        }

	        // Si la face contient bien le sommet, les autres points de la face sont
	        // ajoutés à liste des sommets liés. Un sommet ne peut apparaitre qu'une
	        // seule fois dans la liste.
	        if (isVertexInFace){
	          for(int k = 0; k < faces.get(j).getVertices().size(); k++){
	            linkedVertices.add(faces.get(j).getVertices().get(k));
	          }
	        }

	        // Si les sommets liés contiennent un sommet concave (sommet d'une arrête concave)
	        // alors ce sommet devient le premier sommet de la suite de la boucle.
	        for(int lk = 0; lk < linkedVertices.size(); lk++){
	          for(int ce = 0; ce < concaveEdges.size(); ce++){
	            if ((Geom3D.equals(linkedVertices.get(lk), concaveEdges.get(ce).getVertices().get(0)))
	              ||(Geom3D.equals(linkedVertices.get(lk), concaveEdges.get(ce).getVertices().get(1)))){
	              loop.add(linkedVertices.get(lk));
	            }
	          }
	        }

	        isVertexInFace = false;
	      }
	    }

	    return convexPolyhedrons;
	  }
}
