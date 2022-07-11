/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.iterable;

import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Tests for {@link MappedWithIndex}.
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 lines)
 */
final class MappedWithIndexTest {
    @Test
    void transformsIterable() throws Exception {
        new Assertion<>(
            "must transform an iterable",
            new MappedWithIndex<>(
                (input, index) -> new Joined(
                    "-",
                    new TextOf(index.toString()),
                    new TextOf(input)
                ),
                new IterableOf<>(
                    "hello", "world"
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    new TextOf("0-hello"),
                    new TextOf("1-world")
                )
            )
        ).affirm();
    }

    @Test
    void transformsEmptyIterable() {
        new Assertion<>(
            "must transform an empty iterable",
            new MappedWithIndex<>(
                (input, index) -> {
                    Assertions.fail("must do not be executed");
                    return input;
                },
                new IterableOf<>()
            ),
            new IsEqual<>(
                new IterableOf<>()
            )
        ).affirm();
    }

    @Test
    void string() {
        new Assertion<>(
            "must convert to string",
            new MappedWithIndex<>(
                (x, index) -> x * index * 2,
                new IterableOf<>(1, 2, 3)
            ).toString(),
            new IsEqual<>("0, 4, 12")
        ).affirm();
    }

    @Test
    void transformsArray() {
        new Assertion<>(
            "Transforms an array",
            new MappedWithIndex<>(
                (input, index) -> new Joined(
                    "-",
                    index.toString(),
                    input
                ),
                "a", "b", "c"
            ),
            new IsEqual<>(
                new IterableOf<>(
                    new TextOf("0-a"),
                    new TextOf("1-b"),
                    new TextOf("2-c")
                )
            )
        ).affirm();
    }
}
