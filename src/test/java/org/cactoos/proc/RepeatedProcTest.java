/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Proc;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link RepeatedProc}.
 *
 * @since 0.49.2
 */
final class RepeatedProcTest {

    @Test
    void runsProcMultipleTimes() throws Exception {
        final AtomicInteger atom = new AtomicInteger();
        final Proc<AtomicInteger> func = new RepeatedProc<>(
            AtomicInteger::getAndIncrement,
            3
        );
        func.exec(atom);
        new Assertion<>(
            "must run proc 3 times",
            atom.get(),
            new IsEqual<>(3)
        );
    }

    @Test
    void throwsIfZero() {
        new Assertion<>(
            "Must throw if zero",
            () -> {
                new RepeatedProc<>(
                    obj -> { },
                    0
                ).exec(new Object());
                return "discarded";
            },
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }
}
