package org.jeometry.geom3D.algorithm.bounds;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.Geom3D;
import org.jeometry.geom3D.algorithm.convexhull.quickhull.QuickHull;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.mesh.Mesh;
import org.jeometry.geom3D.point.ArrayListPoint3DContainer;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.point.Point3DContainer;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;
import org.jeometry.math.solver.Solver;

/**
 * This class is an implementation of the algorithm given in "<a href="https://www.sciencedirect.com/science/article/pii/S0925772104000100">Minimal Enclosing Parallelepiped in 3D</a>", december 2002 by
 * Frédéric Vivien and Nicolas Wicker (Laboratoire de l'Informatique du parallelisme,
 * Ecole Normale supérieure de Lyon, UMR cnrs-inria-ens Lyon 5668).
 * The complexity of the algorithm is <i>O</i>(<i>n log n</i> + <i>k</i>). The code is:
 * <p>
 * <code>
 * 1: Compute the convex hull C of the set of points S<br>
 * 2: N = NULL {The set of candidate supporting planes}<br>
 * 3: Let F be the set of all the faces of C<br>
 * 4: for each face f of F do<br>
 * 5: &nbsp;&nbsp;Find the vertex v of C which is the furthest from f<br>
 * 6: &nbsp;&nbsp;Associate to f the vector nf normal to f and linking f and v (v+nf is a point of f )<br>
 * 7: &nbsp;&nbsp;N = N [{( f , f −nf ,nf )}<br>
 * 8: Let E be the set of all the edges of C<br>
 * 9: for each pair {e1,e2} of elements of E do<br>
 * 10: &nbsp;&nbsp;if e1 and e2 are not parallel then<br>
 * 11: &nbsp;&nbsp;&nbsp;&nbsp;Build the planes f1 and f2 parallel to e1 and e2, f1 containing e1 and f2 including e2<br>
 * 12: &nbsp;&nbsp;&nbsp;&nbsp;Compute the vector nf1 normal to f1 (and thus to f2) such that f1+nf1 = f2<br>
 * 13: &nbsp;&nbsp;&nbsp;&nbsp;if C is enclosed in the space between the planes f1 and f2 then<br>
 * 14: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;N = N [{( f1, f2,nf1)}<br>
 * 15: vol_min = + infinite<br>
 * 16: planes = NULL<br>
 * 17: for each element ( f1, f01,n1) of N do<br>
 * 18: &nbsp;&nbsp;for each element ( f2, f02,n2) of N do<br>
 * 19: &nbsp;&nbsp;&nbsp;&nbsp;for each element ( f3, f03,n3) of N do<br>
 * 20: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if n1 ^n2.n3 != 0 then<br>
 * 21: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;vol = ||n1||^2 * ||n2||^2 * ||n3||^2 /  n1^n2.n3<br>
 * 22: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if vol &lt; vol_min then<br>
 * 23: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;vol_min = vol<br>
 * 24: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;planes = { f1, f01 , f2, f02, f3, f03 }<br>
 * 25: return planes<br>
 * </code>
 * </p>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 *
 */
public class MinimalEnclosingParallelepipede {

	/**
	 * The 0 limit. Under this value, a value is considered as equal to 0
	 */
	public static double ZERO_LIMIT = 0.000001d;

