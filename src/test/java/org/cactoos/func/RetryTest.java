/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.security.SecureRandom;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsApplicable;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Retry}.
 *
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class RetryTest {

    @Test
    void runsFuncMultipleTimes() {
        new Assertion<>(
            "Didn't run multiple times",
            new Retry<>(
                input -> {
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                    return 0;
                },
                Integer.MAX_VALUE
            ),
            new IsApplicable<>(true, 0)
        ).affirm();
    }

    @Test
    void runsFuncConditionMultipleTimes() {
        new Assertion<>(
            "Didn't check condition multiple times",
            new Retry<>(
                input -> {
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                    return true;
                },
                count -> count == Integer.MAX_VALUE
            ),
            new IsApplicable<>(true, true)
        ).affirm();
    }

    @Test
    void processInterruptExceptionOnWait() {
        final Thread main = Thread.currentThread();
        new Thread(
            () -> {
                while (true) {
                    if (Thread.State.TIMED_WAITING == main.getState()) {
                        main.interrupt();
                        return;
                    }
                }
            }
        ).start();
        new Assertion<>(
            "Must be interrupted on wait",
            () -> new Retry<>(
                input -> {
                    throw new IllegalArgumentException("Happens");
                },
                attempts -> false,
                Duration.ofMillis(Long.MAX_VALUE)
            ).apply(true),
            new Throws<>("sleep interrupted", InterruptedException.class)
        ).affirm();
    }
}
