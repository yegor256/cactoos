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
@SuppressWarnings("PMD.AvoidCatchingGenericException")
final class TimedFuncTest {

    @Test
    void functionGetsInterrupted() {
        MatcherAssert.assertThat(
            "Not interrupted after timeout",
            () -> new Timed<Boolean, Boolean>(
                input -> {
                    return new And(
                        new Endless<>(() -> input)
                    ).value();
                },
                100L
            ).apply(true),
            new Throws<>(TimeoutException.class)
        );
    }

    @Test
    void futureTaskIsCancelled() {
        final Future<Boolean> future = Executors.newSingleThreadExecutor()
            .submit(
                () -> {
                    Thread.sleep(2000L);
                    return true;
                }
            );
        try {
            new Timed<Boolean, Boolean>(
                50L,
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
        MatcherAssert.assertThat(
            "Must execute the function",
            new Timed<Boolean, Boolean>(
                input -> true,
                3000L
            ).apply(true),
            new IsTrue()
        );
    }
}
