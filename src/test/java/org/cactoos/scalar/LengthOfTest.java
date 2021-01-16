/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cactoos.scalar;

import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link LengthOf}.
 *
 * @since 0.1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
final class LengthOfTest {

    @Test
    void lengthOfInput() {
        new Assertion<>(
            "Must calculate length of input with float value",
            new LengthOf(
                new InputOf("Hello3")
            ),
            new ScalarHasValue<>(6L)
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
            new ScalarHasValue<>(12L)
        ).affirm();
    }

    @Test
    void lengthOfZeroBuffer() throws Exception {
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
    void lengthOf() throws Exception {
        new Assertion<>(
            "Must calculate length of iterable for integer",
            new LengthOf(
                new IterableOf<>(1, 2, 3, 4)
            ),
            new ScalarHasValue<>(4L)
        ).affirm();
    }

    @Test
    void lengthOfEmptyIterable() {
        new Assertion<>(
            "Must calculate length of empty iterable",
            new LengthOf(
                new IterableOf<>()
            ),
            new ScalarHasValue<>(0L)
        ).affirm();
    }

    @Test
    void lengthOfEmptyText() {
        new Assertion<>(
            "Must calculate length of empty string",
            new LengthOf(new TextOf("")),
            new ScalarHasValue<>(0L)
        ).affirm();
    }

    @Test
    void lengthOfUnicodeAsText() {
        new Assertion<>(
            "Must calculate character-length of unicode text",
            new LengthOf(new TextOf("привет")),
            new ScalarHasValue<>(6L)
        ).affirm();
    }

    @Test
    void lengthOfUnicodeAsInput() {
        new Assertion<>(
            "Must calculate character-length of unicode input",
            new LengthOf(new InputOf("Привет")),
            new ScalarHasValue<>(12L)
        ).affirm();
    }
}
