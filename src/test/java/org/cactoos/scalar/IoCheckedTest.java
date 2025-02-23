/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoChecked}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IoCheckedTest {

    @Test
    void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        new Assertion<>(
            "Must rethrow IOException",
            () -> new IoChecked<>(
                () -> {
                    throw exception;
                }
            ).value(),
            new Throws<>(exception.getMessage(), exception.getClass())
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    void throwsException() {
        new Assertion<>(
            "Must throw IOException from Exception",
            () -> new IoChecked<>(
                () -> {
                    throw new Exception("intended to fail");
                }
            ).value(),
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void runtimeExceptionGoesOut() {
        final RuntimeException exception = new IllegalStateException("intended to fail here");
        new Assertion<>(
            "Must rethrow RuntimeExcepion",
            () -> new IoChecked<>(
                () -> {
                    throw exception;
                }
            ).value(),
            new Throws<>(exception.getMessage(), exception.getClass())
        ).affirm();
    }

}
