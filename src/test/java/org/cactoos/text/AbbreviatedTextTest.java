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

import org.cactoos.matchers.TextHasString;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link AbbreviatedText}.
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class AbbreviatedTextTest {

    @Test
    public void abbreviatesAnEmptyText() {
        final String msg = "";
        MatcherAssert.assertThat(
            "Can't abbreviate an msg text",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText(msg, 8),
            new TextHasString(msg)
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
        final String msg = "elegant objects";
        MatcherAssert.assertThat(
            "Can't abbreviate a text with same length",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText(msg, 15),
            new TextHasString(msg)
        );
    }

    @Test
    public void abbreviatesTextOneCharBigger() {
        final String msg = "the old mcdonald";
        MatcherAssert.assertThat(
            "Can't abbreviate a text one char bigger",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText(msg, 17),
            new TextHasString(msg)
        );
    }

    @Test
    public void abbreviatesTextTwoCharsBigger() {
        final String msg = "hi everybody!";
        MatcherAssert.assertThat(
            "Can't abbreviate a text two chars bigger",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText(msg, 15),
            new TextHasString(msg)
        );
    }

    @Test
    public void abbreviatesTextWithWidthBiggerThanLength() {
        final String msg = "cactoos framework";
        MatcherAssert.assertThat(
            "Can't abbreviate a text with width bigger than length",
            // @checkstyle MagicNumber (1 line)
            new AbbreviatedText(msg, 50),
            new TextHasString(msg)
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

}
