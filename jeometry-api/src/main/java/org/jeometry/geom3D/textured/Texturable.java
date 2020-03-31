package org.jeometry.geom3D.textured;

import org.jeometry.Geometry;
import org.jeometry.geom2D.point.Point2DContainer;

/**
 * This class describe an object that can be textured, i-e an object that provide texture coordinates. 
 * Texture coordinates are expressed as a ratio of the resource bounds. For example, if the texture resource is an image, 
 * texture coordinates (x / image width, y / image height) correspond to the pixel (x, y) on the image.
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Geometry#version} build {@value Geometry#BUILD}
 * @since 1.0.0
 */
public interface Texturable {
	
  /**
   * The texture coordinates are expressed in pixel. 
   */
  public static final int COORDINATE_PIXEL     = 1;
  
  /**
   * The texture coordinates are expressed in ratio of texture.
   */
  public static final int COORDINATE_RATIO     = 2;
	
  /**
   * A mask that enable to extract coordinate nature from an integer.
   * @see #ORIGIN_MASK
   * @see #AXIS_MASK
   */
  public static final int COORDINATE_MASK = COORDINATE_PIXEL | COORDINATE_RATIO;
  
  /**
   * The texture origin if the center of the texture.
   */
  public static final int ORIGIN_CENTER        = 4;
  
  /**
   * The texture origin if the upper left point of the texture.
   */
  public static final int ORIGIN_UPPER_LEFT    = 8;
  
  /**
   * The texture origin if the lower left point of the texture.
   */
  public static final int ORIGIN_LOWER_LEFT    = 16;
  
  /**
   * The texture origin if the upper right point of the texture.
   */
  public static final int ORIGIN_UPPER_RIGHT   = 32;
  
  /**
   * The texture origin if the lower right point of the texture.
   */
  public static final int ORIGIN_LOWER_RIGHT   = 64;
  
  /**
   * The mask that enable to extract coordinate origin from an integer.
   * @see #COORDINATE_MASK
   * @see #AXIS_MASK
   */
  public static final int ORIGIN_MASK = ORIGIN_CENTER | ORIGIN_UPPER_LEFT | ORIGIN_LOWER_LEFT | ORIGIN_UPPER_RIGHT | ORIGIN_LOWER_RIGHT;
  
  /**
   * The texture coordinates X axis is left to right.
   */
  public static final int AXIS_X_RIGHT = 128;
  
  /**
   * The texture coordinates X axis is right to left.
   */
  public static final int AXIS_X_LEFT = 256;
  
  /**
   * The texture coordinates Y axis is top to bottom.
   */
  public static final int AXIS_Y_BOTTOM = 512;
  
  /**
   * The texture coordinates Y axis is bottom to tom.
   */
  public static final int AXIS_Y_TOP = 1024;
  
  /**
   * The mask that enable to extract axis information from an integer.
   * @see #COORDINATE_MASK
   * @see #ORIGIN_MASK
   */
  public static final int AXIS_MASK = AXIS_X_RIGHT | AXIS_X_LEFT | AXIS_Y_BOTTOM | AXIS_Y_TOP;
  
  /**
   * The mask that enable to extract X axis information from an integer.
   * @see #COORDINATE_MASK
   * @see #ORIGIN_MASK
   */
  public static final int AXIS_X_MASK = AXIS_X_RIGHT | AXIS_X_LEFT;
  
  /**
   * The mask that enable to extract Y axis information from an integer.
   * @see #COORDINATE_MASK
   * @see #ORIGIN_MASK
   */
  public static final int AXIS_Y_MASK = AXIS_Y_BOTTOM | AXIS_Y_TOP;

  
  /**
   * Get the {@link Texture texture} in witch the texture coordinates are expressed.
   * @return the {@link Texture texture} in witch the texture coordinates are expressed.
   */
  public Texture getTexture();
  
  /**
   * Set the {@link Texture texture} in witch the texture coordinates are expressed.
   * @param texture {@link Texture texture} in witch the texture coordinates are expressed.
   */
  public void setTexture(Texture texture);
  
  /**
   * Get the texture coordinates attached to this object. Texture coordinates are expressed as a ratio of the resource bounds. For example, if the texture resource is an image, 
   * texture coordinates (x / image width, y / image height) correspond to the pixel (x, y) on the image.
   * @return the texture coordinates attached to this object.
   * @see #setTextureCoodinates(Point2DContainer)
   */
  public Point2DContainer getTextureCoodinates();

