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
import java.util.Calendar;
import java.util.IllegalFormatConversionException;
import java.util.Locale;
import java.util.UnknownFormatConversionException;
import org.cactoos.list.ListOf;
import org.cactoos.matchers.TextHasString;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link FormattedText}.
 *
 * @author Andriy Kryvtsun (kontiky@gmail.com)
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class FormattedTextTest {

    @Test
    public void formatsText() {
        MatcherAssert.assertThat(
            "Can't format a text",
            new FormattedText(
                "%d. Formatted %s", 1, "text"
            ),
            new TextHasString("1. Formatted text")
        );
    }

    @Test
    public void formatsTextWithObjects() {
        MatcherAssert.assertThat(
            "Can't format a text with objects",
            new FormattedText(
                new TextOf("%d. Number as %s"),
                new Integer(1),
                new String("string")
            ),
            new TextHasString("1. Number as string")
        );
    }

    @Test(expected = UnknownFormatConversionException.class)
    public void failsForInvalidPattern() throws IOException {
        new FormattedText(
            new TextOf("%%. Formatted %$"),
            new ListOf<>(1, "invalid")
        ).asString();
    }

    @Test
    public void formatsTextWithCollection() {
        MatcherAssert.assertThat(
            "Can't format a text with a collection",
            new FormattedText(
                new TextOf("%d. Formatted as %s"),
                new ListOf<>(1, "txt")
            ),
            new TextHasString("1. Formatted as txt")
        );
    }

    @Test(expected = IllegalFormatConversionException.class)
    public void ensuresThatFormatterFails() throws IOException {
        new FormattedText(
            new TextOf("Local time: %d"),
            Locale.ROOT,
            Calendar.getInstance()
        ).asString();
    }

    @Test
    public void formatsWithLocale() {
        MatcherAssert.assertThat(
            "Can't format a text with Locale",
            new FormattedText(
                // @checkstyle MagicNumber (1 line)
                "%,d", Locale.GERMAN, 1234567890
            ),
            new TextHasString("1.234.567.890")
        );
    }

    @Test
    public void formatsWithText() {
        MatcherAssert.assertThat(
            "Can't format a string with text",
            new FormattedText(
                "Format with text: %s",
                new TextOf("Cactoos")
            ),
            new TextHasString("Format with text: Cactoos")
        );
    }
}
