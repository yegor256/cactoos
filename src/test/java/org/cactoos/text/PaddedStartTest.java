/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link PaddedStart}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class PaddedStartTest {

    @Test
    void noPaddingIfOrigTextIsAsLongAsRequestedLength() {
        MatcherAssert.assertThat(
            "Shouldn't pad the text",
            new PaddedStart(
                new TextOf("x"),
                1,
                '-'
            ),
            new HasString("x")
        );
    }

    @Test
    void somePaddingIfOrigTextIsShorterThanRequestedLength() {
        MatcherAssert.assertThat(
            "Should pad chars at start",
            new PaddedStart(
                new TextOf("x"),
                2,
                '-'
            ),
            new HasString("-x")
        );
    }

    @Test
    void noPaddingIfRequestedLengthIsNegative() {
        MatcherAssert.assertThat(
            "Shouldn't consider negative min length",
            new PaddedStart(
                new TextOf("x"),
                -1,
                '-'
            ),
            new HasString("x")
        );
    }
}
