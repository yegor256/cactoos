/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedBiFunc}.
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class IoCheckedBiFuncTest {

    @Test
    void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void rethrowsCheckedToIoException() {
        new Assertion<>(
            "IOException should be rethrown",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IOException("intended to fail");
                }
            ).apply(1, 2),
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void runtimeExceptionGoesOut() {
        new Assertion<>(
            "Runtime exception should be rethrown",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IllegalStateException("intended to fail here");
                }
            ).apply(1, 2),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }
}
