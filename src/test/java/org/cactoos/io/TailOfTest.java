/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.util.Arrays;
import java.util.Random;
import org.cactoos.bytes.BytesOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link TailOf}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TailOfTest {

    @Test
    void tailsOnLongStream() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        MatcherAssert.assertThat(
            "Can't tail long stream",
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size - 1
                )
            ).asBytes(),
            new IsEqual<>(Arrays.copyOfRange(bytes, 1, bytes.length))
        );
    }

    @Test
    void tailsOnExactStream() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        MatcherAssert.assertThat(
            "Can't tail exact stream",
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size
                )
            ).asBytes(),
            new IsEqual<>(bytes)
        );
    }

    @Test
    void tailsOnExactStreamAndBuffer() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        MatcherAssert.assertThat(
            "Can't tail exact stream and buffer",
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size,
                    size
                )
            ).asBytes(),
            new IsEqual<>(bytes)
        );
    }

    @Test
    void tailsOnShorterStream() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        MatcherAssert.assertThat(
            "Can't tail shorter stream",
            new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(bytes)),
                    size + 1
                )
            ).asBytes(),
            new IsEqual<>(bytes)
        );
    }

    @Test
    void tailsOnStreamLongerThanBufferAndBytes() throws Exception {
        final int size = 4;
        final byte[] bytes = this.generate(size);
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void failsIfBufferSizeSmallerThanTailSize() {
        final int size = 4;
        MatcherAssert.assertThat(
            "Can't read in smaller buffer",
            () -> new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(this.generate(size))),
                    size,
                    size - 1
                )
            ).asBytes(),
            new Throws<>(IllegalArgumentException.class)
        );
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
