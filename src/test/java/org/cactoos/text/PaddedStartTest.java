/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link PaddedStart}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class PaddedStartTest {

    @Test
    void noPaddingIfOrigTextIsAsLongAsRequestedLength() {
        new Assertion<>(
            "Shouldn't pad the text",
            new PaddedStart(
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
            "Should pad chars at start",
            new PaddedStart(
                new TextOf("x"),
                2,
                '-'
            ),
            new HasString("-x")
        ).affirm();
    }

    @Test
    void noPaddingIfRequestedLengthIsNegative()  {
        new Assertion<>(
            "Shouldn't consider negative min length",
            new PaddedStart(
                new TextOf("x"),
                -1,
                '-'
            ),
            new HasString("x")
        ).affirm();
    }
}
