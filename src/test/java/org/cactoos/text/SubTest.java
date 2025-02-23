/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Func;
import org.cactoos.func.FuncOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
        final Func<CharSequence, Integer> half = new FuncOf<>(
            sequence -> sequence.length() / 2
        );
        new Assertion<>(
            "Must cut a text with start",
            new Sub(new TextOf("sequence"), half),
            new IsText("ence")
        ).affirm();
    }

    @Test
    void cutTextWithStartAndEnd() {
        new Assertion<>(
            "Can't cut a text with start and end",
            new Sub("hello world", 2, 50),
            new HasString("llo world")
        ).affirm();
    }

    @Test
    void cutTextWithStart() {
        new Assertion<>(
            "Can't cut a text with start",
            new Sub("cut here", 2),
            new HasString("t here")
        ).affirm();
    }

    @Test
    void cutTextWithNegativeStart() {
        new Assertion<>(
            "Can't cut text with negative start",
            new Sub("hello world", -5),
            new HasString("world")
        ).affirm();
    }

    @Test
    void cutTextWithNegativeStartAndEnd() {
        new Assertion<>(
            "Can't cut text with negative start and positive end",
            new Sub("hello world", -5, 8),
            new HasString("wo")
        ).affirm();
    }

    @Test
    void cutTextWithNegativeStartAndNegativeEnd() {
        new Assertion<>(
            "Can't cut text with negative start and negative end",
            new Sub("hello world", -5, -2),
            new HasString("wor")
        ).affirm();
    }

}