  /**
   * Set the texture coordinates attached to this object. Texture coordinates are expressed as a ratio of the resource bounds. For example, if the texture resource is an image, 
   * texture coordinates (x / image width, y / image height) correspond to the pixel (x, y) on the image.
   * @param coordinates the texture coordinates attached to this object.
   * @see #getTextureCoodinates()
   */
  public void setTextureCoodinates(Point2DContainer coordinates);
  
  /**
   * Get the nature of the texture coordinates. Possible values are:
   * <ul>
   * <li>{@link #COORDINATE_PIXEL} if the coordinates are expressed in pixels.
   * <li>{@link #COORDINATE_RATIO} if the coordinates are expressed in ratio of the texture.
   * </ul>
   * @return the nature of the texture coordinates.
   * @see #setTextureCoordinatesNature(int)
   */
  public int getTextureCoordinatesNature();
  
  /**
   * Set the nature of the texture coordinates. Possible values are:
   * <ul>
   * <li>{@link #COORDINATE_PIXEL} if the coordinates are expressed in pixels.
   * <li>{@link #COORDINATE_RATIO} if the coordinates are expressed in ratio of the texture.
   * </ul>
   * @param nature the nature of the texture coordinates.
   */
  public void setTextureCoordinatesNature(int nature);
  
  /**
   * Get the texture coordinate origin. Possible values are:
   * <ul>
   * <li>{@link #ORIGIN_CENTER} if the origin of the coordinates is the center of the texture.
   * <li>{@link #ORIGIN_LOWER_LEFT} if the origin of the coordinates is the lower left point of the texture.
   * <li>{@link #ORIGIN_LOWER_RIGHT} if the origin of the coordinates is the lower right point of the texture.
   * <li>{@link #ORIGIN_UPPER_LEFT} if the origin of the coordinates is the upper left point of the texture.
   * <li>{@link #ORIGIN_UPPER_RIGHT} if the origin of the coordinates is the upper right point of the texture.
   * </ul>
   * @return the texture coordinate origin.
   * @see #setTextureCoordinatesOrigin(int)
   */
  public int getTextureCoordinatesOrigin();
  
  /**
   * Set the texture coordinate origin. Possible values are:
   * <ul>
   * <li>{@link #ORIGIN_CENTER} if the origin of the coordinates is the center of the texture.
   * <li>{@link #ORIGIN_LOWER_LEFT} if the origin of the coordinates is the lower left point of the texture.
   * <li>{@link #ORIGIN_LOWER_RIGHT} if the origin of the coordinates is the lower right point of the texture.
   * <li>{@link #ORIGIN_UPPER_LEFT} if the origin of the coordinates is the upper left point of the texture.
   * <li>{@link #ORIGIN_UPPER_RIGHT} if the origin of the coordinates is the upper right point of the texture.
   * </ul>
   * @param origin  the texture coordinate origin.
   * @see #getTextureCoordinatesOrigin()
   */
  public void setTextureCoordinatesOrigin(int origin);
  
  /**
   * Get the texture coordinate referential X direction. Possible values are:
   * <ul>
   * <li>{@link #AXIS_X_LEFT} if the X axis is right towards left.
   * <li>{@link #AXIS_X_RIGHT} if the X axis is left towards right.
   * </ul>
   * @return the texture coordinate referential X direction.
   * @see #setTextureCoordinatesAxisXDirection(int)
   */
  public int getTextureCoordinatesAxisXDirection();
  
  /**
   * Set the texture coordinate referential X direction. Possible values are:
   * <ul>
   * <li>{@link #AXIS_X_LEFT} if the X axis is right towards left.
   * <li>{@link #AXIS_X_RIGHT} if the X axis is left towards right.
   * </ul>
   * @param direction the texture coordinate referential X direction.
   * @see #getTextureCoordinatesAxisXDirection()
   */
  public void setTextureCoordinatesAxisXDirection(int direction);
  
  /**
   * Get the texture coordinate referential Y direction. Possible values are:
   * <ul>
   * <li>{@link #AXIS_Y_TOP} if the Y axis is bottom towards top.
   * <li>{@link #AXIS_Y_BOTTOM} if the Y axis is top towards bottom.
   * </ul>
   * @return the texture coordinate referential Y direction.
   * @see #setTextureCoordinatesAxisYDirection(int)
   */
  public int getTextureCoordinatesAxisYDirection();
  
  /**
   * Set the texture coordinate referential Y direction. Possible values are:
   * <ul>
   * <li>{@link #AXIS_Y_TOP} if the Y axis is bottom towards top.
   * <li>{@link #AXIS_Y_BOTTOM} if the Y axis is top towards bottom.
   * </ul>
   * @param direction the texture coordinate referential Y direction.
   * @see #getTextureCoordinatesAxisYDirection()
   */
  public void setTextureCoordinatesAxisYDirection(int direction);
}
