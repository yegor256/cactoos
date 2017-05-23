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
they are procedural and not object-oriented. They do their job,
but mostly through static methods. Cactoos is suggesting
to do almost exactly the same, but through objects.

**Principles**.
There are a few design principles behind Cactoos:

  * No `null`
  * No code in constructors
  * No `static` methods (even `private` ones)
  * No public methods without `@Override`

**How to use**.
The library has no dependencies. All you need is this:

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
import java.io.File;
import org.cactoos.io.InputAsString;
import org.cactoos.io.InputFile;
CharSequence text = new InputAsString(
  new InputFile(
    new File("/code/a.txt")
  )
);
```

To write a file:

```java
import java.io.File;
import org.cactoos.io.InputString;
import org.cactoos.io.OutputFile;
import org.cactoos.io.Pipe;
new Pipe(
  new OutputFile(
    new File("/code/a.txt")
  ),
  new InputString("Hello, world!")
).push();
```

To read a binary file from classpath:

```java
byte[] data = new InputAsBytes(
  new InputURL(
    this.getClass().getResource("/foo/img.jpg")
  )
).take();
```

## Strings

To format a text:

```java
import org.cactoos.strings.Sprintf;
CharSequence text = new Sprintf(
  "How are you, %s?", name
);
```

## Iterables/Collections/Lists/Sets

To filter a collection:

```java
import java.util.Arrays;
import java.util.Collection;
import org.cactoos.lists.ArrayAsIterable;
import org.cactoos.lists.Filtered;
import org.cactoos.lists.IterableAsCollection;
Collection<String> filtered = new IterableAsCollection(
  new Filtered<>(
    new ArrayAsIterable("hello", "world", "dude"),
    i -> i.length() > 4
  )
);
```

To iterate a collection:

```java
import org.cactoos.lists.ArrayAsIterable;
import org.cactoos.lists.ForEach;
new ForEach(
  new ArrayAsIterable("how", "are", "you"),
  i -> System.out.printf("Item: %s\n", i)
).run();
```

To sort a list of words in the file:

```java
List<String> sorted = new IterableAsList(
  new Sorted<>(
    new TextAsLines(
      new InputAsString(
        new FileInput(
          new File("/tmp/names.txt")
        )
      )
    )
  )
);
```

## How to contribute?

Just fork the repo and send us a pull request.

Make sure your branch builds without any warnings/issues:

```
mvn clean install -Pqulice
```

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
