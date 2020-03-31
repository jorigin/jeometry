package org.jeometry.math;

import org.jeometry.Geometry;

/**
 * A set of matrix data.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 *
 */
public class MatrixTestData {

	/**
	 * a 3 component vector.
	 */
	public static final double[] V_3_A = new double[] {-1.36528596, 0.2637087, -0.1998471};

	/**
	 * a 4 component vector.
	 */
	public static final double[] V_4_A = new double[] {-1.36528596, 0.2637087, -0.1998471, 3.45692302};

	/**
	 * a 4 component vector.
	 */
	public static final double[] V_PROD_M_4x4_A_X_V_4_A = new double[] {-1592.1687401217387, 48.8202233506501, -135.49691805832344, -1402.2024992659703};

	/**
	 * A 4 component linear matrix
	 */
	public static final double[][] M_4L_A = new double[][] {{2.36987452, 1.036987452, -3.98542015, 0.006984548}};

	/**
	 * A 4 component column matrix
	 */
	public static final double[][] M_4C_A = new double[][] {{ 2.36987452}, 
		{ 1.036987452}, 
		{-3.98542015}, 
		{ 0.006984548}};

		/**
		 * The result of the product {@link #M_4L_A} with {@link #V_4_A}
		 */
		public static final double[] V_PROD_M_4L_A_V_4_A = new double[] {-2.14147409220994684};

		/**
		 * A 3x3 matrix.
		 */
		public static final double[][] M_3x3_A = new double[][]{
			{-0.3489054, -0.7051164, -0.3809763}, 
			{-0.8560400, -0.6291361, -0.7050437},
			{-0.1990242,  0.5199935,  0.8552008}
		};

		/**
		 * The inverse of {@link #M_3x3_A}
		 */
		public static final double[][] M_3x3_A_INV = new double[][]{
			{ 0.5070835964176541, -1.1977844683656990, -0.76157969102133000},
		    {-2.5807044250194540,  1.1069599186495120, -0.23705790083590658},
		    { 1.6871726891994256, -0.9518233121942631,  1.13621944555775520},
		};
		
		/**
		 * The cofactor matrix of {@link #M_3x3_A}
		 */
		public static final double[][] M_3x3_A_COFACTOR = new double[][]{
			{-0.17141955481292992,  0.87240685118954000, -0.5703485447336201},
            { 0.40491090971907000, -0.37420768053077996,  0.3217637675317800},
			{ 0.25745193201225003,  0.08013739768601996, -0.3840988604310600}
		};
		
		/**
		 * The {@link MatrixTestData#M_3x3_A M_3x3_A} matrix determinant.
		 */
		public static final double M_3x3_A_DETERMINANT = -0.3380498916232778d;

		/**
		 * A 4x4 matrix.
		 */
		public static final double[][] M_4x4_A = new double[][] {
			{ 3.45692302, -1.36528596,    1.3685204, -459.0254136},
			{22.65974148,  0.00698741,    8.1269853,   23.5410397},
			{12.87456921, -3.12586921,   11.3685214,  -33.2154242},
			{36.25697942, -3.01127952, 6984.3652127,   12.6985412}
		};

		/**
		 * The inverse of {@link #M_4x4_A}
		 */
		public static final double[][] M_4x4_A_INV = new double[][] {
			{ 0.0023362906372642535,  0.0443514687366402000, -8.724924050199067E-4, -5.0644838364482117E-5},
	        { 0.0336722068448692500,  0.1844081481703914000, -0.334517281604580900, 3.23322649154155100E-4},
			{ 6.5004947736464510E-6, -1.5033815455939636E-4, -1.414927212295284E-4, 1.43580902831110600E-4},
			{-0.0022610663539576160, -2.1492488273757343E-4,  9.879670270111554E-4, -9.1500333970123050E-7},
		};
		
		/**
		 * The cofactor of {@link #M_4x4_A}
		 */
		public static final double[][] M_4x4_A_COFACTOR = new double[][] {
			{ -510389.604736797160, -7356081.50289591800000000,  -1420.107971669177, 493955.993433863740},
	        {-9689089.292630604000,       -4.028608442532788E7,  32843.101820705080, 46952.8166568281000},
	        {  190606.017346975920,        7.307915394281422E7,  30910.715006775317, -215832.77883597394},
	        {   11063.948390032145,   -70633.55751726116000000, -31366.902334349514, 199.893020771515400}

		};
			
