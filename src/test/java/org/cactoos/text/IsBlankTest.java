/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link IsBlank}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IsBlankTest {

    @Test
    void determinesEmptyText() {
        MatcherAssert.assertThat(
            "Can't determine an empty text",
            new IsBlank(
                new TextOf("")
            ),
            new HasValue<>(Boolean.TRUE)
        );
    }

    @Test
    void determinesBlankText() {
        MatcherAssert.assertThat(
            "Can't determine an empty text with spaces",
            new IsBlank(
                new TextOf("  ")
            ),
            new HasValue<>(Boolean.TRUE)
        );
    }

    @Test
    void determinesNotBlankText() {
        MatcherAssert.assertThat(
            "Can't detect a nonempty text",
            new IsBlank(
                new TextOf("not empty")
            ),
            new HasValue<>(Boolean.FALSE)
        );
    }
}