	/**
	 * Compute the minimal enclosing parallelepiped of a point set. 
	 * This method is an implementation of the algorithm given in "<a href="https://www.sciencedirect.com/science/article/pii/S0925772104000100">Minimal Enclosing Parallelepiped in 3D</a>", december 2002 by
	 * Frédéric Vivien and Nicolas Wicker (Laboratoire de l'Informatique du parallelisme,
	 * Ecole Normale supérieure de Lyon, UMR cnrs-inria-ens Lyon 5668).
	 * The complexity of the algorithm is <i>O</i>(<i>n log n</i> + <i>k</i>). The code is:
	 * <p>
	 * <code>
	 * 1: Compute the convex hull C of the set of points S<br>
	 * 2: N = NULL {The set of candidate supporting planes}<br>
	 * 3: Let F be the set of all the faces of C<br>
	 * 4: for each face f of F do<br>
	 * 5: &nbsp;&nbsp;Find the vertex v of C which is the furthest from f<br>
	 * 6: &nbsp;&nbsp;Associate to f the vector nf normal to f and linking f and v (v+nf is a point of f )<br>
	 * 7: &nbsp;&nbsp;N = N [{( f , f −nf ,nf )}<br>
	 * 8: Let E be the set of all the edges of C<br>
	 * 9: for each pair {e1,e2} of elements of E do<br>
	 * 10: &nbsp;&nbsp;if e1 and e2 are not parallel then<br>
	 * 11: &nbsp;&nbsp;&nbsp;&nbsp;Build the planes f1 and f2 parallel to e1 and e2, f1 containing e1 and f2 including e2<br>
	 * 12: &nbsp;&nbsp;&nbsp;&nbsp;Compute the vector nf1 normal to f1 (and thus to f2) such that f1+nf1 = f2<br>
	 * 13: &nbsp;&nbsp;&nbsp;&nbsp;if C is enclosed in the space between the planes f1 and f2 then<br>
	 * 14: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;N = N [{( f1, f2,nf1)}<br>
	 * 15: vol_min = + infinite<br>
	 * 16: planes = NULL<br>
	 * 17: for each element ( f1, f01,n1) of N do<br>
	 * 18: &nbsp;&nbsp;for each element ( f2, f02,n2) of N do<br>
	 * 19: &nbsp;&nbsp;&nbsp;&nbsp;for each element ( f3, f03,n3) of N do<br>
	 * 20: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if n1 ^n2.n3 != 0 then<br>
	 * 21: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;vol = ||n1||^2 * ||n2||^2 * ||n3||^2 /  n1^n2.n3<br>
	 * 22: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if vol &lt; vol_min then<br>
	 * 23: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;vol_min = vol<br>
	 * 24: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;planes = { f1, f01 , f2, f02, f3, f03 }<br>
	 * 25: return planes<br>
	 * </code>
	 * </p>
	 * @param p3dm Point3DManagerI The points to enclose
	 * @return IPolyhedron The polyhedron representing the parallelepiped
	 *
	 */
	public static Mesh<Point3D> computeMinimalEnclosingParallelepiped(Point3DContainer<Point3D> p3dm){

		//  L'algorithme se base sur l'enveloppe convexe du nuage de points
		Mesh<Point3D> c = QuickHull.computeConvexHull(p3dm, true);

		if (c == null){
			return null;
		}

		Point3DContainer<?>[] antipodals = new Point3DContainer[c.getFaces().size()];
		Point3D[] normals = new Point3D[c.getFaces().size()];
		double[] thicknesses = new double[c.getFaces().size()];
		double norme;


		antipodals =  polyhedronAntipodals(c, thicknesses, normals);


		for(int i=0;i<c.getFaces().size();i++){
			// normales[i][0]=_facettes[i][0];
			// normales[i][1]=_facettes[i][1];
			// normales[i][2]=_facettes[i][2];


			norme = Math.sqrt(  normals[i].getX()*normals[i].getX()
					+ normals[i].getY()*normals[i].getY()
					+ normals[i].getZ()*normals[i].getZ());

			normals[i].setX(normals[i].getX() * (thicknesses[i]/norme));
			normals[i].setY(normals[i].getY() * (thicknesses[i]/norme));
			normals[i].setZ(normals[i].getZ() * (thicknesses[i]/norme));
		}

		int[] triplet_solution = new int[3];

		minimalEnclosingParallelepiped_bestTriplet(triplet_solution, c, normals);

		return minimalEnclosingParallelepiped_computeParallelepiped(triplet_solution, c, normals, antipodals);
	}

