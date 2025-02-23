/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test Case for {@link HeadOf}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class HeadOfTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void headIterable() {
        MatcherAssert.assertThat(
            "Must skip elements in iterable",
            new HeadOf<>(
                2, new IterableOf<>(
                    "one", "two", "three", "four"
                )
            ),
            new HasValues<>(
                "one",
                "two"
            )
        );
    }

}
