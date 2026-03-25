/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link MaxOf}.
 *
 * @since 0.10
 */
@SuppressWarnings("PMD.TooManyMethods")
final class MaxOfTest {

    @Test
    void maxIntOfPositiveIntegers() {
        MatcherAssert.assertThat(
            "must select maximum integer of positive integers",
            () -> new MaxOf(1, 2, 3, 4).intValue(),
            new HasValue<>(4)
        );
    }

    @Test
    void maxLongOfPositiveIntegers() {
        MatcherAssert.assertThat(
            "must select maximum long of positive integers",
            () -> new MaxOf(1, 2, 3, 4).longValue(),
            new HasValue<>(4L)
        );
    }

    @Test
    void maxDoubleOfPositiveIntegers() {
        MatcherAssert.assertThat(
            "must select maximum double of positive integers",
            () -> new MaxOf(1, 2, 3, 4).doubleValue(),
            new HasValue<>(4.0d)
        );
    }

    @Test
    void maxFloatOfPositiveIntegers() {
        MatcherAssert.assertThat(
            "must select maximum float of positive integers",
            () -> new MaxOf(1, 2, 3, 4).floatValue(),
            new HasValue<>(4.0f)
        );
    }

    @Test
    void maxIntOfPositiveLongs() {
        MatcherAssert.assertThat(
            "must select maximum integer of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).intValue(),
            new HasValue<>(4)
        );
    }

    @Test
    void maxLongOfPositiveLongs() {
        MatcherAssert.assertThat(
            "must select maximum long of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).longValue(),
            new HasValue<>(4L)
        );
    }

    @Test
    void maxDoubleOfPositiveLongs() {
        MatcherAssert.assertThat(
            "must select maximum double of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).doubleValue(),
            new HasValue<>(4.0d)
        );
    }

    @Test
    void maxFloatOfPositiveLongs() {
        MatcherAssert.assertThat(
            "must select maximum float of positive longs",
            () -> new MaxOf(1L, 2L, 3L, 4L).floatValue(),
            new HasValue<>(4.0f)
        );
    }

    @Test
    void maxIntOfPositiveDoubles() {
        MatcherAssert.assertThat(
            "must select maximum integer of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).intValue(),
            new HasValue<>(4)
        );
    }

    @Test
    void maxLongOfPositiveDoubles() {
        MatcherAssert.assertThat(
            "must select maximum long of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).longValue(),
            new HasValue<>(4L)
        );
    }

    @Test
    void maxDoubleOfPositiveDoubles() {
        MatcherAssert.assertThat(
            "must select maximum double of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).doubleValue(),
            new HasValue<>(4.0d)
        );
    }

    @Test
    void maxFloatOfPositiveDoubles() {
        MatcherAssert.assertThat(
            "must select maximum float of positive doubles",
            () -> new MaxOf(1.0d, 2.0d, 3.0d, 4.0d).floatValue(),
            new HasValue<>(4.0f)
        );
    }

    @Test
    void maxIntOfPositiveFloats() {
        MatcherAssert.assertThat(
            "must select maximum integer of positive floats",
            () -> new MaxOf(1.0f, 2.0f, 3.0f, 4.0f).intValue(),
            new HasValue<>(4)
        );
    }

    @Test
    void maxLongOfPositiveFloats() {
        MatcherAssert.assertThat(
            "must select maximum long of positive floats",
            () -> new MaxOf(1.0f, 2.0f, 3.0f, 4.0f).longValue(),
            new HasValue<>(4L)
        );
    }

    @Test
    void maxDoubleOfPositiveFloats() {
        MatcherAssert.assertThat(
            "must select maximum double of positive floats",
            () -> new MaxOf(1.0f, 2.0f, 3.0f, 4.0f).doubleValue(),
            new HasValue<>(4.0d)
        );
    }

    @Test
    void maxFloatOfPositiveFloats() {
        MatcherAssert.assertThat(
            "must select maximum float of positive floats",
            () -> new MaxOf(1.0f, 2.0f, 3.0f, 4.0f).floatValue(),
            new HasValue<>(4.0f)
        );
    }

