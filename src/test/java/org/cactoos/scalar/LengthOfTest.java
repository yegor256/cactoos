/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.scalar;

import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link LengthOf}.
 *
 * @since 0.1.0
 */
final class LengthOfTest {

    @Test
    void lengthOfInput() {
        new Assertion<>(
            "Must calculate length of input with float value",
            new LengthOf(
                new InputOf("Hello3")
            ),
            new HasValue<>(6L)
        ).affirm();
    }

    @Test
    void lengthOfInputWithCustomBuffer() {
        new Assertion<>(
            "Must calculate length with custom buffer",
            new LengthOf(
                new InputOf("test buffer1"),
                1
            ),
            new HasValue<>(12L)
        ).affirm();
    }

    @Test
    void lengthOfZeroBuffer() {
        new Assertion<>(
            "Must fail to calculate length of empty buffer",
            () -> new LengthOf(
                new InputOf("test buffer2"),
                0
            ).value(),
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void lengthOf() {
        new Assertion<>(
            "Must calculate length of iterable for integer",
            new LengthOf(
                new IterableOf<>(1, 2, 3, 4)
            ),
            new HasValue<>(4L)
        ).affirm();
    }

    @Test
    void lengthOfEmptyIterable() {
        new Assertion<>(
            "Must calculate length of empty iterable",
            new LengthOf(
                new IterableOf<>()
            ),
            new HasValue<>(0L)
        ).affirm();
    }

    @Test
    void lengthOfEmptyText() {
        new Assertion<>(
            "Must calculate length of empty string",
            new LengthOf(new TextOf("")),
            new HasValue<>(0L)
        ).affirm();
    }

    @Test
    void lengthOfUnicodeAsText() {
        new Assertion<>(
            "Must calculate character-length of unicode text",
            new LengthOf(new TextOf("привет")),
            new HasValue<>(6L)
        ).affirm();
    }

    @Test
    void lengthOfUnicodeAsInput() {
        new Assertion<>(
            "Must calculate character-length of unicode input",
            new LengthOf(new InputOf("Привет")),
            new HasValue<>(12L)
        ).affirm();
    }
}
