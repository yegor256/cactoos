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
 * Test case for {@link Union}.
 *
 * @since 0.49
 */
final class UnionTest {

    /**
     * Tests that set union can be computed correctly.
     * @since 0.49
     */
    @Test
    void computesSetUnion() {
        new Assertion<>(
            "Can't compute the union of two sets",
            new Union<>(
                new SetOf<>(1, 2, 3),
                new SetOf<>(3, 4, 5)
            ),
            new HasValues<>(1, 2, 3, 4, 5)
        ).affirm();
    }

    /**
     * Tests that set union with empty second set returns the first set.
     * @since 0.49
     */
    @Test
    void computesSetUnionWithEmptySecondSet() {
        new Assertion<>(
            "Can't compute the union with empty second set",
            new Union<>(
                new SetOf<>(1, 2, 3),
                new SetOf<>()
            ),
            new HasValues<>(1, 2, 3)
        ).affirm();
    }

    /**
     * Tests that set union with empty first set returns the second set.
     * @since 0.49
     */
    @Test
    void computesSetUnionWithEmptyFirstSet() {
        new Assertion<>(
            "Can't compute the union with empty first set",
            new Union<>(
                new SetOf<Integer>(),
                new SetOf<>(1, 2, 3)
            ),
            new HasValues<>(1, 2, 3)
        ).affirm();
    }

    /**
     * Tests that set union works with java.util.Set.
     * @since 0.49
     */
    @Test
    void computesSetUnionWithJavaUtilSets() {
        final Set<Integer> first = new HashSet<>();
        first.add(1);
        first.add(2);
        first.add(3);
        final Set<Integer> second = new HashSet<>();
        second.add(3);
        second.add(4);
        second.add(5);
        new Assertion<>(
            "Can't compute the union of two java.util.Set",
            new Union<>(first, second),
            new HasValues<>(1, 2, 3, 4, 5)
        ).affirm();
    }

    /**
     * Tests that set union works with iterables.
     * @since 0.49
     */
    @Test
    void computesSetUnionWithIterables() {
        new Assertion<>(
            "Can't compute the union of two iterables",
            new Union<>(
                Collections.singletonList(1),
                Collections.singletonList(2)
            ),
            new HasValues<>(1, 2)
        ).affirm();
    }

    /**
     * Tests that set union works with iterators.
     * @since 0.49
     */
    @Test
    void computesSetUnionWithIterators() {
        new Assertion<>(
            "Can't compute the union of two iterators",
            new Union<>(
                new SetOf<>(1, 2, 3).iterator(),
                new SetOf<>(3, 4, 5).iterator()
            ),
            new HasValues<>(1, 2, 3, 4, 5)
        ).affirm();
    }

    /**
     * Tests that set union with identical sets works correctly.
     * @since 0.49
     */
    @Test
    void computesSetUnionWithIdenticalSets() {
        new Assertion<>(
            "Can't compute the union of identical sets",
            new Union<>(
                new SetOf<>(1, 2, 3),
                new SetOf<>(1, 2, 3)
            ),
            new HasSize(3)
        ).affirm();
    }
}
