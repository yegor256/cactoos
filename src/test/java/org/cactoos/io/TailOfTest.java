/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

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
        MatcherAssert.assertThat(
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size - 1
                )
            ).asBytes(),
            Matchers.equalTo(Arrays.copyOfRange(bytes, 1, bytes.length))
        );
    }

    @Test
    public void tailsOnExactStream() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        MatcherAssert.assertThat(
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size
                )
            ).asBytes(),
            Matchers.equalTo(bytes)
        );
    }

    @Test
    public void tailsOnExactStreamAndBuffer() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        MatcherAssert.assertThat(
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size,
                    size
                )
            ).asBytes(),
            Matchers.equalTo(bytes)
        );
    }

    @Test
    public void tailsOnShorterStream() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        MatcherAssert.assertThat(
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size + 1
                )
            ).asBytes(),
            Matchers.equalTo(bytes)
        );
    }

    @Test
    public void tailsOnStreamLongerThanBufferAndBytes() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        MatcherAssert.assertThat(
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size - 1,
                    size - 1
                )
            ).asBytes(),
            Matchers.equalTo(
                Arrays.copyOfRange(bytes, 1, bytes.length)
            )
        );
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
