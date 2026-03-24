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
        final Integer single = 10;
        MatcherAssert.assertThat(
            "Must find the single",
            new Reduced<>(
                (first, last) -> first,
                new IterableOf<Scalar<Integer>>(() -> single)
            ),
            new HasValue<>(single)
        );
    }

    @Test
    void firstAtIterable() {
        final String one = "Apple";
        final String two = "Banana";
        final String three = "Orange";
        MatcherAssert.assertThat(
            "Must find the first",
            new Reduced<>(
                (first, last) -> first,
                new IterableOf<Scalar<String>>(
                    () -> one,
                    () -> two,
                    () -> three
                )
            ),
            new HasValue<>(one)
        );
    }

    @Test
    void lastAtIterable() {
        final Character one = 'A';
        final Character two = 'B';
        final Character three = 'O';
        MatcherAssert.assertThat(
            "Must find the last",
            new Reduced<>(
                (first, last) -> last,
                new IterableOf<Scalar<Character>>(
                    () -> one,
                    () -> two,
                    () -> three
                )
            ),
            new HasValue<>(three)
        );
    }

    @Test
    void lastAtIterableOfValues() {
        final Character one = 'A';
        final Character two = 'B';
        final Character three = 'O';
        MatcherAssert.assertThat(
            "Must find the last character",
            new Reduced<>(
                new IterableOf<>(one, two, three),
                (first, last) -> last
            ),
            new HasValue<>(three)
        );
    }

    @Test
    void constructedFromVarargs() {
        final String one = "One";
        final String two = "Two";
        final String three = "Three";
        MatcherAssert.assertThat(
            "Must concatenate the strings in vararg array",
            new Reduced<>(
                (first, last) -> first + last,
                one,
                two,
                three
            ),
            new HasValue<>("OneTwoThree")
        );
    }
}
