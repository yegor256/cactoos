/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link LoggingInputStream}.
 *
 * @since 0.39
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
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
            new Assertion<>(
                "Read doesn't throw an the exception.",
                stream::read,
                new Throws<>(message, IOException.class)
            ).affirm();
        }
    }

    @Test
    void readEmptyStream() throws IOException {
        final LoggingInputStream stream = new LoggingInputStream(
            new ByteArrayInputStream(
                "".getBytes()
            ),
            this.getClass().getSimpleName()
        );
        new Assertion<>(
            "Empty stream did not return -1",
            stream.read(),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    void readByteByByte() throws IOException {
        final LoggingInputStream stream = new LoggingInputStream(
            new ByteArrayInputStream(
                new byte[] {
                    (byte) 20,
                    (byte) 10,
                }
            ),
            this.getClass().getSimpleName()
        );
        new Assertion<>(
            "First byte was not 20",
            stream.read(),
            new IsEqual<>(20)
        ).affirm();
        new Assertion<>(
            "Second byte was not 10",
            stream.read(),
            new IsEqual<>(10)
        ).affirm();
        new Assertion<>(
            "When stream is exhausted it didn't return -1",
            stream.read(),
            new IsEqual<>(-1)
        ).affirm();
    }
}
