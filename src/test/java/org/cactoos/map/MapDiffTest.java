/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasEntry;

/**
 * Test case for {@link MapDiff}.
 *
 * @since 1.0
 */
@SuppressWarnings(
    {
        "PMD.AvoidDuplicateLiterals",
        "PMD.AvoidDirectAccessToStaticFields"
    }
)
final class MapDiffTest {

    /**
     * String constant for "one".
     */
    private static final String ONE = "one";

    /**
     * String constant for "two".
     */
    private static final String TWO = "two";

    /**
     * String constant for "three".
     */
    private static final String THREE = "three";

    /**
     * String constant for "four".
     */
    private static final String FOUR = "four";

    /**
     * String constant for "five".
     */
    private static final String FIVE = "five";

    /**
     * Tests that map difference can be computed correctly.
     * @since 1.0
     */
    @Test
    void computesMapDifference() {
        new Assertion<>(
            "Can't compute the difference of two maps",
            new MapDiff<>(
                new MapOf<>(
                    new MapEntry<>(1, ONE),
                    new MapEntry<>(2, TWO),
                    new MapEntry<>(3, THREE)
                ),
                new MapOf<>(
                    new MapEntry<>(2, TWO),
                    new MapEntry<>(3, THREE),
                    new MapEntry<>(4, FOUR)
                )
            ),
            new HasEntry<>(1, ONE)
        ).affirm();
    }

    /**
     * Tests that map difference with empty second map returns the first map.
     * @since 1.0
     */
    @Test
    void computesMapDifferenceWithEmptySecondMap() {
        new Assertion<>(
            "Can't compute the difference with empty second map",
            new MapDiff<>(
                new MapOf<>(
                    new MapEntry<>(1, ONE),
                    new MapEntry<>(2, TWO),
                    new MapEntry<>(3, THREE)
                ),
                new MapOf<>()
            ),
            new HasEntry<>(1, ONE)
        ).affirm();
    }

    /**
     * Tests that map difference with empty first map returns empty map.
     * @since 1.0
     */
    @Test
    void computesMapDifferenceWithEmptyFirstMap() {
        new Assertion<>(
            "Can't compute the difference with empty first map",
            new MapDiff<Integer, String>(
                new MapOf<Integer, String>(),
                new MapOf<>(
                    new MapEntry<>(1, ONE),
                    new MapEntry<>(2, TWO),
                    new MapEntry<>(3, THREE)
                )
            ).size(),
            new IsEqual<>(0)
        ).affirm();
    }

    /**
     * Tests that map difference works with java.util.Map.
     * @since 1.0
     */
    @Test
    void computesMapDifferenceWithJavaUtilMaps() {
        final Map<Integer, String> first = new HashMap<>();
        first.put(1, ONE);
        first.put(2, TWO);
        first.put(3, THREE);
        final Map<Integer, String> second = new HashMap<>();
        second.put(3, THREE);
        second.put(4, FOUR);
        second.put(5, FIVE);
        new Assertion<>(
            "Can't compute the difference of two java.util.Map",
            new MapDiff<>(first, second),
            new HasEntry<>(1, ONE)
        ).affirm();
    }

    /**
     * Tests that map difference works with iterables.
     * @since 1.0
     */
    @Test
    void computesMapDifferenceWithIterables() {
        new Assertion<>(
            "Can't compute the difference of two iterables",
            new MapDiff<>(
                Collections.singletonList(new MapEntry<>(1, ONE)),
                Collections.singletonList(new MapEntry<>(2, TWO))
            ),
            new HasEntry<>(1, ONE)
        ).affirm();
    }

    /**
     * Tests that map difference works with iterators.
     * @since 1.0
     */
    @Test
    void computesMapDifferenceWithIterators() {
        new Assertion<>(
            "Can't compute the difference of two iterators",
            new MapDiff<>(
                new MapOf<>(
                    new MapEntry<>(1, ONE),
                    new MapEntry<>(2, TWO),
                    new MapEntry<>(3, THREE)
                ).entrySet().iterator(),
                new MapOf<>(
                    new MapEntry<>(3, THREE),
                    new MapEntry<>(4, FOUR),
                    new MapEntry<>(5, FIVE)
                ).entrySet().iterator()
            ),
            new HasEntry<>(1, ONE)
        ).affirm();
    }
}
