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
package org.cactoos.scalar;

import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link MultiplicationOf}.
 *
 * @since 0.49.2
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class MultiplicationOfTest {

    /**
     * Ensures that multiplication of int numbers return proper value.
     */
    @Test
    public void withListOfNumbersInt() {
        MatcherAssert.assertThat(
            new MultiplicationOf(2, 3).intValue(),
            new IsEqual<>(6)
        );
    }

    /**
     * Ensures that multiplication of double numbers return proper value.
     */
    @Test
    public void withListOfNumbersDouble() {
        MatcherAssert.assertThat(
            new MultiplicationOf(2.0d, 2.5d, 3.0d).doubleValue(),
            new IsEqual<>(15.0d)
        );
    }

    /**
     * Ensures that multiplication of float numbers return proper value.
     */
    @Test
    public void withListOfNumbersFloat() {
        MatcherAssert.assertThat(
            new MultiplicationOf(3.0f, 3.0f, 3.0f).floatValue(),
            new IsEqual<>(27.0f)
        );
    }

    /**
     * Ensures that multiplication of long numbers return proper value.
     */
    @Test
    public void withListOfNumbersLong() {
        MatcherAssert.assertThat(
            new MultiplicationOf(2L, 3L, 2L).longValue(),
            new IsEqual<>(12L)
        );
    }

    /**
     * Ensures that multiplication of int numbers iterable return proper value.
     */
    @Test
    public void withIterableInt() {
        MatcherAssert.assertThat(
            new MultiplicationOf(
                new IterableOf<>(2L, 3L, 3L)
            ).intValue(),
            new IsEqual<>(18)
        );
    }

    /**
     * Ensures that multiplication of long numbers iterable return proper value.
     */
    @Test
    public void withIterableLong() {
        MatcherAssert.assertThat(
            new MultiplicationOf(
                new IterableOf<>(1, 2, 3)
            ).longValue(),
            new IsEqual<>(6L)
        );
    }

    /**
     * Ensures that multiplication of float numbers iterable
     * return proper value.
     */
    @Test
    public void withIterableFloat() {
        MatcherAssert.assertThat(
            new MultiplicationOf(
                new IterableOf<>(0.5f, 2.0f, 10.0f)
            ).floatValue(),
            new IsEqual<>(10.0f)
        );
    }

    /**
     * Ensures that multiplication of double numbers iterable
     * return proper value.
     */
    @Test
    public void withIterableDouble() {
        MatcherAssert.assertThat(
            new MultiplicationOf(
                new IterableOf<>(1.5d, 2.0d, 5.0d)
            ).doubleValue(),
            new IsEqual<>(15.0d)
        );
    }

    /**
     * Ensures that empty iterable will not be multiplied.
     */
    @Test(expected = IllegalArgumentException.class)
    public void rejectsEmptyIterable() {
        new MultiplicationOf(new IterableOf<>()).doubleValue();
    }
}
