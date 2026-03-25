/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link TrimmedRight}.
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TrimmedRightTest {

    @Test
    void convertsText() {
        MatcherAssert.assertThat(
            "Can't right trim a text",
            new TrimmedRight(new TextOf("  Hello!   \t ")),
            new HasString("  Hello!")
        );
    }

    @Test
    void trimmedBlankTextIsEmptyText() {
        MatcherAssert.assertThat(
            "Can't trim a blank text",
            new TrimmedRight(new TextOf("  \t ")),
            new HasString("")
        );
    }
}
