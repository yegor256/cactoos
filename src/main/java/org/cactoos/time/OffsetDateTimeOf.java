/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.time;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Parser for {@link OffsetDateTime} instances.
 * @since 0.27
 */
public final class OffsetDateTimeOf implements Scalar<OffsetDateTime> {
    /**
     * The parsed date.
     */
    private final Unchecked<OffsetDateTime> parsed;

    /**
     * Parses ISO date to create {@link OffsetDateTime} instances.
     * @param date The date to parse.
     */
    public OffsetDateTimeOf(final CharSequence date) {
        this(date, new Iso().value());
    }

    /**
     * Parses date using the provided format to create
     *  {@link OffsetDateTime} instances.
     * @param date The date to parse.
     * @param format The format to use.
     * @param offset The offset to use.
     */
    public OffsetDateTimeOf(final CharSequence date, final String format,
        final ZoneOffset offset) {
        this(date,
            DateTimeFormatter.ofPattern(format).withZone(offset.normalized())
        );
    }

    /**
     * Parses the date using the formatter to create
     *  {@link OffsetDateTime} instances.
     * @param date The date to parse.
     * @param formatter The formatter to use.
     */
    public OffsetDateTimeOf(final CharSequence date,
        final DateTimeFormatter formatter) {
        this.parsed = new Unchecked<>(
            () -> ZonedDateTime.from(formatter.parse(date)).toOffsetDateTime()
        );
    }

    @Override
    public OffsetDateTime value() {
        return this.parsed.value();
    }

}
