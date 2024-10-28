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

package org.cactoos.experimental;

import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.cactoos.scalar.LengthOf;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Threads}.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.CloseResource" })
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
    void containsResults() {
        final ExecutorService extor = Executors.newFixedThreadPool(3);
        try {
            new Assertion<>(
                "Must contain results from callables",
                new Threads<>(
                    extor,
                    Duration.ofSeconds(1L),
                    () -> {
                        this.sleep();
                        return "txt 1";
                    },
                    () -> {
                        this.sleep();
                        return "txt 2";
                    },
                    () -> {
                        this.sleep();
                        return "txt 3";
                    }
                ),
                new HasValues<>(
                    "txt 1",
                    "txt 2",
                    "txt 3"
                )
            ).affirm();
        } finally {
            extor.shutdownNow();
        }
    }

    /**
     * Execution takes longer than timeout when
     *  {@link ExecutorService} was initiated by someone else.
     */
    @RepeatedTest(ThreadsTest.REPETITIONS)
    void failsDueToTimeoutWithExternalExecutorService() {
        final ExecutorService extor = Executors.newFixedThreadPool(2);
        try {
            new Assertion<>(
                "Must fail due to timeout",
                () -> new LengthOf(
                    new Threads<>(
                        extor,
                        Duration.ofMillis(1L),
                        () -> {
                            this.sleep();
                            return "txt 1";
                        },
                        () -> {
                            this.sleep();
                            return "txt 2";
                        },
                        () -> {
                            this.sleep();
                            return "txt 3";
                        }
                    )
                ).value(),
                new Throws<>(CancellationException.class)
            ).affirm();
        } finally {
            extor.shutdownNow();
        }
    }

    /**
     * Execute 1 task within executor service and ensure that we'll get the
     *  expected exception type.
     */
    @Test
    void failsDueToException() {
        final ExecutorService extor = Executors.newSingleThreadExecutor();
        try {
            new Assertion<>(
                "Must rethrow error",
                () -> new LengthOf(
                    new Threads<>(
                        extor,
                        Duration.ofSeconds(1L),
                        () -> {
                            this.sleep();
                            return "txt 1";
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
            ).affirm();
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
        new Assertion<>(
            "Must contain results from the callables when using inline executor service",
            new Threads<>(
                3,
                Duration.ofSeconds(1L),
                () -> {
                    this.sleep();
                    return "txt 1";
                },
                () -> {
                    this.sleep();
                    return "txt 2";
                },
                () -> {
                    this.sleep();
                    return "txt 3";
                }
            ),
            new HasValues<>("txt 1", "txt 2", "txt 3")
        ).affirm();
    }

    /**
     * Execution takes longer than timeout when
     *  {@link ExecutorService} was initiated by {@link Threads} itself.
     */
    @RepeatedTest(ThreadsTest.REPETITIONS)
    void failsDueToTimeoutWithInlineExecutorService() {
        new Assertion<>(
            "Must fail due to timeout",
            () -> new LengthOf(
                new Threads<>(
                    2,
                    Duration.ofMillis(1L),
                    () -> {
                        this.sleep();
                        return "txt 1";
                    },
                    () -> {
                        this.sleep();
                        return "txt 2";
                    },
                    () -> {
                        this.sleep();
                        return "txt 3";
                    }
                )
            ).value(),
            new Throws<>(CancellationException.class)
        ).affirm();
    }

    /**
     * Execute the tasks concurrently using {@link Threads} when
     *  {@link ExecutorService} was initiated by someone else.
     */
    @RepeatedTest(ThreadsTest.REPETITIONS)
    void containsResultsNoTimeout() {
        final ExecutorService extor = Executors.newFixedThreadPool(3);
        try {
            new Assertion<>(
                "Must contain results from the callables without using timeout",
                new Threads<>(
                    extor,
                    () -> {
                        this.sleep();
                        return "txt 1";
                    },
                    () -> {
                        this.sleep();
                        return "txt 2";
                    },
                    () -> {
                        this.sleep();
                        return "txt 3";
                    }
                ),
                new HasValues<>("txt 1", "txt 2", "txt 3")
            ).affirm();
        } finally {
            extor.shutdownNow();
        }
    }

    /**
     * Execute 1 task within executor service and ensure that we'll get the
     *  expected exception type.
     */
    @Test
    void failsDueToExceptionNoTimeout() {
        final ExecutorService extor = Executors.newSingleThreadExecutor();
        try {
            new Assertion<>(
                "Must rethrow error",
                () -> new LengthOf(
                    new Threads<>(
                        extor,
                        () -> {
                            this.sleep();
                            return "txt 1";
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
            ).affirm();
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
        new Assertion<>(
            "Must contain results from the callables when using inline executor without timeout",
            new Threads<>(
                3,
                () -> {
                    this.sleep();
                    return "txt 1";
                },
                () -> {
                    this.sleep();
                    return "txt 2";
                },
                () -> {
                    this.sleep();
                    return "txt 3";
                }
            ),
            new HasValues<>("txt 1", "txt 2", "txt 3")
        ).affirm();
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
