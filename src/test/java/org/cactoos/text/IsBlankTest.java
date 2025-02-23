/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link IsBlank}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IsBlankTest {

    @Test
    void determinesEmptyText() {
        new Assertion<>(
            "Can't determine an empty text",
            new IsBlank(
                new TextOf("")
            ),
            new HasValue<>(Boolean.TRUE)
        ).affirm();
    }

    @Test
    void determinesBlankText() {
        new Assertion<>(
            "Can't determine an empty text with spaces",
            new IsBlank(
                new TextOf("  ")
            ),
            new HasValue<>(Boolean.TRUE)
        ).affirm();
    }

    @Test
    void determinesNotBlankText() {
        new Assertion<>(
            "Can't detect a nonempty text",
            new IsBlank(
                new TextOf("not empty")
            ),
            new HasValue<>(Boolean.FALSE)
        ).affirm();
    }
}
