/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Rotated}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class RotatedTest {

    @Test
    void rotateRightText() {
        MatcherAssert.assertThat(
            "Can't rotate text to right",
            new Rotated(
                new TextOf("Hello!"), 2
            ),
            new HasString("o!Hell")
        );
    }

    @Test
    void rotateLeftText() {
        MatcherAssert.assertThat(
            "Can't rotate text to left",
            new Rotated(
                new TextOf("Hi!"), -1
            ),
            new HasString("i!H")
        );
    }

    @Test
    void noRotateWhenShiftZero() {
        final String nonrotate = "Cactoos!";
        MatcherAssert.assertThat(
            "Rotate text shift zero",
            new Rotated(
                new TextOf(nonrotate), 0
            ),
            new HasString(nonrotate)
        );
    }

    @Test
    void noRotateWhenShiftModZero() {
        final String nonrotate = "Rotate";
        MatcherAssert.assertThat(
            "Rotate text shift mod zero",
            new Rotated(
                new TextOf(nonrotate), nonrotate.length()
            ),
            new HasString(nonrotate)
        );
    }

    @Test
    void noRotateWhenEmpty() {
        MatcherAssert.assertThat(
            "Rotate text when empty",
            new Rotated(
                new TextOf(""), 2
            ),
            new HasString("")
        );
    }
}
