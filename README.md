<img src="http://cf.jare.io/?u=http%3A%2F%2Fwww.yegor256.com%2Fimages%2Fbooks%2Felegant-objects%2Fcactus.svg" height="100px" />

[![Donate via Zerocracy](https://www.0crat.com/contrib-badge/C63314D6Z.svg)](https://www.0crat.com/contrib/C63314D6Z)

[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![Managed by Zerocracy](https://www.0crat.com/badge/C63314D6Z.svg)](https://www.0crat.com/p/C63314D6Z)
[![DevOps By Rultor.com](http://www.rultor.com/b/yegor256/cactoos)](http://www.rultor.com/p/yegor256/cactoos)
[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Build Status](https://travis-ci.org/yegor256/cactoos.svg?branch=master)](https://travis-ci.org/yegor256/cactoos)
[![Build status](https://ci.appveyor.com/api/projects/status/8vs8huy61og6jwif?svg=true)](https://ci.appveyor.com/project/yegor256/cactoos)
[![Javadoc](http://www.javadoc.io/badge/org.cactoos/cactoos.svg)](http://www.javadoc.io/doc/org.cactoos/cactoos)
[![PDD status](http://www.0pdd.com/svg?name=yegor256/cactoos)](http://www.0pdd.com/p?name=yegor256/cactoos)
[![Maven Central](https://img.shields.io/maven-central/v/org.cactoos/cactoos.svg)](https://maven-badges.herokuapp.com/maven-central/org.cactoos/cactoos)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/yegor256/cactoos/blob/master/LICENSE.txt)

[![jpeek report](http://i.jpeek.org/org.cactoos/cactoos/badge.svg)](http://i.jpeek.org/org.cactoos/cactoos/)
[![Test Coverage](https://img.shields.io/codecov/c/github/yegor256/cactoos.svg)](https://codecov.io/github/yegor256/cactoos?branch=master)
[![SonarQube](https://img.shields.io/badge/sonar-ok-green.svg)](https://sonarcloud.io/dashboard?id=org.cactoos%3Acactoos)

**ATTENTION**: We're still in a very early alpha version, the API
may and _will_ change frequently. Please, use it at your own risk,
until we release version 1.0. You can view our progress towards
this release [here](https://github.com/yegor256/cactoos/milestone/1).

Cactoos is a collection of object-oriented Java primitives.

**Motivation**.
We are not happy with
[JDK](https://en.wikipedia.org/wiki/Java_Development_Kit),
[Guava](https://github.com/google/guava), and
[Apache Commons](https://commons.apache.org/) because
they are procedural and not object-oriented. They do their job,
but mostly through static methods. Cactoos is suggesting
to do almost exactly the same, but through objects.

**Principles**.
These are the [design principles](http://www.elegantobjects.org#principles) behind Cactoos.

**How to use**.
The library has no dependencies. All you need is this
(get the latest version [here](https://github.com/yegor256/cactoos/releases)):

Maven:
```xml
<dependency>
  <groupId>org.cactoos</groupId>
  <artifactId>cactoos</artifactId>
</dependency>
```

Gradle:
```groovy
dependencies {
    compile 'org.cactoos:cactoos:<version>'
}
```

Java version required: 1.8+.

StackOverflow tag is [cactoos](https://stackoverflow.com/questions/tagged/cactoos).

## Input/Output

More about it here:
[Object-Oriented Declarative Input/Output in Cactoos](http://www.yegor256.com/2017/06/22/object-oriented-input-output-in-cactoos.html).

To read a text file in UTF-8:

```java
String text = new TextOf(
  new File("/code/a.txt")
).asString();
```

To write a text into a file:

```java
new LengthOf(
  new TeeInput(
    "Hello, world!",
    new File("/code/a.txt")
  )
).intValue();
```

To read a binary file from classpath:

```java
byte[] data = new BytesOf(
  new ResourceOf("foo/img.jpg")
).asBytes();
```

## Text/Strings

To format a text:

```java
String text = new FormattedText(
  "How are you, %s?",
  name
).asString();
```

To manipulate with a text:

```java
// To lower case
new LowerText(
	new TextOf("Hello")
);
// To upper case
new UpperText(
	new TextOf("Hello")
);
```

## Iterables/Collections/Lists/Sets

More about it here: [Lazy Loading and Caching via Sticky Cactoos Primitives](http://www.yegor256.com/2017/10/17/lazy-loading-caching-sticky-cactoos.html).

To filter a collection:

```java
Collection<String> filtered = new ListOf<>(
  new Filtered<>(
    s -> s.length() > 4,
    new IterableOf<>("hello", "world", "dude")
  )
);
```

To iterate a collection:

```java
new And(
  new Mapped<>(
    new FuncOf<>(
      input -> {
        System.out.printf("Item: %s\n", input);
      }
    ),
    new IterableOf<>("how", "are", "you")
  )
).value();
```

Or even more compact:

```java
new And(
  (String input) -> System.out.printf("Item: %s\n", input),
  "how", "are", "you"
).value();
```

To sort a list of words in the file:

```java
List<String> sorted = new ListOf<>(
  new Sorted<>(
    new SplitText(
      new TextOf(
        new File("/tmp/names.txt")
      ),
      new TextOf("\\s+")
    )
  )
);
```

To count elements in an iterable:

```java
int total = new LengthOf(
  "how", "are", "you"
).intValue();
```

## Funcs and Procs

This is a traditional `foreach` loop:

```java
for (String name : names) {
  System.out.printf("Hello, %s!\n", name);
}
```

This is its object-oriented alternative (no streams!):

```java
new And(
  names,
  n -> {
    System.out.printf("Hello, %s!\n", n);
  }
).value();
```

This is an endless `while/do` loop:

```java
while (!ready) {
  System.out.prinln("Still waiting...");
}
```

Here is its object-oriented alternative:

```java
new And(
  new Endless<>(ready),
  ready -> {
    System.out.println("Still waiting...");
    return !ready;
  }
).value();
```

## Dates and Times
From our `org.cactoos.time` package.

Our classes are divided in two groups: those that parse strings into date/time objects, and those that format those objects into strings.

For example, this is the traditional way of parsing a string into an [OffsetDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/OffsetDateTime.html):

```java
final OffsetDateTime date = OffsetDateTime.parse("2007-12-03T10:15:30+01:00");
```

Here is its object-oriented alternative (no static method calls!) using `OffsetDateTimeOf`, which is a `Scalar`:

```java
final OffsetDateTime date = new OffsetDateTimeOf("2007-12-03T10:15:30+01:00").value();
```

To format an `OffsetDateTime` into a `Text`:

```java
final OffsetDateTime date = ...;
final OffsetDateTimeAsText text = new OffsetDateTimeAsText(date);
```

## Our objects vs. their static methods

Cactoos | Guava | Apache Commons | JDK 8
------ | ------ | ------ | ------
`And` | `Iterables.all()` | - | -
`Filtered` | `Iterables.filter()` | ? | -
`FormattedText` | - | - | `String.format()`
`IsBlank` | - | `StringUtils.isBlank()`| -
`JoinedText` | - | - | `String.join()`
`LengthOf` | - | - | `String#length()`
`LowerText` | - | - | `String#toLowerCase()`
`NormalizedText` | - | `StringUtils.normalize()` | -
`Or` | `Iterables.any()` | - | -
`RepeatedText` | - | `StringUtils.repeat()` | -
`ReplacedText` | - | - | `String#replace()`
`ReversedText` | - | - | `StringBuilder#reverse()`
`RotatedText` | - | `StringUtils.rotate()`| -
`SplitText` | - | - | `String#split()`
`StickyList` | `Lists.newArrayList()` | ? | `Arrays.asList()`
`SubText` | - | - | `String#substring()`
`SwappedCaseText` | - | `StringUtils.swapCase()` | -
`TextOf` | ? | `IOUtils.toString()` | -
`TrimmedLeftText` | - | `StringUtils.stripStart()` | -
`TrimmedRightText` | - | `StringUtils.stripEnd()` | -
`TrimmedText` | - | `StringUtils.stripAll()` | `String#trim()`
`UpperText` | - | - | `String#toUpperCase()`

## Questions

Ask your questions related to cactoos library on [Stackoverflow](https://stackoverflow.com/questions/ask) with [cactoos](https://stackoverflow.com/tags/cactoos/info) tag.

## How to contribute?

Just fork the repo and send us a pull request.

Make sure your branch builds without any warnings/issues:

```
mvn clean install -Pqulice
```

Note: [Checkstyle](https://en.wikipedia.org/wiki/Checkstyle) is used as a static code analyze tool with
[checks list](http://checkstyle.sourceforge.net/checks.html) in GitHub precommits.

## Contributors

  - [@yegor256](https://github.com/yegor256) as Yegor Bugayenko ([Blog](http://www.yegor256.com))
  - [@g4s8](https://github.com/g4s8) as Kirill Che. (g4s8.public@gmail.com)
  - [@fabriciofx](https://github.com/fabriciofx) as Fabrício Cabral
  - [@englishman](https://github.com/englishman) as Andriy Kryvtsun
  - [@VsSekorin](https://github.com/VsSekorin) as Vseslav Sekorin
  - [@DronMDF](https://github.com/DronMDF) as Andrey Valyaev
  - [@dusan-rychnovsky](https://github.com/dusan-rychnovsky) as Dušan Rychnovský ([Blog](http://blog.dusanrychnovsky.cz/))
  - [@timmeey](https://github.com/timmeey) as Tim Hinkes ([Blog](https://blog.timmeey.de))
  - [@alex-semenyuk](https://github.com/alex-semenyuk) as Alexey Semenyuk
  - [@smallcreep](https://github.com/smallcreep) as Ilia Rogozhin
  - [@memoyil](https://github.com/memoyil) as Mehmet Yildirim
  - [@llorllale](https://github.com/llorllale) as George Aristy
  - [@driver733](https://github.com/driver733) as Mikhail Yakushin
  - [@izrik](https://github.com/izrik) as Richard Sartor
  - [@Vatavuk](https://github.com/Vatavuk) as Vedran Grgo Vatavuk
  - [@dgroup](https://github.com/dgroup) as Yurii Dubinka

## License (MIT)

Copyright (c) 2017-2018 Yegor Bugayenko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
