/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.HashSet;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Grouped}.
 *
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class GroupedTest {

    @Test
    void groupedByNumber() {
        MatcherAssert.assertThat(
            "Can't behave as a map",
            new Grouped<>(
                new IterableOf<>(1, 1, 1, 4, 5, 6, 7, 8, 9),
                number -> number,
                Object::toString
            ),
            new BehavesAsMap<>(1, new ListOf<>("1", "1", "1"))
        );
    }

    @Test
    void emptyIterable() {
        MatcherAssert.assertThat(
            "Can't build grouped by empty iterable",
            new Grouped<>(
                new IterableOf<Integer>(),
                number -> number,
                Object::toString
            ).entrySet(),
            new IsEqual<>(new HashSet<>())
        );
    }

    @Test
    void groupedByOneHasEntries() {
        MatcherAssert.assertThat(
            "Can't group int values",
            new Grouped<>(
                new IterableOf<>(1, 1, 1, 4, 5, 6, 7, 8, 9),
                number -> number,
                Object::toString
            ),
            new IsMapContaining<>(
                new IsEqual<>(1),
                new IsEqual<>(new ListOf<>("1", "1", "1"))
            )
        );
    }

    @Test
    void groupedBySuperType() {
        MatcherAssert.assertThat(
            "Must group Number values",
            new Grouped<>(
                new IterableOf<Number>(1, 1f, 1L, 4f, 5, 6f, 7, 8f, 9),
                Number::intValue,
                Object::toString
            ),
            new IsMapContaining<>(
                new IsEqual<>(1),
                new IsEqual<>(new ListOf<>("1", "1.0", "1"))
            )
        );
    }
}
