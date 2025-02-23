/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.text.ComparableText;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Equals}.
 *
 * @since 0.9
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class EqualsTest {

    @Test
    void compareEquals() {
        new Assertion<>(
            "Must compare if two integers are equal",
            new Equals<>(1, 1),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void compareNotEquals() {
        new Assertion<>(
            "Must compare if two integers are not equal",
            new Equals<>(1, 2),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void compareEqualsTextScalar() {
        final String str = "hello";
        new Assertion<>(
            "Must compare if two strings are equal",
            new Equals<>(str, str),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void compareNotEqualsTextScalar() {
        new Assertion<>(
            "Must compare if two strings are not equal",
            new Equals<>("world", "worle"),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void compareText() {
        new Assertion<>(
            "Must compare if two comparable test are equal, see #1174",
            new Equals<>(
                new ComparableText(new TextOf("hello")),
                new ComparableText(new TextOf("hello"))
            ),
            new HasValue<>(true)
        ).affirm();
    }
}
