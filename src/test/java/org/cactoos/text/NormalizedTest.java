/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Normalized}.
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class NormalizedTest {

    @Test
    void normalizesText() {
        new Assertion<>(
            "Can't normalize a text",
            new Normalized(" \t hello  \t\tworld   \t"),
            new HasString("hello world")
        ).affirm();
    }

}
