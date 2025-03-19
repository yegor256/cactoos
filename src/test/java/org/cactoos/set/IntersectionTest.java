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
 * Test case for {@link Intersection}.
 *
 * @since 0.58.0
 */
final class IntersectionTest {

    /**
     * Tests that set intersection can be computed correctly.
     */
    @Test
    void computesSetIntersection() {
        new Assertion<>(
            "Can't compute the intersection of two sets",
            new Intersection<>(
                new SetOf<>(1, 2, 3),
                new SetOf<>(2, 3, 4)
            ),
            new HasValues<>(2, 3)
        ).affirm();
    }

    /**
     * Tests that set intersection with empty second set returns empty set.
     */
    @Test
    void computesSetIntersectionWithEmptySecondSet() {
        new Assertion<>(
            "Can't compute the intersection with empty second set",
            new Intersection<>(
                new SetOf<>(1, 2, 3),
                new SetOf<>()
            ),
            new HasSize(0)
        ).affirm();
    }

    /**
     * Tests that set intersection with empty first set returns empty set.
     */
    @Test
    void computesSetIntersectionWithEmptyFirstSet() {
        new Assertion<>(
            "Can't compute the intersection with empty first set",
            new Intersection<>(
                new SetOf<Integer>(),
                new SetOf<>(1, 2, 3)
            ),
            new HasSize(0)
        ).affirm();
    }

    /**
     * Tests that set intersection works with java.util.Set.
     */
    @Test
    void computesSetIntersectionWithJavaUtilSets() {
        final Set<Integer> first = new HashSet<>();
        first.add(1);
        first.add(2);
        first.add(3);
        final Set<Integer> second = new HashSet<>();
        second.add(3);
        second.add(4);
        second.add(5);
        new Assertion<>(
            "Can't compute the intersection of two java.util.Set",
            new Intersection<>(first, second),
            new HasValues<>(3)
        ).affirm();
    }

    /**
     * Tests that set intersection works with iterables.
     */
    @Test
    void computesSetIntersectionWithIterables() {
        new Assertion<>(
            "Can't compute the intersection of two iterables",
            new Intersection<>(
                Collections.singletonList(1),
                Collections.singletonList(1)
            ),
            new HasValues<>(1)
        ).affirm();
    }

    /**
     * Tests that set intersection works with iterators.
     */
    @Test
    void computesSetIntersectionWithIterators() {
        new Assertion<>(
            "Can't compute the intersection of two iterators",
            new Intersection<>(
                new SetOf<>(1, 2, 3).iterator(),
                new SetOf<>(3, 4, 5).iterator()
            ),
            new HasValues<>(3)
        ).affirm();
    }
}
