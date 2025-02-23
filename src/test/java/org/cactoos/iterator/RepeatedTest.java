/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Repeated}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class RepeatedTest {

    @Test
    void allSameTest() {
        final int size = 42;
        final int element = 11;
        new Assertion<>(
            "Must generate an iterable with fixed size",
            new IterableOf<>(
                new Repeated<>(
                    size, element
                )
            ),
            new HasSize(size)
        ).affirm();
    }

    @Test
    void emptyTest() {
        new Assertion<>(
            "Must generate an empty iterator",
            new IterableOf<>(new Repeated<>(0, 0)),
            new HasSize(0)
        ).affirm();
    }
}
