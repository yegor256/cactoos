/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.HashSet;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Grouped}.
 *
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class GroupedTest {

    @Test
    void groupedByNumber() {
        new Assertion<>(
            "Can't behave as a map",
            new Grouped<>(
                new IterableOf<>(1, 1, 1, 4, 5, 6, 7, 8, 9),
                number -> number,
                Object::toString
            ),
            new BehavesAsMap<>(1, new ListOf<>("1", "1", "1"))
        ).affirm();
    }

    @Test
    void emptyIterable() {
        new Assertion<>(
            "Can't build grouped by empty iterable",
            new Grouped<>(
                new IterableOf<Integer>(),
                number -> number,
                Object::toString
            ).entrySet(),
            new IsEqual<>(new HashSet<>())
        ).affirm();
    }

    @Test
    void groupedByOneHasEntries() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void groupedBySuperType() {
        new Assertion<>(
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
        ).affirm();
    }

}
