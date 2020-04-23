package org.jeometry.geom3D.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.point.Point3D;

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

		assertNotNull("Null transform provided.", transform);

		Point3D result = null;

		if (input == null) {
			try {
				result = transform.transform(input);			  
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNull("No null result from transformation.", result);
			
		} else {

			assertNotNull("Null expected point provided.", expected);

			try {
				result = transform.transform(input);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNotNull("Null result from transformation.", result);

			assertEquals("Invalid coordinate x", expected.getX(), result.getX(), epsilon);
			assertEquals("Invalid coordinate y", expected.getY(), result.getY(), epsilon);
			assertEquals("Invalid coordinate z", expected.getZ(), result.getZ(), epsilon);
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

		assertNotNull("Null transform provided.", transform);

		// Test with null result
		reference = transform.transform(input, null);		
		assertNull("Expected null refrence.", reference);

		result = JeometryFactory.createPoint3D();		
		assertNotNull("Unable to instanciate result point.", result);

		if (input == null) {
			try {
				reference = transform.transform(input, result);	
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			

			assertNotNull("Null reference from transformation.", reference);
			assertSame("Result and reference are not the same object.", result, reference);

			assertEquals("Invalid coordinate x", Double.NaN, reference.getX(), epsilon);
			assertEquals("Invalid coordinate y", Double.NaN, reference.getY(), epsilon);
			assertEquals("Invalid coordinate z", Double.NaN, reference.getZ(), epsilon);
			
		} else {

			assertNotNull("Null expected point provided.", expected);

			try {
				reference = transform.transform(input, result);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			

			assertNotNull("Null reference from transformation.", reference);
			assertSame("Result and reference are not the same object.", result, reference);

			assertEquals("Invalid coordinate x", expected.getX(), reference.getX(), epsilon);
			assertEquals("Invalid coordinate y", expected.getY(), reference.getY(), epsilon);
			assertEquals("Invalid coordinate z", expected.getZ(), reference.getZ(), epsilon);
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

		assertNotNull("Null transform provided.", transform);

		Point3D result = null;

		if (input == null) {
			try {
				result = transform.transformAffect(input);			  
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNull("No null result from transformation.", result);
			
		} else {

			assertNotNull("Null expected point provided.", expected);

			try {
				result = transform.transformAffect(input);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNotNull("Null result from transformation.", result);
			assertSame("Result and input are not the same object.", input, result);

			assertEquals("Invalid coordinate x", expected.getX(), result.getX(), epsilon);
			assertEquals("Invalid coordinate y", expected.getY(), result.getY(), epsilon);
			assertEquals("Invalid coordinate z", expected.getZ(), result.getZ(), epsilon);
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

		assertNotNull("Null transform provided.", transform);

		Point3D result = null;

		if (input == null) {
			try {
				result = transform.transformInverse(input);			  		
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNull("No null result from transformation.", result);
			
		} else {

			assertNotNull("Null expected point provided.", expected);

			try {
				result = transform.transformInverse(input);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}


			assertNotNull("Null result from transformation.", result);

			assertEquals("Invalid coordinate x", expected.getX(), result.getX(), epsilon);
			assertEquals("Invalid coordinate y", expected.getY(), result.getY(), epsilon);
			assertEquals("Invalid coordinate z", expected.getZ(), result.getZ(), epsilon);

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

		assertNotNull("Null transform provided.", transform);

		// Test with null result
		reference = transform.transformInverse(input, null);		
		assertNull("Expected null refrence.", reference);

		result = JeometryFactory.createPoint3D();		
		assertNotNull("Unable to instanciate result point.", result);

		if (input == null) {
			try {
				reference = transform.transformInverse(input, result);	
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNotNull("Null reference from transformation.", reference);
			assertSame("Result and reference are not the same object.", result, reference);

			assertEquals("Invalid coordinate x", Double.NaN, reference.getX(), epsilon);
			assertEquals("Invalid coordinate y", Double.NaN, reference.getY(), epsilon);
			assertEquals("Invalid coordinate z", Double.NaN, reference.getZ(), epsilon);
		} else {

			assertNotNull("Null expected point provided.", expected);

			try {
				reference = transform.transformInverse(input, result);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNotNull("Null reference from transformation.", reference);
			assertSame("Result and reference are not the same object.", result, reference);

			assertEquals("Invalid coordinate x", expected.getX(), reference.getX(), epsilon);
			assertEquals("Invalid coordinate y", expected.getY(), reference.getY(), epsilon);
			assertEquals("Invalid coordinate z", expected.getZ(), reference.getZ(), epsilon);
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

		assertNotNull("Null transform provided.", transform);

		Point3D result = null;

		if (input == null) {
			try {
				result = transform.transformInverseAffect(input);			  
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			
			assertNull("No null result from transformation.", result);
			
		} else {

			assertNotNull("Null expected point provided.", expected);

			try {
				result = transform.transformInverseAffect(input);
			} catch (Throwable t) {
				fail("Unexpected exception: "+t.getMessage());
			}
			

			assertNotNull("Null result from transformation.", result);
			assertSame("Result and input are not the same object.", input, result);

			assertEquals("Invalid coordinate x", expected.getX(), result.getX(), epsilon);
			assertEquals("Invalid coordinate y", expected.getY(), result.getY(), epsilon);
			assertEquals("Invalid coordinate z", expected.getZ(), result.getZ(), epsilon);

		}


	}
}
