/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.scalar;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test suite for {@link ThrowsOnFalse}.
 *
 * @since 0.56.0
 */
final class ThrowsOnFalseTest {

    @Test
    void throwsOnFalse() {
        final String message = "test message";
        new Assertion<>(
            "Throws an exception",
            () -> new ThrowsOnFalse(
                () -> false, message
            ).value(),
            new Throws<>(message, IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void throwsSuppliedExceptionOnFalse() {
        final String message = "illegal state";
        new Assertion<>(
            "Throws supplied exception",
            () -> new ThrowsOnFalse(
                () -> false,
                () -> new IllegalStateException(message)
            ).value(),
            new Throws<>(message, IllegalStateException.class)
        ).affirm();
    }

    @Test
    void returnsTrueOnTrue() throws Exception {
        new Assertion<>(
            "Must return true on true statement",
            new ThrowsOnFalse(() -> true, "test").value(),
            new IsEqual<>(true)
        ).affirm();
    }
}
