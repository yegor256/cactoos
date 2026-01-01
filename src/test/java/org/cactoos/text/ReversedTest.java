/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Reversed}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ReversedTest {

    @Test
    void reverseText() {
        new Assertion<>(
            "Can't reverse a text",
            new Reversed(
                new TextOf("Hello!")
            ),
            new HasString("!olleH")
        ).affirm();
    }

    @Test
    void reversedEmptyTextIsEmptyText() {
        new Assertion<>(
            "Can't reverse empty text",
            new Reversed(
                new TextOf("")
            ),
            new HasString("")
        ).affirm();
    }
}
