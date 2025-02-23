/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Retry}.
 *
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class RetryTest {

    @Test
    void runsScalarMultipleTimes() {
        new Assertion<>(
            "must retry in case of failure",
            new Retry<>(
                () -> {
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                    return 0;
                },
                Integer.MAX_VALUE
            ),
            new HasValue<>(0)
        ).affirm();
    }

    @Test
    void runsScalarTwiceWithDefaults() {
        final AtomicInteger tries = new AtomicInteger(0);
        new Assertion<>(
            "Should run twice with defaults",
            new Retry<>(
                () -> {
                    if (tries.getAndIncrement() <= 1) {
                        throw new IllegalArgumentException("Not enough tries");
                    }
                    return 0;
                }
            ),
            new HasValue<>(0)
        ).affirm();
    }

    @Test
    void runsScalarMultipleTimesIgnoringNegativeDuration() {
        final int times = 2;
        final AtomicInteger tries = new AtomicInteger(0);
        new Assertion<>(
            "Should ignore negative duration",
            new Retry<>(
                () -> {
                    if (tries.getAndIncrement() < times) {
                        throw new IllegalArgumentException("Not yet");
                    }
                    return 0;
                },
                Integer.MAX_VALUE,
                Duration.of(-5L, ChronoUnit.DAYS)
            ),
            new HasValue<>(0)
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    void runsScalarMultipleTimesWithWait() throws Exception {
        final int times = 3;
        final long wait = 500L;
        final AtomicInteger tries = new AtomicInteger(0);
        final List<Instant> executions = new ArrayList<>(times);
        new Retry<>(
            () -> {
                if (tries.getAndIncrement() < times) {
                    executions.add(Instant.now());
                    throw new IllegalArgumentException("Not done yet");
                }
                return 0;
            },
            Integer.MAX_VALUE,
            Duration.ofMillis(wait)
        ).value();
        for (int position = 0; position < times - 1; position += 1) {
            new Assertion<>(
                String.format(
                    "Should wait the given time between attempts on attempt %d",
                    position
                ),
                executions.get(position).plusMillis(wait),
                Matchers.lessThanOrEqualTo(executions.get(position + 1))
            ).affirm();
        }
    }
}