		/**
		 * The {@link MatrixTestData#M_4x4_A M_4x4_A} matrix expressed within a single dimensional array in {@link Matrix#ROW_MAJOR}.
		 */
		public static final double[] M_4x4_A_ROWMAJOR = new double[] {
				3.45692302, -1.36528596,    1.3685204, -459.0254136,
				22.65974148,  0.00698741,    8.1269853,   23.5410397,
				12.87456921, -3.12586921,   11.3685214,  -33.2154242,
				36.25697942, -3.01127952, 6984.3652127,   12.6985412
		};

		/**
		 * The {@link MatrixTestData#M_4x4_A M_4x4_A} matrix expressed within a single dimensional array in {@link Matrix#COLUMN_MAJOR}
		 */
		public static final double[] M_4x4_A_COLUMNMAJOR = new double[] {
				3.45692302, 22.65974148, 12.87456921,  36.25697942, 
				-1.36528596,  0.00698741, -3.12586921,  -3.01127952,
				1.3685204,   8.1269853,  11.3685214, 6984.3652127,
				-459.0254136,  23.5410397, -33.2154242,   12.6985412
		};

		/**
		 * The {@link MatrixTestData#M_4x4_A M_4x4_A} matrix determinant.
		 */
		public static final double M_4x4_A_DETERMINANT = -2.1846152041017145E8;

		/**
		 * A 4x4 matrix.
		 */
		public static final double[][] M_4x4_B = new double[][] {
			{0.37635972705741605, -0.6562297094791463, -0.34595043189408814, 0.35760795101456533}, 
			{0.05485412293382774, -0.8277842762787191, -0.7645418241100518, 0.5903801510041287}, 
			{0.6378734400547261, 0.4881916032381257, -0.5994457753638186, -0.2462926076270161}, 
			{0.23311198402704603, 0.4246202954815326, 0.2418492075819768, 0.9927378463572852}, 
		};

		/**
		 * A 5x5 matrix.
		 */
		public static final double[][] M_5x5_A = new double[][] {
			{-0.1180710, -0.3937494,  0.6888403, -0.7191015, -0.0774862},
			{-0.3663894, -0.3909305, -0.2514673, -0.1998471,  0.4295696},
			{ 0.0091618, -0.4103808,  0.2637087,  0.3439714,  0.3653926},
			{-0.9802176,  0.7260585,  0.3678009,  0.7093690, -0.1036077},
			{ 0.4253439, -0.5693282,  0.7328173,  0.9992786, -0.4779186} 
		};

		/**
		 * The {@link MatrixTestData#M_5x5_A M_5x5_A} expressed within a single dimensional array in {@link Matrix#ROW_MAJOR}.
		 */
		public static final double[] M_5x5_A_ROWMAJOR = new double[] {
				-0.1180710, -0.3937494,  0.6888403, -0.7191015, -0.0774862,
				-0.3663894, -0.3909305, -0.2514673, -0.1998471,  0.4295696,
				0.0091618, -0.4103808,  0.2637087,  0.3439714,  0.3653926,
				-0.9802176,  0.7260585,  0.3678009,  0.7093690, -0.1036077,
				0.4253439, -0.5693282,  0.7328173,  0.9992786, -0.4779186 	
		};

		/**
		 * The {@link MatrixTestData#M_5x5_A M_5x5_A} matrix expressed within a single dimensional array in {@link Matrix#COLUMN_MAJOR}.
		 */
		public static final double[] M_5x5_A_COLUMNMAJOR = new double[] {
				-0.1180710, -0.3663894,  0.0091618, -0.9802176,  0.4253439,
				-0.3937494, -0.3909305, -0.4103808,  0.7260585, -0.5693282,
				0.6888403, -0.2514673,  0.2637087,  0.3678009,  0.7328173, 
				-0.7191015, -0.1998471,  0.3439714,  0.7093690,  0.9992786,
				-0.0774862,  0.4295696,  0.3653926, -0.1036077, -0.4779186 	
		};

		/**
		 * The {@link MatrixTestData#M_5x5_A 5x5 C matrix} determinant.
		 */
		public static final double M_5x5_A_DETERMINANT = -0.4944084244983374;

