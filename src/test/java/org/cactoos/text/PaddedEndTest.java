/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
        new Assertion<>(
            "Shouldn't pad the text",
            new PaddedEnd(
                new TextOf("x"),
                1,
                '-'
            ),
            new HasString("x")
        ).affirm();
    }

    @Test
    void somePaddingIfOrigTextIsShorterThanRequestedLength() {
        new Assertion<>(
            "Should pad chars at end",
            new PaddedEnd(
                new TextOf("x"),
                2,
                '-'
            ),
            new HasString("x-")
        ).affirm();
    }

    @Test
    void noPaddingIfRequestedLengthIsNegative() {
        new Assertion<>(
            "Shouldn't consider negative min length",
            new PaddedEnd(
                new TextOf("x"),
                -1,
                '-'
            ),
            new HasString("x")
        ).affirm();
    }
}
