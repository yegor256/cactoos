/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test cases for {@link HeadOf}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class HeadOfTest {

    @Test
    void readsHeadOfLongerInput() {
        new Assertion<>(
            "must limit exactly the number of read bytes",
            new TextOf(
                new HeadOf(
                    new InputOf("readsHeadOfLongerInput"),
                    5
                )
            ),
            new HasString("reads")
        ).affirm();
    }

    @Test
    void readsEmptyHeadOfInput() {
        new Assertion<>(
            "must limit to 0 the number of read bytes",
            new TextOf(
                new HeadOf(
                    new InputOf("readsEmptyHeadOfInput"),
                    0
                )
            ),
            new HasString("")
        ).affirm();
    }

    @Test
    void readsHeadOfShorterInput() {
        final String input = "readsHeadOfShorterInput";
        new Assertion<>(
            "must limit to at most the number of available bytes",
            new TextOf(
                new HeadOf(
                    new InputOf(input),
                    35
                )
            ),
            new HasString(input)
        ).affirm();
    }
}
