package org.jeometry.test.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.math.Matrix;
import org.jeometry.math.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * A test suite for the {@link Vector} implementation.<br>
 * <br>
 * <b>Usage:</b>
 * <p>
 * Create a class that extends this one and add the method:<br><br>
 * <code>
 * {@literal @}BeforeClass<br>
 * public static void initClass() {<br>
 * &nbsp;&nbsp;vectorClass = [the vector objects class];<br>
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
public class VectorTest {

	/**
	 * The class that the vector objects have to respect.
	 */
	protected static Class<? extends Vector> vectorClass = null;

	/**
	 * Initialize the test static context.
	 */
	@BeforeAll
	public static void initClass() {
		fail("Test class is not initialized. method init() has to be implemented");
	}

	/**
	 * Test method {@link Vector#getDimension()}
	 */
	@Test
	public void getDimensionTest() {
		Vector v = JeometryFactory.createVector(10);

		assertEquals(v.getDimension(), 10, "Invalid vector dimension.");
	}

	/**
	 * Testing {@link Vector#plus(Vector)}
	 */
	@Test
	public void plusTest() {
		
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(a, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = a.plus(b);
		
		assertNotNull(result, "Null result vector");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]+bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
	}

	/**
	 * Testing {@link Vector#plus(Vector, Vector)}
	 */
	@Test
	public void plusResultTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(a, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = JeometryFactory.createVector(a.getDimension());
		
		assertNotNull(result, "Cannot create result vector");
		
		Vector reference = a.plus(b, result);
		
		assertNotNull(reference, "Null result reference vector");
		assertSame(result, reference, "Result and returned reference vectors are not the same");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]+bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
		
	}

	/**
	 * Testing {@link Vector#plusAffect(Vector)}
	 */
	@Test
	public void plusAffectTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(a, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = a.plusAffect(b);
		
		assertNotNull(result, "Null result vector");
		
		assertSame(a, result, "Result vector is not this");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]+bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
	}

	/**
	 * Testing {@link Vector#plus(double)}
	 */
	@Test
	public void plusScalarTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a = JeometryFactory.createVector(aData);
		
		assertNotNull(a, "Null a vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector result = a.plus(scalar);
		
		assertNotNull(result, "Null result vector");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]+scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing {@link Vector#plus(double, Vector)}
	 */
	@Test
	public void plusScalarResultTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a      = JeometryFactory.createVector(aData);
		Vector result = JeometryFactory.createVector(aData.length);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(result, "Null result vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, result.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector resultRef = a.plus(scalar, result);
		
		assertNotNull(resultRef, "Null result vector");
		assertSame(result, resultRef, "Result and returned reference are not the same.");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]+scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing {@link Vector#plusAffect(double)}
	 */
	@Test
	public void plusScalarAffectTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a      = JeometryFactory.createVector(aData);
		
		assertNotNull(a, "Null a vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector resultRef = a.plusAffect(scalar);
		
		assertNotNull(resultRef, "Null result vector");
		assertSame(a, resultRef, "Result and returned reference are not the same.");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(a.getValue(i), aData[i]+scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing {@link Vector#minus(Vector)}
	 */
	@Test
	public void minusTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(a, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = a.minus(b);
		
		assertNotNull(result, "Null result vector");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]-bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
	}

	/**
	 * Testing {@link Vector#minus(Vector, Vector)}
	 */
	@Test
	public void minusResultTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(a, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(),"Vector a and b dimensions are incompatibles.");
		
		Vector result = JeometryFactory.createVector(a.getDimension());
		
		assertNotNull(result, "Cannot create result vector");
		
		Vector reference = a.minus(b, result);
		
		assertNotNull(reference, "Null result reference vector");
		assertSame(result, reference, "Result and returned reference vectors are not the same");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]-bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
		
	}

	/**
	 * Testing {@link Vector#minusAffect(Vector)}
	 */
	@Test
	public void minusAffectTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(a, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = a.minusAffect(b);
		
		assertNotNull(result, "Null result vector");
		
		assertSame(a, result, "Result vector is not this");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]-bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
	}

	/**
	 * Testing {@link Vector#minus(double)}
	 */
	@Test
	public void minusScalarTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a = JeometryFactory.createVector(aData);
		
		assertNotNull(a, "Null a vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector result = a.minus(scalar);
		
		assertNotNull(result, "Null result vector");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]-scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing {@link Vector#minus(double, Vector)}
	 */
	@Test
	public void minusScalarResultTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a      = JeometryFactory.createVector(aData);
		Vector result = JeometryFactory.createVector(aData.length);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(result, "Null result vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, result.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector resultRef = a.minus(scalar, result);
		
		assertNotNull(resultRef, "Null result vector");
		assertSame(result, resultRef, "Result and returned reference are not the same.");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]-scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing {@link Vector#minusAffect(double)}
	 */
	@Test
	public void minusScalarAffectTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a      = JeometryFactory.createVector(aData);
		
		assertNotNull(a, "Null a vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector resultRef = a.minusAffect(scalar);
		
		assertNotNull(resultRef, "Null result vector");
		assertSame(a, resultRef, "Result and returned reference are not the same.");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(a.getValue(i), aData[i]-scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing {@link Vector#multiply(Vector)}
	 */
	@Test
	public void multiplyVectorTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(a, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = a.multiply(b);
		
		assertNotNull(result, "Null result vector");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]*bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
	}

	/**
	 * Testing {@link Vector#multiply(Vector, Vector)}
	 */
	@Test
	public void multiplyVectorResultTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(b, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = JeometryFactory.createVector(a.getDimension());
		
		assertNotNull(result, "Cannot create result vector");
		
		Vector reference = a.multiply(b, result);
		
		assertNotNull(reference, "Null result reference vector");
		assertSame(result, reference, "Result and returned reference vectors are not the same");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]*bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
		
	}

	/**
	 * Testing {@link Vector#multiplyAffect(Vector)}
	 */
	@Test
	public void multiplyAffectVectorTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(b, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = a.multiplyAffect(b);
		
		assertNotNull(result, "Null result vector");
		
		assertSame(a, result, "Result vector is not this");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]*bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
	}

	/**
	 * Testing {@link Vector#divide(Vector)}
	 */
	@Test
	public void divideVectorTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(b, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = a.divide(b);
		
		assertNotNull( result, "Null result vector");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]/bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
	}

	/**
	 * Testing {@link Vector#divide(Vector, Vector)}
	 */
	@Test
	public void divideVectorResultTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(b, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = JeometryFactory.createVector(a.getDimension());
		
		assertNotNull(result, "Cannot create result vector");
		
		Vector reference = a.divide(b, result);
		
		assertNotNull(reference, "Null result reference vector");
		assertSame(result, reference, "Result and returned reference vectors are not the same");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]/bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
		
	}

	/**
	 * Testing {@link Vector#divideAffect(Vector)}
	 */
	@Test
	public void divideAffectVectorTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(b, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		Vector result = a.divideAffect(b);
		
		assertNotNull(result, "Null result vector");
		
		assertSame(a, result, "Result vector is not this");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]/bData[i], Double.MIN_VALUE, "Invalid result component "+i);
		}
	}

	/**
	 * Testing {@link Vector#divide(double)}
	 */
	@Test
	public void divideScalarTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a = JeometryFactory.createVector(aData);
		
		assertNotNull(a, "Null a vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector result = a.divide(scalar);
		
		assertNotNull(result, "Null result vector");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]/scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing {@link Vector#divide(double, Vector)}
	 */
	@Test
	public void divideScalarResultTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a      = JeometryFactory.createVector(aData);
		Vector result = JeometryFactory.createVector(aData.length);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(result, "Null result vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, result.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector resultRef = a.divide(scalar, result);
		
		assertNotNull(resultRef, "Null result vector");
		assertSame(result, resultRef, "Result and returned reference are not the same.");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]/scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing {@link Vector#multiplyAffect(double)}
	 */
	@Test
	public void divideScalarAffectTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a      = JeometryFactory.createVector(aData);
		
		assertNotNull(a, "Null a vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector resultRef = a.divideAffect(scalar);
		
		assertNotNull(resultRef, "Null result vector");
		assertSame(a, resultRef, "Result and returned reference are not the same.");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(a.getValue(i), aData[i]/scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing method {@link Vector#dot(Vector)}
	 */
	@Test
	public void dotTest() {
		double[] aData = MathTestData.V_4_A;
		double[] bData = MathTestData.V_4_B;

		Vector a = JeometryFactory.createVector(aData);
		Vector b = JeometryFactory.createVector(bData);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(b, "Null b vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, b.getClass(), "Unexpected b vector class "+b.getClass().getSimpleName());
		}
		
		assertEquals(a.getDimension(), b.getDimension(), "Vector a and b dimensions are incompatibles.");
		
		double result = a.dot(b);
		
		double expectedResult = 0.0d;
		for(int i = 0; i < aData.length; i++) {
			expectedResult = expectedResult + aData[i]*bData[i];
		}
		
		assertEquals(result, expectedResult, Double.MIN_VALUE, "Invalid dot product.");
	}
	
	/**
	 * Test method {@link Vector#getValue(int)}
	 */
	@Test
	public void getVectorComponentTest() {

		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		for(int i = 0; i < v.getDimension(); i++) {
			assertEquals(v.getValue(i), MathTestData.V_4_A[i], Double.MIN_VALUE, "Invalid vector component "+i);
		}
	}

	/**
	 * Test method {@link Vector#setValue(int, double)}
	 */
	@Test
	public void setVectorComponentTest() {
		Vector v = JeometryFactory.createVector(4);

		for(int dimension = 0; dimension < MathTestData.V_4_A.length; dimension++) {
			v.setValue(dimension, MathTestData.V_4_A[dimension]);
		}

		for(int dimension = 0; dimension < MathTestData.V_4_A.length; dimension++) {
			assertEquals(MathTestData.V_4_A[dimension], v.getValue(dimension), Double.MIN_VALUE, "Invalid component "+dimension+" value.");
		}
	}

	/**
	 * Test method {@link Vector#setValues(Vector)}
	 */
	@Test
	public void setComponentsVectorTest() {
		Vector v = JeometryFactory.createVector(4);

		Vector u = JeometryFactory.createVector(MathTestData.V_4_A);

		v.setValues(u);

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals(MathTestData.V_4_A[dimension], v.getValue(dimension), Double.MIN_VALUE, "Invalid component "+dimension+" value.");
		}
	}

	/**
	 * Test method {@link Vector#getValues()}
	 */
	@Test
	public void getComponentsTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		double[] components = v.getValues();

		assertNotNull(components, "Null components");

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals(MathTestData.V_4_A[dimension], components[dimension], Double.MIN_VALUE, "Invalid component "+dimension+" value.");
		}
	}

	/**
	 * Test method {@link Vector#getValues(double[])}
	 */
	@Test
	public void getComponentsResultTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		double[] components = new double[MathTestData.V_4_A.length];

		double[] result = v.getValues(components);

		assertNotNull(result, "Null components");

		assertSame(components, result, "Result is not the same as the parameter");

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals(MathTestData.V_4_A[dimension], components[dimension], Double.MIN_VALUE, "Invalid component "+dimension+" value.");
		}
	}

	/**
	 * Test {@link Vector#setValues(double[])}
	 */
	@Test
	public void setComponentsArrayTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A.length);

		v.setValues(MathTestData.V_4_A);

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals(MathTestData.V_4_A[dimension], v.getValue(dimension), Double.MIN_VALUE, "Invalid component "+dimension+" value.");
		}

	}


	/**
	 * Testing {@link Vector#setValues(double)}
	 */
	@Test
	public void setValuesDoubleTest() {

		Vector v = JeometryFactory.createVector(MathTestData.V_4_A.length);
		
		double value = Math.PI;
		
		assertNotNull(v, "Null vector.");
		
		v.setValues(value);
		
		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals(value, v.getValue(dimension), Double.MIN_VALUE, "Invalid component "+dimension+" value.");
		}
	}

	/**
	 * Testing {@link Vector#setValues(Matrix)}
	 */
	@Test
	public void setValuesMatrixTest() {

		double[] data = MathTestData.V_4_A;
		
		Matrix row    = JeometryFactory.createMatrix(1, data.length, data, Matrix.ROW_MAJOR);
		Matrix column = JeometryFactory.createMatrix(data.length, 1, data, Matrix.COLUMN_MAJOR);
		
		assertNotNull(row, "Null row matrix.");
		assertEquals(1, row.getRowsCount(), "Invalid row matrix rows count "+row.getRowsCount()+", expected "+1);
		assertEquals(data.length, row.getColumnsCount(), "Invalid row matrix columns count "+row.getColumnsCount()+", expected "+data.length);
		
		assertNotNull(column, "Null column matrix.");
		assertEquals(data.length, column.getRowsCount(), "Invalid row matrix rows count "+column.getRowsCount()+", expected "+data.length);
		assertEquals(1, column.getColumnsCount(), "Invalid row matrix columns count "+column.getColumnsCount()+", expected "+1);

		Vector v = JeometryFactory.createVector(data.length);		
		v.setValues(column);
		
		assertNotNull(v, "Null row matrix.");
		assertEquals(data.length, v.getDimension(), "Invalid vector dimension "+v.getDimension()+", expected "+data.length);
		
		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals(row.getValue(0, dimension), v.getValue(dimension), "Invalid vector dimension "+dimension+" value, expected "+row.getValue(0, dimension));
			
		}
		
		Vector u = JeometryFactory.createVector(data.length);		
		u.setValues(row);
		
		assertNotNull(u, "Null row matrix.");
		assertEquals(data.length, v.getDimension(), "Invalid vector dimension "+u.getDimension()+", expected "+data.length);
		
		for(int dimension = 0; dimension < u.getDimension(); dimension++) {
			assertEquals(column.getValue(dimension, 0), u.getValue(dimension), "Invalid vector dimension "+dimension+" value, expected "+column.getValue(dimension, 0));
			
		}
	}

	/**
	 * Test {@link Vector#extract(int, int)}
	 */
	@Test
	public void extractTest() {

		int start = 0;
		int length = 4;

		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		Vector extracted = v.extract(start, length);

		assertNotNull(extracted, "Extraction is null.");

		assertEquals(length, extracted.getDimension(), "Invalid extraction dimension.");

		for(int dimension = 0; dimension < extracted.getDimension(); dimension++) {
			assertEquals(v.getValue(dimension+start), extracted.getValue(dimension), Double.MIN_VALUE, "Invalid extraction component.");
		}

		start = 1;
		length = 2;
		extracted = v.extract(start, length);

		assertNotNull(extracted, "Extraction is null.");

		assertEquals(length, extracted.getDimension(), "Invalid extraction dimension.");

		for(int dimension = 0; dimension < extracted.getDimension(); dimension++) {
			assertEquals(v.getValue(dimension+start), extracted.getValue(dimension), Double.MIN_VALUE, "Invalid extraction component.");
		}

		start = v.getDimension() - 1;
		length = 1;
		extracted = v.extract(start, length);

		assertNotNull(extracted, "Extraction is null.");

		assertEquals(length, extracted.getDimension(), "Invalid extraction dimension.");

		for(int dimension = 0; dimension < extracted.getDimension(); dimension++) {
			assertEquals(v.getValue(dimension+start), extracted.getValue(dimension), Double.MIN_VALUE, "Invalid extraction component.");
		}
	}

	/**
	 * Test {@link Vector#normSquare()}
	 */
	@Test
	public void normSquareTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		assertEquals(MathTestData.V_4_A_NORM2, v.normSquare(), Double.MIN_VALUE, "Invalid norm square.");
	}

	/**
	 * Test {@link Vector#norm()}
	 */
	@Test
	public void normTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);
		assertEquals(MathTestData.V_4_A_NORM, v.norm(), Double.MIN_VALUE, "Invalid norm square.");  
	}

	/**
	 * Test {@link Vector#normalize()}
	 */
	@Test
	public void normalizeTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		v.normalize();

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals(MathTestData.V_4_A_NORMALIZED[dimension], v.getValue(dimension), Double.MIN_VALUE, "Invalid component "+dimension+" value.");
		}
	}

	/**
	 * Test {@link Vector#orthogonal()}
	 */
	@Test
	public void orthogonalTest() {
		//TODO Implements VectorTest.orthogonal()
	}

	/**
	 * Test  {@link Vector#orthogonal(Vector)}
	 */
	@Test
	public void orthogonalResultTest() {
		//TODO Implements VectorTest.orthogonalResult()
	}

	/**
	 * Test {@link Vector#multiply(double)}
	 */
	@Test
	public void multiplyTest() {

		double factor = 2.0d;

		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		Vector result = v.multiply(factor);

		assertNotNull(result, "Null result");

		assertEquals(MathTestData.V_4_A.length, result.getDimension(), "Invalid result size");

		for(int dimension = 0; dimension < result.getDimension(); dimension++) {
			assertEquals(MathTestData.V_4_A[dimension]*factor, result.getValue(dimension), Double.MIN_VALUE, "Invalid component "+dimension+" value.");
		}

	}

	/**
	 * Test {@link Vector#multiply(double, Vector)}
	 */
	@Test
	public void multiplyResultTest() {
		double factor = 2.0d;

		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		Vector parameter = JeometryFactory.createVector(MathTestData.V_4_A.length);

		Vector result = v.multiply(factor, parameter);

		assertNotNull(result, "Null result");
		assertSame(parameter, result, "Result and parameter differs");

		assertEquals(MathTestData.V_4_A.length, result.getDimension(), "Invalid result size");

		for(int dimension = 0; dimension < result.getDimension(); dimension++) {
			assertEquals(MathTestData.V_4_A[dimension]*factor, result.getValue(dimension), Double.MIN_VALUE, "Invalid component "+dimension+" value.");
		}
	}

	/**
	 * Test {@link Vector#multiplyAffect(double)}
	 */
	@Test
	public void multiplyAffect() {

		double factor = 2.0d;

		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		v.multiplyAffect(factor);

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals(MathTestData.V_4_A[dimension]*factor, v.getValue(dimension), Double.MIN_VALUE, "Invalid component "+dimension+" value.");
		}
	}
	
	/**
	 * Testing {@link Vector#multiply(double)}
	 */
	@Test
	public void multiplyScalarTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a = JeometryFactory.createVector(aData);
		
		assertNotNull(a, "Null a vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector result = a.multiply(scalar);
		
		assertNotNull(result, "Null result vector");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]*scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing {@link Vector#multiply(double, Vector)}
	 */
	@Test
	public void multiplyScalarResultTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a      = JeometryFactory.createVector(aData);
		Vector result = JeometryFactory.createVector(aData.length);
		
		assertNotNull(a, "Null a vector");
		assertNotNull(result, "Null result vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
			assertEquals(vectorClass, result.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector resultRef = a.multiply(scalar, result);
		
		assertNotNull(resultRef, "Null result vector");
		assertSame(result, resultRef, "Result and returned reference are not the same.");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(result.getValue(i), aData[i]*scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
	/**
	 * Testing {@link Vector#multiplyAffect(double)}
	 */
	@Test
	public void multiplyScalarAffectTest() {
		
		double[] aData = MathTestData.V_4_A;
		double scalar = 0.3651;

		Vector a      = JeometryFactory.createVector(aData);
		
		assertNotNull(a, "Null a vector");
		
		if (vectorClass != null) {
			assertEquals(vectorClass, a.getClass(), "Unexpected a vector class "+a.getClass().getSimpleName());
		}
		
		Vector resultRef = a.multiplyAffect(scalar);
		
		assertNotNull(resultRef, "Null result vector");
		assertSame(a, resultRef, "Result and returned reference are not the same.");
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals(a.getValue(i), aData[i]*scalar, Double.MIN_VALUE, "Invalid result component "+i);
		}
	}
	
}
