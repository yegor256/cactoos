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
import org.cactoos.TextHasString;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link ReplacedAllText}.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ReplacedAllTextTest {

    @Test
    public void replaceText() {
        MatcherAssert.assertThat(
            "Can't replace a text",
            new ReplacedAllText(
                new StringAsText("Hello!"),
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
            new ReplacedAllText(
                new StringAsText(text),
                "xyz", "i"
            ),
            new TextHasString(text)
        );
    }

    @Test
    public void replacesAllOccurrences() {
        MatcherAssert.assertThat(
            "Can't replace a text with multiple needle occurrences",
            new ReplacedAllText(
                new StringAsText("one cat, two cats, three cats"),
                "cat",
                "dog"
            ),
            new TextHasString("one dog, two dogs, three dogs")
        );
    }

    @Test
    public void replacesAllOccurrencesWithArray() {
        MatcherAssert.assertThat(
            "Can't replace all ocurrences in the array",
            new ReplacedAllText(
                new StringAsText("one day, two days, three days"),
                new String[]{"day"},
                "hour"
            ),
            new TextHasString("one hour, two hours, three hours")
        );
    }

    @Test
    public void replacesTextWithItem() {
        MatcherAssert.assertThat(
            "Can't replace a text with an element.",
            new ReplacedAllText(
                new StringAsText("jello!"),
                new String[]{"j"},
                "c"
            ),
            new TextHasString("cello!")
        );
    }

    @Test
    public void replacesTextWithSubstring() {
        MatcherAssert.assertThat(
            "Can't replace an substring with regex.",
            new ReplacedAllText(
                new StringAsText("Hello with regex!"),
                new RegexText("ello with"),
                "i"
            ),
            new TextHasString("Hi regex!")
        );
    }

    @Test
    public void replacesTextWithRegex() {
        MatcherAssert.assertThat(
            "Can't replace a text with regex.",
            new ReplacedAllText(
                new StringAsText("this is_an slug"),
                new RegexText("_| "),
                "-"
            ),
            new TextHasString("this-is-an-slug")
        );
    }

    @Test
    public void replacesAllOccurrencesWithRegex() {
        MatcherAssert.assertThat(
            "Can't replace all ocurrences with regex.",
            new ReplacedAllText(
                new StringAsText("one monkey, two monkeys"),
                new RegexText("monkey"),
                "human"
            ),
            new TextHasString("one human, two humans")
        );
    }

    @Test
    public void replacesSeveralNeedles() {
        MatcherAssert.assertThat(
            "Can't replace several needles.",
            new ReplacedAllText(
                new ReplacedAllText(
                    new StringAsText(
                        "one two three"
                    ),
                    new String[]{"one", "two", "three"},
                    ""
                ),
                new RegexText("\\ +"),
                ""
            ),
            new TextHasString("")
        );
    }

    @Test
    public void failsWhenSubstringNotFoundWihtRegex() {
        final String text = "Inmutable to your intents!";
        MatcherAssert.assertThat(
            "Replace a text abnormally with regex object.",
            new ReplacedAllText(
                new StringAsText(text),
                new RegexText("[0-9]"),
                "i"
            ),
            new TextHasString(text)
        );
    }

    @Test
    public void removesAllDuplicatedSpacesWithReplacedRegexText() {
        MatcherAssert.assertThat(
            "Can't remove duplicates.",
            new ReplacedAllText(
                new StringAsText("    one  cat, two  cats,     "),
                new RegexText("\\s+"),
                " "
            ),
            new TextHasString(" one cat, two cats, ")
        );
    }

    @Test
    public void checksCompareToForReplacedRegexText() throws IOException {
        MatcherAssert.assertThat(
            "Can't create a slug text.",
            new ReplacedAllText(
                new StringAsText("slug___text"),
                new RegexText("\\_+"),
                "-"
            )
            .compareTo(
                new StringAsText("slug-text")
            ),
            Matchers.equalTo(0)
        );
    }

}
