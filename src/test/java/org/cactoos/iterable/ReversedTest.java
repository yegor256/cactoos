/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Reversed}.
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ReversedTest {

    @Test
    void reversesIterable() {
        new Assertion<>(
            "Must reverse an iterable",
            new Reversed<>(
                new IterableOf<>(
                    "h", "w", "d"
                )
            ),
            new IsEqual<>(new IterableOf<>("d", "w", "h"))
        ).affirm();
    }

    @Test
    void iteratesMultipleTimes() {
        final Iterable<String> itr = new Reversed<>(
            new IterableOf<>("h", "w", "d")
        );
        final Iterable<String> expected = new IterableOf<>("d", "w", "h");
        new Assertion<>(
            "Must iterates once",
            itr,
            new IsEqual<>(expected)
        ).affirm();
        new Assertion<>(
            "Must iterates twice",
            itr,
            new IsEqual<>(expected)
        ).affirm();
    }

    @Test
    void reverseList() {
        final String last = "last";
        new Assertion<>(
            "Must reverse list",
            new Reversed<>(
                new ListOf<>(
                    "item", last
                )
            ).iterator().next(),
            new IsEqual<>(last)
        ).affirm();
    }

    @Test
    void reverseEmptyList() {
        new Assertion<>(
            "Must reverse empty list",
            new Reversed<>(
                new ListOf<>()
            ),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    void size() {
        new Assertion<>(
            "Size must be the same",
            new Reversed<>(
                new IterableOf<>(
                    "0", "1", "2"
                )
            ),
            new HasSize(3)
        ).affirm();
    }

    @Test
    void isEmpty() {
        new Assertion<>(
            "Must be not empty",
            new Reversed<>(
                new IterableOf<>(
                    6, 16
                )
            ),
            new IsNot<>(new IsEmptyIterable<>())
        ).affirm();
    }

    @Test
    void reversesVarargs() {
        new Assertion<>(
            "Must reverse varargs",
            new Reversed<>(1, 2, 3, 4),
            new IsEqual<>(new IterableOf<>(4, 3, 2, 1))
        ).affirm();
    }
}
