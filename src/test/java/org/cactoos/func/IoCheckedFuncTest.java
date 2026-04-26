/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedFunc}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IoCheckedFuncTest {

    @Test
    void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void rethrowsCheckedToIoException() {
        MatcherAssert.assertThat(
            "Must rethrow as IOException",
            () -> new IoCheckedFunc<>(
                i -> {
                    throw new InterruptedException("intended to fail");
                }
            ).apply(1),
            new Throws<>(
                IOException.class
            )
        );
    }

    @Test
    void runtimeExceptionGoesOut() {
        MatcherAssert.assertThat(
            "Must throw runtime exception as is",
            () -> new IoCheckedFunc<>(
                i -> {
                    throw new IllegalStateException("intended to fail here");
                }
            ).apply(1),
            new Throws<>(
                IllegalStateException.class
            )
        );
    }
}
