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
package org.cactoos.scalar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.AcceptPendingException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Checked}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class CheckedTest {

    @Test(expected = IllegalStateException.class)
    public void runtimeExceptionGoesOut() throws Exception {
        new Checked<>(
            () -> {
                throw new IllegalStateException("runtime");
            },
            IOException::new
        ).value();
    }

    @Test(expected = IllegalStateException.class)
    public void usesGenericVarianceOnExceptionTypes() throws Exception {
        new Checked<String, IllegalStateException>(
            () -> {
                throw new IllegalStateException();
            },
            (Throwable ex) -> {
                return new AcceptPendingException();
            }
        ).value();
    }

    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void usesContravarianceOnResultType() throws Exception {
        new Assertion<>(
            "Must use contravariance on result",
            new Checked<CharSequence, IOException>(
                () -> new String("contravariance"),
                IOException::new
            ).value(),
            new IsEqual<>("contravariance")
        );
    }

    @Test(expected = IOException.class)
    public void throwsIoException() throws Exception {
        new Checked<>(
            () -> {
                throw new InterruptedException("interrupt");
            },
            IOException::new
        ).value();
    }

    @Test
    public void ioExceptionGoesOut() throws Exception {
        try {
            new Checked<>(
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
            new Checked<>(
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
            new Checked<>(
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
