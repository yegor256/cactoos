/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Parser for {@link ZonedDateTime} instances.
 * @since 0.27
 */
public final class ZonedDateTimeOf implements Scalar<ZonedDateTime> {
    /**
     * The parsed date.
     */
    private final Unchecked<ZonedDateTime> parsed;

    /**
     * Parses date to create {@link ZonedDateTime} instances.
     * @param date The date to parse.
     */
    public ZonedDateTimeOf(final CharSequence date) {
        this(date, new Iso().value());
    }

    /**
     * Parses date using the provided format to create
     *  {@link ZonedDateTime} instances.
     * @param date The date to parse.
     * @param format The format to use.
     * @param zone The zone to use.
     */
    public ZonedDateTimeOf(final CharSequence date, final String format,
        final ZoneId zone) {
        this(date, DateTimeFormatter.ofPattern(format).withZone(zone));
    }

    /**
     * Parses the date using the formatter to create
     *  {@link ZonedDateTime} instances.
     * @param date The date to parse.
     * @param formatter The formatter to use.
     */
    public ZonedDateTimeOf(final CharSequence date,
        final DateTimeFormatter formatter) {
        this.parsed = new Unchecked<>(
            () -> ZonedDateTime.from(formatter.parse(date))
        );
    }

    @Override
    public ZonedDateTime value() {
        return this.parsed.value();
    }

}
