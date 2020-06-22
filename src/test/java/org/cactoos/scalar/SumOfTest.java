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
package org.cactoos.scalar;

import java.util.Collection;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

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
        new Assertion<>(
            "",
            new SumOf(1, 2, 3).intValue(),
            new IsEqual<Integer>(6)
        ).affirm();
    }

    @Test
    public void withListOfNumbersDouble() {
        new Assertion<>(
            "",
            new SumOf(1.0d, 2.0d, 3.0d).doubleValue(),
            new IsEqual<Double>(6.0d)
        ).affirm();
    }

    @Test
    public void withListOfNumbersFloat() {
        new Assertion<>(
            "",
            new SumOf(1.0f, 2.0f, 3.0f).floatValue(),
            new IsEqual<Float>(6.0f)
        ).affirm();
    }

    @Test
    public void withListOfNumbersLong() {
        new Assertion<>(
            "",
            new SumOf(1L, 2L, 3L).longValue(),
            new IsEqual<Long>(6L)
        ).affirm();
    }

    @Test
    public void withCollectionLong() {
        new Assertion<>(
            "",
            new SumOf(
                new IterableOf<>(1, 2, 3, 4)
            ).longValue(),
            new IsEqual<Long>(10L)
        ).affirm();
    }

    @Test
    public void withCollectionInt() {
        new Assertion<>(
            "",
            new SumOf(
                new IterableOf<>(1L, 2L, 3L, 4L)
            ).intValue(),
            new IsEqual<Integer>(10)
        ).affirm();
    }

    @Test
    public void withCollectionFloat() {
        new Assertion<>(
            "",
            new SumOf(
                new IterableOf<>(1.0f, 2.0f, 3.0f, 4.0f)
            ).floatValue(),
            new IsEqual<Float>(10.0f)
        ).affirm();
    }

    @Test
    public void withCollectionDouble() {
        new Assertion<>(
            "",
            new SumOf(
                new IterableOf<>(1.0d, 2.0d, 3.0d, 4.0d)
            ).doubleValue(),
            new IsEqual<Double>(10.0d)
        ).affirm();
    }

    @Test
    public void withIterableOfInts() {
        final Collection<Integer> ints = new ListOf<>(1, 2, 3, 4);
        new Assertion<>(
            "",
            new SumOf(ints).intValue(),
            new IsEqual<Integer>(10)
        ).affirm();
    }

    @Test
    public void overflowIntFromLongValues() {
        new Assertion<>(
            "",
            new SumOf(2_147_483_647L + 1L << 1, 10L).intValue(),
            new IsEqual<Integer>(2_147_483_647)
        ).affirm();
    }

    @Test
    public void overflowIntFromLongValuesIncludingNegative() {
        new Assertion<>(
            "",
            new SumOf(
                2_147_483_647L + 1L << 1,
                10L,
                -(2_147_483_647L + 1L) << 1
            ).intValue(),
            new IsEqual<Integer>(10)
        ).affirm();
    }

    @Test
    public void overflowFloatFromLongValues() {
        new Assertion<>(
            "",
            new SumOf(2_147_483_647L + 1L << 1, 10L).floatValue(),
            new IsEqual<Float>(4_294_967_300.0f)
        ).affirm();
    }
}
