/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.IOException;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link CheckedBytes}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class CheckedBytesTest {

    @Test
    void runtimeExceptionIsNotWrapped() {
        new Assertion<>(
            "must not wrap runtime exception",
            new CheckedBytes<>(
                () -> {
                    throw new IllegalStateException("runtime1");
                },
                IOException::new
            )::asBytes,
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void checkedExceptionIsWrapped() {
        new Assertion<>(
            "must wrap checked exception",
            new CheckedBytes<>(
                () -> {
                    throw new IOException("runtime2");
                },
                IOException::new
            )::asBytes,
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void extraWrappingIgnored() {
        try {
            new CheckedBytes<>(
                () -> {
                    throw new IOException("runtime3");
                },
                IOException::new
            ).asBytes();
        } catch (final IOException exp) {
            new Assertion<>(
                "must not add extra wrapping of IOException",
                exp.getCause(),
                new IsNull<>()
            ).affirm();
        }
    }
}
