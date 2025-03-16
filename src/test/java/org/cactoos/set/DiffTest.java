/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link Diff}.
 *
 * @since 0.49
 */
final class DiffTest {

    /**
     * Tests that set difference can be computed correctly.
     * @since 0.49
     */
    @Test
    void computesSetDifference() {
        new Assertion<>(
            "Can't compute the difference of two sets",
            new Diff<>(
                new SetOf<>(1, 2, 3),
                new SetOf<>(2, 3, 4)
            ),
            new HasValues<>(1)
        ).affirm();
    }

    /**
     * Tests that set difference with empty second set returns the first set.
     * @since 0.49
     */
    @Test
    void computesSetDifferenceWithEmptySecondSet() {
        new Assertion<>(
            "Can't compute the difference with empty second set",
            new Diff<>(
                new SetOf<>(1, 2, 3),
                new SetOf<>()
            ),
            new HasValues<>(1, 2, 3)
        ).affirm();
    }

    /**
     * Tests that set difference with empty first set returns empty set.
     * @since 0.49
     */
    @Test
    void computesSetDifferenceWithEmptyFirstSet() {
        new Assertion<>(
            "Can't compute the difference with empty first set",
            new Diff<>(
                new SetOf<Integer>(),
                new SetOf<>(1, 2, 3)
            ),
            new HasSize(0)
        ).affirm();
    }

    /**
     * Tests that set difference works with java.util.Set.
     * @since 0.49
     */
    @Test
    void computesSetDifferenceWithJavaUtilSets() {
        final Set<Integer> first = new HashSet<>();
        first.add(1);
        first.add(2);
        first.add(3);
        final Set<Integer> second = new HashSet<>();
        second.add(3);
        second.add(4);
        second.add(5);
        new Assertion<>(
            "Can't compute the difference of two java.util.Set",
            new Diff<>(first, second),
            new HasValues<>(1, 2)
        ).affirm();
    }

    /**
     * Tests that set difference works with iterables.
     * @since 0.49
     */
    @Test
    void computesSetDifferenceWithIterables() {
        new Assertion<>(
            "Can't compute the difference of two iterables",
            new Diff<>(
                Collections.singletonList(1),
                Collections.singletonList(2)
            ),
            new HasValues<>(1)
        ).affirm();
    }

    /**
     * Tests that set difference works with iterators.
     * @since 0.49
     */
    @Test
    void computesSetDifferenceWithIterators() {
        new Assertion<>(
            "Can't compute the difference of two iterators",
            new Diff<>(
                new SetOf<>(1, 2, 3).iterator(),
                new SetOf<>(3, 4, 5).iterator()
            ),
            new HasValues<>(1, 2)
        ).affirm();
    }
}
