<img src="http://cf.jare.io/?u=http%3A%2F%2Fwww.yegor256.com%2Fimages%2Fbooks%2Felegant-objects%2Fcactus.svg" height="100px" />

[![DevOps By Rultor.com](http://www.rultor.com/b/yegor256/cactoos)](http://www.rultor.com/p/yegor256/cactoos)

[![Build Status](https://travis-ci.org/yegor256/cactoos.svg?branch=master)](https://travis-ci.org/yegor256/cactoos)
[![PDD status](http://www.0pdd.com/svg?name=yegor256/cactoos)](http://www.0pdd.com/p?name=yegor256/cactoos)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/yegor256/takes/blob/master/LICENSE.txt)

Cactoos is a collection of object-oriented Java primitives.

**Motivation**.
We are not happy with
[JDK](https://en.wikipedia.org/wiki/Java_Development_Kit),
[Guava](https://github.com/google/guava), and
[Apache Commons](https://commons.apache.org/) because
they are procedural, not object-oriented. They do their job,
but mostly through static methods. Cactoos is suggesting
to do almost exactly the same, but through objects.

**Principles**.
There are a few design principles behind Cactoos:

  * No `null`
  * No code in constructors
  * No `static` methods (even `private` ones)
  * No public methods without `@Override`

**How to use**.
The library has no dependencies. All you need to do is this:

```xml
<dependency>
  <groupId>org.cactoos</groupId>
  <artifactId>cactoos</artifactId>
  <version>0.1</version>
</dependency>
```

## Input/Output

To read a file:

```java
CharSequence text = new InputAsString(
  new InputFile(
    new File("/code/a.txt")
  )
);
```

To write a file:

```java
new Pipe(
  new OutputFile(
    new File("/code/a.txt")
  ),
  new InputString("Hello, world!")
).push();
```

## Strings

TBD...

## Collections

TBD...
