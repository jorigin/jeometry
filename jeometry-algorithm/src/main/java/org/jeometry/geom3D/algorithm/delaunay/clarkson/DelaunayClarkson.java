package org.jeometry.geom3D.algorithm.delaunay.clarkson;

import org.jeometry.Jeometry;

/**
 * This class is an implementation of the Ken Clarkson <a href="http://www.netlib.org/voronoi/hull.html">hull algorithm</a> that enable to compute convex hull in n-dimension.<br><br>
 * This algorithm compute a convex hull for a set of <code>n</code> points in a <code>O(n x log(n))</code> average time 
 * with a worst case in <code>O(n x n)</code>. <br>
 * This algorithm is based on an <b>integer representation</b> of input point coordinates, so if your points are closer than a coordinate unit, 
 * it is required to apply a scale on them. This scale can be given in parameter to computation method
 * ({@link #compute(double[][], float) compute(double[][], float)}.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
 * @since 1.0.0
 */
public class DelaunayClarkson {

  /**
   * The samples.
   */
  protected double[][] samples = null;

  // Delaunay core components
  /**
   * The simplices (link triangles / tetrahedra to vertices).
   */
  private int[][] simplexes;  // triangles/tetrahedra --> vertices
                              //   Tri = new int[ntris][dim + 1]  
  
  /**
   * The vertices
   */
  private int[][] vertices;   // vertices --> triangles/tetrahedra
                              //   Vertices = new int[nrs][nverts[i]]
  
  /**
   * The neighbors.
   */
  private int[][] neighbors;  // triangles/tetrahedra --> triangles/tetrahedra
                              //   Walk = new int[ntris][dim + 1]
  
  /**
   * The edges.
   */
  private int[][] Edges;      // tri/tetra edges --> global edge number
                              //   Edges = new int[ntris][3 * (dim - 1)];
  
  /**
   * The edges global numbers.
   */
  private int NumEdges;       // number of unique global edge numbers

  
  /* ******* BEGINNING OF CONVERTED HULL CODE ******* */

  // <<<< Constants >>>>
  /**
   * Internal constant.
   */
  private static final double DBL_MANT_DIG = 53;
  
  /**
   * Radix internal constant.
   */
  private static final double FLT_RADIX = 2;
  
  /**
   * Numerical precision internal constant.
   */
  private static final double DBL_EPSILON = 2.2204460492503131E-16;
  
  /**
   * log(2) internal constant.
   */
  private static final double ln2 = Math.log(2);


  // <<<< Variables >>>>
  /* we need to have two indices for every pointer into basis_s and
     simplex arrays, because they are two-dimensional arrays of
     blocks.  ( _bn = block number ) */

  // for the pseudo-pointers
  /**
   * Infinity value.
   */
  private static final int INFINITY = -2;      // replaces infinity
  
  /**
   * Null value.
   */
  private static final int NOVAL = -1;         // replaces null

  /**
   * Copy of samples array.
   */
  private double[][] site_blocks;        // copy of samples array
  
  /**
   * Output array.
   */
  private int[][]   a3s;                // output array
  
  /**
   * Output array maximum size.
   */
  private int       a3size;             // output array maximum size
  
  /**
   * Output objects count.
   */
  private int       nts = 0;            // # output objects

  /***
   * Max basis / simplex blocks
   */
  private static final int max_blocks = 10000; // max # basis/simplex blocks
  
  /**
   * Maximum objects constant.
   */
  private static final int Nobj = 10000;
  
  /**
   * Maximum dimension constant.
   */
  private static final int MAXDIM = 8;         // max dimension

  /**
   * the dimension.
   */
  private int    dim;
  
  /**
   * P value.
   */
  private int    p;
  
  /**
   * P num value.
   */
  private long   pnum;
  
  /**
   * Sites currently specifying region
   */
  private int    rdim;          // # sites currently specifying region
  
  /**
   * Internal variable.
   */
  private int cdim;
  
  /**
   * Internal variable.
   */
  private int    exact_bits;
  
  /**
   * Internal variable.
   */
  private double b_err_min;
  
  /**
   * Internal variable.
   */
  private double  b_err_min_sq;
  
  /**
   * Internal variable.
   */
  private double ldetbound = 0;
  
  /**
   * Internal variable.
   */
  private int    failcount = 0;          // static: reduce_inner
  
  /**
   * Internal variable.
   */
  private int    lscale;                 // static: reduce_inner
  
  /**
   * The max scale.
   */
  private double max_scale;              // static: reduce_inner

  /**
   * The simplex blocks.
   */
  private int    nsb = 0;                // # simplex blocks
  
  /**
   * The basis blocks.
   */
  private int    nbb = 0;                // # basis_s blocks
  
  /**
   * The search limit.
   */
  private int    ss = MAXDIM;            // static: search
  
  /**
   * The visit triangles max.
   */
  private int    ss2 = 2000;             // static: visit_triang
  
  /**
   * Used by visit triangle.
   */
  private long   vnum = -1;              // static: visit_triang


  // "void stuff" -- dummy variables to hold unused return information
  /**
   * Internal private variable.
   */
  private final int[] voidp = new int[1];
  
  /**
   * Internal private variable.
   */
  private final int[] voidp_bn = new int[1];

  // basis_s stuff
  /** Internal private variable. */ private int[][]      bbt_next = new int[max_blocks][];
  /** Internal private variable. */ private int[][]      bbt_next_bn = new int[max_blocks][];
  /** Internal private variable. */ private int[][]      bbt_ref_count = new int[max_blocks][];
  /** Internal private variable. */ private int[][]      bbt_lscale = new int[max_blocks][];
  /** Internal private variable. */ private double[][]   bbt_sqa = new double[max_blocks][];
  /** Internal private variable. */ private double[][]   bbt_sqb = new double[max_blocks][];
  /** Internal private variable. */ private double[][][] bbt_vecs = new double[max_blocks][][];

  /** Internal private variable. */ private int ttbp;
  /** Internal private variable. */ private int ttbp_bn;
  /** Internal private variable. */ private int ib;
  /** Internal private variable. */ private int ib_bn;
  /** Internal private variable. */ private int basis_s_list = NOVAL;
  /** Internal private variable. */ private int basis_s_list_bn;
  /** Internal private variable. */ private int pnb = NOVAL;
  /** Internal private variable. */ private int pnb_bn;
  /** Internal private variable. */ private int b = NOVAL;              // static: sees
  
  /** Internal private variable. */ private int b_bn;
  /** Internal private variable. */ private int[][]   sbt_next = new int[max_blocks][];
  /** Internal private variable. */ private int[][]   sbt_next_bn = new int[max_blocks][];
  /** Internal private variable. */ private long[][]  sbt_visit = new long[max_blocks][];
  /** Internal private variable. */ private short[][] sbt_mark = new short[max_blocks][];
  /** Internal private variable. */ private int[][]   sbt_normal = new int[max_blocks][];
  /** Internal private variable. */ private int[][]   sbt_normal_bn = new int[max_blocks][];
  /** Internal private variable. */ private int[][]   sbt_peak_vert = new int[max_blocks][];
  /** Internal private variable. */ private int[][]   sbt_peak_simp = new int[max_blocks][];
  /** Internal private variable. */ private int[][]   sbt_peak_simp_bn = new int[max_blocks][];
  /** Internal private variable. */ private int[][]   sbt_peak_basis = new int[max_blocks][];
  /** Internal private variable. */ private int[][]   sbt_peak_basis_bn = new int[max_blocks][];
  /** Internal private variable. */ private int[][][] sbt_neigh_vert = new int[max_blocks][][];
  /** Internal private variable. */ private int[][][] sbt_neigh_simp = new int[max_blocks][][];
  /** Internal private variable. */ private int[][][] sbt_neigh_simp_bn = new int[max_blocks][][];

  /** Internal private variable. */ private int[][][] sbt_neigh_basis = new int[max_blocks][][];
  /** Internal private variable. */ private int[][][] sbt_neigh_basis_bn = new int[max_blocks][][];
  /** Internal private variable. */ private int   simplex_list = NOVAL;
  /** Internal private variable. */ private int   simplex_list_bn;
  /** Internal private variable. */ private int   ch_root;
  /** Internal private variable. */ private int   ch_root_bn;
  
  /** Internal private variable. */ private int   ns;                            // static: make_facets
  /** Internal private variable. */ private int   ns_bn;
  /** Internal private variable. */ private int[] st = new int[ss+MAXDIM+1];    // static: search
  /** Internal private variable. */ private int[] st_bn = new int[ss+MAXDIM+1];
  /** Internal private variable. */ private int[] st2 = new int[ss2+MAXDIM+1];    // static: visit_triang
  /** Internal private variable. */ private int[] st2_bn = new int[ss2+MAXDIM+1];

  
  /**
   * Get the samples to process with Delaunay computation. Samples can be 2D, 3D or higher dimension points. <br>
   * The underlying data structure is an array of double <code>d[n][m]</code> where <code>n</code> is the number of samples (io the number of points) 
   * and <code>m</code> is the sample space dimension (ie 2 for 2D, 3 for 3D, ...). 
   * @return the samples to process with Delaunay computation. Samples can be 2D, 3D or higher dimension points. 
   */
  public double[][] getSamples() {
    return samples;
  }

  /**
   * Set the the samples to process with Delaunay computation. Samples can be 2D, 3D or higher dimension points. <br>
   * The underlying data structure is an array of double <code>d[n][m]</code> where <code>n</code> is the number of samples (io the number of points) 
   * and <code>m</code> is the sample space dimension (ie 2 for 2D, 3 for 3D, ...). 
   * @param samples the samples to process with Delaunay computation. Samples can be 2D, 3D or higher dimension points. 
   */
  public void setSamples(double[][] samples) {
    this.samples = samples;
  }

  /**
   * Get the simplexes that compose the Delaunay computation result. 
   * A simplex is a basic Delaunay element and can can be a triangle if the underlying {@link #getSamples() samples} lies within a 2D space 
   * or a tetrahedron if the underlying samples lies within a 3D space. <br><br>
   * Simplexes are described by a list of {@link #getSamples() samples} that compose its vertices. 
   * The vertex <code>n</code> of the simplex <code>k</code> can be accessed by <code>getSamples(getSimplexes(k)[n])</code>.
   * @return the simplexes that compose the Delaunay computation result. 
   * @see #getSamples()
   */
  public int[][] getSimplexes() {
    return simplexes;
  }

