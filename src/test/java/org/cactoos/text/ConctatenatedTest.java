/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.text;

import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Concatenated}.
 * @since 0.47
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class ConctatenatedTest {

    @Test
    void concatenateText() {
        new Assertion<>(
            "Must contcatenate single text",
            new Concatenated(new TextOf("bar")),
            new IsText("bar")
        ).affirm();
    }

    @Test
    void concatenateTexts() {
        new Assertion<>(
            "Must contcatenate multi texts",
            new Concatenated(new TextOf("abc"), new TextOf("xyz")),
            new IsText("abcxyz")
        ).affirm();
    }

    @Test
    void concatenateIterables() {
        new Assertion<>(
            "Must concatenate iterables",
            new Concatenated(new IterableOf<>(new TextOf("foo"), new TextOf("foo1"))),
            new IsText("foofoo1")
        ).affirm();
    }

    @Test
    void concatenateString() {
        new Assertion<>(
            "Must contcatenate single string",
            new Concatenated("bar"),
            new IsText("bar")
        ).affirm();
    }

    @Test
    void concatenateStrings() {
        new Assertion<>(
            "Must contcatenate multi strings",
            new Concatenated("abc", "xyz"),
            new IsText("abcxyz")
        ).affirm();
    }
}
