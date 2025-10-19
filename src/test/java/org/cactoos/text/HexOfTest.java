/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.bytes.BytesOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link HexOf}.
 * @since 0.28
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class HexOfTest {

    @Test
    void emptyString() {
        new Assertion<>(
            "Can't represent an empty string as hexadecimal",
            new HexOf(
                new BytesOf("")
            ),
            new HasString("")
        ).affirm();
    }

    @Test
    void notEmptyString() {
        new Assertion<>(
            "Can't represent a string as hexadecimal",
            new HexOf(
                new BytesOf("What's up, друг?")
            ),
            new HasString("5768617427732075702c20d0b4d180d183d0b33f")
        ).affirm();
    }
}