  /**
   * Get the vertices that compose the delaunay computation result. Each vertex is described by a reference to the {@link #getSamples() samples} it represents 
   * and by a list of references to simplexes that relie on this vertex.
   * @return the vertices that compose the delaunay computation result.
   * @see #getSamples()
   * @see #getSimplexes()
   */
  public int[][] getVertices() {
    return vertices;
  }

  /**
   * Get the neighborhood of the delaunay simplexes. 
   * @return the neighborhood of the delaunay simplexes. 
   */
  public int[][] getNeighbors() {
    return neighbors;
  }
  
/**
 * Compute new block basis.
 * @return the new block basis.
 */
  private int new_block_basis_s() {
    bbt_next[nbb] = new int[Nobj];
    bbt_next_bn[nbb] = new int[Nobj];
    bbt_ref_count[nbb] = new int[Nobj];
    bbt_lscale[nbb] = new int[Nobj];
    bbt_sqa[nbb] = new double[Nobj];
    bbt_sqb[nbb] = new double[Nobj];
    bbt_vecs[nbb] = new double[2*rdim][];
    for (int i=0; i<2*rdim; i++) bbt_vecs[nbb][i] = new double[Nobj];
    for (int i=0; i<Nobj; i++) {
      bbt_next[nbb][i] = i+1;
      bbt_next_bn[nbb][i] = nbb;
      bbt_ref_count[nbb][i] = 0;
      bbt_lscale[nbb][i] = 0;
      bbt_sqa[nbb][i] = 0;
      bbt_sqb[nbb][i] = 0;
      for (int j=0; j<2*rdim; j++) bbt_vecs[nbb][j][i] = 0;
    }
    bbt_next[nbb][Nobj-1] = NOVAL;
    basis_s_list = 0;
    basis_s_list_bn = nbb;
    nbb++;
    return basis_s_list;
  }

  /**
   * Reduce inner.
   * @param v the vertex
   * @param v_bn the vertx back
   * @param s the simplex
   * @param s_bn the simplex back
   * @param k the deep
   * @return the inner
   */
  private int reduce_inner(int v, int v_bn, int s, int s_bn, int k) {
    int q, q_bn;
    double dd,
           Sb = 0;
    double scale;

    bbt_sqa[v_bn][v] = 0;
    for (int i=0; i<rdim; i++) {
      bbt_sqa[v_bn][v] += bbt_vecs[v_bn][i][v] * bbt_vecs[v_bn][i][v];
    }
    bbt_sqb[v_bn][v] = bbt_sqa[v_bn][v];
    if (k <= 1) {
      for (int i=0; i<rdim; i++) {
        bbt_vecs[v_bn][i][v] = bbt_vecs[v_bn][rdim+i][v];
      }
      return 1;
    }
    for (int j=0; j<250; j++) {
      int    xx = rdim;
      double labound;

      for (int i=0; i<rdim; i++) {
        bbt_vecs[v_bn][i][v] = bbt_vecs[v_bn][rdim+i][v];
      }
      for (int i=k-1; i>0; i--) {
        q = sbt_neigh_basis[s_bn][i][s];
        q_bn = sbt_neigh_basis_bn[s_bn][i][s];
        dd = 0;
        for (int l=0; l<rdim; l++) {
          dd -= bbt_vecs[q_bn][l][q] * bbt_vecs[v_bn][l][v];
        }
        dd /= bbt_sqb[q_bn][q];
        for (int l=0; l<rdim; l++) {
          bbt_vecs[v_bn][l][v] += dd * bbt_vecs[q_bn][rdim+l][q];
        }
      }
      bbt_sqb[v_bn][v] = 0;
      for (int i=0; i<rdim; i++) {
        bbt_sqb[v_bn][v] += bbt_vecs[v_bn][i][v] * bbt_vecs[v_bn][i][v];
      }
      bbt_sqa[v_bn][v] = 0;
      for (int i=0; i<rdim; i++) {
        bbt_sqa[v_bn][v] += bbt_vecs[v_bn][rdim+i][v]
                          * bbt_vecs[v_bn][rdim+i][v];
      }

      if (2*bbt_sqb[v_bn][v] >= bbt_sqa[v_bn][v]) return 1;

      // scale up vector
      if (j < 10) {
        labound = Math.floor(Math.log(bbt_sqa[v_bn][v])/ln2) / 2;
        max_scale = exact_bits-labound-0.66*(k-2)-1;
        if (max_scale < 1) max_scale = 1;

        if (j == 0) {

          ldetbound = 0;
          Sb = 0;
          for (int l=k-1; l>0; l--) {
            q = sbt_neigh_basis[s_bn][l][s];
            q_bn = sbt_neigh_basis_bn[s_bn][l][s];
            Sb += bbt_sqb[q_bn][q];
            ldetbound += Math.floor(Math.log(bbt_sqb[q_bn][q])/ln2) / 2 + 1;
            ldetbound -= bbt_lscale[q_bn][q];
          }
        }
      }
      if (ldetbound - bbt_lscale[v_bn][v]
        + Math.floor(Math.log(bbt_sqb[v_bn][v])/ln2) / 2 + 1 < 0) {
        scale = 0;
      }
      else {
        lscale = (int) (Math.log(2*Sb/(bbt_sqb[v_bn][v]
                                     + bbt_sqa[v_bn][v]*b_err_min))/ln2) / 2;
        if (lscale > max_scale) lscale = (int) max_scale;
        else if (lscale < 0) lscale = 0;
        bbt_lscale[v_bn][v] += lscale;
        scale = (lscale < 20) ? 1 << lscale : Math.pow(2, lscale);
      }

      while (xx < 2*rdim) bbt_vecs[v_bn][xx++][v] *= scale;

      for (int i=k-1; i>0; i--) {
        q = sbt_neigh_basis[s_bn][i][s];
        q_bn = sbt_neigh_basis_bn[s_bn][i][s];
        dd = 0;
        for (int l=0; l<rdim; l++) {
          dd -= bbt_vecs[q_bn][l][q] * bbt_vecs[v_bn][rdim+l][v];
        }
        dd /= bbt_sqb[q_bn][q];
        dd = Math.floor(dd+0.5);
        for (int l=0; l<rdim; l++) {
          bbt_vecs[v_bn][rdim+l][v] += dd * bbt_vecs[q_bn][rdim+l][q];
        }
      }
    }
    if (failcount++ < 10) System.out.println("reduce_inner failed!");
    return 0;
  }

  /**
   * Reduce the system
   * @param v the v parameter
   * @param v_bn the v_bn parameter
   * @param rp the rp parameter
   * @param s the s parameter
   * @param s_bn s_bn the parameter
   * @param k the k parameter
   * @return the reduces system
   */
  private int reduce(int[] v, int[] v_bn, int rp, int s, int s_bn, int k) {
    if (v[0] == NOVAL) {
      v[0] = basis_s_list != NOVAL ? basis_s_list : new_block_basis_s();
      v_bn[0] = basis_s_list_bn;
      basis_s_list = bbt_next[v_bn[0]][v[0]];
      basis_s_list_bn = bbt_next_bn[v_bn[0]][v[0]];
      bbt_ref_count[v_bn[0]][v[0]] = 1;
    }
    else bbt_lscale[v_bn[0]][v[0]] = 0;
    if (rp == INFINITY) {
      bbt_next[v_bn[0]][v[0]] = bbt_next[ib_bn][ib];
      bbt_next_bn[v_bn[0]][v[0]] = bbt_next_bn[ib_bn][ib];
      bbt_ref_count[v_bn[0]][v[0]] = bbt_ref_count[ib_bn][ib];
      bbt_lscale[v_bn[0]][v[0]] = bbt_lscale[ib_bn][ib];
      bbt_sqa[v_bn[0]][v[0]] = bbt_sqa[ib_bn][ib];
      bbt_sqb[v_bn[0]][v[0]] = bbt_sqb[ib_bn][ib];
      for (int i=0; i<2*rdim; i++) {
        bbt_vecs[v_bn[0]][i][v[0]] = bbt_vecs[ib_bn][i][ib];
      }
    }
    else {
      double sum = 0;
      int sbt_nv = sbt_neigh_vert[s_bn][0][s];
      if (sbt_nv == INFINITY) {
        for (int i=0; i<dim; i++) {
          bbt_vecs[v_bn[0]][i+rdim][v[0]] = bbt_vecs[v_bn[0]][i][v[0]]
            = site_blocks[i][rp];
        }
      }
      else {
        for (int i=0; i<dim; i++) {
          bbt_vecs[v_bn[0]][i+rdim][v[0]] = bbt_vecs[v_bn[0]][i][v[0]]
            = site_blocks[i][rp] - site_blocks[i][sbt_nv];
        }
      }
      for (int i=0; i<dim; i++) {
        sum += bbt_vecs[v_bn[0]][i][v[0]] * bbt_vecs[v_bn[0]][i][v[0]];
      }
      bbt_vecs[v_bn[0]][2*rdim-1][v[0]] = sum;
      bbt_vecs[v_bn[0]][rdim-1][v[0]] = sum;
    }
    return reduce_inner(v[0], v_bn[0], s, s_bn, k);
  }

