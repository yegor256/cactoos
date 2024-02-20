/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
                Duration.of(-5, ChronoUnit.DAYS)
            ),
            new HasValue<>(0)
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    void runsScalarMultipleTimesWithWait() throws Exception {
        final int times = 3;
        final long wait = 500;
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