	/**
	 * Compute the antipodals points of the given {@link Polyhedron polyhedron}.
	 * @param polyhedron the polyhedron.
	 * @param thicknesses the thicknesses of all the box involved.
	 * @param normals the normal at the points
	 * @return the antipodals of the given polyhedron.
	 */
	private static Point3DContainer<Point3D>[] polyhedronAntipodals(Mesh<?> polyhedron, double[] thicknesses, Point3D[] normals){

		Collection<? extends Face<?>> faces = polyhedron.getFaces();

		Point3DContainer<?> vertices = polyhedron.getVertices();

		// Distance courante entre une face et son antipode
		double distance    = -1.0d;

		// Distance maximale entre une face et son antipode
		double distanceMax = -1.0d;

		Point3D v1     = null;
		Point3D v2     = null;
		Point3D normal = null;

		// Parametres du plan portant la face
		double a = 0.0d;
		double b = 0.0d;
		double c = 0.0d;
		double d = 0.0d;

		// Ensemble de points manager représentant
		@SuppressWarnings("unchecked")
		Point3DContainer<Point3D>[] antipodals = new Point3DContainer[faces.size()];

		// Variables de parcours de la liste des faces
		Iterator<? extends Face<?>> iter = null;
		Face<?> currentFace = null;

		// Variable de parcours des antipodes
		int i = 0;

		iter = faces.iterator();
		i = 0;
		while(iter.hasNext()){
			//   for(int i=0;i<faces.size();i++){

			distanceMax=-1.0d;
			antipodals[i] = new ArrayListPoint3DContainer<Point3D>();

			currentFace = iter.next();

			// Calcul des vecteurs de base du plan
			v1 = currentFace.getVertices().get(1).minus(currentFace.getVertices().get(0));
			v2 = currentFace.getVertices().get(2).minus(currentFace.getVertices().get(0));

			// Calcul et sauvegarde de la normale au plan
			normal = Geom3D.cross(v1, v2);
			normals[i] = normal;

			// Affectation des 3 premières constantes de l'equation du plan
			a = normal.getX();
			b = normal.getY();
			c = normal.getZ();

			// Calcul de d en fonction de a, b et c
			d = - a * currentFace.getVertices().get(0).getX()
					- b * currentFace.getVertices().get(0).getY()
					- c * currentFace.getVertices().get(0).getZ();

			for(int j=0;j<vertices.size();j++){
				// Calcul de la distance entre le point courant et le plan defini par la face
				// courante. Pour un point p = (xp, yp, zp) et un plan d'equation ax + by + cz + d = 0
				// la distance du point au plan est de:
				// (a * xp + b * yp + c * zp + d) / sqrt(a*a + b*b + c*c)
				distance = Math.abs((  a*vertices.get(j).getX()
						+ b*vertices.get(j).getY()
						+ c*vertices.get(j).getZ()
						+ d)
						/ Math.sqrt(a*a+b*b+c*c));

				// Si la distance obtenue est strictement supérieure à la distance maximale, le point
				// courant est le seul antipode de la face.
				if(distance>distanceMax+ZERO_LIMIT){
					distanceMax=distance;
					// Effacement de tous les anciens antipodes
					if (antipodals[i].size() > 0){
						antipodals[i].clear();
					}

					// Ajout du nouvel antipode
					antipodals[i].add(vertices.get(j));
				}

				// Sinon, si la distance est egale à la distance max, le point courant est aussi
				// un antipode de la face courante
				else if(Math.abs(distance-distanceMax)<ZERO_LIMIT){
					// Ajout d'un nouvel antipode
					antipodals[i].add(vertices.get(j));
				}
			}

			// Calcul de l'epaisseur maximale du parallelepipede dont une face contient la face
			// courante de la boite englobante et contenant tous les points du polyhedre
			thicknesses[i]=distanceMax;


			i++;
		}


		return antipodals;
	}


