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

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for LocalDateTimeAsText.
 * @since 0.28
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class LocalDateTimeAsTextTest {

    @Test
    public void localDateTimeFormattedAsIsoDateTime() {
        final LocalDateTime date = LocalDateTime.of(
            2017, 12, 13, 14, 15, 16, 17
        );
        MatcherAssert.assertThat(
            "Can't format a LocalDateTime with default/ISO format.",
            new LocalDateTimeAsText(date).asString(),
            Matchers.is(
                MessageFormat.format(
                    "2017-12-13T14:15:16.000000017{0}",
                    date.atZone(ZoneId.systemDefault()).getOffset().toString()
                )
            )
        );
    }

    @Test
    public void localDateTimeFormattedWithFormatString() {
        final LocalDateTime date = LocalDateTime.of(
            2017, 12, 13, 14, 15, 16, 17
        );
        MatcherAssert.assertThat(
            "Can't format a LocalDateTime with format.",
            new LocalDateTimeAsText(date, "yyyy-MM-dd HH:mm:ss").asString(),
            Matchers.is("2017-12-13 14:15:16")
        );
    }

    @Test
    public void localDateTimeFormattedWithFormatStringWithLocale() {
        final LocalDateTime date = LocalDateTime.of(
            2017, 12, 13, 14, 15, 16, 17
        );
        MatcherAssert.assertThat(
            "Can't format a LocalDateTime with format using locale.",
            new LocalDateTimeAsText(
                date, "yyyy MMM dd. HH.mm.ss", Locale.FRENCH
            ).asString(),
            Matchers.is("2017 déc. 13. 14.15.16")
        );
    }

    @Test
    public void currentLocalDateTimeAsText() {
        MatcherAssert.assertThat(
            "Can't format a LocalDateTime with ISO format.",
            new LocalDateTimeAsText(LocalDateTime.now()).asString(),
            Matchers.notNullValue()
        );
    }

}
