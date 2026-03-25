/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link EndsWith}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class EndsWithTest {

    @Test
    void textMatchesEnding() {
        MatcherAssert.assertThat(
            "Text's ending must match pattern",
            new EndsWith(
                new TextOf("Elegant Object"),
                "Object"
            ),
            new HasValue<>(Boolean.TRUE)
        );
    }

    @Test
    void textMismatchesEnding() {
        MatcherAssert.assertThat(
            "Text's ending must not match pattern",
            new EndsWith(
                new TextOf("Java is awesome"),
                "good"
            ),
            new HasValue<>(Boolean.FALSE)
        );
    }
}