  /**
   * Get the basis.
   * @param s the simplex
   * @param s_bn the simplex bn
   */
  private void get_basis_sede(int s, int s_bn) {
    int   k=1;
    int   q, q_bn;
    int[] curt = new int[1];
    int[] curt_bn = new int[1];

    if (sbt_neigh_vert[s_bn][0][s] == INFINITY && cdim > 1) {
      int t_vert, t_simp, t_simp_bn, t_basis, t_basis_bn;
      t_vert = sbt_neigh_vert[s_bn][0][s];
      t_simp = sbt_neigh_simp[s_bn][0][s];
      t_simp_bn = sbt_neigh_simp_bn[s_bn][0][s];
      t_basis = sbt_neigh_basis[s_bn][0][s];
      t_basis_bn = sbt_neigh_basis_bn[s_bn][0][s];
      sbt_neigh_vert[s_bn][0][s] = sbt_neigh_vert[s_bn][k][s];
      sbt_neigh_simp[s_bn][0][s] = sbt_neigh_simp[s_bn][k][s];
      sbt_neigh_simp_bn[s_bn][0][s] = sbt_neigh_simp_bn[s_bn][k][s];
      sbt_neigh_basis[s_bn][0][s] = sbt_neigh_basis[s_bn][k][s];
      sbt_neigh_basis_bn[s_bn][0][s] = sbt_neigh_basis_bn[s_bn][k][s];
      sbt_neigh_vert[s_bn][k][s] = t_vert;
      sbt_neigh_simp[s_bn][k][s] = t_simp;
      sbt_neigh_simp_bn[s_bn][k][s] = t_simp_bn;
      sbt_neigh_basis[s_bn][k][s] = t_basis;
      sbt_neigh_basis_bn[s_bn][k][s] = t_basis_bn;

      q = sbt_neigh_basis[s_bn][0][s];
      q_bn = sbt_neigh_basis_bn[s_bn][0][s];
      if ((q != NOVAL) && --bbt_ref_count[q_bn][q] == 0) {
        bbt_next[q_bn][q] = basis_s_list;
        bbt_next_bn[q_bn][q] = basis_s_list_bn;
        bbt_ref_count[q_bn][q] = 0;
        bbt_lscale[q_bn][q] = 0;
        bbt_sqa[q_bn][q] = 0;
        bbt_sqb[q_bn][q] = 0;
        for (int j=0; j<2*rdim; j++) bbt_vecs[q_bn][j][q] = 0;
        basis_s_list = q;
        basis_s_list_bn = q_bn;
      }

      sbt_neigh_basis[s_bn][0][s] = ttbp;
      sbt_neigh_basis_bn[s_bn][0][s] = ttbp_bn;
      bbt_ref_count[ttbp_bn][ttbp]++;
    }
    else {
      if (sbt_neigh_basis[s_bn][0][s] == NOVAL) {
        sbt_neigh_basis[s_bn][0][s] = ttbp;
        sbt_neigh_basis_bn[s_bn][0][s] = ttbp_bn;
        bbt_ref_count[ttbp_bn][ttbp]++;
      } else while (k < cdim && sbt_neigh_basis[s_bn][k][s] != NOVAL) k++;
    }
    while (k < cdim) {
      q = sbt_neigh_basis[s_bn][k][s];
      q_bn = sbt_neigh_basis_bn[s_bn][k][s];
      if (q != NOVAL && --bbt_ref_count[q_bn][q] == 0) {
        bbt_next[q_bn][q] = basis_s_list;
        bbt_next_bn[q_bn][q] = basis_s_list_bn;
        bbt_ref_count[q_bn][q] = 0;
        bbt_lscale[q_bn][q] = 0;
        bbt_sqa[q_bn][q] = 0;
        bbt_sqb[q_bn][q] = 0;
        for (int j=0; j<2*rdim; j++) bbt_vecs[q_bn][j][q] = 0;
        basis_s_list = q;
        basis_s_list_bn = q_bn;
      }
      sbt_neigh_basis[s_bn][k][s] = NOVAL;
      curt[0] = sbt_neigh_basis[s_bn][k][s];
      curt_bn[0] = sbt_neigh_basis_bn[s_bn][k][s];
      reduce(curt, curt_bn, sbt_neigh_vert[s_bn][k][s], s, s_bn, k);
      sbt_neigh_basis[s_bn][k][s] = curt[0];
      sbt_neigh_basis_bn[s_bn][k][s] = curt_bn[0];
      k++;
    }
  }

  /**
   * Compute the sees.
   * @param rp the rp
   * @param s the s
   * @param s_bn the s_bn
   * @return the sees
   */
  private int sees(int rp, int s, int s_bn) {
    double  dd, dds;
    int     q, q_bn, q1, q1_bn, q2, q2_bn;
    int[]   curt = new int[1];
    int[]   curt_bn = new int[1];

    if (b == NOVAL) {
      b = (basis_s_list != NOVAL) ? basis_s_list : new_block_basis_s();
      b_bn = basis_s_list_bn;
      basis_s_list = bbt_next[b_bn][b];
      basis_s_list_bn = bbt_next_bn[b_bn][b];
    }
    else bbt_lscale[b_bn][b] = 0;
    if (cdim==0) return 0;
    if (sbt_normal[s_bn][s] == NOVAL) {
      get_basis_sede(s, s_bn);
      if (rdim==3 && cdim==3) {
        sbt_normal[s_bn][s] = basis_s_list != NOVAL ? basis_s_list
                                                    : new_block_basis_s();
        sbt_normal_bn[s_bn][s] = basis_s_list_bn;
        q = sbt_normal[s_bn][s];
        q_bn = sbt_normal_bn[s_bn][s];
        basis_s_list = bbt_next[q_bn][q];
        basis_s_list_bn = bbt_next_bn[q_bn][q];
        q1 = sbt_neigh_basis[s_bn][1][s];
        q1_bn = sbt_neigh_basis_bn[s_bn][1][s];
        q2 = sbt_neigh_basis[s_bn][2][s];
        q2_bn = sbt_neigh_basis_bn[s_bn][2][s];
        bbt_ref_count[q_bn][q] = 1;
        bbt_vecs[q_bn][0][q] = bbt_vecs[q1_bn][1][q1]
                  *bbt_vecs[q2_bn][2][q2]
             - bbt_vecs[q1_bn][2][q1]
                  *bbt_vecs[q2_bn][1][q2];
        bbt_vecs[q_bn][1][q] = bbt_vecs[q1_bn][2][q1]
                  *bbt_vecs[q2_bn][0][q2]
             - bbt_vecs[q1_bn][0][q1]
                  *bbt_vecs[q2_bn][2][q2];
        bbt_vecs[q_bn][2][q] = bbt_vecs[q1_bn][0][q1]
                  *bbt_vecs[q2_bn][1][q2]
             - bbt_vecs[q1_bn][1][q1]
                  *bbt_vecs[q2_bn][0][q2];
        bbt_sqb[q_bn][q] = 0;
        for (int i=0; i<rdim; i++) bbt_sqb[q_bn][q] += bbt_vecs[q_bn][i][q]
                                                     * bbt_vecs[q_bn][i][q];
        for (int i=cdim+1; i>0; i--) {
          int m = (i > 1) ? sbt_neigh_vert[ch_root_bn][i-2][ch_root]
                          : INFINITY;
          int j;
          for (j=0; j<cdim && m != sbt_neigh_vert[s_bn][j][s]; j++);
          if (j < cdim) continue;
          if (m == INFINITY) {
            if (bbt_vecs[q_bn][2][q] > -b_err_min) continue;
          }
          else {
            if (sees(m, s, s_bn) == 0) {
              continue;
            }
          }
          bbt_vecs[q_bn][0][q] = -bbt_vecs[q_bn][0][q];
          bbt_vecs[q_bn][1][q] = -bbt_vecs[q_bn][1][q];
          bbt_vecs[q_bn][2][q] = -bbt_vecs[q_bn][2][q];
          break;
        }
      }
      else {
        for (int i=cdim+1; i>0; i--) {
          int m = (i > 1) ? sbt_neigh_vert[ch_root_bn][i-2][ch_root]
                          : INFINITY;
          int j;
          for (j=0; j<cdim && m != sbt_neigh_vert[s_bn][j][s]; j++);
          if (j < cdim) continue;
          curt[0] = sbt_normal[s_bn][s];
          curt_bn[0] = sbt_normal_bn[s_bn][s];
          reduce(curt, curt_bn, m, s, s_bn, cdim);
          q = sbt_normal[s_bn][s] = curt[0];
          q_bn = sbt_normal_bn[s_bn][s] = curt_bn[0];
          if (bbt_sqb[q_bn][q] != 0) break;
        }
      }

      for (int i=0; i<cdim; i++) {
        q = sbt_neigh_basis[s_bn][i][s];
        q_bn = sbt_neigh_basis_bn[s_bn][i][s];
        if (q != NOVAL && --bbt_ref_count[q_bn][q] == 0) {
          bbt_next[q_bn][q] = basis_s_list;
          bbt_next_bn[q_bn][q] = basis_s_list_bn;
          bbt_ref_count[q_bn][q] = 0;
          bbt_lscale[q_bn][q] = 0;
          bbt_sqa[q_bn][q] = 0;
          bbt_sqb[q_bn][q] = 0;
          for (int l=0; l<2*rdim; l++) bbt_vecs[q_bn][l][q] = 0;
          basis_s_list = q;
          basis_s_list_bn = q_bn;
        }
        sbt_neigh_basis[s_bn][i][s] = NOVAL;
      }
    }
    if (rp == INFINITY) {
      bbt_next[b_bn][b] = bbt_next[ib_bn][ib];
      bbt_next_bn[b_bn][b] = bbt_next_bn[ib_bn][ib];
      bbt_ref_count[b_bn][b] = bbt_ref_count[ib_bn][ib];
      bbt_lscale[b_bn][b] = bbt_lscale[ib_bn][ib];
      bbt_sqa[b_bn][b] = bbt_sqa[ib_bn][ib];
      bbt_sqb[b_bn][b] = bbt_sqb[ib_bn][ib];
      for (int i=0; i<2*rdim; i++) {
        bbt_vecs[b_bn][i][b] = bbt_vecs[ib_bn][i][ib];
      }
    }
    else {
      double sum = 0;
      int sbt_nv = sbt_neigh_vert[s_bn][0][s];
      if (sbt_nv == INFINITY) {
        for (int l=0; l<dim; l++) {
          bbt_vecs[b_bn][l+rdim][b] = bbt_vecs[b_bn][l][b]
            = site_blocks[l][rp];
        }
      }
      else {
        for (int l=0; l<dim; l++) {
          bbt_vecs[b_bn][l+rdim][b] = bbt_vecs[b_bn][l][b]
            = site_blocks[l][rp] - site_blocks[l][sbt_nv];
        }
      }
      for (int l=0; l<dim; l++) {
        sum += bbt_vecs[b_bn][l][b] * bbt_vecs[b_bn][l][b];
      }
      bbt_vecs[b_bn][2*rdim-1][b] = bbt_vecs[b_bn][rdim-1][b] = sum;
    }
    q = sbt_normal[s_bn][s];
    q_bn = sbt_normal_bn[s_bn][s];
    for (int i=0; i<3; i++) {
      double sum = 0;
      dd = 0;
      for (int l=0; l<rdim; l++) {
        dd += bbt_vecs[b_bn][l][b] * bbt_vecs[q_bn][l][q];
      }
      if (dd == 0.0) return 0;
      for (int l=0; l<rdim; l++) {
        sum += bbt_vecs[b_bn][l][b] * bbt_vecs[b_bn][l][b];
      }
      dds = dd*dd/bbt_sqb[q_bn][q]/sum;
      if (dds > b_err_min_sq) return (dd < 0 ? 1 : 0);
      get_basis_sede(s, s_bn);
      reduce_inner(b, b_bn, s, s_bn, cdim);
    }
    return 0;
  }

