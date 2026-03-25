/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.IOException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link ItemAt}.
 *
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ItemAtTest {

    @Test
    void elementByPosIterableTest() {
        MatcherAssert.assertThat(
            "must take the item by position from the iterable",
            new ItemAt<>(
                1, new IterableOf<>(1, 2, 3)
            ),
            new HasValue<>(2)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void elementByPosFallbackIterableTest() {
        final int fallback = 5;
        MatcherAssert.assertThat(
            "must fallback to default one",
            new ItemAt<>(
                1, fallback, new IterableOf<>()
            ),
            new HasValue<>(fallback)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void elementByPosNoFallbackIterableTest() {
        MatcherAssert.assertThat(
            "must take the item by position from the iterable",
            new ItemAt<>(
                1, 5, new IterableOf<>(0, 1)
            ),
            new HasValue<>(1)
        );
    }

    @Test
    void elementByPosTest() {
        MatcherAssert.assertThat(
            "must take the item by position from the iterator",
            new ItemAt<>(
                1,
                new IterableOf<>(1, 2, 3)
            ),
            new HasValue<>(2)
        );
    }

    @Test
    void failForNegativePositionTest() {
        MatcherAssert.assertThat(
            "Must fail for negative position",
            () -> new ItemAt<>(
                -1,
                new IterableOf<>(1, 2, 3)
            ).value(),
            new Throws<>(
                "The position must be non-negative: -1",
                IOException.class
            )
        );
    }

    @Test
    void failForPosMoreLengthTest() {
        MatcherAssert.assertThat(
            "Must fail for greater than length position",
            () -> new ItemAt<>(
                3,
                new IterableOf<>(1, 2, 3)
            ).value(),
            new Throws<>(
                "The iterable doesn't have the position #3",
                IOException.class
            )
        );
    }

    @Test
    void sameValueTest() throws Exception {
        final ItemAt<Integer> item = new ItemAt<>(
            1,
            new IterableOf<>(1, 2, 3)
        );
        MatcherAssert.assertThat(
            "Not the same value",
            item,
            new HasValue<>(item.value())
        );
    }
}
