/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link TailOf}.
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class TailOfTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void tailIterator() throws Exception {
        MatcherAssert.assertThat(
            "Can't get tail portion of iterator",
            () -> new TailOf<>(
                2,
                new IterableOf<>(
                    "one", "two", "three", "four"
                ).iterator()
            ),
            Matchers.contains(
                "three",
                "four"
            )
        );
    }

    @Test
    public void returnsIntactIterator() throws Exception {
        MatcherAssert.assertThat(
            new LengthOf(
                new TailOf<>(
                    3,
                    new IterableOf<>(
                        "one", "two"
                    ).iterator()
                )
            ).intValue(),
            Matchers.equalTo(2)
        );
    }

    @Test(expected = NoSuchElementException.class)
    public void returnsEmptyIterator() throws Exception {
        new TailOf<>(
            0,
            new IterableOf<>(
                "one", "two"
            ).iterator()
        ).next();
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyIteratorForNegativeSize() throws Exception {
        new TailOf<>(
            -1,
            new IterableOf<>(
                "one", "two"
            ).iterator()
        ).next();
    }
}
