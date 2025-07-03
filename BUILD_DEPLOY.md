# Buid and deploy
In order to build an deploy a distribution of Jeometry, all the following steps have to be performed.
## 1. Preparing Maven configuration
Edit the maven settings file (by default located at ~/.m2/settings.xml) and add following entries:
```xml
<settings>

	<!-- Maven Central publishing -->
	<!-- https://central.sonatype.org/publish/generate-portal-token/ -->
	<servers>
		<server>
			<id>central</id>
			<username>YOUR ID</username>
			<password>YOUR PASSPHRASE</password>
		</server>
	</servers>
</settings>
```

## 2. Cleaning the project
Simply run the command
```console
mvn clean
```
## 3. Release preparation
### 3.1. Code update
- Ensure that the `org.jeometry.Jeometry.BUILD` variable is up to date
- Ensure that the `org.jeometry.Jeometry.version` variable is up to date
- Ensure that the README.md file section "Usage" is up to date with new version number
- Ensure that the CHANGELOG.md file has an entry dedicated to the new version (with anticipated link)

### 3.2. Git commit and push
From the main directory, run:
```console
git commit -m "Preparing release X.Y.Z"
```
Then run
```console
git push -u origin master
```

### 3.2. Maven release preparation
From the main directory, run:
```console
mvn -Dgpg.passphrase="yourpassphrase" release:prepare
```
### 4. Release perform
From the main directory, run:
```console
mvn -Dgpg.passphrase="yourpassphrase" release:perform
```
### 5. Git project update
From the main directory, run:
```console
git push --tags
git push origin master
```

### 5. Problem resolution
Actions described here have to be performed if an error has occured during previous steps 3 to 5.
## 5.1. Undo the release
From the main directory, run:
```console
git reset --hard HEAD~1
```
_(You may have to do it a second time, depending upon when the error occurred.)_

### 5.2. Delete the tag
From the main directory, run:
```console
git tag -d tagName
git push origin :refs/tags/tagName
```
