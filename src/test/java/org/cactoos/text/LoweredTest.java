/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Lowered}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class LoweredTest {

    @Test
    void convertsText() {
        MatcherAssert.assertThat(
            "Can't lower case a text",
            new Lowered(new TextOf("Hello!")),
            new HasString("hello!")
        );
    }

    @Test
    void convertsString() {
        MatcherAssert.assertThat(
            "Can't lower case a string",
            new Lowered("WoRLd!"),
            new HasString("world!")
        );
    }
}
