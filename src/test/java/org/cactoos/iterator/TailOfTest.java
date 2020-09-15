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
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link TailOf}.
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
final class TailOfTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void tailIterator() {
        new Assertion<>(
            "Must get tail portion of iterator",
            new IterableOf<>(
                new TailOf<>(
                    2,
                    new IteratorOf<>(
                        "one", "two", "three", "four"
                    )
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    "three",
                    "four"
                )
            )
        ).affirm();
    }

    @Test
    void returnsIntactIterator() {
        new Assertion<>(
            "Must return an intact iterator",
            new IterableOf<>(
                new TailOf<>(
                    3,
                    new IteratorOf<>(
                        "one", "two"
                    )
                )
            ),
            new HasSize(2)
        ).affirm();
    }

    @Test
    void returnsEmptyIterator() {
        new Assertion<>(
            "Must throw an exception if empty",
            () -> new TailOf<>(
                0,
                new IteratorOf<>(
                    "one", "two"
                )
            ).next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    void emptyIteratorForNegativeSize() {
        new Assertion<>(
            "Must throw an exception for negative size",
            () -> new TailOf<>(
                -1,
                new IteratorOf<>(
                    "one", "two"
                )
            ).next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }
}