  /**
   * Create a new block of simplex.
   * @return a new block of simplex
   */
  private int new_block_simplex() {
    sbt_next[nsb] = new int[Nobj];
    sbt_next_bn[nsb] = new int[Nobj];
    sbt_visit[nsb] = new long[Nobj];
    sbt_mark[nsb] = new short[Nobj];
    sbt_normal[nsb] = new int[Nobj];
    sbt_normal_bn[nsb] = new int[Nobj];
    sbt_peak_vert[nsb] = new int[Nobj];
    sbt_peak_simp[nsb] = new int[Nobj];
    sbt_peak_simp_bn[nsb] = new int[Nobj];
    sbt_peak_basis[nsb] = new int[Nobj];
    sbt_peak_basis_bn[nsb] = new int[Nobj];
    sbt_neigh_vert[nsb] = new int[rdim][];
    sbt_neigh_simp[nsb] = new int[rdim][];
    sbt_neigh_simp_bn[nsb] = new int[rdim][];
    sbt_neigh_basis[nsb] = new int[rdim][];
    sbt_neigh_basis_bn[nsb] = new int[rdim][];
    for (int i=0; i<rdim; i++) {
      sbt_neigh_vert[nsb][i] = new int[Nobj];
      sbt_neigh_simp[nsb][i] = new int[Nobj];
      sbt_neigh_simp_bn[nsb][i] = new int[Nobj];
      sbt_neigh_basis[nsb][i] = new int[Nobj];
      sbt_neigh_basis_bn[nsb][i] = new int[Nobj];
    }
    for (int i=0; i<Nobj; i++) {
      sbt_next[nsb][i] = i+1;
      sbt_next_bn[nsb][i] = nsb;
      sbt_visit[nsb][i] = 0;
      sbt_mark[nsb][i] = 0;
      sbt_normal[nsb][i] = NOVAL;
      sbt_peak_vert[nsb][i] = NOVAL;
      sbt_peak_simp[nsb][i] = NOVAL;
      sbt_peak_basis[nsb][i] = NOVAL;
      for (int j=0; j<rdim; j++) {
        sbt_neigh_vert[nsb][j][i] = NOVAL;
        sbt_neigh_simp[nsb][j][i] = NOVAL;
        sbt_neigh_basis[nsb][j][i] = NOVAL;
      }
    }
    sbt_next[nsb][Nobj-1] = NOVAL;
    simplex_list = 0;
    simplex_list_bn = nsb;

    nsb++;
    return simplex_list;
  }

  /**
   * Starting at s, visit simplices t such that test(s,i,0) is true, and t is the i'th neighbor of s;
   * apply visit function to all visited simplices;
   * when visit returns non null, exit and return its value.
   * @param s the starting index
   * @param s_bn the starting index back
   * @param whichfunc  the function to use
   * @param ret the return index
   * @param ret_bn the return index backup
  */
  private void visit_triang_gen(int s, int s_bn, int whichfunc,
                                int[] ret, int[] ret_bn) {
    int v;
    int v_bn;
    int t;
    int t_bn;
    int tms = 0;

    vnum--;
    if (s != NOVAL) {
      st2[tms] = s;
      st2_bn[tms] = s_bn;
      tms++;
    }
    while (tms != 0) {
      if (tms > ss2) {
        // JAVA: efficiency issue: how much is this stack hammered?
        ss2 += ss2;
        int[] newst2 = new int[ss2+MAXDIM+1];
        int[] newst2_bn = new int[ss2+MAXDIM+1];
        System.arraycopy(st2, 0, newst2, 0, st2.length);
        System.arraycopy(st2_bn, 0, newst2_bn, 0, st2_bn.length);
        st2 = newst2;
        st2_bn = newst2_bn;
      }
      tms--;
      t = st2[tms];
      t_bn = st2_bn[tms];
      if (t == NOVAL || sbt_visit[t_bn][t] == vnum) continue;
      sbt_visit[t_bn][t] = vnum;
      if (whichfunc == 1) {
        if (sbt_peak_vert[t_bn][t] == NOVAL) {
          v = t;
          v_bn = t_bn;
        }
        else {
          v = NOVAL;
          v_bn = NOVAL;
        }
        if (v != NOVAL) {
          ret[0] = v;
          ret_bn[0] = v_bn;
          return;
        }
      }
      else {
        int[] vfp = new int[cdim];

        if (t != NOVAL) {
          for (int j=0; j<cdim; j++) vfp[j] = sbt_neigh_vert[t_bn][j][t];
          for (int j=0; j<cdim; j++) {
            a3s[j][nts] = (vfp[j] == INFINITY) ? -1 : vfp[j];
          }
          nts++;
          if (nts > a3size) {
            // JAVA: efficiency issue, hammering an array
            a3size += a3size;
            int[][] newa3s = new int[rdim][a3size+MAXDIM+1];
            for (int i=0; i<rdim; i++) {
              System.arraycopy(a3s[i], 0, newa3s[i], 0, a3s[i].length);
            }
            a3s = newa3s;
          }
        }
      }
      for (int i=0; i<cdim; i++) {
        int j = sbt_neigh_simp[t_bn][i][t];
        int j_bn = sbt_neigh_simp_bn[t_bn][i][t];
        if ((j != NOVAL) && sbt_visit[j_bn][j] != vnum) {
          st2[tms] = j;
          st2_bn[tms] = j_bn;
          tms++;
        }
      }
    }
    ret[0] = NOVAL;
  }

  /**
   * Make neighbor connections between newly created simplices incident to p.
   * @param s the start simplex 
   * @param s_bn  the back
   */
  private void connect(int s, int s_bn) {
    int xb, xf;
    int sb, sb_bn;
    int sf, sf_bn;
    int tf, tf_bn;
    int ccj, ccj_bn;
    int xfi;

    if (s == NOVAL) return;
    for (int i=0; (sbt_neigh_vert[s_bn][i][s] != p) && (i<cdim); i++);
    if (sbt_visit[s_bn][s] == pnum) return;
    sbt_visit[s_bn][s] = pnum;
    ccj = sbt_peak_simp[s_bn][s];
    ccj_bn = sbt_peak_simp_bn[s_bn][s];
    for (xfi=0; (sbt_neigh_simp[ccj_bn][xfi][ccj] != s
              || sbt_neigh_simp_bn[ccj_bn][xfi][ccj] != s_bn)
                     && (xfi<cdim); xfi++);
    for (int i=0; i<cdim; i++) {
      int l;
      if (p == sbt_neigh_vert[s_bn][i][s]) continue;
      sb = sbt_peak_simp[s_bn][s];
      sb_bn = sbt_peak_simp_bn[s_bn][s];
      sf = sbt_neigh_simp[s_bn][i][s];
      sf_bn = sbt_neigh_simp_bn[s_bn][i][s];
      xf = sbt_neigh_vert[ccj_bn][xfi][ccj];
      if (sbt_peak_vert[sf_bn][sf] == NOVAL) {  // are we done already?
        for (l=0; (sbt_neigh_vert[ccj_bn][l][ccj]
                != sbt_neigh_vert[s_bn][i][s]) && (l<cdim); l++);
        sf = sbt_neigh_simp[ccj_bn][l][ccj];
        sf_bn = sbt_neigh_simp_bn[ccj_bn][l][ccj];
        if (sbt_peak_vert[sf_bn][sf] != NOVAL) continue;
      } else do {
        xb = xf;
        for (l=0; (sbt_neigh_simp[sf_bn][l][sf] != sb
                || sbt_neigh_simp_bn[sf_bn][l][sf] != sb_bn)
                && l<cdim; l++);
        xf = sbt_neigh_vert[sf_bn][l][sf];
        sb = sf;
        sb_bn = sf_bn;
        for (l=0; (sbt_neigh_vert[sb_bn][l][sb] != xb) && (l<cdim); l++);
        tf = sbt_neigh_simp[sf_bn][l][sf];
        tf_bn = sbt_neigh_simp_bn[sf_bn][l][sf];
        sf = tf;
        sf_bn = tf_bn;
      } while (sbt_peak_vert[sf_bn][sf] != NOVAL);

      sbt_neigh_simp[s_bn][i][s] = sf;
      sbt_neigh_simp_bn[s_bn][i][s] = sf_bn;
      for (l=0; (sbt_neigh_vert[sf_bn][l][sf] != xf) && (l<cdim); l++);
      sbt_neigh_simp[sf_bn][l][sf] = s;
      sbt_neigh_simp_bn[sf_bn][l][sf] = s_bn;

      connect(sf, sf_bn);
    }

  }

