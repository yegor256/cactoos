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
 * Test case for {@link Lowered}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class LoweredTest {

    @Test
    void convertsText() {
        new Assertion<>(
            "Can't lower case a text",
            new Lowered(new TextOf("HelLo!")),
            new HasString("hello!")
        ).affirm();
    }

    @Test
    void convertsString() {
        new Assertion<>(
            "Can't lower case a string",
            new Lowered("WoRLd!"),
            new HasString("world!")
        ).affirm();
    }
}
