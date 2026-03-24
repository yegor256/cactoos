/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Replaced}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class ReplacedTest {

    @Test
    void replaceText() {
        MatcherAssert.assertThat(
            "Can't replace a text",
            new Replaced(
                new TextOf("Hello!"),
                "ello", "i"
            ),
            new HasString("Hi!")
        );
    }

    @Test
    void notReplaceTextWhenSubstringNotFound() {
        final String text = "HelloAgain!";
        MatcherAssert.assertThat(
            "Replace a text abnormally",
            new Replaced(
                new TextOf(text),
                "xyz", "i"
            ),
            new HasString(text)
        );
    }

    @Test
    void replacesAllOccurrences() {
        MatcherAssert.assertThat(
            "Can't replace a text with multiple needle occurrences",
            new Replaced(
                new TextOf("one cat, two cats, three cats"),
                "cat",
                "dog"
            ),
            new HasString("one dog, two dogs, three dogs")
        );
    }

    @Test
    void regexConstantReplace() {
        MatcherAssert.assertThat(
            "Cannot do simple replacement with regex",
            new Replaced(
                new TextOf("one cow two cows in the yard"),
                () -> Pattern.compile("cow"),
                matcher -> "pig"
            ),
            new HasString("one pig two pigs in the yard")
        );
    }

    @Test
    void regexDynamicReplace() {
        MatcherAssert.assertThat(
            "Cannot do dynamic string replacement",
            new Replaced(
                new TextOf("one two THREE four FIVE six"),
                () -> Pattern.compile("[a-z]+"),
                matcher -> String.valueOf(matcher.group().length())
            ),
            new HasString("3 3 THREE 4 FIVE 3")
        );
    }

    @Test
    void emptyText() {
        MatcherAssert.assertThat(
            "Substitution in empty text with non-empty regex.",
            new Replaced(
                new TextOf(""),
                "123",
                "WOW"
            ),
            new HasString("")
        );
    }

    @Test
    void emptyRegex() {
        MatcherAssert.assertThat(
            "Substitution in text with empty regex.",
            new Replaced(
                new TextOf("abc"),
                "",
                "1"
            ),
            new HasString("1a1b1c1")
        );
    }

    @Test
    void emptyTextAndEmptyRegex() {
        MatcherAssert.assertThat(
            "Substitution in empty text with empty regex.",
            new Replaced(
                new TextOf(""),
                "",
                "1"
            ),
            new HasString("1")
        );
    }

    @Test
    void invalidRegex() {
        final String regex = "invalid_regex{0,";
        MatcherAssert.assertThat(
            "Doesn't throw proper exception",
            new Replaced(
                new TextOf("text"),
                regex,
                "error"
            )::asString,
            new Throws<>(
                new PatternSyntaxException(
                    "Unclosed counted closure",
                    regex,
                    16
                ).getMessage(),
                PatternSyntaxException.class
            )
        );
    }

    @Test
    void nonDefaultCharsetText() {
        MatcherAssert.assertThat(
            "Cannot do dynamic string replacement with non-default charset",
            new Replaced(
                new TextOf("abc def GHI JKL", StandardCharsets.UTF_16LE),
                () -> Pattern.compile("[A-Z]+"),
                matcher -> String.valueOf(matcher.group().length())
            ),
            new HasString("abc def 3 3")
        );
    }

    @Test
    void unicodeText() {
        MatcherAssert.assertThat(
            "Cannot do dynamic string replacement with unicode characters",
            new Replaced(
                new TextOf("abc def GHI\u2300JKL"),
                () -> Pattern.compile("[a-z]+|\u2300"),
                matcher -> String.valueOf(matcher.group().length())
            ),
            new HasString("3 3 GHI1JKL")
        );
    }
}
