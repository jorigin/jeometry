package org.jeometry.test.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Quaternion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * A test suite for the {@link Quaternion} implementation.<br>
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
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class QuaternionTest extends VectorTest {

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
	@BeforeAll
	public static void initClass() {
		fail("Test class is not initialized. method init() has to be implemented");
	}
	
	/**
	 * Initialize testing.
	 */
	@BeforeEach
	public void init() {
		quaternionRefA =  2.12469239d;
		quaternionRefB = -1.76893983d;
		quaternionRefC = 98.12472855d;
		quaternionRefD = -2.11445671d;
		
		quaternionRefComponents = new double[] {quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD};
		
		quaternionRef = JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		
		quaternionRefNormSquare = quaternionRefA*quaternionRefA + quaternionRefB*quaternionRefB + quaternionRefC*quaternionRefC + quaternionRefD*quaternionRefD;
		quaternionRefNorm       = Math.sqrt(quaternionRefNormSquare);
		
		quaternionTargetA =  36.58797462d;
		quaternionTargetB =   0.00768983d;
		quaternionTargetC = -44.12672855d;
		quaternionTargetD =   8.25697125d;
		
		quaternionTargetComponents = new double[] {quaternionTargetA, quaternionTargetB, quaternionTargetC, quaternionTargetD};
		
		quaternionTarget =  JeometryFactory.createQuaternion(quaternionTargetA, quaternionTargetB, quaternionTargetC, quaternionTargetD);
		
		quaternionMultA = quaternionRefA * quaternionTargetA - quaternionRefB * quaternionTargetB - quaternionRefC * quaternionTargetC - quaternionRefD * quaternionTargetD;
		quaternionMultB = quaternionRefA * quaternionTargetB + quaternionRefB * quaternionTargetA + quaternionRefC * quaternionTargetD - quaternionRefD * quaternionTargetC;
		quaternionMultC = quaternionRefA * quaternionTargetC - quaternionRefB * quaternionTargetD + quaternionRefC * quaternionTargetA + quaternionRefD * quaternionTargetB;
		quaternionMultD = quaternionRefA * quaternionTargetD + quaternionRefB * quaternionTargetC - quaternionRefC * quaternionTargetB + quaternionRefD * quaternionTargetA;

		quaternionMultResult =  JeometryFactory.createQuaternion(quaternionMultA, quaternionMultB, quaternionMultC, quaternionMultD);	
		
		
		quaternionRefInvertedA = quaternionRefA / quaternionRefNormSquare;
		quaternionRefInvertedB = -1.0d * quaternionRefB / quaternionRefNormSquare;
		quaternionRefInvertedC = -1.0d * quaternionRefC / quaternionRefNormSquare;
		quaternionRefInvertedD = -1.0d * quaternionRefD / quaternionRefNormSquare;
		
		quaternionRefInvertedResult =  JeometryFactory.createQuaternion(quaternionRefInvertedA, quaternionRefInvertedB, quaternionRefInvertedC, quaternionRefInvertedD);	
		
		quaternionRefConjugateA = quaternionRefA;
		quaternionRefConjugateB = -1.0d*quaternionRefB;
		quaternionRefConjugateC = -1.0d*quaternionRefC;
		quaternionRefConjugateD = -1.0d*quaternionRefD;
		
		quaternionRefConjugateResult =  JeometryFactory.createQuaternion(quaternionRefConjugateA, quaternionRefConjugateB, quaternionRefConjugateC, quaternionRefConjugateD);	
	}
	
	/**
	 * Testing the quaternion default constructor.
	 */
	@Test
	public void constructorDefaultTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		assertEquals(0.0d, q.getScalar(), 0.0d, "Invalid scalar (a) value.");
		assertEquals(0.0d, q.getI(), 0.0d, "Invalid i (b) value.");
		assertEquals(0.0d, q.getJ(), 0.0d, "Invalid j (c) value.");
		assertEquals(0.0d, q.getK(), 0.0d, "Invalid k (d) value.");
	}
	
	/**
	 * Testing the quaternion parametrized constructor.
	 */
	@Test
	public void constructorParametersTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		assertEquals(quaternionRefA, q.getScalar(), 0.0d, "Invalid scalar (a) value.");
		assertEquals(quaternionRefB, q.getI(), 0.0d, "Invalid i (b) value.");
		assertEquals(quaternionRefC, q.getJ(), 0.0d, "Invalid j (c) value.");
		assertEquals(quaternionRefD, q.getK(), 0.0d, "Invalid k (d) value.");
	}
	
	
	/**
	 * Test the {@link Quaternion#setComponents(double, double, double, double)} method.
	 */
	@Test
	public void setComponentsTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		q.setComponents(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		
		assertEquals(quaternionRefA, q.getScalar(), 0.0d, "Invalid scalar value");
		assertEquals(quaternionRefB, q.getI(), 0.0d, "Invalid i value");
		assertEquals(quaternionRefC, q.getJ(), 0.0d, "Invalid j value");
		assertEquals(quaternionRefD, q.getK(), 0.0d, "Invalid k value");
	}
	
	/**
	 * Test the {@link Quaternion#getValues()} method.
	 */
	@Test
	public void getVectorComponentsTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		
		double[] components = q.getValues();
		
		assertNotNull(components, "Components vector is null");
		assertEquals(4, components.length, "Components vector are not 4");
		assertEquals(q.getScalar(), components[org.jeometry.math.Quaternion.QUATERNION_SCALAR_COMPONENT], 0.0d, "Invalid scalar (a) value.");
		assertEquals(q.getI(), components[org.jeometry.math.Quaternion.QUATERNION_I_COMPONENT], 0.0d, "Invalid i (b) value.");
		assertEquals(q.getJ(), components[org.jeometry.math.Quaternion.QUATERNION_J_COMPONENT], 0.0d, "Invalid j (c) value.");
		assertEquals(q.getK(), components[org.jeometry.math.Quaternion.QUATERNION_K_COMPONENT], 0.0d, "Invalid k (d) value.");
		
	}
	
	/**
	 * Test the {@link Quaternion#getValues(double[])} method.
	 */
	@Test
	public void getVectorComponentsResultTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		
		double[] result = new double[4];
		
		double[] components = q.getValues(result);
		
		assertNotNull(components, "Components vector is null");
		assertEquals(4, components.length, "Components vector are not 4");
		assertSame(components, result, "Components output are not the same");
		
		assertEquals(q.getScalar(), components[org.jeometry.math.Quaternion.QUATERNION_SCALAR_COMPONENT], 0.0d, "Invalid scalar (a) value.");
		assertEquals(q.getI(), components[org.jeometry.math.Quaternion.QUATERNION_I_COMPONENT], 0.0d, "Invalid i (b) value.");
		assertEquals(q.getJ(), components[org.jeometry.math.Quaternion.QUATERNION_J_COMPONENT], 0.0d, "Invalid j (c) value.");
		assertEquals(q.getK(), components[org.jeometry.math.Quaternion.QUATERNION_K_COMPONENT], 0.0d, "Invalid k (d) value.");
		
	}
	
	/**
	 * Testing {@link Quaternion#getScalar()} and {@link Quaternion#setScalar(double)} methods.
	 */
	@Test
	public void accessorScalarTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals(quaternionRefA, q.getScalar(), 0.0d, "Invalid scalar parameter");
		
		q.setScalar(9999999);
		
		assertEquals(9999999, q.getScalar(), 0.0d, "Invalid scalar parameter");
	}
	
	/**
	 * Testing {@link Quaternion#getI()} and {@link Quaternion#setI(double)} methods.
	 */
	@Test
	public void accessorITest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals(quaternionRefB, q.getI(), 0.0d, "Invalid i parameter");
		
		q.setI(9999999);
		
		assertEquals(9999999, q.getI(), 0.0d, "Invalid i parameter");
	}
	
	/**
	 * Testing {@link Quaternion#getJ()} and {@link Quaternion#setJ(double)} methods.
	 */
	@Test
	public void accessorJTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals(quaternionRefC, q.getJ(), 0.0d, "Invalid j parameter");
		
		q.setJ(9999999);
		
		assertEquals(9999999, q.getJ(), 0.0d, "Invalid j parameter");
	}
	
	/**
	 * Testing {@link Quaternion#getK()} and {@link Quaternion#setK(double)} methods.
	 */
	@Test
	public void accessorKTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals(quaternionRefD, q.getK(), 0.0d, "Invalid k parameter");
		
		q.setK(9999999);
		
		assertEquals(9999999, q.getK(), 0.0d, "Invalid k parameter");
	}
	
	
	/**
	 * Test {@link Quaternion#normSquare()} method.
	 */
	@Test
	public void normSquareTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		
		if (q != null) {
			assertEquals(quaternionRefNormSquare, q.normSquare(), 0.0d, "Invalid norm square value.");
		}
	}
	
	/**
	 * Test {@link Quaternion#norm()} method.
	 */
	@Test
	public void normTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		
		if (q != null) {
			assertEquals(quaternionRefNorm, q.norm(), 0.0d, "Invalid norm square value.");
		}
	}
	
	/**
	 * Test {@link Quaternion#mult(Quaternion)} method.
	 */
	@Test
	public void multTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		
		if (q != null) {
			
			Quaternion result = null;
			try {
				result = q.mult(quaternionTarget);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}
			
			assertNotNull(result, "Resulting quaternion is null");
			
			if (result != null) {
				assertEquals(quaternionMultA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(quaternionMultB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(quaternionMultC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(quaternionMultD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
			}
			
			
		}
	}
	
	/**
	 * Test {@link Quaternion#mult(Quaternion, Quaternion)} method.
	 */
	@Test
	public void multResultTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		Quaternion result = null;
		try {
			result =  JeometryFactory.createQuaternion();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion q is null");
		assertNotNull(result, "Created quaternion result is null");
		
		if (q != null) {
			
			Quaternion resultRef = null;
			
			try {
				resultRef = q.mult(quaternionTarget, result);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull(result, "Resulting quaternion is null");
			assertNotNull(resultRef, "Resulting quaternion ref is null");
			
			assertSame(result, resultRef, "Result references differs.");
			
			if (result != null) {
				assertEquals(quaternionMultA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(quaternionMultB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(quaternionMultC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(quaternionMultD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
			}
		}
	}
	
	
	/**
	 * Test {@link Quaternion#multAffect(Quaternion)} method.
	 */
	@Test
	public void multAffectTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		
		if (q != null) {
			
			Quaternion result = null;
			try {
				result = q.multAffect(quaternionTarget);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}
			
			assertNotNull(result, "Resulting quaternion is null");
			assertSame(result, q, "Resulting quaternion reference is invalid.");
			
			if (result != null) {
				assertEquals(quaternionMultA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(quaternionMultB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(quaternionMultC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(quaternionMultD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
			}
		}
	}
	
	/**
	 * Test {@link Quaternion#invertQuaternion()} method.
	 */
	@Test
	public void invertQuaternionTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
        assertNotNull(q, "Created quaternion is null");
		
		if (q != null) {
			Quaternion result = null;
			
			try {
				result = q.invertQuaternion();
			} catch (IllegalStateException e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
				result = null;
			}
			
			assertNotNull(result, "Resulting quaternion is null");
			
			if (result != null) {
				assertEquals(quaternionRefInvertedA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(quaternionRefInvertedB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(quaternionRefInvertedC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(quaternionRefInvertedD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
			}
		}
	}
	
	/**
	 * Test {@link Quaternion#invertQuaternion(Quaternion)} method.
	 */
	@Test
	public void invertResultTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		Quaternion result = null;
		try {
			result =  JeometryFactory.createQuaternion();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion q is null");
		assertNotNull(result, "Created quaternion result is null");
		
		if (q != null) {
			
			Quaternion resultRef = null;
			
			try {
				resultRef = q.invertQuaternion(result);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull(result, "Resulting quaternion is null");
			assertNotNull(resultRef, "Resulting quaternion ref is null");
			assertSame(result, resultRef, "Result references differs.");
			
			if (result != null) {
				assertEquals(quaternionRefInvertedA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(quaternionRefInvertedB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(quaternionRefInvertedC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(quaternionRefInvertedD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
			}
		}
	}
	
	/**
	 * Test {@link Quaternion#invertQuaternionAffect()} method.
	 */
	@Test
	public void invertAffectTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		assertNotNull(q, "Created quaternion q is null");
		
		if (q != null) {
			
			Quaternion resultRef = null;
			
			try {
				resultRef = q.invertQuaternionAffect();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull(resultRef, "Resulting quaternion is null");
			assertSame(q, resultRef, "Result references differs.");
			
			if (resultRef != null) {
				assertEquals(quaternionRefInvertedA, resultRef.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(quaternionRefInvertedB, resultRef.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(quaternionRefInvertedC, resultRef.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(quaternionRefInvertedD, resultRef.getK(), 0.0d, "Invalid k base (d) parameter.");
			}
		}
	}
	
	/**
	 * Test {@link Quaternion#conjugateQuaternion()} method.
	 */
	@Test
	public void conjugateQuaternionTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
        assertNotNull(q, "Created quaternion is null");
		
		if (q != null) {
			Quaternion result = null;
			
			try {
				result = q.conjugateQuaternion();
			} catch (IllegalStateException e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
				result = null;
			}
			
			assertNotNull(result, "Resulting quaternion is null");
			
			if (result != null) {
				assertEquals(quaternionRefConjugateA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(quaternionRefConjugateB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(quaternionRefConjugateC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(quaternionRefConjugateD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
			}
		}
	}
	
	/**
	 * Test {@link Quaternion#conjugateQuaternion(Quaternion)} method.
	 */
	@Test
	public void conjugateResultTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		Quaternion result = null;
		try {
			result =  JeometryFactory.createQuaternion();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion q is null");
		assertNotNull(result, "Created quaternion result is null");
		
		if (q != null) {
			
			Quaternion resultRef = null;
			
			try {
				resultRef = q.conjugateQuaternion(result);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull(result, "Resulting quaternion is null");
			assertNotNull(resultRef, "Resulting quaternion ref is null");
			assertSame(result, resultRef, "Result references differs.");
			
			if (result != null) {
				assertEquals(quaternionRefConjugateA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(quaternionRefConjugateB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(quaternionRefConjugateC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(quaternionRefConjugateD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
			}
		}
	}
	
	/**
	 * Test {@link Quaternion#conjugateQuaternionAffect()} method.
	 */
	@Test
	public void conjugateAffectTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(quaternionRefA, quaternionRefB, quaternionRefC, quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}

		assertNotNull(q, "Created quaternion q is null");
		
		if (q != null) {
			
			Quaternion resultRef = null;
			
			try {
				resultRef = q.conjugateQuaternionAffect();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull(resultRef, "Resulting quaternion is null");
			assertSame(q, resultRef, "Result references differs.");
			
			if (resultRef != null) {
				assertEquals(quaternionRefConjugateA, resultRef.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(quaternionRefConjugateB, resultRef.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(quaternionRefConjugateC, resultRef.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(quaternionRefConjugateD, resultRef.getK(), 0.0d, "Invalid k base (d) parameter.");
			}
		}
	}
}
