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
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link TeeInputStream}.
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
final class TeeInputStreamTest {

    @Test
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
            Matchers.allOf(
                Matchers.equalTo(content),
                Matchers.equalTo(
                    new String(baos.toByteArray(), StandardCharsets.UTF_8)
                )
            )
        ).affirm();
    }

    @Test
    void leftInputClosed() {
        try (StringWriterMock write = new StringWriterMock()) {
            new LengthOf(
                new TeeInput(
                    "foo",
                    new OutputTo(write)
                )
            ).intValue();
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
