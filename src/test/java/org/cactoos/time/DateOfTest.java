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
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class DateOfTest {

    @Test
    public final void testParsingIsoFormattedStringToZonedDateTime()
        throws Exception {
        MatcherAssert.assertThat(
            "Can't parse a ZonedDateTime with default/ISO format.",
            new DateOf.Zoned(
            "2017-12-13T14:15:16.000000017+01:00[Europe/Berlin]"
            ).value(),
            Matchers.is(
                ZonedDateTime.of(
                2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")
                )
            )
        );
    }

    @Test
    public final void testParsingIsoFormattedStringToOffsetDateTime()
        throws Exception {
        MatcherAssert.assertThat(
            "Can't parse a OffsetDateTime with default/ISO format.",
            new DateOf.Offset(
            "2017-12-13T14:15:16.000000017+01:00[Europe/Berlin]"
            ).value(),
            Matchers.is(
                OffsetDateTime.of(
                    2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1)
                )
            )
        );
    }

    @Test
    public final void testParsingIsoFormattedStringToLocalDateTime()
        throws Exception {
        MatcherAssert.assertThat(
            "Can't parse a LocalDateTime with default/ISO format.",
            new DateOf.Local(
            "2017-12-13T14:15:16.000000017+01:00[Europe/Berlin]"
            ).value(),
            Matchers.is(LocalDateTime.of(2017, 12, 13, 14, 15, 16, 17))
        );
    }

}
