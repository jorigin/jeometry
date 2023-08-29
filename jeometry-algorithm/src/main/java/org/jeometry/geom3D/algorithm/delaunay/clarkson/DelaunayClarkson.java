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
  /** Internal private variable. */ private int[] st = new int[this.ss+MAXDIM+1];    // static: search
  /** Internal private variable. */ private int[] st_bn = new int[this.ss+MAXDIM+1];
  /** Internal private variable. */ private int[] st2 = new int[this.ss2+MAXDIM+1];    // static: visit_triang
  /** Internal private variable. */ private int[] st2_bn = new int[this.ss2+MAXDIM+1];

  
  /**
   * Get the samples to process with Delaunay computation. Samples can be 2D, 3D or higher dimension points. <br>
   * The underlying data structure is an array of double <code>d[n][m]</code> where <code>n</code> is the number of samples (io the number of points) 
   * and <code>m</code> is the sample space dimension (ie 2 for 2D, 3 for 3D, ...). 
   * @return the samples to process with Delaunay computation. Samples can be 2D, 3D or higher dimension points. 
   */
  public double[][] getSamples() {
    return this.samples;
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
    return this.simplexes;
  }

  /**
   * Get the vertices that compose the delaunay computation result. Each vertex is described by a reference to the {@link #getSamples() samples} it represents 
   * and by a list of references to simplexes that relie on this vertex.
   * @return the vertices that compose the delaunay computation result.
   * @see #getSamples()
   * @see #getSimplexes()
   */
  public int[][] getVertices() {
    return this.vertices;
  }

  /**
   * Get the neighborhood of the delaunay simplexes. 
   * @return the neighborhood of the delaunay simplexes. 
   */
  public int[][] getNeighbors() {
    return this.neighbors;
  }
  
