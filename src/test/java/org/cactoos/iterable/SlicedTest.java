/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Sliced}.
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SlicedTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void sliceIterable() {
        new Assertion<>(
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
        ).affirm();
    }
}
