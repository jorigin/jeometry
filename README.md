# Jeometry
A Java based Geometry toolkit. This library enable to deal with 2D/3D points, meshes and various algorithm.

## Integration
Jeometry can be used as a Maven dependency or as a standalone library.

### Maven
Jeometry is available at [Maven Central](https://search.maven.org/search?q=org.jorigin.jeometry). 

To import the library, add the following parts to the maven project:
```xml

<!-- You can update the properties section with Jeometry version -->
<properties>
  <jeometry.version>1.0.6</jeometry.version> 
</properties>

<!-- The Jeometry API that contains all interfaces -->
<dependency>
  <groupId>org.jorigin</groupId>
  <artifactId>jeometry-api</artifactId>
  <version>${jeometry.version}</version>
</dependency>

<!-- (Optional) The Jeometry module that contains geometric algorithms implementations -->
<dependency>
  <groupId>org.jorigin</groupId>
  <artifactId>jeometry-algorithm</artifactId>
  <version>${jeometry.version}</version>
</dependency>

<!-- The Jeometry Simple implementation -->
<!-- At least one api implementation is needed -->
<dependency>
  <groupId>org.jorigin</groupId>
  <artifactId>jeometry-simple</artifactId>
  <version>${jeometry.version}</version>
</dependency>
```

### Standalone
Jeometry can be used as standalone library by integrating the jars provided by a [release](https://github.com/jorigin/jeometry/releases) to the classpath. 
Be carrefull to also integrate the [JCommon](https://github.com/jorigin/jcommon) dependency.

## Usage
For a quick overwiew ot the library, please refer to the [Getting Started](https://github.com/jorigin/jeometry/wiki/Getting-Started).

For more information, tutorials and advanced uses, please check the [Wiki](https://github.com/jorigin/jeometry/wiki).

## Changes:

see [changelog](CHANGELOG.md) for details.
