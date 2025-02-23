/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.io.IOException;
import org.cactoos.Proc;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedProc}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IoCheckedProcTest {
    @Test
    void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void rethrowsCheckedToIoException() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void runtimeExceptionGoesOut() {
        new Assertion<>(
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
        ).affirm();
    }

}
