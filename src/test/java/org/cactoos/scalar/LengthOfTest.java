/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.scalar;

import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
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
        MatcherAssert.assertThat(
            "Must calculate length of input with float value",
            new LengthOf(
                new InputOf("Hello3")
            ),
            new HasValue<>(6L)
        );
    }

    @Test
    void lengthOfInputWithCustomBuffer() {
        MatcherAssert.assertThat(
            "Must calculate length with custom buffer",
            new LengthOf(
                new InputOf("test buffer1"),
                1
            ),
            new HasValue<>(12L)
        );
    }

    @Test
    void lengthOfZeroBuffer() {
        MatcherAssert.assertThat(
            "Must fail to calculate length of empty buffer",
            () -> new LengthOf(
                new InputOf("test buffer2"),
                0
            ).value(),
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void lengthOf() {
        MatcherAssert.assertThat(
            "Must calculate length of iterable for integer",
            new LengthOf(
                new IterableOf<>(1, 2, 3, 4)
            ),
            new HasValue<>(4L)
        );
    }

    @Test
    void lengthOfEmptyIterable() {
        MatcherAssert.assertThat(
            "Must calculate length of empty iterable",
            new LengthOf(
                new IterableOf<>()
            ),
            new HasValue<>(0L)
        );
    }

    @Test
    void lengthOfEmptyText() {
        MatcherAssert.assertThat(
            "Must calculate length of empty string",
            new LengthOf(new TextOf("")),
            new HasValue<>(0L)
        );
    }

    @Test
    void lengthOfUnicodeAsText() {
        MatcherAssert.assertThat(
            "Must calculate character-length of unicode text",
            new LengthOf(new TextOf("привет")),
            new HasValue<>(6L)
        );
    }

    @Test
    void lengthOfUnicodeAsInput() {
        MatcherAssert.assertThat(
            "Must calculate character-length of unicode input",
            new LengthOf(new InputOf("Привет")),
            new HasValue<>(12L)
        );
    }
}
