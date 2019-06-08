/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.text;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.TextHasString;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Replaced}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
public final class ReplacedTest {

    @Test
    public void replaceText() {
        new Assertion<>(
            "Can't replace a text",
            new Replaced(
                new TextOf("Hello!"),
                "ello", "i"
            ),
            new TextHasString("Hi!")
        ).affirm();
    }

    @Test
    public void notReplaceTextWhenSubstringNotFound() {
        final String text = "HelloAgain!";
        new Assertion<>(
            "Replace a text abnormally",
            new Replaced(
                new TextOf(text),
                "xyz", "i"
            ),
            new TextHasString(text)
        ).affirm();
    }

    @Test
    public void replacesAllOccurrences() {
        new Assertion<>(
            "Can't replace a text with multiple needle occurrences",
            new Replaced(
                new TextOf("one cat, two cats, three cats"),
                "cat",
                "dog"
            ),
            new TextHasString("one dog, two dogs, three dogs")
        ).affirm();
    }

    @Test
    public void regexConstantReplace() {
        new Assertion<>(
            "Cannot do simple replacement with regex",
            new Replaced(
                new TextOf("one cow two cows in the yard"),
                () -> Pattern.compile("cow"),
                matcher -> "pig"
            ),
            new TextHasString("one pig two pigs in the yard")
        ).affirm();
    }

    @Test
    public void regexDynamicReplace() {
        new Assertion<>(
            "Cannot do dynamic string replacement",
            new Replaced(
                new TextOf("one two THREE four FIVE six"),
                () -> Pattern.compile("[a-z]+"),
                matcher -> String.valueOf(matcher.group().length())
            ),
            new TextHasString("3 3 THREE 4 FIVE 3")
        ).affirm();
    }

    @Test
    public void emptyText() {
        new Assertion<>(
            "Substitution in empty text with non-empty regex.",
            new Replaced(
                new TextOf(""),
                "123",
                "WOW"
            ),
            new TextHasString("")
        ).affirm();
    }

    @Test
    public void emptyRegex() {
        new Assertion<>(
            "Substitution in text with empty regex.",
            new Replaced(
                new TextOf("abc"),
                "",
                "1"
            ),
            new TextHasString("1a1b1c1")
        ).affirm();
    }

    @Test
    public void emptyTextAndEmptyRegex() {
        new Assertion<>(
            "Substitution in empty text with empty regex.",
            new Replaced(
                new TextOf(""),
                "",
                "1"
            ),
            new TextHasString("1")
        ).affirm();
    }

    @Test
    public void invalidRegex() {
        final String regex = "invalid_regex{0,";
        new Assertion<>(
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
                    // @checkstyle MagicNumberCheck (1 line)
                    16
                ).getMessage(),
                PatternSyntaxException.class
            )
        ).affirm();
    }

    @Test
    public void nonDefaultCharsetText() {
        new Assertion<>(
            "Cannot do dynamic string replacement with non-default charset",
            new Replaced(
                new TextOf("abc def GHI JKL", StandardCharsets.UTF_16LE),
                () -> Pattern.compile("[A-Z]+"),
                matcher -> String.valueOf(matcher.group().length())
            ),
            new TextHasString("abc def 3 3")
        ).affirm();
    }

    @Test
    public void unicodeText() {
        new Assertion<>(
            "Cannot do dynamic string replacement with unicode characters",
            new Replaced(
                new TextOf("abc def GHI\u2300JKL"),
                () -> Pattern.compile("[a-z]+|\u2300"),
                matcher -> String.valueOf(matcher.group().length())
            ),
            new TextHasString("3 3 GHI1JKL")
        ).affirm();
    }
}
