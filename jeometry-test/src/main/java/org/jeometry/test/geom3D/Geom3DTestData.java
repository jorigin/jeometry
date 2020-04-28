package org.jeometry.test.geom3D;


/**
 * A set of data dedicated to 3D geometry functionalities test<br>
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.2
 *
 */
public class Geom3DTestData {

	/**
	 * A 3D point with all coordinates set to <i>0</i>.
	 */
	public static final double[] GEOM3D_POINT0 = new double[] {0.0d, 0.0d, 0.0d};
	
	/**
	 * The <i>A</i> 3D point.
	 */
	public static final double[] GEOM3D_POINTA = new double[] {1.0d, 2.0d, 3.0d};
	
	/**
	 * A 3D affine transformation matrix set to identity.
	 */
	public static final double[][] TRANSFORM_MATRIX_ID = new double[][] {
		{1, 0, 0, 0},
		{0, 1, 0, 0},
		{0, 0, 1, 0},
		{0, 0, 0, 1}
	};
	
	
	/**
	 * A 3D affine transformation matrix set to a rotation and a translation.
	 */
	public static final double[][] TRANSFORM_MATRIX_RT = new double[][] {
		{  0.7802619276224012, -0.37575434036478533,  0.49999999999999994, 1.0 },
		{  0.5732492354716722,  0.74936663721845710, -0.33141357403559180, 2.0 },
		{ -0.2501532297095488,  0.54521401185307620,  0.80010314519126560, 3.0 },
		{  0.0,                 0.0,                  0.0,                 1.0 }
	};
	
	/**
	 * The inverse of 3D affine transformation described by {@link Geom3DTestData#TRANSFORM_MATRIX_RT}.
	 */
	public static final double[][] TRANSFORM_MATRIX_RT_INV = new double[][] {
		{  0.7802619276224011,  0.5732492354716721, -0.25015322970954873, -1.1763007094370990 },
		{ -0.3757543403647854,  0.7493666372184572,  0.54521401185307630, -2.7586209696313580 },
		{  0.4999999999999999, -0.3314135740355918,  0.80010314519126570, -2.2374822875026132 },
		{  0.0,                 0.0,                 0.0,                  1.0 }
	};
	
	/**
	 * The result of the transformation of the point {@link Geom3DTestData#GEOM3D_POINTA GEOM3D_POINTA} by the transformation {@link Geom3DTestData#TRANSFORM_MATRIX_RT TRANSFORM_MATRIX_RT}.
	 */
	public static final double[] TRANSFORM_MATRIX_RT_POINTA = new double[] {2.528753246892831, 3.077741787801811, 6.240584229570400};

	/**
	 * The result of the transformation of the point {@link Geom3DTestData#GEOM3D_POINTA GEOM3D_POINT0} by the transformation {@link Geom3DTestData#TRANSFORM_MATRIX_RT TRANSFORM_MATRIX_RT}.
	 */
	public static final double[] TRANSFORM_MATRIX_RT_POINT0 = new double[] {1.0d, 2.0d, 3.0d};

}
