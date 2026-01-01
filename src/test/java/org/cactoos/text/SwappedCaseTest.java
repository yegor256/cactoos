/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link SwappedCase}.
 *
 * @since 0.13.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SwappedCaseTest {

    @Test
    void swapText() {
        new Assertion<>(
            "Can't swap a text",
            new SwappedCase(
                new TextOf("HellO!")
            ),
            new HasString("hELLo!")
        ).affirm();
    }

    @Test
    void swapEmptyText() {
        new Assertion<>(
            "Empty swapped text should be the same as original",
            new SwappedCase(
                new TextOf("")
            ),
            new HasString("")
        ).affirm();
    }

}