    @Test
    void maxIntOfNegativeIntegers() {
        MatcherAssert.assertThat(
            "must select maximum integer of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).intValue(),
            new HasValue<>(-1)
        );
    }

    @Test
    void maxLongOfNegativeIntegers() {
        MatcherAssert.assertThat(
            "must select maximum long of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).longValue(),
            new HasValue<>(-1L)
        );
    }

    @Test
    void maxDoubleOfNegativeIntegers() {
        MatcherAssert.assertThat(
            "must select maximum double of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).doubleValue(),
            new HasValue<>(-1.0d)
        );
    }

    @Test
    void maxFloatOfNegativeIntegers() {
        MatcherAssert.assertThat(
            "must select maximum float of negative integers",
            () -> new MaxOf(-1, -2, -3, -4).floatValue(),
            new HasValue<>(-1.0f)
        );
    }

    @Test
    void maxIntOfNegativeLongs() {
        MatcherAssert.assertThat(
            "must select maximum integer of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).intValue(),
            new HasValue<>(-1)
        );
    }

    @Test
    void maxLongOfNegativeLongs() {
        MatcherAssert.assertThat(
            "must select maximum long of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).longValue(),
            new HasValue<>(-1L)
        );
    }

    @Test
    void maxDoubleOfNegativeLongs() {
        MatcherAssert.assertThat(
            "must select maximum double of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).doubleValue(),
            new HasValue<>(-1.0d)
        );
    }

    @Test
    void maxFloatOfNegativeLongs() {
        MatcherAssert.assertThat(
            "must select maximum float of negative longs",
            () -> new MaxOf(-1L, -2L, -3L, -4L).floatValue(),
            new HasValue<>(-1.0f)
        );
    }

    @Test
    void maxIntOfNegativeDoubles() {
        MatcherAssert.assertThat(
            "must select maximum integer of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).intValue(),
            new HasValue<>(-1)
        );
    }

    @Test
    void maxLongOfNegativeDoubles() {
        MatcherAssert.assertThat(
            "must select maximum long of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).longValue(),
            new HasValue<>(-1L)
        );
    }

    @Test
    void maxDoubleOfNegativeDoubles() {
        MatcherAssert.assertThat(
            "must select maximum double of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).doubleValue(),
            new HasValue<>(-1.0d)
        );
    }

    @Test
    void maxFloatOfNegativeDoubles() {
        MatcherAssert.assertThat(
            "must select maximum float of negative doubles",
            () -> new MaxOf(-1.0d, -2.0d, -3.0d, -4.0d).floatValue(),
            new HasValue<>(-1.0f)
        );
    }

    @Test
    void maxIntOfNegativeFloats() {
        MatcherAssert.assertThat(
            "must select maximum integer of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).intValue(),
            new HasValue<>(-1)
        );
    }

    @Test
    void maxLongOfNegativeFloats() {
        MatcherAssert.assertThat(
            "must select maximum long of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).longValue(),
            new HasValue<>(-1L)
        );
    }

    @Test
    void maxDoubleOfNegativeFloats() {
        MatcherAssert.assertThat(
            "must select maximum double of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).doubleValue(),
            new HasValue<>(-1.0d)
        );
    }

    @Test
    void maxFloatOfNegativeFloats() {
        MatcherAssert.assertThat(
            "must select maximum float of negative floats",
            () -> new MaxOf(-1.0f, -2.0f, -3.0f, -4.0f).floatValue(),
            new HasValue<>(-1.0f)
        );
    }

    @Test
    void maxIntOfMixedIntegers() {
        MatcherAssert.assertThat(
            "must select maximum integer of positive and negative integers",
            () -> new MaxOf(-2, -1, 0, 1, 2).intValue(),
            new HasValue<>(2)
        );
    }

    @Test
    void maxLongOfMixedIntegers() {
        MatcherAssert.assertThat(
            "must select maximum long of positive and negative integers",
            () -> new MaxOf(-2, -1, 0, 1, 2).longValue(),
            new HasValue<>(2L)
        );
    }

