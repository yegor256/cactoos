/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link Retry}.
 *
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RetryTest {

    @Test
    public void runsScalarMultipleTimes() throws Exception {
        new Assertion<>(
            "must retry in case of failure",
            new Retry<>(
                () -> {
                    // @checkstyle MagicNumberCheck (1 line)
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                    return 0;
                },
                Integer.MAX_VALUE
            ),
            new ScalarHasValue<>(0)
        ).affirm();
    }

    @Test
    public void runsScalarTwiceWithDefaults() throws Exception {
        // @checkstyle MagicNumberCheck (1 line)
        final AtomicInteger tries = new AtomicInteger(0);
        new Assertion<>(
            "Should run twice with defaults",
            new Retry<>(
                () -> {
                    // @checkstyle MagicNumberCheck (1 line)
                    if (tries.getAndIncrement() <= 1) {
                        throw new IllegalArgumentException("Not enough tries");
                    }
                    return 0;
                }
            ).value(),
           new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void runsScalarMultipleTimesIgnoringNegativeDuration()
    throws Exception {
        // @checkstyle MagicNumberCheck (2 line)
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
                // @checkstyle MagicNumberCheck (1 line)
                Duration.of(-5, ChronoUnit.DAYS)
            ).value(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void runsScalarMultipleTimesWithWait() throws Exception {
        // @checkstyle MagicNumberCheck (3 line)
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
        for (int position = 0; position < executions.size() - 1; position =
            position + 1) {
            final int actual = position;
            new Assertion<>(
                "Should wait the given time between attempts",
                executions.get(actual).plusMillis(wait),
                Matchers.lessThanOrEqualTo(executions.get(actual + 1))
            ).affirm();
        }
    }
}
