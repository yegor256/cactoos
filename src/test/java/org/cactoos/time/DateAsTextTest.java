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

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for DateAsText.
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyMethods"})
public final class DateAsTextTest {

    @Test
    public void formatsCurrentTime() {
        MatcherAssert.assertThat(
            "Can't format current time",
            new DateAsText().asString(),
            Matchers.notNullValue()
        );
    }

    @Test
    public void dateFormattedUsingIsoFormatter() {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        calendar.set(Calendar.MILLISECOND, 17);
        MatcherAssert.assertThat(
            "Can't format a java.util.Date with ISO format.",
            new DateAsText(calendar.getTime()).asString(),
            Matchers.is("2017-12-13T14:15:16.017Z")
        );
    }

    @Test
    public void dateFormattedUsingCustomFormat() {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        MatcherAssert.assertThat(
            "Can't format a java.util.Date with custom format.",
            new DateAsText(
                calendar.getTime(), "yyyy MM dd hh:mm:ss"
            ).asString(),
            Matchers.is("2017 12 13 02:15:16")
        );
    }

    @Test
    public void dateFormattedUsingCustomFormatDifferentLocale() {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        MatcherAssert.assertThat(
            "Can't format a java.util.Date with custom format.",
            new DateAsText(
                calendar.getTime(), "yyyy MMM dd hh:mm:ss", Locale.ITALIAN
            ).asString(),
            Matchers.is("2017 dic 13 02:15:16")
        );
    }

    @Test
    public void millisFormattedUsingIsoFormatter() {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        calendar.set(Calendar.MILLISECOND, 17);
        MatcherAssert.assertThat(
            "Can't format a java.util.Date with ISO format.",
            new DateAsText(calendar.getTime().getTime()).asString(),
            Matchers.is("2017-12-13T14:15:16.017Z")
        );
    }

    @Test
    public void millisFormattedUsingCustomFormat() {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        MatcherAssert.assertThat(
            "Can't format a java.util.Date with custom format.",
            new DateAsText(
                calendar.getTime().getTime(),
                "yyyy MM dd hh:mm:ss"
            ).asString(),
            Matchers.is("2017 12 13 02:15:16")
        );
    }

    @Test
    public void millisFormattedUsingCustomFormatDifferentLocale() {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        MatcherAssert.assertThat(
            "Can't format a java.util.Date with custom format.",
            new DateAsText(
                calendar.getTime().getTime(),
                "yyyy MMMM dd hh:mm:ss",
                Locale.US
            ).asString(),
            Matchers.is("2017 December 13 02:15:16")
        );
    }

}