    @Test
    void maxDoubleOfMixedIntegers() {
        MatcherAssert.assertThat(
            "must select maximum double of positive and negative integers",
            () -> new MaxOf(-2, -1, 0, 1, 2).doubleValue(),
            new HasValue<>(2.0d)
        );
    }

    @Test
    void maxFloatOfMixedIntegers() {
        MatcherAssert.assertThat(
            "must select maximum float of positive and negative integers",
            () -> new MaxOf(-2, -1, 0, 1, 2).floatValue(),
            new HasValue<>(2.0f)
        );
    }

    @Test
    void maxIntOfMixedLongs() {
        MatcherAssert.assertThat(
            "must select maximum integer of positive and negative longs",
            () -> new MaxOf(-2L, -1L, 0L, 1L, 2L).intValue(),
            new HasValue<>(2)
        );
    }

    @Test
    void maxLongOfMixedLongs() {
        MatcherAssert.assertThat(
            "must select maximum long of positive and negative longs",
            () -> new MaxOf(-2L, -1L, 0L, 1L, 2L).longValue(),
            new HasValue<>(2L)
        );
    }

    @Test
    void maxDoubleOfMixedLongs() {
        MatcherAssert.assertThat(
            "must select maximum double of positive and negative longs",
            () -> new MaxOf(-2L, -1L, 0L, 1L, 2L).doubleValue(),
            new HasValue<>(2.0d)
        );
    }

    @Test
    void maxFloatOfMixedLongs() {
        MatcherAssert.assertThat(
            "must select maximum float of positive and negative longs",
            () -> new MaxOf(-2L, -1L, 0L, 1L, 2L).floatValue(),
            new HasValue<>(2.0f)
        );
    }

    @Test
    void maxIntOfMixedDoubles() {
        MatcherAssert.assertThat(
            "must select maximum integer of positive and negative doubles",
            () -> new MaxOf(-2.0d, -1.0d, 0.0d, 1.0d, 2.0d).intValue(),
            new HasValue<>(2)
        );
    }

    @Test
    void maxLongOfMixedDoubles() {
        MatcherAssert.assertThat(
            "must select maximum long of positive and negative doubles",
            () -> new MaxOf(-2.0d, -1.0d, 0.0d, 1.0d, 2.0d).longValue(),
            new HasValue<>(2L)
        );
    }

    @Test
    void maxDoubleOfMixedDoubles() {
        MatcherAssert.assertThat(
            "must select maximum double of positive and negative doubles",
            () -> new MaxOf(-2.0d, -1.0d, 0.0d, 1.0d, 2.0d).doubleValue(),
            new HasValue<>(2.0d)
        );
    }

    @Test
    void maxFloatOfMixedDoubles() {
        MatcherAssert.assertThat(
            "must select maximum float of positive and negative doubles",
            () -> new MaxOf(-2.0d, -1.0d, 0.0d, 1.0d, 2.0d).floatValue(),
            new HasValue<>(2.0f)
        );
    }

    @Test
    void maxIntOfMixedFloats() {
        MatcherAssert.assertThat(
            "must select maximum integer of positive and negative floats",
            () -> new MaxOf(-2.0f, -1.0f, 0.0f, 1.0f, 2.0f).intValue(),
            new HasValue<>(2)
        );
    }

    @Test
    void maxLongOfMixedFloats() {
        MatcherAssert.assertThat(
            "must select maximum long of positive and negative floats",
            () -> new MaxOf(-2.0f, -1.0f, 0.0f, 1.0f, 2.0f).longValue(),
            new HasValue<>(2L)
        );
    }

    @Test
    void maxDoubleOfMixedFloats() {
        MatcherAssert.assertThat(
            "must select maximum double of positive and negative floats",
            () -> new MaxOf(-2.0f, -1.0f, 0.0f, 1.0f, 2.0f).doubleValue(),
            new HasValue<>(2.0d)
        );
    }

    @Test
    void maxFloatOfMixedFloats() {
        MatcherAssert.assertThat(
            "must select maximum float of positive and negative floats",
            () -> new MaxOf(-2.0f, -1.0f, 0.0f, 1.0f, 2.0f).floatValue(),
            new HasValue<>(2.0f)
        );
    }
}
