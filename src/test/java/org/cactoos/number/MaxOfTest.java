/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link MaxOf}.
 *
 * @since 0.10
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
