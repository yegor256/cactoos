/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import org.cactoos.iterable.IterableOf;
import org.cactoos.text.FormattedText;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test for {@link Sliced}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class SlicedTest {

    @Test
    public void sliceTheMiddle() throws Exception {
        final Iterable<Integer> expected = new IterableOf<>(
            4, 5
        );
        new Assertion<>(
            new FormattedText(
                "Must return sliced iterator of [%s]",
                expected
            ).asString(),
            new IterableOf<>(
                new Sliced<>(
                    3,
                    2,
                    new IteratorOf<>(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
                    )
                )
            ),
            new IsEqual<>(
                expected
            )
        ).affirm();
    }

    @Test
    public void sliceTheHead() throws Exception {
        final Iterable<Integer> expected = new IterableOf<>(
            1, 2, 3, 4, 5
        );
        new Assertion<>(
            new FormattedText(
                "Must return iterator with the head elements of [%s]",
                expected
            ).asString(),
            new IterableOf<>(
                new Sliced<>(
                    0,
                    5,
                    new IteratorOf<>(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
                    )
                )
            ),
            new IsEqual<>(
                expected
            )
        ).affirm();
    }

    @Test
    public void sliceTheWholeTail() throws Exception {
        final Iterable<Integer> expected = new IterableOf<>(
            6, 7, 8, 9, 0
        );
        new Assertion<>(
            new FormattedText(
                "Must return the iterator of tail elements of [%s]",
                expected
            ).asString(),
            new IterableOf<>(
                new Sliced<>(
                    5,
                    100,
                    new IteratorOf<>(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
                    )
                )
            ),
            new IsEqual<>(
                expected
            )
        ).affirm();
    }
}
