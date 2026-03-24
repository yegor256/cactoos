/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Reversed}.
 *
 * @since 0.45
 */
final class ReversedTest {

    @Test
    void reversesIterator() {
        MatcherAssert.assertThat(
            "Must reverse the iterator",
            new ListOf<>(
                new Reversed<>(
                    new IteratorOf<>("c", "a", "c", "t", "o", "o", "s")
                )
            ),
            new IsEqual<>(
                new ListOf<>(
                    new IteratorOf<>("s", "o", "o", "t", "c", "a", "c")
                )
            )
        );
    }

    @Test
    void reversesEmptyList() {
        MatcherAssert.assertThat(
            "Must reverse empty list",
            new ListOf<>(
                new Reversed<>(
                    new IteratorOf<>()
                )
            ),
            new IsEmptyCollection<>()
        );
    }

    @Test
    void iteratorSizeReversed() {
        MatcherAssert.assertThat(
            "Must be the same size",
            new ListOf<>(
                new Reversed<>(
                    new IteratorOf<>("c", "a", "c", "t", "o", "o", "s")
                )
            ),
            new HasSize(7)
        );
    }

}
