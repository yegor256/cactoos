/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
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
@SuppressWarnings("PMD.ReplaceJavaUtilDate")
public final class TextOfDateTime extends TextEnvelope {

    /**
     * Formats date using ISO date time format.
     * @param date The date to format
     */
    public TextOfDateTime(final LocalDate date) {
        this(
            () -> new Iso().value().format(
                ZonedDateTime.of(date, LocalTime.MIN, ZoneId.systemDefault())
            )
        );
    }

    /**
     * Formats date using provided date time format string using default locale.
     * @param format The format to use
     * @param date The date to format
     */
    public TextOfDateTime(final String format, final LocalDate date) {
        this(
            () -> DateTimeFormatter.ofPattern(
                format, Locale.getDefault(Locale.Category.FORMAT)
            ).format(
                ZonedDateTime.of(date, LocalTime.MIN, ZoneId.systemDefault())
            )
        );
    }

    /**
     * Formats the date using the provided format string using the provided
     * locale.
     * @param format The format string to use
     * @param date The date to format
     * @param locale The locale to use
     */
    public TextOfDateTime(
        final String format,
        final LocalDate date,
        final Locale locale
    ) {
        this(
            () -> DateTimeFormatter.ofPattern(format, locale).format(
                ZonedDateTime.of(date, LocalTime.MIN, ZoneId.systemDefault())
            )
        );
    }

    /**
     * Formats date using ISO date time format.
     * @param date The date to format
     */
    public TextOfDateTime(final LocalDateTime date) {
        this(
            () -> new Iso().value().format(date.atZone(ZoneId.systemDefault()))
        );
    }

    /**
     * Formats date using provided date time format string using default locale.
     * @param format The format to use
     * @param date The date to format
     */
    public TextOfDateTime(final String format, final LocalDateTime date) {
        this(
            () -> DateTimeFormatter.ofPattern(
                format, Locale.getDefault(Locale.Category.FORMAT)
            ).format(date.atZone(ZoneId.systemDefault()))
        );
    }

    /**
     * Formats the date using the provided format string using the provided
     * locale.
     * @param format The format string to use
     * @param date The date to format
     * @param locale The locale to use
     */
    public TextOfDateTime(
        final String format,
        final LocalDateTime date,
        final Locale locale
    ) {
        this(
            () -> DateTimeFormatter.ofPattern(format, locale)
                .format(date.atZone(ZoneId.systemDefault()))
        );
    }

    /**
     * Formats the date with ISO format using the system zone.
     * @param date The date to format
     */
    public TextOfDateTime(final Date date) {
        this(
            () -> new Iso().value().format(
                ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
            )
        );
    }

    /**
     * Formats the date with to format using the default locale and the system
     * zone.
     * @param format The format to use
     * @param date The date to format
     */
    public TextOfDateTime(final String format, final Date date) {
        this(
            () -> DateTimeFormatter.ofPattern(
                format, Locale.getDefault(Locale.Category.FORMAT)
            ).format(
                ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
            )
        );
    }

    /**
     * Formats the date using the format and locale using the system default
     * zone.
     * @param format The format to use
     * @param date The date to format
     * @param locale The locale to use
     */
    public TextOfDateTime(
        final String format,
        final Date date,
        final Locale locale
    ) {
        this(
            () -> DateTimeFormatter.ofPattern(format, locale).format(
                ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
            )
        );
    }

    /**
     * Formats date using ISO date time format.
     * @param date The date to format
     */
    public TextOfDateTime(final OffsetDateTime date) {
        this(() -> new Iso().value().format(date));
    }

    /**
     * Formats date using provided date time format string using default locale.
     * @param format The format to use
     * @param date The date to format
     */
    public TextOfDateTime(final String format, final OffsetDateTime date) {
        this(
            () -> DateTimeFormatter.ofPattern(
                format, Locale.getDefault(Locale.Category.FORMAT)
            ).format(date)
        );
    }

    /**
     * Formats the date using the provided format string using the provided
     * locale.
     * @param format The format string to use
     * @param date The date to format
     * @param locale The locale to use
     */
    public TextOfDateTime(
        final String format,
        final OffsetDateTime date,
        final Locale locale
    ) {
        this(() -> DateTimeFormatter.ofPattern(format, locale).format(date));
    }

    /**
     * Formats date using ISO date time format.
     * @param date The date to format
     */
    public TextOfDateTime(final ZonedDateTime date) {
        this(() -> new Iso().value().format(date));
    }

    /**
     * Formats date using provided date time format string using default locale.
     * @param format The format to use
     * @param date The date to format
     */
    public TextOfDateTime(final String format, final ZonedDateTime date) {
        this(
            () -> DateTimeFormatter.ofPattern(
                format, Locale.getDefault(Locale.Category.FORMAT)
            ).format(date)
        );
    }

    /**
     * Formats the date using the provided format string using the provided
     * locale.
     * @param format The format to use
     * @param date The date to format
     * @param locale The locale to use
     */
    public TextOfDateTime(
        final String format,
        final ZonedDateTime date,
        final Locale locale
    ) {
        this(() -> DateTimeFormatter.ofPattern(format, locale).format(date));
    }

    /**
     * Formats the date using the provided formatter.
     * @param formatter The formatter to use
     * @param date The date to format
     */
    public TextOfDateTime(final DateTimeFormatter formatter, final LocalDate date) {
        this(
            () -> formatter.format(
                ZonedDateTime.of(
                    date, LocalTime.MIN, ZoneId.systemDefault()
                )
            )
        );
    }

    /**
     * Formats the date using the provided formatter.
     * @param formatter The formatter to use
     * @param date The date to format
     */
    public TextOfDateTime(
        final DateTimeFormatter formatter,
        final LocalDateTime date
    ) {
        this(() -> formatter.format(date.atZone(ZoneId.systemDefault())));
    }

    /**
     * Formats the date using the format and locale using the system default
     * zone.
     * @param formatter The formatter to use
     * @param date The date to format
     */
    public TextOfDateTime(final DateTimeFormatter formatter, final Date date) {
        this(
            () -> formatter.format(
                ZonedDateTime.ofInstant(
                    date.toInstant(),
                    ZoneId.systemDefault()
                )
            )
        );
    }

    /**
     * Formats the date using the provided formatter.
     * @param formatter The formatter to use
     * @param date The date to format
     */
    public TextOfDateTime(
        final DateTimeFormatter formatter,
        final OffsetDateTime date
    ) {
        this(() -> formatter.format(date));
    }

    /**
     * Formats the date using the provided formatter.
     * @param formatter The formatter to use
     * @param date The date to format
     */
    public TextOfDateTime(
        final DateTimeFormatter formatter,
        final ZonedDateTime date
    ) {
        this(() -> formatter.format(date));
    }

    /**
     * Formats the date using the provided scalar of formatted text.
     * @param scalar The scalar of formatted text
     */
    private TextOfDateTime(final org.cactoos.Scalar<? extends CharSequence> scalar) {
        super(new TextOfScalar(scalar));
    }
}
