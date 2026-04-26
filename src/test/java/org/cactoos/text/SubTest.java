/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.func.FuncOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Sub}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SubTest {

    @Test
    void acceptsCharSequence() {
        MatcherAssert.assertThat(
            "Must cut a text with start",
            new Sub(
                new TextOf("sequence"),
                new FuncOf<>(sequence -> sequence.length() / 2)
            ),
            new IsText("ence")
        );
    }

    @Test
    void cutTextWithStartAndEnd() {
        MatcherAssert.assertThat(
            "Can't cut a text with start and end",
            new Sub("hello world", 2, 50),
            new HasString("llo world")
        );
    }

    @Test
    void cutTextWithStart() {
        MatcherAssert.assertThat(
            "Can't cut a text with start",
            new Sub("cut here", 2),
            new HasString("t here")
        );
    }

    @Test
    void cutTextWithNegativeStart() {
        MatcherAssert.assertThat(
            "Can't cut text with negative start",
            new Sub("hello world", -5),
            new HasString("world")
        );
    }

    @Test
    void cutTextWithNegativeStartAndEnd() {
        MatcherAssert.assertThat(
            "Can't cut text with negative start and positive end",
            new Sub("hello world", -5, 8),
            new HasString("wo")
        );
    }

    @Test
    void cutTextWithNegativeStartAndNegativeEnd() {
        MatcherAssert.assertThat(
            "Can't cut text with negative start and negative end",
            new Sub("hello world", -5, -2),
            new HasString("wor")
        );
    }
}
