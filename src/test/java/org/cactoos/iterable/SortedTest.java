/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Collections;
import java.util.Comparator;
import org.cactoos.list.ListOf;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Sorted}.
 *
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SortedTest {

    @Test
    void sortsAnArray() {
        new Assertion<>(
            "Can't sort an iterable",
            new Sorted<>(
                new IterableOf<>(
                    3, 2, 10, 44, -6, 0
                )
            ),
            Matchers.hasItems(-6, 0, 2, 3, 10, 44)
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void sortsAnArrayWithComparator() {
        new Assertion<>(
            "Can't sort an iterable with a comparator",
            new Sorted<>(
                Comparator.reverseOrder(), new IterableOf<>(
                    "a", "c", "hello", "dude", "Friend"
                )
            ),
            Matchers.hasItems("hello", "dude", "c", "a", "Friend")
        ).affirm();
    }

    @Test
    void sortsAnEmptyArray() {
        new Assertion<>(
            "Can't sort an empty iterable",
            new Sorted<Integer>(
                Collections.emptyList()
            ),
            Matchers.iterableWithSize(0)
        ).affirm();
    }

    @Test
    void sortsCollection() {
        new Assertion<>(
            "Must sort elements in collection",
            new Sorted<>(
                new ListOf<>(
                    "o", "t", "t", "f"
                )
            ),
            new IsEqual<>(new IterableOf<>("f", "o", "t", "t"))
        ).affirm();
    }

    @Test
    void sortsByNymberComprator() {
        new Assertion<>(
            "Must sort an iterable of numbers",
            new Sorted<>(
                (Number fst, Number snd) -> Integer.compare(fst.intValue(), snd.intValue()),
                new IterableOf<Number>(3L, 1f, 2d)
            ),
            new IsEqual<>(
                new IterableOf<Number>(1f, 2d, 3L)
            )
        ).affirm();
    }

}
