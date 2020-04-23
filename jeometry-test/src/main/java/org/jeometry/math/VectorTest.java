package org.jeometry.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.junit.BeforeClass;
import org.junit.Test;

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
	@BeforeClass
	public static void initClass() {
		fail("Test class is not initialized. method init() has to be implemented");
	}

	/**
	 * Test method {@link Vector#getDimension()}
	 */
	@Test
	public void getDimensionTest() {
		Vector v = JeometryFactory.createVector(10);

		assertEquals("Invalid vector dimension.", v.getDimension(), 10);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = a.plus(b);
		
		assertNotNull("Null result vector", result);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]+bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = JeometryFactory.createVector(a.getDimension());
		
		assertNotNull("Cannot create result vector", result);
		
		Vector reference = a.plus(b, result);
		
		assertNotNull("Null result reference vector", reference);
		assertSame("Result and returned reference vectors are not the same", result, reference);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]+bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = a.plusAffect(b);
		
		assertNotNull("Null result vector", result);
		
		assertSame("Result vector is not this", a, result);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]+bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = a.minus(b);
		
		assertNotNull("Null result vector", result);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]-bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = JeometryFactory.createVector(a.getDimension());
		
		assertNotNull("Cannot create result vector", result);
		
		Vector reference = a.minus(b, result);
		
		assertNotNull("Null result reference vector", reference);
		assertSame("Result and returned reference vectors are not the same", result, reference);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]-bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = a.minusAffect(b);
		
		assertNotNull("Null result vector", result);
		
		assertSame("Result vector is not this", a, result);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]-bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = a.multiply(b);
		
		assertNotNull("Null result vector", result);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]*bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = JeometryFactory.createVector(a.getDimension());
		
		assertNotNull("Cannot create result vector", result);
		
		Vector reference = a.multiply(b, result);
		
		assertNotNull("Null result reference vector", reference);
		assertSame("Result and returned reference vectors are not the same", result, reference);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]*bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = a.multiplyAffect(b);
		
		assertNotNull("Null result vector", result);
		
		assertSame("Result vector is not this", a, result);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]*bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = a.divide(b);
		
		assertNotNull("Null result vector", result);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]/bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = JeometryFactory.createVector(a.getDimension());
		
		assertNotNull("Cannot create result vector", result);
		
		Vector reference = a.divide(b, result);
		
		assertNotNull("Null result reference vector", reference);
		assertSame("Result and returned reference vectors are not the same", result, reference);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]/bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		Vector result = a.divideAffect(b);
		
		assertNotNull("Null result vector", result);
		
		assertSame("Result vector is not this", a, result);
		
		for(int i = 0; i < aData.length; i++) {
			assertEquals("Invalid result component "+i, result.getVectorComponent(i), aData[i]/bData[i], Double.MIN_VALUE);
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
		
		assertNotNull("Null a vector", a);
		assertNotNull("Null b vector", a);
		
		if (vectorClass != null) {
			assertEquals("Unexpected a vector class "+a.getClass().getSimpleName(), vectorClass, a.getClass());
			assertEquals("Unexpected b vector class "+b.getClass().getSimpleName(), vectorClass, b.getClass());
		}
		
		assertEquals("Vector a and b dimensions are incompatibles.", a.getDimension(), b.getDimension());
		
		double result = a.dot(b);
		
		double expectedResult = 0.0d;
		for(int i = 0; i < aData.length; i++) {
			expectedResult = expectedResult + aData[i]*bData[i];
		}
		
		assertEquals("Invalid dot product.", result, expectedResult, Double.MIN_VALUE);
	}
	
	/**
	 * Test method {@link Vector#getVectorComponent(int)}
	 */
	@Test
	public void getVectorComponentTest() {

		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		for(int i = 0; i < v.getDimension(); i++) {
			assertEquals("Invalid vector component "+i, v.getVectorComponent(i), MathTestData.V_4_A[i], Double.MIN_VALUE);
		}
	}

	/**
	 * Test method {@link Vector#setVectorComponent(int, double)}
	 */
	@Test
	public void setVectorComponentTest() {
		Vector v = JeometryFactory.createVector(4);

		for(int dimension = 0; dimension < MathTestData.V_4_A.length; dimension++) {
			v.setVectorComponent(dimension, MathTestData.V_4_A[dimension]);
		}

		for(int dimension = 0; dimension < MathTestData.V_4_A.length; dimension++) {
			assertEquals("Invalid component "+dimension+" value.", MathTestData.V_4_A[dimension], v.getVectorComponent(dimension), Double.MIN_VALUE);
		}
	}

	/**
	 * Test method {@link Vector#setComponents(Vector)}
	 */
	@Test
	public void setComponentsVectorTest() {
		Vector v = JeometryFactory.createVector(4);

		Vector u = JeometryFactory.createVector(MathTestData.V_4_A);

		v.setComponents(u);

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals("Invalid component "+dimension+" value.", MathTestData.V_4_A[dimension], v.getVectorComponent(dimension), Double.MIN_VALUE);
		}
	}

	/**
	 * Test method {@link Vector#getComponents()}
	 */
	@Test
	public void getComponentsTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		double[] components = v.getComponents();

		assertNotNull("Null components", components);

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals("Invalid component "+dimension+" value.", MathTestData.V_4_A[dimension], components[dimension], Double.MIN_VALUE);
		}
	}

	/**
	 * Test method {@link Vector#getComponents(double[])}
	 */
	@Test
	public void getComponentsResultTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		double[] components = new double[MathTestData.V_4_A.length];

		double[] result = v.getComponents(components);

		assertNotNull("Null components", result);

		assertSame("Result is not the same as the parameter", components, result);

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals("Invalid component "+dimension+" value.", MathTestData.V_4_A[dimension], components[dimension], Double.MIN_VALUE);
		}
	}

	/**
	 * Test {@link Vector#setComponents(double[])}
	 */
	@Test
	public void setComponentsArrayTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A.length);

		v.setComponents(MathTestData.V_4_A);

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals("Invalid component "+dimension+" value.", MathTestData.V_4_A[dimension], v.getVectorComponent(dimension), Double.MIN_VALUE);
		}

	}


	/**
	 * Testing {@link Vector#setComponents(double)}
	 */
	@Test
	public void setComponentsSingleDoubleTest() {
      // TODO Implements VectorTest.setComponentsSingleDoubleTest()
	}

	/**
	 * Testing {@link Vector#setComponents(Matrix)}
	 */
	@Test
	public void setComponentsMatrixTest() {
		// TODO Implements VectorTest.setComponentsMatrixTest()
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

		assertNotNull("Extraction is null.", extracted);

		assertEquals("Invalid extraction dimension.", length, extracted.getDimension());

		for(int dimension = 0; dimension < extracted.getDimension(); dimension++) {
			assertEquals("Invalid extraction component.", v.getVectorComponent(dimension+start), extracted.getVectorComponent(dimension), Double.MIN_VALUE);
		}

		start = 1;
		length = 2;
		extracted = v.extract(start, length);

		assertNotNull("Extraction is null.", extracted);

		assertEquals("Invalid extraction dimension.", length, extracted.getDimension());

		for(int dimension = 0; dimension < extracted.getDimension(); dimension++) {
			assertEquals("Invalid extraction component.", v.getVectorComponent(dimension+start), extracted.getVectorComponent(dimension), Double.MIN_VALUE);
		}

		start = v.getDimension() - 1;
		length = 1;
		extracted = v.extract(start, length);

		assertNotNull("Extraction is null.", extracted);

		assertEquals("Invalid extraction dimension.", length, extracted.getDimension());

		for(int dimension = 0; dimension < extracted.getDimension(); dimension++) {
			assertEquals("Invalid extraction component.", v.getVectorComponent(dimension+start), extracted.getVectorComponent(dimension), Double.MIN_VALUE);
		}
	}

	/**
	 * Test {@link Vector#normSquare()}
	 */
	@Test
	public void normSquareTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		assertEquals("Invalid norm square.", MathTestData.V_4_A_NORM2, v.normSquare(), Double.MIN_VALUE);
	}

	/**
	 * Test {@link Vector#norm()}
	 */
	@Test
	public void normTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);
		assertEquals("Invalid norm square.", MathTestData.V_4_A_NORM, v.norm(), Double.MIN_VALUE);  
	}

	/**
	 * Test {@link Vector#normalize()}
	 */
	@Test
	public void normalizeTest() {
		Vector v = JeometryFactory.createVector(MathTestData.V_4_A);

		v.normalize();

		for(int dimension = 0; dimension < v.getDimension(); dimension++) {
			assertEquals("Invalid component "+dimension+" value.", MathTestData.V_4_A_NORMALIZED[dimension], v.getVectorComponent(dimension), Double.MIN_VALUE);
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

		assertNotNull("Null result", result);

		assertEquals("Invalid result size", MathTestData.V_4_A.length, result.getDimension());

		for(int dimension = 0; dimension < result.getDimension(); dimension++) {
			assertEquals("Invalid component "+dimension+" value.", MathTestData.V_4_A[dimension]*factor, result.getVectorComponent(dimension), Double.MIN_VALUE);
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

		assertNotNull("Null result", result);
		assertSame("Result and parameter differs", parameter, result);

		assertEquals("Invalid result size", MathTestData.V_4_A.length, result.getDimension());

		for(int dimension = 0; dimension < result.getDimension(); dimension++) {
			assertEquals("Invalid component "+dimension+" value.", MathTestData.V_4_A[dimension]*factor, result.getVectorComponent(dimension), Double.MIN_VALUE);
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
			assertEquals("Invalid component "+dimension+" value.", MathTestData.V_4_A[dimension]*factor, v.getVectorComponent(dimension), Double.MIN_VALUE);
		}
	}
}
