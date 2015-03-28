## Introduction ##

If you like [Google Guava](https://code.google.com/p/guava-libraries/), I am working to extend and add new utilities classes.

For the last ten years, I have written similar argument checking routines, helpers, and collections in C, C++, Perl, Python, JavaScript, VBA, C#, and Java.  I'm tired of re-writing these routines each time I join a new project.  If I open-source them once with excellent testing (and publish to Maven Central Repository), I can easily import to future Java projects.

## Focus ##

For the initial phase, I will focus on argument checking.  This is similar to [com.google.common.base.Preconditions](http://docs.guava-libraries.googlecode.com/git-history/release/javadoc/com/google/common/base/Preconditions.html) from Google Guava, but taken up a notch.  See [MapArgs](http://docs.kevinarpe-papaya.googlecode.com/git/javadoc/com/googlecode/kevinarpe/papaya/argument/MapArgs.html) for an example.

This project targets Java 1.6+.

## Testing ##

This library is well-tested and its test coverage is continually improving.  As of writing, there are over 9,000 tests for this library.

Every class and static method is marked with Java annotations as either [@NotFullyTested](http://docs.kevinarpe-papaya.googlecode.com/git/kevinarpe-papaya/javadoc/com/googlecode/kevinarpe/papaya/annotation/NotFullyTested.html) or [@FullyTested](http://docs.kevinarpe-papaya.googlecode.com/git/kevinarpe-papaya/javadoc/com/googlecode/kevinarpe/papaya/annotation/FullyTested.html).  Most code is "fully tested" at first release.  However, code that is released as @NotFullyTested will normally be tested in the next release.

## Documentation ##

All packages, (public and protected) classes, and (public and protected) methods are fully documented with standard Javadoc, including a variety of useful references peppered throughout.  All known exceptions, including unchecked (runtime) exceptions, are fully documented for each method.

[Browse the latest aggregated API docs here.](http://docs.kevinarpe-papaya.googlecode.com/git/javadoc/index.html)

## License ##

This project is not licensed under Apache License 2.0.  Instead, I am using GNU General Public License 3.0+ with a "[Classpath](http://www.gnu.org/software/classpath/license.html)" linking exception.  (More on [Wikipedia](http://en.wikipedia.org/wiki/GPL_linking_exception).)  This exception allows this library to be used unmodified in any project, regardless of license, without affecting the linking project's license.  If the Classpath linking exception were not available, linking projects would be required to use a GPL-compatible license and fully adhere to GPL license requirements under all circumstances.

In short:
  * If your project only links to this library, your project's license **is not** affected.
  * If your project modifies, copies, or borrows source code from this library, your project's license **is** affected, and must be GPL-compatible.

## Maven Central Repository ##

The JARs produced by this project are now released to the [Maven Central Repository](http://search.maven.org/).  ([Example search result](http://search.maven.org/#search|ga|1|kevinarpe-papaya).)  Special thanks is owed to [Sonatype](http://www.sonatype.com/) for donating time and resources to the open source community to make publishing to the Maven Central Repository possible for all.

Release Seventeen is available after 27 December 2014.  Read the [release notes](ReleaseNotes.md).

Apache Maven dependency:
```
<dependency>
  <groupId>com.googlecode.kevinarpe-papaya</groupId>
  <artifactId>kevinarpe-papaya</artifactId>
  <version>0.0.17</version>
</dependency>
```

There are also numerous sub-artefacts:
  * kevinarpe-papaya-joda-time
  * kevinarpe-papaya-testing-log4j
  * kevinarpe-papaya-testing-log4j-junit
  * kevinarpe-papaya-testing-log4j-testng

## The Future of Java ##

The ball is rolling again after the buyout of Sun Microsystems by Oracle, the open sourcing of Java, and the release of Java 1.7.

There will be a separate project (in the near future) called **kevinarpe-papaya7** for utilities targetting features, classes, and methods specific to Java 1.7+.  Example: NIO2 introduces [java.nio.file.Path](http://docs.oracle.com/javase/7/docs/api/java/nio/file/Path.html), which may displace [java.io.File](http://docs.oracle.com/javase/6/docs/api/java/io/File.html) in the future.

If things go very well, there will probably be another project called **kevinarpe-papaya8** for utilities targetting features, classes, and methods specific to Java 1.8+.