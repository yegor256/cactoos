/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.util.Arrays;
import java.util.Random;
import org.cactoos.bytes.BytesOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link TailOf}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TailOfTest {

    @Test
    void tailsOnLongStream() throws Exception {
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
    void tailsOnExactStream() throws Exception {
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
    void tailsOnExactStreamAndBuffer() throws Exception {
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
    void tailsOnShorterStream() throws Exception {
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
    void tailsOnStreamLongerThanBufferAndBytes() throws Exception {
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

    @Test
    void failsIfBufferSizeSmallerThanTailSize() {
        final int size = 4;
        new Assertion<>(
            "Can't read in smaller buffer",
            () -> new BytesOf(
                new TailOf(
                    new InputOf(new BytesOf(this.generate(size))),
                    size,
                    size - 1
                )
            ).asBytes(),
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
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
