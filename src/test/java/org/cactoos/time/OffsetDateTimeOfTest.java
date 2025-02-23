/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.time;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Tests for {@link OffsetDateTimeOf}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class OffsetDateTimeOfTest {

    @Test
    void testParsingIsoFormattedStringToOffsetDateTime() {
        new Assertion<>(
            "Can't parse a OffsetDateTime with default/ISO format.",
            new OffsetDateTimeOf("2017-12-13T14:15:16.000000017+01:00"),
            new HasValue<>(
                OffsetDateTime.of(
                    2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1)
                )
            )
        ).affirm();
    }

    @Test
    void testParsingFormattedStringWithOffsetToOffsetDateTime() {
        new Assertion<>(
            "Can't parse a OffsetDateTime with custom format.",
            new OffsetDateTimeOf(
                "2017-12-13 14:15:16",
                "yyyy-MM-dd HH:mm:ss",
                ZoneOffset.ofHours(1)
            ),
            new HasValue<>(
                OffsetDateTime.of(
                    LocalDateTime.of(2017, 12, 13, 14, 15, 16),
                    ZoneOffset.ofHours(1)
                )
            )
        ).affirm();
    }

}
