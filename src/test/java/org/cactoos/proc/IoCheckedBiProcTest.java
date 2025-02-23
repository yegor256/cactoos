/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.func.IoCheckedBiFunc;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedBiFunc}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IoCheckedBiProcTest {
    @Test
    void executesWrappedProc() throws Exception {
        final AtomicInteger counter = new AtomicInteger();
        new IoCheckedBiProc<>(
            (first, second) -> counter.incrementAndGet()
        ).exec(true, true);
        new Assertion<>(
            "Must execute wrapped proc",
            counter.get(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void wrapsExceptions() {
        final IoCheckedBiProc<Object, Object> proc = new IoCheckedBiProc<>(
            (first, second) -> {
                throw new IOException();
            }
        );
        new Assertion<>(
            "Must wrap with IOException",
            () -> {
                proc.exec(true, true);
                return true;
            },
            new Throws<>(
                "java.io.IOException",
                IOException.class
            )
        ).affirm();
    }

    @Test
    void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        try {
            new IoCheckedBiProc<>(
                (fst, scd) -> {
                    throw exception;
                }
            ).exec(1, 2);
        } catch (final IOException ex) {
            new Assertion<>(
                "Must re-throw IOException",
                ex,
                new IsEqual<>(exception)
            ).affirm();
        }
    }

    @Test
    void runtimeExceptionGoesOut() {
        final String msg = "intended to fail here";
        final IoCheckedBiProc<Object, Object> proc = new IoCheckedBiProc<>(
            (fst, scd) -> {
                throw new IllegalStateException(msg);
            }
        );
        new Assertion<>(
            "Must re-throw runtime exceptions",
            () -> {
                proc.exec(true, true);
                return true;
            },
            new Throws<>(
                msg,
                IllegalStateException.class
            )
        ).affirm();
    }
}
