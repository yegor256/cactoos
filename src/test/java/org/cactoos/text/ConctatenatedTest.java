/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.text;

import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Concatenated}.
 * @since 0.47
 */
final class ConctatenatedTest {

    @Test
    void concatenateText() {
        MatcherAssert.assertThat(
            "Must contcatenate single text",
            new Concatenated(new TextOf("bar")),
            new IsText("bar")
        );
    }

    @Test
    void concatenateTexts() {
        MatcherAssert.assertThat(
            "Must contcatenate multi texts",
            new Concatenated(new TextOf("abc"), new TextOf("xyz")),
            new IsText("abcxyz")
        );
    }

    @Test
    void concatenateIterables() {
        MatcherAssert.assertThat(
            "Must concatenate iterables",
            new Concatenated(new IterableOf<>(new TextOf("foo"), new TextOf("foo1"))),
            new IsText("foofoo1")
        );
    }

    @Test
    void concatenateString() {
        MatcherAssert.assertThat(
            "Must contcatenate single string",
            new Concatenated("bar"),
            new IsText("bar")
        );
    }

    @Test
    void concatenateStrings() {
        MatcherAssert.assertThat(
            "Must contcatenate multi strings",
            new Concatenated("abc", "xyz"),
            new IsText("abcxyz")
        );
    }
}
