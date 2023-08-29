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
	
	/**
	 * Reference value.
	 */
	double quaternionRefA = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionRefB = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionRefC = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionRefD = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double[] quaternionRefComponents = null;
	
	/**
	 * Reference value.
	 */
	Quaternion quaternionRef = null;
	
	/**
	 * Reference value.
	 */
	double quaternionRefNormSquare = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionRefNorm       = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionTargetA = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionTargetB = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionTargetC = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionTargetD = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double[] quaternionTargetComponents = null;
	
	/**
	 * Reference value.
	 */
	Quaternion quaternionTarget = null;
	
	/**
	 * Reference value.
	 */
	double quaternionMultA = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionMultB = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionMultC = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionMultD = Double.NaN;
	
	/**
	 * Reference value.
	 */
	Quaternion quaternionMultResult = null;
	
	/**
	 * Reference value.
	 */
	double quaternionRefInvertedA = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionRefInvertedB = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionRefInvertedC = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionRefInvertedD = Double.NaN;
	
	/**
	 * Reference value.
	 */
	Quaternion quaternionRefInvertedResult = null;
	
	/**
	 * Reference value.
	 */
	double quaternionRefConjugateA = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionRefConjugateB = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionRefConjugateC = Double.NaN;
	
	/**
	 * Reference value.
	 */
	double quaternionRefConjugateD = Double.NaN;
	
	/**
	 * Reference value.
	 */
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
		this.quaternionRefA =  2.12469239d;
		this.quaternionRefB = -1.76893983d;
		this.quaternionRefC = 98.12472855d;
		this.quaternionRefD = -2.11445671d;
		
		this.quaternionRefComponents = new double[] {this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD};
		
		this.quaternionRef = JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		
		this.quaternionRefNormSquare = this.quaternionRefA*this.quaternionRefA + this.quaternionRefB*this.quaternionRefB + this.quaternionRefC*this.quaternionRefC + this.quaternionRefD*this.quaternionRefD;
		this.quaternionRefNorm       = Math.sqrt(this.quaternionRefNormSquare);
		
		this.quaternionTargetA =  36.58797462d;
		this.quaternionTargetB =   0.00768983d;
		this.quaternionTargetC = -44.12672855d;
		this.quaternionTargetD =   8.25697125d;
		
		this.quaternionTargetComponents = new double[] {this.quaternionTargetA, this.quaternionTargetB, this.quaternionTargetC, this.quaternionTargetD};
		
		this.quaternionTarget =  JeometryFactory.createQuaternion(this.quaternionTargetA, this.quaternionTargetB, this.quaternionTargetC, this.quaternionTargetD);
		
		this.quaternionMultA = this.quaternionRefA * this.quaternionTargetA - this.quaternionRefB * this.quaternionTargetB - this.quaternionRefC * this.quaternionTargetC - this.quaternionRefD * this.quaternionTargetD;
		this.quaternionMultB = this.quaternionRefA * this.quaternionTargetB + this.quaternionRefB * this.quaternionTargetA + this.quaternionRefC * this.quaternionTargetD - this.quaternionRefD * this.quaternionTargetC;
		this.quaternionMultC = this.quaternionRefA * this.quaternionTargetC - this.quaternionRefB * this.quaternionTargetD + this.quaternionRefC * this.quaternionTargetA + this.quaternionRefD * this.quaternionTargetB;
		this.quaternionMultD = this.quaternionRefA * this.quaternionTargetD + this.quaternionRefB * this.quaternionTargetC - this.quaternionRefC * this.quaternionTargetB + this.quaternionRefD * this.quaternionTargetA;

		this.quaternionMultResult =  JeometryFactory.createQuaternion(this.quaternionMultA, this.quaternionMultB, this.quaternionMultC, this.quaternionMultD);	
		
		
		this.quaternionRefInvertedA = this.quaternionRefA / this.quaternionRefNormSquare;
		this.quaternionRefInvertedB = -1.0d * this.quaternionRefB / this.quaternionRefNormSquare;
		this.quaternionRefInvertedC = -1.0d * this.quaternionRefC / this.quaternionRefNormSquare;
		this.quaternionRefInvertedD = -1.0d * this.quaternionRefD / this.quaternionRefNormSquare;
		
		this.quaternionRefInvertedResult =  JeometryFactory.createQuaternion(this.quaternionRefInvertedA, this.quaternionRefInvertedB, this.quaternionRefInvertedC, this.quaternionRefInvertedD);	
		
		this.quaternionRefConjugateA = this.quaternionRefA;
		this.quaternionRefConjugateB = -1.0d*this.quaternionRefB;
		this.quaternionRefConjugateC = -1.0d*this.quaternionRefC;
		this.quaternionRefConjugateD = -1.0d*this.quaternionRefD;
		
		this.quaternionRefConjugateResult =  JeometryFactory.createQuaternion(this.quaternionRefConjugateA, this.quaternionRefConjugateB, this.quaternionRefConjugateC, this.quaternionRefConjugateD);	
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		assertEquals(this.quaternionRefA, q.getScalar(), 0.0d, "Invalid scalar (a) value.");
		assertEquals(this.quaternionRefB, q.getI(), 0.0d, "Invalid i (b) value.");
		assertEquals(this.quaternionRefC, q.getJ(), 0.0d, "Invalid j (c) value.");
		assertEquals(this.quaternionRefD, q.getK(), 0.0d, "Invalid k (d) value.");
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
		
		q.setComponents(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		
		assertEquals(this.quaternionRefA, q.getScalar(), 0.0d, "Invalid scalar value");
		assertEquals(this.quaternionRefB, q.getI(), 0.0d, "Invalid i value");
		assertEquals(this.quaternionRefC, q.getJ(), 0.0d, "Invalid j value");
		assertEquals(this.quaternionRefD, q.getK(), 0.0d, "Invalid k value");
	}
	
	/**
	 * Test the {@link Quaternion#getValues()} method.
	 */
	@Test
	public void getVectorComponentsTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals(this.quaternionRefA, q.getScalar(), 0.0d, "Invalid scalar parameter");
		
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals(this.quaternionRefB, q.getI(), 0.0d, "Invalid i parameter");
		
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals(this.quaternionRefC, q.getJ(), 0.0d, "Invalid j parameter");
		
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertEquals(this.quaternionRefD, q.getK(), 0.0d, "Invalid k parameter");
		
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		
		if (q != null) {
			assertEquals(this.quaternionRefNormSquare, q.normSquare(), 0.0d, "Invalid norm square value.");
		}
	}
	
	/**
	 * Test {@link Quaternion#norm()} method.
	 */
	@Test
	public void normTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		
		if (q != null) {
			assertEquals(this.quaternionRefNorm, q.norm(), 0.0d, "Invalid norm square value.");
		}
	}
	
	/**
	 * Test {@link Quaternion#mult(Quaternion)} method.
	 */
	@Test
	public void multTest() {
		Quaternion q = null;
		try {
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		
		if (q != null) {
			
			Quaternion result = null;
			try {
				result = q.mult(this.quaternionTarget);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}
			
			assertNotNull(result, "Resulting quaternion is null");
			
			if (result != null) {
				assertEquals(this.quaternionMultA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(this.quaternionMultB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(this.quaternionMultC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(this.quaternionMultD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
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
				resultRef = q.mult(this.quaternionTarget, result);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}

			assertNotNull(result, "Resulting quaternion is null");
			assertNotNull(resultRef, "Resulting quaternion ref is null");
			
			assertSame(result, resultRef, "Result references differs.");
			
			if (result != null) {
				assertEquals(this.quaternionMultA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(this.quaternionMultB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(this.quaternionMultC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(this.quaternionMultD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		
		assertNotNull(q, "Created quaternion is null");
		
		if (q != null) {
			
			Quaternion result = null;
			try {
				result = q.multAffect(this.quaternionTarget);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}
			
			assertNotNull(result, "Resulting quaternion is null");
			assertSame(result, q, "Resulting quaternion reference is invalid.");
			
			if (result != null) {
				assertEquals(this.quaternionMultA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(this.quaternionMultB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(this.quaternionMultC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(this.quaternionMultD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
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
				assertEquals(this.quaternionRefInvertedA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(this.quaternionRefInvertedB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(this.quaternionRefInvertedC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(this.quaternionRefInvertedD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
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
				assertEquals(this.quaternionRefInvertedA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(this.quaternionRefInvertedB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(this.quaternionRefInvertedC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(this.quaternionRefInvertedD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
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
				assertEquals(this.quaternionRefInvertedA, resultRef.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(this.quaternionRefInvertedB, resultRef.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(this.quaternionRefInvertedC, resultRef.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(this.quaternionRefInvertedD, resultRef.getK(), 0.0d, "Invalid k base (d) parameter.");
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
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
				assertEquals(this.quaternionRefConjugateA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(this.quaternionRefConjugateB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(this.quaternionRefConjugateC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(this.quaternionRefConjugateD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
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
				assertEquals(this.quaternionRefConjugateA, result.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(this.quaternionRefConjugateB, result.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(this.quaternionRefConjugateC, result.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(this.quaternionRefConjugateD, result.getK(), 0.0d, "Invalid k base (d) parameter.");
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
			q =  JeometryFactory.createQuaternion(this.quaternionRefA, this.quaternionRefB, this.quaternionRefC, this.quaternionRefD);
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
				assertEquals(this.quaternionRefConjugateA, resultRef.getScalar(), 0.0d, "Invalid scalar (a) parameter.");
				assertEquals(this.quaternionRefConjugateB, resultRef.getI(), 0.0d, "Invalid i base (b) parameter.");
				assertEquals(this.quaternionRefConjugateC, resultRef.getJ(), 0.0d, "Invalid j base (c) parameter.");
				assertEquals(this.quaternionRefConjugateD, resultRef.getK(), 0.0d, "Invalid k base (d) parameter.");
			}
		}
	}
}
