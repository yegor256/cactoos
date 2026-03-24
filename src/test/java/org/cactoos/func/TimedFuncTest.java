/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import org.cactoos.iterable.Endless;
import org.cactoos.scalar.And;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Timed}.
 * @since 0.29.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TimedFuncTest {

    @Test
    void functionGetsInterrupted() {
        final long period = 100L;
        MatcherAssert.assertThat(
            "Not interrupted after timeout",
            () -> new Timed<Boolean, Boolean>(
                input -> {
                    return new And(
                        new Endless<>(() -> input)
                    ).value();
                },
                period
            ).apply(true),
            new Throws<>(TimeoutException.class)
        );
    }

    @Test
    void futureTaskIsCancelled() {
        final long time = 2000L;
        final Future<Boolean> future = Executors.newSingleThreadExecutor()
            .submit(
                () -> {
                    Thread.sleep(time);
                    return true;
                }
            );
        try {
            final long period = 50L;
            new Timed<Boolean, Boolean>(
                period,
                input -> future
            ).apply(true);
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception exp) {
            MatcherAssert.assertThat(
                "Must be canceled after 1 sec",
                future.isCancelled(),
                new IsTrue()
            );
        }
    }

    @Test
    void functionIsExecuted() throws Exception {
        final long period = 3000L;
        MatcherAssert.assertThat(
            "Must execute the function",
            new Timed<Boolean, Boolean>(
                input -> true,
                period
            ).apply(true),
            new IsTrue()
        );
    }
}
