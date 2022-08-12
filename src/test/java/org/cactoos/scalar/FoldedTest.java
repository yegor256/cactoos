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
package org.cactoos.scalar;

import org.cactoos.iterable.HeadOf;
import org.cactoos.iterable.RangeOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Folded}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class FoldedTest {

    @Test
    void skipIterable() throws Exception {
        new Assertion<>(
            "Must fold elements in iterable",
            new Folded<>(
                0L, Long::sum,
                new HeadOf<>(
                    10,
                    new RangeOf<>(0L, Long.MAX_VALUE, value -> ++value)
                )
            ),
            new HasValue<>(45L)
        ).affirm();
    }

    @Test
    void constructedFromVarargs() throws Exception {
        new Assertion<>(
            "Must fold elements in vararg array",
            new Folded<>(
                0L,
                (first, second) -> first + second,
                1, 2, 3, 4, 5
            ).value(),
            new IsEqual<>(15L)
        ).affirm();
    }
}
