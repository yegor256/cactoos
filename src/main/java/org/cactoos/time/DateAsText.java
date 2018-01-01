/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
package org.cactoos.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import org.cactoos.Text;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.UncheckedText;

/**
 * Formatter for date instances.
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 0.27
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class DateAsText implements Text {

    /**
     * Scalar carrying the formatted date.
     */
    private final UncheckedScalar<String> formatted;

    /**
     * Formats current time using the ISO format.
     */
    public DateAsText() {
        this(System.currentTimeMillis());
    }

    /**
     * Formats the milliseconds using the ISO format.
     * @param milliseconds Milliseconds to format.
     */
    public DateAsText(final long milliseconds) {
        this(
            ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(milliseconds), ZoneId.of("UTC")
            ),
            new Iso().get()
        );
    }

    /**
     * Formats the milliseconds using the format and the default locale.
     * @param milliseconds Milliseconds to format.
     * @param format The format to use.
     */
    public DateAsText(final long milliseconds, final String format) {
        this(
            ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(milliseconds), ZoneId.of("UTC")
            ),
            format,
            Locale.getDefault(Locale.Category.FORMAT)
        );
    }

    /**
     * Formats the milliseconds as date using the format and the locale.
     * @param milliseconds Milliseconds to format as date.
     * @param format The format to use.
     * @param locale The locale to use for the format.
     */
    public DateAsText(final long milliseconds, final String format,
        final Locale locale) {
        this(
            ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(milliseconds), ZoneId.of("UTC")
            ),
            DateTimeFormatter.ofPattern(format, locale)
        );
    }

    /**
     * Formats the date with ISO format using the system zone.
     * @param date The date to format.
     */
    public DateAsText(final Date date) {
        this(
            ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")),
            new Iso().get()
        );
    }

    /**
     * Formats the date with to format using the default locale and the system
     * zone.
     * @param date The date to format.
     * @param format The format to use.
     */
    public DateAsText(final Date date, final String format) {
        this(
            ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")),
            DateTimeFormatter.ofPattern(
                format,
                Locale.getDefault(Locale.Category.FORMAT)
            )
        );
    }

    /**
     * Formats the date using the format and locale using the system default
     * zone.
     * @param date The date to format.
     * @param format The format to use.
     * @param locale The locale to use.
     */
    public DateAsText(final Date date, final String format,
        final Locale locale) {
        this(
            ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")),
            DateTimeFormatter.ofPattern(format, locale)
        );
    }

    /**
     * Formats date using ISO date time format.
     * @param date The date to format.
     */
    public DateAsText(final TemporalAccessor date) {
        this(date, new Iso().get());
    }

    /**
     * Formats date using provided date time format string using default locale.
     * @param date The date to format.
     * @param format The format to use.
     */
    public DateAsText(final TemporalAccessor date, final String format) {
        this(date, format, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Formats the date using the provided format string using the provided
     * locale.
     * @param date The date to format.
     * @param format The format string to use.
     * @param locale The locale to use.
     */
    public DateAsText(final TemporalAccessor date, final String format,
        final Locale locale) {
        this(date, DateTimeFormatter.ofPattern(format, locale));
    }

    /**
     * Formats the date using the provided formatter.
     * @param date The date to format.
     * @param formatter The formatter to use.
     */
    public DateAsText(final TemporalAccessor date,
        final DateTimeFormatter formatter) {
        this.formatted = new UncheckedScalar<>(
            () -> formatter.format(date)
        );
    }

    @Override
    public String asString() {
        return this.formatted.value();
    }

    @Override
    public int compareTo(final Text text) {
        return new UncheckedText(this).compareTo(text);
    }

}

