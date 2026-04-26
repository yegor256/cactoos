/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.io.IOException;
import org.cactoos.Proc;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedProc}.
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IoCheckedProcTest {

    @Test
    void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        MatcherAssert.assertThat(
            "Must rethrow original IOException",
            () -> {
                new IoCheckedProc<>(
                    (Proc<Integer>) i -> {
                        throw exception;
                    }
                ).exec(1);
                return null;
            },
            new Throws<>(exception.getMessage(), exception.getClass())
        );
    }

    @Test
    void rethrowsCheckedToIoException() {
        MatcherAssert.assertThat(
            "Must wrap and throw IOException",
            () -> {
                new IoCheckedProc<>(
                    (Proc<Integer>) i -> {
                        throw new InterruptedException("intended to fail");
                    }
                ).exec(1);
                return null;
            },
            new Throws<>(IOException.class)
        );
    }

    @Test
    void runtimeExceptionGoesOut() {
        MatcherAssert.assertThat(
            "Must throw runtime exceptions as is",
            () -> {
                new IoCheckedProc<>(
                    i -> {
                        throw new IllegalStateException("intended to fail here");
                    }
                ).exec(1);
                return null;
            },
            new Throws<>(IllegalStateException.class)
        );
    }
}
