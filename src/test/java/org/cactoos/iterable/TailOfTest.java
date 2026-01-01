/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TailOf}.
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TailOfTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void tailIterable() {
        MatcherAssert.assertThat(
            "Can't get tail portion of iterable",
            new TailOf<>(
                3,
                "one", "two", "three", "four"
            ),
            Matchers.contains(
                "two",
                "three",
                "four"
            )
        );
    }
}
