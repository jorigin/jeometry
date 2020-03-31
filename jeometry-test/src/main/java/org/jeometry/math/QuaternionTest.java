package org.jeometry.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.jeometry.Geometry;
import org.jeometry.factory.GeometryFactory;
import org.jeometry.math.Quaternion;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A test suite for the {@link SimpleQuaternion} implementation.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * Create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * &nbsp;&nbsp;quaternionClass = [the quaternion objects class];<br>
 * <br>
 * &nbsp;&nbsp;GeometryFactory.setMathBuilder([a builder that provide suitable classes]);<br>
 * }<br>
 * </code>
 * <br>
 * If the object provided by the geometry factory are not from the same classes as the declared ones, tests will fail.
 * </p>
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} b{@value Geometry#BUILD}
 * @since 1.0.0
 */
public class QuaternionTest {

	/**
	 * The class that the quaternion objects have to respect.
	 */
	protected static Class<? extends Quaternion> quaternionClass = null;
	
	double quaternionRefA = Double.NaN;
	double quaternionRefB = Double.NaN;
	double quaternionRefC = Double.NaN;
	double quaternionRefD = Double.NaN;
	
	double[] quaternionRefComponents = null;
	
	Quaternion quaternionRef = null;
	
	double quaternionRefNormSquare = Double.NaN;
	
	double quaternionRefNorm       = Double.NaN;
	
	double quaternionTargetA = Double.NaN;
	double quaternionTargetB = Double.NaN;
	double quaternionTargetC = Double.NaN;
	double quaternionTargetD = Double.NaN;
	
	double[] quaternionTargetComponents = null;
	
	Quaternion quaternionTarget = null;
	
	
	double quaternionMultA = Double.NaN;
	double quaternionMultB = Double.NaN;
	double quaternionMultC = Double.NaN;
	double quaternionMultD = Double.NaN;
	
	Quaternion quaternionMultResult = null;
	
	double quaternionRefInvertedA = Double.NaN;
	double quaternionRefInvertedB = Double.NaN;
	double quaternionRefInvertedC = Double.NaN;
	double quaternionRefInvertedD = Double.NaN;
	
	Quaternion quaternionRefInvertedResult = null;
	
	
	double quaternionRefConjugateA = Double.NaN;
	double quaternionRefConjugateB = Double.NaN;
	double quaternionRefConjugateC = Double.NaN;
	double quaternionRefConjugateD = Double.NaN;
	
	Quaternion quaternionRefConjugateResult = null;
	
	/**
	 * Initialize the test static context.
	 */
	@BeforeClass
	public static void initClass() {
		fail("Test class is not initialized. method init() has to be implemented");
	}
	
	/**
	 * Initialize testing.
	 */
	@Before
	public void init() {
		quaternionRefA =  2.12469239d;
		quaternionRefB = -1.76893983d;
		quaternionRefC = 98.12472855d;
		quaternionRefD = -2.11445671d;
		
		quaternionRefComponents = new double[] {quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD};
		
		quaternionRef = GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		
		quaternionRefNormSquare = quaternionRefA*quaternionRefA + quaternionRefB*quaternionRefB + quaternionRefC*quaternionRefC + quaternionRefD*quaternionRefD;
		quaternionRefNorm       = Math.sqrt(quaternionRefNormSquare);
		
		quaternionTargetA =  36.58797462d;
		quaternionTargetB =   0.00768983d;
		quaternionTargetC = -44.12672855d;
		quaternionTargetD =   8.25697125d;
		
		quaternionTargetComponents = new double[] {quaternionTargetA, quaternionTargetB, quaternionTargetC, quaternionTargetD};
		
		quaternionTarget =  GeometryFactory.createQuaternion(quaternionTargetA, quaternionTargetB, quaternionTargetC, quaternionTargetD);
		
		quaternionMultA = quaternionRefA * quaternionTargetA - quaternionRefB * quaternionTargetB - quaternionRefC * quaternionTargetC - quaternionRefD * quaternionTargetD;
		quaternionMultB = quaternionRefA * quaternionTargetB + quaternionRefB * quaternionTargetA + quaternionRefC * quaternionTargetD - quaternionRefD * quaternionTargetC;
		quaternionMultC = quaternionRefA * quaternionTargetC - quaternionRefB * quaternionTargetD + quaternionRefC * quaternionTargetA + quaternionRefD * quaternionTargetB;
		quaternionMultD = quaternionRefA * quaternionTargetD + quaternionRefB * quaternionTargetC - quaternionRefC * quaternionTargetB + quaternionRefD * quaternionTargetA;

		quaternionMultResult =  GeometryFactory.createQuaternion(quaternionMultA, quaternionMultB, quaternionMultC, quaternionMultD);	
		
		
		quaternionRefInvertedA = quaternionRefA / quaternionRefNormSquare;
		quaternionRefInvertedB = -1.0d * quaternionRefB / quaternionRefNormSquare;
		quaternionRefInvertedC = -1.0d * quaternionRefC / quaternionRefNormSquare;
		quaternionRefInvertedD = -1.0d * quaternionRefD / quaternionRefNormSquare;
		
		quaternionRefInvertedResult =  GeometryFactory.createQuaternion(quaternionRefInvertedA, quaternionRefInvertedB, quaternionRefInvertedC, quaternionRefInvertedD);	
		
		quaternionRefConjugateA = quaternionRefA;
		quaternionRefConjugateB = -1.0d*quaternionRefB;
		quaternionRefConjugateC = -1.0d*quaternionRefC;
		quaternionRefConjugateD = -1.0d*quaternionRefD;
		
		quaternionRefConjugateResult =  GeometryFactory.createQuaternion(quaternionRefConjugateA, quaternionRefConjugateB, quaternionRefConjugateC, quaternionRefConjugateD);	
	}
	
	/**
	 * Testing the quaternion default constructor.
	 */
	@Test
	public void constructorDefaultTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion is null", q);
		assertEquals("Invalid scalar (a) value.", 0.0d, q.getScalar(), 0.0d);
		assertEquals("Invalid i (b) value.", 0.0d, q.getI(), 0.0d);
		assertEquals("Invalid j (c) value.", 0.0d, q.getJ(), 0.0d);
		assertEquals("Invalid k (d) value.", 0.0d, q.getK(), 0.0d);
	}
	
	/**
	 * Testing the quaternion parametrized constructor.
	 */
	@Test
	public void constructorParametersTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion is null", q);
		assertEquals("Invalid scalar (a) value.", quaternionRefA, q.getScalar(), 0.0d);
		assertEquals("Invalid i (b) value.", quaternionRefB, q.getI(), 0.0d);
		assertEquals("Invalid j (c) value.", quaternionRefC, q.getJ(), 0.0d);
		assertEquals("Invalid k (d) value.", quaternionRefD, q.getK(), 0.0d);
	}
	
	
	/**
	 * Test the {@link SimpleQuaternion#setComponents(double, double, double, double)} method.
	 */
	@Test
	public void setComponentsTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		q.setComponents(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		
		assertEquals("Invalid scalar value", quaternionRefA, q.getScalar(), 0.0d);
		assertEquals("Invalid i value", quaternionRefB, q.getI(), 0.0d);
		assertEquals("Invalid j value", quaternionRefC, q.getJ(), 0.0d);
		assertEquals("Invalid k value", quaternionRefD, q.getK(), 0.0d);
	}
	
	/**
	 * Test the {@link SimpleQuaternion#getComponents()} method.
	 */
	@Test
	public void getVectorComponentsTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion is null", q);
		
		double[] components = q.getComponents();
		
		assertNotNull("Components vector is null", components);
		assertEquals("Components vector are not 4", 4, components.length);
		assertEquals("Invalid scalar (a) value.", q.getScalar(), components[org.jeometry.math.Quaternion.QUATERNION_SCALAR_COMPONENT], 0.0d);
		assertEquals("Invalid i (b) value.", q.getI(), components[org.jeometry.math.Quaternion.QUATERNION_I_COMPONENT], 0.0d);
		assertEquals("Invalid j (c) value.", q.getJ(), components[org.jeometry.math.Quaternion.QUATERNION_J_COMPONENT], 0.0d);
		assertEquals("Invalid k (d) value.", q.getK(), components[org.jeometry.math.Quaternion.QUATERNION_K_COMPONENT], 0.0d);
		
	}
	
	/**
	 * Test the {@link SimpleQuaternion#getComponents(double[])} method.
	 */
	@Test
	public void getVectorComponentsResultTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion is null", q);
		
		double[] result = new double[4];
		
		double[] components = q.getComponents(result);
		
		assertNotNull("Components vector is null", components);
		assertEquals("Components vector are not 4", 4, components.length);
		assertSame("Components output are not the same", components, result);
		
		assertEquals("Invalid scalar (a) value.", q.getScalar(), components[org.jeometry.math.Quaternion.QUATERNION_SCALAR_COMPONENT], 0.0d);
		assertEquals("Invalid i (b) value.", q.getI(), components[org.jeometry.math.Quaternion.QUATERNION_I_COMPONENT], 0.0d);
		assertEquals("Invalid j (c) value.", q.getJ(), components[org.jeometry.math.Quaternion.QUATERNION_J_COMPONENT], 0.0d);
		assertEquals("Invalid k (d) value.", q.getK(), components[org.jeometry.math.Quaternion.QUATERNION_K_COMPONENT], 0.0d);
		
	}
	
	/**
	 * Testing {@link SimpleQuaternion#getScalar()} and {@link SimpleQuaternion#setScalar(double)} methods.
	 */
	@Test
	public void accessorScalarTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals("Invalid scalar parameter", quaternionRefA, q.getScalar(), 0.0d);
		
		q.setScalar(9999999);
		
		assertEquals("Invalid scalar parameter", 9999999, q.getScalar(), 0.0d);
	}
	
	/**
	 * Testing {@link SimpleQuaternion#getI()} and {@link SimpleQuaternion#setI(double)} methods.
	 */
	@Test
	public void accessorITest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals("Invalid i parameter", quaternionRefB, q.getI(), 0.0d);
		
		q.setI(9999999);
		
		assertEquals("Invalid i parameter", 9999999, q.getI(), 0.0d);
	}
	
	/**
	 * Testing {@link SimpleQuaternion#getJ()} and {@link SimpleQuaternion#setJ(double)} methods.
	 */
	@Test
	public void accessorJTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals("Invalid j parameter", quaternionRefC, q.getJ(), 0.0d);
		
		q.setJ(9999999);
		
		assertEquals("Invalid j parameter", 9999999, q.getJ(), 0.0d);
	}
	
	/**
	 * Testing {@link SimpleQuaternion#getK()} and {@link SimpleQuaternion#setK(double)} methods.
	 */
	@Test
	public void accessorKTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals("Invalid k parameter", quaternionRefD, q.getK(), 0.0d);
		
		q.setK(9999999);
		
		assertEquals("Invalid k parameter", 9999999, q.getK(), 0.0d);
	}
	
	
	/**
	 * Test {@link SimpleQuaternion#normSquare()} method.
	 */
	@Test
	public void normSquareTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion is null", q);
		
		if (q != null) {
			assertEquals("Invalid norm square value.", quaternionRefNormSquare, q.normSquare(), 0.0d);
		}
	}
	
	/**
	 * Test {@link SimpleQuaternion#norm()} method.
	 */
	@Test
	public void normTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion is null", q);
		
		if (q != null) {
			assertEquals("Invalid norm square value.", quaternionRefNorm, q.norm(), 0.0d);
		}
	}
	
	/**
	 * Test {@link SimpleQuaternion#mult(org.jeometry.math.Quaternion)} method.
	 */
	@Test
	public void multTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion is null", q);
		
		if (q != null) {
			
			org.jeometry.math.Quaternion result = null;
			try {
				result = q.mult(quaternionTarget);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}
			
			assertNotNull("Resulting quaternion is null", result);
			
			if (result != null) {
				assertEquals("Invalid scalar (a) parameter.", quaternionMultA, result.getScalar(), 0.0d);
				assertEquals("Invalid i base (b) parameter.", quaternionMultB, result.getI(), 0.0d);
				assertEquals("Invalid j base (c) parameter.", quaternionMultC, result.getJ(), 0.0d);
				assertEquals("Invalid k base (d) parameter.", quaternionMultD, result.getK(), 0.0d);
			}
			
			
		}
	}
	
	/**
	 * Test {@link SimpleQuaternion#mult(org.jeometry.math.Quaternion, org.jeometry.math.Quaternion)} method.
	 */
	@Test
	public void multResultTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		Quaternion result = null;
		try {
			result =  GeometryFactory.createQuaternion();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion q is null", q);
		assertNotNull("Created quaternion result is null", result);
		
		if (q != null) {
			
			org.jeometry.math.Quaternion resultRef = null;
			
			try {
				resultRef = q.mult(quaternionTarget, result);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull("Resulting quaternion is null", result);
			assertNotNull("Resulting quaternion ref is null", resultRef);
			
			assertSame("Result references differs.", result, resultRef);
			
			if (result != null) {
				assertEquals("Invalid scalar (a) parameter.", quaternionMultA, result.getScalar(), 0.0d);
				assertEquals("Invalid i base (b) parameter.", quaternionMultB, result.getI(), 0.0d);
				assertEquals("Invalid j base (c) parameter.", quaternionMultC, result.getJ(), 0.0d);
				assertEquals("Invalid k base (d) parameter.", quaternionMultD, result.getK(), 0.0d);
			}
		}
	}
	
	
	/**
	 * Test {@link SimpleQuaternion#multAffect(org.jeometry.math.Quaternion)} method.
	 */
	@Test
	public void multAffectTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion is null", q);
		
		if (q != null) {
			
			org.jeometry.math.Quaternion result = null;
			try {
				result = q.multAffect(quaternionTarget);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}
			
			assertNotNull("Resulting quaternion is null", result);
			assertSame("Resulting quaternion reference is invalid.", result, q);
			
			if (result != null) {
				assertEquals("Invalid scalar (a) parameter.", quaternionMultA, result.getScalar(), 0.0d);
				assertEquals("Invalid i base (b) parameter.", quaternionMultB, result.getI(), 0.0d);
				assertEquals("Invalid j base (c) parameter.", quaternionMultC, result.getJ(), 0.0d);
				assertEquals("Invalid k base (d) parameter.", quaternionMultD, result.getK(), 0.0d);
			}
		}
	}
	
	/**
	 * Test {@link SimpleQuaternion#invertQuaternion()} method.
	 */
	@Test
	public void invertQuaternionTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
        assertNotNull("Created quaternion is null", q);
		
		if (q != null) {
			org.jeometry.math.Quaternion result = null;
			
			try {
				result = q.invertQuaternion();
			} catch (IllegalStateException e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
				result = null;
			}
			
			assertNotNull("Resulting quaternion is null", result);
			
			if (result != null) {
				assertEquals("Invalid scalar (a) parameter.", quaternionRefInvertedA, result.getScalar(), 0.0d);
				assertEquals("Invalid i base (b) parameter.", quaternionRefInvertedB, result.getI(), 0.0d);
				assertEquals("Invalid j base (c) parameter.", quaternionRefInvertedC, result.getJ(), 0.0d);
				assertEquals("Invalid k base (d) parameter.", quaternionRefInvertedD, result.getK(), 0.0d);
			}
		}
	}
	
	/**
	 * Test {@link SimpleQuaternion#invertQuaternion(org.jeometry.math.Quaternion)} method.
	 */
	@Test
	public void invertResultTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		Quaternion result = null;
		try {
			result =  GeometryFactory.createQuaternion();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion q is null", q);
		assertNotNull("Created quaternion result is null", result);
		
		if (q != null) {
			
			org.jeometry.math.Quaternion resultRef = null;
			
			try {
				resultRef = q.invertQuaternion(result);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull("Resulting quaternion is null", result);
			assertNotNull("Resulting quaternion ref is null", resultRef);
			assertSame("Result references differs.", result, resultRef);
			
			if (result != null) {
				assertEquals("Invalid scalar (a) parameter.", quaternionRefInvertedA, result.getScalar(), 0.0d);
				assertEquals("Invalid i base (b) parameter.", quaternionRefInvertedB, result.getI(), 0.0d);
				assertEquals("Invalid j base (c) parameter.", quaternionRefInvertedC, result.getJ(), 0.0d);
				assertEquals("Invalid k base (d) parameter.", quaternionRefInvertedD, result.getK(), 0.0d);
			}
		}
	}
	
	/**
	 * Test {@link SimpleQuaternion#invertQuaternionAffect()} method.
	 */
	@Test
	public void invertAffectTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		assertNotNull("Created quaternion q is null", q);
		
		if (q != null) {
			
			org.jeometry.math.Quaternion resultRef = null;
			
			try {
				resultRef = q.invertQuaternionAffect();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull("Resulting quaternion is null", resultRef);
			assertSame("Result references differs.", q, resultRef);
			
			if (resultRef != null) {
				assertEquals("Invalid scalar (a) parameter.", quaternionRefInvertedA, resultRef.getScalar(), 0.0d);
				assertEquals("Invalid i base (b) parameter.", quaternionRefInvertedB, resultRef.getI(), 0.0d);
				assertEquals("Invalid j base (c) parameter.", quaternionRefInvertedC, resultRef.getJ(), 0.0d);
				assertEquals("Invalid k base (d) parameter.", quaternionRefInvertedD, resultRef.getK(), 0.0d);
			}
		}
	}
	
	/**
	 * Test {@link SimpleQuaternion#conjugateQuaternion()} method.
	 */
	@Test
	public void conjugateQuaternionTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
        assertNotNull("Created quaternion is null", q);
		
		if (q != null) {
			org.jeometry.math.Quaternion result = null;
			
			try {
				result = q.conjugateQuaternion();
			} catch (IllegalStateException e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
				result = null;
			}
			
			assertNotNull("Resulting quaternion is null", result);
			
			if (result != null) {
				assertEquals("Invalid scalar (a) parameter.", quaternionRefConjugateA, result.getScalar(), 0.0d);
				assertEquals("Invalid i base (b) parameter.", quaternionRefConjugateB, result.getI(), 0.0d);
				assertEquals("Invalid j base (c) parameter.", quaternionRefConjugateC, result.getJ(), 0.0d);
				assertEquals("Invalid k base (d) parameter.", quaternionRefConjugateD, result.getK(), 0.0d);
			}
		}
	}
	
	/**
	 * Test {@link SimpleQuaternion#conjugateQuaternion(org.jeometry.math.Quaternion)} method.
	 */
	@Test
	public void conjugateResultTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		Quaternion result = null;
		try {
			result =  GeometryFactory.createQuaternion();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull("Created quaternion q is null", q);
		assertNotNull("Created quaternion result is null", result);
		
		if (q != null) {
			
			org.jeometry.math.Quaternion resultRef = null;
			
			try {
				resultRef = q.conjugateQuaternion(result);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull("Resulting quaternion is null", result);
			assertNotNull("Resulting quaternion ref is null", resultRef);
			assertSame("Result references differs.", result, resultRef);
			
			if (result != null) {
				assertEquals("Invalid scalar (a) parameter.", quaternionRefConjugateA, result.getScalar(), 0.0d);
				assertEquals("Invalid i base (b) parameter.", quaternionRefConjugateB, result.getI(), 0.0d);
				assertEquals("Invalid j base (c) parameter.", quaternionRefConjugateC, result.getJ(), 0.0d);
				assertEquals("Invalid k base (d) parameter.", quaternionRefConjugateD, result.getK(), 0.0d);
			}
		}
	}
	
	/**
	 * Test {@link SimpleQuaternion#conjugateQuaternionAffect()} method.
	 */
	@Test
	public void conjugateAffectTest() {
		Quaternion q = null;
		try {
			q =  GeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		assertNotNull("Created quaternion q is null", q);
		
		if (q != null) {
			
			org.jeometry.math.Quaternion resultRef = null;
			
			try {
				resultRef = q.conjugateQuaternionAffect();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull("Resulting quaternion is null", resultRef);
			assertSame("Result references differs.", q, resultRef);
			
			if (resultRef != null) {
				assertEquals("Invalid scalar (a) parameter.", quaternionRefConjugateA, resultRef.getScalar(), 0.0d);
				assertEquals("Invalid i base (b) parameter.", quaternionRefConjugateB, resultRef.getI(), 0.0d);
				assertEquals("Invalid j base (c) parameter.", quaternionRefConjugateC, resultRef.getJ(), 0.0d);
				assertEquals("Invalid k base (d) parameter.", quaternionRefConjugateD, resultRef.getK(), 0.0d);
			}
		}
	}
}
