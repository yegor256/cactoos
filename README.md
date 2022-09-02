<img alt="logo" src="https://www.objectionary.com/cactus.svg" height="100px" />

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/yegor256/cactoos)](http://www.rultor.com/p/yegor256/cactoos)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![mvn](https://github.com/yegor256/cactoos/actions/workflows/mvn.yml/badge.svg)](https://github.com/yegor256/cactoos/actions/workflows/mvn.yml)
[![Javadoc](http://www.javadoc.io/badge/org.cactoos/cactoos.svg)](http://www.javadoc.io/doc/org.cactoos/cactoos)
[![PDD status](http://www.0pdd.com/svg?name=yegor256/cactoos)](http://www.0pdd.com/p?name=yegor256/cactoos)
[![Maven Central](https://img.shields.io/maven-central/v/org.cactoos/cactoos.svg)](https://maven-badges.herokuapp.com/maven-central/org.cactoos/cactoos)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/yegor256/cactoos/blob/master/LICENSE.txt)
[![Test Coverage](https://img.shields.io/codecov/c/github/yegor256/cactoos.svg)](https://codecov.io/github/yegor256/cactoos?branch=master)
[![SonarQube](https://img.shields.io/badge/sonar-ok-green.svg)](https://sonarcloud.io/dashboard?id=org.cactoos%3Acactoos)
[![Hits-of-Code](https://hitsofcode.com/github/yegor256/cactoos)](https://hitsofcode.com/view/github/yegor256/cactoos)
![Lines of code](https://img.shields.io/tokei/lines/github/yegor256/cactoos)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/dc71110fe0ac43b8a535eec8b8fec389)](https://www.codacy.com/gh/yegor256/cactoos/dashboard)

Project architect: [@victornoel](https://github.com/victornoel)

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
These are the [design principles](https://www.elegantobjects.org#principles) behind Cactoos.

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
).value();
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
new Lowered(
	new TextOf("Hello")
);
// To upper case
new Upper(
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

To flatten one iterable:
```java
new Joined<>(
  new Mapped<>(
    iter -> new IterableOf<>(
      new ListOf<>(iter).toArray(new Integer[]{})
    ),
    new IterableOf<>(1, 2, 3, 4, 5, 6))
  )
);    // Iterable<Integer>
```

To flatten and join several iterables:
```java
new Joined<>(
  new Mapped<>(
    iter -> new IterableOf<>(
      new ListOf<>(iter).toArray(new Integer[]{})
    ),
    new Joined<>(
      new IterableOf<>(new IterableOf<>(1, 2, 3)),
      new IterableOf<>(new IterableOf<>(4, 5, 6))
    )
  )
);    // Iterable<Integer>
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
    new IterableOf<>("how", "are", "you", "?")
  )
).value();
```

Or even more compact:

```java
new ForEach<String>(
    input -> System.out.printf(
        "Item: %s\n", input
    )
).exec("how", "are", "you", "?");
```

To sort a list of words in the file:

```java
List<Text> sorted = new ListOf<>(
  new Sorted<>(
    new Split(
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
  new IterableOf<>("how", "are", "you")
).intValue();
```

To create a set of elements by providing variable arguments:

```java
final Set<String> unique = new SetOf<String>(
    "one",
    "two",
    "one",
    "three"
);
```

To create a set of elements from existing iterable:
```java
final Set<String> words = new SetOf<>(
    new IterableOf<>("abc", "bcd", "abc", "ccc")
);
```

To create a sorted iterable with unique elements from existing iterable:
```java
final Iterable<String> sorted = new Sorted<>(
    new SetOf<>(
        new IterableOf<>("abc", "bcd", "abc", "ccc")
    )
);
```

To create a sorted set from existing vararg elements using comparator:
```java
final Set<String> sorted = new org.cactoos.set.Sorted<>(
    (first, second) -> first.compareTo(second),
    "abc", "bcd", "abc", "ccc", "acd"
);
```

To create a sorted set from existing iterable using comparator:
```java
final Set<String> sorted = new org.cactoos.set.Sorted<>(
    (first, second) -> first.compareTo(second),
    new IterableOf<>("abc", "bcd", "abc", "ccc", "acd")
);
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
  n -> {
    System.out.printf("Hello, %s!\n", n);
  },
  names
).value();
```

This is an endless `while/do` loop:

```java
while (!ready) {
  System.out.println("Still waiting...");
}
```

Here is its object-oriented alternative:

```java
new And(
  ready -> {
    System.out.println("Still waiting...");
    return !ready;
  },
  new Endless<>(booleanParameter)
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
final String text = new TextOf(date).asString();
```

## Our objects vs. their static methods

Cactoos | Guava | Apache Commons | JDK 8
------ | ------ | ------ | ------
`And` | `Iterables.all()` | - | -
`Filtered` | `Iterables.filter()` | ? | -
`FormattedText` | - | - | `String.format()`
`IsBlank` | - | `StringUtils.isBlank()`| -
`Joined` | - | - | `String.join()`
`LengthOf` | - | - | `String#length()`
`Lowered` | - | - | `String#toLowerCase()`
`Normalized` | - | `StringUtils.normalize()` | -
`Or` | `Iterables.any()` | - | -
`Repeated` | - | `StringUtils.repeat()` | -
`Replaced` | - | - | `String#replace()`
`Reversed` | - | - | `StringBuilder#reverse()`
`Rotated` | - | `StringUtils.rotate()`| -
`Split` | - | - | `String#split()`
`StickyList` | `Lists.newArrayList()` | ? | `Arrays.asList()`
`Sub` | - | - | `String#substring()`
`SwappedCase` | - | `StringUtils.swapCase()` | -
`TextOf` | ? | `IOUtils.toString()` | -
`TrimmedLeft` | - | `StringUtils.stripStart()` | -
`TrimmedRight` | - | `StringUtils.stripEnd()` | -
`Trimmed` | - | `StringUtils.stripAll()` | `String#trim()`
`Upper` | - | - | `String#toUpperCase()`

## Questions

Ask your questions related to cactoos library on [Stackoverflow](https://stackoverflow.com/questions/ask) with [cactoos](https://stackoverflow.com/tags/cactoos/info) tag.

## How to contribute?

Just fork the repo and send us a pull request.

Make sure your branch builds without any warnings/issues:
```
mvn clean verify -Pqulice
```

To run a build similar to the CI with Docker only, use:
```
docker run \
	--tty \
	--interactive \
	--workdir=/main \
	--volume=${PWD}:/main \
	--volume=cactoos-mvn-cache:/root/.m2 \
	--rm \
	maven:3-jdk-8 \
	bash -c "mvn clean install site -Pqulice -Psite --errors; chown -R $(id -u):$(id -g) target/"
```

To remove the cache used by Docker-based build:
```
docker volume rm cactoos-mvn-cache
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
  - [@iakunin](https://github.com/iakunin) as Maksim Iakunin
  - [@fanifieiev](https://github.com/fanifieiev) as Fevzi Anifieiev
  - [@victornoel](https://github.com/victornoel) as Victor Noël
  - [@paulodamaso](https://github.com/paulodamaso) as Paulo Lobo
