/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Repeated}.
 *
 * @since 0.49.2
 * @checkstyle JavadocMethodCheck (100 lines)
 */
final class RepeatedTest {

    @Test
    void runsMultipleTimes() throws Exception {
        final AtomicInteger atom = new AtomicInteger();
        new Assertion<>(
            "Must run scalar 3 times",
            new Repeated<>(
                atom::incrementAndGet,
                3
            ).value(),
            new IsEqual<>(3)
        ).affirm();
    }

    @Test
    void throwsIfZero() {
        new Assertion<>(
            "Must throws an exception if number of repetitions not be at least 1",
            () -> new Repeated<>(
                new ScalarOf<>(
                    () -> {
                        throw new IllegalStateException("intended to fail");
                    },
                    "discarded"
                ),
                0
            ).value(),
            new Throws<>(
                "The number of repetitions must be at least 1",
                IllegalArgumentException.class
            )
        ).affirm();
    }

}
