/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.AcceptPendingException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Checked}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class CheckedTest {

    @Test
    void runtimeExceptionGoesOut() {
        MatcherAssert.assertThat(
            "Must throw runtime exception",
            () -> new Checked<>(
                () -> {
                    throw new IllegalStateException("runtime");
                },
                IOException::new
            ).value(),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void usesGenericVarianceOnExceptionTypes() {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void usesContravarianceOnResultType() throws Exception {
        MatcherAssert.assertThat(
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
        MatcherAssert.assertThat(
            "Must throw io exception",
            () -> new Checked<>(
                () -> {
                    throw new InterruptedException("interrupt");
                },
                IOException::new
            ).value(),
            new Throws<>(IOException.class)
        );
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
            MatcherAssert.assertThat(
                "Must not have cause when throwing io exception",
                exp.getCause(),
                Matchers.nullValue()
            );
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
            MatcherAssert.assertThat(
                "Must not have cause when throwing file not found exception",
                exp.getCause(),
                Matchers.nullValue()
            );
        }
    }

    @Test
    void throwsIoExceptionWithModifiedMessage() {
        MatcherAssert.assertThat(
            "Must throw io exception with modified message",
            () -> new Checked<>(
                () -> {
                    throw new IOException("io");
                },
                exp -> new IOException("error msg", exp)
            ).value(),
            new Throws<>("error msg", IOException.class)
        );
    }
}
