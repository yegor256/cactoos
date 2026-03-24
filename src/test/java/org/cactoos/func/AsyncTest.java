/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsApplicable;
import org.llorllale.cactoos.matchers.Satisfies;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Async}.
 *
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.CloseResource")
final class AsyncTest {
    @Test
    void runsInBackground() {
        MatcherAssert.assertThat(
            "Must run in the background",
            new Async<>(
                input -> {
                    TimeUnit.DAYS.sleep(1L);
                    return "done!";
                }
            ),
            new IsApplicable<>(
                true,
                new Satisfies<>(
                    future -> !future.isDone()
                )
            )
        );
    }

    @Test
    void runsAsProcInBackground() {
        MatcherAssert.assertThat(
            "Must run proc in the background",
            input -> {
                final CountDownLatch latch = new CountDownLatch(1);
                new Async<>(
                    new FuncOf<>(ipt -> latch.countDown(), true)
                ).exec(input);
                latch.await();
                return true;
            },
            new IsApplicable<>(
                true, new IsEqual<>(true)
            )
        );
    }

    @Test
    void runsInBackgroundWithoutFuture() {
        final CountDownLatch latch = new CountDownLatch(1);
        MatcherAssert.assertThat(
            "Must run in the background without us touching the Future",
            new Async<>(
                new FuncOf<>(input -> latch.countDown(), true)
            ),
            new IsApplicable<>(
                true,
                new Satisfies<>(
                    future -> latch.await(1L, TimeUnit.SECONDS)
                )
            )
        );
    }

    @Test
    void runsInBackgroundWithThreadFactory() {
        final String name = "secret name for thread factory";
        final ThreadFactory factory = r -> new Thread(r, name);
        final CountDownLatch latch = new CountDownLatch(1);
        MatcherAssert.assertThat(
            "Must run in the background with specific thread factory",
            new Async<>(
                new FuncOf<>(
                    input -> {
                        if (!input.equals(Thread.currentThread().getName())) {
                            throw new IllegalStateException(
                                "Another thread factory was used"
                            );
                        }
                        latch.countDown();
                    },
                    true
                ),
                factory
            ),
            new IsApplicable<>(
                name,
                new Satisfies<>(
                    future -> {
                        future.get();
                        return latch.getCount() == 0L;
                    }
                )
            )
        );
    }

    @Test
    void runsInBackgroundWithExecutorService() {
        final String name = "secret name for thread executor";
        final ThreadFactory factory = r -> new Thread(r, name);
        final CountDownLatch latch = new CountDownLatch(1);
        MatcherAssert.assertThat(
            "Must run in the background with specific thread executor",
            new Async<>(
                new FuncOf<>(
                    input -> {
                        if (!input.equals(Thread.currentThread().getName())) {
                            throw new IllegalStateException(
                                "Another thread executor was used"
                            );
                        }
                        latch.countDown();
                    },
                    true
                ),
                Executors.newSingleThreadExecutor(factory)
            ),
            new IsApplicable<>(
                name,
                new Satisfies<>(
                    future -> {
                        future.get();
                        return latch.getCount() == 0L;
                    }
                )
            )
        );
    }

    @Test
    void shutsDownInternalExecutorOnClose() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final Async<Boolean, Boolean> async = new Async<>(
            input -> {
                latch.countDown();
                return input;
            }
        );
        final Future<Boolean> future = async.apply(true);
        latch.await(1L, TimeUnit.SECONDS);
        future.get(1L, TimeUnit.SECONDS);
        async.close();
        MatcherAssert.assertThat(
            "must reject tasks after close shuts down internal executor",
            () -> async.apply(true),
            new Throws<>(RejectedExecutionException.class)
        );
    }

    @Test
    void doesNotShutDownExternalExecutor() throws Exception {
        final ExecutorService exec = Executors.newSingleThreadExecutor();
        try {
            final CountDownLatch latch = new CountDownLatch(1);
            final Async<Boolean, Boolean> async = new Async<>(
                input -> {
                    latch.countDown();
                    return input;
                },
                exec
            );
            async.apply(true);
            latch.await(1L, TimeUnit.SECONDS);
            async.close();
            final Future<Boolean> after = exec.submit(() -> true);
            MatcherAssert.assertThat(
                "must not shut down external executor on close",
                after.get(1L, TimeUnit.SECONDS),
                new IsEqual<>(true)
            );
        } finally {
            exec.shutdownNow();
        }
    }
}
