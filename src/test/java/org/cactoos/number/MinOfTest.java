/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link MinOf}.
 *
 * @since 0.10
 */
@SuppressWarnings("PMD.TooManyMethods")
final class MinOfTest {

    @Test
    void minIntOfIntegers() {
        MatcherAssert.assertThat(
            "must select minimum integer of positive integers",
            new MinOf(
                new ListOf<>(1, 2, 3, 4).toArray(new Integer[0])
            ).intValue(),
            Matchers.equalTo(1)
        );
    }

    @Test
    void minLongOfIntegers() {
        MatcherAssert.assertThat(
            "must select minimum long of positive integers",
            new MinOf(
                new ListOf<>(1, 2, 3, 4).toArray(new Integer[0])
            ).longValue(),
            Matchers.equalTo(1L)
        );
    }

    @Test
    void minDoubleOfIntegers() {
        MatcherAssert.assertThat(
            "must select minimum double of positive integers",
            new MinOf(
                new ListOf<>(1, 2, 3, 4).toArray(new Integer[0])
            ).doubleValue(),
            Matchers.equalTo(1.0d)
        );
    }

    @Test
    void minFloatOfIntegers() {
        MatcherAssert.assertThat(
            "must select minimum float of positive integers",
            new MinOf(
                new ListOf<>(1, 2, 3, 4).toArray(new Integer[0])
            ).floatValue(),
            Matchers.equalTo(1.0f)
        );
    }

    @Test
    void minIntOfLongs() {
        MatcherAssert.assertThat(
            "must select minimum integer of positive longs",
            new MinOf(
                new ListOf<>(1L, 2L, 3L, 4L).toArray(new Long[0])
            ).intValue(),
            Matchers.equalTo(1)
        );
    }

    @Test
    void minLongOfLongs() {
        MatcherAssert.assertThat(
            "must select minimum long of positive longs",
            new MinOf(
                new ListOf<>(1L, 2L, 3L, 4L).toArray(new Long[0])
            ).longValue(),
            Matchers.equalTo(1L)
        );
    }

    @Test
    void minDoubleOfLongs() {
        MatcherAssert.assertThat(
            "must select minimum double of positive longs",
            new MinOf(
                new ListOf<>(1L, 2L, 3L, 4L).toArray(new Long[0])
            ).doubleValue(),
            Matchers.equalTo(1.0d)
        );
    }

    @Test
    void minFloatOfLongs() {
        MatcherAssert.assertThat(
            "must select minimum float of positive longs",
            new MinOf(
                new ListOf<>(1L, 2L, 3L, 4L).toArray(new Long[0])
            ).floatValue(),
            Matchers.equalTo(1.0f)
        );
    }

    @Test
    void minIntOfDoubles() {
        MatcherAssert.assertThat(
            "must select minimum integer of positive doubles",
            new MinOf(
                new ListOf<>(1.0d, 2.0d, 3.0d, 4.0d).toArray(new Double[0])
            ).intValue(),
            Matchers.equalTo(1)
        );
    }

    @Test
    void minLongOfDoubles() {
        MatcherAssert.assertThat(
            "must select minimum long of positive doubles",
            new MinOf(
                new ListOf<>(1.0d, 2.0d, 3.0d, 4.0d).toArray(new Double[0])
            ).longValue(),
            Matchers.equalTo(1L)
        );
    }

    @Test
    void minDoubleOfDoubles() {
        MatcherAssert.assertThat(
            "must select minimum double of positive doubles",
            new MinOf(
                new ListOf<>(1.0d, 2.0d, 3.0d, 4.0d).toArray(new Double[0])
            ).doubleValue(),
            Matchers.equalTo(1.0d)
        );
    }

    @Test
    void minFloatOfDoubles() {
        MatcherAssert.assertThat(
            "must select minimum float of positive doubles",
            new MinOf(
                new ListOf<>(1.0d, 2.0d, 3.0d, 4.0d).toArray(new Double[0])
            ).floatValue(),
            Matchers.equalTo(1.0f)
        );
    }

    @Test
    void minIntOfFloats() {
        MatcherAssert.assertThat(
            "must select minimum integer of positive floats",
            new MinOf(
                new ListOf<>(1.0f, 2.0f, 3.0f, 4.0f).toArray(new Float[0])
            ).intValue(),
            Matchers.equalTo(1)
        );
    }

    @Test
    void minLongOfFloats() {
        MatcherAssert.assertThat(
            "must select minimum long of positive floats",
            new MinOf(
                new ListOf<>(1.0f, 2.0f, 3.0f, 4.0f).toArray(new Float[0])
            ).longValue(),
            Matchers.equalTo(1L)
        );
    }

    @Test
    void minDoubleOfFloats() {
        MatcherAssert.assertThat(
            "must select minimum double of positive floats",
            new MinOf(
                new ListOf<>(1.0f, 2.0f, 3.0f, 4.0f).toArray(new Float[0])
            ).doubleValue(),
            Matchers.equalTo(1.0d)
        );
    }

    @Test
    void minFloatOfFloats() {
        MatcherAssert.assertThat(
            "must select minimum float of positive floats",
            new MinOf(
                new ListOf<>(1.0f, 2.0f, 3.0f, 4.0f).toArray(new Float[0])
            ).floatValue(),
            Matchers.equalTo(1.0f)
        );
    }
}
