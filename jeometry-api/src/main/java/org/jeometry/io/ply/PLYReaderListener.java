package org.jeometry.io.ply;

import org.jeometry.Geometry;
import org.jeometry.geom3D.mesh.Face;
import org.jeometry.geom3D.point.Point3D;

/**
 * A listener dedicated to the monitoring of a {@link PLYReader PLY reader}
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version}
 * @since 1.0.0
 *
 */
public interface PLYReaderListener {

	/**
	 * A PLY stream read has been started.
	 */
  public void readStarted();

  /**
   * A PLY stream read has been finished.
   */
  public void readFinished();

  /**
   * A PLY stream header read has been started.
   */
  public void readHeaderStarted();

  /**
   * A PLY stream header read has finished.
   */
  public void readHeaderFinished();

  /**
   * The PLY reader has read the given {@link PLYElementDescription description}.
   * @param description the read description.
   */
  public void readElementDescription(PLYElementDescription description);

  /**
   * A PLY stream data read has been started.
   */
  public void readDataStarted();

  /**
   * A PLY stream data read has been finished.
   */
  public void readDataFinished();

  /**
   * The PLY reader has started to read the given {@link PLYElementDescription element description}.
   * @param description the read {@link PLYElementDescription element description}.
   */
  public void readElementsStarted(PLYElementDescription description);

  /**
   * The PLY reader has finished to read the given {@link PLYElementDescription element description}.
   * @param description the read {@link PLYElementDescription element description}.
   */
  public void readElementsFinished(PLYElementDescription description);

  /**
   * The PLY vertex has read the given {@link Point3D 3D point}.
   * @param vertex the read point.
   */
  public void readVertex(Point3D vertex);

  /**
   * The PLY vertex has read the given {@link Face 3D face}.
   * @param face the read face.
   */
  public void readFace(Face<?> face);
}
