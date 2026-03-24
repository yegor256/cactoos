/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.IterableOfInts;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link FirstOf}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class FirstOfTest {

    @Test
    void returnsMatchingValue() {
        final int value = 1;
        MatcherAssert.assertThat(
            "Must return the only matching element",
            new FirstOf<>(
                i -> i >= value,
                new IterableOfInts(0, value),
                () -> -1
            ),
            new HasValue<>(value)
        );
    }

    @Test
    void returnsMatchingValueWithExceptionalFallback() {
        final int value = 2;
        MatcherAssert.assertThat(
            "Exception was not thrown",
            new FirstOf<>(
                i -> i >= value,
                new IterableOfInts(0, value),
                () -> {
                    throw new IllegalArgumentException(
                        "This exception should not be thrown"
                    );
                }
            ),
            new HasValue<>(value)
        );
    }

    @Test
    void returnsFirstValueForMultipleMatchingOnes() {
        final String value = "1";
        MatcherAssert.assertThat(
            "Must return first matching element",
            new FirstOf<>(
                i -> !i.isEmpty(),
                new IterableOf<>("1", "2"),
                () -> ""
            ),
            new HasValue<>(value)
        );
    }

    @Test
    void returnsFallbackIfNothingMatches() {
        final String value = "abc";
        MatcherAssert.assertThat(
            "Must return fallback",
            new FirstOf<>(
                i -> i.length() > 2,
                new IterableOf<>("ab", "cd"),
                () -> value
            ),
            new HasValue<>(value)
        );
    }

    @Test
    void throwsFallbackIfNothingMatches() {
        MatcherAssert.assertThat(
            "Fallback was not thrown",
            new FirstOf<>(
                num -> num.equals(0),
                new IterableOf<>(
                    1, 2, 3, 4, 5
                ),
                () -> {
                    throw new IllegalArgumentException(
                        String.format("Unable to found element with id %d", 0)
                    );
                }
            ),
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void returnsFirstValueWithScalarFallback() {
        MatcherAssert.assertThat(
            "Returns first value with scalar fallback",
            new FirstOf<>(
                new IterableOfInts(2, 1, 0),
                () -> -1
            ),
            new HasValue<>(2)
        );
    }

    @Test
    void returnsFirstValueWithIntegerFallback() {
        MatcherAssert.assertThat(
            "Returns first value with Integer fallback",
            new FirstOf<>(
                new IterableOfInts(2, 1, 0),
                -1
            ),
            new HasValue<>(2)
        );
    }

    @Test
    void returnsFallbackWhenIterableEmpty() {
        MatcherAssert.assertThat(
            "Returns fallback when iterable empty",
            new FirstOf<>(
                new IterableOf<>(),
                () -> -1
            ),
            new HasValue<>(-1)
        );
    }
}
