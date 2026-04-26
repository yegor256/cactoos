/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Unit tests for {@link Joined}.
 * @since 0.36
 */
final class JoinedTest {
    @Test
    void joinsOk() {
        MatcherAssert.assertThat(
            "Cannot properly join inputs",
            new Joined(
                new InputOf("first"),
                new InputOf("second"),
                new InputOf("third")
            ),
            new HasContent("firstsecondthird")
        );
    }

    /**
     * Must join inputs of the iterable in the given order.
     */
    @Test
    void fromIterable() {
        MatcherAssert.assertThat(
            "Can't join iterable of inputs",
            new Joined(
                new IterableOf<>(
                    new InputOf("ab"),
                    new InputOf("cde"),
                    new InputOf("fghi")
                )
            ),
            new HasContent("abcdefghi")
        );
    }
}
