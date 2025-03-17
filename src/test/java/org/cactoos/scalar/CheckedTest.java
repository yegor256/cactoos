/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Checked}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class CheckedTest {

    @Test
    void runtimeExceptionGoesOut() {
        new Assertion<>(
            "Must throw runtime exception",
            () -> new Checked<>(
                () -> {
                    throw new IllegalStateException("runtime");
                },
                IOException::new
            ).value(),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void usesGenericVarianceOnExceptionTypes() {
        new Assertion<>(
            "Must use generic variance on exception types",
            () -> new Checked<String, IllegalStateException>(
                () -> {
                    throw new IllegalStateException();
                },
                (Throwable ex) -> {
                    return new AcceptPendingException();
                }
            ).value(),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void usesContravarianceOnResultType() throws Exception {
        new Assertion<>(
            "Must use contravariance on result",
            new Checked<CharSequence, IOException>(
                () -> "contravariance",
                IOException::new
            ).value(),
            new IsEqual<>("contravariance")
        );
    }

    @Test
    void throwsIoException() {
        new Assertion<>(
            "Must throw io exception",
            () -> new Checked<>(
                () -> {
                    throw new InterruptedException("interrupt");
                },
                IOException::new
            ).value(),
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void ioExceptionGoesOut() {
        try {
            new Checked<>(
                () -> {
                    throw new IOException("io");
                },
                IOException::new
            ).value();
        } catch (final IOException exp) {
            new Assertion<>(
                "Must not have cause when throwing io exception",
                exp.getCause(),
                Matchers.nullValue()
            ).affirm();
        }
    }

    @Test
    void fileNotFoundExceptionGoesOut() throws Exception {
        try {
            new Checked<>(
                () -> {
                    throw new FileNotFoundException("file not found");
                },
                IOException::new
            ).value();
        } catch (final FileNotFoundException exp) {
            new Assertion<>(
                "Must not have cause when throwing file not found exception",
                exp.getCause(),
                Matchers.nullValue()
            ).affirm();
        }
    }

    @Test
    void throwsIoExceptionWithModifiedMessage() {
        final String message = "error msg";
        new Assertion<>(
            "Must throw io exception with modified message",
            () -> new Checked<>(
                () -> {
                    throw new IOException("io");
                },
                exp -> new IOException(message, exp)
            ).value(),
            new Throws<>(message, IOException.class)
        ).affirm();
    }
}
