<img src="http://cf.jare.io/?u=http%3A%2F%2Fwww.yegor256.com%2Fimages%2Fbooks%2Felegant-objects%2Fcactus.svg" height="100px" />

[![DevOps By Rultor.com](http://www.rultor.com/b/yegor256/cactoos)](http://www.rultor.com/p/yegor256/cactoos)

[![Build Status](https://travis-ci.org/yegor256/cactoos.svg?branch=master)](https://travis-ci.org/yegor256/cactoos)
[![Build status](https://ci.appveyor.com/api/projects/status/8vs8huy61og6jwif?svg=true)](https://ci.appveyor.com/project/yegor256/cactoos)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/org.cactoos/cactoos/badge.svg?color=blue&prefix=v)](http://www.javadoc.io/doc/org.cactoos/cactoos)
[![PDD status](http://www.0pdd.com/svg?name=yegor256/cactoos)](http://www.0pdd.com/p?name=yegor256/cactoos)
[![Test Coverage](https://img.shields.io/codecov/c/github/yegor256/cactoos.svg)](https://codecov.io/github/yegor256/cactoos?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/org.cactoos/cactoos.svg)](https://maven-badges.herokuapp.com/maven-central/org.cactoos/cactoos)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/yegor256/cactoos/blob/master/LICENSE.txt)

**ATTENTION**: We're still in a very early alpha version, the API
may and _will_ change frequently. Please, use it at your own risk,
until we release version 1.0 (July 2017).

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
There are a few design principles behind Cactoos:

  * No `null` ([why?](http://www.yegor256.com/2014/05/13/why-null-is-bad.html))
  * No code in constructors ([why?](http://www.yegor256.com/2015/05/07/ctors-must-be-code-free.html))
  * No getters and setters ([why?](http://www.yegor256.com/2014/09/16/getters-and-setters-are-evil.html))
  * No mutable objects ([why?](http://www.yegor256.com/2014/06/09/objects-should-be-immutable.html))
  * No `static` methods, not even `private` ones ([why?](http://www.yegor256.com/2017/02/07/private-method-is-new-class.html))
  * No `instanceof`, type casting, or reflection ([why?](http://www.yegor256.com/2015/04/02/class-casting-is-anti-pattern.html))
  * No implementation inheritance ([why?](http://www.yegor256.com/2016/09/13/inheritance-is-procedural.html))
  * No public methods without `@Override`
  * No statements in test methods except `assertThat` ([why?](http://www.yegor256.com/2017/05/17/single-statement-unit-tests.html))

**How to use**.
The library has no dependencies. All you need is this
(get the latest version [here](https://github.com/yegor256/cactoos/releases)):

```xml
<dependency>
  <groupId>org.cactoos</groupId>
  <artifactId>cactoos</artifactId>
</dependency>
```

Java version required: 1.8+.

## Input/Output

To read a text file in UTF-8:

```java
String text = new BytesAsText(
  new InputAsBytes(
    new FileAsInput(
      new File("/code/a.txt")
    )
  )
).asString();
```

To write a text into a file:

```java
new LengthOfInput(
  new TeeInput(
    new BytesAsInput(
      new TextAsBytes(
        new StringAsText("Hello, world!")
      )
    ),
    new FileAsOutput(
      new File("/code/a.txt")
    )
  )
).asValue();
```

To read a binary file from classpath:

```java
byte[] data = new InputAsBytes(
  new ResourceAsInput("foo/img.jpg")
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
new LowerText("Hello");
// To upper case
new UpperText("Hello");
```

## Iterables/Collections/Lists/Sets

To filter a collection:

```java
Collection<String> filtered = new IterableAsCollection<>(
  new FilteredIterable<>(
    new ArrayAsIterable<>("hello", "world", "dude"),
    new Func<String, Boolean>() {
      @Override
      public boolean apply(String i) {
        return i.length() > 4;
      }
    }
  )
);
```

With Lambda:

```java
new IterableAsCollection<>(
  new FilteredIterable<>(
    new ArrayAsIterable<>("hello", "world", "dude"),
    i -> i.length() > 4
  )
);
```

To iterate a collection:

```java
new AllOf(
  new TransformedIterable<>(
    new ArrayAsIterable<>("how", "are", "you"),
    new ProcAsFunc<>(
      input -> {
        System.out.printf("Item: %s\n", input);
      }
    )
  )
).asValue();
```

Or even more compact:

```java
new IterableAsBoolean(
  new ArrayAsIterable<>("how", "are", "you"),
  new ProcAsFunc<>(
    input -> System.out.printf("Item: %s\n", i)
  )
).asValue();
```

To sort a list of words in the file:

```java
List<String> sorted = new SortedList<>(
  new IterableAsList<>(
    new TextAsLines(
      new InputAsText(
        new FileAsInput(
          new File("/tmp/names.txt")
        )
      )
    )
  )
);
```

To count elements in an iterable:

```java
int total = new LengthOfIterable(
  new ArrayAsIterable<>("how", "are", "you")
).asValue();
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
new IterableAsBoolean<>(
  names,
  new ProcAsFunc<>(
    n -> {
      System.out.printf("Hello, %s!\n", n);
    }
  )
).asValue();
```

This is an endless `while/do` loop:

```java
while (!ready) {
  System.out.prinln("Still waiting...");
}
```

Here is its object-oriented alternative:

```java
new IterableAsBoolean<>(
  new EndlessIterable<>(ready),
  r -> {
    System.out.prinln("Still waiting...");
    return !ready;
  }
).asValue();
```

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


## License (MIT)

Copyright (c) 2017 Yegor Bugayenko

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
