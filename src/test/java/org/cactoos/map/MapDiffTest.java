/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasEntry;

/**
 * Test case for {@link MapDiff}.
 *
 * @since 0.58.0
 */
final class MapDiffTest {

    /**
     * Tests that map difference can be computed correctly.
     * @since 0.58.0
     */
    @Test
    void computesMapDifference() {
        MatcherAssert.assertThat(
            "Can't compute the difference of two maps",
            new MapDiff<>(
                new MapOf<>(
                    new MapEntry<>(1, "one"),
                    new MapEntry<>(2, "two"),
                    new MapEntry<>(3, "three")
                ),
                new MapOf<>(
                    new MapEntry<>(2, "two"),
                    new MapEntry<>(3, "three"),
                    new MapEntry<>(4, "four")
                )
            ),
            new HasEntry<>(1, "one")
        );
    }

    /**
     * Tests that map difference with empty second map returns the first map.
     * @since 0.58.0
     */
    @Test
    void computesMapDifferenceWithEmptySecondMap() {
        MatcherAssert.assertThat(
            "Can't compute the difference with empty second map",
            new MapDiff<>(
                new MapOf<>(
                    new MapEntry<>(5, "five"),
                    new MapEntry<>(6, "six"),
                    new MapEntry<>(7, "seven")
                ),
                new MapOf<>()
            ),
            new HasEntry<>(5, "five")
        );
    }

    /**
     * Tests that map difference with empty first map returns empty map.
     * @since 0.58.0
     */
    @Test
    void computesMapDifferenceWithEmptyFirstMap() {
        MatcherAssert.assertThat(
            "Can't compute the difference with empty first map",
            new MapDiff<Integer, String>(
                new MapOf<Integer, String>(),
                new MapOf<>(
                    new MapEntry<>(8, "eight"),
                    new MapEntry<>(9, "nine"),
                    new MapEntry<>(10, "ten")
                )
            ).size(),
            new IsEqual<>(0)
        );
    }

    /**
     * Tests that map difference works with java.util.Map.
     * @since 0.58.0
     */
    @Test
    void computesMapDifferenceWithJavaUtilMaps() {
        final Map<Integer, String> first = new HashMap<>();
        first.put(11, "eleven");
        first.put(12, "twelve");
        first.put(13, "thirteen");
        final Map<Integer, String> second = new HashMap<>();
        second.put(13, "thirteen");
        second.put(14, "fourteen");
        second.put(15, "fifteen");
        MatcherAssert.assertThat(
            "Can't compute the difference of two java.util.Map",
            new MapDiff<>(first, second),
            new HasEntry<>(11, "eleven")
        );
    }

    /**
     * Tests that map difference works with iterables.
     * @since 0.58.0
     */
    @Test
    void computesMapDifferenceWithIterables() {
        MatcherAssert.assertThat(
            "Can't compute the difference of two iterables",
            new MapDiff<>(
                Collections.singletonList(new MapEntry<>(16, "sixteen")),
                Collections.singletonList(new MapEntry<>(17, "seventeen"))
            ),
            new HasEntry<>(16, "sixteen")
        );
    }

    /**
     * Tests that map difference works with iterators.
     * @since 0.58.0
     */
    @Test
    void computesMapDifferenceWithIterators() {
        MatcherAssert.assertThat(
            "Can't compute the difference of two iterators",
            new MapDiff<>(
                new MapOf<>(
                    new MapEntry<>(18, "eighteen"),
                    new MapEntry<>(19, "nineteen"),
                    new MapEntry<>(20, "twenty")
                ).entrySet().iterator(),
                new MapOf<>(
                    new MapEntry<>(20, "twenty"),
                    new MapEntry<>(21, "twenty-one"),
                    new MapEntry<>(22, "twenty-two")
                ).entrySet().iterator()
            ),
            new HasEntry<>(18, "eighteen")
        );
    }
}
