/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Reversed}.
 *
 * @since 0.45
 */
final class ReversedTest {

    @Test
    void reversesIterator() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void reversesEmptyList() {
        new Assertion<>(
            "Must reverse empty list",
            new ListOf<>(
                new Reversed<>(
                    new IteratorOf<>()
                )
            ),
            new IsEmptyCollection<>()
        ).affirm();
    }

    @Test
    void iteratorSizeReversed() {
        new Assertion<>(
            "Must be the same size",
            new ListOf<>(
                new Reversed<>(
                    new IteratorOf<>("c", "a", "c", "t", "o", "o", "s")
                )
            ),
            new HasSize(7)
        ).affirm();
    }

}
