/*
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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Parser for {@link ZonedDateTime} instances.
 * @since 0.27
 */
public final class ZonedDateTimeOf implements Scalar<ZonedDateTime> {
    /**
     * The parsed date.
     */
    private final UncheckedScalar<ZonedDateTime> parsed;

    /**
     * Parses date to create {@link ZonedDateTime} instances.
     * @param date The date to parse.
     */
    public ZonedDateTimeOf(final CharSequence date) {
        this(date, new Iso().get());
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
        this.parsed = new UncheckedScalar<>(
            () -> ZonedDateTime.from(formatter.parse(date))
        );
    }

    @Override
    public ZonedDateTime value() {
        return this.parsed.value();
    }

}
