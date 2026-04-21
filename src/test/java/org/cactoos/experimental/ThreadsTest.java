/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.experimental;

import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Threads}.
 *
 * @since 1.0.0
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.CloseResource"})
final class ThreadsTest {

    /**
     * Repetitions when running test several times.
     */
    private static final int REPETITIONS = 5;

    /**
     * Execute the tasks concurrently using {@link Threads} when
     *  {@link ExecutorService} was initiated by someone else.
     */
    @RepeatedTest(ThreadsTest.REPETITIONS)
    void containsResults() throws Exception {
        final ExecutorService extor = Executors.newFixedThreadPool(3);
        try {
            MatcherAssert.assertThat(
                "Must contain results from callables",
                new Threads<>(
                    extor,
                    Duration.ofSeconds(1L),
                    () -> {
                        this.sleep();
                        return "alpha";
                    },
                    () -> {
                        this.sleep();
                        return "beta";
                    },
                    () -> {
                        this.sleep();
                        return "gamma";
                    }
                ),
                new HasValues<>(
                    "alpha",
                    "beta",
                    "gamma"
                )
            );
        } finally {
            extor.shutdownNow();
        }
    }

    /**
     * Execution takes longer than timeout when
     *  {@link ExecutorService} was initiated by someone else.
     */
    @RepeatedTest(ThreadsTest.REPETITIONS)
    void failsDueToTimeoutWithExternalExecutorService() throws Exception {
        final ExecutorService extor = Executors.newFixedThreadPool(2);
        try {
            MatcherAssert.assertThat(
                "Must fail due to timeout",
                () -> new LengthOf(
                    new Threads<>(
                        extor,
                        Duration.ofMillis(1L),
                        () -> {
                            this.sleep();
                            return "alpha";
                        },
                        () -> {
                            this.sleep();
                            return "beta";
                        },
                        () -> {
                            this.sleep();
                            return "gamma";
                        }
                    )
                ).value(),
                new Throws<>(CancellationException.class)
            );
        } finally {
            extor.shutdownNow();
        }
    }

    /**
     * Execute 1 task within executor service and ensure that we'll get the
     *  expected exception type.
     */
    @Test
    void failsDueToException() throws Exception {
        final ExecutorService extor = Executors.newSingleThreadExecutor();
        try {
            MatcherAssert.assertThat(
                "Must rethrow error",
                () -> new LengthOf(
                    new Threads<>(
                        extor,
                        Duration.ofSeconds(1L),
                        () -> {
                            this.sleep();
                            return "alpha";
                        },
                        () -> {
                            throw new IllegalStateException(
                                "Something went wrong"
                            );
                        }
                    )
                ).value(),
                new Throws<>(
                    "java.io.IOException: java.util.concurrent.ExecutionException: java.lang.IllegalStateException: Something went wrong",
                    UncheckedIOException.class
                )
            );
        } finally {
            extor.shutdownNow();
        }
    }

    /**
     * Execute the tasks concurrently using {@link Threads} when
     *  {@link ExecutorService} was initiated by {@link Threads} itself.
     */
    @RepeatedTest(ThreadsTest.REPETITIONS)
    void containsValuesWithInlineExecutorService() {
        MatcherAssert.assertThat(
            "Must contain results from the callables when using inline executor service",
            new Threads<>(
                3,
                Duration.ofSeconds(1L),
                () -> {
                    this.sleep();
                    return "alpha";
                },
                () -> {
                    this.sleep();
                    return "beta";
                },
                () -> {
                    this.sleep();
                    return "gamma";
                }
            ),
            new HasValues<>("alpha", "beta", "gamma")
        );
    }

    /**
     * Execution takes longer than timeout when
     *  {@link ExecutorService} was initiated by {@link Threads} itself.
     */
    @RepeatedTest(ThreadsTest.REPETITIONS)
    void failsDueToTimeoutWithInlineExecutorService() {
        MatcherAssert.assertThat(
            "Must fail due to timeout",
            () -> new LengthOf(
                new Threads<>(
                    2,
                    Duration.ofMillis(1L),
                    () -> {
                        this.sleep();
                        return "alpha";
                    },
                    () -> {
                        this.sleep();
                        return "beta";
                    },
                    () -> {
                        this.sleep();
                        return "gamma";
                    }
                )
            ).value(),
            new Throws<>(CancellationException.class)
        );
    }

    /**
     * Execute the tasks concurrently using {@link Threads} when
     *  {@link ExecutorService} was initiated by someone else.
     */
    @RepeatedTest(ThreadsTest.REPETITIONS)
    void containsResultsNoTimeout() throws Exception {
        final ExecutorService extor = Executors.newFixedThreadPool(3);
        try {
            MatcherAssert.assertThat(
                "Must contain results from the callables without using timeout",
                new Threads<>(
                    extor,
                    () -> {
                        this.sleep();
                        return "alpha";
                    },
                    () -> {
                        this.sleep();
                        return "beta";
                    },
                    () -> {
                        this.sleep();
                        return "gamma";
                    }
                ),
                new HasValues<>("alpha", "beta", "gamma")
            );
        } finally {
            extor.shutdownNow();
        }
    }

    /**
     * Execute 1 task within executor service and ensure that we'll get the
     *  expected exception type.
     */
    @Test
    void failsDueToExceptionNoTimeout() throws Exception {
        final ExecutorService extor = Executors.newSingleThreadExecutor();
        try {
            MatcherAssert.assertThat(
                "Must rethrow error",
                () -> new LengthOf(
                    new Threads<>(
                        extor,
                        () -> {
                            this.sleep();
                            return "alpha";
                        },
                        () -> {
                            throw new IllegalStateException(
                                "Something went wrong"
                            );
                        }
                    )
                ).value(),
                new Throws<>(
                    "java.io.IOException: java.util.concurrent.ExecutionException: java.lang.IllegalStateException: Something went wrong",
                    UncheckedIOException.class
                )
            );
        } finally {
            extor.shutdownNow();
        }
    }

    /**
     * Execute the tasks concurrently using {@link Threads} when
     *  {@link ExecutorService} was initiated by {@link Threads} itself.
     */
    @RepeatedTest(ThreadsTest.REPETITIONS)
    void containsValuesWithInlineExecutorServiceNoTimeout() {
        MatcherAssert.assertThat(
            "Must contain results from the callables when using inline executor without timeout",
            new Threads<>(
                3,
                () -> {
                    this.sleep();
                    return "alpha";
                },
                () -> {
                    this.sleep();
                    return "beta";
                },
                () -> {
                    this.sleep();
                    return "gamma";
                }
            ),
            new HasValues<>("alpha", "beta", "gamma")
        );
    }

    /**
     * Sleep.
     */
    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(100L);
        } catch (final InterruptedException iex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(iex);
        }
    }
}
