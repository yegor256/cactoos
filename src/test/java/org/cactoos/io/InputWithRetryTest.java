/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.io;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link InputWithRetry}.
 *
 * @since 1.0
 */
final class InputWithRetryTest {

    @Test
    void testInputWithRetryShouldRead() throws Exception {
        final AtomicInteger cnt = new AtomicInteger(0);
        final InputWithRetry iwr = new InputWithRetry(
            () -> {
                if (cnt.getAndIncrement() < 2) {
                    throw new IllegalArgumentException("Test exception");
                }
                return new DeadInputStream();
            },
            3,
            Duration.ZERO
        );
        new Assertion<>(
            "should read -1 from stream",
            iwr.stream().read(),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    void testExceededNumberOfAttempts() {
        final AtomicInteger cnt = new AtomicInteger(0);
        final InputWithRetry iwr = new InputWithRetry(
            () -> {
                if (cnt.getAndIncrement() < 3) {
                    throw new IllegalArgumentException("Test exception");
                }
                return new DeadInputStream();
            },
            3
        );
        new Assertion<>(
            "should throw same exception",
            iwr::stream,
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }
}
