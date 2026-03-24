/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test suite for {@link ThrowsOnFalse}.
 *
 * @since 0.56.0
 */
final class ThrowsOnFalseTest {

    @Test
    void throwsOnFalse() {
        MatcherAssert.assertThat(
            "Throws an exception",
            () -> new ThrowsOnFalse(
                () -> false, "test message"
            ).value(),
            new Throws<>("test message", IllegalArgumentException.class)
        );
    }

    @Test
    void throwsSuppliedExceptionOnFalse() {
        MatcherAssert.assertThat(
            "Throws supplied exception",
            () -> new ThrowsOnFalse(
                () -> false,
                () -> new IllegalStateException("illegal state")
            ).value(),
            new Throws<>("illegal state", IllegalStateException.class)
        );
    }

    @Test
    void returnsTrueOnTrue() throws Exception {
        MatcherAssert.assertThat(
            "Must return true on true statement",
            new ThrowsOnFalse(() -> true, "test").value(),
            new IsEqual<>(true)
        );
    }
}
