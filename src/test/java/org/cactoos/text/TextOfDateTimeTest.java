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
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsBlank;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link TextOfDateTime}.
 *
 * @since 1.0.0
 * @checkstyle StringLiteralsConcatenationCheck (1000 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
final class TextOfDateTimeTest {

    @Test
    void readsLocalDateFormattedWithFormatString() {
        final LocalDate date = LocalDate.of(2017, 12, 13);
        new Assertion<>(
            "Must format a LocalDate with format.",
            new TextOfDateTime("yyyy-MM-dd HH:mm:ss", date),
            new IsText("2017-12-13 00:00:00")
        ).affirm();
    }

    @Test
    void readsLocalDateFormattedWithFormatStringWithLocale() {
        final LocalDate date = LocalDate.of(2017, 12, 13);
        new Assertion<>(
            "Must format a LocalDate with format using locale.",
            new TextOfDateTime(
                "yyyy MMM dd. HH.mm.ss", date, Locale.FRENCH
            ),
            new IsText("2017 déc. 13. 00.00.00")
        ).affirm();
    }

    @Test
    void readsLocalDateFormattedAsIsoDateTime() {
        final LocalDate date = LocalDate.of(2017, 12, 13);
        new Assertion<>(
            "Must format a LocalDate with default/ISO format.",
            new TextOfDateTime(date),
            new IsText(
                MessageFormat.format(
                "2017-12-13T00:00:00{0}",
                date.atTime(LocalTime.MIN).atZone(ZoneId.systemDefault())
                    .getOffset().toString()
                )
            )
        ).affirm();
    }

    @Test
    void readsCurrentLocalDateAsText() throws Exception {
        new Assertion<>(
            "Must format a LocalDate with ISO format.",
            new TextOfDateTime(LocalDate.now()).asString(),
            new IsNot<>(new IsBlank())
        ).affirm();
    }

    @Test
    void localDateTimeFormattedAsIsoDateTime() {
        final LocalDateTime date = LocalDateTime.of(
            2017, 12, 13, 14, 15, 16, 17
        );
        new Assertion<>(
            "Must format a LocalDateTime with default/ISO format.",
            new TextOfDateTime(date),
            new IsText(
                MessageFormat.format(
                    "2017-12-13T14:15:16.000000017{0}",
                    date.atZone(ZoneId.systemDefault()).getOffset().toString()
                )
            )
        ).affirm();
    }

    @Test
    void localDateTimeFormattedWithFormatString() {
        final LocalDateTime date = LocalDateTime.of(
            2017, 12, 13, 14, 15, 16, 17
        );
        new Assertion<>(
            "Must format a LocalDateTime with format.",
            new TextOfDateTime("yyyy-MM-dd HH:mm:ss", date),
            new IsText("2017-12-13 14:15:16")
        ).affirm();
    }

    @Test
    void localDateTimeFormattedWithFormatStringWithLocale() {
        final LocalDateTime date = LocalDateTime.of(
            2017, 12, 13, 14, 15, 16, 17
        );
        new Assertion<>(
            "Must format a LocalDateTime with format using locale.",
            new TextOfDateTime(
                "yyyy MMM dd. HH.mm.ss", date, Locale.FRENCH
            ),
            new IsText("2017 déc. 13. 14.15.16")
        ).affirm();
    }

    @Test
    void currentLocalDateTimeAsText() throws Exception {
        new Assertion<>(
            "Must format a LocalDateTime with ISO format.",
            new TextOfDateTime(LocalDateTime.now()).asString(),
            new IsNot<>(new IsNull<>())
        ).affirm();
    }

    @Test
    void dateFormattedUsingIsoFormatter() {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getDefault());
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        calendar.set(Calendar.MILLISECOND, 17);
        final ZoneOffset offset = calendar.getTimeZone().toZoneId()
            .getRules().getOffset(calendar.toInstant());
        new Assertion<>(
            "Must format a java.util.Date with ISO format.",
            new TextOfDateTime(calendar.getTime()),
            new IsText("2017-12-13T14:15:16.017" + offset)
        ).affirm();
    }

    @Test
    void dateFormattedUsingCustomFormat()  {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getDefault());
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        new Assertion<>(
            "Must format a java.util.Date with custom format.",
            new TextOfDateTime(
                "yyyy MM dd hh:mm:ss", calendar.getTime()
            ),
            new IsText("2017 12 13 02:15:16")
        ).affirm();
    }

    @Test
    void dateFormattedUsingCustomFormatDifferentLocale() {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getDefault());
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        new Assertion<>(
            "Must format a java.util.Date with custom format.",
            new TextOfDateTime(
                "yyyy MMM dd hh:mm:ss", calendar.getTime(), Locale.ITALIAN
            ),
            new IsText("2017 dic 13 02:15:16")
        ).affirm();
    }

    @Test
    void offsetDateTimeFormattedAsIsoDateTime() {
        final OffsetDateTime date = OffsetDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1)
        );
        new Assertion<>(
            "Must format a OffsetDateTime with default/ISO format.",
            new TextOfDateTime(date),
            new IsText("2017-12-13T14:15:16.000000017+01:00")
        ).affirm();
    }

    @Test
    void offsetDateTimeFormattedWithFormatString() {
        final OffsetDateTime date = OffsetDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1)
        );
        new Assertion<>(
            "Must format a OffsetDateTime with format.",
            new TextOfDateTime("yyyy-MM-dd HH:mm:ss", date),
            new IsText("2017-12-13 14:15:16")
        ).affirm();
    }

    @Test
    void offsetDateTimeFormattedWithFormatStringWithLocale() {
        final OffsetDateTime date = OffsetDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1)
        );
        new Assertion<>(
            "Must format a OffsetDateTime with format using locale.",
            new TextOfDateTime(
                "yyyy MMM dd. HH.mm.ss", date, Locale.FRENCH
            ),
            new IsText("2017 déc. 13. 14.15.16")
        ).affirm();
    }

    @Test
    void currentOffsetDateTimeAsText() throws Exception {
        new Assertion<>(
            "Must format a OffsetDateTime with ISO format.",
            new TextOfDateTime(OffsetDateTime.now()).asString(),
            new IsNot<>(new IsNull<>())
        ).affirm();
    }

    @Test
    void zonedDateTimeFormattedAsIsoDateTime() {
        final ZonedDateTime date = ZonedDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")
        );
        new Assertion<>(
            "Must format a ZonedDateTime with default/ISO format.",
            new TextOfDateTime(date),
            new IsText("2017-12-13T14:15:16.000000017+01:00")
        ).affirm();
    }

    @Test
    void zonedDateTimeFormattedWithFormatString() {
        final ZonedDateTime date = ZonedDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")
        );
        new Assertion<>(
            "Must format a ZonedDateTime with format.",
            new TextOfDateTime("yyyy-MM-dd HH:mm:ss", date),
            new IsText("2017-12-13 14:15:16")
        ).affirm();
    }

    @Test
    void zonedDateTimeFormattedWithFormatStringWithLocale() {
        final ZonedDateTime date = ZonedDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")
        );
        new Assertion<>(
            "Must format a ZonedDateTime with format using locale.",
            new TextOfDateTime(
                "yyyy MMM dd. HH.mm.ss", date, Locale.FRENCH
            ),
            new IsText("2017 déc. 13. 14.15.16")
        ).affirm();
    }

    @Test
    void currentZonedDateTimeAsText() throws Exception {
        new Assertion<>(
            "Must format a ZonedDateTime with ISO format.",
            new TextOfDateTime(ZonedDateTime.now()).asString(),
            new IsNot<>(new IsNull<>())
        ).affirm();
    }
}
