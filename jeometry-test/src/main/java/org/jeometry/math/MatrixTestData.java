package org.jeometry.math;

import org.jeometry.Jeometry;
import org.jeometry.math.decomposition.CholeskyDecomposition;
import org.jeometry.math.decomposition.LUDecomposition;
import org.jeometry.math.decomposition.QRDecomposition;

/**
 * A set of matrix data.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version}
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
	 * The 4 component vector {@link #V_4_A} square norm.
	 */
	public static final double V_4_A_NORM2 = V_4_A[0]* V_4_A[0] + V_4_A[1]* V_4_A[1] + V_4_A[2] * V_4_A[2] + V_4_A[3]* V_4_A[3];

	/**
	 * The 4 component vector {@link #V_4_A} norm.
	 */
	public static final double V_4_A_NORM = Math.sqrt(V_4_A_NORM2);

	/**
	 * a 4 component normalized vector.
	 */
	public static final double[] V_4_A_NORMALIZED = new double[] {V_4_A[0]/V_4_A_NORM, V_4_A[1]/V_4_A_NORM, V_4_A[2]/V_4_A_NORM, V_4_A[3]/V_4_A_NORM};

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
		 * This matrix is dedicated to the testing of {@link LUDecomposition LU decomposition}.
		 * @see #DECOMPOSITION_LU_L
		 * @see #DECOMPOSITION_LU_U
		 * @see #DECOMPOSITION_LU_P
		 * @see #DECOMPOSITION_LU_SOLVE_CONSTANT
		 * @see #DECOMPOSITION_LU_SOLVE_RESULT
		 */
		public static final double[][] DECOMPOSITION_LU_INPUT = new double[][] {
			{17,    24,     1,     8,    15},
			{23,     5,     7,    14,    16},
			{ 4,     6,    13,    20,    22},
			{10,    12,    19,    21,     3},
			{11,    18,    25,     2,     9}
		};

		/**
		 * This matrix is the lower triangular matrix <i>L</i> resulting of the {@link LUDecomposition LU decomposition} of the {@link #DECOMPOSITION_LU_INPUT input matrix}.
		 * @see #DECOMPOSITION_LU_INPUT
		 * @see #DECOMPOSITION_LU_U
		 * @see #DECOMPOSITION_LU_P
		 * @see #DECOMPOSITION_LU_SOLVE_CONSTANT
		 * @see #DECOMPOSITION_LU_SOLVE_RESULT
		 */
		public static final double[][] DECOMPOSITION_LU_L = new double[][] {
			{ 1.000000000000000,   0,                   0,                   0,                   0},
			{ 0.739130434782609,   1.000000000000000,   0,                   0,                   0},
			{ 0.478260869565217,   0.768736616702355,   1.000000000000000,   0,                   0},
			{ 0.173913043478261,   0.252676659528908,   0.516365202411714,   1.000000000000000,   0},
			{ 0.434782608695652,   0.483940042826552,   0.723083548664944,   0.923076923076923,   1.000000000000000}
		};

		/**
		 * This matrix is the upper triangular matrix <i>U</i> resulting of the {@link LUDecomposition LU decomposition} of the {@link #DECOMPOSITION_LU_INPUT input matrix}.
		 * @see #DECOMPOSITION_LU_INPUT
		 * @see #DECOMPOSITION_LU_L
		 * @see #DECOMPOSITION_LU_P
		 * @see #DECOMPOSITION_LU_SOLVE_CONSTANT
		 * @see #DECOMPOSITION_LU_SOLVE_RESULT
		 */
		public static final double[][] DECOMPOSITION_LU_U = new double[][] {
			{ 23.000000000000000,   5.000000000000000,   7.000000000000000,  14.000000000000000,  16.000000000000000},
			{  0,                  20.304347826086957,  -4.173913043478261,  -2.347826086956521,   3.173913043478262},
			{  0,                   0,                  24.860813704496785,  -2.890792291220558,  -1.092077087794433},
			{  0,                   0,                   0,                  19.651162790697672,  18.979328165374678},
			{  0,                   0,                   0,                   0,                 -22.222222222222225}
		};

		/**
		 * This matrix is the permutation matrix <i>P</i> resulting of the LU decomposition of the {@link #DECOMPOSITION_LU_INPUT input matrix}.
		 * @see #DECOMPOSITION_LU_INPUT
		 * @see #DECOMPOSITION_LU_L
		 * @see #DECOMPOSITION_LU_U
		 * @see #DECOMPOSITION_LU_SOLVE_CONSTANT
		 * @see #DECOMPOSITION_LU_SOLVE_RESULT
		 */
		public static final double[][] DECOMPOSITION_LU_P = new double[][] {
			{ 0,     1,     0,     0,     0},
			{ 1,     0,     0,     0,     0},
			{ 0,     0,     0,     0,     1},
			{ 0,     0,     1,     0,     0},
			{ 0,     0,     0,     1,     0}
		};

		/**
		 * A constant vector <i>B</i> used for testing linear system solving with {@link LUDecomposition LU decomposition} of {@link MatrixTestData#DECOMPOSITION_LU_INPUT input matrix}.
		 * @see #DECOMPOSITION_LU_INPUT
		 * @see #DECOMPOSITION_LU_L
		 * @see #DECOMPOSITION_LU_U
		 * @see #DECOMPOSITION_LU_P
		 * @see #DECOMPOSITION_LU_SOLVE_RESULT
		 */
		public static final double[] DECOMPOSITION_LU_SOLVE_CONSTANT = new double[] {
				65,
				65,
				65,
				65,
				65,
		};

		/**
		 * A result vector <i>X</i> used for testing linear system solving with {@link LUDecomposition LU decomposition} of {@link MatrixTestData#DECOMPOSITION_LU_INPUT input matrix}.
		 * @see #DECOMPOSITION_LU_INPUT
		 * @see #DECOMPOSITION_LU_L
		 * @see #DECOMPOSITION_LU_U
		 * @see #DECOMPOSITION_LU_P
		 * @see #DECOMPOSITION_LU_SOLVE_CONSTANT
		 */
		public static final double[] DECOMPOSITION_LU_SOLVE_RESULT = new double[] {
				1.000000000000000,
				1.000000000000000,
				1.000000000000000,
				1.000000000000000,
				1.000000000000000,
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

			/**
			 * This matrix is dedicated to the testing of the Singular Values (SVD) decomposition.
			 * @see #DECOMPOSITION_SVD_U
			 * @see #DECOMPOSITION_SVD_S
			 * @see #DECOMPOSITION_SVD_V
			 */
			public static final double[][] DECOMPOSITION_SVD_INPUT = new double[][] {
				{ 17, 24,  1,  8, 15},
				{ 23,  5,  7, 14, 16},
				{  4,  6, 13, 20, 22},
				{ 10, 12, 19, 21,  3},
				{ 11, 18, 25,  2,  9}
			};

			/**
			 * This matrix is the <i>U</i> matrix obtained after the Singular Values (SVD) decomposition of the {@link #DECOMPOSITION_SVD_INPUT input matrix}.
			 * @see #DECOMPOSITION_SVD_INPUT
			 * @see #DECOMPOSITION_SVD_S
			 * @see #DECOMPOSITION_SVD_V
			 */
			public static final double[][] DECOMPOSITION_SVD_U = new double[][] {
				{ 0.44721359549995793928183473374626,     0.54563487312996778094350033489632,  0.51166727360169272880036685827406, -0.19543950758485479560047750383079,   -0.44975836315119696024624071783596},
				{ 0.44721359549995793928183473374626,     0.44975836315119696024624071783596, -0.19543950758485479560047750383079,  0.51166727360169272880036685827406,    0.54563487312996778094350033489632},
				{ 0.44721359549995793928183473374626, -3.6102493598044168843311881307351e-44, -0.63245553203367586639977870888654, -0.63245553203367586639977870888654, 1.7037410954189828268483469351314e-49},
				{ 0.44721359549995793928183473374626,    -0.44975836315119696024624071783596, -0.19543950758485479560047750383079,  0.51166727360169272880036685827406,   -0.54563487312996778094350033489632},
				{ 0.44721359549995793928183473374626,    -0.54563487312996778094350033489632,  0.51166727360169272880036685827406, -0.19543950758485479560047750383079,    0.44975836315119696024624071783596}

			};

			/**
			 * This matrix is the <i>S</i> matrix obtained after the Singular Values (SVD) decomposition of the {@link #DECOMPOSITION_SVD_INPUT input matrix}.
			 * @see #DECOMPOSITION_SVD_INPUT
			 * @see #DECOMPOSITION_SVD_U
			 * @see #DECOMPOSITION_SVD_V
			 */
			public static final double[][] DECOMPOSITION_SVD_S = new double[][] {
				{ 65.0,                                 0,                                 0,                                 0,                                 0},
				{    0, 22.547088685879657984674226396467,                                 0,                                 0,                                 0},
				{    0,                                 0, 21.687425355202639411956035427154,                                 0,                                 0},
				{    0,                                 0,                                 0, 13.403565997991492328585154445703,                                 0},
				{    0,                                 0,                                 0,                                 0, 11.900789544861194527298509087321}

			};

			/**
			 * This matrix is the <i>V</i> matrix obtained after the Singular Values (SVD) decomposition of the {@link #DECOMPOSITION_SVD_INPUT input matrix}.
			 * @see #DECOMPOSITION_SVD_INPUT
			 * @see #DECOMPOSITION_SVD_U
			 * @see #DECOMPOSITION_SVD_S
			 */
			public static final double[][] DECOMPOSITION_SVD_V = new double[][] {
				{ 0.44721359549995793928183473374626,   0.40451643610455471341979148603303,    0.24656489623973259874361786663113,     0.6627260006535807384660483816136,  0.36927828655536965366206505316499},
				{ 0.44721359549995793928183473374626, 0.0055661597144479252799165035570114,     0.6627260006535807384660483816136,   -0.24656489623973259874361786663113, -0.54769427408549129087991810164138},
				{ 0.44721359549995793928183473374626,  -0.82016519163800527739941597918008, 1.0263687996129757425478176433711e-49, 2.7831673927115195568141887434356e-44,  0.35683197506024327443570609695277},
				{ 0.44721359549995793928183473374626, 0.0055661597144479252799165035570114,    -0.6627260006535807384660483816136,    0.24656489623973259874361786663113, -0.54769427408549129087991810164138},
				{ 0.44721359549995793928183473374626,   0.40451643610455471341979148603303,   -0.24656489623973259874361786663113,    -0.6627260006535807384660483816136,  0.36927828655536965366206505316499}

			};

			/**
			 * The matrix is dedicated to the testing of {@link QRDecomposition QR decomposition}.
			 * @see #DECOMPOSITION_QR_Q
			 * @see #DECOMPOSITION_QR_R
			 */
			public static final double[][] DECOMPOSITION_QR_INPUT = new double[][] {
				{ 92,    99,     1,     8,    15},
				{ 98,    80,     7,    14,    16},
				{  4,    81,    88,    20,    22},
				{ 85,    87,    19,    21,     3},
				{ 86,    93,    25,     2,     9},
				{ 17,    24,    76,    83,    90},
				{ 23,     5,    82,    89,    91},
				{ 79,     6,    13,    95,    97},
				{ 10,    12,    94,    96,    78},
				{ 11,    18,   100,    77,    84},
			};

			/**
			 * The <i>Q</i> matrix from the {@link QRDecomposition QR decomposition} of the {@link #DECOMPOSITION_QR_INPUT} matrix.
			 * @see #DECOMPOSITION_QR_INPUT
			 * @see #DECOMPOSITION_QR_R
			 */
			public static final double[][] DECOMPOSITION_QR_Q = new double[][] {
				{-0.460086274265395,   0.196627523055269,   0.215582591526964,   0.357403282603279,  -0.278007402231540 },
				{-0.490091900847921,  -0.031002872723505,   0.100982455671368,  -0.296830913963298,   0.002197781005910},
				{-0.020003751055017,   0.732990922532919,  -0.193615727755168,   0.197613696046200,  -0.098043520838981},
				{-0.425079709919115,   0.139469224638785,   0.081540406773979,   0.100806459537306,   0.626511804533955},
				{-0.430080647682870,   0.188116143108394,   0.068941698875262,  -0.455378530260138,  -0.140388567485506},
				{-0.085015941983823,   0.090235273719329,  -0.361829251964398,   0.421669515509660,  -0.339015521287026},
				{-0.115021568566349,  -0.137395122059446,  -0.476429387819994,  -0.232564681056918,  -0.058810338049576},
				{-0.395074083336590,  -0.577467482485041,  -0.179170603583662,   0.339797361690089,  -0.148739059460358},
				{-0.050009377637543,   0.033076975302844,  -0.495871436717382,   0.165072692443687,   0.565503685478469},
				{-0.055010315401297,   0.081723893772453,  -0.508470144616100,  -0.391112297353757,  -0.201396686540991}

			};

			/**
			 * The <i>P</i> matrix from the {@link QRDecomposition QR decomposition} of the {@link #DECOMPOSITION_QR_INPUT} matrix.
			 * @see #DECOMPOSITION_QR_INPUT
			 * @see #DECOMPOSITION_QR_Q
			 */
			public static final double[][] DECOMPOSITION_QR_R = new double[][] {


				{1.0e+02 * -1.999624964837157,  1.0e+02 * -1.699318652123711,  1.0e+02 * -0.557104466882229,  1.0e+02 * -0.845908622739040,  1.0e+02 * -0.852909935608296},
				{                 0,   1.0e+02 * 1.058686033980072,   1.0e+02 * 0.712016560199359,  1.0e+02 * -0.310260351105106,  1.0e+02 * -0.302606959833420},
				{                 0,                   0,  1.0e+02 * -1.791972943708063,  1.0e+02 * -1.750947773332652,  1.0e+02 * -1.732337110645067},
				{                 0,                   0,                   0,   1.0e+02 * 0.361744400881764,   1.0e+02 * 0.309327200714440},
				{                 0,                   0,                   0,                   0,  1.0e+02 * -0.287747263377251}
			};

			/**
			 * A constant vector <i>B</i> used for testing linear system solving with {@link QRDecomposition QR decomposition}.
			 */
			public static final double[] DECOMPOSITION_QR_SOLVE_CONSTANT = new double[] {
					215,
					215,
					215,
					215,
					215,
					290,
					290,
					290,
					290,
					290,
			};

			/**
			 * A result vector <i>B</i> used for testing linear system solving with {@link QRDecomposition QR decomposition}.
			 */
			public static final double[] DECOMPOSITION_QR_SOLVE_RESULT = new double[] {
					1.0d,
					1.0d,
					1.0d,
					1.0d,
					1.0d,
			};

			/**
			 * The matrix is dedicated to the testing of {@link CholeskyDecomposition Cholesky decomposition}.
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_R
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_SOLVE_CONSTANT
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_SOLVE_RESULT
			 */
			public static final double[][] DECOMPOSITION_CHOLESKY_INPUT = new double[][] {
				{1.000000000000000, 0.500000000000000, 0.333333333333333, 0.250000000000000, 0.200000000000000,   0.166666666666667},
				{0.500000000000000, 1.000000000000000, 0.666666666666667, 0.500000000000000, 0.400000000000000,   0.333333333333333},
				{0.333333333333333, 0.666666666666667, 1.000000000000000, 0.750000000000000, 0.600000000000000,   0.500000000000000},
				{0.250000000000000, 0.500000000000000, 0.750000000000000, 1.000000000000000, 0.800000000000000,   0.666666666666667},
				{0.200000000000000, 0.400000000000000, 0.600000000000000, 0.800000000000000, 1.000000000000000,   0.833333333333333},
				{0.166666666666667, 0.333333333333333, 0.500000000000000, 0.666666666666667, 0.833333333333333,   1.000000000000000}
			};

			/**
			 * The triangular matrix <i>R</i> obtained after {@link CholeskyDecomposition Cholesky decomposition} of the matrix {@link MatrixTestData#DECOMPOSITION_CHOLESKY_INPUT}.
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_INPUT
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_SOLVE_CONSTANT
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_SOLVE_RESULT
			 */
			public static final double[][] DECOMPOSITION_CHOLESKY_R = new double[][] {
				{1.000000000000000,   0.500000000000000, 0.333333333333333, 0.250000000000000,   0.200000000000000,   0.166666666666667},
				{0,                   0.866025403784439, 0.577350269189626, 0.433012701892219,   0.346410161513776,   0.288675134594813},
				{0,                   0,                 0.745355992499930, 0.559016994374947,   0.447213595499958,   0.372677996249965},
				{0,                   0,                 0,                 0.661437827766148,   0.529150262212918,   0.440958551844098},
				{0,                   0,                 0,                 0,                   0.600000000000000,   0.500000000000000},
				{0,                   0,                 0,                 0,                   0,                   0.552770798392567}
			};

			/**
			 * A constant vector <i>B</i> used for testing linear system solving with {@link CholeskyDecomposition Cholesky decomposition} of the matrix {@link MatrixTestData#DECOMPOSITION_CHOLESKY_INPUT}.
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_INPUT
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_R
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_SOLVE_RESULT
			 */
			public static final double[] DECOMPOSITION_CHOLESKY_SOLVE_CONSTANT = new double[] {
					2.450000000000000,
					3.400000000000000,
					3.850000000000000,
					3.966666666666666,
					3.833333333333333,
					3.500000000000000
			};

			/**
			 * A result vector <i>X</i> used for testing linear system solving with {@link CholeskyDecomposition Cholesky decomposition} of the matrix {@link MatrixTestData#DECOMPOSITION_CHOLESKY_INPUT}.
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_INPUT
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_R
			 * @see MatrixTestData#DECOMPOSITION_CHOLESKY_SOLVE_CONSTANT
			 */
			public static final double[] DECOMPOSITION_CHOLESKY_SOLVE_RESULT = new double[] {
					1.000000000000000,
					0.999999999999999,
					1.000000000000002,
					0.999999999999998,
					1.000000000000002,
					0.999999999999999
			};
}
