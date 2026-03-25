/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Binary}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BinaryTest {

    @Test
    void conditionTrueReturnsTrue() {
        MatcherAssert.assertThat(
            "Binary has to return true",
            new Binary(
                new True(),
                () -> { }
            ),
            new HasValue<>(true)
        );
    }

    @Test
    void conditionTrueInvokesProc() throws Exception {
        final AtomicInteger counter = new AtomicInteger(0);
        new Binary(
            new True(),
            counter::incrementAndGet
        ).value();
        MatcherAssert.assertThat(
            "Binary has to invoke increment method",
            counter.get(),
            new IsEqual<>(1)
        );
    }

    @Test
    void conditionFalseReturnsFalse() {
        MatcherAssert.assertThat(
            "Binary has to return false",
            new Binary(
                new False(),
                () -> { }
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void conditionFalseDoesNotInvokeProc() throws Exception {
        final AtomicInteger counter = new AtomicInteger(0);
        new Binary(
            new False(),
            counter::incrementAndGet
        ).value();
        MatcherAssert.assertThat(
            "Binary must not invoke increment method",
            counter.get(),
            new IsEqual<>(0)
        );
    }

    @Test
    void throwsException() {
        final String msg = "Custom exception message";
        final Binary binary = new Binary(
            new True(),
            () -> {
                throw new IllegalArgumentException(msg);
            }
        );
        MatcherAssert.assertThat(
            "Binary has to throw exception",
            binary,
            new Throws<>(msg, IllegalArgumentException.class)
        );
    }
}
