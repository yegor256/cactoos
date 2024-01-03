/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
package org.cactoos.io;

import java.util.Arrays;
import java.util.Random;
import org.cactoos.bytes.BytesOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Tests for {@link TailOf}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class TailOfTest {

    @Test
    public void tailsOnLongStream() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        new Assertion<>(
            "Can't tail long stream",
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size - 1
                )
            ).asBytes(),
            new IsEqual<>(Arrays.copyOfRange(bytes, 1, bytes.length))
        ).affirm();
    }

    @Test
    public void tailsOnExactStream() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        new Assertion<>(
            "Can't tail exact stream",
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size
                )
            ).asBytes(),
            new IsEqual<>(bytes)
        ).affirm();
    }

    @Test
    public void tailsOnExactStreamAndBuffer() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        new Assertion<>(
            "Can't tail exact stream and buffer",
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size,
                    size
                )
            ).asBytes(),
            new IsEqual<>(bytes)
        ).affirm();
    }

    @Test
    public void tailsOnShorterStream() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        new Assertion<>(
            "Can't tail shorter stream",
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size + 1
                )
            ).asBytes(),
            new IsEqual<>(bytes)
        ).affirm();
    }

    @Test
    public void tailsOnStreamLongerThanBufferAndBytes() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        new Assertion<>(
            "Can't tail longer stream",
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size - 1,
                    size - 1
                )
            ).asBytes(),
            new IsEqual<>(
                Arrays.copyOfRange(bytes, 1, bytes.length)
            )
        ).affirm();
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsIfBufferSizeSmallerThanTailSize() throws Exception {
        final int size = 4;
        new BytesOf(
            new TailOf(
                new InputOf(new BytesOf(this.generate(size))),
                size,
                size - 1
            )
        ).asBytes();
    }

    /**
     * Generate random byte array.
     * @param size Size of array
     * @return Generated array
     */
    private byte[] generate(final int size) {
        final byte[] bytes = new byte[size];
        new Random().nextBytes(bytes);
        return bytes;
    }
}
