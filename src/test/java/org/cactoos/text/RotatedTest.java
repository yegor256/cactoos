/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
        new Assertion<>(
            "Can't rotate text to right",
            new Rotated(
                new TextOf("Hello!"), 2
            ),
            new HasString("o!Hell")
        ).affirm();
    }

    @Test
    void rotateLeftText() {
        new Assertion<>(
            "Can't rotate text to left",
            new Rotated(
                new TextOf("Hi!"), -1
            ),
            new HasString("i!H")
        ).affirm();
    }

    @Test
    void noRotateWhenShiftZero() {
        final String nonrotate = "Cactoos!";
        new Assertion<>(
            "Rotate text shift zero",
            new Rotated(
                new TextOf(nonrotate), 0
            ),
            new HasString(nonrotate)
        ).affirm();
    }

    @Test
    void noRotateWhenShiftModZero() {
        final String nonrotate = "Rotate";
        new Assertion<>(
            "Rotate text shift mod zero",
            new Rotated(
                new TextOf(nonrotate), nonrotate.length()
            ),
            new HasString(nonrotate)
        ).affirm();
    }

    @Test
    void noRotateWhenEmpty() {
        new Assertion<>(
            "Rotate text when empty",
            new Rotated(
                new TextOf(""), 2
            ),
            new HasString("")
        ).affirm();
    }
}