  /**
   * Visit simplices s with sees(p,s), and make a facet for every neighbor of s not seen by p.
   * @param seen the seen simplex index 
   * @param seen_bn the seen simplex index back
   * @param ret the return index
   * @param ret_bn the return index back
  */
  private void make_facets(int seen, int seen_bn, int[] ret, int[] ret_bn) {
    int n, n_bn;
    int q, q_bn;
    int j;

    if (seen == NOVAL) {
      ret[0] = NOVAL;
      return;
    }
    sbt_peak_vert[seen_bn][seen] = p;

    for (int i=0; i<cdim; i++) {
      n = sbt_neigh_simp[seen_bn][i][seen];
      n_bn = sbt_neigh_simp_bn[seen_bn][i][seen];

      if (pnum != sbt_visit[n_bn][n]) {
        sbt_visit[n_bn][n] = pnum;
        if (sees(p, n, n_bn) != 0) make_facets(n, n_bn, voidp, voidp_bn);
      }
      if (sbt_peak_vert[n_bn][n] != NOVAL) continue;

      ns = (simplex_list != NOVAL) ? simplex_list : new_block_simplex();
      ns_bn = simplex_list_bn;
      simplex_list = sbt_next[ns_bn][ns];
      simplex_list_bn = sbt_next_bn[ns_bn][ns];
      sbt_next[ns_bn][ns] = sbt_next[seen_bn][seen];
      sbt_next_bn[ns_bn][ns] = sbt_next_bn[seen_bn][seen];
      sbt_visit[ns_bn][ns] = sbt_visit[seen_bn][seen];
      sbt_mark[ns_bn][ns] = sbt_mark[seen_bn][seen];
      sbt_normal[ns_bn][ns] = sbt_normal[seen_bn][seen];
      sbt_normal_bn[ns_bn][ns] = sbt_normal_bn[seen_bn][seen];
      sbt_peak_vert[ns_bn][ns] = sbt_peak_vert[seen_bn][seen];
      sbt_peak_simp[ns_bn][ns] = sbt_peak_simp[seen_bn][seen];
      sbt_peak_simp_bn[ns_bn][ns] = sbt_peak_simp_bn[seen_bn][seen];
      sbt_peak_basis[ns_bn][ns] = sbt_peak_basis[seen_bn][seen];
      sbt_peak_basis_bn[ns_bn][ns] = sbt_peak_basis_bn[seen_bn][seen];
      for (j=0; j<rdim; j++) {
        sbt_neigh_vert[ns_bn][j][ns] = sbt_neigh_vert[seen_bn][j][seen];
        sbt_neigh_simp[ns_bn][j][ns] = sbt_neigh_simp[seen_bn][j][seen];
        sbt_neigh_simp_bn[ns_bn][j][ns]
                       = sbt_neigh_simp_bn[seen_bn][j][seen];
        sbt_neigh_basis[ns_bn][j][ns] = sbt_neigh_basis[seen_bn][j][seen];
        sbt_neigh_basis_bn[ns_bn][j][ns]
                        = sbt_neigh_basis_bn[seen_bn][j][seen];
      }

      for (j=0; j<cdim; j++) {
        q = sbt_neigh_basis[seen_bn][j][seen];
        q_bn = sbt_neigh_basis_bn[seen_bn][j][seen];
        if (q != NOVAL) bbt_ref_count[q_bn][q]++;
      }

      sbt_visit[ns_bn][ns] = 0;
      sbt_peak_vert[ns_bn][ns] = NOVAL;
      sbt_normal[ns_bn][ns] = NOVAL;
      sbt_peak_simp[ns_bn][ns] = seen;
      sbt_peak_simp_bn[ns_bn][ns] = seen_bn;

      q = sbt_neigh_basis[ns_bn][i][ns];
      q_bn = sbt_neigh_basis_bn[ns_bn][i][ns];
      if (q != NOVAL && --bbt_ref_count[q_bn][q] == 0) {
        bbt_next[q_bn][q] = basis_s_list;
        bbt_next_bn[q_bn][q] = basis_s_list_bn;
        bbt_ref_count[q_bn][q] = 0;
        bbt_lscale[q_bn][q] = 0;
        bbt_sqa[q_bn][q] = 0;
        bbt_sqb[q_bn][q] = 0;
        for (int l=0; l<2*rdim; l++) bbt_vecs[q_bn][l][q] = 0;
        basis_s_list = q;
        basis_s_list_bn = q_bn;
      }
      sbt_neigh_basis[ns_bn][i][ns] = NOVAL;

      sbt_neigh_vert[ns_bn][i][ns] = p;
      for (j=0; (sbt_neigh_simp[n_bn][j][n] != seen
                  || sbt_neigh_simp_bn[n_bn][j][n] != seen_bn)
                  && j<cdim; j++);
      sbt_neigh_simp[seen_bn][i][seen] = sbt_neigh_simp[n_bn][j][n] = ns;
      sbt_neigh_simp_bn[seen_bn][i][seen] = ns_bn;
      sbt_neigh_simp_bn[n_bn][j][n] = ns_bn;
    }
    ret[0] = ns;
    ret_bn[0] = ns_bn;
  }

  /**
   * P lies outside flat containing previous sites; make p a vertex of every current simplex, and create some new simplices.
   * @param s the start simplex
   * @param s_bn the start simplex back
   * @param ret the return index
   * @param ret_bn the return index back
  */
  private void extend_simplices(int s, int s_bn, int[] ret, int[] ret_bn) {
    int q, q_bn;
    int ns, ns_bn;

    if (sbt_visit[s_bn][s] == pnum) {
      if (sbt_peak_vert[s_bn][s] != NOVAL) {
        ret[0] = sbt_neigh_simp[s_bn][cdim-1][s];
        ret_bn[0] = sbt_neigh_simp_bn[s_bn][cdim-1][s];
      }
      else {
        ret[0] = s;
        ret_bn[0] = s_bn;
      }
      return;
    }
    sbt_visit[s_bn][s] = pnum;
    sbt_neigh_vert[s_bn][cdim-1][s] = p;
    q = sbt_normal[s_bn][s];
    q_bn = sbt_normal_bn[s_bn][s];
    if (q != NOVAL && --bbt_ref_count[q_bn][q] == 0) {
      bbt_next[q_bn][q] = basis_s_list;
      bbt_next_bn[q_bn][q] = basis_s_list_bn;
      bbt_ref_count[q_bn][q] = 0;
      bbt_lscale[q_bn][q] = 0;
      bbt_sqa[q_bn][q] = 0;
      bbt_sqb[q_bn][q] = 0;
      for (int j=0; j<2*rdim; j++) bbt_vecs[q_bn][j][q] = 0;
      basis_s_list = q;
      basis_s_list_bn = q_bn;
    }
    sbt_normal[s_bn][s] = NOVAL;

    q = sbt_neigh_basis[s_bn][0][s];
    q_bn = sbt_neigh_basis_bn[s_bn][0][s];
    if (q != NOVAL && --bbt_ref_count[q_bn][q] == 0) {
      bbt_next[q_bn][q] = basis_s_list;
      bbt_ref_count[q_bn][q] = 0;
      bbt_lscale[q_bn][q] = 0;
      bbt_sqa[q_bn][q] = 0;
      bbt_sqb[q_bn][q] = 0;
      for (int j=0; j<2*rdim; j++) bbt_vecs[q_bn][j][q] = 0;

      basis_s_list = q;
      basis_s_list_bn = q_bn;
    }
    sbt_neigh_basis[s_bn][0][s] = NOVAL;

    if (sbt_peak_vert[s_bn][s] == NOVAL) {
      int[] esretp = new int[1];
      int[] esretp_bn = new int[1];
      extend_simplices(sbt_peak_simp[s_bn][s],
                       sbt_peak_simp_bn[s_bn][s], esretp, esretp_bn);
      sbt_neigh_simp[s_bn][cdim-1][s] = esretp[0];
      sbt_neigh_simp_bn[s_bn][cdim-1][s] = esretp_bn[0];
      ret[0] = s;
      ret_bn[0] = s_bn;
      return;
    }
    else {
      ns = (simplex_list != NOVAL) ? simplex_list : new_block_simplex();
      ns_bn = simplex_list_bn;
      simplex_list = sbt_next[ns_bn][ns];
      simplex_list_bn = sbt_next_bn[ns_bn][ns];
      sbt_next[ns_bn][ns] = sbt_next[s_bn][s];
      sbt_next_bn[ns_bn][ns] = sbt_next_bn[s_bn][s];
      sbt_visit[ns_bn][ns] = sbt_visit[s_bn][s];
      sbt_mark[ns_bn][ns] = sbt_mark[s_bn][s];
      sbt_normal[ns_bn][ns] = sbt_normal[s_bn][s];
      sbt_normal_bn[ns_bn][ns] = sbt_normal_bn[s_bn][s];
      sbt_peak_vert[ns_bn][ns] = sbt_peak_vert[s_bn][s];
      sbt_peak_simp[ns_bn][ns] = sbt_peak_simp[s_bn][s];
      sbt_peak_simp_bn[ns_bn][ns] = sbt_peak_simp_bn[s_bn][s];
      sbt_peak_basis[ns_bn][ns] = sbt_peak_basis[s_bn][s];
      sbt_peak_basis_bn[ns_bn][ns] = sbt_peak_basis_bn[s_bn][s];
      for (int j=0; j<rdim; j++) {
        sbt_neigh_vert[ns_bn][j][ns] = sbt_neigh_vert[s_bn][j][s];
        sbt_neigh_simp[ns_bn][j][ns] = sbt_neigh_simp[s_bn][j][s];
        sbt_neigh_simp_bn[ns_bn][j][ns] = sbt_neigh_simp_bn[s_bn][j][s];
        sbt_neigh_basis[ns_bn][j][ns] = sbt_neigh_basis[s_bn][j][s];
        sbt_neigh_basis_bn[ns_bn][j][ns] = sbt_neigh_basis_bn[s_bn][j][s];
      }

      for (int j=0; j<cdim; j++) {
        q = sbt_neigh_basis[s_bn][j][s];
        q_bn = sbt_neigh_basis_bn[s_bn][j][s];
        if (q != NOVAL) bbt_ref_count[q_bn][q]++;
      }

      sbt_neigh_simp[s_bn][cdim-1][s] = ns;
      sbt_neigh_simp_bn[s_bn][cdim-1][s] = ns_bn;
      sbt_peak_vert[ns_bn][ns] = NOVAL;
      sbt_peak_simp[ns_bn][ns] = s;
      sbt_peak_simp_bn[ns_bn][ns] = s_bn;
      sbt_neigh_vert[ns_bn][cdim-1][ns] = sbt_peak_vert[s_bn][s];
      sbt_neigh_simp[ns_bn][cdim-1][ns] = sbt_peak_simp[s_bn][s];
      sbt_neigh_simp_bn[ns_bn][cdim-1][ns] = sbt_peak_simp_bn[s_bn][s];
      sbt_neigh_basis[ns_bn][cdim-1][ns] = sbt_peak_basis[s_bn][s];
      sbt_neigh_basis_bn[ns_bn][cdim-1][ns] = sbt_peak_basis_bn[s_bn][s];
      q = sbt_peak_basis[s_bn][s];
      q_bn = sbt_peak_basis_bn[s_bn][s];
      if (q != NOVAL) bbt_ref_count[q_bn][q]++;
      for (int i=0; i<cdim; i++) {
        int[] esretp = new int[1];
        int[] esretp_bn = new int[1];
        extend_simplices(sbt_neigh_simp[ns_bn][i][ns],
                         sbt_neigh_simp_bn[ns_bn][i][ns], esretp, esretp_bn);
        sbt_neigh_simp[ns_bn][i][ns] = esretp[0];
        sbt_neigh_simp_bn[ns_bn][i][ns] = esretp_bn[0];
      }
    }
    ret[0] = ns;
    ret_bn[0] = ns_bn;
    return;
  }

