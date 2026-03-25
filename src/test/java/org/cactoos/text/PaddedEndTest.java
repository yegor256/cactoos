/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link PaddedEnd}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class PaddedEndTest {

    @Test
    void noPaddingIfOrigTextIsAsLongAsRequestedLength() {
        MatcherAssert.assertThat(
            "Shouldn't pad the text",
            new PaddedEnd(
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
            "Should pad chars at end",
            new PaddedEnd(
                new TextOf("x"),
                2,
                '-'
            ),
            new HasString("x-")
        );
    }

    @Test
    void noPaddingIfRequestedLengthIsNegative() {
        MatcherAssert.assertThat(
            "Shouldn't consider negative min length",
            new PaddedEnd(
                new TextOf("x"),
                -1,
                '-'
            ),
            new HasString("x")
        );
    }
}
