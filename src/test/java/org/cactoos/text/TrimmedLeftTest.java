/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link TrimmedLeft}.
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TrimmedLeftTest {

    @Test
    void convertsText() {
        MatcherAssert.assertThat(
            "Can't left trim a text",
            new TrimmedLeft(new TextOf("  Hello!   \t ")),
            new HasString("Hello!   \t ")
        );
    }

    @Test
    void trimmedBlankTextIsEmptyText() {
        MatcherAssert.assertThat(
            "Can't trim a blank text",
            new TrimmedLeft(new TextOf("  \t ")),
            new HasString("")
        );
    }
}
