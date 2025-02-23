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
 * Test case for {@link EndsWith}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class EndsWithTest {

    @Test
    void textMatchesEnding() {
        new Assertion<>(
            "Text's ending must match pattern",
            new EndsWith(
                new TextOf("Elegant Object"),
                "Object"
            ),
            new HasValue<>(Boolean.TRUE)
        ).affirm();
    }

    @Test
    void textMismatchesEnding() {
        new Assertion<>(
            "Text's ending must not match pattern",
            new EndsWith(
                new TextOf("Java is awesome"),
                "good"
            ),
            new HasValue<>(Boolean.FALSE)
        ).affirm();
    }
}