	private static double minimalEnclosingParallelepiped_bestTriplet(int[] triplet_solution, Mesh<? extends Point3D> polyhedron, Point3D[] normals){

		// Faces du polyhedre récupérées dans un tableau
		Face<?>[] faces = new Face[polyhedron.getFaces().size()];
		faces = polyhedron.getFaces().toArray(faces);

		// Table des epaisseurs
		double[] thicknesses = new double[faces.length];

		// Recuperation des antipodes du polyhedre
		Point3DContainer<Point3D>[] antipodals = polyhedronAntipodals(polyhedron, thicknesses, normals);

		// Nombre de sommets par face
		int _nb_sommets_par_facette = 3;



		int _nb_facettes = polyhedron.getFaces().size();

		int taille;
		int compteur;
		int[][] candidats_des_facettes;



		Point3D vecteur_temp = null;
		double produit_temp1;
		double produit_temp2;
		double volume_temporaire;
		double volume_min;
		double scalaire_temp;
		int face_2, face_3;
		int depart_1, depart_2, longueur_1, longueur_2;
		int[] nb_candidats_par_facette = new int[_nb_facettes];


		candidats_des_facettes=new int [_nb_facettes][100];

		for(int i=0; i<_nb_facettes; i++){
			candidats_des_facettes[i] = null;
		}

		int[] temp_candidats = new int[_nb_facettes];


		// Calcule le maximum de vecteurs à tester
		taille = 3;
		int taille_temp;
		for(int i=0;i<_nb_facettes;i++){
			taille_temp=3*antipodals[i].size();
			if (taille_temp>taille){
				taille = taille_temp;
			}
		}

		Point3D[] vecteurs     = new Point3D[taille];
		Point3D[] vecteurs_bis = new Point3D[taille];


		// Pour chaque facette, la fonction calcule:
		// 1) L'ensemble des vecteurs formés par les sommets de la face et ses antipodes
		// 2)
		for(int i=0;i<_nb_facettes;i++){
			nb_candidats_par_facette[i]=0;

			taille=_nb_sommets_par_facette * antipodals[i].size();

			//      System.out.println("Face "+i+" antipodals: "+antipodals[i].size()+" Vertices: "+_nb_sommets_par_facette);

			compteur=0;

			// 1) Calcul de tous les vecteurs entre les points d'une face et ses antipodes
			for(int j=0;j<faces[i].getVertices().size();j++){
				for(int k=0;k<antipodals[i].size();k++){
					vecteurs[compteur] = faces[i].getVertices().get(j).minus(antipodals[i].get(k));
					compteur++;
				}
			}


			for(int j=i+1;j<faces.length;j++){
				if (minimalEnclosingParallelepiped_tesCandidat(vecteurs,normals[j])){

					compteur=0;
					for(int k=0;k<faces[j].getVertices().size();k++){
						for(int l=0;l<antipodals[j].size();l++){
							vecteurs_bis[compteur] = faces[j].getVertices().get(k).minus(antipodals[j].get(l));
							compteur++;
						}
					}
					if (minimalEnclosingParallelepiped_tesCandidat(vecteurs_bis,normals[i])){
						temp_candidats[nb_candidats_par_facette[i]] = j;
						nb_candidats_par_facette[i]++;
					}
				}
			}

			candidats_des_facettes[i]=new int[nb_candidats_par_facette[i]];
			for(int j=0; j<nb_candidats_par_facette[i]; j++){
				candidats_des_facettes[i][j] = temp_candidats[j];
			}
		}


		volume_min=-1.0;

		for(int i=0;i<_nb_facettes;i++){
			produit_temp1=thicknesses[i]*thicknesses[i];
			for(int j=0;j<nb_candidats_par_facette[i];j++)
			{
				face_2 = candidats_des_facettes[i][j];

				vecteur_temp = Geom3D.cross(normals[i], normals[face_2]);

				produit_temp2=thicknesses[face_2]*thicknesses[face_2]*produit_temp1;

				depart_1   = j+1;
				longueur_1 =  nb_candidats_par_facette[i];
				depart_2   = 0;
				longueur_2 = nb_candidats_par_facette[face_2];

				while((depart_1<longueur_1)&&(depart_2<longueur_2)){
					while ((depart_1<longueur_1)&&
							(depart_2<longueur_2)&&
							(candidats_des_facettes[i][depart_1]!=candidats_des_facettes[face_2][depart_2])
							){
						if (candidats_des_facettes[i][depart_1]<candidats_des_facettes[face_2][depart_2])
							depart_1++;
						else
							depart_2++;
					}
					if ((depart_1<longueur_1)&&(depart_2<longueur_2)){
						face_3 = candidats_des_facettes[i][depart_1];
						scalaire_temp = Math.abs(vecteur_temp.dot(normals[face_3]));

						if(scalaire_temp>ZERO_LIMIT)
						{
							volume_temporaire=Math.abs(produit_temp2*thicknesses[face_3]*thicknesses[face_3]/scalaire_temp);

							if((volume_min<0)||(volume_temporaire<volume_min))
							{
								volume_min=volume_temporaire;
								triplet_solution[0]=i;
								triplet_solution[1]=face_2;
								triplet_solution[2]=face_3;

							}
						}
						depart_1++;
						depart_2++;
					}
				}
			}
		}

		return volume_min;
	}

