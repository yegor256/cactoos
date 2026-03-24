/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test Case for {@link Sorted}.
 * @since 0.19
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SortedTest {

    @Test
        void sortsIterable() {
        MatcherAssert.assertThat(
            "Must sort elements in iterator",
            new IterableOf<>(
                new Sorted<>(
                    new IteratorOf<>(
                        "one", "two", "three", "four"
                    )
                )
            ),
            new HasValues<>(
                "four", "one", "three", "two"
            )
        );
    }

}
