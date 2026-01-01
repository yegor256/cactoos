/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Tests for {@link ZonedDateTimeOf}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class ZonedDateTimeOfTest {

    @Test
    void testParsingIsoFormattedStringToZonedDateTime() {
        new Assertion<>(
            "Can't parse a ZonedDateTime with default/ISO format.",
            new ZonedDateTimeOf("2017-12-13T14:15:16.000000017+01:00"),
            new HasValue<>(
                ZonedDateTime.of(
                    2017, 12, 13, 14, 15, 16, 17,
                    ZoneId.ofOffset("", ZoneOffset.ofHours(1))
                )
            )
        ).affirm();
    }

    @Test
    void testParsingFormattedStringWithZoneToZonedDateTime() {
        new Assertion<>(
            "Can't parse a ZonedDateTime with custom format and zone.",
            new ZonedDateTimeOf(
                "2017-12-13 14:15:16",
                "yyyy-MM-dd HH:mm:ss",
                ZoneId.of("Europe/Berlin")
            ),
            new HasValue<>(
                ZonedDateTime.of(
                    LocalDateTime.of(2017, 12, 13, 14, 15, 16),
                    ZoneId.of("Europe/Berlin")
                )
            )
        ).affirm();
    }

    @Test
    void testParsingFormattedStringWithFormatterToZonedDateTime() {
        new Assertion<>(
            "Can't parse a ZonedDateTime with custom format and zone.",
            new ZonedDateTimeOf(
                "2017-12-13 14:15:16",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.of("Europe/Berlin"))
            ),
            new HasValue<>(
                ZonedDateTime.of(
                    LocalDateTime.of(2017, 12, 13, 14, 15, 16),
                    ZoneId.of("Europe/Berlin")
                )
            )
        ).affirm();
    }

}
