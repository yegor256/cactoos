/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link LoggingInputStream}.
 *
 * @since 0.39
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class LoggingInputStreamTest {

    @Test
    void reThrowsException() throws IOException {
        final String message = "Some read exception.";
        try (LoggingInputStream stream = new LoggingInputStream(
            new InputStream() {
                @Override
                public int read() throws IOException {
                    throw new IOException(message);
                }
            },
            this.getClass().getSimpleName()
        )) {
            MatcherAssert.assertThat(
                "Read doesn't throw an the exception.",
                stream::read,
                new Throws<>(message, IOException.class)
            );
        }
    }

    @Test
    void readEmptyStream() throws IOException {
        MatcherAssert.assertThat(
            "Empty stream did not return -1",
            new LoggingInputStream(
                new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)),
                this.getClass().getSimpleName()
            ).read(),
            new IsEqual<>(-1)
        );
    }

    @Test
    void readsFirstByte() throws IOException {
        MatcherAssert.assertThat(
            "First byte was not 20",
            new LoggingInputStream(
                new ByteArrayInputStream(
                    new byte[] {(byte) 20, (byte) 10}
                ),
                this.getClass().getSimpleName()
            ).read(),
            new IsEqual<>(20)
        );
    }

    @Test
    void readsSecondByte() throws IOException {
        try (LoggingInputStream stream = new LoggingInputStream(
            new ByteArrayInputStream(
                new byte[] {(byte) 20, (byte) 10}
            ),
            this.getClass().getSimpleName()
        )) {
            stream.read();
            MatcherAssert.assertThat(
                "Second byte was not 10",
                stream.read(),
                new IsEqual<>(10)
            );
        }
    }

    @Test
    void returnsMinusOneWhenExhausted() throws IOException {
        try (LoggingInputStream stream = new LoggingInputStream(
            new ByteArrayInputStream(
                new byte[] {(byte) 20, (byte) 10}
            ),
            this.getClass().getSimpleName()
        )) {
            stream.read();
            stream.read();
            MatcherAssert.assertThat(
                "When stream is exhausted it didn't return -1",
                stream.read(),
                new IsEqual<>(-1)
            );
        }
    }
}
