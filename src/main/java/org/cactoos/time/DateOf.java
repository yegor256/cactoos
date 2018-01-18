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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Parser for {@link Date} instances.
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 0.27
 */
public final class DateOf implements Scalar<Date> {

    /**
     * The parsed date.
     */
    private final UncheckedScalar<Date> parsed;

    /**
     * Parses the provided date as ISO formatted.
     * @param date The date to parse.
     */
    public DateOf(final CharSequence date) {
        this(date, new Iso().get());
    }

    /**
     * Parses the date using the provided format.
     * @param date The date to parse.
     * @param format The format to use.
     */
    public DateOf(final CharSequence date, final String format) {
        this(date, DateTimeFormatter.ofPattern(format));
    }

    /**
     * Parsing the date using the provided formatter.
     * @param date The date to parse.
     * @param formatter The formatter to use.
     */
    public DateOf(final CharSequence date, final DateTimeFormatter formatter) {
        this.parsed = new UncheckedScalar<>(
            () -> Date.from(
                LocalDateTime.from(
                    formatter.parse(date)
                ).toInstant(ZoneOffset.UTC)
            )
        );
    }

    @Override
    public Date value() {
        return this.parsed.value();
    }

}
