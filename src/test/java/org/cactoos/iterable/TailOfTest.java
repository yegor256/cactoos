/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TailOf}.
 *
 * @since 0.31
 */
final class TailOfTest {

    @Test
        void tailIterable() {
        MatcherAssert.assertThat(
            "Can't get tail portion of iterable",
            new TailOf<>(
                3,
                "one", "two", "three", "four"
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<>(
                    new IsEqual<>("two"),
                    new IsEqual<>("three"),
                    new IsEqual<>("four")
                )
            )
        );
    }
}
