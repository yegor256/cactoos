/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test cases for {@link HeadOf}.
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class HeadOfTest {

    @Test
    void readsHeadOfLongerInput() {
        MatcherAssert.assertThat(
            "must limit exactly the number of read bytes",
            new TextOf(
                new HeadOf(
                    new InputOf("readsHeadOfLongerInput"),
                    5
                )
            ),
            new HasString("reads")
        );
    }

    @Test
    void readsEmptyHeadOfInput() {
        MatcherAssert.assertThat(
            "must limit to 0 the number of read bytes",
            new TextOf(
                new HeadOf(
                    new InputOf("readsEmptyHeadOfInput"),
                    0
                )
            ),
            new HasString("")
        );
    }

    @Test
    void readsHeadOfShorterInput() {
        MatcherAssert.assertThat(
            "must limit to at most the number of available bytes",
            new TextOf(
                new HeadOf(
                    new InputOf("readsHeadOfShorterInput"),
                    35
                )
            ),
            new HasString("readsHeadOfShorterInput")
        );
    }
}
