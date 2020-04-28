package org.jeometry.test.geom3D.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.point.Point3D;
import org.jeometry.geom3D.transform.Transform3D;

/**
 * A set of data dedicated to 3D transform functionalities test<br>
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.2
 *
 */
public class Transform3DTest {

	/**
	 * Test the method {@link Transform3D#transform(Point3D)}
	 * @param transform the transform to use
	 * @param input the input 3D point
	 * @param expected the expected result
	 * @param epsilon the minimal difference between values ton consider them as equals
	 */
	public static void transformTest(Transform3D transform, Point3D input, Point3D expected, double epsilon) {

		assertNotNull(transform, "Null transform provided.");

		Point3D result = null;

		if (input == null) {
			try {
				result = transform.transform(input);			  
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNull(result, "No null result from transformation.");
			
		} else {

			assertNotNull(expected, "Null expected point provided.");

			try {
				result = transform.transform(input);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNotNull(result, "Null result from transformation.");

			assertEquals(expected.getX(), result.getX(), epsilon, "Invalid coordinate x");
			assertEquals(expected.getY(), result.getY(), epsilon, "Invalid coordinate y");
			assertEquals(expected.getZ(), result.getZ(), epsilon, "Invalid coordinate z");
		}


	}

	/**
	 * Test the method {@link Transform3D#transform(Point3D, Point3D)}
	 * @param transform the transform to use
	 * @param input the input 3D point
	 * @param expected the expected result
	 * @param epsilon the minimal difference between values ton consider them as equals
	 */
	public static void transformResultTest(Transform3D transform, Point3D input, Point3D expected, double epsilon) {

		Point3D reference = null;

		Point3D result = null;

		assertNotNull(transform, "Null transform provided.");

		// Test with null result
		reference = transform.transform(input, null);		
		assertNull(reference, "Expected null refrence.");

		result = JeometryFactory.createPoint3D();		
		assertNotNull(result, "Unable to instanciate result point.");

		if (input == null) {
			try {
				reference = transform.transform(input, result);	
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			

			assertNotNull(reference, "Null reference from transformation.");
			assertSame(result, reference, "Result and reference are not the same object.");

			assertEquals(Double.NaN, reference.getX(), epsilon, "Invalid coordinate x");
			assertEquals(Double.NaN, reference.getY(), epsilon, "Invalid coordinate y");
			assertEquals(Double.NaN, reference.getZ(), epsilon, "Invalid coordinate z");
			
		} else {

			assertNotNull(expected, "Null expected point provided.");

			try {
				reference = transform.transform(input, result);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			

			assertNotNull(reference, "Null reference from transformation.");
			assertSame(result, reference, "Result and reference are not the same object.");

			assertEquals(expected.getX(), reference.getX(), epsilon, "Invalid coordinate x");
			assertEquals(expected.getY(), reference.getY(), epsilon, "Invalid coordinate y");
			assertEquals(expected.getZ(), reference.getZ(), epsilon, "Invalid coordinate z");
		}	
	}

	/**
	 * Test the method {@link Transform3D#transformAffect(Point3D)}
	 * @param transform the transform to use
	 * @param input the input 3D point
	 * @param expected the expected result
	 * @param epsilon the minimal difference between values ton consider them as equals
	 */
	public static void transformAffectTest(Transform3D transform, Point3D input, Point3D expected, double epsilon) {

		assertNotNull(transform, "Null transform provided.");

		Point3D result = null;

		if (input == null) {
			try {
				result = transform.transformAffect(input);			  
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNull(result, "No null result from transformation.");
			
		} else {

			assertNotNull(expected, "Null expected point provided.");

			try {
				result = transform.transformAffect(input);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNotNull(result, "Null result from transformation.");
			assertSame(input, result, "Result and input are not the same object.");

			assertEquals(expected.getX(), result.getX(), epsilon, "Invalid coordinate x");
			assertEquals( expected.getY(), result.getY(), epsilon, "Invalid coordinate y");
			assertEquals(expected.getZ(), result.getZ(), epsilon, "Invalid coordinate z");
		}


	}

	/**
	 * Test the method {@link Transform3D#transformInverse(Point3D)}
	 * @param transform the transform to use
	 * @param input the input 3D point
	 * @param expected the expected result
	 * @param epsilon the minimal difference between values ton consider them as equals
	 */
	public static void transformInverseTest(Transform3D transform, Point3D input, Point3D expected, double epsilon) {

		assertNotNull(transform, "Null transform provided.");

		Point3D result = null;

		if (input == null) {
			try {
				result = transform.transformInverse(input);			  		
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNull(result, "No null result from transformation.");
			
		} else {

			assertNotNull(expected, "Null expected point provided.");

			try {
				result = transform.transformInverse(input);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}


			assertNotNull(result, "Null result from transformation.");

			assertEquals(expected.getX(), result.getX(), epsilon, "Invalid coordinate x");
			assertEquals(expected.getY(), result.getY(), epsilon, "Invalid coordinate y");
			assertEquals(expected.getZ(), result.getZ(), epsilon, "Invalid coordinate z");

		}


	}

	/**
	 * Test the method {@link Transform3D#transformInverse(Point3D, Point3D)}
	 * @param transform the transform to use
	 * @param input the input 3D point
	 * @param expected the expected result
	 * @param epsilon the minimal difference between values ton consider them as equals
	 */
	public static void transformInverseResultTest(Transform3D transform, Point3D input, Point3D expected, double epsilon) {

		Point3D reference = null;

		Point3D result = null;

		assertNotNull(transform, "Null transform provided.");

		// Test with null result
		reference = transform.transformInverse(input, null);		
		assertNull(reference, "Expected null refrence.");

		result = JeometryFactory.createPoint3D();		
		assertNotNull(result, "Unable to instanciate result point.");

		if (input == null) {
			try {
				reference = transform.transformInverse(input, result);	
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNotNull(reference, "Null reference from transformation.");
			assertSame(result, reference, "Result and reference are not the same object.");

			assertEquals(Double.NaN, reference.getX(), epsilon, "Invalid coordinate x");
			assertEquals(Double.NaN, reference.getY(), epsilon, "Invalid coordinate y");
			assertEquals(Double.NaN, reference.getZ(), epsilon, "Invalid coordinate z");
		} else {

			assertNotNull(expected, "Null expected point provided.");

			try {
				reference = transform.transformInverse(input, result);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNotNull(reference, "Null reference from transformation.");
			assertSame(result, reference, "Result and reference are not the same object.");

			assertEquals(expected.getX(), reference.getX(), epsilon, "Invalid coordinate x");
			assertEquals(expected.getY(), reference.getY(), epsilon, "Invalid coordinate y");
			assertEquals(expected.getZ(), reference.getZ(), epsilon, "Invalid coordinate z");
		}	
	}

	/**
	 * Test the method {@link Transform3D#transformInverseAffect(Point3D)}
	 * @param transform the transform to use
	 * @param input the input 3D point
	 * @param expected the expected result
	 * @param epsilon the minimal difference between values ton consider them as equals
	 */
	public static void transformInverseAffectTest(Transform3D transform, Point3D input, Point3D expected, double epsilon) {

		assertNotNull(transform, "Null transform provided.");

		Point3D result = null;

		if (input == null) {
			try {
				result = transform.transformInverseAffect(input);			  
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNull(result, "No null result from transformation.");
			
		} else {

			assertNotNull(expected, "Null expected point provided.");

			try {
				result = transform.transformInverseAffect(input);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			

			assertNotNull(result, "Null result from transformation.");
			assertSame(input, result, "Result and input are not the same object.");

			assertEquals(expected.getX(), result.getX(), epsilon, "Invalid coordinate x");
			assertEquals(expected.getY(), result.getY(), epsilon, "Invalid coordinate y");
			assertEquals(expected.getZ(), result.getZ(), epsilon, "Invalid coordinate z");

		}


	}
}
