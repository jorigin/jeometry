package org.jeometry.simple.geom3D.transform;

import org.jeometry.factory.JeometryFactory;
import org.jeometry.geom3D.transform.Transform3DMatrixTest;
import org.jeometry.simple.factory.SimpleMathBuilder;
import org.junit.BeforeClass;

/**
 * Unitary tests dedicated to {@link SimpleTransform3DMatrix} implementations.<br>
 * @author Julien Seinturier - (c) 2016 - JOrigin project - <a href="http://www.jorigin.org">http:/www.jorigin.org</a>
 * @since 1.0.2
 *
 */
public class SimpleTransform3DMatrixTest extends Transform3DMatrixTest{

	@BeforeClass
	public static void initClass() {
		JeometryFactory.setMathBuilder(new SimpleMathBuilder());	
		transformation3DClass = SimpleTransform3DMatrix.class;
	}
}