/**
 * Compute new block basis.
 * @return the new block basis.
 */
  private int new_block_basis_s() {
    this.bbt_next[this.nbb] = new int[Nobj];
    this.bbt_next_bn[this.nbb] = new int[Nobj];
    this.bbt_ref_count[this.nbb] = new int[Nobj];
    this.bbt_lscale[this.nbb] = new int[Nobj];
    this.bbt_sqa[this.nbb] = new double[Nobj];
    this.bbt_sqb[this.nbb] = new double[Nobj];
    this.bbt_vecs[this.nbb] = new double[2*this.rdim][];
    for (int i=0; i<2*this.rdim; i++) this.bbt_vecs[this.nbb][i] = new double[Nobj];
    for (int i=0; i<Nobj; i++) {
      this.bbt_next[this.nbb][i] = i+1;
      this.bbt_next_bn[this.nbb][i] = this.nbb;
      this.bbt_ref_count[this.nbb][i] = 0;
      this.bbt_lscale[this.nbb][i] = 0;
      this.bbt_sqa[this.nbb][i] = 0;
      this.bbt_sqb[this.nbb][i] = 0;
      for (int j=0; j<2*this.rdim; j++) this.bbt_vecs[this.nbb][j][i] = 0;
    }
    this.bbt_next[this.nbb][Nobj-1] = NOVAL;
    this.basis_s_list = 0;
    this.basis_s_list_bn = this.nbb;
    this.nbb++;
    return this.basis_s_list;
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

    this.bbt_sqa[v_bn][v] = 0;
    for (int i=0; i<this.rdim; i++) {
      this.bbt_sqa[v_bn][v] += this.bbt_vecs[v_bn][i][v] * this.bbt_vecs[v_bn][i][v];
    }
    this.bbt_sqb[v_bn][v] = this.bbt_sqa[v_bn][v];
    if (k <= 1) {
      for (int i=0; i<this.rdim; i++) {
        this.bbt_vecs[v_bn][i][v] = this.bbt_vecs[v_bn][this.rdim+i][v];
      }
      return 1;
    }
    for (int j=0; j<250; j++) {
      int    xx = this.rdim;
      double labound;

      for (int i=0; i<this.rdim; i++) {
        this.bbt_vecs[v_bn][i][v] = this.bbt_vecs[v_bn][this.rdim+i][v];
      }
      for (int i=k-1; i>0; i--) {
        q = this.sbt_neigh_basis[s_bn][i][s];
        q_bn = this.sbt_neigh_basis_bn[s_bn][i][s];
        dd = 0;
        for (int l=0; l<this.rdim; l++) {
          dd -= this.bbt_vecs[q_bn][l][q] * this.bbt_vecs[v_bn][l][v];
        }
        dd /= this.bbt_sqb[q_bn][q];
        for (int l=0; l<this.rdim; l++) {
          this.bbt_vecs[v_bn][l][v] += dd * this.bbt_vecs[q_bn][this.rdim+l][q];
        }
      }
      this.bbt_sqb[v_bn][v] = 0;
      for (int i=0; i<this.rdim; i++) {
        this.bbt_sqb[v_bn][v] += this.bbt_vecs[v_bn][i][v] * this.bbt_vecs[v_bn][i][v];
      }
      this.bbt_sqa[v_bn][v] = 0;
      for (int i=0; i<this.rdim; i++) {
        this.bbt_sqa[v_bn][v] += this.bbt_vecs[v_bn][this.rdim+i][v]
                          * this.bbt_vecs[v_bn][this.rdim+i][v];
      }

      if (2*this.bbt_sqb[v_bn][v] >= this.bbt_sqa[v_bn][v]) return 1;

      // scale up vector
      if (j < 10) {
        labound = Math.floor(Math.log(this.bbt_sqa[v_bn][v])/ln2) / 2;
        this.max_scale = this.exact_bits-labound-0.66*(k-2)-1;
        if (this.max_scale < 1) this.max_scale = 1;

        if (j == 0) {

          this.ldetbound = 0;
          Sb = 0;
          for (int l=k-1; l>0; l--) {
            q = this.sbt_neigh_basis[s_bn][l][s];
            q_bn = this.sbt_neigh_basis_bn[s_bn][l][s];
            Sb += this.bbt_sqb[q_bn][q];
            this.ldetbound += Math.floor(Math.log(this.bbt_sqb[q_bn][q])/ln2) / 2 + 1;
            this.ldetbound -= this.bbt_lscale[q_bn][q];
          }
        }
      }
      if (this.ldetbound - this.bbt_lscale[v_bn][v]
        + Math.floor(Math.log(this.bbt_sqb[v_bn][v])/ln2) / 2 + 1 < 0) {
        scale = 0;
      }
      else {
        this.lscale = (int) (Math.log(2*Sb/(this.bbt_sqb[v_bn][v]
                                     + this.bbt_sqa[v_bn][v]*this.b_err_min))/ln2) / 2;
        if (this.lscale > this.max_scale) this.lscale = (int) this.max_scale;
        else if (this.lscale < 0) this.lscale = 0;
        this.bbt_lscale[v_bn][v] += this.lscale;
        scale = (this.lscale < 20) ? 1 << this.lscale : Math.pow(2, this.lscale);
      }

      while (xx < 2*this.rdim) this.bbt_vecs[v_bn][xx++][v] *= scale;

      for (int i=k-1; i>0; i--) {
        q = this.sbt_neigh_basis[s_bn][i][s];
        q_bn = this.sbt_neigh_basis_bn[s_bn][i][s];
        dd = 0;
        for (int l=0; l<this.rdim; l++) {
          dd -= this.bbt_vecs[q_bn][l][q] * this.bbt_vecs[v_bn][this.rdim+l][v];
        }
        dd /= this.bbt_sqb[q_bn][q];
        dd = Math.floor(dd+0.5);
        for (int l=0; l<this.rdim; l++) {
          this.bbt_vecs[v_bn][this.rdim+l][v] += dd * this.bbt_vecs[q_bn][this.rdim+l][q];
        }
      }
    }
    if (this.failcount++ < 10) System.out.println("reduce_inner failed!");
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
      v[0] = this.basis_s_list != NOVAL ? this.basis_s_list : new_block_basis_s();
      v_bn[0] = this.basis_s_list_bn;
      this.basis_s_list = this.bbt_next[v_bn[0]][v[0]];
      this.basis_s_list_bn = this.bbt_next_bn[v_bn[0]][v[0]];
      this.bbt_ref_count[v_bn[0]][v[0]] = 1;
    }
    else this.bbt_lscale[v_bn[0]][v[0]] = 0;
    if (rp == INFINITY) {
      this.bbt_next[v_bn[0]][v[0]] = this.bbt_next[this.ib_bn][this.ib];
      this.bbt_next_bn[v_bn[0]][v[0]] = this.bbt_next_bn[this.ib_bn][this.ib];
      this.bbt_ref_count[v_bn[0]][v[0]] = this.bbt_ref_count[this.ib_bn][this.ib];
      this.bbt_lscale[v_bn[0]][v[0]] = this.bbt_lscale[this.ib_bn][this.ib];
      this.bbt_sqa[v_bn[0]][v[0]] = this.bbt_sqa[this.ib_bn][this.ib];
      this.bbt_sqb[v_bn[0]][v[0]] = this.bbt_sqb[this.ib_bn][this.ib];
      for (int i=0; i<2*this.rdim; i++) {
        this.bbt_vecs[v_bn[0]][i][v[0]] = this.bbt_vecs[this.ib_bn][i][this.ib];
      }
    }
    else {
      double sum = 0;
      int sbt_nv = this.sbt_neigh_vert[s_bn][0][s];
      if (sbt_nv == INFINITY) {
        for (int i=0; i<this.dim; i++) {
          this.bbt_vecs[v_bn[0]][i+this.rdim][v[0]] = this.bbt_vecs[v_bn[0]][i][v[0]]
            = this.site_blocks[i][rp];
        }
      }
      else {
        for (int i=0; i<this.dim; i++) {
          this.bbt_vecs[v_bn[0]][i+this.rdim][v[0]] = this.bbt_vecs[v_bn[0]][i][v[0]]
            = this.site_blocks[i][rp] - this.site_blocks[i][sbt_nv];
        }
      }
      for (int i=0; i<this.dim; i++) {
        sum += this.bbt_vecs[v_bn[0]][i][v[0]] * this.bbt_vecs[v_bn[0]][i][v[0]];
      }
      this.bbt_vecs[v_bn[0]][2*this.rdim-1][v[0]] = sum;
      this.bbt_vecs[v_bn[0]][this.rdim-1][v[0]] = sum;
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

    if (this.sbt_neigh_vert[s_bn][0][s] == INFINITY && this.cdim > 1) {
      int t_vert, t_simp, t_simp_bn, t_basis, t_basis_bn;
      t_vert = this.sbt_neigh_vert[s_bn][0][s];
      t_simp = this.sbt_neigh_simp[s_bn][0][s];
      t_simp_bn = this.sbt_neigh_simp_bn[s_bn][0][s];
      t_basis = this.sbt_neigh_basis[s_bn][0][s];
      t_basis_bn = this.sbt_neigh_basis_bn[s_bn][0][s];
      this.sbt_neigh_vert[s_bn][0][s] = this.sbt_neigh_vert[s_bn][k][s];
      this.sbt_neigh_simp[s_bn][0][s] = this.sbt_neigh_simp[s_bn][k][s];
      this.sbt_neigh_simp_bn[s_bn][0][s] = this.sbt_neigh_simp_bn[s_bn][k][s];
      this.sbt_neigh_basis[s_bn][0][s] = this.sbt_neigh_basis[s_bn][k][s];
      this.sbt_neigh_basis_bn[s_bn][0][s] = this.sbt_neigh_basis_bn[s_bn][k][s];
      this.sbt_neigh_vert[s_bn][k][s] = t_vert;
      this.sbt_neigh_simp[s_bn][k][s] = t_simp;
      this.sbt_neigh_simp_bn[s_bn][k][s] = t_simp_bn;
      this.sbt_neigh_basis[s_bn][k][s] = t_basis;
      this.sbt_neigh_basis_bn[s_bn][k][s] = t_basis_bn;

      q = this.sbt_neigh_basis[s_bn][0][s];
      q_bn = this.sbt_neigh_basis_bn[s_bn][0][s];
      if ((q != NOVAL) && --this.bbt_ref_count[q_bn][q] == 0) {
        this.bbt_next[q_bn][q] = this.basis_s_list;
        this.bbt_next_bn[q_bn][q] = this.basis_s_list_bn;
        this.bbt_ref_count[q_bn][q] = 0;
        this.bbt_lscale[q_bn][q] = 0;
        this.bbt_sqa[q_bn][q] = 0;
        this.bbt_sqb[q_bn][q] = 0;
        for (int j=0; j<2*this.rdim; j++) this.bbt_vecs[q_bn][j][q] = 0;
        this.basis_s_list = q;
        this.basis_s_list_bn = q_bn;
      }

      this.sbt_neigh_basis[s_bn][0][s] = this.ttbp;
      this.sbt_neigh_basis_bn[s_bn][0][s] = this.ttbp_bn;
      this.bbt_ref_count[this.ttbp_bn][this.ttbp]++;
    }
    else {
      if (this.sbt_neigh_basis[s_bn][0][s] == NOVAL) {
        this.sbt_neigh_basis[s_bn][0][s] = this.ttbp;
        this.sbt_neigh_basis_bn[s_bn][0][s] = this.ttbp_bn;
        this.bbt_ref_count[this.ttbp_bn][this.ttbp]++;
      } else while (k < this.cdim && this.sbt_neigh_basis[s_bn][k][s] != NOVAL) k++;
    }
    while (k < this.cdim) {
      q = this.sbt_neigh_basis[s_bn][k][s];
      q_bn = this.sbt_neigh_basis_bn[s_bn][k][s];
      if (q != NOVAL && --this.bbt_ref_count[q_bn][q] == 0) {
        this.bbt_next[q_bn][q] = this.basis_s_list;
        this.bbt_next_bn[q_bn][q] = this.basis_s_list_bn;
        this.bbt_ref_count[q_bn][q] = 0;
        this.bbt_lscale[q_bn][q] = 0;
        this.bbt_sqa[q_bn][q] = 0;
        this.bbt_sqb[q_bn][q] = 0;
        for (int j=0; j<2*this.rdim; j++) this.bbt_vecs[q_bn][j][q] = 0;
        this.basis_s_list = q;
        this.basis_s_list_bn = q_bn;
      }
      this.sbt_neigh_basis[s_bn][k][s] = NOVAL;
      curt[0] = this.sbt_neigh_basis[s_bn][k][s];
      curt_bn[0] = this.sbt_neigh_basis_bn[s_bn][k][s];
      reduce(curt, curt_bn, this.sbt_neigh_vert[s_bn][k][s], s, s_bn, k);
      this.sbt_neigh_basis[s_bn][k][s] = curt[0];
      this.sbt_neigh_basis_bn[s_bn][k][s] = curt_bn[0];
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

    if (this.b == NOVAL) {
      this.b = (this.basis_s_list != NOVAL) ? this.basis_s_list : new_block_basis_s();
      this.b_bn = this.basis_s_list_bn;
      this.basis_s_list = this.bbt_next[this.b_bn][this.b];
      this.basis_s_list_bn = this.bbt_next_bn[this.b_bn][this.b];
    }
    else this.bbt_lscale[this.b_bn][this.b] = 0;
    if (this.cdim==0) return 0;
    if (this.sbt_normal[s_bn][s] == NOVAL) {
      get_basis_sede(s, s_bn);
      if (this.rdim==3 && this.cdim==3) {
        this.sbt_normal[s_bn][s] = this.basis_s_list != NOVAL ? this.basis_s_list
                                                    : new_block_basis_s();
        this.sbt_normal_bn[s_bn][s] = this.basis_s_list_bn;
        q = this.sbt_normal[s_bn][s];
        q_bn = this.sbt_normal_bn[s_bn][s];
        this.basis_s_list = this.bbt_next[q_bn][q];
        this.basis_s_list_bn = this.bbt_next_bn[q_bn][q];
        q1 = this.sbt_neigh_basis[s_bn][1][s];
        q1_bn = this.sbt_neigh_basis_bn[s_bn][1][s];
        q2 = this.sbt_neigh_basis[s_bn][2][s];
        q2_bn = this.sbt_neigh_basis_bn[s_bn][2][s];
        this.bbt_ref_count[q_bn][q] = 1;
        this.bbt_vecs[q_bn][0][q] = this.bbt_vecs[q1_bn][1][q1]
                  *this.bbt_vecs[q2_bn][2][q2]
             - this.bbt_vecs[q1_bn][2][q1]
                  *this.bbt_vecs[q2_bn][1][q2];
        this.bbt_vecs[q_bn][1][q] = this.bbt_vecs[q1_bn][2][q1]
                  *this.bbt_vecs[q2_bn][0][q2]
             - this.bbt_vecs[q1_bn][0][q1]
                  *this.bbt_vecs[q2_bn][2][q2];
        this.bbt_vecs[q_bn][2][q] = this.bbt_vecs[q1_bn][0][q1]
                  *this.bbt_vecs[q2_bn][1][q2]
             - this.bbt_vecs[q1_bn][1][q1]
                  *this.bbt_vecs[q2_bn][0][q2];
        this.bbt_sqb[q_bn][q] = 0;
        for (int i=0; i<this.rdim; i++) this.bbt_sqb[q_bn][q] += this.bbt_vecs[q_bn][i][q]
                                                     * this.bbt_vecs[q_bn][i][q];
        for (int i=this.cdim+1; i>0; i--) {
          int m = (i > 1) ? this.sbt_neigh_vert[this.ch_root_bn][i-2][this.ch_root]
                          : INFINITY;
          int j;
          for (j=0; j<this.cdim && m != this.sbt_neigh_vert[s_bn][j][s]; j++);
          if (j < this.cdim) continue;
          if (m == INFINITY) {
            if (this.bbt_vecs[q_bn][2][q] > -this.b_err_min) continue;
          }
          else {
            if (sees(m, s, s_bn) == 0) {
              continue;
            }
          }
          this.bbt_vecs[q_bn][0][q] = -this.bbt_vecs[q_bn][0][q];
          this.bbt_vecs[q_bn][1][q] = -this.bbt_vecs[q_bn][1][q];
          this.bbt_vecs[q_bn][2][q] = -this.bbt_vecs[q_bn][2][q];
          break;
        }
      }
      else {
        for (int i=this.cdim+1; i>0; i--) {
          int m = (i > 1) ? this.sbt_neigh_vert[this.ch_root_bn][i-2][this.ch_root]
                          : INFINITY;
          int j;
          for (j=0; j<this.cdim && m != this.sbt_neigh_vert[s_bn][j][s]; j++);
          if (j < this.cdim) continue;
          curt[0] = this.sbt_normal[s_bn][s];
          curt_bn[0] = this.sbt_normal_bn[s_bn][s];
          reduce(curt, curt_bn, m, s, s_bn, this.cdim);
          q = this.sbt_normal[s_bn][s] = curt[0];
          q_bn = this.sbt_normal_bn[s_bn][s] = curt_bn[0];
          if (this.bbt_sqb[q_bn][q] != 0) break;
        }
      }

      for (int i=0; i<this.cdim; i++) {
        q = this.sbt_neigh_basis[s_bn][i][s];
        q_bn = this.sbt_neigh_basis_bn[s_bn][i][s];
        if (q != NOVAL && --this.bbt_ref_count[q_bn][q] == 0) {
          this.bbt_next[q_bn][q] = this.basis_s_list;
          this.bbt_next_bn[q_bn][q] = this.basis_s_list_bn;
          this.bbt_ref_count[q_bn][q] = 0;
          this.bbt_lscale[q_bn][q] = 0;
          this.bbt_sqa[q_bn][q] = 0;
          this.bbt_sqb[q_bn][q] = 0;
          for (int l=0; l<2*this.rdim; l++) this.bbt_vecs[q_bn][l][q] = 0;
          this.basis_s_list = q;
          this.basis_s_list_bn = q_bn;
        }
        this.sbt_neigh_basis[s_bn][i][s] = NOVAL;
      }
    }
    if (rp == INFINITY) {
      this.bbt_next[this.b_bn][this.b] = this.bbt_next[this.ib_bn][this.ib];
      this.bbt_next_bn[this.b_bn][this.b] = this.bbt_next_bn[this.ib_bn][this.ib];
      this.bbt_ref_count[this.b_bn][this.b] = this.bbt_ref_count[this.ib_bn][this.ib];
      this.bbt_lscale[this.b_bn][this.b] = this.bbt_lscale[this.ib_bn][this.ib];
      this.bbt_sqa[this.b_bn][this.b] = this.bbt_sqa[this.ib_bn][this.ib];
      this.bbt_sqb[this.b_bn][this.b] = this.bbt_sqb[this.ib_bn][this.ib];
      for (int i=0; i<2*this.rdim; i++) {
        this.bbt_vecs[this.b_bn][i][this.b] = this.bbt_vecs[this.ib_bn][i][this.ib];
      }
    }
    else {
      double sum = 0;
      int sbt_nv = this.sbt_neigh_vert[s_bn][0][s];
      if (sbt_nv == INFINITY) {
        for (int l=0; l<this.dim; l++) {
          this.bbt_vecs[this.b_bn][l+this.rdim][this.b] = this.bbt_vecs[this.b_bn][l][this.b]
            = this.site_blocks[l][rp];
        }
      }
      else {
        for (int l=0; l<this.dim; l++) {
          this.bbt_vecs[this.b_bn][l+this.rdim][this.b] = this.bbt_vecs[this.b_bn][l][this.b]
            = this.site_blocks[l][rp] - this.site_blocks[l][sbt_nv];
        }
      }
      for (int l=0; l<this.dim; l++) {
        sum += this.bbt_vecs[this.b_bn][l][this.b] * this.bbt_vecs[this.b_bn][l][this.b];
      }
      this.bbt_vecs[this.b_bn][2*this.rdim-1][this.b] = this.bbt_vecs[this.b_bn][this.rdim-1][this.b] = sum;
    }
    q = this.sbt_normal[s_bn][s];
    q_bn = this.sbt_normal_bn[s_bn][s];
    for (int i=0; i<3; i++) {
      double sum = 0;
      dd = 0;
      for (int l=0; l<this.rdim; l++) {
        dd += this.bbt_vecs[this.b_bn][l][this.b] * this.bbt_vecs[q_bn][l][q];
      }
      if (dd == 0.0) return 0;
      for (int l=0; l<this.rdim; l++) {
        sum += this.bbt_vecs[this.b_bn][l][this.b] * this.bbt_vecs[this.b_bn][l][this.b];
      }
      dds = dd*dd/this.bbt_sqb[q_bn][q]/sum;
      if (dds > this.b_err_min_sq) return (dd < 0 ? 1 : 0);
      get_basis_sede(s, s_bn);
      reduce_inner(this.b, this.b_bn, s, s_bn, this.cdim);
    }
    return 0;
  }

  /**
   * Create a new block of simplex.
   * @return a new block of simplex
   */
  private int new_block_simplex() {
    this.sbt_next[this.nsb] = new int[Nobj];
    this.sbt_next_bn[this.nsb] = new int[Nobj];
    this.sbt_visit[this.nsb] = new long[Nobj];
    this.sbt_mark[this.nsb] = new short[Nobj];
    this.sbt_normal[this.nsb] = new int[Nobj];
    this.sbt_normal_bn[this.nsb] = new int[Nobj];
    this.sbt_peak_vert[this.nsb] = new int[Nobj];
    this.sbt_peak_simp[this.nsb] = new int[Nobj];
    this.sbt_peak_simp_bn[this.nsb] = new int[Nobj];
    this.sbt_peak_basis[this.nsb] = new int[Nobj];
    this.sbt_peak_basis_bn[this.nsb] = new int[Nobj];
    this.sbt_neigh_vert[this.nsb] = new int[this.rdim][];
    this.sbt_neigh_simp[this.nsb] = new int[this.rdim][];
    this.sbt_neigh_simp_bn[this.nsb] = new int[this.rdim][];
    this.sbt_neigh_basis[this.nsb] = new int[this.rdim][];
    this.sbt_neigh_basis_bn[this.nsb] = new int[this.rdim][];
    for (int i=0; i<this.rdim; i++) {
      this.sbt_neigh_vert[this.nsb][i] = new int[Nobj];
      this.sbt_neigh_simp[this.nsb][i] = new int[Nobj];
      this.sbt_neigh_simp_bn[this.nsb][i] = new int[Nobj];
      this.sbt_neigh_basis[this.nsb][i] = new int[Nobj];
      this.sbt_neigh_basis_bn[this.nsb][i] = new int[Nobj];
    }
    for (int i=0; i<Nobj; i++) {
      this.sbt_next[this.nsb][i] = i+1;
      this.sbt_next_bn[this.nsb][i] = this.nsb;
      this.sbt_visit[this.nsb][i] = 0;
      this.sbt_mark[this.nsb][i] = 0;
      this.sbt_normal[this.nsb][i] = NOVAL;
      this.sbt_peak_vert[this.nsb][i] = NOVAL;
      this.sbt_peak_simp[this.nsb][i] = NOVAL;
      this.sbt_peak_basis[this.nsb][i] = NOVAL;
      for (int j=0; j<this.rdim; j++) {
        this.sbt_neigh_vert[this.nsb][j][i] = NOVAL;
        this.sbt_neigh_simp[this.nsb][j][i] = NOVAL;
        this.sbt_neigh_basis[this.nsb][j][i] = NOVAL;
      }
    }
    this.sbt_next[this.nsb][Nobj-1] = NOVAL;
    this.simplex_list = 0;
    this.simplex_list_bn = this.nsb;

    this.nsb++;
    return this.simplex_list;
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

    this.vnum--;
    if (s != NOVAL) {
      this.st2[tms] = s;
      this.st2_bn[tms] = s_bn;
      tms++;
    }
    while (tms != 0) {
      if (tms > this.ss2) {
        // JAVA: efficiency issue: how much is this stack hammered?
        this.ss2 += this.ss2;
        int[] newst2 = new int[this.ss2+MAXDIM+1];
        int[] newst2_bn = new int[this.ss2+MAXDIM+1];
        System.arraycopy(this.st2, 0, newst2, 0, this.st2.length);
        System.arraycopy(this.st2_bn, 0, newst2_bn, 0, this.st2_bn.length);
        this.st2 = newst2;
        this.st2_bn = newst2_bn;
      }
      tms--;
      t = this.st2[tms];
      t_bn = this.st2_bn[tms];
      if (t == NOVAL || this.sbt_visit[t_bn][t] == this.vnum) continue;
      this.sbt_visit[t_bn][t] = this.vnum;
      if (whichfunc == 1) {
        if (this.sbt_peak_vert[t_bn][t] == NOVAL) {
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
        int[] vfp = new int[this.cdim];

        if (t != NOVAL) {
          for (int j=0; j<this.cdim; j++) vfp[j] = this.sbt_neigh_vert[t_bn][j][t];
          for (int j=0; j<this.cdim; j++) {
            this.a3s[j][this.nts] = (vfp[j] == INFINITY) ? -1 : vfp[j];
          }
          this.nts++;
          if (this.nts > this.a3size) {
            // JAVA: efficiency issue, hammering an array
            this.a3size += this.a3size;
            int[][] newa3s = new int[this.rdim][this.a3size+MAXDIM+1];
            for (int i=0; i<this.rdim; i++) {
              System.arraycopy(this.a3s[i], 0, newa3s[i], 0, this.a3s[i].length);
            }
            this.a3s = newa3s;
          }
        }
      }
      for (int i=0; i<this.cdim; i++) {
        int j = this.sbt_neigh_simp[t_bn][i][t];
        int j_bn = this.sbt_neigh_simp_bn[t_bn][i][t];
        if ((j != NOVAL) && this.sbt_visit[j_bn][j] != this.vnum) {
          this.st2[tms] = j;
          this.st2_bn[tms] = j_bn;
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
    for (int i=0; (this.sbt_neigh_vert[s_bn][i][s] != this.p) && (i<this.cdim); i++);
    if (this.sbt_visit[s_bn][s] == this.pnum) return;
    this.sbt_visit[s_bn][s] = this.pnum;
    ccj = this.sbt_peak_simp[s_bn][s];
    ccj_bn = this.sbt_peak_simp_bn[s_bn][s];
    for (xfi=0; (this.sbt_neigh_simp[ccj_bn][xfi][ccj] != s
              || this.sbt_neigh_simp_bn[ccj_bn][xfi][ccj] != s_bn)
                     && (xfi<this.cdim); xfi++);
    for (int i=0; i<this.cdim; i++) {
      int l;
      if (this.p == this.sbt_neigh_vert[s_bn][i][s]) continue;
      sb = this.sbt_peak_simp[s_bn][s];
      sb_bn = this.sbt_peak_simp_bn[s_bn][s];
      sf = this.sbt_neigh_simp[s_bn][i][s];
      sf_bn = this.sbt_neigh_simp_bn[s_bn][i][s];
      xf = this.sbt_neigh_vert[ccj_bn][xfi][ccj];
      if (this.sbt_peak_vert[sf_bn][sf] == NOVAL) {  // are we done already?
        for (l=0; (this.sbt_neigh_vert[ccj_bn][l][ccj]
                != this.sbt_neigh_vert[s_bn][i][s]) && (l<this.cdim); l++);
        sf = this.sbt_neigh_simp[ccj_bn][l][ccj];
        sf_bn = this.sbt_neigh_simp_bn[ccj_bn][l][ccj];
        if (this.sbt_peak_vert[sf_bn][sf] != NOVAL) continue;
      } else do {
        xb = xf;
        for (l=0; (this.sbt_neigh_simp[sf_bn][l][sf] != sb
                || this.sbt_neigh_simp_bn[sf_bn][l][sf] != sb_bn)
                && l<this.cdim; l++);
        xf = this.sbt_neigh_vert[sf_bn][l][sf];
        sb = sf;
        sb_bn = sf_bn;
        for (l=0; (this.sbt_neigh_vert[sb_bn][l][sb] != xb) && (l<this.cdim); l++);
        tf = this.sbt_neigh_simp[sf_bn][l][sf];
        tf_bn = this.sbt_neigh_simp_bn[sf_bn][l][sf];
        sf = tf;
        sf_bn = tf_bn;
      } while (this.sbt_peak_vert[sf_bn][sf] != NOVAL);

      this.sbt_neigh_simp[s_bn][i][s] = sf;
      this.sbt_neigh_simp_bn[s_bn][i][s] = sf_bn;
      for (l=0; (this.sbt_neigh_vert[sf_bn][l][sf] != xf) && (l<this.cdim); l++);
      this.sbt_neigh_simp[sf_bn][l][sf] = s;
      this.sbt_neigh_simp_bn[sf_bn][l][sf] = s_bn;

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
    this.sbt_peak_vert[seen_bn][seen] = this.p;

    for (int i=0; i<this.cdim; i++) {
      n = this.sbt_neigh_simp[seen_bn][i][seen];
      n_bn = this.sbt_neigh_simp_bn[seen_bn][i][seen];

      if (this.pnum != this.sbt_visit[n_bn][n]) {
        this.sbt_visit[n_bn][n] = this.pnum;
        if (sees(this.p, n, n_bn) != 0) make_facets(n, n_bn, this.voidp, this.voidp_bn);
      }
      if (this.sbt_peak_vert[n_bn][n] != NOVAL) continue;

      this.ns = (this.simplex_list != NOVAL) ? this.simplex_list : new_block_simplex();
      this.ns_bn = this.simplex_list_bn;
      this.simplex_list = this.sbt_next[this.ns_bn][this.ns];
      this.simplex_list_bn = this.sbt_next_bn[this.ns_bn][this.ns];
      this.sbt_next[this.ns_bn][this.ns] = this.sbt_next[seen_bn][seen];
      this.sbt_next_bn[this.ns_bn][this.ns] = this.sbt_next_bn[seen_bn][seen];
      this.sbt_visit[this.ns_bn][this.ns] = this.sbt_visit[seen_bn][seen];
      this.sbt_mark[this.ns_bn][this.ns] = this.sbt_mark[seen_bn][seen];
      this.sbt_normal[this.ns_bn][this.ns] = this.sbt_normal[seen_bn][seen];
      this.sbt_normal_bn[this.ns_bn][this.ns] = this.sbt_normal_bn[seen_bn][seen];
      this.sbt_peak_vert[this.ns_bn][this.ns] = this.sbt_peak_vert[seen_bn][seen];
      this.sbt_peak_simp[this.ns_bn][this.ns] = this.sbt_peak_simp[seen_bn][seen];
      this.sbt_peak_simp_bn[this.ns_bn][this.ns] = this.sbt_peak_simp_bn[seen_bn][seen];
      this.sbt_peak_basis[this.ns_bn][this.ns] = this.sbt_peak_basis[seen_bn][seen];
      this.sbt_peak_basis_bn[this.ns_bn][this.ns] = this.sbt_peak_basis_bn[seen_bn][seen];
      for (j=0; j<this.rdim; j++) {
        this.sbt_neigh_vert[this.ns_bn][j][this.ns] = this.sbt_neigh_vert[seen_bn][j][seen];
        this.sbt_neigh_simp[this.ns_bn][j][this.ns] = this.sbt_neigh_simp[seen_bn][j][seen];
        this.sbt_neigh_simp_bn[this.ns_bn][j][this.ns]
                       = this.sbt_neigh_simp_bn[seen_bn][j][seen];
        this.sbt_neigh_basis[this.ns_bn][j][this.ns] = this.sbt_neigh_basis[seen_bn][j][seen];
        this.sbt_neigh_basis_bn[this.ns_bn][j][this.ns]
                        = this.sbt_neigh_basis_bn[seen_bn][j][seen];
      }

      for (j=0; j<this.cdim; j++) {
        q = this.sbt_neigh_basis[seen_bn][j][seen];
        q_bn = this.sbt_neigh_basis_bn[seen_bn][j][seen];
        if (q != NOVAL) this.bbt_ref_count[q_bn][q]++;
      }

      this.sbt_visit[this.ns_bn][this.ns] = 0;
      this.sbt_peak_vert[this.ns_bn][this.ns] = NOVAL;
      this.sbt_normal[this.ns_bn][this.ns] = NOVAL;
      this.sbt_peak_simp[this.ns_bn][this.ns] = seen;
      this.sbt_peak_simp_bn[this.ns_bn][this.ns] = seen_bn;

      q = this.sbt_neigh_basis[this.ns_bn][i][this.ns];
      q_bn = this.sbt_neigh_basis_bn[this.ns_bn][i][this.ns];
      if (q != NOVAL && --this.bbt_ref_count[q_bn][q] == 0) {
        this.bbt_next[q_bn][q] = this.basis_s_list;
        this.bbt_next_bn[q_bn][q] = this.basis_s_list_bn;
        this.bbt_ref_count[q_bn][q] = 0;
        this.bbt_lscale[q_bn][q] = 0;
        this.bbt_sqa[q_bn][q] = 0;
        this.bbt_sqb[q_bn][q] = 0;
        for (int l=0; l<2*this.rdim; l++) this.bbt_vecs[q_bn][l][q] = 0;
        this.basis_s_list = q;
        this.basis_s_list_bn = q_bn;
      }
      this.sbt_neigh_basis[this.ns_bn][i][this.ns] = NOVAL;

      this.sbt_neigh_vert[this.ns_bn][i][this.ns] = this.p;
      for (j=0; (this.sbt_neigh_simp[n_bn][j][n] != seen
                  || this.sbt_neigh_simp_bn[n_bn][j][n] != seen_bn)
                  && j<this.cdim; j++);
      this.sbt_neigh_simp[seen_bn][i][seen] = this.sbt_neigh_simp[n_bn][j][n] = this.ns;
      this.sbt_neigh_simp_bn[seen_bn][i][seen] = this.ns_bn;
      this.sbt_neigh_simp_bn[n_bn][j][n] = this.ns_bn;
    }
    ret[0] = this.ns;
    ret_bn[0] = this.ns_bn;
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

    if (this.sbt_visit[s_bn][s] == this.pnum) {
      if (this.sbt_peak_vert[s_bn][s] != NOVAL) {
        ret[0] = this.sbt_neigh_simp[s_bn][this.cdim-1][s];
        ret_bn[0] = this.sbt_neigh_simp_bn[s_bn][this.cdim-1][s];
      }
      else {
        ret[0] = s;
        ret_bn[0] = s_bn;
      }
      return;
    }
    this.sbt_visit[s_bn][s] = this.pnum;
    this.sbt_neigh_vert[s_bn][this.cdim-1][s] = this.p;
    q = this.sbt_normal[s_bn][s];
    q_bn = this.sbt_normal_bn[s_bn][s];
    if (q != NOVAL && --this.bbt_ref_count[q_bn][q] == 0) {
      this.bbt_next[q_bn][q] = this.basis_s_list;
      this.bbt_next_bn[q_bn][q] = this.basis_s_list_bn;
      this.bbt_ref_count[q_bn][q] = 0;
      this.bbt_lscale[q_bn][q] = 0;
      this.bbt_sqa[q_bn][q] = 0;
      this.bbt_sqb[q_bn][q] = 0;
      for (int j=0; j<2*this.rdim; j++) this.bbt_vecs[q_bn][j][q] = 0;
      this.basis_s_list = q;
      this.basis_s_list_bn = q_bn;
    }
    this.sbt_normal[s_bn][s] = NOVAL;

    q = this.sbt_neigh_basis[s_bn][0][s];
    q_bn = this.sbt_neigh_basis_bn[s_bn][0][s];
    if (q != NOVAL && --this.bbt_ref_count[q_bn][q] == 0) {
      this.bbt_next[q_bn][q] = this.basis_s_list;
      this.bbt_ref_count[q_bn][q] = 0;
      this.bbt_lscale[q_bn][q] = 0;
      this.bbt_sqa[q_bn][q] = 0;
      this.bbt_sqb[q_bn][q] = 0;
      for (int j=0; j<2*this.rdim; j++) this.bbt_vecs[q_bn][j][q] = 0;

      this.basis_s_list = q;
      this.basis_s_list_bn = q_bn;
    }
    this.sbt_neigh_basis[s_bn][0][s] = NOVAL;

    if (this.sbt_peak_vert[s_bn][s] == NOVAL) {
      int[] esretp = new int[1];
      int[] esretp_bn = new int[1];
      extend_simplices(this.sbt_peak_simp[s_bn][s],
                       this.sbt_peak_simp_bn[s_bn][s], esretp, esretp_bn);
      this.sbt_neigh_simp[s_bn][this.cdim-1][s] = esretp[0];
      this.sbt_neigh_simp_bn[s_bn][this.cdim-1][s] = esretp_bn[0];
      ret[0] = s;
      ret_bn[0] = s_bn;
      return;
    }
    else {
      ns = (this.simplex_list != NOVAL) ? this.simplex_list : new_block_simplex();
      ns_bn = this.simplex_list_bn;
      this.simplex_list = this.sbt_next[ns_bn][ns];
      this.simplex_list_bn = this.sbt_next_bn[ns_bn][ns];
      this.sbt_next[ns_bn][ns] = this.sbt_next[s_bn][s];
      this.sbt_next_bn[ns_bn][ns] = this.sbt_next_bn[s_bn][s];
      this.sbt_visit[ns_bn][ns] = this.sbt_visit[s_bn][s];
      this.sbt_mark[ns_bn][ns] = this.sbt_mark[s_bn][s];
      this.sbt_normal[ns_bn][ns] = this.sbt_normal[s_bn][s];
      this.sbt_normal_bn[ns_bn][ns] = this.sbt_normal_bn[s_bn][s];
      this.sbt_peak_vert[ns_bn][ns] = this.sbt_peak_vert[s_bn][s];
      this.sbt_peak_simp[ns_bn][ns] = this.sbt_peak_simp[s_bn][s];
      this.sbt_peak_simp_bn[ns_bn][ns] = this.sbt_peak_simp_bn[s_bn][s];
      this.sbt_peak_basis[ns_bn][ns] = this.sbt_peak_basis[s_bn][s];
      this.sbt_peak_basis_bn[ns_bn][ns] = this.sbt_peak_basis_bn[s_bn][s];
      for (int j=0; j<this.rdim; j++) {
        this.sbt_neigh_vert[ns_bn][j][ns] = this.sbt_neigh_vert[s_bn][j][s];
        this.sbt_neigh_simp[ns_bn][j][ns] = this.sbt_neigh_simp[s_bn][j][s];
        this.sbt_neigh_simp_bn[ns_bn][j][ns] = this.sbt_neigh_simp_bn[s_bn][j][s];
        this.sbt_neigh_basis[ns_bn][j][ns] = this.sbt_neigh_basis[s_bn][j][s];
        this.sbt_neigh_basis_bn[ns_bn][j][ns] = this.sbt_neigh_basis_bn[s_bn][j][s];
      }

      for (int j=0; j<this.cdim; j++) {
        q = this.sbt_neigh_basis[s_bn][j][s];
        q_bn = this.sbt_neigh_basis_bn[s_bn][j][s];
        if (q != NOVAL) this.bbt_ref_count[q_bn][q]++;
      }

      this.sbt_neigh_simp[s_bn][this.cdim-1][s] = ns;
      this.sbt_neigh_simp_bn[s_bn][this.cdim-1][s] = ns_bn;
      this.sbt_peak_vert[ns_bn][ns] = NOVAL;
      this.sbt_peak_simp[ns_bn][ns] = s;
      this.sbt_peak_simp_bn[ns_bn][ns] = s_bn;
      this.sbt_neigh_vert[ns_bn][this.cdim-1][ns] = this.sbt_peak_vert[s_bn][s];
      this.sbt_neigh_simp[ns_bn][this.cdim-1][ns] = this.sbt_peak_simp[s_bn][s];
      this.sbt_neigh_simp_bn[ns_bn][this.cdim-1][ns] = this.sbt_peak_simp_bn[s_bn][s];
      this.sbt_neigh_basis[ns_bn][this.cdim-1][ns] = this.sbt_peak_basis[s_bn][s];
      this.sbt_neigh_basis_bn[ns_bn][this.cdim-1][ns] = this.sbt_peak_basis_bn[s_bn][s];
      q = this.sbt_peak_basis[s_bn][s];
      q_bn = this.sbt_peak_basis_bn[s_bn][s];
      if (q != NOVAL) this.bbt_ref_count[q_bn][q]++;
      for (int i=0; i<this.cdim; i++) {
        int[] esretp = new int[1];
        int[] esretp_bn = new int[1];
        extend_simplices(this.sbt_neigh_simp[ns_bn][i][ns],
                         this.sbt_neigh_simp_bn[ns_bn][i][ns], esretp, esretp_bn);
        this.sbt_neigh_simp[ns_bn][i][ns] = esretp[0];
        this.sbt_neigh_simp_bn[ns_bn][i][ns] = esretp_bn[0];
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

    this.st[tms] = this.sbt_peak_simp[root_bn][root];
    this.st_bn[tms] = this.sbt_peak_simp_bn[root_bn][root];
    tms++;
    this.sbt_visit[root_bn][root] = this.pnum;
    if (sees(this.p, root, root_bn) == 0) {
      for (int i=0; i<this.cdim; i++) {
        this.st[tms] = this.sbt_neigh_simp[root_bn][i][root];
        this.st_bn[tms] = this.sbt_neigh_simp_bn[root_bn][i][root];
        tms++;
      }
    }
    while (tms != 0) {
      if (tms > this.ss) {
        // JAVA: efficiency issue: how much is this stack hammered?
        this.ss += this.ss;
        int[] newst = new int[this.ss+MAXDIM+1];
        int[] newst_bn = new int[this.ss+MAXDIM+1];
        System.arraycopy(this.st, 0, newst, 0, this.st.length);
        System.arraycopy(this.st_bn, 0, newst_bn, 0, this.st_bn.length);
        this.st = newst;
        this.st_bn = newst_bn;
      }
      tms--;
      s = this.st[tms];
      s_bn = this.st_bn[tms];
      if (this.sbt_visit[s_bn][s] == this.pnum) continue;
      this.sbt_visit[s_bn][s] = this.pnum;
      if (sees(this.p, s, s_bn) == 0) continue;
      if (this.sbt_peak_vert[s_bn][s] == NOVAL) {
        ret[0] = s;
        ret_bn[0] = s_bn;
        return;
      }
      for (int i=0; i<this.cdim; i++) {
        this.st[tms] = this.sbt_neigh_simp[s_bn][i][s];
        this.st_bn[tms] = this.sbt_neigh_simp_bn[s_bn][i][s];
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
    this.simplexes = null;
    this.vertices = null;
    this.neighbors = null;
    this.Edges = null;
    this.NumEdges = 0;

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
    this.dim = samples.length;
    nrs = samples[0].length;
    for (int i=1; i<this.dim; i++) nrs = Math.min(nrs, samples[i].length);

    if (nrs <= this.dim) throw new DelaunayException("DelaunayClarkson: "
                                          +"not enough samples");
    if (this.dim > MAXDIM) throw new DelaunayException("DelaunayClarkson: "
                               +"dimension bound MAXDIM exceeded");

    // copy samples
    this.site_blocks = new double[this.dim][nrs];
    for (int j=0; j<this.dim; j++) {
      System.arraycopy(samples[j], 0, this.site_blocks[j], 0, nrs);
    }

    // WLH 29 Jan 98 - scale samples values to avoid integer convertion loss of precision
    for (int j=0; j<this.dim; j++) {
      for (int kk=0; kk<nrs; kk++) {
        this.site_blocks[j][kk] = scale * samples[j][kk];
      }
    }


    this.exact_bits = (int) (DBL_MANT_DIG*Math.log(FLT_RADIX)/ln2);
    this.b_err_min = DBL_EPSILON*MAXDIM*(1<<MAXDIM)*MAXDIM*3.01;
    this.b_err_min_sq = this.b_err_min * this.b_err_min;

    this.cdim = 0;
    this.rdim = this.dim+1;
    if (this.rdim > MAXDIM) throw new DelaunayException(
              "dimension bound MAXDIM exceeded; rdim="+this.rdim+"; dim="+this.dim);

    this.pnb = this.basis_s_list != NOVAL ? this.basis_s_list : new_block_basis_s();
    this.pnb_bn = this.basis_s_list_bn;
    this.basis_s_list = this.bbt_next[this.pnb_bn][this.pnb];
    this.basis_s_list_bn = this.bbt_next_bn[this.pnb_bn][this.pnb];
    this.bbt_next[this.pnb_bn][this.pnb] = NOVAL;

    this.ttbp = this.basis_s_list != NOVAL ? this.basis_s_list : new_block_basis_s();
    this.ttbp_bn = this.basis_s_list_bn;
    this.basis_s_list = this.bbt_next[this.ttbp_bn][this.ttbp];
    this.basis_s_list_bn = this.bbt_next_bn[this.ttbp_bn][this.ttbp];
    this.bbt_next[this.ttbp_bn][this.ttbp] = NOVAL;
    this.bbt_ref_count[this.ttbp_bn][this.ttbp] = 1;
    this.bbt_lscale[this.ttbp_bn][this.ttbp] = -1;
    this.bbt_sqa[this.ttbp_bn][this.ttbp] = 0;
    this.bbt_sqb[this.ttbp_bn][this.ttbp] = 0;
    for (int j=0; j<2*this.rdim; j++) this.bbt_vecs[this.ttbp_bn][j][this.ttbp] = 0;

    root = NOVAL;
    this.p = INFINITY;
    this.ib = (this.basis_s_list != NOVAL) ? this.basis_s_list : new_block_basis_s();
    this.ib_bn = this.basis_s_list_bn;
    this.basis_s_list = this.bbt_next[this.ib_bn][this.ib];
    this.basis_s_list_bn = this.bbt_next_bn[this.ib_bn][this.ib];
    this.bbt_ref_count[this.ib_bn][this.ib] = 1;
    this.bbt_vecs[this.ib_bn][2*this.rdim-1][this.ib] = this.bbt_vecs[this.ib_bn][this.rdim-1][this.ib] = 1;
    this.bbt_sqa[this.ib_bn][this.ib] = this.bbt_sqb[this.ib_bn][this.ib] = 1;

    root = (this.simplex_list != NOVAL) ? this.simplex_list : new_block_simplex();
    root_bn = this.simplex_list_bn;
    this.simplex_list = this.sbt_next[root_bn][root];
    this.simplex_list_bn = this.sbt_next_bn[root_bn][root];

    this.ch_root = root;
    this.ch_root_bn = root_bn;

    s = (this.simplex_list != NOVAL) ? this.simplex_list : new_block_simplex();
    s_bn = this.simplex_list_bn;
    this.simplex_list = this.sbt_next[s_bn][s];
    this.simplex_list_bn = this.sbt_next_bn[s_bn][s];
    this.sbt_next[s_bn][s] = this.sbt_next[root_bn][root];
    this.sbt_next_bn[s_bn][s] = this.sbt_next_bn[root_bn][root];
    this.sbt_visit[s_bn][s] = this.sbt_visit[root_bn][root];
    this.sbt_mark[s_bn][s] = this.sbt_mark[root_bn][root];
    this.sbt_normal[s_bn][s] = this.sbt_normal[root_bn][root];
    this.sbt_normal_bn[s_bn][s] = this.sbt_normal_bn[root_bn][root];
    this.sbt_peak_vert[s_bn][s] = this.sbt_peak_vert[root_bn][root];
    this.sbt_peak_simp[s_bn][s] = this.sbt_peak_simp[root_bn][root];
    this.sbt_peak_simp_bn[s_bn][s] = this.sbt_peak_simp_bn[root_bn][root];
    this.sbt_peak_basis[s_bn][s] = this.sbt_peak_basis[root_bn][root];
    this.sbt_peak_basis_bn[s_bn][s] = this.sbt_peak_basis_bn[root_bn][root];
    for (int i=0; i<this.rdim; i++) {
      this.sbt_neigh_vert[s_bn][i][s] = this.sbt_neigh_vert[root_bn][i][root];
      this.sbt_neigh_simp[s_bn][i][s] = this.sbt_neigh_simp[root_bn][i][root];
      this.sbt_neigh_simp_bn[s_bn][i][s] = this.sbt_neigh_simp_bn[root_bn][i][root];
      this.sbt_neigh_basis[s_bn][i][s] = this.sbt_neigh_basis[root_bn][i][root];
      this.sbt_neigh_basis_bn[s_bn][i][s] = this.sbt_neigh_basis_bn[root_bn][i][root];
    }
    for (int i=0;i<this.cdim;i++) {
      q = this.sbt_neigh_basis[root_bn][i][root];
      q_bn = this.sbt_neigh_basis_bn[root_bn][i][root];
      if (q != NOVAL) this.bbt_ref_count[q_bn][q]++;
    }
    this.sbt_peak_vert[root_bn][root] = this.p;
    this.sbt_peak_simp[root_bn][root] = s;
    this.sbt_peak_simp_bn[root_bn][root] = s_bn;
    this.sbt_peak_simp[s_bn][s] = root;
    this.sbt_peak_simp_bn[s_bn][s] = root_bn;
    while (this.cdim < this.rdim) {
      int oof = 0;

      if (s_num == 0) this.p = 0;
      else this.p++;
      for (int i=0; i<this.dim; i++) {
        this.site_blocks[i][this.p] = Math.floor(this.site_blocks[i][this.p]+0.5);
      }
      s_num++;
      this.pnum = (s_num*this.dim-1)/this.dim + 2;

      this.cdim++;
      this.sbt_neigh_vert[root_bn][this.cdim-1][root] = this.sbt_peak_vert[root_bn][root];

      q = this.sbt_neigh_basis[root_bn][this.cdim-1][root];
      q_bn = this.sbt_neigh_basis_bn[root_bn][this.cdim-1][root];
      if (q != NOVAL && --this.bbt_ref_count[q_bn][q] == 0) {
        this.bbt_next[q_bn][q] = this.basis_s_list;
        this.bbt_next_bn[q_bn][q] = this.basis_s_list_bn;
        this.bbt_ref_count[q_bn][q] = 0;
        this.bbt_lscale[q_bn][q] = 0;
        this.bbt_sqa[q_bn][q] = 0;
        this.bbt_sqb[q_bn][q] = 0;
        for (int l=0; l<2*this.rdim; l++) this.bbt_vecs[q_bn][l][q] = 0;

        this.basis_s_list = q;
        this.basis_s_list_bn = q_bn;
      }
      this.sbt_neigh_basis[root_bn][this.cdim-1][root] = NOVAL;

      get_basis_sede(root, root_bn);
      if (this.sbt_neigh_vert[root_bn][0][root] == INFINITY) oof = 1;
      else {
        curt[0] = this.pnb;
        curt_bn[0] = this.pnb_bn;
        reduce(curt, curt_bn, this.p, root, root_bn, this.cdim);
        this.pnb = curt[0];
        this.pnb_bn = curt_bn[0];
        if (this.bbt_sqa[this.pnb_bn][this.pnb] != 0) oof = 1;
        else this.cdim--;
      }
      if (oof != 0) extend_simplices(root, root_bn, this.voidp, this.voidp_bn);
      else {
        search(root, root_bn, retp, retp_bn);
        make_facets(retp[0], retp_bn[0], ret2p, ret2p_bn);
        connect(ret2p[0], ret2p_bn[0]);
      }
    }

    for (int i=s_num; i<nrs; i++) {
      this.p++;
      s_num++;
      for (int j=0; j<this.dim; j++) {
        this.site_blocks[j][this.p] = Math.floor(this.site_blocks[j][this.p]+0.5);
      }
      this.pnum = (s_num*this.dim-1)/this.dim + 2;
      search(root, root_bn, retp, retp_bn);
      make_facets(retp[0], retp_bn[0], ret2p, ret2p_bn);
      connect(ret2p[0], ret2p_bn[0]);
    }

    this.a3size = this.rdim*nrs;
    this.a3s = new int[this.rdim][this.a3size+MAXDIM+1];
    visit_triang_gen(root, root_bn, 1, retp, retp_bn);
    visit_triang_gen(retp[0], retp_bn[0], 0, this.voidp, this.voidp_bn);

    // deallocate memory
    /* NOTE: If this deallocation is not performed, more points
       could theoretically be added to the triangulation later */
    this.site_blocks = null;
    this.st = null;
    this.st_bn = null;
    this.st2 = null;
    this.st2_bn = null;
    this.sbt_next = null;
    this.sbt_next_bn = null;
    this.sbt_visit = null;
    this.sbt_mark = null;
    this.sbt_normal = null;
    this.sbt_normal_bn = null;
    this.sbt_peak_vert = null;
    this.sbt_peak_simp = null;
    this.sbt_peak_simp_bn = null;
    this.sbt_peak_basis = null;
    this.sbt_peak_basis_bn = null;
    this.sbt_neigh_vert = null;
    this.sbt_neigh_simp = null;
    this.sbt_neigh_simp_bn = null;
    this.sbt_neigh_basis = null;
    this.sbt_neigh_basis_bn = null;
    this.bbt_next = null;
    this.bbt_next_bn = null;
    this.bbt_ref_count = null;
    this.bbt_lscale = null;
    this.bbt_sqa = null;
    this.bbt_sqb = null;
    this.bbt_vecs = null;

/* ********** END OF CONVERTED HULL CODE ********** */
/*          (but still inside constructor)          */

    // compute number of triangles or tetrahedra
    int[] nverts = new int[nrs];
    for (int i=0; i<nrs; i++) nverts[i] = 0;
    int ntris = 0;
    boolean positive;
    for (int i=0; i<this.nts; i++) {
      positive = true;
      for (int j=0; j<this.rdim; j++) {
        if (this.a3s[j][i] < 0) positive = false;
      }
      if (positive) {
        ntris++;
        for (int j=0; j<this.rdim; j++) nverts[this.a3s[j][i]]++;
      }
    }
    this.vertices = new int[nrs][];
    for (int i=0; i<nrs; i++) this.vertices[i] = new int[nverts[i]];
    for (int i=0; i<nrs; i++) nverts[i] = 0;

    // build Tri & Vertices components
    this.simplexes = new int[ntris][this.rdim];

    int itri = 0;
    for (int i=0; i<this.nts; i++) {
      positive = true;
      for (int j=0; j<this.rdim; j++) {
        if (this.a3s[j][i] < 0) positive = false;
      }
      if (positive) {
        for (int j=0; j<this.rdim; j++) {
          this.vertices[this.a3s[j][i]][nverts[this.a3s[j][i]]++] = itri;
          this.simplexes[itri][j] = this.a3s[j][i];
        }
        itri++;
      }
    }

    // Deallocate remaining helper information
    this.a3s = null;

    // call more generic method for constructing Walk and Edges arrays
    finish_triang(samples);
  }
  
  /**
   * Checks a triangulation in various ways to make sure it is constructed correctly; test returns false if there are any problems with the triangulation.  
   * This method is expensive, provided mainly for debugging purposes. 
   * @return <code>true</code> if all the test are passing and <code>false</code> otherwise.
   */
  public boolean valid() {

    int dim = this.samples.length;
    int dim1 = dim+1;
    int ntris = this.simplexes.length;
    int nrs = this.samples[0].length;
    for (int i=1; i<dim; i++) {
      nrs = Math.min(nrs, this.samples[i].length);
    }

    // verify triangulation dimension
    for (int i=0; i<ntris; i++) {
      if (this.simplexes[i].length < dim1){
        System.out.println("Bad triangulation dimension: "+this.simplexes[i].length+" (should be "+dim1+")");
        return false;
      }
    }

    // verify no illegal triangle vertices
    for (int i=0; i<ntris; i++) {
      for (int j=0; j<dim1; j++) {
        if (this.simplexes[i][j] < 0 || this.simplexes[i][j] >= nrs){
          System.out.println("Illegal vertice found: "+this.simplexes[i][j]+" for triangle "+i);
          return false;
        }
      }
    }

    // verify that all points are in at least one triangle
    int[] nverts = new int[nrs];
    for (int i=0; i<nrs; i++) nverts[i] = 0;
    for (int i=0; i<ntris; i++) {
      for (int j=0; j<dim1; j++) nverts[this.simplexes[i][j]]++;
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
            if (this.simplexes[i][k] == this.simplexes[j][l] && !m[l]) {
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
    if (this.neighbors != null){
      for (int i=0; i<ntris; i++) {
        for (int j=0; j<dim1; j++) {
          if (this.neighbors[i][j] != -1) {
            boolean found = false;
            for (int k=0; k<dim1; k++) {
              if (this.neighbors[this.neighbors[i][j]][k] == i) found = true;
            }
            if (!found) return false;

            // make sure two walk'ed triangles share dim vertices
            int sb = 0;
            for (int k=0; k<dim1; k++) {
              for (int l=0; l<dim1; l++) {
                if (this.simplexes[i][k] == this.simplexes[this.neighbors[i][j]][l]) sb++;
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
    int dim = this.samples.length;
    int dim1 = dim+1;
    if (this.simplexes[0].length != dim1) {
      throw new DelaunayException("Delaunay.improve: samples dimension " +
                             "does not match");
    }
    // only 2-D triangulations supported for now
    if (dim > 2) {
      throw new DelaunayException("Delaunay.improve: dimension " +
                                       "must be 2!");
    }
    int ntris = this.simplexes.length;
    int nrs = this.samples[0].length;
    for (int i=1; i<dim; i++) {
      nrs = Math.min(nrs, this.samples[i].length);
    }
    double[] samp0 = this.samples[0];
    double[] samp1 = this.samples[1];

    // go through entire triangulation pass times
    boolean eflipped = false;
    for (int p=0; p<pass; p++) {
      eflipped = false;

      // edge keeps track of which edges have been checked
      boolean[] edge = new boolean[this.NumEdges];
      for (int i=0; i<this.NumEdges; i++) edge[i] = true;

      // check every edge of every triangle
      for (int t=0; t<ntris; t++) {
        int[] trit = this.simplexes[t];
        int[] walkt = this.neighbors[t];
        int[] edgest = this.Edges[t];
        for (int e=0; e<2; e++) {
          int curedge = edgest[e];
          // only check the edge if it hasn't been checked yet
          if (edge[curedge]) {
            int t2 = walkt[e];

            // only check edge if it is not part of the outer hull
            if (t2 >= 0) {
              int[] trit2 = this.simplexes[t2];
              int[] walkt2 = this.neighbors[t2];
              int[] edgest2 = this.Edges[t2];

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
                  int val = (this.neighbors[w2][0] == t) ? 0
                          : (this.neighbors[w2][1] == t) ? 1 : 2;
                  this.neighbors[w2][val] = t2;
                }
                if (w4 >= 0) {
                  int val = (this.neighbors[w4][0] == t2) ? 0
                          : (this.neighbors[w4][1] == t2) ? 1 : 2;
                  this.neighbors[w4][val] = t;
                }

                // update Edges array
                edgest[0] = e1;
                edgest[1] = e4;
                // Edges[t][2] and Edges[t2][0] stay the same
                edgest2[1] = e3;
                edgest2[2] = e2;

                // update Vertices array
                int[] vertn1 = this.vertices[n1];
                int[] vertn2 = this.vertices[n2];
                int[] vertn3 = this.vertices[n3];
                int[] vertn4 = this.vertices[n4];
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
                this.vertices[n1] = tn1;
                this.vertices[n2] = tn2;
                this.vertices[n3] = tn3;
                this.vertices[n4] = tn4;
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
    int mdim = this.simplexes[0].length - 1;
    int mdim1 = mdim + 1;
    int dim = samples.length;

    int ntris = this.simplexes.length;
    int nrs = samples[0].length;
    for (int i=1; i<dim; i++) {
      nrs = Math.min(nrs, samples[i].length);
    }

    if (this.vertices == null) {
      // build Vertices component
      this.vertices = new int[nrs][];
      int[] nverts = new int[nrs];
      for (int i=0; i<ntris; i++) {
        for (int j=0; j<mdim1; j++) nverts[this.simplexes[i][j]]++;
      }
      for (int i=0; i<nrs; i++) {
        this.vertices[i] = new int[nverts[i]];
        nverts[i] = 0;
      }
      for (int i=0; i<ntris; i++) {
        for (int j=0; j<mdim1; j++) {
          this.vertices[this.simplexes[i][j]][nverts[this.simplexes[i][j]]++] = i;
        }
      }
    }

    if (this.neighbors == null && mdim <= 3) {
      // build Walk component
      this.neighbors = new int[ntris][mdim1];
      for (int i=0; i<ntris; i++) {
      WalkDim:
        for (int j=0; j<mdim1; j++) {
          int v1 = j;
          int v2 = (v1+1)%mdim1;
          this.neighbors[i][j] = -1;
          for (int k=0; k<this.vertices[this.simplexes[i][v1]].length; k++) {
            int temp = this.vertices[this.simplexes[i][v1]][k];
            if (temp != i) {
              for (int l=0; l<this.vertices[this.simplexes[i][v2]].length; l++) {
                if (mdim == 2) {
                  if (temp == this.vertices[this.simplexes[i][v2]][l]) {
                    this.neighbors[i][j] = temp;
                    continue WalkDim;
                  }
                }
                else {    // mdim == 3
                  int temp2 = this.vertices[this.simplexes[i][v2]][l];
                  int v3 = (v2+1)%mdim1;
                  if (temp == temp2) {
                    for (int m=0; m<this.vertices[this.simplexes[i][v3]].length; m++) {
                      if (temp == this.vertices[this.simplexes[i][v3]][m]) {
                        this.neighbors[i][j] = temp;
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

    if (this.Edges == null && mdim <= 3) {
      // build Edges component

      // initialize all edges to "not yet found"
      int edim = 3*(mdim-1);
      this.Edges = new int[ntris][edim];
      for (int i=0; i<ntris; i++) {
        for (int j=0; j<edim; j++) this.Edges[i][j] = -1;
      }

      // calculate global edge values
      this.NumEdges = 0;
      if (mdim == 2) {
        for (int i=0; i<ntris; i++) {
          for (int j=0; j<3; j++) {
            if (this.Edges[i][j] < 0) {
              // this edge doesn't have a "global edge number" yet
              int othtri = this.neighbors[i][j];
              if (othtri >= 0) {
                int cside = -1;
                for (int k=0; k<3; k++) {
                  if (this.neighbors[othtri][k] == i) cside = k;
                }
                if (cside != -1) {
                  this.Edges[othtri][cside] = this.NumEdges;
                }
                else {
                  throw new DelaunayException("Delaunay.finish_triang: " +
                                         "error in triangulation!");
                }
              }
              this.Edges[i][j] = this.NumEdges++;
            }
          }
        }
      }
      else {    // mdim == 3
        int[] ptlook1 = {0, 0, 0, 1, 1, 2};
        int[] ptlook2 = {1, 2, 3, 2, 3, 3};
        for (int i=0; i<ntris; i++) {
          for (int j=0; j<6; j++) {
            if (this.Edges[i][j] < 0) {
              // this edge doesn't have a "global edge number" yet

              // search through the edge's two end points
              int endpt1 = this.simplexes[i][ptlook1[j]];
              int endpt2 = this.simplexes[i][ptlook2[j]];

              // create an intersection of two sets
              int[] set = new int[this.vertices[endpt1].length];
              int setlen = 0;
              for (int p1=0; p1<this.vertices[endpt1].length; p1++) {
                int temp = this.vertices[endpt1][p1];
                for (int p2=0; p2<this.vertices[endpt2].length; p2++) {
                  if (temp == this.vertices[endpt2][p2]) {
                    set[setlen++] = temp;
                    break;
                  }
                }
              }

              // assign global edge number to all members of set
              for (int kk=0; kk<setlen; kk++) {
                int k = set[kk];
                for (int l=0; l<edim; l++) {
                  if ((this.simplexes[k][ptlook1[l]] == endpt1
                    && this.simplexes[k][ptlook2[l]] == endpt2)
                   || (this.simplexes[k][ptlook1[l]] == endpt2
                    && this.simplexes[k][ptlook2[l]] == endpt1)) {
                    this.Edges[k][l] = this.NumEdges;
                  }
                }
              }
              this.Edges[i][j] = this.NumEdges++;
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

    s.append("\nTri (triangles -> vertices) " + this.simplexes.length + "\n");
    for (int i=0; i<this.simplexes.length; i++) {
      s.append("  " + i + " -> ");
      for (int j=0; j<this.simplexes[i].length; j++) {
        s.append(" " + this.simplexes[i][j]);
      }
      s.append("\n");
    }

    s.append("\nVertices (vertices -> triangles) " + this.vertices.length + "\n");
    for (int i=0; i<this.vertices.length; i++) {
      s.append("  " + i + " -> ");
      for (int j=0; j<this.vertices[i].length; j++) {
        s.append(" " + this.vertices[i][j]);
      }
      s.append("\n");
    }

    if (this.neighbors != null){
      s.append("\nWalk (triangles -> triangles) " + this.neighbors.length + "\n");
      for (int i=0; i<this.neighbors.length; i++) {
        s.append("  " + i + " -> ");
        for (int j=0; j<this.neighbors[i].length; j++) {
          s.append(" " + this.neighbors[i][j]);
        }
        s.append("\n");
      }
    }

    if (this.Edges != null){
      s.append("\nEdges (triangles -> global edges) " + this.Edges.length + "\n");
      for (int i=0; i<this.Edges.length; i++) {
        s.append("  " + i + " -> ");
        for (int j=0; j<this.Edges[i].length; j++) {
          s.append(" " + this.Edges[i][j]);
        }
        s.append("\n");
      }
    }
    return s.toString();
  }
  
}