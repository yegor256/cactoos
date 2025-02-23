/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Repeated}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class RepeatedTest {

    @Test
    void allSameTest() {
        final int size = 42;
        final int element = 11;
        MatcherAssert.assertThat(
            "Can't generate an iterable with fixed size",
            new LengthOf(
                new Filtered<>(
                    input -> input == element,
                    new Repeated<>(size, element)
                )
            ),
            new HasValue<>((long) size)
        );
    }

    @Test
    void emptyTest() {
        MatcherAssert.assertThat(
            "Can't generate an empty iterable",
            new LengthOf(new Repeated<>(0, 0)),
            new HasValue<>(0L)
        );
    }

    @Test
    void repeatsIntegerTwice() {
        final Iterable<Integer> list = new Repeated<>(5, 1);
        MatcherAssert.assertThat(
            "Can't repeat an integer",
            list, Matchers.iterableWithSize(5)
        );
        MatcherAssert.assertThat(
            "Can't repeat an integer, again",
            list, Matchers.iterableWithSize(5)
        );
    }
}
