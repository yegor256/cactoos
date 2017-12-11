/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Parser for date instances.
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @param <T> Type of the date to parse to.
 * @since 1.0
 */
public class DateOf<T extends TemporalAccessor> implements Scalar<T> {

    /**
     * The parsed date.
     */
    private UncheckedScalar<T> parsed;

    /**
     * Parses the date using the provided format.
     * @param date The date to parse.
     * @param format The format to use.
     * @param converter The converter to convert
     *  {@link TemporalAccessor} into type T
     */
    public DateOf(final String date, final String format,
        final Func<TemporalAccessor, T> converter) {
        this(date, DateTimeFormatter.ofPattern(format), converter);
    }

    /**
     * Parses the provided date as ISO formatted.
     * @param date The date to parse.
     * @param converter The converter to convert
     *  {@link TemporalAccessor} into type T
     */
    public DateOf(final String date,
        final Func<TemporalAccessor, T> converter) {
        this(date, DateTimeFormatter.ISO_DATE_TIME, converter);
    }

    /**
     * Parsing the date using the provided formatter.
     * @param date The date to parse.
     * @param formatter The formatter to use.
     * @param converter The converter to convert
     *  {@link TemporalAccessor} into type T
     */
    public DateOf(final String date, final DateTimeFormatter formatter,
        final Func<TemporalAccessor, T> converter) {
        this.parsed = new UncheckedScalar<>(
            () -> converter.apply(formatter.parse(date))
        );
    }

    @Override
    public final T value() throws Exception {
        return this.parsed.value();
    }

    /**
     * Convenience class for parsing to {@link LocalDateTime} instances.
     */
    public static class Local extends DateOf<LocalDateTime> {
        /**
         * Parses date using the provided format to create
         *  {@link LocalDateTime} instances.
         * @param date The date to parse.
         * @param format The format to use.
         */
        public Local(final String date, final String format) {
            super(date, format, LocalDateTime::from);
        }
        /**
         * Parses ISO date to create {@link LocalDateTime} instances.
         * @param date The date to parse.
         */
        public Local(final String date) {
            super(date, LocalDateTime::from);
        }
        /**
         * Parses the date using the formatter to create
         *  {@link LocalDateTime} instances.
         * @param date The date to parse.
         * @param formatter The formatter to use.
         */
        public Local(final String date, final DateTimeFormatter formatter) {
            super(date, formatter, LocalDateTime::from);
        }
    }

    /**
     * Convenience class for parsing to {@link OffsetDateTime} instances.
     */
    public static class Offset extends DateOf<OffsetDateTime> {
        /**
         * Parses date using the provided format to create
         *  {@link OffsetDateTime} instances.
         * @param date The date to parse.
         * @param format The format to use.
         * @param offset The offset to use.
         */
        public Offset(final String date, final String format,
            final ZoneOffset offset) {
            super(date,
                DateTimeFormatter.ofPattern(format)
                    .withZone(offset.normalized()),
                temporal -> ZonedDateTime.from(temporal).toOffsetDateTime()
            );
        }
        /**
         * Parses ISO date to create {@link OffsetDateTime} instances.
         * @param date The date to parse.
         */
        public Offset(final String date) {
            super(date, OffsetDateTime::from);
        }
        /**
         * Parses the date using the formatter to create
         *  {@link OffsetDateTime} instances.
         * @param date The date to parse.
         * @param formatter The formatter to use.
         */
        public Offset(final String date, final DateTimeFormatter formatter) {
            super(date, formatter, OffsetDateTime::from);
        }
    }

    /**
     * Convenience class for parsing to {@link ZonedDateTime} instances.
     */
    public static class Zoned extends DateOf<ZonedDateTime> {
        /**
         * Parses date to create {@link ZonedDateTime} instances.
         * @param date The date to parse.
         */
        public Zoned(final String date) {
            super(date, ZonedDateTime::from);
        }
        /**
         * Parses date using the provided format to create
         *  {@link ZonedDateTime} instances.
         * @param date The date to parse.
         * @param format The format to use.
         * @param zone The zone to use.
         */
        public Zoned(final String date, final String format,
            final ZoneId zone) {
            super(date,
                DateTimeFormatter.ofPattern(format).withZone(zone),
                ZonedDateTime::from
            );
        }
        /**
         * Parses the date using the formatter to create
         *  {@link ZonedDateTime} instances.
         * @param date The date to parse.
         * @param formatter The formatter to use.
         */
        public Zoned(final String date, final DateTimeFormatter formatter) {
            super(date, formatter, ZonedDateTime::from);
        }
    }

}
