# JEometry
A Java based Geometry toolkit. This library enable to deal with 2D/3D points, meshes and various algorithm.

## Usage
JEometry is available at Maven Central. To import the library just add the following dependency to your maven project:
```xml
<!-- The Jeometry API that contains all interfaces -->
<dependency>
  <groupId>org.jorigin</groupId>
  <artifactId>jeometry-api</artifactId>
  <version>1.0.0</version>
</dependency>

<!-- The Jeometry Simple implementation -->
<dependency>
  <groupId>org.jorigin</groupId>
  <artifactId>jeometry-simple</artifactId>
  <version>1.0.0</version>
</dependency>
```
For more information, tutorials and advances uses, please check the [Wiki](https://github.com/jorigin/jeometry/wiki).

## Changes:

### TODO
  - Add set(Matrix) to Matrix, this method affect the input matrix to the current one
  - Add createTransformation3DMatrix(Matrix) to geometry factory
  - Implements SVD decomposition
  - Implements LU decomposition
  - Implements linear solvers
  - Refactor getMatrix / setMAtrix within Matrix in order to handle sizes
  
### 1.0.0
  + Initial release.
