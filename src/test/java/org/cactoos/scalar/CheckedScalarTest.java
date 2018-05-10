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
package org.cactoos.scalar;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link CheckedScalar}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class CheckedScalarTest {

    @Test(expected = IllegalStateException.class)
    public void runtimeExceptionGoesOut() throws Exception {
        new CheckedScalar<>(
            () -> {
                throw new IllegalStateException("runtime");
            },
            IOException::new
        ).value();
    }

    @Test(expected = IOException.class)
    public void throwsIoException() throws Exception {
        new CheckedScalar<>(
            () -> {
                throw new InterruptedException("interrupt");
            },
            IOException::new
        ).value();
    }

    @Test
    public void ioExceptionGoesOut() throws Exception {
        try {
            new CheckedScalar<>(
                () -> {
                    throw new IOException("io");
                },
                IOException::new
            ).value();
        } catch (final IOException exp) {
            MatcherAssert.assertThat(
                exp.getCause(),
                Matchers.nullValue()
            );
        }
    }

    @Test
    public void fileNotFoundExceptionGoesOut() throws Exception {
        try {
            new CheckedScalar<>(
                () -> {
                    throw new FileNotFoundException("file not found");
                },
                IOException::new
            ).value();
        } catch (final FileNotFoundException exp) {
            MatcherAssert.assertThat(
                exp.getCause(),
                Matchers.nullValue()
            );
        }
    }

    @Test
    public void throwsIoExceptionWithModifiedMessage() throws Exception {
        final String message = "error msg";
        try {
            new CheckedScalar<>(
                () -> {
                    throw new IOException("io");
                },
                exp -> new IOException(message, exp)
            ).value();
        } catch (final IOException exp) {
            MatcherAssert.assertThat(
                exp.getMessage(),
                Matchers.containsString(message)
            );
        }
    }
}
