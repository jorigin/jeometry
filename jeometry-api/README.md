# JEometry
A Java based Geometry toolkit. This library enable to deal with 2D/3D points, meshes and various algorithm.

## Usage
JEometry is available at Maven Central. To import the library just add the following dependency to your maven project:
```xml
<TODO>
```

## Buid and deploy
Edit the maven settings file (by default located at ~/.m2/settings.xml) and add following entries:
```xml
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>${nexus.user}</username>
      <password>${nexus.password}</password>
    </server>
  </servers>
  
  <profiles>
    <profile>
      <id>ossrh</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
    </profile>
  </profiles>
</settings>
```
Make then the following actions.

__1. Clean the project__:
```console
mvn clean
```
__2. Prepare the release__:
```console
mvn -Dgpg.passphrase="yourpassphrase" -Dnexus.user="your_sonatype_username" -Dnexus.password="your_sonatype_password" release:prepare
```
__3. Perform the release__:
```console
mvn -Dgpg.passphrase="yourpassphrase" -Dnexus.user="your_sonatype_username" -Dnexus.password="your_sonatype_password" release:perform
```
__4. Update the Git project__:
```console
git push --tags
git push origin master
```

__5. In case of <span style="color:red">problem</span> during steps 1 to 4__:

+ 5.1: Undo the release:
```console
git reset --hard HEAD~1
```
_(You may have to do it a second time, depending upon when the error occurred.)_

+ 5.2: Delete the tag.
```console
git tag -d tagName
git push origin :refs/tags/tagName
```

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
