/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedFunc}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class IoCheckedFuncTest {

    @Test
    void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        new Assertion<>(
            "Must rethrow original IOException",
            () -> new IoCheckedFunc<>(
                i -> {
                    throw exception;
                }
            ).apply(1),
            new Throws<>(
                exception.getMessage(),
                exception.getClass()
            )
        ).affirm();
    }

    @Test
    void rethrowsCheckedToIoException() {
        new Assertion<>(
            "Must rethrow as IOException",
            () -> new IoCheckedFunc<>(
                i -> {
                    throw new InterruptedException("intended to fail");
                }
            ).apply(1),
            new Throws<>(
                IOException.class
            )
        ).affirm();
    }

    @Test
    void runtimeExceptionGoesOut() {
        new Assertion<>(
            "Must throw runtime exception as is",
            () -> new IoCheckedFunc<>(
                i -> {
                    throw new IllegalStateException("intended to fail here");
                }
            ).apply(1),
            new Throws<>(
                IllegalStateException.class
            )
        ).affirm();
    }

}
