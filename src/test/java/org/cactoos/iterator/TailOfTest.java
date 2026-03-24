/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link TailOf}.
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TailOfTest {

    @Test
        void tailIterator() {
        MatcherAssert.assertThat(
            "Must get tail portion of iterator",
            new IterableOf<>(
                new TailOf<>(
                    2,
                    new IteratorOf<>(
                        "one", "two", "three", "four"
                    )
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    "three",
                    "four"
                )
            )
        );
    }

    @Test
    void returnsIntactIterator() {
        MatcherAssert.assertThat(
            "Must return an intact iterator",
            new IterableOf<>(
                new TailOf<>(
                    3,
                    new IteratorOf<>(
                        "one", "two"
                    )
                )
            ),
            new HasSize(2)
        );
    }

    @Test
    void returnsEmptyIterator() {
        MatcherAssert.assertThat(
            "Must throw an exception if empty",
            () -> new TailOf<>(
                0,
                new IteratorOf<>(
                    "one", "two"
                )
            ).next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void emptyIteratorForNegativeSize() {
        MatcherAssert.assertThat(
            "Must throw an exception for negative size",
            () -> new TailOf<>(
                -1,
                new IteratorOf<>(
                    "one", "two"
                )
            ).next(),
            new Throws<>(NoSuchElementException.class)
        );
    }
}
