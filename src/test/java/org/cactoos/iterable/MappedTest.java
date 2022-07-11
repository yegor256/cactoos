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

import java.util.Collections;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.Upper;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Mapped}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
final class MappedTest {

    @Test
    void transformsList() throws Exception {
        new Assertion<>(
            "Must transform an iterable",
            new Mapped<>(
                input -> new Upper(new TextOf(input)),
                new IterableOf<>(
                    "hello", "world", "друг"
                )
            ).iterator().next(),
            new IsText("HELLO")
        ).affirm();
    }

    @Test
    void transformsEmptyList() {
        new Assertion<>(
            "Must transform an empty iterable",
            new Mapped<>(
                (String input) -> new Upper(new TextOf(input)),
                Collections.emptyList()
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    void string() {
        new Assertion<>(
            "Must convert to string",
            new Mapped<>(
                x -> x * 2,
                new ListOf<>(1, 2, 3)
            ).toString(),
            new IsEqual<>("2, 4, 6")
        ).affirm();
    }

    @Test
    void transformsArray() {
        new Assertion<>(
            "Transforms an array",
            new Mapped<>(
                input -> new Upper(new TextOf(input)).asString(),
                "a", "b", "c"
            ),
            new IsEqual<>(new IterableOf<>("A", "B", "C"))
        ).affirm();
    }
}