  /**
   * Return a simplex s that corresponds to a facet of the current hull, and sees(p, s).
   * @param root the root
   * @param root_bn  the root back
   * @param ret the return index
   * @param ret_bn the return index back
  */
  private void search(int root, int root_bn, int[] ret, int[] ret_bn) {
    int s, s_bn;
    int tms = 0;

    st[tms] = sbt_peak_simp[root_bn][root];
    st_bn[tms] = sbt_peak_simp_bn[root_bn][root];
    tms++;
    sbt_visit[root_bn][root] = pnum;
    if (sees(p, root, root_bn) == 0) {
      for (int i=0; i<cdim; i++) {
        st[tms] = sbt_neigh_simp[root_bn][i][root];
        st_bn[tms] = sbt_neigh_simp_bn[root_bn][i][root];
        tms++;
      }
    }
    while (tms != 0) {
      if (tms > ss) {
        // JAVA: efficiency issue: how much is this stack hammered?
        ss += ss;
        int[] newst = new int[ss+MAXDIM+1];
        int[] newst_bn = new int[ss+MAXDIM+1];
        System.arraycopy(st, 0, newst, 0, st.length);
        System.arraycopy(st_bn, 0, newst_bn, 0, st_bn.length);
        st = newst;
        st_bn = newst_bn;
      }
      tms--;
      s = st[tms];
      s_bn = st_bn[tms];
      if (sbt_visit[s_bn][s] == pnum) continue;
      sbt_visit[s_bn][s] = pnum;
      if (sees(p, s, s_bn) == 0) continue;
      if (sbt_peak_vert[s_bn][s] == NOVAL) {
        ret[0] = s;
        ret_bn[0] = s_bn;
        return;
      }
      for (int i=0; i<cdim; i++) {
        st[tms] = sbt_neigh_simp[s_bn][i][s];
        st_bn[tms] = sbt_neigh_simp_bn[s_bn][i][s];
        tms++;
      }
    }
    ret[0] = NOVAL;
    return;
  }

  /**
   * Create a new instance of this algorithm.
   */
  public DelaunayClarkson(){
    super();
  }
  
  /**
   * Compute the convex hull of the set of points given in parameter as <code>samples</code>. 
   * As the algorithm relies on the use of integer coordinates, the <code>scale</code> parameter is multiplied to each coordinate in order 
   * to obtain an integer value by truncating the result. The <code>scale</code> parameter can represent the precision of the points.
   * @param samples the set of points used for convex hull computation.
   * @param scale the scale to apply to the point coordinates in order to obtain integer values after truncating them.
   * @throws DelaunayException if an error occurs.
   */
  public void compute(double[][] samples, float scale) throws DelaunayException {
    simplexes = null;
    vertices = null;
    neighbors = null;
    Edges = null;
    NumEdges = 0;

    int s, s_bn, q, q_bn;
    int root, root_bn;

    int[] retp = new int[1];
    int[] retp_bn = new int[1];
    int[] ret2p = new int[1];
    int[] ret2p_bn = new int[1];
    int[] curt = new int[1];
    int[] curt_bn = new int[1];
    int s_num = 0;
    int nrs;

    this.samples = samples;
    
    // Start of main hull triangulation algorithm
    dim = samples.length;
    nrs = samples[0].length;
    for (int i=1; i<dim; i++) nrs = Math.min(nrs, samples[i].length);

    if (nrs <= dim) throw new DelaunayException("DelaunayClarkson: "
                                          +"not enough samples");
    if (dim > MAXDIM) throw new DelaunayException("DelaunayClarkson: "
                               +"dimension bound MAXDIM exceeded");

    // copy samples
    site_blocks = new double[dim][nrs];
    for (int j=0; j<dim; j++) {
      System.arraycopy(samples[j], 0, site_blocks[j], 0, nrs);
    }

    // WLH 29 Jan 98 - scale samples values to avoid integer convertion loss of precision
    for (int j=0; j<dim; j++) {
      for (int kk=0; kk<nrs; kk++) {
        site_blocks[j][kk] = scale * samples[j][kk];
      }
    }


    exact_bits = (int) (DBL_MANT_DIG*Math.log(FLT_RADIX)/ln2);
    b_err_min = DBL_EPSILON*MAXDIM*(1<<MAXDIM)*MAXDIM*3.01;
    b_err_min_sq = b_err_min * b_err_min;

    cdim = 0;
    rdim = dim+1;
    if (rdim > MAXDIM) throw new DelaunayException(
              "dimension bound MAXDIM exceeded; rdim="+rdim+"; dim="+dim);

    pnb = basis_s_list != NOVAL ? basis_s_list : new_block_basis_s();
    pnb_bn = basis_s_list_bn;
    basis_s_list = bbt_next[pnb_bn][pnb];
    basis_s_list_bn = bbt_next_bn[pnb_bn][pnb];
    bbt_next[pnb_bn][pnb] = NOVAL;

    ttbp = basis_s_list != NOVAL ? basis_s_list : new_block_basis_s();
    ttbp_bn = basis_s_list_bn;
    basis_s_list = bbt_next[ttbp_bn][ttbp];
    basis_s_list_bn = bbt_next_bn[ttbp_bn][ttbp];
    bbt_next[ttbp_bn][ttbp] = NOVAL;
    bbt_ref_count[ttbp_bn][ttbp] = 1;
    bbt_lscale[ttbp_bn][ttbp] = -1;
    bbt_sqa[ttbp_bn][ttbp] = 0;
    bbt_sqb[ttbp_bn][ttbp] = 0;
    for (int j=0; j<2*rdim; j++) bbt_vecs[ttbp_bn][j][ttbp] = 0;

    root = NOVAL;
    p = INFINITY;
    ib = (basis_s_list != NOVAL) ? basis_s_list : new_block_basis_s();
    ib_bn = basis_s_list_bn;
    basis_s_list = bbt_next[ib_bn][ib];
    basis_s_list_bn = bbt_next_bn[ib_bn][ib];
    bbt_ref_count[ib_bn][ib] = 1;
    bbt_vecs[ib_bn][2*rdim-1][ib] = bbt_vecs[ib_bn][rdim-1][ib] = 1;
    bbt_sqa[ib_bn][ib] = bbt_sqb[ib_bn][ib] = 1;

    root = (simplex_list != NOVAL) ? simplex_list : new_block_simplex();
    root_bn = simplex_list_bn;
    simplex_list = sbt_next[root_bn][root];
    simplex_list_bn = sbt_next_bn[root_bn][root];

    ch_root = root;
    ch_root_bn = root_bn;

    s = (simplex_list != NOVAL) ? simplex_list : new_block_simplex();
    s_bn = simplex_list_bn;
    simplex_list = sbt_next[s_bn][s];
    simplex_list_bn = sbt_next_bn[s_bn][s];
    sbt_next[s_bn][s] = sbt_next[root_bn][root];
    sbt_next_bn[s_bn][s] = sbt_next_bn[root_bn][root];
    sbt_visit[s_bn][s] = sbt_visit[root_bn][root];
    sbt_mark[s_bn][s] = sbt_mark[root_bn][root];
    sbt_normal[s_bn][s] = sbt_normal[root_bn][root];
    sbt_normal_bn[s_bn][s] = sbt_normal_bn[root_bn][root];
    sbt_peak_vert[s_bn][s] = sbt_peak_vert[root_bn][root];
    sbt_peak_simp[s_bn][s] = sbt_peak_simp[root_bn][root];
    sbt_peak_simp_bn[s_bn][s] = sbt_peak_simp_bn[root_bn][root];
    sbt_peak_basis[s_bn][s] = sbt_peak_basis[root_bn][root];
    sbt_peak_basis_bn[s_bn][s] = sbt_peak_basis_bn[root_bn][root];
    for (int i=0; i<rdim; i++) {
      sbt_neigh_vert[s_bn][i][s] = sbt_neigh_vert[root_bn][i][root];
      sbt_neigh_simp[s_bn][i][s] = sbt_neigh_simp[root_bn][i][root];
      sbt_neigh_simp_bn[s_bn][i][s] = sbt_neigh_simp_bn[root_bn][i][root];
      sbt_neigh_basis[s_bn][i][s] = sbt_neigh_basis[root_bn][i][root];
      sbt_neigh_basis_bn[s_bn][i][s] = sbt_neigh_basis_bn[root_bn][i][root];
    }
    for (int i=0;i<cdim;i++) {
      q = sbt_neigh_basis[root_bn][i][root];
      q_bn = sbt_neigh_basis_bn[root_bn][i][root];
      if (q != NOVAL) bbt_ref_count[q_bn][q]++;
    }
    sbt_peak_vert[root_bn][root] = p;
    sbt_peak_simp[root_bn][root] = s;
    sbt_peak_simp_bn[root_bn][root] = s_bn;
    sbt_peak_simp[s_bn][s] = root;
    sbt_peak_simp_bn[s_bn][s] = root_bn;
    while (cdim < rdim) {
      int oof = 0;

      if (s_num == 0) p = 0;
      else p++;
      for (int i=0; i<dim; i++) {
        site_blocks[i][p] = Math.floor(site_blocks[i][p]+0.5);
      }
      s_num++;
      pnum = (s_num*dim-1)/dim + 2;

      cdim++;
      sbt_neigh_vert[root_bn][cdim-1][root] = sbt_peak_vert[root_bn][root];

      q = sbt_neigh_basis[root_bn][cdim-1][root];
      q_bn = sbt_neigh_basis_bn[root_bn][cdim-1][root];
      if (q != NOVAL && --bbt_ref_count[q_bn][q] == 0) {
        bbt_next[q_bn][q] = basis_s_list;
        bbt_next_bn[q_bn][q] = basis_s_list_bn;
        bbt_ref_count[q_bn][q] = 0;
        bbt_lscale[q_bn][q] = 0;
        bbt_sqa[q_bn][q] = 0;
        bbt_sqb[q_bn][q] = 0;
        for (int l=0; l<2*rdim; l++) bbt_vecs[q_bn][l][q] = 0;

        basis_s_list = q;
        basis_s_list_bn = q_bn;
      }
      sbt_neigh_basis[root_bn][cdim-1][root] = NOVAL;

      get_basis_sede(root, root_bn);
      if (sbt_neigh_vert[root_bn][0][root] == INFINITY) oof = 1;
      else {
        curt[0] = pnb;
        curt_bn[0] = pnb_bn;
        reduce(curt, curt_bn, p, root, root_bn, cdim);
        pnb = curt[0];
        pnb_bn = curt_bn[0];
        if (bbt_sqa[pnb_bn][pnb] != 0) oof = 1;
        else cdim--;
      }
      if (oof != 0) extend_simplices(root, root_bn, voidp, voidp_bn);
      else {
        search(root, root_bn, retp, retp_bn);
        make_facets(retp[0], retp_bn[0], ret2p, ret2p_bn);
        connect(ret2p[0], ret2p_bn[0]);
      }
    }

    for (int i=s_num; i<nrs; i++) {
      p++;
      s_num++;
      for (int j=0; j<dim; j++) {
        site_blocks[j][p] = Math.floor(site_blocks[j][p]+0.5);
      }
      pnum = (s_num*dim-1)/dim + 2;
      search(root, root_bn, retp, retp_bn);
      make_facets(retp[0], retp_bn[0], ret2p, ret2p_bn);
      connect(ret2p[0], ret2p_bn[0]);
    }

    a3size = rdim*nrs;
    a3s = new int[rdim][a3size+MAXDIM+1];
    visit_triang_gen(root, root_bn, 1, retp, retp_bn);
    visit_triang_gen(retp[0], retp_bn[0], 0, voidp, voidp_bn);

    // deallocate memory
    /* NOTE: If this deallocation is not performed, more points
       could theoretically be added to the triangulation later */
    site_blocks = null;
    st = null;
    st_bn = null;
    st2 = null;
    st2_bn = null;
    sbt_next = null;
    sbt_next_bn = null;
    sbt_visit = null;
    sbt_mark = null;
    sbt_normal = null;
    sbt_normal_bn = null;
    sbt_peak_vert = null;
    sbt_peak_simp = null;
    sbt_peak_simp_bn = null;
    sbt_peak_basis = null;
    sbt_peak_basis_bn = null;
    sbt_neigh_vert = null;
    sbt_neigh_simp = null;
    sbt_neigh_simp_bn = null;
    sbt_neigh_basis = null;
    sbt_neigh_basis_bn = null;
    bbt_next = null;
    bbt_next_bn = null;
    bbt_ref_count = null;
    bbt_lscale = null;
    bbt_sqa = null;
    bbt_sqb = null;
    bbt_vecs = null;

/* ********** END OF CONVERTED HULL CODE ********** */
/*          (but still inside constructor)          */

    // compute number of triangles or tetrahedra
    int[] nverts = new int[nrs];
    for (int i=0; i<nrs; i++) nverts[i] = 0;
    int ntris = 0;
    boolean positive;
    for (int i=0; i<nts; i++) {
      positive = true;
      for (int j=0; j<rdim; j++) {
        if (a3s[j][i] < 0) positive = false;
      }
      if (positive) {
        ntris++;
        for (int j=0; j<rdim; j++) nverts[a3s[j][i]]++;
      }
    }
    vertices = new int[nrs][];
    for (int i=0; i<nrs; i++) vertices[i] = new int[nverts[i]];
    for (int i=0; i<nrs; i++) nverts[i] = 0;

    // build Tri & Vertices components
    simplexes = new int[ntris][rdim];

    int itri = 0;
    for (int i=0; i<nts; i++) {
      positive = true;
      for (int j=0; j<rdim; j++) {
        if (a3s[j][i] < 0) positive = false;
      }
      if (positive) {
        for (int j=0; j<rdim; j++) {
          vertices[a3s[j][i]][nverts[a3s[j][i]]++] = itri;
          simplexes[itri][j] = a3s[j][i];
        }
        itri++;
      }
    }

    // Deallocate remaining helper information
    a3s = null;

    // call more generic method for constructing Walk and Edges arrays
    finish_triang(samples);
  }
  
