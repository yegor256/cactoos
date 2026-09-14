/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.io.InputOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link SwappedCase}.
 *
 * @since 0.13.3
 */
final class SwappedCaseTest {

    @Test
    void swapInput() {
        MatcherAssert.assertThat(
            "Can't swap an input",
            new SwappedCase(
                new InputOf("HellO!")
            ),
            new HasString("hELLo!")
        );
    }

    @Test
    void swapText() {
        MatcherAssert.assertThat(
            "Can't swap a text",
            new SwappedCase(
                new TextOf("HellO!")
            ),
            new HasString("hELLo!")
        );
    }

    @Test
    void swapEmptyText() {
        MatcherAssert.assertThat(
            "Empty swapped text should be the same as original",
            new SwappedCase(
                new TextOf("")
            ),
            new HasString("")
        );
    }
}
