package org.jeometry.simple.geom2D.algorithm.relation.de9im;

import org.jeometry.Jeometry;
import org.jeometry.factory.JeometryFactory;
import org.jeometry.simple.factory.SimplePointBuilder;
import org.jeometry.simple.factory.SimplePrimitiveBuilder;
import org.jeometry.test.geom2D.algorithm.relation.de9im.DE9IMTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * Run the {@link DE9IMTest DE-9IM test suite} against the {@code simple} implementation.
 *
 * @author Julien SEINTURIER - <a href="https://github.com/jorigin/jeometry">github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} b{@value Jeometry#BUILD}
 * @since 2.0.1
 */
public class SimpleDE9IMTest extends DE9IMTest {

	/** Register the {@code simple} builders before tests run. */
	@BeforeAll
	public static void init() {
		JeometryFactory.setPointBuilder(new SimplePointBuilder());
		JeometryFactory.setPrimitiveBuilder(new SimplePrimitiveBuilder());
	}
}
