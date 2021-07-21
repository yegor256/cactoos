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
package org.cactoos.number;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link MaxOf}.
 *
 * @since 0.10
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class MaxOfTest {

    @Test
    void withPositiveIntegerCollection() {
        new Assertion<>(
            "must select maximum integer of positive integers",
            () -> new MaxOf(1, 2, 3, 4).intValue(),
            new HasValue<>(4)
        ).affirm();
        new Assertion<>(
            "must select maximum long of positive integers",
            () -> new MaxOf(1, 2, 3, 4).longValue(),
            new HasValue<>(4L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of positive integers",
            () -> new MaxOf(1, 2, 3, 4).doubleValue(),
            new HasValue<>(4.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of positive integers",
            () -> new MaxOf(1, 2, 3, 4).floatValue(),
            new HasValue<>(4.0f)
        ).affirm();
    }

    @Test
    void withPositiveLongCollection() {
        new Assertion<>(
            "must select maximum integer of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).intValue(),
            new HasValue<>(4)
        ).affirm();
        new Assertion<>(
            "must select maximum long of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).longValue(),
            new HasValue<>(4L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).doubleValue(),
            new HasValue<>(4.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).floatValue(),
            new HasValue<>(4.0f)
        ).affirm();
    }

    @Test
    void withPositiveDoubleCollection() {
        new Assertion<>(
            "must select maximum integer of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).intValue(),
            new HasValue<>(4)
        ).affirm();
        new Assertion<>(
            "must select maximum long of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).longValue(),
            new HasValue<>(4L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).doubleValue(),
            new HasValue<>(4.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).floatValue(),
            new HasValue<>(4.0f)
        ).affirm();
    }

    @Test
    void withPositiveFloatCollection() {
        new Assertion<>(
            "must select maximum integer of positive floats",
            () -> new MaxOf(1.0f, 2.0f, 3.0f, 4.0f).intValue(),
            new HasValue<>(4)
        ).affirm();
        new Assertion<>(
            "must select maximum long of positive floats",
            () -> new MaxOf(1.0f, 2.0f, 3.0f, 4.0f).longValue(),
            new HasValue<>(4L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of positive floats",
            () -> new MaxOf(1.0f, 2.0f, 3.0f, 4.0f).doubleValue(),
            new HasValue<>(4.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of positive floats",
            () -> new MaxOf(1.0f, 2.0f, 3.0f, 4.0f).floatValue(),
            new HasValue<>(4.0f)
        ).affirm();
    }

    @Test
    void withNegativeIntegerCollection() {
        new Assertion<>(
            "must select maximum integer of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).intValue(),
            new HasValue<>(-1)
        ).affirm();
        new Assertion<>(
            "must select maximum long of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).longValue(),
            new HasValue<>(-1L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).doubleValue(),
            new HasValue<>(-1.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).floatValue(),
            new HasValue<>(-1.0f)
        ).affirm();
    }

    @Test
    void withNegativeLongCollection() {
        new Assertion<>(
            "must select maximum integer of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).intValue(),
            new HasValue<>(-1)
        ).affirm();
        new Assertion<>(
            "must select maximum long of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).longValue(),
            new HasValue<>(-1L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).doubleValue(),
            new HasValue<>(-1.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).floatValue(),
            new HasValue<>(-1.0f)
        ).affirm();
    }

    @Test
    void withNegativeDoubleCollection() {
        new Assertion<>(
            "must select maximum integer of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).intValue(),
            new HasValue<>(-1)
        ).affirm();
        new Assertion<>(
            "must select maximum long of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).longValue(),
            new HasValue<>(-1L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).doubleValue(),
            new HasValue<>(-1.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).floatValue(),
            new HasValue<>(-1.0f)
        ).affirm();
    }

    @Test
    void withNegativeFloatCollection() {
        new Assertion<>(
            "must select maximum integer of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).intValue(),
            new HasValue<>(-1)
        ).affirm();
        new Assertion<>(
            "must select maximum long of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).longValue(),
            new HasValue<>(-1L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).doubleValue(),
            new HasValue<>(-1.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).floatValue(),
            new HasValue<>(-1.0f)
        ).affirm();
    }

    @Test
    void withPositiveAndNegativeIntegerCollection() {
        new Assertion<>(
            "must select maximum integer of positive and negative integers",
            () -> new MaxOf(-2, -1, 0, 1, 2).intValue(),
            new HasValue<>(2)
        ).affirm();
        new Assertion<>(
            "must select maximum long of positive and negative integers",
            () -> new MaxOf(-2, -1, 0, 1, 2).longValue(),
            new HasValue<>(2L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of positive and negative integers",
            () -> new MaxOf(-2, -1, 0, 1, 2).doubleValue(),
            new HasValue<>(2.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of positive and negative integers",
            () -> new MaxOf(-2, -1, 0, 1, 2).floatValue(),
            new HasValue<>(2.0f)
        ).affirm();
    }

    @Test
    void withPositiveAndNegativeLongCollection() {
        new Assertion<>(
            "must select maximum integer of positive and negative longs",
            () -> new MaxOf(-2L, -1L, 0L, 1L, 2L).intValue(),
            new HasValue<>(2)
        ).affirm();
        new Assertion<>(
            "must select maximum long of positive and negative longs",
            () -> new MaxOf(-2L, -1L, 0L, 1L, 2L).longValue(),
            new HasValue<>(2L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of positive and negative longs",
            () -> new MaxOf(-2L, -1L, 0L, 1L, 2L).doubleValue(),
            new HasValue<>(2.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of positive and negative longs",
            () -> new MaxOf(-2L, -1L, 0L, 1L, 2L).floatValue(),
            new HasValue<>(2.0f)
        ).affirm();
    }

    @Test
    void withPositiveAndNegativeDoubleCollection() {
        new Assertion<>(
            "must select maximum integer of positive and negative doubles",
            () -> new MaxOf(-2.0d, -1.0d, 0.0d, 1.0d, 2.0d).intValue(),
            new HasValue<>(2)
        ).affirm();
        new Assertion<>(
            "must select maximum long of positive and negative doubles",
            () -> new MaxOf(-2.0d, -1.0d, 0.0d, 1.0d, 2.0d).longValue(),
            new HasValue<>(2L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of positive and negative doubles",
            () -> new MaxOf(-2.0d, -1.0d, 0.0d, 1.0d, 2.0d).doubleValue(),
            new HasValue<>(2.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of positive and negative doubles",
            () -> new MaxOf(-2.0d, -1.0d, 0.0d, 1.0d, 2.0d).floatValue(),
            new HasValue<>(2.0f)
        ).affirm();
    }

    @Test
    void withPositiveAndNegativeFloatCollection() {
        new Assertion<>(
            "must select maximum integer of positive and negative floats",
            () -> new MaxOf(-2.0f, -1.0f, 0.0f, 1.0f, 2.0f).intValue(),
            new HasValue<>(2)
        ).affirm();
        new Assertion<>(
            "must select maximum long of positive and negative floats",
            () -> new MaxOf(-2.0f, -1.0f, 0.0f, 1.0f, 2.0f).longValue(),
            new HasValue<>(2L)
        ).affirm();
        new Assertion<>(
            "must select maximum double of positive and negative floats",
            () -> new MaxOf(-2.0f, -1.0f, 0.0f, 1.0f, 2.0f).doubleValue(),
            new HasValue<>(2.0d)
        ).affirm();
        new Assertion<>(
            "must select maximum float of positive and negative floats",
            () -> new MaxOf(-2.0f, -1.0f, 0.0f, 1.0f, 2.0f).floatValue(),
            new HasValue<>(2.0f)
        ).affirm();
    }
}
