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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.cactoos.Text;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Formatter for {@link java.time.LocalDateTime} instances.
 * @since 0.27
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class LocalDateTimeAsText implements Text {

    /**
     * Scalar carrying the formatted date.
     */
    private final UncheckedScalar<String> formatted;

    /**
     * Formats date using ISO date time format.
     * @param date The date to format.
     */
    public LocalDateTimeAsText(final LocalDateTime date) {
        this(date, new Iso().get());
    }

    /**
     * Formats date using provided date time format string using default locale.
     * @param date The date to format.
     * @param format The format to use.
     */
    public LocalDateTimeAsText(final LocalDateTime date, final String format) {
        this(date, format, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Formats the date using the provided format string using the provided
     * locale.
     * @param date The date to format.
     * @param format The format string to use.
     * @param locale The locale to use.
     */
    public LocalDateTimeAsText(final LocalDateTime date, final String format,
        final Locale locale) {
        this(date, DateTimeFormatter.ofPattern(format, locale));
    }

    /**
     * Formats the date using the provided formatter.
     * @param date The date to format.
     * @param formatter The formatter to use.
     */
    public LocalDateTimeAsText(final LocalDateTime date,
        final DateTimeFormatter formatter) {
        this.formatted = new UncheckedScalar<>(
            () -> formatter.format(date.atZone(ZoneId.systemDefault()))
        );
    }

    @Override
    public String asString() {
        return this.formatted.value();
    }

}
