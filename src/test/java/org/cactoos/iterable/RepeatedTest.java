/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
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
@SuppressWarnings("PMD.UnnecessaryLocalRule")
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
    void repeatsInteger() {
        MatcherAssert.assertThat(
            "Can't repeat an integer",
            new Repeated<>(5, 1),
            Matchers.iterableWithSize(5)
        );
    }

    @Test
    void repeatsIntegerOnSecondTraversal() {
        final Iterable<Integer> list = new Repeated<>(5, 1);
        list.iterator().next();
        MatcherAssert.assertThat(
            "Can't repeat an integer on second traversal",
            list,
            Matchers.iterableWithSize(5)
        );
    }
}
