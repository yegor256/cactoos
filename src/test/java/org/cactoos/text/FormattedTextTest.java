/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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

import java.util.Calendar;
import java.util.IllegalFormatConversionException;
import java.util.Locale;
import java.util.UnknownFormatConversionException;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link FormattedText}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class FormattedTextTest {

    @Test
    void formatsText() {
        new Assertion<>(
            "Can't format a text",
            new FormattedText(
                "%d. Formatted %s", 1, "text"
            ),
            new HasString("1. Formatted text")
        ).affirm();
    }

    @Test
    void formatsTextWithObjects() {
        new Assertion<>(
            "Can't format a text with objects",
            new FormattedText(
                new TextOf("%d. Number as %s"),
                1,
                new String("string")
            ),
            new HasString("1. Number as string")
        ).affirm();
    }

    @Test
    void failsForInvalidPattern() {
        Assertions.assertThrows(
            UnknownFormatConversionException.class,
            () -> new FormattedText(
                new TextOf("%%. Formatted %$"),
                new ListOf<>(1, "invalid")
            ).asString()
        );
    }

    @Test
    void formatsTextWithCollection() {
        new Assertion<>(
            "Can't format a text with a collection",
            new FormattedText(
                new TextOf("%d. Formatted as %s"),
                new ListOf<>(1, "txt")
            ),
            new HasString("1. Formatted as txt")
        ).affirm();
    }

    @Test
    void ensuresThatFormatterFails() throws Exception {
        Assertions.assertThrows(
            IllegalFormatConversionException.class,
            () -> new FormattedText(
                new TextOf("Local time: %d"),
                Locale.ROOT,
                Calendar.getInstance()
            ).asString()
        );
    }

    @Test
    void formatsWithLocale() {
        new Assertion<>(
            "Can't format a text with Locale",
            new FormattedText(
                "%,d", Locale.GERMAN, 1_234_567_890
            ),
            new HasString("1.234.567.890")
        ).affirm();
    }

    @Test
    void formatsWithText() {
        new Assertion<>(
            "Can't format a string with text",
            new FormattedText(
                "Format with text: %s",
                new TextOf("Cactoos")
            ),
            new HasString("Format with text: Cactoos")
        ).affirm();
    }
}
