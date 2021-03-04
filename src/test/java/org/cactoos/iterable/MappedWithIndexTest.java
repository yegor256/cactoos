/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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

import java.util.Collections;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.cactoos.text.Upper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link MappedWithIndex}.
 * @since 0.50
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
final class MappedWithIndexTest {
    @Test
    void transformsList() throws Exception {
        MatcherAssert.assertThat(
            "Can't transform an iterable",
            new MappedWithIndex<>(
                (index, input) -> new Upper(
                    new Joined(
                        "-",
                        new TextOf(index.toString()),
                        new TextOf(input)
                    )
                ),
                new IterableOf<>(
                    "hello", "world", "друг"
                )
            ).iterator().next().asString(),
            Matchers.equalTo("0-HELLO")
        );
    }

    @Test
    void transformsEmptyList() {
        MatcherAssert.assertThat(
            "Can't transform an empty iterable",
            new MappedWithIndex<>(
                (index, input) -> {
                    Assertions.fail("must do not be executed");
                    return input;
                },
                Collections.emptyList()
            ),
            Matchers.emptyIterable()
        );
    }

    @Test
    void string() {
        MatcherAssert.assertThat(
            "Can't convert to string",
            new MappedWithIndex<>(
                (index, x) -> x * index * 2,
                new IterableOf<>(1, 2, 3)
            ).toString(),
            Matchers.equalTo("0, 4, 12")
        );
    }

    @Test
    void transformsArray() {
        new Assertion<>(
            "Transforms an array",
            new MappedWithIndex<>(
                (index, input) -> new Upper(
                    new TextOf(
                        String.format(
                            "%1$s-%2$s",
                            index,
                            input
                        )
                    )
                ).asString(),
                "a", "b", "c"
            ),
            new IsEqual<>(new IterableOf<>("0-A", "1-B", "2-C"))
        ).affirm();
    }
}
