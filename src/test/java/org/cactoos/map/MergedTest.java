/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Merged}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class MergedTest {

    @Test
    @SuppressWarnings("unchecked")
    void behavesAsMap() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void createsMapFromMaps() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void overridesValues() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void mergesEmptyMaps() {
        new Assertion<>(
            "Must merge empty maps",
            new Merged<Integer, Integer>(
                new MapOf<Integer, Integer>(),
                new MapOf<Integer, Integer>()
            ),
            new IsEqual<>(
                new MapOf<Integer, Integer>()
            )
        ).affirm();
    }

    @Test
    void throwsNullPointerForNullMap() {
        new Assertion<>(
            "Exception is expected for merge with null",
            () -> new Merged<Integer, Integer>(
                new MapOf<Integer, Integer>(),
                null
            ).size(),
            new Throws<>(NullPointerException.class)
        ).affirm();
    }

    @Test
    void behavesAsMapWithWildCards() {
        new Assertion<>(
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
        ).affirm();
    }

}