		/**
		 * A 4x3 matrix.
		 */
		public static final double[][] M_4x3_A = new double[][] {
			{-0.6814359, -0.9401949,  0.4056839}, 
			{-0.9660992, -0.9649611, -0.2315810},
			{-0.3560276, -0.5212195, -0.9616091},
			{-0.3269735,  0.8608128,  0.5911279}
		};

		/**
		 * The transpose of the {@link MatrixTestData#M_4x3_A} matrix.
		 */
		public static final double[][] M_4x3_A_TRANSPOSE = new double[][] {			
			{-0.6814359, -0.9660992, -0.3560276, -0.3269735}, 
			{-0.9401949, -0.9649611, -0.5212195,  0.8608128},
			{ 0.4056839, -0.2315810, -0.9616091,  0.5911279}

		};

		/**
		 * A 3x4 matrix.
		 */
		public static final double[][] M_3x4_A = new double[][] {
			{ 0.2242035,  0.9148579, -0.6261270,  0.0339424}, 
			{ 0.6411728,  0.6593071, -0.8716299,  0.9981717},
			{-0.3769121, -0.5572688,  0.2030611, -0.3207281} 
		};

		/**
		 * The matrix that represents the {@link MatrixTestData#M_4x3_A M_4x3_A}&times;{@link MatrixTestData#M_3x4_A M_3x4_A} product result.
		 */
		public static final double[][] M_4x4_PRODUCT_A = new double[][] {
			{ -0.9085148810695599,  -1.4693691695447202,   1.3285460214131,     -1.09171973800408 },
			{ -0.74802395133518,    -1.39099632378469,     1.39896464829619,    -0.9217140529808501 },
			{ -0.05157229498608995, -0.13348363026040988,  0.48196359215223994, -0.22393592601267986 },
			{  0.255817891987,      -0.06101143423428995, -0.42554815653352995,  0.6585493824073699 }
		};

		/**
		 * This matrix represent the {@link MatrixTestData#M_4x3_A M_4x3_A}&times;{@link MatrixTestData#M_4x4_A M_4x4_A} product result.
		 */
		public static final double[][] M_4x3_A_M_4x4_A_PRODUCT = new double[][] {
			{ -0.6814359, -0.9401949,  0.4056839 },
			{ -0.9660992, -0.9649611, -0.2315810 },
			{ -0.3560276, -0.5212195, -0.9616091 },
			{ -0.3269735,  0.8608128,  0.5911279 }
		};
		
		/**
		 * This matrix is dedicated to the testing of LU decomposition.
		 * @see #DECOMPOSITION_LU_L
		 * @see #DECOMPOSITION_LU_U
		 */
		public static final double[][] DECOMPOSITION_LU_INPUT = new double[][] {
			{ 1,  2,   3, 4},
			{-9,  8, -15, 4},
			{ 2, 13, -21, 7},
			{ 4, -5,   5, 3}
		};
		
		/**
		 * This matrix is the lower triangular matrix <i>L</i> resulting of the LU decomposition of the {@link #DECOMPOSITION_LU_INPUT input matrix}.
		 * @see #DECOMPOSITION_LU_INPUT
		 * @see #DECOMPOSITION_LU_U
		 */
		public static final double[][] DECOMPOSITION_LU_L = new double[][] {
			  { 1.0000,  0,         0,         0},
			  {-0.2222222222222222,  1.0000,    0,         0},
			  {-0.1111111111111111,  0.195488721804511127,    1.0000,    0},
			  {-0.4444444444444444, -0.097744360902255650,   -0.6641975308641974,    1.0000}
		};
			
		/**
		 * This matrix is the upper triangular matrix <i>U</i> resulting of the LU decomposition of the {@link #DECOMPOSITION_LU_INPUT input matrix}.
		 * @see #DECOMPOSITION_LU_INPUT
		 * @see #DECOMPOSITION_LU_L
		 */
		public static final double[][] DECOMPOSITION_LU_U = new double[][] {
			{-9,    8,      -15.0000, 4.0000},
	        { 0,   14.777777777777779, -24.333333333333332, 7.888888888888889},
	        { 0,    0,        6.090225563909774, 2.9022556390977443},
	        { 0,    0,        0,      7.476543209876543},
		};
		
