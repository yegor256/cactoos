/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.util.Calendar;
import java.util.IllegalFormatConversionException;
import java.util.Locale;
import java.util.UnknownFormatConversionException;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link FormattedText}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class FormattedTextTest {

    @Test
    void formatsText() {
        MatcherAssert.assertThat(
            "Can't format a text",
            new FormattedText(
                "%d. Formatted %s", 1, "text"
            ),
            new HasString("1. Formatted text")
        );
    }

    @Test
    void formatsTextWithObjects() {
        MatcherAssert.assertThat(
            "Can't format a text with objects",
            new FormattedText(
                new TextOf("%d. Number as %s"),
                1,
                "string"
            ),
            new HasString("1. Number as string")
        );
    }

    @Test
    void failsForInvalidPattern() {
        MatcherAssert.assertThat(
            "Exception is expected for invalid format",
            () -> new FormattedText(
                new TextOf("%%. Formatted %$"),
                new ListOf<>(1, "invalid")
            ).asString(),
            new Throws<>(UnknownFormatConversionException.class)
        );
    }

    @Test
    void formatsTextWithCollection() {
        MatcherAssert.assertThat(
            "Can't format a text with a collection",
            new FormattedText(
                new TextOf("%d. Formatted as %s"),
                new ListOf<>(1, "txt")
            ),
            new HasString("1. Formatted as txt")
        );
    }

    @Test
    void ensuresThatFormatterFails() {
        MatcherAssert.assertThat(
            "Exception is expected for wrong format",
            () -> new FormattedText(
                new TextOf("Local time: %d"),
                Locale.ROOT,
                Calendar.getInstance()
            ).asString(),
            new Throws<>(IllegalFormatConversionException.class)
        );
    }

    @Test
    void formatsWithLocale() {
        MatcherAssert.assertThat(
            "Can't format a text with Locale",
            new FormattedText(
                "%,d", Locale.GERMAN, 1_234_567_890
            ),
            new HasString("1.234.567.890")
        );
    }

    @Test
    void formatsWithText() {
        MatcherAssert.assertThat(
            "Can't format a string with text",
            new FormattedText(
                "Format with text: %s",
                new TextOf("Cactoos")
            ),
            new HasString("Format with text: Cactoos")
        );
    }
}
