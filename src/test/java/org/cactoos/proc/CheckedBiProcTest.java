/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link CheckedBiProc}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class CheckedBiProcTest {

    @Test
    void runtimeExceptionIsNotWrapped() {
        new Assertion<>(
            "Runtime exception is warped",
            () -> {
                new CheckedBiProc<>(
                    (first, second) -> {
                        throw new IllegalStateException("runtime1");
                    },
                    IOException::new
                ).exec(true, true);
                return 1;
            },
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void checkedExceptionIsWrapped() {
        new Assertion<>(
            "",
            () -> {
                new CheckedBiProc<>(
                    (first, second) -> {
                        throw new EOFException("runtime2");
                    },
                    IOException::new
                ).exec(true, true);
                return 1;
            },
            new Throws<>(IOException.class)
        );
    }

    @Test
    void extraWrappingIgnored() {
        try {
            new CheckedBiProc<>(
                (first, second) -> {
                    throw new IOException("runtime3");
                },
                IOException::new
            ).exec(true, true);
        } catch (final IOException exp) {
            new Assertion<>(
                "Extra wrapping of IOException has been added",
                exp.getCause(),
                new IsNull<>()
            ).affirm();
        }
    }

    @Test
    void noExceptionThrown() throws Exception {
        final AtomicInteger counter = new AtomicInteger();
        new CheckedBiProc<>(
            (first, second) -> counter.incrementAndGet(),
            exp -> exp
        ).exec(true, true);
        new Assertion<>(
            "Must not throw an exception",
            counter.get(),
            new IsEqual<>(1)
        ).affirm();
    }
}
