/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Sliced}.
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SlicedTest {

    @Test
        void sliceIterable() {
        MatcherAssert.assertThat(
            "Must get sliced iterable of elements",
            new Sliced<>(
                1,
                2,
                "one", "two", "three", "four"
            ),
            new IsEqual<>(
                new IterableOf<>(
                    "two", "three"
                )
            )
        );
    }
}
