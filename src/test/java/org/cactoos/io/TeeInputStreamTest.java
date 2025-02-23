/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link TeeInputStream}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TeeInputStreamTest {

    @Test
    @SuppressWarnings("unchecked")
    void copiesContentByteByByte() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String content = "Hello, товарищ!";
        new Assertion<>(
            "Must copy InputStream to OutputStream byte by byte",
            new TextOf(
                new ReaderOf(
                    new TeeInputStream(
                        new ByteArrayInputStream(
                            content.getBytes(StandardCharsets.UTF_8)
                        ), baos
                    )
                )
            ).asString(),
            new AllOf<>(
                new IsEqual<>(content),
                new IsEqual<>(
                    new String(baos.toByteArray(), StandardCharsets.UTF_8)
                )
            )
        ).affirm();
    }

    @Test
    void leftInputClosed() throws Exception {
        try (StringWriterMock write = new StringWriterMock()) {
            new LengthOf(
                new TeeInput(
                    "foo",
                    new OutputTo(write)
                )
            ).value();
            new Assertion<>(
                "Must use output after usage from TeeInput",
                write.isClosed(),
                new IsEqual<>(true)
            ).affirm();
        }
    }

    /**
     * Mock object around StringWriter for checking closing state.
     * @since 0.1
     */
    private static final class StringWriterMock extends StringWriter {
        /**
         * Closing state.
         */
        private final AtomicBoolean closed = new AtomicBoolean(false);

        @Override
        public void close() {
            this.closed.set(true);
        }

        public boolean isClosed() {
            return this.closed.get();
        }
    }
}
