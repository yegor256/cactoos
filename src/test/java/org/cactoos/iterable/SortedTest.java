/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Collections;
import java.util.Comparator;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Sorted}.
 *
 * @since 0.7
 */
final class SortedTest {

    @Test
    void sortsAnArray() {
        MatcherAssert.assertThat(
            "Can't sort an iterable",
            new Sorted<>(
                new IterableOf<>(
                    3, 2, 10, 44, -6, 0
                )
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<>(
                    new IsEqual<>(-6),
                    new IsEqual<>(0),
                    new IsEqual<>(2),
                    new IsEqual<>(3),
                    new IsEqual<>(10),
                    new IsEqual<>(44)
                )
            )
        );
    }

    @Test
        void sortsAnArrayWithComparator() {
        MatcherAssert.assertThat(
            "Can't sort an iterable with a comparator",
            new Sorted<>(
                Comparator.reverseOrder(), new IterableOf<>(
                    "a", "c", "hello", "dude", "Friend"
                )
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<>(
                    new IsEqual<>("hello"),
                    new IsEqual<>("dude"),
                    new IsEqual<>("c"),
                    new IsEqual<>("a"),
                    new IsEqual<>("Friend")
                )
            )
        );
    }

    @Test
    void sortsAnEmptyArray() {
        MatcherAssert.assertThat(
            "Can't sort an empty iterable",
            new Sorted<Integer>(
                Collections.emptyList()
            ),
            new HasSize(0)
        );
    }

    @Test
    void sortsCollection() {
        MatcherAssert.assertThat(
            "Must sort elements in collection",
            new Sorted<>(
                new ListOf<>(
                    "o", "t", "t", "f"
                )
            ),
            new IsEqual<>(new IterableOf<>("f", "o", "t", "t"))
        );
    }

    @Test
    void sortsByNumberComparator() {
        MatcherAssert.assertThat(
            "Must sort an iterable of numbers",
            new Sorted<>(
                (Number fst, Number snd) -> Integer.compare(fst.intValue(), snd.intValue()),
                new IterableOf<Number>(3L, 1f, 2d)
            ),
            new IsEqual<>(
                new IterableOf<Number>(1f, 2d, 3L)
            )
        );
    }

    @Test
    void sortsVarargs() {
        MatcherAssert.assertThat(
            "Must sort varargs",
            new Sorted<>(10, 18, -10, 20, -2),
            new IsIterableContainingInOrder<>(
                new ListOf<>(
                    new IsEqual<>(-10),
                    new IsEqual<>(-2),
                    new IsEqual<>(10),
                    new IsEqual<>(18),
                    new IsEqual<>(20)
                )
            )
        );
    }

    @Test
    void sortsVarargsWithComparator() {
        MatcherAssert.assertThat(
            "Must sort varargs with comparator",
            new Sorted<>(
                Comparator.reverseOrder(),
                -7, 0, 12, 1, -4
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<>(
                    new IsEqual<>(12),
                    new IsEqual<>(1),
                    new IsEqual<>(0),
                    new IsEqual<>(-4),
                    new IsEqual<>(-7)
                )
            )
        );
    }
}
