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
package org.cactoos.number;

import java.util.Collection;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link SumOf}.
 *
 * @since 0.9
 * @todo #1335:30min Following the rewrite of the Number implementations
 *  in cactoos, the three methods below prefixed by overflow where adapted
 *  because they started to give different results. The task is to investigate
 *  thoroughly what is a "correct" behaviour concerning overflow in the case
 *  of SumOf and adapt the tests and the code to make this obvious and clear.
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class SumOfTest {

    @Test
    void withListOfNumbersInt() {
        MatcherAssert.assertThat(
            new SumOf(1, 2, 3).intValue(),
            new IsEqual<>(6)
        );
    }

    @Test
    void withListOfNumbersDouble() {
        MatcherAssert.assertThat(
            new SumOf(1.0d, 2.0d, 3.0d).doubleValue(),
            new IsEqual<>(6.0d)
        );
    }

    @Test
    void withListOfNumbersFloat() {
        MatcherAssert.assertThat(
            new SumOf(1.0f, 2.0f, 3.0f).floatValue(),
            new IsEqual<>(6.0f)
        );
    }

    @Test
    void withListOfNumbersLong() {
        MatcherAssert.assertThat(
            new SumOf(1L, 2L, 3L).longValue(),
            new IsEqual<>(6L)
        );
    }

    @Test
    void withCollectionLong() {
        MatcherAssert.assertThat(
            new SumOf(
                new IterableOf<>(1, 2, 3, 4)
            ).longValue(),
            new IsEqual<>(10L)
        );
    }

    @Test
    void withCollectionInt() {
        MatcherAssert.assertThat(
            new SumOf(
                new IterableOf<>(1L, 2L, 3L, 4L)
            ).intValue(),
            new IsEqual<>(10)
        );
    }

    @Test
    void withCollectionFloat() {
        MatcherAssert.assertThat(
            new SumOf(
                new IterableOf<>(1.0f, 2.0f, 3.0f, 4.0f)
            ).floatValue(),
            new IsEqual<>(10.0f)
        );
    }

    @Test
    void withCollectionDouble() {
        MatcherAssert.assertThat(
            new SumOf(
                new IterableOf<>(1.0d, 2.0d, 3.0d, 4.0d)
            ).doubleValue(),
            new IsEqual<>(10.0d)
        );
    }

    @Test
    void withIterableOfInts() {
        final Collection<Integer> ints = new ListOf<>(1, 2, 3, 4);
        MatcherAssert.assertThat(
            new SumOf(ints).intValue(),
            new IsEqual<>(10)
        );
    }

    @Test
    void overflowIntFromLongValues() {
        MatcherAssert.assertThat(
            new SumOf(2_147_483_647L + 1L << 1, 10L).intValue(),
            new IsEqual<>(10)
        );
    }

    @Test
    void overflowIntFromLongValuesIncludingNegative() {
        MatcherAssert.assertThat(
            new SumOf(
                2_147_483_647L + 1L << 1,
                10L,
                -(2_147_483_647L + 1L) << 1
            ).intValue(),
            new IsEqual<>(10)
        );
    }

    @Test
    void overflowFloatFromLongValues() {
        MatcherAssert.assertThat(
            new SumOf(2_147_483_647L + 1L << 1, 10L).floatValue(),
            new IsEqual<>(4_294_967_300.0f)
        );
    }
}
