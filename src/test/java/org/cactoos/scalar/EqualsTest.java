/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.text.ComparableText;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Equals}.
 *
 * @since 0.9
 */
final class EqualsTest {

    @Test
    void compareEquals() {
        MatcherAssert.assertThat(
            "Must compare if two integers are equal",
            new Equals<>(1, 1),
            new HasValue<>(true)
        );
    }

    @Test
    void compareNotEquals() {
        MatcherAssert.assertThat(
            "Must compare if two integers are not equal",
            new Equals<>(1, 2),
            new HasValue<>(false)
        );
    }

    @Test
    void compareEqualsTextScalar() {
        MatcherAssert.assertThat(
            "Must compare if two strings are equal",
            new Equals<>("hello", "hello"),
            new HasValue<>(true)
        );
    }

    @Test
    void compareNotEqualsTextScalar() {
        MatcherAssert.assertThat(
            "Must compare if two strings are not equal",
            new Equals<>("world", "worle"),
            new HasValue<>(false)
        );
    }

    @Test
    void compareText() {
        MatcherAssert.assertThat(
            "Must compare if two comparable test are equal, see #1174",
            new Equals<>(
                new ComparableText(new TextOf("hello")),
                new ComparableText(new TextOf("hello"))
            ),
            new HasValue<>(true)
        );
    }
}
