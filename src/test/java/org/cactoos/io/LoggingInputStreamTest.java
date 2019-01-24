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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link LoggingInputStream}.
 *
 * @since 0.39
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class LoggingInputStreamTest {

    @Test(expected = Exception.class)
    public void reThrowsException() throws IOException {
        final LoggingInputStream stream = new LoggingInputStream(
            new InputStream() {
                @Override
                public int read() throws IOException {
                    throw new IOException("Some read exception");
                }
            },
            this.getClass().getSimpleName()
        );
        MatcherAssert.assertThat(
            "Read doesn't throw an the exception",
            stream.read(),
            new IsNull<>()
        );
    }

    @Test
    public void readEmptyStream() {
        final LoggingInputStream stream = new LoggingInputStream(
            new ByteArrayInputStream(
                "".getBytes()
            ),
            this.getClass().getSimpleName()
        );
        new Assertion<>(
            "Read empty stream behavior is the same as of JDK",
            stream::read,
            new IsEqual<>(
                new ByteArrayInputStream(
                    "".getBytes()
                ).read()
            )
        ).affirm();
    }
}
