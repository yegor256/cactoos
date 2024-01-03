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
