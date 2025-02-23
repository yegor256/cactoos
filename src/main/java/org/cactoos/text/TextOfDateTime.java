/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import org.cactoos.time.Iso;

/**
 * Text of date time
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class TextOfDateTime extends TextEnvelope {

    /**
     * Formats date using ISO date time format.
     * @param date The date to format.
     */
    public TextOfDateTime(final LocalDate date) {
        this(new Iso().value(), date);
    }

    /**
     * Formats date using provided date time format string using default locale.
     * @param format The format to use.
     * @param date The date to format.
     */
    public TextOfDateTime(final String format, final LocalDate date) {
        this(format, date, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Formats the date using the provided format string using the provided
     * locale.
     * @param format The format string to use.
     * @param date The date to format.
     * @param locale The locale to use.
     */
    public TextOfDateTime(
        final String format,
        final LocalDate date,
        final Locale locale
    ) {
        this(DateTimeFormatter.ofPattern(format, locale), date);
    }

    /**
     * Formats date using ISO date time format.
     * @param date The date to format.
     */
    public TextOfDateTime(final LocalDateTime date) {
        this(new Iso().value(), date);
    }

    /**
     * Formats date using provided date time format string using default locale.
     * @param format The format to use.
     * @param date The date to format.
     */
    public TextOfDateTime(final String format, final LocalDateTime date) {
        this(format, date, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Formats the date using the provided format string using the provided
     * locale.
     * @param format The format string to use.
     * @param date The date to format.
     * @param locale The locale to use.
     */
    public TextOfDateTime(
        final String format,
        final LocalDateTime date,
        final Locale locale
    ) {
        this(DateTimeFormatter.ofPattern(format, locale), date);
    }

    /**
     * Formats the date with ISO format using the system zone.
     * @param date The date to format.
     */
    public TextOfDateTime(final Date date) {
        this(new Iso().value(), date);
    }

    /**
     * Formats the date with to format using the default locale and the system
     * zone.
     * @param format The format to use.
     * @param date The date to format.
     */
    public TextOfDateTime(final String format, final Date date) {
        this(format, date, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Formats the date using the format and locale using the system default
     * zone.
     * @param format The format to use.
     * @param date The date to format.
     * @param locale The locale to use.
     */
    public TextOfDateTime(
        final String format,
        final Date date,
        final Locale locale
    ) {
        this(DateTimeFormatter.ofPattern(format, locale), date);
    }

    /**
     * Formats date using ISO date time format.
     * @param date The date to format.
     */
    public TextOfDateTime(final OffsetDateTime date) {
        this(new Iso().value(), date);
    }

    /**
     * Formats date using provided date time format string using default locale.
     * @param format The format to use.
     * @param date The date to format.
     */
    public TextOfDateTime(final String format, final OffsetDateTime date) {
        this(format, date, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Formats the date using the provided format string using the provided
     * locale.
     * @param format The format string to use.
     * @param date The date to format.
     * @param locale The locale to use.
     */
    public TextOfDateTime(
        final String format,
        final OffsetDateTime date,
        final Locale locale
    ) {
        this(DateTimeFormatter.ofPattern(format, locale), date);
    }

    /**
     * Formats date using ISO date time format.
     * @param date The date to format.
     */
    public TextOfDateTime(final ZonedDateTime date) {
        this(new Iso().value(), date);
    }

    /**
     * Formats date using provided date time format string using default locale.
     * @param format The format to use.
     * @param date The date to format.
     */
    public TextOfDateTime(final String format, final ZonedDateTime date) {
        this(format, date, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Formats the date using the provided format string using the provided
     * locale.
     * @param format The format to use.
     * @param date The date to format.
     * @param locale The locale to use.
     */
    public TextOfDateTime(
        final String format,
        final ZonedDateTime date,
        final Locale locale
    ) {
        this(DateTimeFormatter.ofPattern(format, locale), date);
    }

    /**
     * Formats the date using the provided formatter.
     * @param formatter The formatter to use.
     * @param date The date to format.
     */
    public TextOfDateTime(final DateTimeFormatter formatter, final LocalDate date) {
        super(
            new TextOfScalar(
                () -> formatter.format(
                    ZonedDateTime.of(
                        date, LocalTime.MIN, ZoneId.systemDefault()
                    )
                )
            )
        );
    }

    /**
     * Formats the date using the provided formatter.
     * @param formatter The formatter to use.
     * @param date The date to format.
     */
    public TextOfDateTime(
        final DateTimeFormatter formatter,
        final LocalDateTime date
    ) {
        super(
            new TextOfScalar(
                () -> formatter.format(date.atZone(ZoneId.systemDefault()))
            )
        );
    }

    /**
     * Formats the date using the format and locale using the system default
     * zone.
     * @param formatter The formatter to use.
     * @param date The date to format.
     */
    public TextOfDateTime(final DateTimeFormatter formatter, final Date date) {
        super(
            new TextOfScalar(
                () -> formatter.format(
                    ZonedDateTime.ofInstant(
                        date.toInstant(),
                        ZoneId.systemDefault()
                    )
                )
            )
        );
    }

    /**
     * Formats the date using the provided formatter.
     * @param formatter The formatter to use.
     * @param date The date to format.
     */
    public TextOfDateTime(
        final DateTimeFormatter formatter,
        final OffsetDateTime date
    ) {
        super(new TextOfScalar(() -> formatter.format(date)));
    }

    /**
     * Formats the date using the provided formatter.
     * @param formatter The formatter to use.
     * @param date The date to format.
     */
    public TextOfDateTime(
        final DateTimeFormatter formatter,
        final ZonedDateTime date
    ) {
        super(new TextOfScalar(() -> formatter.format(date)));
    }
}
