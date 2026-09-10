/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.io;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link InputWithRetry}.
 * @since 1.0
 */
final class InputWithRetryTest {

    @Test
    void readsAfterRetries() throws Exception {
        final AtomicInteger cnt = new AtomicInteger(0);
        MatcherAssert.assertThat(
            "should read -1 from stream",
            new InputWithRetry(
                () -> {
                    if (cnt.getAndIncrement() < 2) {
                        throw new IllegalArgumentException("Test exception");
                    }
                    return new DeadInputStream();
                },
                cnt.get() + 3,
                Duration.ZERO
            ).stream().read(),
            new IsEqual<>(-1)
        );
    }

    @Test
    void throwsWhenAttemptsExceeded() {
        final AtomicInteger cnt = new AtomicInteger(0);
        MatcherAssert.assertThat(
            "should throw same exception",
            new InputWithRetry(
                () -> {
                    if (cnt.getAndIncrement() < 3) {
                        throw new IllegalArgumentException("Test exception");
                    }
                    return new DeadInputStream();
                },
                cnt.get() + 3
            )::stream,
            new Throws<>(IllegalArgumentException.class)
        );
    }
}
