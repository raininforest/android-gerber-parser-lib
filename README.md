# Android Gerber Parser Library
Android java lib for gerber file parsing.

### TODO:
TF, TA, TD, TO command are not implemented. (do not affect graphics).

### Gradle Setup

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.raininforest:android-gerber-parser-lib:-SNAPSHOT'
}
```

### Maven Setup

```xml
<!-- <repositories> section of pom.xml -->
<repository>
   <id>jitpack.io</id>
   <url>https://jitpack.io</url>
</repository>

<!-- <dependencies> section of pom.xml -->
<dependency>
    <groupId>com.github.raininforest</groupId>
    <artifactId>android-gerber-parser-lib</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```