  /**
   * Checks a triangulation in various ways to make sure it is constructed correctly; test returns false if there are any problems with the triangulation.  
   * This method is expensive, provided mainly for debugging purposes. 
   * @return <code>true</code> if all the test are passing and <code>false</code> otherwise.
   */
  public boolean valid() {

    int dim = samples.length;
    int dim1 = dim+1;
    int ntris = simplexes.length;
    int nrs = samples[0].length;
    for (int i=1; i<dim; i++) {
      nrs = Math.min(nrs, samples[i].length);
    }

    // verify triangulation dimension
    for (int i=0; i<ntris; i++) {
      if (simplexes[i].length < dim1){
        System.out.println("Bad triangulation dimension: "+simplexes[i].length+" (should be "+dim1+")");
        return false;
      }
    }

    // verify no illegal triangle vertices
    for (int i=0; i<ntris; i++) {
      for (int j=0; j<dim1; j++) {
        if (simplexes[i][j] < 0 || simplexes[i][j] >= nrs){
          System.out.println("Illegal vertice found: "+simplexes[i][j]+" for triangle "+i);
          return false;
        }
      }
    }

    // verify that all points are in at least one triangle
    int[] nverts = new int[nrs];
    for (int i=0; i<nrs; i++) nverts[i] = 0;
    for (int i=0; i<ntris; i++) {
      for (int j=0; j<dim1; j++) nverts[simplexes[i][j]]++;
    }
    for (int i=0; i<nrs; i++) {
      if (nverts[i] == 0){
        System.out.println("Point "+i+" is not in any triangle.");
        return false;
      }
    }

    // test for duplicate triangles
    for (int i=0; i<ntris; i++) {
      for (int j=i+1; j<ntris; j++) {
        boolean[] m = new boolean[dim1];
        for (int mi=0; mi<dim1; mi++) m[mi] = false;
        for (int k=0; k<dim1; k++) {
          for (int l=0; l<dim1; l++) {
            if (simplexes[i][k] == simplexes[j][l] && !m[l]) {
              m[l] = true;
            }
          }
        }
        boolean mtot = true;
        for (int k=0; k<dim1; k++) {
          if (!m[k]) mtot = false;
        }
        if (mtot) return false;
      }
    }

    // test for errors in Walk array
    if (neighbors != null){
      for (int i=0; i<ntris; i++) {
        for (int j=0; j<dim1; j++) {
          if (neighbors[i][j] != -1) {
            boolean found = false;
            for (int k=0; k<dim1; k++) {
              if (neighbors[neighbors[i][j]][k] == i) found = true;
            }
            if (!found) return false;

            // make sure two walk'ed triangles share dim vertices
            int sb = 0;
            for (int k=0; k<dim1; k++) {
              for (int l=0; l<dim1; l++) {
                if (simplexes[i][k] == simplexes[neighbors[i][j]][l]) sb++;
              }
            }
            if (sb != dim) return false;
          }
        }
      }
    }

    // Note: Another test that could be performed is one that
    //       makes sure, given a triangle T, all points in the
    //       triangulation that are not part of T are located
    //       outside the bounds of T.  This test would verify
    //       that there are no overlapping triangles.

    // all tests passed
    return true;
  }

  /**
   * Improve uses edge-flipping to bring the current triangulation closer to the true Delaunay triangulation.  
   * pass is the number of passes the algorithm should take over all edges (however, the algorithm terminates if no edges are flipped for an entire pass).
   * @param pass the number of passes the algorithm should take over all edges.
   * @throws DelaunayException if an error occurs.
   */
  public void improve(int pass) throws DelaunayException {
    int dim = samples.length;
    int dim1 = dim+1;
    if (simplexes[0].length != dim1) {
      throw new DelaunayException("Delaunay.improve: samples dimension " +
                             "does not match");
    }
    // only 2-D triangulations supported for now
    if (dim > 2) {
      throw new DelaunayException("Delaunay.improve: dimension " +
                                       "must be 2!");
    }
    int ntris = simplexes.length;
    int nrs = samples[0].length;
    for (int i=1; i<dim; i++) {
      nrs = Math.min(nrs, samples[i].length);
    }
    double[] samp0 = samples[0];
    double[] samp1 = samples[1];

    // go through entire triangulation pass times
    boolean eflipped = false;
    for (int p=0; p<pass; p++) {
      eflipped = false;

      // edge keeps track of which edges have been checked
      boolean[] edge = new boolean[NumEdges];
      for (int i=0; i<NumEdges; i++) edge[i] = true;

      // check every edge of every triangle
      for (int t=0; t<ntris; t++) {
        int[] trit = simplexes[t];
        int[] walkt = neighbors[t];
        int[] edgest = Edges[t];
        for (int e=0; e<2; e++) {
          int curedge = edgest[e];
          // only check the edge if it hasn't been checked yet
          if (edge[curedge]) {
            int t2 = walkt[e];

            // only check edge if it is not part of the outer hull
            if (t2 >= 0) {
              int[] trit2 = simplexes[t2];
              int[] walkt2 = neighbors[t2];
              int[] edgest2 = Edges[t2];

              // check if the diagonal needs to be flipped
              int f = (walkt2[0] == t) ? 0 :
                      (walkt2[1] == t) ? 1 : 2;
              int A = (e + 2) % 3;
              int B = (A + 1) % 3;
              int C = (B + 1) % 3;
              int D = (f + 2) % 3;
              double ax = samp0[trit[A]];
              double ay = samp1[trit[A]];
              double bx = samp0[trit[B]];
              double by = samp1[trit[B]];
              double cx = samp0[trit[C]];
              double cy = samp1[trit[C]];
              double dx = samp0[trit2[D]];
              double dy = samp1[trit2[D]];
              double abx = ax - bx;
              double aby = ay - by;
              double acx = ax - cx;
              double acy = ay - cy;
              double dbx = dx - bx;
              double dby = dy - by;
              double dcx = dx - cx;
              double dcy = dy - cy;
              double Q = abx*acx + aby*acy;
              double R = dbx*abx + dby*aby;
              double S = acx*dcx + acy*dcy;
              double T = dbx*dcx + dby*dcy;
              boolean QD = abx*acy - aby*acx >= 0;
              boolean RD = dbx*aby - dby*abx >= 0;
              boolean SD = acx*dcy - acy*dcx >= 0;
              boolean TD = dcx*dby - dcy*dbx >= 0;
              boolean sig = (QD ? 1 : 0) + (RD ? 1 : 0)
                          + (SD ? 1 : 0) + (TD ? 1 : 0) < 2;
              boolean d;
              if (QD == sig) d = true;
              else if (RD == sig) d = false;
              else if (SD == sig) d = false;
              else if (TD == sig) d = true;
              else if (Q < 0 && T < 0 || R > 0 && S > 0) d = true;
              else if (R < 0 && S < 0 || Q > 0 && T > 0) d = false;
              else if ((Q < 0 ? Q : T) < (R < 0 ? R : S)) d = true;
              else d = false;
              if (d) {
                // diagonal needs to be swapped
                eflipped = true;
                int n1 = trit[A];
                int n2 = trit[B];
                int n3 = trit[C];
                int n4 = trit2[D];
                int w1 = walkt[A];
                int w2 = walkt[C];
                int e1 = edgest[A];
                int e2 = edgest[C];
                int w3, w4, e3, e4;
                if (trit2[(D+1)%3] == trit[C]) {
                  w3 = walkt2[D];
                  w4 = walkt2[(D+2)%3];
                  e3 = edgest2[D];
                  e4 = edgest2[(D+2)%3];
                }
                else {
                  w3 = walkt2[(D+2)%3];
                  w4 = walkt2[D];
                  e3 = edgest2[(D+2)%3];
                  e4 = edgest2[D];
                }

                // update Tri array
                trit[0] = n1;
                trit[1] = n2;
                trit[2] = n4;
                trit2[0] = n1;
                trit2[1] = n4;
                trit2[2] = n3;

                // update Walk array
                walkt[0] = w1;
                walkt[1] = w4;
                walkt[2] = t2;
                walkt2[0] = t;
                walkt2[1] = w3;
                walkt2[2] = w2;
                if (w2 >= 0) {
                  int val = (neighbors[w2][0] == t) ? 0
                          : (neighbors[w2][1] == t) ? 1 : 2;
                  neighbors[w2][val] = t2;
                }
                if (w4 >= 0) {
                  int val = (neighbors[w4][0] == t2) ? 0
                          : (neighbors[w4][1] == t2) ? 1 : 2;
                  neighbors[w4][val] = t;
                }

                // update Edges array
                edgest[0] = e1;
                edgest[1] = e4;
                // Edges[t][2] and Edges[t2][0] stay the same
                edgest2[1] = e3;
                edgest2[2] = e2;

                // update Vertices array
                int[] vertn1 = vertices[n1];
                int[] vertn2 = vertices[n2];
                int[] vertn3 = vertices[n3];
                int[] vertn4 = vertices[n4];
                int ln1 = vertn1.length;
                int ln2 = vertn2.length;
                int ln3 = vertn3.length;
                int ln4 = vertn4.length;
                int[] tn1 = new int[ln1 + 1];  // Vertices[n1] adds t2
                int[] tn2 = new int[ln2 - 1];  // Vertices[n2] loses t2
                int[] tn3 = new int[ln3 - 1];  // Vertices[n3] loses t
                int[] tn4 = new int[ln4 + 1];  // Vertices[n4] adds t
                System.arraycopy(vertn1, 0, tn1, 0, ln1);
                tn1[ln1] = t2;
                int c = 0;
                for (int i=0; i<ln2; i++) {
                  if (vertn2[i] != t2) tn2[c++] = vertn2[i];
                }
                c = 0;
                for (int i=0; i<ln3; i++) {
                  if (vertn3[i] != t) tn3[c++] = vertn3[i];
                }
                System.arraycopy(vertn4, 0, tn4, 0, ln4);
                tn4[ln4] = t;
                vertices[n1] = tn1;
                vertices[n2] = tn2;
                vertices[n3] = tn3;
                vertices[n4] = tn4;
              }
            }

            // the edge has now been checked
            edge[curedge] = false;
          }
        }
      }

      // if no edges have been flipped this pass, then stop
      if (!eflipped) break;
    }
  }

