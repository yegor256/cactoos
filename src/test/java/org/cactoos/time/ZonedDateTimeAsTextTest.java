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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for ZonedDateTimeAsText.
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ZonedDateTimeAsTextTest {

    @Test
    public void zonedDateTimeFormattedAsIsoDateTime() {
        final ZonedDateTime date = ZonedDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")
        );
        MatcherAssert.assertThat(
            "Can't format a ZonedDateTime with default/ISO format.",
            new ZonedDateTimeAsText(date).asString(),
            Matchers.is("2017-12-13T14:15:16.000000017+01:00")
        );
    }

    @Test
    public void zonedDateTimeFormattedWithFormatString() {
        final ZonedDateTime date = ZonedDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")
        );
        MatcherAssert.assertThat(
            "Can't format a ZonedDateTime with format.",
            new ZonedDateTimeAsText(date, "yyyy-MM-dd HH:mm:ss").asString(),
            Matchers.is("2017-12-13 14:15:16")
        );
    }

    @Test
    public void zonedDateTimeFormattedWithFormatStringWithLocale() {
        final ZonedDateTime date = ZonedDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")
        );
        MatcherAssert.assertThat(
            "Can't format a ZonedDateTime with format using locale.",
            new ZonedDateTimeAsText(
                date, "yyyy MMM dd. HH.mm.ss", Locale.FRENCH
            ).asString(),
            Matchers.is("2017 déc. 13. 14.15.16")
        );
    }

    @Test
    public void currentZonedDateTimeAsText() {
        MatcherAssert.assertThat(
            "Can't format a ZonedDateTime with ISO format.",
            new ZonedDateTimeAsText(ZonedDateTime.now()).asString(),
            Matchers.notNullValue()
        );
    }

}