	private static Mesh<Point3D> minimalEnclosingParallelepiped_computeParallelepiped(int[] triplet_solution, Mesh<?> polyhedron, Point3D[] normals, Point3DContainer<?>[] antipodals){

		// Le polyhedre representant le parallelepipede
		Mesh<Point3D> parallelepiped = null;

		double[] P1               = new double[4];
		double[] P2               = new double[4];
		double[] P3               = new double[4];
		double[] P1prime          = new double[4];
		double[] P2prime          = new double[4];
		double[] P3prime          = new double[4];

		// Sommets du parallelepipede
		double[][] parallelepipedVertices = new double[8][3];

		boolean computationOK = true;

		Point3D point0 = null;

		// Variables pour la réslution de systèmes d'equations
		double[][] coefficientsData = null;

		// Calcul de l'hyperplan de chaque la première face du triplet
		//    point0 = Geometry.newPoint3D(polyhedron.getFaces().get(triplet_solution[0]).getVertices().get(0));//
		point0 = antipodals[triplet_solution[0]].get(0);
		P1[0] = normals[triplet_solution[0]].getX();
		P1[1] = normals[triplet_solution[0]].getY();
		P1[2] = normals[triplet_solution[0]].getZ();
		P1[3] = -(  point0.getX() * normals[triplet_solution[0]].getX()
				+ point0.getY() * normals[triplet_solution[0]].getY()
				+ point0.getZ() * normals[triplet_solution[0]].getZ());


		P1prime[0] = P1[0];
		P1prime[1] = P1[1];
		P1prime[2] = P1[2];

		P1prime[3]=  P1prime[0] * antipodals[triplet_solution[0]].get(0).getX()
				+ P1prime[1] * antipodals[triplet_solution[0]].get(0).getY()
				+ P1prime[2] * antipodals[triplet_solution[0]].get(0).getZ();



		//    point0 = Geometry.newPoint3D(polyhedron.getFaces().get(triplet_solution[1]).getVertices().get(0));
		point0 = antipodals[triplet_solution[1]].get(0);

		P2[0] = normals[triplet_solution[1]].getX();
		P2[1] = normals[triplet_solution[1]].getY();
		P2[2] = normals[triplet_solution[1]].getZ();
		P2[3] = -(  point0.getX() * normals[triplet_solution[1]].getX()
				+ point0.getY() * normals[triplet_solution[1]].getY()
				+ point0.getZ() * normals[triplet_solution[1]].getZ());


		P2prime[0] = P2[0];
		P2prime[1] = P2[1];
		P2prime[2] = P2[2];
		P2prime[3] =  P2prime[0] * antipodals[triplet_solution[1]].get(0).getX()
				+ P2prime[1] * antipodals[triplet_solution[1]].get(0).getY()
				+ P2prime[2] * antipodals[triplet_solution[1]].get(0).getZ();



		//    point0 = Geometry.newPoint3D(polyhedron.getFaces().get(triplet_solution[2]).getVertices().get(0));
		point0 = antipodals[triplet_solution[2]].get(0);
		P3[0] = normals[triplet_solution[2]].getX();
		P3[1] = normals[triplet_solution[2]].getY();
		P3[2] = normals[triplet_solution[2]].getZ();
		P3[3] = -(  point0.getX() * normals[triplet_solution[2]].getX()
				+ point0.getY() * normals[triplet_solution[2]].getY()
				+ point0.getZ() * normals[triplet_solution[2]].getZ());


		P3prime[0] = P3[0];
		P3prime[1] = P3[1];
		P3prime[2] = P3[2];
		P3prime[3]=   P3prime[0] * antipodals[triplet_solution[2]].get(0).getX()
				+ P3prime[1] * antipodals[triplet_solution[2]].get(0).getY()
				+ P3prime[2] * antipodals[triplet_solution[2]].get(0).getZ();


		// Calcul des 8 sommets du parallelepipède. Il s'agit de l'intersection des
		// 3 plans du triplet selectionné
		Matrix coefficients;
		Vector constants;
		Vector solution;

		Solver solver = JeometryFactory.createSolver();

		if (solver != null) {

			// Calcul du sommet 1 du parallelepipede
			coefficientsData = new double[3][3];
			for(int i=0;i<3;i++){
				coefficientsData[0][i]=P1[i];
				coefficientsData[1][i]=P2[i];
				coefficientsData[2][i]=P3[i];
			}

			coefficients = JeometryFactory.createMatrix(coefficientsData);
			constants    = JeometryFactory.createVector(new double[] {  P1[3], P2[3], P3[3] });
			solution     = JeometryFactory.createVector(constants);

			try {
				if (solver.solve(coefficients, constants, solution) != null) {
					parallelepipedVertices[0][0]  = solution.getValue(0);
					parallelepipedVertices[0][1]  = solution.getValue(1);
					parallelepipedVertices[0][3]  = solution.getValue(2);
				} else {
					Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system");
					parallelepipedVertices[0] = null;
					computationOK = false;
				}

			} catch (Exception e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system: "+e.getMessage(), e);
				parallelepipedVertices[0] = null;
				computationOK = false;
			}

			// Calcul du sommet 2 du parallelepipede
			for(int i=0;i<3;i++){
				coefficientsData[0][i]=P1[i];
				coefficientsData[1][i]=P2[i];
				coefficientsData[2][i]=P3prime[i];
			}

			coefficients = JeometryFactory.createMatrix(coefficientsData);
			constants    = JeometryFactory.createVector(new double[] {  P1[3], P2[3], P3prime[3]});
			solution     = JeometryFactory.createVector(constants);

			try {
				if (solver.solve(coefficients, constants, solution) != null) {
					parallelepipedVertices[1][0]  = solution.getValue(0);
					parallelepipedVertices[1][1]  = solution.getValue(1);
					parallelepipedVertices[1][3]  = solution.getValue(2);
				} else {
					Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system");
					parallelepipedVertices[1] = null;
					computationOK = false;
				}

			} catch (Exception e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system: "+e.getMessage(), e);
				parallelepipedVertices[1] = null;
				computationOK = false;
			}

			// Calcul du sommet 3 du parallelepipede
			for(int i=0;i<3;i++){
				coefficientsData[0][i] = P1[i];
				coefficientsData[1][i] = P2prime[i];
				coefficientsData[2][i] = P3prime[i];
			}

			coefficients = JeometryFactory.createMatrix(coefficientsData);
			constants    = JeometryFactory.createVector(new double[] {  P1[3], P2prime[3], P3prime[3]});
			solution     = JeometryFactory.createVector(constants);

			try {
				if (solver.solve(coefficients, constants, solution) != null) {
					parallelepipedVertices[2][0]  = solution.getValue(0);
					parallelepipedVertices[2][1]  = solution.getValue(1);
					parallelepipedVertices[2][3]  = solution.getValue(2);
				} else {
					Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system");
					parallelepipedVertices[2] = null;
					computationOK = false;
				}

			} catch (Exception e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system: "+e.getMessage(), e);
				parallelepipedVertices[2] = null;
				computationOK = false;
			}

			// Calcul du sommet 4 du parallelepipede
			for(int i=0;i<3;i++){
				coefficientsData[0][i] = P1[i];
				coefficientsData[1][i] = P2prime[i];
				coefficientsData[2][i] = P3[i];
			}

			coefficients = JeometryFactory.createMatrix(coefficientsData);
			constants    = JeometryFactory.createVector(new double[] {  P1[3], P2prime[3], P3[3]});
			solution     = JeometryFactory.createVector(constants);

			try {
				if (solver.solve(coefficients, constants, solution) != null) {
					parallelepipedVertices[3][0]  = solution.getValue(0);
					parallelepipedVertices[3][1]  = solution.getValue(1);
					parallelepipedVertices[3][3]  = solution.getValue(2);
				} else {
					Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system");
					parallelepipedVertices[3] = null;
					computationOK = false;
				}

			} catch (Exception e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system: "+e.getMessage(), e);
				parallelepipedVertices[3] = null;
				computationOK = false;
			}

			// Calcul du sommet 5 du parallelepipede
			for(int i=0;i<3;i++){
				coefficientsData[0][i] = P1prime[i];
				coefficientsData[1][i] = P2[i];
				coefficientsData[2][i] = P3[i];
			}

			coefficients = JeometryFactory.createMatrix(coefficientsData);
			constants    = JeometryFactory.createVector(new double[] {  P1prime[3], P2[3], P3[3]});
			solution     = JeometryFactory.createVector(constants);

			try {
				if (solver.solve(coefficients, constants, solution) != null) {
					parallelepipedVertices[4][0]  = solution.getValue(0);
					parallelepipedVertices[4][1]  = solution.getValue(1);
					parallelepipedVertices[4][3]  = solution.getValue(2);
				} else {
					Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system");
					parallelepipedVertices[4] = null;
					computationOK = false;
				}

			} catch (Exception e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system: "+e.getMessage(), e);
				parallelepipedVertices[4] = null;
				computationOK = false;
			}

			// Calcul du sommet 6 du parallelepipede
			for(int i=0;i<3;i++){
				coefficientsData[0][i] = P1prime[i];
				coefficientsData[1][i] = P2[i];
				coefficientsData[2][i] = P3prime[i];
			}

			coefficients = JeometryFactory.createMatrix(coefficientsData);
			constants    = JeometryFactory.createVector(new double[] {  P1prime[3], P2[3], P3prime[3]});
			solution     = JeometryFactory.createVector(constants);

			try {
				if (solver.solve(coefficients, constants, solution) != null) {
					parallelepipedVertices[5][0]  = solution.getValue(0);
					parallelepipedVertices[5][1]  = solution.getValue(1);
					parallelepipedVertices[5][3]  = solution.getValue(2);
				} else {
					Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system");
					parallelepipedVertices[5] = null;
					computationOK = false;
				}

			} catch (Exception e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system: "+e.getMessage(), e);
				parallelepipedVertices[5] = null;
				computationOK = false;
			}

			// Calcul du sommet 7 du parallelepipede
			for(int i=0;i<3;i++){
				coefficientsData[0][i] = P1prime[i];
				coefficientsData[1][i] = P2prime[i];
				coefficientsData[2][i] = P3prime[i];
			}

			coefficients = JeometryFactory.createMatrix(coefficientsData);
			constants    = JeometryFactory.createVector(new double[] {  P1prime[3], P2prime[3], P3prime[3]});
			solution     = JeometryFactory.createVector(constants);

			try {
				if (solver.solve(coefficients, constants, solution) != null) {
					parallelepipedVertices[6][0]  = solution.getValue(0);
					parallelepipedVertices[6][1]  = solution.getValue(1);
					parallelepipedVertices[6][3]  = solution.getValue(2);
				} else {
					Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system");
					parallelepipedVertices[6] = null;
					computationOK = false;
				}

			} catch (Exception e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system: "+e.getMessage(), e);
				parallelepipedVertices[6] = null;
				computationOK = false;
			}

			// Calcul du sommet 8 du parallelepipede
			for(int i=0;i<3;i++){
				coefficientsData[0][i] = P1prime[i];
				coefficientsData[1][i] = P2prime[i];
				coefficientsData[2][i] = P3[i];
			}

			coefficients = JeometryFactory.createMatrix(coefficientsData);
			constants    = JeometryFactory.createVector(new double[] {  P1prime[3], P2prime[3], P3[3]});
			solution     = JeometryFactory.createVector(constants);

			try {
				if (solver.solve(coefficients, constants, solution) != null) {
					parallelepipedVertices[7][0]  = solution.getValue(0);
					parallelepipedVertices[7][1]  = solution.getValue(1);
					parallelepipedVertices[7][3]  = solution.getValue(2);
				} else {
					Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system");
					parallelepipedVertices[7] = null;
					computationOK = false;
				}

			} catch (Exception e) {
				Jeometry.logger.log(Level.SEVERE, "Cannot solve linear system: "+e.getMessage(), e);
				parallelepipedVertices[7] = null;
				computationOK = false;
			}

		} else {
			computationOK = false;
		}

		if (computationOK) {
			// Affectation des faces du polyhedre   
			Point3D pt1 = JeometryFactory.createPoint3D(parallelepipedVertices[5][0], parallelepipedVertices[5][1], parallelepipedVertices[5][2]);
			Point3D pt2 = JeometryFactory.createPoint3D(parallelepipedVertices[4][0], parallelepipedVertices[4][1], parallelepipedVertices[4][2]);
			Point3D pt3 = JeometryFactory.createPoint3D(parallelepipedVertices[0][0], parallelepipedVertices[0][1], parallelepipedVertices[0][2]);
			Point3D pt4 = JeometryFactory.createPoint3D(parallelepipedVertices[1][0], parallelepipedVertices[1][1], parallelepipedVertices[1][2]);
			Point3D pt5 = JeometryFactory.createPoint3D(parallelepipedVertices[6][0], parallelepipedVertices[6][1], parallelepipedVertices[6][2]);
			Point3D pt6 = JeometryFactory.createPoint3D(parallelepipedVertices[7][0], parallelepipedVertices[7][1], parallelepipedVertices[7][2]);
			Point3D pt7 = JeometryFactory.createPoint3D(parallelepipedVertices[3][0], parallelepipedVertices[3][1], parallelepipedVertices[3][2]);
			Point3D pt8 = JeometryFactory.createPoint3D(parallelepipedVertices[2][0], parallelepipedVertices[2][1], parallelepipedVertices[2][2]);

			// Creation du polyhedre représentant le parallelepipede
			Point3DContainer<Point3D> points;

			parallelepiped = JeometryFactory.createMesh();

			//  Face de dessous
			points = JeometryFactory.createPoint3DContainer(4);
			points.add(pt4);
			points.add(pt3);
			points.add(pt2);
			points.add(pt1);			
			parallelepiped.addFace(JeometryFactory.createMeshFace(parallelepiped, points));

			// Face du haut
			points = JeometryFactory.createPoint3DContainer(4);
			points.add(pt5);
			points.add(pt6);
			points.add(pt7);
			points.add(pt8);
			parallelepiped.addFace(JeometryFactory.createMeshFace(parallelepiped, points));

			// Face de gauche
			points = JeometryFactory.createPoint3DContainer(4);
			points.add(pt1);
			points.add(pt2);
			points.add(pt6);
			points.add(pt5);
			parallelepiped.addFace(JeometryFactory.createMeshFace(parallelepiped, points));

			// Face de droite
			points = JeometryFactory.createPoint3DContainer(4);
			points.add(pt4);
			points.add(pt8);
			points.add(pt7);
			points.add(pt3);
			parallelepiped.addFace(JeometryFactory.createMeshFace(parallelepiped, points));

			// Face de devant
			points = JeometryFactory.createPoint3DContainer(4);
			points.add(pt1);
			points.add(pt5);
			points.add(pt8);
			points.add(pt4);
			parallelepiped.addFace(JeometryFactory.createMeshFace(parallelepiped, points));

			// Face de derrière
			points = JeometryFactory.createPoint3DContainer(4);
			points.add(pt3);
			points.add(pt7);
			points.add(pt6);
			points.add(pt2);
			parallelepiped.addFace(JeometryFactory.createMeshFace(parallelepiped, points));

			return parallelepiped;
		}

		return null;
	}

	private static boolean minimalEnclosingParallelepiped_tesCandidat(Point3D[] vecteurs,Point3D normale_facette2){
		double sign = 0.0d;
		double produit_scalaire = 0.0d;

		produit_scalaire = vecteurs[0].dot(normale_facette2);

		if(produit_scalaire<0){
			sign = -1;//NEGATIF;
		} else{
			sign = 1;//POSITIF;
		}

		for(int i=1;i<vecteurs.length;i++){
			produit_scalaire=0.0d;

			// Attention au cas ou le vecteur n'existe pas
			if (vecteurs[i] == null){
				continue;
			}
			produit_scalaire+=vecteurs[i].dot(normale_facette2);

			if((produit_scalaire<0)&&(sign == 1)){
				return true;
			} else if((produit_scalaire>0)&&(sign == -1)){
				return true;
			}
		}

		return false;
	}
}
