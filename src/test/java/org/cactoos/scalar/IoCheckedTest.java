/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.IOException;
import java.text.ParseException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
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
        MatcherAssert.assertThat(
            "Must rethrow IOException",
            () -> new IoChecked<>(
                () -> {
                    throw exception;
                }
            ).value(),
            new Throws<>(exception.getMessage(), exception.getClass())
        );
    }

    @Test
    void throwsException() {
        MatcherAssert.assertThat(
            "Must throw IOException from Exception",
            () -> new IoChecked<>(
                () -> {
                    throw new ParseException("intended to fail", 0);
                }
            ).value(),
            new Throws<>(IOException.class)
        );
    }

    @Test
    void runtimeExceptionGoesOut() {
        final RuntimeException exception = new IllegalStateException("intended to fail here");
        MatcherAssert.assertThat(
            "Must rethrow RuntimeException",
            () -> new IoChecked<>(
                () -> {
                    throw exception;
                }
            ).value(),
            new Throws<>(exception.getMessage(), exception.getClass())
        );
    }
}
