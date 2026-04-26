/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.time;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Tests for {@link OffsetDateTimeOf}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class OffsetDateTimeOfTest {

    @Test
    void parsesIsoFormattedStringToOffsetDateTime() {
        MatcherAssert.assertThat(
            "Can't parse a OffsetDateTime with default/ISO format.",
            new OffsetDateTimeOf("2017-12-13T14:15:16.000000017+01:00"),
            new HasValue<>(
                OffsetDateTime.of(
                    2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1)
                )
            )
        );
    }

    @Test
    void parsesFormattedStringWithOffsetToOffsetDateTime() {
        MatcherAssert.assertThat(
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
        );
    }
}
