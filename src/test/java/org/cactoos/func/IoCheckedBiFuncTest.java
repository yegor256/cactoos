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
 * Test case for {@link IoCheckedBiFunc}.
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IoCheckedBiFuncTest {

    @Test
    void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        MatcherAssert.assertThat(
            "Must rethrow IOException",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw exception;
                }
            ).apply(1, 2),
            new Throws<>(
                exception.getMessage(),
                exception.getClass()
            )
        );
    }

    @Test
    void rethrowsCheckedToIoException() {
        MatcherAssert.assertThat(
            "IOException should be rethrown",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IOException("intended to fail");
                }
            ).apply(1, 2),
            new Throws<>(IOException.class)
        );
    }

    @Test
    void runtimeExceptionGoesOut() {
        MatcherAssert.assertThat(
            "Runtime exception should be rethrown",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IllegalStateException("intended to fail here");
                }
            ).apply(1, 2),
            new Throws<>(IllegalStateException.class)
        );
    }
}
