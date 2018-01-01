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

import org.cactoos.TextHasString;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link AbbreviatedText}.
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.28
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.AvoidDuplicateLiterals",
        "PMD.TooManyMethods"
    }
)
public final class AbbreviatedTextTest {

    @Test
    public void abbreviatesAnEmptyText() {
        MatcherAssert.assertThat(
            "Can't abbreviate an empty text",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText("", 8),
            new TextHasString("")
        );
    }

    @Test
    public void abbreviatesText() {
        MatcherAssert.assertThat(
            "Can't abbreviate a text",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText("hello world", 8),
            new TextHasString("hello...")
        );
    }

    @Test
    public void abbreviatesTextOneCharSmaller() {
        MatcherAssert.assertThat(
            "Can't abbreviate a text one char smaller",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText("oo programming", 10),
            new TextHasString("oo prog...")
        );
    }

    @Test
    public void abbreviatesTextWithSameLength() {
        MatcherAssert.assertThat(
            "Can't abbreviate a text with same length",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText("elegant objects", 15),
            new TextHasString("elegant objects")
        );
    }

    @Test
    public void abbreviatesTextOneCharBigger() {
        MatcherAssert.assertThat(
            "Can't abbreviate a text one char bigger",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText("the old mcdonald", 17),
            new TextHasString("the old mcdonald")
        );
    }

    @Test
    public void abbreviatesTextTwoCharsBigger() {
        MatcherAssert.assertThat(
            "Can't abbreviate a text two chars bigger",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText("elegant objects", 17),
            new TextHasString("elegant objects")
        );
    }

    @Test
    public void abbreviatesTextWithWidthBiggerThanLength() {
        MatcherAssert.assertThat(
            "Can't abbreviate a text with width bigger than length",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText("cactoos framework", 50),
            new TextHasString("cactoos framework")
        );
    }

    @Test
    public void abbreviatesTextWithOffsetAndWidthSmaller() {
        MatcherAssert.assertThat(
            "Can't abbreviate a text with offset and width smaller",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText("it's a beautiful day", 2, 17),
            new TextHasString("'s a beautiful...")
        );
    }

    @Test
    public void abbreviatesTextWithOffsetAndWidthBigger() {
        MatcherAssert.assertThat(
            "Can't abbreviate a text with offset and width bigger",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText("game of thrones", 2, 50),
            new TextHasString("me of thrones")
        );
    }

    @Test
    public void abbreviatesTextBiggerThanDefaultMaxWidth() {
        // @checkstyle LineLengthCheck (10 line)
        MatcherAssert.assertThat(
            "Can't abbreviate a text bigger than default max width",
            new AbbreviatedText(
                "The quick brown fox jumps over the lazy black dog and after that returned to the cave"
            ),
            new TextHasString(
                "The quick brown fox jumps over the lazy black dog and after that returned to ..."
            )
        );
    }

    @Test
    public void abbreviatesTextWithOffsetAndBiggerThanDefaultMaxWidth() {
        // @checkstyle LineLengthCheck (10 line)
        MatcherAssert.assertThat(
            "Can't abbreviate a text with offset and bigger than default max width",
            new AbbreviatedText(
                "I tried making beer in the bathtub, I tried making synthetic gin, I tried making fudge for a living",
                2,
                // @checkstyle MagicNumberCheck (1 line)
                80
            ),
            new TextHasString(
                "tried making beer in the bathtub, I tried making synthetic gin, I tried makin..."
            )
        );
    }

}
