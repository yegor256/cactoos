/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Parser for {@link LocalDateTime} instances.
 * @since 0.27
 */
public final class LocalDateTimeOf implements Scalar<LocalDateTime> {
    /**
     * The parsed date.
     */
    private final Unchecked<LocalDateTime> parsed;

    /**
     * Parses ISO date to create {@link LocalDateTime} instances.
     * @param date The date to parse.
     */
    public LocalDateTimeOf(final CharSequence date) {
        this(date, new Iso().value());
    }

    /**
     * Parses date using the provided format to create
     *  {@link LocalDateTime} instances.
     * @param date The date to parse.
     * @param format The format to use.
     */
    public LocalDateTimeOf(final CharSequence date, final String format) {
        this(date, DateTimeFormatter.ofPattern(format));
    }

    /**
     * Parses the date using the formatter to create
     *  {@link LocalDateTime} instances.
     * @param date The date to parse.
     * @param formatter The formatter to use.
     */
    public LocalDateTimeOf(final CharSequence date,
        final DateTimeFormatter formatter) {
        this.parsed = new Unchecked<>(
            () -> LocalDateTime.from(formatter.parse(date))
        );
    }

    @Override
    public LocalDateTime value() {
        return this.parsed.value();
    }

}
