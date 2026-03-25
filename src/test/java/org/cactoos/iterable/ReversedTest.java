/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Reversed}.
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ReversedTest {

    @Test
    void reversesIterable() {
        MatcherAssert.assertThat(
            "Must reverse an iterable",
            new Reversed<>(
                new IterableOf<>(
                    "h", "w", "d"
                )
            ),
            new IsEqual<>(new IterableOf<>("d", "w", "h"))
        );
    }

    @Test
    void iteratesOnce() {
        MatcherAssert.assertThat(
            "Must iterate once",
            new Reversed<>(
                new IterableOf<>("h", "w", "d")
            ),
            new IsEqual<>(new IterableOf<>("d", "w", "h"))
        );
    }

    @Test
    void iteratesTwice() {
        final Iterable<String> itr = new Reversed<>(
            new IterableOf<>("h", "w", "d")
        );
        itr.iterator().next();
        MatcherAssert.assertThat(
            "Must iterate twice",
            itr,
            new IsEqual<>(new IterableOf<>("d", "w", "h"))
        );
    }

    @Test
    void reverseList() {
        final String last = "last";
        MatcherAssert.assertThat(
            "Must reverse list",
            new Reversed<>(
                new ListOf<>(
                    "item", last
                )
            ).iterator().next(),
            new IsEqual<>(last)
        );
    }

    @Test
    void reverseEmptyList() {
        MatcherAssert.assertThat(
            "Must reverse empty list",
            new Reversed<>(
                new ListOf<>()
            ),
            new IsEmptyIterable<>()
        );
    }

    @Test
    void size() {
        MatcherAssert.assertThat(
            "Size must be the same",
            new Reversed<>(
                new IterableOf<>(
                    "0", "1", "2"
                )
            ),
            new HasSize(3)
        );
    }

    @Test
    void isEmpty() {
        MatcherAssert.assertThat(
            "Must be not empty",
            new Reversed<>(
                new IterableOf<>(
                    6, 16
                )
            ),
            new IsNot<>(new IsEmptyIterable<>())
        );
    }

    @Test
    void reversesVarargs() {
        MatcherAssert.assertThat(
            "Must reverse varargs",
            new Reversed<>(1, 2, 3, 4),
            new IsEqual<>(new IterableOf<>(4, 3, 2, 1))
        );
    }
}
