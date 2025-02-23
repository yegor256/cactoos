/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Tests for {@link LocalDateTimeOf}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class LocalDateTimeOfTest {

    @Test
    void testParsingIsoFormattedStringToLocalDateTime() {
        new Assertion<>(
            "Can't parse a LocalDateTime with default/ISO format.",
            new LocalDateTimeOf("2017-12-13T14:15:16.000000017+01:00"),
            new HasValue<>(LocalDateTime.of(2017, 12, 13, 14, 15, 16, 17))
        ).affirm();
    }

    @Test
    void testParsingFormattedStringWithFormatToLocalDateTime() {
        new Assertion<>(
            "Can't parse a LocalDateTime with custom format.",
            new LocalDateTimeOf(
                "2017-12-13 14:15:16.000000017",
                "yyyy-MM-dd HH:mm:ss.n"
            ),
            new HasValue<>(LocalDateTime.of(2017, 12, 13, 14, 15, 16, 17))
        ).affirm();
    }

    @Test
    void testParsingFormattedStringWithFormatterToLocalDateTime() {
        new Assertion<>(
            "Can't parse a LocalDateTime with custom formatter.",
            new LocalDateTimeOf(
                "2017-12-13 14:15:16.000000017",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n")
            ),
            new HasValue<>(LocalDateTime.of(2017, 12, 13, 14, 15, 16, 17))
        ).affirm();
    }

}
