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
package org.cactoos.scalar;

import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link SumOf}.
 *
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class SumOfTest {

    @Test
    public void withListOfNumbersInt() {
        MatcherAssert.assertThat(
            new SumOf(1, 2, 3).intValue(),
            new IsEqual<>(6)
        );
    }

    @Test
    public void withListOfNumbersDouble() {
        MatcherAssert.assertThat(
            new SumOf(1.0d, 2.0d, 3.0d).doubleValue(),
            new IsEqual<>(6.0d)
        );
    }

    @Test
    public void withListOfNumbersFloat() {
        MatcherAssert.assertThat(
            new SumOf(1.0f, 2.0f, 3.0f).floatValue(),
            new IsEqual<>(6.0f)
        );
    }

    @Test
    public void withListOfNumbersLong() {
        MatcherAssert.assertThat(
            new SumOf(1L, 2L, 3L).longValue(),
            new IsEqual<>(6L)
        );
    }

    @Test
    public void withCollectionLong() {
        MatcherAssert.assertThat(
            new SumOf(
                new IterableOf<>(1, 2, 3, 4)
            ).longValue(),
            new IsEqual<>(10L)
        );
    }
    @Test
    public void withCollectionInt() {
        MatcherAssert.assertThat(
            new SumOf(
                new IterableOf<>(1L, 2L, 3L, 4L)
            ).intValue(),
            new IsEqual<>(10)
        );
    }
    @Test
    public void withCollectionFloat() {
        MatcherAssert.assertThat(
            new SumOf(
                new IterableOf<>(1.0f, 2.0f, 3.0f, 4.0f)
            ).floatValue(),
            new IsEqual<>(10.0f)
        );
    }
    @Test
    public void withCollectionDouble() {
        MatcherAssert.assertThat(
            new SumOf(
                new IterableOf<>(1.0d, 2.0d, 3.0d, 4.0d)
            ).doubleValue(),
            new IsEqual<>(10.0d)
        );
    }

    @Test
    public void overflowIntFromLongValues() {
        MatcherAssert.assertThat(
            new SumOf((Integer.MAX_VALUE + 1L) * 2L, 10L).intValue(),
            new IsEqual<>(2147483647)
        );
    }

    @Test
    public void overflowIntFromLongValuesIncludingNegative() {
        MatcherAssert.assertThat(
            new SumOf(
                (Integer.MAX_VALUE + 1L) * 2L,
                10L,
                -(Integer.MAX_VALUE + 1L) * 2L
            ).intValue(),
            new IsEqual<>(10)
        );
    }

    @Test
    public void overflowFloatFromLongValues() {
        MatcherAssert.assertThat(
            new SumOf((Integer.MAX_VALUE + 1L) * 2L, 10L).floatValue(),
            new IsEqual<>(4294967300f)
        );
    }

}
