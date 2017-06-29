/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
import java.io.UncheckedIOException;
import org.cactoos.Func;
import org.cactoos.TextHasString;
import org.cactoos.func.UncheckedFunc;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link ReplacedAllText}.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ReplacedAllTextTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void replacesText() {
        MatcherAssert.assertThat(
            "Can't replace a text.",
            new ReplacedAllText(
                new StringAsText("Hello!"),
                new String[]{"ello"},
                "i"
            ),
            new TextHasString("Hi!")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void replacesTextWithRegex() {
        MatcherAssert.assertThat(
            "Can't replace a text.",
            new ReplacedAllText(
                new StringAsText("Hello!"),
                new RegexText("ello"),
                "i"
            ),
            new TextHasString("Hi!")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void notReplacesTextWhenSubstringNotFound() {
        final String text = "HelloAgain!";
        MatcherAssert.assertThat(
            "Replace a text abnormally.",
            new ReplacedAllText(
                new StringAsText(text),
                new String[]{"xyz"},
                "i"
            ),
            new TextHasString(text)
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void notReplacesTextWhenSubstringNotFoundWihtRegex() {
        final String text = "HelloAgain!";
        MatcherAssert.assertThat(
            "Replace a text abnormally.",
            new ReplacedAllText(
                new StringAsText(text),
                new RegexText("[0-9]"),
                "i"
            ),
            new TextHasString(text)
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void replacesAllOccurrences() {
        MatcherAssert.assertThat(
            "Can't replace a text with multiple needle occurrences.",
            new ReplacedAllText(
                new StringAsText("one cat, two cats, three cats"),
                new String[]{"cat"},
                "dog"
            ),
            new TextHasString("one dog, two dogs, three dogs")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void replacesAllOccurrencesWithRegex() {
        MatcherAssert.assertThat(
            "Can't replace a text with multiple needle occurrences.",
            new ReplacedAllText(
                new StringAsText("one cat, two cats, three cats"),
                new RegexText("cat"),
                "dog"
            ),
            new TextHasString("one dog, two dogs, three dogs")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void replacesAllOccurrencesWithReplacedArrayText() {
        MatcherAssert.assertThat(
            "Can't replace a text with multiple needle occurrences.",
            new ReplacedArrayText(
                new StringAsText("one cat, two cats, three cats"),
                new String[]{"cat"},
                "dog"
            ),
            new TextHasString("one dog, two dogs, three dogs")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void replacesAllNeedlesFound() {
        MatcherAssert.assertThat(
            "Can't replace a text with multiple needle occurrences.",
            new ReplacedAllText(
                new StringAsText("one cat + one dog + one pig = three mammals"),
                new String[]{"cat", "dog", "pig", "mammal"},
                "bird"
            ),
            new TextHasString("one bird + one bird + one bird = three birds")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void removesAllDuplicatedSpacesWithATextAsRegex() {
        MatcherAssert.assertThat(
            "Can't remove duplicates with string pattern (ReplacedAllText).",
            new ReplacedAllText(
                new StringAsText("    one  cat, two  cats,      "),
                new StringAsText("\\s+"),
                " "
            ),
            new TextHasString(" one cat, two cats, ")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void removesAllDuplicatedSpacesWithReplacedRegexText() {
        MatcherAssert.assertThat(
            "Can't remove duplicates with string pattern (ReplacedRegexText).",
            new ReplacedRegexText(
                new StringAsText("    one  cat, two  cats,      "),
                new StringAsText("\\s+"),
                " "
            ),
            new TextHasString(" one cat, two cats, ")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void removesAllDuplicatedSpacesWithCustomMethod() {
        MatcherAssert.assertThat(
            "Can't remove duplicates with string pattern (Custom method).",
            new ReplacedAllText(
                new ReplacedRegexText(
                    new StringAsText("    one  cat, two  cats,      "),
                    new StringAsText("\\s+"),
                    " "
                )
            ),
            new TextHasString(" one cat, two cats, ")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void removesSpacesDuplicatesWithRegexTextObject() {
        MatcherAssert.assertThat(
            "Can't remove duplicates with regex.",
            new ReplacedAllText(
                new StringAsText("    one  cat, two  cats,      "),
                new RegexText("\\s+"),
                " "
            ),
            new TextHasString(" one cat, two cats, ")
        );
    }

    @Test(expected = UncheckedIOException.class)
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void failsForInvalidRegexObject() {
        new UncheckedFunc<>(
            (Func<Integer, String>) i -> {
                return new ReplacedAllText(
                    new StringAsText("Inevitability"),
                    new RegexText("***"),
                    " "
                ).asString();
            }
        ).apply(1);
    }

    @Test(expected = UncheckedIOException.class)
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void failsForInvalidPatternExpression() {
        new UncheckedFunc<>(
            (Func<Integer, String>) i -> {
                return new ReplacedAllText(
                    new StringAsText("Inevitability"),
                    new StringAsText("***"),
                    " "
                ).asString();
            }
        ).apply(1);
    }

    @Test(expected = UncheckedIOException.class)
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void failsForInvalidRegexText() {
        new UncheckedFunc<>(
            (Func<Integer, String>) i -> {
                return new RegexText("***").asString();
            }
        ).apply(1);
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void checksCompareToForReplaceAllText() throws IOException {
        MatcherAssert.assertThat(
            "This text in unicode and ascii must be equal. (codecoverage)",
            new ReplacedAllText(
                new StringAsText("    spaces  emptiness      "),
                new StringAsText("\\s+"),
                " "
            )
            .compareTo(
                new StringAsText(" spaces emptiness ")
            ),
            Matchers.equalTo(0)
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void checksCompareToForReplacedRegexText() throws IOException {
        MatcherAssert.assertThat(
            "This text in unicode and ascii must be equal.",
            new ReplacedRegexText(
                new StringAsText("    spaces  emptiness      "),
                new StringAsText("\\s+"),
                " "
            )
            .compareTo(
                new StringAsText(" spaces emptiness ")
            ),
            Matchers.equalTo(0)
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void checksCompareToForReplacedArrayText() throws IOException {
        MatcherAssert.assertThat(
            "Can't replace a text with multiple needle occurrences.",
            new ReplacedArrayText(
                new StringAsText("one cat, two cats, three cats"),
                new String[]{"cat"},
                "dog"
            )
            .compareTo(
                new StringAsText("one dog, two dogs, three dogs")
            ),
            Matchers.equalTo(0)
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void checksCompareToForRegexText() throws IOException {
        MatcherAssert.assertThat(
            "Text is not equal. (compareTo-codecoverage)",
            new RegexText("\\s+")
            .compareTo(
                new StringAsText("\\s+")
            ),
            Matchers.equalTo(0)
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void failsTocompareToForRegexText() throws IOException {
        MatcherAssert.assertThat(
            "It should fail. (compareTo-codecoverage)",
            new RegexText("\\s+")
            .compareTo(
                new StringAsText("\\s++")
            ),
            Matchers.equalTo(-1)
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void failsTocompareToForRegexTextWithAltCtor() throws IOException {
        MatcherAssert.assertThat(
            "It should fail. (compareTo-codecoverage)",
            new RegexText(new StringAsText("\\s+"))
            .compareTo(
                new StringAsText("\\s++")
            ),
            Matchers.equalTo(-1)
        );
    }

}