  /** 
   * Finish_triang calculates a triangulation's helper arrays, Walk and Edges, if the triangulation algorithm hasn't calculated them already.  
   * Any extension to the Delaunay class should call finish_triang at the end of its triangulation constructor. 
   * @param samples the samples 
   * @throws DelaunayException if an error occurs
   */
  private void finish_triang(double[][] samples) throws DelaunayException {
    int mdim = simplexes[0].length - 1;
    int mdim1 = mdim + 1;
    int dim = samples.length;

    int ntris = simplexes.length;
    int nrs = samples[0].length;
    for (int i=1; i<dim; i++) {
      nrs = Math.min(nrs, samples[i].length);
    }

    if (vertices == null) {
      // build Vertices component
      vertices = new int[nrs][];
      int[] nverts = new int[nrs];
      for (int i=0; i<ntris; i++) {
        for (int j=0; j<mdim1; j++) nverts[simplexes[i][j]]++;
      }
      for (int i=0; i<nrs; i++) {
        vertices[i] = new int[nverts[i]];
        nverts[i] = 0;
      }
      for (int i=0; i<ntris; i++) {
        for (int j=0; j<mdim1; j++) {
          vertices[simplexes[i][j]][nverts[simplexes[i][j]]++] = i;
        }
      }
    }

    if (neighbors == null && mdim <= 3) {
      // build Walk component
      neighbors = new int[ntris][mdim1];
      for (int i=0; i<ntris; i++) {
      WalkDim:
        for (int j=0; j<mdim1; j++) {
          int v1 = j;
          int v2 = (v1+1)%mdim1;
          neighbors[i][j] = -1;
          for (int k=0; k<vertices[simplexes[i][v1]].length; k++) {
            int temp = vertices[simplexes[i][v1]][k];
            if (temp != i) {
              for (int l=0; l<vertices[simplexes[i][v2]].length; l++) {
                if (mdim == 2) {
                  if (temp == vertices[simplexes[i][v2]][l]) {
                    neighbors[i][j] = temp;
                    continue WalkDim;
                  }
                }
                else {    // mdim == 3
                  int temp2 = vertices[simplexes[i][v2]][l];
                  int v3 = (v2+1)%mdim1;
                  if (temp == temp2) {
                    for (int m=0; m<vertices[simplexes[i][v3]].length; m++) {
                      if (temp == vertices[simplexes[i][v3]][m]) {
                        neighbors[i][j] = temp;
                        continue WalkDim;
                      }
                    }
                  }
                } // end if (mdim == 3)
              } // end for (int l=0; l<Vertices[Tri[i][v2]].length; l++)
            } // end if (temp != i)
          } // end for (int k=0; k<Vertices[Tri[i][v1]].length; k++)
        } // end for (int j=0; j<mdim1; j++)
      } // end for (int i=0; i<Tri.length; i++)
    } // end if (Walk == null && mdim <= 3)

    if (Edges == null && mdim <= 3) {
      // build Edges component

      // initialize all edges to "not yet found"
      int edim = 3*(mdim-1);
      Edges = new int[ntris][edim];
      for (int i=0; i<ntris; i++) {
        for (int j=0; j<edim; j++) Edges[i][j] = -1;
      }

      // calculate global edge values
      NumEdges = 0;
      if (mdim == 2) {
        for (int i=0; i<ntris; i++) {
          for (int j=0; j<3; j++) {
            if (Edges[i][j] < 0) {
              // this edge doesn't have a "global edge number" yet
              int othtri = neighbors[i][j];
              if (othtri >= 0) {
                int cside = -1;
                for (int k=0; k<3; k++) {
                  if (neighbors[othtri][k] == i) cside = k;
                }
                if (cside != -1) {
                  Edges[othtri][cside] = NumEdges;
                }
                else {
                  throw new DelaunayException("Delaunay.finish_triang: " +
                                         "error in triangulation!");
                }
              }
              Edges[i][j] = NumEdges++;
            }
          }
        }
      }
      else {    // mdim == 3
        int[] ptlook1 = {0, 0, 0, 1, 1, 2};
        int[] ptlook2 = {1, 2, 3, 2, 3, 3};
        for (int i=0; i<ntris; i++) {
          for (int j=0; j<6; j++) {
            if (Edges[i][j] < 0) {
              // this edge doesn't have a "global edge number" yet

              // search through the edge's two end points
              int endpt1 = simplexes[i][ptlook1[j]];
              int endpt2 = simplexes[i][ptlook2[j]];

              // create an intersection of two sets
              int[] set = new int[vertices[endpt1].length];
              int setlen = 0;
              for (int p1=0; p1<vertices[endpt1].length; p1++) {
                int temp = vertices[endpt1][p1];
                for (int p2=0; p2<vertices[endpt2].length; p2++) {
                  if (temp == vertices[endpt2][p2]) {
                    set[setlen++] = temp;
                    break;
                  }
                }
              }

              // assign global edge number to all members of set
              for (int kk=0; kk<setlen; kk++) {
                int k = set[kk];
                for (int l=0; l<edim; l++) {
                  if ((simplexes[k][ptlook1[l]] == endpt1
                    && simplexes[k][ptlook2[l]] == endpt2)
                   || (simplexes[k][ptlook1[l]] == endpt2
                    && simplexes[k][ptlook2[l]] == endpt1)) {
                    Edges[k][l] = NumEdges;
                  }
                }
              }
              Edges[i][j] = NumEdges++;
            } // end if (Edges[i][j] < 0)
          } // end for (int j=0; j<6; j++)
        } // end for (int i=0; i<ntris; i++)
      } // end if (mdim == 3)
    } // end if (Edges == null && mdim <= 3)
  }

  @Override
  public String toString() {
    return sampleString(null);
  }

  /**
   * Export the samples as a String.
   * @param samples the samples to export
   * @return the string
   */
  private String sampleString(double[][] samples) {
    StringBuffer s = new StringBuffer("");
    if (samples != null) {
      s.append("\nsamples " + samples[0].length + "\n");
      for (int i=0; i<samples[0].length; i++) {
        s.append("  " + i + " -> " + samples[0][i] + " " +
                 samples[1][i] + " " + samples[2][i] + "\n");
      }
      s.append("\n");
    }

    s.append("\nTri (triangles -> vertices) " + simplexes.length + "\n");
    for (int i=0; i<simplexes.length; i++) {
      s.append("  " + i + " -> ");
      for (int j=0; j<simplexes[i].length; j++) {
        s.append(" " + simplexes[i][j]);
      }
      s.append("\n");
    }

    s.append("\nVertices (vertices -> triangles) " + vertices.length + "\n");
    for (int i=0; i<vertices.length; i++) {
      s.append("  " + i + " -> ");
      for (int j=0; j<vertices[i].length; j++) {
        s.append(" " + vertices[i][j]);
      }
      s.append("\n");
    }

    if (neighbors != null){
      s.append("\nWalk (triangles -> triangles) " + neighbors.length + "\n");
      for (int i=0; i<neighbors.length; i++) {
        s.append("  " + i + " -> ");
        for (int j=0; j<neighbors[i].length; j++) {
          s.append(" " + neighbors[i][j]);
        }
        s.append("\n");
      }
    }

    if (Edges != null){
      s.append("\nEdges (triangles -> global edges) " + Edges.length + "\n");
      for (int i=0; i<Edges.length; i++) {
        s.append("  " + i + " -> ");
        for (int j=0; j<Edges[i].length; j++) {
          s.append(" " + Edges[i][j]);
        }
        s.append("\n");
      }
    }
    return s.toString();
  }
  
}