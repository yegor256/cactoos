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

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Parser for {@link OffsetDateTime} instances.
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 0.27
 */
public final class OffsetDateTimeOf implements Scalar<OffsetDateTime> {
    /**
     * The parsed date.
     */
    private final UncheckedScalar<OffsetDateTime> parsed;

    /**
     * Parses ISO date to create {@link OffsetDateTime} instances.
     * @param date The date to parse.
     */
    public OffsetDateTimeOf(final CharSequence date) {
        this(date, new Iso().get());
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
        this.parsed = new UncheckedScalar<>(
            () -> ZonedDateTime.from(formatter.parse(date)).toOffsetDateTime()
        );
    }

    @Override
    public OffsetDateTime value() {
        return this.parsed.value();
    }

}
