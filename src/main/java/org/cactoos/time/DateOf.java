/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.time;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Parser for {@link Date} instances.
 * @since 0.27
 */
public final class DateOf implements Scalar<Date> {

    /**
     * The parsed date.
     */
    private final Unchecked<Date> parsed;

    /**
     * Parses the provided date as ISO formatted.
     * @param date The date to parse.
     */
    public DateOf(final CharSequence date) {
        this(date, new Iso().value());
    }

    /**
     * Parses the date using the provided format.
     * @param date The date to parse.
     * @param format The format to use.
     */
    public DateOf(final CharSequence date, final String format) {
        this(date, DateTimeFormatter.ofPattern(format));
    }

    /**
     * Parsing the date using the provided formatter.
     * @param date The date to parse.
     * @param formatter The formatter to use.
     */
    public DateOf(final CharSequence date, final DateTimeFormatter formatter) {
        this.parsed = new Unchecked<>(
            () -> Date.from(
                LocalDateTime.parse(
                    date,
                    new DateTimeFormatterBuilder()
                        .append(formatter)
                        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0L)
                        .toFormatter()
                ).toInstant(ZoneOffset.UTC)
            )
        );
    }

    @Override
    public Date value() {
        return this.parsed.value();
    }

}
