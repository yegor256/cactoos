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

import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link MaxOf}.
 *
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class MaxOfTest {

    @Test
    public void withIntegerCollection() {
        new Assertion<>(
            "must maximum integer of positive integers",
            () -> new MaxOf(1, 2, 3, 4).intValue(),
            new ScalarHasValue<>(4)
        ).affirm();
        new Assertion<>(
            "must maximum long of positive integers",
            () -> new MaxOf(1, 2, 3, 4).longValue(),
            new ScalarHasValue<>(4L)
        ).affirm();
        new Assertion<>(
            "must maximum double of positive integers",
            () -> new MaxOf(1, 2, 3, 4).doubleValue(),
            new ScalarHasValue<>(4.0d)
        ).affirm();
        new Assertion<>(
            "must maximum float of positive integers",
            () -> new MaxOf(1, 2, 3, 4).floatValue(),
            new ScalarHasValue<>(4.0f)
        ).affirm();
    }

    @Test
    public void withLongCollection() {
        new Assertion<>(
            "must maximum integer of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).intValue(),
            new ScalarHasValue<>(4)
        ).affirm();
        new Assertion<>(
            "must maximum long of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).longValue(),
            new ScalarHasValue<>(4L)
        ).affirm();
        new Assertion<>(
            "must maximum double of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).doubleValue(),
            new ScalarHasValue<>(4.0d)
        ).affirm();
        new Assertion<>(
            "must maximum float of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).floatValue(),
            new ScalarHasValue<>(4.0f)
        ).affirm();
    }

    @Test
    public void withDoubleCollection() {
        new Assertion<>(
            "must maximum integer of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).intValue(),
            new ScalarHasValue<>(4)
        ).affirm();
        new Assertion<>(
            "must maximum long of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).longValue(),
            new ScalarHasValue<>(4L)
        ).affirm();
        new Assertion<>(
            "must maximum double of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).doubleValue(),
            new ScalarHasValue<>(4.0d)
        ).affirm();
        new Assertion<>(
            "must maximum float of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).floatValue(),
            new ScalarHasValue<>(4.0f)
        ).affirm();
    }

    @Test
    public void withFloatCollection() {
        new Assertion<>(
            "must maximum integer of positive floats",
            () -> new MaxOf(1.0f, 2.0f, 3.0f, 4.0f).intValue(),
            new ScalarHasValue<>(4)
        ).affirm();
        new Assertion<>(
            "must maximum long of positive floats",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).longValue(),
            new ScalarHasValue<>(4L)
        ).affirm();
        new Assertion<>(
            "must maximum double of positive floats",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).doubleValue(),
            new ScalarHasValue<>(4.0d)
        ).affirm();
        new Assertion<>(
            "must maximum float of positive floats",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).floatValue(),
            new ScalarHasValue<>(4.0f)
        ).affirm();
    }

    @Test
    public void withNegativeIntegerCollection() {
        new Assertion<>(
            "must maximum integer of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).intValue(),
            new ScalarHasValue<>(-1)
        ).affirm();
        new Assertion<>(
            "must maximum long of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).longValue(),
            new ScalarHasValue<>(-1L)
        ).affirm();
        new Assertion<>(
            "must maximum double of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).doubleValue(),
            new ScalarHasValue<>(-1.0d)
        ).affirm();
        new Assertion<>(
            "must maximum float of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).floatValue(),
            new ScalarHasValue<>(-1.0f)
        ).affirm();
    }

    @Test
    public void withNegativeLongCollection() {
        new Assertion<>(
            "must maximum integer of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).intValue(),
            new ScalarHasValue<>(-1)
        ).affirm();
        new Assertion<>(
            "must maximum long of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).longValue(),
            new ScalarHasValue<>(-1L)
        ).affirm();
        new Assertion<>(
            "must maximum double of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).doubleValue(),
            new ScalarHasValue<>(-1.0d)
        ).affirm();
        new Assertion<>(
            "must maximum float of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).floatValue(),
            new ScalarHasValue<>(-1.0f)
        ).affirm();
    }

    @Test
    public void withNegativeDoubleCollection() {
        new Assertion<>(
            "must maximum integer of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).intValue(),
            new ScalarHasValue<>(-1)
        ).affirm();
        new Assertion<>(
            "must maximum long of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).longValue(),
            new ScalarHasValue<>(-1L)
        ).affirm();
        new Assertion<>(
            "must maximum double of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).doubleValue(),
            new ScalarHasValue<>(-1.0d)
        ).affirm();
        new Assertion<>(
            "must maximum float of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).floatValue(),
            new ScalarHasValue<>(-1.0f)
        ).affirm();
    }

    @Test
    public void withNegativeFloatCollection() {
        new Assertion<>(
            "must maximum integer of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).intValue(),
            new ScalarHasValue<>(-1)
        ).affirm();
        new Assertion<>(
            "must maximum long of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).longValue(),
            new ScalarHasValue<>(-1L)
        ).affirm();
        new Assertion<>(
            "must maximum double of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).doubleValue(),
            new ScalarHasValue<>(-1.0d)
        ).affirm();
        new Assertion<>(
            "must maximum float of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).floatValue(),
            new ScalarHasValue<>(-1.0f)
        ).affirm();
    }
}
