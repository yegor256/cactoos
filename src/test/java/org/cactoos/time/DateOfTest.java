/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.time;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Tests for DateOf.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class DateOfTest {

    @Test
    void parsesIsoFormattedStringToDate() {
        MatcherAssert.assertThat(
            "must parse a Date with default/ISO format.",
            new DateOf("2017-12-13T14:15:16.000000017Z"),
            new HasValue<>(
                Date.from(
                    LocalDateTime.of(
                        2017, 12, 13, 14, 15, 16, 17
                    ).toInstant(ZoneOffset.UTC)
                )
            )
        );
    }

    @Test
    void parsesCustomFormattedStringToDate() {
        MatcherAssert.assertThat(
            "must parse a Date with custom format.",
            new DateOf(
                "2017-12-13 14:15:16.000000017",
                "yyyy-MM-dd HH:mm:ss.n"
            ),
            new HasValue<>(
                Date.from(
                    LocalDateTime.of(
                        2017, 12, 13, 14, 15, 16, 17
                    ).toInstant(ZoneOffset.UTC)
                )
            )
        );
    }

    @Test
    void parsesCustomFormattedStringWithoutTimeToDate() {
        MatcherAssert.assertThat(
            "must parse a Date with custom format.",
            new DateOf(
                "2018-01-01",
                "yyyy-MM-dd"
            ),
            new HasValue<>(
                Date.from(
                    LocalDateTime.of(
                        2018, 01, 01, 0, 0, 0, 0
                    ).toInstant(ZoneOffset.UTC)
                )
            )
        );
    }

    @Test
    void parsesCustomFormatterStringToDate() {
        MatcherAssert.assertThat(
            "must parse a Date with custom format.",
            new DateOf(
                "2017-12-13 14:15:16.000000017",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n")
            ),
            new HasValue<>(
                Date.from(
                    LocalDateTime.of(
                        2017, 12, 13, 14, 15, 16, 17
                    ).toInstant(ZoneOffset.UTC)
                )
            )
        );
    }
}
