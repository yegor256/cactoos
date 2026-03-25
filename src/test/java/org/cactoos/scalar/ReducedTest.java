/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.Collections;
import java.util.NoSuchElementException;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Reduced}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ReducedTest {

    @Test
    void failsForEmptyIterable() {
        MatcherAssert.assertThat(
            "Exception is expected for empty iterable",
            () -> new Reduced<>(
                (first, last) -> first,
                Collections.emptyList()
            ).value(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void singleAtSingleIterable() {
        MatcherAssert.assertThat(
            "Must find the single",
            new Reduced<>(
                (first, last) -> first,
                new IterableOf<Scalar<Integer>>(() -> 10)
            ),
            new HasValue<>(10)
        );
    }

    @Test
    void firstAtIterable() {
        MatcherAssert.assertThat(
            "Must find the first",
            new Reduced<>(
                (first, last) -> first,
                new IterableOf<Scalar<String>>(
                    () -> "Apple",
                    () -> "Banana",
                    () -> "Orange"
                )
            ),
            new HasValue<>("Apple")
        );
    }

    @Test
    void lastAtIterable() {
        MatcherAssert.assertThat(
            "Must find the last",
            new Reduced<>(
                (first, last) -> last,
                new IterableOf<Scalar<Character>>(
                    () -> 'A',
                    () -> 'B',
                    () -> 'O'
                )
            ),
            new HasValue<>('O')
        );
    }

    @Test
    void lastAtIterableOfValues() {
        MatcherAssert.assertThat(
            "Must find the last character",
            new Reduced<>(
                new IterableOf<>('A', 'B', 'O'),
                (first, last) -> last
            ),
            new HasValue<>('O')
        );
    }

    @Test
    void constructedFromVarargs() {
        MatcherAssert.assertThat(
            "Must concatenate the strings in vararg array",
            new Reduced<>(
                (first, last) -> first + last,
                "One",
                "Two",
                "Three"
            ),
            new HasValue<>("OneTwoThree")
        );
    }
}
