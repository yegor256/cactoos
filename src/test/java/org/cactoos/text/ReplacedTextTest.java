/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.cactoos.matchers.TextHasString;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link ReplacedText}.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ReplacedTextTest {

    @Test
    public void replaceText() {
        MatcherAssert.assertThat(
            "Can't replace a text",
            new ReplacedText(
                new TextOf("Hello!"),
                "ello", "i"
            ),
            new TextHasString("Hi!")
        );
    }

    @Test
    public void notReplaceTextWhenSubstringNotFound() {
        final String text = "HelloAgain!";
        MatcherAssert.assertThat(
            "Replace a text abnormally",
            new ReplacedText(
                new TextOf(text),
                "xyz", "i"
            ),
            new TextHasString(text)
        );
    }

    @Test
    public void replacesAllOccurrences() {
        MatcherAssert.assertThat(
            "Can't replace a text with multiple needle occurrences",
            new ReplacedText(
                new TextOf("one cat, two cats, three cats"),
                "cat",
                "dog"
            ),
            new TextHasString("one dog, two dogs, three dogs")
        );
    }

    @Test
    public void regexConstantReplace() {
        MatcherAssert.assertThat(
            "Cannot do simple replacement with regex",
            new ReplacedText(
                new TextOf("one cow two cows in the yard"),
                () -> Pattern.compile("cow"),
                matcher -> "pig"
            ),
            new TextHasString("one pig two pigs in the yard")
        );
    }

    @Test
    public void regexDynamicReplace() {
        MatcherAssert.assertThat(
            "Cannot do dynamic string replacement",
            new ReplacedText(
                new TextOf("one two THREE four FIVE six"),
                () -> Pattern.compile("[a-z]+"),
                matcher -> String.valueOf(matcher.group().length())
            ),
            new TextHasString("3 3 THREE 4 FIVE 3")
        );
    }

    @Test
    public void emptyText() {
        MatcherAssert.assertThat(
            "Substitution in empty text with non-empty regex.",
            new ReplacedText(
                new TextOf(""),
                "123",
                "WOW"
            ),
            new TextHasString("")
        );
    }

    @Test
    public void emptyRegex() {
        MatcherAssert.assertThat(
            "Substitution in text with empty regex.",
            new ReplacedText(
                new TextOf("abc"),
                "",
                "1"
            ),
            new TextHasString("1a1b1c1")
        );
    }

    @Test
    public void emptyTextAndEmptyRegex() {
        MatcherAssert.assertThat(
            "Substitution in empty text with empty regex.",
            new ReplacedText(
                new TextOf(""),
                "",
                "1"
            ),
            new TextHasString("1")
        );
    }

    @Test(expected = PatternSyntaxException.class)
    public void invalidRegex() throws IOException {
        new ReplacedText(
            new TextOf("text"),
            "invalid_regex{0,",
            "error"
        ).asString();
    }

    @Test
    public void nonDefaultCharsetText() {
        MatcherAssert.assertThat(
            "Cannot do dynamic string replacement with non-default charset",
            new ReplacedText(
                new TextOf("abc def GHI JKL", StandardCharsets.UTF_16LE),
                () -> Pattern.compile("[A-Z]+"),
                matcher -> String.valueOf(matcher.group().length())
            ),
            new TextHasString("abc def 3 3")
        );
    }

    @Test
    public void unicodeText() {
        MatcherAssert.assertThat(
            "Cannot do dynamic string replacement with unicode characters",
            new ReplacedText(
                new TextOf("abc def GHI\u2300JKL"),
                () -> Pattern.compile("[a-z]+|\u2300"),
                matcher -> String.valueOf(matcher.group().length())
            ),
            new TextHasString("3 3 GHI1JKL")
        );
    }
}