		/**
		 * This matrix is the permutation matrix <i>P</i> resulting of the LU decomposition of the {@link #DECOMPOSITION_LU_INPUT input matrix}.
		 * @see #DECOMPOSITION_LU_INPUT
		 * @see #DECOMPOSITION_LU_L
		 * @see #DECOMPOSITION_LU_U
		 */
		public static final double[][] DECOMPOSITION_LU_P = new double[][] {
			{0,     1,     0,     0},
		    {0,     0,     1,     0},
		    {1,     0,     0,     0},
		    {0,     0,     0,     1}
		};
		
		/**
		 * This matrix is dedicated to the testing of Gauss Elimination based solvers. Matlab code: <br><br>
		 * <code>A = [9 3 4; 4 3 4; 1 1 1]</code>
		 * @see #SOLVER_GAUSS_ELIMINATION_B
		 * @see #SOLVER_GAUSS_ELIMINATION_X
		 */
		public static final double[][] SOLVER_GAUSS_ELIMINATION_A = new double[][] {
			{ 9.0d,  3.0d,  4.0d },
			{ 4.0d,  3.0d,  4.0d },
			{ 1.0d,  1.0d,  1.0d}};

		/**
		 * This vector is dedicated to the testing of Gauss Elimination based solvers. Matlab code: <br><br>
		 * <code>B = [7; 8; 3]</code>
		 * @see #SOLVER_GAUSS_ELIMINATION_A
		 * #see {@link #SOLVER_GAUSS_ELIMINATION_X}
		 */
		public static final double[] SOLVER_GAUSS_ELIMINATION_B = new double[] {7.0d, 8.0d, 3.0d};	
			
		/**
		 * This vector is dedicated to the testing of Gauss Elimination based solvers. Matlab code: <br><br>
		 * <code>B = [-1/5; 4; -4/5]</code>
		 * @see #SOLVER_GAUSS_ELIMINATION_A
		 * @see #SOLVER_GAUSS_ELIMINATION_B
		 */
		public static final double[] SOLVER_GAUSS_ELIMINATION_X = new double[] {-1.0d/5.0d, 4.0d, -4.0d/5.0d};	
		
        /**
         * This matrix is dedicated to the testing of Eigenvalue decomposition. 
         * Computing an eigenvalues decomposition from this matrix should provide {@link #DECOMPOSITION_EIGEN_V V} and  {@link #DECOMPOSITION_EIGEN_D D} matrices.
         * @see #DECOMPOSITION_EIGEN_V
         * @see #DECOMPOSITION_EIGEN_D
         */
		public static final double[][] DECOMPOSITION_EIGEN_INPUT = new double[][] {
                       {1.000000000000000,   0.500000000000000,   0.333333333333333,   0.250000000000000},
					   {0.500000000000000,   1.000000000000000,   0.666666666666667,   0.500000000000000},
					   {0.333333333333333,   0.666666666666667,   1.000000000000000,   0.750000000000000},
					   {0.250000000000000,   0.500000000000000,   0.750000000000000,   1.000000000000000}
		};
		
		/**
		 * This matrix is the <i>V</i> matrix computed from the Eigenvalues decomposition of the {@link #DECOMPOSITION_EIGEN_INPUT input matrix}.
		 * @see #DECOMPOSITION_EIGEN_INPUT
		 * @see #DECOMPOSITION_EIGEN_D
		 */
		public static final double[][] DECOMPOSITION_EIGEN_V = new double[][] {
			  { 0.069318526074277,  -0.442222850107573,  -0.810476380106626,   0.377838497343619},
			  {-0.361796329835911,   0.742039806455369,  -0.187714392599047,   0.532206396207443},
			  { 0.769367037085765,   0.048636017022092,   0.300968104554782,   0.561361826396131},
			  {-0.521893398986829,  -0.501448316705362,   0.466164717820999,   0.508790056532360}
        };
        
        /**
         * This matrix is the <i>D</i> matrix computed from the Eigenvalues decomposition of the {@link #DECOMPOSITION_EIGEN_INPUT input matrix}.
         * @see #DECOMPOSITION_EIGEN_INPUT
         * @see #DECOMPOSITION_EIGEN_V 
         */
        public static final double[][] DECOMPOSITION_EIGEN_D = new double[][] {
        	{0.207775485918012, 0                , 0                , 0},
            {0                , 0.407832884117875, 0                , 0},
            {0                , 0                , 0.848229155477913, 0},
            {0                , 0                , 0                , 2.536162474486201}
      };
}
