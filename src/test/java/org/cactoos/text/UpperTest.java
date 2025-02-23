/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Upper}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class UpperTest {

    @Test
    void convertsText() {
        new Assertion<>(
            "Can't upper case a text",
            new Upper(new TextOf("Hello!")),
            new HasString("HELLO!")
        ).affirm();
    }

    @Test
    void convertsString() {
        new Assertion<>(
            "Can't upper case a string",
            new Upper("World!"),
            new HasString("WORLD!")
        ).affirm();
    }
}
