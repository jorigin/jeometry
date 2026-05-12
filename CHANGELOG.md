# Change Log

## TODO
### Add
- Add Ellipse representation within 2D space
- Implements `SimplePointBuilder.createPoint2DContainer()`
- Implements `SimplePointBuilder.createPoint2DContainer(int)`
- Implements `SimplePointBuilder.createPoint2DContainer(List<? extends Point2D> points)`
- Implements `SimplePointBuilder.createPoint2DContainer(Point2D[] points)`
- Implements `SimplePointBuilder.createPoint3DContainer()`
- Implements `SimplePointBuilder.createPoint3DContainer(int)`
- Wrapper to Vector / Matrix in order to use a vector as a matrix and a matrix as a vector without copying data
- Views on a Vector / Matrix to focus on a specific part of the object without copying the underlying data
- Decomposition `compose()` method to recompute input matrix from a decomposition
- Vector concatenation
- Vector `Vector plusFactor(double, Vector)`
- Vector `Vector plusFactor(double, Vector, Vector)`
- Vector `Vector minusFactor(double, Vector)`
- Vector `Vector minusFactor(double, Vector, Vector)`
- Matrix matrix norms (see (Here)[https://en.wikipedia.org/wiki/Matrix_norm])
- Test implements Point2DTest
- Test `Box2D` interface
- Test `Ellipse2D` interface
- Test `Line2D` interface
- Test `LineString2D` interface
- Test `Polygon2D` interface
- Test `JeometryFactory.createPoint2DContainer()`
- Test `JeometryFactory.createPoint2DContainer(int capacity)`
- Test `JeometryFactory.createPoint2DContainer(List<? extends Point2D> points)`
- Test `JeometryFactory.createPoint2DContainer(Point2D[] points)`
- Test `JeometryFactory.createBox2D(Point2D min, double width, double height)`
- Test `JeometryFactory.createBox2D(Point2D min, Point2D max)`
- Test `JeometryFactory.createBox2D(double minX, double minY, double width, double height)`
- Test `JeometryFactory.createLineString2D(Point2DContainer vertices, boolean closed)`
- Test `JeometryFactory.createLineString2D(List<? extends Point2D> vertices, boolean closed)`
- Test `JeometryFactory.createLineString2D(Point2D[] vertices, boolean closed)`

### Change
- Refactor Matrix `setTo` to `setValues`
- Refactor Quaternion `mult` into `product`
- Refactor make all xxxAffect() methods to return a reference on the object instead of `void` in order to chain processing
- Refactor make all setValue(s) methods to return a reference on the object for chaining purpose
- Enable JeometryFactory to handle multiples implementation

## [2.0.1](https://github.com/jorigin/jeometry/releases/tag/jeometry-2.0.1)

### Added
- Interface `Box2D` primitive within jeometry-api
- Interface `Ellipse2D` primitive within jeometry-api
- Interface `Geometry2D` within jeometry-api
- Interface `Line2D` primitive within jeometry-api
- Interface `LineString2D` primitive within jeometry-api
- Class `org.jeometry.simple.geom2D.point.SimpleArrayListPoint2DContainer` within jeometry-simple
- Method `double distance(Coord2D c)` within `Coord2D` interface
- Method `get(int index, Point2D output)` within `Point2DContainer` class
- Method `Box2D createBox2D(Point2D min, double width, double height)` within `PrimitiveBuilder` class
- Method `Box2D createBox2D(Point2D min, Point2D max)` within `PrimitiveBuilder` class
- Method `Line2D createLine2D(Point2D start, Point2D end)` within `PrimitiveBuilder` class
- Method `LineString2D createLineString2D(Point2DContainer vertices, boolean closed)` within `PrimitiveBuilder` class
- Method `LineString2D createLineString2D(List<? extends Point2D> vertices, boolean closed)` within `PrimitiveBuilder` class
- Method `LineString2D createLineString2D(Point2D[] vertices, boolean closed)` within `PrimitiveBuilder` class
- Method `Box2D createBox2D(double minX, double minY, double width, double height)` within `PrimitiveBuilder` class
- Method `Ellipse2D createEllipse2D(Point2D center, double semiMajorAxis, double semiMinorAxis, double angle)` within `PrimitiveBuilder` class
- Method `Ellipse2D createEllipse2D(Point2D center, double semiMajorAxis, double semiMinorAxis)` within `PrimitiveBuilder` class
- Package `org.jeometry.geom2D.algorithm.relation.de9im` within `jeometry-algorithm` for DE9IM matrix computation


### Change
- Using JCOMMON 2.0.1
- Updating Javadoc @author tag for jeometry-api
- Refactored `org.jeometry.geom2D.point.Point2DContainer.DATA_COORDINATE` into `org.jeometry.geom2D.point.Point2DContainer.DATA_COORDINATE2D`
- Refactored class `org.jeometry.simple.geom3D.point.ArrayListPoint3DContainer` to `org.jeometry.simple.geom2D.point.SimpleArrayListPoint3DContainer`
- Refactored class `org.jeometry.simple.geom3D.point.ArrayCoordinatesPoint3DContainer` to `org.jeometry.simple.geom2D.point.SimpleArrayPoint3DContainer`
- Refactored `LineSet3D` method `List<Line3D<T>> getSegments()` into `List<Line3D<T>> getLines()` 
- Refactored `Polygon3D` method `List<Line3D<T>> getSegments()` into `List<Line3D<T>> getHull()` 
- Interface `Point2D` now implements `Geometry2D` interface
- Interface `Coord2D` now implements `Clonable`
- Interface `Coord2D` now implements `Comparable<Coord2D>` 

### Removed
- Removed `public double getXMin()` from `SpatialLocalization3D` (replaced by `public Point3D getMinCoordinates()`)
- Removed `public double getYMin()` from `SpatialLocalization3D` (replaced by `public Point3D getMinCoordinates()`)
- Removed `public double getZMin()` from `SpatialLocalization3D` (replaced by `public Point3D getMinCoordinates()`)
- Removed `public double getXMax()` from `SpatialLocalization3D` (replaced by `public Point3D getMaxCoordinates()`)
- Removed `public double getYMax()` from `SpatialLocalization3D` (replaced by `public Point3D getMaxCoordinates()`)
- Removed `public double getZMax()` from `SpatialLocalization3D` (replaced by `public Point3D getMaxCoordinates()`)

## [2.0.0](https://github.com/jorigin/jeometry/releases/tag/jeometry-2.0.0)
### Change
- Passing to JAVA 17
- Using JCOMMON 2.0.0

## [1.0.6](https://github.com/jorigin/jeometry/releases/tag/release-1.0.6)
### Added
- JeometryFactory `public static boolean loadImplementation(String)` method
- SpatialLocalization2D `public Point2D getMinCoordinates()`
- SpatialLocalization2D `public Point2D getMaxCoordinates()`
- Ellipse2D within `geom2D.primitive` package
### Change
- JeometryFactory initialization try to instanciate specific implementation and switch to default on (simple) if not found
### Removed
- SpatialLocalization2D `public double getXMin()`, use `public Point2D getMinCoordinates()` instead
- SpatialLocalization2D `public double getYMin()`, use `public Point2D getMinCoordinates()` instead
- SpatialLocalization2D `public double getXMax()`, use `public Point2D getMaxCoordinates()` instead
- SpatialLocalization2D `public double getYMax()`, use `public Point2D getMaxCoordinates()` instead

## [1.0.5](https://github.com/jorigin/jeometry/releases/tag/release-1.0.5)
### Added
- Project jeometry-algorithm that contains geometric algorithms implementation
- Class `org.jeometry.geom3D.algorithm.bounds.org.jeometry.geom3D.algorithm.bounds.AxisAlignedBoundingBox`

### Refactor
- Javadoc updated
- Moved package `org.jeometry.geom3D.algorithm` from jeometry-api to jeometry-algorithm project
- Class `ArrayCoordinatesPoint3DConainer` moved from jeometry-api to jeometry-simple
- Class `ArrayListPoint3DContainer` moved from jeometry-api to jeometry-simple
- Class `org.jeometry.geom3D.algorithm.ConvexDecomposition` moved to package `org.jeometry.geom3D.algorithm.decomposition.ConvexDecomposition`
- Geom3D `public static Box computeAxisAlignedBoundingBox(Collection<? extends Point3D> points)` moved to AxisAlignedBoundingBox.
- Geom3D `public static Box computeAxisAlignedBoundingBox(Point3DContainer<?> points)` moved to AxisAlignedBoundingBox.
- Geom3D `public static Box computeAxisAlignedBoundingBox(Mesh<?> polyhedron)` moved to AxisAlignedBoundingBox.
- Geom3D `public static <T extends Point3D> List<Mesh<T>> computeConvexDecomposition(Mesh<T> polyhedron)` moved to ConvexDecomposition.

### Removed
- Geom3D `public static double dot(Point3D v1, Point3D v2)`, use Point3D dot method instead.
- Geom3D `public static Point3D cross(Point3D v1, Point3D v2)`, use Point3D cross method instead.

## [1.0.4](https://github.com/jorigin/jeometry/releases/tag/release-1.0.4)
### Added
- PointBuilder `public createPoint3D(Vector)` method
- PointBuilder `public createPoint2D(Vector)` method
- JeometryFactory `public static createPoint3D(Vector)` method
- JeometryFactory `public static createPoint2D(Vector)` method
- Vector `public Vector plus(double)` method
- Vector `public Vector plus(double, Vector)` method
- Vector `public Vector plusAffect(double)` method
- Vector `public Vector minus(double)` method
- Vector `public Vector minus(double, Vector)` method
- Vector `public Vector minusAffect(double)` method
- Vector `public Vector divide(double)` method
- Vector `public Vector divide(double, Vector)` method
- Vector `public Vector divideAffect(double)` method
- Quaternion `public Quaternion plus(double)` method
- Quaternion `public Quaternion plusAffect(double)` method
- Quaternion `public Quaternion minus(double)` method
- Quaternion `public Quaternion minusAffect(double)` method
- Quaternion `public Quaternion divide(double)` method
- Quaternion `public Quaternion divideAffect(double)` method
- Quaternion `public Quaternion plus(double, Quaternion)` method
- Quaternion `public Quaternion minus(double, Quaternion)` method
- Quaternion `public Quaternion divide(double, Quaternion)` method
- Coor2D `public Coord2D plus(Vector)` method
- Coor2D `public Coord2D plusAffect(Vector)` method
- Coor2D `public Coord2D plus(double)` method
- Coor2D `public Coord2D plusAffect(double)` method
- Coor2D `public Coord2D minus(Vector)` method
- Coor2D `public Coord2D minusAffect(Vector)` method
- Coor2D `public Coord2D minus(double)` method
- Coor2D `public Coord2D minusAffect(double)` method
- Coor2D `public Coord2D multiply(double)` method
- Coor2D `public Coord2D multiplyAffect(double)` method
- Coor2D `public Coord2D multiply(Vector)` method
- Coor2D `public Coord2D multiplyAffect(Vector)` method
- Coor2D `public Coord2D divide(Vector)` method
- Coor2D `public Coord2D divideAffect(Vector)` method
- Coor2D `public Coor2D divide(double)` method
- Coor2D `public Coor2D divide(double, Vector)` method
- Point2D `public Point2D plus(Vector)` method
- Point2D `public Point2D plusAffect(Vector)` method
- Point2D `public Point2D plus(double)` method
- Point2D `public Point2D plusAffect(double)` method
- Point2D `public Point2D minus(Vector)` method
- Point2D `public Point2D minusAffect(Vector)` method
- Point2D `public Coord2D minus(double)` method
- Point2D `public Coord2D minusAffect(double)` method
- Point2D `public Point2D multiply(double)` method
- Point2D `public Point2D multiplyAffect(double)` method
- Point2D `public Point2D multiply(Vector)` method
- Point2D `public Point2D multiplyAffect(Vector)` method
- Point2D `public Point2D divide(Vector)` method
- Point2D `public Point2D divideAffect(Vector)` method
- Point2D `public Point2D divide(double)` method
- Point2D `public Point2D divide(double, Vector)` method

## [1.0.3](https://github.com/jorigin/jeometry/releases/tag/release-1.0.3)
### Added
- Unit tests upgraded to use Junit 5
- Modular structure added
- Jeometry `static void print(Vector vector, PrintStream stream, String prefix, int width, NumberFormat format)`
- Jeometry `static void print(Matrix, Writer, String` for Writer output
- Jeometry `static void print(Vector vector, Writer writer, String prefix)` for Writer output
- Jeometry `static void print(Matrix matrix, Writer writer, String prefix, int width, NumberFormat format)` for Writer output
- Jeometry `static void print(Matrix matrix, Writer writer, String prefix, int width, int fraction)`  for Writer output
- Jeometry `static void print(Vector vector, Writer writer, String prefix, int width, NumberFormat format)` for Writer output
- JeometryFactory `createPoint3D(Point3D)` method
- JeometryFactory `createPoint2D(Point2D)` method
- Matrix `Matrix concatHorizontal(Vector)` method
- Matrix `Matrix concatHorizontal(Vector, Matrix)` method
- Matrix `Matrix concatVertical(Vector)` method
- Matrix `Matrix concatVertical(Vector, Matrix)` method
- Matrix `Vector getColumn(int)` method
- Matrix `Vector getColumn(int, Vector)` method
- Matrix `double[] getColumn(int, double[])` method
- Matrix `Vector getRow(int)` method
- Matrix `Vector getRow(int, Vector)` method
- Matrix `double[] getRow(int, double[])` method
- Matrix `Matrix setColumn(int, Vector)` method
- Matrix `Matrix setColumn(int, double[])` method
- Matrix `Matrix setRow(int, Vector)` method
- Matrix `Matrix setRow(int, double[])` method
- Point2D `setValues(double, double, double)` method
- Point2D `setValues(Point3D)` method
- Point3D `setValues(double, double, double)` method
- Point3D `setValues(Point3D)` methods
- Transform3D `Point3D transformAffect(Point3D)` method
- Transform3D `Point3D transformInverse(Point3D)` method
- Transform3D `Point3D transformInverse(Point3D, Point3D)` method
- Transform3D `Point3D transformInverseAffect(Point3D)` method
- Transform3D `Transform3D invertTransformAffect()` method
- TransformBuilder class that enables to contruct Transform3D objects
- Vector `plus(Vector)`, `plus(Vector, Vector)`, `plusAffect(Vector)` methods
- Vector `minus(Vector)`, `minus(Vector, Vector)`, `minusAffect(Vector)` methods
- Vector `multiply(Vector)`, `multiply(Vector, Vector)`, `multiplyAffect(Vector)` methods
- Vector `divide(Vector)`, `divide(Vector, Vector)`, `divideAffect(Vector)` methods
- Vector `setComponents(double)` method
- Vector `setComponents(Matrix)` method
- Vector `dot(Vector)` method

### Changed
- Jeometry `static void print(Matrix matrix, PrintStream stream, String prefix)` was not writing on the stream but on System.out
- Jeometry `static void print(Vector vector, PrintStream stream, String prefix)` was not writing on the stream but on System.out
- MatrixTestData into MathTestData
- Vector `double[] getComponents()` into  `double[] getValues()`
- Vector `double[] getComponents(double[] components)` into `double[] getValues(double[] components)`
- Vector `void setComponents(double[] components)` into `void setValues(double[] components)`
- Vector `void setComponents(double value)` into `void setValues(double value)`
- Vector ` void setComponents(Matrix matrix)` into ` void setValues(Matrix matrix)`
- Vector `double getVectorComponent(int dimension)` into `double getValue(int dimension)`
- Vector `void setVectorComponent(int dimension, double value)` into `void setValue(int dimension, double value)`
- Vector `void setComponents(Vector v)` into `void setValues(Vector v)`
- Transform3DMatrix does not extends Matrix anymore
- Point3D renamed `mult` methods in `multiply` to conform to `Vector`
- Updated unit tests with new methods

### Removed
- `Transform3DUtils` class has been removed

## [1.0.2](https://github.com/jorigin/jeometry/releases/tag/release-1.0.2)
### Added
- Matrix decomposition - Cholesky
- Matrix decomposition - QR
- `Resolvable` interface that describe objects that can solve linear systems
- Matrix horizontal / vertical concatenation methods 
- Matrix `setValues(Matrix)` method that enable to copy the input matrix within the actual 
- Matrix `setTo(double)` method that enable set all the MAtrix values to the given one
- Matrix `extract(int, int, int, int)` and `extract(int, int, int, int, Matrix)` that enable to extract a submatrix
- Vector `setVectorComponent(int, double)` that enables to set all vector component values to the given one
- Vector `setComponents(Vector)` that enables to copy the values from the given vector within the actual
- Vector `setComponents(double[])` that enables to affect all the array values to the corresponding vector components
- Vector `extract(int, int)` that enables to extract a sub-vector from this one 
- Complete test suite for `Resolvable` interface implementations

### Removed
- `decomposeLU()` and `decomposeSVD()` methods from `Matrix` interface

### Changed
- LU related test matrices from `MatTestData`
