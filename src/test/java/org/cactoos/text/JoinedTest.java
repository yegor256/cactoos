/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Joined}.
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class JoinedTest {

    @Test
    void joinsStrings() {
        MatcherAssert.assertThat(
            "Can't join strings",
            new Joined(" ", "hello", "world"),
            new HasString("hello world")
        );
    }

    @Test
    void joinsTexts() {
        MatcherAssert.assertThat(
            "Can't join texts",
            new Joined(
                new TextOf(" "),
                new TextOf("foo"),
                new TextOf("bar")
            ),
            new HasString("foo bar")
        );
    }

    @Test
    void joinsTextsWitjStringDelimit() {
        MatcherAssert.assertThat(
            "Can't join texts with String delimit",
            new Joined(
                " ",
                new TextOf("one"),
                new TextOf("two")
            ),
            new HasString("one two")
        );
    }
}
