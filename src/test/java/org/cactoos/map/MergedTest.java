/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Merged}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class MergedTest {

    @Test
    @SuppressWarnings("unchecked")
    void behavesAsMap() {
        MatcherAssert.assertThat(
            "Must behave as a map",
            new Merged<>(
                new MapOf<>(
                    new MapEntry<>("a", 1)
                ),
                new MapOf<>(
                    new MapEntry<>("b", 2)
                )
            ),
            new AllOf<>(
                new IterableOf<>(
                    new BehavesAsMap<>("a", 1),
                    new BehavesAsMap<>("b", 2)
                )
            )
        );
    }

    @Test
    void createsMapFromMaps() {
        MatcherAssert.assertThat(
            "Must merge a few maps",
            new Merged<Integer, Integer>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 0)
                ),
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 1)
                )
            ),
            new IsEqual<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 0),
                    new MapEntry<>(1, 1)
                )
            )
        );
    }

    @Test
    void overridesValues() {
        MatcherAssert.assertThat(
            "Must override values",
            new Merged<Integer, Integer>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                ),
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 1)
                )
            ),
            new IsEqual<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 1)
                )
            )
        );
    }

    @Test
    void mergesEmptyMaps() {
        MatcherAssert.assertThat(
            "Must merge empty maps",
            new Merged<Integer, Integer>(
                new MapOf<Integer, Integer>(),
                new MapOf<Integer, Integer>()
            ),
            new IsEqual<>(
                new MapOf<Integer, Integer>()
            )
        );
    }

    @Test
    void throwsNullPointerForNullMap() {
        MatcherAssert.assertThat(
            "Exception is expected for merge with null",
            () -> new Merged<Integer, Integer>(
                new MapOf<Integer, Integer>(),
                null
            ).size(),
            new Throws<>(NullPointerException.class)
        );
    }

    @Test
    void behavesAsMapWithWildCards() {
        MatcherAssert.assertThat(
            "Must behave as a map with common type",
            new Merged<Number, Number>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 1)
                ),
                new MapOf<Long, Long>(
                    new MapEntry<>(2L, 1L)
                )
            ),
            new IsEqual<>(
                new MapOf<Number, Number>(
                    new MapEntry<>(1, 1),
                    new MapEntry<>(2L, 1L)
                )
            )
        );
    }

}
