/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Trimmed}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TrimmedTest {

    @Test
    void convertsText() {
        MatcherAssert.assertThat(
            "Can't trim a text",
            new Trimmed(new TextOf("  Hello!   \t ")),
            new HasString("Hello!")
        );
    }

    @Test
    void trimmedBlankTextIsEmptyText() {
        MatcherAssert.assertThat(
            "Can't trim a blank text",
            new Trimmed(new TextOf("  \t ")),
            new HasString("")
        );
    }
}
