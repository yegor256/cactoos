/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Joined}.
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class JoinedTest {

    @Test
    void joinsStrings() {
        new Assertion<>(
            "Can't join strings",
            new Joined(" ", "hello", "world"),
            new HasString("hello world")
        ).affirm();
    }

    @Test
    void joinsTexts() {
        new Assertion<>(
            "Can't join texts",
            new Joined(
                new TextOf(" "),
                new TextOf("foo"),
                new TextOf("bar")
            ),
            new HasString("foo bar")
        ).affirm();
    }

    @Test
    void joinsTextsWitjStringDelimit() {
        new Assertion<>(
            "Can't join texts with String delimit",
            new Joined(
                " ",
                new TextOf("one"),
                new TextOf("two")
            ),
            new HasString("one two")
        ).affirm();
    }
}
