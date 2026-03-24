/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Split}.
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SplitTest {

    @Test
    void splitStringWithStringRegex() {
        MatcherAssert.assertThat(
            "Must split string with string regex",
            new Split("Hello world!", "\\s+"),
            new IsEqual<>(new IterableOf<>(new TextOf("Hello"), new TextOf("world!")))
        );
    }

    @Test
    void splitStringWithTextRegex() {
        MatcherAssert.assertThat(
            "Must split string with text regex",
            new Split("Cactoos OOP!", new TextOf("\\s")),
            new IsEqual<>(new IterableOf<>(new TextOf("Cactoos"), new TextOf("OOP!")))
        );
    }

    @Test
    void splitTextWithStringRegex() {
        MatcherAssert.assertThat(
            "Must split text with string regex",
            new Split(new TextOf("Cact4Primitives!"), "\\d+"),
            new IsEqual<>(new IterableOf<>(new TextOf("Cact"), new TextOf("Primitives!")))
        );
    }

    @Test
    void splitTextWithTextRegex() {
        MatcherAssert.assertThat(
            "Must split text with text regex",
            new Split(new TextOf("Split#OOP"), new TextOf("#")),
            new IsEqual<>(new IterableOf<>(new TextOf("Split"), new TextOf("OOP")))
        );
    }

    @Test
    void splitStringWithStringRegexAndLimit() {
        MatcherAssert.assertThat(
            "Must split string with string regex and limit",
            new Split("Hello! ! world!", " ", 2),
            new IsEqual<>(new IterableOf<>(new TextOf("Hello!"), new TextOf("! world!")))
        );
    }

    @Test
    void splitStringWithTextRegexAndLimit() {
        MatcherAssert.assertThat(
            "Must split string with text regex and limit",
            new Split("Cactoos! ! OOP!", new TextOf(" "), 2),
            new IsEqual<>(new IterableOf<>(new TextOf("Cactoos!"), new TextOf("! OOP!")))
        );
    }

    @Test
    void splitTextWithStringRegexAndLimit() {
        final Text txt = new TextOf("Cact!4Primitives");
        MatcherAssert.assertThat(
            "Must split text with string regex and limit",
            new Split(txt, "4", 1),
            new IsEqual<>(new IterableOf<>(txt))
        );
    }

    @Test
    void splitTextWithTextRegexAndLimit() {
        final Text txt = new TextOf("Split!# #OOP");
        MatcherAssert.assertThat(
            "Must split text with text regex and limit",
            new Split(txt, "\\W+", 1),
            new IsEqual<>(new IterableOf<>(txt))
        );
    }

    @Test
    void splitWithZeroLimit() {
        MatcherAssert.assertThat(
            "Must split string with string regex and zero limit",
            new Split("Hello. The! !world", " +", 0),
            new IsEqual<>(
                new IterableOf<>(new TextOf("Hello."), new TextOf("The!"), new TextOf("!world"))
            )
        );
    }

    @Test
    void splitWithNegativeLimit() {
        MatcherAssert.assertThat(
            "Must split string with string regex and negative limit",
            new Split("Hello: The world", " ", -1),
            new IsEqual<>(
                new IterableOf<>(new TextOf("Hello:"), new TextOf("The"), new TextOf("world"))
            )
        );
    }
}
