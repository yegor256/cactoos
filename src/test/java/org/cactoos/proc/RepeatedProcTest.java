/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link RepeatedProc}.
 * @since 0.49.2
 */
final class RepeatedProcTest {

    @Test
    void runsProcMultipleTimes() throws Exception {
        final AtomicInteger atom = new AtomicInteger();
        new RepeatedProc<>(AtomicInteger::getAndIncrement, 3).exec(atom);
        MatcherAssert.assertThat(
            "must run proc 3 times",
            atom.get(),
            new IsEqual<>(3)
        );
    }

    @Test
    void throwsIfZero() {
        MatcherAssert.assertThat(
            "Must throw if zero",
            () -> {
                new RepeatedProc<>(
                    obj -> { },
                    0
                ).exec(new Object());
                return "discarded";
            },
            new Throws<>(IllegalArgumentException.class)
        );
    }
}
