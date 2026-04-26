/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Repeated}.
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class RepeatedTest {

    @Test
    void allSameTest() {
        final int size = 42;
        MatcherAssert.assertThat(
            "Must generate an iterable with fixed size",
            new IterableOf<>(
                new Repeated<>(
                    size, 11
                )
            ),
            new HasSize(size)
        );
    }

    @Test
    void emptyTest() {
        MatcherAssert.assertThat(
            "Must generate an empty iterator",
            new IterableOf<>(new Repeated<>(0, 0)),
            new HasSize(0)
        );
    }
}
