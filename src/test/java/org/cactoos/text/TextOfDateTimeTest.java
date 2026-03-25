/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsBlank;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link TextOfDateTime}.
 *
 * @since 1.0.0
 * @checkstyle StringLiteralsConcatenationCheck (1000 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class TextOfDateTimeTest {

    @Test
    void readsLocalDateFormattedWithFormatString() {
        MatcherAssert.assertThat(
            "Must format a LocalDate with format.",
            new TextOfDateTime("yyyy-MM-dd HH:mm:ss", LocalDate.of(2017, 12, 13)),
            new IsText("2017-12-13 00:00:00")
        );
    }

    @Test
    void readsLocalDateFormattedWithFormatStringWithLocale() {
        MatcherAssert.assertThat(
            "Must format a LocalDate with format using locale.",
            new TextOfDateTime(
                "yyyy MMM dd. HH.mm.ss", LocalDate.of(2017, 12, 13), Locale.FRENCH
            ),
            new IsText("2017 déc. 13. 00.00.00")
        );
    }

    @Test
    void readsLocalDateFormattedAsIsoDateTime() {
        final LocalDate date = LocalDate.of(2017, 12, 13);
        MatcherAssert.assertThat(
            "Must format a LocalDate with default/ISO format.",
            new TextOfDateTime(date),
            new IsText(
                MessageFormat.format(
                "2017-12-13T00:00:00{0}",
                date.atTime(LocalTime.MIN).atZone(ZoneId.systemDefault())
                    .getOffset().toString()
                )
            )
        );
    }

    @Test
    void readsCurrentLocalDateAsText() throws Exception {
        MatcherAssert.assertThat(
            "Must format a LocalDate with ISO format.",
            new TextOfDateTime(LocalDate.now()).asString(),
            new IsNot<>(new IsBlank())
        );
    }

    @Test
    void localDateTimeFormattedAsIsoDateTime() {
        final LocalDateTime date = LocalDateTime.of(
            2017, 12, 13, 14, 15, 16, 17
        );
        MatcherAssert.assertThat(
            "Must format a LocalDateTime with default/ISO format.",
            new TextOfDateTime(date),
            new IsText(
                MessageFormat.format(
                    "2017-12-13T14:15:16.000000017{0}",
                    date.atZone(ZoneId.systemDefault()).getOffset().toString()
                )
            )
        );
    }

    @Test
    void localDateTimeFormattedWithFormatString() {
        MatcherAssert.assertThat(
            "Must format a LocalDateTime with format.",
            new TextOfDateTime(
                "yyyy-MM-dd HH:mm:ss",
                LocalDateTime.of(2017, 12, 13, 14, 15, 16, 17)
            ),
            new IsText("2017-12-13 14:15:16")
        );
    }

    @Test
    void localDateTimeFormattedWithFormatStringWithLocale() {
        MatcherAssert.assertThat(
            "Must format a LocalDateTime with format using locale.",
            new TextOfDateTime(
                "yyyy MMM dd. HH.mm.ss",
                LocalDateTime.of(2017, 12, 13, 14, 15, 16, 17),
                Locale.FRENCH
            ),
            new IsText("2017 déc. 13. 14.15.16")
        );
    }

    @Test
    void currentLocalDateTimeAsText() throws Exception {
        MatcherAssert.assertThat(
            "Must format a LocalDateTime with ISO format.",
            new TextOfDateTime(LocalDateTime.now()).asString(),
            new IsNot<>(new IsNull<>())
        );
    }

    @Test
    void dateFormattedUsingIsoFormatter() {
        final ZonedDateTime zoned = ZonedDateTime.of(
            2017, 12, 13, 14, 15, 16, 17_000_000, ZoneId.systemDefault()
        );
        MatcherAssert.assertThat(
            "Must format a java.util.Date with ISO format.",
            new TextOfDateTime(Date.from(zoned.toInstant())),
            new IsText("2017-12-13T14:15:16.017" + zoned.getOffset())
        );
    }

    @Test
    void dateFormattedUsingCustomFormat()  {
        MatcherAssert.assertThat(
            "Must format a java.util.Date with custom format.",
            new TextOfDateTime(
                "yyyy MM dd hh:mm:ss",
                Date.from(
                    ZonedDateTime.of(
                        2017, 12, 13, 14, 15, 16, 0, ZoneId.systemDefault()
                    ).toInstant()
                )
            ),
            new IsText("2017 12 13 02:15:16")
        );
    }

    @Test
    void dateFormattedUsingCustomFormatDifferentLocale() {
        MatcherAssert.assertThat(
            "Must format a java.util.Date with custom format.",
            new TextOfDateTime(
                "yyyy MMM dd hh:mm:ss",
                Date.from(
                    ZonedDateTime.of(
                        2017, 12, 13, 14, 15, 16, 0, ZoneId.systemDefault()
                    ).toInstant()
                ),
                Locale.ITALIAN
            ),
            new IsText("2017 dic 13 02:15:16")
        );
    }

    @Test
    void offsetDateTimeFormattedAsIsoDateTime() {
        MatcherAssert.assertThat(
            "Must format a OffsetDateTime with default/ISO format.",
            new TextOfDateTime(
                OffsetDateTime.of(2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1))
            ),
            new IsText("2017-12-13T14:15:16.000000017+01:00")
        );
    }

    @Test
    void offsetDateTimeFormattedWithFormatString() {
        MatcherAssert.assertThat(
            "Must format a OffsetDateTime with format.",
            new TextOfDateTime(
                "yyyy-MM-dd HH:mm:ss",
                OffsetDateTime.of(2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1))
            ),
            new IsText("2017-12-13 14:15:16")
        );
    }

    @Test
    void offsetDateTimeFormattedWithFormatStringWithLocale() {
        MatcherAssert.assertThat(
            "Must format a OffsetDateTime with format using locale.",
            new TextOfDateTime(
                "yyyy MMM dd. HH.mm.ss",
                OffsetDateTime.of(2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1)),
                Locale.FRENCH
            ),
            new IsText("2017 déc. 13. 14.15.16")
        );
    }

    @Test
    void currentOffsetDateTimeAsText() throws Exception {
        MatcherAssert.assertThat(
            "Must format a OffsetDateTime with ISO format.",
            new TextOfDateTime(OffsetDateTime.now()).asString(),
            new IsNot<>(new IsNull<>())
        );
    }

    @Test
    void zonedDateTimeFormattedAsIsoDateTime() {
        MatcherAssert.assertThat(
            "Must format a ZonedDateTime with default/ISO format.",
            new TextOfDateTime(
                ZonedDateTime.of(2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin"))
            ),
            new IsText("2017-12-13T14:15:16.000000017+01:00")
        );
    }

    @Test
    void zonedDateTimeFormattedWithFormatString() {
        MatcherAssert.assertThat(
            "Must format a ZonedDateTime with format.",
            new TextOfDateTime(
                "yyyy-MM-dd HH:mm:ss",
                ZonedDateTime.of(2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin"))
            ),
            new IsText("2017-12-13 14:15:16")
        );
    }

    @Test
    void zonedDateTimeFormattedWithFormatStringWithLocale() {
        MatcherAssert.assertThat(
            "Must format a ZonedDateTime with format using locale.",
            new TextOfDateTime(
                "yyyy MMM dd. HH.mm.ss",
                ZonedDateTime.of(2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")),
                Locale.FRENCH
            ),
            new IsText("2017 déc. 13. 14.15.16")
        );
    }

    @Test
    void currentZonedDateTimeAsText() throws Exception {
        MatcherAssert.assertThat(
            "Must format a ZonedDateTime with ISO format.",
            new TextOfDateTime(ZonedDateTime.now()).asString(),
            new IsNot<>(new IsNull<>())
        );
    }
}
