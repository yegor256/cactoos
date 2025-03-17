/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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

import org.cactoos.list.ListOf;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link MinOf}.
 *
 * @since 0.10
 */
final class MinOfTest {

    @Test
    void withIntegerCollection() {
        new Assertion<>(
            "must select minimum integer of positive integers",
            new MinOf(
                new ListOf<>(1, 2, 3, 4).toArray(new Integer[4])
            ).intValue(),
            Matchers.equalTo(1)
        ).affirm();
        new Assertion<>(
            "must select minimum long of positive integers",
            new MinOf(
                new ListOf<>(1, 2, 3, 4).toArray(new Integer[4])
            ).longValue(),
            Matchers.equalTo(1L)
        ).affirm();
        new Assertion<>(
            "must select minimum double of positive integers",
            new MinOf(
                new ListOf<>(1, 2, 3, 4).toArray(new Integer[4])
            ).doubleValue(),
            Matchers.equalTo(1.0d)
        ).affirm();
        new Assertion<>(
            "must select minimum float of positive integers",
            new MinOf(
                new ListOf<>(1, 2, 3, 4).toArray(new Integer[4])
            ).floatValue(),
            Matchers.equalTo(1.0f)
        ).affirm();
    }

    @Test
    void withLongCollection() {
        new Assertion<>(
            "must select minimum integer of positive longs",
            new MinOf(
                new ListOf<>(1L, 2L, 3L, 4L).toArray(new Long[4])
            ).intValue(),
            Matchers.equalTo(1)
        ).affirm();
        new Assertion<>(
            "must select minimum long of positive longs",
            new MinOf(
                new ListOf<>(1L, 2L, 3L, 4L).toArray(new Long[4])
            ).longValue(),
            Matchers.equalTo(1L)
        ).affirm();
        new Assertion<>(
            "must select minimum double of positive longs",
            new MinOf(
                new ListOf<>(1L, 2L, 3L, 4L).toArray(new Long[4])
            ).doubleValue(),
            Matchers.equalTo(1.0d)
        ).affirm();
        new Assertion<>(
            "must select minimum float of positive longs",
            new MinOf(
                new ListOf<>(1L, 2L, 3L, 4L).toArray(new Long[4])
            ).floatValue(),
            Matchers.equalTo(1.0f)
        ).affirm();
    }

    @Test
    void withDoubleCollection() {
        new Assertion<>(
            "must select minimum integer of positive doubles",
            new MinOf(
                new ListOf<>(1.0d, 2.0d, 3.0d, 4.0d).toArray(new Double[4])
            ).intValue(),
            Matchers.equalTo(1)
        ).affirm();
        new Assertion<>(
            "must select minimum long of positive doubles",
            new MinOf(
                new ListOf<>(1.0d, 2.0d, 3.0d, 4.0d).toArray(new Double[4])
            ).longValue(),
            Matchers.equalTo(1L)
        ).affirm();
        new Assertion<>(
            "must select minimum double of positive doubles",
            new MinOf(
                new ListOf<>(1.0d, 2.0d, 3.0d, 4.0d).toArray(new Double[4])
            ).doubleValue(),
            Matchers.equalTo(1.0d)
        ).affirm();
        new Assertion<>(
            "must select minimum float of positive doubles",
            new MinOf(
                new ListOf<>(1.0d, 2.0d, 3.0d, 4.0d).toArray(new Double[4])
            ).floatValue(),
            Matchers.equalTo(1.0f)
        ).affirm();
    }

    @Test
    void withFloatCollection() {
        new Assertion<>(
            "must select minimum integer of positive floats",
            new MinOf(
                new ListOf<>(1.0f, 2.0f, 3.0f, 4.0f).toArray(new Float[4])
            ).intValue(),
            Matchers.equalTo(1)
        ).affirm();
        new Assertion<>(
            "must select minimum long of positive floats",
            new MinOf(
                new ListOf<>(1.0f, 2.0f, 3.0f, 4.0f).toArray(new Float[4])
            ).longValue(),
            Matchers.equalTo(1L)
        ).affirm();
        new Assertion<>(
            "must select minimum float of positive floats",
            new MinOf(
                new ListOf<>(1.0f, 2.0f, 3.0f, 4.0f).toArray(new Float[4])
            ).doubleValue(),
            Matchers.equalTo(1.0d)
        ).affirm();
        new Assertion<>(
            "must select minimum float of positive floats",
            new MinOf(
                new ListOf<>(1.0f, 2.0f, 3.0f, 4.0f).toArray(new Float[4])
            ).floatValue(),
            Matchers.equalTo(1.0f)
        ).affirm();
    }

}
